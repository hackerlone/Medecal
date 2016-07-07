function updateBespeakRead(id){
	lh.post("front", "/updateBespeakRead", {id:id}, function(rsp){
		if(rsp.status == 'success'){
			lh.alert({content:'操作成功',clickYes:lh.back});
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}

function updateBespeaNotRead(id){
	lh.post("front", "/updateBespeaNotRead", {id:id}, function(rsp){
		if(rsp.status == 'success'){
			lh.alert({content:'操作成功',clickYes:lh.back});
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}