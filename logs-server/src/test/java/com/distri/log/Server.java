package com.distri.log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws Exception {
		
		// 要连接的服务端IP地址和端口
	    int port = 55533;
		ServerSocket socketServer = new ServerSocket(port);
		while(true)
		{
			Socket accept = socketServer.accept();
			InputStream inputStream = accept.getInputStream();
			byte[] bytes = new byte[1024];
		    int len;
		    StringBuilder sb = new StringBuilder();
		    while ((len = inputStream.read(bytes)) != -1) {
		      //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
		      sb.append(new String(bytes, 0, len,"UTF-8"));
		    }
		    System.out.println("get message from client: " + sb);
		    Thread.sleep(5*10*1000);
		    inputStream.close();
		    accept.close();
		   // socketServer.close();
		}
		
	}
}
