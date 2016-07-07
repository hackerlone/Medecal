package com.lhfeiyu.action.back.domain.medical;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.MedicationLog;
import com.lhfeiyu.po.MedicationRepertory;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.MedicationLogService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackMedicationLogAction {
	
	@Autowired
	private MedicationLogService  medicationLogService;
	@Autowired
	private MedicationRepertoryService  medicationRepertoryService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/medicationLog")
	public ModelAndView  medicationLog(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backMedicationLog;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "药物记录表页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-MedicationLog-PAGE-/back/medicationLog-加载药物记录表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationLogList", method=RequestMethod.POST)
	public JSONObject getMedicationLogList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "in_or_out_time");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "in_or_out_time");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "num");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "num");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("5")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("6")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<MedicationLog> medication_logList = medicationLogService.selectListByCondition(map);
			Integer total = medicationLogService.selectCountByCondition(map);
			Result.gridData(medication_logList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationLog-AJAX-/back/getMedicationLogList-加载药物记录表列表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateMedicationLog", method = RequestMethod.POST)
	public JSONObject addOrUpdateMedicationLog(@ModelAttribute MedicationLog medication_log,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			Integer medicationId = medication_log.getMedicationId();
			Integer hospitalId = medication_log.getHospitalId();
			Integer num = medication_log.getNum();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("medicationId", medicationId);
			map.put("hospitalId", hospitalId);
			MedicationRepertory medicationRepertory = medicationRepertoryService.selectByCondition(map);
			Integer total = Integer.valueOf(medicationRepertory.getRemainNum());
			Integer inOrOut = medication_log.getInOrOut();
			if(inOrOut == 1){//1.出库2.入库
				if(num > total){
					return Result.failure(json, "出库数量不能大于当前库存数量", null);
				}
			}
			if(null == medication_log.getId()){//添加
				medication_log.setCreatedAt(date);
				medication_log.setCreatedBy(username);
				medicationLogService.insert(medication_log);
			}else{//修改
				medication_log.setUpdatedAt(date);
				medication_log.setUpdatedBy(username);
				medicationLogService.updateByPrimaryKeySelective(medication_log);
			}
			if(inOrOut == 1){
				num = total - num;
			}else if(inOrOut == 2){
				num = total + num;
			}
			medicationRepertory.setRemainNum(num);
			medicationRepertoryService.updateByPrimaryKeySelective(medicationRepertory);
			Result.success(json, "添加或修改药物记录表成功", null);
			json.put("id", medication_log.getId());
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationLog-AJAX-/back/addOrUpdateMedicationLog-新增或修改药物记录表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationLogDelete",method=RequestMethod.POST)
	public JSONObject updateMedicationLogDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_log);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationLog-AJAX-/back/updateMedicationLogDelete-删除药物记录表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationLogRecover",method=RequestMethod.POST)
	public JSONObject updateMedicationLogRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_log);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationLog-AJAX-/back/updateMedicationLogRecover-恢复药物记录表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteMedicationLogThorough",method=RequestMethod.POST)
	public JSONObject deleteMedicationLogThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_log);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationLog-AJAX-/back/deleteMedicationLogThorough-彻底删除药物记录表出现异常", json);
		}
		return json;
	}
	
}
