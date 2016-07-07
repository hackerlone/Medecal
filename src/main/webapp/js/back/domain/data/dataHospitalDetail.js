/** 基础数据和基础设置 */
/** 基础数据和基础设置 */
lh.config = {
	mainObjLowerName : 'hospital',
	mainObjUpperName : 'Hospital',
}

$(function() {
	initData();
	//loadGrid();
	initQueryComponent();
	//initUploadSimple({showEdBtns:true});
	
});

function initData(){
	
	$('#f_typeId').combobox({
		valueField : 'id',
		textField : 'codeName',
		editable : false,
		multiple : false,
		required : false,
		panelHeight : 200,
		url : "/back/getDictArrayByParentCode?parentCode=hospital_type",
	});
	
	$('#hospital_baseInfo').show();
	lh.initSubConfig({subObjUpperName:'DataHospital', subObjLowerName:'dataHospital'});
	lh.initSubConfig({subObjUpperName:'DataHospitalDoctor', subObjLowerName:'dataHospitalDoctor'});
	lh.initSubConfig({subObjUpperName:'DataHospitalNurse', subObjLowerName:'dataHospitalNurse'});
	lh.initSubConfig({subObjUpperName:'DataHospitalContract', subObjLowerName:'dataHospitalContract'});
	lh.initSubConfig({subObjUpperName:'DataHospitalVisit', subObjLowerName:'dataHospitalVisit'});
	
	
	var param = lh.paramJsonStr;
	var obj = JSON.parse(param);
	if(!param || !obj || !obj.dataHospital){
		$.messager.alert('提示', '该条数据已不存在，无法查看详细信息');return;
	}
	var dataHospitalId = obj.dataHospital.id;
	lh.dataHospitalId = dataHospitalId;
	lh.config.queryObj = {dataHospitalId:dataHospitalId};
	initDataHospital(obj.dataHospital);//初始化诊所基本信息
	loadGridForDoctor();
	loadGridForNurse();
	loadGridForContract();
	loadGridForVisit();
}

function initDataHospital(dataHospital){
	lh.setField({data : dataHospital, inputsDom : lh['dataHospital'].$mainObj});
}

function loadGridForVisit(){
	$('#dataHospitalVisit_datagrid').datagrid({
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
		url:'/back/getDataHospitalVisitList',
	    queryParams:lh.config.queryObj,//查询参数
	    pageSize:200,//每页数据条数
	    pageList:lh.grid.pageList,//每页数据条数选择数组
	    width:1190,
	    height:300,
	    //title : "<center>走访信息</center>",
	    toolbar: [{
            text:'新增走访', iconCls:'icon-add', handler:function(){addMainObj('dataHospitalVisit')}
        },'-',{
            text:'删除走访', iconCls:'icon-remove', handler:function(){batchThoroughDelete('dataHospitalVisit')}
        }],
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
			{field:'operate',title:'操作',width:100,align:'center',formatter: function(value,row,index){
				return  '<span style="cursor: pointer;color: #EC4949" onclick="openMainObjWin(\'dataHospitalVisit\','+index+',\'update\')">修改</span>'
				+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openMainObjWin(\'dataHospitalVisit\','+index+',\'read\')">查看</span>';
			}},
	        {field:'visitDate',title:'走访日期',width:100,align:'center',formatter: function(value,row,index){
	        	return lh.formatGridDate(value);
	        }},
	        {field:'visitNum',title:'走访次数',width:100,align:'center'},
	        {field:'visitAnalysis',title:'走访分析',width:860,align:'center'}
	    ]],
        onLoadError: function(data){
	    	lh.backDatagridErrorCheck(data);
	    },
	    onDblClickRow: function(index, row){
	    	openMainObjWin('dataHospitalVisit', index, 'read');
        },
	    onLoadSuccess:function(data){
	    	lh.filtGridOperation();
	    	lh.clipboard();//复制功能
	   }  
	});
}

