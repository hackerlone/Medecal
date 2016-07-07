package com.lhfeiyu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.CommentMapper;
import com.lhfeiyu.po.Comment;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.User;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-评论回复-Comment <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class CommentService extends CommonService<Comment>{
	@Autowired
	CommentMapper mapper;

	public int updateCommentDelete(JSONObject json,String ids, User user) {
		String username = user.getUsername();
		Comment comment = new Comment();
		comment.setMainStatus(2);
		comment.setIds(ids);
		comment.setUpdatedBy(username);
		return super.updateByIdsSelective(comment);
	}

	public int insertService(JSONObject json,Comment comment, User user) {
		String username = user.getPatientName();
		Integer userId = user.getId();
		Date date = new Date();
		comment.setMainStatus(1);
		comment.setTypeId(1);//(1.患者2.医生)
		comment.setUserId(userId);
		comment.setCreatedAt(date);
		comment.setCreatedBy(username);
		return super.insertSelective(comment);
	}
	
	public int insertService(JSONObject json,Comment comment, Doctor doctor) {
		String username = doctor.getUsername();
		Integer userId = doctor.getId();
		Date date = new Date();
		comment.setMainStatus(1);
		comment.setTypeId(2);//(1.患者2.医生)
		comment.setUserId(userId);
		comment.setCreatedAt(date);
		comment.setCreatedBy(username);
		return super.insertSelective(comment);
	}

	public int updateService(JSONObject json,Comment comment, User user) {
		String username = user.getUsername();
		comment.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(comment);
	}
	
	public int updateService(JSONObject json,Comment comment, Doctor doctor) {
		String username = doctor.getUsername();
		comment.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(comment);
	}

}
