lh.param = {};
$(function(){
	initData();
	var cityId = $("#cityId").val();
	provinceChange(cityId);
});
function initData(){
	jQuery(".pz_tab").slide({});
	/*jQuery('.one_262 ul li').hover(function() {
		jQuery(this).find('.sub_ny').stop().slideDown(300);
		$('#hospitalUl').show();
	}, function() {
		jQuery(this).find('.sub_ny').stop().slideUp(300);
		$('#hospitalUl').show();
	});*/
	
}

function provinceChange(cityId){
	var provinceId = $("#province").val();
	if(!provinceId){
		$("#cityDiv").html('<select id="city" class="input8" style="width: 80px;  margin-top: 3px;"></select>');
		return;
	}
	lh.post("front", "/getCityArray", {provinceId:provinceId}, function(rsp){
		if(rsp){
			var dom = '<select id="city" onChange="cityChange()" class="input8" style="width: 80px;  margin-top: 3px;"><option value="">请选择</option>'
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

function refreshDoctorList(param){
	lh.param = param;
	loadGridData();
}

function loadGridData(page, size, count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var obj = lh.param;
	var province = $("#province").val();
	var city = $("#city").val();
	obj.page = page;
	obj.rows = size;
	//if(!province){lh.alert('请选择医生所属省份县市');return;}
	if(province)obj.province = province;
	if(city)obj.city = city;
	$.post('/getDoctorList', obj, function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#doctorListUl').html(buildDom(rsp.rows));
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
			},
			lastLoginDate:function(){
				return lh.formatDate({date:this.lastLoginTime,flag:'datetime'});
			}
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}

function addBespeak(){
	if(lh.preventRepeat()){//防止重复提交
		saveData();
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(){
	
}