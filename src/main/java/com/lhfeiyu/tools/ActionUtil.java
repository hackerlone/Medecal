package com.lhfeiyu.tools;

import javax.servlet.http.HttpSession;

import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.AdminService;
import com.lhfeiyu.service.UserService;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 控制层工具类 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2016年3月10日15:32:52 <p>
 * <strong> 修  改  人：</strong>  <p>
 * <strong> 修改时间：</strong>  <p>
 * <strong> 修改描述：</strong>  <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
public class ActionUtil {
	
	public static User checkSession4User(HttpSession session){
		User user = null;
		Object userObj = session.getAttribute("user");
		if(null != userObj){
			user = (User)userObj;
		}
		return user;
	}
	
	public static Admin checkSession4Admin(HttpSession session){
		Admin admin = null;
		Object adminObj = session.getAttribute("admin");
		if(null != adminObj){
			admin = (Admin)adminObj;
		}
		return admin;
	}
	
	public static int checkSession4UserId(HttpSession session){
		int userId = 0;
		Object userIdObj = session.getAttribute("userId");
		if(null != userIdObj){
			userId = (Integer)userIdObj;
		}
		return userId;
	}
	
	public static int checkSession4AdminId(HttpSession session){
		int adminId = 0;
		Object adminIdObj = session.getAttribute("adminId");
		if(null != adminIdObj){
			adminId = (Integer)adminIdObj;
		}
		return adminId;
	}
	
	public static User loadUserBySessionUserId(HttpSession session, UserService userService){
		User user = null;
		Object userIdObj = session.getAttribute("userId");
		if(null != userIdObj){
			Integer userId = (Integer)userIdObj;
			user = userService.selectByPrimaryKey(userId);
		}
		return user;
	}
	
	public static Admin loadAdminBySessionAdminId(HttpSession session, AdminService adminService){
		Admin admin = null;
		Object adminIdObj = session.getAttribute("adminId");
		if(null != adminIdObj){
			Integer adminId = (Integer)adminIdObj;
			admin = adminService.selectByPrimaryKey(adminId);
		}
		return admin;
	}
	
	/** 具体业务逻辑对应方法  */
	public static Doctor checkSession4Doctor(HttpSession session){
		Doctor doctor = null;
		Object doctorObj = session.getAttribute("doctor");
		if(null != doctorObj){
			doctor = (Doctor)doctorObj;
		}
		return doctor;
	}
	
	public static int checkSession4DoctorId(HttpSession session){
		int doctorId = 0;
		Object doctorIdObj = session.getAttribute("doctorId");
		if(null != doctorIdObj){
			doctorId = (Integer)doctorIdObj;
		}
		return doctorId;
	}
	
	public static Hospital checkSession4Hospital(HttpSession session){
		Hospital hospital = null;
		Object hospitalObj = session.getAttribute("hospital");
		if(null != hospitalObj){
			hospital = (Hospital)hospitalObj;
		}
		return hospital;
	}
	
	public static int checkSession4HospitalId(HttpSession session){
		int hospitalId = 0;
		Object hospitalIdObj = session.getAttribute("hospitalId");
		if(null != hospitalIdObj){
			hospitalId = (Integer)hospitalIdObj;
		}
		return hospitalId;
	}
	
}