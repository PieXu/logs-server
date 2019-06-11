package com.easysoft.logserver.logs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.easysoft.logserver.logs.model.AuditLog;
import com.easysoft.logserver.logs.service.ILogService;
import com.github.pagehelper.Page;

/**
 * 
* Title: LogsController
* Description: 日志管理维护
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月6日
 */
@RestController
public class LogsController {

	@Autowired
	private ILogService logService;
	
	@RequestMapping(value={"logs/list"},produces = "application/json")
	public JSONObject listFiles(HttpServletRequest reqeust, HttpServletResponse response, 
			Integer page, Integer limit,AuditLog logs) {
		JSONObject json = new JSONObject();
		Page<AuditLog> logPage = logService.getPage(logs, page, limit, "create_time desc");
		json.put("code", 0);
		json.put("msg", "查询成功");
		json.put("count",logPage.getTotal());
		json.put("data", logPage.getResult());
		return json;
	}
	
}
