<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
</head>
<body>
	<%@ include file="/views/front/common/doctor/top.htm"  %>
	<div class="pz_main">
		<%@ include file="/views/front/common/doctor/doctorTop.htm" %> <!-- 顶部菜单栏 -->
	    <div class="w_1000">
	    	<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        	<div class="c_851">
	            	<div class="t_851_2">病历夹</div>
	                <div class="l_849">
	                	<table cellpadding="0" cellspacing="0" border="0" width="773">
	                    	<tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333"><strong>患者信息</strong></td>
	                        </tr>
	                        <tr height="50" valign="bottom">
	                        	<td width="67" style="line-height:30px;">患者姓名</td>
	                            <td width="310"><input type="text" class="input14" /></td>
	                            <td width="67" style="line-height:30px;">性　　别</td>
	                            <td width="329" style="line-height:30px;"><input type="radio" name="sex" checked="checked"/> 男 &nbsp;&nbsp;&nbsp;<input type="radio" name="sex" /> 女</td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">年　　龄</td>
	                            <td width="310"><input type="text" class="input14" /></td>
	                            <td width="67" style="line-height:30px;">出生日期</td>
	                            <td width="329"><input type="text" class="input14" /></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">身份证号</td>
	                            <td width="310"><input type="text" class="input14" /></td>
	                            <td width="67" style="line-height:30px;">电话号码</td>
	                            <td width="329"><input type="text" class="input14" /></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">职　　业</td>
	                            <td width="310"><select class="input15"><option></option></select></td>
	                            <td width="67">&nbsp;</td>
	                            <td width="329">&nbsp;</td>
	                        </tr>
	                        <tr height="54" valign="bottom">
	                        	<td width="773" colspan="4" style="border-bottom:1px solid #f2f2f2; line-height:36px; font-size:14px; color:#333333"><strong>就诊情况</strong></td>
	                        </tr>
	                        <tr height="50" valign="bottom">
	                        	<td width="67" style="line-height:30px;">就诊科室</td>
	                            <td width="310"><select class="input15"><option></option></select></td>
	                            <td width="67" style="line-height:30px;">就诊日期</td>
	                            <td width="329"><input type="text" class="input14" /></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">过敏历史</td>
	                            <td colspan="3" width="706"><input type="text" class="input16" /></td>
	                        </tr>
	                        <tr height="93" valign="bottom">
	                        	<td width="67" style="line-height:50px;" valign="top">基本病情</td>
	                            <td colspan="3" width="706"><textarea class="text_input"></textarea></td>
	                        </tr>
	                        <tr height="40" valign="bottom">
	                        	<td width="67" style="line-height:30px;">添加诊断</td>
	                            <td colspan="3" width="706" style="position:relative; z-index:1111;">
	                            <input type="text" class="input14" /> <a class="sub_but" href="javascript:;" onclick="$('#box1').fadeIn(0)">添加诊断</a>
	                            <div class="pf_268">
	                            	<ul>
	                                    <li></li>
	                                </ul>
	                            </div>
	                            </td>
	                        </tr>
	                        <tr id="box1" style="display:none;">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706">
	                            	<div class="play_down">
	                                	<span>无果 <a href="javascript:;" class="pf_6"><img src="/images/front/er2.png" width="6" height="6" /></a></span>
	                                    <span>无解 <a href="javascript:;" class="pf_6"><img src="/images/front/er2.png" width="6" height="6" /></a></span>
	                                </div>
	                            </td>
	                        </tr>
	                        <tr height="40" valign="bottom" id="bo_b6">
	                        	<td width="67" style="line-height:30px;">添加处方</td>
	                            <td colspan="3" width="706"><input type="text" class="input14" /> <a class="sub_but" href="javascript:;" onclick="$('#box2').fadeIn(0)">添加诊断</a></td>
	                        </tr>
	                        <tr id="box2" style="display:none">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706">
	                            <div class="prescription">
	                            	<div class="t_706">
	                                	<div class="l_60">药物类型：</div>
	                                    <select class="pre_sec"><option></option></select>
	                                    <div class="l_60">药物名称：</div>
	                                    <input type="text" class="pre_text" />
	                                    <div class="l_60">药物名称：</div>
	                                    <input type="text" class="pre_text" />
	                                </div>
	                                <div class="t_706">
	                                	<div class="l_60">药物类型：</div>
	                                    <select class="pre_sec"><option></option></select>
	                                    <div class="l_60">药物名称：</div>
	                                    <input type="text" class="pre_text" />
	                                    <div class="l_60">药物名称：</div>
	                                    <input type="text" class="pre_text" />
	                                    <input type="submit" class="pre_sub" value="保存" />
	                                    <a  href="javascript:;" onclick="$('#box2').fadeOut() ,$('#bo_b6').fadeIn(0)" class="cancel">取消</a>
	                                </div>
	                                <div class="add"><a href="javascript:;"><img src="/images/front/er4.jpg" width="15" height="15" /> &nbsp;增加一条</a></div>
	                            </div>
	                            </td>
	                        </tr>
	                        <tr height="92" valign="bottom">
	                        	<td width="67">&nbsp;</td>
	                            <td colspan="3" width="706">
	                            	<input type="submit" class="sub_fil hover" value="完成" style="margin-left:165px;" />
	                                <input type="submit" class="sub_fil" value="导出" />
	                                <a href="javascript:;" class="cancel" style="font-size:14px; color:#333333; line-height:41px;">取消</a>
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
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/diagnose.js" title="v"></script>
</body>
</html>

