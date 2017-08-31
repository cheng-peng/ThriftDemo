package com.cxp.maven_java;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

import com.cxp.maven_java.file_connect.FileService;
import com.cxp.maven_java.file_connect.FileServiceImpl;
import com.cxp.maven_java.string_connect.HelloWorldService;
import com.cxp.maven_java.string_connect.HelloWorldServiceImpl;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) {

		// 字符串连接
//		 stringConnect();

		// 文件连接
		fileConnect();
	}

	// 字符串连接
	private static void stringConnect() {
		try {
			// 实现反转字符串的Processor,传入实现类ReverseImp
			HelloWorldService.Processor processor = new HelloWorldService.Processor(
					new HelloWorldServiceImpl());
			// 构建Thrift通信协议，设置其中的参数
			TNonblockingServerSocket socket = new TNonblockingServerSocket(1234);
			TNonblockingServer.Args arg = new TNonblockingServer.Args(socket);
			arg.protocolFactory(new TBinaryProtocol.Factory());
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			// 根据设定好的参数，构建server，并启动server
			TServer server = new TNonblockingServer(arg);
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}

	// 文件连接
	private static void fileConnect() {
		try {
			// 实现反转字符串的Processor,传入实现类ReverseImp
			FileService.Processor processor = new FileService.Processor<FileService.Iface>(
					new FileServiceImpl());
			// 构建Thrift通信协议，设置其中的参数
			TNonblockingServerSocket socket = new TNonblockingServerSocket(
					12345);
			TNonblockingServer.Args arg = new TNonblockingServer.Args(socket);
			arg.protocolFactory(new TBinaryProtocol.Factory());
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			// 根据设定好的参数，构建server，并启动server
			TServer server = new TNonblockingServer(arg);
			server.serve();

			// // 创建非阻塞的 Transport
			// TNonblockingServerTransport serverSocket = new
			// TNonblockingServerSocket(12345);
			//
			// // 创建 Processor
			// TProcessor processor = new
			// FileService.Processor<FileService.Iface>(new FileServiceImpl());
			//
			// // 创建 transport factory , Nonblocking 使用 TFramedTransport
			// TTransportFactory transportFactory = new
			// TFramedTransport.Factory();
			//
			// // 创建 protocol factory
			// TBinaryProtocol.Factory protocolFactory = new
			// TBinaryProtocol.Factory();
			//
			// // 创建 arguments
			// TThreadedSelectorServer.Args tArgs = new
			// TThreadedSelectorServer.Args(serverSocket);
			// tArgs.processor(processor);
			// tArgs.transportFactory(transportFactory);
			// tArgs.protocolFactory(protocolFactory);
			//
			// // 创建 server
			// TServer server = new TThreadedSelectorServer(tArgs);
			//
			// // 启动 server
			// server.serve();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
