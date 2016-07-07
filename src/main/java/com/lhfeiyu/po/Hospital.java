package com.lhfeiyu.po;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSONObject;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层对象 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司<p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0<p>
 */
public class Hospital extends Parent {

	/**============================== 自定义字段 开始 _@CAUTION_SELF_FIELD_BEGIN@_ ==============================*/
	private String provinceName;
	private String cityName;
	
	/**============================== 自定义字段 结束 _@CAUTION_SELF_FIELD_FINISH@_ ==============================*/

	/** 自增整型ID  */
	private Integer id;
	
	/** 简称  */
	private String briefName;
	
	/** 全称  */
	private String wholeName;
	
	/** 负责人  */
	private Integer adminId;
	
	/** 用户密码  */
	private String password;
	
	/** 图标  */
	private String logo;
	
	/** LOGO-图片表中对应的ID  */
	private Integer logoPicId;
	
	/**   */
	private String website;
	
	/** 省  */
	private Integer province;
	
	/** 市  */
	private Integer city;
	
	/** 地址  */
	private String address;
	
	/** 邮箱  */
	private String email;
	
	/** 登录id  */
	private String loginName;
	
	/** 最后登录时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;
	
	/** 手机  */
	private String phone;
	
	/** 座机  */
	private String tel;
	
	/** 传真  */
	private String fax;
	
	/** QQ  */
	private String qq;
	
	/** 微信  */
	private String weixin;
	
	/** 微博  */
	private String weibo;
	
	/** 二维码  */
	private String qrCode;
	
	/** 二维码2  */
	private String qrCode2;
	
	/** 客服名称  */
	private String csName;
	
	/** 客服邮箱  */
	private String csEmail;
	
	/** 客服手机  */
	private String csPhone;
	
	/** 客服QQ  */
	private String csQq;
	
	/** 客服微信  */
	private String csWeixin;
	
	/** 主页皮肤ID  */
	private Integer skinId;
	
	/** 能否血液检测(1.能2.不能)  */
	private Integer bloodTest;
	
	/** 职位ID串  */
	private String positionIds;
	
	/** 职位名称  */
	private String positionNames;
	
	/** 职称ID串  */
	private String titleIds;
	
	/** 职称名称  */
	private String titleNames;
	
	/** 标签  */
	private String tags;
	
	/** 短语  */
	private String words;
	
	/** 简介  */
	private String introduction;
	
	/** 序号  */
	private String serial;
	
	/** 类型ID  */
	private Integer typeId;
	
	/** 关联ID  */
	private Integer linkId;
	
	/** 组ID  */
	private Integer groupId;
	
	/** 等级  */
	private Integer gradeId;
	
	/** 业务状态  */
	private Integer mainStatus;
	
	/** 逻辑状态  */
	private Integer logicStatus;
	
	/** BigDecimal  */
	private BigDecimal attrDecimal;
	
	/** ID串  */
	private String mainIds;
	
	/** 备用字段-字符串  */
	private String attrStr;
	
	/** 备用字段-字符串2  */
	private String attrStr2;
	
	/** 备用字段-整型  */
	private Integer attrInt;
	
	/** 备用字段-整型2  */
	private Integer attrInt2;
	
	/** 备用字段-数字  */
	private BigDecimal attrNum;
	
	/** 备用字段-日期  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date attrDate;
	
	/** 备用字段-日期时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date attrDatetime;
	
	/** 排列顺序  */
	private Integer sequence;
	
	/** 描述  */
	private String description;
	
	/** 备注  */
	private String remark;
	
	/** 删除时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deletedAt;
	
	/** 删除人  */
	private String deletedBy;
	
	/** 创建时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	
	/** 创建人  */
	private String createdBy;
	
