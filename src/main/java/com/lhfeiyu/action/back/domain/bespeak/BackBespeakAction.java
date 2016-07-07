package com.lhfeiyu.action.back.domain.bespeak;

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
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.BespeakService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackBespeakAction {
	
	@Autowired
	private BespeakService  bespeakService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/bespeak")
	public ModelAndView  bespeak(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backBespeak;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "预约页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Bespeak-PAGE-/back/bespeak-加载预约出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBespeakList", method=RequestMethod.POST)
	public JSONObject getBespeakList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "bespeak_time");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "bespeak_time");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "base_condition");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "base_condition");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("5")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("6")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<Bespeak> bespeakList = bespeakService.selectListByCondition(map);
			Integer total = bespeakService.selectCountByCondition(map);
			Result.gridData(bespeakList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/back/getBespeakList-加载预约列表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateBespeak", method = RequestMethod.POST)
	public JSONObject addOrUpdateBespeak(@ModelAttribute Bespeak bespeak,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == bespeak.getId()){//添加
				bespeak.setCreatedAt(date);
				bespeak.setCreatedBy(username);
				bespeakService.insert(bespeak);
			}else{//修改
				bespeak.setUpdatedAt(date);
				bespeak.setUpdatedBy(username);
				bespeakService.updateByPrimaryKeySelective(bespeak);
			}
			json.put("id", bespeak.getId());
			Result.success(json, "添加或修改预约成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/back/addOrUpdateBespeak-新增或修改预约出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateBespeakDelete",method=RequestMethod.POST)
	public JSONObject updateBespeakDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.bespeak);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/back/updateBespeakDelete-删除预约出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBespeakRecover",method=RequestMethod.POST)
	public JSONObject updateBespeakRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.bespeak);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/back/updateBespeakRecover-恢复预约出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBespeakThorough",method=RequestMethod.POST)
	public JSONObject deleteBespeakThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.bespeak);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Bespeak-AJAX-/back/deleteBespeakThorough-彻底删除预约出现异常", json);
		}
		return json;
	}
	
}
