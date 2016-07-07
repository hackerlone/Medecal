$(function(){	
	var cityId = $("#cityId").val();
	provinceChange(cityId);
	initData();
});


function initData(){
	loadGridData();
}

function provinceChange(cityId){
	var provinceId = $("#province").val();
	if(!provinceId){
		$("#cityDiv").html('<select id="city" class="input8" style="width: 80px;  margin-top: 3px;"></select>');
		return;
	}
	lh.post("front", "/getCityArray", {provinceId:provinceId}, function(rsp){
		if(rsp){
			var dom = '<select id="city" onChange="cityChange()" class="input8" style="width: 80px;  margin-top: 3px;"><option value="">全部</option>'
			+'<option value="">全部</option>';
			$("#cityDiv").empty();
			for(var i = 0;i < rsp.length;i++){
				dom +=' <option';
				if(cityId && cityId == rsp[i].id)dom += ' selected="selected" ';
				dom += ' value="'+rsp[i].id+'">'+rsp[i].name+'</option>';
			}
			dom += '</select>';
			$("#cityDiv").append(dom);
		}
	}, "json")
	loadGridData();
}

function cityChange(){
	loadGridData();
}

function doSearch(){
	loadGridData();
}

function resetSearch(){
	$("#province").val('');
	$("#city").val('');
}

function loadGridData(page, size, count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	//if(!province){lh.alert('请选择医生所属省份县市');return;}
	var obj = {page:page,rows:size,bloodTest:'bloodTest'};
	var province = $("#province").val();
	var city = $("#city").val();
	if(province)obj.province = province;
	if(city)obj.city = city;
	$.post('/hospital/getHospitalList', obj, function(rsp){
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



