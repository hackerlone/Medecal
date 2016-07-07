/**初始化数据*/
SAVING_FLAG = false;
CURRENT_PAGE = 1;//当前页数
PAGE_COUNT = 10;
$(function(){
	loadFansRanKing();
	scrollBottom(loadFansRanKing);
	
});


function loadFansRanKing(){
	//var currentUserId = $("#currentUserId").val();
	var param = {};
	//param.currentUserId = currentUserId;
	param.fans_sum = 1;
	param.orderBy = 'fansSumCount';
	param.ascOrdesc = 'desc';
	param.rows = PAGE_COUNT;
	param.page = CURRENT_PAGE;
	$.post('/getUserList',param,function(rsp){
		if(rsp){
			frontLoginCheck(rsp);//登陆检查
			if(rsp.status=='success'){
				var count = rsp.total;
				if(count && count > 0){
					makeMeFansRanKingListDom(rsp.rows,1);
					CURRENT_PAGE++ ;
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

function makeMeFansRanKingListDom(fansList,isAppend){
	var template = $('#template').html();
	Mustache.parse(template);   // optional, speeds up future uses
	var rendered = Mustache.render(template, {rows:fansList});
	if(isAppend){
		$('#fansRanKing').append(rendered);
	}else{
		$('#fansRanKing').html(rendered);
	}
}

/**关注**/
function focusHe(userId){
	if(SAVING_FLAG)return;
	SAVING_FLAG = true;
	var obj = {};
	obj.userId = userId;
	frontBaseLoadingOpen('正在保存数据...');//加载遮罩
	$.post('/addOrUpdateFans',obj,function(rsp){
		SAVING_FLAG = false;
		frontBaseLoadingClose();//解除遮罩
		if(rsp){
			frontLoginCheck(rsp);//登陆检查 
			if(rsp.status == 'success'){
				location.reload();
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
	},'json');
}
/**取消关注**/
function cancelFocus(id){
	$.post('/deleteFans',{id:id},function(rsp){
		if(rsp){
			frontLoginCheck(rsp);//登陆检查 
			if(rsp.status == 'success'){
				location.reload();
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
	},'json');
}


