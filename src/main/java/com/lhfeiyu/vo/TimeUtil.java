package com.lhfeiyu.vo;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static long getRemainSeconds(Date endTime){
		if(null == endTime){return 0;}
		Calendar cd = Calendar.getInstance();
		Calendar ld = Calendar.getInstance();
		ld.setTime(endTime);
		long remainSeconds = (ld.getTimeInMillis() - cd.getTimeInMillis())/1000;
		if(remainSeconds < 0)remainSeconds = 0;
		return remainSeconds;
	}
	
	public static String getBeforeTimeStr(Date compareDate){
		Calendar cd = Calendar.getInstance();
		Calendar ld = Calendar.getInstance();
		ld.setTime(compareDate);
		String timeStr = "";
		long between_minutes = (cd.getTimeInMillis() - ld.getTimeInMillis())/(1000*60);
		long between_hours = between_minutes/60;
		long between_days = between_hours/24;
		if(between_minutes < 60){
			if(between_minutes<=0){
				between_minutes = 1;
			}
			timeStr = between_minutes+"分钟前";
		}else if(between_hours < 24){
			timeStr = between_hours+"小时前";
		}else if(between_days < 28){
			timeStr = between_days+"天前";
		}else{
			int yearDiff = ld.get(Calendar.YEAR) - cd.get(Calendar.YEAR);
			int monthDiff = ld.get(Calendar.MONTH) - cd.get(Calendar.MONTH);
			if(monthDiff > 0){
				cd.set(Calendar.MONTH, cd.get(Calendar.MONTH)+monthDiff);
				if(cd.after(ld)){
					monthDiff = monthDiff - 1;
					if(monthDiff<=0){
						timeStr = between_days+"天前";
					}
				}
				timeStr = monthDiff+"月前";
			}
			int months = 12 * yearDiff + monthDiff;
			int years = months/12;
			if(years >= 1){//年
				timeStr = yearDiff+"年前";
			}
		}
		return timeStr;
	}
		

}
