<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_back_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/back/back.css" title="v"/>
</head>
<body>
    <!-- 查询条件  开始 -->
    <table>
		<tbody>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>充值人编号：</span><input id="user" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>充值人姓名：</span><input id="userName" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>充值时间：</span><input id="createdAt" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><button id="searchLoanBusiness" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
				<td class="td_pad"><button id="clearsSearchLoanBusiness" onclick="clearSearch();return false;" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<div class="clear-both height10"></div>
	<!-- 查询条件 结束 -->
	<button id="userInfoLink" onclick="jumpToUserInfo()" class="button button-royal button-rounded button-small">用户信息</button>
	<!-- 表格  开始 -->
	<div id='datagrid_div'>
		<table id="datagrid"></table>
	</div>
	<!-- 表格  结束 -->
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/charge/charge.js" title="v"></script>
</body>
</html>