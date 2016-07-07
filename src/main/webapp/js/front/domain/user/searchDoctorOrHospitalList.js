$(function(){	
	initData();
	var typeId = $("#typeId").val();
	if(typeId == 1){
		hospitalList();
	}else if(typeId == 2){
		doctorList();
	}
});


function initData(){
	
	$(window).resize(function () { goTop(); });
    $(window).scroll(function () { goTop(); });
}

function loadHospitalGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var searchName = $("#searchName").val();
	$.post('/hospital/getHospitalList',{page:page,rows:size,wholeName:searchName},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#dataList').html(buildHospitalDom(rsp.rows));
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


function buildHospitalDom(data){
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


function loadDoctorGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var searchName = $("#searchName").val();
	$.post('/getDoctorList',{page:page,rows:size,username:searchName},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
			$('#dataList').html(buildDoctorDom(rsp.rows));
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


function buildDoctorDom(data){
	if(!data) return '';
	var obj = {
			rows:data,
			createDate:function(){
				return lh.formatDate({date:this.createdAt});
			}
	} 
	var template1 = $('#template1').html();
	Mustache.parse(template1);
	var rendered = Mustache.render(template1, obj);
	return rendered;
}

function hospitalList(){
	$("#nameLi1").addClass("select");
	$("#nameLi2").removeClass("select");
	$('#dataList').empty();
	loadHospitalGridData();
}
function doctorList(){
	$("#nameLi2").addClass("select");
	$("#nameLi1").removeClass("select");
	$('#dataList').empty();
	loadDoctorGridData();
}

