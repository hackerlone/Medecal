$(function () {
	initPage();
});

function initPage(){
	$(window).resize(function () { goTop(); });
    $(window).scroll(function () { goTop(); });
    jQuery(".menu").slide({
		type : "menu",
		titCell : ".nli",
		targetCell : ".sub",
		effect : "slideDown",
		delayTime : 200,
		triggerTime : 0,
		returnDefault : true
	});
}

function addComment(){
	if(lh.preventRepeat()){//防止重复提交
		saveData();
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
	
}

function saveData(){
	var articleId = $("#articleId").val();
	var content = $("#content").val();
	if(!content){
		lh.alert("请填写评论内容");
		return;
	}
	var obj = {commentTypeId:1,objectId:articleId,content:content};
	lh.post("front", '/addOrUpdateComment', obj, function(rsp){
		if(rsp.status == 'success'){
			location.reload();
		}else{
			lh.alert(rsp.msg);
		}
	}, 'json');
}

