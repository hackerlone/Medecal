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
 	<form class="margin-5-5-10-15">
    	<input type="hidden" id="advertisementId" value="${advertisementId}">
    	<table class="padL5">
    		<tr class="tr_ht" align="right">
    			<td class="td_pad"><span>广告标题：</span><input role="textbox" id="f_title" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
    			<td class="td_pad"><span>链接地址：</span><input role="textbox" id="f_linkUrl" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
    		</tr>
    		<tr class="tr_ht" align="right">
    			<td class="td_pad"><span>栏目位置：</span><input role="combobox" id="f_catId" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
    			<td class="td_pad"><span>状态：</span><input role="combobox" id="f_mainStatus" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
    		</tr>
    		<tr class="tr_ht" align="right">
                    <td class="td_pad"><span style="float:left;">广告图片:</span></td>
                    <td style="top:10px;"  valign="middle">
	                   	<div style="display:inline-block;float:left;">
						 	<img  id="advertisementPicPath" class="picurl"  src="${advertisement.picPath}" style="height:60px;max-width:100px;overflow:hidden;padding:5px;"/>
						 	<input type="hidden" name="filePaths" id="filePaths" value="${advertisement.picPath}"/>
						 	<input type="hidden" name="fileDBIds" id="fileDBIds"/>
					 	</div>
					 	<button style="float:left;" id="browse" type="button" class="button button-primary button-rounded button-small" >选择图片</button>
                 	</td>
            </tr>
            <tr>
            	<td colspan="3" >
            		 <div id="upload_outer_div" style="margin-top:30px;"><!-- 上传文件进度展示 --></div>
            	</td>
            </tr>
    	</table>
    </form>
    <div class="fl-margin-5-0-1-20">
	    <div class="inline-center fl mgV10">
		    <%-- <c:if test="${operation != read}"> --%>
			     <button id="mainObjSave" onclick="saveMainObj()" class="button button-primary button-rounded button-small mgV10">保存</button>
		    <%-- </c:if> --%>
		     <button id="mainObjBack" onclick="lh.jumpToUrl('/back/advertisement')" class="button button-primary button-rounded button-small mgV10">返回</button>
		 </div>
	 </div>
    <%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_back_js.htm"%>
	<%@ include file="/views/common/common_upload_js.htm"%>
	<script type="text/javascript">
		ARTICLE_JSON = '${advertisementJson}';
  	</script>
	<script type="text/javascript" src="/js/back/base/advertisement/addOrUpdateAdvertisement.js" title="v"></script>
</body>
</html>