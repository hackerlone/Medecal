package com.lhfeiyu.util.dust;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.TriggerFiredBundle;

public interface JobFactory {
	 Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException;
}
