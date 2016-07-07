/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层PO类 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 成都蓝海飞鱼科技有限公司开发人员 <p>
 * <strong> 编写时间：</strong> 2015-2016 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
package com.lhfeiyu.vo;

import com.alibaba.fastjson.JSONObject;

public class ChatMsgDomain {
	
	private String avatar;//
	private String name;//
	private String userId;//
	private String receiverId;//
	private String singleOrGroup;//
	private String serial;//
	private String msgGroup;//
	private String price;//
	private String offerPrice;//
	private String auctionGoodsId;//
	
	public String toJSONString(){
		JSONObject json = new JSONObject();
        json.put("avt", avatar);
        json.put("name", name);
        json.put("uId", userId);
        json.put("rId", receiverId);
        json.put("sog", singleOrGroup);
        json.put("srl", serial);
        json.put("mg", msgGroup);
        json.put("price", price);
        json.put("offerPrice", offerPrice);
        json.put("agId", auctionGoodsId);
    	String jsonStr = json.toJSONString();
    	jsonStr = jsonStr.replace("\"","'");
    	return jsonStr;
	}

	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getReceiverId() {
		return receiverId;
	}


	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}


	public String getSingleOrGroup() {
		return singleOrGroup;
	}


	public void setSingleOrGroup(String singleOrGroup) {
		this.singleOrGroup = singleOrGroup;
	}


	public String getSerial() {
		return serial;
	}


	public void setSerial(String serial) {
		this.serial = serial;
	}


	public String getMsgGroup() {
		return msgGroup;
	}


	public void setMsgGroup(String msgGroup) {
		this.msgGroup = msgGroup;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getOfferPrice() {
		return offerPrice;
	}


	public void setOfferPrice(String offerPrice) {
		this.offerPrice = offerPrice;
	}


	public String getAuctionGoodsId() {
		return auctionGoodsId;
	}


	public void setAuctionGoodsId(String auctionGoodsId) {
		this.auctionGoodsId = auctionGoodsId;
	}
	
}
	