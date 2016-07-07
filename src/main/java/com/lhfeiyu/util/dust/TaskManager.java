package com.lhfeiyu.util.dust;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 定时器
 * @author lxy
 *
 */
public class TaskManager implements ServletContextListener {
	 /**
	  * 每天的毫秒数
	  */
	 //public static final long PERIOD_DAY = DateUtils.MILLIS_IN_DAY;
	 /**
	  * 一周内的毫秒数
	  */
	 //public static final long PERIOD_WEEK = PERIOD_DAY * 7;
	 /**
	  * 无延迟
	  */
	 public static final long NO_DELAY = 0;
	 /**
	  * 定时器
	  */
	 private Timer timer;
	 /**
	  * 在Web应用启动时初始化任务
	  */
	 @Override
	public void contextInitialized(ServletContextEvent event) {
	        //定义定时器
	  timer = new Timer("数据库表备份",true); 
	  //启动备份任务,每月(4个星期)执行一次
//	  timer.schedule(new TimeData(),NO_DELAY, PERIOD_WEEK * 4);
	 // timer.schedule(new TimeData(),NO_DELAY, 60000);
	 }
	 /**
	  * 在Web应用结束时停止任务
	  */
	 @Override
	public void contextDestroyed(ServletContextEvent event) {
	  timer.cancel(); // 定时器销毁
	 }
	}
