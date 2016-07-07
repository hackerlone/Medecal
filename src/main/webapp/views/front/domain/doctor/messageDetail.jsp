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
	<%@ include file="/views/front/common/doctor/top.htm"%>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm" %>
	    <div class="w_1000">
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        	<div class="c_851">
	            	<div class="t_851_2">留言详情</div>
	                <div class="l_849">
	                	<input type="hidden" id="messageId" value="${message.id}">
	                	<table cellpadding="0" cellspacing="0" border="0" width="773">
	                	 	<tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:30px;">留言人</td>
	                            <td width="310"><input type="text" class="input14" readonly="readonly" value="${message.senderName}"/></td>
	                	 	</tr>
	                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;" valign="top">标题</td>
	                            <td colspan="3" width="706"><textarea readonly="readonly" class="text_input">${message.title}</textarea></td>
	                        </tr>
	                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;" valign="top">内容</td>
	                            <td colspan="3" width="706"><textarea readonly="readonly" class="text_input">${message.content}</textarea></td>
	                        </tr>
	                        <tr height="93" valign="bottom" id="replyTr" <c:if test="${empty message.replyContent}"> style="display:none"</c:if>>
	                        	<td width="67" style="line-height:50px;" valign="top">回复内容</td>
	                            <td colspan="3" width="706"><textarea <c:if test="${!empty message.replyContent}"> readonly="readonly" </c:if> class="text_input" id="content">${message.replyContent}</textarea></td>
	                        </tr>
	                        <tr height="92" valign="bottom" id="noReply">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706" >
	                            	<button onclick="lh.back();" type="button" class="btn btn-default" style="float: left;margin-left: 150px;line-height: 27px;padding: 6px 50px;">返回</button>
	                            	<c:if test="${empty message.replyContent}"> 
	                            		<button onclick="reply('replyYes');" type="button" class="btn btn-success" style="float: left;margin-left: 10px;line-height: 27px;padding: 6px 50px;">回复</button>
	                            	</c:if>
	                            	<!-- <button type="button" class="btn btn-success" style="float: left;margin:0px 20px;line-height: 27px;">完成并保存为模板</button> -->
	                                <!-- <input type="submit" class="sub_fil" value="导出" />
	                                <a href="javascript:;" class="cancel" style="font-size:14px; color:#333333; line-height:41px;">取消</a> -->
	                            </td>
	                        </tr>
	                        <tr height="92" valign="bottom" id="reply" style="display:none">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706" >
	                            	<button onclick="reply('replyNo');" type="button" class="btn btn-default" style="float: left;margin-left: 150px;line-height: 27px;padding: 6px 50px;">取消</button>
	                            	<button onclick="saveReply();" type="button" class="btn btn-success" style="float: left;margin-left: 10px;line-height: 27px;padding: 6px 50px;">保存</button>
	                            </td>
	                        </tr>
	                    </table>
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
	<script type="text/javascript" src="/js/front/domain/doctor/messageDetail.js" title="v"></script>
</body>
</html>

