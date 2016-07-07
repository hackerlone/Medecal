package com.lhfeiyu.action.front.domain.hospital;


import javax.servlet.http.HttpServletRequest;

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
import com.lhfeiyu.po.Announcement;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.service.AnnouncementService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Result;

@Controller
@RequestMapping(value="/hospital")
public class HospitalAnnouncementAction {
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private AnnouncementService announcementService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@RequestMapping(value = "/addOrUpdateAnnouncementForHospital")
	public ModelAndView addOrUpdateAnnouncementForHospital(ModelMap modelMap,HttpServletRequest request) {
		String path = PagePath.addOrUpdateAnnouncementForHospital;
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(modelMap,PagePath.doHspitalLogin,"hospiatl");
			modelMap = hospitalService.getHospitalData(modelMap,db_hospital,null);
		} catch (Exception e) {
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-HospitalAnnouncement-PAGE-/addOrUpdateAnnouncementForHospital-加载诊所文章添加或修改出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateAnnouncementForHospital", method = RequestMethod.POST)
	public JSONObject addOrUpdateAnnouncementForHospital(@ModelAttribute Announcement announcement,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Hospital db_hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			if(null == db_hospital)return Result.userSessionInvalid(json,"hospital");
			if(null == announcement.getId()){//添加
				announcementService.insertService(json,announcement,db_hospital);
			}else{//修改
				announcementService.updateService(json,announcement,db_hospital);
			}
			json.put("id", announcement.getId());
			Result.success(json, "添加或修改文章成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-HospitalAnnouncement-AJAX-/addOrUpdateAnnouncementForHospital-新增或修改诊所文章出现异常", json);
		}
		return json;
	}
	
	
}
