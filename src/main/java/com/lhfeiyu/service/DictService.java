package com.lhfeiyu.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.DictMapper;
import com.lhfeiyu.po.Dict;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Result;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-数据字典-Dict <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class DictService extends CommonService<Dict> {

	@Autowired
	DictMapper mapper;
	
	@Autowired
	DictMapper dictMapper;
	
	public JSONObject getDictList(JSONObject json, Map<String, Object> map) {
		List<Dict> dictList = dictMapper.selectListByCondition(map);
		Integer total = dictMapper.selectCountByCondition(map);
		return Result.gridData(dictList, total, json);
	}
	
	/**
	 * 根据map中的查询条件加载数据字典数组
	 * @param map
	 * @return JSONArray
	 */
	public JSONArray getDictArray(Map<String,Object> map) {
		JSONArray array = new JSONArray();
		map.put("orderBy", "id");
		map.put("ascOrdesc", "asc");
		List<Dict> dictList = dictMapper.selectListByCondition(map);
		for(Dict d : dictList){
			JSONObject json = new JSONObject();
			json.put("id", d.getId());
			json.put("code", d.getCode());
			json.put("codeName", d.getCodeName());
			json.put("parentCode", d.getParentCode());
			array.add(json);
		}
		return array;
	}
	
	/**
	 * 根据父类型代码加载数据字典数组
	 * @param String parentCode
	 * @param String[] parentCode
	 * @return JSONArray
	 */
	public JSONArray getDictArrayByParentCode(String parentCode, String[] keys) {
		JSONArray array = new JSONArray();
		Map<String, Object> map = CommonGenerator.getHashMap();
		map.put("parentCode", parentCode);
		List<Dict> dictList = dictMapper.selectListByCondition(map);
		if(null == keys){
			keys = new String[]{"code", "codeName", "parentCode"};
		}
		for(Dict d : dictList){
			JSONObject json = new JSONObject();
			json.put(keys[0], d.getCode());
			json.put(keys[1], d.getCodeName());
			json.put(keys[2], d.getParentCode());
			array.add(json);
		}
		return array;
	}
	
	/**
	 * 新增或修改数据字典
	 * @param json 消息数据容器对象（主要用于保存提示消息或数据）
	 * @param dict 数据字典对象
	 * @param username 操作人名称（数据库记录）
	 * @return JSONObject
	 */
	public JSONObject addUpdateDict(JSONObject json, Dict dict, String username){
		String codeName = dict.getCodeName();
		if(!Check.isNotNull(codeName)){
			return Result.failure(json, "代码名称不能为空", "codeName_null");
		}
		if(null == dict.getId()){//添加
			return addDict(json, dict, username);
		}else{//修改
			return updateDict(json, dict, username);
		}
	}
	
	/**
	 * 新增数据字典（代码若已经存在则提示失败）
	 * @param json 消息数据容器对象（主要用于保存提示消息或数据）
	 * @param dict 数据字典对象
	 * @param username 操作人名称（数据库记录）
	 * @return JSONObject
	 */
	public JSONObject addDict(JSONObject json, Dict dict, String username){
		String code = dict.getCode();
		Map<String, Object> map = CommonGenerator.getHashMap();
		map.put("code", code);
		int count = dictMapper.selectCountByCondition(map);
		if(count > 0){
			return Result.failure(json, "该代码已经存在，不能新增重复的代码", "code_repeat");
		}
		Date date = new Date();
		dict.setId(null);
		dict.setSerial(CommonGenerator.getSerialByDate("d"));
		dict.setMainStatus(1);
		dict.setCreatedBy(username);
		dict.setCreatedAt(date);
		dictMapper.insert(dict);
		json.put("id", dict.getId());
		return Result.success(json);
	}
	
	/**
	 * 修改数据字典（ID不能为空，数据库中必须存在该ID的数据，该数据权限不能为只读，否则提示失败； code值不会被修改）
	 * @param json 消息数据容器对象（主要用于保存提示消息或数据）
	 * @param dict 数据字典对象
	 * @param username 操作人名称（数据库记录）
	 * @return JSONObject
	 */
	public JSONObject updateDict(JSONObject json, Dict dict, String username){
		Date date = new Date();
		Integer dictId = dict.getId();
		if(null == dictId){//添加
			return Result.failure(json, "编号为空，无法进行执行修改", "dictId_null");
		}
		Dict dbDict = dictMapper.selectByPrimaryKey(dictId);
		if(null == dbDict){
			return Result.failure(json, "该条数据不存在，无法进行执行修改", "dict_null");
		}
		Integer auth = dbDict.getAuthority();
		if(null != auth && auth == 3){
			return Result.failure(json, "该条数据权限为只读，不能进行修改", "authorith_readonly");
		}
		dict.setCode(null);//不能修改code
		dict.setUpdatedBy(username);
		dict.setUpdatedAt(date);
		dictMapper.updateByPrimaryKeySelective(dict);
		return Result.success(json);
	}
	
}

