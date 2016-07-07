package com.lhfeiyu.action.front.domain.consult;

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
import com.lhfeiyu.po.Consult;
import com.lhfeiyu.po.Department;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.ConsultService;
import com.lhfeiyu.service.DepartmentService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class ConsultAction {
	@Autowired
	private ConsultService consultService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private ProvinceCityAreaService provinceCityAreaService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/consult")
	public ModelAndView  consult(ModelMap modelMap,HttpServletRequest request
			,@RequestParam(required=false) Integer doctorId
			,@RequestParam(required=false) Integer hospitalId){
		String path = PagePath.consult;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			if(Check.isNotNull(doctorId)){
				modelMap.put("doctorId", doctorId);
			}
			if(Check.isNotNull(hospitalId)){
				modelMap.put("hospitalId", hospitalId);
			}
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Consult-PAGE-/consult-加载用户咨询页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@RequestMapping(value="/consultDoctorList")
	public ModelAndView  consultDoctorList(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.consultDoctorList;
		try{
			//User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			//if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null != user){
				modelMap.put("user", user);
			}
			List<Department> deptList = departmentService.getAllDepartmentByLevel();
			List<Hospital> hospitalList = hospitalService.selectListByCondition(null);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("deptList", deptList);
			modelMap.put("hospitalList", hospitalList);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Consult-PAGE-/consultDoctorList-加载咨询医生列表页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getConsultList", method=RequestMethod.POST)
	public JSONObject getConsultList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<Consult> consultList = consultService.selectListByCondition(map);
			Integer total = consultService.selectCountByCondition(map);
			Result.gridData(consultList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Consult-AJAX-/getConsultList-加载咨询列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateConsult", method = RequestMethod.POST)
	public JSONObject addOrUpdateConsult(@ModelAttribute Consult consult,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			if(null == consult.getId()){//添加
				consultService.insertService(json,consult,user);
			}else{//修改
				consultService.updateService(json,consult,user);
			}
			json.put("id", consult.getId());
			Result.success(json, "添加或修改咨询成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Consult-AJAX-/addOrUpdateConsult-新增或修改咨询出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doctorReplyConsult", method = RequestMethod.POST)
	public JSONObject doctorReplyConsult(@ModelAttribute Consult consult,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer consultId = consult.getId();
			if(null != consultId){
				Consult db_consult = consultService.selectByPrimaryKey(consultId);
				if(null == db_consult){
					return Result.failure(json, "咨询信息不存在", "consult_null");
				}
				Integer sessionDoctorId = session_doctor.getId();
				Integer doctorId = db_consult.getDoctorId();
				if(!Check.integerEqual(sessionDoctorId, doctorId)){
					return Result.failure(json, "您没有权限回复该咨询信息", "authority_error");
				}
				Map<String,Object> map = CommonGenerator.getHashMap();
				map.put("expression1","main_status = 2");
				map.put("expression2","reply_content = '"+ consult.getReplyContent()+"'");
				map.put("id", consult.getId());
				consultService.updateFieldById(map);
				json.put("id", consultId);
				Result.success(json, "回复成功", null);
			}else{
				Result.failure(json, "传入数据有误,请检查数据", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Comment-AJAX-/doctorReplyConsult-医生回复咨询出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateConsultDelete",method=RequestMethod.POST)
	public JSONObject updateConsultDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			consultService.updateConsultDelete(json,ids,user);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Consult-AJAX-/updateConsultDelete-删除咨询出现异常", json);
		}
		return json;
	}
	
}
