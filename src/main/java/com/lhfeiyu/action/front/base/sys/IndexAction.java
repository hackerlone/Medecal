package com.lhfeiyu.action.front.base.sys;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.IndexService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Result;

@Controller
public class IndexAction {
	
	@Autowired
	private IndexService indexService;
	
	private static Logger logger = Logger.getLogger("R");
	
	/** 主页面  */
	@RequestMapping(value={"/","/index"}, method = RequestMethod.GET)
	public ModelAndView index(ModelMap modelMap,HttpSession session,HttpServletRequest request,
		   @RequestParam(required=false) String path){
		try{
			User user = ActionUtil.checkSession4User(session);
			if(null != user){
				modelMap.put("user", user);
			}
			modelMap = indexService.getData(modelMap,request);
			if(null != path){
				modelMap.put("path", path);
			}
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Index-PAGE-/index-加载首页出现异常", modelMap);
		}
		return new ModelAndView(PagePath.frontIndex,modelMap);
	}
	
	/** 测试页面  */
	@RequestMapping(value={"/test"}, method = RequestMethod.GET)
	public ModelAndView test(ModelMap modelMap,HttpServletRequest request){
		return new ModelAndView(PagePath.test, modelMap);
	}
	
	@RequestMapping(value="/searchDoctorOrHospitalList/{typeId}")
	public ModelAndView searchDoctorOrHospitalList(ModelMap modelMap,HttpSession session,HttpServletRequest request,
			@RequestParam(required=false) String searchName,
			@PathVariable Integer typeId){
		String path = PagePath.searchDoctorOrHospitalList;
		try{
			modelMap = indexService.getData(modelMap,request);
			if(null != searchName){
				modelMap.put("searchName", searchName);
			}
			modelMap.put("searchTypeId", typeId);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Index-PAGE-/searchDoctorOrHospitalList-加载查询医生或诊所出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	
}