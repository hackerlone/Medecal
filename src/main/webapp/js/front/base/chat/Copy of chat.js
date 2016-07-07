/** 聊天 主JS  */

//RECEIVE_ID = '8a48b551505b4af001505f21acd1091c';
RECEIVE_ID = '8a48b551505b4af001505f21acde091d';

$(function(){

	//loadShopList();//加载店铺列表（包含每个店铺的部分商品）

	//IM.init();
	initChat();
	
});

function initChat(){
	//初始化SDK，App的通知消息显示号码
	var resp = RL_YTX.init("8a48b551505b4af001505f21aca8091b"); 
	if(102 == resp.code){
		//缺少必要参数,用户逻辑处理
		alert(102);
	}else if(101 == resp.code){
		//不支持HTML5，关闭页面,用户逻辑处理
		alert(101);
	}else if(200 == resp.code){
		//初始化成功,用户逻辑处理
		console.log('init success');
		//login();
		//logout();
	}  
	
}

function login(){
	/*var username = $('#username').val();
	var password = $('#password').val();*/
	var userId = $('#userId').val();
	var sig = $('#sig').val();
	var timeStamp = $('#timeStamp').val();
	//voip账号密码登录方式参数设置为：
	//var loginBuilder = new RL_YTX.LoginBuilder(3, "8001977500000001","9w2ujrum");
	//var loginBuilder = new RL_YTX.LoginBuilder(3, "8001977500000002","tvupavtz");
	//var loginBuilder = new RL_YTX.LoginBuilder(3, "8001977500000003","7ar8dvy8");
	//var loginBuilder = new RL_YTX.LoginBuilder(3, "8001977500000004","eyo4xjnn");
	//var loginBuilder = new RL_YTX.LoginBuilder(3, "8001977500000005","y7ubn4ci");
	
	//var loginBuilder = new RL_YTX.LoginBuilder(3, username,password);
	//账号登录参数设置,用户登录名和sig之间的两个逗号中间添加一个空的字符串
	var loginBuilder = new RL_YTX.LoginBuilder(1, userId, '', sig, timeStamp);
	//注意：sig字段又用户请求自己的服务器生成，sig规则：MD5(appid+userName+timestamp+apptoken); 当voip账号密码登录时，可以不传sig
	//执行用户登录
	RL_YTX.login(loginBuilder, function(obj){
		//登录成功回调
		RL_YTX.onMsgReceiveListener(function(obj){
			//收到push消息或者离线消息
			$('#content').append(obj.msgSender+': '+obj.msgContent+'<br/>');
			//alert('onMsgReceiveListener');
		});
		//注册群组通知事件监听
		RL_YTX.onNoticeReceiveListener(function(obj){
		 	//收到群组通知
			alert('onMsgReceiveListener');
		});
		RL_YTX.onConnectStateChangeLisenter(function(obj){
			//连接状态变更
			alert('onMsgReceiveListener');
		});
		
		//历史聊天信息YRH
		loadHistoryChat();
		
	},function(obj){
		//登录失败方法回调
		alert('登录失败方法回调');
	});
	
}

function loadHistoryChat(){
	//构建拉取消息请求对象
	var obj = new RL_YTX.SyncMsgBuilder();
	//设置开始消息版本号
	obj.setSVersion(3);
	//设置结束消息版本号
	obj.setEVersion(6);
	//发送拉取消息请求
	RL_YTX.syncMsg(obj, function(obj){
		//拉取消息失败
		alert('load history error');
	});
}

function logout(){
	RL_YTX.logout(function(){
		//登出成功处理
		alert('logout');
	}, function(obj){
		//登出失败处理
		alert('登出失败方法回调');
	});
}

