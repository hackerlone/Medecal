<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
</head>
<body>
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a> - <a href="/doctorLogin">医生入口</a></div>
	        <div class="c_1100_12">
	        	<div class="l_720">
	            	<img src="/images/front/ysrk.png" width="685" height="407" />
	            </div>
	            <div class="r_378">
	                <div class="r_change">
	                    <div class="t_345">
	                        <span>医生入口</span>
	                        <button style="margin-left:172px;margin-top:3px;" type="button" onclick="loginQuickly();" class="btn btn-success">快速登陆</button>
	                    </div>
	                    <div class="d_345">
	                        <div class="d_change">
	                            <table cellpadding="0" cellspacing="0" border="0" width="345">
			                    	<tr height="42" valign="middle">
			                        	<td width="83" align="right">手机号：</td>
			                            <td width="15">&nbsp;</td>
			                            <td width="247" colspan="2"><input id="phone" type="text" class="input10" placeholder="请输入手机号码"/></td>
			                        </tr>
			                        <tr height="22" valign="middle">
			                        	<td width="83" >&nbsp;</td>
			                            <td width="15">&nbsp;</td>
			                            <td width="247" colspan="2" id="phoneTip" style="color:#fc0000; font-size:12px;display:none;">请填写手机号码！</td>
			                        </tr>
			                        <tr height="53" valign="middle">
			                        	<td width="83" align="right">密码：</td>
			                            <td width="15">&nbsp;</td>
			                            <td width="247" colspan="2"><input id="password" type="password" class="input10" placeholder="请输入密码"/></td>
			                        </tr>
			                        <tr height="22" valign="middle">
			                        	<td width="83" >&nbsp;</td>
			                            <td width="15">&nbsp;</td>
			                            <td width="247" colspan="2" id="passwordTip" style="color:#fc0000; font-size:12px;display:none;">请填写密码！</td>
			                        </tr>
			                        <tr height="70" valign="bottom">
			                        	<td width="83" align="right" style="line-height:42px;">验证码：</td>
			                            <td width="15">&nbsp;</td>
			                            <td width="116"><input id="verificationCode" type="text" class="input13" placeholder="请输入验证码"/>　</td>
			                        	<td width="134"><i style="float:left; width:96px; height:42px;">
				                        	<a href='javascript:loadRandomCode();'>
												<img src='/login/loadVerificationCode' id='imgcode' style="width:96px;"/>
											</a>
											<a href="javascript:loadRandomCode();">重新加载</a>
			                        	</i>
			                        	</td>
			                        </tr>
			                        <tr height="22" valign="middle">
			                        	<td width="83" >&nbsp;</td>
			                            <td width="15">&nbsp;</td>
			                            <td width="247" colspan="2" id="verificationCodeTip" style="color:#fc0000; font-size:12px;display:none;">请填写验证码！</td>
			                        </tr>
			                        <tr height="65" valign="bottom">
			                        	<td width="83">&nbsp;</td>
			                            <td width="15">&nbsp;</td>
			                            <td width="247" colspan="2"><input type="submit" onclick="doMobileLogin()" class="input12" value="医生登录" /> <a href="javascript:;" style=" float:left; margin-left:57px; color:#818181; line-height:35px;">忘记密码？</a></td>
			                        </tr>
			                    </table>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div class="h_40">&nbsp;</div>
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/menuBottom.htm" %>
	    <%@ include file="/views/front/common/bottom.htm" %>
	</div>
		
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/base/login/doctorLogin.js" title="v"></script>
</body>
</html>