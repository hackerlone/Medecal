package com.lhfeiyu.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhfeiyu.dao.RoleMapper;
import com.lhfeiyu.po.Role;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-角色-Role <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class RoleService extends CommonService<Role>{
	
	@Autowired
	RoleMapper mapper;

	/**
	 * 查询下拉框角色
	 * @param map
	 * @return
	 */
	public List<Role> selectRoleByCombobox(Map<String, Object> map){
		return mapper.selectRoleByCombobox(map);
	}
}
