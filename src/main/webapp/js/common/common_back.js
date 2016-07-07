/** 日期：2015年7月21日15:12:34 作者：虞荣华 描述：后台通用辅助JS（easyui版） */

lh = lh || {};
$(function() {
	//isBackLogin();
});

/** datagrid表格相关数据 */
lh.grid = {
	pageSize : 50,
	pageList : [ 10, 20, 30, 50, 80, 100, 200 ]
};

lh.config = {};

/** 页面跳转easyui-tab */
lh.jumpToTab = function () {

}

/** 页面新打开easyui-tab */
lh.openNewTab = function (url) {

}

lh.initConfig = function (config){
	if(!config || !config.mainObjLowerName || !config.mainObjUpperName){
		$.messager.alert('提示', '基本设置参数对象不能为空');return;
	}
	var upper = config.mainObjUpperName;
	var lower = config.mainObjLowerName;
	lh.config = {
		mainObj : config.mainObj || {},
		mainObjUpperName : upper,
		mainObjLowerName : lower,
		gridUrl : config.gridUrl || '/back/get'+upper+'List',
		addOrUpdateUrl : config.addOrUpdateUrl || '/back/addOrUpdate'+upper,
		batchDeleteUrl : config.batchDeleteUrl || '/back/update'+upper+'Delete',
		batchThoroughDeleteUrl : config.batchThoroughDeleteUrl || '/back/delete'+upper+'Thorough',
		batchRecoverUrl : config.batchRecoverUrl || '/back/update'+upper+'Recover',
		queryObj : config.queryObj || null,
		isSearchByParamJson : config.isSearchByParamJson || false,
		gridDomSelector : config.gridDomSelector || '#datagrid',
		gridDivDomSelector : config.gridDivDomSelector || '#datagrid_div',
		mainObjForm : config.mainObjForm || '#mainObjForm',
		mainObjWin : config.mainObjWin || '#mainObjWin',
		mainObjTip : config.mainObjTip || '#mainObjTip',
		mainObjSave : config.mainObjSave || '#mainObjSave',
		gridSwitchShowField : config.gridSwitchShowField || 'deletedAt,deletedBy',
		mainObjReadOnlyTr : config.mainObjReadOnlyTr || '#mainObjTable .readOnlyTr',
		mainObjDomSelector : config.mainObjDomSelector || '#mainObjTable .domain-input',
		mainQueryDomSelector : config.mainQueryDomSelector || '#mainQueryTable .domain-input'
	}
	
	lh.$grid = $(lh.config.gridDomSelector);
	lh.$mainObj = $(lh.config.mainObjDomSelector);
	lh.$mainQuery = $(lh.config.mainQueryDomSelector);
	lh.$mainObjForm = $(lh.config.mainObjForm);
	lh.$mainObjWin = $(lh.config.mainObjWin);
	lh.$mainObjTip = $(lh.config.mainObjTip);
	lh.$mainObjSave = $(lh.config.mainObjSave);
	lh.$mainObjReadOnlyTr = $(lh.config.mainObjReadOnlyTr);
}

lh.filtGridOperation = function (){
	var fieldStr = lh.config.gridSwitchShowField;
	if(lh.status.trashShow){
   		$('.opt_alive').hide();
   		$('.opt_trash').show();
   		if(fieldStr){
   			var gridDiv = lh.config.gridDivDomSelector;
   			var fieldAry = fieldStr.split(',');
   			for(var i in fieldAry){
   				var selector = gridDiv+' td[field="'+fieldAry[i]+'"]';
   				$(selector).show();
   			}
   		}
   		//lh.status.trashShow = false;
   	}else{
   		$('.opt_alive').show();
   		$('.opt_trash').hide();
   		if(fieldStr){
   			var gridDiv = lh.config.gridDivDomSelector;
   			var fieldAry = fieldStr.split(',');
   			for(var i in fieldAry){
   				var selector = gridDiv+' td[field="'+fieldAry[i]+'"]';
   				$(selector).hide();
   			}
   		}
   		//lh.status.trashShow = true;
   	}
}

lh.getInputsDom = function (param){
	var inputsDom;
	if(!param){
		return;
	}else if(param.inputsDom){
		inputsDom = param.inputsDom;
	}else if(param.domSelector){
		inputsDom = $(param.domSelector);
	}
	return inputsDom;
}

