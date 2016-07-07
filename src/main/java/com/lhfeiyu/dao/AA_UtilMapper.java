/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层Mapper：通用-公共操作 <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
package com.lhfeiyu.dao;

import java.util.List;
import java.util.Map;

import com.lhfeiyu.po.AaTemplateBase;
import com.lhfeiyu.po.AaTemplateMain;

public interface AA_UtilMapper {
	
	/** 根据 ID 删除数据  */
	int deleteById(Map<String,Object> map);

    /** 根据 ID串 删除数据  */
    int deleteByIds(Map<String,Object> map);
    
    /** 根据 条件参数删除 数据（sql片断） */
    int deleteByCondition(Map<String,Object> map);
    
    /** 根据 ID将删除时间设置为当前时间 更新数据  */
    int updateDeletedNowById(Map<String,Object> map);
    
    /** 根据  ID串将删除时间设置为当前时间 更新数据  */
    int updateDeletedNowByIds(Map<String,Object> map);
    
    /** 根据 ID置空删除时间 更新数据  */
    int updateDeletedNullById(Map<String,Object> map);
    
    /** 根据 ID串置空删除时间 更新数据  */
    int updateDeletedNullByIds(Map<String,Object> map);
    
    /** 根据 ID修改主状态 更新数据  */
    int updateMainStatusById(Map<String,Object> map);
    
    /** 根据 ID串修改主状态 更新数据  */
    int updateMainStatusByIds(Map<String,Object> map);
    
    /** 根据 ID修改逻辑状态 更新数据  */
    int updateLogicStatusById(Map<String,Object> map);
    
    /** 根据 ID串修改逻辑状态 更新数据  */
    int updateLogicStatusByIds(Map<String,Object> map);
    
    /** 根据 条件参数修改字段 更新数据（sql片断）  */
    int updateFieldByCondition(Map<String,Object> map);
    
    /** 新增数据（传入完整sql）  */
    int insertBySql(Map<String,Object> map);
    
    /** 删除数据（传入完整sql）  */
    int deleteBySql(Map<String,Object> map);
    
    /** 修改数据（传入完整sql）  */
    int updateBySql(Map<String,Object> map);
    
    /** 查询数据，返回字符串  */
    String selectForString(Map<String,Object> map);
    
    /** 查询数据，返回整型  */
    Integer selectForInteger(Map<String,Object> map);
    
    /** 查询数据，返回Double  */
    Double selectForDouble(Map<String,Object> map);
    
    /** 查询数据，返回模板基本型对象  */
    AaTemplateBase selectForTemplateBase(Map<String,Object> map);
    
    /** 查询数据，返回模板详细型对象  */
    AaTemplateMain selectForTemplateMain(Map<String,Object> map);
    
    /** 查询数据，返回模板基本型对象集合  */
    List<AaTemplateBase> selectForTemplateBaseList(Map<String,Object> map);
    
    /** 查询数据，返回模板详细型对象集合  */
    List<AaTemplateMain> selectForTemplateMainList(Map<String,Object> map);
    
}
