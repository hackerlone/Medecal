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
	<input type="hidden" value="${typeId}" id="typeId">
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm"%><!-- 顶部栏 -->
		<%@ include file="/views/front/common/menuTop.htm"%><!-- 顶部菜单栏（搜索+菜单） -->
	</div>
	<div class="pz_main">
		<div class="w_1000">
			<div class="c_1100_10">
				当前位置： <a href="/">首页</a> - <a href="/articleList/${typeId}">${typeName}</a><!--  - <a href="javascript:;">健康资讯分类一</a> -->
			</div>
			<div class="w_824">
				<div class="c_824">
					<ul id="articleListUl">
					</ul>
					<div class="fy_new">
						<div id="page" class="inline-center"></div>
					</div>
				</div>
			</div>
			<div class="w_256">
				<div class="c_256">
					<div class="t_256">${typeName}</div><%-- ${typeName_is} --%>
					<div class="pz_SideLayer1">
<!-- 						<li class="l3"><a href="javascript:;" class="hover">健康资讯分类一</a></li> -->
<!-- 						<li class="l3"><a href="javascript:;">健康资讯分类二</a></li> -->
						<c:forEach items="${latestArticleList}" var="latestArticle">
							<li class="l3"><a href="/article/${latestArticle.typeId}/${latestArticle.id}">${latestArticle.title}</a></li>
						</c:forEach>
					</div>
				</div>
				<c:if test="${typeId != 43}">
				<div class="c_256_1">
					<div class="tit_h_47">热点讨论 <a href="/articleList/43" class="fr">更多</a></div>
					<div class="list_256">
						<ul>
<%-- 							<c:forEach items="${hotDiscussionList}" var="hotDiscussion"> --%>
<%-- 	                    		<li><a href="/article/${hotDiscussion.typeId}/${hotDiscussion.id}"><nobr>${hotDiscussion.title}</nobr></a></li> --%>
<%-- 	                    	</c:forEach> --%>
	                    	<c:forEach items="${hotDiscussionList}" var="hotDiscussion">
	                    		<li><a href="/article/${hotDiscussion.typeId}/${hotDiscussion.id}"><nobr>${hotDiscussion.title}</nobr></a></li>
	                    	</c:forEach>
						</ul>
					</div>
				</div>
				</c:if>
				<div class="c_256_2">
					<div class="tit_h_47">良医在线</div>
					<div class="list_256_1">
						<ul>
							<c:forEach items="${doctorList}" var="doctor">
		                    	<li style="border-top:0;">
		                        	<div class="l_86">
		                            	<a href="/doctor/${doctor.id}"><img src="${doctor.avatar}" width="86" height="86" onerror="this.src='/images/front/main13.png'"/></a>
		                            </div>
		                            <div class="r_126">
		                            	<div class="t_126">
		                                	<a href="/doctor/${doctor.id}">${doctor.username}<br /><span>${doctor.departmentName}/${doctor.titleNames}</span></a>
		                                </div>
		                                <div class="more_2">
		                                	<a href="/consult?doctorId=${doctor.id}&hospitalId=${doctor.hospitalId}">在线咨询</a>
		                                </div>
		                            </div>
		                        </li>
	                        </c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="pz_down">
		<%@ include file="/views/front/common/menuBottom.htm"%><!-- 底部菜单栏 -->
		<%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/front/common/nav.htm"%><!-- 右侧浮动导航菜单 -->
	
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/base/article/articleList.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		{{#rows}}
			<li class="gray">
				<a href="/article/{{typeId}}/{{id}}">
					<div class="l_new">
						<nobr>{{title}}</nobr>
					</div>
					<span>{{&createDate}}</span>
				</a>
			</li>
		{{/rows}}
	</script>
</body>
</html>

