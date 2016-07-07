package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.BespeakMapper;
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.User;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Bespeak <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class BespeakService extends CommonService<Bespeak> {

	@Autowired
	BespeakMapper bespeakmapper;
	
	public Bespeak selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<Bespeak> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(Bespeak bespeak) {
		return super.updateByPrimaryKeySelective(bespeak);
	}
	
	public int insertService(Bespeak bespeak) {
		return super.insertSelective(bespeak);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int insertService(JSONObject json,Bespeak bespeak, User user) {
		Date date = new Date();
		String username = user.getUsername();
		Integer userId = user.getId();
		//bespeak.setMainStatus(1);
		bespeak.setUserId(userId.toString());
		bespeak.setCreatedBy(username);
		bespeak.setCreatedAt(date);
		return super.insertSelective(bespeak);
	}

	public int updateService(JSONObject json,Bespeak bespeak, User user) {
		String username = user.getUsername();
		bespeak.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(bespeak);
	}

	public int updateBespeakDelete(JSONObject json,String ids, User user) {
		String username = user.getUsername();
		Bespeak bespeak = new Bespeak();
		//bespeak.setMainStatus(2);
		bespeak.setIds(ids);
		bespeak.setUpdatedBy(username);
		return super.updateByIdsSelective(bespeak);
	}

	public int insertService(JSONObject json, Bespeak bespeak, Hospital hospital) {
		Date date = new Date();
		bespeak.setMainStatus(1);
		bespeak.setCreatedBy(hospital.getWholeName());
		bespeak.setCreatedAt(date);
		return super.insertSelective(bespeak);
	}

	public int updateService(JSONObject json, Bespeak bespeak, Hospital hospital) {
		bespeak.setUpdatedBy(hospital.getWholeName());
		return super.updateByPrimaryKeySelective(bespeak);
	}

}