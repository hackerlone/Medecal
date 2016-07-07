/**================================= 本模板中的拦截方法或回调方法，可根据实际业务需求扩展 开始 ========================================*/
/** 回调：打开新增窗口之后 执行此方法 */
function afterOpenAddWin(data, operation, isReadOnly){}

/** 回调：表单设值完成 执行此方法 */
function afterSetMainObj(data, operation, isReadOnly){}

/** 回调：打开窗口设置完成后 执行此方法 */
function afterOpenWin(data, operation, isReadOnly){}

/** 拦截：打开窗口之前 执行此方法，返回false则不打开窗口，不向下执行 */
function beforeOpenWin(data, operation, isReadOnly){return true;}

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
	lh.initConfig(lh.config);
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
function addMainObj(){
	openMainObjWin(-1, 'add');
}

/** 得到主对象的所有字段值 */
function getMainObj(id, operation){
	var mainObj = lh.getObjFromDom({inputsDom : lh.$mainObj});
	if(id){//有id为更新，无id为新增
		mainObj.id = id;
	}
	return mainObj;
}

/** 设置相关信息的字段值 */
function setMainObj(data){
	lh.setField({data : data, inputsDom : lh.$mainObj});
	//以下为只读字段：
}

/** 提交主对象数据 */
function saveMainObj(){
	if(lh.preventRepeat()){//防止重复提交
		lh.$mainObjForm.submit();
	}else{
		lh.showRepeatTip();//提示重复提交
	}
}

/** 关闭主对象窗口 */
function closeMainObjWin(){
	lh.$mainObjWin.window('close');
}

/** 打开主对象窗口 */
function openMainObjWin(index, operation){
	var rows,data;
	if(index >= 0){
		rows = lh.$grid.datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = lh.$mainObjWin;
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $form.form('enableValidation');
	       var flag = $form.form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var mainObj = getMainObj(id);//得到需要的字段信息
	       		
	       		if(preAddOrUpdate){//新增修改操作执行之前的拦截方法，返回false则不执行新增修改
	       			var flag = preAddOrUpdate(mainObj);
	       			if(!flag)return false;
	       		}
	       		
	       		lh.post('back', lh.config.addOrUpdateUrl, mainObj, function(rsp){
					 if(rsp.status=='success'){
						 lh.$grid.datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeMainObjWin();
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
		var flag = beforeOpenWin(data, operation, isReadOnly);
		if(!flag)return;
	}
	
	initFormComponent();//初始化组件
	lh.$mainObjWin.window('open');
	$form.form('clear');
	$form.form('disableValidation');
	lh.$mainObjTip.html('');
	
	if(operation == 'add'){//添加
		lh.$mainObjSave.show();
		lh.$mainObjReadOnlyTr.hide();//隐藏只读字段
		lh.$mainObj.textbox('readonly', false);//新增时设置为可编辑
	}else{//查看或修改
		lh.$mainObjReadOnlyTr.show();//显示只读字段
		var isReadOnly = false;
		if(operation == 'read'){//查看
			isReadOnly = true;
			lh.$mainObjSave.hide();
		}else{//修改
			lh.$mainObjSave.show();
		}
		lh.$mainObj.textbox('readonly', isReadOnly);//设置只读还是可编辑
		setMainObj(data, operation);//设置用户详细信息字段值
	}
	
	if(afterOpenWin){//拦截：打开添加窗口设置表单值完成后执行此方法
		afterOpenWin(data, operation, isReadOnly);
	}
}
