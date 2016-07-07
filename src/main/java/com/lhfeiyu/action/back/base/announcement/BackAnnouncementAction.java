package com.lhfeiyu.action.back.base.announcement;

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
import com.lhfeiyu.po.Announcement;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.AnnouncementService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackAnnouncementAction {
	@Autowired
	private AnnouncementService  announcementService;
	@Autowired
	private AA_UtilService  utilService;
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/announcement")
	public ModelAndView  announcement(ModelMap modelMap,HttpSession session,
			@RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backAnnouncement;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "公告页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Announcement-PAGE-/back/announcement-加载公告出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/addOrUpdateAnnouncementPage")
	public ModelAndView  addOrUpdateAnnouncementPage(ModelMap modelMap,HttpSession session,
			@RequestParam(value="announcementId",required=false) Integer announcementId,
			@RequestParam(value="operation",required=false) String operation){
		String path = PagePath.backAddOrUpdateAnnouncement;
		try{
			if(null != announcementId && !"".equals(announcementId)){
				Announcement announcement = announcementService.selectByPrimaryKey(announcementId);
				if(null != announcement && !"".equals(announcement)){
					modelMap.put("announcement", announcement);
					JSONObject obj = new JSONObject();
					obj.put("announcement", announcement);
					modelMap.put("announcementJson", obj.toJSONString());
					modelMap.put("announcementId", announcementId);
					modelMap.put("operation", operation);
					Result.success(modelMap, "数据加载成功", null);
				}else{
					Result.failure(modelMap, "无法查询到您所需要的数据", null);
				}
			}
			Result.success(modelMap, "添加或修改文章页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Announcement-PAGE-/back/announcement-加载添加或修改公告页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getAnnouncementList", method=RequestMethod.POST)
	public JSONObject getAnnouncementList(HttpServletRequest request) {
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
					map.put("orderBy", "content");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "content");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<Announcement> announcementList = announcementService.selectListByCondition(map);
			Integer total = announcementService.selectCountByCondition(map);
			Result.gridData(announcementList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Announcement-AJAX-/back/getAnnouncementList-加载公告列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateAnnouncement", method = RequestMethod.POST)
	public JSONObject addOrUpdateAnnouncement(@ModelAttribute Announcement announcement,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == announcement.getId()){//添加
				announcement.setCreatedAt(date);
				announcement.setCreatedBy(username);
				announcementService.insert(announcement);
			}else{//修改
				announcement.setUpdatedAt(date);
				announcement.setUpdatedBy(username);
				announcementService.updateByPrimaryKeySelective(announcement);
			}
			json.put("id", announcement.getId());
			Result.success(json, "添加或修改公告成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Announcement-AJAX-/back/addOrUpdateAnnouncement-新增或修改公告出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateAnnouncementDelete",method=RequestMethod.POST)
	public JSONObject updateAnnouncementDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.announcement);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Announcement-AJAX-/back/updateAnnouncementDelete-删除公告出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateAnnouncementRecover",method=RequestMethod.POST)
	public JSONObject updateAnnouncementRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.announcement);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Announcement-AJAX-/back/updateAnnouncementRecover-恢复公告出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAnnouncementThorough",method=RequestMethod.POST)
	public JSONObject deleteAnnouncementThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.announcement);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Announcement-AJAX-/back/deleteAnnouncementThorough-彻底删除公告出现异常", json);
		}
		return json;
	}
	
}
