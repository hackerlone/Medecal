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
   	<table id="mainQueryTable">
		<tbody>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>药物名称：</span><input role="textbox" id="sc_name" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>英语名称：</span><input role="textbox" id="sc_englishName" class="domain-input easyui-textbox width100" /></td>
				<!-- <td class="td_pad"><span>库存数量：</span><input role="textbox" id="sc_remainNum" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>告警数量：</span><input role="textbox" id="sc_warningNum" class="domain-input easyui-textbox width100" /></td> -->
				<td class="td_pad"><span>生厂商：</span><input role="textbox" id="sc_producer" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>生产地址：</span><input role="textbox" id="sc_produceAddress" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>药物类型：</span><input role="combobox" id="sc_typeId" class="domain-input easyui-combobox width100" /></td>
				<td class="td_pad"><span>排序：</span><input role="combobox" id="sc_ascOrdesc" class="domain-input easyui-combobox width100" /></td>
				<td class="td_pad"><button id="searchYes" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>产品批号：</span><input role="textbox" id="sc_lotNumber" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>生产日期：</span><input role="datebox" id="sc_produceDate" class="domain-input easyui-datebox width100" /></td>
				<td class="td_pad"><span>有效期至：</span><input role="datebox" id="sc_validTill" class="domain-input easyui-datebox width100" /></td>
				<td class="td_pad"><span>生产商电话：</span><input role="textbox" id="sc_producerTel" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>生产商邮编：</span><input role="textbox" id="sc_producerCode" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>状态：</span><input role="combobox" id="sc_mainStatus" class="domain-input easyui-combobox width100" /></td>
				<td class="td_pad"><button id="searchClear" onclick="clearSearch();return false;" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<div class="clear-both height10"></div>
	<!-- 查询条件 结束 -->
	<div id="opt_outer_div">
		<div class="fl_opt_div">
			<button role="opt_1" id="btn_batchDelete" onclick="lh.commonBatchDelete()"  class="button button-primary button-rounded button-small">批量删除</button>
			<button role="opt_1" onclick="addMainObj()" class="button button-primary button-rounded button-small">添加</button>
			<button  onclick="exportMedication()" class="button button-primary button-rounded button-small">导出药品信息</button>
			<button role="opt_2" id="btn_batchRecover" onclick="lh.commonBatchRecover()" class="hide button button-primary button-rounded button-small">批量恢复</button>
			<button role="opt_2" id="btn_throughDelete" onclick="lh.commonBatchThoroughDelete()" class="hide button button-primary button-rounded button-small">彻底删除</button>
			
		</div>
		<div class="fr_opt_div">
			<button role="opt_1" id="btn_trash" onclick="lh.commonShowTrash()" class="button button-primary button-rounded button-small">回收站</button>
			<button role="opt_2" id="btn_trashBack" onclick="lh.commonReturnBack()" class="hide button button-primary button-rounded button-small">返回</button>
		</div>
	</div>
	<!-- 表格  开始 -->
	<div id='datagrid_div'>
		<table id="datagrid"></table>
	</div>
	<!-- 表格  结束 -->
    
    <div id="mainObjWindiv" style="display:none;">
	     <div id='mainObjWin' class="easyui-window" title="添加药品" style="width: 720px;" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
			<div id="mainObjTip"></div>
	         <form id="mainObjForm"><br/>
	       		 <table id="mainObjTable" class="padL5">
					<tbody>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>药物类型：</span><input role="combobox" id="f_typeId" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>药物名称：</span><input role="textbox" id="f_name" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span>英语名称：</span><input role="textbox" id="f_englishName" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<!-- <td class="td_pad"><span>库存数量：</span><input role="textbox" id="f_remainNum" class="domain-input easyui-textbox width140" data-options="required:true"/></td> -->
						</tr>
						<tr class="tr_ht" align="right">
							<!-- <td class="td_pad"><span>告警数量：</span><input role="textbox" id="f_warningNum" class="domain-input easyui-textbox width140" data-options="required:false"/></td> -->
							<td class="td_pad"><span>单价：</span><input role="numberbox" id="f_producer" class="domain-input easyui-numberbox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>生厂商：</span><input role="textbox" id="f_producer" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>生产地址：</span><input role="textbox" id="f_produceAddress" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>产品批号：</span><input role="textbox" id="f_lotNumber" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>生产日期：</span><input role="datetimebox" id="f_produceDate" class="domain-input easyui-datetimebox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>单位：</span><input role="textbox" id="f_unit" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<!-- <td class="td_pad"><span>有效期至：</span><input role="datetimebox" id="f_validTill" class="domain-input easyui-datetimebox width140" data-options="required:false"/></td> -->
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>生产商电话：</span><input role="textbox" id="f_producerTel" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>生产商邮编：</span><input role="textbox" id="f_producerCode" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>状态：</span><input role="combobox" id="f_mainStatus" class="domain-input easyui-combobox width140" data-options="required:false"/></td>
						</tr>
						<tr>
							<td class="td_pad" colspan="3">
								<span>用法：</span>
								<input role="textbox" id="f_usageAndDosage" class="domain-input easyui-textbox"  data-options="required:false,multiline:true,height:80,prompt:'请输入内容',width:620"/>
							</td>
						</tr>
						<tr>
							<td class="td_pad" colspan="3">
								<span>禁忌：</span>
								<input role="textbox" id="f_attention" class="domain-input easyui-textbox"  data-options="required:false,multiline:true,height:80,prompt:'请输入内容',width:620"/>
							</td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			<div class="inline-center mgV40">
			     <button id="mainObjSave" onclick="saveMainObj()" class="button button-primary button-rounded button-small">保存</button>
			     <button id="mainObjBack" onclick="closeMainObjWin()" class="button button-primary button-rounded button-small">返回</button>
			 </div>
	     </div>
    </div>
    <%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_back_js.htm"%>
	<script type="text/javascript">
  		lh.paramJsonStr = '${paramJson}';
  	</script>
  	<script type="text/javascript" src="/js/common/back_template.js" title="v"></script>
	<script type="text/javascript" src="/js/back/domain/medication/medication.js" title="v"></script>
</body>
</html>