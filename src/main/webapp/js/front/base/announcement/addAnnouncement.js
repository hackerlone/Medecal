SAVING_FLAG = false;

function addAnnouncement(){
	if(SAVING_FLAG)return;
	SAVING_FLAG = true;
	var title = $("#title").val();
	var description = $("#description").val();
	var content = $("#content").val();
	var typeId = $("#typeId").val();
	var obj = {};
	obj.title = title;
	obj.description = description;
	obj.content = content;
	obj.typeId = typeId;
	frontBaseLoadingOpen('正在保存数据...');//加载遮罩
	$.post('/addOrUpdateAnnouncement',obj,function(rsp){
		SAVING_FLAG = false;
		frontBaseLoadingClose();//解除遮罩
		if(rsp){
			frontLoginCheck(rsp);//登陆检查 
			if(rsp.status == 'success'){
				window.location.reload();
			}else{
				frontBaseAlert(rsp.msg);
			}
		}
	},'json');
}