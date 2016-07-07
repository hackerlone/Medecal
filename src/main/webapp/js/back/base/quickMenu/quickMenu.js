var HEIGHT = document.documentElement.clientHeight;
var WIDTH = document.documentElement.clientWidth;
if(WIDTH<1000)WIDTH=1000;
IS_SHOW_TRASH = false;
SAVING_FLAG = 0;
$(function() {
	GRID_QUERYOBJ = {};//查询条件
	loadGrid();
	initData();
});

/**加载用户列表*/
function loadGrid(){
	$('#datagrid').datagrid({
	    url:'/back/getQuickMenuList',
	    pagination:true,//允许分页
		pageSize:20,//每页10条数据
		loadMsg:'',
		width:WIDTH-20,
		height:HEIGHT-125,
		idField:'id',
		sortName:'addTime',
		sortOrder:'DESC',
		queryParams:GRID_QUERYOBJ,
		fitColumns:true,
		selectOnQuickMenu:true,
		quickMenuOnSelect:true,
		singleSelect:true,
		striped:true,
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
	        {field:'username',title:'用户名',width:220,align:'center'},
	        {field:'menuName',title:'菜单名称',width:220,align:'center'},
	        {field:'menuUrl',title:'菜单链接',width:220,align:'center'},
	        {field:'operate',title:'操作',width:160,align:'center',formatter: function(value,row,index){
	        	return  '<span class="g_alive"><span  style="cursor: pointer;color: #EC4949" onclick="openQuickMenuDetailWin('+index+',\'update\')">修改</span>'
	        	+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openQuickMenuDetailWin('+index+',\'read\')">查看</span></span>'
	        	+'<span class="g_trash"><span  style="cursor: pointer;color: #EC4949;" onclick="batchThoroughDelete('+row.id+')">彻底删除</span>'
	        	+'&nbsp;|&nbsp;<span  style="cursor: pointer;color: green" onclick="batchRecover('+row.id+')">恢复</span></span>';
	        }},
	        {field:'mainStatus',title:'状态',width:60,align:'center',formatter: function(value,row,index){
	        	var status = "启用";
	        	if(value == 2){status = '<span style="color:orange">停用</span>';}
	        	return status;
	        }},
	        {field:'deletedBy',title:'删除人',width:220,align:'center'},
	        {field:'deletedAt',title:'删除时间',width:120,align:'center',formatter: function(value,row,index){
	        	var dateStr = "";
	        	if(value){
	        		var d = new Date(value);
	        		var month = d.getMonth()+1;
	        		dateStr = ''+d.getFullYear()+'/'+month+'/'+d.getDate();
	        	}
	        	return dateStr;
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
	    onDblClickRow: function(index, row){
	    	openQuickMenuDetailWin(index, 'read');
        },
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
	/*$("#user").combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:400,
	    url:'/back/getUser'
	});*/
	$("#menu").combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:400,
	    url:'/back/getMenu'
	});
}

function loadCombo(){

	/*$("#f_user").combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:200,
	    url:'/back/getUser'
	});*/
	$("#f_menu").combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:200,
	    url:'/back/getMenu'
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

/**批量删除数据**/
function batchDelete(){
	commonBatchDelete('/back/updateQuickMenuDelete');//common_back:通用批量恢复
}

/**批量彻底删除**/
function batchThoroughDelete(id){
	commonBatchThoroughDelete('/back/deleteQuickMenuThorough',null,id);//common_back:通用批量强制删除
}

/**批量恢复数据**/
function batchRecover(id){
	commonBatchRecover('/back/updateQuickMenuRecover',null,id);//common_back:通用批量恢复
}

/**添加数据*/
function addQuickMenu(){
	openQuickMenuDetailWin(-1,'add');
}

function openQuickMenuDetailWin(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = $('#quickMenuDetailForm');
	
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#quickMenuDetailForm').form('enableValidation');
	       var flag = $('#quickMenuDetailForm').form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var quickMenu = getQuickMenuDetail(id);//得到需要的字段信息
	       	    $.post('/back/addOrUpdateQuickMenu',quickMenu,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeQuickMenuDetailWin();
				       	 },500);
					 }else{
						 $.messager.alert('提示',rsp.msg);
					 }
				},'json');
	       }
	       return false;
	    }
	});
	
	loadCombo();
	$('#quickMenuDetailWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	$('#quickMenuDetailTip').html('');
	
	var domIds = "#f_user,#f_menu,#f_mainStatus";
	
	if(operation == 'add'){//添加
		$('#quickMenuDetailTable .readOnlyTr').hide();//隐藏只读字段
		$('#quickMenuDetailSave').show();
		$(domIds).textbox('readonly',false);//新增时设置为可编辑
	}else{//查看或修改
		$('#quickMenuDetailTable .readOnlyTr').show();//显示只读字段
		$('#winSearchTr,#winSearchDivisionTr').hide();//隐藏查询按钮
		var isReadOnly = false;
		if(operation == 'read'){//查看
			isReadOnly = true;
			$('#quickMenuDetailSave').hide();
		}else{//修改
			$('#quickMenuDetailSave').show();
		}
		$(domIds).textbox('readonly',isReadOnly);//设置只读还是可编辑
		setQuickMenuDetail(data);//设置用户详细信息字段值
	}
}

