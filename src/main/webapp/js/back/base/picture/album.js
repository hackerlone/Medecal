var HEIGHT = document.documentElement.clientHeight;
var WIDTH = document.documentElement.clientWidth;
if(WIDTH<1000)WIDTH=1000;
IS_SHOW_TRASH = false;
CURRENT_PIC_TYPE = 0;
SAVING_FLAG = 0;
$(function() {
	GRID_QUERYOBJ = {};//查询条件
	loadGrid();
	initSearch();
	//paramMapData();
});

/**加载用户列表*/
function loadGrid(){
	$('#datagrid').datagrid({
	    url:'/back/getAlbumList',
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
			{field:'serial',title:'相册编号',width:160,align:'center',formatter: function(value,row,index){
				if(!value)return '';
				var dom = '<span>'+value+'</span>' +
				'<button class="copy_btn pointer fr" data-clipboard-text="'+value+'">复制</button>';
	        	return dom;
	        }},
	        {field:'title',title:'相册名称',width:180,align:'center'},
	        {field:'picPath',title:'相册封面',width:200,align:'center',formatter: function(value,row,index){
	        	var logo = '<a href="'+value+'" target="_blank"><img style="height:60px;cursor:pointer;" src="'+value+'"/></a>';
	        	if(!value)logo = '<span style="line-height:60px;">暂无封面<span>';
	        	return logo;
	        }},
	        {field:'operate',title:'操作',width:100,align:'center',formatter: function(value,row,index){
	        	return '<span class="g_alive"><span class="opt_cuation" onclick="openAlbumWin('+index+',\'update\')">修改</span>'+//jumpToAlbumUpdate
	        		   ' |  <span class="opt_green" onclick="openAlbumWin('+index+',\'read\')">查看</span></span>'+//jumpToAlbum
	        		   '<span class="g_trash"><span class="opt_cuation" onclick="batchThoroughDelete('+row.id+')">彻底删除</span>'+
	        		   ' |  <span class="opt_green" onclick="batchRecover('+row.id+')">恢复</span></span>';
	        }},
	        {field:'typeName',title:'相册类型',width:120,align:'center'},
	        {field:'pics',title:'相册图片',width:200,align:'center',formatter: function(value,row,index){
	        	return '<span class="colorGreen pointer" onclick="subShowMain(\'图片\', \'/back/picture?albumId='+row.id+'\')">点击查看</span>';
	        }},
	        {field:'username',title:'所属用户',width:200,align:'center',formatter: function(value,row,index){
	        	if(!value)return '';
				var dom = '<span>'+value+'</span>' +
				'<button class="copy_btn pointer fr" data-clipboard-text="'+row.userSerial+'">复制编号</button>';
	        	return dom;
	        }},
	        {field:'createdAt',title:'添加时间',width:120,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'createdBy',title:'添加人',width:120,align:'center'},
	        {field:'updatedAt',title:'修改时间',width:120,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'updatedBy',title:'修改人',width:120,align:'center'},
	        {field:'deletedAt',title:'删除时间',width:120,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'deletedBy',title:'删除人',width:120,align:'center'}
	        ]],
	    /*,onSelect:function(rowIndex, rowData){onClickRowOfGrid(rowIndex, rowData);},
	    onClickRow: function(index, row){},
	    loadFilter: function(data){return data;},
		onLoadSuccess:function(data){} */
	        onLoadError: function(data){
		    	lh.backDatagridErrorCheck(data);
		    },
		    onDblClickRow: function(index, row){
		    	openAlbumWin(index, 'read');
	        },
	    onLoadSuccess:function(data){
	       	if(IS_SHOW_TRASH){
	       		$('.g_alive').hide();
	       		$('.g_trash').show();
	       	}else{
	       		$('.g_alive').show();
	       		$('.g_trash').hide();
	       	}
	    	initClipboard();//复制功能
	   }  
	});
}

/** grid 行点击事件 */
/**function onClickRowOfGrid(rowIndex, rowData){}*/

function loadCombo(){
	
	$('#f_albumType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		url:'/getDictCombo?parentId=80',
		onLoadSuccess:function(){
			$('#f_albumType').combobox('setValue',82);//商品图片
		}
	});
	
}

/** 关闭窗口 */
function closeAlbumWin(){
	$('#albumWin').window('close');
	//$('#datagrid').datagrid('clearSelections');
	//$('#datagrid').datagrid('clearChecked');
}

