$(function(){
	initData();
	initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			$("#advertisementPicPath").attr('src', filePath);
	}});
});

function initData(){
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
	$('#f_catId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		required : true,
		panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '首页'
		},{
			'id' : 2,
			'name' : '医生主页'
		},{
			'id' : 3,
			'name' : '诊所主页'
		}]
	});
	if(ARTICLE_JSON){
		var obj = JSON.parse(ARTICLE_JSON);
		var advertisement = obj.advertisement;
		setData(advertisement);
	}
}

function setData(data){
	$('#picPath').attr('src',data.picPath);
	$('#f_title').textbox('setValue',data.title);
	$('#f_linkUrl').textbox('setValue',data.linkUrl);
	$('#f_catId').combobox('setValue',data.catId);
	$('#f_mainStatus').combobox('setValue',data.mainStatus);
}

function saveMainObj(){
	var advertisementId = $("#advertisementId").val();
	var filePaths = $("#filePaths").val();
	var title = $("#f_title").textbox('getValue');
	var linkUrl = $("#f_linkUrl").textbox('getValue');
	var mainStatus = $("#f_mainStatus").combobox('getValue');
	var catId = $("#f_catId").combobox('getValue');
	var filePaths = $("#filePaths").val();
	if(!title){$.messager.alert('提示','请填写广告标题');return;}
	if(!linkUrl){$.messager.alert('提示','请填写链接地址');return;}
	//if(!filePaths){$.messager.alert('提示','请上传图片');return;}
	var obj = {id:advertisementId,title:title,mainStatus:mainStatus,catId:catId,linkUrl:linkUrl};
	if(filePaths){obj.picPath = filePaths.substring(1)}
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		$.messager.alert('提示','请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post('back','/back/addOrUpdateAdvertisement',obj,function(rsp){
		if(rsp.status == 'success'){
			$.messager.confirm('提示','是否继续添加数据',function(r){
				if(r){
					lh.reload();
				}else{
					lh.jumpToUrl('/back/advertisement');
				}
			});
		}else{
			$.messager.alert('提示',rsp.msg);
		}
	},'json');
}
