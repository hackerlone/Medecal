package com.lhfeiyu.thirdparty.kuaidi.pojo;

import com.thoughtworks.xstream.XStream;

public class TaskResponse {
	/**{
		result:true,
		"returnCode":"200",
		"message":"提交成功"
		}*/
	private static XStream xstream;
	private Boolean result;//true表示成功，false表示失败
	/**快递100接口的推送订阅状态（
	 * 200提交成功，
	 * 701:拒绝订阅的快递公司，
	 * 700: 订阅方的订阅数据存在错误（如不支持的快递公司、单号为空、单号超长等），
	 * 600: 您不是合法的订阅者（即授权Key出错），
	 * 500: 服务器错误，
	 * 501:重复订阅）
	 */
	private String returnCode;
	private String message;//提交成功

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private static XStream getXStream() {
		if (xstream == null) {
			xstream = new XStream();
			xstream.autodetectAnnotations(true);
			xstream.alias("orderResponse", TaskResponse.class);
		}
		return xstream;
	}

	public String toXml(){
		return "<?xml version='1.0' encoding='UTF-8'?>\r\n" + getXStream().toXML(this);
	}

	public static TaskResponse fromXml(String sXml){
		return (TaskResponse)getXStream().fromXML(sXml);
	}

	public static void main(String[] args){
		TaskResponse req = new TaskResponse();
		req.setMessage("订阅成功");
		req.setResult(true);
		req.setReturnCode("200");
		System.out.print(req.toXml());
	}

}
