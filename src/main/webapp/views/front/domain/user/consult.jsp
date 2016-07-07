<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
<link rel="stylesheet" type="text/css" href="/css/front/user/zxnr.css" title="v"/>
</head>
<style>
	h2,h3,p	{
		margin:0px;
	}
</style>
<body>
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm" %>
		<%@ include file="/views/front/common/menuTop.htm" %>
	</div>
	<div class="pz_main">
		<div class="w_1000">
	    	<div class="c_1100_10" style="float: none;">当前位置：<a href="/">首页</a> - <a href="/consultDoctor">咨询内容</a></div>
	        <form>
	        	<input type="hidden" id="doctorId" value="${doctorId}">
	        	<input type="hidden" id="hospitalId" value="${hospitalId}">
	            <div class="zxnr">
	                <p class="h1">咨询内容</p>
	                <hr style="width:800px;margin:0 auto;color:#eee; "/>
	                <div class="lhnr">
	                    <h2>请确认本次咨询的疾病问题：</h2>
	                    <input type="text" id="titleName" class="inp1"/><div class="spa1"><span>*必填</span></div>
	                    <div class="fr"><p class="pgray">请填写疾病名称，2-20个字，如：糖尿病合并下肢动脉栓塞。</p></div>
	                    <p class="smp">（如不知道疾病名称，可填写主要症状，如：饭后胃部阵痛，腹胀。）</p>
	                    <h3 class="h3_1">其他必要说明及希望医生提供的帮助：</h3>
	                    <p class="smp">（此处填写的内容是公开的，请勿填写姓名、电话等隐私信息！）</p>
	                    <div class="box2">
	                        <div class="txt2">病情主诉（如发病时间，主要症状等）:</div><span>*必填</span>
	                        <textarea id="mainContent"></textarea>
	                        <div class="txt3">
	                            <p>
	                            <img src="/images/front/jg.png" alt="例子"/>例子说明:<br/>2011年12月份出现头痛，视物模糊。 当地就诊后医生诊断高血压，血压160/100。开了药，一直服用到现在，有改善!现偶有头痛及视物模糊的情况发生，血压控制不理想，一般晨起血压140/98，睡前血压130/80。
	                            </p>
	                        </div>
	                        <p class="pbt">填写要求：简明、扼要（字数限制20-1000个字）。</p>
	                    </div>
	                    <div class="box3">
	                        <div class="txt2">希望医生提供的帮助:</div>
	                        <span>*必填</span>
	                        <textarea id="helpContent"></textarea>
	                        <div class="txt5">
	                            <p>
	                            <img src="/images/front/jg.png" alt="例子"/>例子说明:<br/>高血压是否会引起头痛、视物模糊? 还有其他原因吗？医生说我的血压不好控制，是否还有别的好办法？ 能去找您就诊吗？
	                            </p>
	                        </div>
	                        <div class="txt4">
	                            <p class="pbt" >填写说明<br/>1、字数限制10-1000个字，不建议超过三个要求，可根据医生回复情况再次追问！2、真实的大医院高年资医生，如需要可向医生联系就诊事宜！</p>
	                        </div>
	                    </div>
	                    <div class="bt1">
	                        <input type="button" value="完成并保存" onclick="addConsult()"/>
	                    </div>
	                </div>
	            </div>
	        </form>
			<div class="h_20">&nbsp;</div>
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
	<script type="text/javascript" src="/js/front/domain/user/consult.js" title="v"></script>
</body>
</html>

