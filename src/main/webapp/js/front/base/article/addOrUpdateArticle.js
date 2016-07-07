$(function(){
	initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			$("#pic").attr('src', filePath);
	}});
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	MYEDITOR = UE.getEditor('editor');
});



function addOrUpdateArticle(){
	if(lh.preventRepeat()){//防止重复提交
		saveData();
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(){
	var title = $("#title").val();
	var typeId = $("#typeId").val();
	var filePaths = $("#filePaths").val();
	var content = MYEDITOR.getContent();
	if(!title){
		lh.alert('请输入文章标题');
		return;
	}
	if(!filePaths){
		lh.alert('请上传文章图片');
		return;
	}
	if(!content){
		lh.alert('请输入文章内容');
		return;
	}
	if(!typeId){
		lh.alert('请选择文章类型');
		return;
	}
	filePaths = filePaths.substring(1);
	var obj = {typeId:typeId,title:title,content:content,picPaths:filePaths};
	lh.post("front", "/addOrUpdateArticle", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/doctorHome');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}
