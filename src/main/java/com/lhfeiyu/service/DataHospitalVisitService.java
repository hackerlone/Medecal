package com.lhfeiyu.service;

import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.DataHospitalVisitMapper;
import com.lhfeiyu.po.DataHospitalVisit;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-DataHospitalVisit <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class DataHospitalVisitService extends CommonService<DataHospitalVisit> {

	@Autowired
	DataHospitalVisitMapper dataHospitalVisitmapper;
	
	public DataHospitalVisit selectService(Map<String, Object> paramMap) {
		return super.selectByCondition(paramMap);
	}
	
	public List<DataHospitalVisit> selectListService(Map<String, Object> paramMap) {
		return super.selectListByCondition(paramMap);
	}
	
	public int updateService(DataHospitalVisit dataHospitalVisit) {
		return super.updateByPrimaryKeySelective(dataHospitalVisit);
	}
	
	public int insertService(DataHospitalVisit dataHospitalVisit) {
		return super.insertSelective(dataHospitalVisit);
	}
	
	public int updateDeleteService(Integer id, String updatedBy) {
		return super.updateDeletedNowById(id, updatedBy);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

}