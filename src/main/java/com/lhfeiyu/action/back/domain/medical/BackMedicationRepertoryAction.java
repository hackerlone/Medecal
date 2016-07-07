package com.lhfeiyu.action.back.domain.medical;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.MedicationRepertory;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.MedicationRepertoryService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackMedicationRepertoryAction {
	
	@Autowired
	private MedicationRepertoryService  medicationRepertoryService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/medicationRepertory")
	public ModelAndView  medicationRepertory(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backMedicationRepertory;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "药物页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-MedicationRepertory-PAGE-/back/medicationRepertory-加载药物出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationRepertoryList", method=RequestMethod.POST)
	public JSONObject getMedicationRepertoryList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "remain_num");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "remain_num");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "warning_num");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "warning_num");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("5")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("6")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<MedicationRepertory> medicationRepertoryList = medicationRepertoryService.selectListByCondition(map);
			Integer total = medicationRepertoryService.selectCountByCondition(map);
			Result.gridData(medicationRepertoryList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationRepertory-AJAX-/back/getMedicationRepertoryList-加载药物列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationRepertoryArray", method=RequestMethod.POST)
	public JSONArray getMedicationRepertoryArray(HttpServletRequest request,@RequestParam Integer hospitalId) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			map.put("hospitalId", hospitalId);
			List<MedicationRepertory> medicationRepertoryList = medicationRepertoryService.selectListByCondition(map);
			for(MedicationRepertory mr:medicationRepertoryList){
				JSONObject json = new JSONObject();
				json.put("id",mr.getId());
				json.put("name",mr.getMedicationName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationRepertory-AJAX-/back/getMedicationRepertoryArray-加载药物类型数组列表出现异常", array);
		}
		return array;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateMedicationRepertory", method = RequestMethod.POST)
	public JSONObject addOrUpdateMedicationRepertory(@ModelAttribute MedicationRepertory medicationRepertory,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			if(null == medicationRepertory.getId()){//添加
				medicationRepertoryService.insertData(json,medicationRepertory,admin);
			}else{//修改
				medicationRepertoryService.updateData(json,medicationRepertory,admin);
			}
			json.put("id", medicationRepertory.getId());
			//Result.success(json, "添加或修改药物成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationRepertory-AJAX-/back/addOrUpdateMedicationRepertory-新增或修改药物出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationRepertoryDelete",method=RequestMethod.POST)
	public JSONObject updateMedicationRepertoryDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_repertory);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationRepertory-AJAX-/back/updateMedicationRepertoryDelete-删除药物出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationRepertoryRecover",method=RequestMethod.POST)
	public JSONObject updateMedicationRepertoryRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_repertory);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationRepertory-AJAX-/back/updateMedicationRepertoryRecover-恢复药物出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteMedicationRepertoryThorough",method=RequestMethod.POST)
	public JSONObject deleteMedicationRepertoryThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_repertory);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationRepertory-AJAX-/back/deleteMedicationRepertoryThorough-彻底删除药物出现异常", json);
		}
		return json;
	}
	
}
