BARTREE = null;
SAVING_FLAG = 0;
$(function() {
	formateDate();
	initCombo();
	pushDataWhenUpdate();
	$('#TitleIds').click(function (){
		$('#titleIds').combobox('setValue','');
	});
	$('#Institution').click(function (){
		$('#institution').combobox('setValue','');
	});
	$('#GoodAt').click(function (){
		$('#goodAt').combobox('setValue','');
	});
	/*$('#TypeIds').click(function (){
		$('#typeIds').combobox('setValue','');
	});*/
});

function initCombo(){
	$('#typeIds').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		data : [{
			'id' : 44,
			'name' : '心理咨询师'
		}, {
			'id' : 45,
			'name' : '婚姻家庭咨询师'
		}, {
			'id' : 46,
			'name' : '催眠咨询师'
		}, {
			'id' : 47,
			'name' : '法律咨询师'
		}]
	});	
	
	
	$('#titleIds').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:true,
	    required:false,
	    panelHeight:400,
	    url:'/back/getUserTitleForCombo'
	});		
	
	$('#institution').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:400,
	    url:'/back/getInsititionForCombo'
	});		
	
	$('#goodAt').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:true,
	    required:false,
	    panelHeight:400,
	    url:'/back/getUserGoodAtForCombo'
	});		

	$('#city').combobox({
		valueField : 'name',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
	    onSelect: function(rec){
            var url = '/areaInfo/getCity?provinceId='+rec.id;
            $('#area').combobox('reload', url);
        }
	});

	$('#province').combobox({
		valueField : 'name',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		url:'/areaInfo/getProvince',
		onSelect: function(rec){
            var url = '/areaInfo/getCity?provinceId='+rec.id;
            $('#city').combobox('reload', url);
        }
	});
	
	$('#group').combobox({
		valueField : 'name',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		url:'/areaInfo/getProvince'
	});
}

function returnBack(){
	window.location.href = "/back/userInfo.html";
}

function addUser(){
	//SAVING_FLAG = false;
	var username = $('#username').val();
	var password = $('#password').val();
	var realName = $('#realName').val();
	var idCard = $('#idCard').val();
	var idCardPic = $('#idCardPic').val();
	
	var avatar = $('#avatar').val();
	var email = $('#email').val();
	var phone = $('#phone').val();
	var tel = $('#tel').val();
	var zhifubaoAccount = $('#zhifubaoAccount').val();
	var bankAccount = $('#bankAccount').val();
	var bankAccount2 = $('#bankAccount2').val();
	var d_birthday = $('#birthday').datebox('getValue');
	var qq = $('#qq').val();
	var weixin = $('#weixin').val();
	var province = $('#province').combobox('getValue');
	var city = $('#city').combobox('getValue');
	var address = $('#address').val();
	var weibo = $('#weibo').val();
	var sex = $('#sex').combobox('getValue');
	var fax = $('#fax').val();
	var postalcode = $('#postalcode').val();
	var d_addTime = $('#addTime').datebox('getValue');
	var typeIds = $('#typeIds').combobox('getValues');
	var titleIds = $('#titleIds').combobox('getValues');
	if(typeIds&&typeIds.length>0){
		var ids = "";
		for(var i in typeIds){
			var id = typeIds[i];
			if(id){
				ids+=","+id;
			}
		}
		typeIds = ids.substring(1);
	}else{
		typeIds = "";
	}
	if(titleIds&&titleIds.length>0){
		var ids = "";
		for(var i in titleIds){
			var id = titleIds[i];
			if(id){
				ids+=","+id;
			}
		}
		titleIds = ids.substring(1);
	}else{
		titleIds = "";
	}
	//var studioName = $('#studioName').val();
	var institution = $('#institution').combobox('getValue');
	var phoneHidden = $('#phoneHidden').prop('checked');//用户名
	if(phoneHidden){
		var phoneConceal = 2;
	}
	var realNameHidden = $('#realNameHidden').prop('checked');//用户名
	if(realNameHidden){
		var realNameConceal = 2;
	}
	
	var goodAt = $('#goodAt').combobox('getValues');
	if(goodAt&&goodAt.length>0){
		var ids = "";
		for(var i in goodAt){
			var id = goodAt[i];
			if(id){
				ids+=","+id;
			}
		}
		goodAt = ids.substring(1);
	}else{
		goodAt = "";
	}
	
	var position = $('#position').val();
	var positionId = $('#positionId').val();
	var group = $('#group').combobox('getValue');
	
	var liscenseFile = $('#liscenseFile').val();
	var liscenseFile2 = $('#liscenseFile2').val();
	var liscenseFile3 = $('#liscenseFile3').val();
	var liscenseFile4 = $('#liscenseFile4').val();
	var description = $('#description').text();
	
	var user = {};
	var userId = $('#userId').val();
	if(userId){
		user = USER_OBJ;
		/*user.d_updateTime = user.updateTime;
		user.d_startDate = user.startDate;
		user.d_cutOffDate = user.cutOffDate;
		user.d_lastLoginTime = user.lastLoginTime;
		user.d_regDate = user.regDate;
		user.d_foundTime = user.foundTime;*/
		
		delete user.updateTime;
		delete user.startDate;
		delete user.cutOffDate;
		delete user.lastLoginTime;
		delete user.regDate;
		delete user.foundTime;
	}
	user.username = username;
	user.password = password;
	user.realName = realName;
	user.idCard = idCard;
	user.idCardPic = idCardPic;
	user.avatar = avatar;
	user.email = email;
	user.realNameConceal = realNameConceal;
	user.phoneConceal = phoneConceal;
	user.phone = phone;
	user.tel = tel;
	user.zhifubaoAccount = zhifubaoAccount;
	user.bankAccount = bankAccount;
	user.bankAccount2 = bankAccount2;
	user.d_birthday = d_birthday;
	delete user.birthday;
	user.qq = qq;
	user.weixin = weixin;
	user.province = province;
	user.city = city;
	user.address = address;
	user.weibo = weibo;
	user.sex = sex;
	user.fax = fax;
	user.postalcode = postalcode;
	user.d_addTime = d_addTime;
	delete user.addTime;
	user.typeIds = typeIds;
	user.titleIds = titleIds;
	//user.studioName = studioName;
	user.institutionId = institution;
	user.goodAtIds = goodAt;
	/*user.liscenseFile = liscenseFile;
	user.liscenseFile2 = liscenseFile2;
	user.liscenseFile3 = liscenseFile3;
	user.liscenseFile4 = liscenseFile4;*/
	user.description = description;
	
	user.position = position;
	user.positionId = positionId;
	user.group = group;
	
	if(!username || !password){
		alert('请填写用户名和密码');	return;
	}
	//SAVING_FLAG = true;
	$.post('/addOrUpdateUser',user,function(rsp){
		backLoginCheck(rsp);//后台登陆检查
		//SAVING_FLAG = false;
		if(rsp.status=='success'){
			$.messager.alert('提示',rsp.msg);
			setTimeout(function(){
				//window.location.href = "/back/user.html";
				window.location.reload();
		    },1000);
		}else{
			$.messager.alert('提示','操作失败');
		}
	},'json');
}

