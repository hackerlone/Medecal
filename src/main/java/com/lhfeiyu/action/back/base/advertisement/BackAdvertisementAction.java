package com.lhfeiyu.action.back.base.advertisement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.lhfeiyu.po.Advertisement;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.AdvertisementService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackAdvertisementAction {
	@Autowired
	private AdvertisementService  advertisementService;
	@Autowired
	private AA_UtilService  utilService;
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/advertisement")
	public ModelAndView  advertisement(ModelMap modelMap,HttpSession session,
			@RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backAdvertisement;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "广告页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Advertisement-PAGE-/back/advertisement-加载广告出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/addOrUpdateAdvertisementPage")
	public ModelAndView  addOrUpdateAdvertisementPage(ModelMap modelMap,HttpSession session,
			@RequestParam(value="advertisementId",required=false) Integer advertisementId,
			@RequestParam(value="operation",required=false) String operation){
		String path = PagePath.backAddOrUpdateAdvertisement;
		try{
			if(null != advertisementId && !"".equals(advertisementId)){
				Advertisement advertisement = advertisementService.selectByPrimaryKey(advertisementId);
				if(null != advertisement && !"".equals(advertisement)){
					modelMap.put("advertisement", advertisement);
					JSONObject obj = new JSONObject();
					obj.put("advertisement", advertisement);
					modelMap.put("advertisementJson", obj.toJSONString());
					modelMap.put("advertisementId", advertisementId);
					modelMap.put("operation", operation);
					Result.success(modelMap, "数据加载成功", null);
				}else{
					Result.failure(modelMap, "无法查询到您所需要的数据", null);
				}
			}
			Result.success(modelMap, "添加或修改文章页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Advertisement-PAGE-/back/advertisement-加载添加或修改广告页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getAdvertisementList", method=RequestMethod.POST)
	public JSONObject getAdvertisementList(HttpServletRequest request) {
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
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "title");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "title");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<Advertisement> advertisementList = advertisementService.selectListByCondition(map);
			Integer total = advertisementService.selectCountByCondition(map);
			Result.gridData(advertisementList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Advertisement-AJAX-/back/getAdvertisementList-加载广告列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateAdvertisement", method = RequestMethod.POST)
	public JSONObject addOrUpdateAdvertisement(@ModelAttribute Advertisement advertisement,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			if(null == advertisement.getId()){//添加
				Integer catId = advertisement.getCatId();
				map.put("catId", catId);
				map.put("mainStatus", 1);
				List<Advertisement> db_advertisementList = advertisementService.selectListByCondition(map);
				if(db_advertisementList.size() >= 5){
					return Result.failure(json, "每个栏目最多添加5张图片,您可以删除或修改以前的图片", null);
				}
				advertisement.setCreatedAt(date);
				advertisement.setCreatedBy(username);
				advertisementService.insert(advertisement);
			}else{//修改
				advertisement.setUpdatedAt(date);
				advertisement.setUpdatedBy(username);
				advertisementService.updateByPrimaryKeySelective(advertisement);
			}
			json.put("id", advertisement.getId());
			Result.success(json, "添加或修改广告成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Advertisement-AJAX-/back/addOrUpdateAdvertisement-新增或修改广告出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateAdvertisementDelete",method=RequestMethod.POST)
	public JSONObject updateAdvertisementDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.advertisement);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Advertisement-AJAX-/back/updateAdvertisementDelete-删除广告出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateAdvertisementRecover",method=RequestMethod.POST)
	public JSONObject updateAdvertisementRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.advertisement);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Advertisement-AJAX-/back/updateAdvertisementRecover-恢复广告出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAdvertisementThorough",method=RequestMethod.POST)
	public JSONObject deleteAdvertisementThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.advertisement);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Advertisement-AJAX-/back/deleteAdvertisementThorough-彻底删除广告出现异常", json);
		}
		return json;
	}
	
}
