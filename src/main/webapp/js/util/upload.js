/**
 * 2015年7月21日15:12:34 虞荣华
 * Plupload上传组件，公共方法（自动提交）
 * remark:
 * 1.自动提交时会自动清空上传路径，$('#uploadFilePath').val('');//清空上传路径
 */
 
/**--------------------- 弃用：请使用最新的common_upload.js -----------------------------------*/

IS_AUTO_SUBMIT_FILE = false;//是否自动提交的标识


function initUploadSimple(url,params) {
	initUpload(url,null,params,null,null,null);
}

/**
 * 2015年7月21日15:13:14 虞荣华
 * Plupload上传组件，公共方法（手动提交）
 * @param {提交地址} url
 * @param {文件名} fileName
 * @param {参数} params
 * @param {选择文件的按钮DomID} chooseBtnDomId
 * @param {显示文件列表的DomID} filelist
 * @param {提交上传的按钮DomID,如果为自动提交,则传null} submitBtnDomId
 */
function initUpload(url,fileName,params,chooseBtnDomId,filelist,submitBtnDomId) {
	var date = (new Date()).valueOf();
	//var random = "";
	//for(var i=0;i<2;i++){random += Math.floor(Math.random()*10);}//2位随机数
	var random = Math.floor(Math.random()*10);//1位随机数就够了，日期本来就很难重复
	random = date + random;//添加一个随机数，传到后端防止文件名重复
	if(params){
		params.random = random;
	}else{
		params = {random:random};
	}
	if(!submitBtnDomId)IS_AUTO_SUBMIT_FILE = true;//如果没有传递提交文件的按钮ID，则视为自动提交，更新自动提交标识
	if(!chooseBtnDomId)chooseBtnDomId = 'browse';
	if(!filelist)filelist = 'filelist';
	var uploader = new plupload.Uploader({
				browse_button : chooseBtnDomId,
				url : url,
				//file_data_name : fileName,
				file_data_name : 'multipartFile',
				//unique_names:true,
				multi_selection : true,
				runtimes : 'html5,flash,silverlight,html4',
				flash_swf_url : '/third-party/plupload/Moxie.swf',
				silverlight_xap_url : '/third-party/plupload/Moxie.xap',
				chunk_size : "512kb",
				max_retries : 2,
				filters : {
					prevent_duplicates : false
				},
				multipart_params:params//额外参数
			});
	uploader.init();
	upload_events(uploader,submitBtnDomId);//绑定事件：文件添加，上传进度，错误，成功上传
}

/**
 * 绑定上传组件的事件：文件添加，上传进度，错误，成功上传
 * @param {基本上传对象} uploader
 * @param {手动提交的DomID,如果为自动提交则为null} submitBtnDomId
 * @param {上传成功的回调函数} successFun
 * @param {上传失败的回调函数} failureFun
 * @param {手动提交对应的提交函数,如果没有则直接执行上传} submitFun
 */
function upload_events(uploader,submitBtnDomId){
	uploader.bind('FilesAdded', file_upload_added);
	uploader.bind('UploadProgress', file_upload_progress);
	uploader.bind('Error', file_upload_error);
	uploader.bind('FileUploaded', file_upload_uploaded);
	if(submitBtnDomId){
		document.getElementById(submitBtnDomId).onclick = function() {
			if(file_upload_submit){
				file_upload_submit(uploader);//执行指定的提交函数
			}else{
				uploader.start();//直接开始上传
			}
		};
	}
}

/**
 * @param {基本上传对象} uploader
 * @param {文件对象} file
 */
function filesAdded(uploader,files){
	var html = '';
	if (uploader.files.length > 1)uploader.removeFile(uploader.files[0]);// 只能上传一张照片
		plupload.each(files, function(file) {
			if(file && file.size){
				html += '<li id="' + file.id + '">' + file.name + ' ('+ plupload.formatSize(file.size) + ') <b></b></li>';
			}else{
				html += '<li id="' + file.id + '">' + file.name + ' <b></b></li>';
			}
		});
	if(filelist.id){
		document.getElementById(''+filelist.id+'').innerHTML = html;
	}else{
		document.getElementById(''+filelist+'').innerHTML = html;
	}
	if(IS_AUTO_SUBMIT_FILE){//如果为自动提交，即选择了文件后立即执行上传操作
		$('#uploadFilePath').val('');//清空上传路径
		uploader.start();
	}
}

/**
 * @param {基本上传对象} uploader
 * @param {文件对象} file
 */
function uploadProgress(uploader,file){
	if(file.percent == 100){
		document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span style="color:green;padding-right:10px;">'
			+ file.percent + "%</span>";
	}else{
		document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span style="color:red">'+ file.percent + "%</span>";
	}
}

/**
 * @param {基本上传对象} uploader
 * @param {错误对象} error
 */
function uploadError(uploader,error){
	alert(error.code + ": " + error.message);
}

/**
 * 弃用
 * @param {基本上传对象} uploader
 * @param {文件} file
 * @param {返回数据} response
 * @param {上传成功的回调函数} successFun
 * @param {上传失败的回调函数} failureFun
 */
function fileUploaded(uploader, file, response, successFun, failureFun){
	var rsp = response.response;
	rsp = JSON.parse(rsp);
	var status = rsp.status;
	if (status && status == 'success') {
		var filePath = rsp.filePath;
		successFun(filePath);
	}else{
		failureFun();
	}
}

