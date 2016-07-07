package com.lhfeiyu.action.back.base.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.LoginLog;
import com.lhfeiyu.service.AdminService;
import com.lhfeiyu.service.LoginLogService;
import com.lhfeiyu.util.Md5Util;

@Controller
@RequestMapping(value = "/back")
public class BackLoginAction {
	@Autowired
	private AdminService service;
	@Autowired
	private LoginLogService logService;
	
	private static Logger logger = Logger.getLogger("R");
	
	/** 登陆页面 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView backLogin() {
		return new ModelAndView(PagePath.backLogin);
	}
	
	/** 进行登陆  , 只接受POST请求 */
	@ResponseBody
	@RequestMapping(value = "/doBackLogin", method = RequestMethod.POST)
	public JSONObject doBackLogin(HttpServletRequest request,HttpSession session,
			@RequestParam String verificationCode,@RequestParam String loginAccount,@RequestParam String password) {
		JSONObject json = new JSONObject();
		try{
			String ip = request.getRemoteAddr();
			Object codeObj = session.getAttribute("randomCode");
			if(null == codeObj){
				json.put("status","randomCode_error");//验证码输入错误
				json.put("msg","验证码输入错误");
				return json;
			}
			String randomCode = codeObj.toString();
			if(!(randomCode.equalsIgnoreCase(verificationCode)) && !"1".equals(verificationCode)){//  1:方便测试，后期删除
				json.put("status","randomCode_error");//验证码输入错误
				json.put("msg","验证码输入错误");
				return json;
			}
			
			loginAccount = loginAccount.trim();//因为方法参数已经过滤为空的情况，所以不用担心username为null
			password = password.trim();//去除空格
			if(!"".equals(loginAccount) && !"".equals(password)){
				Map<String,Object> map = new HashMap<String,Object>();
				String encrypt_pswd = Md5Util.encrypt(password);
				map.put("loginAccount", loginAccount);
				map.put("password", encrypt_pswd);
				Admin admin = service.selectByCondition(map);
				if(null != admin){
					
					loginSuccess(json, session, ip, admin);//登陆成功
					
				}else{
					json.put("status","input_error");
					json.put("msg","用户名或密码输入错误");
				}
			}else{
				json.put("status","input_isneed");
				json.put("msg","用户名和密码不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("LH_ERROR_登陆异常_"+e.getMessage());
			json.put("status","server_error");
			json.put("msg","服务器出现异常，请联系管理员。");
		}
		return json;
	}
	
	private JSONObject loginSuccess(JSONObject json,HttpSession session,String ip,Admin user){
		Admin admin = new Admin();
		Integer adminId = user.getId();
		Date date = new Date();
		admin.setId(adminId);
		admin.setLastLoginTime(date);
		service.updateByPrimaryKeySelective(user);
		session.setAttribute("admin", user);
		session.setAttribute("adminId", user.getId());
		LoginLog log = new LoginLog();
		log.setUserId(user.getId());
		log.setUsername(user.getUsername());
		log.setLoginTime(date);
		log.setLoginIp(ip);
		log.setCreatedAt(date);
		log.setCreatedBy("system");
		logService.insert(log);
		json.put("status","success");
		json.put("msg","登陆成功");
		return json;
	}
	
}