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
public class UserFund extends Parent {

	/**============================== 自定义字段 开始 _@CAUTION_SELF_FIELD_BEGIN@_ ==============================*/
	
	/**============================== 自定义字段 结束 _@CAUTION_SELF_FIELD_FINISH@_ ==============================*/

	/**   */
	private Integer id;
	
	/** 用户ID  */
	private Integer userId;
	
	/** 冻结资金  */
	private BigDecimal frozenMoney;
	
	/** 可用金额  */
	private BigDecimal avaliableMoney;
	
	/** 积分  */
	private BigDecimal integral;
	
	/** 其他资产  */
	private BigDecimal otherFund;
	
	/** 支付密码  */
	private String payPassword;
	
	/**   */
	private Integer bankId1;
	
	/** 银行卡1  */
	private String bankCard1;
	
	/**   */
	private Integer bankId2;
	
	/** 银行卡2  */
	private String bankCard2;
	
	/**   */
	private Integer bankId3;
	
	/** 银行卡3  */
	private String bankCard3;
	
	/**   */
	private Integer bankId4;
	
	/** 银行卡4  */
	private String bankCard4;
	
	/**   */
	private Integer bankId5;
	
	/** 银行卡5  */
	private String bankCard5;
	
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
	
	/** 备用字段-字符串  */
	private String attrStr;
	
	/** 备用字段-整型  */
	private Integer attrInt;
	
	/** 排列顺序  */
	private Integer sequence;
	
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public BigDecimal getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(BigDecimal frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public BigDecimal getAvaliableMoney() {
		return avaliableMoney;
	}
	public void setAvaliableMoney(BigDecimal avaliableMoney) {
		this.avaliableMoney = avaliableMoney;
	}
	public BigDecimal getIntegral() {
		return integral;
	}
	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}
	public BigDecimal getOtherFund() {
		return otherFund;
	}
	public void setOtherFund(BigDecimal otherFund) {
		this.otherFund = otherFund;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public Integer getBankId1() {
		return bankId1;
	}
	public void setBankId1(Integer bankId1) {
		this.bankId1 = bankId1;
	}
	public String getBankCard1() {
		return bankCard1;
	}
	public void setBankCard1(String bankCard1) {
		this.bankCard1 = bankCard1;
	}
	public Integer getBankId2() {
		return bankId2;
	}
	public void setBankId2(Integer bankId2) {
		this.bankId2 = bankId2;
	}
	public String getBankCard2() {
		return bankCard2;
	}
	public void setBankCard2(String bankCard2) {
		this.bankCard2 = bankCard2;
	}
	public Integer getBankId3() {
		return bankId3;
	}
	public void setBankId3(Integer bankId3) {
		this.bankId3 = bankId3;
	}
	public String getBankCard3() {
		return bankCard3;
	}
	public void setBankCard3(String bankCard3) {
		this.bankCard3 = bankCard3;
	}
	public Integer getBankId4() {
		return bankId4;
	}
	public void setBankId4(Integer bankId4) {
		this.bankId4 = bankId4;
	}
	public String getBankCard4() {
		return bankCard4;
	}
	public void setBankCard4(String bankCard4) {
		this.bankCard4 = bankCard4;
	}
	public Integer getBankId5() {
		return bankId5;
	}
	public void setBankId5(Integer bankId5) {
		this.bankId5 = bankId5;
	}
	public String getBankCard5() {
		return bankCard5;
	}
	public void setBankCard5(String bankCard5) {
		this.bankCard5 = bankCard5;
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
	public String getAttrStr() {
		return attrStr;
	}
	public void setAttrStr(String attrStr) {
		this.attrStr = attrStr;
	}
	public Integer getAttrInt() {
		return attrInt;
	}
	public void setAttrInt(Integer attrInt) {
		this.attrInt = attrInt;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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
	
	/**=========================== 自定义GETSET方法结束 _@CAUTION_SELF_GETSET_FINISH@_ ===========================*/
	
}