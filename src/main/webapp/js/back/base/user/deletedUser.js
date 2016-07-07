var HEIGHT = document.documentElement.clientHeight;
var WIDTH = document.documentElement.clientWidth;
if(WIDTH<800)WIDTH=800;
SAVING_FLAG = 0;
BARTREE = null;
$(function() {
	GRID_QUERYOBJ = {isDelete:2};//查询条件
	loadGrid();
	initSearch();
});


/**加载文章新闻表格*/
function loadGrid(){
	$('#datagrid').datagrid({
	    url:'/back/getAllUser',
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
		selectOnCheck:false,
		checkOnSelect:false,
		singleSelect:true,
		striped:true,
	    columns:[
		[
			{field:'checkbox',title:'多选框',checkbox:true},
			{field:'id',title:'',hidden:true},
	        {field:'username',title:'用户名',width:220,align:'left',formatter: function(value,row,index){//search('+"'"+row.module+"'"+')
	        	var type = row.type;
	        	var url = '/member/expertmember/'+row.id+'/index.html';
	        	if(!type){
	        		type = "咨客会员";
	        		url = '/client/'+row.id+'/index.html';
	        	}
	        	return  '<span style="cursor: pointer;color: #555;" onclick="search('+row.typeId+');">['+type+']</span>&nbsp;' +
	        			'<a style="color: #555;text-decoration: initial;" target="_blank" href='+url+'>'+value+'</a>';
	        }},
	        {field:'realName',title:'真实姓名',width:120,align:'center'},
	        {field:'memberId',title:'会员编号',width:120,align:'center'},
	        {field:'memberGradeName',title:'会员等级',width:120,align:'center'},
	        {field:'cutOffDate',title:'会员到期时间',width:120,align:'center',formatter: function(value,row,index){
	        	var date = value;
	        	if(!date)date = row.cutOffDateShow;
	        	var dateStr = "";
	        	if(date){
	        		var d = new Date(date);
	        		var month = d.getMonth()+1;
	        		dateStr = ''+d.getFullYear()+'/'+month+'/'+d.getDate();
	        	}
	        	return dateStr;
	        }},
	        {field:'type',title:'类型',width:140,align:'center'},
	        {field:'money',title:'账户金额',width:120,align:'center'},
	        {field:'integral',title:'积分',width:120,align:'center'},
	        {field:'province',title:'地区',width:160,align:'center',formatter: function(value,row,index){
	        	return row.province+','+row.city;
	        }},
	        {field:'email',title:'邮箱',width:130,align:'center'},
	        {field:'phone',title:'电话',width:120,align:'center'},
	        {field:'tel',title:'固话',width:120,align:'center'},
	        {field:'qq',title:'qq',width:120,align:'center'},
	        {field:'status',title:'状态',width:100,align:'center',formatter: function(value,row,index){
	        	var status = "待审核";
	        	if(value == 2){status = "启用";}
	        	if(value == 3){status = "停用";}
	        	return status;
	        }},
	        {field:'lastLoginTime',title:'上次登陆时间',width:120,align:'center',formatter: function(value,row,index){
	        	var dateStr = "";
	        	if(value){
	        		var d = new Date(value);
	        		var month = d.getMonth()+1;
	        		dateStr = ''+d.getFullYear()+'/'+month+'/'+d.getDate();
	        	}
	        	return dateStr;
	        }},
	        {field:'addTime',title:'注册时间',width:120,align:'center',formatter: function(value,row,index){
	        	var dateStr = "";
	        	if(value){
	        		var d = new Date(value);
	        		var month = d.getMonth()+1;
	        		dateStr = ''+d.getFullYear()+'/'+month+'/'+d.getDate();
	        	}
	        	return dateStr;
	        }},//sortable:true,order:'desc',
	        
	        {field:'description',title:'描述',width:160,align:'center'}
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
			
		}
	});
	
	
}

function onClickRowOfGrid(rowIndex, rowData){
}


function search(typeId){
	$('#datagrid').datagrid('load',{
		isDelete:2,
		typeId : typeId
	});
}

function initSearch(){
	$('#sc_type').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		data : [ {
			'id' : 1,
			'name' : '咨客会员'
		}, {
			'id' : 2,
			'name' : '专家会员'
		}, {
			'id' : 44,
			'name' : '心理咨询专家'
		}, {
			'id' : 45,
			'name' : '婚姻家庭咨询专家'
		}, {
			'id' : 46,
			'name' : '催眠咨询专家'
		}, {
			'id' : 47,
			'name' : '法律法规咨询专家'
		}]
	});
	
	$('#sc_order').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		data : [ {
			'id' : 1,
			'name' : '用户id↑'
		}, {
			'id' : 2,
			'name' : '用户id↓'
		}, {
			'id' : 3,
			'name' : '金额↑'
		}, {
			'id' : 4,
			'name' : '金额↓'
		}, {
			'id' : 5,
			'name' : '注册时间↑'
		}, {
			'id' : 6,
			'name' : '注册时间↓'
		}, {
			'id' : 7,
			'name' : '最近登陆时间↑'
		}, {
			'id' : 68,
			'name' : '最近登陆时间↓'
		}]
	});
	
	$('#sc_status').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		data : [ {
			'id' : 1,
			'name' : '待审核'
		}, {
			'id' : 2,
			'name' : '启用'
		}, {
			'id' : 3,
			'name' : '停用'
		}]
	});

	$('#sc_memberGrade').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:240,
	    panelHeight:'auto',
		data : [ {
			'id' : 173,
			'name' : '推荐会员'
		}, {
			'id' : 174,
			'name' : '银牌会员'
		}, {
			'id' : 156,
			'name' : '金牌会员'
		}, {
			'id' : 175,
			'name' : '银冠会员'
		}, {
			'id' : 176,
			'name' : '金冠会员'
		}, {
			'id' : 2,
			'name' : '已到期会员'
		}]
	});
	
}

