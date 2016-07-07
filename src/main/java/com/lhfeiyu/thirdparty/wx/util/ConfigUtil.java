package com.lhfeiyu.thirdparty.wx.util;

public class ConfigUtil {
	/**
	 * 服务号相关信息
	 */
	 public final static String APPID = "wxc191ed6c5d1f462e";//服务号的应用号
	 public final static String APP_SECRECT = "d8d45ae75ae50eb202ad9e18e59ae32a";//服务号的应用密码
	 public final static String TOKEN = "weixin_token";//服务号的配置token
	 public final static String MCH_ID = "1260907701";//商户号
	 public final static String API_KEY = "a8d45ae75ae50eb202ad9e18e59ae32d";//API密钥
	 public final static String SIGN_TYPE = "MD5";//签名加密方式
	 public final static String CERT_PATH = "D:/cert/apiclient_cert.p12";//微信支付证书存放路径地址
	//微信支付统一接口的回调action
	 public final static String NOTIFY_URL = "http://weipaike.net/fund/charge/rechargeSuccess";
	 public final static String NOTIFY_URL_PAY = "http://weipaike.net/fund/charge/paySuccess";
	//微信支付成功支付后跳转的地址
	 public final static String SUCCESS_URL = "http://weipaike.net/user";
	 //oauth2授权时回调action
	 public final static String REDIRECT_URI = "http://weipaike.net/fund/charge/recharge";
	/**
	 * 微信基础接口地址
	 */
	 //获取token接口(GET)
	 public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	 //oauth2授权接口(GET)
	 public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	 //刷新access_token接口（GET）
	 public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	// 菜单创建接口（POST）
	 public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 菜单查询（GET）
	 public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除（GET）
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * 微信支付接口地址
	 */
	//微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	//退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	//短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	//接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
}
