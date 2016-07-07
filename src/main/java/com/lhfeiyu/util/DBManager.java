package com.lhfeiyu.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.lhfeiyu.util.dust.PropertiesUtil;

/**
 * 
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong>  获取jdbc连接    <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 黄剑锋 <p>
 * <strong> 编写时间：</strong> 2014-1-6下午6:48:10 <p>
 * <strong> 修  改  人：</strong>  <p>
 * <strong> 修改时间：</strong>  <p>
 * <strong> 修改描述：</strong>  <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class DBManager {
	
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	private static final String DRIVER;
	
	static{//初始化jdbc属性
		URL = PropertiesUtil.getValue("jdbc", "jdbc.url");
		USER = PropertiesUtil.getValue("jdbc", "jdbc.username");
		PASSWORD = PropertiesUtil.getValue("jdbc", "jdbc.password");
		DRIVER = PropertiesUtil.getValue("jdbc", "jdbc.driverClassName");
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获得jdbc连接
	public static Connection getConnection() throws Exception{
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	public static void close(Connection con) throws Exception{
		if(con != null){
			if(!con.isClosed()){
				con.close();
			}
		}
	}
	//关闭jdbc连接
	public static void main(String[] args) {
		try {
			System.out.println(DBManager.getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
