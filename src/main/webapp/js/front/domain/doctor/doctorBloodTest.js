
$(function(){
	getDictArray();
	getDiagnoseTemplate();
	
	$('#birthday').datetimepicker({
	    format: 'yyyy-mm-dd',
	    language: 'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});
	$('#diagnoseTime').datetimepicker({
	    format: 'yyyy-mm-dd',
	    language: 'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});
});

function getDictArray(){
	lh.post('front', '/getDictArray', {parentCode:'job'}, function(rsp){
		var array = rsp;
		var dom = '<option>请选择</option>';
		for(i = 0;i<array.length;i++){
			var obj = array[i];
			dom += '<option value="'+obj.id+'">'+obj.name+'</option>';
		}
		$('#jobSelect').html(dom);
	});
}

function getDiagnoseTemplate(){
	lh.post('front', '/getDiagnoseTemplateList', {}, function(rsp){
		if(rsp.success){
			var array = rsp.rows;
			lh.data.diagnoseTemplateArray = array;
			var dom = '<option>请选择</option>';
			for(i = 0;i<array.length;i++){
				var obj = array[i];
				dom += '<option value="'+obj.id+'">'+obj.templateName+'</option>';
			}
			$('#templateSelect').html(dom);
		}
	});
}


function searchPatient(){
	var idcardNum = $('#idcardNum').val();
	if(!idcardNum || idcardNum < 5)return;
	lh.post('front','/searchPatient',{idCardNum:idcardNum},function(rsp){
		if(rsp.success){
			var patient = rsp.patient;
			if(!patient){
				$('#searchNoneTip').show();
				$('#searchSuccessTip').hide();
				$('#diagnoseJump').hide();
				return;
			}
			$('#searchNoneTip').hide();
			$('#searchSuccessTip').show();
			var diagnoseCount = rsp.diagnoseCount;
			
			var phone = $('#phone').val();
			var username = $('#username').val();
			var sex1 = $('#sex1').val();
			var sex2 = $('#sex2').val();
			var age = $('#age').val();
			var birthday = $('#birthday').val();
			var jobSelect = $('#jobSelect').val();
			$('#patientId').val(patient.id);
			
			if(!phone)$('#phone').val(patient.phone);
			if(!username)$('#username').val(patient.username);
			if(!sex1 && !sex2){
				if(patient.sex == 1){
					$('#sex1').prop('checked',true);
					$('#sex2').prop('checked',false);
				}else if(patient.sex == 2){
					$('#sex1').prop('checked',false);
					$('#sex2').prop('checked',true);
				}
			}
			if(!age)$('#age').val(patient.age);
			if(!birthday)$('#birthday').val(patient.birthday);
			$('#jobSelect').val(patient.job);
			
			if(diagnoseCount){
				$('#diagnoseJump').show();
				lh.data.patientIdcardNum = idcardNum;
			}else{
				$('#diagnoseJump').hide();
			}
		}
	});
}

function chooseTemplate(){
	var templateId = $('#templateSelect').val();
	if(!templateId)return;
	templateId = parseInt(templateId);
	var template = _.find(lh.data.diagnoseTemplateArray, { id: templateId});
	var allergyHistory = $('#allergyHistory').val(template.allergyHistory);
	var baseCondition = $('#baseCondition').val(template.baseCondition);
	//剩下两个 
	
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

function selected(id){
	$("#cancer_"+id).attr('checked','checked');
}

/** 新增病历 */
function savePatientReport(){
	var typeIds = "";
	var str=document.getElementsByName("cancer");
	for(i=0;i<str.length;i++){
		if(str[i].checked == true){
			typeIds += ","+str[i].value; 
		}
	}
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	var username = $('#username').val();
	if(!username){
		lh.alert('请填写用户姓名');return;
	}
	var adiconBarcode = $('#adiconBarcode').val();//用户出生日期
	if(!adiconBarcode){
		lh.alert('请填写条形码');return;
	}
	
	var idcardNumFlag = validatorNum('#idcardNum',/^[0-9]{2,20}$/);
	var phoneFlag = validatorNum('#phone',/^[0-9]{2,11}$/);
	var ageFlag = validatorNum('#age',/^[0-9]{1,3}$/);
	var errorContent = '';
	if(!idcardNumFlag){
		errorContent+='请输入正确身份证,';
	}
	if(!phoneFlag){
		errorContent+='请输入正确电话号码,';
	}
	if(!ageFlag){
		errorContent+='请输入正确年龄,';
	}
	if(errorContent!=''){
		lh.alert(errorContent);return;
	}
	//var obj = getBaseDiagnoseFromDom();
	var obj = {};
	var phone = $('#phone').val();
	var sex1 = $('#sex1').val();
	var sex2 = $('#sex2').val();
	var age = $('#age').val();
	var patientId = $('#patientId').val();
	//var birthday = $('#birthday').val();
	//var diagnoseTime = $('#diagnoseTime').val();
	//var patientIdcardNum = $('#idcardNum').val();//身份证号码
	//var patientBirthday = $('#birthday').val();//用户出生日期
	obj.patientName = username;
	obj.itemCodes = typeIds.substring(1);
	if(phone)obj.patientPhone = phone;
	if(adiconBarcode)obj.adiconBarcode = adiconBarcode;
	if(sex1)obj.sex = sex1;
	if(sex2)obj.sex = sex2;
	if(age)obj.age = age;
	if(patientId)obj.patientId = patientId;
	//if(birthday)obj.birthday = birthday;
	//if(patientIdcardNum)obj.patientIdcardNum = patientIdcardNum;
	//if(patientBirthday)obj.patientBirthday = patientBirthday;
	lh.post('front', '/doctor/addPatientReport', obj, function(rsp){
		if(rsp.success){
			lh.alert({content:'您已经成功添加血液检测项目',clickYes:lh.back});
		}else{
			lh.alert(rsp.msg);
		}
	});
}