/**根据查询条件查询匹配的数据*/
function doSearch(){
	var sc_moneyFrom = $('#sc_moneyFrom').val();
	var sc_moneyTo = $('#sc_moneyTo').val();
	var sc_memberId = $('#sc_memberId').val();
	var sc_username = $('#sc_username').val();
	
	var sc_type = $('#sc_type').combobox('getValue');
	var sc_status = $('#sc_status').combobox('getValue');
	var sc_order = $('#sc_order').combobox('getValue');
	
	var sc_addTimeFrom = $('#sc_addTimeFrom').datebox('getValue');
	var sc_addTimeTo = $('#sc_addTimeTo').datebox('getValue');
	var sc_memberGrade = $('#sc_memberGrade').combobox('getValue');
	if(sc_addTimeFrom && !sc_addTimeTo){
		sc_addTimeTo = sc_addTimeFrom;
	}
	if(!sc_addTimeFrom && sc_addTimeTo){
		sc_addTimeFrom = sc_addTimeTo;
	}
	var sc_lastLoginTimeFrom = $('#sc_lastLoginTimeFrom').datebox('getValue');
	var sc_lastLoginTimeTo = $('#sc_lastLoginTimeTo').datebox('getValue');
	if(sc_lastLoginTimeFrom && !sc_lastLoginTimeTo){
		sc_lastLoginTimeTo = sc_lastLoginTimeFrom;
	}
	if(!sc_lastLoginTimeFrom && sc_lastLoginTimeTo){
		sc_lastLoginTimeFrom = sc_lastLoginTimeTo;
	}

	if(!sc_type){sc_type=""}
	if(!sc_status){sc_status=""}
	if(!sc_order){sc_order=""}
	if(!sc_memberGrade){sc_memberGrade=""}
	var common_queryObj = GRID_QUERYOBJ;
	
	common_queryObj.moneyFrom = sc_moneyFrom;
	common_queryObj.moneyTo = sc_moneyTo;
	common_queryObj.memberId = sc_memberId;
	common_queryObj.usernameLike = sc_username;
	
	common_queryObj.typeId = sc_type;
	common_queryObj.status = sc_status;
	common_queryObj.sc_order = sc_order;
	
	common_queryObj.addTimeFrom = sc_addTimeFrom;
	common_queryObj.addTimeTo = sc_addTimeTo;
	common_queryObj.lastLoginTimeFrom = sc_lastLoginTimeFrom;
	common_queryObj.lastLoginTimeTo = sc_lastLoginTimeTo;
	common_queryObj.memberGrade = sc_memberGrade;
	common_queryObj.isDelete = 2;
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$('#sc_moneyFrom').val('');
	$('#sc_moneyTo').val('');
	$('#sc_memberId').val('');
	$('#sc_username').val('');
	
	$('#sc_type').combobox('reset');
	$('#sc_status').combobox('reset');
	$('#sc_order').combobox('reset');
	
	$('#sc_lastLoginTimeFrom').datebox('reset');
	$('#sc_lastLoginTimeTo').datebox('reset');
	$('#sc_addTimeFrom').datebox('reset');
	$('#sc_addTimeTo').datebox('reset');
	$('#sc_memberGrade').combobox('reset');
}

function returnBack(){
	window.location.href = "/back/user.html";
}

function recoverUser(){
	//var rows = $('#datagrid').datagrid('getSelections');//得到选择的记录，可多条
	var rows = $('#datagrid').datagrid('getChecked');//得到选择的记录，可多条
	if(rows.length){//如果有选择的记录,rows.length为选中记录的条数
		$.messager.confirm('提示','是否确认还原？',function(r){
			if(r){
				var ids='';
				for(var i = 0, len = rows.length; i < len; i ++){
					ids += ','+rows[i].id;
				}
				if(ids){
					ids = ids.substring(1);
				}
				$.post('/recoverDelUser',{ids:ids},function(rsp){
					if(rsp.status=='success'){
						$('#datagrid').datagrid('reload');
						$('#datagrid').datagrid('clearSelections');
						$('#datagrid').datagrid('clearChecked');
						$.messager.alert('提示',rsp.msg);
					}else{
						$.messager.alert('提示','恢复失败');
					}
				},'json');
			}	
		});
	}else{//如果没有选择记录就进行提示
		$.messager.alert('提示','请勾选数据');   
	}
}

function thoroughDeleteUser(){
	//var rows = $('#datagrid').datagrid('getSelections');//得到选择的记录，可多条
	var rows = $('#datagrid').datagrid('getChecked');//得到选择的记录，可多条
	if(rows.length){//如果有选择的记录,rows.length为选中记录的条数
		$.messager.confirm('提示','是否确认进行彻底删除？',function(r){
			if(r){
				var ids='';
				for(var i = 0, len = rows.length; i < len; i ++){
					ids += ','+rows[i].id;
				}
				if(ids){
					ids = ids.substring(1);
				}
				$.post('/thoroughDeleteUser',{ids:ids},function(rsp){
					if(rsp.status=='success'){
						$('#datagrid').datagrid('reload');
						$('#datagrid').datagrid('clearSelections');
						$('#datagrid').datagrid('clearChecked');
						$.messager.alert('提示',rsp.msg);
					}else{
						$.messager.alert('提示','彻底删除失败');
					}
				},'json');
			}	
		});
	}else{//如果没有选择记录就进行提示
		$.messager.alert('提示','请勾选数据');   
	}
}



