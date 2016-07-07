package com.lhfeiyu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.OperationLogMapper;
import com.lhfeiyu.po.OperationLog;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-公告-Announcement <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class OperationLogService extends CommonService<OperationLog>{
	@Autowired
	OperationLogMapper mapper;
}