function sendMsg(){
	//新建消息体对象
	var obj = new RL_YTX.MsgBuilder();
	//设置自定义消息id
	var d = new Date();
	var timeId = d.getMilliseconds();
	obj.setId(timeId);
	//假设页面存在一个id为file的<input type=”file”>元素 
	//获取图片或附件对象
	//var file = document.getElementById("file").files[0];
	//设置图片或附件对象
	//obj.setFile(file);
	//设置发送的文本内容
	var text = '你好';
	obj.setText(text);
	//设置发送的消息类型1文本消息4 图片消息6 附件消息
	//发送非文本消息时，text字段将被忽略，发送文本消息时 file字段将被忽略
	obj.setType(1);
	//设置接收者
	//obj.setReceiver('8001977500000004');
	obj.setReceiver(RECEIVE_ID);
	
	RL_YTX.sendMsg(obj, function(){
	//发送消息成功
	//处理用户逻辑，通知页面
		console.log('sendMsg success');
	}, function(obj){//失败
	//发送消息失败
	//处理用户逻辑，通知页面刷新，展现重发按钮
		console.log('sendMsg failure');
	}, function(sended, total){
	//发送图片或附件时的进度条
			//如果发送文本消息，可以不传该参数
	});
	
	
}

function receiveMsg(){
	//获取发送者为 
	var sender = obj.msgSender;
	//获取发送者昵称，如果不存在，使用账号代替
	var you_senderNickName = obj.senderNickName;
	var name = obj.msgSender;
	if(!!you_senderNickName){
	    name = you_senderNickName;
	}
	var content_type = null;
	//获取消息版本号
	var version = obj.version;
	//获取消息发送时间
	var time = obj.msgDateCreated;
	//获取消息类型 
	//1:文本消息 2:语音消息4:图片消息6:文件
	var msgType = obj.msgType;
	if(1 == msgType || 0 == msgType){
		//文本消息，获取消息内容
	var you_msgContent = obj.msgContent;
	}else if(2 == msgType){
		//语音消息，获取语音文件url
		var url = obj.msgFileUrl;
	}else if(3 == msgType){
	//3：视频消息，获取视频url
	    //语音消息，获取语音文件url
		var url = obj.msgFileUrl;
	}else if(4 == msgType){
		//图片消息 获取图片url
		var url = obj.msgFileUrl;
	}else{
		//后续还会支持(地理位置，视频，以及自定义消息等)
	}
	//通知前端更新页面
}

function createGroup(){
	var obj = new RL_YTX.CreateGroupBuilder();//新建创建群组对象
	obj.setGroupName('groupTest');//设置群组名称
	obj. setDeclared("欢迎体验云通讯群组功能");//设置群组公告
	obj. setScope(1);// 设置群组类型，如：1临时群组（100人）
	obj. setPermission(1)// 设置群组验证权限，如：需要身份验证2
	RL_YTX.createGroup(obj, function(obj){//发送消息
		var groupId = obj.data;//获取新建群组id,更新页面
		RECEIVE_ID = groupId;
		alert(RECEIVE_ID);
	}, function(obj){
	    //创建群组失败
		alert('error');
	});
	
	
}


function joinGroup(){
	var builder= new RL_YTX.JoinGroupBuilder();
	
	builder.setGroupId(RECEIVE_ID);//设置申请群组id
	
	builder.setDeclared('申请理由');//设置申请理由
	
	RL_YTX.joinGroup(builder, function(){//发送请求
	//操作成功
		
	},function(obj){
	//操作失败
		alert('error');
	});
	
	
}


function getGroupUser(){
	var obj = new RL_YTX.GetGroupMemberListBuilder();
	//设置群组id
	obj.setGroupId(RECEIVE_ID);
	//该接口为分页接口，如果要获取全部数据，设置pageSize为-1
	obj.setPageSize(-1);
	//发送请求
	RL_YTX.getGroupMemberList(obj, function(obj){
	    //成功获取数据，更新页面
		console.log(obj);
	}, function(obj){
	    //获取数据失败
		alert('error');
	});
	
	
}







