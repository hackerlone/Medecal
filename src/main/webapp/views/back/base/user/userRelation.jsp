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
				<td class="td_pad"><span>用户名：</span><input id="username" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>用户编号：</span><input id="userSerial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><button id="searchLoanBusiness" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>论坛类型：</span><input id="sc_type" class="easyui-combobox width120" /></td>
				<td class="td_pad"><button id="clearsSearchLoanBusiness" onclick="clearSearch();return false;" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<div class="clear-both height10"></div>
	<!-- 查询条件 结束 -->
	<div class="fl_opt_div">
		<button id="batchDelete" onclick="batchDelete();return false;"  class="button button-primary button-rounded button-small" >批量删除</button>
		<button id="addUserRelation" onclick="addUserRelation();return false;" class="button button-primary button-rounded button-small">添加</button>
		<button id="batchRecover"  onclick="batchRecover();return false;" class="hide button button-primary button-rounded button-small">批量恢复</button>
		<button id="batchThoroughDelete" onclick="batchThoroughDelete();return false;" class="hide button button-primary button-rounded button-small">批量彻底删除</button>
		<button id="antiqueCityLink" onclick="jumpToAntiqueCity();return false;" class="button button-royal button-rounded button-small">古玩城</button>
		<button id="userLink" onclick="jumpToUserInfo();return false;" class="button button-royal button-rounded button-small">用户信息</button>
	</div>
	<div class="fr_opt_div">
		<button id="showTrash" onclick="showTrash();return false;" class="button button-primary button-rounded button-small">回收站</button>
		<button id="returnBack" onclick="returnBack();return false;" class="hide button button-primary button-rounded button-small">返回</button>
	</div>
	<!-- 表格  开始 -->
	<div id='datagrid_div'>
		<table id="datagrid"></table>
	</div>
	<!-- 表格  结束 -->
    
    <div id="userRelationDetailWindiv" style="display:none;">
	     <div id='userRelationDetailWin' class="easyui-window" title="添加古玩城用户" style="width: 750px;height:350px" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
	         <div id="userRelationDetailTip" style="margin-left: 28px;"></div>
	         <form id="userRelationDetailForm"><br/>
	       		 <table id="userRelationDetailTable" class="padL5">
					<tbody>
						<tr id="winSearchTr" class="tr_ht" align="left">
							<td class="td_pad"><span>用户查询：</span><input id="f_serialSearch" class="easyui-textbox width140"/></td>
							<td class="td_pad" colspan="2">
								<input type="button" onclick="searchUser();" class="button button-primary button-rounded button-small" value="查询"/>
								<input type="button" onclick="jumpToUserInfo()" class="button button-royal button-rounded button-small" value="用户信息"/>
								<span class="colorGray"> 请输入用户编号（可从用户信息表格中复制）</span>
							</td>
						</tr>
						<tr id="winSearchDivisionTr" class="tr_ht" align="left">
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad">
								<span class="colorGray">用户编号：</span>
								<input id="f_serial" data-options="readonly:true" class="easyui-textbox width140"/>
								<input id="f_userId" type="hidden"/>
								<input id="f_shopId" type="hidden"/>
							</td>
							<td class="td_pad"><span class="colorGray">用户名：</span><input id="f_username" data-options="readonly:true" class="easyui-textbox width140"/></td>
							<td class="td_pad"><span class="colorGray">真实姓名：</span><input id="f_realName" data-options="readonly:true" class="easyui-textbox width140" /></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>古玩城名称：</span><input id="f_antiqueCity" class="easyui-combobox width140"/></td>
						</tr>
						<!-- <tr class="tr_ht" align="right">
							<td class="td_pad"><span>状态：</span><input id="f_mainStatus" class="easyui-combobox width140"/></td>
						</tr> -->
					</tbody>
				 </table>
			 </form>  
			 <div class="inline-center mgV40">
			     <button id="userRelationDetailSave" onclick="submitUserRelationDetail()"  class="button button-primary button-rounded button-small" >保存</button>
			     <button id="userRelationDetailBack" onclick="closeUserRelationDetailWin()"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
	</div>
    
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/user/userRelation.js" title="v"></script>
</body>
</html>