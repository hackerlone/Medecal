package com.lhfeiyu.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.DiagnoseMapper;
import com.lhfeiyu.dao.DoctorMapper;
import com.lhfeiyu.dao.DoctorPatientMapper;
import com.lhfeiyu.dao.MedicationMapper;
import com.lhfeiyu.dao.MedicationRepertoryMapper;
import com.lhfeiyu.po.Diagnose;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.DoctorPatient;
import com.lhfeiyu.po.User;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.vo.Prescription;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Diagnose <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class DiagnoseService extends CommonService<Diagnose> {

	@Autowired
	DiagnoseMapper diagnoseMapper;
	@Autowired
	MedicationMapper medicationMapper;
	@Autowired
	MedicationRepertoryMapper medicationRepertoryMapper;
	@Autowired
	DoctorMapper doctorMapper;
	@Autowired
	DoctorPatientMapper dpMapper;
	
	public Diagnose selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<Diagnose> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(Diagnose diagnose) {
		return super.updateByPrimaryKeySelective(diagnose);
	}
	
	public int insertService(Diagnose diagnose) {
		return super.insertSelective(diagnose);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int updateService(JSONObject json, Diagnose diagnose, User session_user) {
		diagnose.setUpdatedBy(session_user.getUsername());
		return super.updateByPrimaryKeySelective(diagnose);
	}
	
	public JSONObject addDiagnose(JSONObject json, Doctor sessionDoctor, Diagnose diagnose) throws ParseException{
		//更新药品库存
		String name = sessionDoctor.getRealname();
		String pres = diagnose.getPrescription();
		Map<String, Object> mapu = new HashMap<String, Object>();
		Integer userId = diagnose.getUserId();
		mapu.put("userId", userId);
		List<DoctorPatient> dpList = dpMapper.selectListByCondition(mapu);
		if(Check.isNull(dpList)){
			DoctorPatient dp = new DoctorPatient();
			dp.setDoctorId(sessionDoctor.getId());
			dp.setUserId(userId);
			dp.setCreatedAt(new Date());
			dp.setCreatedBy(sessionDoctor.getUsername());
			dpMapper.insert(dp);
		}
		if(Check.isNotNull(pres)){
			List<Prescription> presList = JSONObject.parseArray(pres, Prescription.class);
			Map<String,Object> map = CommonGenerator.getHashMap();
			for(Prescription p : presList){
				map.put("id", p.getId());
				map.put("consumeNum", p.getMedicalNum());
				map.put("updatedBy", name);
				//DOTO FIXME 这里是修改库存
				//同时要新增medication_log:药品进出库记录表
				medicationRepertoryMapper.updateReduceNumById(map);
			}
		}
		diagnose.setHospitalId(sessionDoctor.getHospitalId());
		diagnose.setHospitalName(sessionDoctor.getHospitalName());
		diagnose.setDepartmentId(sessionDoctor.getDepartmentId());
		diagnose.setDepartmentName(sessionDoctor.getDepartmentName());
		diagnose.setDoctorId(sessionDoctor.getId());
		diagnose.setDoctorName(name);
		diagnose.setCreatedAt(new Date());
		diagnose.setCreatedBy(name);
		diagnoseMapper.insert(diagnose);
		return Result.success(json);
	}

	public JSONObject getDoctorData(JSONObject json, User db_user,Map<String,Object> map) {
		String idcardNum = db_user.getIdcardNum();
		map.put("patientIdcardNum", idcardNum);
		List<Diagnose> diagnoseList = diagnoseMapper.selectListByCondition(map);
		if(diagnoseList.size() > 0){
			List<Doctor> doctorList = new ArrayList<>(diagnoseList.size());
			Integer total = 0;
			for(Diagnose d:diagnoseList){
				Integer doctorId = d.getDoctorId();
				if(null != doctorId){
					map.clear();
					map.put("id", doctorId);
					Doctor doctor = doctorMapper.selectByCondition(map);
					total = doctorMapper.selectCountByCondition(map);
					doctorList.add(doctor);
				}
			}
			if(doctorList.size() > 0){
				Result.gridData(doctorList, total, json);
			}else{
				Result.failure(json, "暂无我的医生", null);
			}
		}else{
			Result.failure(json, "病历不存在", null);
		}
		return json;
	}



}