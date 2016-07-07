package com.lhfeiyu.action.front.domain.doctor;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.PatientReport;
import com.lhfeiyu.po.PatientReportDetail;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.PatientReportDetailService;
import com.lhfeiyu.service.PatientReportService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/doctor")
public class DoctorPatientReportAction {
	
	@Autowired
	private DoctorService  doctorService;
	@Autowired
	private PatientReportService  patientReportService;
	@Autowired
	private PatientReportDetailService patientReportDetailService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/doctorUserReportDetail/{id}")
	public ModelAndView  doctorUserReportDetail(ModelMap modelMap,HttpServletRequest request,
			@PathVariable Integer id){
		String path = PagePath.doctorUserReportDetail;
		try{
			Doctor db_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == db_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin, "doctor");
			modelMap = doctorService.getDoctorData(modelMap,db_doctor,null);
			if(null != id){
				PatientReport patientReport = patientReportService.selectByPrimaryKey(id);
				String barCode = patientReport.getAdiconBarcode();
				Map<String, Object> map = CommonGenerator.getHashMap();
				map.put("adiconBarcode", barCode);
				List<PatientReportDetail> prdList = patientReportDetailService.selectListByCondition(map);
				modelMap.put("prdList", prdList);
				modelMap.put("patientReport", patientReport);
			}
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-doctorPatientReport-PAGE-/doctorUserReportDetail-加载用户检测报告详情页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorUserReportList")
	public ModelAndView  doctorUserReportList(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorUserReportList;
		try{
			Doctor db_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == db_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"hospiatl");
			modelMap = doctorService.getDoctorData(modelMap,db_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-doctorPatientReport-PAGE-/doctorUserReportList-加载用户检测报告列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addPatientReport", method=RequestMethod.POST)
	public JSONObject addPatientReport(HttpServletRequest request
			,@ModelAttribute PatientReport patientReport) {
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的doctor，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json,"doctor");
			patientReportService.insertService(json, patientReport, session_doctor);
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
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的doctor，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json,"doctor");
			Integer prId = patientReport.getId();
			if(null == prId)return Result.failure(json, "编号不能为空", "prId_null");
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("id", prId);
			PatientReport dbPatientReport = patientReportService.selectByCondition(map);
			if(!Check.integerEqual(dbPatientReport.getDoctorId(), session_doctor.getId())){
				return Result.failure(json, "该检测档案不是您创建的，您不能进行修改", "auth_lack");
			}
			PatientReport pr = new PatientReport();
			pr.setId(prId);
			pr.setDoctorResult(patientReport.getDoctorResult());
			patientReportService.updateByPrimaryKeySelective(pr);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-doctorPatientReport-AJAX-/addPatientReport-添加用户检测报告列表出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getdoctorUserReportList", method=RequestMethod.POST)
	public JSONObject getdoctorReportList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的doctor，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json,"doctor");
			/*String adiconBarcode = null;
			Map<String,Object> doctormap = new HashMap<String,Object>();
			Integer doctorId = session_doctor.getId();
			doctormap.put("doctorId", doctorId);
			List<Patient> patientList = patientService.selectListByCondition(doctormap);
			if(patientList.size() > 0 && null != patientList){
				for(Patient p:patientList){
					adiconBarcode += ","+"\""+p.getAdiconBarcode()+"\"";
				}
			}*/
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			//map.put("adiconBarcode", adiconBarcode);
			map.put("doctorId", session_doctor.getId());
			map.put("doctor", 1);
			String searchType = request.getParameter("searchType");
			if(Check.isNotNull(searchType)){
				if(searchType.equals("1")){
					map.put("orderBy", "itemName_CN");
				}else if(searchType.equals("2")){
					map.put("orderBy", "report_date");
				}else if(searchType.equals("3")){
					map.put("orderBy", "result");
				}/*else if(searchType.equals("4")){
					map.put("orderBy", "d1.username");
				}*/
			}
			List<PatientReport> patientReportList = patientReportService.selectListByCondition(map);
			Integer total = patientReportService.selectCountByCondition(map);
			Result.gridData(patientReportList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-doctorPatientReport-AJAX-/getdoctorUserReportList-加载用户检测报告列表出现异常", json);
		}
		return Result.success(json);
	}
	
}
