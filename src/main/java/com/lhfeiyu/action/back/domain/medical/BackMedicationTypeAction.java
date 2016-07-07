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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.MedicationType;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.MedicationTypeService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackMedicationTypeAction {
	
	@Autowired
	private MedicationTypeService  medicationTypeService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/medicationType")
	public ModelAndView  medicationType(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backMedicationType;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "药物类型页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-MedicationType-PAGE-/back/medicationType-加载药物类型出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationTypeList", method=RequestMethod.POST)
	public JSONObject getMedicationTypeList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "name");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "name");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<MedicationType> medication_typeList = medicationTypeService.selectListByCondition(map);
			Integer total = medicationTypeService.selectCountByCondition(map);
			Result.gridData(medication_typeList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationType-AJAX-/back/getMedicationTypeList-加载药物类型列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMedicationTypeArray", method=RequestMethod.POST)
	public JSONArray getMedicationTypeArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<MedicationType> medication_typeList = medicationTypeService.selectListByCondition(map);
			for(MedicationType mt:medication_typeList){
				JSONObject json = new JSONObject();
				json.put("id",mt.getId());
				json.put("name",mt.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationType-AJAX-/back/getMedicationTypeArray-加载药物类型数组列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateMedicationType", method = RequestMethod.POST)
	public JSONObject addOrUpdateMedicationType(@ModelAttribute MedicationType medication_type,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == medication_type.getId()){//添加
				medication_type.setCreatedAt(date);
				medication_type.setCreatedBy(username);
				medicationTypeService.insert(medication_type);
			}else{//修改
				medication_type.setUpdatedAt(date);
				medication_type.setUpdatedBy(username);
				medicationTypeService.updateByPrimaryKeySelective(medication_type);
			}
			json.put("id", medication_type.getId());
			Result.success(json, "添加或修改药物类型成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationType-AJAX-/back/addOrUpdateMedicationType-新增或修改药物类型出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationTypeDelete",method=RequestMethod.POST)
	public JSONObject updateMedicationTypeDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_type);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationType-AJAX-/back/updateMedicationTypeDelete-删除药物类型出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMedicationTypeRecover",method=RequestMethod.POST)
	public JSONObject updateMedicationTypeRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_type);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationType-AJAX-/back/updateMedicationTypeRecover-恢复药物类型出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteMedicationTypeThorough",method=RequestMethod.POST)
	public JSONObject deleteMedicationTypeThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.medication_type);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-MedicationType-AJAX-/back/deleteMedicationTypeThorough-彻底删除药物类型出现异常", json);
		}
		return json;
	}
	
}
