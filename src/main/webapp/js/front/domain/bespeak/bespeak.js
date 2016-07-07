$(function(){
	initData();
	 $("#datetimepicker").datetimepicker({
	        format:'yyyy-mm-dd hh:ii:ss',
	        todayBtn:true,
	        language: 'zh-CN',
	        startView:2,
	        viewSelect:'year',
	        minView: 1, //选择日期后，不会再跳转去选择时分秒 
	        autoclose:true //选择日期后自动关闭 
	    });
});
function initData(){
	$(window).resize(function () { goTop(); });
    $(window).scroll(function () { goTop(); });
	jQuery(".menu").slide({ type: "menu", titCell: ".nli", targetCell: ".sub", effect: "slideDown", delayTime:200, triggerTime: 0,returnDefault: true });
}

function isRegisterAgentStatus(operation){
	if(operation == 'yes'){
		$("#isRegisterAgentYes")[0].checked = true;
		$("#isRegisterAgentNo")[0].checked = false;
	}else{
		$("#isRegisterAgentYes")[0].checked = false;
		$("#isRegisterAgentNo")[0].checked = true;
	}
}

function addBespeak(){
	var baseCondition = $("#baseCondition").val();
	var typeId = $("#typeId").val();
	var doctorId = $("#doctorId").val();
	var hospitalId = $("#hospitalId").val();
	var datetimepicker = $("#datetimepicker").val();
	var isRegisterAgent = $("#isRegisterAgentYes").val();
	if(typeId == 2){
		if(!isRegisterAgent){
			lh.alert('请选择上门服务项目'); return;
		}
		/*var isRegisterAgentYes = $("#isRegisterAgentYes")[0].checked;
		var isRegisterAgentNo = $("#isRegisterAgentNo")[0].checked ;
		if(isRegisterAgentYes){
			var isRegisterAgent = 1;
		}else if(isRegisterAgentNo){
			var isRegisterAgent = 2;
		}else{
			lh.alert('请选择是否代为挂号'); return;
		}*/
	}
	if(typeId == 3){
		var  cancerTypes = $("#cancerTypes").val(); 
	}
	if(!baseCondition){lh.alert("请填写基本病情");return;}
	var obj = {baseCondition:baseCondition,typeId:typeId,doctorId:doctorId,bespeakTime:datetimepicker,hospitalId:hospitalId};
	if(typeId == 2){
		obj.isRegisterAgent = isRegisterAgent;
	}
	if(typeId == 3){
		obj.cancerTypes = cancerTypes;
	}
	saveData(obj)
}

function saveData(obj){
	if(lh.preventRepeat()){//防止重复提交
		lh.post("front", "/addOrUpdateBespeak", obj, function(rsp){
			if(rsp.status == 'success'){
				lh.alert("您的预约已提交");
				location.reload();
			}else{
				lh.alert(rsp.msg);
			}
		}, "json");
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}