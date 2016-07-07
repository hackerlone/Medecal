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
<body>
	 <form class="margin-5-5-10-15">
    	<table class="padL5">
    		<tr class="tr_ht" align="right">
                    <td class="td_pad"><span style="float:left;">广告图片:</span></td>
                    <td style="top:10px;"  valign="middle">
	                   	<div style="display:inline-block;float:left;">
						 	<img  id="pic" class="picurl"  src="${pic.picPath}" style="height:60px;max-width:100px;overflow:hidden;padding:5px;"/>
						 	<input type="hidden" name="filePaths" id="filePaths" value="${pic.picPath}"/>
						 	<input type="hidden" name="fileDBIds" id="fileDBIds"/>
					 	</div>
					 	<button style="float:left;" id="browse" type="button" class="button button-primary button-rounded button-small" >选择图片</button>
                 	</td>
            </tr>
            <tr>
            	<td colspan="3" >
            		 <div id="upload_outer_div" style="margin-top:30px;"><!-- 上传文件进度展示 --></div>
            	</td>
            </tr>
    	</table>
    </form>
    <button class="button button-primary button-rounded button-small" onclick="lh.back()">返回</button>
    <%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_back_js.htm"%>
	<%@ include file="/views/common/common_upload_js.htm"%>
	<script type="text/javascript">
		$(function(){
			initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
				successFun:function(fileId, filePath){
					$("#upld_container_"+fileId).remove();
					$("#pic").attr('src', filePath);
			}});
		});
  	</script>
</body>
</html>