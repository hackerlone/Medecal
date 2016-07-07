package com.lhfeiyu.tools;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 工具类：返回结果 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2016年3月24日20:22:44 <p>
 * <strong> 修  改  人：</strong>  <p>
 * <strong> 修改时间：</strong>  <p>
 * <strong> 修改描述：</strong>  <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
public class Result {
	
	public static JSONObject userSessionInvalid(JSONObject json){
		return userSessionInvalid(json,"user");
	}
	
	public static ModelAndView userSessionInvalid(ModelMap modelMap, String jumpUrl){
		return userSessionInvalid(modelMap, jumpUrl, "user");
	}
	
	public static JSONObject userSessionInvalid(JSONObject json, String flag){
		json.put("flag", flag);
		json.put("toLogin", 1);
		json.put("status", "failure");
		json.put("code", "session_invalid");
		json.put("msg", "您长时间未操作，为了账户安全请重新登陆。");
		return json;
	}
	
	public static ModelAndView userSessionInvalid(ModelMap modelMap, String jumpUrl, String flag){
		String path = PagePath.login;
		if(null != jumpUrl){
			modelMap.put("flag", flag);
			modelMap.put("status", "failure");
			modelMap.put("code", "session_invalid_redirect");
			modelMap.put("jumpUrl", jumpUrl);
			path = jumpUrl;
		}
		return new ModelAndView(path, modelMap);
	}
	
	public static JSONObject adminSessionInvalid(JSONObject json){
		json.put("toLogin", 1);
		json.put("status", "failure");
		json.put("code", "session_invalid");
		json.put("msg", "会话过期，请重新登陆。");
		return json;
	}
	
	public static ModelAndView adminSessionInvalid(ModelMap modelMap, String jumpUrl){
		if(null != jumpUrl){
			modelMap.put("status", "failure");
			modelMap.put("code", "session_invalid_redirect");
			modelMap.put("jumpUrl", jumpUrl);
		}
		return new ModelAndView(PagePath.backLogin, modelMap);
	}
	
	public static <T> JSONObject gridData(List<T> dataList,Integer total,JSONObject json){
		if(Check.isLtZero(total))total = 0;
		json.put("rows", dataList);
		json.put("total", total);
		json.put("success", "success");
		json.put("status", "success");
		//json.put("recordsFiltered", null == dataList ? 0 : dataList.size());
		//json.put("recordsTotal", total);
		return json;
	}
	
	public static JSONObject success(JSONObject json){
		return success(json, "操作成功", "success_code");
	}
	
	public static JSONObject success(JSONObject json, String msg){
		return success(json, msg, "success_code");
	}
	
	public static JSONObject success(JSONObject json, String msg, String code){
		if(hasError(json) || hasSuccess(json))return json;
		json.put("success", "success");
		json.put("status", "success");
		if(!Check.isNotNull(msg))msg = "操作成功";
		if(!Check.isNotNull(code))code = "success_code";
		json.put("msg", msg);
		json.put("code", code);
		return json;
	}
	
	public static ModelMap success(ModelMap modelMap){
		return success(modelMap, "操作成功", "success_code");
	}
	
	public static ModelMap success(ModelMap modelMap, String msg){
		return success(modelMap, msg, "success_code");
	}
	
	public static ModelMap success(ModelMap modelMap, String msg, String code){
		modelMap.put("success", "success");
		modelMap.put("status", "success");
		if(!Check.isNotNull(msg))msg = "操作成功";
		if(!Check.isNotNull(code))code = "success_code";
		modelMap.put("msg", msg);
		modelMap.put("code", code);
		return modelMap;
	}
	
	public static JSONObject failure(JSONObject json, String msg, String code){
		json.put("failure", "failure");
		json.put("status", "failure");
		if(!Check.isNotNull(msg))msg = "业务逻辑处理失败";
		if(!Check.isNotNull(code))code = "error_code";
		json.put("msg", msg);
		json.put("code", code);
		return json;
	}
	
	public static ModelMap failure(ModelMap modelMap, String msg, String code){
		modelMap.put("failure", "failure");
		modelMap.put("status", "failure");
		if(!Check.isNotNull(msg))msg = "业务逻辑处理失败";
		if(!Check.isNotNull(code))code = "error_code";
		modelMap.put("msg", msg);
		modelMap.put("code", code);
		return modelMap;
	}
	
	public static ModelAndView failureToPage(ModelMap modelMap, String path, String msg, String code){
		return new ModelAndView(path, failure(modelMap, msg, code));
	}
	
	public static boolean hasError(JSONObject json){
		if(null != json && json.containsKey("failure")){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查json中是否包含成功标识：json.containsKey("success")，如果包含成功标识则返回true，否则返回false
	 * @param json
	 * @return boolean
	 */
	public static boolean hasSuccess(JSONObject json){
		if(null != json && json.containsKey("success")){
			return true;
		}
		return false;
	}
	
	public static ModelMap catchError(Exception e, Logger logger, String errorMsg, ModelMap modelMap){
		e.printStackTrace();
		logger.error(errorMsg+"-"+e.getMessage());
		modelMap.put("status", "server_error");
		modelMap.put("failure", "server_error");
		modelMap.put("msg","服务器出现异常，请联系管理员。");
		return modelMap;
	}
	
	public static JSONObject catchError(Exception e, Logger logger, String errorMsg, JSONObject json){
		e.printStackTrace();
		logger.error(errorMsg+"-"+e.getMessage());
		json.put("status", "server_error");
		json.put("failure", "server_error");
		json.put("msg","服务器出现异常，请联系管理员。");
		return json;
	}
	
	public static JSONArray catchError(Exception e, Logger logger, String errorMsg, JSONArray array){
		e.printStackTrace();
		logger.error(errorMsg+"-"+e.getMessage());
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "server_error");
		jsonObj.put("failure", "server_error");
		jsonObj.put("msg","服务器出现异常，请联系管理员。");
		array.add(jsonObj);
		return array;
	}
	
	
	/**============================================具体业务逻辑==================================================**/
	
	
}