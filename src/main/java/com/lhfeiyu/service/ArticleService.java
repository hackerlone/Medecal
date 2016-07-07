package com.lhfeiyu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.ArticleMapper;
import com.lhfeiyu.dao.CommentMapper;
import com.lhfeiyu.dao.DictMapper;
import com.lhfeiyu.dao.DoctorMapper;
import com.lhfeiyu.po.Article;
import com.lhfeiyu.po.Comment;
import com.lhfeiyu.po.Dict;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.User;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.util.dust.ColumnName;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-文章-Article <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class ArticleService extends CommonService<Article>{
	@Autowired
	ArticleMapper mapper;
	@Autowired
	CommentMapper commentMapper;
	@Autowired
	DoctorMapper doctorMapper;
	@Autowired
	DictMapper dictMapper;

	public int addArticle(Article article){
		
		article.setSerial(CommonGenerator.getSerialByDate("a"));
		
		return 0;
	}

	public ModelMap getArticleData(ModelMap modelMap, Integer id,Integer typeId) {
		Article article = mapper.selectByPrimaryKey(id);//当前文章
			modelMap.put("article", article);
		Map<String,Object> map = Pagination.getPageParams(1, 1);
		if(null != article && !"".equals(article)){
			map.put("nextArticleId", article.getId());
		}else{
			map.put("nextArticleId", 1);
		}
		map.put("orderBy", ColumnName.id);
		map.put("ascOrdesc", ColumnName.ASC);
		map.put("nextArticle", 1);
		map.put("mainStatus", 1);
		map.put("typeId", typeId);//文章类型
		//map.put("typeId", typeId);
		List<Article> nextArticleList = mapper.selectListByCondition(map);//下一遍文章
		if(null == nextArticleList || nextArticleList.size()<=0){
			map.remove("nextArticle");
			//map.remove("typeId");
			map.put("nextArticleId", 1);
			nextArticleList = mapper.selectListByCondition(map);
		}
		if(nextArticleList.size()>0){
			modelMap.put("nextArticle", nextArticleList.get(0));//下一篇文章
		}
		if(null != article && !"".equals(article)){
			map.put("preArticleId", article.getId());
		}else{
			map.put("preArticleId", 1);
		}
		map.put("orderBy", ColumnName.id);
		map.put("ascOrdesc", ColumnName.DESC);
		map.remove("nextArticle");
		map.remove("nextArticleId");
		map.put("preArticle", 1);
		List<Article> preArticleList = mapper.selectListByCondition(map);//上一篇文章
		if(null == preArticleList || preArticleList.size()<=0){
//			map.clear();
//			Integer count = mapper.selectCountByCondition(map);
//			map.put("id", count);
			map.remove("nextArticle");
			map.remove("preArticle");
			preArticleList = mapper.selectListByCondition(map);
		}
		if(preArticleList.size()>0){
			modelMap.put("preArticle", preArticleList.get(0));//上一篇文章
		}
		
		map.clear();
		map.put("objectId", id);
		map.put("commentTypeId", 1);
		List<Comment> commengList = commentMapper.selectListByCondition(map);
			modelMap.put("commengList", commengList);
			modelMap = hotDiscussionAndArticle(modelMap,typeId);
		return modelMap;
	}

	public ModelMap hotDiscussionAndArticle(ModelMap modelMap,Integer typeId){
		Dict dict = dictMapper.selectByPrimaryKey(typeId);
		modelMap.put("typeName", dict.getCodeName());
		Map<String,Object> map = new HashMap<String,Object>();
		map.clear();
		map.put("typeId", 43);
		map.put("mainStatus", 1);
		List<Article> hotDiscussionList = mapper.selectListByCondition(map);//热点讨论
			modelMap.put("hotDiscussionList", hotDiscussionList);
		map.clear();
		map.put("start", 0);
		map.put("count", 6);
		List<Doctor> doctorList = doctorMapper.selectListByCondition(map);//良医在线
			modelMap.put("doctorList", doctorList);
			return modelMap;
	}
	
	/**
	 * 根据文章类型查询最新5条文章
	 * @param modelMap 返回数据集合
	 * @param typeId 文章id
	 * @param queryType 查询类型 1 查询最新文章 2.根据访问量查询文章
	 * @return
	 */
	public ModelMap latestArticle(ModelMap modelMap,Integer typeId,int queryType){
		if(typeId!=null){
			if(typeId.equals(42)){
				modelMap.put("typeName_is", "健康资讯");
			}else if(typeId.equals(58)){
				modelMap.put("typeName_is", "疾病专题");
			}
		}
		HashMap<String, Object> map =  new HashMap<String, Object>();
		map.put("start", 0);
		map.put("count", 5);
		map.put("ascOrdesc", "DESC");
		map.put("typeId", typeId);
		if(queryType==1){
			map.put("orderBy", "id");
		}if(queryType==2){
			map.put("orderBy", "scans");
		}
		List<Article> articleList = mapper.selectListByCondition(map);
		if(queryType==1){
			modelMap.put("latestArticleList", articleList);
		}if(queryType==2){
			modelMap.put("scansArticleList", articleList);
		}
		return modelMap;
	}
	

	public int insertService(JSONObject json,Article article, Doctor doctor) {
		String username = doctor.getUsername();
		Integer userId = doctor.getId();
		Date date = new Date();
		article.setCreatedBy(username);
		article.setCreatedAt(date);
		article.setMainStatus(1);
		article.setUserId(userId);
		return super.insertSelective(article);
	}

	public int updateService(JSONObject json,Article article,Doctor doctor) {
		String username = doctor.getUsername();
		article.setUpdatedBy(username);
		return super.updateByPrimaryKeySelective(article);
	}

	public int updateArticleDelete(JSONObject json,String ids, User user) {
		String username = user.getUsername();
		Article article = new Article();
		article.setMainStatus(2);
		article.setIds(ids);
		article.setUpdatedBy(username);
		return super.updateByIdsSelective(article);
	}
	
	/**
	 * 修改文章访问次数
	 * @param id  文章id
	 */
	public void updateArticleScans(int id){
		mapper.updateArticleScans(id);
	}
	
	
}
