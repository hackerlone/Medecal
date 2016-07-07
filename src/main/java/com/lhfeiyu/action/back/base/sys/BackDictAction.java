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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Dict;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DictService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackDictAction {
	
	@Autowired
	private DictService  dictService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/dict")
	public ModelAndView  dict(ModelMap modelMap,HttpSession session,
			@RequestParam(value="typeId",required=false) Integer typeId){
		String path = PagePath.backDict;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "数据字典页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Dict-PAGE-/back/dict-加载数据字典出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDictList", method=RequestMethod.POST)
	public JSONObject getDictList(HttpServletRequest request) {
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
		//	map.put("parentCodeIsNotNull", 1);
			map.put("parentCodeNotNull", 1);//不查跟节点
			List<Dict> dictList = dictService.selectListByCondition(map);
			Integer total = dictService.selectCountByCondition(map);
			Result.gridData(dictList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/getDictList-加载数据字典列表出现异常", json);
		}
		return json;
	}
	
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
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/getArticleTypeArray-加载数据字典文章类型列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPatientJobTypeArray", method=RequestMethod.POST)
	public JSONArray getPatientJobTypeArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parentCode", "job");
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
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/getPatientJobTypeArray-加载数据字典职业类型列表出现异常", array);
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
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/getTitlesArray-加载数据字典职称类型列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDict", method = RequestMethod.POST)
	public JSONObject addOrUpdateDict(@ModelAttribute Dict dict,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == dict.getId()){//添加
				dict.setCreatedAt(date);
				dict.setCreatedBy(username);
				dictService.insert(dict);
			}else{//修改
				dict.setUpdatedAt(date);
				dict.setUpdatedBy(username);
				dictService.updateByPrimaryKeySelective(dict);
			}
			json.put("id", dict.getId());
			Result.success(json, "添加或修改数据字典成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/addOrUpdateDict-新增或修改数据字典出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDictDelete",method=RequestMethod.POST)
	public JSONObject updateDictDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("dictIds", ids);
			map.put("authority", 3);//只读
			int count = dictService.selectCountByCondition(map);
			if(count > 0){
				return Result.failure(json, "不能删除权限为只读的数据", "authority_readonly");
			}
			dictService.updateDeletedNowByIds(ids, username);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/updateDictDelete-删除数据字典出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDictRecover",method=RequestMethod.POST)
	public JSONObject updateDictRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.dict);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/updateDictRecover-恢复数据字典出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDictThorough",method=RequestMethod.POST)
	public JSONObject deleteDictThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("dictIds", ids);
			map.put("authority", 3);//只读
			int count = dictService.selectCountByCondition(map);
			if(count > 0){
				return Result.failure(json, "不能删除权限为只读的数据", "authority_readonly");
			}
			dictService.deleteByIds(ids);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/deleteDictThorough-彻底删除数据字典出现异常", json);
		}
		return json;
	}
	
	/**
	 * 加载所有的数据字典
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllDictArray")
	public JSONArray getAllDictArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			array = dictService.getDictArray(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 加载数据字典的根节点
	 * @param request
	 * @return JSONArray
	 */
	@ResponseBody
	@RequestMapping(value = "/getRootDictArray")
	public JSONArray getRootDictArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parentCodeNull", 1);
			array = dictService.getDictArray(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 加载可扩展的数据字典数组
	 * @param request
	 * @return JSONArray
	 */
	@ResponseBody
	@RequestMapping(value = "/getRootDictArrayForExpand")
	public JSONArray getRootDictArrayForExpand(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parentCodeNull", 1);
			map.put("canExpand", 1);//允许扩展
			array = dictService.getDictArray(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 根据父类型加载数据字典，返回数组，一般用于下拉列表
	 * @param request
	 * @param parentCode
	 * @return JSONArray
	 */
	@ResponseBody
	@RequestMapping(value = "/getDictArrayByParentCode")
	public JSONArray getDictArrayByParentCode(HttpServletRequest request, @RequestParam String parentCode) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("parentCode", parentCode);
			array = dictService.getDictArray(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	
}
