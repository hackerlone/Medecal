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
import com.lhfeiyu.po.Diagnose;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.DiagnoseService;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;
import com.lhfeiyu.vo.DiagnoseTag;
import com.lhfeiyu.vo.Prescription;
@Controller
public class UserDiagnoseAction {
	@Autowired
	private DiagnoseService  diagnoseService;
	@Autowired
	private IndexService  indexService;
	@Autowired
	private ProvinceCityAreaService  provinceCityAreaService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/userDiagnoseList")
	public ModelAndView  userDiagnoseList(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.userDiagnoseList;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-UserDiagnose-PAGE-/userDiagnoseList-加载用户中心出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value = "/myDoctor")
	public ModelAndView myDoctor(ModelMap modelMap,HttpServletRequest request,
			@RequestParam(required=false) Integer diagnoseId) {
		String path = PagePath.myDoctor;
		try {
			User db_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == db_user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = indexService.getIntroductionAndvision(modelMap);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-UserDiagnose-PAGE-/updateDiagnose-加载修改患者病历出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value = "/readDiagnose/{diagnoseId}")
	public ModelAndView updateDiagnose(ModelMap modelMap,HttpServletRequest request,
			@PathVariable Integer diagnoseId) {
		String path = PagePath.updateDiagnose;
		try {
			User db_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == db_user)return Result.userSessionInvalid(modelMap,PagePath.login,"user");
			if(null != diagnoseId){
				Map<String,Object> hashMap = CommonGenerator.getHashMap();
				hashMap.put("id", diagnoseId);
				Diagnose diagnose = diagnoseService.selectByCondition(hashMap);
				modelMap.put("diagnose", diagnose);
				String prescription = diagnose.getPrescription();
				String diagnoseTags = diagnose.getDiagnoseTags();
				if(Check.isNotNull(prescription)){
					List<Prescription> prescriptionList = JSONObject.parseArray(prescription, Prescription.class);
					List<DiagnoseTag> diagnoseTagList = JSONObject.parseArray(diagnoseTags, DiagnoseTag.class);
					modelMap.put("prescriptionList", prescriptionList);
					modelMap.put("diagnoseTagList", diagnoseTagList);
				}
			}
			modelMap.put("userId", db_user.getId());
			modelMap = indexService.getIntroductionAndvision(modelMap);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-UserDiagnose-PAGE-/updateDiagnose-加载修改患者病历出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMyDoctorList", method=RequestMethod.POST)
	public JSONObject getMyDoctorList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json, "user");
			Integer userId = session_user.getId();
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			diagnoseService.getDoctorData(json, session_user,map);
			json.put("userId", userId);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserDiagnose-AJAX-/getUserDiagnoseList-加载患者病历列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserDiagnoseList", method=RequestMethod.POST)
	public JSONObject getUserDiagnoseList(HttpServletRequest request, 
			@RequestParam(required=false) Integer isLink) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json, "user");
			//String idcardNumIN = null;
			Integer userId = session_user.getId();
			String idcardNum = session_user.getIdcardNum();
			/*
			Map<String,Object> usermap = new HashMap<String,Object>();
			usermap.put("relationId", userId);
			List<User> userList = userService.selectListByCondition(usermap);
			if(Check.isNotNull(userList)){
				idcardNumIN = "\""+idcardNum+"\"";
				for(User u:userList){
					idcardNumIN += ","+"\""+u.getIdcardNum()+"\"";
				}
			}else{
				idcardNumIN = "\""+idcardNum+"\"";
			}*/
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			//map.put("idcardNumIN", idcardNumIN);
			if(Check.isNotNull(isLink)){
				map.put("linkUserId", userId);
			}else{
				map.put("idcardNumIN", idcardNum);
			}
			List<Diagnose> diagnoseList = diagnoseService.selectListByCondition(map);
			Integer total = diagnoseService.selectCountByCondition(map);
			Result.gridData(diagnoseList, total, json);
			json.put("userId", userId);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserDiagnose-AJAX-/getUserDiagnoseList-加载患者病历列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDiagnose", method = RequestMethod.POST)
	public JSONObject updateDiagnose(@ModelAttribute Diagnose diagnose,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json, "user");
			diagnoseService.updateService(json,diagnose,session_user);
			json.put("id", diagnose.getId());
			Result.success(json, "修改病历成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserDiagnose-AJAX-/updateDiagnose-修改病历出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDiagnoseDelete",method=RequestMethod.POST)
	public JSONObject updateDiagnoseDelete(HttpServletRequest request, @RequestParam Integer diagnoseId) {
		JSONObject json = new JSONObject();
		try {
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json, "user");
			Diagnose diagnose = diagnoseService.selectByPrimaryKey(diagnoseId);
			if(null == diagnose){
				return Result.failure(json, "病历不存在", "diagnose_null");
			}
			String sessionIdcardNum = session_user.getIdcardNum();
			String idcardNum = diagnose.getPatientIdcardNum();
			if(sessionIdcardNum.equals(idcardNum)){
				return Result.failure(json, "您没有权限删除该病历", "authority_error");
			}
			diagnoseService.updateDeletedNowById(diagnoseId, session_user.getUsername());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserDiagnose-AJAX-/updateDiagnoseDelete-删除病历出现异常", json);
		}
		return json;
	}
	
}
