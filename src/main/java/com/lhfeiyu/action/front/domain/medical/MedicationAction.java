package com.lhfeiyu.action.front.domain.medical;


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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Medication;
import com.lhfeiyu.po.MedicationRepertory;
import com.lhfeiyu.po.MedicationType;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.service.MedicationService;
import com.lhfeiyu.service.MedicationTypeService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class MedicationAction {
	@Autowired
	private MedicationService medicationService;
	@Autowired
	private MedicationRepertoryService medicationRepertoryService;
	@Autowired
	private MedicationTypeService  medicationTypeService;
	@Autowired
	private AA_UtilService  utilService;
	@Autowired
	private HospitalService  hospitalService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value = "/medication")
	public ModelAndView medication(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.medication;
		try {
			Hospital db_hospitalhospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospitalhospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			//modelMap = medicationService.getMedicationData(modelMap,db_hospitalhospital,null);
			modelMap = hospitalService.getHospitalData(modelMap,db_hospitalhospital,null);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-PAGE-/medication-加载诊所药物出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value = "/addOrUpdateMedication")
	public ModelAndView addOrUpdateArticle(ModelMap modelMap,HttpServletRequest request
			,@RequestParam(required=false) Integer medicationId) {
		String path = PagePath.addOrUpdateMedication;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			//modelMap = medicationService.getMedicationData(modelMap,db_hospital,null);
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			if(null != medicationId){
				//modelMap = medicationService.getMedicationData(modelMap,db_hospital,medicationId);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", medicationId);
				MedicationRepertory medication = medicationRepertoryService.selectByCondition(map);
				if(null != medication){
					modelMap.put("medication", medication);
				}
			}
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-PAGE-/addOrUpdateMedication-加载诊所药物添加或修改出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	@ResponseBody
	@RequestMapping(value = "/getMedicationTypeArray", method=RequestMethod.POST)
	public JSONArray getMedicationTypeArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<MedicationType> medication_typeList = medicationTypeService.selectListByCondition(map);
			for(MedicationType mt:medication_typeList){
				JSONObject json = new JSONObject();
				json.put("id",mt.getId());
				json.put("name",mt.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationType-AJAX-/back/getMedicationTypeArray-加载药物类型数组列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/loadMedicationComboboxByTypeId", method = RequestMethod.POST)
	public JSONObject loadMedicationComboboxByTypeId(HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			String typeId = request.getParameter("typeId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", typeId);
			map.put("groupBy", "id");
			List<Medication> list = medicationService.selectListByCondition(map);
			json.put("medicationList", list);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-AJAX-/addOrUpdateMedication-新增或修改药物出现异常", json);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/loadAttentionAndUsageAndDosage", method = RequestMethod.POST)
	public JSONObject loadAttentionAndUsageAndDosage(HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			String id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			Medication medication = medicationService.selectByCondition(map);
			json.put("medication",medication);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-AJAX-/loadAttentionAndUsageAndDosage-获取药品用法和禁忌出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateMedication", method = RequestMethod.POST)
	public JSONObject addOrUpdateMedication(@ModelAttribute Medication medication,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json,"hospital");
			if(null == medication.getId()){//添加
				medicationService.insertService(json,medication,db_hospital);
			}else{
				medicationService.updateService(json,medication,db_hospital);
			}
			json.put("id", medication.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-AJAX-/addOrUpdateMedication-新增或修改药物出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateMedicationRepertory", method = RequestMethod.POST)
	public JSONObject addOrUpdateMedicationRepertory(@ModelAttribute MedicationRepertory medicationRepertory,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json,"hospital");
			medicationRepertory.setHospitalId(db_hospital.getId());
			if(null == medicationRepertory.getId()){//添加
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("medicationId", medicationRepertory.getMedicationId());
				map.put("hospitalId", db_hospital.getId());
				MedicationRepertory db_medicationRepertory = medicationRepertoryService.selectByCondition(map);
				if(null != db_medicationRepertory){
					return Result.failure(json, "该诊所下已有该药品,请不要重复添加", null);
				}
				medicationRepertoryService.insert(medicationRepertory);
			}else{
				medicationRepertoryService.updateService(medicationRepertory);
			}
			json.put("id", medicationRepertory.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-AJAX-/addOrUpdateMedication-新增或修改药物出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationList", method=RequestMethod.POST)
	public JSONObject getMedicationList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json, "hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("hospitalId", db_hospital.getId());
			List<MedicationRepertory> medicationRepertoryList = medicationRepertoryService.selectListByCondition(map);
			Integer total = medicationRepertoryService.selectCountByCondition(map);
//			List<Medication> medicationList = medicationService.selectListByCondition(map);
//			Integer total = medicationService.selectCountByCondition(map);
			Result.gridData(medicationRepertoryList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Medication-AJAX-/getMedicationList-加载药物列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationDelete",method=RequestMethod.POST)
	public JSONObject updateMedicationDelete(HttpServletRequest request, @RequestParam Integer medicationId) {
		JSONObject json = new JSONObject();
		try {
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json, "hospital");
			Medication medication = medicationService.selectByPrimaryKey(medicationId);
			if(null == medication){
				return Result.failure(json, "药物不存在", "medication_null");
			}
			medicationService.updateDeletedNowById(medicationId, db_hospital.getWholeName());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-AJAX-/updateMedicationDelete-删除药物出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationRepertoryDelete",method=RequestMethod.POST)
	public JSONObject updateMedicationRepertoryDelete(HttpServletRequest request, @RequestParam Integer medicationId) {
		JSONObject json = new JSONObject();
		try {
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json, "hospital");
			String username = db_hospital.getBriefName();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", medicationId);
			map.put("table", Table.medication_repertory);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedication-AJAX-/updateMedicationDelete-删除药物出现异常", json);
		}
		return json;
	}
	
	
}
