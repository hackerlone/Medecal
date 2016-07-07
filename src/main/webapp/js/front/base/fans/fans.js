/**初始化数据*/
PAGE_COUNT = 10;//取多少条数据
CURRENT_PAGE = 1;//当前页数
CURRENT_FANS_PAGE = 1;//当前页数
CURRENT_RECOMMEND_PAGE = 1;
CURRENT_NOTICE_PAGE = 1;
CURRENT_CHAT_PAGE = 1;
CURRENT_COUNT = 0;//当前消息条数
CURRENT_TAB = 'notice_tab';
CURRENT_NOTICE_TYPE = 'all';
$(function(){
	initPage();
	loadData();
	scrollBottom(scrollBottomTab);
});

function initPage(){
	initUlBox1();
	initUlBox2();
	initTeacher();
	initMenu();
	initTopSearch();
	var label = $('#labelShow').val();
	if(label=='fans'){
		$('#slide_teacher .hd ul li').trigger('click');
		CURRENT_TAB = 'fans_tab1';
	}
}

function initTeacher(){
	var li = $('#slide_teacher .hd ul li');
	var ul = $('#slide_teacher .bd .ul_out_box');
	$(li).click(function() {
		$(li).removeClass('on');
		$(this).addClass('on');
		$(ul).hide();
		$(ul).eq($(this).index()).stop().fadeIn(300);
	})
}

function initUlBox1(){
	var li1 = $('#ul_out_box1 .inhd ul li');
	var ul1 = $('#ul_out_box1 .inbd .inbd_ul');
	$(li1).click(function() {
		$(li1).removeClass('on');
		$(this).addClass('on');
		$(ul1).hide();
		$(ul1).eq($(this).index()).stop().fadeIn(300);
		CURRENT_TAB = 'notice_tab';
	})
}

function initUlBox2(){
	var li2 = $('#ul_out_box2 .inhd ul li');
	var ul2 = $('#ul_out_box2 .inbd .inbd_ul');
	$(li2).click(function() {
		$(li2).removeClass('on');
		$(this).addClass('on');
		$(ul2).hide();
		$(ul2).eq($(this).index()).stop().fadeIn(300);
		CURRENT_TAB = this.id;
		
		if(this.id == 'fans_tab3'){
			$('#batchBonus').hide();
		}else{
			$('#batchBonus').show();
		}
		//var text = $(this).text();
	})
}

function loadData(){
	loadFocusMe();
	loadMeFocus();
	loadRecommend();
	setTimeout(function(){
		loadChat();
	},50);
	loadNoticeList();
}

function scrollBottomTab(){
	var tab = CURRENT_TAB;
	if(tab == 'notice_tab'){
		showNotice();
	}else if(tab == 'fans_tab1'){
		loadMeFocus();
	}else if(tab == 'fans_tab2'){
		loadFocusMe();
	}else if(tab == 'fans_tab3'){
		loadRecommend();
	}
}

function loadMeFocus(){
	var currentUserId = $("#currentUserId").val();
	var searchKey = $("#searchKey").val();
	var isAppend = true;
	if(CURRENT_PAGE == 1){
		isAppend = false;
		$('#meFocus').empty();
	}
	var param = {fansId:currentUserId, rows:PAGE_COUNT, page:CURRENT_PAGE};
	if(searchKey)param.searchName = searchKey;
	$.post('/getFansList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var count = rsp.total;
				if(count && count > 0){
					$("#meFocusCount").text('('+count+')');
					CURRENT_PAGE ++;
					makeMeFocusListDom(rsp.rows, isAppend);
				}else{
					$('#resultTip').text('没有更多数据').show();
				}
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
		SCROLL_LOADING = false;//设置为加载完毕
	},'json');
}

function makeMeFocusListDom(fansList,isAppend){
	var template = $('#template').html();
	Mustache.parse(template);   // optional, speeds up future uses
	var rendered = Mustache.render(template, {rows:fansList});
	if(isAppend){
		$('#meFocus').append(rendered);
	}else{
		$('#meFocus').html(rendered);
	}
}

function loadFocusMe(){
	var currentUserId = $("#currentUserId").val();
	var param = {userId:currentUserId, rows:PAGE_COUNT, page:CURRENT_FANS_PAGE};
	$.post('/getFansList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var count = rsp.total;
				if(count && count > 0){
					$("#focusMeCount").text('('+count+')');
					makeFocusMeListDom(rsp.rows,1);
					CURRENT_FANS_PAGE ++;
				}else{
					$('#resultTip').text('没有更多数据').show();		
				}
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
		SCROLL_LOADING = false;//设置为加载完毕
	},'json');
}

function makeFocusMeListDom(fansList,isAppend){
	var template1 = $('#template1').html();
	Mustache.parse(template1);   // optional, speeds up future uses
	var rendered = Mustache.render(template1, {rows:fansList});
	if(isAppend){
		$('#focusMe').append(rendered);
	}else{
		$('#focusMe').html(rendered);
	}
}

function loadRecommend(){
	var param = {rows:PAGE_COUNT,page:CURRENT_RECOMMEND_PAGE};
	$.post('/getFansList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var count = rsp.total;
				if(count && count > 0){
					$("#recommendCount").text('('+count+')');
					makeRecommendListDom(rsp.rows,1);
					CURRENT_RECOMMEND_PAGE ++;
				}else{
					$('#resultTip').text('没有更多数据').show();			
				}
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
		SCROLL_LOADING = false;//设置为加载完毕
	},'json');
}

