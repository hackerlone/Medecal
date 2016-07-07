var HEIGHT = document.documentElement.clientHeight;
var WIDTH = document.documentElement.clientWidth;
if(WIDTH<1000)WIDTH=1000;
IS_SHOW_TRASH = false;
SAVING_FLAG = 0;
$(function() {
	GRID_QUERYOBJ = {};//查询条件
	loadGrid();
	initSearch();
	//paramMapData();
});

/**加载用户控制列表*/
function loadGrid(){
	$('#datagrid').datagrid({
	    url:'/back/getUserControlList',
	    pagination:true,//允许分页
		pageSize:20,//每页10条数据
		loadMsg:'',
		width:WIDTH-1,
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
			{field:'userSerial',title:'用户编号',width:160,align:'center',formatter: function(value,row,index){
				if(!value)return '';
				var dom = '<span>'+value+'</span>' +
				'<button class="copy_btn pointer fr" data-clipboard-text="'+value+'">复制</button>';
	        	return dom;
	        }},
	        {field:'username',title:'用户名',width:140,align:'center'},
	        {field:'realName',title:'真实姓名',width:100,align:'center'},
	        {field:'operate',title:'操作',width:100,align:'center',formatter: function(value,row,index){
	        	return '<span class="g_alive"><span class="opt_cuation" onclick="openUserControlWin('+index+',\'update\')">修改</span>'+//jumpToUserUpdate
	        		   ' |  <span class="opt_green" onclick="openUserControlWin('+index+',\'read\')">查看</span></span>'+//jumpToUserControl
	        		   '<span class="g_trash"><span class="opt_cuation" onclick="batchThoroughDelete('+row.id+')">彻底删除</span>'+
	        		   ' |  <span class="opt_green" onclick="batchRecover('+row.id+')">恢复</span></span>';
	        }},
	        {field:'controlType',title:'控制类型',width:140,align:'center'},
	        {field:'startDate',title:'起效时间',width:100,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'endDate',title:'失效时间',width:100,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'createdAt',title:'添加时间',width:120,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'createdBy',title:'添加人',width:140,align:'center'},
	        {field:'updatedAt',title:'修改时间',width:120,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'updatedBy',title:'修改人',width:140,align:'center'},
	        {field:'deletedAt',title:'删除时间',width:120,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'deletedBy',title:'删除人',width:140,align:'center'}
	        ]],
	    /*,onSelect:function(rowIndex, rowData){onClickRowOfGrid(rowIndex, rowData);},
	    onClickRow: function(index, row){},
	    loadFilter: function(data){return data;},
		onLoadSuccess:function(data){} */
	        onLoadError: function(data){
		    	lh.backDatagridErrorCheck(data);
		    },
		    onDblClickRow: function(index, row){
		    	openUserControlWin(index, 'read');
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

/** grid 行点击事件 */
/**function onClickRowOfGrid(rowIndex, rowData){}*/

function loadCombo(){
	
	$('#f_controlType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
	    url:'/back/getUserControlTypeList'
		/*data : [{
			'id' : 1,
			'name' : '临时-禁止访问首页'
		},{
			'id' : 2,
			'name' : '临时-禁止论坛留言'
		}]*/
	});
}

/** 关闭窗口 */
function closeUserControlWin(){
	$('#userControlWin').window('close');
	//$('#datagrid').datagrid('clearSelections');
	//$('#datagrid').datagrid('clearChecked');
}

/** 提交表单 */
function submitUserControl(){
	//$('#userControlForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#userControlForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}

/** 添加用户 */
function addUser(){
	openUserControlWin(-1,'add');
}

/** 打开窗口 */
function openUserControlWin(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = $('#userControlForm');
	
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#userControlForm').form('enableValidation');
	       var flag = $('#userControlForm').form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var user = getUserControl(id);//得到用户信息的字段值
	       	    $.post('/back/addOrUpdateUserControl',user,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeUserControlWin();
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
	$('#userControlWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	$('#userControlTip').html('');
	
	var domIds = "#f_controlType,#f_startDate,#f_endDate";
	
	if(operation == 'add'){//添加用户
		$('#userControlSave').show();
		$('#winSearchTr,#winSearchDivisionTr').show();//显示查询按钮
		$('#userControlTable .readOnlyTr').hide();//隐藏只读字段
		$(domIds).textbox('readonly',false);//新增时设置为可编辑
	}else{//查看或修改
		$('#winSearchTr,#winSearchDivisionTr').hide();//隐藏查询按钮
		$('#userControlTable .readOnlyTr').show();//显示只读字段
		var isReadOnly = false;
		if(operation == 'read'){//查看
			isReadOnly = true;
			$('#userControlSave').hide();
		}else{//修改
			$('#userControlSave').show();
		}
		$(domIds).textbox('readonly',isReadOnly);//设置只读还是可编辑
		setUserControl(data);//设置用户详细信息字段值
	}
	
}

/** 得到用户信息的字段值 */
function getUserControl(id){
	var userId = $('#f_userId').val();
	var controlTypeId = $('#f_controlType').combobox('getValue');
	var startDate = $('#f_startDate').datetimebox('getValue');
	var endDate = $('#f_endDate').datetimebox('getValue');
	
	var userControl = {
		userId:userId,
		controlTypeId:controlTypeId,
		startDate:startDate,
		endDate:endDate
	};
	if(id){//有id为更新，无id为新增
		userControl.id = id;
	}
	return userControl;
}

/** 设置用户信息的字段值 */
function setUserControl(data){
	$('#f_serial').textbox('setValue',data.userSerial);
	$('#f_username').textbox('setValue',data.username);
	$('#f_realName').textbox('setValue',data.realName);
	$('#f_startDate').datebox('setValue',getDateStr(data.startDate));
	$('#f_endDate').datebox('setValue',getDateStr(data.endDate));
	$('#f_controlType').combobox('setValue', data.controlTypeId);
	
	//查询产生的只读字段：
	$('#f_serial').textbox('setValue', data.serial);
	$('#f_username').textbox('setValue', data.username);
	$('#f_realName').textbox('setValue', data.realName);
	$('#f_userId').val(data.userId);//隐藏字段-用户ID
	$('#f_shopId').val(data.shopId);//隐藏字段-店铺ID
	//以下为只读字段：
}

function search(typeId){
	$('#datagrid').datagrid('load',{
		typeId : typeId
	});
}

function initSearch(){
	
	$('#sc_controlType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
	    url:'/back/getUserControlTypeList'
		/*data : [{
			'id' : 1,
			'name' : '临时-禁止访问首页'
		},{
			'id' : 2,
			'name' : '临时-禁止论坛留言'
		}]*/
	});
	
}


/**根据查询条件查询匹配的数据*/
function doSearch(){
	var sc_username = $('#sc_username').textbox('getValue');
	var sc_realName = $('#sc_realName').textbox('getValue');
	var sc_serial = $('#sc_serial').textbox('getValue');
	
	var sc_controlType = $('#sc_controlType').combobox('getValue');
	
	var sc_validDateFrom = $('#sc_validDateFrom').datebox('getValue');
	var sc_validDateTo = $('#sc_validDateTo').datebox('getValue');
	if(sc_validDateFrom && !sc_validDateTo){
		sc_validDateTo = sc_validDateFrom;
	}
	if(!sc_validDateFrom && sc_validDateTo){
		sc_validDateFrom = sc_validDateTo;
	}

	if(!sc_controlType){sc_controlType=""}
	
	var common_queryObj = GRID_QUERYOBJ;
	common_queryObj.username = sc_username;
	common_queryObj.realName = sc_realName;
	common_queryObj.serial = sc_serial;
	
	common_queryObj.controlTypeId = sc_controlType;
	
	common_queryObj.validDateFrom = sc_validDateFrom;
	common_queryObj.validDateTo = sc_validDateTo;
	
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$('#sc_username,#sc_realName,#sc_serial').textbox('reset');
	$('#sc_controlType').combobox('reset');
	$('#validDateFrom,#validDateTo').datebox('reset');
	
}

function searchUser(){
	searchUserBySerial();//common_back:通用跟据用户编号查询用户
}

/** 跳转到 修改用户 页面 */
/**function jumpToUserUpdate(){}*/
/** 跳转到 用户详情 页面 */
/**function jumpToUserControl(){}*/

/** 批量删除用户 */
function batchDelete(){
	commonBatchDelete('/back/updateUserControlDelete');//common_back:通用批量删除
}

/** 切换到回收站 */
function showTrach(){
	IS_SHOW_TRASH = true;
	commonShowTrash('#batchRecover,#batchThoroughDelete,#returnBack','#batchDelete,#addUserControl,#userMoneyLink,#userInfoLink,#showTrash');
}

/** 从回收站返回 */
function returnBack(){
	IS_SHOW_TRASH = false;
	commonReturnBack('#batchDelete,#addUserControl,#userMoneyLink,#userInfoLink,#showTrash','#batchRecover,#batchThoroughDelete,#returnBack');
}

/** 批量恢复用户 */
function batchRecover(id){
	commonBatchRecover('/back/updateUserControlRecover',null,id);//common_back:通用批量恢复
}

/** 批量强制删除用户 */
function batchThoroughDelete(id){
	commonBatchThoroughDelete('/back/deleteUserControlThrough',null,id);//common_back:通用批量强制删除
}

/** 跳转：用户资金 */
function jumpToUserMoney(){
	var url = "/back/userFund";
	var id = $('#userId').val();
	if(id){
		url += "?userId="+id;
	}
	subShowMain('用户资金', url)
}

/** 跳转：用户控制 */
function jumpToUserInfo(){
	var url = "/back/userInfo";
	var id = $('#userId').val();
	if(id){
		url += "?userId="+id;
	}
	subShowMain('用户信息', url)
}

/** 跳转：新增或修改 */
function jumpToUserAddOrUpdate(id){
	var url = "/back/userAddOrUpdate";
	if(id){
		url += "?userId="+id;
	}
	window.location.href = url;
}

