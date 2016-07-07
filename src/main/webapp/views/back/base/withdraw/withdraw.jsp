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
				<td class="td_pad"><span>提现人姓名：</span><input id="userSerial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>提现时间：</span><input id="applyDate" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><span>处理时间：</span><input id="dealDate" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><span>状态：</span><input id="dealStatus" class="easyui-combobox width120" /></td>
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
    <div id="agreeDetailWindiv" style="display:none;">
	     <div id='agreeDetailWin' class="easyui-window" title="处理意见" style="width: 330px;height:235px" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
       		 <table id="agreeDetailTable" class="padL5">
				<tbody>
					<tr class="tr_ht">
						<td class="td_pad">
							<input id="Id" type="hidden"/>
						</td>
						<td class="td_pad"><span class="colorGray">处理意见：</span><input id="attr2" data-options="multiline:true,height:60,width:260" class="easyui-textbox width140"/></td>
					</tr>
				</tbody>
			 </table>
			 <div class="inline-center mgV40">
			     <button id="agreeDetailSave" onclick="submitAgreeDetail();return false;"  class="button button-primary button-rounded button-small" >确认</button>
			     <button id="agreeDetailBack" onclick="closeAgreeDetailWin();return false;"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
    </div>
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/withdraw/withdraw.js" title="v"></script>
</body>
</html>