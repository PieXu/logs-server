package com.easysoft.logserver.nio;

import java.net.InetSocketAddress;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.easysoft.commons.helper.CommonsHelper;
import com.easysoft.logserver.logs.model.AuditLog;
import com.easysoft.logserver.logs.service.ILogService;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * 
* Title: ServerHandler
* Description: 处理日志消息类
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月6日
 */
@Component
@Sharable  
public class ServerHandler extends SimpleChannelInboundHandler<String> {

	public static ServerHandler handler;
	@Autowired
	private ILogService logService;
	@PostConstruct
    public void init(){
		handler = this;
		handler.logService = this.logService;
	}
	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String logMsg) throws Exception {
        try {
        	if(StringUtils.isNotBlank(logMsg)){
        		AuditLog log = JSONObject.parseObject(logMsg, AuditLog.class);
        		log.setId(CommonsHelper.getUUID());
        		log.setCreateTime(new Date());
        		handler.logService.insert(log);
        	}
//        	 boolean close = false;
//             if (logMsg.indexOf("bye") == 0) {
//                 close = true;
//                 response = "系统退出" + "\r\n";
//             }
//          ChannelFuture future =  ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
//          if(close){
//        	  future.addListener(ChannelFutureListener.CLOSE).sync().channel().closeFuture().sync();
////          future.addListener(ChannelFutureListener.CLOSE);
//          }
////            ctx.channel()
        } catch (Exception e) {
            ctx.writeAndFlush("日志存储失败>>>>>>");
            logger.error("日志存储失败:{} ", e.getMessage());
        }
        finally{
        	ReferenceCountUtil.release(logMsg);
        }
	}
	
	/*
	 * （非 Javadoc）
	* Title: channelActive
	* Description:连接上之后  打印连接信息 
	* @param ctx
	* @throws Exception
	* @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	    InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
	    String clientIp = insocket.getAddress().getHostAddress();
	    logger.info("日志客户端[{}]连接成功...",clientIp);
	    ctx.flush();
	}

	/*
	 * （非 Javadoc）
	* Title: exceptionCaught
	* Description: 异常捕获
	* @param ctx
	* @param cause
	* @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
	    String clientIp = insocket.getAddress().getHostAddress();
		logger.error("服务器处理客户端[{}]信息异常：{}",clientIp,cause.getMessage());
	    ctx.close();
	}
	
}
