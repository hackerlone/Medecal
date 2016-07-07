package com.lhfeiyu.action.back.base.article;

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
import com.lhfeiyu.po.Article;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.ArticleService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackArticleAction {
	
	@Autowired
	private ArticleService  articleService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/article")
	public ModelAndView  article(ModelMap modelMap,HttpSession session,
			@RequestParam(value="typeId",required=false) Integer typeId){
		String path = PagePath.backArticle;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "文章页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Article-PAGE-/back/article-加载文章出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	@RequestMapping(value="/addOrUpdateArticlePage")
	public ModelAndView  addOrUpdateArticlePage(ModelMap modelMap,HttpSession session,
			@RequestParam(value="articleId",required=false) Integer articleId,
			@RequestParam(value="operation" ,required=false) String operation){
		String path = PagePath.backAddOrUpdateArticle;
		try{
			if(null != articleId && !"".equals(articleId)){
				Article article = articleService.selectByPrimaryKey(articleId);
				if(null != article && !"".equals(article)){
					modelMap.put("article", article);
					JSONObject obj = new JSONObject();
					obj.put("article", article);
					modelMap.put("articleJson", obj.toJSONString());
					modelMap.put("articleId", articleId);
					modelMap.put("operation", operation);
					Result.success(modelMap, "数据加载成功", null);
				}else{
					Result.failure(modelMap, "无法查询到您所需要的数据", null);
				}
			}
			Result.success(modelMap, "添加或修改文章页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Article-PAGE-/back/article-加载添加或修改文章页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getArticleList", method=RequestMethod.POST)
	public JSONObject getArticleList(HttpServletRequest request) {
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
			List<Article> articleList = articleService.selectListByCondition(map);
			Integer total = articleService.selectCountByCondition(map);
			Result.gridData(articleList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Article-AJAX-/back/getArticleList-加载文章列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateArticle", method = RequestMethod.POST)
	public JSONObject addOrUpdateArticle(@ModelAttribute Article article,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == article.getId()){//添加
				article.setScans(0);
				article.setCreatedAt(date);
				article.setCreatedBy(username);
				articleService.insert(article);
			}else{//修改
				article.setUpdatedAt(date);
				article.setUpdatedBy(username);
				articleService.updateByPrimaryKeySelective(article);
			}
			json.put("id", article.getId());
			Result.success(json, "添加或修改文章成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Article-AJAX-/back/addOrUpdateArticle-新增或修改文章出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateArticleDelete",method=RequestMethod.POST)
	public JSONObject updateArticleDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.article);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Article-AJAX-/back/updateArticleDelete-删除文章出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateArticleRecover",method=RequestMethod.POST)
	public JSONObject updateArticleRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.article);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Article-AJAX-/back/updateArticleRecover-恢复文章出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteArticleThorough",method=RequestMethod.POST)
	public JSONObject deleteArticleThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.article);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Article-AJAX-/back/deleteArticleThorough-彻底删除文章出现异常", json);
		}
		return json;
	}
	
}
