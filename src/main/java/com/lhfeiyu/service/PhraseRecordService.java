package com.lhfeiyu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.PhraseRecordMapper;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.PhraseRecord;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-PhraseRecord <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class PhraseRecordService extends CommonService<PhraseRecord> {

	@Autowired
	PhraseRecordMapper phraseRecordmapper;
	
	public PhraseRecord selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<PhraseRecord> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(PhraseRecord phraseRecord) {
		return super.updateByPrimaryKeySelective(phraseRecord);
	}
	
	public int insertService(PhraseRecord phraseRecord) {
		return super.insertSelective(phraseRecord);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public JSONObject updateData(JSONObject json, PhraseRecord phraseRecord, Admin admin) {
		Date date = new Date();
		String username = admin.getUsername();
		phraseRecord.setUpdatedAt(date);
		phraseRecord.setUpdatedBy(username);
		phraseRecordmapper.updateByPrimaryKeySelective(phraseRecord);
		return json;
	}

	public JSONObject insertData(JSONObject json, PhraseRecord phraseRecord, Admin admin) {
		Date date = new Date();
		String username = admin.getUsername();
		phraseRecord.setCreatedAt(date);
		phraseRecord.setCreatedBy(username);
		phraseRecordmapper.insert(phraseRecord);
		return json;
	}

}