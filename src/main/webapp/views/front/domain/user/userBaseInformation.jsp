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
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a> - <a href="/userBaseInformation">基本信息</a></div>
	       <%@ include file="/views/front/common/user/userLeft.htm" %>
	       <div class="w_824" style="padding-top:0;">
	       	<div  class="c_824" style="background-color: #FFFFFF;padding: 0 0 0 20px;">
	        	<div class="t_851_4 pointer" id="remindNewMsg"  style="display:none;color:red;background: url(/images/front/main28.jpg) no-repeat 0 center;">&nbsp; 您有<u id="remindCount">0</u>个新的定期提醒</div>
	        	<div class="t_851_4">用户基本信息<button class="fr btn btn-success" onclick="reset();">重置密码</button></div>
	        	<div style="color:red;">带红色*的为必填</div>
	        	<input type="hidden" id="userId" value="${user.id}"/>
	        	<input type="hidden" id="cityId" value="${user.city}">
	               	<table cellpadding="0" cellspacing="0" border="0" width="773" id="baseInformation">
	               		<tr height="40" valign="bottom">
                           <td width="67" style="line-height:30px;"><span style="color:red;">*</span>电话号码</td>
                           <td width="329"><input type="text" class="input14" id="phone"  value="${user.phone}"/></td>
                           <td width="67" style="line-height:30px;"><span style="color:red;">*</span>身份证号码</td>
                           <td width="329"><input type="text" class="input14" id="idcardNum" readonly="readonly" value="${user.idcardNum}"/></td>
                       </tr>
                       <tr height="50" valign="bottom">
                       	<td width="67" style="line-height:30px;"><span style="color:red;">*</span>用户昵称</td>
                           <td width="310"><input type="text" class="input14" id="username" value="${user.username}"/></td>
                           <td width="67" style="line-height:30px;"><span style="color:red;">*</span>真实姓名</td>
                           <td width="310"><input type="text" class="input14" id="realName"  value="${user.realName}"/></td>
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
                           
                           <td width="67" style="line-height:30px;"><span style="color:red;">*</span>出生日期</td>
                           <td width="329"><input id="datetimepicker" class="input14" type="text" value="<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>">
                           <td width="67" style="line-height:30px;">年龄</td>
                           <td width="329"><input type="number" class="input14" id="age" value="${user.age}" readonly="readonly"/></td>
                       </tr>
                        <tr height="48" valign="bottom">
                         	<td width="88" style="color:#333333; line-height:33px;"><span style="color:red;">*</span>所在省份</td>
                             <td colspan="1" width="299" >
                             <select class="input8" id="province" onChange="provinceChange()" style="width:268px;">
								<c:forEach var="province" items="${provinceCityAreaList}">	                                	
                             		<option value="${province.id}" <c:if test="${province.id == user.province}">selected="selected"</c:if>>${province.areaName}</option>
                             	</c:forEach>
                             </select>
                             </td>
                            <td width="88" style="color:#333333; line-height:33px;"><span style="color:red;">*</span>所在城市</td>
                             <td colspan="1" width="299" id="cityDiv" style="width:268px;">
                             	
                             </td>
                         </tr>
                       <tr height="40" valign="bottom">
                       		<td width="67" style="line-height:30px;">地址</td>
                            <td width="310"><input type="text" class="input14" id="address" value="${user.address}"/></td>
                       		<td width="67" style="line-height:30px;"><span style="color:red;">*</span>性别</td>
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
                       <tr height="40" valign="bottom">
                         <td width="67" style="line-height:30px;" valign="middle"><span style="color:red;">*</span>用户头像</td>
                           	<td style="top:10px;"  valign="middle">
                        	<div style="display:inline-block;float:left;">
								 <img  class="picurl" id="userAvatar" src="${user.avatar}" style="height:60px;max-width:100px;overflow:hidden;padding:5px;"/>
								 <input type="hidden" name="filePaths" id="filePaths" value="${user.avatar}"/>
								 <input type="hidden" name="fileDBIds" id="fileDBIds"/>
							 </div>
							 <button id="browse" type="button" class="sub_fil hover" >选择图片</button>
                       		</td>
                        </tr>
                        <tr>
                        	<td colspan="3" >
                        		 <div id="upload_outer_div" style="margin-top:30px;"><!-- 上传文件进度展示 --></div>
                        	</td>
                        </tr>
                       <tr height="92" valign="bottom">
                       	<td width="67">&nbsp;</td>
                           <td colspan="3" width="706" style="text-align:center;">
                           	<button style="margin-right:80px;" type="button" onclick="upDatePatientInformation();" class="btn btn-success">保存</button>
                           	<button style="margin-right:80px;" type="button" onclick="lh.back();" class="btn btn-success">返回</button>
                           </td>
                       </tr>
                   </table>
                   <table cellpadding="0" cellspacing="0" border="0" width="773" style="display:none;" id="reset">
	               		<tr height="40" valign="bottom">
                           <td width="67" style="line-height:30px;">新密码</td>
                           <td width="329"><input type="password" class="input14" id="password"/></td>
                       </tr>
                       <tr height="92" valign="bottom">
                       	<td width="67">&nbsp;</td>
                           <td colspan="3" width="706" style="text-align:center;">
                           	<button style="margin-right:80px;" type="button" onclick="upDateUserPassword();" class="btn btn-success">保存</button>
                           	<button style="margin-right:80px;" type="button" onclick="reset('cancel');" class="btn btn-success">取消</button>
                           </td>
                       </tr>
                   </table>
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
	<%@ include file="/views/common/common_upload_js.htm"%>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/userBaseInformation.js" title="v"></script>
</body>
</html>

