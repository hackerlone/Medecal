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
public class Message extends Parent {

	/**============================== 自定义字段 开始 _@CAUTION_SELF_FIELD_BEGIN@_ ==============================*/
	private String receiverName;
	/**============================== 自定义字段 结束 _@CAUTION_SELF_FIELD_FINISH@_ ==============================*/

	/** 自增整型ID  */
	private Integer id;
	
	/** 诊所id  */
	private Integer hospitalId;
	
	/** 发送人ID  */
	private Integer senderId;
	
	/** 发送人名称  */
	private String senderName;
	
	/** 接收人ID  */
	private Integer receiverId;
	
	/** 接收人ID串  */
	private String receiverIds;
	
	/** 接收组ID  */
	private Integer receiverGroupId;
	
	/** 消息类型组  */
	private String msgGroup;
	
	/** 消息类型1:文本消息 2:语音消息 3:视频消息  4:图片消息  5:位置消息  6:文件  */
	private Integer contentType;
	
	/** 标题  */
	private String title;
	
	/** 主题  */
	private String subject;
	
	/** 内容  */
	private String content;
	
	/** 回复内容  */
	private String replyContent;
	
	/** 发送时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;
	
	/** 阅读时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date readTime;
	
	/** 隐私等级  */
	private Integer concealLevel;
	
	/**   */
	private String toAll;
	
	/** 有效期  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date expiryDate;
	
	/** 是否匿名  */
	private String anonymous;
	
	/** 是否置顶  */
	private Integer isTop;
	
	/** 附件ID  */
	private Integer fileId;
	
	/** 链接地址  */
	private String linkUrl;
	
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
	
	/** 业务状态(1.未读消息，2.已读消息)  */
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
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
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
	public Integer getReceiverGroupId() {
		return receiverGroupId;
	}
	public void setReceiverGroupId(Integer receiverGroupId) {
		this.receiverGroupId = receiverGroupId;
	}
	public String getMsgGroup() {
		return msgGroup;
	}
	public void setMsgGroup(String msgGroup) {
		this.msgGroup = msgGroup;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	public Integer getConcealLevel() {
		return concealLevel;
	}
	public void setConcealLevel(Integer concealLevel) {
		this.concealLevel = concealLevel;
	}
	public String getToAll() {
		return toAll;
	}
	public void setToAll(String toAll) {
		this.toAll = toAll;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	/**=========================== 自定义GETSET方法结束 _@CAUTION_SELF_GETSET_FINISH@_ ===========================*/
	
}