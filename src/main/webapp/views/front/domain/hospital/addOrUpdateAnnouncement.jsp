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
	<%@ include file="/views/front/common/hospital/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/hospital/hospitalTop.htm" %>
	    <div class="w_1000">
	    	<%@ include file="/views/front/common/hospital/hospitalLeft.htm" %><!--左边导航 -->
	        <div class="w_851">
	        	<div class="c_851">
	            	<div class="t_851_2">写公告</div>
	                <div class="d_851_form">
	                	<div class="title">
	                    	<table cellpadding="0" cellspacing="0" border="0" width="811">
	                        	<tr height="58" valign="middle" align="left">
	                            	<td width="46" style="color:#333333; font-size:14px;">标题：</td>
	                                <td width="435"><input type="text" id="title" class="input5" /></td>
	                                <td width="330" style="color:#5c8c3f">（1~50字）</td>
	                            </tr>
	                        </table>
	                    </div>
	                    <div class="text1">
	                    	<div class="edit">
	                    	 	<script id="editor" type="text/plain" style="width:810px;height:400px;"></script>
	                    	</div>
	                        <div class="form">
	                        <table cellpadding="0" cellspacing="0" border="0" width="773">
	                        	<!-- <tr height="30" valign="middle">
	                            	<td width="74" style="color:#333333;">上传附件：</td>
	                                <td colspan="5" width="699" style="color:#5c8c3f; ">最多上传3个附件，每个附件大小不超过2M</td>
	                            </tr>
	                            <tr height="43" valign="middle">
	                            	<td width="74">&nbsp;</td>
	                                <td width="69" style="color:#555555; font-size:12px;">文件描述：</td>
	                                <td width="234"><input type="text" class="input6" /></td>
	                                <td width="234"><input type="text" class="input6" /></td>
	                                <td width="69"><input type="button" value="浏览" class="input7" /></td>
	                                <td width="93" style="font-size:12px; color:#555555">增加一条</td>
	                            </tr>
	                            <tr height="43" valign="middle">
	                            	<td width="74">&nbsp;</td>
	                                <td width="69">&nbsp;</td>
	                                <td colspan="4" width="630"><a href="javascript:;">上传</a></td>
	                            </tr> -->
	                           <%--  <tr height="48" valign="bottom">
	                            	<td width="74" style="color:#333333; line-height:33px;">文章属性：</td>
	                                <td colspan="5" width="699" >
	                                <select class="input8" id="typeId">
										<c:forEach var="dict" items="${dictList}">	                                	
	                                		<option value="${dict.id}">${dict.codeName}</option>
	                                	</c:forEach>
	                                </select>
	                                </td>
	                            </tr> --%>
	                        </table>
	                        </div>
	                    </div>
	                    <div class="Submit">
	                        <input type="submit" class="input9" onclick="addOrUpdateAnnouncement()"/>
	                        <input type="submit" value="取消" class="input9" style="margin-left:5px" onclick="lh.back();"/>
	                        <!-- <a href="/hospital/hospitalHome">取消</a> -->
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_ueditor_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/hospital/hospitalCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/hospital/addOrUpdateAnnouncement.js" title="v"></script>
</body>
</html>
