<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/css/front/hospital/hospital.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/third-party/pagination/paging.css"/>
</head>
<body>
	<%@ include file="/views/front/common/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/hospital/hospitalTop.htm"%> <!-- 顶部菜单栏 -->
	   <div style="clear:both;"></div>
	   <div id="geren" style="height:1600px;">
	        <div class="ge-fl " style="height:1598px;">
	        	<input type="hidden" id="hospitalId" value="${hospitalId}">
	            <div class="flbt" id="flbt1"><h2>医院信息</h2></div>
	            <div class="neirong1"><span class="span1">医院介绍</span></div>
	            <div class="neirong2" style="margin-top:5px;width:225px;"><p style="letter-spacing:normal;">
	            <c:if test="${!empty hospital.introduction}">${hospital.introduction}</c:if>
	            <c:if test="${empty hospital.introduction}">暂无介绍</c:if>
	            </p></div>
	            <div class="neirong3 ys-nr3"><span class="span2">联系方式</span></div>
	            <div class="neirong4" style="margin-top:5px;width:225px;"><p style="letter-spacing:normal;"><b>地址：</b><br/>
	                <br/>
	                	${hospital.address}
	                <br/>
	                <br/>
	                <b >医院电话：</b><br/>
	                ${hospital.phone}<br/>
	                ${hospital.tel}</p></div>
	            <div class="flbt" id="flbt2" style="margin-top:140px;"><h2>网站统计</h2></div>
	            <div class="neirong5">
	                <h4>上次在线：<br><i><fmt:formatDate value="${hospital.lastLoginTime}"  pattern="YYYY-MM-dd HH:mm:ss"/></i></h4>
	                <h4>开通时间：<br><i><fmt:formatDate value="${hospital.createdAt}" pattern="YYYY-MM-dd HH:mm:ss"/></i></h4>
	            </div>
	        </div>
	        <div class="ge-fr ">
	            <div class="ge-fr-tit2" style="margin-top:70px;"><h2>${hospital.wholeName}医院的最新文章</h2><span><!-- <a href="#">More></a> --></span></div>
	            <div class="ge-ul ys-zy">
	                <ul>
	                	<c:if test="${!empty articleList}">
	                		<c:forEach var="article" items="${articleList}">
	                    		<li><a href="/article/${article.typeId}/${article.id}"><span>${article.title}</span></a> <i>2015-3-15</i><img src="/images/front/yanj.jpg"><div>${article.scans}</div></li>
	                    		<hr/>
	                    	</c:forEach>
	                    </c:if>
	                    <c:if test="${empty articleList}">
	                   	 	<li><span>暂无数据</span></li>
	                    </c:if>
	                </ul>
	            </div>
	            <div class="ys-zixun">
	                <div class="ge-fr-tit2" style="margin-bottom:0;"><h2>${hospital.wholeName}医院可咨询专家</h2><i style="font-size:16px;color:#fff;font-style:normal;"></i></div>
	                <div id="doctorList">
		               <!--  <div class="zixun-box">
		                    <img src="images/ys-zixun.png" style="padding-top:4px;">
		                    <h4>马艳</h4>
		                    <b>副主任医师</b>
		                    <span>血液科  皮肤病科  儿科</span>
		                    <a href="#"><input type="button" value="咨询" class="zx-btn1"/></a>
		                    <a href="#"><input type="button" value="预约" class="zx-btn2"/></a>
		                </div> -->
	                </div>
	                <div style="clear:both;"></div>
	                <div class="fy_new" style="float: none;width:853px;padding-top:20px;">
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
	<script type="text/javascript" src="/js/front/domain/hospital/hospitalCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/hospital/hospitalHome.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache"><!-- -->
		{{#rows}}
			<div class="zixun-box">

	           <img src="{{avatar}}" onclick="javascript:window.open('/doctor/{{id}}')" class="pointer img-circle"  style="padding-top:4px;width:130px;height:130px;" onerror="this.src='/images/front/main13.png'" onclick="location.href='/doctor/{{id}}'">

	            <div style="margin:10px;">
					<span onclick="javascript:window.open('/doctor/{{id}}')" class="pointer" style="color: rgb(101,153,70);font-size: 18px;">{{username}}</span>
	            	<span style="font-size: 14px;font-weight: 700;">{{titleNames}}</span>
				</div>
				<div style="margin:10px;">
	            	<span>{{departmentName}}</span>
				</div>
	            <a href="/consult?doctorId={{id}}&hospitalId={{hospitalId}}"><input type="button" value="咨询" class="zx-btn1"/></a>
	            <a href="#"><input type="button" value="预约" onclick="selectBespeak('{{id}}','{{hospitalId}}')" class="zx-btn2"/></a>
	         </div>
		{{/rows}}		 		 
	</script>
</body>
</html>