/** 从Dom集合中获取input数据组装成对象 */
lh.getObjFromDom = function (param){
	var inputsDom = lh.getInputsDom(param);
	if(param.baseObj){
		var obj = param.baseObj;
	}else{
		var obj = {};
	}
	for(var i in inputsDom){
		var input = inputsDom[i];
		if(!input || !input.id || !input.attributes || !input.attributes.role)continue;
		var id = input.id;
		var role = input.attributes.role.nodeValue;
		//var field = id.replace('sc_','').replace('f_','');
		var field = id.substring(id.lastIndexOf('_')+1, id.length);
		var domObj = $("#"+id);
		if(role == 'textbox'){
			obj[field] = domObj.textbox('getValue');
		} else if (role == 'combobox') {
			obj[field] = domObj.combobox('getValue');
		} else if (role == 'numberbox') {
			obj[field] = domObj.numberbox('getValue');
		} else if (role == 'datetimebox') {
			obj[field] = domObj.datetimebox('getValue');
		} else if (role == 'datebox') {
			obj[field] = domObj.datebox('getValue');
		}
	}
	return obj;
}

lh.getQueryObj = function (){
	if(lh.config && lh.config.queryObj){
		return lh.getObjFromDom({baseObj:lh.config.queryObj, inputsDom : lh.$mainQuery});
	} else{
		return lh.getObjFromDom({inputsDom : lh.$mainQuery});
	}
}

lh.commonClearSearch = function (param){
	lh.clearField(param);
}



lh.clearField = function (param){
	if(!param || !param.inputsDom)return;
	var inputsDom = param.inputsDom;
	for(var i in inputsDom){
		var input = inputsDom[i];
		if(!input || !input.id || !input.attributes || !input.attributes.role)continue;
		var id = input.id;
		var role = input.attributes.role.nodeValue;
		var domObj = $("#"+id);
		if(role == 'textbox'){
			domObj.textbox('clear');
		} else if (role == 'combobox') {
			domObj.combobox('clear');
		} else if (role == 'numberbox') {
			domObj.numberbox('clear');
		} else if (role == 'datetimebox') {
			domObj.datetimebox('clear');
		} else if (role == 'datebox') {
			domObj.datebox('clear');
		}
	}
}

lh.setField = function (param) {
	var data = param.data;
	if(!data)return;
	var inputsDom = lh.getInputsDom(param);
	for(var i in inputsDom){
		var input = inputsDom[i];
		if(!input || !input.id || !input.attributes || !input.attributes.role)continue;
		var id = input.id;
		var role = input.attributes.role.nodeValue;
		//var field = id.replace('sc_','').replace('f_','');
		var field = id.substring(id.lastIndexOf('_')+1, id.length);
		var domObj = $("#"+id);
		var fieldValue = data[field];
		if(role == 'textbox'){
			domObj.textbox('setValue', fieldValue);
		} else if (role == 'combobox') {
			domObj.combobox('setValue', fieldValue);
		} else if (role == 'numberbox') {
			domObj.numberbox('setValue', lh.formatNumber(fieldValue));
		} else if (role == 'datetimebox') {
			domObj.datetimebox('setValue', lh.formatDate(fieldValue));
		} else if (role == 'datebox') {
			domObj.datebox('setValue', lh.formatDate(fieldValue));
		}
	}
}

lh.showRepeatTip = function (tip){
	if(!tip)tip = '请不要重复提交数据';
	$.messager.alert('提示', tip);
}


lh.setDomIsReadOnly = function (param){
	var inputsDom = lh.getInputsDom(param);
	if(!param || !param.isReadOnly){
		param = {isReadOnly:true};
	}
	inputsDom.textbox('readonly',param.isReadOnly);
}

/**
 * 加载下拉列表的数据并缓存，方便复用
 * param:{url:'',paramObj:{},cacheName:'',domId:'#xx'}（url,name是必须项）
 */
lh.loadComboDataToCache = function(param){
	if(!param || !param.url || !param.cacheName)return;
	if(!param.paramObj)param.paramObj = null;
	var cacheData = lh.getDataFromCache(param.cacheName);//先从缓存中读取数据，存在即返回，不存在则选择加载
	if(cacheData){
		if(param.domId){
			$(param.domId).combobox('loadData', _.cloneDeep(rsp));
		}
		return cacheData;
	}
	
	lh.post('back', param.url, param.paramObj, function(rsp){
		lh.cache[param.cacheName] = rsp;
		if(param.domId){
			$(param.domId).combobox('loadData', _.cloneDeep(rsp));
		}
	});
}

