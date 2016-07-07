<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_back_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/back/back.css" title="v"/>
</head>

<body class="easyui-layout">
	<input type="hidden" value="${user.id}" id="userId">
    <div data-options="region:'north'" style="height:60px;">
    	<div class="header">
    		<span><img src="/images/front/main23.png" alt="logo" height="55" style="margin-left:60px;margin-top:2px;"/></span>
    		<div class="head_icons">
	    		<div id="head_logout" onclick="logout()" class="head_icon"><img src="/images/back/bg14.png" title="安全退出"/></div>
	    		<div id="head_refresh" onclick="refresh()" class="head_icon"><img src="/images/back/bg13.png" title="更新缓存"/></div>
	    		<div id="head_password" onclick="openUpdatePassword()" class="head_icon"><img src="/images/back/bg12.png" title="修改密码"/></div>
	    		<c:if test="${!empty admin.roleId && admin.roleId != 3}">
		    		<div id="head_message" onclick="showMain('消息通知管理','/back/notice')" class="head_icon"><img src="/images/back/bg09.png" title="站内短信"/></div>
	    		</c:if>
    			<div id="head_index" onclick="login()"  class="head_icon"><img src="/images/back/bg08.png" title="网站首页"/></div>
    		</div>
    	</div>
    </div>
    <!-- <div data-options="region:'south'" style="height:30px;">
    	<div class="footer">
    		<div id="foot_items" class="foot_items">快速通道==>：
    			<span class="foot_item" onclick="showMain('文章管理','/back/article')">文章管理</span>
    			<span class="foot_item" onclick="showMain('用户管理','/back/user')">用户管理</span>
    			<span class="foot_item" onclick="showMain('会员申请','/back/application')">会员申请</span>
    			<span class="foot_item" onclick="showMain('公告管理','/back/announcement')">公告管理</span>
    		</div>
    	</div>
    </div> -->
    <div data-options="region:'west',split:true,collapsible:true,title:'菜单'" style="width:200px;">
    	<div id="firstpane" class="menu_list">
    		<%-- 
    		<h3 class="menu_head">预约管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('预约','/back/bespeak')">预约</a>
				<!-- <a onclick="showMain('预约就诊','/back/forumArticle')">预约就诊</a> -->
				<!-- <a onclick="showMain('预约血液检测','/back/forumMember')">预约血液检测</a> -->
			</div>
			
			<h3 class="menu_head current">诊所管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('诊所','/back/hospital')">诊所</a>
				<a onclick="showMain('科室','/back/department')">科室</a>
				<a onclick="showMain('医生','/back/doctor')">医生</a>
				<a onclick="showMain('护士','/back/nurse')">护士</a>
				<a onclick="showMain('咨询','/back/consult')">咨询</a>
				<a onclick="showMain('短语','/back/phraseRecord')">短语</a>
				<a onclick="showMain('血液检测项目','/back/cancer')">血液检测项目</a>
			</div>
			
			<h3 class="menu_head">报告管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('血液检测报告','/back/patientReport')">血液检测报告</a>
			</div>
			<h3 class="menu_head">病历管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('病历','/back/diagnose')">病历</a>
				<a onclick="showMain('病历模板','/back/diagnoseTemplate')">病历模板</a>
			</div>
			
			<h3 class="menu_head">药物管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('药品类型','/back/medicationType')">药品类型</a>
				<a onclick="showMain('药品','/back/medication')">药品</a>
				<a onclick="showMain('药品库','/back/medicationRepertory')">药品库</a>
				<a onclick="showMain('药品记录','/back/medicationLog')">药品记录</a>
			</div>
			
			<h3 class="menu_head">资讯管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('新闻资讯','/back/article')">新闻资讯</a>
				<a onclick="showMain('评论','/back/comment')">评论</a>
				<a onclick="showMain('诊所公告','/back/announcement')">诊所公告</a>
				<a onclick="showMain('广告','/back/advertisement')">广告</a>
			</div>
			
			<h3 class="menu_head">用户管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('患者信息','/back/user')">患者信息</a>
				<!-- <a onclick="showMain('患者信息','/back/patient')">患者信息</a> -->
				<!-- <a onclick="showMain('用户控制','/back/userControl')">用户控制</a> -->
				<a onclick="showMain('我的圈子','/back/fans')">我的圈子</a>
				<a onclick="showMain('定期提醒','/back/regularRemind')">定期提醒</a>
			</div>
			
			<h3 class="menu_head">图片管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('图片','/back/picture')">图片</a>
			</div>
			
			<h3 class="menu_head">消息管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('用户消息','/back/notice')">用户消息</a>
				<a onclick="showMain('医生留言','/back/message')">医生留言</a>
				<!-- <a onclick="showMain('医生消息','/back/chat')">医生消息</a> -->
				<a onclick="showMain('站内信','/back/internalMessage')">站内信</a>
			</div>
			
			<h3 class="menu_head">数据管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('诊所数据管理','/back/dataHospital')">诊所数据管理</a>
			</div>
			
			<h3 class="menu_head">系统管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('友情链接','/back/addOrUpdateSysArticlePage?roleCode=link')">友情链接</a>
				<a onclick="showMain('数据字典','/back/dict')">数据字典</a><!--包括通知消息类型-->
				<!-- <a onclick="showMain('平台配置','/back/sysDict')">平台配置</a> --><!--包括云通讯，短信，快递，银行，支付宝，微信，QQ等账号-->
				<a onclick="showMain('登录日志','/back/loginLog')">登录日志</a><!--包括登陆，交易，后台操作，用户操作等-->
				<a onclick="showMain('用户角色','/back/admin')">用户角色</a><!--针对后台人员：管理员，财务等-->
				<a onclick="showMain('省市区','/back/provinceCityArea')">省市区</a><!--针对后台人员：管理员，财务等-->
			</div>
			
			<h3 class="menu_head">学生管理</h3>
			<div style="display:none" class="menu_body">
				<a onclick="showMain('学生','/back/addOrUpdateSysArticlePage?roleCode=link')">学生</a>
				
			</div>
			--%>
			
		</div>
    </div>
    <div data-options="region:'center'" id="main" class="easyui-tabs" style="padding:0px;background:#eee;">
    	<!--<div id="main" class="easyui-tabs" style="width:500px;height:250px;"></div>-->
    </div>

	<div style="display:none;" id="updatePasswordWin" title="修改密码">
        <div style="margin:8px 8px 8px 35px;">
       		<div id="updatePsdTip" style="color:red;"></div>
            	旧密码：<input type="text" id="oldPsd"></input><br/><br/>
            	新密码：<input type="text" id="newPsd1"></input><br/><br/>
            	新密码：<input type="text" id="newPsd2"></input><br/><br/>
           		<a id="submitUptPsd" onclick="updatePassword()" class="easyui-linkbutton">确认</a>&nbsp;
           		<a id="returnUptPsd" onclick="closeUpdatePwdWin()" class="easyui-linkbutton">返回</a>
       	</div>
	</div>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_back_js.htm"%>
	<script type="text/javascript" src="/js/back/base/main.js" title="v"></script>
	
</body>
</html>