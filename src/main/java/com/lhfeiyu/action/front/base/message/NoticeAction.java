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
import com.lhfeiyu.po.Notice;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.NoticeService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class NoticeAction {
	
	@Autowired
	private NoticeService  noticeService;
	
	private static Logger logger = Logger.getLogger("R");
	
	
	@ResponseBody
	@RequestMapping(value = "/getNoticeList", method=RequestMethod.POST)
	public JSONObject getNoticeList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			//map.put("mainStatus", 1);
			List<Notice> noticeList = noticeService.selectListByCondition(map);
			Integer total = noticeService.selectCountByCondition(map);
			Result.gridData(noticeList, total, json);
			Result.success(json, "数据加载成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Notice-AJAX-/getNoticeList-加载患者消息列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateNotice", method = RequestMethod.POST)
	public JSONObject addOrUpdateNotice(@ModelAttribute Notice notice,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			if(null == notice.getId()){//添加
				noticeService.insertService(json,notice,user);
			}else{//修改
				noticeService.updateService(json,notice,user);
			}
			json.put("id", notice.getId());
			Result.success(json, "添加或修改患者消息成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Notice-AJAX-/addOrUpdateNotice-新增或修改患者消息出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateNoticeDelete",method=RequestMethod.POST)
	public JSONObject updateNoticeDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			noticeService.updateNoticeDelete(json,ids,user);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Notice-AJAX-/updateNoticeDelete-删除患者消息出现异常", json);
		}
		return json;
	}
	
}
