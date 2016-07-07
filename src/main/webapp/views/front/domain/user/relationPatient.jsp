<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link type="text/css" href="/third-party/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" >
</head>
<body>
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a></div>
	       <%@ include file="/views/front/common/user/userLeft.htm" %>
	       <div class="w_851" style="padding-top:0;">
	        	<div class="t_851_4">关联用户</div>
	        	<span id="searchNoneTip" style="margin-left:10px;color:orange;display:none;">该身份证号和电话号码没有对应的用户，请补全用户信息</span>
	            <span id="searchSuccessTip" style="margin-left:10px;color:green;display:none;">已经自动填充用户信息</span>
	        	<input type="hidden" id="id" value="${relationPatient.id}"/>
	        	<input type="hidden" id="cityId" value="${relationPatient.city}"/>
	               	<table cellpadding="0" cellspacing="0" border="0" width="773">
	               		<tr height="40" valign="bottom">
                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>身份证号码</td>
                           <td width="329"><input type="text" class="input14" id="idcardNum" value="${relationPatient.idcardNum}"/></td>
                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>电话号码</td>
                           <td width="329"><input type="text" class="input14" id="phone"  value="${relationPatient.phone}"/></td>
                       </tr>
                       <tr height="50" valign="bottom">
                       	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>用户昵称</td>
                           <td width="310"><input type="text" class="input14" id="username" value="${relationPatient.username}"/></td>
                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>真实姓名</td>
                           <td width="310"><input type="text" class="input14" id="realName" value="${relationPatient.realName}"/></td>
                       </tr>
                       <tr height="40" valign="bottom">
                          <%--  <td width="67" style="line-height:30px;">年龄</td>
                           <td width="329"><input type="number" class="input14" id="age" value="${user.age}"/></td> --%>
                           <td width="67" style="line-height:30px;">邮箱</td>
                           <td width="329"><input type="text" class="input14" id="email" value="${relationPatient.email}"/></td>
                           <td width="67" style="line-height:30px;">与该用户关系</td>
                           <td width="329"><input type="text" class="input14" id="userRelation" value="${relationPatient.userRelation}"/></td>
                       </tr>
                       <tr height="40" valign="bottom">
                          <td width="67" style="line-height:30px;">职业</td>
                           <td width="329">
                           	<select class="input8" id="job">
								<c:forEach var="job" items="${jobList}">	                                	
                              		<option value="${job.id}" <c:if test="${relationPatient.jobId == job.id}">selected=selected</c:if>>${job.codeName}</option>
                              	</c:forEach>
                              </select>
                           </td>
                           <td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>出生日期</td>
                           <td width="329"><input type="text" class="input14" id="birthday" value='<fmt:formatDate value="${relationPatient.birthday}" pattern="YYYY-MM-dd hh:mm:ss"/>'/></td>
                       </tr>
                        <tr height="48" valign="bottom">
                         	<td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在省份</td>
                             <td colspan="1" width="299" >
                             <select class="input8" id="province" onChange="provinceChange()">
								<c:forEach var="province" items="${provinceCityAreaList}">	                                	
                             		<option value="${province.id}" <c:if test="${relationPatient.province == province.id}">selected = selected</c:if>>${province.areaName}</option>
                             	</c:forEach>
                             </select>
                             </td>
                            <td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在城市</td>
                             <td colspan="1" width="299" id="cityDiv">
                             	
                             </td>
                         </tr>
                       <tr height="40" valign="bottom">
                       		<td width="67" style="line-height:30px;">地址</td>
                            <td width="310"><input type="text" class="input14" id="address" value="${relationPatient.address}"/></td>
                       		<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>性　　别</td>
                            <td width="329" style="line-height:30px;">
                            	<input id="sex1" type="radio" name="sex" value="2" <c:if test="${relationPatient.sex == 2}">checked=checked</c:if> /> 男 &nbsp;&nbsp;&nbsp;
                            	<input id="sex2" type="radio" name="sex" value="1" <c:if test="${relationPatient.sex == 1}">checked=checked</c:if>/> 女
                            </td>
                       </tr>
                       <tr height="92" valign="bottom">
                       	<td width="67">&nbsp;</td>
                           <td colspan="3" width="706" style="text-align:center;">
                           	<button style="margin-right:80px;" type="button" onclick="upDateUser();" class="btn btn-success">保存</button>
                           	<button style="margin-right:80px;" type="button" onclick="lh.back();" class="btn btn-success">返回</button>
                           </td>
                       </tr>
                   </table>
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
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/relationPatient.js" title="v"></script>
</body>
</html>

