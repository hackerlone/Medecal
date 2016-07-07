package com.lhfeiyu.action.back.domain.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.DataHospitalVisit;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DataHospitalVisitService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackDataHospitalVisitAction {
	
	@Autowired
	private DataHospitalVisitService  dataHospitalVisitService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/getDataHospitalVisitList", method=RequestMethod.POST)
	public JSONObject getDataHospitalVisitList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			
			if(admin.getRoleId()  == 3){//数据录入员 - 只查看自己的数据
				map.put("adminId", admin.getId());
			}
			
			List<DataHospitalVisit> hospitalList = dataHospitalVisitService.selectListByCondition(map);
			Integer total = dataHospitalVisitService.selectCountByCondition(map);
			Result.gridData(hospitalList, total, json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalVisit-AJAX-/back/getDataHospitalVisitList-加载诊所数据走访列表出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDataHospitalVisit", method = RequestMethod.POST)
	public JSONObject addOrUpdateDataHospitalVisit(@ModelAttribute DataHospitalVisit hospital,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == hospital.getId()){//添加
				hospital.setCreatedAt(date);
				hospital.setCreatedBy(username);
				dataHospitalVisitService.insert(hospital);
			}else{//修改
				//DataHospitalVisit dbHospital = dataHospitalVisitService.selectByPrimaryKey(hospital.getId());
				hospital.setUpdatedAt(date);
				hospital.setUpdatedBy(username);
				dataHospitalVisitService.updateByPrimaryKeySelective(hospital);
			}
			json.put("id", hospital.getId());
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalVisit-AJAX-/back/addOrUpdateDataHospitalVisit-新增或修改诊所数据走访出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDataHospitalVisitDelete",method=RequestMethod.POST)
	public JSONObject updateDataHospitalVisitDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_visit);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalVisit-AJAX-/back/updateDataHospitalVisitDelete-删除诊所数据走访出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDataHospitalVisitRecover",method=RequestMethod.POST)
	public JSONObject updateDataHospitalVisitRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_visit);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalVisit-AJAX-/back/updateDataHospitalVisitRecover-恢复诊所数据走访出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDataHospitalVisitThorough",method=RequestMethod.POST)
	public JSONObject deleteDataHospitalVisitThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_visit);
			map.put("username",username);
			utilService.deleteByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalVisit-AJAX-/back/deleteDataHospitalVisitThorough-彻底删除诊所数据走访出现异常", json);
		}
		return Result.success(json);
	}
	
	
}
