/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层Mapper：通用-角色-Role <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
package com.lhfeiyu.dao;

import java.util.List;
import java.util.Map;

import com.lhfeiyu.po.Role;

public interface RoleMapper extends CommonMapper<Role>{

	/**
	 * 查询下拉框角色
	 * @param map
	 * @return
	 */
	List<Role> selectRoleByCombobox(Map<String, Object> map);
}
