package com.lhfeiyu.action.front.base.sys;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.SysArticle;
import com.lhfeiyu.service.SysArticleService;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class SysArticleAction {
	
	@Autowired
	private SysArticleService  sysArticleService;
	
	private static Logger logger = Logger.getLogger("R");

	@ResponseBody
	@RequestMapping(value = "/getSysArticleList")
	public JSONObject getSysArticleList(HttpServletRequest request) {
		List<SysArticle> sysArticleList = null;
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			map = Pagination.getOrderByAndPage(map,request);
			sysArticleList = sysArticleService.selectListByCondition(map);
			Integer total = sysArticleService.selectCountByCondition(map);
			Result.gridData(sysArticleList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-SysArticle-AJAX-/getSysArticleList-加载新闻文章列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sysArticle/{articleId}")
	public JSONObject sysArticle(HttpServletRequest request, @PathVariable Integer articleId) {
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			map = Pagination.getOrderByAndPage(map,request);
			SysArticle sysArticle = sysArticleService.selectByPrimaryKey(articleId);
			json.put("sysArticle", sysArticle);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-SysArticle-AJAX-/sysArticle-加载新闻文章列表出现异常", json);
		}
		return json;
	}


	
}
