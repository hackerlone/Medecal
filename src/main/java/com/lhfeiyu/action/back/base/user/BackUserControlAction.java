package com.lhfeiyu.action.back.base.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.lhfeiyu.po.UserControl;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.UserControlService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;
@Controller
@RequestMapping(value = "/back")
public class BackUserControlAction {
	@Autowired
	private UserControlService userControlService;
	@Autowired
	private AA_UtilService utilService;
	@Autowired
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value = "/userControl", method = RequestMethod.GET)
	public ModelAndView userControl(ModelMap modelMap,HttpServletRequest request) {
		return new ModelAndView(PagePath.backUserControl);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserControlList",method=RequestMethod.POST)
	public JSONObject getUserControlList(HttpServletRequest request,HttpServletResponse response) {
		List<UserControl> userControlList = null;
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			//sc_order格式例子：id___asc,created_at___desc,如果传递了request,则可自动获取分页参数
			map = Pagination.getOrderByAndPage(map,request);
			userControlList = userControlService.selectListByCondition(map);
			Integer total = userControlService.selectCountByCondition(map);
			json.put("rows", userControlList);
			json.put("total", total);
			json.put("status", "success");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_获取用户控制信息出现异常_", json);
		}
		return json;
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/getUserControl")
	public JSONArray getUserControl(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			List<UserControl> userControlList = userControlService.selectListByCondition(null);
			for(UserControl u : userControlList){
				JSONObject json = new JSONObject();
				json.put("id",u.getId());
				json.put("name",u.getUsername());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_获取用户控制信息出现异常_", array);
		}
		return array;
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateUserControl", method = RequestMethod.POST)
	public JSONObject addOrUpdateUserControl(@ModelAttribute UserControl userControl,HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			//Admin admin = (Admin) request.getSession().getAttribute("admin");
			//user.setUpdatedBy(admin.getUsername());
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Date d = new Date();
			if(null != userControl.getId()){//更新
				userControl.setUpdatedAt(d); 
				userControl.setUpdatedBy(admin.getUsername());
				userControlService.updateByPrimaryKeySelective(userControl);
			}else{//新增
				userControl.setCreatedAt(d); 
				userControl.setCreatedBy(admin.getUsername());
				userControlService.insert(userControl);
			}
			json.put("status", "success");
			json.put("msg", "操作成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_新增修改用户控制信息出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateUserControlDelete",method=RequestMethod.POST)
	public JSONObject updateUserControlDelete(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			//Admin admin = (Admin) request.getSession().getAttribute("admin");
			Map<String,Object> map = new HashMap<String,Object>();
			//Table.admin
			map.put("table", Table.user_control);
			map.put("ids", ids);
			//map.put("username",user.getUsername());
			utilService.updateDeletedNowByIds(map);
			json.put("status","success");
			json.put("msg","删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_删除用户出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteUserControlThrough",method=RequestMethod.POST)
	public JSONObject deleteUserControlThrough(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			//Admin admin = (Admin) request.getSession().getAttribute("admin");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", Table.user_control);
			map.put("ids", ids);
			utilService.deleteByIds(map);
			json.put("status","success");
			json.put("msg","彻底删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_彻底删除用户出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateUserControlRecover",method=RequestMethod.POST)
	public JSONObject updateUserControlRecover(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			//Admin admin = (Admin) request.getSession().getAttribute("admin");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", Table.user_control);
			map.put("ids", ids);
			//map.put("username",user.getUsername());
			utilService.updateDeletedNullByIds(map);
			json.put("status","success");
			json.put("msg","恢复成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_恢复用户出现异常_", json);
		}
		return json;
	}
	
}
