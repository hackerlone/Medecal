/** 日期：2016年2月21日20:16:18 作者：虞荣华 描述：蓝海飞鱼基本库(对象化) */

/** 唯一全局对象 */
var lh = {
	ui : {},
	dom : {
		clientHeight:document.documentElement.clientHeight,
		clientWidth:document.documentElement.clientWidth,
		clientSafeWidth:document.documentElement.clientWidth < 1000 ? 1000 : document.documentElement.clientWidth
		},
	data : {},
	grid : {},//表格相关数据
	cache : {},
	status : {savingFlag:false,trashShow:false,scrollLoading:false,optTimeRec:0,optTimeGap:3},
	plugins : {},
	paramJson : null,//参数Json
	paramJsonStr : null,
	test:{phone:18702827609,password:888888}
};

/** jquery post 包装方法 */
lh.post = function(pos, url, obj, fun, type){
	if(!pos)return;
	if(pos == 'back'){//后台请求
		
	}else if(pos == 'front'){//前台请求
		
	}
	if(!type)type = 'json';
	$.post(url, obj, fun, type);
}

/** 页面跳转URL地址 */
lh.jumpToUrl = function jumpTorl(url){
	if(!url)return;
	window.location.href = url; 
}

/** 页面顶部 */
lh.goTop = function (){
	document.documentElement.scrollTop = document.body.scrollTop = 0;
}

/** 刷新当前页 */
lh.reload = function (){
	window.location.reload();
}

/** history.back 页面回退返回 */
lh.back = function (index){
	if(index){
		window.history.back(index);
	}else{
		window.history.back();
	}
}

/**
 * 向缓存对象中存入数据（lh.cache）
 * param:{url:'',paramObj:{},name:'',domId:'#xx'}（url,name是必须项）
 */
lh.addDataToCache = function(param){
	if(!param || !param.cacheName || !param.data)return;
	lh.cache[param.cacheName] = param.data;
}

/**
 * 从缓存对象中查询获取缓存的对象（复制一份返回）
 * name:使用存入缓存时使用的name做为访问key
 */
lh.getDataFromCache = function(cacheName){
	if(!cacheName)return;
	var data = lh.cache[cacheName];
	if(!data)return null;
	return _.cloneDeep(data);
}

/**
 * 防止重复提交
 * @param {防止重复提交的秒数，即多少秒内禁止重复提交} timeGap
 * @param {记录的操作时间，以此来判断是否超过指定秒数} timeRec
 * @return {成功则返回操作时间，否则返回false}
 */
lh.preventRepeat = function (timeRec, timeGap){
	if(!timeRec)timeRec = lh.status.optTimeRec;
	if(!timeGap)timeGap = lh.status.optTimeGap;
	var now = new Date(); 
	var hh = now.getHours(); 
	var mm = now.getMinutes(); 
	var ss = now.getSeconds(); 
	var sec = (hh * 3600) + (mm * 60) + ss; 
	if((sec - timeRec) > timeGap){//lh.status.optTimeGap:间隔秒数，指定秒内禁止重复提交 
		lh.status.optTimeRec = sec;
		return sec;
	}
	return false;
}

/** 页面底部加载数据 */
lh.scrollBottom = function (func){
	$(window).scroll(function () {
		if(scrollLoading)return;
		scrollLoading = true;
	    var scrollTop = $(this).scrollTop();
	    var scrollHeight = $(document).height();
	    var windowHeight = $(this).height();
	    if (scrollTop + windowHeight == scrollHeight) {
	    	//此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
			func();	
	    }else{
	    	scrollLoading = false;
	    }
	});
}

/** 检查返回登陆错误 */
lh.checkLoginError = function (){
	$.ajaxSetup({
		error:function(data, textStatus) {
			if(data && data.responseText=='toLogin'){
				window.parent.location.href=url;
			}
		}
	});
}

/** 格式化日期，参数：日期，时分-标识，秒-标识 */
lh.formatDate = function (param){
	if(!param)param = {};
	var obj = {
		date: param.date || new Date(), 
		format : param.format, 
		flag : param.flag || 'date'
	};
	if(!_.isDate(param.date))param.date = new Date(param.date);
	if(!obj.format){
		if(obj.flag == 'date'){
			obj.format = 'YYYY-MM-DD';
		}else if(obj.flag == 'datetime'){
			obj.format = 'YYYY-MM-DD hh:mm:ss';
		}
	}
	return moment(obj.date).format(obj.format);
}

/** 生成唯一序号（serial,id...） */
lh.createSerial = function (){
	return new Date().getTime() +'-'+ Math.floor(Math.random()*100000);
}

/** 初始化方法 */
lh.init = function (){
	
};

/** 默认执行内容 */
$(function() {
	lh.checkLoginError();
	
	/** 支持拖动：申明了draggable=true的DIV可直接拖动 */
	/*$('div[draggable=true]').drag(function( ev, dd ){
		$( this ).css({
			top: dd.offsetY,
			left: dd.offsetX
		});
	});*/
})

