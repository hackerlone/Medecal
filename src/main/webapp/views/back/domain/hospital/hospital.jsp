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
   	<table id="mainQueryTable" style="min-width:1000px;">
		<tbody>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>名称：</span><input role="textbox" id="sc_briefName" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>地址：</span><input role="textbox" id="sc_address" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>手机号码：</span><input role="textbox" id="sc_phone" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>邮箱：</span><input role="textbox" id="sc_email" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>排序：</span><input role="combobox" id="sc_ascOrdesc" class="domain-input easyui-combobox width100" /></td>
				<td class="td_pad"><button id="searchYes" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>qq：</span><input role="textbox" id="sc_qq" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>微信：</span><input role="textbox" id="sc_weixin" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>微博：</span><input role="textbox" id="sc_weibo" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>状态：</span><input role="combobox" id="sc_mainStatus" class="domain-input easyui-combobox width100" /></td>
				<td class="td_pad"></td>
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
			<button  onclick="resetPassword()" class="button button-primary button-rounded button-small">重置密码</button>
			<button  onclick="exportHospital()" class="button button-primary button-rounded button-small">导出诊所信息</button>
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
	     <div id='mainObjWin' class="easyui-window" title="诊所信息" style="width: 645px;" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
			<div id="mainObjTip"></div>
	         <form id="mainObjForm"><br/>
	       		 <table id="mainObjTable" class="padL5">
					<tbody>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>简称：</span><input role="textbox" id="f_briefName" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>全称：</span><input role="textbox" id="f_wholeName" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>地址：</span><input role="textbox" id="f_address" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>手机：</span><input role="textbox" id="f_phone" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red">*</span>省(市)：</span><input role="combobox" id="f_province" class="domain-input easyui-combobox width130" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red">*</span>市(县)：</span><input role="combobox" id="f_city" class="domain-input easyui-combobox width130" data-options="required:true"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>血液检测：</span><input role="combobox" id="f_bloodTest" class="domain-input easyui-combobox width140"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>状态：</span><input role="combobox" id="f_mainStatus" class="domain-input easyui-combobox width140"/></td>
							<td class="td_pad"><span>座机：</span><input role="textbox" id="f_tel" class="domain-input easyui-textbox width140" /></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>网址：</span><input role="textbox" id="f_website" class="domain-input easyui-textbox width140"/></td>
							<td class="td_pad"><span>邮箱：</span><input role="textbox" id="f_email" class="domain-input easyui-textbox width140"/></td>
							<td class="td_pad"><span>QQ：</span><input role="textbox" id="f_qq" class="domain-input easyui-textbox width140" /></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>微博：</span><input role="textbox" id="f_weibo" class="domain-input easyui-textbox width140" /></td>
							<td class="td_pad"><span>微信：</span><input role="textbox" id="f_weixin" class="domain-input easyui-textbox width140"/></td>
						</tr>
						<tr>
							<td class="td_pad" colspan="3">
								<span>简介：</span>
								<input role="textbox" id="f_introduction" class="domain-input easyui-textbox"  data-options="required:true,multiline:true,height:100,prompt:'请输入内容',width:570"/>
							</td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <span>诊所LOGO：</span>
			 <button id="browse" type="button" class="button button-primary button-rounded button-small" >选择图片</button>
			 <span>(建议图片长宽均为120像素)</span>
			 <div style="display:inline-block;float:left;">
				 <img  id="pic" class="picurl"  src="${pic.picPath}" style="height:60px;max-width:100px;overflow:hidden;padding:5px;"/>
				 <input type="hidden" name="filePaths" id="filePaths" value="${ap.picPath}"/>
				 <input type="hidden" name="fileDBIds" id="fileDBIds"/>
			 </div>
			 <div id="upload_outer_div" style="margin-top:30px;"><!-- 上传文件进度展示 --></div>
			<div class="inline-center mgV40">
			     <button id="mainObjSave" onclick="saveMainObj()" class="button button-primary button-rounded button-small">保存</button>
			     <button id="mainObjBack" onclick="closeMainObjWin()" class="button button-primary button-rounded button-small">返回</button>
			 </div>
	     </div>
    </div>
    
    <div id="resetPasswordWindiv" style="display:none;">
	     <div id='resetPasswordWin' class="easyui-window" title="重置密码" style="width: 300px;" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
			<div id="resetPasswordTip"></div>
	         <form id="resetPasswordForm"><br/>
	       		 <table id="resetPasswordTable" class="padL5" style="width:280px;">
					<tbody>
						<tr class="tr_ht" align="center">
							<td class="td_pad"><span>密码：</span><input role="textbox" type="password" id="password" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			<div class="inline-center mgV40">
			     <button id="mainObjSave" onclick="saveResetPassword()" class="button button-primary button-rounded button-small">保存</button>
			     <button id="mainObjBack" onclick="closeResetPasswordWin()" class="button button-primary button-rounded button-small">返回</button>
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
  	<script type="text/javascript" src="/js/common/common_back_resetPassword.js" title="v"></script>
	<script type="text/javascript" src="/js/back/domain/hospital/hospital.js" title="v"></script>
</body>
</html>