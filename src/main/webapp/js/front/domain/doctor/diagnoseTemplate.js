$(function(){
});

/** 更新病历模板 */
function saveDiagnoseTemplate(diagnoseTemplateId){
	//if(!diagnoseTemplateId)return;
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	var templateName = $('#templateName').val();
	if(!templateName){
		lh.alert('请输入模板名称');return;
	}
	var obj = getBaseDiagnoseFromDom();
	obj.diagnoseTemplateId = diagnoseTemplateId;
	obj.templateName = templateName;
	if(null != diagnoseTemplateId){
		lh.post('front', '/updateDiagnoseTemplate', obj,function(rsp){
			if(rsp.success){
				//lh.alert('您已经成功更新该病历');
				lh.back();
			}else{
				lh.alert(rsp.msg);
			}
		});
	}else{
		lh.post('front', '/addDiagnoseTemplate', obj,function(rsp){
			if(rsp.success){
				//lh.alert('您已经成功更新该病历');
				lh.back();
			}else{
				lh.alert(rsp.msg);
			}
		});
	}
}


