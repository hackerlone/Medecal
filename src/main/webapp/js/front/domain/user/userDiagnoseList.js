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
	var name = $("#name").val();
	if(!name) name = null;
	$.post('/getUserDiagnoseList',{page:page,rows:size,doctorName:name, isLink:1},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#userDiagnoseList').html(buildDom(rsp.rows));
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
			diagnoseDate:function(){
				return lh.formatDate({date:this.diagnoseTime,flag:'datetime'});
			}
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}

function readDiagnose(id){
	lh.jumpToUrl("/readDiagnose/"+id);
}

function deleteDiagnose(id){
	lh.post("front", "/updateDiagnoseDelete", {diagnoseId:id}, function(rsp){
		if(rsp.status == 'success'){
			loadGridData();
			lh.alert('删除病历成功');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}

