/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层PO类 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 成都蓝海飞鱼科技有限公司开发人员 <p>
 * <strong> 编写时间：</strong> 2015-2016 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
package com.lhfeiyu.vo;

import java.util.Date;

public class ChatGroup {
	
	private Integer id;
	private String serial;
	private Integer userId;
	private Date createdAt;
	private String createdBy;
	
	private String groupId;//群组ID
	private String groupName;//群组名称
	private String groupType;//1: 同学  2: 朋友 3: 同事 默认1
	private String province;//群组省份
	private String city;//群组城市
	private String type;//群组人数范围 1 100人 2 300人 3 500人 4 1000人 5 2000人 默认为1
	private String scope;//群组人数范围 1 100人 2 300人 3 500人 4 1000人 5 2000人 默认为1
	private String declared;//群组描述
	private String permission;//群组权限 必选  1：默认可直接加入  2：需要身份验证    3:私有群组（不能主动加入，仅能管理员邀请） 默认为1
	private String mode;//创建者退出，群组是否解散 必选  1: 不解散 2: 解散 默认1
	private String groupDomain;//扩展信息
	
	private String appId;
	private String thirdName;
	private String thirdPassword;
	private String timeStamp;
	private String selfAccount;
	
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getDeclared() {
		return declared;
	}
	public void setDeclared(String declared) {
		this.declared = declared;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getGroupDomain() {
		return groupDomain;
	}
	public void setGroupDomain(String groupDomain) {
		this.groupDomain = groupDomain;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getThirdName() {
		return thirdName;
	}
	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}
	public String getThirdPassword() {
		return thirdPassword;
	}
	public void setThirdPassword(String thirdPassword) {
		this.thirdPassword = thirdPassword;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSelfAccount() {
		return selfAccount;
	}
	public void setSelfAccount(String selfAccount) {
		this.selfAccount = selfAccount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
	