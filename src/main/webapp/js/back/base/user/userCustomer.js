var HEIGHT = document.documentElement.clientHeight;
var WIDTH = document.documentElement.clientWidth;
if(WIDTH<1000)WIDTH=1000;
IS_SHOW_TRASH = false;
TEMPDATA = null;
SAVING_FLAG = 0;
$(function() {
	GRID_QUERYOBJ = {};//查询条件
	loadGrid();
	initData();
});

/**加载用户列表*/
function loadGrid(){
	$('#datagrid').datagrid({
	    url:'/back/getUserCustomerList',
	    pagination:true,//允许分页
		pageSize:20,//每页10条数据
		loadMsg:'',
		width:WIDTH-20,
		height:HEIGHT-125,
		idField:'id',
		sortName:'addTime',
		sortOrder:'DESC',
		queryParams:GRID_QUERYOBJ,
		fitColumns:false,
		selectOnCheck:false,
		checkOnSelect:false,
		singleSelect:true,
		striped:true,
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
	        {field:'username',title:'用户',width:220,align:'center'},
	        {field:'customerName',title:'客户',width:220,align:'center'},
	        {field:'mainStatus',title:'状态',width:220,align:'center',formatter:function(value,row,index){
	        	if(value == 1){
	        		return '启用';
	        	}else{
	        		return '停用';
	        	}
	        }},
	        {field:'operate',title:'操作',width:160,align:'center',formatter: function(value,row,index){
	        	return  '<span  style="cursor: pointer;color: green" onclick="addUserCustomer('+index+',\'edit\')">修改</span>'
	        }},
	        {field:'createdBy',title:'创建人',width:220,align:'center'},
	        {field:'createdAt',title:'创建时间',width:120,align:'center',formatter: function(value,row,index){
	        	var dateStr = "";
	        	if(value){
	        		var d = new Date(value);
	        		var month = d.getMonth()+1;
	        		dateStr = ''+d.getFullYear()+'/'+month+'/'+d.getDate();
	        	}
	        	return dateStr;
	        }}
	    ]],
	    /*onSelect:function(rowIndex, rowData){
	    	onClickRowOfGrid(rowIndex, rowData);*//**当点击表格中的某行数据时执行*//*
	    },
	    onClickRow: function(index, row){
			
		},
	    loadFilter: function(data){
	    	return data;
	    },*/
	    onLoadError: function(data){
	    	lh.backDatagridErrorCheck(data);
	    },
		onLoadSuccess:function(data){
			if(IS_SHOW_TRASH){
	       		$('.g_alive').hide();
	       		$('.g_trash').show();
	       	}else{
	       		$('.g_alive').show();
	       		$('.g_trash').hide();
	       	}
		}
	});
}

function onClickRowOfGrid(){

}

function initData(){
	$('#user').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:200,
	    url:'/back/getUser'
	});
	$('#customer').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
		required:false,
		panelHeight:200,
		url:'/back/getUser'
	});
	$('#mainStatus').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]
	});
}

/**根据查询条件查询匹配的数据*/
function doSearch(){
	var user = $("#user").combobox('getValue');
	var customer = $("#customer").combobox('getValue');
	var mainStatus = $("#mainStatus").combobox('getValue');
	var common_queryObj = GRID_QUERYOBJ;
	common_queryObj.userId = user;
	common_queryObj.customerId = customer;
	common_queryObj.mainStatus = mainStatus;
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$("#user").combobox('setValue','');
	$("#customer").combobox('setValue','');
	$("#mainStatus").combobox('setValue','');
}

/**批量删除数据**/
function batchDelete(){
	commonBatchDelete('/back/updateUserCustomer');//common_back:通用批量恢复
}

/**批量彻底删除**/
function batchThoroughDelete(id){
	commonBatchThoroughDelete('/back/deleteUserCustomerThorough',null,id);//common_back:通用批量强制删除
}

/**批量恢复数据**/
function batchRecover(id){
	commonBatchRecover('/back/updateUserCustomerRecover',null,id);//common_back:通用批量恢复
}

/**读取删除数据*/
function showTrash(){
	IS_SHOW_TRASH = true;
	commonShowTrash('#batchRecover,#batchThoroughDelete,#returnBack','#batchDelete,#addUserCustomer,#showTrash');
}
/**读取未删除数据**/
function returnBack(){
	IS_SHOW_TRASH = false;
	commonReturnBack('#batchDelete,#addUserCustomer,#showTrash','#batchRecover,#batchThoroughDelete,#returnBack');
}

function loadCombo(){
	$('#f_user').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:200,
	    url:'/back/getUser'
	});
	$('#f_customer').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
		required:true,
		panelHeight:200,
		url:'/back/getUser'
	});
	$('#f_mainStatus').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:'auto',
		data : [{
			'id' : 1,
			'name' : '启用'
		},{
			'id' : 2,
			'name' : '停用'
		}]
	});
}

/** 关闭窗口 */
function closeUserCustomerDetailWin(){
	$('#userCustomerDetailWin').window('close');
}

/** 提交表单 */
function submitUserCustomerDetail(){
	//$('#userCustomerDetailForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#userCustomerDetailForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}

function addUserCustomer(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
		TEMPDATA = data;
	}
	var $form = $('#userCustomerDetailForm');
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#userCustomerDetailForm').form('enableValidation');
	       var flag = $('#userCustomerDetailForm').form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var userCustomer = getUserCustomerDetail(id);//得到用户信息的字段值
	       	    $.post('/back/addOrUpdateUserCustomer',userCustomer,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeUserCustomerDetailWin();
				       	 },500);
					 }else{
						 $.messager.alert('提示',rsp.msg);
					 }
				},'json');
	       }
	       return false;
	    }
	});
	
	loadCombo();//加载下拉列表数据（省市）
	$('#userCustomerDetailWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	if(operation != 'add'){
		setUserCustomerDetail(data);//设置用户详细信息字段值
	}	
}

/** 得到用户信息的字段值 */
function getUserCustomerDetail(id){
	var f_user = $('#f_user').combobox('getValue');
	var f_mainStatus = $('#f_mainStatus').combobox('getValue');
	var f_customer = $('#f_customer').combobox('getValue');
	var userCustomer = {
		userId : f_user,
		customerId : f_customer,
		mainStatus : f_mainStatus
	};
	if(id){//有id为更新，无id为新增
		userCustomer.id = id;
	}
	return userCustomer;
}

/** 设置用户信息的字段值 */
function setUserCustomerDetail(data){
	$("#f_Id").val(data.id);
	$('#f_user').combobox('setValue',data.userId);
	$('#f_customer').combobox('setValue',data.customerId);
	$('#f_mainStatus').combobox('setValue',data.mainStatus);
}


