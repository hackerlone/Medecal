$(function(){
	initData();
});
function initData(){
	$(window).resize(function () { goTop(); });
    $(window).scroll(function () { goTop(); });
	jQuery(".menu").slide({ type: "menu", titCell: ".nli", targetCell: ".sub", effect: "slideDown", delayTime:200, triggerTime: 0,returnDefault: true });
}

function addConsult(){
	var titleName = $("#titleName").val();
	var mainContent = $("#mainContent").val();
	var helpContent = $("#helpContent").val();
	var doctorId = $("#doctorId").val();
	var hospitalId = $("#hospitalId").val();
	if(!titleName){
		lh.alert('请填写咨询的疾病问题');
		return;
	}else{
		if(titleName.length < 2 || titleName.length > 20){
			lh.alert("填写的疾病问题字数在2-20之间");
			return;
		}
	}
	if(!mainContent){
		lh.alert('请填写病情简要情况');
		return;
	}else{
		if(mainContent.length < 20 || mainContent.length > 1000){
			lh.alert("填写病情简要情况的字数在20-1000之间");
			return;
		}
	}
	if(!helpContent){
		lh.alert('请填写希望医生能提供的帮助');
		return;
	}else{
		if(helpContent.length < 10 || helpContent.length > 1000){
			lh.alert("填写希望医生能提供的帮助字数在10-1000之间");
			return;
		}
	}
	var obj = {title:titleName,mainContent:mainContent,helpContent:helpContent};
	if(doctorId){
		obj.doctorId = doctorId;
	}
	if(hospitalId){
		obj.hospitalId = hospitalId;
	}
	saveData(obj);
}

function saveData(obj){
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	lh.post('front', '/addOrUpdateConsult', obj,function(rsp){
		if(rsp.success){
			var param = {content:'添加成功',clickYes:goToUserHome};
			lh.alert(param);
		}else{
			lh.alert(rsp.msg);
		}
	},'json');
}

function goToUserHome(){
	lh.jumpToUrl('/userBaseInformation');
}