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
<script type="text/javascript">
var typeId = '${medication.medicationTypeId}';
var mainStatus = '${medication.mainStatus}';
</script>
<body>
	<%@ include file="/views/front/common/hospital/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/hospital/hospitalTop.htm" %>
	    <div class="w_1000">
	    	<%@ include file="/views/front/common/hospital/hospitalLeft.htm" %><!--左边导航 -->
	         <input type="hidden" id="medicationRepertoryId" value="${medication.id}">
            	<div class="t_851_2">药品记录信息-刚从addOrUpdateMedication复制过来，页面和JS未修改</div>
                <div class="l_849">
                	<table cellpadding="0" cellspacing="0" border="0" width="773">
                        <tr height="50" valign="bottom">
<!--                         	<td width="67" style="line-height:30px;">药品名称</td> -->
<%--                             <td width="310"><input type="text" class="input14" id="name" value="${medication.name}"/></td> --%>
<!--                         	<td width="80" style="line-height:30px;">药品英文名称</td> -->
<%--                             <td width="310"><input type="text" class="input14" id="englishName" value="${medication.englishName}"/></td> --%>
                            
                            <td width="67" style="line-height:30px;">药品类型</td>
                            <td width="310">
                            	<select class="input15" id="f_medicationTypeId">
									<option>请选择</option>
								</select>
                            </td>
                        	<td width="80" style="line-height:30px;">药品名称</td>
                            <td width="310">
								<select class="input15" id="f_medicationId">
									<option>请选择</option>
									<c:if test="${!empty medication.medicationId}">
										<option value="${medication.medicationId}" selected="selected">${medication.medicationName}</option>
									</c:if>
								</select>
							</td>
                        </tr>
                        <tr height="50" valign="bottom">
                        	<td width="67" style="line-height:30px;">库存数量</td>
                            <td width="310"><input type="number" class="input14" id="remainNum" value="${medication.remainNum}"/></td>
                        	<td width="67" style="line-height:30px;">警告数量</td>
                            <td width="310"><input type="number" class="input14" id="warningNum" value="${medication.warningNum}"/></td>
                        </tr>
                        <tr height="50" valign="bottom">
                        	<td width="67" style="line-height:30px;">药品价格</td>
                            <td width="310"><input type="number" class="input14" id="price" value="${medication.price}"/></td>
                            <td width="67" style="line-height:30px;">是否开启</td>
                            <td width="310">
								<select class="input15" id="f_mainStatus">
									<option>请选择</option>
									<option value="1">启用</option>
									<option value="2">停用</option>
								</select>
							</td>
                        </tr>
                        <tr height="50" valign="bottom">
                        	<td width="67" style="line-height:30px;font-weight:bolder;">只读信息</td>
                        </tr>
                        <tr height="50" valign="bottom">
                        	<td width="67" style="line-height:30px;color:gray;">单位</td>
                            <td width="310"><input type="number" class="input14" id="unit" readonly="readonly" value="${medication.unit}"/></td>
                        </tr>
                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;color:gray" valign="top">用法</td>
	                            <td colspan="3" width="706"><textarea style="width:662px;" readonly="readonly" id="usageAndDosage" class="text_input">${medication.usageAndDosage}</textarea></td>
	                    </tr>
	                    <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;color:gray" valign="top">禁忌</td>
	                            <td colspan="3" width="706"><textarea style="width:662px;" readonly="readonly" id="attention" class="text_input">${medication.attention}</textarea></td>
	                    </tr>
                       
<!--                         <tr height="50" valign="bottom"> -->
<!--                         	<td width="67" style="line-height:30px;">生厂商</td> -->
<%--                             <td width="310"><input type="text" class="input14" id="producer" value="${medication.producer}"/></td> --%>
<!--                         	<td width="67" style="line-height:30px;">生产商电话</td> -->
<%--                             <td width="310"><input type="text" class="input14" id="producerTel" value="${medication.producerTel}"/></td> --%>
<!--                         </tr> -->
<!--                         <tr height="50" valign="bottom"> -->
<!--                         	<td width="67" style="line-height:30px;">生产商邮编</td> -->
<%--                             <td width="310"><input type="text" class="input14" id="producerCode" value="${medication.producerCode}"/></td> --%>
<!--                         	<td width="67" style="line-height:30px;">生产地址</td> -->
<%--                             <td width="310"><input type="text" class="input14" id="produceAddress" value="${medication.produceAddress}"/></td> --%>
<!--                         </tr> -->
                        <tr height="92" valign="bottom">
                        	<td width="67">&nbsp;</td>
                            <td colspan="3" width="706">
                            	<input type="submit" class="sub_fil hover" value="完成" style="margin-left:165px;" onclick="addOrUpdateMedication()" />
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
	<script type="text/javascript" src="/js/front/domain/hospital/hospitalCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/hospital/addOrUpdateMedicationLog.js" title="v"></script>
</body>
</html>
