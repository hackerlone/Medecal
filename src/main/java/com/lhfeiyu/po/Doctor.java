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
public class Doctor extends Parent {

	/**============================== 自定义字段 开始 _@CAUTION_SELF_FIELD_BEGIN@_ ==============================*/
	private String provinceName;
	private String cityName;
	private String hospitalName;
	private String departmentName;
	private String hospitalProvinceName;
	private String sexName;
	private String hospitalLogo;
	private Integer consultCount;
	private Integer fansId;
	private String educationName;
	private Integer diagnoseCount;
	private Double diagnoseTotalPrice;
	/**============================== 自定义字段 结束 _@CAUTION_SELF_FIELD_FINISH@_ ==============================*/

	/** 自增整型ID  */
	private Integer id;
	
	/** 用户名  */
	private String username;
	
	/** 真实姓名  */
	private String realname;
	
	/** 用户密码  */
	private String password;
	
	/**   */
	private Integer sex;
	
	/**   */
	private String avatar;
	
	/** 头像-图片表中对应的ID  */
	private Integer avatarPicId;
	
	/** 邮箱  */
	private String email;
	
	/** 登录id  */
	private String loginName;
	
	/** 电话  */
	private String phone;
	
	/** 最后登录时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;
	
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
	
	/**   */
	private Integer province;
	
	/**   */
	private Integer city;
	
	/**   */
	private String address;
	
	/** 身份证号码  */
	private String idCard;
	
	/** 职位ID串  */
	private String positionIds;
	
	/** 职位名称  */
	private String positionNames;
	
	/** 职称ID串  */
	private String titleIds;
	
	/** 职称名称  */
	private String titleNames;
	
	/** 医院  */
	private Integer hospitalId;
	
	/** 科室ID  */
	private Integer departmentId;
	
	/** 善长领域ID串  */
	private String goodAtIds;
	
	/** 擅长领域  */
	private String goodAt;
	
	/** 学历  */
	private String educationCode;
	
	/** 咨询范围  */
	private String consultScope;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getAvatarPicId() {
		return avatarPicId;
	}
	public void setAvatarPicId(Integer avatarPicId) {
		this.avatarPicId = avatarPicId;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getGoodAtIds() {
		return goodAtIds;
	}
	public void setGoodAtIds(String goodAtIds) {
		this.goodAtIds = goodAtIds;
	}
	public String getGoodAt() {
		return goodAt;
	}
	public void setGoodAt(String goodAt) {
		this.goodAt = goodAt;
	}
	public String getEducationCode() {
		return educationCode;
	}
	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}
	public String getConsultScope() {
		return consultScope;
	}
	public void setConsultScope(String consultScope) {
		this.consultScope = consultScope;
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
	
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalProvinceName() {
		return hospitalProvinceName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public void setHospitalProvinceName(String hospitalProvinceName) {
		this.hospitalProvinceName = hospitalProvinceName;
	}

	public String getHospitalLogo() {
		return hospitalLogo;
	}

	public void setHospitalLogo(String hospitalLogo) {
		this.hospitalLogo = hospitalLogo;
	}

	public Integer getConsultCount() {
		return consultCount;
	}

	public void setConsultCount(Integer consultCount) {
		this.consultCount = consultCount;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public Integer getFansId() {
		return fansId;
	}

	public void setFansId(Integer fansId) {
		this.fansId = fansId;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}
	
	public Integer getDiagnoseCount() {
		return diagnoseCount;
	}

	public void setDiagnoseCount(Integer diagnoseCount) {
		this.diagnoseCount = diagnoseCount;
	}

	public Double getDiagnoseTotalPrice() {
		return diagnoseTotalPrice;
	}

	public void setDiagnoseTotalPrice(Double diagnoseTotalPrice) {
		this.diagnoseTotalPrice = diagnoseTotalPrice;
	}
	/**=========================== 自定义GETSET方法结束 _@CAUTION_SELF_GETSET_FINISH@_ ===========================*/
	
}