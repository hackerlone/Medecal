package com.lhfeiyu.action.front.base.sys;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class ProvinceCityAreaAction {
	
	@Autowired
	private ProvinceCityAreaService  provinceCityAreaService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@ResponseBody
	@RequestMapping(value = "/getProvinceCityAreaList", method=RequestMethod.POST)
	public JSONObject getProvinceCityAreaList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("higherIdISNOTNULL", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			Integer total = provinceCityAreaService.selectCountByCondition(map);
			Result.gridData(provinceCityAreaList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-AJAX-/back/getProvinceCityAreaList-加载省市区信息列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProvinceArray", method=RequestMethod.POST)
	public JSONArray getProvinceArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			for(ProvinceCityArea pc:provinceCityAreaList){
				JSONObject json = new JSONObject();
				json.put("id",pc.getId());
				json.put("name",pc.getAreaName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-AJAX-/back/getProvinceArray-加载省(直辖市)数组列表出现异常", array);
		}
		return array;
	}
	@ResponseBody
	@RequestMapping(value = "/getCityArray", method=RequestMethod.POST)
	public JSONArray getCityArray(HttpServletRequest request,@RequestParam Integer provinceId) {
		JSONArray array = new JSONArray();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);
			map.put("mainStatus", 1);
			map.put("higherId", provinceId);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			for(ProvinceCityArea pc:provinceCityAreaList){
				JSONObject json = new JSONObject();
				json.put("id",pc.getId());
				json.put("name",pc.getAreaName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-AJAX-/back/getCityArray-加载市(县)数组列表出现异常", array);
		}
		return array;
	}
	
}
