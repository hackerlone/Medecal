$(function(){
	initData();
});

function initData(){
	loadRandomCode();
}

function loadRandomCode(){ 
	$('#imgcode').attr('src','/login/loadVerificationCode?id='+parseInt(1000*Math.random()));
}

function doMobileLogin(){
	var phone = $("#phone").val();
	if(!phone){
		$("#phoneTip").show();
		return;
	}
	var password = $("#password").val();
	if(!password){
		$("#passwordTip").show();
		return;
	}
	var verificationCode = $("#verificationCode").val();
	if(!verificationCode){
		$("#verificationCodeTip").show();
		return;
	}
	var obj = {loginAccount:phone,password:password,verificationCode:verificationCode};
	lh.post("front", '/doLogin', obj, function(rsp){
		if(rsp.status == 'success'){
			var targetId = locache.get("user_li_id");
			if(null != targetId && "" != targetId){
				locache.set("user_li_id", 4);
				lh.jumpToUrl('/userReportList');
			}else{
				locache.set("user_li_id", 1);
				lh.jumpToUrl('/userBaseInformation');
			}
		}else{
			lh.alert(rsp.msg);
		}
	}, 'json')
}

function loginQuickly(){
	var obj = {loginAccount:lh.test.phone,password:lh.test.password,verificationCode:1};
	lh.post("front", '/doLogin', obj, function(rsp){
		if(rsp.status == 'success'){
			var targetId = locache.get("user_li_id");
			if(null != targetId && "" != targetId){
				locache.set("user_li_id", 4);
				lh.jumpToUrl('/userReportList');
			}else{
				locache.set("user_li_id", 1);
				lh.jumpToUrl('/userBaseInformation');
			}
		}else{
			lh.alert(rsp.msg);
		}
	}, 'json')
}