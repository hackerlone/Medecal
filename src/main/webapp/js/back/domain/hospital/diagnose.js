/** 基础数据和基础设置 */
lh.config = {
	mainObjLowerName : 'diagnose',
	mainObjUpperName : 'Diagnose',
	queryObj:{isLink:1}
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
			{field:'operate',title:'操作',width:160,align:'center',formatter: function(value,row,index){
				return  '<span class="opt_alive"><span style="cursor: pointer;color: #2078B9" onclick="window.open(\'/back/diagnosePrint/'+row.id+'\');">打印</span>'
				+'&nbsp;|&nbsp;<span style="cursor: pointer;color: #EC4949" onclick="openMainObjWin('+index+',\'update\')">修改</span>'
				+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openMainObjWin('+index+',\'read\')">查看</span></span>'
				+'<span class="opt_trash"><span style="cursor: pointer;color: #EC4949;" onclick="lh.commonBatchThoroughDelete('+row.id+')">彻底删除</span>'
				+'&nbsp;|&nbsp;<span style="cursor: pointer;color: green" onclick="lh.commonBatchRecover('+row.id+')">恢复</span></span>';
			}},
	        {field:'hospitalName',title:'诊所',width:120,align:'center'},
	        {field:'departmentName',title:'科室',width:120,align:'center'},
	        {field:'doctorName',title:'医生名称',width:120,align:'center'},
	        {field:'patientName',title:'患者名称',width:120,align:'center'},
	        {field:'patientSex',title:'用户性别',width:120,align:'center',formatter: function(value,row,index){
	        	var sex = '';
	        	if(value){
	        		if(value == 1){sex = '女';}else if(value == 2)sex = '男';
	        	}
	        	return sex;
	        }},
	        {field:'patientAge',title:'用户年龄',width:120,align:'center'},
	        {field:'patientBirthday',title:'用户出生日期',width:120,align:'center'},
	        {field:'patientIdcardNum',title:'用户身份证号码',width:120,align:'center'},
	        {field:'patientPhone',title:'用户联系电话',width:120,align:'center'},
	        {field:'patientJobName',title:'用户职业',width:120,align:'center'},
	        {field:'allergyHistory',title:'过敏历史',width:120,align:'center'},
	        {field:'baseCondition',title:'基本病情',width:120,align:'center'},
	        {field:'diagnoseTags',title:'诊断结果',width:120,align:'center',formatter: function(value,row,index){
	        	var data = JSON.parse(value);
	        	//var data = value;
	        	var diagnoseTagNames = '';
	        	for(var i=0;i<data.length;i++){
	        		var obj = data[i];
	        		if(obj)diagnoseTagNames += obj.tagName+",";	
				}
	        	return diagnoseTagNames;
	        }},
	        {field:'diagnoseTime',title:'就诊时间',width:120,align:'center',formatter: function(value,row,index){
	        	return lh.formatGridDate(value);
	        }},
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
	$('#sc_hospitalId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : true,
		multiple : false,
		//required : true,
		panelHeight : 200,
		filter: lh.comboboxDefaultFilter,
		/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
		url : "/back/getHospitalArray"
	});
	$('#sc_doctorId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : true,
		multiple : false,
		//required : true,
		filter: lh.comboboxDefaultFilter,
		panelHeight : 200,
		/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
		url : "/back/getDoctorArray"
	});
	$('#sc_departmentId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:200,
	    url : '/back/getDepartmentArray'
	});
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
			'name' : '患者名称升序'
		},{
			'id' : 2,
			'name' : '患者名称降序'
		},{
			'id' : 3,
			'name' : '医生名称升序'
		},{
			'id' : 4,
			'name' : '医生名称降序'
		},{
			'id' : 5,
			'name' : '创建时间升序'
		},{
			'id' : 6,
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
		//required : true,
		filter: lh.comboboxDefaultFilter,
		panelHeight : 200,
		/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
		url : "/back/getHospitalArray"
	});
	$('#f_doctorId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : true,
		multiple : false,
		//required : true,
		filter: lh.comboboxDefaultFilter,
		panelHeight : 200,
		/*data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]*/
		url : "/back/getDoctorArray"
	});
	$('#f_departmentId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:200,
	    url : '/back/getDepartmentArray'
	});
	$('#f_patientJob').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
		required:false,
		panelHeight:200,
		url : '/back/getPatientJobTypeArray'
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
	$('#f_patientSex').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
	    required : true,
	    panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '女'
		},{
			'id' : 2,
			'name' : '男'
		}]
	});
}

/** 新增修改操作执行之前的拦截方法，返回false则不执行新增修改，如无对应操作可不用申明此方法 */
function preAddOrUpdate(mainObj){
	return true;
}

function afterOpenWin(data, operation, isReadOnly){
	if(!data && !data.diagnoseTags)return;
	var tags = JSON.parse(data.diagnoseTags);
	var diagnoseTagNames = '';
	for(var i=0;i<tags.length;i++){
		var obj = tags[i];
		if(obj)diagnoseTagNames += obj.tagName+",";	
	}
	$('#f_diagnoseTagsReadOnly').textbox('setValue', diagnoseTagNames);
}

function exportDiagnose(){
	var obj = lh.getQueryObj();
	delete obj.ascOrdesc;
	window.location.href = '/back/diagnoseExcel?obj='+obj;
}
