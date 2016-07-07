/** 聊天公用 JS  */

/** 初始化聊天组件  */
function initCommonChat(options,onLoginFun,isShowHour){
	//初始化SDK，App的通知消息显示号码
	var resp = RL_YTX.init("8a48b551505b4af001505f21aca8091b"); 
	if(102 == resp.code){//缺少必要参数,用户逻辑处理
		frontBaseAlert('缺少必要参数');
	}else if(101 == resp.code){//不支持HTML5，关闭页面,用户逻辑处理
		frontBaseAlert('您的浏览器不支持HTML5');
	}else if(200 == resp.code){//初始化成功,用户逻辑处理
		console.log('init success');
		chatLogin(options,onLoginFun,isShowHour);
	}  
	
}

function chatLogin(options,onLoginFun,isShowHour){
	var loginBuilder = new RL_YTX.LoginBuilder(3, options.userTokenId,options.userTokenPswd);
	//var loginBuilder = new RL_YTX.LoginBuilder(1, USER_TOKEN_ID, '', CHAT_SIG, CHAT_TIMESTAMP);
	//注意：sig字段又用户请求自己的服务器生成，sig规则：MD5(appid+userName+timestamp+apptoken); 当voip账号密码登录时，可以不传sig
	//执行用户登录
	RL_YTX.login(loginBuilder, function(obj){//登录成功回调
		RL_YTX.onMsgReceiveListener(function(obj){//收到push消息或者离线消息
			commonReceiveMsg(obj,options);//将接收到的消息显示到页面
		});
		//注册群组通知事件监听
		RL_YTX.onNoticeReceiveListener(function(obj){//收到群组通知
			console.log('onNoticeReceiveListener');
		});
		RL_YTX.onConnectStateChangeLisenter(function(obj){//连接状态变更
			console.log('onConnectStateChangeLisenter');
		});
		loadChatHistory(options,isShowHour);//历史聊天信息-fromDB
		if(onLoginFun)onLoginFun();
	},function(obj){
		//登录失败方法回调
		frontBaseAlert('聊天组件登录失败');
	});
	
}

function chatLogout(){
	RL_YTX.logout(function(){//登出成功处理
		console.log('logout success');
	}, function(obj){//登出失败处理
		frontBaseAlert('聊天组件登出失败');
	});
}

function commonSendMsg(chat,notDB){//msgContent,receiverTokenId,senderAvatar,
	chat.content = chat.content || $("#msgContent").val();
	if(!chat.content){
		frontBaseAlert('请输入您要发送的消息');return;
	}
	var obj = new RL_YTX.MsgBuilder();//新建消息体对象
	var serial = createSerial();
	obj.setId(serial);//设置自定义消息id
	obj.setText(chat.content);//设置发送的文本内容
	obj.setType(1);//设置发送的消息类型1文本消息4 图片消息6 附件消息,发送非文本消息时，text字段将被忽略，发送文本消息时 file字段将被忽略
	var singleOrGroup = 1;
	chat.serial = serial;
	if(chat.chatGroupId){
		obj.setReceiver(chat.chatGroupId);//设置接收群组
		singleOrGroup = 2;
	}else{
		obj.setReceiver(chat.receiverTokenId);//设置接收者
	}
	
	obj.setDomain(chat.senderAvatar+"__"+chat.senderName+"__"+chat.senderId+"__"+chat.receiverId+"__"+singleOrGroup+"__"+serial);
	RL_YTX.sendMsg(obj, function(){//发送消息成功
		msgSendAppend(chat,notDB);
	}, function(obj){//发送消息失败
		chat.content += '-发送失败';
		msgSendAppend(chat,1);
	}, function(sended, total){//发送图片或附件时的进度条,如果发送文本消息，可以不传该参数
	});
}

