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
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Menu;
import com.lhfeiyu.po.RoleMenu;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.MenuService;
import com.lhfeiyu.service.RoleMenuService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackRoleMenuAction {
	
	@Autowired
	private RoleMenuService  roleMenuService;
	@Autowired
	private MenuService  menuService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/roleMenu")
	public ModelAndView  roleMenu(ModelMap modelMap,HttpSession session,
			@RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backRoleMenu;
		try{
			modelMap.put("typeId", typeId);
		}catch(Exception e){
			e.printStackTrace();
			path = PagePath.error;
			logger.error("LH_ERROR_加载快捷菜单页面出现异常_"+e.getMessage());
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateRoleMenu", method = RequestMethod.POST)
	public JSONObject addOrUpdateRoleMenu(@ModelAttribute RoleMenu roleMenu,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == roleMenu.getId()){//添加
				roleMenu.setMainStatus(2);
				roleMenu.setCreatedAt(new Date());
				roleMenu.setCreatedBy(admin.getUsername());
				roleMenuService.insert(roleMenu);
			}else{//修改
				roleMenu.setUpdatedAt(new Date());
				roleMenu.setUpdatedBy(admin.getUsername());
				roleMenuService.updateByPrimaryKeySelective(roleMenu);
			}
			json.put("status", "success");
			json.put("id",roleMenu.getId());
			json.put("msg", "操作成功");
		}catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "操作失败");
			Result.catchError(e, logger, "LH_ERROR_添加或修改快捷菜单出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRoleMenuList",method=RequestMethod.POST)
	public JSONObject getRoleMenuList(HttpServletRequest request) {
		List<RoleMenu> roleMenuList = null;
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			//sc_order格式例子：id___asc,created_at___desc,如果传递了request,则可自动获取分页参数
			map = Pagination.getOrderByAndPage(map,request);
			roleMenuList = roleMenuService.selectListByCondition(map);
			Integer total = roleMenuService.selectCountByCondition(map);
			json.put("rows", roleMenuList);
			json.put("total", total);
			json.put("status", "success");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_加载快捷菜单列表出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMenuListOfCurrentUser",method=RequestMethod.POST)
	public JSONObject getMenuListOfCurrentUser(HttpServletRequest request) {
		List<Menu> menuList = null;
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin){
				return Result.adminSessionInvalid(json);
			}
			Integer roleId = admin.getRoleId();
			if(null == roleId){
				return Result.failure(json, "请选择对应角色", "role_null");
			}
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			if(roleId != 1)map.put("roleId", roleId);//1：超级管理加载所有菜单
			
			menuList = menuService.selectListByCondition(map);
			json.put("menuList", menuList);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_加载当前登陆用户菜单列表出现异常_", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMenuListOfRole",method=RequestMethod.POST)
	public JSONObject getMenuListOfRole(HttpServletRequest request,
			@RequestParam Integer roleId) {
		List<Menu> menuList = null;
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin){
				return Result.adminSessionInvalid(json);
			}
			if(null == roleId){
				return Result.failure(json, "请选择对应角色", "role_null");
			}
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("roleId", roleId);
			map.put("orderBy", "id");
			map.put("ascOrdesc", "asc");
			menuList = menuService.selectListByCondition(map);
			json.put("menuList", menuList);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_加载指定角色的菜单列表出现异常_", json);
		}
		return Result.success(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRoleMenuDelete",method=RequestMethod.POST)
	public JSONObject updateRoleMenuDelete(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.quick_menu);
			map.put("username",admin.getUsername());
			utilService.updateDeletedNowByIds(map);
			json.put("status","success");
			json.put("msg","删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_删除快捷菜单出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteRoleMenuThorough",method=RequestMethod.POST)
	public JSONObject deleteRoleMenuThorough(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.quick_menu);
			map.put("username",admin.getUsername());
			utilService.deleteByIds(map);
			json.put("status","success");
			json.put("msg","彻底删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_彻底删除快捷菜单出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRoleMenuRecover",method=RequestMethod.POST)
	public JSONObject updateRoleMenuRecover(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.role_menu);
			map.put("username",admin.getUsername());
			utilService.updateDeletedNullByIds(map);
			json.put("status","success");
			json.put("msg","恢复成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_恢复快捷菜单出现异常_", json);
		}
		return json;
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "/getMenu")
	public JSONArray getMenu(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			List<Menu> dictList = menuService.selectListByCondition(null);
			for(Menu m:dictList){
				JSONObject json = new JSONObject();
				json.put("id", m.getId());
				json.put("name", m.getName());
				array.add(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}*/
	
}
