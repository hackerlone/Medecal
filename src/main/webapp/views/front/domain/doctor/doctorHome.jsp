<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/css/front/doctor/doctor.css" title="v"/>
<style>
	* {
		box-sizing: initial;
		-webkit-box-sizing: border-box;
	}
</style>
</head>
<body>
	<%@ include file="/views/front/common/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm"%> <!-- 顶部菜单栏 -->
	   <div style="clear:both;"></div>
	   <div id="geren">
	   		<input type="hidden" id="doctorId" value="${doctorId}">
	   		<input type="hidden" id="hospitalId" value="${doctor.hospitalId}">
	        <div class="ge-fl">
	            <div class="flbt" id="flbt1"><h2>医生介绍</h2></div>
	            <div class="neirong1"><span class="span1">擅长</span></div>
	            <div class="neirong2"><p><c:if test="${! empty doctor.goodAt}">${doctor.goodAt}</c:if>
	            	<c:if test="${empty doctor.goodAt}">暂未填写</c:if>
	            </p></div>
	            <div class="neirong3"><span class="span2">执业经历</span></div>
	            <div class="neirong4"><p>
	            	<c:if test="${! empty doctor.introduction}">${doctor.introduction}</c:if>
	            	<c:if test="${empty doctor.introduction}">暂未填写</c:if>
	            </p></div>
	            <div class="flbt" id="flbt2"><h2>网站统计</h2></div>
	            <div class="neirong5">
	                <h4>总文章：<i>${articleCount}</i>篇</h4>
	                <h4>总患者：<i>${diagnoseCount}</i>位</h4>
	                <h4>上次在线：<br><i><fmt:formatDate value="${doctor.lastLoginTime}" pattern="YYYY-MM-dd HH:mm:ss"/></i></h4>
	                <h4>开通时间：<br><i><fmt:formatDate value="${doctor.createdAt}" pattern="YYYY-MM-dd HH:mm:ss"/></i></h4>
	            </div>
	            <div class="flbt" id="flbt3" onclick="selectBespeak(${doctor.id},${doctor.hospitalId})"><h2><a href="javascript:;">我要预约</a><img src="/images/front/naozhong.jpg"/></h2></div>
	        </div>
	        <div class="ge-fr">
	            <div class="ge-fr-tit"><h2>患友咨询区</h2><span><!-- （已帮助43782位患者） --></span></div>
	            <div class="ge-fr2">
	                <!-- <input class="ge-inp1" type="text" placeholder="请在此简单的描述病情，向李某某大夫提问。"> -->
	                <input class="ge-inp2" style=" float: left;" onclick="location.href='/consult?doctorId=${doctor.id}&hospitalId=${doctor.hospitalId}'" type="button" value="咨询">
	            </div>
	            <div class="zixunfanwei">
	                <p>咨询范围：<br/>${doctor.consultScope}</p>
	            </div>
	            <div class="ge-fr-tit2"><h2>${doctor.username}的文章</h2><!-- <span><a href="/articleList/42">More></a></span> --></div>
	            <div class="ge-ul">
	                <ul>
	                	<c:if test="${!empty articleList}">
		                	<c:forEach var="article" items="${articleList}">
		                    <li><a href="/article/${article.typeId}/${article.id}"><span>${article.title}</span></a> <i><fmt:formatDate value="${article.createdAt}" pattern="YYYY-MM-dd HH:mm:ss"/></i><img src="/images/front/yanj.jpg"><div>${article.scans}</div></li>
		                    <hr/>
		                    </c:forEach>
	                    </c:if>
	                    <c:if test="${empty articleList}">
		                    <li><a href="javascript:;"><span>暂无数据</span></a></li>
		                    <hr/>
	                    </c:if>
	                </ul>
	            </div>
	            <div class="message">
	                <div class="message-nav">
	                    <span>留言区</span>
	                </div>
	                <div class="message-inp">
	                    <textarea placeholder="给${doctor.username}大夫留言" id="content"></textarea>
	                </div>
	                <div class="message-sub">
	                    <input type="submit" value="留言" onclick="sendMessage()"/>
	                </div>
	            </div>
	        </div>
	        <div class="h_20">&nbsp;</div>
	   </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorHome.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
</body>
</html>

