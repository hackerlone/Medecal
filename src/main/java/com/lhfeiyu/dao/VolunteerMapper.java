package com.lhfeiyu.dao;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.Volunteer;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层com.lhfeiyu.dao/VolunteerMapper.java <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 王家明 <p>
 * <strong> 编写时间：</strong>2016年7月5日 下午6:24:45<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public interface VolunteerMapper extends CommonMapper<Volunteer>{

	void updateDeletedNowById(Integer volunteerId);

	void insertService(Volunteer nurse);

	void updateService(Volunteer nurse);

}
