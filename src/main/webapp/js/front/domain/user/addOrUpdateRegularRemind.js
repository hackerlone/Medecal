
$(function(){
	$('#remindTime').datetimepicker({
	    format: 'yyyy-mm-dd HH:mm:ss',
	    language: 'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});
});

function addOrUpdateRegularRemind(){
	var regularRemindId = $("#regularRemindId").val();
	var remindTime = $("#remindTime").val();
	var title = $("#title").val();
	var content = $("#content").val();
	if(!title){lh.alert('请填写提醒标题');return;}
	if(!remindTime){lh.alert('请选择提醒时间');return;}
	if(!content){lh.alert('请填写提醒内容');return;}
	
	var obj = {id:regularRemindId,title:title,remindTime:remindTime,content:content};
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post("front", "/addOrUpdateRegularRemind", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.back();
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}