package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.NurseMapper;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Nurse;
import com.lhfeiyu.util.Md5Util;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Nurse <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class NurseService extends CommonService<Nurse> {

	@Autowired
	NurseMapper nursemapper;
	
	public Nurse selectService(Map<String, Object> paramMap) {
		return super.selectByCondition(paramMap);
	}
	
	public List<Nurse> selectListService(Map<String, Object> paramMap) {
		return super.selectListByCondition(paramMap);
	}
	
	public int updateService(Nurse nurse) {
		return super.updateByPrimaryKeySelective(nurse);
	}
	
	public int insertService(Nurse nurse) {
		return super.insertSelective(nurse);
	}
	
	public int updateDeleteService(Integer id, String updatedBy) {
		return super.updateDeletedNowById(id, updatedBy);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int insertService(JSONObject json, Nurse nurse, Hospital hospital) {
		Date date = new Date();
		nurse.setMainStatus(1);
		String password = nurse.getPassword();
		Integer hospitalId = hospital.getId();
		if(null != password){
			password = Md5Util.encrypt(password);
			nurse.setPassword(password);
		}
		nurse.setHospitalId(hospitalId);
		nurse.setCreatedBy(hospital.getWholeName());
		nurse.setCreatedAt(date);
		return super.insertSelective(nurse);
	}

	public int updateService(JSONObject json, Nurse nurse, Hospital hospital) {
		Date date = new Date();
		Integer hospitalId = hospital.getId();
		nurse.setHospitalId(hospitalId);
		nurse.setUpdatedBy(hospital.getWholeName());
		nurse.setUpdatedAt(date);
		return super.updateByPrimaryKeySelective(nurse);
	}

}