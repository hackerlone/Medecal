package com.lhfeiyu.action.front.base.message;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.InternalMessage;
import com.lhfeiyu.service.InternalMessageService;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class InternalMessageAction {
	
	@Autowired
	private InternalMessageService  internalMessageService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@ResponseBody
	@RequestMapping(value = "/getInternalMessageList", method=RequestMethod.POST)
	public JSONObject getInternalMessageList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<InternalMessage> internalMessageList = internalMessageService.selectListByCondition(map);
			Integer total = internalMessageService.selectCountByCondition(map);
			Result.gridData(internalMessageList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-InternalMessage-AJAX-/getInternalMessageList-加载站内信列表出现异常", json);
		}
		return json;
	}
	
	
	
	
}
