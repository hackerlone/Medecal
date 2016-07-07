<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/third-party/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>
</head>
<body>
	<%@ include file="/views/front/common/doctor/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm" %> <!-- 顶部菜单栏 -->
	    <div class="w_1000">
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        	<div class="c_851">
	            	<div class="t_851_2">患者信息</div>
	                <div class="l_849">
	                	<input type="hidden" id="userId" value="${user.id}"/>
	                	<input type="hidden" id="cityId" value="${user.city}">
	                	<table cellpadding="0" cellspacing="0" border="0" width="773">
	                       <tr height="50" valign="bottom">
	                       	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>患者昵称</td>
	                           <td width="310"><input type="text" class="input14" id="username" value="${user.username}"/></td>
	                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>真实姓名</td>
	                           <td width="310"><input type="text" class="input14" id="realName"  value="${user.realName}"/></td>
	                       </tr>
	                       <tr height="40" valign="bottom">
	                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>电话号码</td>
	                           <td width="329"><input type="text" class="input14" id="phone" value="${user.phone}"/></td>
	                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>身份证号码</td>
	                           <td width="329"><input type="text" class="input14" id="idcardNum" value="${user.idcardNum}"/></td>
	                       </tr>
	                       <tr height="40" valign="bottom">
	                       		<td width="67" style="line-height:30px;">职业</td>
		                           <td width="329">
		                           <select class="input8" id="job" style="width:268px;">
										<c:forEach var="job" items="${jobList}">	                                	
	                                		<option value="${job.id}" <c:if test="${job.id == user.job}">selected="selected"</c:if>>${job.codeName}</option>
	                                	</c:forEach>
	                                </select>
	                           </td>
	                           <td width="67" style="line-height:30px;">邮箱</td>
	                           <td width="329"><input type="text" class="input14" id="email" value="${user.email}"/></td>
	                       </tr>
	                       <tr height="40" valign="bottom">
	                           
	                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>出生日期</td>
	                           <td width="329"><input type="text" class="input14" id="birthday" value="<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>"/></td>
	                       		<td width="67" style="line-height:30px;">年龄</td>
	                           <td width="329"><input type="number" class="input14" id="age" value="${user.age}"/></td>
	                       </tr>
	                       <tr height="48" valign="bottom">
                            	<td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在省份</td>
                                <td colspan="1" width="299" >
                                <select class="input8" id="province" onChange="provinceChange()" style="width:268px;">
									<c:forEach var="province" items="${provinceCityAreaList}">	                                	
                                		<option value="${province.id}" <c:if test="${province.id == user.province}">selected="selected"</c:if>>${province.areaName}</option>
                                	</c:forEach>
                                </select>
                                </td>
                               <td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在城市</td>
                                <td colspan="1" width="299" id="cityDiv" style="width:268px;">
                                
                                </td>
                            </tr>
	                       <tr height="40" valign="bottom">
	                       		<td width="67" style="line-height:30px;">地址</td>
	                            <td width="310"><input type="text" class="input14" id="address" value="${user.address}"/></td>
	                       		<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>性别</td>
	                            <td width="310">
	                            	<select id="sex" style="height:30px;width:268px;border: 1px solid #e4e4e3;">
	                            		<c:if test="${empty user.sex}">
		                            		<option value="1">男</option>
		                            		<option value="2">女</option>
	                            		</c:if>
	                            		<c:if test="${!empty user.sex}">
	                            			<c:if test="${user.sex == 1}">
		                            			<option value="1" selected="selected">男</option>
		                            			<option value="2">女</option>
		                            		</c:if>
	                            			<c:if test="${user.sex == 2}">
		                            			<option value="1" >男</option>
		                            			<option value="2" selected="selected">女</option>
		                            		</c:if>
	                            		</c:if>
	                            	</select>
	                            </td>
	                       </tr>
	                        <tr height="92" valign="bottom">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706">
	                            	<input type="submit" class="sub_fil hover" value="完成" style="margin-left:165px;" onclick="addOrUpdatePatient()" />
	                                <input type="submit" class="sub_fil" value="返回" onclick="lh.back();" />
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
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/addOrUpdatePatient.js" title="v"></script>
</body>
</html>

