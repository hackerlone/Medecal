/** 单人聊天公用 JS  */
MSG_IN_DOM = 0;
function commonSendSingleMsg(chat,notDB){//msgContent,receiverTokenId,senderAvatar,
	chat.content = chat.content || $("#msgContent").val();
	if(!chat.content){
		frontBaseAlert('请输入您要发送的消息');return;
	}
	var obj = new RL_YTX.MsgBuilder();//新建消息体对象
	var serial = createSerial();
	obj.setId(serial);//设置自定义消息id
	obj.setText(chat.content);//设置发送的文本内容
	obj.setType(1);//设置发送的消息类型1文本消息4 图片消息6 附件消息,发送非文本消息时，text字段将被忽略，发送文本消息时 file字段将被忽略
	//var receiverId = chat.receiverTokenId;
	var receiverId = chat.receiverSerial;//应用登陆对应的账号：用户序号
	obj.setReceiver(receiverId);//设置接收者
	var singleOrGroup = 1;//单人聊天消息
	//obj.setDomain(chat.senderAvatar+"__"+chat.senderName+"__"+chat.senderId+"__"+chat.receiverId+"__"+singleOrGroup+"__"+serial);
	if(!chat.bonus)chat.bonus = 1;
	var domain = {
		avt:chat.senderAvatar,
		name:chat.senderName,
		uId:chat.senderId,
		rId:receiverId,
		sog:singleOrGroup,
		srl:serial,
		mg:'',
		price:'',
		agId:'',
		bonus:chat.bonus
	};
	
	domain = JSON.stringify(domain).replace(new RegExp("\"","g"),"'");
	obj.setDomain(domain);
	
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
	
	var domain = obj.msgDomain.replace(new RegExp("'","g"),"\"");
	var d = JSON.parse(domain);
	var avatar = d.avt || '/images/front/default_avatar.jpg';
	var name = d.name || '';
	if(d.sog == 1){//只处理单人聊天消息
		if(options.receiverId == d.uId){
			readChat(d.srl);
		}
		msgReceiveAppend({content:you_msgContent,receiverAvatar:avatar,senderName:name});
	}
}

function commonSingleAppend(rendered){
	$('#msg_container').append(rendered);
	MSG_IN_DOM++;
	if(MSG_IN_DOM > 150){
		$('#msg_container .chathostory:lt(10)').remove();
		MSG_IN_DOM -= 10;
	}
	showMsgBottom();//调整滚动条到最新消息
}

function msgSendAppend(chat,notDB){
	if(TEMPLATE_SELF){
		var rendered = Mustache.render(TEMPLATE_SELF, {chat:chat});
		commonSingleAppend(rendered);
		$("#msgContent").val('');
	}
	if(!notDB){
		addChat(chat);
	}
}

function msgReceiveAppend(chat){
	if(TEMPLATE_OTHER){
		var rendered = Mustache.render(TEMPLATE_OTHER, {chat:chat});
		commonSingleAppend(rendered);
	}
}

function readChat(serial){
	$.post('/updateChatRead',{serial:serial},function(rsp){},'json');
}

function addChat(chat){
	var param = {serial:chat.serial,receiverId:chat.receiverId,content:chat.content};
	if(chat.bonus && chat.bonus == 2)param.typeId = 2;
	$.post('/addChat',param,function(rsp){
		if(rsp.status != 'success'){
			frontBaseAlert(rsp.msg);
		}
	},'json');
}

function appendChatHistory(rows,userId,isShowHour){
	for(var i = 0;i<rows.length;i++){
		var c = rows[i];
		if(c.senderId == userId){
			msgSendAppend(c,true);
		}else{
			msgReceiveAppend(c);
		}
	}
}

function loadChatHistory(options){
	//return;//暂时不加载聊天记录
	var receiverId = options.senderId;//发送人作为接收人：接收其他人发的消息
	var param = {sc_order:'send_time___ASC',typeId:1,page:1,rows:10,currentUserChat:"1"};
	if(receiverId)param.receiverId = receiverId;
	$.post('/getChatList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var total = rsp.total;
				var rows = rsp.rows;
				if(rows && rows.length>0){
					var lastTime = rows[0].sendTime;
					$('#lastTime').text(formatDate(lastTime,1)).show();
					appendChatHistory(rows,rsp.userId);
				}
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
	},'json');
}

