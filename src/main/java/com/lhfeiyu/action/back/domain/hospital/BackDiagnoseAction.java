package com.lhfeiyu.action.back.domain.hospital;

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

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Diagnose;
import com.lhfeiyu.po.DiagnoseApply;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DiagnoseService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;
import com.lhfeiyu.vo.DiagnoseTag;
import com.lhfeiyu.vo.Prescription;

@Controller
@RequestMapping(value="/back")
public class BackDiagnoseAction {
	
	@Autowired
	private DiagnoseService  diagnoseService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/diagnose")
	public ModelAndView  diagnose(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backDiagnose;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "病历页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/back/diagnose-加载病历出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/diagnosePrint/{id}")
	public ModelAndView diagnosePrint(ModelMap modelMap, HttpServletRequest request
			,@PathVariable Integer id){
		String path = PagePath.backDiagnosePrint;
		try{
			Map<String,Object> hashMap = CommonGenerator.getHashMap();
			hashMap.put("id", id);
			Diagnose diagnose = diagnoseService.selectByCondition(hashMap);
			modelMap.put("diagnose", diagnose);
			String prescription = diagnose.getPrescription();
			String diagnoseTags = diagnose.getDiagnoseTags();
			if(Check.isNotNull(prescription)){
				List<Prescription> prescriptionList = JSONObject.parseArray(prescription, Prescription.class);
				List<DiagnoseTag> diagnoseTagList = JSONObject.parseArray(diagnoseTags, DiagnoseTag.class);
				modelMap.put("prescriptionList", prescriptionList);
				modelMap.put("diagnoseTagList", diagnoseTagList);
			}
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Diagnose-PAGE-/backDiagnosePrint-加载病历列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseList", method=RequestMethod.POST)
	public JSONObject getDiagnoseList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<Diagnose> diagnoseList = diagnoseService.selectListByCondition(map);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "patient_name");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "patient_name");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "doctor_id");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "doctor_id");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("5")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("6")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			Integer total = diagnoseService.selectCountByCondition(map);
			Result.gridData(diagnoseList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/back/getDiagnoseList-加载病历列表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDiagnose", method = RequestMethod.POST)
	public JSONObject addOrUpdateDiagnose(@ModelAttribute Diagnose diagnose,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == diagnose.getId()){//添加
				diagnose.setCreatedAt(date);
				diagnose.setCreatedBy(username);
				diagnoseService.insert(diagnose);
			}else{//修改
				diagnose.setUpdatedAt(date);
				diagnose.setUpdatedBy(username);
				diagnoseService.updateByPrimaryKeySelective(diagnose);
			}
			json.put("id", diagnose.getId());
			Result.success(json, "添加或修改病历成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/back/addOrUpdateDiagnose-新增或修改病历出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDiagnoseDelete",method=RequestMethod.POST)
	public JSONObject updateDiagnoseDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.diagnose);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/back/updateDiagnoseDelete-删除病历出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDiagnoseRecover",method=RequestMethod.POST)
	public JSONObject updateDiagnoseRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.diagnose);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/back/updateDiagnoseRecover-恢复病历出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDiagnoseThorough",method=RequestMethod.POST)
	public JSONObject deleteDiagnoseThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.diagnose);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Diagnose-AJAX-/back/deleteDiagnoseThorough-彻底删除病历出现异常", json);
		}
		return json;
	}
	
}
