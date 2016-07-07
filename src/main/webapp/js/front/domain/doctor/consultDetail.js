
$(function(){
	
});

function reply(operation){
	if(operation == 'replyNo'){
		$("#reply,#replyTr").hide();
		$("#noReply").show();
	}else if(operation == 'replyYes'){
		$("#replyTr,#reply").show();
		$("#noReply").hide();
	}
}

function saveReply(){
	var content = $("#content").val();
	var consultId = $("#consultId").val();
	if(!content){lh.alert("请填写回复内容");return;}
	var obj = {id:consultId,replyContent:content};
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post("front", "/doctorReplyConsult", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/doctorConsult');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}