package com.lhfeiyu.action.front.domain.hospital;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class HospitalDoctorDiagnoseAction {

	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private DoctorService doctorService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/diagnoseSumList")
	public ModelAndView diagnoseSumList(ModelMap modelMap, HttpServletRequest request){
		String path = PagePath.hospitalDiagnoseSumList;
		try{
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospital");
			modelMap = hospitalService.getHospitalData(modelMap,hospital,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/diagnoseSumList-加载病历夹列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}

	@ResponseBody
	@RequestMapping(value = "/getDiagnoseSumList", method=RequestMethod.POST)
	public JSONObject getDiagnoseSumList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String searchType = request.getParameter("searchType");
			if(Check.isNotNull(searchType)){
				if(searchType.equals("1")){
					map.put("orderBy", "diagnoseCount");
				}else if(searchType.equals("2")){
					map.put("orderBy", "diagnoseTotalPrice");
				}
			}
			map.put("selfOrder", 1);
			map.put("hospitalId", hospital.getId());
			map.put("diagnoseSum", 1);
			List<Doctor> diagnoseList = doctorService.selectDiagnoseSumByCondition(map);
			Integer total = doctorService.selectCountByCondition(map);
			
			Result.gridData(diagnoseList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDiagnose-AJAX-/getDiagnoseSumList-加载诊所病历夹列表出现异常", json);
		}
		return json;
	}

}
