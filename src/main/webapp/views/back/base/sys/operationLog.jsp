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
<body onbeforeunload="javascript:console.log('a');">
    <!-- 查询条件  开始 -->
    <table>
		<tbody>
			<!-- <tr class="tr_ht" align="right">
				<td class="td_pad"><span>用户：</span><input id="user" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>数据字典类型：</span><input id="type" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>官方账号：</span><input id="sc_type" class="easyui-combobox width120" /></td>
				<td class="td_pad"><button id="searchLoanBusiness" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
				<td class="td_pad"><button id="clearsSearchLoanBusiness" onclick="clearSearch();return false;" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr> -->
			<tr class="tr_ht" align="right">
			<td class="td_pad"><span>排序：</span><input role="combobox" id="sc_ascOrdesc" class="domain-input easyui-combobox width140" /></td>
				<td class="td_pad"><button id="exportData" onclick="exportData();return false;" class="button button-primary button-rounded button-small">导出数据</button></td>
			</tr>
		</tbody>
	</table>
	<!-- 查询条件 结束 -->
	
	<!-- 表格  开始 -->
	<div id='datagrid_div'>
		<table id="datagrid"></table>
	</div>
	<!-- 表格  结束 -->
    
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/operationLog/operationLog.js" title="v"></script>
</body>
</html>