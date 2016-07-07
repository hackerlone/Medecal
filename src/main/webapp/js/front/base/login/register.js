
function loadRandomCode(){ 
	$('#imgcode').attr('src','/login/loadVerificationCode?id='+parseInt(1000*Math.random()));
}

/*function loadRandomCode(){ 
	$.get('/login/loadVerificationCode?id='+parseInt(1000*Math.random()), null, null, null);
}*/


function register(){
	var randomCode = $("#randomCode").val();
	var idcardNum = $("#idcardNum").val();
	var phone = $("#phone").val();
	var password = $("#password").val();
	var passwordSure = $("#passwordSure").val();
	if(!idcardNum){lh.alert('请输入身份证号码');return;}
	if(!phone){lh.alert('请输入电话号码');return;}
	if(!password){lh.alert('请输入用户密码');return;}
	if(!passwordSure){lh.alert('请输入确认密码');return;}
	if(passwordSure != password){lh.alert('确认密码与用户密码不一致');return;}
	var obj={idcardNum:idcardNum,randomCode:randomCode,phone:phone,password:password};
	saveData(obj);
}


function saveData(obj){
	if(lh.preventRepeat()){//防止重复提交
		lh.post("front", '/doReg', obj, function(rsp){
			if(rsp.status == 'success'){
				location.href="/userBaseInformation";
			}else if(rsp.status == 'failure'){
				lh.alert(rsp.msg);
			}
		}, "json")
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}
