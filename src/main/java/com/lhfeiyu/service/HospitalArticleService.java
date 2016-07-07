package com.lhfeiyu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.ArticleMapper;
import com.lhfeiyu.po.Article;
import com.lhfeiyu.po.Hospital;

@Service
public class HospitalArticleService extends CommonService<Article>{
	@Autowired
	ArticleMapper articleMapper;

	public int updateArticleDelete(JSONObject json, String ids, Hospital db_hospital) {
		
		Article article = new Article();
		article.setMainStatus(2);
		article.setIds(ids);
		article.setUpdatedBy(db_hospital.getWholeName());
		return super.updateByIdsSelective(article);
	}

	public int updateService(JSONObject json, Article article, Hospital db_hospital) {
		article.setUpdatedBy(db_hospital.getWholeName());
		return super.updateByPrimaryKeySelective(article);
	}

	public int insertService(JSONObject json, Article article, Hospital db_hospital) {
		Date date = new Date();
		article.setMainStatus(1);
		article.setCreatedBy(db_hospital.getWholeName());
		article.setCreatedAt(date);
		return super.insertSelective(article);
	}
}
