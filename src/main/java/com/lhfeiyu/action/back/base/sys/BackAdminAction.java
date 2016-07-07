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

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.AdminService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackAdminAction {
	
	@Autowired
	private AdminService  adminService;
	@Autowired
	private AA_UtilService  utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/admin")
	public ModelAndView  admin(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backAdmin;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "管理员页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Admin-PAGE-/back/admin-加载管理员出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAdminList", method=RequestMethod.POST)
	public JSONObject getAdminList(HttpServletRequest request) {
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
			List<Admin> adminList = adminService.selectListByCondition(map);
			Integer total = adminService.selectCountByCondition(map);
			Result.gridData(adminList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Admin-AJAX-/back/getAdminList-加载管理员列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateAdmin", method = RequestMethod.POST)
	public JSONObject addOrUpdateAdmin(@ModelAttribute Admin admin,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin1 = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin1)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin1.getUsername();
			String phone = admin.getPhone();
			String email = admin.getEmail();
			if(Check.isNull(phone)){
				return Result.failure(json, "请输入手机号码", "phone_null");
			}
			if(Check.isNull(email)){
				return Result.failure(json, "请输入电子邮箱", "email_null");
			}
			Integer adminId = admin.getId();
			if(null != adminId){
				boolean checkPhone = true;
				boolean checkEmail = true;
				Admin oldAdmin = adminService.selectByPrimaryKey(adminId);
				if(null != oldAdmin.getPhone() && phone.equals(oldAdmin.getPhone())){
					checkPhone = false;
				}
				if(null != oldAdmin.getEmail() && email.equals(oldAdmin.getEmail())){
					checkEmail = false;
				}
				
				Map<String, Object> map = CommonGenerator.getHashMap();
				if(checkPhone){
					map.put("phoneCheck", phone);
					int count = adminService.selectCountByCondition(map);
					if(count > 0){
						return Result.failure(json, "手机号码已存在，请重新输入", "phone_repeat");
					}
				}
				if(checkEmail){
					map.clear();
					map.put("emailCheck", email);
					int count2 = adminService.selectCountByCondition(map);
					if(count2 > 0){
						return Result.failure(json, "电子邮箱已存在，请重新输入", "email_repeat");
					}
				}
			}
			if(null == adminId){//添加
				admin.setPassword(Md5Util.encrypt("admin6688"));
				if(Check.isNotNull(admin.getPasswordReset())){
					admin.setPassword(Md5Util.encrypt(admin.getPasswordReset()));
				}
				admin.setCreatedAt(date);
				admin.setCreatedBy(username);
				adminService.insert(admin);
			}else{//修改
				if(Check.isNotNull(admin.getPasswordReset())){
					admin.setPassword(Md5Util.encrypt(admin.getPasswordReset()));
				}
				admin.setUpdatedAt(date);
				admin.setUpdatedBy(username);
				adminService.updateByPrimaryKeySelective(admin);
			}
			json.put("id", admin.getId());
			Result.success(json, "添加或修改管理员成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Admin-AJAX-/back/addOrUpdateAdmin-新增或修改管理员出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateAdminPassword")
	public JSONObject updateAdminPassword(HttpServletRequest request, @RequestParam String oldPsd, @RequestParam String newPsd) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(Check.isNull(oldPsd) || Check.isNull(newPsd)){
				return Result.failure(json, "请输入旧密码和新密码", "password_null");
			}
			oldPsd = Md5Util.encrypt(oldPsd);
			Admin db_admin = adminService.selectByPrimaryKey(admin.getId());
			if(db_admin.getPassword().equals(oldPsd)){
				db_admin.setPassword(Md5Util.encrypt(newPsd));
				adminService.updateByPrimaryKeySelective(db_admin);
				Result.success(json, "密码修改成功", null);
			}else{
				Result.failure(json, "旧密码不正确", "oldPsd_wrong");
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Admin-AJAX-/back/updateAdminPassword-后台修改管理员密码出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateAdminDelete",method=RequestMethod.POST)
	public JSONObject updateAdminDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的admin，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.admin);
			map.put("username", admin.getUsername());
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Admin-AJAX-/back/updateAdminDelete-删除管理员出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateAdminRecover",method=RequestMethod.POST)
	public JSONObject updateAdminRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的admin，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.admin);
			map.put("username", admin.getUsername());
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Admin-AJAX-/back/updateAdminRecover-恢复管理员出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAdminThorough",method=RequestMethod.POST)
	public JSONObject deleteAdminThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的admin，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.admin);
			map.put("username",admin.getUsername());
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Admin-AJAX-/back/deleteAdminThorough-彻底删除管理员出现异常", json);
		}
		return json;
	}
	
}
