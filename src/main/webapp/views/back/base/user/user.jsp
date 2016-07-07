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
				<td class="td_pad"><span>用户名：</span><input role="textbox" id="sc_username" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>地址：</span><input role="textbox" id="sc_address" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>手机号码：</span><input role="textbox" id="sc_phone" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>排序：</span><input role="combobox" id="sc_ascOrdesc" class="domain-input easyui-combobox width100" /></td>
				<td class="td_pad"><button id="searchYes" onclick="doSearch();return false;" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<!-- <td class="td_pad"><span>所属诊所：</span><input role="textbox" id="sc_belongHospital" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>所属医生：</span><input role="textbox" id="sc_belongDoctor" class="domain-input easyui-textbox width100" /></td> -->
				<td class="td_pad"><span>性别：</span><input role="combobox" id="sc_sex" class="domain-input easyui-combobox width100" /></td>
				<td class="td_pad"><span>QQ：</span><input role="textbox" id="sc_qq" class="domain-input easyui-textbox width100" /></td>
				<td class="td_pad"><span>微信：</span><input role="textbox" id="sc_weixin" class="domain-input easyui-textbox width100" /></td>
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
			<button  onclick="exportUser()" class="button button-primary button-rounded button-small">导出用户信息</button>
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
	     <div id='mainObjWin' class="easyui-window" title="用户信息" style="width: 740px;" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
			<div id="mainObjTip"></div>
	         <form id="mainObjForm"><br/>
	       		 <table id="mainObjTable" class="padL5">
					<tbody>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>用户名：</span><input role="textbox" id="f_username" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>真实姓名：</span><input role="textbox" id="f_realName" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>性别：</span><input role="combobox" id="f_sex" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>手机：</span><input role="textbox" id="f_phone" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>省(直辖市)：</span><input role="combobox" id="f_province" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>市(县)：</span><input role="combobox" id="f_city" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>身份证号码：</span><input role="textbox" id="f_idcardNum" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>出生日期：</span><input role="datebox" id="f_birthday" class="domain-input easyui-datebox width140" data-options="required:true"/></td>
							<td class="td_pad"><span><span style="color:red;font-weight:bolder;">*</span>状态：</span><input role="combobox" id="f_mainStatus" class="domain-input easyui-combobox width140"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>职业：</span><input role="combobox" id="f_jobId" class="domain-input easyui-combobox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>上级用户：</span><input role="combobox" id="f_relationId" class="domain-input easyui-combobox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>用户关系：</span><input role="textbox" id="f_userRelation" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>邮箱：</span><input role="textbox" id="f_email" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>地址：</span><input role="textbox" id="f_address" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
							<td class="td_pad"><span>重置密码：</span><input role="textbox" id="f_passwordReset" class="domain-input easyui-textbox width140"/></td>
						</tr>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>艾迪康ID：</span><input role="textbox" id="f_thirdId" class="domain-input easyui-textbox width140" data-options="required:false"/></td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <span>头&nbsp;&nbsp;像：</span>
			 <button id="browse" type="button" class="button button-primary button-rounded button-small" >选择图片</button>
			 <span>(建议图片长宽均为120像素)</span>
			 <div style="display:inline-block;float:left;">
				 <img  id="pic" class="picurl"  src="${pic.picPath}" style="height:60px;max-width:100px;overflow:hidden;padding:5px;"/>
				 <input type="hidden" name="filePaths" id="filePaths" value="${pic.picPath}"/>
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
	     <div id='resetPasswordWin' class="easyui-window" title="重置密码" style="width: 635px;" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
			<div id="resetPasswordTip"></div>
	         <form id="resetPasswordForm"><br/>
	       		 <table id="resetPasswordTable" class="padL5">
					<tbody>
						<tr class="tr_ht" align="right">
							<td class="td_pad"><span>密码：</span><input role="textbox" type="password" id="password" class="domain-input easyui-textbox width100" data-options="required:true"/></td>
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
	<script type="text/javascript" src="/js/back/base/user/user.js" title="v"></script>
</body>
</html>