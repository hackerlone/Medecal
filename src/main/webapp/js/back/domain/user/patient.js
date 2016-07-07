/** 基础数据和基础设置 */
lh.config = {
	mainObjLowerName : 'user',
	mainObjUpperName : 'User',
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
			{field:'avatar',title:'头像',width:200,align:'center',formatter: function(value,row,index){
	        	var logo = '<a href="'+value+'" target="_blank"><img style="height:60px;cursor:pointer;" src="'+value+'"/></a>';
	        	if(!value)logo = '<span style="line-height:60px;">暂无图片<span>';
	        	return logo;
	        }},
	        {field:'username',title:'用户名',width:120,align:'center'},
	        {field:'realName',title:'真实姓名',width:120,align:'center'},
	        {field:'sex',title:'性别',width:120,align:'center',formatter: function(value,row,index){
	        	return value == 2 ? '<span style="color:orange">女</span>' : '男';
	        }},
	        {field:'idcardNum',title:'身份证号码',width:120,align:'center'},
	        {field:'email',title:'邮箱',width:120,align:'center'},
	        {field:'address',title:'地址',width:120,align:'center'},
	        {field:'phone',title:'手机号码',width:120,align:'center'},
	       /* {field:'qq',title:'qq',width:120,align:'center'},
	        {field:'weixin',title:'微信',width:120,align:'center'},*/
	        {field:'provinceName',title:'省(直辖市)',width:120,align:'center'},
	        {field:'cityName',title:'市(县)',width:120,align:'center'},
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
	$('#sc_sex').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '男'
		},{
			'id' : 2,
			'name' : '女'
		}]
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
			'name' : '用户名升序'
		},{
			'id' : 2,
			'name' : '用户名降序'
		},{
			'id' : 3,
			'name' : '地区升序'
		},{
			'id' : 4,
			'name' : '地区降序'
		},{
			'id' : 5,
			'name' : '注册时间升序'
		},{
			'id' : 6,
			'name' : '注册时间降序'
		}]
	});
}

/** 初始化表单中的组件及数据 */
function initFormComponent(){
	if(!UPLOAD_OBJ.hasInit){
		initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
			successFun:function(fileId, filePath){
				$("#upld_container_"+fileId).remove();
				$("#pic").attr('src', filePath);
		}});
	}
	$("#upload_outer_div").empty();
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
	$('#f_sex').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : false,
		required : true,
		panelHeight : 'auto',
		data : [{
			'id' : 1,
			'name' : '男'
		},{
			'id' : 2,
			'name' : '女'
		}]
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
	var filePaths = $("#filePaths").val();
	if(!filePaths){
		//$.messager.alert('提示',"请上传用户头像"); return;
	}else{
		var ids = UPLOAD_OBJ.idsStr;
		if(filePaths.substring(0,1) != "/"){
			filePaths = filePaths.substring(1);
			ids = ids.substring(1);
		}
		mainObj.avatar = filePaths;
		mainObj.avatarPicId = ids;
	}
	return true;
}

function afterOpenWin(data, operation, isReadOnly){
	if(!data)return;
	$("#pic").attr('src', data.avatar);
	$("#filePaths").val(data.avatar);
	$("#fileDBIds").val(data.avatarPicId);
}

function exportPatient(){
	var obj = lh.getQueryObj();
	delete obj.ascOrdesc;
	window.location.href = '/back/patientExcel?obj='+obj;
}
