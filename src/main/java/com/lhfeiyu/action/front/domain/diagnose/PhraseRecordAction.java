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
import com.lhfeiyu.po.PhraseRecord;
import com.lhfeiyu.service.PhraseRecordService;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class PhraseRecordAction {
	@Autowired
	private PhraseRecordService phraseRecordService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@ResponseBody
	@RequestMapping(value = "/getPhraseRecordArray", method=RequestMethod.POST)
	public JSONArray getPhraseRecordArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			List<PhraseRecord> prList = phraseRecordService.selectListByCondition(map);
			for(PhraseRecord pr : prList){
				JSONObject json = new JSONObject();
				json.put("id", pr.getId());
				json.put("name", pr.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-PhraseRecord-AJAX-/getPhraseRecordArray-加载医学短语列表出现异常", array);
		}
		return array;
	}
	

	
}
