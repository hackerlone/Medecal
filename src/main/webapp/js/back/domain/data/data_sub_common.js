/**================================= 本模板中的拦截方法或回调方法，可根据实际业务需求扩展 开始 ========================================*/
/** 回调：打开新增窗口之后 执行此方法 */
function afterOpenAddWin(data, operation, isReadOnly){}

/** 回调：表单设值完成 执行此方法 */
function afterSetMainObj(data, operation, isReadOnly){}

/** 回调：打开窗口设置完成后 执行此方法 */
function afterOpenWin(lower, data, operation, isReadOnly){}

/** 拦截：打开窗口之前 执行此方法，返回false则不打开窗口，不向下执行 */
function beforeOpenWin(lower, data, operation, isReadOnly){return true;}

/** 拦截：新增修改操作执行之前 执行此方法，返回false则不执行新增修改 */
function beforeAddOrUpdate(mainObj){return true;}

/** 拦截：新增修改操作完成之后 执行此方法，返回false则不向下执行（刷新表格，或提示错误）*/
function afterAddOrUpdate(mainObj, rsp){return true;}

/** 拦截：执行查询之前 执行此方法，返回false则不执行查询 */
function beforeSearch(common_queryObj){return true;}

/** 拦截：执行清除查询之前 执行此方法，返回false则不执行清除查询 */
function beforeClearSearch(){return true;}
/**================================= 本模板中的拦截方法或回调方法，可根据实际业务需求扩 结束 ========================================*/


/** 基础数据和基础设置 */
lh.config = {

}

$(function() {
	//lh.initConfig(lh.config);
	//loadGrid();
	//searchByParamJsonStr();
});


/** 获取参数,判断是否执行默认查询 */
function searchByParamJsonStr() {
	if (lh.paramJsonStr) {
		lh.paramJson = JSON.parse(lh.paramJsonStr);
		if (lh.config.isSearchByParamJson) {
			var obj = lh.paramJson;
			//$('#sc_type').combobox('setValue', obj.typeId);
			doSearch();
		}
	}
}

/**根据查询条件查询匹配的数据*/
function doSearch(){
	var common_queryObj = lh.getQueryObj({inputsDom : lh.$mainQuery});
	lh.$grid.datagrid('load', common_queryObj);  
}

/**重置查询查询条件*/
function clearSearch(){
	lh.commonClearSearch({inputsDom : lh.$mainQuery});
}

/**添加数据*/
function addMainObj(lower){
	openMainObjWin(lower, -1, 'add');
}

/** 得到主对象的所有字段值 */
function getMainObj(lower, id, operation){
	var mainObj = lh.getObjFromDom({inputsDom : lh[lower].$mainObj});
	if(id){//有id为更新，无id为新增
		mainObj.id = id;
	}
	return mainObj;
}

/** 设置相关信息的字段值 */
function setMainObj(lower, data, operation){
	lh.setField({data : data, inputsDom : lh[lower].$mainObj});
	//以下为只读字段：
}

/** 提交主对象数据 */
function saveMainObj(lower){
	if(lh.preventRepeat()){//防止重复提交
		lh[lower].$mainObjForm.submit();
	}else{
		lh.showRepeatTip();//提示重复提交
	}
}

/** 关闭主对象窗口 */
function closeMainObjWin(lower){
	lh[lower].$mainObjWin.window('close');
}

