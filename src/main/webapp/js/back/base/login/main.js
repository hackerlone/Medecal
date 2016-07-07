$(function() {

	initSwitchShowHide();
	initModernizr();
	
	$('#login_submit').on('click', function(event){
		event.preventDefault();
		doLogin();
	});
});

function initSwitchShowHide(){
	$('.hide-password').on('click', function(){//hide or show password
		var $this= $(this),
			$password_field = $this.prev('input');
		( 'password' == $password_field.attr('type') ) ? $password_field.attr('type', 'text') : $password_field.attr('type', 'password');
		( '隐藏' == $this.text() ) ? $this.text('显示') : $this.text('隐藏');
		$password_field.putCursorAtEnd();//focus and move cursor to the end of input field
	});
	
}

function doInit(){
	if(window.localStorage){
		var username = localStorage.getItem("signin-username");
		var pswd = localStorage.getItem("signin-password");
		if(username || pswd){
			$('#signin-username').val(username);
			$('#signin-password').val(pswd);
			$("#remember").attr('checked',true);
		}
	}
	$(window).keydown(function(event){
		if(event.keyCode == '13'){
			 doLogin();
		}
	});
}

function doLogin(){
	$('#signin-username,#signin-password,#signin-verificationCode').removeClass('has-error').next('span').removeClass('is-visible');
	var username = $('#signin-username').val();
	var password = $('#signin-password').val();
	var verificationCode = $('#signin-verificationCode').val();
	if(!username){
		$('#signin-username').addClass('has-error').next('span').addClass('is-visible');return;
	}
	if(!password){
		$('#signin-password').addClass('has-error').next('span').addClass('is-visible');return;
	}
	if(!verificationCode){
		$('#signin-verificationCode').addClass('has-error').next('span').addClass('is-visible');return;
	}
	if($("#remember").is(':checked')){
		window.localStorage.setItem("signin-username",username);
		window.localStorage.setItem("signin-password",password);
	}else{
		window.localStorage.removeItem('signin-username');
		window.localStorage.removeItem('signin-password');
	}
	
	var obj = {loginAccount:username,password:password,verificationCode:verificationCode};
	
	$.post('/back/doBackLogin',obj,function(rsp){
		if(rsp.status == 'success'){
			window.location.href="/back/main";return;//alert('登录成功');
		}else{
			alert(rsp.msg);
		}
	},'json');
}

function quickLogin(){
	var obj = {loginAccount:'admin@wonature.com',password:'888888',verificationCode:'1'};
	$.post('/back/doBackLogin',obj,function(rsp){
		if(rsp.status == 'success'){
			window.location.href="/back/main";return;//alert('登录成功');
		}else{
			alert(rsp.msg);
		}
	},'json');
}

function loadRandomCode(){ 
	$('#imgcode').attr('src','/login/loadVerificationCode?id='+parseInt(1000*Math.random()));
}

function forgetPassword(){
	alert('请联系管理员或软件提供方找回密码');
}

function initModernizr(){
	//IE9 placeholder fallback
	//credits http://www.hagenburger.net/BLOG/HTML5-Input-Placeholder-Fix-With-jQuery.html
	if(!Modernizr.input.placeholder){
		$('[placeholder]').focus(function() {
			var input = $(this);
			if (input.val() == input.attr('placeholder')) {
				input.val('');
		  	}
		}).blur(function() {
		 	var input = $(this);
		  	if (input.val() == '' || input.val() == input.attr('placeholder')) {
				input.val(input.attr('placeholder'));
		  	}
		}).blur();
		$('[placeholder]').parents('form').submit(function() {
		  	$(this).find('[placeholder]').each(function() {
				var input = $(this);
				if (input.val() == input.attr('placeholder')) {
			 		input.val('');
				}
		  	})
		});
	}
}

//credits http://css-tricks.com/snippets/jquery/move-cursor-to-end-of-textarea-or-input/
jQuery.fn.putCursorAtEnd = function() {
	return this.each(function() {
    	// If this function exists...
    	if (this.setSelectionRange) {
      		// ... then use it (Doesn't work in IE)
      		// Double the length because Opera is inconsistent about whether a carriage return is one character or two. Sigh.
      		var len = $(this).val().length * 2;
      		this.setSelectionRange(len, len);
    	} else {
    		// ... otherwise replace the contents with itself
    		// (Doesn't work in Google Chrome)
      		$(this).val($(this).val());
    	}
	});
};