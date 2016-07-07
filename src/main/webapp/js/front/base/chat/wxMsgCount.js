SAVING_FLAG = false;
CURRENT_PAGE = 1;//当前页数
PAGE_COUNT = 10;//取多少条数据
$(function(){
	loadShopGoods();
	scrollBottom(loadShopGoods);
});

/**加载藏品*/
function loadShopGoods(){
	var userId = $("#userId").val();
	var param = {};
	param.userId = userId;
	param.rows = PAGE_COUNT;
	param.page = CURRENT_PAGE;
	$.post('/getGoodsList',param,function(rsp){
		if(rsp){
			if(rsp.status=='success'){
				var count =  rsp.rows.length;
				if(count && count > 0){
					makeShopGoodsListDom(rsp.rows,1);
					CURRENT_PAGE++;
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

function makeShopGoodsListDom(shopGoodsList,isAppend){
	var template = $('#template').html();
	Mustache.parse(template);   // optional, speeds up future uses
	var rendered = Mustache.render(template, {rows:shopGoodsList});
	if(isAppend){
		$('#goodsList').append(rendered);
	}else{
		$('#goodsList').html(rendered);
	}
}

function checkedGoods(id){
	var $list = $("#goodsId_"+id);
	if($list[0].checked){
		$("#goodsId_"+id).prop('checked',false);
	}else{
		$("#goodsId_"+id).prop('checked',true);
	}
}

function allMsg(){
	if(SAVING_FLAG)return;
	SAVING_FLAG = true;
	var $list = $(".goodsChecked:checked");
	if($list.length <= 0 ){
		frontWxAlert("至少选择1件商品，群发给粉丝.");
		SAVING_FLAG = false;
		return;
	}
	if($list.length > 9){
		frontWxAlert("最多选择9件商品，群发给粉丝.");
		SAVING_FLAG = false;
		return;
	}
	var goodsIds = ""
	for(var i = 0;i<$list.length;i++){
		goodsIds += ","+$list[i].value;
	}
	var obj = {};
	var wxMsgCountId = $("#wxMsgCountId").val();
	obj.id = wxMsgCountId;
	if(goodsIds.length > 0){
		goodsIds = goodsIds.substring(1);
		obj.goodsIds = goodsIds;
	}
	frontBaseLoadingOpen('正在保存数据...');
	$.post('/addOrUpdateWxMsgCount',obj,function(rsp){
		SAVING_FLAG = false;
		frontBaseLoadingClose();//解除遮罩
		if(rsp){
			frontLoginCheck(rsp);//登陆检查
			if(rsp.status == 'success'){
				frontBaseAlert(rsp.msg,"reload()");
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
	},'json');
}

function reload(){
	window.location.reload();
}