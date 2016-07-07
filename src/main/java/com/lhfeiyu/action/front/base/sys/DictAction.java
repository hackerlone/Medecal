package com.lhfeiyu.action.front.base.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.Dict;
import com.lhfeiyu.service.DictService;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class DictAction {
	
	@Autowired
	private DictService  dictService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@ResponseBody
	@RequestMapping(value = "/getArticleTypeArray", method=RequestMethod.POST)
	public JSONArray getArticleTypeArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parentCode", "article_type");
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			List<Dict> dictList = dictService.selectListByCondition(map);
			for(Dict d:dictList){
				JSONObject json = new JSONObject();
				json.put("id", d.getId());
				json.put("name", d.getCodeName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/getArticleTypeArray-加载数据字典文章类型列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getTitlesArray", method=RequestMethod.POST)
	public JSONArray getTitlesArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parentCode", "title");
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			List<Dict> dictList = dictService.selectListByCondition(map);
			for(Dict d:dictList){
				JSONObject json = new JSONObject();
				json.put("id", d.getId());
				json.put("name", d.getCodeName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/getTitlesArray-加载数据字典职称类型列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDictArray", method=RequestMethod.POST)
	public JSONArray getDictArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			List<Dict> dictList = dictService.selectListByCondition(map);
			for(Dict d:dictList){
				JSONObject json = new JSONObject();
				json.put("id", d.getId());
				json.put("name", d.getCodeName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/getDictArray-加载数据字典列表出现异常", array);
		}
		return array;
	}
	
	
}

