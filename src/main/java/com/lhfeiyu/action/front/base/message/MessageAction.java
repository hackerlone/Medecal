package com.lhfeiyu.action.front.base.message;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.Message;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.MessageService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class MessageAction {
	
	@Autowired
	private MessageService  messageService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@ResponseBody
	@RequestMapping(value = "/getMessageList", method=RequestMethod.POST)
	public JSONObject getMessageList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<Message> messageList = messageService.selectListByCondition(map);
			Integer total = messageService.selectCountByCondition(map);
			Result.gridData(messageList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Message-AJAX-/getMessageList-加载留言列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateMessage", method = RequestMethod.POST)
	public JSONObject addOrUpdateMessage(@ModelAttribute Message message,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			if(null == message.getId()){//添加
				messageService.insertService(json,message,user);
			}else{//修改
				messageService.updateService(json,message,user);
			}
			json.put("id", message.getId());
			Result.success(json, "添加或修改留言成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Message-AJAX-/addOrUpdateMessage-新增或修改留言出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateMessageDelete",method=RequestMethod.POST)
	public JSONObject updateMessageDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			messageService.updateMessageDelete(json,ids,user);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Message-AJAX-/updateMessageDelete-删除留言出现异常", json);
		}
		return json;
	}
	
}
