package com.lhfeiyu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.NoticeMapper;
import com.lhfeiyu.po.Notice;
import com.lhfeiyu.po.User;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-通知消息-Notice <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class NoticeService extends CommonService<Notice>{
	@Autowired
	NoticeMapper mapper;
	
	public int insertService(JSONObject json, Notice notice, User user) {
		Date date = new Date();
		String username = user.getUsername();
		notice.setMainStatus(1);
		notice.setCreatedBy(username);
		notice.setCreatedAt(date);
		return super.insertSelective(notice);
	}

	public int updateService(JSONObject json, Notice notice, User user) {
		String username = user.getUsername();
		notice.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(notice);
	}

	public int updateNoticeDelete(JSONObject json, String ids, User user) {
		String username = user.getUsername();
		Notice notice = new Notice();
		notice.setMainStatus(2);
		notice.setIds(ids);
		notice.setUpdatedBy(username);
		return super.updateByIdsSelective(notice);
	}
	
}
