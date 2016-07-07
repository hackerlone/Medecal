$(function(){
	initData();
});
function initData(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	loadGridData();
}

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var searchType = $("#searchType").val();
	var ascOrdesc = $("#ascOrdesc").val();
	if(!searchType){searchType = null;}
	if(!ascOrdesc){ascOrdesc = null;}
	var doctorName = $("#doctorName").val();
	if(!doctorName)	doctorName = null;
	$.post('/hospital/gethospitalUserReportList',{page:page,rows:size,doctorName:doctorName,searchType:searchType,ascOrdesc:ascOrdesc},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#reportList').html(buildDom(rsp.rows));
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
	if(!data) return '';
	var obj = {
			rows:data,
			createDate:function(){
				return lh.formatDate({date:this.createdAt});
			},
			collectionTime:function(){
				if(!this.collectionDate)return '';
				return lh.formatDate({date:this.collectionDate,flag:'datetime'});
			},
			resultHintName:function(){
				var resultHint = this.resultHint;
				if(resultHint == 'd'){
					return '低';
				}else if(resultHint == 'z'){
					return '正常';
				}else if(resultHint == 'g'){
					return '高';
				}
			}
			
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}

function readPatientReport(id){
	lh.jumpToUrl("/hospital/hospitalUserReportDetail/"+id);
}


