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
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
			<input type="hidden" id="userId" value="${user.id}">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a></div>
	        <%@ include file="/views/front/common/user/userLeft.htm" %>
	       <div class="w_824">
	        	<div class="c_824" style="background-color: #FFFFFF;padding: 0 0 0 20px;">
	            	<div class="t_851_2">咨询详情</div>
	                <div class="l_849">
	                	<input type="hidden" id="consultId" value="${consult.id}">
	                	<table cellpadding="0" cellspacing="0" border="0" width="773">
	                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;" valign="top">咨询标题</td>
	                            <td colspan="3" width="706"><textarea readonly="readonly" class="text_input">${consult.title}</textarea></td>
	                        </tr>
	                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;" valign="top">主诉内容</td>
	                            <td colspan="3" width="706"><textarea readonly="readonly" class="text_input">${consult.mainContent}</textarea></td>
	                        </tr>
	                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;" valign="top">请求帮助内容</td>
	                            <td colspan="3" width="706"><textarea readonly="readonly" class="text_input">${consult.helpContent}</textarea></td>
	                        </tr>
	                        <c:if test="${!empty consult.replyContent}">
		                        <tr height="93" valign="bottom">
		                        	<td width="67" style="line-height:50px;" valign="top">回复内容</td>
		                            <td colspan="3" width="706"><textarea readonly="readonly"  class="text_input" id="content">${consult.replyContent}</textarea></td>
		                        </tr>
	                        </c:if>
	                        <tr height="92" valign="bottom">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706" >
	                            	<button onclick="lh.back();" type="button" class="btn btn-default" style="float: left;margin-left: 150px;line-height: 27px;padding: 6px 50px;">返回</button>
	                            	<!-- <button type="button" class="btn btn-success" style="float: left;margin:0px 20px;line-height: 27px;">完成并保存为模板</button> -->
	                                <!-- <input type="submit" class="sub_fil" value="导出" />
	                                <a href="javascript:;" class="cancel" style="font-size:14px; color:#333333; line-height:41px;">取消</a> -->
	                            </td>
	                        </tr>
	                    </table>
	                </div>
	            </div>
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
</body>
</html>

