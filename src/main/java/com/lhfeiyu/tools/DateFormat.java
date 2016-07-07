package com.lhfeiyu.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <strong>@ClassName: DateFormat(日期格式常量与日期格式转换)</strong><p>
 * <strong>@Author: 王科</strong><p>
 * <strong>@Date: 2014年1月2日 上午11:10:17</strong><p>
 * <strong>@Description: 日期格式常量与日期格式转换</strong><p>
 * <strong>@UpdateAuthor: 无</strong><p>
 * <strong>@UpdateDate: 无</strong><p>
 * <strong>@Description: 无</strong><p>
 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
 * <strong>@version:</strong>v1.0<p>
 */
public class DateFormat {
	
	/**获得年时间格式*/
	public static String YEARS_FORMAT="yyyy";
	/**获得年月时间格式*/
	public static String MONTH_FORMAT="yyyy-MM";
	/**获得年月日时间格式*/
	public static String DAY_FORMAT="yyyy-MM-dd";
	/**获得年月日小时时间格式*/
	public static String HOURS_FORMAT="yyyy-MM-dd HH";
	/**获得年月日小时分钟时间格式*/
	public static String MINUTES_FORMAT="yyyy-MM-dd HH:mm";
	/**获得年月日小时分钟秒时间格式*/
	public static String SECONDS_FORMAT="yyyy-MM-dd HH:mm:ss";
	/** 获得年月日小时分钟秒毫秒时间格式*/
	public static String MS_FORMAT="yyyy-MM-dd HH:mm:ss:SS";
	
	/**
	 * 时间格式
	 */
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 时间格式
	 */
	public static final SimpleDateFormat sForomat = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 时间格式
	 */
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 *时间格式 
	 */
	public static final SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * <strong>@Title: getDateToFormar(指定时间转换成指定格式时间)</strong><p> 
	 * <strong>@Author: 王科</strong><p>
	 * <strong>@Date: 2014年1月2日 上午11:31:23</strong><p>
	 * <strong>@Description: 指定时间转换成指定格式时间</strong><p>
	 * <strong>@UpdateAuthor: 无</strong><p>
	 * <strong>@UpdateDate: 无</strong><p>
	 * <strong>@Description: 无</strong><p>
	 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
	 * <strong>@throws: 1、时间不能为空。2、格式化标准不能为空 </strong><p> 
	 * <strong>@param date 需要进行格式化的时间
	 * <strong>@param format 格式化的标准
	 * <strong>@return Date(返回指定格式后的时间)</strong><p>
	 */
	public static Date getDateToFormatDate(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date formatDate = null;
		try {
			formatDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;
	}
	
	/**
	 * <strong>@Title: getDateToFormatString(时间转成成指定格式的字符串)</strong><p> 
	 * <strong>@Author: 王科</strong><p>
	 * <strong>@Date: 2014年1月2日 上午11:39:49</strong><p>
	 * <strong>@Description: 时间转成成指定格式的字符串</strong><p>
	 * <strong>@UpdateAuthor: 无</strong><p>
	 * <strong>@UpdateDate: 无</strong><p>
	 * <strong>@Description: 无</strong><p>
	 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
	 * <strong>@throws: </strong><p> 
	 * <strong>@param date 需要进行格式化的时间
	 * <strong>@param format 格式化的标准
	 * <strong>@return String(返回时间格式化后的字符串)</strong><p>
	 */
	public static String getDateToFormatString(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String formatDate = sdf.format(date);
		return formatDate;
	}
	
	/**
	 * <strong>@Title: getStringToFormatDate(字符串按照指定格式化标准进行格式化，格式化为date类型)</strong><p> 
	 * <strong>@Author: 王科</strong><p>
	 * <strong>@Date: 2014年1月2日 上午11:41:05</strong><p>
	 * <strong>@Description: 字符串按照指定格式化标准进行格式化，格式化为date类型</strong><p>
	 * <strong>@UpdateAuthor: 无</strong><p>
	 * <strong>@UpdateDate: 无</strong><p>
	 * <strong>@Description: 无</strong><p>
	 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
	 * <strong>@throws: 1、时间不能为空。2、格式化标准不能为空 </strong><p> 
	 * <strong>@param date 需要格式化的String字符串
	 * <strong>@param format 需要进行格式化的标准
	 * <strong>@return Date(TODO)</strong><p>
	 */
	public static Date getStringToFormatDate(String date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date formatDate = null;
		try {
			formatDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;
	}
}
