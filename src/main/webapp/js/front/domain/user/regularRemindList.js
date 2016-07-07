$(function(){
	initData();
});
function initData(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	var flag = $("#flag").val();
	if(flag == "flag"){
		toDayRemind();
	}else{
		loadGridData();
	}
}

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var obj = {page:page,rows:size};
	var flag = $("#flag").val();
	var name = $("#name").val();
	if(flag){obj.flag = 'flag';}
	if(!name){name = null;}
	obj.title = name;
	$.post('/getRegularRemindList',obj,function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#regularRemindList').html(buildDom(rsp.rows));
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

var toDayRemind = function(){
	$('#regularRemindList').empty();
	$("#flag").val(true);
	loadGridData();
}

var allRemind = function(){
	$('#regularRemindList').empty();
	$("#flag").val('');
	loadGridData();
}

function resetQuery(){
	$("#name").val(null);
}

function buildDom(data){
	if(!data) return '';
	var obj = {
			rows:data,
			remindDate:function(){
				return lh.formatDate({date:this.remindTime});
			},
			mainStatusName:function(){
				var status = this.mainStatus;
				if(status == 1){
					return mainStatusName = "提醒";
				}else if(status == 2){
					return mainStatusName = "取消提醒";
				}
			}
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}

function addOrUpdateRegularRemind(id){
	lh.jumpToUrl("/addOrUpdateRegularRemind?id="+id);
}

function deleteRegularRemind(id){
	lh.post("front", "/updateRegularRemindDelete", {id:id}, function(rsp){
		if(rsp.status == 'success'){
			$('#regularRemindList').empty();
			loadGridData();
			lh.alert('删除定期提醒成功');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}

function notRemind(id){
	lh.post("front", "/updateNotRemind", {id:id}, function(rsp){
		if(rsp.status == 'success'){
			$('#regularRemindList').empty();
			loadGridData();
			lh.alert('修改成功');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}
function remind(id){
	lh.post("front", "/updateRemind", {id:id}, function(rsp){
		if(rsp.status == 'success'){
			$('#regularRemindList').empty();
			loadGridData();
			lh.alert('修改成功');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json")
}

