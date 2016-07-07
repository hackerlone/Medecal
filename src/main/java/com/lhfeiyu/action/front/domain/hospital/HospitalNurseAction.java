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
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Nurse;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.NurseService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class HospitalNurseAction {
	
	@Autowired
	private HospitalService  hospitalService;
	@Autowired
	private NurseService  nurseService;
	@Autowired
	private ProvinceCityAreaService  provinceCityAreaService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/hospitalNurseDetail")
	public ModelAndView  hospitalNurseDetail(ModelMap modelMap,HttpServletRequest request,
			@RequestParam(required=false) Integer id){
		String path = PagePath.hospitalNurseDetail;
		try{
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			if(null != id){
				Nurse nurse = nurseService.selectByPrimaryKey(id);
				modelMap.put("nurse", nurse);
			}else{
				Nurse nurse = new Nurse();
				nurse.setProvince(db_hospital.getProvince());
				nurse.setCity(db_hospital.getCity());
				modelMap.put("nurse", nurse);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-PAGE-/hospitalNurseDetail-加载护士详情页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/hospitalNurseList")
	public ModelAndView  hospitalNurseList(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.hospitalNurseList;
		try{
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
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-PAGE-/hospitalNurseList-加载护士列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateNurseForHospital", method = RequestMethod.POST)
	public JSONObject addOrUpdateNurseForHospital(@ModelAttribute Nurse nurse,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			if(null == nurse.getId()){//添加
				nurseService.insertService(json,nurse,hospital);
			}else{
				nurseService.updateService(json,nurse,hospital);
			}
			json.put("id", nurse.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-AJAX-/addOrUpdateNurseForHospital-新增或修改护士信息出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/gethospitalNurseList", method=RequestMethod.POST)
	public JSONObject gethospitalNurseList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital session_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的hospital，存在即返回
			if(null == session_hospital)return Result.userSessionInvalid(json,"hospital");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			//map.put("adiconBarcode", session_hospital.getThirdId());
			map.put("hospitalId", session_hospital.getId());
			List<Nurse> nurseList = nurseService.selectListByCondition(map);
			Integer total = nurseService.selectCountByCondition(map);
			Result.gridData(nurseList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-AJAX-/gethospitalNurseList-加载护士列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateNurseDeleteForHospital",method=RequestMethod.POST)
	public JSONObject updateNurseDeleteForHospital(HttpServletRequest request, @RequestParam Integer nurseId) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			Nurse nurse = nurseService.selectByPrimaryKey(nurseId);
			if(null == nurse){
				return Result.failure(json, "护士不存在", "nurse_null");
			}
			Integer sessionHospitalId = hospital.getId();
			Integer hospitalId = nurse.getHospitalId();
			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
				return Result.failure(json, "您没有权限删除该护士", "authority_error");
			}
			nurseService.updateDeletedNowById(nurseId, hospital.getWholeName());
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-AJAX-/updateNurseDeleteForHospital-删除护士出现异常", json);
		}
		return json;
	}
	
	
}
