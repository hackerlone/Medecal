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
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a></div>
	        <%@ include file="/views/front/common/user/userLeft.htm" %>
	        <div class="w_824">
				<div class="c_824" style="background-color: #FFFFFF;padding: 0 0 0 20px;">
				<div class="t_851_4">病历详情</div>
					<input type="hidden" id="diagnoseId" value="${diagnose.id}"/>
					<table cellpadding="0" cellspacing="0" border="0" width="773">
	                    	<tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333"><strong>用户信息</strong></td>
	                        </tr>
	                        <tr height="50" valign="bottom">
	                        	<td width="67" style="line-height:30px;">用户姓名</td>
	                            <td width="310"><input type="text" class="input14" readonly="readonly" value="${diagnose.patientName}"/></td>
	                            <td width="67" style="line-height:30px;">性　　别</td>
	                            <td width="310"><input type="text" class="input14" readonly="readonly" value="${diagnose.patientSex}"/></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">年　　龄</td>
	                            <td width="310"><input type="text" class="input14" readonly="readonly" value="${diagnose.patientAge}"/></td>
	                            <td width="67" style="line-height:30px;">出生日期</td>
	                            <td width="329"><input type="text" class="input14" readonly="readonly" value="${diagnose.patientBirthday}"/></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">身份证号</td>
	                            <td width="310"><input type="text" class="input14" readonly="readonly" value="${diagnose.patientIdcardNum}"/></td>
	                            <td width="67" style="line-height:30px;">电话号码</td>
	                            <td width="329"><input type="text" class="input14" readonly="readonly" value="${diagnose.patientPhone}"/></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">职　　业</td>
	                            <td width="329"><input type="text" class="input14" readonly="readonly" value="${diagnose.patientJobName}"/></td>
	                            <td width="67">&nbsp;</td>
	                            <td width="329">&nbsp;</td>
	                        </tr>
	                        <tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333"><strong>就诊情况</strong></td>
	                        </tr>
	                        <tr height="50" valign="bottom">
	                        	<td width="67" style="line-height:30px;">就诊科室</td>
	                            <td width="310"><input type="text" class="input14" readonly="readonly" value="${diagnose.departmentName}"/></td>
	                            <td width="67" style="line-height:30px;">就诊日期</td>
	                            <td width="329"><input type="text" class="input14" readonly="readonly"value="${diagnose.diagnoseTime}"/></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">过敏历史</td>
	                            <td colspan="3" width="706"><input style="width:662px;" type="text" class="input16" readonly="readonly" value="${diagnose.allergyHistory}"/></td>
	                        </tr>
	                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;" valign="top">基本病情</td>
	                            <td colspan="3" width="706"><textarea style="width:662px;" class="text_input" readonly="readonly">${diagnose.baseCondition}</textarea></td>
	                        </tr>
	                        <!-- <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">添加诊断</td>
	                            <td colspan="3" width="706" style="position:relative; z-index:1111;">
	                            <input type="text" class="input14" /> <a class="sub_but" href="javascript:;" onclick="$('#box1').fadeIn(0)">添加诊断</a>
	                            <div class="pf_268">
	                            	<ul>
	                                    <li></li>
	                                </ul>
	                            </div>
	                            </td>
	                        </tr> -->
	                        <tr id="box1" style="">
	                        	<td width="67">诊断结果&nbsp;</td>
	                            <td colspan="3" width="706">
	                            	<div class="play_down">
	                            		<c:forEach items="${diagnoseTagList}" var="tag">
	                            			<span>${tag.tagName}<!-- <a href="javascript:" class="pf_6"><img src="/images/front/er2.png" width="6" height="6"/> </a>--></span>
	                            		</c:forEach>
	                                </div>
	                            </td>
	                        </tr>
	                        <!-- <tr height="40" valign="bottom" id="bo_b6">
	                        	<td width="67" style="line-height:30px;">添加处方</td>
	                            <td colspan="3" width="706"><input type="text" class="input14" /> <a class="sub_but" href="javascript:;" onclick="$('#box2').fadeIn(0)">添加诊断</a></td>
	                        </tr> -->
	                        <!-- <tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333"><strong>开具处方</strong></td>
	                        </tr> -->
	                        <tr id="box2" style="">
	                        	<td width="67">处&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方</td>
	                            <td colspan="3" width="706">
	                            <div class="prescription">
	                            	<div class="t_706">
	                            		<c:forEach items="${prescriptionList}" var="pres">
		                                	<div class="l_60">药物类型：</div>
		                                    <input type="text" class="pre_text"readonly="readonly" value="${pres.medicalType}"/>
		                                    <div class="l_60">药物名称：</div>
		                                    <input type="text" class="pre_text"readonly="readonly" value="${pres.medicalName}"/>
		                                    <div class="l_60">药物数量：</div>
		                                    <input type="text" class="pre_text" readonly="readonly" value="${pres.medicalNum}"/>
	                                    </c:forEach>
	                                </div>
	                            </div>
	                            </td>
	                        </tr>
	                        <tr height="92" valign="bottom">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706" style="text-align:center;">
	                            	<button style="margin-right:80px;" type="button" onclick="lh.back();" class="btn btn-success">返回</button>
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
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/user/updateDiagnose.js" title="v"></script>
</body>
</html>