/** 得到相关信息的字段值 */
function getQuickMenuDetail(id){
	var mainStatus = $('#f_mainStatus').combobox('getValue');
	var userId = $('#f_userId').val();
	var menuId = $('#f_menu').combobox('getValue');
	
	var quickMenu = {
		mainStatus:mainStatus,
		userId:userId,
		menuId:menuId
	};
	if(id){//有id为更新，无id为新增
		quickMenu.id = id;
	}
	return quickMenu;
}

/** 设置相关信息的字段值 */
function setQuickMenuDetail(data){
	var mainStatus = data.mainStatus;
	if(!mainStatus)mainStatus = 1;
	$('#f_menu').combobox('setValue',data.menuId);
	$('#f_mainStatus').combobox('setValue', mainStatus);
	//以下为只读字段：
	$('#f_serial').textbox('setValue', data.userSerial);
	$('#f_username').textbox('setValue', data.username);
	$('#f_realName').textbox('setValue', data.realName);
	$('#f_userId').val(data.userId);//隐藏字段-用户ID
}
//提交数据
function submitQuickMenuDetail(){
	//$('#quickMenuDetailForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#quickMenuDetailForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}
//关闭窗口
function closeQuickMenuDetailWin(){
	$('#quickMenuDetailWin').window('close');
}

/**读取删除数据*/
function showTrash(){
	IS_SHOW_TRASH = true;
	commonShowTrash('#batchRecover,#batchThoroughDelete,#returnBack','#batchDelete,#addQuickMenu,#showTrash');
}
/**读取未删除数据**/
function returnBack(){
	IS_SHOW_TRASH = false;
	commonReturnBack('#batchDelete,#addQuickMenu,#showTrash','#batchRecover,#batchThoroughDelete,#returnBack');
}

/**根据查询条件查询匹配的数据*/
function doSearch(){
	var username = $("#username").textbox('getValue');
	var userSerial = $("#userSerial").textbox('getValue');
	var menuId = $("#menu").combobox('getValue');
	var common_queryObj = GRID_QUERYOBJ;
	common_queryObj.userSerial = userSerial;
	common_queryObj.username = username;
	common_queryObj.menuId = menuId;
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$("#username").textbox('setValue','');
	$("#userSerial").textbox('setValue','');
	$("#menu").combobox('setValue','');
}

/** 跳转：用户信息 */
function jumpToUserInfo(){
	var url = "/back/userInfo";
	subShowMain('用户信息', url)
}

function searchUser(){
	searchUserBySerial();//common_back:通用跟据用户编号查询用户
}
