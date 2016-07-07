package com.lhfeiyu.action.back.domain.hospital;

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
import com.lhfeiyu.config.AssetsPath;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.config.Table;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.PictureService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackHospitalAction {
	
	@Autowired
	private HospitalService  hospitalService;
	@Autowired
	private AA_UtilService  utilService;
	@Autowired
	private PictureService  pictureService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/hospital")
	public ModelAndView  hospital(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backHospital;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "诊所页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Hospital-PAGE-/back/hospital-加载诊所出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getHospitalList", method=RequestMethod.POST)
	public JSONObject getHospitalList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "whole_name");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "whole_name");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "province");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "province");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("5")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("6")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<Hospital> hospitalList = hospitalService.selectListByCondition(map);
			Integer total = hospitalService.selectCountByCondition(map);
			Result.gridData(hospitalList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/back/getHospitalList-加载诊所列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getHospitalArray", method=RequestMethod.POST)
	public JSONArray getHospitalArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<Hospital> hospitalList = hospitalService.selectListByCondition(map);
			for(Hospital h:hospitalList){
				JSONObject json = new JSONObject();
				json.put("id",h.getId());
				json.put("name",h.getWholeName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/back/getHospitalArray-加载诊所数组列表出现异常", array);
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateHospital", method = RequestMethod.POST)
	public JSONObject addOrUpdateHospital(@ModelAttribute Hospital hospital,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(Check.isNull(hospital.getLogo())){
				hospital.setLogo(AssetsPath.defaultHospitalLogo);
			}
			if(null == hospital.getId()){//添加
				hospital.setCreatedAt(date);
				hospital.setCreatedBy(username);
				hospitalService.insert(hospital);
			}else{//修改
				Hospital dbHospital = hospitalService.selectByPrimaryKey(hospital.getId());
				String newAvatar = hospital.getLogo();
				String oldAvatar = dbHospital.getLogo();
				if(Check.isNotNull(newAvatar) && Check.isNotNull(oldAvatar) && !newAvatar.equals(oldAvatar)){//路径不相等，删除之前的头像
					Integer avatarPicId = dbHospital.getLogoPicId();
					if(Check.isNotNull(avatarPicId)){
						pictureService.deleteByPrimaryKey(avatarPicId);
					}
				}
				hospital.setUpdatedAt(date);
				hospital.setUpdatedBy(username);
				hospitalService.updateByPrimaryKeySelective(hospital);
			}
			json.put("id", hospital.getId());
			Result.success(json, "添加或修改诊所成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/back/addOrUpdateHospital-新增或修改诊所出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateHospitalDelete",method=RequestMethod.POST)
	public JSONObject updateHospitalDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.hospital);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/back/updateHospitalDelete-删除诊所出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateHospitalRecover",method=RequestMethod.POST)
	public JSONObject updateHospitalRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.hospital);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/back/updateHospitalRecover-恢复诊所出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteHospitalThorough",method=RequestMethod.POST)
	public JSONObject deleteHospitalThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.hospital);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/back/deleteHospitalThorough-彻底删除诊所出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateHospitalPassword",method=RequestMethod.POST)
	public JSONObject updateUserPassword(HttpServletRequest request,@RequestParam Integer id,
			@RequestParam String password) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("expression1","password = '"+Md5Util.encrypt(password)+"'");
			hospitalService.updateFieldById(map);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Hospital-AJAX-/back/updateHospitalPassword-修改诊所密码出现异常", json);
		}
		return json;
	}
	
}