function commonReceiveMsg(obj,options){
	var sender = obj.msgSender;//获取发送者为 
	var you_senderNickName = obj.senderNickName;//获取发送者昵称，如果不存在，使用账号代替
	var name = obj.msgSender;
	if(!!you_senderNickName){
	    name = you_senderNickName;
	}
	var content_type = null;
	var version = obj.version;//获取消息版本号
	var time = obj.msgDateCreated;//获取消息发送时间
	var msgType = obj.msgType;//获取消息类型,1:文本消息 2:语音消息4:图片消息6:文件
	if(1 == msgType || 0 == msgType){//文本消息，获取消息内容
		var you_msgContent = obj.msgContent;
	}else if(2 == msgType){//语音消息，获取语音文件url
		var url = obj.msgFileUrl;
	}else if(3 == msgType){//3：视频消息，获取视频url//语音消息，获取语音文件url
		var url = obj.msgFileUrl;
	}else if(4 == msgType){//图片消息 获取图片url
		var url = obj.msgFileUrl;
	}else{
		//后续还会支持(地理位置，视频，以及自定义消息等)
	}
	
	var date = new Date(parseInt(time));
	var h = date.getHours();
	var mi = date.getMinutes();
	if(h<10)h = '0'+h;
	if(mi<10)mi = '0'+mi;
	obj.sendHour = h+":"+mi;
	
	var ary = obj.msgDomain.split("__");
	var avatar = '/images/front/default_avatar.jpg';
	var name = '';
	var senderId = null;
	var receiverId = null;
	var singleOrGroup = null;
	var serial = null;
	if(ary && ary.length>0){
		avatar = ary[0];
		name = ary[1];
		senderId = ary[2];
		receiverId = ary[3];
		singleOrGroup = ary[4];
		serial = ary[5];
	}
	if(singleOrGroup == 1){
		if(options.receiverId == senderId){
			readChat(serial);
		}
		//群组就不更新阅读状态了，没有读到就不再显示
		msgReceiveAppend({content:you_msgContent,receiverAvatar:avatar,senderName:name,sendHour:obj.sendHour});
	}else{
		if(options.chatGroupId == receiverId){
			if(options.senderId == senderId){
				name = '<span style="color:blue;">'+name+'</span>';
			}
			msgReceiveAppend({content:you_msgContent,receiverAvatar:avatar,senderName:name,sendHour:obj.sendHour});
		}
	}
}

function msgSendAppend(chat,notDB){
	var rendered = Mustache.render(TEMPLATE_SELF, {chat:chat});
	$('#msg_container').append(rendered);
	$("#msgContent").val('');
	showMsgBottom();//调整滚动条到最新消息
	if(!notDB){
		addChat(chat);
	}
}

function msgReceiveAppend(chat){
	var rendered = Mustache.render(TEMPLATE_OTHER, {chat:chat});
	$('#msg_container').append(rendered);
}

function showMsgBottom(domId){
	/*if(!domId)domId = 'msgSend';
	$('html,body').animate({scrollTop:$('#'+domId).offset().top}, 800);*/
	$("#msg_container").scrollTop($("#msg_container")[0].scrollHeight); 
}

function readChat(serial){
	$.post('/updateChatRead',{serial:serial},function(rsp){},'json');
}

function addChat(chat){
	var typeId = chat.typeId;
	var param = {serial:chat.serial,senderId:chat.senderId,typeId:typeId,content:chat.content};
	if(typeId == 1){
		param.receiverId = chat.receiverId;
	}else if(typeId == 2){
		param.chatGroupId = chat.chatGroupId;
	}
	$.post('/addChat',param,function(rsp){
		if(rsp.status != 'success'){
			frontBaseAlert(rsp.msg);
		}
	},'json');
}

function appendChatHistory(rows,userId,isShowHour){
	for(var i = 0;i<rows.length;i++){
		var c = rows[i];
		if(isShowHour){
			var date = new Date(c.sendTime);
			var h = date.getHours();
			var mi = date.getMinutes();
			if(h<10)h = '0'+h;
			if(mi<10)mi = '0'+mi;
			c.sendHour = h+":"+mi;
		}
		if(c.senderId == userId){
			msgSendAppend(c,true);
		}else{
			msgReceiveAppend(c);
		}
	}
}

function loadChatHistory(options,isShowHour){
	var typeId = options.typeId;
	var receiverId = options.receiverId;
	var chatGroupId = options.chatGroupId;
	if(!typeId)typeId = 1;
	var param = {sc_order:'send_time___ASC',typeId:typeId,page:1,rows:10};
	if(receiverId)param.receiverId = receiverId;
	if(chatGroupId)param.chatGroupId = chatGroupId;
	$.post('/getChatList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var total = rsp.total;
				var rows = rsp.rows;
				if(rows && rows.length>0){
					var lastTime = rows[0].sendTime;
					$('#lastTime').text(formatDate(lastTime,1)).show();
					appendChatHistory(rows,rsp.userId,isShowHour);
				}
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
	},'json');
}

function joinGroup(groupId,onJoinGroupFun){
	var builder= new RL_YTX.JoinGroupBuilder();
	builder.setGroupId(groupId);//设置申请群组id
	RL_YTX.joinGroup(builder, function(d){//操作成功
		//console.log('join group success');
		if(onJoinGroupFun)onJoinGroupFun();
	},function(obj){//操作失败
		var code = obj.code;
		if(code == 590017){//成员已经存在,不做处理
			if(onJoinGroupFun)onJoinGroupFun();
		}else if(code == 590010){//群组不存在
			frontBaseAlert('本次拍卖已结束');
		}else if(code == 170003){//未登录
			frontBaseAlert('您未成功登录');
		}else{
			frontBaseAlert('加入群组失败');
		}
	});
}

