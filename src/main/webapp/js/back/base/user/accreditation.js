BARTREE = null;
SAVING_FLAG = 0;
$(function() {
	formateDate();
	pushDataWhenUpdate();
});

/*function returnBack(){
	window.location.href="/back/user.html";
}

function accreditation(){
	var id =$('#userId').val();
	var status = 2;
	user={id:id,status:status}
	$.post('/authentication',user,function(rsp){
		if(rsp.status=='success'){
			$.messager.alert('提示',rsp.msg);
			setTimeout(function(){
				window.location.href = "/back/user.html";
		    },1000);
		}else{
			$.messager.alert('提示','认证失败');
		}
	},'json');
	
}

function cancellAccreditation(){
	var id =$('#userId').val();
	var status = 1;
	user={id:id,status:status}
	$.post('/authentication',user,function(rsp){
		if(rsp.status=='success'){
			$.messager.alert('提示',rsp.msg);
			setTimeout(function(){
				window.location.href = "/back/user.html";
		    },1000);
		}else{
			$.messager.alert('提示','认证失败');
		}
	},'json');

}*/

function pushDataWhenUpdate(){
	var userJson = $('#userData').text();
	var pictureListJson = $('#pictureListData').text();
	if(pictureListJson){
		var obj = JSON.parse(pictureListJson);
		var pictureList = obj.pictureList;
		for(i=0;i<pictureList.length;i++){
			$('#liscenseFile'+i).attr('src',pictureList[i].picPath);
		}
	}
	/*if(userJson){//update - pushData
		var obj = JSON.parse(userJson);  
		var user = obj.user;
		USER_OBJ = user
		$('#userId').val(user.id);
		$('#username').val(user.username);
		
		if(user.idCardPic){
			$('#idCardPic').attr('src',user.idCardPic);
		}else{
			$('#Pic').css('display','');
		}
		if(user.liscenseFile4){
			$('#liscenseFile').attr('src',user.liscenseFile4);
		}else{
			$('#File').css('display','');
		}
		if(user.liscenseFile2){
			$('#liscenseFile2').attr('src',user.liscenseFile2);
		}else{
			$('#File2').css('display','');
		}
		if(user.liscenseFile3){
			$('#liscenseFile3').attr('src',user.liscenseFile3);
		}else{
			$('#File3').css('display','');
		}
		if(user.liscenseFile4){
			$('#liscenseFile4').attr('src',user.liscenseFile4);
		}else{
			$('#File4').css('display','');
		}
		
		$('#idCard').attr('href',user.idCardPic);
		$('#liscense').attr('href',user.liscenseFile);
		$('#liscense2').attr('href',user.liscenseFile2);
		$('#liscense3').attr('href',user.liscenseFile3);
		$('#liscense4').attr('href',user.liscenseFile4);
	}*/
}

function formateDate(){
	$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+m+'-'+d;
}
}


