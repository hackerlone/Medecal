package com.lhfeiyu.thirdparty.kuaidi.pojo;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result {

	private String message = "";/*监控状态相关消息，如:3天查询无记录，60天无变化*/
	@JsonIgnore
	private String nu = ""; /*单号*/
	@JsonIgnore
	private String ischeck = "0";/*是否签收标记，明细状态请参考state字段*/
	@JsonIgnore
	private String com = "";/*快递公司编码,一律用小写字母，见章五《快递公司编码》*/
	/**
	 * status：
		（1）当快递单还在监控过程中，则status=polling；
		（2）当快递单本身为已签收时，status=shutdown，即监控结束，表示此单的生命周期已结束；
		（3）若单号从提交订阅起连续3天都查不到跟踪信息，或单号从某个节点起不再更新并延续了30天，
			我们就会终止对此单的跟踪，并给贵方的回调接口（callbackurl）推送message为“3天查询无记录”或“60天无变化时”、status= abort的提醒，即监控中止。
		/*监控状态:
		 * polling:监控中，shutdown:结束，
		 * abort:中止，updateall：重新推送。
		 * 其中当快递单为已签收时status=shutdown，当message为“3天查询无记录”或“60天无变化时”status= abort ，
		 * 对于stuatus=abort的状度，需要增加额外的处理逻辑，详见本节最后的说明 
	 */
	private String status = "0";
	
	/**
	 * "data":[
			{
			"context":"上海分拨中心/装件入车扫描 ", //内容
			"time":"2012-08-28 16:33:19",    //时间，原始格式
			"ftime":"2012-08-28 16:33:19",   //格式化后时间
			"status":"在途",	       			 //本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
			"areaCode":"310000000000", 		 //本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
			"areaName":"上海市",       		 //本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
		},{
			"context":"上海分拨中心/下车扫描 ",     //内容
			"time":"2012-08-27 23:22:42",          //时间，原始格式
			"ftime":"2012-08-27 23:22:42",        //格式化后时间
			"status":"在途",			//本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
			"areaCode":"310000000000",  //本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
			"areaName":"上海市",       //本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后后的说明）且在订阅接口中提交resultv2标记后才会出现
			}
		]]
	 */
	@JsonIgnore
	private ArrayList<ResultItem> data = new ArrayList<ResultItem>();
	/**
	 *  state:
		0 在途   快件处于运输过程中
		1 揽件  快件已由快递公司揽收
		2 疑难  快递100无法解析的状态，或者是需要人工介入的状态，比方说收件人电话错误。
		3 签收  正常签收或者退回签收
		4 退签  货物退回发货人并签收
		5 派件  货物正在进行派件
		6 退回  货物正处于返回发货人的途中
		此项服务4至7状态值属于额外的增值服务，如果您需要，请与快递100业务联系人接洽购买事宜。
	 */
	@JsonIgnore
	private String state = "0";
	@JsonIgnore
	private String condition = "";

	@SuppressWarnings("unchecked")
	public Result clone() {
		Result r = new Result();
		r.setCom(this.getCom());
		r.setIscheck(this.getIscheck());
		r.setMessage(this.getMessage());
		r.setNu(this.getNu());
		r.setState(this.getState());
		r.setStatus(this.getStatus());
		r.setCondition(this.getCondition());
		r.setData((ArrayList<ResultItem>) this.getData().clone());

		return r;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public ArrayList<ResultItem> getData() {
		return data;
	}

	public void setData(ArrayList<ResultItem> data) {
		this.data = data;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
