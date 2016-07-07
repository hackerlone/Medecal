package com.lhfeiyu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.ChatMapper;
import com.lhfeiyu.po.Chat;
import com.lhfeiyu.po.User;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-聊天记录-Chat <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class ChatService extends CommonService<Chat>{
	@Autowired
	ChatMapper mappper;
	
	public int insertService(JSONObject json, Chat chat, User user) {
		Date date = new Date();
		String username = user.getUsername();
		chat.setMainStatus(1);
		chat.setCreatedBy(username);
		chat.setCreatedAt(date);
		return super.insertSelective(chat);
	}

	public int updateService(JSONObject json, Chat chat, User user) {
		String username = user.getUsername();
		chat.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(chat);
	}

	public int updateChatDelete(JSONObject json, String ids, User user) {
		String username = user.getUsername();
		Chat chat = new Chat();
		chat.setMainStatus(2);
		chat.setIds(ids);
		chat.setUpdatedBy(username);
		return super.updateByIdsSelective(chat);
	}
	
}
