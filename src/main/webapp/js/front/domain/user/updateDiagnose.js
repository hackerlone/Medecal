$(function(){
	initData();
});
function initData(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
}

function updateDiagnose(){
	var diagnoseId = $("#diagnoseId").val();
	var patientName = $("#patientName").val();
	var patientSex = $("#patientSex").val();
	var patientAge = $("#patientAge").val();
	var patientPhone = $("#patientPhone").val();
	if(!patientName){lh.alert("请填写用户姓名");return;}
	if(!patientAge){lh.alert("请填写用户年龄");return;}
	if(!patientSex){lh.alert("请填写用户性别");return;}
	if(!patientPhone){lh.alert("请填写用户联系电话");return;}
	var obj = {id:diagnoseId,patientName:patientName,patientAge:patientAge,patientPhone:patientPhone,patientSex:patientSex};
	saveData(obj);
}

function saveData(obj){
	if(lh.preventRepeat()){//防止重复提交
		lh.post("front", "/updateDiagnose", obj, function(rsp){
			if(rsp.status == 'success'){
				lh.jumpToUrl('/userBaseInformation');
			}else{
				lh.alert(rsp.msg);
			}
		}, "json");
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}
