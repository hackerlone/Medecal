$(function(){
	initPage();
	loadGridData();
});
function initPage(){
	$('.input14').bind('input propertychange', function() {
		$('.input14').on('blur', function(){$('.pf_268').slideUp(300)});
		$('.pf_268').slideDown(300)
		$('.pf_268 ul li').text(event.target.value) 
	});
	//$('#diagnoseTemplateList').addClass('hover');
}

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	//var typeId = $("#typeId").val();
	//if(!typeId)typeId = null;
	//TODO 参数，加载自己的病历模板
	var name = $("#name").val();
	if(!name)name = null;
	lh.post('front', '/getDiagnoseTemplateList',{page:page, rows:size,templateName:name},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#dataListContainer').html(buildDom(rsp.rows));
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


/** 编辑病历模板 */
function editDiagnoseTemplate(id){
	if(!id)return;
	lh.jumpToUrl('/diagnoseTemplate/'+id);
}

/** 删除病历模板 */
function deleteDiagnoseTemplate(id){
	if(!id)return;
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	lh.post('front', '/deleteDiagnoseTemplate', {diagnoseTemplateId:id},function(rsp){
		if(rsp.success){
			loadGridData();
			lh.alert('您已经成功删除该病历模板');
		}else{
			lh.alert(rsp.msg);
		}
	});
}

