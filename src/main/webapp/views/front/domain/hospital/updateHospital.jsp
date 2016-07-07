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
	        <div class="w_851">
		        	<div class="c_851">
		        		<input type="hidden" id="hospitalId" value="${hospitalId}">
		        		<input type="hidden" id="cityId" value="${hospital.city}">
		            	<div class="t_851_2">诊所基本信息<button class="fr btn btn-success" onclick="reset();">重置密码</button></div>
		                <div class="l_849">
		                	<table cellpadding="0" cellspacing="0" border="0" width="773" id="baseInformation">
		                        <tr height="50" valign="bottom">
		                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>诊所简称</td>
		                            <td width="310"><input type="text" class="input14" id="briefName" value="${hospital.briefName}"/></td>
		                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>诊所全称</td>
		                            <td width="310"><input type="text" class="input14" id="wholeName" value="${hospital.wholeName}"/></td>
		                        </tr>
		                        <tr height="50" valign="bottom">
		                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>诊所地址</td>
		                            <td width="310"><input type="text" class="input14" id="address" value="${hospital.address}"/></td>
		                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>联系电话</td>
		                            <td width="310"><input type="text" class="input14" id="phone" value="${hospital.phone}"/></td>
		                        </tr>
		                        <tr height="40" valign="bottom">
		                        	<td width="67" style="line-height:30px;">座机号码</td>
		                            <td width="310"><input type="text" class="input14" id="tel" value="${hospital.tel}"/></td>
		                        	<td width="67" style="line-height:30px;"><span style="color:red;font-weight:bolder;">*</span>血液检测</td>
		                            <td width="310">
		                            	<select id="bloodTest" class="input14">
		                            		<option value="1" <c:if test="${hospital.bloodTest == 1}">selected = selected</c:if>>能</option>
		                            		<option value="2" <c:if test="${hospital.bloodTest == 2}">selected = selected</c:if>>不能</option>
		                            	</select>
		                            </td>
		                        </tr>
		                        <tr height="48" valign="bottom">
	                            	<td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在省份</td>
	                                <td colspan="1" width="299" >
	                                <select class="input8" id="province" onChange="provinceChange()" style="width: 268px;">
										<c:forEach var="province" items="${provinceCityAreaList}">	                                	
	                                		<option value="${province.id}" <c:if test="${province.id == hospital.province}">selected="selected"</c:if>>${province.areaName}</option>
	                                	</c:forEach>
	                                </select>
	                                </td>
	                               <td width="88" style="color:#333333; line-height:33px;"><span style="color:red;font-weight:bolder;">*</span>所在城市</td>
	                                <td colspan="1" width="299" id="cityDiv">
	                                	
	                                </td>
	                            </tr>
	                            <tr height="40" valign="bottom">
		                        	<td width="67" style="line-height:30px;">QQ</td>
		                            <td width="310"><input type="text" class="input14" id="qq" value="${hospital.qq}"/></td>
			                        <td width="67" style="line-height:30px;">邮箱</td>
	                            	<td width="310"><input type="text" class="input14" id="email" value="${hospital.email}"/></td>
		                        </tr>
		                        <tr height="40" valign="bottom">
		                        	<td width="67" style="line-height:30px;">微信</td>
		                            <td width="310"><input type="text" class="input14" id="weixin" value="${hospital.weixin}"/></td>
			                        <td width="67" style="line-height:30px;">微博</td>
	                            	<td width="310"><input type="text" class="input14" id="weibo" value="${hospital.weibo}"/></td>
		                        </tr>
	                             <tr height="93" valign="bottom">
			                        	<td width="67" style="line-height:50px;" valign="top"><span style="color:red;font-weight:bolder;">*</span>简介</td>
			                            <td colspan="3" width="706"><textarea style="width:662px;height:200px;" id="introduction" class="text_input">${hospital.introduction}</textarea></td>
			                    </tr>
	                            <tr height="50" valign="bottom">
	                            	<td width="67" style="line-height:30px;">诊所LOGO</td>
	                            	<td style="top:10px;">
		                        	<div style="display:inline-block;float:left;">
										 <img  class="picurl" id="hospitalLogo" src="${hospital.logo}" style="height:60px;max-width:100px;overflow:hidden;"/>
										 <input type="hidden" name="filePaths" id="filePaths" value="${hospital.logo}"/>
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
		                            	<input type="submit" class="sub_fil hover" value="完成" style="margin-left:165px;" onclick="updateHospital()" />
		                            	<input type="submit" class="sub_fil hover" value="取消"  onclick="lh.back();" />
		                               <!--  <input type="submit" class="sub_fil" value="导出" /> -->
		                                <!-- <a href="/hospital/hospitalHome" class="cancel" style="font-size:14px; color:#333333; line-height:41px;">取消</a> -->
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
		                           	<button style="margin-right:80px;" type="button" onclick="upDateHospitalPassword();" class="btn btn-success">保存</button>
		                           	<button style="margin-right:80px;" type="button" onclick="reset('cancel');" class="btn btn-success">取消</button>
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
	<%@ include file="/views/common/common_upload_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/hospital/hospitalCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/hospital/updateHospital.js" title="v"></script>
</body>
</html>
