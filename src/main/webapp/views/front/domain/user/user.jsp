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
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a></div>
	        <%@ include file="/views/front/common/user/userLeft.htm" %>
	        <div class="w_851" style="padding-top:0;">
	        	<div class="t_851_4">意向详情</div>
	            <div class="text_box1">
	                <div class="tit_773_1">用户基本资料</div>
	                <div class="text_773">
	                <font>姓名：</font>${user.username}<br />
	                <font>标题：</font>
	                		<c:if test="${!empty diagnose}">
	                		本人于${diagnose.diagnoseTime}在${diagnose.hospitalName}就诊<br />
	                		</c:if>
	                <span id="hidden_div" style="display:none">
	                	<font>病情描述：</font>${diagnose.baseCondition}
	                </span>
	                </div>
	                <div class="cxq"><a href="javascript:;"><span id="_strSpan">展开详情＞</span></a></div>
	            </div>
	            <div class="text_box1">
	                <div class="tit_773_1" style="margin-top:5px;">诊断情况</div>
	                <div class="text_773">
	                <font>诊断科室：</font>${diagnose.departmentName}<br />
	                <font>诊断日期：</font>${diagnose.diagnoseTime}<br />
	                <font>过敏历史：</font>${diagnose.allergyHistory}<br />
	                <font>基本病情：</font>${diagnose.baseCondition}<br />
					<font>诊断结果：</font><span id="diagnoseTags"></span><br />
	                <font>诊断处方：</font><span id="prescription"></span>
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
	<script type="text/javascript">
		var TAGSJSON = '${diagnose.diagnoseTags}';
		if(TAGSJSON){
			var obj = JSON.parse(TAGSJSON);
			var diagnoseTags = '';
			for(var a=0 in obj){
				diagnoseTags += obj[a].tagName;	
			}
			$("#diagnoseTags").text(diagnoseTags);
		}
		var PRESCRIPTIONJSON = '${diagnose.prescription}';
		if(PRESCRIPTIONJSON){
			var obj = JSON.parse(PRESCRIPTIONJSON);
			var prescription = '';
			for(var a=0 in obj){
				prescription += '药品名称:'+obj[a].medicalName+'药品数量:'+obj[a].medicalNum;
			}
			$("#prescription").text(prescription);
		}
	</script>
	<script type="text/javascript" src="/js/front/domain/user/user.js" title="v"></script>
</body>
</html>

