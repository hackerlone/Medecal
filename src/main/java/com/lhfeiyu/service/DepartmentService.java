package com.lhfeiyu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.DepartmentMapper;
import com.lhfeiyu.po.Department;
import com.lhfeiyu.tools.CommonGenerator;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-Department <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class DepartmentService extends CommonService<Department> {

	@Autowired
	DepartmentMapper departmentMapper;
	
	public List<Department> getAllDepartmentByLevel(){
		Map<String,Object> map = CommonGenerator.getHashMap();
		map.put("parentIdNull", 1);
		List<Department> rootDeptList = departmentMapper.selectListByCondition(map);
		//List<Department> rootDeptList2 = new ArrayList<Department>(rootDeptList.size());
		for(Department dept : rootDeptList){
			map.clear();
			Integer deptId = dept.getId();
			map.put("parentId", deptId);
			List<Department> subDeptList = departmentMapper.selectListByCondition(map);
			dept.setSubDeptList(subDeptList);
		}
		return rootDeptList;
	}
	
	public Department selectService(Map<String, Object> map) {
		return super.selectByCondition(map);
	}
	
	public List<Department> selectListService(Map<String, Object> map) {
		return super.selectListByCondition(map);
	}
	
	public int updateService(Department department) {
		return super.updateByPrimaryKeySelective(department);
	}
	
	public int insertService(Department department) {
		return super.insertSelective(department);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

}