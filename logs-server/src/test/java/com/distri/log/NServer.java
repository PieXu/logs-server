package com.distri.log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NServer {

	/**
	 * 启动服务端
	 * @throws Exception 
	 */
	public void init() throws Exception
	{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//绑定本地端口
		//serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",9999));
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		//设置成非阻塞
		serverSocketChannel.configureBlocking(false);
		
		Selector sector = Selector.open();
		serverSocketChannel.register(sector, SelectionKey.OP_ACCEPT);
		
		while(true){
			//SocketChannel socketChannel = serverSocketChannel.accept();
			sector.select();
           Iterator<SelectionKey> iterator = sector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				if(selectionKey.isAcceptable()){
					ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    //获得客户端连接通道
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    //向客户端发消息
                    channel.write(ByteBuffer.wrap(new String("success").getBytes()));
                    //在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ事件。
                    channel.register(sector, SelectionKey.OP_READ);
                    
//                  System.out.println("客户端请求连接事件");
				}else if(selectionKey.isConnectable()){
					System.out.println("已经连接");
				}else if(selectionKey.isReadable()){
					ByteBuffer receivebuffer =  ByteBuffer.allocate(10);
					 // 返回为之创建此键的通道。
					SocketChannel  client = (SocketChannel) selectionKey.channel();
		            //将缓冲区清空以备下次读取
		            receivebuffer.clear();
		            //读取服务器发送来的数据到缓冲区中
		            int   count = client.read(receivebuffer);
		            if (count > 0) {
		                System.out.println("服务器端接受客户端数据--:"+new String( receivebuffer.array(),0,count));
		                client.register(sector, SelectionKey.OP_WRITE);
		            }
					
//					SocketChannel  channel = (SocketChannel) selectionKey.channel();
//				
//					int n = channel.read(buff);   
//		            while (n != -1) {    
//		            	buff.flip();
//						System.out.print((char) buff.get());     // switch to OP_WRITE   
//						n = channel.read(buff);   
//		            }     
//					System.out.println("读取数据完成！");
//					 channel.register(sector, SelectionKey.OP_WRITE);
				}else if(selectionKey.isReadable()){
					System.out.println("可以写数据");
				}
				
			}
		
		}
	}
	
	
	/**
	 * 并监听
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new NServer().init();
	}
}
