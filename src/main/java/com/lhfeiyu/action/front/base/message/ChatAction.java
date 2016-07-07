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
import com.lhfeiyu.po.Chat;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.ChatService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class ChatAction {
	
	@Autowired
	private ChatService  chatService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@ResponseBody
	@RequestMapping(value = "/getChatList", method=RequestMethod.POST)
	public JSONObject getChatList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<Chat> chatList = chatService.selectListByCondition(map);
			Integer total = chatService.selectCountByCondition(map);
			Result.gridData(chatList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Chat-AJAX-/getChatList-加载消息列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateChat", method = RequestMethod.POST)
	public JSONObject addOrUpdateChat(@ModelAttribute Chat chat,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			if(null == chat.getId()){//添加
				chatService.insertService(json,chat,user);
			}else{//修改
				chatService.updateService(json,chat,user);
			}
			json.put("id", chat.getId());
			Result.success(json, "添加或修改消息成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Chat-AJAX-/addOrUpdateChat-新增或修改消息出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateChatDelete",method=RequestMethod.POST)
	public JSONObject updateChatDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			chatService.updateChatDelete(json,ids,user);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Chat-AJAX-/updateChatDelete-删除消息出现异常", json);
		}
		return json;
	}
	
}
