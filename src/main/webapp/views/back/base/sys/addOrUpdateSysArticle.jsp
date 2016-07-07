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
    	<input type="hidden" id="articleId" value="${articleId}">
    	<table class="padL5">
    		<tr class="tr_ht" align="right">
    			<td class="td_pad"><span>新闻标题：</span><input role="textbox" id="f_title" class="domain-input easyui-textbox width140" data-options="required:true"/></td>
    			<td class="td_pad"><span>类型：</span><input role="combobox" id="f_typeId" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
    			<td class="td_pad"><span>状态：</span><input role="combobox" id="f_mainStatus" class="domain-input easyui-combobox width140" data-options="required:true"/></td>
    		</tr>
    	</table>
    </form>
    <div class="fl-margin-5-0-1-20">
	    <script id="editor" type="text/plain" style="width:1000px;height:450px;"></script>
	    <div class="inline-center fl mgV10">
	    		<%-- <c:if test="${operation == update}"> --%>
			     	<button id="mainObjSave" onclick="saveMainObj()" class="button button-primary button-rounded button-small mgV10">保存</button>
		 		<%-- </c:if> --%>
		     <button id="mainObjBack" onclick="lh.jumpToUrl('/back/article')" class="button button-primary button-rounded button-small mgV10">返回</button>
		 </div>
	 </div>
    <%@ include file="/views/common/common_js.htm"%>
    <%@ include file="/views/common/common_ueditor_js.htm"%>
	<%@ include file="/views/common/common_back_js.htm"%>
	<%@ include file="/views/common/common_upload_js.htm"%>
	<script type="text/javascript">
		ARTICLE_JSON = '${articleJson}';
  	</script>
	<script type="text/javascript" src="/js/back/base/article/addOrUpdateArticle.js" title="v"></script>
</body>
</html>