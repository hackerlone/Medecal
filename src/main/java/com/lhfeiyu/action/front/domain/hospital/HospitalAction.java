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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Article;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.ArticleService;
import com.lhfeiyu.service.BespeakService;
import com.lhfeiyu.service.ConsultService;
import com.lhfeiyu.service.DiagnoseApplyService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.MessageService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/hospital")
public class HospitalAction {
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private ProvinceCityAreaService provinceCityAreaService;
	
	@Autowired
	private BespeakService bespeakService;
	@Autowired
	private ConsultService consultService;
	@Autowired
	private DiagnoseApplyService diagnoseApplyService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ArticleService articleService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/hospital/{id}")
	public ModelAndView  hospital(ModelMap modelMap,@PathVariable Integer id){
		String path = PagePath.frontHospitalHome;
		try{
			modelMap = hospitalService.getHospitalData(modelMap,null,id);
			modelMap.put("hospitalId", id);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/hospital-加载诊所展示页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/hospitalHome")
	public ModelAndView  hospitalHome(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.frontHospital;
		try{
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospital");
			modelMap = hospitalService.getHospitalData(modelMap,hospital,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/hospitalHome-加载诊所出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/hospitalDiagnoseApply")
	public ModelAndView  hospitalDiagnoseApply(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.hospitalDiagnoseApply;
		try{
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospital");
			modelMap = hospitalService.getHospitalData(modelMap,hospital,null);
			modelMap.put("hospitalId", hospital.getId());
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/hospitalDiagnoseApply-加载诊所授权出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/hospitalArticle")
	public ModelAndView  hospitalArticle(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.hospitalArticle;
		try{
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospital");
			//modelMap = hospitalService.getHospitalData(modelMap,hospital,null);
			modelMap.put("hospitalId", hospital.getId());
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/hospitalArticle-加载诊所医生文章出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	@RequestMapping(value="/hospitalBloodTest")
	public ModelAndView  hospitalBloodTest(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.hospitalBloodTest;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null != user){
				modelMap.put("user", user);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			map.put("orderBy", "id");
			map.put("ascOrDesc", "asc");
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/hospitalBloodTest-加载血液检测诊所出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/hospitalArticleDetails/{articleId}")
	public ModelAndView  hospitalArticleDetails(ModelMap modelMap,HttpServletRequest request,@PathVariable Integer articleId){
		String path = PagePath.hospitalArticleDetails;
		try{
			Article article = articleService.selectByPrimaryKey(articleId);//当前文章
			modelMap.put("article", article);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/hospitalArticleDetails-加载诊所医生文章详情出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value = "/updateHospital")
	public ModelAndView updateHospital(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.updateHospital;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
			modelMap.put("hospitalId", db_hospital.getId());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/updateHospital-加载修改诊所信息出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/hospitalNewMsg", method = RequestMethod.POST)
	public JSONObject hospitalNewMsg(HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital session_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的hospital，存在即返回
			if(null == session_hospital)return Result.userSessionInvalid(json, "hospital");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hospital_id", session_hospital.getId());//当前医生的咨询
			map.put("main_status", 1);//1 未回复的咨询
			int consultCount = consultService.selectCountByCondition(map);//查询当前医生未回复咨询总条数
			map.clear();
			map.put("hospital_id", session_hospital.getId());//当前医生的预约
			map.put("main_status", 1);//1 未回复的预约
			int bespeakCount = bespeakService.selectCountByCondition(map);//查询当前医生未回复预约总条数
			map.clear();
			map.put("hospital_id", session_hospital.getId());//当前医生的病历授权
			map.put("logic_status", 1);//1 未回复的病历授权
			int diagnoseApplyCount = diagnoseApplyService.selectCountByCondition(map);//查询当前医生未授权病历总条数

			map.clear();
			map.put("hospital_id", session_hospital.getId());//当前医生的留言
			map.put("main_status", 1);//1 未回复的留言
			int messageCount = messageService.selectCountByCondition(map);//查询当前医生留言历总条数
			json.put("consultCount", consultCount);
			json.put("bespeakCount", bespeakCount);
			json.put("diagnoseApplyCount", diagnoseApplyCount);
			json.put("messageCount", messageCount);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/addOrUpdateDoctor-新增或修改医生出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getHospitalList", method=RequestMethod.POST)
	public JSONObject getHospitalList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String bloodTest = request.getParameter("bloodTest");
			if(null != bloodTest){
				if(bloodTest.equals("bloodTest")){
					map.put("bloodTest", 1);
				}
			}
			List<Hospital> hospitalList = hospitalService.selectListByCondition(map);
			Integer total = hospitalService.selectCountByCondition(map);
			Result.gridData(hospitalList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/getHospitalList-加载诊所列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getHospitalArticleList", method=RequestMethod.POST)
	public JSONObject getHospitalArticleList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			hospitalService.getHospitalDoctorArticle(json,map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/getHospitalArticleList-加载诊所医生文章列表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDoctor", method = RequestMethod.POST)
	public JSONObject addOrUpdateDoctor(@ModelAttribute Doctor doctor,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			if(null == doctor.getId()){//添加
				doctorService.insertService(json,doctor,hospital);
			}
			json.put("id", doctor.getId());
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/addOrUpdateDoctor-新增或修改医生出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDoctorDelete",method=RequestMethod.POST)
	public JSONObject updateDoctorDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			doctorService.updateDoctorDelete(json,ids,hospital);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/updateDoctorDelete-删除医生出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateHospital", method = RequestMethod.POST)
	public JSONObject updateHospital(@ModelAttribute Hospital hospital,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json,"hospital");
			hospitalService.updateService(json,hospital,db_hospital);
			json.put("id", hospital.getId());
			Result.success(json, "修改诊所成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/updateHospital-修改诊所出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateHospitalDelete",method=RequestMethod.POST)
	public JSONObject updateHospitalDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == hospital)return Result.userSessionInvalid(json,"hospital");
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			hospitalService.updateHospitalDelete(json,ids,hospital);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/updateHospitalDelete-删除诊所出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateHospitalPassword",method=RequestMethod.POST)
	public JSONObject updateUserPassword(HttpServletRequest request,
			@RequestParam String password) {
		JSONObject json = new JSONObject();
		try {
			Hospital session_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == session_hospital)return Result.userSessionInvalid(json, "hospital");
			Integer sessionHospitalId = session_hospital.getId();
			Hospital db_hospital = hospitalService.selectByPrimaryKey(sessionHospitalId);
			if(null == db_hospital){
				return Result.failure(json, "该用户不存在", "user_null");
			}
			Integer db_hospitalId = db_hospital.getId();
			if(!Check.integerEqual(sessionHospitalId, db_hospitalId)){
				return Result.failure(json, "您没有权限修改该用户信息", "authority_error");
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", sessionHospitalId);
			map.put("expression1","password = '"+Md5Util.encrypt(password)+"'");
			hospitalService.updateFieldById(map);
			request.getSession().invalidate();
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/updateHospitalPassword-修改诊所密码出现异常", json);
		}
		return json;
	}
	
	
}
