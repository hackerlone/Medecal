/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层Mapper：通用-基本操作 <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
package com.lhfeiyu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommonMapper<T> {
	
	List<T> selectListByCondition(Map<String, Object> map);
	T selectByCondition(Map<String, Object> map);
	int selectCountByCondition(Map<String, Object> map);
	T selectByPrimaryKey(Integer id);
	T selectBySerial(@Param(value="serial") String serial);

	
	int updateByPrimaryKey(T record);
	int updateBySerial(T record);
	int updateByIds(T record);
	int updateByCondition(@Param(value="condition") String condition, @Param(value="updatedBy") String updatedBy);
	
	int updateDeletedNowByIds(@Param(value="ids") String ids, @Param(value="updatedBy") String updatedBy);
	int updateDeletedNullByIds(@Param(value="ids") String ids, @Param(value="updatedBy") String updatedBy);
	int updateDeletedNowById(@Param(value="id")Integer id, @Param(value="updatedBy") String updatedBy);
	int updateDeletedNullById(@Param(value="id")Integer id, @Param(value="updatedBy") String updatedBy);
	
	int updateFieldById(Map<String, Object> map);
	int updateFieldByIds(Map<String, Object> map);
	
	int updateByPrimaryKeySelective(T record);
	int updateBySerialSelective(T record);
	int updateByIdsSelective(T record);
	
	
	int insert(T record);
	int insertBatch(List<T> recordList);
	int insertSelective(T record);
	

	int deleteByPrimaryKey(Integer id);
	int deleteByCondition(@Param(value="condition") String condition);
	int deleteByIds(@Param(value="ids") String ids);

}
