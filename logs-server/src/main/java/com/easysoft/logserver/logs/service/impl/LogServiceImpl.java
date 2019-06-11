package com.easysoft.logserver.logs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easysoft.commons.mybatis.service.impl.BaseServiceImpl;
import com.easysoft.logserver.logs.dao.LogDao;
import com.easysoft.logserver.logs.model.AuditLog;
import com.easysoft.logserver.logs.service.ILogService;

/**
 * 
* Title: LogSeerviceImpl
* Description: 
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月6日
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<AuditLog> implements ILogService{

	@Autowired
	private LogDao logDao;
}
