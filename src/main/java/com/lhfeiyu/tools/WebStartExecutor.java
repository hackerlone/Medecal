package com.lhfeiyu.tools;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class WebStartExecutor implements ApplicationListener<ContextRefreshedEvent> {
	
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
       if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
           //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
    	   //AuthAccess.setWxDataToProperty("access_token", "");
    	   //AuthAccess.setWxDataToProperty("ticket", "");
    	   //new SysJob().wxTokenRefresh();//获取微信的认证数据
    	    /*PropertiesConfiguration config;
			try {
				config = new PropertiesConfiguration("wx.properties");
				config.setProperty("wx.access_token", "");
				config.setProperty("wx.ticket", "");
				config.save();
			} catch (ConfigurationException e) {
				e.printStackTrace();
				System.out.println("初始化微信认证信息失败");
			}*/
       }
    }
    
}