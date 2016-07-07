package com.lhfeiyu.action.back.base.sys;

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
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.SysArticle;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.SysArticleService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackSysArticleAction {
	
	@Autowired
	private SysArticleService  sysArticleService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/sysArticle")
	public ModelAndView  sysArticle(ModelMap modelMap,HttpSession session,
			@RequestParam(value="typeId",required=false) Integer typeId){
		String path = PagePath.backSysArticle;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "文章页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-SysArticle-PAGE-/back/sysArticle-加载文章出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/addOrUpdateSysArticlePage")
	public ModelAndView  addOrUpdateSysArticlePage(ModelMap modelMap,HttpSession session,
			@RequestParam(value="sysArticleId",required=false) Integer sysArticleId,
			@RequestParam(value="roleCode" ,required=false) String roleCode){
		String path = PagePath.backAddOrUpdateSysArticle;
		try{
			SysArticle sysArticle = null;
			if(null != sysArticleId){
				sysArticle = sysArticleService.selectByPrimaryKey(sysArticleId);
			}else if(null != roleCode){
				Map<String, Object> map = CommonGenerator.getHashMap();
				map.put("roleCode", roleCode);
				sysArticle = sysArticleService.selectByCondition(map);
			}
			modelMap.put("sysArticle", sysArticle);
			JSONObject obj = new JSONObject();
			obj.put("sysArticle", sysArticle);
			modelMap.put("sysArticleJson", obj.toJSONString());
			modelMap.put("sysArticleId", sysArticleId);
			Result.success(modelMap, "添加或修改文章页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-SysArticle-PAGE-/back/sysArticle-加载添加或修改文章页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getSysArticleList", method=RequestMethod.POST)
	public JSONObject getSysArticleList(HttpServletRequest request) {
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
			List<SysArticle> sysArticleList = sysArticleService.selectListByCondition(map);
			Integer total = sysArticleService.selectCountByCondition(map);
			Result.gridData(sysArticleList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-SysArticle-AJAX-/back/getSysArticleList-加载文章列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateSysArticle", method = RequestMethod.POST)
	public JSONObject addOrUpdateSysArticle(@ModelAttribute SysArticle sysArticle,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == sysArticle.getId()){//添加
				sysArticle.setScans(0);
				sysArticle.setCreatedAt(date);
				sysArticle.setCreatedBy(username);
				sysArticleService.insert(sysArticle);
			}else{//修改
				sysArticle.setUpdatedAt(date);
				sysArticle.setUpdatedBy(username);
				sysArticleService.updateByPrimaryKeySelective(sysArticle);
			}
			json.put("id", sysArticle.getId());
			Result.success(json, "添加或修改文章成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-SysArticle-AJAX-/back/addOrUpdateSysArticle-新增或修改文章出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateSysArticleDelete",method=RequestMethod.POST)
	public JSONObject updateSysArticleDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", "sys_article");
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-SysArticle-AJAX-/back/updateSysArticleDelete-删除文章出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateSysArticleRecover",method=RequestMethod.POST)
	public JSONObject updateSysArticleRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", "sys_article");
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-SysArticle-AJAX-/back/updateSysArticleRecover-恢复文章出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteSysArticleThorough",method=RequestMethod.POST)
	public JSONObject deleteSysArticleThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", "sys_article");
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-SysArticle-AJAX-/back/deleteSysArticleThorough-彻底删除文章出现异常", json);
		}
		return json;
	}
	
}
