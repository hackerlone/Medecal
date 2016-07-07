package com.lhfeiyu.util.dust;

import java.util.Date;

/**
 * <strong>@ClassName: PoGeneralFields(Po层实体类公用属性)</strong><p>
 * <strong>@Author: 王科</strong><p>
 * <strong>@Date: 2014年1月2日 下午8:19:43</strong><p>
 * <strong>@Description: Po层实体类公用属性</strong><p>
 * <strong>@UpdateAuthor: 无</strong><p>
 * <strong>@UpdateDate: 无</strong><p>
 * <strong>@Description: 无</strong><p>
 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
 * <strong>@version:</strong>v1.0<p>
 */
public class PoGeneralFields {

	/** 编号 */
	private int id;
	/** 创建人编号 */
	private int foundId;
	/** 创建人名称 */
	private String founder;
	/** 创建时间 */
	private Date foundTime;
	/** 修改人编号 */
	private int editId;
	/** 修改人名称 */
	private String editor;
	/** 修改时间 */
	private Date editTime;
	/** 备注 */
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFoundId() {
		return foundId;
	}

	public void setFoundId(int foundId) {
		this.foundId = foundId;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public Date getFoundTime() {
		return foundTime;
	}

	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
	}

	public int getEditId() {
		return editId;
	}

	public void setEditId(int editId) {
		this.editId = editId;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