	/** 更新时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;
	
	/** 更新人  */
	private String updatedBy;
	
	
	public String toString(){
    	return JSONObject.toJSONString(this);
    }
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBriefName() {
		return briefName;
	}
	public void setBriefName(String briefName) {
		this.briefName = briefName;
	}
	public String getWholeName() {
		return wholeName;
	}
	public void setWholeName(String wholeName) {
		this.wholeName = wholeName;
	}
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getLogoPicId() {
		return logoPicId;
	}
	public void setLogoPicId(Integer logoPicId) {
		this.logoPicId = logoPicId;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getWeibo() {
		return weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getQrCode2() {
		return qrCode2;
	}
	public void setQrCode2(String qrCode2) {
		this.qrCode2 = qrCode2;
	}
	public String getCsName() {
		return csName;
	}
	public void setCsName(String csName) {
		this.csName = csName;
	}
	public String getCsEmail() {
		return csEmail;
	}
	public void setCsEmail(String csEmail) {
		this.csEmail = csEmail;
	}
	public String getCsPhone() {
		return csPhone;
	}
	public void setCsPhone(String csPhone) {
		this.csPhone = csPhone;
	}
	public String getCsQq() {
		return csQq;
	}
	public void setCsQq(String csQq) {
		this.csQq = csQq;
	}
	public String getCsWeixin() {
		return csWeixin;
	}
	public void setCsWeixin(String csWeixin) {
		this.csWeixin = csWeixin;
	}
	public Integer getSkinId() {
		return skinId;
	}
	public void setSkinId(Integer skinId) {
		this.skinId = skinId;
	}
	public Integer getBloodTest() {
		return bloodTest;
	}
	public void setBloodTest(Integer bloodTest) {
		this.bloodTest = bloodTest;
	}
	public String getPositionIds() {
		return positionIds;
	}
	public void setPositionIds(String positionIds) {
		this.positionIds = positionIds;
	}
	public String getPositionNames() {
		return positionNames;
	}
	public void setPositionNames(String positionNames) {
		this.positionNames = positionNames;
	}
	public String getTitleIds() {
		return titleIds;
	}
	public void setTitleIds(String titleIds) {
		this.titleIds = titleIds;
	}
	public String getTitleNames() {
		return titleNames;
	}
	public void setTitleNames(String titleNames) {
		this.titleNames = titleNames;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getLinkId() {
		return linkId;
	}
	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public Integer getMainStatus() {
		return mainStatus;
	}
	public void setMainStatus(Integer mainStatus) {
		this.mainStatus = mainStatus;
	}
	public Integer getLogicStatus() {
		return logicStatus;
	}
	public void setLogicStatus(Integer logicStatus) {
		this.logicStatus = logicStatus;
	}
	public BigDecimal getAttrDecimal() {
		return attrDecimal;
	}
	public void setAttrDecimal(BigDecimal attrDecimal) {
		this.attrDecimal = attrDecimal;
	}
	public String getMainIds() {
		return mainIds;
	}
	public void setMainIds(String mainIds) {
		this.mainIds = mainIds;
	}
	public String getAttrStr() {
		return attrStr;
	}
	public void setAttrStr(String attrStr) {
		this.attrStr = attrStr;
	}
	public String getAttrStr2() {
		return attrStr2;
	}
	public void setAttrStr2(String attrStr2) {
		this.attrStr2 = attrStr2;
	}
	public Integer getAttrInt() {
		return attrInt;
	}
	public void setAttrInt(Integer attrInt) {
		this.attrInt = attrInt;
	}
	public Integer getAttrInt2() {
		return attrInt2;
	}
	public void setAttrInt2(Integer attrInt2) {
		this.attrInt2 = attrInt2;
	}
	public BigDecimal getAttrNum() {
		return attrNum;
	}
	public void setAttrNum(BigDecimal attrNum) {
		this.attrNum = attrNum;
	}
	public Date getAttrDate() {
		return attrDate;
	}
	public void setAttrDate(Date attrDate) {
		this.attrDate = attrDate;
	}
	public Date getAttrDatetime() {
		return attrDatetime;
	}
	public void setAttrDatetime(Date attrDatetime) {
		this.attrDatetime = attrDatetime;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public String getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
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
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	/**=========================== 自定义GETSET方法开始 _@CAUTION_SELF_GETSET_BEGIN@_ ===========================*/
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	/**=========================== 自定义GETSET方法结束 _@CAUTION_SELF_GETSET_FINISH@_ ===========================*/
	
}