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
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.po.Volunteer;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.service.VolunteerService;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class HospitalVolunteerAction {
	
	@Autowired
	private HospitalService  hospitalService;
	@Autowired
	private VolunteerService  volunteerService;
	@Autowired
	private ProvinceCityAreaService  provinceCityAreaService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@RequestMapping(value="/hospitalVolunteerList")
	public ModelAndView hospitalVolunteerList(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.hospitalVolunteerList;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			map.put("higherIdISNULL", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-PAGE-/hospitalNurseList-加载志愿者列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/gethospitalVolunteerList", method=RequestMethod.POST)
	public JSONObject gethospitalVolunteerList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			//map.put("adiconBarcode", session_hospital.getThirdId());
			List<Volunteer> nurseList = volunteerService.selectListByCondition(map);
			Integer total = volunteerService.selectCountByCondition(map);
			Result.gridData(nurseList, total, json);
			System.out.println(json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-AJAX-/gethospitalNurseList-加载志愿者列表出现异常", json);
		}
		return json;
	}

	@RequestMapping(value="/hospitalVolunteerDetail")
	public ModelAndView  hospitalVolunteerDetail(ModelMap modelMap,HttpServletRequest request,
			@RequestParam(required=false) Integer id){
		String path = PagePath.hospitalVolunteerDetail;
		try{
			if(null != id){
				Volunteer nurse = volunteerService.selectByPrimaryKey(id);
				modelMap.put("nurse", nurse);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			map.put("higherIdISNULL", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-PAGE-/hospitalNurseDetail-加载护士详情页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateVolunteerForHospital", method = RequestMethod.POST)
	public JSONObject addOrUpdateVolunteerForHospital(@ModelAttribute Volunteer nurse,HttpServletRequest request){
		JSONObject json = new JSONObject();
		
		try {
			if(null == nurse.getId()){//添加
				System.out.println("00000:"+nurse.getUsername()+"2222:"+request.getParameter("username"));
				volunteerService.insertService(nurse);
			}else{
				System.out.println("1111:"+nurse.getUsername()+"3333:"+request.getParameter("username"));
				volunteerService.updateService(nurse);
			}
			json.put("id", nurse.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-AJAX-/addOrUpdateNurseForHospital-新增或修改志愿者信息出现异常", json);
		}
		return json;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/updateVolunteerDeleteForHospital",method=RequestMethod.POST)
	public JSONObject updateNurseDeleteForHospital(HttpServletRequest request, 
			@RequestParam Integer volunteerId) {
		JSONObject json = new JSONObject();
		try {
			Volunteer volunteer = volunteerService.selectByPrimaryKey(volunteerId);
			if(null == volunteer){
				return Result.failure(json, "志愿者不存在", "volunteer_null");
			}
//			if(!Check.integerEqual(sessionHospitalId, hospitalId)){
//				return Result.failure(json, "您没有权限删除该护士", "authority_error");
//			}
			volunteerService.updateDeletedNowById(volunteerId);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalNurse-AJAX-/updateNurseDeleteForHospital-删除志愿者出现异常", json);
		}
		return json;
	}
	
}
