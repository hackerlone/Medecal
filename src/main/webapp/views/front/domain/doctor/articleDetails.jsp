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
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
<!-- 	        <div class="w_824"> -->
<!-- 				<div class="c_824" style="background-color: #FFFFFF;"> -->
<!-- 					<ul id="articleListUl"> -->
<!-- 						<li class="gray" id="noData" style="display:none;"> -->
<!-- 							<a href="javascript:;"> -->
<!-- 								<div class="l_new"> -->
<!-- 									<nobr>暂无数据</nobr> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 					<div class="fy_new"> -->
<!-- 						<div id="page" class="inline-center"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<input type="hidden" id="articleId" value="${article.id}">
			<div class="w_824" style="margin: 30px 0px 0px 20px;">
	        	<div class="page">
	            	<div class="t_824">
	                	<div class="tit3">${article.title}</div>
	                    <div class="time1">
	                    	<div class="l_flo" style="float:left; margin-left:133px;"><fmt:formatDate value="${article.createdAt}" pattern="YYYY-MM-dd HH:mm:ss"/> &nbsp;&nbsp;&nbsp;发布人：<span>${article.createdBy}</span> &nbsp;&nbsp;&nbsp;访问次数：<font>${article.scans}</font>  &nbsp;&nbsp;&nbsp;分享到：</div>
	                        <div class="bshare-custom" style="float:left; line-height:24px; font-size:12px;">
	                        <a title="分享到QQ空间" class="bshare-qzone"></a>
	                        <a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
	                        <a title="分享到人人网" class="bshare-renren"></a>
	                        <a title="分享到腾讯微博" class="bshare-qqmb"></a>
	                        <a title="分享到网易微博" class="bshare-neteasemb"></a>
	                        <a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a>
	                        </div>
	                        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
							<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
	                    </div>
	                </div>
	                <div class="d_824">
	                	${article.content}
<!-- 						<br /><div class="bshare-custom" style="float:left; line-height:24px; margin-top:25px; font-size:12px;"> -->
<!-- 	                        <a title="分享到QQ空间" class="bshare-qzone"></a> -->
<!-- 	                        <a title="分享到新浪微博" class="bshare-sinaminiblog"></a> -->
<!-- 	                        <a title="分享到人人网" class="bshare-renren"></a> -->
<!-- 	                        <a title="分享到腾讯微博" class="bshare-qqmb"></a> -->
<!-- 	                        <a title="分享到网易微博" class="bshare-neteasemb"></a> -->
<!-- 	                        <a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a> -->
<!-- 	                        </div> -->
<!-- 	                        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script> -->
<!-- 							<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script> -->
	                </div>
<!-- 	                <div class="updown"> -->
<%-- 	                	<div class="l_up"><nobr>上一篇：<a href="/article/${preArticle.typeId}/${preArticle.id}">${preArticle.title}</a></nobr></div> --%>
<%-- 	                    <div class="r_down"><nobr>下一篇：<a href="/article/${nextArticle.typeId}/${nextArticle.id}">${nextArticle.title}</a></nobr></div> --%>
<!-- 	                </div> -->
	            </div>
	            <div class="plun">
	                <div class="t_47">
	                	<div class="l_112">我要评论</div>
	                	<c:if test="${empty doctor}">
	                    	<span>您也可以登录后再评论：<a href="/login">登录</a> <font>|</font> <a href="/register">注册</a></span>
	                    </c:if>
	                </div>
	                <div class="bd_693">
	                        <div class="tt_691_2">
	                            <table width="784" border="0" cellspacing="0" cellpadding="0">
	                                <tr>
	                                    <td colspan="5" style="padding-bottom:10px;">
	                                    <textarea name="textarea" id="content" cols="45" rows="5" class="ttform1"></textarea></td>
	                                </tr>
	                                <tr>
	                                    <td width="784"><input type="image" src="/images/front/new_img3.jpg" onclick="addComment()" /></td>
	                                </tr>
	                            </table>
	                        </div>
	                    </div>
	                    <c:if test="${!empty commengList}">
		                	<div class="pl_693">
		                        <div class="tt_693_1">
		                            <div class="tit_px">评论列表</div>
		                        </div>
		                        <div class="tt_693_2">
		                            <ul>
		                            	<c:forEach items="${commengList}" var="comment">
			                                <li>
			                                    <div class="l_58"><a href="javascript:;">
			                                    <c:if test="${comment.typeId == 1}">
			                                    	<img src="${comment.patientAvatar}" width="58" height="58" onerror="this.src='/images/front/default_avatar.png'"/>
			                                    </c:if>
			                                    <c:if test="${comment.typeId == 2}">
			                                    	<img src="${comment.doctorAvatar}" width="58" height="58" onerror="this.src='/images/front/main13.png'"/>
			                                    </c:if>
			                                    </a></div>
			                                    <div class="r_616">
			                                        <div class="titnewn"><a href="javascript:;">${comment.createdBy}</a><span><fmt:formatDate value="${comment.createdAt}" pattern="YYYY-MM-dd HH:mm:ss"/></span></div>
			                                        <div class="textnewn">${comment.content}</div>
			                                    </div>
			                                </li>
		                                </c:forEach>
		                            </ul>
		                        </div>
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
	<script type="text/javascript" src="/js/front/base/article/article.js" title="v"></script>
</body>
</html>

