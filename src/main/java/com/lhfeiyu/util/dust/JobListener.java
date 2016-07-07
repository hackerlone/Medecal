package com.lhfeiyu.util.dust;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class JobListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		/*WebApplicationContext webApplicationContext = (WebApplicationContext) arg0.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		org.quartz.impl.StdScheduler startQuertz = (org.quartz.impl.StdScheduler) webApplicationContext.getBean("startQuertz");
		if (startQuertz != null) {
			startQuertz.shutdown();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				Logger.getLogger(this.getClass().getName()).log(Level.INFO,String.format("deregistering jdbc driver: %s",driver));
			} catch (SQLException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.ERROR,String.format("Error deregistering driver %s",driver), e);
			}
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

}