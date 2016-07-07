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
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a></div>
	        <%@ include file="/views/front/common/user/userLeft.htm" %>
	        <div class="w_824">
				<div class="c_824" style="background-color: #FFFFFF;padding: 0 0 0 20px;">
		        	<div class="t_851_4">我的病历</div>
					<div class="t_851_3">
		            	<table cellpadding="0" cellspacing="0" border="0">
		                	<tr height="44" valign="middle">
		                        <td width="75">医生姓名：</td>
		                        <td width="199"><input type="text" class="text_input2" id="name" /></td>
		                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
		                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="resetQuery()" style="margin-left: 10px;"/></td>
		                    </tr>
		                </table>
		            </div>
					<div>
						<table cellpadding="0" cellspacing="0" border="0" width="849">
							<tbody id="userDiagnoseList"></tbody>
							<tr height="53" align="center" style="color:#666666;display:none" id="noData">
								<td>暂无数据</td>
							</tr>
		                </table>
	                </div>
					<div class="fy_new">
						<div id="page" class="inline-center"></div>
					</div>
				</div>
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
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/userDiagnoseList.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">诊所</td>
            <td width="70">科室</td>
            <td width="130">医生</td>
            <td width="155">用户姓名</td>
            <td width="155">用户性别</td>
            <td width="155">用户年龄</td>
            <td width="155">就诊日期</td>
			<td width="230">操作</td>
        </tr>
		{{#rows}}
			<tr height="53" align="center" style="color:#666666">
		    	<td width="95">{{hospitalName}}</td>
		        <td width="70">{{departmentName}}</td>
		        <td width="130">{{doctorName}}</td>
		        <td width="155">{{patientName}}</td>
		        <td width="155">{{patientSex}}</td>
		        <td width="155">{{patientAge}}</td>
		        <td width="155">{{&diagnoseDate}}</td>
				<td width="230">
					<!--<button class="btn btn-danger" onclick="lh.confirm({content: '是否确定删除该病历？', clickYes:deleteDiagnose, clickYesParam:{{id}}});">删除</button>-->
					<button class="btn btn-success" onclick="readDiagnose({{id}});">详情</button>
				</td>
			</tr>
		{{/rows}}
	</script>
</body>
</html>

