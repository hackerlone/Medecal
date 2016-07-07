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
	
	/*jQuery('.pz_SideLayer2 li').hover(function(){
		jQuery(this).find('.down_ul').stop().slideDown(300)
	},function(){
		jQuery(this).find('.down_ul').stop().slideUp(300)
 	}
	);*/
	
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
	if(!name)name = null;
	
	$.post('/getInternalMessageList',{page:page,rows:size,receiverId:userId,content:name},function(rsp){
		if(rsp.success){
			var totalNumber = rsp.total || 0;
		    $('#internalMessageList').html(buildDom(rsp.rows));
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
			}
		} 
	var template = $('#template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, obj);
	return rendered;
}