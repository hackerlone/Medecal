package com.lhfeiyu.thirdparty.wx.business;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.thirdparty.wx.util.CommonUtil;
import com.lhfeiyu.thirdparty.wx.util.ConfigUtil;
import com.lhfeiyu.thirdparty.wx.util.Sign;
import com.lhfeiyu.tools.Check;

public class AuthAccess {
	
	/**
	 * 从Property文件中读取微信的验证数据：access_token,ticktet
	 * 只有获取ticket时，需要传递access_token（从Property文件获取失败时远程获取的参数）
	 * @param field
	 */
	public static String getWxDataFromProperty(String field){
		if(!Check.isNotNull(field))return "";
		String fieldValue = "";
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("wx.properties");
			String freshtime = config.getString("wx.freshtime");
			if(Check.isNotNull(freshtime)){
				Calendar freshCal = Calendar.getInstance();
				freshCal.setTimeInMillis(Long.parseLong(freshtime));
				Calendar now = Calendar.getInstance();
				now.add(Calendar.HOUR_OF_DAY, -1);
				if(now.before(freshCal)){//当前减一个小时,如果在更新时间之前，则可直接用：上次更新时间7点，当前时间7点10分，减一个小时 ，6点10分，在7点之前，可直接用。
					fieldValue = config.getString("wx."+field);
				}else{
					fieldValue = setPropertyFromRemote(field);
					System.out.println("setPropertyFromRemote: now is not before freshTime"+field);
				}
			}else{
				fieldValue = setPropertyFromRemote(field);
				System.out.println("setPropertyFromRemote: freshtime is null"+field);
			}
			if(!Check.isNotNull(fieldValue) || "error".equals(fieldValue)){//读取失败则重新远程读取并设置到Property文件中
				fieldValue = setPropertyFromRemote(field);
			}
			System.out.println(fieldValue);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.out.println("从Property文件读取微信验证数据失败");
		}
		return fieldValue;
	}
	
	public static String setPropertyFromRemote(String field){
		System.out.println("setPropertyFromRemote: "+field);
		String access_token = getAccessToken();
		String ticket = getTicket(access_token);
		String property = access_token;
		if("access_token".equals(field)){
			property = access_token;
		}else if("ticket".equals(field)){
			property = ticket;
		}
		setWxDataToProperty("access_token", access_token, "ticket", ticket);
		return property;
	}
	
	/**
	 * 从微信远程服务中读取微信的验证数据：access_token,ticktet，并保存到Property文件中
	 * @param field
	 */
	public static void setWxDataToProperty(String field, String fieldValue, String field2, String fieldValue2){
		if(!Check.isNotNull(field))return;
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("wx.properties");
			if(Check.isNotNull(field) && Check.isNotNull(fieldValue)){
				config.setProperty("wx."+field, fieldValue);
			}
			if(Check.isNotNull(field2) && Check.isNotNull(fieldValue2)){
				config.setProperty("wx."+field2, fieldValue2);
			}
			config.setProperty("wx.freshtime", new Date().getTime());//timeinmilles:更新时间
			config.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.out.println("微信验证数据保存到Property文件中失败");
		}
	}
	
	/**
	 * 获取access_token
	 * @return
	 */
	public static String getAccessToken(){
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ConfigUtil.APPID+"&secret="+ConfigUtil.APP_SECRECT;
		String tokenResult =CommonUtil.httpsRequestForStr(tokenUrl, "GET", null);
		JSONObject tokenJson = JSONObject.parseObject(tokenResult);
		String access_token = tokenJson.getString("access_token");//获取access_token
		System.out.println("access_token_remote_tokenJson:"+tokenJson);
		//setWxDataToProperty("access_token", access_token, null, null);
		return access_token;
	}
	
	/**
	 * 获取ticket
	 * @return
	 */
	public static String getTicket(String access_token){
		if(!Check.isNotNull(access_token))return "";
		String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
		String ticketResult =CommonUtil.httpsRequestForStr(ticketUrl, "GET", null);
		JSONObject ticketJson = JSONObject.parseObject(ticketResult);
		String ticket = ticketJson.getString("ticket");//根据access_token获取ticket
		System.out.println("ticket_remote_ticketJson:"+ticketJson);
		//setWxDataToProperty("ticket", ticket, null, null);
		return ticket;
	}
	
	/**
	 * 获取用户的openid
	 * @return
	 */
	public static String getOpenId(String code){
		String openId = null;
		//if(!"authdeny".equals(code)) {
           // WeixinOauth2Token weiXinOauth2Token = AdvancedUtil.getOauth2AccessToken(ConfigUtil.APPID,ConfigUtil.APP_SECRECT, code);
            //openId = weiXinOauth2Token.getOpenId();
		//}
        if(!Check.isNotNull(code))return "";
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ConfigUtil.APPID+"&secret="+ConfigUtil.APP_SECRECT+"&code="+code+"&grant_type=authorization_code";
		String result =CommonUtil.httpsRequestForStr(requestUrl, "GET", null);
		JSONObject resultJson = JSONObject.parseObject(result);
		openId = resultJson.getString("openid");//根据access_token获取ticket
		System.out.println("user_remote_openId:"+openId);
		return openId;
	}
	
	/**
	 * 获取签名
	 * @return
	 */
	public static ModelMap getSign(ModelMap modelMap, String ticket, String url){
		Map<String, String> signMap = Sign.sign(ticket, url);
		//String signature = signMap.get("signature");
		modelMap.put("timeStamp", signMap.get("timestamp"));
		modelMap.put("nonceStr",  signMap.get("nonceStr"));
		modelMap.put("signature", signMap.get("signature"));
		modelMap.put("appId", ConfigUtil.APPID);
		return modelMap;
	}
	
	/**
	 * 获取签名
	 * @return
	 */
	public static JSONObject getSign(JSONObject json, String ticket, String url){
		Map<String, String> signMap = Sign.sign(ticket, url);
		//String signature = signMap.get("signature");
		json.put("timeStamp", signMap.get("timestamp"));
		json.put("nonceStr",  signMap.get("nonceStr"));
		json.put("signature", signMap.get("signature"));
		json.put("appId", ConfigUtil.APPID);
		return json;
	}
	

}
