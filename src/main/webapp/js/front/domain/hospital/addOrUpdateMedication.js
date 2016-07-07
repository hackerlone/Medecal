$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	
	lh.post("front", "/hospital/getMedicationTypeArray", function(rsp){
		var optionHtml = '<option>请选择</option>';
		if(rsp.length>0){
			for(var i=0;i<rsp.length;i++){
				optionHtml+='<option value="'+rsp[i].id+'">'+rsp[i].name+'</option>';
			}
		}
		$('#f_medicationTypeId').html(optionHtml);
		if(typeId){
			$('#f_medicationTypeId').val(typeId);
		}
		if(mainStatus){
			$('#f_mainStatus').val(mainStatus);
		}
		
	});
});

$(document).on('change','#f_medicationTypeId',function(){
	lh.post("front", "/hospital/loadMedicationComboboxByTypeId",{typeId:$(this).val()}, function(rsp){
		var optionHtml = '<option>请选择</option>';
		if(rsp.medicationList.length>0){
			for(var i=0;i<rsp.medicationList.length;i++){
				optionHtml+='<option value="'+rsp.medicationList[i].id+'">'+rsp.medicationList[i].name+'</option>';
			}
		}
		$('#f_medicationId').html(optionHtml);
	});
});

$(document).on('change','#f_medicationId',function(){
	lh.post("front", "/hospital/loadAttentionAndUsageAndDosage",{id:$(this).val()}, function(rsp){
		var medication = rsp.medication;
		if(medication){
				$("#usageAndDosage").text(medication.usageAndDosage || '暂无');
				$("#attention").text(medication.attention || '暂无');
				$("#unit").text(medication.unit || '');
		}else{
			$("#usageAndDosage").text('暂无');
			$("#attention").text('暂无');
		}
	});
});

function addOrUpdateMedication(){
	if(lh.preventRepeat()){//防止重复提交
//		saveData();
		saveDataNew();
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

/*function saveData(){
	var medicationId = $("#medicationId").val();
	var warningNum = $("#warningNum").val();
	var remainNum = $("#remainNum").val();
	var englishName = $("#englishName").val();
	var name = $("#name").val();
	var producer = $("#producer").val();
	var produceAddress = $("#produceAddress").val();
	var producerTel = $("#producerTel").val();
	var producerCode = $("#producerCode").val();
	if(!name){lh.alert('请填写药品名称');return;}
	if(!englishName){lh.alert('请填写药品英文名称');return;}
	if(!warningNum){lh.alert('请填写告警数量');return;}
	if(!remainNum){lh.alert('请填写库存数量');return;}
	if(!producer){lh.alert('请填写生产商');return;}
	if(!producerTel){lh.alert('请填写生产商电话');return;}
	if(!producerCode){lh.alert('请填写生产商邮编');return;}
	if(!produceAddress){lh.alert('请填写生产地址');return;}
	var obj = {id:medicationId,name:name,englishName:englishName,warningNum:warningNum,remainNum:remainNum,
			producer:producer,producerTel:producerTel,producerCode:producerCode,produceAddress:produceAddress};
	lh.post("front", "/hospital/addOrUpdateMedicationRepertory", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/hospital/hospitalHome');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}*/

function saveDataNew(){
	var medicationTypeId = $("#f_medicationTypeId").val();//药品类型
	var medicationId = $("#f_medicationId").val();//药品名称
	var remainNum = $("#remainNum").val();//库存数量
	var warningNum = $("#warningNum").val();//预警数量
	var price = $("#price").val();//价格
	var mainStatus = $("#f_mainStatus").val();//是否开启 1 开启 2 停用
	var medicationRepertoryId = $("#medicationRepertoryId").val();
	
	var medicationTypeIdFlag = validatorNum('#f_medicationTypeId',/^[0-9]{1,10}$/);
	var medicationIdFlag = validatorNum('#f_medicationId',/^[0-9]{1,10}$/);
	
	var remainNumFlag = validatorNum('#remainNum',/^[0-9]{1,10}$/);
	var warningNumFlag = validatorNum('#warningNum',/^[0-9]{1,10}$/);
	var mainStatusFlag = validatorNum('#f_mainStatus',/^[0-9]{1,10}$/);
	
	
	if(!medicationTypeId){lh.alert('请选择药品类型');return;}
	if(!medicationIdFlag){lh.alert('请填写药品名称');return;}
	if(!remainNumFlag){lh.alert('请填写正确库存数量');return;}
	if(!warningNumFlag){lh.alert('请填写正确预警数量');return;}
	if(!price || price <= 0){lh.alert('请填写正确药品价格');return;}
	if(!mainStatusFlag){lh.alert('请选择是否开启');return;}
	var obj = {
				id:medicationRepertoryId,
				medicationTypeId:medicationTypeId,
				medicationId:medicationId,
				remainNum:remainNum,
				warningNum:warningNum,
				price:price,
				mainStatus:mainStatus
		};
	lh.post("front", "/hospital/addOrUpdateMedicationRepertory", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/hospital/medication');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}

//验证整数
var validatorNum  = function(obj,reg){
//	var reg = /^[0-9]{2,20}$/;
	var val = $(obj).val();
	if(val){
		if(reg.test(val)){  
//			obj.css({'border':'1px solid #c5c5c5'});
			return true;
		}else{
//			obj.css({'border':'1px solid red'});
			return false;
		}  
	}
}
