$(function(){
	initData();
});
function initData(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	loadGridData();
}

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var doctorId = $("#doctorId").val();
	var name = $("#name").val();
	if(!doctorId)doctorId = null;
	if(!name)name = null;
	$.post('/getMessageList',{page:page,rows:size,receiverId:doctorId,title:name},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#messageList').html(buildDom(rsp.rows));
		    $('#page').empty().Paging({
		    	current:page,
		    	pagesize:size,
		    	count:totalNumber,
		    	toolbar:false,
		    	callback:function(page,size,count){
		    		loadGridData(page,size,count);
		    	}
		    });
		}else{
			lh.alert(rsp.msg);return;
		}
	});
}

function resetQuery(){
	$("#name").val(null);
}

function buildDom(data){
	if(!data) return '';
	var obj = {
			rows:data,
			createDate:function(){
				return lh.formatDate({date:this.createdAt});
			}
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}

function messageDetail(id){
	window.location.href = "/messageDetail/"+id;
}