function check(){
	var id =$('#userId').val();
	window.open("/accreditation.html?userId="+id);
	
}


function pushDataWhenUpdate(){
	var userJson = $('#userData').text();
	if(userJson){//update - pushData
		var obj = JSON.parse(userJson);  
		var user = obj.user;
		USER_OBJ = user
		if(user.id){
			$('#checkLiscenseFile').css('display','');
			/*$('#nameName').css('display','none');
			$('#passWord').css('display','none');
			$('#realname').css('display','none');*/
		}else{
			/*$('#nameName').css('display','');
			$('#passWord').css('display','');
			$('#realname').css('display','');*/
		}
		if(user.realNameConceal == 2){
			$('#realNameHidden').attr('checked','checked');
		}
		if(user.phoneConceal == 2){
			$('#phoneHidden').attr('checked','checked');
		}
		
		$('#userId').val(user.id);
		$('#username').val(user.username);
		$('#password').val(user.password);
		$('#realName').val(user.weixinLoginToken);
		$('#idCard').val(user.idCard);
		$('#idCardPic').val(user.idCardPic);
		$('#email').val(user.email);
		$('#avatar').val(user.avatar);
		$('#phone').val(user.qqLoginToken);
		$('#tel').val(user.tel);
		$('#zhifubaoAccount').val(user.zhifubaoAccount);
		$('#bankAccount').val(user.bankAccount);
		$('#bankAccount2').val(user.bankAccount2);
		if(user.birthday){
			var d = new Date(user.birthday);
			var month = d.getMonth()+1;
	        var dateStr = ''+d.getFullYear()+'-'+month+'-'+d.getDate();
			$('#birthday').datebox('setValue',dateStr);
		}
		$('#qq').val(user.qq);
		$('#weixin').val(user.weixin);
		$('#province').combobox('setValue',user.province);
		$('#city').combobox('setValue',user.city);
		//$('#area').combobox('setValue',user.area);
		$('#address').val(user.address);
		$('#weibo').val(user.weibo);
		$('#sex').combobox('setValue',user.sex);
		$('#fax').val(user.fax);
		$('#postalcode').val(user.postalcode);
		if(user.addTime){
			var d = new Date(user.addTime);
			var month = d.getMonth()+1;
	        var dateStr = ''+d.getFullYear()+'-'+month+'-'+d.getDate();
			$('#addTime').datebox('setValue',dateStr);
		}
		
		var typeIds = user.typeIds;
		if(typeIds){
			typeIds = typeIds.split(',');
		}else{
			typeIds = "";
		}
		$('#typeIds').combobox('setValues',typeIds);
		
		var titleIds = user.titleIds;
		if(titleIds){
			titleIds = titleIds.split(',');
		}else{
			titleIds = "";
		}
		$('#titleIds').combobox('setValues',titleIds);
		
		//$('#studioName').val(user.studioName);
		$('#institution').combobox('setValue',user.institutionId);
		
		var goodAt = user.goodAtIds;
		if(goodAt){
			goodAt = goodAt.split(',');
		}else{
			goodAt = "";
		}
		$('#goodAt').combobox('setValues',goodAt);
		
		$('#position').val(user.position);
		$('#positionId').val(user.positionId);
		$('#group').combobox('setValue',user.group);
		
		$('#liscenseFile').val(user.liscenseFile);
		$('#liscenseFile2').val(user.liscenseFile2);
		$('#liscenseFile3').val(user.liscenseFile2);
		$('#liscenseFile4').val(user.liscenseFile2);
		$('#description').text(user.description);
		
	}else{
		$('#checkLiscenseFile').css('display','none');
	}
}

function formateDate(){
	$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+m+'-'+d;
}
}


