/** 登陆注册 对应 JS */

RANDOM_IS_NEED = false;

$(function(){
	loginCheckCode();
	if(window.localStorage.length > 0){
		$("#saveid").attr('checked','true');
		var username = localStorage.getItem("username");
		var pswd = localStorage.getItem("password");
		if(username){
			$('#email').val(username);
		}
		if(pswd){
			$('#password').val(pswd);
		}
	}

	$('#login_validate,#login_validate2').bind('click',scode);
	$(window).keydown(function(event){
		if(event.keyCode == '13'){
			 login();
		}
	});
});

$("#saveid").click(function(){
	var flag = $("#saveid").is(':checked');
	if(!flag){
		$("#saveid").removeAttr('checked');
	}else{
		$("#saveid").attr('checked','true');
	}
});

//加载验证码
function scode(){ 
	$('#imgcode').attr('src','/login/loadVerificationCode?id='+parseInt(1000*Math.random()));
}

function loginCheckCode(){
	$.post('/loginCheckCode',null,function(rsp){
		if(rsp.status == 'randomCode_isneed'){
			RANDOM_IS_NEED = true;
			$("#verificationCode_div").show();
		}
	},'json');
}


function login(){
	var email = $('#email').val();
	var password = $('#password').val();
	var verificationCode = $('#verificationCode').val();
	if(!email || !password ){
		$(".formAssist").css('display','block');
		return;
	}
	if($("#saveid").is(':checked')){
		window.localStorage.setItem("username",email);
		window.localStorage.setItem("password",password);
	}else{
		window.localStorage.removeItem('username');
		window.localStorage.removeItem('password');
	}
	
	var obj = {username:email,password:password};
	if(RANDOM_IS_NEED){
		obj.verificationCode = verificationCode;
	}
	$.post('/doLogin',obj,function(rsp){
		if(rsp.status == 'success'){
			window.location.href="/";//alert('登录成功');
		}else if(rsp.status == 'randomCode_isneed' || rsp.status == 'input_error'){
			RANDOM_IS_NEED = true;
			$("#verificationCode_div").show();
			$(".formAssist").css('display','block');
			$("#content").html(rsp.msg);
		}else{
			$(".formAssist").css('display','block');
			$("#content").html(rsp.msg);
		}
	},'json');
}