/**
 * datagrid加载成功后执行的方法：1显示隐藏对应操作按钮，2初始化复制组件，3将加载的数据存入缓存（对象名+List：dictList）
 */
lh.onGridLoadSuccess = function(data, param){
	lh.filtGridOperation();
	lh.clipboard();//复制功能
	var lowerName = lh.config.mainObjLowerName;
	if(lowerName){
		var cacheName = lowerName+'List';
		lh.addDataToCache({cacheName:cacheName, cacheData:data});
	}
}

/** ------------------------------------------------- */

/** iframe子页面调用父页面的 显示子页面 方法(easyui tabs) */
lh.subShowMain = function (name, path) {
	window.parent.showMain(name, path);
}

/** 后台登陆检查 */
lh.isBackLogin = function isBackLogin() {
	$.ajaxSetup({
		error : function(data, textStatus) {
			if (data && data.responseText == 'toBackLogin') {
				window.parent.location.href = "/back/login";
			}
		}
	});
}

lh.backLoginCheck = function (rsp, successFun, failureFun) {
	if (rsp == 'toBackLogin') {
		$.messager.alert('提示', '会话过期，正跳转到登陆页面...');
		setTimeout(function() {
			window.parent.location.href = '/back/login';
			return;
		}, 2000);
	}
}

lh.backDatagridErrorCheck = function (rsp, successFun, failureFun) {
	if (rsp.responseText == 'toBackLogin') {
		$.messager.alert('提示', '会话过期，正跳转到登陆页面...');
		setTimeout(function() {
			window.parent.location.href = '/back/login';
			return;
		}, 2000);
	}
}

lh.formatGridDate = function (value){
	return !value ? '' : lh.formatDate({date : new Date(value)})
}

/** 从easyui的rows中得到ids,参数：rows */
lh.getIdsFromRows = function (rows) {
	var ids = '';
	for (var i = 0, len = rows.length; i < len; i++) {
		ids += ',' + rows[i].id;
	}
	if (ids) {
		ids = ids.substring(1);
	}
	return ids;
}

/** 下拉列表默认过滤器 */
lh.comboboxDefaultFilter = function (q, row){
	var opts = $(this).combobox('options');
	return row[opts.textField].toLowerCase().indexOf(q.toLowerCase()) > -1;
}

/** 通用批量删除 */
lh.commonBatchDelete = function (ids) {
	var $grid = lh.$grid;
	var url = lh.config.batchDeleteUrl;
	if(!ids){
		var rows = $grid.datagrid('getChecked');// 得到选择的记录，可多条
		if (!rows || !rows.length) {// 如果有选择的记录,rows.length为选中记录的条数
			$.messager.alert('提示', '请勾选数据');return;
		}
	}
	$.messager.confirm('提示', '是否确认删除？', function(r) {
		if (r) {
			ids = ids || lh.getIdsFromRows(rows);// common_back:从rows中得到ids
			$.post(url, {ids : ids}, function(rsp) {
				var status = rsp.status;
				var msg = rsp.msg;
				if (status == 'success') {
					$grid.datagrid('reload');
					$grid.datagrid('clearSelections');
					$grid.datagrid('clearChecked');
					if (msg)
						$.messager.alert('提示', msg);
				} else if (status = 'failure') {
					if (msg)
						$.messager.alert('提示', msg);
				} else {
					$.messager.alert('提示', '删除失败');
				}
			}, 'json');
		}
	});
}

/** 通用批量恢复 */
lh.commonBatchRecover = function (ids) {
	var $grid = lh.$grid;
	var url = lh.config.batchRecoverUrl;
	if(!ids){
		var rows = $grid.datagrid('getChecked');// 得到选择的记录，可多条
		if (!rows || !rows.length) {// 如果有选择的记录,rows.length为选中记录的条数
			$.messager.alert('提示', '请勾选数据');return;
		}
	}
	$.messager.confirm('提示', '是否确认还原？', function(r) {
		if (r) {
			ids = ids || lh.getIdsFromRows(rows);// common_back:从rows中得到ids
			$.post(url, {ids : ids}, function(rsp) {
				var status = rsp.status;
				var msg = rsp.msg;
				if (status == 'success') {
					$grid.datagrid('reload');
					$grid.datagrid('clearSelections');
					$grid.datagrid('clearChecked');
					if (msg)
						$.messager.alert('提示', msg);
				} else if (status = 'failure') {
					if (msg)
						$.messager.alert('提示', msg);
				} else {
					$.messager.alert('提示', '还原失败');
				}
			}, 'json');
		}
	});

}

