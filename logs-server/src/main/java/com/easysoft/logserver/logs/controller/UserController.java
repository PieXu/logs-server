package com.easysoft.logserver.logs.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.easysoft.commons.helper.CommonsHelper;
import com.easysoft.logserver.logs.model.AuditUser;
import com.easysoft.logserver.logs.service.IAuditUserService;
import com.github.pagehelper.Page;

/**
 * 
* Title: UserController
* Description: 用户管理 列表 新建 授权 验证等
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年5月24日
 */
@RestController
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IAuditUserService userService;
	
	/**
	 * 
	* Title: listUsers
	* Description: 列表查询
	* @param reqeust
	* @param response
	* @param page
	* @param limit
	* @param user
	* @return
	 */
	@RequestMapping(value={"user/list"},produces = "application/json")
	public JSONObject listUsers(HttpServletRequest reqeust, HttpServletResponse response, 
			Integer page, Integer limit,AuditUser user) {
		JSONObject json = new JSONObject();
		Page<AuditUser> filePage = userService.getPage(user, page, limit, "create_time desc");
		json.put("code", 0);
		json.put("msg", "查询成功");
		json.put("count",filePage.getTotal());
		json.put("data", filePage.getResult());
		return json;
	}
	
	/**
	 * 
	* Title: delete
	* Description: 删除
	* @param reqeust
	* @param ids
	* @return
	 */
	@RequestMapping("user/delete")
	public JSONObject delete(HttpServletRequest reqeust,String ids)
	{
		JSONObject json = new JSONObject();
		try {
			userService.delByIds(new AuditUser(), ids);
			json.put("success", true);
		} catch (Exception e) {
			json.put("success", false);
			logger.error(e.getMessage());
		}
		return json;
	}
	
	/**
	 * 
	* Title: changeStatus
	* Description: 
	* @param reqeust
	* @param id
	* @param status
	* @return
	 */
	@RequestMapping("user/changeStatus")
	public JSONObject changeStatus(HttpServletRequest reqeust,String id,String status)
	{
		JSONObject json = new JSONObject();
		try {
			AuditUser user = new AuditUser();
			user.setId(id);
			user.setStatus(status);
			userService.update(user);
			json.put("success", true);
		} catch (Exception e) {
			json.put("success", false);
			logger.error(e.getMessage());
		}
		return json;
	}
	
	/**
	 * 
	* Title: saveOrUpdate
	* Description: 
	* @param reqeust
	* @param user
	* @return
	 */
	@RequestMapping("user/saveOrUpdate")
	public JSONObject saveOrUpdate(HttpServletRequest reqeust,AuditUser user)
	{
		JSONObject json = new JSONObject();
		try {
			String id = user.getId();
			if(StringUtils.isNotBlank(id)){
				user.setUpdateTime(new Date());
				userService.update(user);
			}else{
				user.setId(CommonsHelper.getUUID());
				user.setCreateTime(new Date());
				user.setUpdateTime(new Date());
				user.setStatus(AuditUser.STATUS.NORMAL.toString());
				String salt = CommonsHelper.getSalt(5);
				user.setSalt(salt);
				user.setAuthCode(CommonsHelper.encoder("file_uplaod_key_"+salt+new Date().getTime()));
				user.setPassword(CommonsHelper.encoderPassword(salt, user.getPassword()));
				userService.insert(user);
			}
			json.put("success", true);
		} catch (Exception e) {
			json.put("success", false);
			json.put("msg", "数据更新失败！");
			logger.error(e.getMessage());
		}
		return json;
	}
	
}
