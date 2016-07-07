package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.DiagnoseTemplateMapper;
import com.lhfeiyu.po.DiagnoseTemplate;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-DiagnoseTemplate <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class DiagnoseTemplateService extends CommonService<DiagnoseTemplate> {

	@Autowired
	DiagnoseTemplateMapper diagnoseTemplatemapper;
	
	public DiagnoseTemplate selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<DiagnoseTemplate> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(DiagnoseTemplate diagnoseTemplate) {
		return super.updateByPrimaryKeySelective(diagnoseTemplate);
	}
	
	public int insertService(DiagnoseTemplate diagnoseTemplate) {
		return super.insertSelective(diagnoseTemplate);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int insertService(JSONObject json, DiagnoseTemplate diagnoseTemplate, Hospital hospital) {
		Date date = new Date();
		diagnoseTemplate.setHospitalId(hospital.getId());
		diagnoseTemplate.setHospitalName(hospital.getBriefName());
		diagnoseTemplate.setMainStatus(1);
		diagnoseTemplate.setCreatedBy(hospital.getWholeName());
		diagnoseTemplate.setCreatedAt(date);
		return super.insertSelective(diagnoseTemplate);
	}

	public int updateService(JSONObject json, DiagnoseTemplate diagnoseTemplate, Hospital hospital) {
		diagnoseTemplate.setUpdatedBy(hospital.getWholeName());
		return super.updateByPrimaryKeySelective(diagnoseTemplate);
	}

	public int updateDiagnoseTemplateDelete(JSONObject json, String ids, Hospital hospital) {
		DiagnoseTemplate diagnoseTemplate = new DiagnoseTemplate();
		diagnoseTemplate.setMainStatus(2);
		diagnoseTemplate.setIds(ids);
		diagnoseTemplate.setUpdatedBy(hospital.getWholeName());
		return super.updateByIdsSelective(diagnoseTemplate);
	}

	public int addDiagnoseTemplate(JSONObject json, Doctor session_doctor, DiagnoseTemplate diagnoseTemplate) {
		Date date = new Date();
		diagnoseTemplate.setDoctorId(session_doctor.getId());
		diagnoseTemplate.setDoctorName(session_doctor.getRealname());
		diagnoseTemplate.setMainStatus(1);
		diagnoseTemplate.setCreatedBy(session_doctor.getUsername());
		diagnoseTemplate.setCreatedAt(date);
		return super.insertSelective(diagnoseTemplate);
	}

}