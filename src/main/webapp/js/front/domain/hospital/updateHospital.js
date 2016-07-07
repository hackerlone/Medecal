$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			$("#hospitalLogo").attr('src', filePath);
	}});
	var cityId = $("#cityId").val();
	provinceChange(cityId);
});

function provinceChange(city){
	var provinceId = $("#province").val();
	lh.post("front", "/getCityArray", {provinceId:provinceId}, function(rsp){
		if(rsp){
			var dom = '<select id="city"" class="input8" style="width: 268px;"><option value="">请选择</option>';
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

function updateHospital(){
	if(lh.preventRepeat()){//防止重复提交
		saveData();
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(){
	var hospitalId = $("#hospitalId").val();
	var briefName = $("#briefName").val();
	var wholeName = $("#wholeName").val();
	var address = $("#address").val();
	var phone = $("#phone").val();
	var province = $("#province").val();
	var city = $("#city").val();
	var qq = $("#qq").val();
	var email = $("#email").val();
	var weibo = $("#weibo").val();
	var weixin = $("#weixin").val();
	var introduction = $("#introduction").val();
	var tel = $("#tel").val();
	var bloodTest = $("#bloodTest").val();
	var filePaths = $("#filePaths").val();
	if(!briefName){lh.alert('请填写诊所简称');return;}
	if(!wholeName){lh.alert('请填写诊所全称');return;}
	if(!address){lh.alert('请填写诊所地址');return;}
	if(!phone){lh.alert('请填写联系电话');return;}
	if(!tel){lh.alert('请填写座机号码');return;}
	if(!bloodTest){lh.alert('请选择能否进行血液检测');return;}
	//if(!qq){lh.alert('请填写QQ');return;}
	//if(!weibo){lh.alert('请填写微博');return;}
	//if(!weixin){lh.alert('请填写微信');return;}
	/*if(!email){lh.alert('请填写邮箱');return;}*/
	if(!province){lh.alert('请选择省份');return;}
	if(!city){lh.alert('请选择城市');return;}
	if(!introduction){lh.alert('请填写诊所简介');return;}
	//if(!filePaths){lh.alert('请上传诊所LOGO');return;}
	var ids = UPLOAD_OBJ.idsStr;
	if(filePaths.substring(0,1) != "/"){
		filePaths = filePaths.substring(1);
		ids = ids.substring(1);
	}
	var obj = {id:hospitalId,briefName:briefName,wholeName:wholeName,province:province,city:city
			,qq:qq,email:email,weixin:weixin,weibo:weibo,introduction:introduction
			,tel:tel,address:address,bloodTest:bloodTest};
	if(filePaths)obj.logo = filePaths;
	if(ids)obj.logoPicId = ids;
	lh.post("front", "/hospital/updateHospital", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/hospital/hospitalHome');
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

function upDateHospitalPassword(){
	var password = $("#password").val();
	if(!password){lh.alert('请输入新的密码');return;}
	var obj = {password:password};
	if(lh.preventRepeat()){//防止重复提交
		saveHospitalData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveHospitalData(obj){
	lh.post("front", "/hospital/updateHospitalPassword", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.alert({content:'修改密码成功,请重新登录',clickYes:goHospitalLogin});
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}

function goHospitalLogin(){
	lh.jumpToUrl('/hospitalLogin');
}



