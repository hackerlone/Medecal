<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v" />
<link rel="stylesheet" type="text/css" href="/third-party/pagination/paging.css"/>
</head>
<body>
	<div class="pz_top">
		<%@ include file="/views/front/common/top.htm"%>
		<%@ include file="/views/front/common/menuTop.htm"%>
	</div>

	<div class="pz_main">
		<div class="w_1000">
			<div class="c_1100_10">
				当前位置：<a href="/">首页</a> - <a href="">我要预约</a>
			</div>
			<div class="w_229" style="padding-top: 0;">
				<div class="l_262 pz_tab">
					<div class="t_262 hd">
						<ul>
							<li>按科室查看</li>
							<li>按医院查看</li>
						</ul>
					</div>
					<div class="t_262 hd">
	                	<span class="input8" style="float:left;width: 40px;border:none;padding-top: 10px;">省份:</span>
	                	<select id="province" class="input8" style="width: 80px;  margin-top: 3px;" onChange="provinceChange()">
	                		<option value="">全部</option>
		               		<c:forEach var="provinceCityArea" items="${provinceCityAreaList}">
		               			<option value="${provinceCityArea.id}" <c:if test="${user.province == provinceCityArea.id}">selected = selected</c:if>>
		               				${provinceCityArea.areaName}
		               			</option>
		               		</c:forEach>
	                	</select>
	                	<span style="float:left;padding-top: 10px;width: 40px;">县市:</span>
	                	<div id="cityDiv"><select id="city" class="input8" style="width: 80px;  margin-top: 3px;"></select></div>
	                </div>
					<div class="d_262 bd">
						<div class="one_262">
							<ul>
								<c:forEach items="${deptList}" var="dept">
		                    		<li><a href="javascript:">${dept.name}</a>
		                    			<ul class="sub_ny" style="display:block;">
		                    			<c:forEach items="${dept.subDeptList}" var="subDept">
				                    		<li><a href="javascript:" onclick="refreshDoctorList({departmentId:${subDept.id}});">${subDept.name}</a></li>
				                    	</c:forEach>
				                    	</ul>
		                    		</li>
		                    	</c:forEach>
								<!-- <li><a href="javascript:;">内科</a>
									<ul class="sub_ny">
										<li><a href="javascript:;">心血管内科</a></li>
										<li><a href="javascript:;">神经内科</a></li>
										<li><a href="javascript:;">消化内科</a></li>
										<li><a href="javascript:;">心血管内科</a></li>
										<li><a href="javascript:;">免疫科</a></li>
										<li><a href="javascript:;">呼吸科</a></li>
										<li><a href="javascript:;">肾病内科</a></li>
										<li><a href="javascript:;">血液科</a></li>
										<li><a href="javascript:;">感染内科</a></li>
										<li><a href="javascript:;">过敏反应科</a></li>
										<li><a href="javascript:;">老年病科</a></li>
										<li><a href="javascript:;">普通内科</a></li>
									</ul></li>
								<li><a href="javascript:;">妇科</a></li>
								<li><a href="javascript:;">外科</a></li>
								<li><a href="javascript:;">儿科</a></li>
								<li><a href="javascript:;">心脏</a></li>
								<li><a href="javascript:;">肿瘤</a></li>
								<li><a href="javascript:;">其他</a></li> -->
							</ul>
						</div>
						<div class="one_262">
							<ul>
	                    		<li><%-- <a href="javascript:">${hospital.briefName}</a> --%>
	                    			<ul id="hospitalUl" class="sub_ny" style="display:block;">
	                    			<c:forEach items="${hospitalList}" var="hospital">
			                    		<li><a href="javascript:" onclick="refreshDoctorList({hospitalId:${hospital.id}});">${hospital.briefName}</a></li>
			                    	</c:forEach>
			                    	</ul>
	                    		</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="r_819">
				<div class="t_819">可咨询大夫</div>
				<div class="d_819">
					<div class="tit_819">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr height="41" style="color: #999999; font-size: 14px;">
								<td width="19">&nbsp;</td>
								<td width="186">头像</td>
								<td width="186">大夫</td>
								<td width="233">咨询范围</td>
								<td width="152">回复情况</td>
								<td width="124">最新在线</td>
								<td width="105">联系大夫</td>
							</tr>
						</table>
					</div>
					<div class="list_text">
						<ul id="doctorListUl">
							<!-- <li>
								<table cellpadding="0" cellspacing="0" border="0">
									<tr height="110">
										<td width="19">&nbsp;</td>
										<td width="186">
											<div class="l_71">
												<img src="/images/front/er8.jpg" width="71" height="71" />
											</div>
											<div class="r_100">
												<span>张建中</span>
												<p style="margin-top: 5px;">
													主任医师<br />教授
												</p>
											</div>
										</td>
										<td width="233">
											<div class="l_184">
												支气管哮喘症，慢性阻塞性肺病，慢性科所，慢性支气管炎，睡眠呼吸暂停综合征，飞溅质纤维化；难治性哮喘的诊治...<a href="javascript:;">更多 >></a>
											</div>
										</td>
										<td width="152">
											<div class="l_150">
												<p>
													近2周回复咨询<span>36</span>问
												</p>
												<p>
													总共回复<span>1252</span>问
												</p>
											</div>
										</td>
										<td width="124">
											<div class="l_120_1">10小时前在线</div>
										</td>
										<td width="105">
											<div class="l_87">
												<a href="javascript:;">在线咨询 &nbsp;<img src="/images/front/er9.png" width="14" height="14" /></a> <a href="javascript:;" style="margin-top: 10px;">我要预约 &nbsp;<img
													src="/images/front/er10.png" width="14" height="14" /></a>
											</div>
										</td>
									</tr>
								</table>
							</li> -->
						</ul>
						<div class="fy_new">
							<div id="page" class="inline-center"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="pz_down">
		<%@ include file="/views/front/common/menuBottom.htm"%>
		<%@ include file="/views/front/common/bottom.htm"%>
	</div>
	<%@ include file="/views/front/common/nav.htm"%>
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/js/front/domain/user/userCommon.js" title="v"></script>
	<script type="text/javascript" src="/js/front/domain/bespeak/choose.js" title="v"></script>

	<script id="template" type="x-tmpl-mustache">
		{{#rows}}
		<li>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr height="110">
					<td width="19">&nbsp;</td>
					<td width="186" class="pointer" onclick="location.href='/doctor/{{id}}'">
							 <div class="l_71" ><img src="{{avatar}}" width="71" height="71" onerror="this.src='/images/front/main13.png'"/></div>
					</td>
                    <td width="186">
                        <div class="r_100"><span class="pointer" onclick="location.href='/doctor/{{id}}'">{{username}}</span><p style=" margin-top:5px;">{{titleNames}}</p></div>
                    </td>
					<td width="233">
						<div class="l_184">
							{{consultScope}}
						</div>
					</td>
					<td width="152">
						<div class="l_150">
							<p>总共回复<span>{{consultCount}}</span>问</p>
						</div>
					</td>
					<td width="124">
						<div class="l_120_1">{{&lastLoginDate}}</div>
					</td>
					<td width="105">
						<div class="l_87">
							<a href="/consult?doctorId={{id}}&hospitalId={{hospitalId}}">在线咨询 &nbsp;<img src="/images/front/er9.png" width="14" height="14" /></a> 
							<a href="/userBespeak/${typeId}/{{id}}/{{hospitalId}}" style="margin-top: 10px;">我要预约 &nbsp;<img src="/images/front/er10.png" width="14" height="14" /></a>
						</div>
					</td>
				</tr>
			</table>
		</li>
		{{/rows}}		 		 
	</script>
	
</body>
</html>

