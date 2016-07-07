package com.lhfeiyu.action.back.base.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.tools.ActionUtil;

@Controller
public class MainAction {
	
	@RequestMapping(value = "/back/main", method = RequestMethod.GET)
	public ModelAndView backMain(ModelMap modelMap,HttpServletRequest request) {
		try{
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			modelMap.put("admin", admin);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ModelAndView(PagePath.backMain);
	}
	
}
