package com.lhfeiyu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.MedicationLogMapper;
import com.lhfeiyu.dao.MedicationMapper;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Medication;
import com.lhfeiyu.tools.Result;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Medication <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class MedicationService extends CommonService<Medication> {

	@Autowired
	MedicationMapper medicationmapper;
	@Autowired
	MedicationLogMapper medicationLogMapper;
	
	public Medication selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<Medication> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(Medication medication) {
		return super.updateByPrimaryKeySelective(medication);
	}
	
	public int insertService(Medication medication) {
		return super.insertSelective(medication);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int updateService(JSONObject json, Medication medication, Hospital db_hospital) {
		medication.setUpdatedBy(db_hospital.getWholeName());
		return super.updateByPrimaryKeySelective(medication);
	}

	public int insertService(JSONObject json, Medication medication, Hospital db_hospital) {
		Date date = new Date();
		medication.setMainStatus(1);
		medication.setCreatedBy(db_hospital.getWholeName());
		medication.setCreatedAt(date);
		return super.insertSelective(medication);
	}

	public ModelMap getMedicationData(ModelMap modelMap, Hospital db_hospital, Integer medicationId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", medicationId);
		Medication medication = medicationmapper.selectByCondition(map);
		if(null == medication){
			return Result.failure(modelMap, "您访问的药品信息不存在", "doctor_null");
		}
		modelMap.put("medication", medication);
		return modelMap;
	}

	public JSONObject updateData(JSONObject json,Medication medication,Admin admin) {
		Date date = new Date();
		String username = admin.getUsername();
		medication.setUpdatedAt(date);
		medication.setUpdatedBy(username);
		medicationmapper.updateByPrimaryKeySelective(medication);
		return json;
	}

	public JSONObject insertData(JSONObject json, Medication medication, Admin admin) {
		Date date = new Date();
		String username = admin.getUsername();
		medication.setCreatedAt(date);
		medication.setCreatedBy(username);
		medicationmapper.insert(medication);
		return json;
	}


}