
/** 公用基本弹出框 */
lh.alert = function (param){
	if(!param)return;
	if(!_.isObject(param)){
		param = {content:param};
	}
	param.title = param.title || '提示';
	param.content = param.content || '操作成功';
	param.yes = param.yes || '确定';
	var dom = 
	'<div class="modal fade" id="R_DIALOG_ID" aria-labelledby="R_T2_ID" data-backdrop="static" role="dialog">'+
		'<div class="modal-dialog" role="document">'+
			'<div class="modal-content">'+
				'<div class="modal-header">'+
					'<button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
						'<span aria-hidden="true">&times;</span>'+
					'</button>'+
					'<h4 class="modal-title" id="R_T1_ID">R_TITLE</h4>'+
				'</div>'+
				'<div class="modal-body">R_CONTENT</div>'+
				'<div class="modal-footer inline-center">'+
					'<button type="button" id="R_Y_ID" class="btn btn-success" data-dismiss="modal">R_YES</button>'+
				'</div>'+
			'</div>'+
		'</div>'+
	'</div>';
	var dialogId = lh.createSerial();
	var titleId = lh.createSerial();
	var yesId = lh.createSerial();
	dom = dom.replace('R_DIALOG_ID', dialogId)
	.replace('R_T1_ID', titleId)
	.replace('R_T2_ID', titleId)
	.replace('R_Y_ID', yesId)
	.replace('R_TITLE', param.title)
	.replace('R_CONTENT', param.content)
	.replace('R_YES', param.yes);
	$('body').append(dom);
	$('#'+dialogId).modal('show');
	if(param.clickYes){
		$('#'+yesId).on("click", function(){
			param.clickYes();
		});
	}
	return dialogId;
}

lh.hideAlert = function frontBaseAlertClose(id){
	if(!id){
		$('.modal').modal('hide');
	}else{
		$('#'+id).modal('hide');
	}
}

/** 公用基本确认框 */
lh.confirm = function (param){
	if(!param)return;
	if(!_.isObject(param)){
		param = {content:param};
	}
	param.title = param.title || '提示';
	param.content = param.content || '操作成功';
	param.cancel = param.cancel || '取消';
	param.yes = param.yes || '确定';
	var dom = 
	'<div class="modal fade" id="R_DIALOG_ID" aria-labelledby="R_T2_ID" data-backdrop="static" role="dialog">'+
		'<div class="modal-dialog" role="document">'+
			'<div class="modal-content">'+
				'<div class="modal-header">'+
					'<button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
						'<span aria-hidden="true">&times;</span>'+
					'</button>'+
					'<h4 class="modal-title" id="R_T1_ID">R_TITLE</h4>'+
				'</div>'+
				'<div class="modal-body">R_CONTENT</div>'+
				'<div class="modal-footer inline-center">'+
					'<button type="button" id="R_C_ID" class="btn btn-info" data-dismiss="modal">R_CANCEL</button>'+
					'<button type="button" id="R_Y_ID" class="btn btn-success" data-dismiss="modal">R_YES</button>'+
				'</div>'+
			'</div>'+
		'</div>'+
	'</div>';
	var dialogId = lh.createSerial();
	var titleId = lh.createSerial();
	var yesId = lh.createSerial();
	var cancelId = lh.createSerial();
	dom = dom.replace('R_DIALOG_ID', dialogId)
	.replace('R_T1_ID', titleId)
	.replace('R_T2_ID', titleId)
	.replace('R_C_ID', cancelId)
	.replace('R_Y_ID', yesId)
	.replace('R_TITLE', param.title)
	.replace('R_CONTENT', param.content)
	.replace('R_CANCEL', param.cancel)
	.replace('R_YES', param.yes);
	$('body').append(dom);
	$('#'+dialogId).modal('show');
	if(param.clickYes){
		$('#'+yesId).on("click", function(){
			param.clickYes(param.clickYesParam);
		});
	}
	if(param.clickCancel){
		$('#'+cancelId).on("click", function(){
			param.clickCancel(param.clickCancelParam);
		});
	}
	return dialogId;
}

lh.hideConfirm = function (){
	if(!id){
		$('.modal').modal('hide');
	}else{
		$('#'+id).modal('hide');
	}
}

/** 打开公用基本遮罩框 */
var $singleMask = $('#singleMask');
lh.mask = function (content){
	if(!content)return;
	if($singleMask.length<=0){
		$('body').append('<div id="singleMask" style="position: fixed;z-index: 9999;top: 40%;color: white;padding: 10px 30px;'+
				'margin:0 50px;left:0; right:0;text-align:center;height:50px;line-height: 30px;'+
				'border-radius: 10px;background-color:black"></div>');
		$singleMask = $('#singleMask');
	}
	$singleMask.text(content);
	if($mask.length<=0){
		$('body').append('<div id="baseMaskDiv" class="mask">&nbsp;</div>');
		$mask = $('#baseMaskDiv');
	}
	$mask.show();
}

lh.hideMask = function (){
	$singleMask.hide();
}

var $toast = $('#toast');
lh.toast = function (content){
	if(!content)return;
	if($toast.length<=0){
		$('body').append(
				'<div id="toast" style="display: none;">'+
			        '<div class="weui_mask_transparent"></div>'+
			        '<div class="weui_toast">'+
			            '<i class="weui_icon_toast"></i>'+
			            '<p id="toast_content" class="weui_toast_content">'+content+'</p>'+
			        '</div>'+
			    '</div>'
		);
		$toast = $('#toast');
	}
	$('#toast_content').text(content);
	$toast.show();
}

/** 打开加载保存提示 */
var $frontLoading = $('#frontLoading');
lh.loading = function (content){
	if(!content)content = '正在保存数据，请稍候...';
	if($frontLoading.length<=0){
		$('body').append('<div id="frontLoading" style="position: fixed;z-index: 9999;top: 70%;color: white;padding: 10px 30px;' +
				'margin:0 50px;left:0; right:0;text-align:center;height:50px;line-height: 30px;'+
				'border-radius: 10px;background-color:black"><img style="width:20px;margin:0 4px 4px 0;" src="/images/front/loading.gif"/><span id="loadingText"></span></div>');
		$frontLoading = $('#frontLoading');
	}
	$frontLoading.show();
	$('#loadingText').text(content);
	if($mask.length<=0){
		$('body').append('<div id="baseMaskDiv" class="mask">&nbsp;</div>');
		$mask = $('#baseMaskDiv');
	}
	$mask.show();
}

lh.hideLoading = function (){
	$frontLoading.hide();
	$mask.hide();
}

lh.progress = function (param){

}

lh.hideProgress = function (param){

}

lh.tooltip = function (param){

}

lh.hideToolTip = function (param){

}

lh.closeImgView = function closeImgView(){
	$('#pre_closepic').hide();
	$('#imagePreview').css('height','0px');
}
lh.showImgView = function showImgView(index){
	if(!MY_SWIPER)return;
	if(!index)index = 0;
	MY_SWIPER.slideTo(index+1, 1, false);//切换到第一个slide，速度为1秒
	$('#imagePreview').css('height','100%');
	$('#pre_closepic').show();
	
}

lh.scanPic = function (picPathAry, currentIndx){

}

lh.choosePic = function (showPic){

}


lh.syncUploadPic = function(localIds, showPic){
}
