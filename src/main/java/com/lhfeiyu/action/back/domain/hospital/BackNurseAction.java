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
import com.lhfeiyu.po.Nurse;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.NurseService;
import com.lhfeiyu.service.PictureService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackNurseAction {
	
	@Autowired
	private NurseService  nurseService;
	@Autowired
	private AA_UtilService  utilService;
	@Autowired
	private PictureService  pictureService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/nurse")
	public ModelAndView  nurse(ModelMap modelMap, @RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backNurse;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "护士页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Nurse-PAGE-/back/nurse-加载护士出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getNurseList", method=RequestMethod.POST)
	public JSONObject getNurseList(HttpServletRequest request) {
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
			List<Nurse> nurseList = nurseService.selectListByCondition(map);
			Integer total = nurseService.selectCountByCondition(map);
			Result.gridData(nurseList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Nurse-AJAX-/back/getNurseList-加载护士列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getNurseArray", method=RequestMethod.POST)
	public JSONArray getNurseArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<Nurse> nurseList = nurseService.selectListByCondition(map);
			for(Nurse h:nurseList){
				JSONObject json = new JSONObject();
				json.put("id",h.getId());
				json.put("name",h.getUsername());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Nurse-AJAX-/back/getNurseArray-加载用户数组列表出现异常", array);
		}
		return array;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateNurse", method = RequestMethod.POST)
	public JSONObject addOrUpdateNurse(@ModelAttribute Nurse nurse,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(Check.isNull(nurse.getAvatar())){
				nurse.setAvatar(AssetsPath.defaultNurseAvatar);
			}
			if(null == nurse.getId()){//添加
				nurse.setPassword(Md5Util.encrypt(nurse.getPassword()));
				nurse.setCreatedAt(date);
				nurse.setCreatedBy(username);
				nurseService.insert(nurse);
			}else{//修改
				Nurse dbNurse = nurseService.selectByPrimaryKey(nurse.getId());
				String newAvatar = nurse.getAvatar();
				String oldAvatar = dbNurse.getAvatar();
				if(Check.isNotNull(newAvatar) && Check.isNotNull(oldAvatar) && !newAvatar.equals(oldAvatar)){//路径不相等，删除之前的头像
					Integer avatarPicId = dbNurse.getAvatarPicId();
					if(Check.isNotNull(avatarPicId)){
						pictureService.deleteByPrimaryKey(avatarPicId);
					}
				}
				nurse.setUpdatedAt(date);
				nurse.setUpdatedBy(username);
				nurseService.updateByPrimaryKeySelective(nurse);
			}
			json.put("id", nurse.getId());
			Result.success(json, "添加或修改护士成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Nurse-AJAX-/back/addOrUpdateNurse-新增或修改护士出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateNurseDelete",method=RequestMethod.POST)
	public JSONObject updateNurseDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.nurse);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Nurse-AJAX-/back/updateNurseDelete-删除护士出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateNurseRecover",method=RequestMethod.POST)
	public JSONObject updateNurseRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.nurse);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Nurse-AJAX-/back/updateNurseRecover-恢复护士出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteNurseThorough",method=RequestMethod.POST)
	public JSONObject deleteNurseThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.nurse);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Nurse-AJAX-/back/deleteNurseThorough-彻底删除护士出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateNursePassword",method=RequestMethod.POST)
	public JSONObject updateUserPassword(HttpServletRequest request,@RequestParam Integer id,
			@RequestParam String password) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("expression1","password = '"+Md5Util.encrypt(password)+"'");
			nurseService.updateFieldById(map);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Nurse-AJAX-/back/updateNursePassword-修改护士密码出现异常", json);
		}
		return json;
	}
	
	
}
