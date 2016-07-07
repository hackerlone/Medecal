package com.lhfeiyu.action.back.domain.phraseRecord;

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
import com.lhfeiyu.po.PhraseRecord;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.PhraseRecordService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackPhraseRecordAction {
	
	@Autowired
	private PhraseRecordService  phraseRecordService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/phraseRecord")
	public ModelAndView  phraseRecord(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backPhraseRecord;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "短语页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-PhraseRecord-PAGE-/back/phraseRecord-加载短语出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPhraseRecordList", method=RequestMethod.POST)
	public JSONObject getPhraseRecordList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "name");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "name");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<PhraseRecord> phraseRecordList = phraseRecordService.selectListByCondition(map);
			Integer total = phraseRecordService.selectCountByCondition(map);
			Result.gridData(phraseRecordList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PhraseRecord-AJAX-/back/getPhraseRecordList-加载短语列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPhraseRecordArray", method=RequestMethod.POST)
	public JSONArray getPhraseRecordArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<PhraseRecord> phraseRecordList = phraseRecordService.selectListByCondition(map);
			for(PhraseRecord m:phraseRecordList){
				JSONObject json = new JSONObject();
				json.put("id",m.getId());
				json.put("name",m.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PhraseRecordType-AJAX-/back/getPhraseRecordArray-加载短语类型数组列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdatePhraseRecord", method = RequestMethod.POST)
	public JSONObject addOrUpdatePhraseRecord(@ModelAttribute PhraseRecord phraseRecord,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Integer phraseRecordId = phraseRecord.getId();
			if(null == phraseRecordId){//添加
				phraseRecordService.insertData(json,phraseRecord,admin);
			}else{//修改
				phraseRecordService.updateData(json,phraseRecord,admin);
			}
			json.put("id", phraseRecord.getId());
			Result.success(json, "添加或修改短语成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PhraseRecord-AJAX-/back/addOrUpdatePhraseRecord-新增或修改短语出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updatePhraseRecordDelete",method=RequestMethod.POST)
	public JSONObject updatePhraseRecordDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.phrase_record);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PhraseRecord-AJAX-/back/updatePhraseRecordDelete-删除短语出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePhraseRecordRecover",method=RequestMethod.POST)
	public JSONObject updatePhraseRecordRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.phrase_record);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PhraseRecord-AJAX-/back/updatePhraseRecordRecover-恢复短语出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletePhraseRecordThorough",method=RequestMethod.POST)
	public JSONObject deletePhraseRecordThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.phrase_record);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PhraseRecord-AJAX-/back/deletePhraseRecordThorough-彻底删除短语出现异常", json);
		}
		return json;
	}
	
}
