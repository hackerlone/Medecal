/** 全屏显示图片JS 
 * 
 * http://www.swiper.com.cn/
 * */

/** picAry：图片路径数组，tip:左下角的提示文字 */
function showFullScreen(picAry,tip){
	
	
	$('#wrap').hide();
	$('#fullScreen').show();
}

function closeFullScreen(){
	
	$('#fullScreen').hide();
	$('#wrap').show();
}