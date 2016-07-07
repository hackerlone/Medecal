package com.lhfeiyu.action.front.domain.hospital;


import java.util.List;

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
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.po.Cancer;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.service.BespeakService;
import com.lhfeiyu.service.CancerService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Result;

@Controller
@RequestMapping(value="/hospital")
public class HospitalBespeakAction {
	@Autowired
	private BespeakService bespeakService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private CancerService cancerService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@RequestMapping(value = "/hospitalBespeak")
	public ModelAndView hospitalBespeak(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.hospitalBespeak;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalBespeak-PAGE-/hospitalBespeak-加载预约出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value = "/addOrUpdateBespeakForHospital")
	public ModelAndView addOrUpdateArticleForHospital(ModelMap modelMap,HttpServletRequest request
			,@RequestParam(required=false) Integer bespeakId) {
		String path = PagePath.addOrUpdateBespeakForHospital;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			if(null != bespeakId){
				modelMap = hospitalService.getBespeakData(modelMap,db_hospital,bespeakId);
			}
			List<Cancer> cancerTypes = cancerService.selectListByCondition(null);
			modelMap.put("cancerTypes", cancerTypes);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalBespeak-PAGE-/addOrUpdateBespeakForHospital-加载诊所预约添加或修改出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateBespeakForHospital", method = RequestMethod.POST)
	public JSONObject addOrUpdateBespeakForHospital(@ModelAttribute Bespeak bespeak,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			if(null == bespeak.getId()){//添加
				bespeakService.insertService(json,bespeak,hospital);
			}else{
				bespeakService.updateService(json,bespeak,hospital);
			}
			json.put("id", bespeak.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalBespeak-AJAX-/addOrUpdateBespeakForHospital-新增或修改预约出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateBespeakDeleteForHospital",method=RequestMethod.POST)
	public JSONObject updateBespeakDeleteForHospital(HttpServletRequest request, @RequestParam Integer bespeakId) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			Bespeak bespeak = bespeakService.selectByPrimaryKey(bespeakId);
			if(null == bespeak){
				return Result.failure(json, "预约不存在", "bespeak_null");
			}
			Integer sessionHospitalId = hospital.getId();
			Integer hospitalId = bespeak.getHospitalId();
			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
				return Result.failure(json, "您没有权限删除该预约", "authority_error");
			}
			bespeakService.updateDeletedNowById(bespeakId, hospital.getWholeName());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalBespeak-AJAX-/updateBespeakDeleteForHospital-删除预约出现异常", json);
		}
		return json;
	}
	
	
}
