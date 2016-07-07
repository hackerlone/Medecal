package com.lhfeiyu.dao;

import java.util.Map;

import com.lhfeiyu.po.DoctorPatient;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层Mapper：DoctorPatient <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */

public interface DoctorPatientMapper extends CommonMapper<DoctorPatient> {

	String selectPatientIdsByCondition(Map<String,Object> map);
	String selectDoctorIdsByCondition(Map<String,Object> map);
}