
$(function(){
	var cityId = $("#cityId").val();
	provinceChange(cityId);
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
});
function provinceChange(cityId){
	var provinceId = $("#province").val();
	lh.post("front", "/getCityArray", {provinceId:provinceId}, function(rsp){
		if(rsp){
			var dom = '<select id="city"" class="input8"><option value="">请选择</option>';
			$("#cityDiv").empty();
			for(var i = 0;i < rsp.length;i++){
				dom +=' <option';
				if(cityId && cityId == rsp[i].id)dom += ' selected="selected" ';
				dom += ' value="'+rsp[i].id+'">'+rsp[i].name+'</option>';
			}
			dom += '</select>';
			$("#cityDiv").append(dom);
		}
	}, "json")
}


function upDateUser(){
	var id = $("#id").val();
	if(id){
		upDateUserDo();return;
	}
	var idcardNum = $('#idcardNum').val();
	if(!idcardNum || idcardNum < 5){
		lh.alert('该正确的身份证号码');return;
	}
	lh.post('front','/searchRelationPatient',{idcardNum:idcardNum},function(rsp){
		if(rsp.success){
			if(rsp.user){
				lh.alert('该身份证号码已经存在，请重新输入');return;
			}else{
				upDateUserDo();
			}
		}
	});
}

function upDateUserDo(){
	var id = $("#id").val();
	var username = $("#username").val();
	var realName = $("#realName").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var idcardNum = $("#idcardNum").val();
	if($('#sex1')[0].checked){
		var sex = $('#sex1').val();
	}
	if($('#sex2')[0].checked){
		var sex = $('#sex2').val();
	}
	var job = $("#job").val();
	var email = $("#email").val();
	var birthday = $("#birthday").val();
	var province = $("#province").val();
	var userRelation = $("#userRelation").val();
	var city = $("#city").val();
	if(!username || !realName || !phone  || !idcardNum || !sex || !birthday || !province || !city){
		lh.alert('请填写用户昵称,用户真实姓名,用户联系电话,身份证号码,选择性别,选择职业,填写出生日期,选择省份,选择城市');return;
	}
	if(!userRelation){
		lh.alert('请填写与该用户的关系');
		return;
	}
	birthday = birthday+" 00:00:00";
	var obj = {id:id,username:username,realName:realName,phone:phone,address:address,idcardNum:idcardNum,
			jobId:job,email:email,birthday:birthday,province:province,city:city,userRelation:userRelation};
	if(sex)obj.sex = sex;
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post("front", "/addOrUpdateRelationPatient", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.back();
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}