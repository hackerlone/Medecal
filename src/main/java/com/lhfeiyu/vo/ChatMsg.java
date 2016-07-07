/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层PO类 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 成都蓝海飞鱼科技有限公司开发人员 <p>
 * <strong> 编写时间：</strong> 2015-2016 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
package com.lhfeiyu.vo;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.ConstField;

public class ChatMsg {
	
	private Integer id;
	private String serial;
	private Integer userId;
	
	private String appId = ConstField.rl_ytx_appId;
	private String sender;//群组人数范围 1 100人 2 300人 3 500人 4 1000人 5 2000人 默认为1
	private String pushType;//推送类型，1：个人，2：群组，默认为1
	private String[] receiver;//接收者账号，如果是个人，最大上限100人/次，如果是群组，仅支持1个。
	private String  msgType = "1";//1：文本消息，2：语音消息，3：视频消息，4：图片消息，5：位置消息，6：文件
	private String msgContent;//信息内容
	private String msgDomain;//自定义信息内容
	private String msgFileName;//
	private String msgFileUrl;//
	
	public String toJSONString(){
		JSONObject json = new JSONObject();
        json.put("appId", ConstField.rl_ytx_appId);
        json.put("sender", sender);
        json.put("pushType", pushType);
        json.put("receiver", receiver);
        json.put("msgType", msgType);
        json.put("msgContent", msgContent);
        json.put("msgDomain", msgDomain);
        json.put("msgFileName", "");
        json.put("msgFileUrl", "");
        String jsonStr = json.toJSONString();
    	//jsonStr = jsonStr.replace("\"","'");
    	return jsonStr;
	}
	
	public String getAppId() {
		return appId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public String[] getReceiver() {
		return receiver;
	}
	public void setReceiver(String[] receiver) {
		this.receiver = receiver;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgDomain() {
		return msgDomain;
	}
	public void setMsgDomain(String msgDomain) {
		this.msgDomain = msgDomain;
	}
	public String getMsgFileName() {
		return msgFileName;
	}
	public void setMsgFileName(String msgFileName) {
		this.msgFileName = msgFileName;
	}
	public String getMsgFileUrl() {
		return msgFileUrl;
	}
	public void setMsgFileUrl(String msgFileUrl) {
		this.msgFileUrl = msgFileUrl;
	}

}
	