function makeRecommendListDom(fansList,isAppend){
	var template2 = $('#template2').html();
	Mustache.parse(template2);   // optional, speeds up future uses
	var rendered = Mustache.render(template2, {rows:fansList});
	if(isAppend){
		$('#recommend').append(rendered);
	}else{
		$('#recommend').html(rendered);
	}
}

function loadNoticeList(isShowHaveRead){
	var param = {rows:PAGE_COUNT,page:CURRENT_NOTICE_PAGE,orderBy:"A.read_status",ascOrdesc:"ASC"};//,notRead:1未读
	if(!isShowHaveRead){//判断是否显示已读
		param.notRead = 1;
	}
	$.post('/getMyNoticeList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var count = rsp.total;
				if(count && count > 0){
					/*if(CURRENT_NOTICE_PAGE == 1){
						//CURRENT_COUNT = count;
						//$("#notice").text(count);
					}*/
					CURRENT_NOTICE_PAGE ++;
					makeNoticeListDom(rsp.rows,1);
				}else{
					$('#resultTip').text('没有更多数据').show();
					if(CURRENT_NOTICE_PAGE == 1){
						$("#notice").css('background-color','white');
					}
				}
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
		SCROLL_LOADING = false;//设置为加载完毕
	},'json');
}

function makeNoticeListDom(noticeList,isAppend){
	var template_notice = $('#template_notice').html();
	Mustache.parse(template_notice);   // optional, speeds up future uses
	for(var a in noticeList){
		if(noticeList[a].readStatus == 2){
			noticeList[a].readStatus = "";
		}
	}
	var rendered = Mustache.render(template_notice, {rows:noticeList});
	if(isAppend){
		$('#noticeList').append(rendered);
	}else{
		$('#noticeList').html(rendered);
	}
}

function loadChat(isShowHaveRead){
	var param = {rows:PAGE_COUNT,page:CURRENT_CHAT_PAGE,currentUserChat:"1"};
	if(!isShowHaveRead){//判断是否显示已读
		param.notRead = 1;
	}
	$.post('/getChatList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var count = rsp.total;
				if(count && count > 0){
					CURRENT_CHAT_PAGE ++;
					CURRENT_COUNT += CURRENT_COUNT;	
					makeChatListDom(rsp.rows, 1, isShowHaveRead);
				}else{
					$('#resultTip').text('没有更多数据').show();			
				}
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
		SCROLL_LOADING = false;//设置为加载完毕
	},'json');
}

function makeChatListDom(chatList,isAppend, isShowHaveRead){
	isShowHaveRead = 1;//TODO 暂时全部不重叠显示
	if(isShowHaveRead){//显示所有消息，不用重叠显示
		var template_chat = $('#template_all_chat').html();
		Mustache.parse(template_chat);   // optional, speeds up future uses
		var rendered = Mustache.render(template_chat, {rows:chatList});
		if(isAppend){
			$('#noticeList').append(rendered);
		}else{
			$('#noticeList').html(rendered);
		}
		return;
	}
	//TODO 暂时不合并
	/*var data = [];
	for(var i=0; i<chatList.length; i++){
		var chat = chatList[i];
		var haveFlag = false;
		for(var j=0; j<data.length; j++){
			var saved_chat = data[j];
			if(chat.id = saved_chat.id){
				haveFlag = true;
				saved_chat.brothers.push(chat);
				saved_chat.repeat = 1;
				break;
			}
		}
		if(!haveFlag){
			chat.brothers = [chat];
			data.push(chat);
		}
	}*/
	
	var template_chat = $('#template_chat').html();
	Mustache.parse(template_chat);   // optional, speeds up future uses
	//var rendered = Mustache.render(template_chat, {rows:data});
	var rendered = Mustache.render(template_chat, {rows:chatList});
	if(isAppend){
		$('#noticeList').append(rendered);
	}else{
		$('#noticeList').html(rendered);
	}
	/*if(CURRENT_COUNT > 0){
		$("#noticeSum").text(CURRENT_COUNT);
	}else{
		$(".pf_teacher").hide();
	}*/
}

/**
 * 分类显示提示消息
 *  'all'所有消息
 *	'notice'通知提醒
 *	'chat聊天消息
 */
function showNotice(type){
	if(type){
		$('#noticeList').empty();
		CURRENT_NOTICE_PAGE = 1;
		CURRENT_CHAT_PAGE = 1;
		CURRENT_NOTICE_TYPE = type;
	}
	if(CURRENT_NOTICE_TYPE == 'all'){
		loadNoticeList();
		loadChat();
	}else if(CURRENT_NOTICE_TYPE == 'notice'){
		loadNoticeList(1);
	}else if(CURRENT_NOTICE_TYPE == 'chat'){
		loadChat(1);
	}
}

function searchUser(){
	CURRENT_PAGE = 1;
	$('#slide_teacher .hd ul li').trigger('click');
	loadMeFocus();
}

function readed(serial){
	$.post('/updateChatRead',{serial:serial},function(rsp){
		if(rsp){
			frontLoginCheck(rsp);
			if(rsp.status == 'success'){
				window.location.reload();
			}
		}
	},'json');
}

function noticeReaded(serial){
	$.post('/updateNoticeRead',{serial:serial},function(rsp){
		if(rsp){
			frontLoginCheck(rsp);
			if(rsp.status == 'success'){
				window.location.reload();
			}
		}
	},'json');
}


