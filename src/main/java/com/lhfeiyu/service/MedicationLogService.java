package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.MedicationLogMapper;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.MedicationLog;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-MedicationLog <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class MedicationLogService extends CommonService<MedicationLog> {

	@Autowired
	MedicationLogMapper medicationLogmapper;
	
	public MedicationLog selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<MedicationLog> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(JSONObject json, MedicationLog medicationLog, Hospital db_hospital) {
		medicationLog.setUpdatedBy(db_hospital.getWholeName());
		return super.updateByPrimaryKeySelective(medicationLog);
	}

	public int insertService(JSONObject json, MedicationLog medicationLog, Hospital db_hospital) {
		Date date = new Date();
		medicationLog.setMainStatus(1);
		medicationLog.setCreatedBy(db_hospital.getWholeName());
		medicationLog.setCreatedAt(date);
		return super.insertSelective(medicationLog);
	}

	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

}