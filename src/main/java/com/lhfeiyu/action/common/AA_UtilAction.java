package com.lhfeiyu.action.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.tools.Result;

@Controller
public class AA_UtilAction {
	
	@Autowired
	private AA_UtilService service;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value = "/back/pages/{pageName}.html", method = RequestMethod.GET)
	public ModelAndView backCommonPage1(@PathVariable String pageName) {
		return new ModelAndView("/back/"+pageName+"/"+pageName);
	}
	
	@RequestMapping(value = "/back/pages/{folderName}/{pageName}.html", method = RequestMethod.GET)
	public ModelAndView backCommonPage2(@PathVariable String folderName,@PathVariable String pageName) {
		return new ModelAndView("/back/"+folderName+"/"+pageName);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	public JSONObject deleteById(@RequestParam("id") Integer id,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("id", id);
			map.put("username", "admin");
			service.deleteById(map);
			json.put("status", "success");
			json.put("msg", "强制删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_删除表："+table+",ID："+id+" 出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
	public JSONObject deleteByIds(@RequestParam("ids") String ids,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("ids", ids);
			map.put("username", "admin");
			service.deleteByIds(map);
			json.put("status", "success");
			json.put("msg", "强制删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_删除表："+table+",ID串："+ids+" 出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDeletedNowById", method = RequestMethod.POST)
	public JSONObject updateDeletedNowById(@RequestParam("id") Integer id,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("id", id);
			map.put("username", "admin");
			service.updateDeletedNowById(map);
			json.put("status", "success");
			json.put("msg", "删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_更新删除标识-删除，表："+table+",ID："+id+" 出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDeletedNowByIds", method = RequestMethod.POST)
	public JSONObject updateDeletedNowByIds(@RequestParam("ids") String ids,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("ids", ids);
			map.put("username", "admin");
			service.updateDeletedNowByIds(map);
			json.put("status", "success");
			json.put("msg", "删除成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_更新删除标识-删除，表："+table+",ID串："+ids+" 出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDeletedNullById", method = RequestMethod.POST)
	public JSONObject updateDeletedNullById(@RequestParam("id") Integer id,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("id", id);
			map.put("username", "admin");
			service.updateDeletedNullById(map);
			json.put("status", "success");
			json.put("msg", "恢复成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_更新删除标识-恢复，表："+table+",ID："+id+" 出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDeletedNullByIds", method = RequestMethod.POST)
	public JSONObject updateDeletedNullByIds(@RequestParam("ids") String ids,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("ids", ids);
			map.put("username", "admin");
			service.updateDeletedNullByIds(map);
			json.put("status", "success");
			json.put("msg", "恢复成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_更新删除标识-恢复，表："+table+",ID串："+ids+" 出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMainStatusById", method = RequestMethod.POST)
	public JSONObject updateMainStatusById(@RequestParam("id") Integer id,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("id", id);
			map.put("username", "admin");
			service.updateMainStatusById(map);
			json.put("status", "success");
			json.put("msg", "更新成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_更新业务状态，表："+table+",ID："+id+" 出现异常_", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMainStatusByIds", method = RequestMethod.POST)
	public JSONObject updateMainStatusByIds(@RequestParam("ids") String ids,@RequestParam("table") String table) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table", table);
			map.put("ids", ids);
			map.put("username", "admin");
			service.updateMainStatusByIds(map);
			json.put("status", "success");
			json.put("msg", "更新成功");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_更新业务状态，表："+table+",ID串："+ids+" 出现异常_", json);
		}
		return json;
	}
	
	@RequestMapping(value = "/jumpToLogin")
	public ModelAndView jumpToLogin() {
		return new ModelAndView(PagePath.jumpToLogin, null);
	}
	
	@RequestMapping(value = "/jumpToBackLogin")
	public ModelAndView jumpToBackLogin() {
		return new ModelAndView(PagePath.jumpToBackLogin, null);
	}
	
	@RequestMapping(value = "*")
	public ModelAndView page404() {
		 return new ModelAndView("redirect:/index");
	}

}
