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
	        <div class="w_824">
				<div class="c_824" style="background-color: #FFFFFF;padding: 0 0 0 20px;">
		        	<div class="t_851_4">咨询记录</div>
					<div class="t_851_3">
		            	<table cellpadding="0" cellspacing="0" border="0">
		                	<tr height="44" valign="middle">
		                        <td width="75">标题：</td>
		                        <td width="199"><input type="text" class="text_input2" id="name" /></td>
		                        <td width="64"><input type="submit" class="sub_1" value="搜索" onclick="loadGridData()"/></td>
		                        <td width="64"><input type="submit" class="sub_1" value="重置" onclick="resetQuery()" style="margin-left: 10px;"/></td>
		                    </tr>
		                </table>
		            </div>
					 <div class="d_851_1">
					 	<input type="hidden" id="userId" value="${userId}"/>
		            	<table cellpadding="0" cellspacing="0" border="0" width="849">
		                	<tbody id="consultRecordList">
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
	<script type="text/javascript" src="/js/front/domain/user/consultRecord.js" title="v"></script>
	<script id="template" type="x-tmpl-mustache">
		<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">咨询医生</td>
            <td width="70">咨询标题</td>
            <td width="115">创建时间</td>
            <td width="115">咨询状态</td>
            <td width="15">&nbsp;</td>
            <td width="184">操作</td>
        </tr>
		{{#rows}}
			<tr height="53" align="center" style="font-size:14px; color:#63a13f">
        	<td width="95">{{doctorName}}</td>
            <td width="70">{{title}}</td>
            <td width="115">{{&createDate}}</td>
            <td width="115">
				{{#replyContent}}
					已回复
				{{/replyContent}}
				{{^replyContent}}
					未回复
				{{/replyContent}}
			</td>
			<td width="15">&nbsp;</td>
		        <td width="184">
						<button type="button" onclick="consultDetail({{id}});" class="btn btn-success">查看详情</button>
		        </td>
        </tr>
		{{/rows}}
	</script>
</body>
</html>

