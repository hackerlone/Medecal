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
	         	<div class="t_851_2">药物管理<button class="fr btn btn-success" onclick="location.href='/hospital/addOrUpdateMedication'">添加药品 +</button></div>
	        	<div class="t_851_3">
	            	<table cellpadding="0" cellspacing="0" border="0">
	                	<tr height="44" valign="middle">
	                        <td width="75">药品名称：</td>
	                        <td width="199"><input type="text" class="text_input2" id="name"/></td>
	                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
	                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="$('#name').val(null)" style="margin-left: 10px;"/></td>
<!-- 	                        <td width="309" align="right"><a href="/hospital/addOrUpdateMedication" class="l_109">添加药品 +</a></td> -->
	                    </tr>
	                </table>
	            </div>
	            <div class="d_851_1">
	            	<table cellpadding="0" cellspacing="0" border="0" width="849" >
	            		<tbody id="medicationList"></tbody>
	            		<tr height="53" align="center" style="color:#666666;display:none" id="noData">
							<td>暂无数据</td>
						</tr>
	                </table>
	            </div>
	            <div class="fy_new">
					<div id="page" class="inline-center"></div>
				</div>
	        </div>
	    </div>
	</div>
	<div class="pz_down">
	    <%@ include file="/views/front/common/bottom.htm"%><!-- 底部栏 -->
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/hospital/hospitalCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/hospital/medication.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">药品类型名称</td>
            <td width="70">药品名称</td>
            <td width="70">库存数量</td>
            <td width="70">警告数量</td>
			<td width="70">药品价格（元）</td>
            <td width="15">&nbsp;</td>
            <td width="100">操作</td>
        </tr>
		{{#rows}}
			<tr height="58" align="center" style="color:#666666">
		    	<td width="95">{{medicationTypeName}}</td>
		        <td width="70">{{medicationName}}</td>
		        <td width="70">{{remainNum}}</td>
		        <td width="70">{{warningNum}}</td>
 				<td width="70">{{price}}</td>
		        <td width="15">&nbsp;</td>
		        <td width="100">
		        	<button type="button" onclick="lh.confirm({content: '是否确定删除该药品？', clickYes:deleteMedication, clickYesParam:{{id}}});" class="btn btn-danger">删除</button>
					<button type="button" onclick="editMedication({{id}});" class="btn btn-success">编辑</button>
		        </td>
		    </tr>
		{{/rows}}
	</script>
</body>
</html>
