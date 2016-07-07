package com.lhfeiyu.action.front.base.sys;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.ConstField;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.SendMsgUtil;
import com.lhfeiyu.util.YTX_MSG;
import com.lhfeiyu.util.loadingVerificationCodeUtil;

@Controller
public class LoginAction {
	@Autowired
	private UserService userService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private IndexService indexService;
	
	private static Logger logger = Logger.getLogger("R");
	
	/** 登陆页面 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelMap modelMap,HttpServletRequest request) {
		modelMap = indexService.getData(modelMap,request);
		return new ModelAndView(PagePath.login,modelMap);
	}
	
	@RequestMapping(value = "/doctorLogin", method = RequestMethod.GET)
	public ModelAndView doctorLogin(ModelMap modelMap,HttpServletRequest request) {
		modelMap = indexService.getData(modelMap,request);
		return new ModelAndView(PagePath.doDctorLogin,modelMap);
	}
	@RequestMapping(value = "/hospitalLogin", method = RequestMethod.GET)
	public ModelAndView hospitalLogin(ModelMap modelMap,HttpServletRequest request) {
		modelMap = indexService.getData(modelMap,request);
		return new ModelAndView(PagePath.doHspitalLogin,modelMap);
	}
	
	@RequestMapping(value = "/jumpToLogin", method = RequestMethod.GET)
	public ModelAndView jumpToLogin(ModelMap modelMap,HttpServletRequest request) {
		return new ModelAndView(PagePath.login);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView reg(ModelMap modelMap,HttpServletRequest request){
		modelMap = indexService.getData(modelMap,request);
		return new ModelAndView(PagePath.register,modelMap);
	}
	
	/** 进行登陆  , 只接受POST请求 */
	@ResponseBody
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public JSONObject doLogin(HttpServletRequest request,HttpSession session,
			@RequestParam String loginAccount, @RequestParam String password,
			@RequestParam(defaultValue="user") String loginType,
			@RequestParam(required=false) String verificationCode) {
		JSONObject json = new JSONObject();
		try{
			int errorTimes = 1;
			Object errorTimesObj = session.getAttribute("errorTimes");
			if(null != errorTimesObj){//已经记录了该IP有输入错误记录
				errorTimes = (Integer)errorTimesObj;
				if(errorTimes > 30){
					return Result.failure(json, "输入的错误次数太多，请点击找回密码或者联系管理员", "max_errors");
				}
			}
			String ip = request.getRemoteAddr();
			Object codeObj = session.getAttribute("randomCode");
			String randomCode = null;
			if(null != verificationCode && null != codeObj){//有验证码
				randomCode = codeObj.toString();
				if(!randomCode.equalsIgnoreCase(verificationCode) && !"1".equals(verificationCode)){// && !"1".equals(verificationCode) 1:方便测试，后期删除
					return Result.failure(json, "验证码输入错误", "randomCode_error");
				}
			}else{//没有验证码
				Object err_ip_record = session.getServletContext().getAttribute(ip);
				if(null !=  err_ip_record){//已经记录了该IP有输入错误记录
					return Result.failure(json, "验证码输入错误", "randomCode_null");
				}
			}
			loginAccount = loginAccount.trim();
			password = password.trim();
			if(loginAccount.length() < 5){
				return Result.failure(json, "登陆账号不能少于5个字符", "loginId_short");
			}
			if(password.length() < 5){
				return Result.failure(json, "登陆密码不能少于5个字符", "password_short");
			}
			if(Check.isNull(loginAccount) || Check.isNull(password)){
				return Result.failure(json, "登陆账号和密码不能为空", "input_null");
			}
			Map<String,Object> map = CommonGenerator.getHashMap();
			String encrypt_pswd = Md5Util.encrypt(password);
			map.put("loginAccount", loginAccount);
			map.put("password", encrypt_pswd);
			map.put("mainStatus", 1);
			
			//登陆成功
			if("user".equals(loginType)){
				userLoginSuccess(session, json, map, errorTimes, ip);
				if(Result.hasError(json))return json;
			}else if("doctor".equals(loginType)){
				doctorLoginSuccess(session, json, map, errorTimes, ip);
				if(Result.hasError(json))return json;
			}else if("hospital".equals(loginType)){
				hospitalLoginSuccess(session, json, map, errorTimes, ip);
				if(Result.hasError(json))return json;
			}
			return Result.success(json, "登陆成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Login-AJAX-/doLogin-用户登陆出现异常"+loginType, json);
		}
		return json;
	}
	

	private JSONObject userLoginSuccess(HttpSession session, JSONObject json, Map<String,Object> map, int errorTimes, String ip){
		User user = userService.selectByCondition(map);
		if(null == user){
			session.getServletContext().setAttribute(ip, 1);//在application中标识该IP输错了一次账号密码，再次输入需要验证码
			session.setAttribute("errorTimes", ++errorTimes);
			return Result.failure(json, "登陆账号或密码输入错误", "input_error");
		}
		Date date = CommonGenerator.getDate();
		User userSave = new User();
		userSave.setId(user.getId());
		userSave.setLastLoginTime(date);
		userService.updateByPrimaryKeySelective(userSave);
		user.setLastLoginTime(date);
		session.setAttribute("user", user);
		session.setAttribute("loginType", "user");
		session.setAttribute("userId", user.getId());
		session.getServletContext().removeAttribute(ip);
		session.removeAttribute("errorTimes");
		return json;
	}
	
