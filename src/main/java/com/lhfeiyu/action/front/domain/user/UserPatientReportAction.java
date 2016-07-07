package com.lhfeiyu.action.front.domain.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.PatientReport;
import com.lhfeiyu.po.PatientReportDetail;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.service.PatientReportDetailService;
import com.lhfeiyu.service.PatientReportService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class UserPatientReportAction {
	
	@Autowired
	private UserService userService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private PatientReportService patientReportService;
	@Autowired
	private PatientReportDetailService patientReportDetailService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/userReportDetail/{id}")
	public ModelAndView  userReportDetail(ModelMap modelMap,HttpServletRequest request,
			@PathVariable Integer id){
		String path = PagePath.userReportDetail;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			modelMap = indexService.getIntroductionAndvision(modelMap);
			if(null != id){
				PatientReport patientReport = patientReportService.selectByPrimaryKey(id);
				String barCode = patientReport.getAdiconBarcode();
				Map<String, Object> map = CommonGenerator.getHashMap();
				map.put("adiconBarcode", barCode);
				List<PatientReportDetail> prdList = patientReportDetailService.selectListByCondition(map);
				modelMap.put("prdList", prdList);
				modelMap.put("patientReport", patientReport);
			}
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-UserPatientReport-PAGE-/userReportDetail-加载用户检测报告详情页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/userReportList")
	public ModelAndView  userReportList(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.userReportList;
		try{
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(modelMap,PagePath.login);
			modelMap = userService.getUserData(modelMap,user);
			modelMap = indexService.getIntroductionAndvision(modelMap);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-UserPatientReport-PAGE-/userReportList-加载用户检测报告列表出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserReportList", method=RequestMethod.POST)
	public JSONObject getUserReportList(HttpServletRequest request,
			@RequestParam(required=false) Integer isLink) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			User session_user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == session_user)return Result.userSessionInvalid(json, "user");
			Integer userId = session_user.getId();
			/*String adiconBarcode = null;
			Map<String,Object> usermap = new HashMap<String,Object>();
			Integer relationId = session_user.getId();
			usermap.put("relationId", relationId);
			List<User> userList = userService.selectListByCondition(usermap);
			if(userList.size() > 0 && null != userList){
				adiconBarcode = "\""+session_user.getThirdId()+"\"";
				for(User u:userList){
					adiconBarcode += ","+"\""+u.getThirdId()+"\"";
				}
			}else{
				adiconBarcode = "\""+session_user.getThirdId()+"\"";
			}*/
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			//map.put("userId", session_user.getId());
			if(Check.isNotNull(isLink)){
				map.put("linkUserId", userId);
			}else{
				map.put("userId", userId);
			}
			map.put("user", 1);
			List<PatientReport> patientReportList = patientReportService.selectListByCondition(map);
			Integer total = patientReportService.selectCountByCondition(map);
			Result.gridData(patientReportList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-UserPatientReport-AJAX-/getUserReportList-加载用户检测报告列表出现异常", json);
		}
		return json;
	}
	
}
