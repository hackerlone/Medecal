package com.lhfeiyu.action.back.domain.hospital;

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
import com.lhfeiyu.po.DiagnoseTemplate;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DiagnoseTemplateService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackDiagnoseTemplateAction {
	
	@Autowired
	private DiagnoseTemplateService  diagnoseTemplateService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/diagnoseTemplate")
	public ModelAndView  diagnoseTemplate(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backDiagnoseTemplate;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "病历模板页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-DiagnoseTemplate-PAGE-/back/diagnoseTemplate-加载病历模板出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDiagnoseTemplateList", method=RequestMethod.POST)
	public JSONObject getDiagnoseTemplateList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "hospital_id");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "hospital_id");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "doctor_id");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "doctor_id");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("5")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("6")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<DiagnoseTemplate> diagnoseTemplateList = diagnoseTemplateService.selectListByCondition(map);
			Integer total = diagnoseTemplateService.selectCountByCondition(map);
			Result.gridData(diagnoseTemplateList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseTemplate-AJAX-/back/getDiagnoseTemplateList-加载病历模板列表出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDiagnoseTemplate", method = RequestMethod.POST)
	public JSONObject addOrUpdateDiagnoseTemplate(@ModelAttribute DiagnoseTemplate diagnoseTemplate,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == diagnoseTemplate.getId()){//添加
				diagnoseTemplate.setCreatedAt(date);
				diagnoseTemplate.setCreatedBy(username);
				diagnoseTemplateService.insert(diagnoseTemplate);
			}else{//修改
				diagnoseTemplate.setUpdatedAt(date);
				diagnoseTemplate.setUpdatedBy(username);
				diagnoseTemplateService.updateByPrimaryKeySelective(diagnoseTemplate);
			}
			json.put("id", diagnoseTemplate.getId());
			Result.success(json, "添加或修改病历模板成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseTemplate-AJAX-/back/addOrUpdateDiagnoseTemplate-新增或修改病历模板出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDiagnoseTemplateDelete",method=RequestMethod.POST)
	public JSONObject updateDiagnoseTemplateDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.diagnose_template);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseTemplate-AJAX-/back/updateDiagnoseTemplateDelete-删除病历模板出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDiagnoseTemplateRecover",method=RequestMethod.POST)
	public JSONObject updateDiagnoseTemplateRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.diagnose_template);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseTemplate-AJAX-/back/updateDiagnoseTemplateRecover-恢复病历模板出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDiagnoseTemplateThorough",method=RequestMethod.POST)
	public JSONObject deleteDiagnoseTemplateThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.diagnose_template);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-DiagnoseTemplate-AJAX-/back/deleteDiagnoseTemplateThorough-彻底删除病历模板出现异常", json);
		}
		return json;
	}
	
}
