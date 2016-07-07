<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
</head>
<!--[if lt IE 9]>
	<script>
		var e = ("abbr,article,aside,audio,canvas,datalist,details," + "figure,footer,header,hgroup,mark,menu,meter,nav,output, " + "progress,section,time,video").split(',');
		for (var i = 0; i < e.length; i++) {document.createElement(e[i]);}
	</script>
<![endif]-->
</head>
<body class="to3">
<div class="to3">
	<header class="page">
	      <h1 style="background:none;text-align: center;width: 980px;padding:0;margin:55px 0 0 0;letter-spacing:5px;">惟华光能</h1>
	</header>
	<div id="activitiesOrNews">
	</div>
</div>
<%@ include file="/views/common/common_js.htm"%>
<script type="text/javascript" src="/js/article/activitiesOrNews.js" title="v"></script>
</body>
<script type="text/javascript" src="/third-party/mustache/mustache.min.js"></script>
<script id="template" type="x-tmpl-mustache">
{{#rows}}
	标题:{{title}}
	描述:{{description}}
	内容:{{content}}
	<a href="/addComment?commentTypeId=1&objectId={{id}}">评论</a>
	<br/>
{{/rows}}		 		 
</script>
</html>
