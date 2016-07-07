BESPEAKTYPEID = 1;
$(function(){
	loadGridData();
});

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var hospitalId = $("#hospitalId").val();
	lh.post('front', '/getDoctorList',{page:page,rows:size,hospitalId:hospitalId},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#doctorList').html(buildDom(rsp));
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
		createDate:function(){
			return lh.formatDate({date:this.diagnoseTime});
		}
	} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}


function selectBespeak(id,hospitalId){
	DOCTORID = id;
	HOSPITALID = hospitalId;
	var dom = '<div class="modal fade" id="R_DIALOG_ID" aria-labelledby="R_T2_ID" data-backdrop="static" role="dialog">'+
		'<div class="modal-dialog" role="document">'+
			'<div class="modal-content">'+
				'<div class="modal-header">'+
					'<button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
						'<span aria-hidden="true">&times;</span>'+
					'</button>'+
					'<h4 class="modal-title">请选择预约类型</h4>'+
				'</div>'+
				'<div class="modal-body">'+
					'<select id="bespeakTypeId" onchange="getBespeakTypeId()"><option value="1">挂号</option><option value="2">陪诊</option><option value="3">血液检测</option></select>'+
				'</div>'+
				'<div class="modal-footer inline-center">'+
					'<button type="button" class="btn btn-success" onclick="getBespeak()" data-dismiss="modal">R_YES</button>'+
				'</div>'+
			'</div>'+
		'</div>'+
	'</div>';
	var dialogId = lh.createSerial();
	var yesId = lh.createSerial();
	dom = dom.replace('R_DIALOG_ID', dialogId)
			.replace('R_Y_ID', yesId)
			.replace('R_YES', '确定');;
	$('body').append(dom);
	$('#'+dialogId).modal('show');
}

function getBespeakTypeId(){
	var bespeakTypeId = $("#bespeakTypeId").val();
	BESPEAKTYPEID = bespeakTypeId;
}

function getBespeak(){
	if(BESPEAKTYPEID == 3){
		lh.jumpToUrl('/bloodTest/'+BESPEAKTYPEID);
	}else{
		lh.jumpToUrl('/userBespeak/'+BESPEAKTYPEID+'/'+DOCTORID+'/'+HOSPITALID);
	}
}
