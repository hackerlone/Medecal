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
	    	<div class="c_1100_10">当前位置：<a href="/">首页</a> - <a href="">我的中心</a></div>
	        <div class="w_229" style="padding-top:0;">
	        	<div class="t_229 pointer" onclick="location.href=''">我的中心</div>
	        	<div class="pz_SideLayer2">
		            	<li class="l4"><a href="javascript:;">疾病管理</a>
		                	<ul class="down_ul" style="display:block">
		                    <li class="l5 first"><a href="/consultDoctor">咨询医生</a></li>
		                    <li class="l5"><a href="javascript:;">病历</a></li>
		                    </ul>
		                </li>	
		                <li class="l4"><a href="javascript:;">医疗服务</a>
		                	<ul class="down_ul" style="display:block">
		                    	<li class="l5 first"><a href="javascript:;">我的医生</a></li>
		                        <li class="l5"><a href="javascript:;">远程诊疗</a></li>
		                        <li class="l5"><a href="javascript:;">陪诊</a></li>
		                        <li class="l5"><a href="javascript:;">基因检测</a></li>
		                    </ul>
		                </li>
		                <li class="l4"><a href="javascript:;">通知中心</a>
		                	<ul class="down_ul" style="display:block">
		                    	<li class="l5 first"><a href="/userNotice">站内信</a></li>
		                    </ul>
		                </li>
	            </div> 
	        </div>
	        <div class="w_851" style="padding-top:0;">
	        	<div class="t_851_4">意向详情</div>
	            <div class="text_box1">
	            	<div class="tit_773">网上咨询</div>
	                <div class="tit_773_1">用户基本资料</div>
	                <div class="text_773">
	                <font>姓名：</font>${user.username}<br />
	                <font>标题：</font>本人于3月20日在某某医院就诊<br />
	                <font>疾病：</font>肺结核<br />
	                <span id="hidden_div" style="display:none">
	                	<font>病情描述：</font>本人于3月20日在某某医院就诊，医生给我的结论是某某疾病，患病时间大概是20天左右，每天都有眩晕的感觉，头痛
	                    希望从医生那得到的帮助：希望医生能给我一些建议，长期的头痛困扰着我，工作和生活都收到了影响
	                </span>
	                </div>
	                <div class="cxq"><a href="javascript:;"><span id="_strSpan">展开详情＞</span></a></div>
	            </div>
	            <div class="text_box1">
	                <div class="tit_773_1" style="margin-top:5px;">诊断情况</div>
	                <div class="text_773">
	                <font>诊断科室：</font>内科<br />
	                <font>诊断日期：</font>2016年3月18日<br />
	                <font>过敏历史：</font>三年前有过青霉素过敏的情况<br />
	                <font>基本病情：</font>病人，男，39岁，于2012/7/9切除一个悬浮在大量腹水中的165×115×115mm的肿瘤，腹水中没有癌细胞，但医生说是粘液腺癌、恶
	性、广泛转移。但病人经过21天化疗后，精神很好，运动自如。请问下一步治疗：是仅仅用静脉注射（药物）还是同时加以腹腔灌注药物并热疗为
	好？手术中大夫曾说"无法刮干净"。<br />
					<font>诊断结果：</font>粘液腺癌<br />
	                <font>诊断处方：</font>如果可以选择静脉注射化疗药物+腹腔灌注化疗药物比较好。没有检查到癌细胞，不代表不存在。
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
	<script type="text/javascript" src="/js/front/base/user/user.js" title="v"></script>
</body>
</html>

