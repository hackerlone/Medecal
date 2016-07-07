$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			$("#nurseAvatar").attr('src', filePath);
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
function addOrUpdateDoctor(){
	if(lh.preventRepeat()){//防止重复提交
		saveData();
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(){
	var nurseId = $("#nurseId").val();
	//var hospitalId = $("#hospitalId").val();
	var password = $("#password").val();
	var username = $("#username").val();
	var realName = $("#realName").val();
	var address = $("#address").val();
	var phone = $("#phone").val();
	var email = $("#email").val();
	var sex = $("#sex").val();
	var filePaths = $("#filePaths").val();
	var idcardNum = $("#idcardNum").val();
	var province = $("#province").val();
	var city = $("#city").val();
	if(!username){lh.alert('请填写护士昵称');return;}
	if(!realName){lh.alert('请填写护士真实姓名');return;}
	//if(!address){lh.alert('请填写护士地址');return;}
	//if(!phone){lh.alert('请填写护士联系号码');return;}
	//if(!sex){lh.alert('请选择性别');return;}
	//if(!email){lh.alert('请填写护士邮箱');return;}
	if(!filePaths){lh.alert('请上传护士头像');return;}
	//if(!idcardNum){lh.alert('请填写护士身份证号码');return;}
	//if(!province){lh.alert('请选择省份');return;}
	//if(!city){lh.alert('请选择城市');return;}
	//if(!hospitalId){lh.alert('请选择所属诊所');return;}
	if(filePaths.substring(0,1) != "/"){
		filePaths = filePaths.substring(1);
	}else{
		filePaths = filePaths;
	}
	var obj = {id:nurseId,username:username,phone:phone,realName:realName,address:address,sex:sex,email:email,
			avatar:filePaths,idcardNum:idcardNum,province:province,city:city
			,password:password};
	lh.post("front", "/hospital/addOrUpdateNurseForHospital", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/hospital/hospitalNurseList');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}
