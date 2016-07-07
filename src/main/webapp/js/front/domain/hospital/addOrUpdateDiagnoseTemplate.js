$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
});

function adddiagnoseTemplate(diagnoseTemplateId){
	var templateName = $('#templateName').val();
	if(!templateName){
		lh.alert('请输入模板名称');return;
	}
	var obj = getBaseDiagnoseFromDom();
	obj.id = diagnoseTemplateId;
	obj.templateName = templateName;
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post("front", "/hospital/addOrUpdateDiagnoseTemplateForHospital", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/hospital/diagnoseTemplate');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}
