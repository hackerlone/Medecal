package com.lhfeiyu.thirdparty.wx.business;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.thirdparty.wx.util.CommonUtil;
import com.lhfeiyu.tools.Check;

public class Message {
	
	/**
	 * 从Property文件中读取微信的验证数据：access_token,ticktet
	 * 只有获取ticket时，需要传递access_token（从Property文件获取失败时远程获取的参数）
	 * @param field
	 */
	public static JSONObject sendMessage(String templateId, JSONObject msg){
		if(!Check.isNotNull(templateId))return null;
		if(null == msg)return null;
		String access_token = AuthAccess.getWxDataFromProperty("access_token");
		if(Check.isNotNull(access_token)){
			String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
			String result =CommonUtil.httpsRequestForStr(url, "POST", msg.toJSONString(), "application/json");//发送模板消息的固定类型
			JSONObject resultJson = JSONObject.parseObject(result);
			System.out.println("Message.sendMessage_templateId : "+templateId+"_____"+resultJson);//TODO 调试使用
			return resultJson;
		}
		return null;
	}
	
	/*public static JSONObject sendMessage(String templateId, JSONObject msg){
		return sendMessage(templateId, msg, null);
	}*/
	
	public static JSONObject sendKFMessage(MessageNews messageNews){
		if(null == messageNews)return null;
		String access_token = AuthAccess.getWxDataFromProperty("access_token");
		if(Check.isNotNull(access_token)){
			String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;
			/**
			 *http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E5.B8.90.E5.8F.B7.E7.AE.A1.E7.90.86
			 * {
			    "touser":"OPENID",
			    "msgtype":"news",
			    "news":{
			        "articles": [
			         {
			             "title":"Happy Day",
			             "description":"Is Really A Happy Day",
			             "url":"URL",
			             "picurl":"PIC_URL"
			         },
			         {
			             "title":"Happy Day",
			             "description":"Is Really A Happy Day",
			             "url":"URL",
			             "picurl":"PIC_URL"
			         }
			         ]
			    }
}
			 */
			System.out.println("news: "+JSONObject.toJSONString(messageNews));
			String result =CommonUtil.httpsRequestForStr(url, "POST", JSONObject.toJSONString(messageNews));
			JSONObject resultJson = JSONObject.parseObject(result);
			System.out.println("Message.sendKFMessage : "+resultJson);//TODO 调试使用
			return resultJson;
		}
		return null;
	}
	
	public static JSONObject buildMsg(String openId, String templateId, String url, 
			String firstName, String firstValue, 
			String attr1Name, String attr1Value, 
			String attr2Name, String attr2Value, 
			String attr3Name, String attr3Value, 
			String attr4Name, String attr4Value, 
			String remarkName, String remarkValue){
		
		JSONObject json = new JSONObject();
		json.put("touser", openId);//OPENID
		json.put("template_id", templateId);//template_id
		json.put("url", url);//url
		json.put("topcolor", "#FF0000");//topcolor
		
		JSONObject data = new JSONObject();
		
		if(Check.isNotNull(firstName) && Check.isNotNull(firstValue)){
			JSONObject first = new JSONObject();
			first.put("value", firstValue);//first-value
			first.put("color", "#173177");//first-color
			first.put(firstName, first);
		}
		if(Check.isNotNull(attr1Name) && Check.isNotNull(attr1Value)){
			JSONObject attr1 = new JSONObject();
			attr1.put("value", attr1Value);
			attr1.put("color", "#173177");
			data.put(attr1Name, attr1);
		}
		if(Check.isNotNull(attr2Name) && Check.isNotNull(attr2Value)){
			JSONObject attr2 = new JSONObject();
			attr2.put("value", attr2Value);
			attr2.put("color", "#173177");
			data.put(attr2Name, attr2);
		}
		if(Check.isNotNull(attr3Name) && Check.isNotNull(attr3Value)){
			JSONObject attr3 = new JSONObject();
			attr3.put("value", attr3Value);
			attr3.put("color", "#173177");
			data.put(attr3Name, attr3);
		}
		if(Check.isNotNull(attr4Name) && Check.isNotNull(attr4Value)){
			JSONObject attr4 = new JSONObject();
			attr4.put("value", attr4Value);
			attr4.put("color", "#173177");
			data.put(attr4Name, attr4);
		}
		if(Check.isNotNull(remarkName) && Check.isNotNull(remarkValue)){
			JSONObject remark = new JSONObject();
			remark.put("value", remarkValue);
			remark.put("color", "#173177");
			data.put(remarkName, remark);
		}
		
		json.put("data", data);//data
		
		return json;
		
		/*{
	           "touser":"OPENID",
	           "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
	           "url":"http://weixin.qq.com/download",       
	           "topcolor":"#FF0000",     
	           "data":{
	                   "first": {
	                       "value":"恭喜你购买成功！",
	                       "color":"#173177"
	                   },
	                   "keynote1":{
	                       "value":"巧克力",
	                       "color":"#173177"
	                   },
	                   "keynote2": {
	                       "value":"39.8元",
	                       "color":"#173177"
	                   },
	                   "keynote3": {
	                       "value":"2014年9月22日",
	                       "color":"#173177"
	                   },
	                   "remark":{
	                       "value":"欢迎再次购买！",
	                       "color":"#173177"
	                   }
	           }
	       }*/
	}
	
	
}
