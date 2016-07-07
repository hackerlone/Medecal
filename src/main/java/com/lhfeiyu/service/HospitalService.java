package com.lhfeiyu.service;

import java.util.ArrayList;
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
import com.lhfeiyu.dao.DiagnoseTemplateMapper;
import com.lhfeiyu.dao.DoctorMapper;
import com.lhfeiyu.dao.HospitalMapper;
import com.lhfeiyu.dao.PictureMapper;
import com.lhfeiyu.po.Advertisement;
import com.lhfeiyu.po.Announcement;
import com.lhfeiyu.po.Article;
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.po.DiagnoseTemplate;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.User;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.vo.DiagnoseTag;
import com.lhfeiyu.vo.Prescription;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Hospital <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class HospitalService extends CommonService<Hospital> {

	@Autowired
	HospitalMapper hospitalmapper;
	@Autowired
	ArticleMapper articleMapper;
	@Autowired
	DiagnoseTemplateMapper diagnoseTemplateMapper;
	@Autowired
	DoctorMapper doctorMapper;
	@Autowired
	BespeakMapper bespeakMapper;
	@Autowired
	AnnouncementMapper announcementMapper;
	@Autowired
	AdvertisementMapper advertisementMapper;
	@Autowired
	PictureMapper pictureMapper;
	
	public Hospital selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<Hospital> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(Hospital hospital) {
		return super.updateByPrimaryKeySelective(hospital);
	}
	
	public int insertService(Hospital hospital) {
		return super.insertSelective(hospital);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public int insertService(JSONObject json,Hospital hospital, User user) {
		Date date = new Date();
		String username = user.getUsername();
		hospital.setMainStatus(1);
		hospital.setCreatedBy(username);
		hospital.setCreatedAt(date);
		if(Check.isNull(hospital.getLogo())){
			hospital.setLogo(AssetsPath.defaultHospitalLogo);
		}
		return super.insertSelective(hospital);
	}

	public int updateService(JSONObject json,Hospital hospital, Hospital db_hospital) {
		hospital.setUpdatedBy(db_hospital.getWholeName());
		db_hospital = hospitalmapper.selectByPrimaryKey(db_hospital.getId());
		String newAvatar = hospital.getLogo();
		String oldAvatar = db_hospital.getLogo();
		
		if(Check.isNotNull(newAvatar) && Check.isNotNull(oldAvatar) && !newAvatar.equals(oldAvatar)){//路径不相等，删除之前的头像
			Integer avatarPicId = db_hospital.getLogoPicId();
			if(Check.isNotNull(avatarPicId)){
				pictureMapper.deleteByPrimaryKey(avatarPicId);
			}
		}
		return super.updateByPrimaryKeySelective(hospital);
	}

	public int updateHospitalDelete(JSONObject json,String ids, Hospital db_hospital) {
		Hospital hospital = new Hospital();
		hospital.setMainStatus(2);
		hospital.setIds(ids);
		hospital.setUpdatedBy(db_hospital.getWholeName());
		return super.updateByIdsSelective(hospital);
	}

	public ModelMap getHospitalData(ModelMap modelMap, Hospital db_hospital, Integer id) {
		Hospital hospital = null;
		Integer hospitalId = null;
		if(null == id){
			hospitalId = db_hospital.getId();
		}else{
			hospitalId = id;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", hospitalId);
		hospital = hospitalmapper.selectByCondition(map);
		if(null == hospital){
			return Result.failure(modelMap, "您访问的诊所信息不存在", "hospital_null");
		}
		modelMap = getHotDiscussionAndHealthInformation(modelMap,hospitalId);
		modelMap = getAnnouncementAndActivity(modelMap,hospitalId);
		modelMap = getHospitalDoctorArticle(modelMap,hospitalId,null);
		modelMap = getAdvertisement(modelMap);
		modelMap.put("hospital", hospital);
		return modelMap;
	}
	
	public ModelMap getAdvertisement(ModelMap modelMap){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("catId", 3);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 5);
		List<Advertisement> advertisementList = advertisementMapper.selectListByCondition(map);//首页广告
		modelMap.put("advertisementList", advertisementList);
		return modelMap;
	}
	
	public ModelMap getHospitalDoctorArticle(ModelMap modelMap, Integer hospitalId,Integer typeId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hospitalId", hospitalId);
		map.put("mainStatus", 1);
		List<Doctor> doctorList = doctorMapper.selectListByCondition(map);
		if(doctorList.size() > 0){
			List<Article> articleListArray = new ArrayList<Article>();
			for(Doctor d: doctorList){
				map.clear();
				map.put("userId", d.getId());
				if(null != typeId){
					map.put("typeId", typeId);
				}
				map.put("mainStatus",1);
				List<Article> articleList = articleMapper.selectListByCondition(map);
				articleListArray.addAll(articleList);
				if(articleListArray.size() > 6){
					break;
				}
			}
			if(null != typeId){
				if(typeId == 43){
					modelMap.put("hotDiscussionList", articleListArray);
				}else if(typeId == 42){
					modelMap.put("healthInformationList", articleListArray);
				}
			}else{
				modelMap.put("articleList", articleListArray);
			}
		}
		return modelMap;
	}
	
	public JSONObject getHospitalDoctorArticle(JSONObject json,Map<String,Object> map) {
		map.put("mainStatus", 1);
		List<Doctor> doctorList = doctorMapper.selectListByCondition(map);
		if(doctorList.size() > 0){
			List<Article> articleListArray = new ArrayList<Article>();
			for(Doctor d: doctorList){
				map.clear();
				map.put("userId", d.getId());
				map.put("mainStatus",1);
				List<Article> articleList = articleMapper.selectListByCondition(map);
				articleListArray.addAll(articleList);
			}
			Result.gridData(articleListArray, articleListArray.size(), json);
			Result.success(json, "数据加载成功", null);
		}
		return json;
	}

	private ModelMap getHotDiscussionAndHealthInformation(ModelMap modelMap,Integer hospitalId) {
		modelMap = getHospitalDoctorArticle(modelMap,hospitalId,43);//热点讨论
		modelMap = getHospitalDoctorArticle(modelMap,hospitalId,42);//健康资讯
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
	
	public ModelMap getDiagnoseTemplateData(ModelMap modelMap, Hospital db_hospital, Integer diagnoseTemplateId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", diagnoseTemplateId);
		DiagnoseTemplate diagnoseTemplate = diagnoseTemplateMapper.selectByCondition(map);
		modelMap.put("diagnoseTemplate", diagnoseTemplate);
		String prescription = diagnoseTemplate.getPrescription();
		String diagnoseTags = diagnoseTemplate.getDiagnoseTags();
		if(Check.isNotNull(prescription)){
			List<Prescription> prescriptionList = JSONObject.parseArray(prescription, Prescription.class);
			List<DiagnoseTag> diagnoseTagList = JSONObject.parseArray(diagnoseTags, DiagnoseTag.class);
			modelMap.put("prescriptionList", prescriptionList);
			modelMap.put("diagnoseTagList", diagnoseTagList);
		}
		return modelMap;
	}

	public ModelMap getDoctorData(ModelMap modelMap, Hospital db_hospital, Integer doctorId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", doctorId);
		Doctor doctor = doctorMapper.selectByCondition(map);
		modelMap.put("doctor", doctor);
		return modelMap;
	}

	public ModelMap getBespeakData(ModelMap modelMap, Hospital db_hospital, Integer bespeakId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", bespeakId);
		Bespeak bespeak = bespeakMapper.selectByCondition(map);
		modelMap.put("bespeak", bespeak);
		return modelMap;
	}
	
}