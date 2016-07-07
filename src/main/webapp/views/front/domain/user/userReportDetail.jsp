<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v" />
</head>
<body>
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm"%>
		<%@ include file="/views/front/common/menuTop.htm"%>
	</div>
	<div class="pz_main">
		<div class="w_1000">
			<div class="c_1100_10">
				当前位置：<a href="/">首页</a>
			</div>
			<%@ include file="/views/front/common/user/userLeft.htm"%>
			<div class="w_824">
				<div class="c_824" style="background-color: #FFFFFF; padding: 0 0 0 20px;">
					<div class="t_851_4">检测报告详情</div>
					<table cellpadding="0" cellspacing="0" border="0" width="773" id="baseInformation">
						<tr height="40" valign="bottom">
							<td><strong>基本信息</strong></td>
						</tr>
						<tr height="40" valign="bottom">
							<td width="67" style="line-height: 30px;">患者姓名</td>
							<td width="329"><input type="text" class="input14" readonly="readonly" value="${patientReport.patientName}" /></td>
							<td width="67" style="line-height: 30px;">条形码</td>
							<td width="329"><input type="text" class="input14" readonly="readonly" value="${patientReport.adiconBarcode}" /></td>
						</tr>
						<tr height="40" valign="bottom">
							<td width="67" style="line-height: 30px;">年龄</td>
							<td width="329"><input type="text" class="input14" readonly="readonly" value="${patientReport.age}${patientReport.ageType}" /></td>
							<td width="67" style="line-height: 30px;">性别</td>
							<td width="329"><input type="text" class="input14" readonly="readonly" value="${patientReport.sex}" /></td>
						</tr>
						<tr height="40" valign="bottom">
							<td width="67" style="line-height: 30px;">样本种类</td>
							<td width="329"><input type="text" class="input14" readonly="readonly" value="${patientReport.sampleType}" /></td>
							<td width="67" style="line-height: 30px;">标本形状</td>
							<td width="329"><input type="text" class="input14" readonly="readonly" value="${patientReport.sampleChar}" /></td>
						</tr>
						<tr height="40" valign="bottom">
							<td><strong>数据报告</strong></td>
						</tr>
					</table>
					<table cellpadding="0" cellspacing="0" border="0" width="773" id="baseInformation">
						<tr height="40" valign="bottom" style="border-bottom: 1px solid; font-weight: bolder;">
							<td width="80" style="line-height: 30px;">简称</td>
							<td width="120" style="line-height: 30px;">项目</td>
							<td width="80" style="line-height: 30px;">结果</td>
							<td width="80" style="line-height: 30px;">提示</td>
							<td width="80" style="line-height: 30px;">参考值</td>
							<td width="80" style="line-height: 30px;">单位</td>
						</tr>

						<c:forEach items="${prdList}" var="prd">
							<tr height="40" valign="bottom">
								<td width="80">${prd.itemName_EN}</td>
								<td width="120">${prd.itemName_CN}</td>
								<td width="80">${prd.result}</td>
								<td width="80"><c:if test="${prd.resultHint == 'z'}">正常</c:if> <c:if test="${prd.resultHint == 'd'}">低↓</c:if> <c:if
										test="${prd.resultHint == 'g'}">高↑</c:if></td>
								<td width="80">${prd.resultReference}</td>
								<td width="80">${prd.resultUnit}</td>
							</tr>
						</c:forEach>
					</table>
					<table cellpadding="0" cellspacing="0" border="0" width="773" id="baseInformation">
						<tr height="40" valign="bottom">
							<td><strong>其他信息</strong></td>
						</tr>
						<tr height="40" valign="bottom">
							<td width="67" style="line-height: 30px;">检测诊所</td>
							<td width="329"><input type="text" class="input14" style="width:660px;" readonly="readonly" value="${patientReport.hospitalName}" /></td>
						</tr>
						<tr height="10" valign="bottom">
						</tr>
						<tr height="40" valign="bottom">
							<td width="67" style="line-height: 30px;">检测结果</td>
							<td width="329"> 
								<textarea style="border: 1px solid #e4e4e3;width:660px;height:200px;" readonly="readonly">${patientReport.doctorResult}</textarea> 
							</td>
						</tr>

						<tr height="92" valign="bottom">
							<td width="67">&nbsp;</td>
							<td colspan="3" width="706" style="text-align: center;">
								<button style="margin-right: 80px;" type="button" onclick="lh.back();" class="btn btn-success">返回</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="pz_down">
		<%@ include file="/views/front/common/menuBottom.htm"%>
		<%@ include file="/views/front/common/bottom.htm"%>
	</div>
	<%@ include file="/views/front/common/nav.htm"%>

	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
</body>
</html>
