<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/third-party/pagination/paging.css"/>
</head>
<body>
	<%@ include file="/views/front/common/doctor/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm" %>
	    <div class="w_1000">
	    	<input type="hidden" id="doctorId" value="${doctorId}"/>
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        <div class="t_851_2">留言</div>
	         <div class="t_851_3">
	            	<table cellpadding="0" cellspacing="0" border="0">
	                	<tr height="44" valign="middle">
	                        <td width="75">标题：</td>
	                        <td width="199"><input type="text" class="text_input2" id="name" /></td>
	                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
	                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="resetQuery()" style="margin-left: 10px;"/></td>
	                    </tr>
	                </table>
	            </div>
				<div class="c_824" style="background-color: #FFFFFF;">
					<table cellpadding="0" cellspacing="0" border="0" width="849">
		                	<tbody id="messageList">
				             </tbody>
		                </table>
					<div class="fy_new">
						<div id="page" class="inline-center"></div>
					</div>
				</div>
			</div>
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/message.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">标题</td>
            <td width="15">&nbsp;</td>
            <td width="50">状态</td>
            <td width="184">操作</td>
        </tr>
		{{#rows}}
			<tr height="53" align="center" style="font-size:14px; color:#63a13f">
            <td width="70">{{title}}</td>
			<td width="15">&nbsp;</td>
			<td width="50">
			{{#replyContent}}
				已回复
			{{/replyContent}}
			{{^replyContent}}
				未回复
			{{/replyContent}}
			</td>
		        <td width="184">
					{{^replyContent}}
						<button type="button" onclick="messageDetail({{id}});" class="btn btn-success">查看并回复</button>
					{{/replyContent}}
					{{#replyContent}}
						<!--<button type="button" class="btn btn-success">已回复</button>-->
						<button type="button" onclick="messageDetail({{id}});" class="btn btn-success">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
					{{/replyContent}}
		        </td>
        </tr>
		{{/rows}}
	</script>
</body>
</html>

