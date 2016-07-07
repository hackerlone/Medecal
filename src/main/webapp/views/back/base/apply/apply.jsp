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
				<td class="td_pad"><span>申请内容：</span><input id="message" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>回复内容：</span><input id="reply" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>申请人编号：</span><input id="applySerial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>申请日期：</span><input id="applyDate" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><span>状态：</span><input id="mainStatus" class="easyui-combobox width120" /></td>
				<td class="td_pad"><button id="searchLoanBusiness" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>申请用户：</span><input id="applyName" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>申请人真实姓名：</span><input id="realName" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>申请机构编号：</span><input id="instSerial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>类型：</span><input id="sc_type" class="easyui-combobox width120" /></td>
				<td class="td_pad"></td>
				<td class="td_pad"><button id="clearsSearchLoanBusiness" onclick="clearSearch();return false;" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<div class="clear-both height10"></div>
	<!-- 查询条件 结束 -->
	<div class="fl_opt_div">
		<button id="batchDelete" onclick="batchDelete();return false;"  class="button button-primary button-rounded button-small" >批量删除</button>
		<!-- <button id="addApply" onclick="addApply();return false;" class="button button-primary button-rounded button-small">添加</button> -->
		<button id="batchRecover"  onclick="batchRecover();return false;" class="hide button button-primary button-rounded button-small">批量恢复</button>
		<button id="batchThoroughDelete" onclick="batchThoroughDelete();return false;" class="hide button button-primary button-rounded button-small">批量彻底删除</button>
		<button id="userInfoLink" onclick="jumpToUserInfo();return false;" class="button button-royal button-rounded button-small">用户信息</button>
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
    <div id="applyDetailWindiv" style="display:none;">
	     <div id='applyDetailWin' class="easyui-window" title="申请详细信息" style="width: 700px;height:460px" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
	         <form id="applyDetailForm"><br/>
	       		 <table id="applyDetailTable" class="padL5">
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
							<td class="td_pad"><span class="colorGray">申请日期：</span><input id="f_applyDate" data-options="readonly:true" class="easyui-datetimebox width140"/></td>
							<td class="td_pad"><span class="colorGray">申请类型：</span><input id="f_applyType" data-options="readonly:true" class="easyui-combobox width140"/></td>
							<td class="td_pad hide" id="applyInstType"><span class="colorGray">申请机构：</span><input id="f_instType" data-options="readonly:true" class="easyui-combobox width140"/></td>
						</tr>
						<tr class="tr_ht" id="file">
						</tr>
						<tr class="tr_ht" >
							<td class="td_pad" colspan="3"><span class="colorGray">申请内容:</span>
								<input id="f_message" data-options="readonly:true,multiline:true,height:60,width:600" class="easyui-textbox "/>
							</td>
						</tr>
						<tr class="tr_ht">
							<td class="td_pad" colspan="3"><span class="colorGray">回复内容:</span>
								<input id="f_reply" data-options="readonly:true,required:true,multiline:true,height:60,width:600,prompt:'请填写不同意理由'"  class="easyui-textbox "/>
							</td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <div class="inline-center mgV40">
			     <button id="applyDetailSave" onclick="submitApplyDetail();return false;"  class="button button-primary button-rounded button-small" >保存</button>
			     <button id="applyDetailAgree" onclick="applyDetailAgree();return false;"  class="button button-primary button-rounded button-small" >同意</button>
			     <button id="applyDetailDisAgree" onclick="applyDetailDisAgree();return false;"  class="button button-primary button-rounded button-small" >不同意</button>
			     <button id="applyDetailBack" onclick="closeApplyDetailWin();return false;"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
    </div>
    
    <div id="agreeDetailWindiv" style="display:none;">
	     <div id='agreeDetailWin' class="easyui-window" title="添加信息" style="width: 700px;height:435px" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
	         <form id="agreeDetailForm"><br/>
	       		 <table id="agreeDetailTable" class="padL5">
					<tbody>
						<tr class="tr_ht" align="right">
							<td class="td_pad">
								<span class="colorGray">用户编号：</span>
								<input id="serial" data-options="readonly:true" class="easyui-textbox width140"/>
								<input id="userId" type="hidden"/>
								<input id="shopId" type="hidden"/>
							</td>
							<td class="td_pad"><span class="colorGray">用户名：</span><input id="username" data-options="readonly:true" class="easyui-textbox width140"/></td>
							<td class="td_pad"><span class="colorGray">真实姓名：</span><input id="realname" data-options="readonly:true" class="easyui-textbox width140" /></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span class="colorGray">申请日期：</span><input id="agreeDate" data-options="readonly:true" class="easyui-datetimebox width140"/></td>
							<td class="td_pad"><span class="colorGray">申请类型：</span><input id="agreeType"  class="easyui-combobox width140"/></td>
							<td class="td_pad hide" id="agreeInstType"><span class="colorGray">申请机构：</span><input id="instType"  class="easyui-combobox width140"/></td>
						</tr>
						<tr class="tr_ht hide" id="forum">
							<td class="td_pad"><span class="colorGray">论坛名称：</span><input id="attr1" data-options="readonly:true" class="easyui-textbox width140"/></td>
							<td class="td_pad"><span class="colorGray">论坛类型：</span><input id="type" data-options="readonly:true" class="easyui-textbox width140"/></td>
						</tr>
						<tr class="tr_ht hide" align="right" id="auction">
							<td class="td_pad"><span class="colorGray">机构名称：</span><input id="attrInst" data-options="readonly:true" class="easyui-textbox width140"/></td>
							<td class="td_pad"><span class="colorGray">联系电话：</span><input id="attr2" data-options="readonly:true" class="easyui-textbox width140"/></td>
						</tr>
						<tr class="tr_ht hide" align="right" id="wholesale">
							<td class="td_pad"><span class="colorGray">机构名称：</span><input id="attrWholesale" data-options="readonly:true" class="easyui-textbox width140"/></td>
						</tr>
						<tr class="tr_ht" id="fileList">
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <div class="inline-center mgV40">
			     <button id="agreeDetailSave" onclick="submitAgreeDetail();return false;"  class="button button-primary button-rounded button-small" >确认</button>
			     <button id="agreeDetailBack" onclick="closeAgreeDetailWin();return false;"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
    </div>
    
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/apply/apply.js" title="v"></script>
</body>
</html>