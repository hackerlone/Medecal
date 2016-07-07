package com.lhfeiyu.action.front.domain.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Consult;
import com.lhfeiyu.po.Dict;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Message;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.ConsultService;
import com.lhfeiyu.service.DictService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.service.MessageService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class UserAction {
	
	@Autowired
	private UserService  userService;
	@Autowired
	private ProvinceCityAreaService  provinceCityAreaService;
	@Autowired
	private DictService  dictService;
	@Autowired
	private IndexService  indexService;
	@Autowired
	private ConsultService  consultService;
	@Autowired
	private HospitalService  hospitalService;
	@Autowired
	private DoctorService  doctorService;
	@Autowired
	private MessageService  messageService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/userHome")
	public ModelAndView  userHome(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.frontUser;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			modelMap = userService.getPatientData(modelMap,user);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/userHome-加载用户中心出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/goHome")
	public ModelAndView  goHome(ModelMap modelMap,HttpServletRequest request){
		String path = "";
		try{
			String loginTypeStr = (String) request.getSession().getAttribute("loginType");
			if(null != loginTypeStr){
				if(loginTypeStr.equals("user")){
					User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
					if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
					path = PagePath.userBaseInformation;
					userBaseInformation(modelMap,request);
				}else if(loginTypeStr.equals("doctor")){
					Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
					if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
					path = PagePath.frontDoctor;
					modelMap = doctorService.getDoctorData(modelMap,session_doctor,null);
				}else if(loginTypeStr.equals("hospital")){
					Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
					if(null == hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospital");
					path = PagePath.frontHospital;
					modelMap = hospitalService.getHospitalData(modelMap,hospital,null);
				}
			}else{
				path = PagePath.index;
				modelMap = indexService.getData(modelMap,request);
			}
				
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/userHome-加载返回各自的主页出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/userConsultDetail/{id}")
	public ModelAndView  userConsultDetail(ModelMap modelMap,HttpServletRequest request
			,@PathVariable Integer id){
		String path = PagePath.consultDetail;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			if(Check.isNotNull(id)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", id);
				Consult consult = consultService.selectByCondition(map);
				modelMap.put("consult", consult);
			}
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/userConsultDetail-加载咨询详情页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/userNotice")
	public ModelAndView  userNotice(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.userNotice;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/userNotice-加载用户消息出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@RequestMapping(value="/userInternalMessage")
	public ModelAndView  userInternalMessage(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.internalMessage;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/userInternalMessage-加载用户站内信出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/consultRecord")
	public ModelAndView  consultRecord(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.consultRecord;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			modelMap = indexService.getIntroductionAndvision(modelMap);
			modelMap.put("userId", user.getId());
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/consultRecord-加载用户咨询记录出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	@RequestMapping(value="/relationPatientList")
	public ModelAndView  relationPatientList(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.relationPatientList;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/relationPatientList-加载关联患者列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/userBaseInformation")
	public ModelAndView  userBaseInformation(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.userBaseInformation;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			map.clear();
			map.put("parentCode", "job");
			List<Dict> dictList = dictService.selectListByCondition(map);
			modelMap.put("jobList", dictList);
			modelMap = indexService.getIntroductionAndvision(modelMap);
			returnDbUser(user,request,modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/userBaseInformation-加载用户基本信息出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/relationPatient")
	public ModelAndView  relationPatient(ModelMap modelMap,HttpServletRequest request,
			@RequestParam(required=false) Integer id){
		String path = PagePath.relationPatient;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			if(null != id){
				User relationPatient = userService.selectByPrimaryKey(id);
				modelMap.put("relationPatient", relationPatient);
			}
			modelMap = userService.getUserData(modelMap,user);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			map.clear();
			map.put("parentCode", "job");
			List<Dict> dictList = dictService.selectListByCondition(map);
			modelMap.put("jobList", dictList);
			modelMap = indexService.getIntroductionAndvision(modelMap);
			modelMap.put("user", user);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/relationPatient-加载关联患者出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateRelationPatient", method = RequestMethod.POST)
	public JSONObject addOrUpdateRelationPatient(@ModelAttribute User user,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			if(null == user.getId()){
				userService.insertService(json, user, session_user);
			}else{
				userService.updateService(json, user, session_user);
			}
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/addOrUpdateRelationPatient-新增或修改关联患者出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public JSONObject updateUser(@ModelAttribute User user,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json, "user");
			userService.updateUser(json, user, session_user);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/updateUser-", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/searchRelationPatient", method=RequestMethod.POST)
	public JSONObject searchRelationPatient(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			User user = userService.selectByCondition(map);
			json.put("user", user);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/searchRelationPatient-加载关联患者出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRelationPatientList", method=RequestMethod.POST)
	public JSONObject getRelationPatientList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<User> userList = userService.selectListByCondition(map);
			Integer total = userService.selectCountByCondition(map);
			Result.gridData(userList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/getRelationPatientList-加载关联患者列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRelationPatientDelete",method=RequestMethod.POST)
	public JSONObject updateRelationPatientDelete(HttpServletRequest request, @RequestParam Integer id) {
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			User user = userService.selectByPrimaryKey(id);
			if(null == user){
				return Result.failure(json, "关联患者不存在", "user_null");
			}
			Integer sessionUserId = session_user.getId();
			Integer relationId = user.getRelationId();
			if(!Check.integerEqual(sessionUserId, relationId)){
				return Result.failure(json, "您没有权限删除该关联患者", "authority_error");
			}
			userService.updateDeletedNowById(id, session_user.getUsername());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/updateRelationPatientDelete-删除关联患者出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateUserPassword",method=RequestMethod.POST)
	public JSONObject updateUserPassword(HttpServletRequest request,
			@RequestParam String password) {
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			Integer sessionUserId = session_user.getId();
			User db_user = userService.selectByPrimaryKey(sessionUserId);
			if(null == db_user){
				return Result.failure(json, "该用户不存在", "user_null");
			}
			Integer db_userId = db_user.getId();
			if(!Check.integerEqual(sessionUserId, db_userId)){
				return Result.failure(json, "您没有权限修改该用户信息", "authority_error");
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", sessionUserId);
			map.put("expression1","password = '"+Md5Util.encrypt(password)+"'");
			userService.updateFieldById(map);
			request.getSession().invalidate();
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/updateUserPassword-修改用户密码出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addMessageToDoctor", method = RequestMethod.POST)
	public JSONObject addMessageToDoctor(@ModelAttribute Message message,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			messageService.insertService(json,message,user);
			json.put("id", message.getId());
			Result.success(json, "添加或修改留言成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/addMessageToDoctor-新增或修改留言出现异常", json);
		}
		return json;
	}
	
	public ModelMap returnDbUser(User user,HttpServletRequest request,ModelMap modelMap){
		Integer userId = user.getId();
		User db_user = userService.selectByPrimaryKey(userId);
		request.getSession().setAttribute("user", db_user);
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("loginType", "user");
		modelMap.put("user", user);
		return modelMap;
	}
	
	
}
