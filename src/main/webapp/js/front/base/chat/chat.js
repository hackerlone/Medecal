/** 聊天 主JS  */

//RECEIVE_ID = '8a48b551505b4af001505f21acd1091c';
CHAT_OBJ = {};
SELF = false;
TEMPLATE_SELF = null;
TEMPLATE_OTHER = null;
$(function(){
	initPage();
	initData();
	//loadShopList();//加载店铺列表（包含每个店铺的部分商品）
});

function initPage(){
	var $obj = $("#goodsShow");
	if($obj.length<=0){
		$obj = $('#lastTime');
	}
	var btn = $('#goodsAndTime').width() - $obj.width();
	var btnl = btn / 2;
	$obj.css("margin-left", btnl + "px");
}

function initData(){
	var self = $('#self').val();
	if(self){
		SELF = true;
		frontBaseAlert('不能和自己聊天，即将返回上一个页面');
		setTimeout(function(){
			history.back(-1);
		}, 2000);
		return;
	}
	
	var chatGroupId = $('#chatGroupId').val();
	var userTokenId = $('#userTokenId').val();
	var userTokenPswd = $('#userTokenPswd').val();
	var chatSig = $('#sig').val();
	var chatTimeStamp = $('#timeStamp').val();
	
	var senderId = $('#senderId').val();
	var senderAvatar = $('#senderAvatar').val();
	var senderName = $('#senderName').val();
	
	var receiverTokenId = $('#receiverTokenId').val();
	var receiverId = $('#receiverId').val();
	var receiverAvatar = $('#receiverAvatar').val();
	var receiverName = $('#receiverName').val();
	
	var senderSerial = $('#senderSerial').val();
	var receiverSerial = $('#receiverSerial').val();
	
	CHAT_OBJ = {
		userTokenId:userTokenId,
		userTokenPswd:userTokenPswd,
		chatSig:chatSig,
		chatTimeStamp:chatTimeStamp,
		senderId:senderId,
		senderAvatar:senderAvatar,
		senderName:senderName,
		receiverId:receiverId,
		receiverTokenId:receiverTokenId,
		receiverAvatar:receiverAvatar,
		receiverName:receiverName,
		senderSerial:senderSerial,
		receiverSerial:receiverSerial
	};
	
	TEMPLATE_SELF = $('#template_self').html();
	Mustache.parse(TEMPLATE_SELF); 
	TEMPLATE_OTHER = $('#template_other').html();
	Mustache.parse(TEMPLATE_OTHER);
	
	var options = {
			userTokenId:userTokenId,
			userTokenPswd:userTokenPswd,
			senderId:senderId,
			senderName:senderName,
			receiverId:receiverId,
			chatSig:chatSig,
			chatTimeStamp:chatTimeStamp,
			senderSerial:senderSerial,
			receiverSerial:receiverSerial
		}
	
	initCommonChat(options, null);//onLoginFun:null,isShowHour:false
	
}

function sendMsg(){
	if(SELF)return;
	var chat = clone(CHAT_OBJ);
	chat.content = $("#msgContent").val();
	commonSendSingleMsg(chat);
}




