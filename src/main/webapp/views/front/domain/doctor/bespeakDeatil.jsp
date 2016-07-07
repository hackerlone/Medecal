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
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm" %><!--左边导航 -->
	         <input type="hidden" id="bespeakId" value="${bespeak.id}">
          <div class="w_851">
	        	<div class="t_851_2">预约</div>
	        	<div class="c_851" style="border:none;">
	            	<div class="t_851_3" style="background: white;">
		            	<table cellpadding="0" cellspacing="0" border="0" style="  margin: 15px;">
		                    	<tr height="40">
		                        	<td width="85">患者姓名：</td>
		                            <td width="340"><input type="text" id="username" readonly="readonly" class="l_270" value="${bespeak.patientName}"/></td>
		                        </tr>
		                        <tr height="52">
		                        	<td width="85">患者性别：</td>
		                            <td width="340" style="font-size:12px; color:#444444">
		                            	<input type="radio" disabled="disabled" name="sex1" <c:if test="${bespeak.patientSex == 2}"> checked = checked</c:if> /> &nbsp;男 &nbsp;&nbsp;&nbsp;
		                                <input type="radio" disabled="disabled" name="sex1" <c:if test="${bespeak.patientSex == 1}"> checked = checked</c:if> /> &nbsp;女
		                            </td>
		                        </tr>
		                        <tr height="40">
		                        	<td width="85">手机号码：</td>
		                            <td width="340"><input type="text" id="phone" readonly="readonly" class="l_270" value="${bespeak.patientPhone}"/></td>
		                        </tr>
		                        <c:if test="${bespeak.typeId == 2}">
			                       <tr height="52">
			                        	<!-- <td width="100">是否代为远程诊疗：</td>
			                            <td width="300" style="font-size:12px; color:#444444">
			                            	<input type="radio" id="isRegisterAgentYes" checked="checked"/>是 &nbsp;&nbsp;
			                            	<input type="radio" id="isRegisterAgentNo" />否 &nbsp;&nbsp;
			                            </td> -->
			                           <td width="100">服务项目：</td>
			                            <td width="300" style="font-size:12px; color:#444444">
			                            	<input type="text" id="isRegisterAgent" readonly="readonly" class="l_270" value="${bespeak.isRegisterAgent}"/>
			                            </td>
			                        </tr>
		                        </c:if>
		                        <c:if test="${bespeak.typeId == 3}">
			                       <tr height="52">
			                        	<td width="85">癌症类型：</td>
			                            <td width="340">
			                            	<select class="l_270" id="cancerTypes">
				                            	<c:forEach var="cancerTypes" items="${cancerTypes}">
				                            		<option value="${cancerTypes.id}" <c:if test="${cancerTypes.id == bespeak.cancerTypes}">selected = selected </c:if>>${cancerTypes.name}</option>
				                            	</c:forEach>
			                            	</select>
			                            </td>
			                        </tr>
		                        </c:if>
		                        <tr height="40">
		                        	<td width="85">预约时间：</td>
		                            <td width="340">
		                                <input readonly="readonly" value='<fmt:formatDate value="${bespeak.bespeakTime}" pattern="YYYY-MM-dd HH:mm:ss"/>' style="width: 268px;height: 38px;border: 1px solid #e4e4e3;"/>
		                            </td>
		                        </tr>
		                        <tr height="133" valign="bottom">
		                        	<td width="85" valign="top" style="line-height:80px;">患者病情：</td>
		                            <td width="340">
		                            	<textarea class="input18" readonly="readonly" id="baseCondition">${bespeak.baseCondition}</textarea>
		                            </td>
		                        </tr>
		                        <tr height="57" valign="bottom">
		                        	<td width="85">&nbsp;</td>
		                            <td width="340">
		                            	<button type="button" onclick="lh.back()" class="btn btn-success">返回</button>
		                            	<button type="button" onclick="updateBespeakRead(${bespeak.id});" class="btn btn-success">确认</button>
										<button type="button" onclick="updateBespeaNotRead(${bespeak.id});" class="btn btn-danger">驳回</button>
		                            </td>
		                        </tr>
		                    </table>
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
	<script type="text/javascript" src="/js/front/domain/doctor/bespeakDetil.js" title="v"></script>
</body>
</html>
