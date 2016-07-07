package com.lhfeiyu.dao;

import java.util.List;
import java.util.Map;

import com.lhfeiyu.po.Student;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层Mapper：DiagnoseApply <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 王家明 <p>
 * <strong> 编写时间：</strong>2016年6月30日 上午10:21:14<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public interface StudentMapper extends CommonMapper<Student>{

	List<Student> selectListByName(String name);

	List<Student> selectListBySex(String sex);

	void updateDeletedNullByIds(String ids);

	void updateDeletedNowByIds(Map<String, Object> map);

//	public List<Student> selectAllStu();
	
}
