package com.lhfeiyu.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.ConstField;
import com.lhfeiyu.vo.ChatGroup;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：业务-推送 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015-7-4 10:08:21 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
@Service
public class ChatGroupService {
	
/*	@Autowired
	private PushService pushService;*/
	
    public static String createGroup(ChatGroup group){
    	String groupId = null;
    	String subAccount = group.getThirdName();
		String password = group.getThirdPassword();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String subSig = ConstField.generateSubSig(subAccount, password, timeStamp);
		String url = ConstField.rl_ytx_base_sub_url+subAccount+"/Group/CreateGroup?sig="+subSig;
		String authorization = ConstField.getSubAuthorization(subAccount, timeStamp);
		
		JSONObject json = new JSONObject();
        json.put("name", group.getGroupName());
        json.put("type", group.getType());
        json.put("permission", group.getPermission());
    	String param = json.toJSONString();
		String r = PushService.sendPost(url, param, authorization);
		if(null != r){//{"groupId":"gg8001977514","statusCode":"000000"}
			JSONObject jsonResult = JSONObject.parseObject(r);
			if(jsonResult.getString("statusCode").equals("000000")){
				groupId = jsonResult.getString("groupId");
				System.out.println("群组创建成功:"+groupId);//创建成功
			}
		}else{
			System.out.println("群组创建失败:");
			System.out.println(r);
		}
		return groupId;
    }
    
    public static boolean deleteGroup(ChatGroup group){
    	boolean flag = false;
    	//http://docs.yuntongxun.com/index.php/5.0_%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%AB%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#2.3_.E5.88.A0.E9.99.A4.E7.BE.A4.E7.BB.84
    	String subAccount = group.getThirdName();
		String password = group.getThirdPassword();		
    	String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String subSig = ConstField.generateSubSig(subAccount, password, timeStamp);
		String url = ConstField.rl_ytx_base_sub_url+subAccount+"/Group/DeleteGroup?sig="+subSig;
		String authorization = ConstField.getSubAuthorization(subAccount, timeStamp);
		
		JSONObject json = new JSONObject();
        json.put("groupId", group.getGroupId());
    	String param = json.toJSONString();
		String r = PushService.sendPost(url, param, authorization);
		//{"body":"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<Response>\n  <statusCode>000000<\/statusCode>\n<\/Response>\n","statusCode":"000000","statusMsg":"","success":"true"}
		if(null != r){
			JSONObject jsonResult = JSONObject.parseObject(r);
			if(jsonResult.getString("statusCode").equals("000000")){
				flag = true;
				System.out.println("群组删除成功:"+group.getGroupId());//创建成功
			}
		}else{
			System.out.println("群组删除失败:");
			System.out.println(r);
		}
		return flag;
    }
    
    public static void main(String[] args) {
    	ChatGroup group = new ChatGroup();
    	group.setGroupName("最新分组");
    	group.setPermission("0");
    	group.setType("2");
    	//group.setScope("1");
    	group.setThirdName("7dd1ca1c8cdd11e5bb61ac853d9d52fd");
    	group.setThirdPassword("69da3272a105c0472501595c1b84404c");
    	createGroup(group);
    	//group.setGroupId("gg8001977514");
    	
    	//deleteGroup(group);
	}
    
	
}