function loadGridForDoctor(){
	$('#dataHospitalDoctor_datagrid').datagrid({
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
		url:'/back/getDataHospitalDoctorList',
	    queryParams:lh.config.queryObj,//查询参数
	    pageSize:200,//每页数据条数
	    pageList:lh.grid.pageList,//每页数据条数选择数组
	    width:1190,
	    height:300,
	    //title : "<center>医生信息</center>",
	    toolbar: [{
            text:'新增医生', iconCls:'icon-add', handler:function(){addMainObj('dataHospitalDoctor')}
        },'-',{
            text:'删除医生', iconCls:'icon-remove', handler:function(){batchThoroughDelete('dataHospitalDoctor')}
        }],
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
			{field:'operate',title:'操作',width:100,align:'center',formatter: function(value,row,index){
				return  '<span style="cursor: pointer;color: #EC4949" onclick="openMainObjWin(\'dataHospitalDoctor\','+index+',\'update\')">修改</span>'
				+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openMainObjWin(\'dataHospitalDoctor\','+index+',\'read\')">查看</span>';
			}},
	        {field:'realname',title:'姓名',width:100,align:'center'},
	        {field:'phone',title:'联系电话',width:100,align:'center'},
	        {field:'goodAt',title:'擅长',width:280,align:'center'},
	        {field:'educationBackground',title:'教育背景',width:280,align:'center'},
	        {field:'introduction',title:'从业简介',width:300,align:'center'}
	    ]],
        onLoadError: function(data){
	    	lh.backDatagridErrorCheck(data);
	    },
	    onDblClickRow: function(index, row){
	    	openMainObjWin('dataHospitalDoctor', index, 'read');
        },
	    onLoadSuccess:function(data){
	    	lh.filtGridOperation();
	    	lh.clipboard();//复制功能
	   }  
	});

}

function loadGridForNurse(){
	$('#dataHospitalNurse_datagrid').datagrid({
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
		url:'/back/getDataHospitalNurseList',
	    queryParams:lh.config.queryObj,//查询参数
	    pageSize:200,//每页数据条数
	    pageList:lh.grid.pageList,//每页数据条数选择数组
	    width:1190,
	    height:300,
	    //title : "<center>护士信息</center>",
	    toolbar: [{
            text:'新增护士', iconCls:'icon-add', handler:function(){addMainObj('dataHospitalNurse')}
        },'-',{
            text:'删除护士', iconCls:'icon-remove', handler:function(){batchThoroughDelete('dataHospitalNurse')}
        }],
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
			{field:'operate',title:'操作',width:100,align:'center',formatter: function(value,row,index){
				return  '<span style="cursor: pointer;color: #EC4949" onclick="openMainObjWin(\'dataHospitalNurse\','+index+',\'update\')">修改</span>'
				+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openMainObjWin(\'dataHospitalNurse\','+index+',\'read\')">查看</span>';
			}},
	        {field:'realname',title:'姓名',width:100,align:'center'},
	        {field:'phone',title:'联系电话',width:100,align:'center'},
	        {field:'educationBackground',title:'教育背景',width:360,align:'center'},
	        {field:'introduction',title:'从业简介',width:500,align:'center'}
	    ]],
        onLoadError: function(data){
	    	lh.backDatagridErrorCheck(data);
	    },
	    onDblClickRow: function(index, row){
	    	openMainObjWin('dataHospitalNurse', index, 'read');
        },
	    onLoadSuccess:function(data){
	    	lh.filtGridOperation();
	    	lh.clipboard();//复制功能
	   }  
	});

}

function loadGridForContract(){
	$('#dataHospitalContract_datagrid').datagrid({
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
		url:'/back/getDataHospitalContractList',
	    queryParams:lh.config.queryObj,//查询参数
	    pageSize:200,//每页数据条数
	    pageList:lh.grid.pageList,//每页数据条数选择数组
	    width:1190,
	    height:300,
	    //title : "<center>合同信息</center>",
	    toolbar: [{
            text:'新增合同', iconCls:'icon-add', handler:function(){addMainObj('dataHospitalContract')}
        },'-',{
            text:'删除合同', iconCls:'icon-remove', handler:function(){batchThoroughDelete('dataHospitalContract')}
        }],
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
			{field:'operate',title:'操作',width:100,align:'center',formatter: function(value,row,index){
				return  '<span style="cursor: pointer;color: #EC4949" onclick="openMainObjWin(\'dataHospitalContract\','+index+',\'update\')">修改</span>'
				+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openMainObjWin(\'dataHospitalContract\','+index+',\'read\')">查看</span>';
			}},
	        {field:'contractSerial',title:'合同编号',width:120,align:'center'},
	        {field:'contractStartDate',title:'合同开始日期',width:100,align:'center',formatter: function(value,row,index){
	        	return lh.formatGridDate(value);
	        }},
	        {field:'contractTimeLimit',title:'合同期限',width:100,align:'center'},
	        {field:'remark',title:'备注',width:370,align:'center'},
	        {field:'filePaths',title:'合同附件',width:370,align:'center',formatter: function(value,row,index){
	        	if(!value){
	        		return '';
	        	}else{
	        		return '有合同附件';
	        	}
	        }}
	    ]],
        onLoadError: function(data){
	    	lh.backDatagridErrorCheck(data);
	    },
	    onDblClickRow: function(index, row){
	    	openMainObjWin('dataHospitalContract', index, 'read');
        },
	    onLoadSuccess:function(data){
	    	lh.filtGridOperation();
	    	lh.clipboard();//复制功能
	   }  
	});
}

