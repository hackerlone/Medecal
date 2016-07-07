$(function(){
	initData();
});
function initData(){
	$('.input14').bind('input propertychange', function() {
		$('.input14').on('blur', function(){$('.pf_268').slideUp(300)});
		$('.pf_268').slideDown(300)
		$('.pf_268 ul li').text(event.target.value) 
	});
}