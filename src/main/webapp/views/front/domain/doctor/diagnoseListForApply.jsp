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
		<%@ include file="/views/front/common/doctor/doctorTop.htm"%> <!-- 顶部菜单栏 -->
	    <div class="w_1000">
			<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        	<div class="c_851">
	            	<div class="t_851_2">申请查看病历</div>
	            		<div class="d_851_1">
			            	<table cellpadding="0" cellspacing="0" border="0" width="849">
			                	<tbody id="dataListContainer">
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
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	
	<input type="hidden" id="patientIdcardNum" value="${patientIdcardNum}">
	
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/diagnoseListForApply.js" title="v"></script>
	
	<script id="template" type="x-tmpl-mustache"><!-- -->
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="80">姓名</td>
            <td width="60">性别</td>
			<td width="80">所属诊所</td>
			<td width="80">所属医生</td>
            <td width="120">联系电话</td>
			<td width="155">身份证号</td>
            <td width="120">就诊时间</td>
			<td width="120">状态</td>
            <td width="15">&nbsp;</td>
            <td width="100">操作</td>
        </tr>
		{{#rows}}
		<tr height="58" align="center" style="color:#666666">
	    	<td width="80">{{patientName}}</td>
	        <td width="60">{{patientSex}}</td>
			<td width="80">{{hospitalName}}</td>
			<td width="80">{{doctorName}}</td>
	        <td width="120">{{patientPhone}}</td>
			<td width="155">{{patientIdcardNum}}</td>
	        <td width="120">{{createDate}}</td>
			<td width="120">{{statusName}}</td>
	        <td width="15">&nbsp;</td>
	        <td width="150">
				{{#isAgree}}
				<button type="button" onclick="readDiagnose({{id}});" class="btn btn-primary">查看</button>
				{{/isAgree}}
				{{^isAgree}}
				<button type="button" onclick="applyToReadDiagnose({{id}});" class="btn btn-info">申请授权</button>
				{{/isAgree}}
	        </td>

	    </tr>
		{{/rows}}		 		 
	</script>
</body>
</html>

