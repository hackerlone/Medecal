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
				<td class="td_pad"><span>实名认证：</span><input id="sc_isRealAuth" data-options="editable:false" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>省（直辖市）：</span><input id="sc_province" data-options="editable:false" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>市（区）：</span><input id="sc_city" data-options="editable:false" class="easyui-combobox width120" /></td>
				<td class="td_pad"><button id="search" onclick="doSearch()" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>编号：</span><input id="sc_serial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>状态：</span><input id="sc_mainStatus" data-options="editable:false" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>电话：</span><input id="sc_phone" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>登陆时间-从：</span><input id="sc_lastLoginTimeFrom" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><span>至：</span><input id="sc_lastLoginTimeTo" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><button id="clearsSearch" onclick="clearSearch()" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<!-- 查询条件 结束 -->
	
	<div class="clear-both height10"></div>
	<div class="fl_opt_div">
		<button id="userInfoLink" onclick="jumpToUserInfo()" class="button button-royal button-rounded button-small">用户信息</button>
		<button id="userControlLink" onclick="jumpToUserControl()" class="button button-royal button-rounded button-small">用户控制</button>
	</div>
	<!-- 表格  开始 -->
	<div id='datagrid_div'>
		<table id="datagrid"></table>
	</div>
	<!-- 表格  结束 -->
    
     <div id="userFundWindiv" style="display:none;">
	     <div id='userFundWin' class="easyui-window" title="用户详细信息" style="width: 300px;height:300px" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
	         <form id="userFundForm"><br/>
	       		 <table id="userFundTable" class="padL5">
					<tbody>
						<tr class="tr_ht readOnlyTr" align="right">
							<td class="td_pad"><span class="colorGray">可用金额：</span><input id="f_avaliableMoney" data-options="readonly:true" class="easyui-textbox width140"/></td>
						</tr>
						<tr class="tr_ht readOnlyTr" align="right">
							<td class="td_pad"><span class="colorGray">冻结金额：</span><input id="f_frozenMoney" data-options="readonly:true" class="easyui-textbox width140"/></td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <div class="inline-center mgV40">
			     <button id="userFundSave" onclick="submitUserFund()"  class="button button-primary button-rounded button-small" >保存</button>
			     <button id="userFundBack" onclick="closeUserFundWin()"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
    </div>
    
  	<div id="paramMapJson" style="display:none;" >${user_search_condition}</div> 
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/user/userFund.js" title="v"></script>
</body>
</html>