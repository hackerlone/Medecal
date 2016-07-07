package com.lhfeiyu.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.lhfeiyu.service.PatientReportService;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 任务调度：简单任务类 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2016年3月1日20:14:24 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class SysJob {
	
	@Autowired
	private PatientReportService patientReportService;
	
	public void run(){//1个小时执行一次
		//deleteGroupWhichAuctionOver();//每天晚上检查一下删除拍卖结束的群组
		//wxTokenRefresh();
		patientReportService.getbloadTestResult();
	}
	
	
	
}
