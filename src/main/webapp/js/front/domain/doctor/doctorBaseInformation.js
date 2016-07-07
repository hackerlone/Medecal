$(function(){
	initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			$("#userAvatar").attr('src', filePath);
	}});
	var cityId = $("#cityId").val();
	provinceChange(cityId);
});

function provinceChange(city){
	var provinceId = $("#province").val();
	lh.post("front", "/getCityArray", {provinceId:provinceId}, function(rsp){
		if(rsp){
			var dom = '<select id="city"" class="input8"><option value="">请选择</option>';
			$("#cityDiv").empty();
			for(var i = 0;i < rsp.length;i++){
				dom +=' <option';
				if(city && city == rsp[i].id)dom += ' selected="selected" ';
				dom += ' value="'+rsp[i].id+'">'+rsp[i].name+'</option>';
			}
			dom += '</select>';
			$("#cityDiv").append(dom);
		}
	}, "json")
}

function upDateDoctorInformation(){
	var doctorId = $("#doctorId").val();
	var username = $("#username").val();
	var realname = $("#realname").val();
	var idCard = $("#idCard").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var email = $("#email").val();
	var goodAt = $("#goodAt").val();
	var introduction = $("#introduction").val();
	var consultScope = $("#consultScope").val();
	var titleIds = $("#titleIds").val();
	var educationCode = $("#educationCode").val();
	var sex = $("#sex").val();
	var province = $("#province").val();
	var city = $("#city").val();
	var filePaths = $("#filePaths").val();
	if(!username){lh.alert('请填写医生昵称');return;}
	if(!realname){lh.alert('请填写医生真实姓名');return;}
	if(!idCard){lh.alert('请填写医生身份证号码');return;}
	if(!phone){lh.alert('请填写医生联系电话');return;}
	if(!address){lh.alert('请填写医生住址');return;}
	if(!goodAt){lh.alert('请填写擅长');return;}
	/*if(!email){lh.alert('请填写邮箱');return;}*/
	if(!introduction){lh.alert('请填写执业经历');return;}
	if(!consultScope){lh.alert('请填写咨询范围');return;}
	if(!titleIds){lh.alert('请选择职称');return;}
	if(!sex){lh.alert('请选择性别');return;}
	if(!province){lh.alert('请选择省份');return;}
	if(!city){lh.alert('请选择城市');return;}
	if(!educationCode){lh.alert('请选择学历');return;}
	//if(!filePaths){lh.alert('请上传头像');return;}
	var ids = UPLOAD_OBJ.idsStr;
	if(filePaths.substring(0,1) != "/"){
		filePaths = filePaths.substring(1);
		ids = ids.substring(1);
	}
	var obj = {id:doctorId,username:username,realname:realname,idCard:idCard,phone:phone,address:address,email:email,sex:sex,
			province:province,city:city,goodAt:goodAt,introduction:introduction,consultScope:consultScope,titleIds:titleIds,educationCode:educationCode};
	if(filePaths)obj.avatar = filePaths;
	if(ids)obj.avatarPicId = ids;
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post("front", "/updateDoctor", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/doctorHome');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}

var reset = function(operation){
	$("#reset").show();
	$("#baseInformation").hide();
	if(operation == 'cancel'){
		$("#reset").hide();
		$("#baseInformation").show();
	}
}

function upDateDoctorPassword(){
	var password = $("#password").val();
	if(!password){lh.alert('请输入新的密码');return;}
	var obj = {password:password};
	if(lh.preventRepeat()){//防止重复提交
		saveDoctorData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveDoctorData(obj){
	lh.post("front", "/updateDoctorPassword", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.alert({content:'修改密码成功,请重新登录',clickYes:goDoctorLogin});
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}

function goDoctorLogin(){
	lh.jumpToUrl('/doctorLogin');
}

