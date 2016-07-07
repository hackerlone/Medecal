package com.lhfeiyu.action.back.base.sys;

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
import com.lhfeiyu.po.Role;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.RoleService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackRoleAction {
	
	@Autowired
	private RoleService  roleService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/role")
	public ModelAndView  role(ModelMap modelMap,@RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backRole;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "角色页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Role-PAGE-/back/role-加载角色出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateRole", method = RequestMethod.POST)
	public JSONObject addOrUpdateRole(@ModelAttribute Role role,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Date date = new Date();
			String username = admin.getUsername();
			if(null == role.getId()){//添加
				role.setCreatedAt(date);
				role.setCreatedBy(username);
				roleService.insert(role);
			}else{//修改
				role.setUpdatedAt(date);
				role.setUpdatedBy(username);
				roleService.updateByPrimaryKeySelective(role);
			}
			json.put("id", role.getId());
			Result.success(json, "添加或修改角色成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Role-AJAX-/back/addOrUpdateRole-添加或修改角色出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRoleList",method=RequestMethod.POST)
	public JSONObject getRoleList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<Role> roleList = roleService.selectListByCondition(map);
			Integer total = roleService.selectCountByCondition(map);
			Result.gridData(roleList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Role-AJAX-/back/getRoleList-加载角色列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRoleCombobox",method=RequestMethod.POST)
	public JSONObject getRoleCombobox(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parentISNull", "true");
			List<Role> roleList = roleService.selectRoleByCombobox(map);
			json.put("roleListCombobox", roleList);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Role-AJAX-/back/getRoleCombobox-加载角色combobox出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRoleArray", method=RequestMethod.POST)
	public JSONArray getRoleArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			//Map<String,Object> map = new HashMap<String,Object>();
			List<Role> roleList = roleService.selectRoleByCombobox(null);
			for(Role r : roleList){
				JSONObject json = new JSONObject();
				json.put("id", r.getId());
				json.put("name", r.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Dict-AJAX-/back/getArticleTypeArray-加载角色列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRoleDelete",method=RequestMethod.POST)
	public JSONObject updateRoleDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的role，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.role);
			map.put("username", admin.getUsername());
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Role-AJAX-/back/updateRoleDelete-逻辑删除角色出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRoleRecover",method=RequestMethod.POST)
	public JSONObject updateRoleRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的role，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.role);
			map.put("username", admin.getUsername());
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Role-AJAX-/back/updateRoleRecover-恢复角色出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteRoleThorough",method=RequestMethod.POST)
	public JSONObject deleteRoleThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的role，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.role);
			map.put("username",admin.getUsername());
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Role-AJAX-/back/deleteRoleThorough-物理删除角色出现异常", json);
		}
		return json;
	}
	
}
