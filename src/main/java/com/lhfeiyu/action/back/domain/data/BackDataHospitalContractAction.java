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
import com.lhfeiyu.po.DataHospitalContract;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DataHospitalContractService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackDataHospitalContractAction {
	
	@Autowired
	private DataHospitalContractService  dataHospitalContractService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/getDataHospitalContractList", method=RequestMethod.POST)
	public JSONObject getDataHospitalContractList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			
			if(admin.getRoleId()  == 3){//数据录入员 - 只查看自己的数据
				map.put("adminId", admin.getId());
			}
			
			List<DataHospitalContract> hospitalList = dataHospitalContractService.selectListByCondition(map);
			Integer total = dataHospitalContractService.selectCountByCondition(map);
			Result.gridData(hospitalList, total, json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalContract-AJAX-/back/getDataHospitalContractList-加载诊所数据合同列表出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDataHospitalContract", method = RequestMethod.POST)
	public JSONObject addOrUpdateDataHospitalContract(@ModelAttribute DataHospitalContract hospital,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == hospital.getId()){//添加
				hospital.setCreatedAt(date);
				hospital.setCreatedBy(username);
				dataHospitalContractService.insert(hospital);
			}else{//修改
				//DataHospitalContract dbHospital = dataHospitalContractService.selectByPrimaryKey(hospital.getId());
				hospital.setUpdatedAt(date);
				hospital.setUpdatedBy(username);
				dataHospitalContractService.updateByPrimaryKeySelective(hospital);
			}
			json.put("id", hospital.getId());
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalContract-AJAX-/back/addOrUpdateDataHospitalContract-新增或修改诊所数据合同出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDataHospitalContractDelete",method=RequestMethod.POST)
	public JSONObject updateDataHospitalContractDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_contract);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalContract-AJAX-/back/updateDataHospitalContractDelete-删除诊所数据合同出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDataHospitalContractRecover",method=RequestMethod.POST)
	public JSONObject updateDataHospitalContractRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_contract);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalContract-AJAX-/back/updateDataHospitalContractRecover-恢复诊所数据合同出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDataHospitalContractThorough",method=RequestMethod.POST)
	public JSONObject deleteDataHospitalContractThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_contract);
			map.put("username",username);
			utilService.deleteByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalContract-AJAX-/back/deleteDataHospitalContractThorough-彻底删除诊所数据合同出现异常", json);
		}
		return Result.success(json);
	}
	
	
}
