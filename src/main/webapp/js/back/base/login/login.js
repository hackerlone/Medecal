/** 登陆注册 对应 JS */

RANDOM_IS_NEED = false;

$(function() {
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
	
	
	cover();
	$(window).resize(function(){
		cover();
	});
	if (!-[1,]){ //IE
	    if (!-[1,]&&!window.XMLHttpRequest){
		  $.dialog.alert('您当前使用的浏览器版本太低，建议升级到更高版本的浏览器！',function(){});
		}
		$("#sub").hover(   
				function() {   
				$("#sub").stop().animate({opacity: '1'},1000);   
		   },    
		 function() {   
			   $("#sub").stop().animate({opacity: '0.5'},1000);   
		 });  
	 } 
	
});

$(function(){
	
	
});
function cover(){
	var win_width = $(window).width();
	var win_height = $(window).height();
	$("#bigpic").attr({width:win_width,height:win_height});
   $("#wrap").attr("style","position:absolute;left:"+(win_width-510)/2+"px");
}

var tip = '${code}';
if(tip){
	if(tip == 1){
		alert('系统登陆异常');
	}else if(tip == 2){
		alert('验证码错误');
	}else if(tip == 3){
		alert('用户名和密码不能为空');
	}else if(tip == 4){
		alert('用户名或密码错误');
	}else if(tip == 200){
		window.location.href="/admin/main.html";
	}
}

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
	$.post('/back/doLogin',obj,function(rsp){
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
