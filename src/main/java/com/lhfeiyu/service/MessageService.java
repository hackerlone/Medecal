package com.lhfeiyu.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.MessageMapper;
import com.lhfeiyu.dao.NoticeMapper;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Message;
import com.lhfeiyu.po.Notice;
import com.lhfeiyu.po.User;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-消息-Message <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class MessageService extends CommonService<Message>{
	@Autowired
	MessageMapper mapper;
	@Autowired
	NoticeMapper noticeMapper;

	public int insertService(JSONObject json, Message message, User user) {
		Date date = new Date();
		Integer userId = user.getId();
		String username = user.getUsername();
		message.setMainStatus(1);
		message.setSenderId(userId);
		message.setTitle(message.getContent());
		message.setCreatedBy(username);
		message.setCreatedAt(date);
		return super.insertSelective(message);
	}

	public int updateService(JSONObject json, Message message, User user) {
		String username = user.getUsername();
		message.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(message);
	}

	public int updateMessageDelete(JSONObject json, String ids, User user) {
		String username = user.getUsername();
		Message message = new Message();
		message.setMainStatus(2);
		message.setIds(ids);
		message.setUpdatedBy(username);
		return super.updateByIdsSelective(message);
	}

	public JSONObject updateReplyContentAndSendNoctice(JSONObject json, Message message, Doctor session_doctor ,Message db_message) {
		Integer sendId = db_message.getSenderId();
		Integer sessionDoctorId = session_doctor.getId();
		String username = session_doctor.getUsername();
		String replyContent = message.getReplyContent();
		Integer messageId = db_message.getId();
		Map<String,Object> map = CommonGenerator.getHashMap();
		map.put("expression1","main_status = 2");
		map.put("expression2","reply_content = '"+replyContent+"'");
		map.put("id", message.getId());
		mapper.updateFieldById(map);
		json.put("id", messageId);
		Notice notice = new Notice();
		notice.setSenderId(sessionDoctorId);
		notice.setReceiverId(sendId);
		notice.setTitle(replyContent);
		notice.setContent(replyContent);
		notice.setMainStatus(1);
		notice.setCreatedAt(new Date());
		notice.setCreatedBy(username);
		noticeMapper.insert(notice);
		return Result.success(json, "回复成功", null);
	}
}
