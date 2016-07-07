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
	<%@ include file="/views/front/common/hospital/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/hospital/hospitalTop.htm" %>
	    <div class="w_1000">
	    	<%@ include file="/views/front/common/hospital/hospitalLeft.htm" %><!--左边导航 -->
	         <input type="hidden" id="doctorId" value="${doctor.id}">
	         <input type="hidden" id="cityId" value="${doctor.city}">
            	<div class="t_851_2">医生基本信息</div>
                <div class="l_849">
                	<table cellpadding="0" cellspacing="0" border="0" width="773">
                		<tr height="48" valign="bottom">
                           <td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所属科室</td>
                            <td colspan="1" width="299" >
                            	<select class="input8" id="departmentId" style="width: 268px;">
								<c:forEach var="department" items="${departmentList}">	                                	
                             		<option value="${department.id}" <c:if test="${department.id == doctor.departmentId}">selected="selected"</c:if>>${department.name}</option>
                             	</c:forEach>
                             </select>
                            </td>
                        </tr>
                        <tr height="50" valign="bottom">
                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>医生昵称</td>
                            <td width="310"><input type="text" class="input14" id="username" value="${doctor.username}"/></td>
                        	<td width="80" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>医生真实姓名</td>
                            <td width="310"><input type="text" class="input14" id="realName" value="${doctor.realname}"/></td>
                        </tr>
                        <tr height="50" valign="bottom">
                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>身份证号码</td>
                        	<td width="310"><input type="text" class="input14" id="idCard" value="${doctor.idCard}"/></td>
                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>联系电话</td>
                            <td width="310"><input type="text" class="input14" id="phone" value="${doctor.phone}"/></td>
                        </tr>
                        <tr height="48" valign="bottom">
                         	<td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在省份</td>
                             <td colspan="1" width="299" >
                             <select class="input8" id="province" onChange="provinceChange()" style="width:268px;">
								<c:forEach var="province" items="${provinceCityAreaList}">	                                	
                             		<option value="${province.id}" <c:if test="${province.id == doctor.province}">selected="selected"</c:if>>${province.areaName}</option>
                             	</c:forEach>
                             </select>
                             </td>
                            <td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在城市</td>
                             <td colspan="1" width="299" id="cityDiv">
                             	
                             </td>
                         </tr>
                        <tr height="50" valign="bottom">
                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>性别</td>
                            <td width="310">
                            	<select id="sex" style="height:30px;width:268px;border: 1px solid #e4e4e3;">
                            		<c:if test="${empty doctor.sex}">
	                            		<option value="1">男</option>
	                            		<option value="2">女</option>
                            		</c:if>
                            		<c:if test="${!empty doctor.sex}">
                            			<c:if test="${doctor.sex == 1}">
	                            			<option value="1" selected="selected">男</option>
	                            			<option value="2">女</option>
	                            		</c:if>
                            			<c:if test="${doctor.sex == 2}">
	                            			<option value="1" >男</option>
	                            			<option value="2" selected="selected">女</option>
	                            		</c:if>
                            		</c:if>
                            	</select>
                            </td>
                        	<td width="67" style="line-height:30px;">邮箱</td>
                            <td width="310"><input type="text" class="input14" id="email" value="${doctor.email}"/></td>
                        </tr>
                        <tr height="50" valign="bottom">
                            <td width="67" style="line-height:30px;">联系地址</td>
                            <td width="310"><input type="text" class="input14" id="address" value="${doctor.address}"/></td>
                       		<c:if test="${empty doctor.id}">
                       			<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>密码</td>
                           	 	<td width="310"><input type="password" class="input14" id="password"/></td>
                        	</c:if>
                        </tr>
                        <tr height="50" valign="bottom">
                           	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>医生头像</td>
                           	<td style="top:10px;">
                        	<div  style="display:inline-block;float:left;">
								 <img id="doctorAvatar" class="picurl"  <c:if test="${!empty doctor.avatar}">src="${doctor.avatar}"</c:if> style="height:60px;max-width:100px;overflow:hidden;"/>
								 <input type="hidden" name="filePaths" id="filePaths" value="${doctor.avatar}"/>
								 <input type="hidden" name="fileDBIds" id="fileDBIds"/>
							 </div>
							 <button id="browse" type="button" class="sub_fil hover" style="margin-top:10px;">选择图片</button>
                       		</td>
                        </tr>
                        <tr>
                        	<td colspan="3" >
                        		 <div id="upload_outer_div" style="margin-top:30px;"><!-- 上传文件进度展示 --></div>
                        	</td>
                        </tr>
                        <tr height="92" valign="bottom">
                        	<td width="67">&nbsp;</td>
                            <td colspan="3" width="706">
                            	<input type="submit" class="sub_fil hover" value="完成" style="margin-left:165px;" onclick="addOrUpdateDoctor()" />
                            	<input type="submit" class="sub_fil hover" value="取消"  onclick="lh.back();" />
                               <!--  <input type="submit" class="sub_fil" value="导出" /> -->
                                <!-- <a href="/hospital/hospitalHome" class="cancel" style="font-size:14px; color:#333333; line-height:41px;">取消</a> -->
                            </td>
                        </tr>
                    </table>
               </div>
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<%@ include file="/views/common/common_upload_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/hospital/hospitalCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/hospital/addOrUpdateDoctor.js" title="v"></script>
</body>
</html>
