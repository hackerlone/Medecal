package com.lhfeiyu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.CommonMapper;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-公共基本操作<p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华<p>
 * <strong> 编写时间：</strong> 2016年3月1日20:32:42<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司<p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0<p>
 */
@Service
public abstract class CommonService<T> {

	@Autowired
	CommonMapper<T> mapper;

	public List<T> selectListByCondition(Map<String, Object> map) {
		return mapper.selectListByCondition(map);
	}
	
	public T selectByCondition(Map<String, Object> map) {
		return mapper.selectByCondition(map);
	}

	public int selectCountByCondition(Map<String, Object> map) {
		return mapper.selectCountByCondition(map);
	}

	public T selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public T selectBySerial(String serial) {
		return mapper.selectBySerial(serial);
	}
	

	public int updateByPrimaryKey(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	public int updateBySerial(T record) {
		return mapper.updateBySerial(record);
	}
	
	public int updateByIds(T record) {
		return mapper.updateByIds(record);
	}

	public int updateByCondition(String condition, String updatedBy) {
		return mapper.updateByCondition(condition, updatedBy);
	}

	public int updateDeletedNowByIds(String ids, String updatedBy) {
		return mapper.updateDeletedNowByIds(ids, updatedBy);
	}
	
	public int updateDeletedNullByIds(String ids, String updatedBy) {
		return mapper.updateDeletedNullByIds(ids, updatedBy);
	}
	
	public int updateDeletedNowById(Integer id, String updatedBy) {
		return mapper.updateDeletedNowById(id, updatedBy);
	}
	
	public int updateDeletedNullById(Integer id, String updatedBy) {
		return mapper.updateDeletedNullById(id, updatedBy);
	}

	public int updateFieldById(Map<String, Object> map) {
		return mapper.updateFieldById(map);
	}
	
	public int updateFieldByIds(Map<String, Object> map) {
		return mapper.updateFieldByIds(map);
	}

	
	public int updateByPrimaryKeySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public int updateBySerialSelective(T record) {
		return mapper.updateBySerialSelective(record);
	}
	
	public int updateByIdsSelective(T record) {
		return mapper.updateByIdsSelective(record);
	}

	

	public int insert(T record) {
		return mapper.insert(record);
	}
	
	public int insertBatch(List<T> list) {
		return mapper.insertBatch(list);
	}

	public int insertSelective(T record) {
		return mapper.insertSelective(record);
	}
	
	public int deleteByPrimaryKey(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}
	
	public int deleteByCondition(String condition) {
		return mapper.deleteByCondition(condition);
	}
	
	public int deleteByIds(String ids) {
		return mapper.deleteByIds(ids);
	}

}
