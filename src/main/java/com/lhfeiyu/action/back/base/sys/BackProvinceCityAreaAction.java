package com.lhfeiyu.action.back.base.sys;

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
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackProvinceCityAreaAction {
	
	@Autowired
	private ProvinceCityAreaService  provinceCityAreaService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/provinceCityArea")
	public ModelAndView  provinceCityArea(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backProvinceCityArea;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "省市区信息页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-PAGE-/back/provinceCityArea-加载省市区信息出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProvinceCityAreaList", method=RequestMethod.POST)
	public JSONObject getProvinceCityAreaList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
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
		try {//自动获取所有参数（查询条件）
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
		try {//自动获取所有参数（查询条件）
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
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateProvinceCityArea", method = RequestMethod.POST)
	public JSONObject addOrUpdateProvinceCityArea(@ModelAttribute ProvinceCityArea provinceCityArea,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == provinceCityArea.getId()){//添加
				provinceCityArea.setCreatedAt(date);
				provinceCityArea.setCreatedBy(username);
				provinceCityAreaService.insert(provinceCityArea);
			}else{//修改
				provinceCityArea.setUpdatedAt(date);
				provinceCityArea.setUpdatedBy(username);
				provinceCityAreaService.updateByPrimaryKeySelective(provinceCityArea);
			}
			json.put("id", provinceCityArea.getId());
			Result.success(json, "添加或修改省市区信息成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-AJAX-/back/addOrUpdateProvinceCityArea-新增或修改省市区信息出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateProvinceCityAreaDelete",method=RequestMethod.POST)
	public JSONObject updateProvinceCityAreaDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.province_city_area);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-AJAX-/back/updateProvinceCityAreaDelete-删除省市区信息出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateProvinceCityAreaRecover",method=RequestMethod.POST)
	public JSONObject updateProvinceCityAreaRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.province_city_area);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-AJAX-/back/updateProvinceCityAreaRecover-恢复省市区信息出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteProvinceCityAreaThorough",method=RequestMethod.POST)
	public JSONObject deleteProvinceCityAreaThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.province_city_area);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ProvinceCityArea-AJAX-/back/deleteProvinceCityAreaThorough-彻底删除省市区信息出现异常", json);
		}
		return json;
	}
	
	
}
