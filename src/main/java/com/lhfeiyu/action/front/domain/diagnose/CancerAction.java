package com.lhfeiyu.action.front.domain.diagnose;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.Cancer;
import com.lhfeiyu.service.CancerService;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class CancerAction {
	@Autowired
	private CancerService cancerService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/getCancerArray", method=RequestMethod.POST)
	public JSONArray getCancerArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			HashMap<String, Object> paramMap = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<Cancer> prList = cancerService.selectListByCondition(paramMap);
			for(Cancer pr : prList){
				JSONObject json = new JSONObject();
				json.put("id", pr.getId());
				json.put("name", pr.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Cancer-AJAX-/getCancerArray-加载癌症类型串列表出现异常", array);
		}
		return array;
	}
	

	
}
