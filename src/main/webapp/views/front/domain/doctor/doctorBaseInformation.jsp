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
	<%@ include file="/views/front/common/doctor/top.htm"%>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm"%>
		<!-- 顶部菜单栏 -->
		<div class="w_1000">
			<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
			<div class="w_851">
				<div class="c_851">
					<div class="t_851_2">
						医生基本信息
						<button class="fr btn btn-success" onclick="reset();">重置密码</button>
					</div>
					<div class="l_849">
						<input type="hidden" id="doctorId" value="${doctor.id}" /> <input type="hidden" id="cityId" value="${doctor.city}">
						<table cellpadding="0" cellspacing="0" border="0" width="773" id="baseInformation">
							<tr height="54" valign="bottom">
								<td width="773" colspan="4" style="border-bottom: 1px solid #f2f2f2; line-height: 36px; font-size: 14px; color: #333333"><strong>医生信息</strong></td>
							</tr>
							<tr height="50" valign="bottom">
								<td width="67" style="line-height: 30px;">所属诊所</td>
								<td width="310"><input type="text" class="input14" readonly="readonly" value="${doctor.hospitalName}" /></td>
								<td width="67" style="line-height: 30px;">所属科室</td>
								<td width="310"><input type="text" class="input14" readonly="readonly" value="${doctor.departmentName}" /></td>
							</tr>
							<tr height="50" valign="bottom">
								<td width="67" style="line-height: 30px;">医生昵称</td>
								<td width="310"><input type="text" class="input14" id="username" value="${doctor.username}" /></td>
								<td width="67" style="line-height: 30px;">真实姓名</td>
								<td width="310"><input type="text" class="input14" id="realname" value="${doctor.realname}" /></td>
							</tr>
							<tr height="40" valign="bottom">
								<td width="67" style="line-height: 30px;">身份证号</td>
								<td width="310"><input type="text" class="input14" id="idCard" value="${doctor.idCard}" /></td>
								<td width="67" style="line-height: 30px;">电话号码</td>
								<td width="329"><input type="text" class="input14" id="phone" value="${doctor.phone}" /></td>
							</tr>
							<tr height="40" valign="bottom">
								<td width="67" style="line-height: 30px;">地址</td>
								<td width="310"><input type="text" class="input14" id="address" value="${doctor.address}" /></td>
								<td width="67" style="line-height: 30px;">邮箱</td>
								<td width="310"><input type="text" class="input14" id="email" value="${doctor.email}" /></td>
							</tr>
							<tr height="93" valign="bottom">
								<td width="67" style="line-height: 50px;" valign="top">擅长</td>
								<td colspan="3" width="706"><textarea style="width: 662px;" id="goodAt" class="text_input">${doctor.goodAt}</textarea></td>
							</tr>
							<tr height="93" valign="bottom">
								<td width="67" style="line-height: 50px;" valign="top">执业经历</td>
								<td colspan="3" width="706"><textarea style="width: 662px;" id="introduction" class="text_input">${doctor.introduction}</textarea></td>
							</tr>
							<tr height="93" valign="bottom">
								<td width="67" style="line-height: 50px;" valign="top">咨询范围</td>
								<td colspan="3" width="706"><textarea style="width: 662px;" id="consultScope" class="text_input">${doctor.consultScope}</textarea></td>
							</tr>
							<tr height="48" valign="bottom">
								<td width="88" style="color: #333333; line-height: 33px;">所在省份</td>
								<td colspan="1" width="299"><select class="input8" id="province" onChange="provinceChange()">
										<c:forEach var="province" items="${provinceCityAreaList}">
											<option value="${province.id}" <c:if test="${province.id == doctor.province}">selected="selected"</c:if>>${province.areaName}</option>
										</c:forEach>
								</select></td>
								<td width="88" style="color: #333333; line-height: 33px;">所在城市</td>
								<td colspan="1" width="299" id="cityDiv"></td>
							</tr>
							<tr height="48" valign="bottom">
								<td width="88" style="color: #333333; line-height: 33px;">职称</td>
								<td colspan="1" width="299"><select class="input8" id="titleIds">
										<c:forEach var="titleIds" items="${titleIdsList}">
											<option value="${titleIds.id}" <c:if test="${titleIds.id == doctor.titleIds}">selected="selected"</c:if>>${titleIds.codeName}</option>
										</c:forEach>
								</select></td>
								<td width="88" style="color: #333333; line-height: 33px;">学历</td>
								<td colspan="1" width="299"><select class="input8" id="educationCode">
										<option value="">请选择</option>
										<c:forEach var="education" items="${educationList}">
											<option value="${education.code}" <c:if test="${education.code == doctor.educationCode}">selected="selected"</c:if>>${education.codeName}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr height="50" valign="bottom">
								<td width="67" style="line-height: 30px;">性别</td>
								<td width="310"><select id="sex" style="height: 30px; width: 50px;">
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
												<option value="1">男</option>
												<option value="2" selected="selected">女</option>
											</c:if>
										</c:if>
								</select></td>
								<td width="67" style="line-height: 30px;">医生头像</td>

								<td style="top: 10px;">
									<div style="display: inline-block; float: left;">
										<img id="userAvatar" class="picurl" src="${doctor.avatar}" style="height: 60px; max-width: 100px; overflow: hidden;" /> <input type="hidden"
											name="filePaths" id="filePaths" value="${doctor.avatar}" /> <input type="hidden" name="fileDBIds" id="fileDBIds" />
									</div>
									<button id="browse" type="button" class="sub_fil hover" style="top: 10px;">选择图片</button>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="upload_outer_div" style="margin: 20px 50px 0px 50px;">
										<!-- 上传文件进度展示 -->
									</div>
								</td>
							</tr>
							<tr height="92" valign="bottom">
								<td width="67">&nbsp;</td>
								<td colspan="3" width="706" style="text-align: center;">
									<button style="margin-right: 80px;" type="button" onclick="upDateDoctorInformation();" class="btn btn-success">保存</button>
									<button style="margin-right: 80px;" type="button" onclick="lh.back();" class="btn btn-success">返回</button>
								</td>
							</tr>
						</table>
						<table cellpadding="0" cellspacing="0" border="0" width="773" style="display: none;" id="reset">
							<tr height="40" valign="bottom">
								<td width="67" style="line-height: 30px;">新密码</td>
								<td width="329"><input type="password" class="input14" id="password" /></td>
							</tr>
							<tr height="92" valign="bottom">
								<td width="67">&nbsp;</td>
								<td colspan="3" width="706" style="text-align: center;">
									<button style="margin-right: 80px;" type="button" onclick="upDateDoctorPassword();" class="btn btn-success">保存</button>
									<button style="margin-right: 80px;" type="button" onclick="reset('cancel');" class="btn btn-success">取消</button>
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
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorBaseInformation.js" title="v"></script>
</body>
</html>