/** 通用强制删除 */
lh.commonBatchThoroughDelete = function (ids) {
	var $grid = lh.$grid;
	var url = lh.config.batchThoroughDeleteUrl;
	if(!ids){
		var rows = $grid.datagrid('getChecked');// 得到选择的记录，可多条
		if (!rows || !rows.length) {// 如果有选择的记录,rows.length为选中记录的条数
			$.messager.alert('提示', '请勾选数据');return;
		}
	}
	$.messager.confirm('提示', '是否确认进行彻底删除？', function(r) {
		if (r) {
			ids = ids || lh.getIdsFromRows(rows);// common_back:从rows中得到ids
			$.post(url, {ids : ids}, function(rsp) {
				var status = rsp.status;
				var msg = rsp.msg;
				if (status == 'success') {
					$grid.datagrid('reload');
					$grid.datagrid('clearSelections');
					$grid.datagrid('clearChecked');
					if (msg)
						$.messager.alert('提示', msg);
				} else if (status = 'failure') {
					if (msg)
						$.messager.alert('提示', msg);
				} else {
					$.messager.alert('提示', '彻底删除失败');
				}
			}, 'json');
		}
	});
}

/** 通用显示回收站页面 */
lh.commonShowTrash = function () {
	lh.status.trashShow = true;
	if (lh.config.queryObj) {
		lh.config.queryObj.deleted = 1;
	} else {
		lh.config.queryObj = {deleted : 1}
	}
	$('#opt_outer_div button[role="opt_1"]').hide();
	$('#opt_outer_div button[role="opt_2"]').show();
	lh.$grid.datagrid('load', lh.config.queryObj);
}

/** 通用从回收站页面返回对应主页面 */
lh.commonReturnBack = function () {
	lh.status.trashShow = false;
	if (lh.config.queryObj) {
		delete lh.config.queryObj.deleted;
	} else {
		lh.config.queryObj = {}
	}
	$('#opt_outer_div button[role="opt_1"]').show();
	$('#opt_outer_div button[role="opt_2"]').hide();
	lh.$grid.datagrid('load', lh.config.queryObj);
}

/** 复制 * */
lh.clipboard = function () {
	var clipboard = new Clipboard('.copy_btn');
	clipboard.on('success', function(e) {
		console.info('Action:', e.action);
		console.info('Text:', e.text);
		console.info('Trigger:', e.trigger);
		$.messager.show({
			title : '提示',
			msg : '已成功复制：' + e.text,
			timeout : 2000,
			showType : 'slide'
		});
	});

}

lh.commonSearchBySerial = function (domId, cnName, url, baseFun, successFun) {
	if (!domId)
		domId = '#f_serialSearch';
	var serial = $(domId).textbox('getValue');
	if (!serial && !serial.trim()) {
		$.messager.alert('提示', '请输入' + cnName + '编号');
		return;
	}
	serial = serial.trim();
	$.messager.progress({
		title : '提示',
		msg : '',
		text : '正在努力查询中,请稍候...'
	});
	$.post(url, {
		serial : serial
	}, function(rsp) {
		$.messager.progress('close');
		if (rsp.status == 'success') {
			var rows = rsp.rows;
			if (!rows || rows.length <= 0) {
				$.messager.alert('提示', '数据库中不存在该' + cnName + '编号');
			} else if (rows.length > 1) {
				$.messager.alert('提示', '该' + cnName + '编号在数据库中对应多个' + cnName
						+ '');
			} else {
				var data = rows[0];
				if (successFun) {
					successFun(data);// 查询成功后执行回调方法
				} else {// 如果没有转递回调方法，就执行默认的设值
					baseFun(data);
				}
			}
		} else {
			$.messager.alert('提示', rsp.msg);
		}
	}, 'json');
}

lh.searchUserBySerial = function (successFun, domId) {
	function baseFun(user) {
		$('#f_serial').textbox('setValue', user.serial);
		$('#f_username').textbox('setValue', user.username);
		$('#f_realName').textbox('setValue', user.realName);
		$('#f_userId').val(user.id);
		$('#f_shopId').val(user.shopId);
	}
	commonSearchBySerial(domId, '用户', '/back/getUserList', baseFun, successFun);
}

