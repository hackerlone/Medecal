package com.lhfeiyu.thirdparty.wx.business;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class MessageNews {
	
	private String touser;
	private String msgtype;
	private News news;
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	
	public static void main(String[] args) {
		MessageNews mn = new MessageNews();
		mn.setTouser("openid");
		mn.setMsgtype("news");
		News news = new News();
		New n = new New();
		/*n.setDescription("description");
		n.setPicurl("pircurl");
		n.setTitle("title");
		n.setUrl("url");*/
		n.setDescription("霍窑白釉瓷与定窑白釉瓷区别");
		n.setPicurl("http://weipaike.net/file/default/1453104225643__1-15042F9505K95.jpg");
		n.setTitle("霍窑白釉瓷与定窑白釉瓷区别");
		n.setUrl("http://weipaike.net/activity/4");
		List<New> articles = new ArrayList<New>();
		articles.add(n);
		news.setArticles(articles);
		mn.setNews(news);
		System.out.println(JSONObject.toJSONString(mn));
		//{"touser":"123","msgtype":"news","news":{"articles":[{"description":"123","picurl":"sdf","title":"fds","url":"url"}]}}
	}
	
}
