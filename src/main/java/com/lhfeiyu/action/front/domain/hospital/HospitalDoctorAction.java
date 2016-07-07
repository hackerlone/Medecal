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
import com.lhfeiyu.config.AssetsPath;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Department;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.service.DepartmentService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Result;

@Controller
@RequestMapping(value="/hospital")
public class HospitalDoctorAction {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private ProvinceCityAreaService provinceCityAreaService;
	@Autowired
	private DepartmentService departmentService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@RequestMapping(value = "/hospitalDoctor")
	public ModelAndView hospitalDoctor(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.hospitalDoctor;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalDoctor-PAGE-/hospitalDoctor-加载诊所医生出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value = "/hospitalDoctorAddOrUpdate")
	public ModelAndView hospitalDoctorAddOrUpdate(ModelMap modelMap,HttpServletRequest request
			,@RequestParam(required=false) Integer doctorId) {
		String path = PagePath.addOrUpdateDoctorForHospital;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			if(null != doctorId){
				modelMap = hospitalService.getDoctorData(modelMap,db_hospital,doctorId);
			}else{
				Doctor doctor = new Doctor();
				doctor.setProvince(db_hospital.getProvince());
				doctor.setCity(db_hospital.getCity());
				modelMap.put("doctor", doctor);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			map.clear();
			map.put("parentIdNotNull", 1);
			List<Department> departmentList = departmentService.selectListByCondition(map);
			modelMap.put("departmentList", departmentList);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalDoctor-PAGE-/hospitalDoctorAddOrUpdate-加载诊所医生添加或修改出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDoctorForHospital", method = RequestMethod.POST)
	public JSONObject addOrUpdateDoctorForHospital(@ModelAttribute Doctor doctor,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			if(Check.isNull(doctor.getAvatar())){
				doctor.setAvatar(AssetsPath.defaultDoctorAvatar);
			}
			if(null == doctor.getId()){//添加
				doctorService.insertService(json,doctor,hospital);
			}else{
				doctorService.updateService(json,doctor,hospital);
			}
			json.put("id", doctor.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDoctor-AJAX-/addOrUpdateDoctorForHospital-新增或修改医生出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDoctorDeleteForHospital",method=RequestMethod.POST)
	public JSONObject updateDoctorDeleteForHospital(HttpServletRequest request, @RequestParam Integer doctorId) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			Doctor doctor = doctorService.selectByPrimaryKey(doctorId);
			if(null == doctor){
				return Result.failure(json, "医生不存在", "doctor_null");
			}
			Integer sessionHospitalId = hospital.getId();
			Integer hospitalId = doctor.getHospitalId();
			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
				return Result.failure(json, "您没有权限删除该医生", "authority_error");
			}
			doctorService.updateDeletedNowById(doctorId, hospital.getWholeName());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalDoctor-AJAX-/updateDoctorDeleteForHospital-删除医生出现异常", json);
		}
		return json;
	}
	
	
}
