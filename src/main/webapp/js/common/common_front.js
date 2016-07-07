/** 日期：2016年2月21日20:16:18 作者：虞荣华 描述：蓝海飞鱼基本库(对象化) */

/** 唯一全局对象 */
lh = lh || {};

lh.grid.frontSize = 20;

/** 复制 **/
function initFrontClipboard(){
	var clipboard = new Clipboard('.copy_btn');
	clipboard.on('success', function(e) {
		frontBaseAlert('已成功复制到粘贴版');
	});
}
initFrontClipboard();

lh.showRepeatTip = function (tip){
	if(!tip)tip = '请不要重复提交数据';
	lh.alert(tip);
}


function logout(){
	$.post('/logout',null,function(rsp){
	     if(rsp.status == 'success'){     	
	     	window.location.href = "/";
	     }else{
	     	alert('提示',rsp.msg);
	     }
	},'json');
	
}

var getTypeId = function(typeId){
	localStorage.setItem("typeId", typeId);
}

function searchDoctorOrHospital(){
	var searchName = $("#searchName").val();
	var typeId = localStorage.getItem("typeId");
	if(typeId){
		typeId = typeId;
	}else{
		typeId = 2;
	}
	localStorage.removeItem("typeId");
	location.href="/searchDoctorOrHospitalList/"+typeId+"?searchName="+searchName;
	
}

$(function() {
	//var height = window.innerHeight;
	//$('#pff').css('top', (height-100)/2);
	
})
