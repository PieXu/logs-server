package com.distri.log;

import java.io.OutputStream;
import java.net.Socket;

public class Cliect2 {
	
	public static void main(String[] args) throws Exception{
		// 要连接的服务端IP地址和端口
	    String host = "127.0.0.1"; 
	    int port = 9000;
		Socket socket = new Socket(host, port);
		// 建立连接后获得输出流
	    OutputStream outputStream = socket.getOutputStream();
	    String message="你好  服务端，我是客户端2";
	    socket.getOutputStream().write(message.getBytes("UTF-8"));
	    outputStream.close();
	    socket.close();
	}

}
