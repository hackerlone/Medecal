package com.lhfeiyu.action.front.domain.bespeak;

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
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.po.Cancer;
import com.lhfeiyu.po.Department;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.BespeakService;
import com.lhfeiyu.service.CancerService;
import com.lhfeiyu.service.DepartmentService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class BespeakAction {
	@Autowired
	private BespeakService bespeakService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private CancerService cancerService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProvinceCityAreaService provinceCityAreaService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/userBespeak/{typeId}/{doctorId}/{hospitalId}")
	public ModelAndView userBespeak(ModelMap modelMap,HttpServletRequest request
			,@PathVariable Integer typeId
			,@PathVariable Integer doctorId
			,@PathVariable Integer hospitalId){
		String path = PagePath.userBespeak;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			String idcardNum = user.getIdcardNum();
			modelMap = indexService.getIntroductionAndvision(modelMap);
			List<Cancer> cancerTypes = cancerService.selectListByCondition(null);
			modelMap.put("cancerTypes", cancerTypes);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("idCardNum", idcardNum);
			//User user = userService.selectByCondition(map);
			modelMap.put("user", user);
			modelMap.put("typeId", typeId);
			modelMap.put("doctorId", doctorId);
			modelMap.put("hospitalId", hospitalId);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/userBespeak-加载用户挂号预约出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/bloodTest/{hospitalId}")
	public ModelAndView bloodTest(ModelMap modelMap,HttpServletRequest request
			,@PathVariable Integer hospitalId){
		String path = PagePath.userBespeak;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			String idcardNum = user.getIdcardNum();
			modelMap = indexService.getIntroductionAndvision(modelMap);
			List<Cancer> cancerTypes = cancerService.selectListByCondition(null);
			modelMap.put("cancerTypes", cancerTypes);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("idCardNum", idcardNum);
			//Patient patient  = patientService.selectByCondition(map);
			modelMap.put("user", user);
			modelMap.put("typeId", 3);
			modelMap.put("hospitalId", hospitalId);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/bloodTest-加载血液检测出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/bespeakRecord")
	public ModelAndView bespeakRecord(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.bespeakRecord;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = indexService.getIntroductionAndvision(modelMap);
			modelMap.put("userId", user.getId());
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/bespeakRecord-加载用户预约记录出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/bespeakRecordDetail/{bespeakId}")
	public ModelAndView bespeakRecordDetail(ModelMap modelMap,HttpServletRequest request,
			@PathVariable Integer bespeakId){
		String path = PagePath.bespeakRecordDetail;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getBespeakData(modelMap,user,bespeakId);
			List<Cancer> cancerTypes = cancerService.selectListByCondition(null);
			modelMap.put("cancerTypes", cancerTypes);
			modelMap.put("user", user);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/bespeakRecordDetail-加载用户预约详情记录出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/bespeakChooseProvinceCity")
	public ModelAndView bespeakChooseProvinceCity(ModelMap modelMap,@PathVariable Integer typeId){
		String path = PagePath.bespeakChooseProvinceCity;
		try{
			List<ProvinceCityArea> provinceCityArea = provinceCityAreaService.selectListByCondition(null);
			modelMap.put("provinceCityArea", provinceCityArea);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/bespeakChooseProvinceCity-加载挂号陪诊省市选择页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/bespeakChoose/{typeId}")
	public ModelAndView bespeakChoose(ModelMap modelMap,@PathVariable Integer typeId,HttpServletRequest request){
		String path = PagePath.bespeakChoose;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null != user){
				modelMap.put("user", user);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			List<Department> deptList = departmentService.getAllDepartmentByLevel();
			List<Hospital> hospitalList = hospitalService.selectListByCondition(null);
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			map.put("orderBy", "id");
			map.put("ascOrDesc", "asc");
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			modelMap.put("typeId", typeId);
			modelMap.put("deptList", deptList);
			modelMap.put("hospitalList", hospitalList);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-User-PAGE-/bespeakChoose-加载用户挂号预约选择页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBespeakList", method=RequestMethod.POST)
	public JSONObject getBespeakList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			if(hospital!=null){
				map.put("hospital_id", hospital.getId());
			}
			List<Bespeak> bespeakList = bespeakService.selectListByCondition(map);
			Integer total = bespeakService.selectCountByCondition(map);
			Result.gridData(bespeakList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/getBespeakList-加载预约列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateBespeak", method = RequestMethod.POST)
	public JSONObject addOrUpdateBespeak(@ModelAttribute Bespeak bespeak,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			if(null == bespeak.getId()){//添加
				bespeakService.insertService(json,bespeak,user);
			}else{//修改
				bespeakService.updateService(json,bespeak,user);
			}
			json.put("id", bespeak.getId());
			Result.success(json, "添加或修改预约成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/addOrUpdateBespeak-新增或修改预约出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateBespeakDelete",method=RequestMethod.POST)
	public JSONObject updateBespeakDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			bespeakService.updateBespeakDelete(json,ids,user);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/updateBespeakDelete-删除预约出现异常", json);
		}
		return json;
	}
	
}
