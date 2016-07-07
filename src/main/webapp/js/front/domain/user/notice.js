$(function(){	
	initData();
	loadGridData();
});


function initData(){
	$('#_strSpan').click(function(){
	   $('#hidden_div').slideToggle(0);
	   $(this).toggleClass('hover');
	   
	   var _txt=$(this).text();
	   if(_txt=='展开详情＞')
	   {
		   $(this).text('收起＞');
		   }
	   else{$(this).text('展开详情＞');}
	});
	
	
	$(window).resize(function () { goTop(); });
    $(window).scroll(function () { goTop(); });
}

function loadGridData(page,size,count){
	if(!page)page = 1;
	if(!size)size = lh.grid.frontSize || 20;
	if(!count)count = 1;
	var userId = $("#userId").val();
	if(!userId)userId = null;
	var name = $("#name").val();
	if(!name)	name = null;
	$.post('/getNoticeList',{page:page,rows:size,receiverId:userId,title:name},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#noticeList').html(buildDom(rsp.rows));
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
	$("#name").val(null);
}

function buildDom(data){
	if(!data) return '';
	var obj = {
			rows:data,
			createDate:function(){
				return lh.formatDate({date:this.createdAt});
			},
			mainStatusName:function(){
				var status = this.mainStatus;
				if(status == 1){
					return mainStatusName = "未读";
				}else if(status == 2){
					return mainStatusName = "已读";
				}
			}
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}