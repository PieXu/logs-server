package com.easysoft.logserver.logs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.easysoft.commons.mybatis.service.impl.BaseServiceImpl;
import com.easysoft.logserver.logs.dao.AuditUserDao;
import com.easysoft.logserver.logs.model.AuditUser;
import com.easysoft.logserver.logs.service.IAuditUserService;
/**
 * 
* Title: AuditUserServiceImpl
* Description: 
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年5月28日
 */
@Service
public class AuditUserServiceImpl extends BaseServiceImpl<AuditUser> implements IAuditUserService{

	@Autowired
	private AuditUserDao userDao;
	
	/*
	 * （非 Javadoc）
	* Title: getUserByLoginName
	* Description: 
	* @param name
	* @return
	* @see com.innovate.filseserver.service.IUploadUserService#getUserByLoginName(java.lang.String)
	 */
	@Override
	public AuditUser getUserByLoginName(String name) {
		if(StringUtils.hasText(name)){
			return userDao.getUserByLoginName(name);
		}
		return null;
	}

	/*
	 * （非 Javadoc）
	* Title: getUserByAuthCode
	* Description: 
	* @param authcode
	* @return
	* @see com.innovate.filseserver.service.IUploadUserService#getUserByAuthCode(java.lang.String)
	 */
	@Override
	public AuditUser getUserByAuthCode(String authcode) {
		if(StringUtils.hasText(authcode)){
			return userDao.getUserByAuthCode(authcode);
		}
		return null;
	}

}
