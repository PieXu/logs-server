package com.easysoft.logserver.logs.service;

import com.easysoft.commons.mybatis.service.IBaseService;
import com.easysoft.logserver.logs.model.AuditUser;

/**
 * 
* Title: IUploadUserService
* Description: 
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年5月28日
 */
public interface IAuditUserService extends IBaseService<AuditUser>{

	public AuditUser getUserByLoginName(String name);

	public AuditUser getUserByAuthCode(String authcode);

}
