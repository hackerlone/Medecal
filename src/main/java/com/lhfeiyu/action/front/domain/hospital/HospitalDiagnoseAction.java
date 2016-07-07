package com.lhfeiyu.action.front.domain.hospital;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Diagnose;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.service.DiagnoseService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;
import com.lhfeiyu.vo.DiagnoseTag;
import com.lhfeiyu.vo.Prescription;

@Controller
@RequestMapping(value="/hospital")
public class HospitalDiagnoseAction {
	@Autowired
	private DiagnoseService diagnoseService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private MedicationRepertoryService medicationRepertoryService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/diagnoseList")
	public ModelAndView diagnoseList(ModelMap modelMap, HttpServletRequest request){
		String path = PagePath.hospitalDiagnoseList;
		try{
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospital");
			modelMap = hospitalService.getHospitalData(modelMap,hospital,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnoseList-加载病历夹列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/diagnoseRead/{id}")
	public ModelAndView diagnoseRead(ModelMap modelMap, HttpServletRequest request
			,@PathVariable Integer id){
		String path = PagePath.frontHospital;
		try{
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospital");
			//TODO:检查权限：自己的病历，或者已经通过授权的病历
			modelMap = hospitalService.getHospitalData(modelMap,hospital,null);
			Map<String, Object> map = CommonGenerator.getHashMap();
			map.put("id", id);
			Diagnose diagnose = diagnoseService.selectByCondition(map);
			modelMap.put("diagnose", diagnose);
			if(null != diagnose){
				String prescription = diagnose.getPrescription();
				String diagnoseTags = diagnose.getDiagnoseTags();
				if(Check.isNotNull(prescription)){
					List<Prescription> prescriptionList = JSONObject.parseArray(prescription, Prescription.class);
					List<DiagnoseTag> diagnoseTagList = JSONObject.parseArray(diagnoseTags, DiagnoseTag.class);
					modelMap.put("prescriptionList", prescriptionList);
					modelMap.put("diagnoseTagList", diagnoseTagList);
				}
				/*Map<String, Object> map = CommonGenerator.getHashMap();
				map.put("parentCode", "job");
				List<Dict> jobList = dictService.selectListByCondition(map);
				modelMap.put("jobList", jobList);*/
				Integer hospitalId = hospital.getId();
				Integer ownerId = diagnose.getHospitalId();
				if(Check.integerEqual(hospitalId, ownerId)){//病历属于诊所
					path = PagePath.hospitalDiagnoseRead;
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
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseList", method=RequestMethod.POST)
	public JSONObject getDiagnoseList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
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
			map.put("hospitalId", hospital.getId());
			List<Diagnose> diagnoseList = diagnoseService.selectListByCondition(map);
			Integer total = diagnoseService.selectCountByCondition(map);
			Result.gridData(diagnoseList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnose-AJAX-/getDiagnoseList-加载诊所病历夹列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDiagnose", method=RequestMethod.POST)
	public JSONObject deleteDiagnose(HttpServletRequest request, @RequestParam Integer diagnoseId) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			Diagnose diagnose = diagnoseService.selectByPrimaryKey(diagnoseId);
			if(null == diagnose){
				return Result.failure(json, "病历不存在", "diagnose_null");
			}
			Integer sessionHospitalId = hospital.getId();
			Integer hospitalId = diagnose.getHospitalId();
			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
				return Result.failure(json, "您没有权限删除该病历", "authority_error");
			}
			diagnoseService.updateDeletedNowById(diagnoseId, hospital.getWholeName());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/deleteDiagnose-删除病历出现异常", json);
		}
		return json;
	}
	
	
}
