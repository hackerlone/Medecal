<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_back_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/back/back.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/css/back/baseupload.css" title="v"/>
</head>
<body>
    <!-- 查询条件  开始 -->
	<table>
		<tbody>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>相册类型：</span><input id="sc_albumType" class="easyui-combobox width120" /></td>
				<td class="td_pad"><span>相册名称：</span><input id="sc_albumName" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>创建时间-从：</span><input id="sc_startTimeFrom" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><span>至：</span><input id="sc_startTimeTo" data-options="editable:false" class="easyui-datebox width120" /></td>
				<td class="td_pad"><button id="search" onclick="doSearch()" class="button button-primary button-rounded button-small">查 询</button></td>
			</tr>
			<tr class="tr_ht" align="right">
				<td class="td_pad"><span>相册编号：</span><input id="sc_albumSerial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>用户编号：</span><input id="sc_userSerial" class="easyui-textbox width120" /></td>
				<td class="td_pad"><span>商品编号：</span><input id="sc_goodsSn" class="easyui-textbox width120" /></td>
				<td class="td_pad"></td>
				<td class="td_pad"><button id="clearsSearch" onclick="clearSearch()" class="button button-primary button-rounded button-small">重 置</button></td>
			</tr>
		</tbody>
	</table>
	<!-- 查询条件 结束 -->
	
	<div class="clear-both height10"></div>
	<div class="fl_opt_div">
		<button id="batchDelete" onclick="batchDelete()"  class="button button-primary button-rounded button-small" >批量删除</button>
		<button id="addAlbum" onclick="addAlbum()" class="button button-primary button-rounded button-small">添加相册</button>
		<button id="userInfoLink" onclick="jumpToUserInfo()" class="button button-royal button-rounded button-small">用户信息</button>
		<button id="pictureLink" onclick="jumpToPicture()" class="button button-royal button-rounded button-small">图片</button>
		
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
    
     <div id="albumWindiv" style="display:none;">
	     <div id='albumWin' class="easyui-window" title="相册" data-options="modal:true,closed:true,maximizable:false,collapsible:false,minimizable:false">
	         <form id="albumForm"><br/>
	       		 <table id="albumTable" class="padL5">
					<tbody>
						<tr id="album_tr" class="tr_ht" align="right">
						<td class="td_pad"><span>相册类型：</span><input id="f_albumType" class="easyui-combobox width140" data-options="required:true"/></td>
						<td class="td_pad"><span>相册名称：</span><input id="f_albumName" class="easyui-textbox width140" data-options="required:true"/></td>
						<td colspan="2" class="td_pad"><span class="colorGray">相册编号：</span><input id="f_albumSerial" class="easyui-textbox width140" data-options="readonly:true"/></td>
						</tr>
						<tr id="user_tr" class="tr_ht" align="right">
							<td class="td_pad"><span>用户编号：</span><input id="f_userSerial" class="easyui-textbox width140"/></td>
							<td id="user_search_td" align="left" class="td_pad">
								<input type="button" onclick="searchUserBySerial(null,'#f_userSerial');" class="button button-rounded button-small" value="查询"/>
								<input id="f_userId" type="hidden"/>
							</td>
							<td class="td_pad"><span class="colorGray">所属用户：</span><input id="f_username" data-options="readonly:true" class="easyui-textbox width140"/></td>
						</tr>
					</tbody>
				 </table>
			 </form>  
			 <div class="inline-center mgV40">
			     <button id="albumSave" onclick="submitAlbum()"  class="button button-primary button-rounded button-small" >保存</button>
			     <button id="albumBack" onclick="closeAlbumWin()"  class="button button-primary button-rounded button-small" >返回</button>
			 </div>
	     </div>
    </div>
    
<%@ include file="/views/common/common_back_js.htm"%>
<script type="text/javascript" src="/js/back/base/picture/album.js" title="v"></script>
</body>
</html>