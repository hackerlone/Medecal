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
import com.lhfeiyu.po.DataHospitalNurse;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DataHospitalNurseService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackDataHospitalNurseAction {
	
	@Autowired
	private DataHospitalNurseService  dataHospitalNurseService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/getDataHospitalNurseList", method=RequestMethod.POST)
	public JSONObject getDataHospitalNurseList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);

			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			
			if(admin.getRoleId()  == 3){//数据录入员 - 只查看自己的数据
				map.put("adminId", admin.getId());
			}
			
			List<DataHospitalNurse> hospitalList = dataHospitalNurseService.selectListByCondition(map);
			Integer total = dataHospitalNurseService.selectCountByCondition(map);
			Result.gridData(hospitalList, total, json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalNurse-AJAX-/back/getDataHospitalNurseList-加载诊所数据护士列表出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDataHospitalNurse", method = RequestMethod.POST)
	public JSONObject addOrUpdateDataHospitalNurse(@ModelAttribute DataHospitalNurse hospital,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == hospital.getId()){//添加
				hospital.setCreatedAt(date);
				hospital.setCreatedBy(username);
				dataHospitalNurseService.insert(hospital);
			}else{//修改
				//DataHospitalNurse dbHospital = dataHospitalNurseService.selectByPrimaryKey(hospital.getId());
				hospital.setUpdatedAt(date);
				hospital.setUpdatedBy(username);
				dataHospitalNurseService.updateByPrimaryKeySelective(hospital);
			}
			json.put("id", hospital.getId());
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalNurse-AJAX-/back/addOrUpdateDataHospitalNurse-新增或修改诊所数据护士出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDataHospitalNurseDelete",method=RequestMethod.POST)
	public JSONObject updateDataHospitalNurseDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_nurse);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalNurse-AJAX-/back/updateDataHospitalNurseDelete-删除诊所数据护士出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDataHospitalNurseRecover",method=RequestMethod.POST)
	public JSONObject updateDataHospitalNurseRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_nurse);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalNurse-AJAX-/back/updateDataHospitalNurseRecover-恢复诊所数据护士出现异常", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDataHospitalNurseThorough",method=RequestMethod.POST)
	public JSONObject deleteDataHospitalNurseThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			if(Check.isNull(ids))return Result.failure(json, "请先选择数据", "ids_null");
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.data_hospital_nurse);
			map.put("username",username);
			utilService.deleteByIds(map);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DataHospitalNurse-AJAX-/back/deleteDataHospitalNurseThorough-彻底删除诊所数据护士出现异常", json);
		}
		return Result.success(json);
	}
	
	
}
