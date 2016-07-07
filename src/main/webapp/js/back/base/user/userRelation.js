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
	    url:'/back/getUserRelationList',
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
		selectOnCheck:true,
		checkOnSelect:true,
		singleSelect:true,
		striped:true,
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
			{field:'userSerial',title:'用户编号',width:220,align:'center'},
	        {field:'username',title:'用户名',width:220,align:'center'},
	        {field:'antiqueCityName',title:'古玩城名称',width:220,align:'center'},
	        {field:'operate',title:'操作',width:160,align:'center',formatter: function(value,row,index){
	        	return  '<span class="g_alive"><span  style="cursor: pointer;color: #EC4949" onclick="openUserRelationDetailWin('+index+',\'update\')">修改</span>'
	        	+'&nbsp;|&nbsp;<span class="update" style="cursor: pointer;color: green" onclick="openUserRelationDetailWin('+index+',\'read\')">查看</span></span>'
	        	+'<span class="g_trash"><span  style="cursor: pointer;color: #EC4949;" onclick="batchThoroughDelete('+row.id+')">彻底删除</span>'
	        	+'&nbsp;|&nbsp;<span  style="cursor: pointer;color: green" onclick="batchRecover('+row.id+')">恢复</span></span>';
	        }},
	        /*{field:'mainStatus',title:'状态',width:60,align:'center',formatter: function(value,row,index){
	        	var status = "启用";
	        	if(value == 2){status = '<span style="color:orange">停用</span>';}
	        	return status;
	        }},*/
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
	    	openUserRelationDetailWin(index, 'read');
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

	$("#sc_type").combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:200,
	    url:'/back/getAntiqueCity'
	});
}

/**根据查询条件查询匹配的数据*/
function doSearch(){
	var username = $("#username").textbox('getValue');
	var userSerial = $("#userSerial").textbox('getValue');
	var forumId = $("#sc_type").combobox('getValue');
	var common_queryObj = GRID_QUERYOBJ;
	common_queryObj.username = username;
	common_queryObj.userSerial = userSerial;
	common_queryObj.forumId = forumId;
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$("#userSerial").textbox('setValue','');
	 $("#username").textbox('setValue','');
	 $("#sc_type").combobox('setValue','');
}

function loadCombo(){

	$("#f_user").combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:200,
	    url:'/back/getUser'
	});
	
	$("#f_antiqueCity").combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:200,
	    url:'/back/getAntiqueCity'
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
	commonBatchDelete('/back/updateUserRelationDelete');//common_back:通用批量恢复
}

/**批量彻底删除**/
function batchThoroughDelete(id){
	commonBatchThoroughDelete('/back/deleteUserRelationThorough',null,id);//common_back:通用批量强制删除
}

/**批量恢复数据**/
function batchRecover(id){
	commonBatchRecover('/back/updateUserRelationRecover',null,id);//common_back:通用批量恢复
}

/**添加数据*/
function addUserRelation(){
	openUserRelationDetailWin(-1,'add');
}

function openUserRelationDetailWin(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = $('#userRelationDetailForm');
	
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#userRelationDetailForm').form('enableValidation');
	       var flag = $('#userRelationDetailForm').form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var userRelation = getUserRelationDetail(id);//得到需要的字段信息
	       	    $.post('/back/addOrUpdateUserRelation',userRelation,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeUserRelationDetailWin();
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
	$('#userRelationDetailWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	$('#userRelationDetailTip').html('');
	
	var domIds = "#f_user,#f_antiqueCity,#f_mainStatus";
	
	if(operation == 'add'){//添加
		$('#userRelationDetailTable .readOnlyTr').show();//隐藏只读字段
		$('#winSearchTr,#winSearchDivisionTr').show();//显示查询按钮
		$('#userRelationDetailSave').show();
		$(domIds).textbox('readonly',false);//新增时设置为可编辑
	}else{//查看或修改
		$('#userRelationDetailTable .readOnlyTr').show();//显示只读字段
		$('#winSearchTr,#winSearchDivisionTr').hide();//隐藏查询按钮
		var isReadOnly = false;
		if(operation == 'read'){//查看
			isReadOnly = true;
			$('#userRelationDetailSave').hide();
		}else{//修改
			$('#userRelationDetailSave').show();
		}
		$(domIds).textbox('readonly',isReadOnly);//设置只读还是可编辑
		setUserRelationDetail(data);//设置用户详细信息字段值
	}
}

/** 得到相关信息的字段值 */
function getUserRelationDetail(id){
	//var mainStatus = $('#f_mainStatus').combobox('getValue');
	var userId = $('#f_userId').val();
	var relationId = $('#f_antiqueCity').combobox('getValue');
	
	var userRelation = {
		userId:userId,
		relationId:relationId,
		relationType:64
		//mainStatus:mainStatus
	};
	if(id){//有id为更新，无id为新增
		userRelation.id = id;
	}
	return userRelation;
}

/** 设置相关信息的字段值 */
function setUserRelationDetail(data){
//	var mainStatus = data.mainStatus;
//	if(!mainStatus)mainStatus = 1;
	//$('#f_user').combobox('setValue',data.userId);
	$('#f_antiqueCity').combobox('setValue',data.relationId);
	//$('#f_mainStatus').combobox('setValue', mainStatus);
	//以下为只读字段：
	$('#f_serial').textbox('setValue', data.userSerial);
	$('#f_username').textbox('setValue', data.username);
	$('#f_realName').textbox('setValue', data.realName);
	$('#f_userId').val(data.userId);//隐藏字段-用户ID
}
//提交数据
function submitUserRelationDetail(){
	//$('#userRelationDetailForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#userRelationDetailForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}
//关闭窗口
function closeUserRelationDetailWin(){
	$('#userRelationDetailWin').window('close');
}

/**读取删除数据*/
function showTrash(){
	IS_SHOW_TRASH = true;
	commonShowTrash('#batchRecover,#batchThoroughDelete,#returnBack','#batchDelete,#addUserRelation,#showTrash');
}
/**读取未删除数据**/
function returnBack(){
	IS_SHOW_TRASH = false;
	commonReturnBack('#batchDelete,#addUserRelation,#showTrash','#batchRecover,#batchThoroughDelete,#returnBack');
}

function jumpToAntiqueCity(){
	var url = "/back/antiqueCity";
	subShowMain('古玩城', url)
}

/** 跳转：用户信息 */
function jumpToUserInfo(){
	var url = "/back/userInfo";
	var id = $('#userId').val();
	if(id){
		url += "?userId="+id;
	}
	subShowMain('用户信息', url)
}

function searchUser(){
	searchUserBySerial();//common_back:通用跟据用户编号查询用户
}

