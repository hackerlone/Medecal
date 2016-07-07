package com.lhfeiyu.action.front.domain.doctor;

import java.util.HashMap;
import java.util.List;
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
import com.lhfeiyu.po.Cancer;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.MedicationRepertory;
import com.lhfeiyu.po.MedicationType;
import com.lhfeiyu.service.CancerService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.service.MedicationTypeService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;

@Controller
public class TreatmentAction {
	@Autowired
	private MedicationTypeService medicationTypeService;
	@Autowired
	private MedicationRepertoryService medicationRepertoryService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private CancerService cancerService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/treatment")
	public ModelAndView treatment(ModelMap modelMap, HttpServletRequest request){
		String path = PagePath.treatment;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			//modelMap = doctorService.getDoctorData(modelMap,null,id);
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			Map<String,Object> map = CommonGenerator.getHashMap();
//			map.put("hospitalId", session_doctor.getHospitalId());
			List<MedicationType> mtList = medicationTypeService.selectListByCondition(map);//药物类型
			modelMap.put("mtList", mtList);
			
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Treatment-PAGE-/treatment-加载开始就诊页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorBloodTest")
	public ModelAndView doctorBloodTest(ModelMap modelMap, HttpServletRequest request){
		String path = PagePath.doctorBloodTest;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			//modelMap = doctorService.getDoctorData(modelMap,null,id);
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			/*Map<String,Object> map = CommonGenerator.getHashMap();
//			map.put("hospitalId", session_doctor.getHospitalId());
			List<MedicationType> mtList = medicationTypeService.selectListByCondition(map);//药物类型
			modelMap.put("mtList", mtList);*/
			List<Cancer> cancerList =  cancerService.selectListByCondition(null);
			modelMap.put("cancerList", cancerList);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Treatment-PAGE-/doctorBloodTest-加载医生血液检测页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectMedicationRemainingAndPrice", method = RequestMethod.POST)
	public JSONObject selectMedicationRemainingAndPrice(HttpServletRequest request,
			@RequestParam Integer medicationId,
			@RequestParam(required=false) Integer medicationNumber){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital && null == session_doctor)return Result.userSessionInvalid(json,"doctor");
			//if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer hospitalId = null;
			if(null != session_doctor){
				hospitalId = session_doctor.getHospitalId();//诊所id
			}else{
				hospitalId = hospital.getId();
			}
			if(null == hospitalId)return Result.failure(json, "该医生目前不属于任何诊所", "hospital_null");
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != medicationNumber && medicationNumber > 0){
				map.put("medicationNumber", medicationNumber);
			}
			map.put("hospitalId", hospitalId);
			map.put("medicationId", medicationId);
			Integer remainNum= medicationRepertoryService.selectMedicationNumberRemaining(map);
			MedicationRepertory medicationRepertory = medicationRepertoryService.selectByCondition(map);
			double medicalPrice = medicationRepertory.getPrice().doubleValue();
			if(null == remainNum){
				remainNum = -1;
			}
			json.put("medicalPrice", medicalPrice);
			json.put("remainNum", remainNum);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/selectMedicationNumberRemaining", json);
		}
		return json;
	}

	
}
