package com.lhfeiyu.action.front.domain.hospital;




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
import com.lhfeiyu.po.DiagnoseTemplate;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Medication;
import com.lhfeiyu.po.MedicationType;
import com.lhfeiyu.service.DiagnoseTemplateService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.service.MedicationService;
import com.lhfeiyu.service.MedicationTypeService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class HospitalDiagnoseTemplateAction {
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private DiagnoseTemplateService diagnoseTemplateService;
	@Autowired
	private MedicationTypeService medicationTypeService;
	@Autowired
	private MedicationService medicationService;
	@Autowired
	private MedicationRepertoryService medicationRepertoryService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value = "/diagnoseTemplate")
	public ModelAndView diagnoseTemplate(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.hospitalDiagnoseTemplate;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			modelMap.put("hospitalId", db_hospital.getId());
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseTemplate-PAGE-/diagnoseTemplate-加载诊所病历模板页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@RequestMapping(value = "/addOrUpdateDiagnoseTemplateForHospital")
	public ModelAndView addOrUpdateArticleForHospital(ModelMap modelMap,HttpServletRequest request,
			@RequestParam(required=false) Integer diagnoseTemplateId) {
		String path = PagePath.addOrUpdateDiagnoseTemplateForHospital;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			if(null != diagnoseTemplateId){
				modelMap = hospitalService.getDiagnoseTemplateData(modelMap, db_hospital, diagnoseTemplateId);
			}
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("hospitalId", db_hospital.getId());
			List<MedicationType> mtList = medicationTypeService.selectListByCondition(map);//药物类型
			modelMap.put("mtList", mtList);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseTemplate-PAGE-/addOrUpdateDiagnoseTemplateForHospital-加载诊所文章添加或修改出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseTemplateList", method=RequestMethod.POST)
	public JSONObject getDiagnoseTemplateList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("hospitalId", hospital.getId());
			List<DiagnoseTemplate> diagnoseTemplateList = diagnoseTemplateService.selectListByCondition(map);
			Integer total = diagnoseTemplateService.selectCountByCondition(map);
			Result.gridData(diagnoseTemplateList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseTemplate-AJAX-/getDiagnoseTemplateList-加载病历模板列表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDiagnoseTemplateForHospital", method = RequestMethod.POST)
	public JSONObject addOrUpdateDiagnoseTemplateForHospital(@ModelAttribute DiagnoseTemplate diagnoseTemplate,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			if(null == diagnoseTemplate.getId()){//添加
				diagnoseTemplateService.insertService(json,diagnoseTemplate,hospital);
			}else{
				diagnoseTemplateService.updateService(json,diagnoseTemplate,hospital);
			}
			json.put("id", diagnoseTemplate.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseTemplate-AJAX-/addOrUpdateDiagnoseTemplateForHospital-新增或修改病历模板出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDiagnoseTemplateDeleteForHospital",method=RequestMethod.POST)
	public JSONObject updateDiagnoseTemplateDeleteForHospital(HttpServletRequest request, @RequestParam Integer diagnoseTemplateId) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			DiagnoseTemplate diagnoseTemplate = diagnoseTemplateService.selectByPrimaryKey(diagnoseTemplateId);
			if(null == diagnoseTemplate){
				return Result.failure(json, "模板不存在", "diagnoseTemplate_null");
			}
			Integer sessionHospitalId = hospital.getId();
			Integer hospitalId = Integer.valueOf(diagnoseTemplate.getHospitalId());
			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
				return Result.failure(json, "您没有权限删除该模板", "authority_error");
			}
			diagnoseTemplateService.updateDeletedNowById(diagnoseTemplateId, hospital.getWholeName());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseTemplate-AJAX-/updateDiagnoseTemplateDeleteForHospital-删除病历模板出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationListForHospital", method=RequestMethod.POST)
	public JSONObject getMedicationList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("hospitalId", hospital.getId());
			List<Medication> medicationList = medicationService.selectListByCondition(map);
			Integer total = medicationService.selectCountByCondition(map);
			Result.gridData(medicationList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseTemplate-AJAX-/getMedicationList-加载药物列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectMedicationNumberRemainingForHospital", method = RequestMethod.POST)
	public JSONObject selectMedicationNumberRemainingForHospital(HttpServletRequest request,
			@RequestParam Integer medicationId,
			@RequestParam(required=false) Integer medicationNumber){
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			Integer hospitalId = hospital.getId();//诊所id
			if(null == hospitalId)return Result.failure(json, "该诊所不存在", "hospital_null");
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != medicationNumber && medicationNumber > 0){
				map.put("medicationNumber", medicationNumber);
			}
			map.put("hospitalId", hospitalId);
			map.put("medicationId", medicationId);
			Integer remainNum= medicationRepertoryService.selectMedicationNumberRemaining(map);
			if(null == remainNum){
				remainNum = -1;
			}
			json.put("remainNum", remainNum);
			Result.success(json);
			
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/selectMedicationNumberRemainingForHospital", json);
		}
		return json;
	}
	
}
