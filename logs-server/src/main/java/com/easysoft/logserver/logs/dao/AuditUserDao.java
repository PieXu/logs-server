package com.easysoft.logserver.logs.dao;

import org.apache.ibatis.annotations.Select;

import com.easysoft.commons.cons.Constants;
import com.easysoft.commons.mybatis.dao.IBaseDao;
import com.easysoft.logserver.logs.model.AuditUser;

/**
 * 
* Title: UploadUserDao
* Description: 
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年5月28日
 */
public interface AuditUserDao extends IBaseDao<AuditUser>{

	/**
	 * 
	* Title: getUserByLoginName
	* Description: 
	* @param name
	* @return
	 */
	@Select("select * from t_audit_user where login_name = #{name} and del_flag = '"+Constants.STATUS_NORMAL+"'")
	public AuditUser getUserByLoginName(String name);

	/**
	 * 
	* Title: getUserByAuthCode
	* Description: 
	* @param authcode
	* @return
	 */
	@Select("select * from t_audit_user where auth_code = #{authcode} and del_flag = '"+Constants.STATUS_NORMAL+"'")
	public AuditUser getUserByAuthCode(String authcode);

}
