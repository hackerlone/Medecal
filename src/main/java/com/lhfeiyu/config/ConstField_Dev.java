package com.lhfeiyu.config;

import com.lhfeiyu.util.Md5Util;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 数据库所有表的字段描述：用于页面新增修改表单统一读取字段数据 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015年8月7日09:09:21 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class ConstField_Dev {
	
	public static final int USER = 0;

	public static final String rl_ytx_sid = "8a48b551505b4af001505f21504e0908";
	public static final String rl_ytx_authToken = "ce829f3780804eea9e7dd69e690ab133";
	public static final String rl_ytx_appId = "8a48b551510f653b0151135666060d07";//common_base_chat.js:var resp = RL_YTX.init("8a48b551510f653b0151135666060d07");//appId
	public static final String rl_ytx_appToken = "986e4c91d36c1f7fcbab09aa144e335e";
	
	public static final String rl_ytx_ipHttps = "https://app.cloopen.com";//REST服务器IP
	public static final String rl_ytx_ip = "app.cloopen.com";//REST服务器IP
	public static final String rl_ytx_port = "8883";//REST服务器端口
	public static final String rl_ytx_ip_port = "https://app.cloopen.com:8883";//REST服务器IP+端口
	//上线后：sandboxapp -> app
	public static final String rl_ytx_base_sub_url = "https://app.cloopen.com:8883/2013-12-26/SubAccounts/";
	public static final String rl_ytx_base_main_url = "https://app.cloopen.com:8883/2013-12-26/Accounts/8a48b551505b4af001505f21504e0908";
	public static final String rl_ytx_push_url = "https://app.cloopen.com:8883/2013-12-26/Accounts/8a48b551505b4af001505f21504e0908/IM/PushMsg?sig=";
	
	public static final String rl_ytx_default_subAccount = "7dd1ca1c8cdd11e5bb61ac853d9d52fd";
	public static final String rl_ytx_default_password = "69da3272a105c0472501595c1b84404c";
	public static final String rl_ytx_default_voip = "8004986400000002";
	public static final String rl_ytx_default_voip_pswd = "IS4xlWxF";
	
	public static final String auction_quick_group_id = "gg8004986410";
	
	public static final String rl_ytx_msg_moban_id = "62093";
	
	public static final String rl_ytx_msg_moban_notice_id = "62234";
	
	public static String generateSig(String timeStamp){//目前采用的是帐号密码登陆，后期需要改为应用登陆 - JS端采用就用登陆，服务器端采用账号登陆
		///{SoftVersion}/Accounts/{accountSid}/IM/PushMsg
		//https://app.cloopen.com:8883/2013-12-26/SubAccounts/{subAccountSid}/{func}/{funcdes}?sig={SigParameter}
		//使用MD5加密（主帐号Id + 主帐号授权令牌 +时间戳）。其中主帐号Id和主帐号授权令牌分别对应管理控制台中的           ACCOUNT SID和AUTH TOKEN。
		//时间戳是当前系统时间，格式"yyyyMMddHHmmss"。时间戳有效时间为24小时，如：20140416142030
		String sid = ConstField_Dev.rl_ytx_sid;
		String authToken = ConstField_Dev.rl_ytx_authToken;
		String sig = Md5Util.encrypt(sid+authToken+timeStamp);
		return sig;
	}
	
	public static String generateSubSig(String subAccount,String password, String timeStamp){//帐号密码登陆
		String subSig = Md5Util.encrypt(subAccount+password+timeStamp);
		return subSig;
	}
	public static String generateSubSig(String userSerial, String timeStamp){//应用登陆
		String appId = ConstField_Dev.rl_ytx_appId;
		String appToken = ConstField_Dev.rl_ytx_appToken;
		String subSig = Md5Util.encrypt(appId+userSerial+timeStamp+appToken);
		return subSig;
	}
	
	public static String getPushMsgUrl(String timeStamp){
		String baseUrl = ConstField_Dev.rl_ytx_push_url;
		String url = baseUrl+generateSig(timeStamp);
		return url;
	}
	
	public static String getAuthorization(String timeStamp){
		String sid = ConstField_Dev.rl_ytx_sid;
		String authStr =sid+":"+timeStamp;
		String authorization = Md5Util.base64Encode(authStr.getBytes());
		return authorization;
	}
	
	public static String getSubAuthorization(String subAccount,String timeStamp){
		String authStr =subAccount+":"+timeStamp;
		String authorization = Md5Util.base64Encode(authStr.getBytes());
		return authorization;
	}
	
	
}
