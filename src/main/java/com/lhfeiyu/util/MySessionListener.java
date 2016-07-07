package com.lhfeiyu.util;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener{

	 @Override
	public void sessionCreated(HttpSessionEvent event) {
         HttpSession session = event.getSession();
         ServletContext application = session.getServletContext();
         Integer count = (Integer) application.getAttribute("peopleOnline");
         if (count == null) {
             count = 1;
         } else {
             count++;
         }
         application.setAttribute("peopleOnline", count);
  }

  @Override
public void sessionDestroyed(HttpSessionEvent event) {
         HttpSession session = event.getSession();
         ServletContext application = session.getServletContext();
         Integer count = (Integer) application.getAttribute("peopleOnline");
         if(count > 0){
        	 count--;
         }else{
        	 count = 0; 
         }
         application.setAttribute("peopleOnline", count);
  }


}
