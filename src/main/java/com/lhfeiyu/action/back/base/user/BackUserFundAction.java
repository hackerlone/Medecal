package com.lhfeiyu.action.back.base.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.UserFund;
import com.lhfeiyu.service.UserFundService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackUserFundAction {
	@Autowired
	private UserFundService  userFundService;
	private static Logger logger =  Logger.getLogger("R");
	
	
	@RequestMapping(value="/userFund")
	public ModelAndView userCustomer(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.userFund;
		try{
		}catch(Exception e){
			e.printStackTrace();
			path = PagePath.error;
			logger.error("LH_ERROR_加载用户资金页面出现异常_"+e.getMessage());
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateUserFund", method = RequestMethod.POST)
	public JSONObject addOrUpdateUserFund(@ModelAttribute UserFund userFund,HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Date d = new Date();
			if(null != userFund.getId()){//更新
				userFund.setUpdatedAt(d); 
				userFund.setUpdatedBy(admin.getUsername());
				userFundService.updateMoneyById(userFund);
			}else{//新增
				userFund.setCreatedAt(d); 
				userFund.setCreatedBy(admin.getUsername());
				userFundService.insert(userFund);
			}
			json.put("status", "success");
			json.put("msg", "操作成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_新增修改用户资金出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserFundList",method=RequestMethod.POST)
	public JSONObject getUserList(HttpServletRequest request,HttpServletResponse response) {
		List<UserFund> userFundList = null;
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			//sc_order格式例子：id___asc,created_at___desc,如果传递了request,则可自动获取分页参数
			map = Pagination.getOrderByAndPage(map,request);
			userFundList = userFundService.selectListByCondition(map);
			Integer total = userFundService.selectCountByCondition(map);
			json.put("rows", userFundList);
			json.put("total", total);
			json.put("status", "success");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_获取用户信息出现异常_", json);
		}
		return json;
	}
	
}
