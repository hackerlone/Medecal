MYEDITOR = null;
$(function(){
	MYEDITOR = UE.getEditor('editor');
	initData();
});

function initData(){
	if(ARTICLE_JSON){
		var obj = JSON.parse(ARTICLE_JSON);
		var announcement = obj.announcement;
		setData(announcement);
	}
	$('#f_hospitalId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : true,
		multiple : false,
		panelHeight : 200,
		filter: lh.comboboxDefaultFilter,
		url : '/back/getHospitalArray'
		/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
	});
	$('#f_mainStatus').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		required : true,
		panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]
	});
}

function setData(data){
	setTimeout(function(){
		MYEDITOR.setContent(data.content,true);
    },600);
	$('#f_mainStatus').combobox('setValue',data.mainStatus);
}

function saveMainObj(){
	var announcementId = $("#announcementId").val();
	var content = MYEDITOR.getContent();
	if(!content){$.messager.alert('提示','请填公告内容');return;}
	var obj = {};
	obj.id = announcementId;
	obj.content = content;
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		lh.showRepeatTip();//提示重复提交
	}
}

function saveData(obj){
	lh.post('back','/back/addOrUpdateAnnouncement',obj,function(rsp){
		if(rsp.status == 'success'){
			$.messager.confirm('提示','是否继续添加数据',function(r){
				if(r){
					lh.reload();
				}else{
					lh.jumpToUrl('/back/announcement');
				}
			});
		}
	},'json');
}
