/** 基础数据和基础设置 */
lh.config = {
	mainObjLowerName : 'department',
	mainObjUpperName : 'Department',
}

$(function() {
	loadGrid();
	initQueryComponent();
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
			{field:'operate',title:'操作',width:120,align:'center',formatter: function(value,row,index){
				return  '<span class="opt_alive"><span style="cursor: pointer;color: #EC4949" onclick="openMainObjWin('+index+',\'update\')">修改</span>'
				+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openMainObjWin('+index+',\'read\')">查看</span></span>'
				+'<span class="opt_trash"><span style="cursor: pointer;color: #EC4949;" onclick="lh.commonBatchThoroughDelete('+row.id+')">彻底删除</span>'
				+'&nbsp;|&nbsp;<span style="cursor: pointer;color: green" onclick="lh.commonBatchRecover('+row.id+')">恢复</span></span>';
			}},
			/*{field:'hospitalName',title:'诊所',width:120,align:'center'},*/
			{field:'parentName',title:'上级科室',width:120,align:'center'},
	        {field:'name',title:'科室名称',width:120,align:'center'},
	        /*{field:'address',title:'地址',width:120,align:'center'},
	        {field:'email',title:'邮箱',width:120,align:'center'},
	        {field:'phone',title:'手机号码',width:120,align:'center'},
	        {field:'qq',title:'qq',width:120,align:'center'},
	        {field:'weixin',title:'微信',width:120,align:'center'},
	        {field:'weibo',title:'微博',width:120,align:'center'},
	        {field:'provinceName',title:'省(直辖市)',width:120,align:'center'},
	        {field:'cityName',title:'市(县)',width:120,align:'center'},*/
	        {field:'mainStatus',title:'状态',width:60,align:'center',formatter: function(value,row,index){
	        	return value == 2 ? '<span style="color:orange">停用</span>' : '启用';
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
        onLoadError: function(data){
	    	lh.backDatagridErrorCheck(data);
	    },
	    onDblClickRow: function(index, row){
	    	openMainObjWin(index, 'read');
        },
	    onLoadSuccess:function(data){
	    	lh.filtGridOperation();
	    	lh.clipboard();//复制功能
	   }  
	});
}

function onClickRowOfGrid(){}

/** 初始化查询条件中的组件及数据 */
function initQueryComponent(){
	$('#sc_mainStatus').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
	    required : false,
	    panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]
	});
	$('#sc_ascOrdesc').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		required : false,
		panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '部门名称升序'
		},{
			'id' : 2,
			'name' : '部门名称降序'
		},{
			'id' : 3,
			'name' : '创建时间升序'
		},{
			'id' : 4,
			'name' : '创建时间降序'
		}]
	});
}

/** 初始化表单中的组件及数据 */
function initFormComponent(){
	$('#f_hospitalId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : true,
		multiple : false,
		required : true,
		filter: lh.comboboxDefaultFilter,
		panelHeight : 200,
		url : '/back/getHospitalArray'
		/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
	});
	$('#f_parentId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		required : true,
		panelHeight : 200,
		url : '/back/getParentDepartmentArray'
			/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
	});
	$('#f_province').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		//required : true,
		panelHeight : 200,
		/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
		url : "/back/getProvinceArray",
		onSelect: function(rec){
            var url = '/back/getCityArray?provinceId='+rec.id;
            $('#f_city').combobox('reload', url);
        }
	});
	
	$('#f_city').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:200
	});
	$('#f_mainStatus').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
	    required : true,
	    panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]
	});
}

/** 新增修改操作执行之前的拦截方法，返回false则不执行新增修改，如无对应操作可不用申明此方法 */
function preAddOrUpdate(mainObj){
	return true;
}

