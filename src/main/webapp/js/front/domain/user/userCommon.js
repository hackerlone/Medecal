$(function(){
	initCommonPage();
});

function initCommonPage(){
	var targetId = locache.get("user_li_id");
	if(null == targetId && "" != targetId){
		targetId = 1;
	}
	$("#user_li_"+targetId).css({'color':'#63a13f','background-color':'#C5E6B0'});
	$("#user_li_"+locache.get("user_li_id")).parent().parent().show();
	jQuery('.pz_SideLayer2 li').on("click",function(){
		var display =jQuery(this).find('.down_ul').css('display');
		if(display == 'none'){
			jQuery(this).find('.down_ul').show();
		}
		if(display == 'block'){
			jQuery(this).find('.down_ul').hide();
		}
		
	}
	);
	
}

var userAddLocache = function (id,url){
	var targetId = ""; 
	if(null != locache.get("user_li_id") && "" != locache.get("user_li_id")){
		if(id != locache.get("user_li_id")){
			$("#user_li_"+locache.get("user_li_id")).css({'color':' #444444', 'background-color':'#C5E6B0'});
			//$("#user_li_"+locache.get("user_li_id")).parent().removeAttr("selected");
			locache.remove("user_li_id");
			targetId = id;
		}else{
			targetId = id;
		}
	}else{
		targetId = 1;
	}
	locache.set("user_li_id", targetId);
	lh.jumpToUrl(url);
	$("#user_li_"+locache.get("user_li_id")).css({'color':'#63a13f', 'background-color':'#C5E6B0'});
	//$("#user_li_"+locache.get("user_li_id")).parent().attr('selected','selected');
}
