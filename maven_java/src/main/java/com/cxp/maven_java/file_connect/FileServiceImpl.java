package com.cxp.maven_java.file_connect;

import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import org.apache.thrift.TException;

public class FileServiceImpl implements FileService.Iface {

	@Override
	public boolean uploadFile(FileData filedata) throws TException {
		 System.out.println("===============成功接收到文件==================");

        // 写到文件
        String filePath = "G:\\cxp.png";
        try
        {
            java.io.File file = new java.io.File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            FileChannel channel = fos.getChannel();
            channel.write(filedata.buff);
            channel.close();
            System.out.println("===============成功写入到G:\\cxp.png==================");
        }
        catch (Exception x)
        {
            x.printStackTrace();
            return false;
        }
        return true;
	}

}
