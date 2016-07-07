
$(function(){
	initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			$("#userAvatar").attr('src', filePath);
	}});
	var cityId = $("#cityId").val();
	provinceChange(cityId);
	remindNewMsg();
	setInterval(remindNewMsg,60000);
	$("#datetimepicker").datetimepicker({
        format:'yyyy-mm-dd',
        todayBtn:true,
        language: 'zh-CN',
        startView:2,
        viewSelect:'year',
        minView: 2, //选择日期后，不会再跳转去选择时分秒 
        autoclose:true //选择日期后自动关闭 
    });
});

var remindNewMsg = function(){
	lh.post('front','/remindNewMsg',function(rsp){
		if(rsp.regularRemindCount > 0){
			$("#remindNewMsg").show();
			$("#remindCount").text(rsp.regularRemindCount);
		}else{
			$("#remindNewMsg").hide();
		}
	});
}

//点击定期提醒
$(document).on('click','#remindNewMsg',function(){
	location.href='/regularRemindList?flag=flag';
});

function provinceChange(cityId){
	var provinceId = $("#province").val();
	lh.post("front", "/getCityArray", {provinceId:provinceId}, function(rsp){
		if(rsp){
			var dom = '<select id="city"" class="input8" style="width:268px;"><option value="">请选择</option>';
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

function upDatePatientInformation(){
	var userId = $("#userId").val();
	var username = $("#username").val();
	var realName = $("#realName").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var idcardNum = $("#idcardNum").val();
	var sex = $("#sex").val();
	var job = $("#job").val();
	var age = $("#age").val();
	var email = $("#email").val();
	var birthday = $("#datetimepicker").val();
	var province = $("#province").val();
	var filePaths = $("#filePaths").val();
	var city = $("#city").val();
	if(!username){lh.alert('请填写用户昵称');return;}
	if(!realName){lh.alert('请填写用户真实姓名');return;}
	if(!phone){lh.alert('请填写用户联系电话');return;}
	if(!idcardNum){lh.alert('请填写身份证号码');return;}
	if(!sex){lh.alert('请选择性别');return;}
	//if(!address){lh.alert('请填写用户住址');return;}
	//if(!job){lh.alert('请选择职业');return;}
	//if(!age){lh.alert('请填写年龄');return;}
	/*if(!email){lh.alert('请填写邮箱');return;}*/
	if(!birthday){lh.alert('请选择出生日期');return;}
	if(!province){lh.alert('请选择省份');return;}
	if(!city){lh.alert('请选择城市');return;}
	if(!filePaths){lh.alert('请上传头像');return;}
	var ids = UPLOAD_OBJ.idsStr;
	if(filePaths.substring(0,1) != "/"){
		filePaths = filePaths.substring(1);
		ids = ids.substring(1);
	}
	birthday += ' 00:00:00';
	var obj = {id:userId,username:username,realName:realName,phone:phone,address:address,idcardNum:idcardNum,
			sex:sex,job:job,age:age,email:email,birthday:birthday,province:province,city:city};
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
	lh.post("front", "/updateUser", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.alert({conetnt:'保存成功,重新登录后生效',clickYes:myReload});
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}

function myReload(){
	window.location.href="/login";
}

var reset = function(operation){
	$("#reset").show();
	$("#baseInformation").hide();
	if(operation == 'cancel'){
		$("#reset").hide();
		$("#baseInformation").show();
	}
}

function upDateUserPassword(){
	var password = $("#password").val();
	if(!password){lh.alert('请输入新的密码');return;}
	var obj = {password:password};
	if(lh.preventRepeat()){//防止重复提交
		saveUserData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveUserData(obj){
	lh.post("front", "/updateUserPassword", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.alert({content:'修改密码成功,请重新登录',clickYes:goUserLogin});
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}

function goUserLogin(){
	lh.jumpToUrl('/login');
}
