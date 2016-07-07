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
	    	<div class="c_1100_10">当前位置：<a href="javascript:;">首页</a> - <a href="javascript:;">注册</a></div>
	        <div class="c_1100_11">
	        	<div class="l_733">
	            	<p style=" text-align:center; color:#659946; font-size:14px;"><strong>沃诊无忧服务使用协议</strong></p><br />
	                本服务条款是沃诊无忧网站以下单独或并成为"本网站"）的经营者是沃诊无忧有限公司，与用户（下称为"您"），共同缔结的对双方具有约束力的有效契约。 阿里云向用户提供本网站上所展示的产品与服务，并将不断更新服务内容，最新的服务以本网站上的相关产品及服务介绍的页面展示以及向用户实际提供的为准。
	<br />
	<br />　　1、总则
	<br />
	<br />　　1.1 您确认：您在使用本服务之前，已经充分阅读、理解并接受本服务条款的全部内容（特别是以加粗及/或下划线标注的内容），一旦您选择"同意"并完成注册流程或使用本服务，即表示您同意遵循本服务条款之所有约定。
	<br />
	<br />　　1.2 您同意：阿里云有权随时对本服务条款及相应的服务规则内容进行单方面的变更，并有权以消息推送、网页公告等方式予以公布，而无需另行单独通知您；若您在本服务条款内容公告变更后继续使用本服务的，表示您已充分阅读、理解并接受修改后的协议内容，也将遵循修改后的条款内容使用本服务；若您不同意修改后的服务条款，您应立即停止使用本服务。
	<br />　　2、账户
	<br />　　2.1 注册
	<br />
	<br />　　2.1.1. 注册者资格
	<br />
	<br />　　您确认，在您完成注册程序或以其他阿里云允许的方式实际使用本服务时，您应当是具备完全民事权利能力和完全民事行为能力的自然人、法人或其他组织。若您不具备前述主体资格，则您及您的监护人应承担因此而导致的一切后果，且阿里云有权注销（永久冻结）您的账户，并向您及您的监护人索偿。
	<br />　　2、账户
	<br />　　2.1 注册
	<br />
	<br />　　2.1.1. 注册者资格
	<br />
	<br />　　您确认，在您完成注册程序或以其他阿里云允许的方式实际使用本服务时，您应当是具备完全民事权利能力和完全民事行为能力的自然人、法人或其他组织。若您不具备前述主体资格，则您及您的监护人应承担因此而导致的一切后果，且阿里云有权注销（永久冻结）您的账户，并向您及您的监护人索偿。
	            </div>
	            <div class="r_366">
	            	<div class="t_345">
	                	<span>邮箱注册</span>
	                </div>
	                <div class="d_345">
	                	<table cellpadding="0" cellspacing="0" border="0" width="345">
	                    	<tr height="42" valign="middle">
	                        	<td width="83" align="right">身份证号：</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247"><input type="text" id="idcardNum" class="input10" /></td>
	                        </tr>
	                       <!--  <tr height="22" valign="middle">
	                        	<td width="83" >&nbsp;</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247" style="color:#fc0000; font-size:12px;">请填写信息！</td>
	                        </tr> -->
	                        <tr height="53" valign="middle">
	                        	<td width="83" align="right">手机号：</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247"><input type="text" id="phone" class="input10" /></td>
	                        </tr>
	                        <!-- <tr height="35" valign="middle">
	                        	<td width="83">&nbsp;</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247"><input type="submit" onclick="loadRandomCode()" class="input11" value="获取免费短信验证码" /></td>
	                        </tr> -->
	                        <tr height="35" valign="middle">
	                        	<td width="83">&nbsp;</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247">
	                            <a href='javascript:loadRandomCode();'>
										<img src='/login/loadVerificationCode' id='imgcode' style="width:96px;"/>
									</a>
									<a href="javascript:loadRandomCode();">重新加载</a>
	                            </td>
	                        </tr>
	                        <tr height="65" valign="bottom">
	                        	<td width="83" align="right" style="line-height:42px;">验证码 :</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247"><input type="text" id="randomCode" class="input10" /></td>
	                        </tr>
	                        <tr height="70" valign="bottom">
	                        	<td width="83" align="right" style="line-height:42px;">密码：</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247"><input type="password" id="password" class="input10" onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value)></td>
	                        </tr>
	                        <tr height="60" valign="bottom">
	                        	<td width="83">&nbsp;</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247">
	                            <span id="pass_level_0">弱</span>
	                            <span id="pass_level_1">中</span>
	                            <span id="pass_level_2">强</span>
	                            </td>
	                        </tr>
	                        <tr height="70" valign="bottom">
	                        	<td width="83" align="right" style="line-height:42px;">确认密码：</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247"><input id="passwordSure" type="password" class="input10" /></td>
	                        </tr>
	                        <tr height="65" valign="bottom">
	                        	<td width="83">&nbsp;</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247"><input type="submit" onclick="register()" class="input12" value="立即注册" /></td>
	                        </tr>
	                        <tr height="38" valign="bottom">
	                        	<td width="83">&nbsp;</td>
	                            <td width="15">&nbsp;</td>
	                            <td width="247" style="font-size:12px;"><input type="checkbox" checked="checked" /> &nbsp;同意《服务使用协议》</td>
	                        </tr>
	                    </table>
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
	<script type="text/javascript" src="/js/common/common_password.js" title="v"></script>
	<script type="text/javascript" src="/js/front/base/login/register.js" title="v"></script>
</body>
</html>
