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
	    	<div class="c_1100_10" style="float: none;">当前位置：<a href="/">首页</a>-血液检测</div>
	        <div id="ss-ul" style="height: inherit;">
	        
	        	<div class="hd" style="margin:10px;">
	               	<span class="input8" style="float:left;width: 40px;border:none;padding-top: 10px;">省份:</span>
	               	<select id="province" class="input8" style="width: 80px;  margin-top: 3px;" onChange="provinceChange()">
	               		<option value="">全部</option>
	               		<c:forEach var="provinceCityArea" items="${provinceCityAreaList}">
	               			<option value="${provinceCityArea.id}" <c:if test="${user.province == provinceCityArea.id}">selected = selected</c:if>>
	               				${provinceCityArea.areaName}
	               			</option>
	               		</c:forEach>
	               	</select>
	               	<span style="float:left;padding-top: 10px;width: 40px;">县市:</span>
	               	<div id="cityDiv"><select id="city" class="input8" style="width: 80px;  margin-top: 3px;"></select></div>
	               	<!-- <div style="margin-left:260px;">
		               	<button class="btn btn-success" onclick="doSearch();">查询</button>
	    	           	<button class="btn btn-default" onclick="resetSearch();">重置</button>
	               	</div> -->
               </div>
	        
	            <div style="clear:both;"></div>
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
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/hospitalBloodTest.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		{{#rows}}
	            <div class="yy-int">
	            	<div class="yy-content">
	                	<img src="{{logo}}" class="yy-int-img pointer" onclick="javascript:window.open('/hospital/hospital/{{id}}')" style="width: 130px;height: 130px;  overflow: hidden;" onerror="this.src='/images/front/er8.jpg'"/>
	                   		<div class="yy-p" style="overflow: hidden;text-overflow: ellipsis;padding: 10px;">
	                     	<h2><a href="/hospital/hospital/{{id}}" target="_blank">{{wholeName}}</a></h2>
	                      	<p>
	                          <span>地址：</span>{{provinceName}}-{{cityName}}-{{address}}<br/>
	                          <span>联系方式：{{phone}},{{tel}}<br/>
	                          <button class="btn btn-success" onclick="location.href='/bloodTest/{{id}}'">血液检测</button>
	                       	</p>
	                </div>
	              </div>
	         </div>
		{{/rows}}
	</script>
</body>
</html>
