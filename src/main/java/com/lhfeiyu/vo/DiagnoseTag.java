package com.lhfeiyu.vo;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> VO:诊断结果标签 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 成都蓝海飞鱼科技有限公司开发人员 <p>
 * <strong> 编写时间：</strong> 2015-2016 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class DiagnoseTag {
	
	private Integer id;
	private String serial;
	private Integer diagnoseId;
	private String tagName;
	
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
	public Integer getDiagnoseId() {
		return diagnoseId;
	}
	public void setDiagnoseId(Integer diagnoseId) {
		this.diagnoseId = diagnoseId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	


}
	