lh.searchInstBySerial = function (successFun, domId) {
	function baseFun(inst) {
		$('#f_serial').textbox('setValue', inst.serial);
		$('#f_instName').textbox('setValue', inst.name);
		$('#f_username').textbox('setValue', inst.username);
		$('#f_userId').val(inst.userId);
		$('#f_instId').val(inst.id);
	}
	commonSearchBySerial(domId, '机构', '/back/getAuctionInstList', baseFun,
			successFun);
}
lh.searchAuctionQuickInstBySerial = function (successFun, domId) {
	function baseFun(inst) {
		$('#f_serial').textbox('setValue', inst.serial);
		$('#f_instName').textbox('setValue', inst.name);
		$('#f_username').textbox('setValue', inst.username);
		$('#f_userId').val(inst.userId);
		$('#f_instId').val(inst.id);
	}
	commonSearchBySerial(domId, '机构', '/back/getAuctionQuickInstList', baseFun,
			successFun);
}

lh.searchShopBySerial = function (successFun, domId) {
	function baseFun(shop) {
		$('#f_serial').textbox('setValue', shop.serial);
		$('#f_shopName').textbox('setValue', shop.name);
		$('#f_userName').textbox('setValue', shop.userName);
		$('#f_userId').val(shop.userId);
		$('#f_shopId').val(shop.id);
	}
	commonSearchBySerial(domId, '店铺', '/back/getShopList', baseFun, successFun);
}

lh.searchGoodsBySerial = function (successFun, domId) {
	function baseFun(goods) {
		$('#f_serial').textbox('setValue', goods.serial);
		$('#f_goodsName').textbox('setValue', goods.goodsName);
		$('#f_userId').val(goods.userId);
		$('#f_goodsId').val(goods.id);
	}
	commonSearchBySerial(domId, '商品', '/back/getGoodsList', baseFun, successFun);
}

lh.searchAlbumBySerial = function (successFun, domId) {
	function baseFun(album) {
		$('#f_serial').textbox('setValue', album.serial);
		$('#f_albumName').textbox('setValue', album.title);
		$('#f_userId').val(goods.userId);
		$('#f_albumId').val(album.id);
	}
	commonSearchBySerial(domId, '相册', '/back/getAlbumList', baseFun, successFun);
}

/**
 * 描述：公用JS
 * 日期：2013年12月30日9:50:22
 */
LOADEDGRID = [];
/**
 * 判断用户是否登陆
 */
function isLogin(){
	$.ajaxSetup({
		error:function(data, textStatus) {
			if(data&&data.responseText=='toLogin'){
				window.parent.location.href="/index";
			}
		}
	});
}

/**
 * 描述：将简写的Json转换为Tree的标准Json格式
 * 作者：虞荣华
 * 日期：2013年12月30日9:50:22
 * @param rows:简写的json数据
 * @return 标准的json数据
 */

