<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/third-party/pagination/paging.css"/>
</head>
<body>
	<%@ include file="/views/front/common/doctor/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm" %>
	    <div class="w_1000">
	    	<input type="hidden" id="doctorId" value="${doctorId}"/>
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_824">
				<div class="c_824" style="background-color: #FFFFFF;">
					<ul id="chatListUl">
						<li class="gray" id="noData" style="display:none;">
							<a href="javascript:;">
								<div class="l_new">
									<nobr>暂无数据</nobr>
								</div>
							</a>
						</li>
					</ul>
					<div class="fy_new">
						<div id="page" class="inline-center"></div>
					</div>
				</div>
			</div>
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/chat.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		{{#rows}}
			<li class="gray">
				<a href="javascript:;">
					<div class="l_new" onclick="lh.alert('{{content}}')">
						<nobr>{{title}}</nobr>
					</div>
					<span>{{&createDate}}</span>
				</a>
			</li>
		{{/rows}}
	</script>
</body>
</html>

