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
		<%@ include file="/views/front/common/doctor/doctorTop.htm"%> <!-- 顶部菜单栏 -->
	    <div class="w_1000">
			<%@ include file="/views/front/common/doctor/doctorLeft.htm"%><!-- 左边菜单栏 -->
	        <div class="w_851">
	        	<div class="c_851">
	            	<div class="t_851_2">病历模板 <button class="fr pointer btn btn-success" onclick="javascript:location.href='/addDiagnoseTemplate'">添加模板</button></div>
	            	<div class="t_851_3">
		            	<table cellpadding="0" cellspacing="0" border="0">
		                	<tr height="44" valign="middle">
		                        <td width="75">模板名称：</td>
		                        <td width="199"><input type="text" class="text_input2" id="name" /></td>
		                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
		                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="resetQuery()" style="margin-left: 10px;"/></td>
		                    </tr>
		                </table>
		            </div>
            		<div class="d_851_1">
		            	<table cellpadding="0" cellspacing="0" border="0" width="849">
		                	<tbody id="dataListContainer">
		                	</tbody>
		                </table>
		                <div class="fy_new">
							<div id="page" class="inline-center"></div>
						</div>
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
	<script type="text/javascript" src="/js/front/domain/doctor/diagnoseTemplateList.js" title="v"></script>
	
	<script id="template" type="x-tmpl-mustache"><!-- -->
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="300">模板名称</td>
			<td width="95">创建日期</td>
            <td width="15">&nbsp;</td>
            <td width="130">操作</td>
        </tr>
		{{#rows}}
		<tr height="58" align="center" style="color:#666666">
	    	<td width="300">{{templateName}}</td>
	        <td width="155">{{createDate}}</td>
	        <td width="15">&nbsp;</td>
	        <td width="150">
				<!--
				<button type="button" onclick="applyToReadDiagnose({{id}});" class="btn btn-info">申请授权查看</button>
				-->
				<button type="button" onclick="lh.confirm({content: '是否确定删除该病历模板？', clickYes:deleteDiagnoseTemplate, clickYesParam:{{id}}});" class="btn btn-danger">删除</button>
				<button type="button" onclick="editDiagnoseTemplate({{id}});" class="btn btn-success">编辑</button>
	        </td>
	    </tr>
		{{/rows}}		 		 
	</script>
</body>
</html>

