<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
</head>
<body>
	<%@ include file="/views/front/common/doctor/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm"%> <!-- 顶部菜单栏 -->
	    <div class="w_1000">
			<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        	<div class="t_851 txtScroll-top">
	            	<div class="l_97">最新消息：</div>
	                <div class="list_news bd">
	                	<ul>
	                    	<li><a style="text-decoration: none;">
                   				 您有<u id="bespeakCount">0</u>个预约，
	                    	    <u id="consultCount">0</u>条未处理咨询，
	                    	    <u id="diagnoseApplyCount">0</u>个授权申请，
	                    	    <u id="messageCount">0</u>条留言</a>
	                    	 </li>
<!-- 	                        <li><a href="javascript:;"><nobr>您有<u>9</u>个预约，<u>6</u>条未处理咨询，<u>2</u>个授权申请，<u>8</u>条留言....</nobr></a></li> -->
<!-- 	                        <li><a href="javascript:;"><nobr>您有<u>9</u>个预约，<u>6</u>条未处理咨询，<u>2</u>个授权申请，<u>8</u>条留言....</nobr></a></li> -->
	                    </ul>
	                </div>
	            </div>
	            <div class="t_851_1">
	            	<ul>
	                	<li><a href="/treatment" class="l_01">开始就诊</a></li>
	                    <li><a href="/addOrUpdateArticlePage" class="l_02">随手记</a></li>
	                    <li><a href="/doctorBloodTest" class="l_03">血液检测</a></li>
	                    <li><a href="javascript:;" class="l_04">基因检测</a></li>
	                </ul>
	            </div>
	            <div class="d_851">
	            	<div class="tit2">
	                	<span>健康资讯</span>
	                    <div class="more"><a href="/doctorInformation">更多</a></div>
	                </div>
	                <div class="d_list1">
	                	<ul>
	                		<c:forEach items="${healthInformationList}" var="healthInformation">
	                    		<li class="color"><a href="/doctorArticleDetails/${healthInformation.id}"><nobr>${healthInformation.title}</nobr></a><span><fmt:formatDate value="${healthInformation.createdAt}" pattern="YYYY-MM-dd"/></span></li>
	                    	</c:forEach>
	                    </ul>
	                </div>
	            </div>
	            <div class="d_851">
	            	<div class="tit2">
	                	<span>热点讨论</span>
	                    <div class="more"><a href="/doctorArticle">更多</a></div>
	                </div>
	                <div class="d_list1">
	                	<ul>
	                		<c:forEach items="${hotDiscussionList}" var="hotDiscussion">
	                    		<li class="color"><a href="/doctorArticleDetails/${hotDiscussion.id}"><nobr>${hotDiscussion.title}</nobr></a><span><fmt:formatDate value="${hotDiscussion.createdAt}" pattern="YYYY-MM-dd"/></span></li>
	                    	</c:forEach>
	                    </ul>
	                </div>
	            </div>
	            <div class="wzswitch pz_tab">
	            	<div class="t_811 hd">
	                	<ul>
	                    	<li><a>公告</a></li>
	                       <!--  <li><a>活动</a></li> -->
	                    </ul>
	                </div>
	                <c:if test="${!empty announcementList}">
		                 	<c:forEach var="announcement" items="${announcementList}">
		                		<div class="g_811 bd">
				                	<li>
				                    	<div class="box_811">
				                        	<div class="t_771">
				                            	<span><strong><fmt:formatDate value="${announcement.createdAt}" pattern="dd"/></strong><br />
				                            	<fmt:formatDate value="${announcement.createdAt}" pattern="YYYY/MM"/></span>
				                                <div class="r_600"><span>${announcement.title}</span><br /></div>
				                            </div>
				                            <div class="d_771 pointer" id="showContent_${announcement.id}" onclick="showContent(${announcement.id})">
				                            	${announcement.content}
				                            </div>
				                        </div>
				                    </li>
		                		</div>
	                		</c:forEach>
	                    </c:if>
	                    <c:if test="${empty announcementList}">
	                    	<div>
		                    	<li>
			                    	<div class="box_811">
			                            <div class="d_771">
			                            	暂无数据
			                            </div>
			                        </div>
			                    </li>
		                    </div>
	                    </c:if>
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
	<script type="text/javascript" src="/js/front/domain/doctor/doctor.js" title="v"></script>
</body>
</html>

