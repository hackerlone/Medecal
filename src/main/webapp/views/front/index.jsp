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
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_banner">
		<div class="w_1000">
	    	<div class="banner">
	        	<div class="pz_slides_3">
	                <div class="_btn"></div>
	                <div class="_pic">
	                	<ul style='height:331px;'>
	                		<c:if test="${!empty advertisementList}">
		                		<c:forEach var="advertisement" items="${advertisementList}">
		                    		<li><a href="${advertisement.linkUrl}" target="_blank"><img onerror="this.src='/images/front/banner.jpg'" src="${advertisement.picPath}" width="746" height="321" /></a></li>
		                        </c:forEach>
	                        </c:if>
	                        <c:if test="${empty advertisementList}">
	                        	<li><a href="javascript:;"><img src="/images/front/banner.jpg" width="746" height="331" /></a></li>
	                        </c:if>
	                    </ul>
	                </div>
	            </div>
	        </div>
	        <div class="r_344">
				<div class="t_344" style='margin-top:10px;'>
					<a href="/doctorLogin"><img src="/images/front/main1-1.jpg" style="height:110px;width:344px;"/></a>
					<div class="pf_280">
					<div class="tit tit-1"><strong>会员入口</strong><br /><span>member entrance</span></div>

					<a href="/login">
					<div class="more-1">登录</div>
					</a>

					<a href="/register" target="_blank">
						<div class="more-2">注册</div>
					</a>
					</div>
				</div>
				<div class="t_344" style='margin-top:2px;'>
	            	<a href="/doctorLogin"><img src="/images/front/main10-10.jpg" style="height:110px;width:344px;"/></a>
	                <div class="pf_280">
	                	<a href="/doctorLogin" target="_blank">
	                    	<div class="tit"><strong>医生入口</strong><br /><span>Doctor entrance</span></div>
	                        <div class="more">进入</div>
	                    </a>
	                </div>
	            </div>
	            <div class="t_344" style='margin-top:2px;'>
	            	<a href="/hospitalLogin"><img src="/images/front/main4-4.jpg" style="height:110px;width:344px;"/></a>
	                <div class="pf_280">
	                	<a href="/hospitalLogin" target="_blank">
	                    	<div class="tit"><strong>医疗机构入口</strong><br /><span>Medical institution entrance</span></div>
	                        <div class="more">进入</div>
	                    </a>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	<div class="c_1100_4">
	<div class="l_746">
	<div class="tit_0100">
	<div class="work">
	良医在线 &nbsp;<span>Good doctor online</span>
	</div>
	<div class="more_1"><a href="javascript:;" style="background:none;">共${doctorTotal}位医生在线</a></div>
	</div>
	<div class="h_1">&nbsp;</div>
	<div class="d_746">
	<ul>
	<c:forEach items="${doctorList}" var="doctor">
		<li>
		<div class="t_img">

		<a href="/doctor/${doctor.id}" target="_blank"><img class="img-circle" src="${doctor.avatar}" onerror="this.src='/images/front/main13.png'"/></a>

		</div>
		<div class="tit1">
		<a href="/doctor/${doctor.id}" target="_blank">${doctor.username}<br /><span>${doctor.departmentName}/${doctor.titleNames}</span></a>
		</div>
		<div class="con">
		<a target="_blank" href="/consult?doctorId=${doctor.id}&hospitalId=${doctor.hospitalId}">在线咨询</a>
		</div>
		</li>
	</c:forEach>
	</ul>
	</div>
	</div>
	<div class="r_344_2">
	<div class="tit_0100">
	<div class="work">
	名医观点 &nbsp;<span>Medical opinion</span>
	</div>
	<div class="more_1"><a href="/articleList/44" target="_blank">更多</a></div>
	</div>
	<div class="d_344_1">
	<div class="top_314 slideBox">
	<div class="hd">
	<ul><li>1</li><li>2</li><li>3</li></ul>
	</div>
	<div class="bd">
	<ul>
	<c:forEach items="${medicalOpinionList}" var="medicalOpinion" begin="0" end="2">
		<li><a href="/article/${medicalOpinion.typeId}/${medicalOpinion.id}"><img src="${medicalOpinion.picPaths}" onerror="this.src='/images/front/main14.jpg'" width="314" height="100" /></a>
		<div class="pf_314">
		<a href="/article/${medicalOpinion.typeId}/${medicalOpinion.id}"><nobr>${medicalOpinion.title}</nobr></a>
		</div>
		</li>
	</c:forEach>
	</ul>
	</div>
	</div>
	<div class="d_list">
	<ul>
	<c:forEach items="${medicalOpinionList}" var="medicalOpinion" begin="3">
		<li><a href="/article/${medicalOpinion.typeId}/${medicalOpinion.id}"><nobr>${medicalOpinion.title}</nobr></a></li>
	</c:forEach>
	</ul>
	</div>
	</div>
	</div>
	</div>

	<div class="c_1100_3">
	        	<div class="l_368">
	            	<div class="tit_0100">
	                	<div class="work">
	                    	热点讨论 &nbsp;<span>Hot discussion</span>
	                    </div>
	                    <div class="more_1"><a href="/articleList/43" target="_blank">更多</a></div>
	                </div>
	                <div class="list1">
	                	<ul>
	                		<c:forEach items="${hotDiscussionList}" var="hotDiscussion" ><%-- begin="0" end="0" --%>
		                    	<li class="l1"><a href="/article/${hotDiscussion.typeId}/${hotDiscussion.id}">
		                        	<div class="l_80"><img src="${hotDiscussion.picPaths}"onerror="this.src='/images/front/main8.jpg'" width="80" height="60" /></div>
		                            <div class="r_256">
		                            	<div class="t_tit">${hotDiscussion.title}</div>
		                                <div class="d_time"><fmt:formatDate value="${hotDiscussion.createdAt}" pattern="YYYY-MM-dd"/></div>
		                            </div>
		                        </a></li>
	                        </c:forEach>
	                       <%--  <c:forEach items="${hotDiscussionList}" var="hotDiscussion" begin="1" >
	                        	<li class="l2 first"><a href="/article/${hotDiscussion.typeId}/${hotDiscussion.id}"><nobr>${hotDiscussion.title}</nobr></a></li>
	                        </c:forEach> --%>
	                    </ul>
	                </div>
	            </div>
	            <div class="l_368">
	            	<div class="tit_0100">
	                	<div class="work">
	                    	健康资讯 &nbsp;<span>Health information</span>
	                    </div>
	                    <div class="more_1"><a href="/articleList/42" target="_blank">更多</a></div>
	                </div>
	                <div class="list1">
	                	<ul>
	                    	<c:forEach items="${healthInformationList}" var="healthInformation" ><%-- begin="0" end="0" --%>
		                    	<li class="l1"><a href="/article/${healthInformation.typeId}/${healthInformation.id}">
		                        	<div class="l_80"><img src="${healthInformation.picPaths}" onerror="this.src='/images/front/main10.jpg'" width="80" height="60" /></div>
		                            <div class="r_256">
		                            	<div class="t_tit">${healthInformation.title}</div>
		                                <div class="d_time"><fmt:formatDate value="${healthInformation.createdAt}" pattern="YYYY-MM-dd"/></div>
		                            </div>
		                        </a></li>
	                        </c:forEach>
	                       <%--  <c:forEach items="${healthInformationList}" var="healthInformation" begin="1" >
	                        	<li class="l2 first"><a href="/article/${healthInformation.typeId}/${healthInformation.id}"><nobr>${healthInformation.title}</nobr></a></li>
	                        </c:forEach> --%>
	                    </ul>
	                </div>
	            </div>
	            <div class="r_344_1 pz_tab">
	            	<div class="t_344_1 hd">
	                	<ul>
	                    	<li class="left1 on">公告</li>
	                        <li>活动</li>
	                        <li>调查</li>
	                    </ul>
	                </div>
	                <div class="d_344">
	                	<ul class="bd">
	                		<c:forEach var="announcement" items="${announcementList}">
		                    	<li>
		                        	<div class="t_309">
		                            	<div class="l_55"><strong><fmt:formatDate value="${announcement.createdAt}" pattern="dd"/></strong><br />
		                            	<fmt:formatDate value="${announcement.createdAt}" pattern="YYYY/MM"/></div>
		                                <div class="r_242"><a href="/articleList/${announcement.typeId}">${announcement.title}</a></div>
		                            </div>
		                            <div class="d_309">
		                           	 	${announcement.content}
		                            </div>
		                        </li>
	                        </c:forEach>
	                		<c:forEach var="activity" items="${activityList}">
		                    	<li>
		                        	<div class="t_309">
		                            	<div class="l_55"><strong><fmt:formatDate value="${activity.createdAt}" pattern="dd"/></strong><br />
		                            	<fmt:formatDate value="${activity.createdAt}" pattern="YYYY/MM"/></div>
		                                <div class="r_242"><a href="/articleList/${activity.typeId}">${activity.title}</a></div>
		                            </div>
		                            <div class="d_309">
		                           	 	${activity.content}
		                            </div>
		                        </li>
	                        </c:forEach>
	                		<c:forEach var="investigation" items="${investigationList}">
		                    	<li>
		                        	<div class="t_309">
		                            	<div class="l_55"><strong><fmt:formatDate value="${investigation.createdAt}" pattern="dd"/></strong><br />
		                            	<fmt:formatDate value="${investigation.createdAt}" pattern="YYYY/MM"/></div>
		                                <div class="r_242"><a href="/articleList/${investigation.typeId}">${investigation.content}</a></div>
		                            </div>
		                            <div class="d_309">
		                           	 	${investigation.content}
		                            </div>
		                        </li>
	                        </c:forEach>
	                    </ul>
	                </div>
	            </div>
	        </div>
	        <div class="c_1100_5">
	        	<div class="tit_0100">
	                	<div class="work" style="padding:0 0 0 10px; background-position:0 center;">
	                    	发生在身边的那些事
	                    </div>
	                    <div class="more_1"><a href="/articleList/48" target="_blank">更多</a></div>
	             </div>
	             <div class="d_1100">
	             	<ul>
	             		<c:forEach items="${sideList}" var="side">
		                	<li>
		                    	<div class="t_260"><a href="/article/${side.typeId}/${side.id}"><img src="${side.picPaths}" onerror="this.src='/images/front/main16.jpg'" width="260" height="175" /></a></div>
		                        <div class="tit_260"><a href="/article/${side.typeId}/${side.id}"><nobr>${side.title}</nobr></a></div>
		                        <div class="time_260"><fmt:formatDate value="${side.createdAt}" pattern="YYYY-MM-dd"/></div>
		                        <div class="d_260"><a href="/article/${side.typeId}/${side.id}">${side.content}</a></div>
		                    </li>
	                    </c:forEach>
	                </ul>
	             </div>
	        </div>
	    </div>
	</div>

	<div class="pz_down">

	<%@ include file="/views/front/common/otherlink.htm" %>
	<%@ include file="/views/front/common/menuBottom.htm" %>
	<%@ include file="/views/front/common/bottom.htm" %>
	</div>
	<%@ include file="/views/front/common/nav.htm" %>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/index.js" title="v"></script>
</body>
</html>

