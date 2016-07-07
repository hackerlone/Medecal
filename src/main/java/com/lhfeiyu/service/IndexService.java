package com.lhfeiyu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.lhfeiyu.dao.AdvertisementMapper;
import com.lhfeiyu.dao.ArticleMapper;
import com.lhfeiyu.dao.DoctorMapper;
import com.lhfeiyu.po.Advertisement;
import com.lhfeiyu.po.Article;
import com.lhfeiyu.po.Doctor;

@Service
public class IndexService {
	
	@Autowired
	DoctorMapper  doctorMapper;
	@Autowired
	ArticleMapper  articleMapper;
	@Autowired
	AdvertisementMapper  advertisementMapper;
	
	
	public ModelMap getData(ModelMap modelMap, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", 0);
		map.put("count", 5);
		List<Doctor> doctorList = doctorMapper.selectListByCondition(map);//良医在线
		if(null != doctorList && doctorList.size()>0){
			modelMap.put("doctorList", doctorList);
			modelMap.put("doctorTotal", doctorList.size());
		}
		map.clear();
		map.put("typeId", 45);
		map.put("mainStatus", 1);
		List<Article> announcementList = articleMapper.selectListByCondition(map);//公告
			modelMap.put("announcementList", announcementList);
		map.clear();
		map.put("typeId", 46);
		map.put("mainStatus", 1);
		List<Article> activityList = articleMapper.selectListByCondition(map);//活动
			modelMap.put("activityList", activityList);
		map.clear();
		map.put("typeId", 47);
		map.put("mainStatus", 1);
		List<Article> investigationList = articleMapper.selectListByCondition(map);//调查
			modelMap.put("investigationList", investigationList);
		map.clear();
		map.put("typeId", 43);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 6);
		List<Article> hotDiscussionList = articleMapper.selectListByCondition(map);//热点讨论
			modelMap.put("hotDiscussionList", hotDiscussionList);
		map.clear();
		map.put("typeId", 42);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 6);
		List<Article> healthInformationList = articleMapper.selectListByCondition(map);//健康资讯
			modelMap.put("healthInformationList", healthInformationList);
		map.clear();
		map.put("typeId", 44);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 7);
		List<Article> medicalOpinionList = articleMapper.selectListByCondition(map);//名医观点
			modelMap.put("medicalOpinionList", medicalOpinionList);
		map.clear();
		map.put("typeId", 48);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 4);
		List<Article> sideList = articleMapper.selectListByCondition(map);//周边
			modelMap.put("sideList", sideList);
		modelMap = getIntroductionAndvision(modelMap);
		modelMap = getAdvertisement(modelMap);
		return modelMap;
	}
	
	public ModelMap getIntroductionAndvision(ModelMap modelMap){
		Map<String,Object> map = new HashMap<String,Object>();
		map.clear();
		map.put("typeId", 55);
		map.put("mainStatus", 1);
		Article introduction = articleMapper.selectByCondition(map);//公司简介
		modelMap.put("introduction", introduction);
		map.clear();
		map.put("typeId", 56);
		map.put("mainStatus", 1);
		Article vision = articleMapper.selectByCondition(map);//公司愿景
		modelMap.put("vision", vision);
		return modelMap;
	}
	
	public ModelMap getAdvertisement(ModelMap modelMap){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("catId", 1);
		map.put("mainStatus", 1);
		map.put("start", 0);
		map.put("count", 5);
		List<Advertisement> advertisementList = advertisementMapper.selectListByCondition(map);//首页广告
		modelMap.put("advertisementList", advertisementList);
		return modelMap;
	}
	
}
