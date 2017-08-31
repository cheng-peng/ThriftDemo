package com.cxp.maven_java.string_connect;

import org.apache.thrift.TException;

public class HelloWorldServiceImpl implements HelloWorldService.Iface{

	@Override
	public String sayHello(String strParam) throws TException {
		  // 参数为空，返回空
        if (strParam == null || "".equals(strParam)) {
            return "HelloWorld!!!";
        }
        String str=strParam+",HelloWorld!!!";
		return str;
	}

}
