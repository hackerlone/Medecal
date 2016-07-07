$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	loadGridData();
});

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var province = $("#province").val();
	var username = $("#username").val();
	if(!province) province = null;
	if(!username) username = null;
	$.post('/hospital/gethospitalNurseList',{page:page,rows:size,username:username,province:province},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#nurseList').html(buildDom(rsp.rows));
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

function editDoctor(id){
	lh.jumpToUrl("/hospital/hospitalNurseDetail?id="+id);
}

var resetQuery = function(){
	$("#province").val(null);
	$("#username").val(null);
}

/** 删除病历模板 */
function deleteDoctor(id){
	if(!id)return;
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	lh.post('front', '/hospital/updateNurseDeleteForHospital', {nurseId:id},function(rsp){
		if(rsp.success){
			loadGridData();
			lh.alert('您已经成功删除该护士');
		}else{
			lh.alert(rsp.msg);
		}
	});
}

