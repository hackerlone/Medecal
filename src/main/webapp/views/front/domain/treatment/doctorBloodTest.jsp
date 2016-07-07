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
	<%@ include file="/views/front/common/doctor/top.htm"%>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm" %>
	    <div class="w_1000">
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        	<div class="c_851">
	            	<div class="t_851_2">血液检测</div>
                    <input type="hidden" id="patientId"/>
	                <div class="l_849">
	                	<table cellpadding="0" cellspacing="0" border="0" width="773">
	                    	<tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333">
	                        		<strong>患者信息</strong>
	                        		<span id="searchNoneTip" style="margin-left:10px;color:orange;display:none;">该身份证号和电话号码没有对应的患者，请补全患者信息</span>
	                        		<span id="searchSuccessTip" style="margin-left:10px;color:green;display:none;">已经自动填充患者信息</span>
	                        	</td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">条形码</td>
	                            <td width="310"><input type="text" class="input14" id="adiconBarcode"/></td>
	                        	<td width="67" style="line-height:30px;">身份证号</td>
	                            <td width="310"><input type="text" class="input14" id="idcardNum" onblur="searchPatient();"/></td>
	                        </tr>
	                        <tr height="50" valign="bottom">
	                        	<td width="67" style="line-height:30px;">患者姓名</td>
	                            <td width="310"><input type="text" class="input14" id="username"/></td>
	                            <td width="67" style="line-height:30px;">电话号码</td>
	                            <td width="329"><input type="text" class="input14" id="phone"/></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">年　　龄</td>
	                            <td width="310"><input type="text" class="input14" id="age"/></td>
	                            <td width="67" style="line-height:30px;">性　　别</td>
	                            <td width="329" style="line-height:30px;">
	                            	<input id="sex1" type="radio" name="sex" value="2" checked="checked"/> 男 &nbsp;&nbsp;&nbsp;
	                            	<input id="sex2" type="radio" name="sex" value="1"/> 女
	                            </td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">职　　业</td>
	                            <td width="310">
	                            	<select class="input15" id="jobSelect">
	                            	</select>
	                            </td>
	                            <td width="67" style="line-height:30px;">出生日期</td>
	                            <td width="329"><input type="text" class="input14" id="birthday"/></td>
	                            <!-- <td width="67">&nbsp;</td>
	                            <td width="329">&nbsp;</td> -->
	                        </tr>
	                        
	                        <tr height="54" valign="bottom" style="display:none;" id="diagnoseJump">
	                        	<td width="773" colspan="4" style=" line-height:36px; font-size:14px; color:#333333;text-align:center;">
	                        		<button type="button" class="btn btn-success" onclick="openToScanRecord();">该患者有病历记录，点击查看</button>
	                        	</td>
	                        </tr>
	                        
	                        <tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333"><strong>血液检测项</strong></td>
	                        </tr>
	                        <tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333">
	                        	<c:forEach var="cancer" items="${cancerList}" begin="0">
	                        		<div style="margin:0 5px;display:inline-block;;width:170px;"><input type="checkbox" name="cancer" id="cancer_${cancer.id}" onclick="selected(${cancer.id})" value="${cancer.code}"/>${cancer.name}</div>
	                        	</c:forEach>
	                        	</td>
	                        </tr>
	                        <tr height="92" valign="bottom">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706">
	                            	<button onclick="lh.back();" type="button" class="btn btn-default" style="float: left;margin-left: 150px;line-height: 27px;padding: 6px 50px;">取消</button>
	                            	<button onclick="savePatientReport();" type="button" class="btn btn-success" style="float: left;margin-left: 10px;line-height: 27px;padding: 6px 50px;">完成</button>
	                            	<!-- <button type="button" class="btn btn-success" style="float: left;margin:0px 20px;line-height: 27px;">完成并保存为模板</button> -->
	                                <!-- <input type="submit" class="sub_fil" value="导出" />
	                                <a href="javascript:;" class="cancel" style="font-size:14px; color:#333333; line-height:41px;">取消</a> -->
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
	<script type="text/javascript" src="/third-party/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="/third-party/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/diagnoseCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/doctorBloodTest.js" title="v"></script>
</body>
</html>