/** 提交表单 */
function submitAlbum(){
	//$('#albumForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#albumForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}

/** 添加用户 */
function addAlbum(){
	openAlbumWin(-1,'add');
}

/** 打开窗口 */
function openAlbumWin(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = $('#albumForm');
	
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#albumForm').form('enableValidation');
	       var flag = $('#albumForm').form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var album = getAlbum(id);//得到用户信息的字段值
	       	    $.post('/addOrUpdateAlbum',album,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeAlbumWin();
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
	$('#albumWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	$('#albumTip').html('');
	
	var domIds = "#f_albumName,#f_userSerial,#f_albumType";
	
	if(operation == 'add'){//添加用户
		$('#winSearchTr,#winSearchDivisionTr,#user_search_td').show();//显示查询按钮
		$('#albumSave,#user_search_td').show();
		$('#albumTable .readOnlyTr').hide();//隐藏只读字段
		$(domIds).textbox('readonly',false);//新增时设置为可编辑
	}else{//查看或修改
		$('#winSearchTr,#winSearchDivisionTr').hide();//隐藏查询按钮
		$('#albumTable .readOnlyTr').show();//显示只读字段
		var isReadOnly = false;
		if(operation == 'read'){//查看
			isReadOnly = true;
			$('#albumSave,#user_search_td').hide();
			$('#albumSave').hide();
			
		}else{//修改
			$('#albumSave,#user_search_td').show();
		}
		$(domIds).textbox('readonly',isReadOnly);//设置只读还是可编辑
		setAlbum(data);//设置用户详细信息字段值
	}
	
}

/** 得到用户信息的字段值 */
function getAlbum(id){
	var typeId = $('#f_albumType').combobox('getValue');
	var title = $('#f_albumName').textbox('getValue');
	var album = {
		typeId:typeId,
		title:title
	};
	var userId = $('#f_userId').val();
	if(userId)album.userId = userId;
	if(id){//有id为更新，无id为新增
		album.id = id;
	}
	return album;
}

/** 设置用户信息的字段值 */
function setAlbum(data){
	$('#f_albumType').combobox('setValue', data.typeId);
	$('#f_albumName').textbox('setValue', data.title);
	$('#f_serial').textbox('setValue', data.serial);
	$('#f_userSerial').textbox('setValue', data.userSerial);
	$('#f_username').textbox('setValue', data.username);
	//查询产生的只读字段：
	$('#f_userSerial').textbox('setValue', data.userSerial);
	$('#f_username').textbox('setValue', data.username);
	$('#f_userId').val(data.userId);//隐藏字段-用户ID
	//以下为只读字段：
}

function search(typeId){
	$('#datagrid').datagrid('load',{
		typeId : typeId
	});
}

function initSearch(){
	
	$('#sc_albumType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		url:'/getDictCombo?parentId=80',
		onLoadSuccess:function(){
			$('#sc_albumType').combobox('setValue',82);//商品图片
		}
	});
	
}


/**根据查询条件查询匹配的数据*/
function doSearch(){
	var sc_albumType = $('#sc_albumType').combobox('getValue');
	var sc_albumName = $('#sc_albumName').textbox('getValue');
	var sc_albumSerial = $('#sc_albumSerial').textbox('getValue');
	var sc_userSerial = $('#sc_userSerial').textbox('getValue');
	var sc_goodsSn = $('#sc_goodsSn').textbox('getValue');
	
	var sc_startTimeFrom = $('#sc_startTimeFrom').datebox('getValue');
	var sc_startTimeTo = $('#sc_startTimeTo').datebox('getValue');
	if(sc_startTimeFrom && !sc_startTimeTo){
		sc_startTimeTo = sc_startTimeFrom;
	}
	if(!sc_startTimeFrom && sc_startTimeTo){
		sc_startTimeFrom = sc_startTimeTo;
	}
	if(!sc_albumType){sc_albumType=""}
	/*var sc_mainStatus = $('#sc_mainStatus').combobox('getValue');
	if(!sc_mainStatus){sc_mainStatus=""}*/
	
	var common_queryObj = GRID_QUERYOBJ;
	common_queryObj.typeId = sc_albumType;
	common_queryObj.albumNameLike = sc_albumName;
	common_queryObj.albumSerialLike = sc_albumSerial;
	common_queryObj.userSerialLike = sc_userSerial;
	common_queryObj.goodsSnLike = sc_goodsSn;
	common_queryObj.startTimeFrom = sc_startTimeFrom;
	common_queryObj.startTimeTo = sc_startTimeTo;
	
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$('#sc_albumName,#sc_albumSerial,#sc_userSerial,#sc_goodsSn').textbox('reset');
	$('#sc_albumType').combobox('reset');
	$('#sc_startTimeFrom,#sc_startTimeTo').datebox('reset');
}

function searchUser(){
	searchUserBySerial();//common_back:通用跟据用户编号查询用户
}

/** 跳转到 修改用户 页面 */
/**function jumpToAlbumUpdate(){}*/
/** 跳转到 用户详情 页面 */
/**function jumpToAlbum(){}*/

/** 批量删除用户 */
function batchDelete(){
	commonBatchDelete('/back/updateAlbumDelete');//common_back:通用批量删除
}

/** 切换到回收站 */
function showTrach(){
	IS_SHOW_TRASH = true;
	commonShowTrash('#batchRecover,#batchThoroughDelete,#returnBack','#batchDelete,#addAlbum,#userInfoLink,#pictureLink,#showTrash');
}

/** 从回收站返回 */
function returnBack(){
	IS_SHOW_TRASH = false;
	commonReturnBack('#batchDelete,#addAlbum,#userInfoLink,#pictureLink,#showTrash','#batchRecover,#batchThoroughDelete,#returnBack');
}

/** 批量恢复用户 */
function batchRecover(id){
	commonBatchRecover('/back/updateAlbumRecover',null,id);//common_back:通用批量恢复
}

/** 批量强制删除用户 */
function batchThoroughDelete(id){
	commonBatchThoroughDelete('/back/deleteAlbumThorough',null,id);//common_back:通用批量强制删除
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

/** 跳转：图片 */
function jumpToPicture(){
	var url = "/back/picture";
	var id = $('#userId').val();
	if(id){
		url += "?userId="+id;
	}
	subShowMain('图片', url)
}

/** 跳转：新增或修改 */
function jumpToAlbumAddOrUpdate(id){
	var url = "/back/albumAddOrUpdate";
	if(id){
		url += "?albumId="+id;
	}
	window.location.href = url;
}

