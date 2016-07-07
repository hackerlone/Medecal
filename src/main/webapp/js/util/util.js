SCROLL_LOADING = false;
/** 页面底部加载数据 */
function scrollBottom(func){
	$(window).scroll(function () {
		if(SCROLL_LOADING)return;
		SCROLL_LOADING = true;
	    var scrollTop = $(this).scrollTop();
	    var scrollHeight = $(document).height();
	    var windowHeight = $(this).height();
	    if (scrollTop + windowHeight == scrollHeight) {
	    	//此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
			func();	
	    }else{
	    	SCROLL_LOADING = false;
	    }
	});
}

/**克隆对象
 * */
function clone(obj){
	var o;
	switch(typeof obj){
	case 'undefined': break;
	case 'string'   : o = obj + '';break;
	case 'number'   : o = obj - 0;break;
	case 'boolean'  : o = obj;break;
	case 'object'   :
		if(obj === null){
			o = null;
		}else{
			if(obj instanceof Array){
				o = [];
				for(var i = 0, len = obj.length; i < len; i++){
					o.push(clone(obj[i]));
				}
			}else{
				o = {};
				for(var k in obj){
					o[k] = clone(obj[k]);
				}
			}
		}
		break;
	default:		
		o = obj;break;
	}
	return o;	
}

