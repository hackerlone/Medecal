$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	initPage();
	loadGridData();
});

function initPage(){
	$("#startDate").datetimepicker({
        format:'yyyy-mm-dd',
        todayBtn:true,
        language: 'zh-CN',
        startView:2,
        viewSelect:'year',
        minView: 2, //选择日期后，不会再跳转去选择时分秒 
        autoclose:true //选择日期后自动关闭 
    });
	$("#endDate").datetimepicker({
        format:'yyyy-mm-dd',
        todayBtn:true,
        language: 'zh-CN',
        startView:2,
        viewSelect:'year',
        minView: 2, //选择日期后，不会再跳转去选择时分秒 
        autoclose:true //选择日期后自动关闭 
    });
	
	var date = lh.formatDate();
	$('#startDate,#endDate').val(date);
	$('#startDate,#endDate').datetimepicker('update');
}

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var name = $("#name").val();
	if(!name)name = null;
	$.post('/hospital/getMedicationLogList',{page:page,rows:size,name:name},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#medicationLogList').html(buildDom(rsp.rows));
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

function editMedicationLog(id){
	lh.jumpToUrl("/hospital/addOrUpdateMedicationLog?medicationLogId="+id);
}

/** 删除病历模板 */
function deleteMedicationLog(id){
	if(!id)return;
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	lh.post('front', '/hospital/updateMedicationLogDelete', {medicationId:id},function(rsp){
		if(rsp.success){
			loadGridData();
			lh.alert('您已经成功删除该药物记录');
		}else{
			lh.alert(rsp.msg);
		}
	});
}

function resetSearch(){
	$('#startDate,#endDate').val(null);
	$('#startDate,#endDate').datetimepicker('update');
	$('#doctorName').val(null);
}
