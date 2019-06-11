package com.easysoft.logserver.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 
* Title: LogServerRunner
* Description: 日志服务启动执行方法
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月6日
 */
@Component
public class LogServerRunner implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(LogServerRunner.class);
	@Autowired
	private LogServer logServer;
	/**
	 * 启动方法
	 */
	@Override
	public void run(String... paramArrayOfString) throws Exception {
		try {
			logServer.start();
		} catch (Exception e) {
			logger.error(" 日志服务TCP监听启动异常:{}", e);
			e.printStackTrace();
		}
	}
}
