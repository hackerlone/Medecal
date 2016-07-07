package com.lhfeiyu.po;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层对象
 * <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 王家明
 * <p>
 * <strong> 编写时间：</strong>2016年6月30日 上午10:14:44
 * <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司
 * <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0
 * <p>
 */
public class Student extends Parent {
	/** 姓名 */
	private String name;
	/** 地区 */
	private String area;
	/** 电话号码 */
	private String phone;
	/** 自增整型ID */
	private Integer id;
	private String sex;
	private String qq;
	private String weixin;
	private String avatar;

	private String deleted_at;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public Student(String name, String area, String phone, Integer id, String sex, String qq, String weixin,
			String avatar, String deleted_at) {
		super();
		this.name = name;
		this.area = area;
		this.phone = phone;
		this.id = id;
		this.sex = sex;
		this.qq = qq;
		this.weixin = weixin;
		this.avatar = avatar;
		this.deleted_at = deleted_at;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(String deleted_at) {
		this.deleted_at = deleted_at;
	}

}
