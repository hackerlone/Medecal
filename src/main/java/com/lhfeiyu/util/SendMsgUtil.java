package com.lhfeiyu.util;

public class SendMsgUtil {
	
	public static String send() throws Exception {
		String vcode = createRandomVcode();
		/*HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn");
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
		NameValuePair[] data = { 
				new NameValuePair("Uid", "本站用户名"),
				new NameValuePair("Key", "接口安全秘钥"),
				new NameValuePair("smsMob", "手机号码"),
				new NameValuePair("smsText", "验证码："+vcode) };
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
		System.out.println(result); // 打印返回消息状态

		post.releaseConnection();*/
		System.out.println("vcode:"+vcode);
		//return vcode;
		return "123456";
	}
	
	public static String createRandomVcode(){
        String vcode = "";//验证码
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 10);
        }
        return vcode;
    }
	
	public static void main(String[] args) throws Exception {
		send();
	}
}
