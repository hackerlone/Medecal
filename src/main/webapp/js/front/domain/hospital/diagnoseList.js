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
	//$('#diagnoseList').addClass('hover');
}

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	//var typeId = $("#typeId").val();
	//if(!typeId)typeId = null;
	//TODO 参数，加载自己的病历
	var doctorName = $("#doctorName").val();
	var searchType = $("#searchType").val();
	var ascOrdesc = $("#ascOrdesc").val();
	if(!doctorName)doctorName = null;
	if(!searchType){searchType = null;}
	if(!ascOrdesc){ascOrdesc = null;}
	lh.post('front', '/hospital/getDiagnoseList',{page:page,rows:size,ownAndApplied:1,doctorName:doctorName,searchType:searchType,ascOrdesc:ascOrdesc},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#dataListContainerList').html(buildDom(rsp));
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
	$("#doctorName").val(null);
	$("#searchType").val(null);
	$("#ascOrdesc").val(null);
}

function buildDom(data){
	var rows = data.rows;
	var sessionDoctorId = data.doctorId;
	var obj = {
		rows:rows,
		isOwner:function(){
			return sessionDoctorId == this.doctorId;
		},
		createDate:function(){
			return lh.formatDate({date:this.diagnoseTime});
		}
	} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}
/** 阅读病历 */
function readDiagnose(id){
	if(!id)return;
	lh.jumpToUrl('/hospital/diagnoseRead/'+id);
}

/** 删除病历 */
function deleteDiagnose(id){
	if(!id)return;
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	lh.post('front', '/hospital/deleteDiagnose', {diagnoseId:id},function(rsp){
		if(rsp.success){
			loadGridData();
			lh.alert({content:'您已经成功删除该病历',clickYes:lh.reload});
		}else{
			lh.alert(rsp.msg);
		}
	});
}

