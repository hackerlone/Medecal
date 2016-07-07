package com.lhfeiyu.util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.lhfeiyu.po.User;

public class RequestUtil {

    /**
     * 从request中取得参数，装于Map中
     * @param request
     * @return
     */
    public static HashMap<String,Object> getRequestParam(HttpServletRequest request) {
        HashMap<String,Object> data = new HashMap<String,Object>();
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            data.put(key, request.getParameter(key));
        }
        return data;
    }

    /**
     * 将页面取得的字符串id转化成整型数组
     * @param idStr
     * @return
     */
    public static int[] pkeyStrToIntArray(String idStr){
        String[] str = idStr.split(",");
        int[] ids = new int[str.length];
        for(int i=0;i<str.length;i++){
            ids[i] = Integer.parseInt(str[i]);
        }
        return ids;
    }

    /**  取得当前登录用户ID  */
    public static String getLoginUserId(HttpServletRequest request){
           return (String) request.getSession().getAttribute("userId");
    }
    
    /**  取得当前登录用户对象  */
    public static User getLoginUser(HttpServletRequest request){
           return (User) request.getSession().getAttribute("user");
    }
}
