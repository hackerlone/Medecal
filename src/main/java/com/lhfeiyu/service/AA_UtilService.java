package com.lhfeiyu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.AA_UtilMapper;
import com.lhfeiyu.po.AaTemplateBase;
import com.lhfeiyu.po.AaTemplateMain;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-通用功能方法 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2016年3月3日21:23:48 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class AA_UtilService {
	
	@Autowired
	AA_UtilMapper mapper;

	
	public int deleteById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.deleteById(map);
	}

	
	public int deleteByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.deleteByIds(map);
	}

	
	public int deleteByCondition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.deleteByCondition(map);
	}

	
	public int updateDeletedNowById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateDeletedNowById(map);
	}

	
	public int updateDeletedNowByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateDeletedNowByIds(map);
	}

	
	public int updateDeletedNullById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateDeletedNullById(map);
	}

	
	public int updateDeletedNullByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateDeletedNullByIds(map);
	}

	
	public int updateMainStatusById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateMainStatusById(map);
	}

	
	public int updateMainStatusByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateMainStatusByIds(map);
	}

	
	public int updateLogicStatusById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateLogicStatusById(map);
	}

	
	public int updateLogicStatusByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateLogicStatusByIds(map);
	}

	
	public int updateFieldByCondition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateFieldByCondition(map);
	}

	
	public int insertBySql(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.insertBySql(map);
	}

	
	public int deleteBySql(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.deleteBySql(map);
	}

	
	public int updateBySql(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateBySql(map);
	}

	
	public String selectForString(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectForString(map);
	}

	
	public Integer selectForInteger(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectForInteger(map);
	}

	
	public Double selectForDouble(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectForDouble(map);
	}

	
	public AaTemplateBase selectForTemplateBase(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectForTemplateBase(map);
	}

	
	public AaTemplateMain selectForTemplateMain(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectForTemplateMain(map);
	}

	
	public List<AaTemplateBase> selectForTemplateBaseList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectForTemplateBaseList(map);
	}

	
	public List<AaTemplateMain> selectForTemplateMainList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectForTemplateMainList(map);
	}

}
