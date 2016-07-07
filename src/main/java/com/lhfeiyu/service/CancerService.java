package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.CancerMapper;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Cancer;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Cancer <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class CancerService extends CommonService<Cancer> {

	@Autowired
	CancerMapper cancermapper;
	
	public Cancer selectService(Map<String, Object> paramMap) {
		return super.selectByCondition(paramMap);
	}
	
	public List<Cancer> selectListService(Map<String, Object> paramMap) {
		return super.selectListByCondition(paramMap);
	}
	
	public int updateService(Cancer cancer) {
		return super.updateByPrimaryKeySelective(cancer);
	}
	
	public int insertService(Cancer cancer) {
		return super.insertSelective(cancer);
	}
	
	public int updateDeleteService(Integer id, String updatedBy) {
		return super.updateDeletedNowById(id, updatedBy);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public JSONObject insertData(JSONObject json, Cancer cancer, Admin admin) {
		Date date = new Date();
		String username = admin.getUsername();
		cancer.setCreatedAt(date);
		cancer.setCreatedBy(username);
		cancermapper.insert(cancer);
		return json;
	}

	public JSONObject updateData(JSONObject json, Cancer cancer, Admin admin) {
		Date date = new Date();
		String username = admin.getUsername();
		cancer.setUpdatedAt(date);
		cancer.setUpdatedBy(username);
		cancermapper.updateByPrimaryKeySelective(cancer);
		return json;
	}

}