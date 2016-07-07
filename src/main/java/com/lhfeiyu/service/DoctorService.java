package com.lhfeiyu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.AssetsPath;
import com.lhfeiyu.dao.AdvertisementMapper;
import com.lhfeiyu.dao.AnnouncementMapper;
import com.lhfeiyu.dao.ArticleMapper;
import com.lhfeiyu.dao.BespeakMapper;
import com.lhfeiyu.dao.DiagnoseMapper;
import com.lhfeiyu.dao.DoctorMapper;
import com.lhfeiyu.dao.FansMapper;
import com.lhfeiyu.dao.PictureMapper;
import com.lhfeiyu.po.Advertisement;
import com.lhfeiyu.po.Announcement;
import com.lhfeiyu.po.Article;
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Fans;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Doctor <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class DoctorService extends CommonService<Doctor> {

	@Autowired
	DoctorMapper doctormapper;
	@Autowired
	ArticleMapper articleMapper;
	@Autowired
	FansMapper fansMapper;
	@Autowired
	DiagnoseMapper diagnoseMapper;
	@Autowired
	AnnouncementMapper announcementMapper;
	@Autowired
	AdvertisementMapper advertisementMapper;
	@Autowired
	BespeakMapper bespeakMapper;
	@Autowired
	PictureMapper pictureMapper;
	
	public Doctor selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<Doctor> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(JSONObject json,Doctor doctor,Doctor sessionDoctor) {
		String username = sessionDoctor.getUsername();
		doctor.setUpdatedBy(username);
		sessionDoctor = doctormapper.selectByPrimaryKey(sessionDoctor.getId());
		String newAvatar = doctor.getAvatar();
		String oldAvatar = sessionDoctor.getAvatar();
		
		if(Check.isNotNull(newAvatar) && Check.isNotNull(oldAvatar) && !newAvatar.equals(oldAvatar)){//路径不相等，删除之前的头像
			Integer avatarPicId = sessionDoctor.getAvatarPicId();
			if(Check.isNotNull(avatarPicId)){
				pictureMapper.deleteByPrimaryKey(avatarPicId);
			}
		}
		return super.updateByPrimaryKeySelective(doctor);
	}
	
	public int insertService(JSONObject json,Doctor doctor, Hospital hospital) {
		Date date = new Date();
		doctor.setMainStatus(1);
		String password = doctor.getPassword();
		Integer hospitalId = hospital.getId();
		if(null != password){
			password = Md5Util.encrypt(password);
			doctor.setPassword(password);
		}
		doctor.setHospitalId(hospitalId);
		if(Check.isNull(doctor.getAvatar())){
			doctor.setAvatar(AssetsPath.defaultDoctorAvatar);
		}
		doctor.setCreatedBy(hospital.getWholeName());
		doctor.setCreatedAt(date);
		return super.insertSelective(doctor);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}
	
	public int updateDoctorDelete(JSONObject json, String ids, Hospital hospital) {
		Doctor doctor = new Doctor();
		doctor.setMainStatus(2);
		doctor.setIds(ids);
		doctor.setUpdatedBy(hospital.getWholeName());
		return super.updateByIdsSelective(doctor);
	}

	public ModelMap getDoctorData(ModelMap modelMap, Doctor db_doctor, Integer id) {
		Doctor doctor = getDoctorInformation(db_doctor,id);
		if(null == doctor){
			return Result.failure(modelMap, "您访问的医生信息不存在", "doctor_null");
		}
		modelMap = getHotDiscussionAndHealthInformation(modelMap,db_doctor,id);
		modelMap = getAnnouncementAndActivity(modelMap,doctor.getHospitalId());
		modelMap = getFansData(modelMap,db_doctor,id);
		modelMap = getArticleCountAndPatientCount(modelMap,db_doctor,id);
		modelMap = getDoctorArticle(modelMap,db_doctor,id);
		modelMap = getAdvertisement(modelMap);
		modelMap.put("doctor", doctor);
		return modelMap;
	}

	private ModelMap getDoctorArticle(ModelMap modelMap, Doctor db_doctor, Integer id) {
		Integer doctorId = null;
		if(null == id){
			doctorId = db_doctor.getId();
		}else{
			doctorId = id;
		}
		Map<String,Object> map = CommonGenerator.getHashMap();
		map.put("userId", doctorId);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 7);
		List<Article> articleList = articleMapper.selectListByCondition(map);
		modelMap.put("articleList", articleList);
		return modelMap;
	}

	private ModelMap getArticleCountAndPatientCount(ModelMap modelMap, Doctor db_doctor, Integer id) {
		Integer doctorId = null;
		if(null == id){
			doctorId = db_doctor.getId();
		}else{
			doctorId = id;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mainStatus", 1);
		map.put("userId", doctorId);
		Integer articleCount = articleMapper.selectCountByCondition(map);
			modelMap.put("articleCount", articleCount);
		map.clear();
		map.put("mainStatus", 1);
		map.put("doctorId", doctorId);
		Integer diagnoseCount = diagnoseMapper.selectCountByCondition(map);
			modelMap.put("diagnoseCount", diagnoseCount);
		return modelMap;
	}
	
	public ModelMap getHotDiscussionAndHealthInformation(ModelMap modelMap,Doctor db_doctor, Integer id){
		Integer doctorId = null;
		if(null == id){
			doctorId = db_doctor.getId();
		}else{
			doctorId = id;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeId", 43);
		map.put("mainStatus", 1);
		map.put("userId", doctorId);
		List<Article> hotDiscussionList = articleMapper.selectListByCondition(map);//热点讨论
			modelMap.put("hotDiscussionList", hotDiscussionList);
		map.clear();
		map.put("typeId", 42);
		map.put("mainStatus", 1);
		map.put("userId", doctorId);
		List<Article> healthInformationList = articleMapper.selectListByCondition(map);//健康资讯
			modelMap.put("healthInformationList", healthInformationList);
			return modelMap;
	}
	
	private ModelMap getAnnouncementAndActivity(ModelMap modelMap,Integer hospitalId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mainStatus", 1);
		map.put("hospitalId", hospitalId);
		map.put("count", 5);
		map.put("start", 0);
		List<Announcement> announcementList = announcementMapper.selectListByCondition(map);//公告
		modelMap.put("announcementList", announcementList);
		/*map.clear();
		map.put("typeId", 46);
		map.put("mainStatus", 1);
		List<Article> activityList = articleMapper.selectListByCondition(map);//活动
			modelMap.put("activityList", activityList);*/
		return modelMap;
	}
	
	private Doctor getDoctorInformation(Doctor db_doctor, Integer id){
		Doctor doctor = null;
		Integer doctorId = null;
		if(null == id){
			doctorId = db_doctor.getId();
		}else{
			doctorId = id;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", doctorId);
		doctor = doctormapper.selectByCondition(map);
		return doctor;
	}
	
	public ModelMap getFansData(ModelMap  modelMap, Doctor db_doctor, Integer doctorId){
		Doctor doctor = getDoctorInformation(db_doctor,doctorId);
		if(null == doctor){
			return Result.failure(modelMap, "您访问的医生信息不存在", "doctor_null");
		}
		if(null == doctorId){
			doctorId = doctor.getId();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", doctorId);
		map.put("mainStatus", 1);
		List<Fans> fansList = fansMapper.selectListByCondition(map);//粉丝或好友
		modelMap.put("fansList", fansList);
		modelMap.put("doctorId", doctorId);
		modelMap.put("doctor", doctor);
		return modelMap;
	}
	
	public ModelMap getAdvertisement(ModelMap modelMap){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("catId", 2);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 5);
		List<Advertisement> advertisementList = advertisementMapper.selectListByCondition(map);//首页广告
		modelMap.put("advertisementList", advertisementList);
		return modelMap;
	}
	
	public Doctor addRegDoctor(JSONObject json, Doctor doctor, String ip){
		//resetUserField(user, ip);
		return doctor;
	}
	
	public JSONObject validateRegDoctor(JSONObject json, Doctor doctor){
		
		return json;
	}

	public int updateService(JSONObject json, Doctor doctor, Hospital hospital) {
		Date date = new Date();
		Integer hospitalId = hospital.getId();
		doctor.setHospitalId(hospitalId);
		doctor.setUpdatedBy(hospital.getWholeName());
		doctor.setUpdatedAt(date);
		return super.updateByPrimaryKeySelective(doctor);
	}

	public ModelMap getBespeakData(ModelMap modelMap, Doctor session_doctor, Integer bespeakId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", bespeakId);
		Bespeak bespeak = bespeakMapper.selectByCondition(map);
		modelMap.put("bespeak", bespeak);
		return modelMap;
	}
	
	public List<Doctor> selectDiagnoseSumByCondition(Map<String, Object> map) {
		return doctormapper.selectDiagnoseSumByCondition(map);
	}
	
	
}