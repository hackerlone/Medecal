UPLOADER_COLLECTION = {};
UPLOAD_PATH_OBJ = {};
FILE_PATH_STR = "";//将图片路径字符串保存在对象中
FILE_IDS_STR = "";//将图片ID串保存在对象中
UPLOAD_PARAM_OBJ = {};

/**--------------------- 弃用：请使用最新的common_upload.js -----------------------------------*/

/** 附件上传： 提交函数(若直接提交，就不需申明此方法) */
/**function file_upload_submit(){} */

/** 附件上传：事件 - 完成上传（有可能成功、失败） */
function file_upload_uploaded(uploader, file, response){
	var rsp = JSON.parse(response.response);
	var status = rsp.status;
	if (status && status == 'success') {
		var filePath = rsp.filePath;
		var fileDBId = rsp.fileDBId;
		file_upload_success(filePath,fileDBId,file.id);
	}else{
		file_upload_failure(rsp.msg);
	}
}

/** 附件上传：上传成功 */
function file_upload_success(filePath,fileDBId,fileId){
	var fileDom = $('#filePaths');
	var paths = fileDom.val();
	paths += ','+filePath;
	fileDom.val(paths);
	UPLOAD_PATH_OBJ[fileId] = filePath;
	FILE_PATH_STR = paths;
	
	var fileDBIdsDom = $('#fileDBIds');
	var fileDBIds = fileDBIdsDom.val();
	fileDBIds += ','+fileDBId;
	fileDBIdsDom.val(fileDBIds);
	FILE_IDS_STR = fileDBIds;
	//alert(paths);
}

/** 附件上传：上传失败 */
function file_upload_failure(msg){
	alert("上传出现错误："+msg);
}

/** 附件上传：事件 - 添加附件 */
function file_upload_added(uploader,files){
	var html = '';
	//if (uploader.files.length > 1)uploader.removeFile(uploader.files[0]);// 只能上传一张照片
	plupload.each(files, function(file) {
		/*if(file && file.size){
			html += '<li id="' + file.id + '">' + file.name + ' ('+ plupload.formatSize(file.size) + ') <b></b></li>';
		}else{
			html += '<li id="' + file.id + '">' + file.name + ' <b></b></li>';
		}*/
		html += 
		'<div class="progressWrapper" id="progressWrapper_' + file.id + '" style="opacity: 1;">'
			+'<div id="progressDiv_' + file.id + '" class="progressContainer green">'
				+'<span class="progressName">' + file.name + '</span>'
				+'<span id="progressUpload_' + file.id + '" class="progressBarStatus"><strong>正在上传..</strong></span>'
				/*+'<span id="progressCancel_' + file.id + '" class="progressBarStatus" style="display:none;">已取消上传</span>'*/
				+'<a id="cancelProgress_' + file.id + '" onclick="file_upload_cancel(\'' + file.id + '\');return false;" class="progressCancel" title="取消上传" href="#" style="visibility: visible;"> </a>'
				+'<div id="progressBar_' + file.id + '" class="progressBarInProgress" style="width: 0%;"></div>'
			+'</div>'
		+'</div>';
		var fileId = file.id;
		if(!UPLOADER_COLLECTION[fileId]){
			UPLOADER_COLLECTION[fileId] = uploader;
		}
		
	});
	$('#file_upload_progress').append(html);
	if(IS_AUTO_SUBMIT_FILE){//如果为自动提交，即选择了文件后立即执行上传操作
		$('#uploadFilePath').val('');//清空上传路径
		uploader.start();
	}
}

/** 附件上传：取消上传 */
function file_upload_cancel(fileId){
	var uploader = UPLOADER_COLLECTION[fileId];
	if(uploader)uploader.removeFile(uploader.getFile(fileId));
	$('#progressWrapper_'+fileId).remove();
	//清除对应附件的路径
	var filePath = UPLOAD_PATH_OBJ[fileId];
	if(filePath){
		var fileDom = $('#filePaths');
		var paths = fileDom.val();
		paths = paths.replace(','+filePath,'');
		fileDom.val(paths);
		FILE_PATH_STR = paths;
		delete UPLOAD_PATH_OBJ[fileId];
		
		var fileDBIdsDom = $('#fileDBIds');
		var fileDBIds = fileDBIdsDom.val();
		fileDBIds = fileDBIds.replace(','+fileDBId,'');
		fileDBIdsDom.val(ids);
		FILE_IDS_STR = fileDBIds;
	}
	
}

/** 附件上传：事件 - 上传进度变化 */
function file_upload_progress(uploader,file){
	if(file.percent == 100){
		$('#progressDiv_'+file.id).removeClass('green').removeClass('red').addClass('blue');
		$('#progressUpload_'+file.id+',#progressCancel_'+file.id+',#progressBar_'+file.id).css('display','none');
	}else{
		$('#progressBar_'+file.id).css('width',file.percent+"%");
	}
}

/** 附件上传：事件 - 上传失败 */
function file_upload_error(uploader,error){
	alert("上传出现错误："+error.code + ": " + error.message);
}

/** 附件上传：事件 - 重置上传组件 */
function resetUpload(){
	$('#file_upload_progress').empty();
	$('#filePaths').val();
	UPLOADER_COLLECTION = {};
	UPLOAD_PATH_OBJ = {};
	FILE_PATH_STR = "";//将图片路径字符串保存在对象中
	FILE_IDS_STR = "";
}


initUploadSimple('/upload',UPLOAD_PARAM_OBJ);

