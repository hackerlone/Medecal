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
	var patientIdcardNum = $("#patientIdcardNum").val();
	if(!patientIdcardNum){
		lh.jumpToUrl('/doctorHome');return;
	}
	//TODO 参数，加载自己的病历
	lh.post('front', '/getDiagnoseList',{page:page,rows:size,patientIdcardNum:patientIdcardNum},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#dataListContainer').html(buildDom(rsp));
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
	var rows = data.rows;
	var sessionDoctorId = data.doctorId;
	var obj = {
		rows:rows,
		isOwner:function(){
			return sessionDoctorId == this.doctorId;
		},
		statusName:function(){
			var tip = '';
			if(sessionDoctorId == this.doctorId){
				tip = '自由查看';
			}else{
				if(!this.applyStatus){
					tip = '未发起申请';
				}else if(this.applyStatus == 1){
					tip = '等待授权';
				}else if(this.applyStatus == 2){
					tip = '申请被拒绝';
				}else if(this.applyStatus == 3){
					tip = '已授权查看';
				}
			} 
			return tip;
		},
		isAgree:function(){
			if(sessionDoctorId == this.doctorId || this.applyStatus == 3){
				return true;
			} 
			return false;
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

/** 申请阅读病历 */
function applyToReadDiagnose(diagnoseId){
	if(!diagnoseId)return;
	if(!lh.preventRepeat()){
		return lh.showRepeatTip();//提示重复提交
	}
	lh.post('front','/applyToReadDiagnose', {diagnoseId:diagnoseId},function(rsp){
		if(rsp.success){
			lh.alert({content:'您已成功发送授权申请，授权通过后即可查看病历',clickYes:lh.reload});return;
		}else{
			lh.alert(rsp.msg);return;
		}
	});
}

/** 阅读病历 */
function readDiagnose(id){
	if(!id)return;
	lh.jumpToUrl('/diagnoseRead/'+id);
}
