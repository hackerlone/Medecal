$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	loadGridData();
});

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var hospitalId = $("#hospitalId").val();
	var name = $("#name").val();
	if(!hospitalId)hospitalId = null;
	if(!name)name = null;
	$.post('/hospital/getDiagnoseApplyList',{page:page,rows:size,hospitalId:hospitalId,doctorName:name},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#diagnoseApplyList').html(buildDom(rsp.rows));
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
			},
			status:function(){
				var logicStatus = this.logicStatus;
				if(logicStatus == 1){
					return status = '<button type="button" onclick="agree({{id}})" class="btn btn-danger">同意</button>';
				}else if(logicStatus == 2){
					return status = '<button type="button" onclick="disAgree({{id}})" class="btn btn-danger">不同意</button>';
				}
			}
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}

function agree(id){
	lh.post("front", "/hospital/agreeDiagnoseApply", {id:id}, function(rsp){
		if(rsp.status == 'success'){
			loadGridData();
			lh.alert('您同意该授权');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}

function disAgree(id){
	lh.post("front", "/hospital/disAgreeDiagnoseApply", {id:id}, function(rsp){
		if(rsp.status == 'success'){
			loadGridData();
			lh.alert('您不同意该授权');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}

