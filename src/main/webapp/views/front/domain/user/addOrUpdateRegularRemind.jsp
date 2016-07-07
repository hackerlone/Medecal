<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link type="text/css" href="/third-party/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" >
</head>
<body>
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a></div>
	       <%@ include file="/views/front/common/user/userLeft.htm" %>
	       <div class="w_851" style="padding-top:0;">
	        	<div class="t_851_4">定期提醒</div>
	        		<input type="hidden" id="regularRemindId" value="${regularRemind.id}">
	               	<table cellpadding="0" cellspacing="0" border="0" width="773">
	               		<tr height="40" valign="bottom">
                       </tr>
	               		<tr height="40" valign="bottom">
                           <td width="67" style="line-height:30px;">提醒标题</td>
                           <td width="329"><input type="text" class="input14" id="title"  value="${regularRemind.title}"/></td>
                           <td width="67" style="line-height:30px;">提醒时间</td>
                           <td width="329"><input type="text" class="input14" id="remindTime"  value='<fmt:formatDate value="${regularRemind.remindTime}" pattern="YYYY-MM-dd hh:mm:ss"/>'/></td>
                       </tr>
                       <tr height="40" valign="bottom">
                       </tr>
                       <tr height="40" valign="bottom">
                       		<td width="67" style="line-height:50px;" valign="top">提醒内容</td>
	                        <td colspan="3" width="706"><textarea class="text_input" id="content">${regularRemind.content}</textarea></td>
                       </tr>
                       <tr height="92" valign="bottom">
                       	<td width="67">&nbsp;</td>
                           <td colspan="3" width="706" style="text-align:center;">
                           	<button style="margin-right:80px;" type="button" onclick="addOrUpdateRegularRemind();" class="btn btn-success">保存</button>
                           	<button style="margin-right:80px;" type="button" onclick="lh.back();" class="btn btn-success">返回</button>
                           </td>
                       </tr>
                   </table>
	        </div> 
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/menuBottom.htm" %>
	    <%@ include file="/views/front/common/bottom.htm" %>
	</div>
	<%@ include file="/views/front/common/nav.htm" %>
		
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/addOrUpdateRegularRemind.js" title="v"></script>
</body>
</html>

