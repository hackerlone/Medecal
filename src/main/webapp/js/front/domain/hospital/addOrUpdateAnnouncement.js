$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	MYEDITOR = UE.getEditor('editor');
});



function addOrUpdateAnnouncement(){
	var title = $("#title").val();
	var content = MYEDITOR.getContent();
	if(!title){
		lh.alert('请输入文章标题');
		return;
	}
	if(!content){
		lh.alert('请输入文章内容');
		return;
	}
	var obj = {title:title,content:content};
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post("front", "/hospital/addOrUpdateAnnouncementForHospital", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/hospital/hospitalHome');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}
