<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_back_css.htm"%>
<link rel="stylesheet" href="/third-party/reset/reset.css"/> <!-- CSS reset -->
<link rel="stylesheet" href="/css/back/login.css" title="v"/> <!-- Gem style -->
</head>
<body>
	<header role="banner">
		<div id="cd-logo"><a href="#0"><img src="/images/back/login/cd-logo.svg" alt="Logo"></a></div>

		<nav class="main-nav">
			<ul>
				<!-- inser more links here -->
				<!-- <li><a class="cd-signin" href="#0">登 陆</a></li> -->
			</ul>
		</nav>
	</header>

	<div class="cd-user-modal is-visible" style="background:none;"> <!-- this is the entire modal form, including the background -->
		<div class="cd-user-modal-container" style="background:#EAE9E9;top:10%;"> <!-- this is the container wrapper -->
			<!-- <ul class="cd-switcher">
				<li><a href="#0">登 陆</a></li>
				<li><a href="#0">登 陆</a></li>
			</ul> -->
			
			<button style="margin-left:230px;" onclick="quickLogin();return false;" class="button button-primary button-rounded button-small">快捷登陆</button>
			
			<div id="cd-login" class="is-selected" style="background:none;"> <!-- log in form -->
				<form class="cd-form">
					<p class="fieldset">
						<label class="image-replace cd-username" for="signin-username">账号</label>
						<input class="full-width has-padding has-border" id="signin-username" type="text" placeholder="账号">
						<span class="cd-error-message">请输入您的账号</span>
					</p>

					<p class="fieldset">
						<label class="image-replace cd-password" for="signin-password">密码</label>
						<input class="full-width has-padding has-border" id="signin-password" type="password"  placeholder="密码">
						<span class="cd-error-message">请输入您的密码</span>
						<!-- <a href="#0" class="hide-password">隐藏</a> -->
					</p>
					
					<p class="fieldset" style="position: relative;top: -19px;">
						<label class="image-replace cd-close" for="signin-verificationCode">验证码</label>
						<input class="full-width has-padding has-border" id="signin-verificationCode" type="text" placeholder="验证码" style="min-width:200px;width:55%">
						<span class="cd-error-message">请输入验证码</span>
						<a href='javascript:loadRandomCode();'>
							<img src='/login/loadVerificationCode' id='imgcode' style="height: 50px;position: relative;top: 19px;"/>
						</a>
						<a href="javascript:loadRandomCode();">重新加载</a>
					</p>
					
					<p class="fieldset" style="position: relative;top: -19px;">
						<input type="checkbox" id="remember" checked />
						<label for="remember-me">记住我</label>
					</p>

					<p class="fieldset" style="position: relative;top: -19px;">
						<input id="login_submit" class="full-width" type="submit" value="登 陆" />
					</p>
				</form>
				
				<p class="cd-form-bottom-message" onclick="forgetPassword();"><a style="cursor:pointer;color:#2f889a;">忘记密码?</a></p>
			</div> <!-- cd-login -->

		</div> <!-- cd-user-modal-container -->
	</div> <!-- cd-user-modal -->
	<footer class="login_foot" style="">成都蓝海飞鱼科技有限公司提供技术支持</footer>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_back_js.htm"%>
	<script type="text/javascript" src="/third-party/compatible/modernizr.js"></script><!-- Modernizr -->
	<script type="text/javascript" src="/js/back/base/login/main.js" title="v"></script>
</body>
</html>
