/** 基础数据和基础设置 */
lh.config = {
	mainObjLowerName : 'dict',
	mainObjUpperName : 'Dict',
}

$(function() {
	loadGrid();
	initQueryComponent();
	initComboData();
});

/** 加载主表格 */
function loadGrid(){
	lh.$grid.datagrid({
	    loadMsg:'',
		idField:'id',
		sortName:'id',
		sortOrder:'desc',
		striped:true,
		fitColumns:false,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		pagination:true,
		url:lh.config.gridUrl,
	    queryParams:lh.config.queryObj,//查询参数
	    pageSize:lh.grid.pageSize,//每页数据条数
	    pageList:lh.grid.pageList,//每页数据条数选择数组
	    width:lh.dom.clientSafeWidth-1,
	    height:lh.dom.clientHeight-125,
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
			{field:'parentCodeName',title:'所属上级',width:160,align:'center'},
	        {field:'code',title:'代码',width:160,align:'center'},
	        {field:'codeName',title:'代码名称',width:160,align:'center'},
	        {field:'dictValue',title:'值',width:200,align:'center'},
	        {field:'operate',title:'操作',width:200,align:'center',formatter: function(value,row,index){
	        	var dom = '';
	        	if(!row.authority || row.authority != 3){
	        		dom = '<span class="opt_alive"><span style="cursor: pointer;color: #EC4949" onclick="openMainObjWin('+index+',\'update\')">修改</span>&nbsp;|&nbsp;';
	        	}
	        	dom +=
	        	'<span class="update" style="cursor: pointer;color: green" onclick="openMainObjWin('+index+',\'read\')">查看</span></span>'
	        	+'<span class="opt_trash"><span style="cursor: pointer;color: #EC4949;" onclick="lh.commonBatchThoroughDelete('+row.id+')">彻底删除</span>'
	        	+'&nbsp;|&nbsp;<span style="cursor: pointer;color: green" onclick="lh.commonBatchRecover('+row.id+')">恢复</span></span>';
	        	return dom;
	        }},
	        {field:'dictLevel',title:'等级',width:100,align:'center',formatter: function(value,row,index){
	        	return value == 2 ? '系统级' : '<span style="color:green;">业务级</span>';
	        }},
	        {field:'authority',title:'权限',width:100,align:'center',formatter: function(value,row,index){
	        	return value == 3 ? '只读' : (value == 2 ? '<span style="color:green;">修改删除</span>' : '<span style="color:green;">修改</span>');
	        }},
	        {field:'expandable',title:'是否允许扩展子节点',width:120,align:'center',formatter: function(value,row,index){
	        	return value == 2 ? '否' : '<span style="color:green;">是</span>';
	        }},
	        {field:'deletedBy',title:'删除人',width:120,align:'center'},
	        {field:'deletedAt',title:'删除时间',width:120,align:'center',formatter: function(value,row,index){
	        	return lh.formatGridDate(value);
	        }},
	        {field:'updatedBy',title:'修改人',width:120,align:'center'},
	        {field:'updatedAt',title:'修改时间',width:120,align:'center',formatter: function(value,row,index){
	        	return lh.formatGridDate(value);
	        }},
	        {field:'createdBy',title:'创建人',width:120,align:'center'},
	        {field:'createdAt',title:'创建时间',width:120,align:'center',formatter: function(value,row,index){
	        	return lh.formatGridDate(value);
	        }}
	    ]],
	    /*,onSelect:function(rowIndex, rowData){onClickRowOfGrid(rowIndex, rowData);},
	    onClickRow: function(index, row){onClickRowOfGrid(index, row);},
	    loadFilter: function(data){return loadFilterOfGrid(data);}*/
        onLoadError: function(data){
        	lh.onGridLoadError(data);
	    },
	    onDblClickRow: function(index, row){
	    	lh.onGridDblClickRow(index, row);
        },
	    onLoadSuccess:function(data){
	    	lh.onGridLoadSuccess(data);
	   }  
	});
}

/** 初始化下拉列表数据，存入缓存，便于复用 */
function initComboData(){
	lh.loadComboDataToCache({url:'/back/getRootDictArray',cacheName:'rootDictArray', domId:'#sc_parentCode'});
}

/** 初始化查询条件中的组件及数据 */
function initQueryComponent(){
	$('#sc_parentCode').combobox({
		valueField : 'code',
		textField : 'codeName',
		editable : false,
		multiple : false,
	    required : false,
	    panelHeight : 200,
	    data:lh.getDataFromCache('rootDictArray')
	    //url:'/back/getRootDictArray'
	});
}

/** 初始化表单中的组件及数据 */
function initFormComponent(){
	$('#f_parentCode').combobox({
		valueField : 'code',
		textField : 'codeName',
		editable : false,
		multiple : false,
	    required : true,
	    panelHeight : 200,
	    data:lh.getDataFromCache('rootDictArray')
	    //url:'/back/getRootDictArrayForExpand'
	});
}

function preAddOrUpdate(){
	return true;
}


/**================================= 模板JS中的拦截方法或回调方法，根据实际业务需求扩展 开始 ========================================*/

//具体扩展方法列表，请查看 /js/common/back_template.js 文件中的顶部说明
//示例：function afterAddOrUpdate(mainObj, rsp){return true;} //执行新增或修改之前的拦截方法，返回false则不执行新增修改
//使用：将需要扩展的方法复制到对应业务JS文件中，在扩展方法体内执行自定义业务逻辑。

/**================================= 模板JS中的拦截方法或回调方法，根据实际业务需求扩展 结束 ========================================*/




