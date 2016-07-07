package com.lhfeiyu.action.back.domain.user;

import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.PatientReport;
import com.lhfeiyu.po.PatientReportDetail;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.PatientReportDetailService;
import com.lhfeiyu.service.PatientReportService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackPatientReportAction {
	
	@Autowired
	private PatientReportService  patientReportService;
	@Autowired
	private PatientReportDetailService  prdService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/patientReport")
	public ModelAndView  patientReport(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backPatientReport;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "患者报告页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-PatientReport-PAGE-/back/patientReport-加载患者报告出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/patientReportDetail/{reportId}")
	public ModelAndView  patientReportDetail(ModelMap modelMap, @PathVariable Integer reportId){
		String path = PagePath.backPatientReportDetail;
		try{
			PatientReport patientReport = patientReportService.selectByPrimaryKey(reportId);
			Map<String, Object> map = CommonGenerator.getHashMap();
			map.put("adiconBarcode", patientReport.getAdiconBarcode());
			List<PatientReportDetail> prdList = prdService.selectListByCondition(map);
			
			modelMap.put("patientReport", patientReport);
			modelMap.put("prdList", prdList);
			Result.success(modelMap, "患者报告页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-PatientReport-PAGE-/back/backPatientReportDetail-加载患者报告出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/prdPrint/{reportId}")
	public ModelAndView  prdPrint(ModelMap modelMap, @PathVariable Integer reportId){
		String path = PagePath.backPrdPrint;
		try{
			PatientReport patientReport = patientReportService.selectByPrimaryKey(reportId);
			Map<String, Object> map = CommonGenerator.getHashMap();
			map.put("adiconBarcode", patientReport.getAdiconBarcode());
			List<PatientReportDetail> prdList = prdService.selectListByCondition(map);
			
			modelMap.put("patientReport", patientReport);
			modelMap.put("prdList", prdList);
			Result.success(modelMap, "患者报告页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-PatientReport-PAGE-/back/prdPrint-加载患者报告出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPatientReportList", method=RequestMethod.POST)
	public JSONObject getPatientReportList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Map<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "patient_name");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "patient_name");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<PatientReport> patientReportList = patientReportService.selectListByCondition(map);
			Integer total = patientReportService.selectCountByCondition(map);
			Result.gridData(patientReportList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PatientReport-AJAX-/back/getPatientReportList-加载患者报告列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPatientReportArray", method=RequestMethod.POST)
	public JSONArray getPatientReportArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			List<PatientReport> patientReportList = patientReportService.selectListByCondition(null);
			for(PatientReport m : patientReportList){
				JSONObject json = new JSONObject();
				json.put("id",m.getAdiconBarcode());
				json.put("name",m.getAdiconBarcode());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PatientReportType-AJAX-/back/getPatientReportArray-加载患者报告类型数组列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdatePatientReport", method=RequestMethod.POST)
	public JSONObject addOrUpdatePatientReport(HttpServletRequest request
			,@ModelAttribute PatientReport patientReport) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			
			//Integer doctorId = patientReport.getDoctorId();
			/*Integer hospitalId = session_doctor.getHospitalId();
			Hospital hos = hospitalMapper.selectByPrimaryKey(hospitalId);
			if(null != hospitalId && null != hos){
				patientReport.setHospitalId(hospitalId);
				patientReport.setHospitalName(hos.getWholeName());
			}*/
			Date date = new Date();
			patientReport.setMainStatus(1);//1未出报告2.已出报告
			patientReport.setServiceFlag("1");//1：不是2：是(服务器获取还是创建)
			patientReport.setAgeType("岁");
			patientReport.setCreatedBy(admin.getUsername());
			patientReport.setCreatedAt(date);
			patientReportService.insert(patientReport);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-doctorPatientReport-AJAX-/addPatientReport-添加用户检测报告列表出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateReportDoctorResult", method=RequestMethod.POST)
	public JSONObject updateReportDoctorResult(HttpServletRequest request, @ModelAttribute PatientReport patientReport) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Integer prId = patientReport.getId();
			if(null == prId)return Result.failure(json, "编号不能为空", "prId_null");
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("id", prId);
			PatientReport pr = new PatientReport();
			pr.setId(prId);
			pr.setDoctorResult(patientReport.getDoctorResult());
			pr.setUpdatedBy(username);
			pr.setUpdatedAt(new Date());
			patientReportService.updateByPrimaryKeySelective(pr);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-doctorPatientReport-AJAX-/back/updateReportDoctorResult-添加用户检测报告列表出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePatientReportDelete",method=RequestMethod.POST)
	public JSONObject updatePatientReportDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.patient_report);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PatientReport-AJAX-/back/updatePatientReportDelete-删除患者报告出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePatientReportRecover",method=RequestMethod.POST)
	public JSONObject updatePatientReportRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.patient_report);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PatientReport-AJAX-/back/updatePatientReportRecover-恢复患者报告出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletePatientReportThorough",method=RequestMethod.POST)
	public JSONObject deletePatientReportThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.patient_report);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PatientReport-AJAX-/back/deletePatientReportThorough-彻底删除患者报告出现异常", json);
		}
		return json;
	}
	
}
