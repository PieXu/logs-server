package com.distri.log;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import io.netty.util.CharsetUtil;

public class NClient extends Thread {

	private int idx;
	private static CountDownLatch cdl = new CountDownLatch(100);
	
	public NClient(int idx){
		this.idx = idx;
	}
	
	public void run()
	{
		try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000));
			Selector selector = Selector.open();
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			
			 while(true){
		            //选择注册过的io操作的事件(第一次为SelectionKey.OP_CONNECT)
		            selector.select();
		            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
		            while(ite.hasNext()){
		                SelectionKey key = ite.next();
		                //删除已选的key，防止重复处理
		                ite.remove();
		                if(key.isConnectable()){
		                    SocketChannel channel=(SocketChannel)key.channel();
		                    
		                    //如果正在连接，则完成连接
		                    if(channel.isConnectionPending()){
		                        channel.finishConnect();
		                    }
		                    
		                    channel.configureBlocking(false);
		                    //向服务器发送消息
		                    channel.write(ByteBuffer.wrap(new String(this.idx+"第一条消息....").getBytes()));
		                    Thread.sleep(5*10*1000);
		                    channel.write(ByteBuffer.wrap(new String(this.idx+"第二条消息").getBytes()));
		                    
		                    //连接成功后，注册接收服务器消息的事件
		                    channel.register(selector, SelectionKey.OP_READ);
		                    System.out.println("客户端连接成功");
		                }else if(key.isReadable()){ //有可读数据事件。
		                    SocketChannel channel = (SocketChannel)key.channel();
		                    
		                    ByteBuffer buffer = ByteBuffer.allocate(1024);
		                    channel.read(buffer);
		                    byte[] data = buffer.array();
		                    String message = new String(data,CharsetUtil.UTF_8);
		                    System.out.println("接受返回的消息:" + message);
//		                    ByteBuffer outbuffer = ByteBuffer.wrap(("client.".concat(msg)).getBytes());
//		                    channel.write(outbuffer);
		                }
		            }
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 100; i++) {
			new Thread(new NClient(i)).start();
			cdl.countDown();
		}
		//new NClient().start();
	}
}