function convertTreeJsonData(rows){
	var rows = rows.data;//data才是实际数据 -- 暂时注释掉
    function exists(rows, parentId){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentId) return true;
        }
        return false;
    }
    
    var nodes = [];
    // 得到根节点们
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        if (!exists(rows, row.parentId)){
        	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
            var root = {id:row.id,text:row.name,checked:row.checked,iconCls:row.iconCls,attr:attr};
            nodes.push(root);
        }
    }
    
    var toDo = [];
    for(var i=0; i<nodes.length; i++){
        toDo.push(nodes[i]);
    }
    while(toDo.length){
        var node = toDo.shift();    // 父节点
        // 得到子节点们
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (row.parentId == node.id){
            	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
                var child = {id:row.id,text:row.name,checked:row.checked,iconCls:row.iconCls,attr:attr};
                if (node.children){
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}

/**
 * 描述：将简写的Json转换为Treegrid的标准Json格式
 * 作者：王玉川
 * 日期：2014年1月27日
 * @param rows:简写的json数据
 * @return 标准的json数据
 */
function convertTreeGridJsonData(rows){
	var rows = rows.data;//data才是实际数据 -- 暂时注释掉
    function exists(rows, parentId){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentId) return true;
        }
        return false;
    }
    
    var nodes = [];
    // 得到根节点们
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        if (!exists(rows, row.parentId)){
        	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
            var root = {id:row.id,text:row.deptName,higherDept:row.higherDept,deptNote:row.deptNote,orderStatus:row.orderStatus,
            		businessId:row.businessId,business:row.business,deptTypeId:row.deptTypeId,
            		deptType:row.deptType,foundId:row.foundId,founder:row.founder,foundTime:row.foundTime,
            		editId:row.editId,editor:row.editor,editTime:row.editTime,remark:row.remark,attr:attr};
            nodes.push(root);
        }
    }
    
    var toDo = [];
    for(var i=0; i<nodes.length; i++){
        toDo.push(nodes[i]);
    }
    while(toDo.length){
        var node = toDo.shift();    // 父节点
        // 得到子节点们
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (row.parentId == node.id){
            	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
                var child = {id:row.id,text:row.deptName,higherDept:row.higherDept,deptNote:row.deptNote,orderStatus:row.orderStatus,
                		businessId:row.businessId,business:row.business,deptTypeId:row.deptTypeId,
                		deptType:row.deptType,foundId:row.foundId,founder:row.founder,foundTime:row.foundTime,
                		editId:row.editId,editor:row.editor,editTime:row.editTime,remark:row.remark,attr:attr};
                if (node.children){
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}

/**判断按钮的显隐性
 * 参数：buttonIds：以逗号分隔的按钮ID字符串(对应数据库中的ID)
 * 参数：buttonDomIds：以逗号分隔的按钮ID名字字符串(对应页面中的ID值)
 * 这两个参数中的值对应的按钮顺序应该一致
 * 如：  buttonIds='1,2,3'
 * 		buttonDomIds='selfDispatchBtnId,其他按钮ID'
 * */
function isAllowedToShowButtons(buttonIds,buttonDomIds){
	if(!buttonIds || !buttonDomIds){return;}
	$.post('/CRMTWO/user_menuAction_isAllowedToShowButtons',{buttonIds:buttonIds},function(rsp){
	     if(rsp.status=='success'){
	     	var isShowBtnStr = rsp.isShowBtnStr;
	     	if(isShowBtnStr){
	     		var buttonDomIdsAry = buttonDomIds.split(',');//分割多个按钮
	     		var isShowBtnAry = isShowBtnStr.split(',');
	     		for(var i in isShowBtnAry){
	     			if(isShowBtnAry[i]&&isShowBtnAry[i]=="1"){$('#'+buttonDomIdsAry[i]).css('display','');}
	     		}
	     	}
		 }
	},'json');
}

/**渲染表格
 * 调整表格长度，修改分页栏
 * 参数：dataGridOuterDivId：表格的外层的某一个div id;
 * */
function renderDataGrid(dataGridOuterDivId){
	var width = $('#'+dataGridOuterDivId+' .datagrid-view').css('width');  
	$('#'+dataGridOuterDivId+' .datagrid-btable').css('width',width);
	$('#'+dataGridOuterDivId+' .datagrid-view2 .datagrid-htable').attr('style',"width:"+width+"; table-layout: auto;");
	
//	setTimeout(function(){
//		var text = $('#'+dataGridOuterDivId+' .pagination-info').html();
//		text = text.replace(/\d+/g,function($1){return ' <font color="chocolate">'+$1+'</font> '});
//		$('#'+dataGridOuterDivId+' .pagination-info').html(text);
//	},0);
	$('#'+dataGridOuterDivId+' .note').tooltip({//悬浮窗提示  
                onShow: function(){  
                    $(this).tooltip('tip').css({   
                        backgroundColor: 'white',
            	 		borderColor: 'gray'	
                    });  
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

/**验证数字
 * inputDomId:需要验证的输入框的ID
 * */
function validateNum(inputDomId){
	if(inputDomId && (typeof inputDomId =="string")){
		var value = $('#'+inputDomId).val();
		var flag = /^[0-9]+\.?[0-9]{0,9}$/i.test(value);
		if(!flag){
			$('#'+inputDomId).val('');
		}
	}
}
/**验证数字
 * 只能输入正整数 
 * inputDomId:需要验证的输入框的ID
 * */
function validateNums(inputDomId){
	if(inputDomId && (typeof inputDomId =="string")){
		var value = $('#'+inputDomId).val();
		var flag = /^[+]?[1-9]+\d*$/i.test(value);
		if(!flag){
			$('#'+inputDomId).val('');
		}
	}
}

/**session过期返回登录页面*/
$.fn.datagrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
	onLoadError: function(data){
		if(data&&data.responseText=='toLogin'){
			window.parent.location.href="/index";
		}
	}
});
/**默认渲染分页*/
/*$.fn.pagination.defaults = $.extend({}, $.fn.pagination.defaults, {
	beforePageText: '第',//页数文本框前显示的汉字
    afterPageText: '页    共 {pages} 页'
//	displayMsg : "显示 <font color='chocolate'>{from}</font> 到 <font color='chocolate'>{to}</font>, 共 <font color='chocolate'>{total}</font> 记录"
});*/

