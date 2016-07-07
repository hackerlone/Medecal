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
	         <div class="t_851_2">医生库</div>
	        	<div class="t_851_3">
	            	<table cellpadding="0" cellspacing="0" border="0">
	                	<tr height="44" valign="middle">
	                    	<td width="46">区域：</td>
	                        <td width="118">
	                        	<select class="text_input1" id="province"><option value="">请选择</option>
	                        		<c:forEach var="provinceCityArea" items="${provinceCityAreaList}">
	                        			<option value="${provinceCityArea.id}">${provinceCityArea.areaName}</option>
	                        		</c:forEach>
	                        	</select>
	                        </td>
	                        <td width="75">医生姓名：</td>
	                        <td width="199"><input type="text" class="text_input2" id="username" /></td>
	                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
	                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="resetQuery()" style="margin-left: 10px;"/></td>
	                        <td width="269" align="right"><a href="/hospital/hospitalDoctorAddOrUpdate" class="l_109">添加医生 +</a></td>
	                    </tr>
	                </table>
	            </div>
	            <div class="d_851_1">
	            	<table cellpadding="0" cellspacing="0" border="0" width="849">
	                	<tbody id="dataListContainer">
			             </tbody>
	                </table>
	            </div>
	            <div class="d_851_1">
	            	<table cellpadding="0" cellspacing="0" border="0" width="849" id="doctorList">
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
	<script type="text/javascript" src="/js/front/domain/hospital/doctor.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">头像</td>
        	<td width="95">姓名</td>
            <td width="70">性别</td>
            <td width="130">地区</td>
            <td width="155">联系电话</td>
            <td width="15">&nbsp;</td>
            <td width="184">操作</td>
        </tr>
		{{#rows}}
			<tr height="58" align="center" style="color:#666666">
		    	<td width="95"><img src="{{avatar}}"width="98" height="98" onerror="this.src='/images/front/main13.jpg'"/></td>
		    	<td width="95">{{username}}</td>
		        <td width="70">{{sexName}}</td>
		        <td width="130">{{provinceName}}&nbsp;{{cityName}}</td>
		        <td width="155">{{phone}}</td>
		        <td width="15">&nbsp;</td>
		        <td width="184">
		        	<button type="button" onclick="lh.confirm({content: '是否确定删除该医生？', clickYes:deleteDoctor, clickYesParam:{{id}}});" class="btn btn-danger">删除</button>
					<button type="button" onclick="editDoctor({{id}});" class="btn btn-success">编辑</button>
		        </td>
		    </tr>
		{{/rows}}
	</script>
</body>
</html>
