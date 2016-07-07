package com.lhfeiyu.thirdparty.kuaidi.business;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.lhfeiyu.thirdparty.kuaidi.pojo.NoticeRequest;
import com.lhfeiyu.thirdparty.kuaidi.pojo.NoticeResponse;
import com.lhfeiyu.thirdparty.kuaidi.pojo.Result;

public class Callback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Callback() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		NoticeResponse resp = new NoticeResponse();
		resp.setResult(false);
		resp.setReturnCode("500");
		resp.setMessage("保存失败");
		try {
			String param = request.getParameter("param");
			NoticeRequest nReq = JSONObject.parseObject(param, NoticeRequest.class);

			Result result = nReq.getLastResult();
			// 处理快递结果
			
			System.out.println(result.toString());//简单打印
			
			resp.setResult(true);
			resp.setReturnCode("200");
			response.getWriter().print(JSONObject.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
		} catch (Exception e) {
			resp.setMessage("保存失败" + e.getMessage());
			response.getWriter().print(JSONObject.toJSON(resp));//保存失败，服务端等30分钟会重复推送。
		}
	}

}
