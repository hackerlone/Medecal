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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Message;
import com.lhfeiyu.po.RegularRemind;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.service.RegularRemindService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;
@Controller
public class UserRegularRemindAction {
	@Autowired
	private RegularRemindService  regularRemindService;
	@Autowired
	private IndexService  indexService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/regularRemindList")
	public ModelAndView  regularRemindList(ModelMap modelMap,HttpServletRequest request
			,@RequestParam(required=false) String flag){
		String path = PagePath.regularRemindList;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = indexService.getIntroductionAndvision(modelMap);
			modelMap.put("user", user);
			if(null != flag){
				modelMap.put("flag", true);
			}
			
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-PAGE-/regularRemindList-加载定期提醒信息列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/addOrUpdateRegularRemind")
	public ModelAndView  addOrUpdateRegularRemind(ModelMap modelMap,HttpServletRequest request,
			@RequestParam(required=false) Integer id){
		String path = PagePath.addOrUpdateRegularRemind;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			if(null != id){
				RegularRemind regularRemind = regularRemindService.selectByPrimaryKey(id);
				modelMap.put("regularRemind", regularRemind);
			}
			modelMap = indexService.getIntroductionAndvision(modelMap);
			modelMap.put("user", user);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-PAGE-/addOrUpdateRegularRemind-加载添加或修改定期提醒信息出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateRegularRemind", method = RequestMethod.POST)
	public JSONObject addOrUpdateRegularRemind(@ModelAttribute RegularRemind regularRemind,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			if(null == regularRemind.getId()){
				regularRemindService.insertService(json, regularRemind, session_user);
			}else{
				regularRemindService.updateService(json, regularRemind, session_user);
			}
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-AJAX-/addOrUpdateRegularRemind-新增或修改定期提醒出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRegularRemindDelete",method=RequestMethod.POST)
	public JSONObject updateRegularRemindDelete(HttpServletRequest request, @RequestParam Integer id) {
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			RegularRemind regularRemind = regularRemindService.selectByPrimaryKey(id);
			if(null == regularRemind){
				return Result.failure(json, "定期提醒信息不存在", "user_null");
			}
			Integer sessionUserId = session_user.getId();
			Integer userId = regularRemind.getUserId();
			if(!Check.integerEqual(sessionUserId, userId)){
				return Result.failure(json, "您没有权限删除该定期提醒信息", "authority_error");
			}
			regularRemindService.updateDeletedNowById(id, session_user.getUsername());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-AJAX-/updateRegularRemindDelete-删除定期提醒信息出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRegularRemindList", method=RequestMethod.POST)
	public JSONObject getRegularRemindList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json,"user");
			String flag = request.getParameter("flag");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			if(null != flag){
				if(flag.equals("flag")){
					map.put("remindTime", 1);
				}
			}
			map.put("userId",session_user.getId());
			List<RegularRemind> regularRemindList = regularRemindService.selectListByCondition(map);
			Integer total = regularRemindService.selectCountByCondition(map);
			Result.gridData(regularRemindList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-AJAX-/getRegularRemindList-加载定期提醒列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/remindNewMsg", method = RequestMethod.POST)
	public JSONObject remindNewMsg(@ModelAttribute Message message,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("mainStatus", 1);
			map.put("userId", user.getId());
			map.put("remindTime", 1);
			int regularRemindCount = regularRemindService.selectCountByCondition(map);
			json.put("regularRemindCount", regularRemindCount);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-AJAX-/remindNewMsg-查询定期提醒出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateNotRemind", method = RequestMethod.POST)
	public JSONObject updateNotRemind(HttpServletRequest request,@RequestParam Integer id){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			if(null != id){
				RegularRemind regularRemind = regularRemindService.selectByPrimaryKey(id);
				if(null != regularRemind){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("id", id);
					map.put("expression1", "main_status = 2");//(1.提醒2.不提醒)
					regularRemindService.updateFieldById(map);
					Result.success(json);
				}else{
					Result.failure(json, "信息不存在", null);
				}
			}else{
				Result.failure(json, "参数有误", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-AJAX-/updateNotRemind-取消定期提醒出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRemind", method = RequestMethod.POST)
	public JSONObject updateRemind(HttpServletRequest request,@RequestParam Integer id){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			if(null != id){
				RegularRemind regularRemind = regularRemindService.selectByPrimaryKey(id);
				if(null != regularRemind){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("id", id);
					map.put("expression1", "main_status = 1");//(1.提醒2.不提醒)
					regularRemindService.updateFieldById(map);
					Result.success(json);
				}else{
					Result.failure(json, "信息不存在", null);
				}
			}else{
				Result.failure(json, "参数有误", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserRegularRemind-AJAX-/updateRemind-定期提醒出现异常", json);
		}
		return json;
	}
	
	
}
