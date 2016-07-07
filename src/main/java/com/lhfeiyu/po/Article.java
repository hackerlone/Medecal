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
public class Article extends Parent {

	/**============================== 自定义字段 开始 _@CAUTION_SELF_FIELD_BEGIN@_ ==============================*/
	private String typeName;
	/**============================== 自定义字段 结束 _@CAUTION_SELF_FIELD_FINISH@_ ==============================*/

	/** 自增整型ID  */
	private Integer id;
	
	/** 栏目ID  */
	private Integer catId;
	
	/** 角色ID（固定位置的文章标识）  */
	private Integer roleId;
	
	/** 标题  */
	private String title;
	
	/** 子标题  */
	private String subTitle;
	
	/** 描述  */
	private String description;
	
	/** 图片路径  */
	private String picPaths;
	
	/** 内容  */
	private String content;
	
	/** 公司，团队，机构  */
	private String organization;
	
	/** 地址  */
	private String address;
	
	/** 开始时间  */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	
	/**   */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	
	/** 链接地址  */
	private String linkUrl;
	
	/** 自定义属性1  */
	private String attr1;
	
	/** 自定义属性2  */
	private String attr2;
	
	/** 是否热门(1：否，2：是）  */
	private Integer isHot;
	
	/** 是否推荐(1：否，2：是）  */
	private Integer isRecommend;
	
	/** 是否首页展示(1：否，2：是）  */
	private Integer isShowIndex;
	
	/** 是否精华(1：否，2：是）  */
	private Integer isGood;
	
	/** 是否置顶(1：否，2：是）  */
	private Integer isTop;
	
	/** 用户ID  */
	private Integer userId;
	
	/** 作者  */
	private String author;
	
	/** 关键字  */
	private String keywords;
	
	/** 标签  */
	private String tags;
	
	/** 附件ID  */
	private Integer fileId;
	
	/** 附件ID串  */
	private String fileIds;
	
	/** 附件ID  */
	private Integer attachId;
	
	/** 浏览次数  */
	private Integer scans;
	
	/** 点击次数  */
	private Integer hits;
	
	/** 是否允许评论  */
	private Integer allowComment;
	
	/** 来源  */
	private String orgin;
	
	/** 序号  */
	private String serial;
	
	/**   */
	private Integer subTypeId;
	
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
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicPaths() {
		return picPaths;
	}
	public void setPicPaths(String picPaths) {
		this.picPaths = picPaths;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getIsShowIndex() {
		return isShowIndex;
	}
	public void setIsShowIndex(Integer isShowIndex) {
		this.isShowIndex = isShowIndex;
	}
	public Integer getIsGood() {
		return isGood;
	}
	public void setIsGood(Integer isGood) {
		this.isGood = isGood;
	}
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public Integer getScans() {
		return scans;
	}
	public void setScans(Integer scans) {
		this.scans = scans;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getAllowComment() {
		return allowComment;
	}
	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}
	public String getOrgin() {
		return orgin;
	}
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Integer getSubTypeId() {
		return subTypeId;
	}
	public void setSubTypeId(Integer subTypeId) {
		this.subTypeId = subTypeId;
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
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**=========================== 自定义GETSET方法结束 _@CAUTION_SELF_GETSET_FINISH@_ ===========================*/
	
}