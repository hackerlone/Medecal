package com.lhfeiyu.action.back.base.picture;

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
import com.lhfeiyu.po.Picture;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.PictureService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackPictureAction {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private AA_UtilService utilService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/picture")
	public ModelAndView picture(ModelMap modelMap,HttpServletRequest request ,@RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backPicture;
		try{
			modelMap.put("typeId", typeId);
			Result.success(modelMap, "图片页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Picture-PAGE-/back/picture-加载图片出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/addOrUpdatePicturePage")
	public ModelAndView  addOrUpdatePicturePage(ModelMap modelMap,HttpSession session,
			@RequestParam(value="pictureId",required=false) Integer pictureId){
		String path = PagePath.backAddOrUpdatePicture;
		try{
			if(null != pictureId && !"".equals(pictureId)){
				Picture picture = pictureService.selectByPrimaryKey(pictureId);
				if(null != picture && !"".equals(picture)){
					modelMap.put("picture", picture);
					JSONObject obj = new JSONObject();
					obj.put("picture", picture);
					modelMap.put("pictureJson", obj.toJSONString());
					modelMap.put("pictureId", pictureId);
					Result.success(modelMap, "数据加载成功", null);
				}else{
					Result.failure(modelMap, "无法查询到您所需要的数据", null);
				}
			}
			Result.success(modelMap, "添加或修改文章页面加载成功", null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Picture-PAGE-/back/picture-加载添加或修改图片页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getPictureList", method=RequestMethod.POST)
	public JSONObject getPictureList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "created_at");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "title");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "title");
					map.put("ascOrdesc", "DESC");
				}
			}
			List<Picture> pictureList = pictureService.selectListByCondition(map);
			Integer total = pictureService.selectCountByCondition(map);
			Result.gridData(pictureList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Picture-AJAX-/back/getPictureList-加载图片列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdatePicture", method = RequestMethod.POST)
	public JSONObject addOrUpdatePicture(@ModelAttribute Picture picture,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			Date date = new Date();
			String username = admin.getUsername();
			if(null == picture.getId()){//添加
				picture.setCreatedAt(date);
				picture.setCreatedBy(username);
				pictureService.insert(picture);
			}else{//修改
				picture.setUpdatedAt(date);
				picture.setUpdatedBy(username);
				pictureService.updateByPrimaryKeySelective(picture);
			}
			json.put("id", picture.getId());
			Result.success(json, "添加或修改图片成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Picture-AJAX-/back/addOrUpdatePicture-新增或修改图片出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updatePictureDelete",method=RequestMethod.POST)
	public JSONObject updatePictureDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.picture);
			map.put("username", username);
			utilService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Picture-AJAX-/back/updatePictureDelete-删除图片出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePictureRecover",method=RequestMethod.POST)
	public JSONObject updatePictureRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.picture);
			map.put("username", username);
			utilService.updateDeletedNullByIds(map);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Picture-AJAX-/back/updatePictureRecover-恢复图片出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletePictureThorough",method=RequestMethod.POST)
	public JSONObject deletePictureThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			String username = admin.getUsername();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			map.put("table", Table.picture);
			map.put("username",username);
			utilService.deleteByIds(map);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Picture-AJAX-/back/deletePictureThorough-彻底删除图片出现异常", json);
		}
		return json;
	}
	
}
