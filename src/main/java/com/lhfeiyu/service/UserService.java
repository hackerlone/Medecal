package com.lhfeiyu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.AssetsPath;
import com.lhfeiyu.dao.BespeakMapper;
import com.lhfeiyu.dao.DiagnoseMapper;
import com.lhfeiyu.dao.PictureMapper;
import com.lhfeiyu.dao.UserMapper;
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.po.Diagnose;
import com.lhfeiyu.po.User;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RegexUtil;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-用户-User <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class UserService extends CommonService<User> {
	@Autowired
	UserMapper userMapper;
	@Autowired
	DiagnoseMapper diagnoseMapper;
	@Autowired
	BespeakMapper bespeakMapper;
	@Autowired
	PictureMapper pictureMapper;
	
	public User addRegUser(JSONObject json, User user, String ip){
		resetUserField(user, ip);
		return user;
	}
	
	public JSONObject validateRegUser(JSONObject json, User user){
		validateUserMajor(json, user);	//验证字段合法性
		if(Result.hasError(json))return json;
		validateUserExist(json, user); //判断是否为重复用户
		return json;
	}
	
	/** 重置用户的重要字段 */
	private User resetUserField(User user,String ip){
		Date date = new Date();
		User newUser = new User();
		newUser.setSerial(CommonGenerator.getSerialByDate("u"));
		newUser.setUsername(user.getUsername());
		newUser.setPhone(user.getPhone());
		newUser.setPassword(Md5Util.encrypt(user.getPassword()));//密码MD5加密
		newUser.setMainStatus(1);//启用
		newUser.setIdcardNum(user.getIdcardNum());
		newUser.setLastLoginTime(date);
		newUser.setCreatedAt(date);
		newUser.setCreatedBy("-reg-");
		newUser.setAvatar(AssetsPath.defaultUserAvatar);
		userMapper.insertSelective(newUser);
		return newUser;
	}
	
	/** 用户进行注册时验证：用户名，邮箱，密码 等 */
	private JSONObject validateUserMajor(JSONObject json,User user){
		String password = user.getPassword();
		String phone = user.getPhone();
		//String username = user.getUsername();
		//String email = user.getEmail();
		if(!phone.matches(RegexUtil.phone_regexp)){
			return Result.failure(json, "手机号码错误", "phone_type_error");
		}
		if(null == password || "".equals(password.trim()) || password.trim().length()<6 || password.trim().length()>20){
			return Result.failure(json, "密码长度应该在6个字符到20个字符之间", "password_length_error");
		}
		if(!password.matches(RegexUtil.non_special_char_regexp)){
			return Result.failure(json, "密码不能包含特殊字符", "password_specialChar_error");
		}
		
		/*
		if(null == username || "".equals(username.trim()) || username.trim().length()<5 || username.trim().length()>20){
			json.put("msg", "用户名长度应该在5个字符到20个字符之间");
			json.put("status", "username_length_error");
			return json;
		}
		if(!username.matches(RegexUtil.non_special_char_regexp)){
			json.put("msg", "用户名不能包含特殊字符");
			json.put("status", "username_specialChar_error");
			return json;
		}

		if(null == email || "".equals(email.trim()) || email.trim().length()<5 || email.trim().length()>20){
			json.put("msg", "邮箱长度应该在5个字符到20个字符之间");
			json.put("status", "username_length_error");
			return json;
		}
		if(!email.matches(RegexUtil.non_special_char_regexp)){
			json.put("msg", "邮箱不能包含特殊字符");
			json.put("status", "username_specialChar_error");
			return json;
		}
		if(!email.matches(RegexUtil.email_regexp)){
			json.put("msg", "邮箱格式不正确");
			json.put("status", "username_specialChar_error");
			return json;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", user.getUsername());
		List<User> usernameList = service.selectListByCondition(map);
		if(null != usernameList && usernameList.size()>0){//检查数据库是否已存在该用户名
			json.put("msg", "用户名已存在");
			json.put("status", "username_already_exist");
			return json;
		}
		map.put("email", user.getEmail());
		List<User> emailList = service.selectListByCondition(map);
		if(null != emailList && emailList.size()>0){//检查数据库是否已存在该邮箱
			json.put("msg", "邮箱已存在");
			json.put("status", "email_already_exist");
			return json;
		}*/
		return json;
	}
	

	/**验证用户是否存在**/
	private JSONObject validateUserExist(JSONObject json, User user){
		Map<String,Object> map = CommonGenerator.getHashMap();
		map.put("phone", user.getPhone());
		User dbUser = userMapper.selectByCondition(map);
		if(null != dbUser){
			Result.failure(json, "用户已经存在", "user_exist");
		}
		return json;
		
	}

	public ModelMap getUserData(ModelMap modelMap,User user) {
		modelMap.put("user", user);
		return modelMap;
	}

	public ModelMap getPatientData(ModelMap modelMap, User user) {
		String idcardNum = user.getIdcardNum();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("patientIdcardNum", idcardNum);
		Diagnose diagnose = diagnoseMapper.selectByCondition(map);
		if(null == diagnose){
			Result.failure(modelMap, "暂无病历信息", null);
		}
		modelMap.put("diagnose", diagnose);
		return modelMap;
	}
	
	public int updateUser(JSONObject json, User user, User session_user) {
		Date date = new Date();
		session_user = userMapper.selectByPrimaryKey(session_user.getId());
		String username = session_user.getUsername();
		Integer userId = session_user.getId();
		user.setId(userId);
		user.setUpdatedBy(username);
		user.setUpdatedAt(date);
		
		String newAvatar = user.getAvatar();
		String oldAvatar = session_user.getAvatar();
		
		if(Check.isNotNull(newAvatar) && Check.isNotNull(oldAvatar) && !newAvatar.equals(oldAvatar)){//路径不相等，删除之前的头像
			Integer avatarPicId = session_user.getAvatarPicId();
			if(Check.isNotNull(avatarPicId)){
				pictureMapper.deleteByPrimaryKey(avatarPicId);
			}
		}
		return super.updateByPrimaryKeySelective(user);
	}

	public int updateService(JSONObject json, User user, User session_user) {
		Date date = new Date();
		String username = session_user.getUsername();
		Integer userId = session_user.getId();
		user.setUpdatedBy(username);
		user.setUpdatedAt(date);
		user.setRelationId(userId);
		return super.updateByPrimaryKeySelective(user);
	}

	public int insertService(JSONObject json, User user, User session_user) {
		Date date = new Date();
		String username = session_user.getUsername();
		Integer userId = session_user.getId();
		user.setCreatedBy(username);
		user.setCreatedAt(date);
		user.setRelationId(userId);
		user.setMainStatus(1);
		if(Check.isNull(user.getAvatar())){
			user.setAvatar(AssetsPath.defaultUserAvatar);
		}
		return super.insert(user);
	}

	public ModelMap getBespeakData(ModelMap modelMap, User user, Integer bespeakId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", bespeakId);
		Bespeak bespeak = bespeakMapper.selectByCondition(map);
		modelMap.put("bespeak", bespeak);
		return modelMap;
	}
	
	
	
}