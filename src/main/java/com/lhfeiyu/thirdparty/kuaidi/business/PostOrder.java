package com.lhfeiyu.thirdparty.kuaidi.business;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.thirdparty.kuaidi.pojo.TaskRequest;
import com.lhfeiyu.thirdparty.kuaidi.pojo.TaskResponse;

public class PostOrder {

	public static void main(String[] args){
		TaskRequest req = new TaskRequest();
		req.setCompany("yuantong");
		req.setFrom("上海浦东新区");
		req.setTo("广东深圳南山区");
		req.setNumber("12345678");
		req.getParameters().put("callbackurl", "http://www.yourdmain.com/kuaidi");
		req.setKey("ttCDrbtZ441");
		
		HashMap<String, String> p = new HashMap<String, String>(); 
		p.put("schema", "json");
		p.put("param", JSONObject.toJSONString(req));
		try {
			String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
			System.out.println(ret);
			TaskResponse resp = JSONObject.parseObject(ret, TaskResponse.class);
			if(resp.getResult()==true){
				System.out.println("订阅成功");
			}else{
				System.out.println("订阅失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
