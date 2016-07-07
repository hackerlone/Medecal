
$(function(){	
	initData();
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
