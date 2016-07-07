package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.ConsultMapper;
import com.lhfeiyu.po.Consult;
import com.lhfeiyu.po.User;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Consult <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class ConsultService extends CommonService<Consult> {

	@Autowired
	ConsultMapper consultmapper;
	
	public Consult selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<Consult> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(Consult consult) {
		return super.updateByPrimaryKeySelective(consult);
	}
	
	public int insertService(Consult consult) {
		return super.insertSelective(consult);
	}
	
	public int updateDeleteService(Integer id, String updatedBy) {
		return super.updateDeletedNowById(id, updatedBy);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int updateConsultDelete(JSONObject json, String ids, User user) {
		String username = user.getUsername();
		Consult consult = new Consult();
		consult.setMainStatus(2);
		consult.setIds(ids);
		consult.setUpdatedBy(username);
		return super.updateByIdsSelective(consult);
	}

	public int updateService(JSONObject json, Consult consult, User user) {
		String username = user.getUsername();
		consult.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(consult);
	}

	public int insertService(JSONObject json, Consult consult, User user) {
		Date date = new Date();
		String username = user.getUsername();
		Integer userId = user.getId();
		consult.setUserId(userId);
		consult.setMainStatus(1);
		consult.setCreatedBy(username);
		consult.setCreatedAt(date);
		return super.insertSelective(consult);
	}


}