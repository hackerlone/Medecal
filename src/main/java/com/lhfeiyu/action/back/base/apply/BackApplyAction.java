package com.lhfeiyu.action.back.base.apply;

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
import com.lhfeiyu.po.Apply;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.ApplyService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackApplyAction {
	@Autowired
	private ApplyService applyService;
	@Autowired
	private AA_UtilService utilService;

	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/apply")
	public ModelAndView  apply(ModelMap modelMap,HttpSession session){
		String path = PagePath.backApply;
		try{
		}catch(Exception e){
			e.printStackTrace();
			path = PagePath.error;
			logger.error("LH_ERROR_加载申请处理页面出现异常_"+e.getMessage());
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateApply")
	public JSONObject addOrUpdateApply(@ModelAttribute Apply apply,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == apply.getId()){//添加
				apply.setCreatedAt(new Date());
				apply.setCreatedBy(admin.getUsername());
				applyService.insert(apply);
			}else{//修改
				//Apply apply_db = applyService.selectByPrimaryKey(apply.getId());

				json.put("status", "success");
				json.put("id",apply.getId());
				json.put("msg", "操作成功");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "操作失败");
			Result.catchError(e, logger, "LH_ERROR_添加或修改申请处理出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getApplyList",method=RequestMethod.POST)
	public JSONObject getApplyList(HttpServletRequest request) {
		List<Apply> applyList = null;
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			//sc_order格式例子：id___asc,created_at___desc,如果传递了request,则可自动获取分页参数
			map = Pagination.getOrderByAndPage(map,request);
			applyList = applyService.selectListByCondition(map);
			Integer total = applyService.selectCountByCondition(map);
			json.put("rows", applyList);
			json.put("total", total);
			json.put("status", "success");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_加载申请处理列表出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateApplyDelete",method=RequestMethod.POST)
	public JSONObject updateApplyDelete(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.apply);
			map.put("username",admin.getUsername());
			utilService.updateDeletedNowByIds(map);
			json.put("status","success");
			json.put("msg","删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_删除申请出现异常_", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deleteApplyThorough",method=RequestMethod.POST)
	public JSONObject deleteApplyThorough(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.apply);
			map.put("username",admin.getUsername());
			utilService.deleteByIds(map);
			json.put("status","success");
			json.put("msg","彻底删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_彻底删除申请出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateApplyRecover",method=RequestMethod.POST)
	public JSONObject updateApplyRecover(HttpServletRequest request,
			@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.apply);
			map.put("username",admin.getUsername());
			utilService.updateDeletedNullByIds(map);
			json.put("status","success");
			json.put("msg","恢复成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_恢复申请出现异常_", json);
		}
		return json;
	}
	
	
}
