package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.RegularRemindMapper;
import com.lhfeiyu.po.RegularRemind;
import com.lhfeiyu.po.User;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-RegularRemind <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class RegularRemindService extends CommonService<RegularRemind> {

	@Autowired
	RegularRemindMapper regularRemindmapper;
	
	public RegularRemind selectService(Map<String, Object> paramMap) {
		return super.selectByCondition(paramMap);
	}
	
	public List<RegularRemind> selectListService(Map<String, Object> paramMap) {
		return super.selectListByCondition(paramMap);
	}
	
	public int updateService(RegularRemind regularRemind) {
		return super.updateByPrimaryKeySelective(regularRemind);
	}
	
	public int insertService(RegularRemind regularRemind) {
		return super.insertSelective(regularRemind);
	}
	
	public int updateDeleteService(Integer id, String updatedBy) {
		return super.updateDeletedNowById(id, updatedBy);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int insertService(JSONObject json, RegularRemind regularRemind, User session_user) {
		Date date = new Date();
		String username = session_user.getUsername();
		Integer userId = session_user.getId();
		regularRemind.setCreatedBy(username);
		regularRemind.setCreatedAt(date);
		regularRemind.setMainStatus(1);
		regularRemind.setUserId(userId);
		return super.insert(regularRemind);
	}

	public int updateService(JSONObject json, RegularRemind regularRemind, User session_user) {
		Date date = new Date();
		String username = session_user.getUsername();
		Integer userId = session_user.getId();
		regularRemind.setUpdatedBy(username);
		regularRemind.setUpdatedAt(date);
		regularRemind.setUserId(userId);
		return super.updateByPrimaryKeySelective(regularRemind);
	}



}