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
				<td class="td_pad"><span>用户名：</span><input id="sc_username" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>真实姓名：</span><input id="sc_realName" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>用户编号：</span><input id="sc_serial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><button id="clearsSearch" onclick="clearSearch()" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>控制类型：</span><input id="sc_controlType" data-options="editable:false" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>有效时间-从：</span><input id="sc_validDateFrom" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><span>至：</span><input id="sc_validDateTo" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><button id="search" onclick="doSearch()" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
		</tbody>
	</table>
	<!-- 查询条件 结束 -->
	
	<div class="clear-both height10"></div>
	<div class="fl_opt_div">
		<button id="batchDelete" onclick="batchDelete()"  class="button button-primary button-rounded button-small" >批量删除</button>
		<button id="addUser" onclick="addUser()" class="button button-primary button-rounded button-small">添加用户控制</button>
		<button id="userMoneyLink" onclick="jumpToUserMoney()" class="button button-royal button-rounded button-small">用户资金</button>
		<button id="userInfoLink" onclick="jumpToUserInfo()" class="button button-royal button-rounded button-small">用户信息</button>
		
		<button id="batchRecover" onclick="batchRecover()" class="hide button button-primary button-rounded button-small">批量恢复</button>
		<button id="batchThoroughDelete" onclick="batchThoroughDelete()" class="hide button button-primary button-rounded button-small">彻底删除</button>
		
	</div>
	<div class="fr_opt_div">
		<button id="showTrash" onclick="showTrach()" class="button button-primary button-rounded button-small">回收站</button>
		<button id="returnBack" onclick="returnBack()" class="hide button button-primary button-rounded button-small">返回</button>
	</div>
	<!-- 表格  开始 -->
	<div id='datagrid_div'>
		<table id="datagrid"></table>
	</div>
	<!-- 表格  结束 -->
    
     <div id="userControlWindiv" style="display:none;">
	     <div id='userControlWin' class="easyui-window" title="用户控制信息" style="width: 700px;height:320px" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
	         <form id="userControlForm"><br/>
	       		 <table id="userControlTable" class="padL5">
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
							<td class="td_pad"><span>控制类型：</span><input id="f_controlType" data-options="editable:false" class="easyui-combobox width140"/></td>
							<td class="td_pad"><span>有效时间-从：</span><input id="f_startDate" data-options="editable:false" class="easyui-datetimebox width140"/></td>
							<td class="td_pad"><span>至：</span><input id="f_endDate" data-options="editable:false" class="easyui-datetimebox width140" /></td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <div class="inline-center mgV40">
			     <button id="userControlSave" onclick="submitUserControl()"  class="button button-primary button-rounded button-small" >保存</button>
			     <button id="userControlBack" onclick="closeUserControlWin()"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
    </div>
    
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/user/userControl.js" title="v"></script>
</body>
</html>