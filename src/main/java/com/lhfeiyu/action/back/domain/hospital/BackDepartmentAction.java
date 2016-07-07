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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Department;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DepartmentService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackDepartmentAction {
	
	@Autowired
	private DepartmentService  departmentService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/department")
	public ModelAndView  department(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backDepartment;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "科室页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Department-PAGE-/back/department-加载科室出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDepartmentList", method=RequestMethod.POST)
	public JSONObject getDepartmentList(HttpServletRequest request) {
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
			List<Department> departmentList = departmentService.selectListByCondition(map);
			Integer total = departmentService.selectCountByCondition(map);
			Result.gridData(departmentList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Department-AJAX-/back/getDepartmentList-加载科室列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDepartmentArray", method=RequestMethod.POST)
	public JSONArray getDepartmentArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<Department> departmentList = departmentService.selectListByCondition(map);
			for(Department d:departmentList){
				JSONObject json = new JSONObject();
				json.put("id",d.getId());
				json.put("name",d.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Department-AJAX-/back/getDepartmentArray-加载科室数组列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getParentDepartmentArray", method=RequestMethod.POST)
	public JSONArray getParentDepartmentArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parentIdNull", 1);
			List<Department> departmentList = departmentService.selectListByCondition(map);
			for(Department d:departmentList){
				JSONObject json = new JSONObject();
				json.put("id",d.getId());
				json.put("name",d.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Department-AJAX-/back/getParentDepartmentArray-加载科室数组列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDepartment", method = RequestMethod.POST)
	public JSONObject addOrUpdateDepartment(@ModelAttribute Department department,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == department.getId()){//添加
				department.setCreatedAt(date);
				department.setCreatedBy(username);
				departmentService.insert(department);
			}else{//修改
				department.setUpdatedAt(date);
				department.setUpdatedBy(username);
				departmentService.updateByPrimaryKeySelective(department);
			}
			json.put("id", department.getId());
			Result.success(json, "添加或修改科室成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Department-AJAX-/back/addOrUpdateDepartment-新增或修改科室出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDepartmentDelete",method=RequestMethod.POST)
	public JSONObject updateDepartmentDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.department);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Department-AJAX-/back/updateDepartmentDelete-删除科室出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDepartmentRecover",method=RequestMethod.POST)
	public JSONObject updateDepartmentRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.department);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Department-AJAX-/back/updateDepartmentRecover-恢复科室出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDepartmentThorough",method=RequestMethod.POST)
	public JSONObject deleteDepartmentThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.department);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Department-AJAX-/back/deleteDepartmentThorough-彻底删除科室出现异常", json);
		}
		return json;
	}
	
	
}