	private JSONObject doctorLoginSuccess(HttpSession session, JSONObject json, Map<String,Object> map, int errorTimes, String ip){
		Doctor doctor = doctorService.selectByCondition(map);
		if(null == doctor){
			session.getServletContext().setAttribute(ip, 1);//在application中标识该IP输错了一次账号密码，再次输入需要验证码
			session.setAttribute("errorTimes", ++errorTimes);
			return Result.failure(json, "登陆账号或密码输入错误", "input_error");
		}
		Date date = CommonGenerator.getDate();
		Doctor doctorSave = new Doctor();
		doctorSave.setId(doctor.getId());
		// 数据库新增字段，lastLoginTime
		doctorSave.setLastLoginTime(date);
		doctorService.updateByPrimaryKeySelective(doctorSave);
		doctor.setLastLoginTime(date);
		session.setAttribute("doctor", doctor);
		session.setAttribute("loginType", "doctor");
		session.setAttribute("doctorId", doctor.getId());
		session.getServletContext().removeAttribute(ip);
		session.removeAttribute("errorTimes");
		return json;
	}
	
	private JSONObject hospitalLoginSuccess(HttpSession session, JSONObject json, Map<String, Object> map, int errorTimes,
			String ip) {
		Hospital hospital = hospitalService.selectByCondition(map);
		if(null == hospital){
			session.getServletContext().setAttribute(ip, 1);//在application中标识该IP输错了一次账号密码，再次输入需要验证码
			session.setAttribute("errorTimes", ++errorTimes);
			return Result.failure(json, "登陆账号或密码输入错误", "input_error");
		}
		Date date = CommonGenerator.getDate();
		Hospital hospitalSave = new Hospital();
		hospitalSave.setId(hospital.getId());
		hospitalSave.setLastLoginTime(date);
		hospitalService.updateByPrimaryKeySelective(hospitalSave);
		hospital.setLastLoginTime(date);
		session.setAttribute("hospital", hospital);
		session.setAttribute("loginType", "hospital");
		session.setAttribute("hospitalId", hospital.getId());
		session.getServletContext().removeAttribute(ip);
		session.removeAttribute("errorTimes");
		return json;
	}
	
	
	/** 加载验证码  , 只接受POST请求 */
	@ResponseBody
	@RequestMapping(value="/getVerifycode", method = RequestMethod.POST)
	public JSONObject getVerifycode(HttpServletRequest request, HttpSession session, 
			@RequestParam(required = false) String phone){
		JSONObject json = new JSONObject();
		try{
			int randomCodeNum = 1;
			Object randomCodeNumObj = session.getAttribute("randomCodeNum");
			if(null != randomCodeNumObj){
				randomCodeNum = (Integer)randomCodeNumObj;
				if(randomCodeNum > 20){
					return Result.failure(json, "请求验证码的次数太多，请直接联系管理员帮助您注册吧", "max_errors");
				}
			}
			String randomCode = SendMsgUtil.createRandomVcode();
			System.out.println("reg_randomCode: "+randomCode);
			if(Check.isNotNull(phone)){//phone : 短信接口发送短信验证码
				String mobanId = ConstField.rl_ytx_msg_moban_id;
				String[] params = new String[] { randomCode, "30" };
				YTX_MSG.send(phone, mobanId, params);
				json.put("msg", "验证码已发送到您的手机,请及时查看");
			}
			session.setAttribute("randomCode", randomCode);
			session.setAttribute("randomCodeNum", ++randomCodeNum);
			json.put("randomCode", randomCode);
			Result.success(json);
		}catch(Exception e){
			Result.catchError(e, logger, "LH_ERROR-Login-AJAX-/getVerifycode-加载验证码出现异常", json);
		}
		return json;
	}
	
	/** 进行注册  , 只接受POST请求 */
	@ResponseBody
	@RequestMapping(value="/doReg", method = RequestMethod.POST)
	public JSONObject doReg(HttpServletRequest request,HttpSession session,
			@ModelAttribute User user, @RequestParam String randomCode){
		JSONObject json = new JSONObject();
		try{
			Object codeObj = session.getAttribute("randomCode");
			if(null == codeObj){
				return Result.failure(json, "请输入验证码", "randomCode_null");
			}
			String code = (String)codeObj;
			if(!code.equalsIgnoreCase(randomCode.trim())){
				return Result.failure(json, "验证码输入错误", "randomCode_error");
			}
			session.removeAttribute("randomCode");
			String ip = request.getRemoteAddr();//客户端IP地址
			userService.validateRegUser(json, user);//验证输入数据合法性,判断该用户是否已经注册
			if(Result.hasError(json))return json;
			user = userService.addRegUser(json, user, ip);
			session.setAttribute("user", user);
			session.setAttribute("userId", user.getId());
				
			session.getServletContext().removeAttribute(ip);
			session.removeAttribute("errorTimes");
			
			Result.success(json);
		}catch(Exception e){
			Result.catchError(e, logger, "LH_ERROR-Login-AJAX-/doReg-用户注册出现异常", json);
		}
		return json;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public JSONObject logout(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			request.getSession().invalidate();
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Login-AJAX-/doReg-用户注销出现异常", json);
		}
		return json;
	}
	
	/** 加载图形验证码 */
	@RequestMapping(value = "/login/loadVerificationCode", method = RequestMethod.GET)
	public String loadVerificationCode(HttpServletResponse response, HttpSession session) {
		BufferedImage bais = loadingVerificationCodeUtil.createImage();
	    response.setHeader("Pragma", "No-cache");//禁止缓存
	    response.setHeader("Cache-Control", "No-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("image/jpeg");//指定生成的响应是图片
	    session.removeAttribute("randomCode");//销毁验证码
	    session.setAttribute("randomCode", loadingVerificationCodeUtil.strCode);//把验证码存到session
	    try {
			ImageIO.write(bais,"JPEG",response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("LH_ERROR-Login-AJAX-/doReg-加载图形验证码出现异常-"+e.getMessage());
		} 
	    return null;
	}
	
}