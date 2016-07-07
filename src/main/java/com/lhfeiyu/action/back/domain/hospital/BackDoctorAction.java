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
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.PictureService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackDoctorAction {
	
	@Autowired
	private DoctorService  doctorService;
	@Autowired
	private AA_UtilService  utilService;
	@Autowired
	private PictureService  pictureService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/doctor")
	public ModelAndView  doctor(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backDoctor;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "医生页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/back/doctor-加载医生出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDoctorList", method=RequestMethod.POST)
	public JSONObject getDoctorList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "username");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "username");
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
			List<Doctor> doctorList = doctorService.selectListByCondition(map);
			Integer total = doctorService.selectCountByCondition(map);
			Result.gridData(doctorList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/back/getDoctorList-加载医生列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDoctorArray", method=RequestMethod.POST)
	public JSONArray getDoctorArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<Doctor> doctorList = doctorService.selectListByCondition(map);
			for(Doctor h:doctorList){
				JSONObject json = new JSONObject();
				json.put("id",h.getId());
				json.put("name",h.getUsername());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/back/getDoctorArray-加载用户数组列表出现异常", array);
		}
		return array;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateDoctor", method = RequestMethod.POST)
	public JSONObject addOrUpdateDoctor(@ModelAttribute Doctor doctor,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(Check.isNull(doctor.getAvatar())){
				doctor.setAvatar(AssetsPath.defaultDoctorAvatar);
			}
			if(null == doctor.getId()){//添加
				doctor.setPassword(Md5Util.encrypt(doctor.getPassword()));
				doctor.setCreatedAt(date);
				doctor.setCreatedBy(username);
				doctorService.insert(doctor);
			}else{//修改
				Doctor dbDoctor = doctorService.selectByPrimaryKey(doctor.getId());
				String newAvatar = doctor.getAvatar();
				String oldAvatar = dbDoctor.getAvatar();
				if(Check.isNotNull(newAvatar) && Check.isNotNull(oldAvatar) && !newAvatar.equals(oldAvatar)){//路径不相等，删除之前的头像
					Integer avatarPicId = dbDoctor.getAvatarPicId();
					if(Check.isNotNull(avatarPicId)){
						pictureService.deleteByPrimaryKey(avatarPicId);
					}
				}
				doctor.setUpdatedAt(date);
				doctor.setUpdatedBy(username);
				doctorService.updateByPrimaryKeySelective(doctor);
			}
			json.put("id", doctor.getId());
			Result.success(json, "添加或修改医生成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/back/addOrUpdateDoctor-新增或修改医生出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateDoctorDelete",method=RequestMethod.POST)
	public JSONObject updateDoctorDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.doctor);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/back/updateDoctorDelete-删除医生出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDoctorRecover",method=RequestMethod.POST)
	public JSONObject updateDoctorRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.doctor);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/back/updateDoctorRecover-恢复医生出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteDoctorThorough",method=RequestMethod.POST)
	public JSONObject deleteDoctorThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.doctor);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/back/deleteDoctorThorough-彻底删除医生出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDoctorPassword",method=RequestMethod.POST)
	public JSONObject updateUserPassword(HttpServletRequest request,@RequestParam Integer id,
			@RequestParam String password) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("expression1","password = '"+Md5Util.encrypt(password)+"'");
			doctorService.updateFieldById(map);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/back/updateDoctorPassword-修改医生密码出现异常", json);
		}
		return json;
	}
	
	
}
