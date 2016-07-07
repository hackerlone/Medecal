package com.lhfeiyu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.AnnouncementMapper;
import com.lhfeiyu.po.Announcement;
import com.lhfeiyu.po.Hospital;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-公告-Announcement <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class AnnouncementService extends CommonService<Announcement>{
	@Autowired
	AnnouncementMapper mapper;

	public int insertService(JSONObject json, Announcement announcement, Hospital db_hospital) {
		Date date = new Date();
		announcement.setHospitalId(db_hospital.getId());
		announcement.setMainStatus(1);
		announcement.setCreatedBy(db_hospital.getWholeName());
		announcement.setCreatedAt(date);
		return super.insertSelective(announcement);
	}

	public int updateService(JSONObject json, Announcement announcement, Hospital db_hospital) {
		String username = db_hospital.getWholeName();
		announcement.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(announcement);
	}
}