/** 打开主对象窗口 */
function openMainObjWin(lower, index, operation){
	var rows,data;
	if(index >= 0){
		rows = lh[lower].$grid.datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = lh[lower].$mainObjWin;
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $form.form('enableValidation');
	       var flag = $form.form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var mainObj = getMainObj(lower, id);//得到需要的字段信息
	       		
	       		if(preAddOrUpdate){//新增修改操作执行之前的拦截方法，返回false则不执行新增修改
	       			var flag = preAddOrUpdate(lower, mainObj);
	       			if(!flag)return;
	       		}
	       		
	       		lh.post('back', lh[lower].config.addOrUpdateUrl, mainObj, function(rsp){
					 if(rsp.status=='success'){
						 lh[lower].$grid.datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeMainObjWin(lower);
				       	 },500);
					 }else{
						 $.messager.alert('提示',rsp.msg);
					 }
				});
	       }
	       return false;
	    }
	});
	
	if(beforeOpenWin){//拦截：打开添加窗口之前执行此方法
		var flag = beforeOpenWin(lower, data, operation, isReadOnly);
		if(!flag)return;
	}
	
	initFormComponent(lower);//初始化组件
	lh[lower].$mainObjWin.window('open');
	$form.form('clear');
	$form.form('disableValidation');
	lh[lower].$mainObjTip.html('');
	
	if(operation == 'add'){//添加
		lh[lower].$mainObjSave.show();
		lh[lower].$mainObjReadOnlyTr.hide();//隐藏只读字段
		lh[lower].$mainObj.textbox('readonly', false);//新增时设置为可编辑
	}else{//查看或修改
		lh[lower].$mainObjReadOnlyTr.show();//显示只读字段
		var isReadOnly = false;
		if(operation == 'read'){//查看
			isReadOnly = true;
			lh[lower].$mainObjSave.hide();
		}else{//修改
			lh[lower].$mainObjSave.show();
		}
		lh[lower].$mainObj.textbox('readonly', isReadOnly);//设置只读还是可编辑
		setMainObj(lower, data, operation);//设置用户详细信息字段值
	}
	
	if(afterOpenWin){//拦截：打开添加窗口设置表单值完成后执行此方法
		afterOpenWin(lower, data, operation, isReadOnly);
	}
}

/** 通用批量删除 */
function batchDelete(lower, ids) {
	var $grid = lh[lower].$grid;
	var url = lh[lower].config.batchDeleteUrl;
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
function batchRecover(lower, ids) {
	var $grid = lh[lower].$grid;
	var url = lh[lower].config.batchRecoverUrl;
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
function batchThoroughDelete(lower, ids) {
	var $grid = lh[lower].$grid;
	var url = lh[lower].config.batchThoroughDeleteUrl;
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



lh.initSubConfig = function (config){
	var upper = config.subObjUpperName;
	var lower = config.subObjLowerName;
	
	var lowerObj = {};
	
	lowerObj.config = {
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
		gridDomSelector : config.gridDomSelector || '#'+lower+'_datagrid',
		gridDivDomSelector : config.gridDivDomSelector || '#'+lower+'_datagrid_div',
		mainObjForm : config.mainObjForm || '#'+lower+'ObjForm',
		mainObjWin : config.mainObjWin || '#'+lower+'ObjWin',
		mainObjTip : config.mainObjTip || '#'+lower+'ObjTip',
		mainObjSave : config.mainObjSave || '#'+lower+'ObjSave',
		gridSwitchShowField : config.gridSwitchShowField || 'deletedAt,deletedBy',
		mainObjReadOnlyTr : config.mainObjReadOnlyTr || '#'+lower+'ObjTable .readOnlyTr',
		mainObjDomSelector : config.mainObjDomSelector || '#'+lower+'ObjTable .domain-input',
		mainQueryDomSelector : config.mainQueryDomSelector || '#'+lower+'QueryTable .domain-input'
	}
	
	lowerObj.$grid = $(lowerObj.config.gridDomSelector);
	lowerObj.$mainObj = $(lowerObj.config.mainObjDomSelector);
	lowerObj.$mainQuery = $(lowerObj.config.mainQueryDomSelector);
	lowerObj.$mainObjForm = $(lowerObj.config.mainObjForm);
	lowerObj.$mainObjWin = $(lowerObj.config.mainObjWin);
	lowerObj.$mainObjTip = $(lowerObj.config.mainObjTip);
	lowerObj.$mainObjSave = $(lowerObj.config.mainObjSave);
	lowerObj.$mainObjReadOnlyTr = $(lowerObj.config.mainObjReadOnlyTr);
	
	lh[lower] = lowerObj;
}
