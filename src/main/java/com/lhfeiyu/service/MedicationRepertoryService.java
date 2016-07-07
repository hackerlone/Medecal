package com.lhfeiyu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.MedicationLogMapper;
import com.lhfeiyu.dao.MedicationRepertoryMapper;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.MedicationRepertory;
import com.lhfeiyu.tools.Result;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-MedicationRepertory <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class MedicationRepertoryService extends CommonService<MedicationRepertory> {

	@Autowired
	MedicationRepertoryMapper medicationRepertoryMapper;
	@Autowired
	MedicationLogMapper medicationLogMapper;
	
	public MedicationRepertory selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<MedicationRepertory> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(MedicationRepertory medicationRepertory) {
		return super.updateByPrimaryKeySelective(medicationRepertory);
	}
	
	public int insertService(MedicationRepertory medicationRepertory) {
		return super.insertSelective(medicationRepertory);
	}
	
	public int updateDeleteService(Integer id, String updatedBy) {
		return super.updateDeletedNowById(id, updatedBy);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public JSONObject insertData(JSONObject json, MedicationRepertory medicationRepertory, Admin admin) {
		Date date = new Date();
		/*Integer num = medicationRepertory.getRemainNum();*/
		Integer medicationId = medicationRepertory.getMedicationId();
		Integer hospitalId = medicationRepertory.getHospitalId();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("medicationId", medicationId);
		map.put("hospitalId", hospitalId);
		MedicationRepertory db_medicationRepertory = medicationRepertoryMapper.selectByCondition(map);
		if(null != db_medicationRepertory){
			return Result.failure(json, "该诊所下已有该药品,请不要重复添加", null);
		}
		String username = admin.getUsername();
		medicationRepertory.setCreatedAt(date);
		medicationRepertory.setCreatedBy(username);
		medicationRepertoryMapper.insert(medicationRepertory);
		Result.success(json, "添加或修改药物成功", null);
		/*MedicationLog medicationLog = new MedicationLog();
		medicationLog.setInOrOut(2);//1.出库2.入库
		medicationLog.setInOrOutTime(date);
		medicationLog.setMainStatus(1);
		medicationLog.setMedicationId(medicationId);
		medicationLog.setHospitalId(hospitalId);
		medicationLog.setNum(num);
		medicationLog.setCreatedAt(date);
		medicationLog.setCreatedBy(username);
		medicationLogMapper.insert(medicationLog);*/
		return json;
		
	}

	public JSONObject updateData(JSONObject json, MedicationRepertory medicationRepertory, Admin admin) {
		Date date = new Date();
		/*Integer num = medicationRepertory.getRemainNum();
		Integer medicationId = medicationRepertory.getMedicationId();
		Integer hospitalId = medicationRepertory.getHospitalId();*/
		String username = admin.getUsername();
		medicationRepertory.setUpdatedAt(date);
		medicationRepertory.setUpdatedBy(username);
		medicationRepertoryMapper.updateByPrimaryKeySelective(medicationRepertory);
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("medicationId", medicationId);
		map.put("hospitalId", hospitalId);
		MedicationLog medicationLog = medicationLogMapper.selectByCondition(map);//更新药品记录
		medicationLog.setNum(num);
		medicationLog.setUpdatedAt(date);
		medicationLog.setUpdatedBy(username);
		medicationLogMapper.updateByPrimaryKeySelective(medicationLog);*/
		Result.success(json, "添加或修改药物成功", null);
		return json;
		
	}
	
	/**
	 * 查询药品剩余库存数量
	 * @param map
	 * @return
	 */
	public Integer selectMedicationNumberRemaining(Map<String, Object> map){
		return medicationRepertoryMapper.selectMedicationNumberRemaining(map);
	}

}