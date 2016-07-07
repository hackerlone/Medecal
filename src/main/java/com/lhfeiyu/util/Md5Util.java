package com.lhfeiyu.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密工具类
 * @author 荣华 2015年7月23日09:18:43
 *
 */
public class Md5Util {

	/**
	 * 将字符串进行MD5加密，并返回加密后了字符串
	 */
	public static String encrypt(String str) {   
		if(null != str && !"".equals(str)){
			try {   
				MessageDigest md = MessageDigest.getInstance("MD5");   
			    md.update(str.getBytes());   
			    str = new BigInteger(1, md.digest()).toString(16);   
			   } catch (Exception e) {   
				   e.printStackTrace(); 
				   str = "";
			   }   
		}else{
			str = "";
		}
		return str;   
	}
	
	
	/** 
     * @param bytes 
     * @return 
     */  
    public static byte[] base64Decode(final byte[] bytes) {  
        return Base64.decodeBase64(bytes);  
    }  
  
    /** 
     * 二进制数据编码为BASE64字符串 
     * 
     * @param bytes 
     * @return 
     * @throws Exception 
     */  
    public static String base64Encode(final byte[] bytes) {  
        return new String(Base64.encodeBase64(bytes));  
    }  
	
}
