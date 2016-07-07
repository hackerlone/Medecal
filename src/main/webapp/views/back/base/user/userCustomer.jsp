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
				<td class="td_pad"><span>用户：</span><input id="user" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>客户：</span><input id="customer" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>客户：</span><input id="mainStatus" class="easyui-combobox width120" /></td>
				<td class="td_pad"><button id="searchLoanBusiness" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
				<td class="td_pad"><button id="clearsSearchLoanBusiness" onclick="clearSearch();return false;" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<div class="clear-both height10"></div>
	<!-- 查询条件 结束 -->
	<div class="fl_opt_div">
		<button id="batchDelete" onclick="batchDelete();return false;"  class="button button-primary button-rounded button-small" >批量删除</button>
		<button id="addUserCustomer" onclick="addUserCustomer(null,'add');return false;" class="button button-primary button-rounded button-small">添加</button>
		<button id="batchRecover"  onclick="batchRecover();return false;" class="hide button button-primary button-rounded button-small">批量恢复</button>
		<button id="batchThoroughDelete" onclick="batchThoroughDelete();return false;" class="hide button button-primary button-rounded button-small">批量彻底删除</button>
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
    <div id="userCustomerDetailWindiv" style="display:none;">
	     <div id='userCustomerDetailWin' class="easyui-window" title="客户关系" style="width: 300px;height:330px" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
	         <form id="userCustomerDetailForm"><br/>
	       		 <table id="userCustomerDetailTable" class="padL5">
					<tbody>
						<tr class="tr_ht">
							<td>
								<input id="f_Id" type="hidden"/>
							</td>
						</tr>
						<tr class="tr_ht">
							<td class="td_pad"><span class="colorGray">用户：</span><input id="f_user"  class="easyui-combobox width140"/></td>
						</tr>
						<tr class="tr_ht">
							<td class="td_pad"><span class="colorGray">客户：</span><input id="f_customer"  class="easyui-combobox width140"/></td>
						</tr>
						<tr class="tr_ht">
							<td class="td_pad"><span class="colorGray">状态：</span><input id="f_mainStatus"  class="easyui-combobox width140"/></td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <div class="inline-center mgV40">
			     <button id="userCustomerDetailSave" onclick="submitUserCustomerDetail();return false;"  class="button button-primary button-rounded button-small" >保存</button>
			     <button id="userCustomerDetailBack" onclick="closeUserCustomerDetailWin();return false;"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
    </div>
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/user/userCustomer.js" title="v"></script>
</body>
</html>