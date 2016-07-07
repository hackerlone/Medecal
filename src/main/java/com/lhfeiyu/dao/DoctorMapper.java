package com.lhfeiyu.dao;

import java.util.List;
import java.util.Map;

import com.lhfeiyu.po.Doctor;

public interface DoctorMapper extends CommonMapper<Doctor>{
	
	List<Doctor> selectDiagnoseSumByCondition(Map<String, Object> map);
}
