package com.lhfeiyu.action.front.domain.doctor;

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
import com.lhfeiyu.po.DiagnoseApply;
import com.lhfeiyu.po.DiagnoseTemplate;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Medication;
import com.lhfeiyu.po.MedicationRepertory;
import com.lhfeiyu.po.MedicationType;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.DiagnoseApplyService;
import com.lhfeiyu.service.DiagnoseService;
import com.lhfeiyu.service.DiagnoseTemplateService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.service.MedicationService;
import com.lhfeiyu.service.MedicationTypeService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;
import com.lhfeiyu.vo.DiagnoseTag;
import com.lhfeiyu.vo.Prescription;

@Controller
public class DiagnoseAction {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private DiagnoseService diagnoseService;
	@Autowired
	private DiagnoseTemplateService diagnoseTemplateService;
	@Autowired
	private DiagnoseApplyService diagnoseApplyService;
	@Autowired
	private UserService userService;
	@Autowired
	private MedicationTypeService medicationTypeService;
	@Autowired
	private MedicationService medicationService;
	@Autowired
	private MedicationRepertoryService medicationRepertoryService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/diagnoseList")
	public ModelAndView diagnoseList(ModelMap modelMap, HttpServletRequest request){
		String path = PagePath.diagnoseList;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnoseList-加载病历夹列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/diagnoseTemplateList")
	public ModelAndView diagnoseTemplate(ModelMap modelMap, HttpServletRequest request){
		String path = PagePath.diagnoseTemplateList;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnoseTemplateList-加载病历夹模板列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/diagnoseForApplyList")
	public ModelAndView diagnoseForApplyList(ModelMap modelMap, HttpServletRequest request,
			@RequestParam String patientIdcardNum){
		String path = PagePath.diagnoseListForApply;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			modelMap.put("patientIdcardNum", patientIdcardNum);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnoseForApplyList-加载可供申请的病历列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/diagnose")
	public ModelAndView diagnose(ModelMap modelMap, HttpServletRequest request
			,@RequestParam(required=false)Integer userId){
		String path = PagePath.diagnoseRead;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			if(Check.isNotNull(userId)){
				User user = userService.selectByPrimaryKey(userId);
				modelMap.put("user", user);
			}
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnose-加载病历夹列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/diagnoseRead/{id}")
	public ModelAndView diagnoseRead(ModelMap modelMap, HttpServletRequest request
			,@PathVariable Integer id){
		String path = PagePath.frontDoctor;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			//TODO:检查权限：自己的病历，或者已经通过授权的病历
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			Map<String,Object> hashMap = CommonGenerator.getHashMap();
			hashMap.put("id", id);
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
			Integer doctorId = session_doctor.getId();
			Integer ownerId = diagnose.getDoctorId();
			if(Check.integerEqual(doctorId, ownerId)){//病历属于医生
				path = PagePath.diagnoseRead;
				return new ModelAndView(path,modelMap);
			}else{
				Map<String,Object> map = CommonGenerator.getHashMap();
				map.put("diagnoseId", id);
				map.put("applyDoctorId", doctorId);
				map.put("logicStatus", 3);//1反对，2通过
				DiagnoseApply diagnoseApply = diagnoseApplyService.selectByCondition(map);
				if(null != diagnoseApply){//已经通过授权的病历
					path = PagePath.diagnoseRead;
					return new ModelAndView(path,modelMap);
				}
			}
			modelMap.clear();
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnose-加载病历列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/addDiagnoseTemplate")
	public ModelAndView addDiagnoseTemplate(ModelMap modelMap, HttpServletRequest request){
		String path = PagePath.diagnoseTemplate;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("hospitalId", session_doctor.getHospitalId());
			List<MedicationType> mtList = medicationTypeService.selectListByCondition(map);//药物类型
			modelMap.put("mtList", mtList);
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/addDiagnoseTemplate-加载添加列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/diagnoseTemplate/{id}")
	public ModelAndView diagnoseTemplate(ModelMap modelMap, HttpServletRequest request
			,@PathVariable Integer id){
		String path = PagePath.frontDoctor;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin);
			
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			DiagnoseTemplate diagnoseTemplate = diagnoseTemplateService.selectByPrimaryKey(id);
			modelMap.put("dt", diagnoseTemplate);
			String prescription = diagnoseTemplate.getPrescription();
			String diagnoseTags = diagnoseTemplate.getDiagnoseTags();
			if(Check.isNotNull(prescription)){
				List<Prescription> prescriptionList = JSONObject.parseArray(prescription, Prescription.class);
				List<DiagnoseTag> diagnoseTagList = JSONObject.parseArray(diagnoseTags, DiagnoseTag.class);
				modelMap.put("prescriptionList", prescriptionList);
				modelMap.put("diagnoseTagList", diagnoseTagList);
			}
			
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("hospitalId", session_doctor.getHospitalId());
			List<MedicationType> mtList = medicationTypeService.selectListByCondition(map);//药物类型
			modelMap.put("mtList", mtList);
			//检查权限：自己的病历模板，或者医院公共模板
			Integer doctorId = session_doctor.getId();
			Integer ownerId = diagnoseTemplate.getDoctorId();
			if(Check.integerEqual(doctorId, ownerId) || (null == ownerId && Check.integerEqual(session_doctor.getHospitalId(), diagnoseTemplate.getHospitalId()))){//病历属于医生
				path = PagePath.diagnoseTemplate;
				return new ModelAndView(path,modelMap);
			}
			modelMap.clear();
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnose-加载病历模板列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseList", method=RequestMethod.POST)
	public JSONObject getDiagnoseList(HttpServletRequest request, @RequestParam(required=false) String patientIdcardNum) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer doctorId = session_doctor.getId();
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			if(Check.isNull(patientIdcardNum)){
				map.put("doctorIdAndApplied", doctorId);
			}else{
				map.put("doctorIdForApply", doctorId);
			}
			String searchType = request.getParameter("searchType");
			if(Check.isNotNull(searchType)){
				if(searchType.equals("1")){
					map.put("orderBy", "diagnose_time");
				}else if(searchType.equals("2")){
					map.put("orderBy", "base_condition");
				}else if(searchType.equals("3")){
					map.put("orderBy", "patient_name");
				}else if(searchType.equals("4")){
					map.put("orderBy", "d1.username");
				}
			}
			List<Diagnose> diagnoseList = diagnoseService.selectListByCondition(map);
			Integer total = diagnoseService.selectCountByCondition(map);
			Result.gridData(diagnoseList, total, json);
			json.put("doctorId", doctorId);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/getDiagnoseList-加载病历列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseTemplateList", method=RequestMethod.POST)
	public JSONObject getDiagnoseTemplateList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer doctorId = session_doctor.getId();
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("doctorAndHospital", 1);
			map.put("commonDoctorId", doctorId);
			map.put("commonHospitalId", session_doctor.getHospitalId());
			List<DiagnoseTemplate> diagnoseTemplateList = diagnoseTemplateService.selectListByCondition(map);
			Integer total = diagnoseTemplateService.selectCountByCondition(map);
			Result.gridData(diagnoseTemplateList, total, json);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/getDiagnoseTemplateList-加载病历模板列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDiagnose", method=RequestMethod.POST)
	public JSONObject deleteDiagnose(HttpServletRequest request, @RequestParam Integer diagnoseId) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Diagnose diagnose = diagnoseService.selectByPrimaryKey(diagnoseId);
			if(null == diagnose){
				return Result.failure(json, "病历不存在", "diagnose_null");
			}
			Integer sessionDoctorId = session_doctor.getId();
			Integer doctorId = diagnose.getDoctorId();
			if(!Check.integerEqual(sessionDoctorId, doctorId)){
				return Result.failure(json, "您没有权限删除该病历", "authority_error");
			}
			diagnoseService.updateDeletedNowById(diagnoseId, session_doctor.getUsername());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/deleteDiagnose-删除病历出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDiagnoseTemplate", method=RequestMethod.POST)
	public JSONObject deleteDiagnoseTemplate(HttpServletRequest request, @RequestParam Integer diagnoseTemplateId) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			DiagnoseTemplate diagnoseTemplate = diagnoseTemplateService.selectByPrimaryKey(diagnoseTemplateId);
			if(null == diagnoseTemplate){
				return Result.failure(json, "病历模板不存在", "diagnoseTemplate_null");
			}
			Integer sessionDoctorId = session_doctor.getId();
			Integer doctorId = diagnoseTemplate.getDoctorId();
			if(!Check.integerEqual(sessionDoctorId, doctorId)){
				return Result.failure(json, "您没有权限删除该病历模板", "authority_error");
			}
			diagnoseTemplateService.updateDeletedNowById(diagnoseTemplateId, session_doctor.getUsername());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/deleteDiagnoseTemplate-删除病历模板出现异常", json);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/updateDiagnoseTemplate", method=RequestMethod.POST)
	public JSONObject updateDiagnoseTemplate(HttpServletRequest request, @RequestParam Integer diagnoseTemplateId
			,@ModelAttribute DiagnoseTemplate dt) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			DiagnoseTemplate diagnoseTemplate = diagnoseTemplateService.selectByPrimaryKey(diagnoseTemplateId);
			if(null == diagnoseTemplate){
				return Result.failure(json, "病历模板不存在", "diagnoseTemplate_null");
			}
			Integer sessionDoctorId = session_doctor.getId();
			Integer doctorId = diagnoseTemplate.getDoctorId();
			if(!Check.integerEqual(sessionDoctorId, doctorId) && (null == doctorId && !Check.integerEqual(session_doctor.getHospitalId(), diagnoseTemplate.getHospitalId()))){
				return Result.failure(json, "您没有权限修改该病历模板", "authority_error");
			}
			dt.setId(diagnoseTemplateId);
			diagnoseTemplateService.updateByPrimaryKeySelective(dt);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/updateDiagnoseTemplate-更新病历模板出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addDiagnose", method=RequestMethod.POST)
	public JSONObject addDiagnose(HttpServletRequest request, @ModelAttribute Diagnose diagnose) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			diagnoseService.addDiagnose(json, session_doctor, diagnose);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/addDiagnose-新增病历出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addDiagnoseTemplate", method=RequestMethod.POST)
	public JSONObject addDiagnoseTemplate(HttpServletRequest request, @ModelAttribute DiagnoseTemplate diagnoseTemplate) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			diagnoseTemplateService.addDiagnoseTemplate(json, session_doctor, diagnoseTemplate);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/addDiagnoseTemplate-新增病历模板出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationList", method=RequestMethod.POST)
	public JSONObject getMedicationList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("hospitalId", session_doctor.getHospitalId());
			List<Medication> medicationList = medicationService.selectListByCondition(map);
			Integer total = medicationService.selectCountByCondition(map);
			Result.gridData(medicationList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Medication-AJAX-/getMedicationList-加载药物列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationRepertoryAry", method=RequestMethod.POST)
	public JSONObject getMedicationRepertoryAry(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital && null == session_doctor)return Result.userSessionInvalid(json,"doctor");
			//if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);
			if(null != hospital){
				map.put("hospitalId", hospital.getId());
			}else{
				map.put("hospitalId", session_doctor.getHospitalId());
			}
			List<MedicationRepertory> mrList = medicationRepertoryService.selectListByCondition(map);
			Integer total = medicationRepertoryService.selectCountByCondition(map);
			Result.gridData(mrList, total, json);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Medication-AJAX-/getMedicationRepertoryAry-加载药物列表出现异常", json);
		}
		return json;
	}
	
}
