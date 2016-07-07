<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/css/front/lhfy.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/third-party/pagination/paging.css"/>
</head>
<body>
<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<input type="hidden" id="typeId" value="${typeId}"> 
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10" style="float: none;">当前位置：<a href="/">首页</a></div>
	        <div id="ss-ul" style="height: inherit;">
	            <ul class="ss-ul-top">
	                <li class="select" id="nameLi1"><a href="#" onclick="hospitalList()">诊所</a></li>
	                <div class="fenge"></div>
	                <li id="nameLi2"><a href="#" onclick="doctorList()">医生</a></li>
	            </ul>
	            <div style="clear:both;"></div>
	            <hr style="background-color:rgb(101,153,70);height:0.2em;margin:0 40px;"/>
	            <div  id="dataList">
	                
	            </div>
	        </div>
	        <div class="h_20">&nbsp;</div>
            <div class="fy_new" style="width:980px;margin: 0 auto;padding: 0;">
				<div id="page" class="inline-center"></div>
			</div>
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/menuBottom.htm" %>
	    <%@ include file="/views/front/common/bottom.htm" %>
	</div>
	<%@ include file="/views/front/common/nav.htm" %>
		
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/user/searchDoctorOrHospitalList.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		{{#rows}}
	            <div class="yy-int">
	            	<div class="yy-content">
	                	<img src="{{logo}}" class="yy-int-img pointer" onclick="javascript:window.open('/hospital/hospital/{{id}}')" style="width: 100px;" onerror="this.src='/images/front/er8.jpg'"/>
	                   		<div class="yy-p" style="overflow: hidden;text-overflow: ellipsis;padding: 10px;">
	                     	<h2><a href="/hospital/hospital/{{id}}" target="_blank">{{wholeName}}</a></h2>
	                      	<p>
	                         <span>地址：</span>{{address}}<br/>
	                          <span>联系方式：{{phone}},{{tel}}<br/>
	                          <span style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">医院介绍：{{introduction}}</span>
	                          <a href="/hospital/hospital/{{id}}" target="_blank">详细介绍</a>
	                       	</p>
	                </div>
	              </div>
	         </div>
		{{/rows}}
	</script>
	<script id="template1" type="x-tmpl-mustache">
		{{#rows}}
	            <div class="yy-int">
	            	<div class="yy-content">
	                	<img src="{{avatar}}" onclick="javascript:window.open('/doctor/{{id}}')" class="yy-int-img pointer" style="width: 100px;" onerror="this.src='/images/front/er8.jpg'"/>
	                   		<div class="yy-p" style="overflow: hidden;text-overflow: ellipsis;padding: 10px;">
	                     	<h2><a href="/doctor/{{id}}" target="_blank">{{username}}</a></h2>
	                      	<p>
	                         <span>地址：</span>{{address}}<br/>
	                          <span>联系方式：{{phone}}<br/>
	                          <span style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">医生执业经历：{{introduction}}</span>
	                          <a href="/doctor/{{id}}" target="_blank">详细介绍</a>
	                       	</p>
	                </div>
	              </div>
	         </div>
		{{/rows}}
	</script>
</body>
</html>