/** 初始化查询条件中的组件及数据 */
function initQueryComponent(){}

/** 初始化表单中的组件及数据 */
function initFormComponent(lower){
	initUploadAllFile({showEdBtns:true,showItemDiv:true,multiFlag:true,multiReplace:false,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			updateFilePaths(filePath, 'add');
			appendFileLink(filePath);
	}});
	$("#upload_outer_div").empty();
	
	/*$('#f_province').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		required : true,
		panelHeight : 200,
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
	    required:true,
	    panelHeight:200
	});*/

}

function updateFilePaths(filePath, opt, fileId){
	var paths = lh.currentContractPaths
	if(opt == 'add'){
		paths += ','+filePath;
		lh.currentContractPaths = paths;
	}else if(opt == 'del'){
		var toRemove = ','+filePath;
		paths = paths.replace(toRemove, '');
		paths = paths.replace(filePath, '');
		lh.currentContractPaths = paths;
		if(fileId)$("#file_link_"+fileId).remove();
	}
}

function appendFileLink(filePath){
	if(!filePath)return;
	if(!lh.fileAry)lh.fileAry = [];
	var fileId = lh.createSerial();
	var name = filePath.substring(filePath.indexOf('_')+1, filePath.length);
	var dom = '<div id="file_link_'+fileId+'"><span>附件：</span><a href="'+filePath+'" download="'+name+'">'+name+'</a>'+
	'<button onclick="updateFilePaths(\''+filePath+'\',\'del\',\''+fileId+'\');return false;" class="button button-primary button-rounded button-tiny" style="margin-left:20px;">删除</button></div>';
	$("#contractFileLinkDiv").append(dom);
}

/** 拦截：打开窗口之前 执行此方法，返回false则不打开窗口，不向下执行 */
function beforeOpenWin(lower, data, operation, isReadOnly){
	$("#contractFileLinkDiv").empty();
	if(!data)return true;
	lh.currentWinData = data;
	if(lower == 'dataHospitalContract'){
		lh.currentContractPath = null;
		var paths = data.filePaths;
		lh.currentContractPaths = paths;
		var pathAry = paths.split(',');
		for(var i in pathAry){
			appendFileLink(pathAry[i]);
		}
	}
	return true;
}

function preAddOrUpdate(lower, mainObj){
	mainObj.dataHospitalId = lh.dataHospitalId;
	if(lower == 'dataHospitalContract'){
		if(mainObj.contractStartDate)mainObj.contractStartDate += ' 00:00:00';
		var paths = lh.currentContractPaths;
		mainObj.filePaths = paths;
	}
	if(lower == 'dataHospitalVisit'){
		if(mainObj.visitDate)mainObj.visitDate += ' 00:00:00';
	}
	
	/*var filePaths = $("#filePaths").val();
	if(!filePaths){
		//$.messager.alert('提示',"请上传用户头像"); return;
	}else{
		var ids = UPLOAD_OBJ.idsStr;
		if(filePaths.substring(0,1) != "/"){
			filePaths = filePaths.substring(1);
			ids = ids.substring(1);
		}
		mainObj.logo = filePaths;
		mainObj.logoPicId = ids;
	}*/
	
	return true;
}

function afterOpenWin(data, operation, isReadOnly){
	/*if(!data)return;
	$("#pic").attr('src', data.logo);
	$("#filePaths").val(data.logo);
	$("#fileDBIds").val(data.logoPicId);*/
}

function preAddOrUpdateDataHospital(){
	
	return true;
}

function updateDataHospital(lower){
	if(!lower)lower = 'dataHospital';
	if(lh.preventRepeat()){//防止重复提交
		lh[lower].$mainObjForm.submit();
	}else{
		lh.showRepeatTip();//提示重复提交
	}
	var mainObj = getMainObj(lower);//得到需要的字段信息
	mainObj.id = lh.dataHospitalId;//主键ID
	var flag = preAddOrUpdateDataHospital(mainObj);
	if(!flag)return false;
	
	lh.post('back', lh[lower].config.addOrUpdateUrl, mainObj, function(rsp){
		 if(rsp.status=='success'){
			 $.messager.alert('提示', '已经成功保存诊所基本信息');
		 }else{
			 $.messager.alert('提示',rsp.msg);
		 }
	});
}

/** ============================================================= **/


