package com.easysoft.logserver.nio;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**
 * 
 * Title: StringProtocolInitalizer 
 * Description: 设置channel格式 包括消息的编码解码 业务逻辑处理类的设置
 * Company: easysoft.ltd
 * @author IvanHsu
 * @date 2019年6月6日
 */
@Component
public class StringInitalizer extends ChannelInitializer<SocketChannel> {

	/**
	 * 初始化channel 设置格式等
	 */
	@Override
	protected void initChannel(SocketChannel sockerChannel) throws Exception {
		ChannelPipeline pipeline = sockerChannel.pipeline();
		pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 2));
		pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		pipeline.addLast("handler", new ServerHandler());
	}

}
