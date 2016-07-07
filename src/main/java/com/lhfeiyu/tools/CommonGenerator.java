package com.lhfeiyu.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 生成器：编号等 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015年10月30日18:02:29 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class CommonGenerator {
	
	//TODO getMap
	public static Map<String,Object> getHashMap(){
		return new HashMap<String,Object>();
	}

	public static Date getDate(){
		return new Date();
	}
	
	public static Calendar getCalendar(){
		return Calendar.getInstance();
	}
	
	public static Calendar getCalendar(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	
	public static String getTwoLessTen(int num){
		if(num < 10 && num >0){
			return "0"+num;
		}else{
			return ""+num;
		}
	}
	
	public static String getSerialByDate(String prefix,String suffix){
		Calendar c = Calendar.getInstance();
		Random r = new Random();
		int random = r.nextInt(100);
		String dateStr = prefix
						+c.get(Calendar.YEAR)+
						getTwoLessTen(c.get(Calendar.MONTH))+
						getTwoLessTen(c.get(Calendar.DAY_OF_MONTH))+
						getTwoLessTen(c.get(Calendar.HOUR_OF_DAY))+
						getTwoLessTen(c.get(Calendar.MINUTE))+
						getTwoLessTen(c.get(Calendar.SECOND))+
						getTwoLessTen(random)+
						suffix;
		return dateStr;
	}
	public static String getSerialByDate(String prefix){
		return getSerialByDate(prefix,"");
	}
	public static String getSerialByDate(){
		return getSerialByDate("","");
	}
	
	public static String getDateStr(Date date){
		if(null == date)date = new Date();
	    Calendar c = Calendar.getInstance();
	    
		int month = c.get(Calendar.MONTH)+1;
		String monthStr = String.valueOf(month);
		if(month<10)monthStr = "0"+monthStr;
		
		int day = c.get(Calendar.DAY_OF_MONTH);
		String dayStr = String.valueOf(day);
		if(day<10)dayStr = "0"+dayStr;
		
		int hour = c.get(Calendar.HOUR);
		String hourStr = String.valueOf(hour);
		if(hour<10)hourStr = "0"+hourStr;
		
		int minute = c.get(Calendar.MINUTE);
		String minuteStr = String.valueOf(minute);
		if(minute<10)minuteStr = "0"+minuteStr;
		
		int second = c.get(Calendar.SECOND);
		String secondStr = String.valueOf(second);
		if(second<10)secondStr = "0"+secondStr;
		
		String timeStr = ""+c.get(Calendar.YEAR)+"年"+monthStr+"月"+dayStr+"日"+hourStr+"时"+minuteStr+"分"+second+"秒";
		return timeStr;
	}
	
	public static String getDateNumStr(Date date){
		if(null == date)date = new Date();
	    Calendar c = Calendar.getInstance();
	    
		int month = c.get(Calendar.MONTH)+1;
		String monthStr = String.valueOf(month);
		if(month<10)monthStr = "0"+monthStr;
		
		int day = c.get(Calendar.DAY_OF_MONTH);
		String dayStr = String.valueOf(day);
		if(day<10)dayStr = "0"+dayStr;
		
		int hour = c.get(Calendar.HOUR);
		String hourStr = String.valueOf(hour);
		if(hour<10)hourStr = "0"+hourStr;
		
		int minute = c.get(Calendar.MINUTE);
		String minuteStr = String.valueOf(minute);
		if(minute<10)minuteStr = "0"+minuteStr;
		
		int second = c.get(Calendar.SECOND);
		String secondStr = String.valueOf(second);
		if(second<10)secondStr = "0"+secondStr;
		
		String timeStr = ""+c.get(Calendar.YEAR)+"-"+monthStr+"-"+dayStr+"-"+hourStr+"-"+minuteStr+"-"+second+"-"+c.get(Calendar.MILLISECOND);
		return timeStr;
	}
	
	public static void main(String[] args) {
		//getSerialByDate();
		
		//String name = ",'~`!@#$%^&*()-_+=[]{}|?<>/\\--321".replaceAll("[/'\"\\;,:-<>]", "");
		//System.out.println(name);
	}
}
