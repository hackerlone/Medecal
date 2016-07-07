SAVING_FLAG = false;

function addComment(){
	if(SAVING_FLAG)return;
	SAVING_FLAG = true;
	var content = $("#content").val();
	var objectId = $("#objectId").val();
	var commentTypeId = $("#commentTypeId").val();
	var parentId = $("#parentId").val();
	if(!content){
		frontBaseAlert('请填写评论的内容');
		SAVING_FLAG = false;
		return ;
	}
	var obj = {};
	obj.commentTypeId = commentTypeId;
	obj.content =  content;
	obj.objectId =  objectId;
	obj.parentId =  parentId;
	obj.comment = 'yes';
	frontBaseLoadingOpen('正在保存数据...');//加载遮罩
	$.post('/addOrUpdateComment',obj,function(rsp){
		SAVING_FLAG = false;
		frontBaseLoadingClose();//解除遮罩
		if(rsp){
			frontLoginCheck(rsp);//登陆检查
			if(rsp.status == 'success'){
				window.location.reload();
			}else{
				if(rsp.noPhone == 'noPhone'){
					frontBaseAlert(rsp.msg,"jumpToBindUserPhone()");
				}else{
					frontBaseAlert(rsp.msg);
				}
			}
		}
	},'json');
	
}