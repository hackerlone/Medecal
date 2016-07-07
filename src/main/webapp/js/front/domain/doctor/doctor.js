$(function(){
	doctorNewMsg();
	setInterval(doctorNewMsg,60000);
});
var doctorNewMsg = function(){
	lh.post('front','/doctorNewMsg',function(rsp){
		if(rsp.success){
			$('#bespeakCount').text(rsp.bespeakCount);
			$('#consultCount').text(rsp.consultCount);
			$('#diagnoseApplyCount').text(rsp.diagnoseApplyCount);
			$('#messageCount').text(rsp.messageCount);
		}
	});
}
//点击预约
$(document).on('click','#bespeakCount',function(){
	if($(this).text()>0){
		location.href='/doctorBespeak';
	}
});

//点击条未处理咨询
$(document).on('click','#consultCount',function(){
	if($(this).text()>0){
		location.href='/doctorConsult';
	}
});

//点击授权 
$(document).on('click','#diagnoseApplyCount',function(){
	if($(this).text()>0){
		location.href='/doctorDiagnoseApply';
	}
});

//点击留言 
$(document).on('click','#messageCount',function(){
	if($(this).text()>0){
		location.href='/doctorMessage';
	}
});

//显示公告
function showContent(id){
	var content = $("#showContent_"+id);
	lh.alert(content[0].innerHTML);
}