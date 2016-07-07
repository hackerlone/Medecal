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
   	<table id="mainQueryTable" style="min-width:800px;">
		<tbody>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>诊所名称：</span><input role="textbox" id="sc_wholeName" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>诊所地址：</span><input role="textbox" id="sc_address" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>联系电话：</span><input role="textbox" id="sc_phone" class="domain-input easyui-textbox width100" /></td>
				<!-- <td class="td_pad"><span>排序：</span><input role="combobox" id="sc_ascOrdesc" class="domain-input easyui-combobox width100" /></td> -->
				<td class="td_pad"><button id="searchYes" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>是否有效走访：</span><input role="combobox" id="sc_haveVisitValid" class="domain-input easyui-combobox width100"
				data-options="valueField:'id',textField:'name',editable:false,multiple:false,required:false,panelHeight:'auto',data:[{'id':1,'name':'否'},{'id':2,'name':'是'}]"/>
				</td>
				<td class="td_pad"><span>是否签意向书：</span><input role="combobox" id="sc_isAssignIntention" class="domain-input easyui-combobox width100" 
				data-options="valueField:'id',textField:'name',editable:false,multiple:false,required:false,panelHeight:'auto',data:[{'id':1,'name':'否'},{'id':2,'name':'是'}]"/>
				</td>
				<td class="td_pad"><span>是否签合同：</span><input role="combobox" id="sc_isAssignContract" class="domain-input easyui-combobox width100" 
				data-options="valueField:'id',textField:'name',editable:false,multiple:false,required:false,panelHeight:'auto',data:[{'id':1,'name':'否'},{'id':2,'name':'是'}]"/>
				</td>
				<!-- <td class="td_pad"><span>状态：</span><input role="combobox" id="sc_mainStatus" class="domain-input easyui-combobox width100" /></td> -->
				<td class="td_pad" colspan="1"><button id="searchClear" onclick="clearSearch();return false;" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<div class="clear-both height10"></div>
	<!-- 查询条件 结束 -->
	<div id="opt_outer_div">
		<div class="fl_opt_div">
			<button role="opt_1" id="btn_batchDelete" onclick="lh.commonBatchDelete()"  class="button button-primary button-rounded button-small">批量删除</button>
			<button role="opt_1" onclick="addMainObj()" class="button button-primary button-rounded button-small">添加</button>
			<!-- <button  onclick="exportDataHospital()" class="button button-primary button-rounded button-small">导出诊所数据</button> -->
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
	     <div id='mainObjWin' class="easyui-window" title="诊所信息" style="width: 800px;" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
			<div id="mainObjTip"></div>
	         <form id="mainObjForm"><br/>
	       		 <table id="mainObjTable" class="padL5">
					<tbody>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>名称：</span><input role="textbox" id="f_wholeName" class="domain-input easyui-textbox width200" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>类型：</span><input role="combobox" id="f_typeId" class="domain-input easyui-combobox width200" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>地址：</span><input role="textbox" id="f_address" class="domain-input easyui-textbox width200" data-options="required:true"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>电话：</span><input role="textbox" id="f_phone" class="domain-input easyui-textbox width200" data-options="required:true"/></td>
							<td class="td_pad"><span>微信：</span><input role="textbox" id="f_weixin" class="domain-input easyui-textbox width200"/></td>
							<!-- <td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>状态：</span><input role="combobox" id="f_mainStatus" class="domain-input easyui-combobox width200"/></td> -->
							<!-- <td class="td_pad"><span><span style="color:red">*</span>省(市)：</span><input role="combobox" id="f_province" class="domain-input easyui-combobox width130" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red">*</span>市(县)：</span><input role="combobox" id="f_city" class="domain-input easyui-combobox width130" data-options="required:true"/></td> -->
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
	<%@ include file="/views/common/common_upload_js.htm"%>
	<script type="text/javascript">
  		lh.paramJsonStr = '${paramJson}';
  	</script>
  	
  	<script type="text/javascript" src="/js/common/back_template.js" title="v"></script>
	<script type="text/javascript" src="/js/back/domain/data/dataHospital.js" title="v"></script>
</body>
</html>