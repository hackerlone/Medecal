package com.lhfeiyu.config;

import com.lhfeiyu.util.Md5Util;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 数据库所有表的字段描述：用于页面新增修改表单统一读取字段数据 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015年8月7日09:09:21 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class ConstField {
	
	public static final int USER = 0;

	public static final String rl_ytx_sid = "8a48b551523a5c1201523dbb47ea0666";
	public static final String rl_ytx_authToken = "cb79b92ebcc54ceaa2b92fe88222c91d";
	public static final String rl_ytx_appId = "8a48b551523a5c1201523ebeef200aa5";//common_base_chat.js:var resp = RL_YTX.init("8a48b551523a5c1201523ebeef200aa5");//appId
	public static final String rl_ytx_appToken = "32463f3d748492e4302e7f478fc77863";
	
	public static final String rl_ytx_ipHttps = "https://app.cloopen.com";//REST服务器IP
	public static final String rl_ytx_ip = "app.cloopen.com";//REST服务器IP
	public static final String rl_ytx_port = "8883";//REST服务器端口
	public static final String rl_ytx_ip_port = "https://app.cloopen.com:8883";//REST服务器IP+端口
	//上线后：sandboxapp -> app
	public static final String rl_ytx_base_sub_url = "https://app.cloopen.com:8883/2013-12-26/SubAccounts/";
	public static final String rl_ytx_base_main_url = "https://app.cloopen.com:8883/2013-12-26/Accounts/8a48b551523a5c1201523dbb47ea0666";
	public static final String rl_ytx_push_url = "https://app.cloopen.com:8883/2013-12-26/Accounts/8a48b551523a5c1201523dbb47ea0666/IM/PushMsg?sig=";
	
	public static final String rl_ytx_default_subAccount = "4b4aa7d6ba8511e59288ac853d9f54f2";
	public static final String rl_ytx_default_password = "3c7ae373d513e396226e7b658500cf47";
	public static final String rl_ytx_default_voip = "8008953800000002";
	public static final String rl_ytx_default_voip_pswd = "cVSsuUVJ";
	
	public static final String auction_quick_group_id = "gg800895382";
	
	public static final String rl_ytx_msg_moban_id = "62710";
	
	public static final String rl_ytx_msg_moban_notice_id = "62639";
	
	//微信相关
	/** 拍卖结束提醒 */
	public static final String wx_moban_1 = "7fHYb4X_O7ihIpNrM1-r8zJobQ6AY9UcyvnBs3i3R3g";
	/** 实时交易提醒 */
	public static final String wx_moban_2 = "Q0EXJns1vjgS2VwWHywGPr6BgkrKh8fDzWFjcV4aoCw";
	/** 评价完成结果通知 */
	public static final String wx_moban_3 = "QH26XFEVxCOd6rI1zDOqLE7BGt2lYfrRlOJv7nrpMYA";
	/** 竞拍成功通知 */
	public static final String wx_moban_4 = "UiPQ4Bl3T58IDBAiNU6uhjqTI_ya2aRfqVCtXpTw9_0";
	/** 付款成功通知 */
	public static final String wx_moban_5 = "W2xJkIBolM7oPgesdtZHP9dHyDIgtyrsQFi6LEXy8bk";
	/** 商品已发出通知 */
	public static final String wx_moban_6 = "cDuLYLkJb-EZqYHoR_FrsBZGUQswR8b9Cy39ivoRV9o";
	/** 订单确认收货通知 */
	public static final String wx_moban_7 = "eaGaAL5yfsi8Llynwd1kDM0a1HJ9uUvz5gt8FqPy8Pc";
	/** 出价被超越通知 */
	public static final String wx_moban_8 = "pKpg4VTuQOUmmhLXyUlWv39K1kQF_0_X-aKCVlhb_8Y";
	/** 提现成功通知 */
	public static final String wx_moban_9 = "s2sAtrNSCk5SNxP5eNJV8kuA6fMQEcaOB5oQe615mXo";
	/** 充值通知 */
	public static final String wx_moban_10 = "wLtGKHx7f6W3vxyilmeCFhnZ-8qh_d_27n1_Wi7F4T4";
	
	public static final String page_user_center = "http://weipaike.net/user";
	public static final String wx_message_remark = "备注：如有疑问，请联系微拍客客服人员。";
	
	
	public static String generateSig(String timeStamp){//目前采用的是帐号密码登陆，后期需要改为应用登陆 - JS端采用就用登陆，服务器端采用账号登陆
		///{SoftVersion}/Accounts/{accountSid}/IM/PushMsg
		//https://app.cloopen.com:8883/2013-12-26/SubAccounts/{subAccountSid}/{func}/{funcdes}?sig={SigParameter}
		//使用MD5加密（主帐号Id + 主帐号授权令牌 +时间戳）。其中主帐号Id和主帐号授权令牌分别对应管理控制台中的           ACCOUNT SID和AUTH TOKEN。
		//时间戳是当前系统时间，格式"yyyyMMddHHmmss"。时间戳有效时间为24小时，如：20140416142030
		String sid = ConstField.rl_ytx_sid;
		String authToken = ConstField.rl_ytx_authToken;
		String sig = Md5Util.encrypt(sid+authToken+timeStamp);
		return sig;
	}
	
	public static String generateSubSig(String subAccount,String password, String timeStamp){//帐号密码登陆
		String subSig = Md5Util.encrypt(subAccount+password+timeStamp);
		return subSig;
	}
	public static String generateSubSig(String userSerial, String timeStamp){//应用登陆
		String appId = ConstField.rl_ytx_appId;
		String appToken = ConstField.rl_ytx_appToken;
		String subSig = Md5Util.encrypt(appId+userSerial+timeStamp+appToken);
		return subSig;
	}
	
	public static String getPushMsgUrl(String timeStamp){
		String baseUrl = ConstField.rl_ytx_push_url;
		String url = baseUrl+generateSig(timeStamp);
		return url;
	}
	
	public static String getAuthorization(String timeStamp){
		String sid = ConstField.rl_ytx_sid;
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
