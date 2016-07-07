package com.lhfeiyu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.StudentMapper;
import com.lhfeiyu.po.Student;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层 学生表 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 王家明 <p>
 * <strong> 编写时间：</strong>2016年6月30日 上午10:24:58<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
@Service
public class StudentService extends CommonService<Student>{
	@Autowired
	StudentMapper mapper;

	public List<Student> selectListByName(String name) {
		// TODO Auto-generated method stub
		return mapper.selectListByName(name);
	}

	public List<Student> selectListBySex(String sex) {
		// TODO Auto-generated method stub
		return mapper.selectListBySex(sex);
	}

	public void updateDeletedNullByIds(String ids) {
		// TODO Auto-generated method stub
		mapper.updateDeletedNullByIds(ids);
	}

	public void updateDeletedNowByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		mapper.updateDeletedNowByIds(map);
	}
	
//	public List<Student> selectAllStu(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		return mapper.selectListByCondition(map);
//	}
}
