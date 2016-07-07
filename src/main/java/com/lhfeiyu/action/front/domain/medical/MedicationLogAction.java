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

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.MedicationLog;
import com.lhfeiyu.po.MedicationRepertory;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.MedicationLogService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class MedicationLogAction {
	@Autowired
	private MedicationLogService medicationLogService;
	@Autowired
	private MedicationRepertoryService medicationRepertoryService;
	@Autowired
	private HospitalService  hospitalService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value = "/medicationLog")
	public ModelAndView medicationLog(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.medicationLog;
		try {
			Hospital db_hospitalhospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospitalhospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			//modelMap = medicationLogService.getMedicationLogData(modelMap,db_hospitalhospital,null);
			modelMap = hospitalService.getHospitalData(modelMap,db_hospitalhospital,null);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalMedicationLogLog-PAGE-/medicationLog-加载诊所药物出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value = "/addOrUpdateMedicationLog")
	public ModelAndView addOrUpdateMedicationLog(ModelMap modelMap,HttpServletRequest request
			,@RequestParam(required=false) Integer medicationLogId) {
		String path = PagePath.addOrUpdateMedicationLog;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			//modelMap = medicationLogService.getMedicationLogData(modelMap,db_hospital,null);
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			if(null != medicationLogId){
				//modelMap = medicationLogService.getMedicationLogData(modelMap,db_hospital,medicationLogId);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", medicationLogId);
				MedicationRepertory medicationLog = medicationRepertoryService.selectByCondition(map);
				if(null != medicationLog){
					modelMap.put("medicationLog", medicationLog);
				}
			}
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalMedicationLog-PAGE-/addOrUpdateMedicationLog-加载诊所药物添加或修改出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateMedicationLog", method = RequestMethod.POST)
	public JSONObject addOrUpdateMedicationLog(@ModelAttribute MedicationLog medicationLog,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json,"hospital");
			if(null == medicationLog.getId()){//添加
				medicationLogService.insertService(json, medicationLog, db_hospital);
			}else{
				medicationLogService.updateService(json, medicationLog, db_hospital);
			}
			json.put("id", medicationLog.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedicationLog-AJAX-/addOrUpdateMedicationLog-新增或修改药物出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationLogList", method=RequestMethod.POST)
	public JSONObject getMedicationLogList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json, "hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("hospitalId", db_hospital.getId());
			List<MedicationLog> medicationLogList = medicationLogService.selectListByCondition(map);
			Integer total = medicationLogService.selectCountByCondition(map);
			Result.gridData(medicationLogList, total, json);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationLog-AJAX-/getMedicationLogList-加载药物列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationLogDelete",method=RequestMethod.POST)
	public JSONObject updateMedicationLogDelete(HttpServletRequest request, @RequestParam Integer medicationLogId) {
		JSONObject json = new JSONObject();
		try {
			Hospital  db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json, "hospital");
			MedicationLog medicationLog = medicationLogService.selectByPrimaryKey(medicationLogId);
			if(null == medicationLog){
				return Result.failure(json, "药物记录不存在", "medicationLog_null");
			}
			medicationLogService.updateDeletedNowById(medicationLogId, db_hospital.getWholeName());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalMedicationLog-AJAX-/updateMedicationLogDelete-删除药物出现异常", json);
		}
		return json;
	}
	
}
