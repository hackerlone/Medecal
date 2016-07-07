package com.lhfeiyu.tools;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.lhfeiyu.po.User;
import com.lhfeiyu.util.RegexUtil;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 检查 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2016年1月6日14:42:10 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class Check {
	
	public static boolean integerEqual(Integer num1, Integer num2){
		if(null != num1 && null != num2 && num1.intValue() == num2.intValue()){
			return true;
		}
		return false;
	}
	
	public static boolean integerNotEqual(Integer num1, Integer num2){
		if(null != num1 && null != num2 && num1.intValue() == num2.intValue()){
			return false;
		}
		return true;
	}

	public static boolean isNotNull(String str){
		if(null == str || "".equals(str.trim())){
			return false;
		}
		return true;
	}
	
	public static boolean isNull(String str){
		if(null == str || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Integer integer){
		if(null == integer){
			return false;
		}
		return true;
	}
	
	public static boolean isNull(Integer integer){
		if(null == integer){
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(List<?> list){
		if(null == list || list.size() <= 0 ){
			return false;
		}
		return true;
	}
	
	public static boolean isNull(List<?> list){
		if(null == list || list.size() <= 0 ){
			return true;
		}
		return false;
	}
	
	public static boolean isGtZero(Integer integer){
		if(null != integer && integer.intValue() > 0){
			return true;
		}
		return false;
	}
	
	public static boolean isGtEqlZero(Integer integer){
		if(null != integer && integer.intValue() >= 0){
			return true;
		}
		return false;
	}
	
	public static boolean isLtZero(Integer integer){
		if(null == integer || integer.intValue() < 0){
			return true;
		}
		return false;
	}
	
	public static boolean isLtEqlZero(Integer integer){
		if(null == integer || integer.intValue() <= 0){
			return true;
		}
		return false;
	}
	
	public static boolean isNullZero(Integer integer){
		if(null == integer || integer.intValue() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean haveNoSpecialChar(String str){
		if(!Check.isNotNull(str))return true;
		if(str.matches(RegexUtil.non_special_char_regexp)){
			return true;
		}
		return false;
	}
	
	
	public static ModelMap actionPagePromoterCheck(HttpSession session, ModelMap modelMap, String r){
		if(null == modelMap)return null;
		if( !isNotNull(r) ){
			User user = ActionUtil.checkSession4User(session);
			if(null != user){
				r = user.getSerial();
			}
		}
		modelMap.put("r", r);
		return modelMap;
	}
	
}
