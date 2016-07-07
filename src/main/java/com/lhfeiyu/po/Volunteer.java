package com.lhfeiyu.po;
/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层com.lhfeiyu.po/Volunteer.java <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 王家明 <p>
 * <strong> 编写时间：</strong>2016年7月5日 下午5:51:53<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class Volunteer {
	private Integer id;

	private String address;

	private String avatar;

	private Integer city;

	private String email;

	private String phone;

	private Integer province;

	private String qq;

	private int sex;

	private String username;

	private String weixin;
	
	private String deleted_at;
	
	
	/**============================== 自定义字段 开始 _@CAUTION_SELF_FIELD_BEGIN@_ ==============================*/
	private String provinceName;
	private String sexName;
	private String hospitalName;
	private String cityName;
	/**============================== 自定义字段 结束 _@CAUTION_SELF_FIELD_FINISH@_ ==============================*/

	
	
	public Volunteer() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Volunteer(Integer id, String address, String avatar, Integer city, String email, String phone, Integer province,
			String qq, int sex, String username, String weixin, String deleted_at) {
		super();
		this.id = id;
		this.address = address;
		this.avatar = avatar;
		this.city = city;
		this.email = email;
		this.phone = phone;
		this.province = province;
		this.qq = qq;
		this.sex = sex;
		this.username = username;
		this.weixin = weixin;
		this.deleted_at = deleted_at;
	}



	public String getDeleted_at() {
		return deleted_at;
	}



	public void setDeleted_at(String deleted_at) {
		this.deleted_at = deleted_at;
	}



	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	
	
	
	/**=========================== 自定义GETSET方法开始 _@CAUTION_SELF_GETSET_BEGIN@_ ===========================*/
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	/**=========================== 自定义GETSET方法结束 _@CAUTION_SELF_GETSET_FINISH@_ ===========================*/
	
}
