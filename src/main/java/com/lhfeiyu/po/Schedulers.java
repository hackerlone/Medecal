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
public class Schedulers extends Parent {

	/**============================== 自定义字段 开始 _@CAUTION_SELF_FIELD_BEGIN@_ ==============================*/
	
	/**============================== 自定义字段 结束 _@CAUTION_SELF_FIELD_FINISH@_ ==============================*/

	/**   */
	private Integer id;
	
	/** 任务类型ID  */
	private Integer taskTypeId;
	
	/** 通知ID  */
	private Integer noticeId;
	
	/** 关联连接地址  */
	private String linkUrl;
	
	/** 用户ID  */
	private Integer userId;
	
	/** 接收人ID  */
	private Integer receiverId;
	
	/** 接收人ID串  */
	private String receiverIds;
	
	/** 描述  */
	private String description;
	
	/** 已执行次数  */
	private Integer executedNum;
	
	/** 图片路径  */
	private String picPath;
	
	/** 图片ID  */
	private Integer picId;
	
	/** 等级  */
	private Integer grade;
	
	/** 是否起效  */
	private String isValid;
	
	/** 触发条件-key  */
	private String triggerKey;
	
	/** 触发条件-value  */
	private String triggerValue;
	
	/** 开始时间（触发时间）  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/** 结束时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
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
	public Integer getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverIds() {
		return receiverIds;
	}
	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getExecutedNum() {
		return executedNum;
	}
	public void setExecutedNum(Integer executedNum) {
		this.executedNum = executedNum;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getTriggerKey() {
		return triggerKey;
	}
	public void setTriggerKey(String triggerKey) {
		this.triggerKey = triggerKey;
	}
	public String getTriggerValue() {
		return triggerValue;
	}
	public void setTriggerValue(String triggerValue) {
		this.triggerValue = triggerValue;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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