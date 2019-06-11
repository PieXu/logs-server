package com.easysoft.logserver.logs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.easysoft.commons.cons.Constants;
import com.easysoft.commons.helper.CommonsHelper;
import com.easysoft.logserver.logs.model.AuditUser;
import com.easysoft.logserver.logs.model.ResultObject;
import com.easysoft.logserver.logs.service.IAuditUserService;
import com.easysoft.logserver.utils.RandomValidateCodeUtil;

/**
 * 
* Title: WebController
* Description: 
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月6日
 */
@Controller
public class WebController {
	
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	@Autowired
	private IAuditUserService userService;
	/**
	 * 
	 * Title: login Description: 登录页面
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login")
	public ResultObject login(HttpServletRequest request, Model model) 
	{
		ResultObject result = new ResultObject();
		try {
			String name = request.getParameter("username");
			String password = request.getParameter("password");
			String code = request.getParameter("code");
			HttpSession session = request.getSession();
			String sessionCode = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
			if(sessionCode.equals(code)){
				AuditUser user = userService.getUserByLoginName(name);
				if(null!=user){
					String realPwd = user.getPassword();
					String salt = user.getSalt();
					String encoderPassword = CommonsHelper.encoderPassword(salt, password);
					if(AuditUser.STATUS.NORMAL.toString().equalsIgnoreCase(user.getStatus())){
						if(encoderPassword.equals(realPwd)){
							session.setAttribute(Constants.SESSION_USER_KEY, user);
							session.setAttribute("username", user.getName());
							session.setAttribute("isSuper", user.getIsSuper());
							session.setAttribute("_userId", user.getId());
							result.setResult(ResultObject.OPERATE_RESULT.success.toString());
						}else{
							result.setResult(ResultObject.OPERATE_RESULT.fail.toString());
							result.setMessage("密码不正确");	
						}
					}else{
						result.setResult(ResultObject.OPERATE_RESULT.fail.toString());
						result.setMessage("当前用户已冻结，请联系管理员");
					}
				}else{
					result.setResult(ResultObject.OPERATE_RESULT.fail.toString());
					result.setMessage("当前用户不存在");
				}
			}else{
				result.setResult(ResultObject.OPERATE_RESULT.fail.toString());
				result.setMessage("验证码不正确");
			}
			
		} catch (Exception e) {
			result.setResult(ResultObject.OPERATE_RESULT.fail.toString());
			result.setMessage("登录异常，请联系管理员");
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	* Title: getVerify
	* Description: 验证码图片
	* @param request
	* @param response
	 */
	@RequestMapping(value = "/getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
			randomValidateCode.getRandcode(request, response);// 输出验证码图片方法
		} catch (Exception e) {
			logger.error("获取验证码失败>>>> ", e);
		}
	}
	
	/**
	 * 
	* Title: changPass
	* Description: 修改密码
	* @param request
	* @param model
	* @return
	 */
	@ResponseBody
	@RequestMapping(value={"changPass"})
	public ResultObject changPass(HttpServletRequest request,Model model)
	{
		ResultObject result = new ResultObject();
		try {
			String id = request.getParameter("userId");
			if(!StringUtils.isEmpty(id)){
				// 设置加密密码
				AuditUser user = userService.get(new AuditUser(id));
				String newPass = request.getParameter("newpassword");
				user.setPassword(CommonsHelper.encoderPassword(user.getSalt(), newPass));
				userService.update(user);
				result.setResult(ResultObject.OPERATE_RESULT.success.toString());
				result.setMessage("密码修改成功！");
			}else{
				result.setResult(ResultObject.OPERATE_RESULT.fail.toString());
				result.setMessage("参数ID 为空 ");
			}
		} catch (Exception e) {
			result.setResult(ResultObject.OPERATE_RESULT.fail.toString());
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 
	 * Title: main Description: 菜单跳转
	 * 
	 * @param request
	 * @param model
	 * @param path
	 * @return
	 */
	@RequestMapping("{path}")
	public String main(HttpServletRequest request, Model model, @PathVariable("path") String path) {
		if("main".equalsIgnoreCase(path)){
			JSONObject json = getSysInfo();
			model.addAttribute("sysInfo", json);
		}
		model.addAttribute("path", path);
		return path;
	}
	
	/**
	 * 
	* Title: logout
	* Description: 退出
	* @param request
	* @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request)
	{
		request.getSession().removeAttribute(Constants.SESSION_USER_KEY);
		return "redirect:/";
	}
	
	
	/**
	 * 
	* Title: getData
	* Description: 控制台系统的信息
	* @param request
	* @param model
	* @return
	 */
	private JSONObject getSysInfo()
	{
		JSONObject jsonData = new JSONObject();
		jsonData.put("version" , System.getProperty("java.class.version"));
		jsonData.put("vendor" , System.getProperty("java.specification.vendor"));
		jsonData.put("vendorurl" , System.getProperty("java.vendor.url"));
		jsonData.put("jdkhome" , System.getProperty("java.home"));
		jsonData.put("vmversion" , System.getProperty("java.vm.version"));
		jsonData.put("osversion" , System.getProperty("os.version"));
		jsonData.put("osarch" , System.getProperty("os.arch"));
		jsonData.put("osname" , System.getProperty("os.name"));
		jsonData.put("tmpdir" , System.getProperty("java.io.tmpdir"));
		jsonData.put("vmname" , System.getProperty("java.vm.name"));
		jsonData.put("vmvendor" , System.getProperty("java.vm.vendor"));
		jsonData.put("tmpdir" , System.getProperty("java.io.tmpdir"));
		jsonData.put("userdir" , System.getProperty("user.dir"));
		jsonData.put("userhome" , System.getProperty("user.home"));
		return jsonData;
	}
	
}
