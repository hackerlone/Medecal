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
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_824">
				<div class="c_824" style="background-color: #FFFFFF;">
            		<div class="d_851_1">
		            	<table cellpadding="0" cellspacing="0" border="0" width="849">
		                	<tbody id="doctorList">
		                	</tbody>
		                </table>
		                <div class="fy_new">
							<div id="page" class="inline-center"></div>
						</div>
		            </div>
				</div>
			</div>
	    </div>
	</div>
<!-- 	<a href="/article/{{typeId}}/{{id}}"> -->
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorList.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
			<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">头像</td>
        	<td width="95">姓名</td>
            <td width="70">性别</td>
            <td width="130">地区</td>
            <td width="155">联系电话</td>
            <td width="15">&nbsp;</td>
            <td width="184">操作</td>
        </tr>
		{{#rows}}
			<tr height="58" align="center" style="color:#666666">
		    	<td width="95"><img src="{{avatar}}"width="98" height="98" onerror="this.src='/images/front/main13.jpg'"/></td>
		    	<td width="95">{{username}}</td>
		        <td width="70">{{sexName}}</td>
		        <td width="130">{{provinceName}}&nbsp;{{cityName}}</td>
		        <td width="155">{{phone}}</td>
		        <td width="15">&nbsp;</td>
		        <td width="184">
					{{^fansId}}
						<button type="button" onclick="joinMyFans({{id}});" class="btn btn-success">加入我的圈子</button>
					{{/fansId}}
					{{#fansId}}
						<button type="button" onclick="lh.confirm({content: '是否确定删除？', clickYes:cancelJoinMyFans, clickYesParam:{{id}}});" class="btn btn-danger">删除我的圈子</button>
		        	{{/fansId}}
				</td>
		    </tr>
		{{/rows}}
	</script>
</body>
</html>

