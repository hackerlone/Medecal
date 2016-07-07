$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
});


function addOrUpdateBespeak(){
	if(lh.preventRepeat()){//防止重复提交
		saveData();
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(){
	var bespeakId = $("#bespeakId").val();
	var baseCondition = $("#baseCondition").val();
	var cancerTypes = $("#cancerTypes").val();
	var isRegisterAgent = $("#isRegisterAgent").val();
	if(!baseCondition){lh.alert('请填写基本病情');return;}
	if(!cancerTypes){lh.alert('请填写筛查癌症类型串');return;}
	if(!isRegisterAgent){lh.alert('请选择是否代为挂号');return;}
	var obj = {id:bespeakId,isRegisterAgent:isRegisterAgent,cancerTypes:cancerTypes,baseCondition:baseCondition};
	lh.post("front", "/hospital/addOrUpdateBespeakForHospital", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/hospital/hospitalBespeak');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}
