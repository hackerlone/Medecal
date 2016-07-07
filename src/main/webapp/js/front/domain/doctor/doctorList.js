$(function(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	loadGridData();
});

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var searchDoctorName = $("#searchDoctorName").val();
	if(!searchDoctorName) searchDoctorName = null;
	$.post('/getDoctorList',{page:page,rows:size,username:searchDoctorName},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#doctorList').html(buildDom(rsp.rows));
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
/**加入我的圈子**/
function joinMyFans(doctorId){
	if(lh.preventRepeat()){//防止重复提交
		lh.post("front", "/joinMyFans", {id:doctorId}, function(rsp){
			if(rsp.status == 'success'){
				lh.alert({conteng:"加入成功",clickYes:reload});
				
			}else{
				lh.alert(rsp.msg);
			}
		}, 'json');
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
	
}
/**我的圈子中删除**/
function cancelJoinMyFans(doctorId){
	if(lh.preventRepeat()){//防止重复提交
		lh.post("front", "/cancelJoinMyFans", {id:doctorId}, function(rsp){
			if(rsp.status == 'success'){
				lh.alert({conteng:"删除成功",clickYes:reload});
			}else{
				lh.alert(rsp.msg);
			}
		}, 'json');
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
	
}

function reload(){
	window.location.reload();
}

