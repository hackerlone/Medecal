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
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class HospitalPatientAction {
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private UserService userService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value = "/hospitalPatient")
	public ModelAndView hospitalPatient(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.hospitalPatient;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalPatient-PAGE-/hospitalPatient-加载诊所患者库页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPatientList", method=RequestMethod.POST)
	public JSONObject getPatientList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json, "hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("belongHospitalId", db_hospital.getId());
			List<User> userList = userService.selectListByCondition(map);
			Integer total = userService.selectCountByCondition(map);
			Result.gridData(userList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/hospital/getPatientList-加载患者列表出现异常", json);
		}
		return json;
	}
	
	
}
