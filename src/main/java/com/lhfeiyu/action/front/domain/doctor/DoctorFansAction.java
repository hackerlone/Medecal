package com.lhfeiyu.action.front.domain.doctor;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Fans;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.FansService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;

@Controller
public class DoctorFansAction {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private FansService fansService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/doctorList")
	public ModelAndView  doctorList(ModelMap modelMap,HttpServletRequest request,
			@RequestParam(required=false) String searchDoctorName ){
		String path = PagePath.doctorList;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			modelMap.put("searchDoctorName", searchDoctorName);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-DoctorFans-PAGE-/doctorList-加载医生列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/joinMyFans", method = RequestMethod.POST)
	public JSONObject joinMyFans(HttpServletRequest request,@RequestParam Integer id){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer doctorId = session_doctor.getId();
			String doctorName = session_doctor.getUsername();
			if(null != id){
				Fans fans = new Fans();
				fans.setUserId(doctorId);
				fans.setFansId(id);
				fans.setMainStatus(1);
				fans.setCreatedAt(new Date());
				fans.setCreatedBy(doctorName);
				fansService.insert(fans);
				Result.success(json);
			}else{
				Result.failure(json, "参数有误", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DoctorFans-AJAX-/joinMyFans-加入我的圈子出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cancelJoinMyFans", method = RequestMethod.POST)
	public JSONObject cancelJoinMyFans(HttpServletRequest request,@RequestParam Integer id){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer doctorId = session_doctor.getId();
			if(null != id){
				Map<String,Object> map = CommonGenerator.getHashMap();
				map.put("fansId", id);
				map.put("mainStatus", 1);
				Fans db_fans = fansService.selectByCondition(map);
				if(null == db_fans){
					return Result.failure(json, "信息不存在", "fans_null");
				}
				Integer userId = db_fans.getUserId();
				if(!Check.integerEqual(doctorId, userId)){
					return Result.failure(json, "您没有权限删除该信息", "authority_error");
				}
				Integer deleteId = db_fans.getId();
				fansService.deleteByPrimaryKey(deleteId);
				Result.success(json);
			}else{
				Result.failure(json, "参数有误", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DoctorFans-AJAX-/cancelJoinMyFans-我的圈子中删除出现异常", json);
		}
		return json;
	}
	
	
	
}
