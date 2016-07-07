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
	         <div class="w_851">
	         	<input type="hidden" id="doctorId" value="${doctorId}"/>
	         	<div class="t_851_2">授权记录</div>
	         	<div class="t_851_3">
	            	<table cellpadding="0" cellspacing="0" border="0">
	                	<tr height="44" valign="middle">
	                        <td width="75">姓名：</td>
	                        <td width="199"><input type="text" class="text_input2" id="name" /></td>
	                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
	                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="resetQuery()" style="margin-left: 10px;"/></td>
	                    </tr>
	                </table>
	            </div>
	        	<div class="t_851_3">
	            	<table cellpadding="0" cellspacing="0" border="0" width="849">
	                	
	                </table>
	            </div>
	            <div class="d_851_1">
	            	<table cellpadding="0" cellspacing="0" border="0" width="849" id="diagnoseApplyList">
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
	<script type="text/javascript" src="/js/front/domain/doctor/doctorCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/doctor/diagnoseApply.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">诊所</td>
            <td width="70">病历所属医生</td>
            <td width="130">申请查看病历医生</td>
            <td width="155">是否授权</td>
            <td width="15">&nbsp;</td>
			<td width="184">操作</td>
        </tr>
		{{#rows}}
			<tr height="53" align="center" style="color:#666666">
		    	<td width="95">{{doctorName}}</td>
		        <td width="70">{{doctorName}}</td>
		        <td width="130">{{applyDoctorName}}</td>
		        <td width="155">{{logicStatusName}}</td>
		        <td width="15">&nbsp;</td>
				<td width="15">
					<button type="button" onclick="disAgree({{id}});" class="btn btn-danger">不同意</button>
					<button type="button" onclick="agree({{id}})" class="btn btn-success">同意</button>
				</td>
			</tr>
		{{/rows}}
	</script>
</body>
</html>
