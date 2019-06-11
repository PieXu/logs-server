package com.easysoft.logserver.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * Title: LogServer 
 * Description: 日志服务 Company: easysoft.ltd
 * 
 * @author IvanHsu
 * @date 2019年6月6日
 */
@Component
public class LogServer {

	private Logger logger = LoggerFactory.getLogger(LogServer.class);
	@Value("${easysoft.log.server.bossThreads}")
	private int bossCount;
	@Value("${easysoft.log.server.workerThreads}")
	private int workerCount;
	@Value("${easysoft.log.server.port}")
	private int tcpPort;
	@Value("${easysoft.log.server.keepalive}")
	private boolean keepAlive;
	@Value("${easysoft.log.server.backlog}")
	private int backlog;
	@Value("${easysoft.log.server.loglevel}")
	private String loglevel;

	/**
	 * 
	 * Title: start 
	 * Description:创建辅助工具类ServerBootstrap，用于服务器通道的一系列配置
	 */
	public void start() {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		// 绑定2个线程组，一个主线程组，一个工作线程组
		serverBootstrap.group(bossGroup(), workerGroup()).channel(NioServerSocketChannel.class) // 指定NIO的模式.NioServerSocketChannel对应TCP
				.option(ChannelOption.SO_BACKLOG, backlog).handler(new LoggingHandler(loglevel))// 设置日志级别
				/*
				 * 设置这样做好的好处就是禁用nagle算法 Nagle算法试图减少TCP包的数量和结构性开销,
				 * 将多个较小的包组合成较大的包进行发送.但这不是重点, 关键是这个算法受TCP延迟确认影响,
				 * 会导致相继两次向连接发送请求包, 读数据时会有一个最多达500毫秒的延时.
				 * TCP/IP协议中，无论发送多少数据，总是要在数据前面加上协议头，同时，对方接收到数据，也需要发送ACK表示确认。
				 * 为了尽可能的利用网络带宽，TCP总是希望尽可能的发送足够大的数据。（一个连接会设置MSS参数，因此，TCP/
				 * IP希望每次都能够以MSS尺寸的数据块来发送数据）。
				 * Nagle算法就是为了尽可能发送大块数据，避免网络中充斥着许多小数据块。
				 */
				.childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, keepAlive);
		// 监听业务处理
		serverBootstrap.childHandler(new StringInitalizer());
		// 绑定端口,开始接收进来的连接
		ChannelFuture channelFuture;
		try {
			channelFuture = serverBootstrap.bind(tcpPort).sync();
			logger.info("日志服务启动成功，监听服务端口为{}",tcpPort);
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 创建线两个事件循环组 一个是用于处理服务器端接收客户端连接的主線程 selector
	 * @return
	 */
	@Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
	public EventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossCount);
	}

	/**
	 * 创建线两个事件循环组， 一个是进行网络通信的（网络读写的），工作線程
	 * @return
	 */
	@Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(workerCount);
	}
}
