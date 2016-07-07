package com.lhfeiyu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.HealthRecordMapper;
import com.lhfeiyu.po.HealthRecord;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-HealthRecord <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class HealthRecordService extends CommonService<HealthRecord> {

	@Autowired
	HealthRecordMapper healthRecordmapper;
	
	public HealthRecord selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<HealthRecord> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(HealthRecord healthRecord) {
		return super.updateByPrimaryKeySelective(healthRecord);
	}
	
	public int insertService(HealthRecord healthRecord) {
		return super.insertSelective(healthRecord);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

}