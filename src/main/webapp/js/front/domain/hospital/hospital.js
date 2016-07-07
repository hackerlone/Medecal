$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	
	jQuery(".txtScroll-top").slide({mainCell:".bd ul",autoPage:true,effect:"top",autoPlay:true,vis:1});
    
    jQuery(".pz_tab").slide({});
    
    doctorNewMsg();
	setInterval(doctorNewMsg,60000);
});

var doctorNewMsg = function(){
	lh.post('front','/hospital/hospitalNewMsg',function(rsp){
		if(rsp.success){
			$('#bespeakCount').text(rsp.bespeakCount);
			$('#consultCount').text(rsp.consultCount);
			$('#diagnoseApplyCount').text(rsp.diagnoseApplyCount);
			$('#messageCount').text(rsp.messageCount);
		}
	});
}

function showContent(id){
	var content = $("#showContent_"+id);
	lh.alert(content[0].innerHTML);
}

//点击预约
//$(document).on('click','#bespeakCount',function(){
//	if($(this).text()>0){
//		location.href='/hospital/hospitalBespeak';
//	}
//});
//
////点击条未处理咨询
//$(document).on('click','#consultCount',function(){
//	if($(this).text()>0){
//		location.href='/doctorConsult';
//	}
//});
//
////点击授权 
//$(document).on('click','#diagnoseApplyCount',function(){
//	if($(this).text()>0){
//		location.href='/hospital/hospitalDiagnoseApply';
//	}
//});
//
////点击留言 
//$(document).on('click','#messageCount',function(){
//	if($(this).text()>0){
//		location.href='/doctorMessage';
//	}
//});>>>>>>> .r1763
