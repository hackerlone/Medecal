package com.lhfeiyu.action.front.domain.hospital;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.DiagnoseApply;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.service.DiagnoseApplyService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class HospitalDiagnoseApplyAction {
	@Autowired
	private DiagnoseApplyService diagnoseApplyService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseApplyList", method=RequestMethod.POST)
	public JSONObject getDiagnoseApplyList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("hospitalId", hospital.getId());
			List<DiagnoseApply> diagnoseApplyList = diagnoseApplyService.selectListByCondition(map);
			Integer total = diagnoseApplyService.selectCountByCondition(map);
			Result.gridData(diagnoseApplyList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseApply-AJAX-/getDiagnoseApplyList-加载授权列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/agreeDiagnoseApply",method=RequestMethod.POST)
	public JSONObject hospitalAgreeDiagnoseApply(HttpServletRequest request, @RequestParam Integer id) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			DiagnoseApply diagnoseApply = diagnoseApplyService.selectByPrimaryKey(id);
			if(null == diagnoseApply){
				return Result.failure(json, "授权申请不存在", "diagnoseApply_null");
			}
			Integer sessionHospitalId = hospital.getId();
			Integer hospitalId = diagnoseApply.getHostpitalId();
			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
				return Result.failure(json, "您没有权限修改", "authority_error");
			}
			diagnoseApply.setLogicStatus(3);//同意
			diagnoseApplyService.updateService(diagnoseApply);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseApply-AJAX-/hospitalAgreeDiagnoseApply-诊所同意授权出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/disAgreeDiagnoseApply",method=RequestMethod.POST)
	public JSONObject hospitalDisAgreeDiagnoseApply(HttpServletRequest request, @RequestParam Integer id) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			DiagnoseApply diagnoseApply = diagnoseApplyService.selectByPrimaryKey(id);
			if(null == diagnoseApply){
				return Result.failure(json, "授权申请不存在", "diagnoseApply_null");
			}
			Integer sessionHospitalId = hospital.getId();
			Integer hospitalId = diagnoseApply.getHostpitalId();
			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
				return Result.failure(json, "您没有权限修改", "authority_error");
			}
			diagnoseApply.setLogicStatus(2);//拒绝
			diagnoseApplyService.updateService(diagnoseApply);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnoseApply-AJAX-/hospitalDisAgreeDiagnoseApply-诊所不同意授权出现异常", json);
		}
		return json;
	}
	
	
}
