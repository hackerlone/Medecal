package com.lhfeiyu.action.front.base.comment;


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
import com.lhfeiyu.po.Comment;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.CommentService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.tools.Check;

@Controller
public class CommentAction {

	@Autowired
	private CommentService  commentService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateComment", method = RequestMethod.POST)
	public JSONObject addOrUpdateComment(@ModelAttribute Comment comment,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			Doctor doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == user&&null == doctor)return Result.userSessionInvalid(json);
			if(null == comment.getId()){//添加
				if(user!=null){
					commentService.insertService(json,comment,user);
				}else if(doctor!=null){
					commentService.insertService(json,comment,doctor);
				}
			}else{//修改
				if(user!=null){
					commentService.updateService(json,comment,user);
				}else if(doctor!=null){
					commentService.updateService(json,comment,doctor);
				}
			}
			json.put("id", comment.getId());
			Result.success(json, "添加或修改评论成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Comment-AJAX-/addOrUpdateComment-新增或修改评论出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateCommentDelete",method=RequestMethod.POST)
	public JSONObject updateCommentDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			User user = ActionUtil.checkSession4User(request.getSession());//验证session中的user，存在即返回
			if(null == user)return Result.userSessionInvalid(json);
			Boolean flag = Check.haveNoSpecialChar(ids);
			if(flag == false){return Result.failure(json, "参数错误", null);}
			commentService.updateCommentDelete(json,ids,user);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Comment-AJAX-/updateCommentDelete-删除评论出现异常", json);
		}
		return json;
	}
	
}
