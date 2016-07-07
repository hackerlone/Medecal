package com.lhfeiyu.action.front.domain.diagnose;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.Diagnose;
import com.lhfeiyu.po.DiagnoseApply;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.service.DiagnoseApplyService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.service.DiagnoseService;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class DiagnoseApplyAction {
	@Autowired
	private DiagnoseService diagnoseService;
	@Autowired
	private DiagnoseApplyService diagnoseApplyService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseApplyList", method=RequestMethod.POST)
	public JSONObject getDiagnoseApplyList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的hospital，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("doctorId", session_doctor.getId());
			if(hospital!=null){
				map.put("hospitalId", hospital.getId());
			}
			List<DiagnoseApply> diagnoseApplyList = diagnoseApplyService.selectListByCondition(map);
			Integer total = diagnoseApplyService.selectCountByCondition(map);
			Result.gridData(diagnoseApplyList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseApply-AJAX-/getDiagnoseApplyList-加载授权列表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/doctor/agreeDiagnoseApply",method=RequestMethod.POST)
	public JSONObject doctorAgreeDiagnoseApply(HttpServletRequest request, @RequestParam Integer id) {
		JSONObject json = new JSONObject();
		try {
			Doctor doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == doctor)return Result.userSessionInvalid(json,"doctor");
			DiagnoseApply diagnoseApply = diagnoseApplyService.selectByPrimaryKey(id);
			if(null == diagnoseApply){
				return Result.failure(json, "授权申请不存在", "diagnoseApply_null");
			}
			Integer sessionDoctorId = doctor.getId();
			Integer doctorId = diagnoseApply.getDoctorId();
			if(!Check.integerEqual(sessionDoctorId, doctorId)){
				return Result.failure(json, "您没有权限修改", "authority_error");
			}
			diagnoseApply.setLogicStatus(3);
			diagnoseApplyService.updateService(diagnoseApply);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseApply-AJAX-/doctorAgreeDiagnoseApply-诊所同意授权出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doctor/disAgreeDiagnoseApply",method=RequestMethod.POST)
	public JSONObject doctorDisAgreeDiagnoseApply(HttpServletRequest request, @RequestParam Integer id) {
		JSONObject json = new JSONObject();
		try {
			Doctor doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == doctor)return Result.userSessionInvalid(json,"doctor");
			DiagnoseApply diagnoseApply = diagnoseApplyService.selectByPrimaryKey(id);
			if(null == diagnoseApply){
				return Result.failure(json, "授权申请不存在", "diagnoseApply_null");
			}
			Integer sessionDoctorId = doctor.getId();
			Integer doctorId = diagnoseApply.getDoctorId();
			if(!Check.integerEqual(sessionDoctorId, doctorId)){
				return Result.failure(json, "您没有权限修改", "authority_error");
			}
			diagnoseApply.setLogicStatus(2);
			diagnoseApplyService.updateService(diagnoseApply);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseApply-AJAX-/doctorDisAgreeDiagnoseApply-诊所不同意授权出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/applyToReadDiagnose", method=RequestMethod.POST)
	public JSONObject applyToReadDiagnose(HttpServletRequest request, @RequestParam Integer diagnoseId) {
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer doctorId = session_doctor.getId();
			Diagnose diagnose = diagnoseService.selectByPrimaryKey(diagnoseId);
			Integer ownerDoctorId = diagnose.getDoctorId();
			if(Check.integerEqual(doctorId, ownerDoctorId)){
				return Result.failure(json, "该病历属于您，可以直接查看", "alreadyOwner");
			}
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("applyDoctorId", doctorId);
			map.put("diagnoseId", diagnoseId);
			DiagnoseApply diagnoseApply = diagnoseApplyService.selectByCondition(map);
			if(null == diagnoseApply){
				diagnoseApply = new DiagnoseApply();
				diagnoseApply.setApplyDoctorId(doctorId);
				diagnoseApply.setDiagnoseId(diagnoseId);
				diagnoseApply.setHostpitalId(diagnose.getHospitalId());
				diagnoseApply.setDoctorId(ownerDoctorId);
				diagnoseApply.setLogicStatus(1);
				diagnoseApply.setCreatedAt(new Date());
				diagnoseApplyService.insert(diagnoseApply);
				return Result.success(json);
			}else{
				Integer status = diagnoseApply.getLogicStatus();
				if(null != status && status  == 3){
					return Result.failure(json, "您已经被授权查看病历，无须重复申请", "alreadyAgree");
				}
			}
			diagnoseApply.setLogicStatus(1);
			diagnoseApply.setUpdatedBy(session_doctor.getUsername());
			diagnoseApplyService.updateByPrimaryKey(diagnoseApply);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseApply-AJAX-/applyToReadDiagnose-申请查看病历出现异常", json);
		}
		return json;
	}
	
}
