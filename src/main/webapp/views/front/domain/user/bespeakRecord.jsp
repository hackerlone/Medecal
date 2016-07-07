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
			<input type="hidden" id="userId" value="${user.id}">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a></div>
	        <%@ include file="/views/front/common/user/userLeft.htm" %>
	        <div class="c_824" style="background-color: #FFFFFF;padding: 0 0 0 20px;">
		        	<div class="t_851_4">我的预约</div>
					<div class="t_851_3">
		            	<table cellpadding="0" cellspacing="0" border="0">
		                	<tr height="44" valign="middle">
		                		<td width="46">类型：</td>
		                        <td width="118">
		                        	<select class="text_input1" id="typeId"><option value="">请选择</option>
		                        			<option value="1">远程诊疗</option>
		                        			<option value="2">陪诊</option>
		                        			<option value="3">血液检测</option>
		                        	</select>
		                        </td>
	                     		<!-- <td width="75">基本病情：</td>
		                        <td width="199"><input type="text" class="text_input2" id="baseCondition" /></td> -->
		                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
		                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="resetQuery()" style="margin-left: 10px;"/></td>
		                    </tr>
		                </table>
		            </div>
					 <div class="d_851_1">
		            	<table cellpadding="0" cellspacing="0" border="0" width="849">
		                	<tbody id="bespeakRecordList">
		                		<tr height="53" align="center" style="font-size:14px; color:#63a13f" id="noData">
		                			<td width="95">暂无数据</td>
		                		</tr>
				             </tbody>
		                </table>
		            </div>
		            <div class="fy_new">
						<div id="page" class="inline-center"></div>
					</div>
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
	<script type="text/javascript" src="/js/front/domain/user/bespeakRecord.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="155">患者名称</td>
            <td width="70">预约类型</td>
            <td width="70">预约时间</td>
            <td width="70">状态</td>
            <td width="15">&nbsp;</td>
            <td width="184">操作</td>
        </tr>
		{{#rows}}
			<tr height="58" align="center" style="color:#666666">
		        <td width="155">{{patientName}}</td>
		        <td width="70">{{&typeName}}</td>
		        <td width="70">{{&bespeakDate}}</td>
		        <td width="70">{{&mainStatusName}}</td>
		        <td width="15">&nbsp;</td>
		        <td width="184">
		        	<!--<button type="button" onclick="lh.confirm({content: '是否确定删除该预约？', clickYes:deleteBespeak, clickYesParam:{{id}}});" class="btn btn-danger">删除</button>-->
					<button type="button" onclick="bespeakRecordDetail({{id}});" class="btn btn-success">详情</button>
		        </td>
		    </tr>
		{{/rows}}
	</script>
</body>
</html>

