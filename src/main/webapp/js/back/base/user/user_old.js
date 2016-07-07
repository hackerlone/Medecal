var HEIGHT = document.documentElement.clientHeight;
var WIDTH = document.documentElement.clientWidth;
if(WIDTH<1000)WIDTH=1000;
IS_SHOW_TRASH = false;
SAVING_FLAG = 0;
$(function() {
	GRID_QUERYOBJ = {ur_shop:1,uf_money:1};//查询条件
	loadGrid();
	initSearch();
	//paramMapData();
	initUploadSimple({showEdBtns:true,showItemDiv:true,multiFlag:false,multiReplace:true,
		successFun:function(fileId, filePath){
			$("#upld_container_"+fileId).remove();
			$("#pic").attr('src', filePath);
	}});
});

/**获取参数*/
function paramMapData(){
	var paramMap = $('#paramMapJson').text();
	if(paramMap){
		var paramObj = JSON.parse(paramMap);
		var obj = paramObj.paramMap;
		$('#sc_type').combobox('setValue',obj.typeId);
		$('#sc_memberGrade').combobox('setValue',obj.memberGrade);
		$('#sc_order').combobox('setValue',obj.sc_order);
		$('#sc_status').combobox('setValue',obj.status);
		$('#sc_username').val(obj.usernameLike);
		$('#sc_memberId').val(obj.memberId);
		$('#sc_moneyFrom').val(obj.moneyFrom);
		$('#sc_moneyTo').val(obj.moneyTo);
		$('#sc_lastLoginTimeFrom').datebox('setValue',obj.lastLoginTimeFrom);
		$('#sc_lastLoginTimeTo').datebox('setValue',obj.lastLoginTimeTo);
		$('#sc_addTimeFrom').datebox('setValue',obj.addTimeFrom);
		$('#sc_addTimeTo').datebox('setValue',obj.addTimeTo);
		doSearch();
	}
}

/**加载用户列表*/
function loadGrid(){
	$('#datagrid').datagrid({
	    url:'/back/getUserList',
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
			{field:'serial',title:'用户编号',width:160,align:'center',formatter: function(value,row,index){
				if(!value)return '';
				var dom = '<span>'+value+'</span>' +
				'<button class="copy_btn pointer fr" data-clipboard-text="'+value+'">复制</button>';
	        	return dom;
	        }},
	        {field:'username',title:'用户名',width:140,align:'center'},
	        {field:'realName',title:'真实姓名',width:100,align:'center'},
	        {field:'operate',title:'操作',width:100,align:'center',formatter: function(value,row,index){
	        	return '<span class="g_alive"><span class="opt_cuation" onclick="openUserDetailWin('+index+',\'update\')">修改</span>'+//jumpToUserUpdate
	        		   ' |  <span class="opt_green" onclick="openUserDetailWin('+index+',\'read\')">查看</span></span>'+//jumpToUserDetail
	        		   '<span class="g_trash"><span class="opt_cuation" onclick="batchThoroughDelete('+row.id+')">彻底删除</span>'+
	        		   ' |  <span class="opt_green" onclick="batchRecover('+row.id+')">恢复</span></span>';
	        }},
	        {field:'lastLoginTime',title:'上次登陆时间',width:100,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }},
	        {field:'mainStatus',title:'状态',width:60,align:'center',formatter: function(value,row,index){
	        	var status = "启用";
	        	if(value == 2){status = '<span style="color:orange">停用</span>';}
	        	return status;
	        }},
	        {field:'isRealAuth',title:'实名认证',width:80,align:'center',formatter: function(value,row,index){
	        	var isRealAuth = "未认证";
	        	if(value == 2){isRealAuth = '<span style="color:green">已认证</span>';}
	        	return isRealAuth;
	        }},
	        {field:'avaliableMoney',title:'可用金额',width:120,align:'center'},
	        {field:'frozenMoney',title:'冻结金额',width:120,align:'center'},
	        {field:'shopName',title:'店铺',width:140,align:'center'},
	        {field:'province',title:'地区',width:160,align:'center',formatter: function(value,row,index){
	        	var province = row.provinceName,city = row.cityName;
	        	if(!province){province = '';}
	        	if(!city){city = '';}/*else{city = ','+city}*/
	        	return province+city;
	        }},
	        {field:'email',title:'邮箱',width:180,align:'center'},
	        {field:'phone',title:'电话',width:120,align:'center'},
	        {field:'qq',title:'qq',width:120,align:'center'},
	        {field:'weixin',title:'微信',width:120,align:'center'},
	        {field:'createdAt',title:'注册时间',width:120,align:'center',formatter: function(value,row,index){
	        	return getDateStr(value);
	        }}
	        ]],
	    /*,onSelect:function(rowIndex, rowData){onClickRowOfGrid(rowIndex, rowData);},
	    onClickRow: function(index, row){},
	    loadFilter: function(data){return data;},
		onLoadSuccess:function(data){} */
	        onLoadError: function(data){
		    	lh.backDatagridErrorCheck(data);
		    },
		    onDblClickRow: function(index, row){
		    	openUserDetailWin(index, 'read');
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
	
	$('#f_mainStatus').combobox({
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
	
	$('#f_isRealAuth').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		data : [{
			'id' : 1,
			'name' : '未认证'
		},{
			'id' : 2,
			'name' : '已认证'
		}]
	});
	
	$('#f_city').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight: 180
	    /*,onSelect: function(rec){
            var url = '/getCity?provinceId='+rec.id;
            $('#area').combobox('reload', url);
        }*/
	});

	$('#f_province').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight: 180,
		url:'/getProvince',
		onSelect: function(rec){
			var $city = $('#f_city');
			$city.combobox('clear');
            var url = '/getCity?provinceId='+rec.id;
            $city.combobox('reload', url);
        }
	});
}

/** 关闭窗口 */
function closeUserDetailWin(){
	$('#userDetailWin').window('close');
	//$('#datagrid').datagrid('clearSelections');
	//$('#datagrid').datagrid('clearChecked');
}

/** 提交表单 */
function submitUserDetail(){
	//$('#userDetailForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#userDetailForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}

/** 添加用户 */
function addUser(){
	openUserDetailWin(-1,'add');
}

/** 打开窗口 */
function openUserDetailWin(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = $('#userDetailForm');
	
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#userDetailForm').form('enableValidation');
	       var flag = $('#userDetailForm').form('validate');
	       var filePaths = $("#filePaths").val();
			var filePathArr = new Array();
			if(filePaths.indexOf(',') >= 0){
				filePaths = filePaths.substring(1);
			}
			filePathArr = filePaths.split(",");
			if(filePathArr.length > 1){
				$.messager.alert('提示','请先删除以前的图片,再重新上传');
				return;
			}
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var user = getUserDetail(id);//得到用户信息的字段值
	       		user.avatar = filePathArr[0];
	       		$.post('/back/addOrUpdateUserDetail',user,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeUserDetailWin();
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
	$('#userDetailWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	$('#userDetailTip').html('');
	
	var domIds = "#f_username,#f_realName,#f_isRealAuth,#f_mainStatus," +
				 "#f_province,#f_city,#f_phone,#f_email,"+
				 "#f_qq,#f_weixin,#f_lastLoginTime,#f_createdAt";
	
	if(operation == 'add'){//添加用户
		$('#userDetailTable .readOnlyTr').hide();//隐藏只读字段
		$('#userDetailSave').show();
		$(domIds).textbox('readonly',false);//新增时设置为可编辑
	}else{//查看或修改
		$('#userDetailTable .readOnlyTr').show();//显示只读字段
		var isReadOnly = false;
		if(operation == 'read'){//查看
			isReadOnly = true;
			$('#userDetailSave').hide();
		}else{//修改
			$('#userDetailSave').show();
		}
		$(domIds).textbox('readonly',isReadOnly);//设置只读还是可编辑
		setUserDetail(data);//设置用户详细信息字段值
	}
	
}

/** 得到用户信息的字段值 */
function getUserDetail(id){
	var username = $('#f_username').textbox('getValue');
	var realName = $('#f_realName').textbox('getValue');
	var lastLoginTime = $('#f_lastLoginTime').datebox('getValue');
	var mainStatus = $('#f_mainStatus').combobox('getValue');
	var isRealAuth = $('#f_isRealAuth').combobox('getValue');
	var createdAt = $('#f_createdAt').datebox('getValue');
	var phone = $('#f_phone').textbox('getValue');
	var province = $('#f_province').combobox('getValue');
	var city = $('#f_city').combobox('getValue');
	var email = $('#f_email').textbox('getValue');
	var qq = $('#f_qq').textbox('getValue');
	var weixin = $('#f_weixin').textbox('getValue');
	
	var user = {
		username:username,
		realName:realName,
		lastLoginTime:lastLoginTime,
		mainStatus:mainStatus,
		isRealAuth:isRealAuth,
		createdAt:createdAt,
		phone:phone,
		province:province,
		city:city,
		email:email,
		qq:qq,
		weixin:weixin
	};
	if(id){//有id为更新，无id为新增
		user.id = id;
	}
	return user;
}

/** 设置用户信息的字段值 */
function setUserDetail(data){
	$('#f_username').textbox('setValue',data.username);
	$('#f_realName').textbox('setValue',data.realName);
	$('#f_lastLoginTime').datebox('setValue',getDateStr(data.lastLoginTime));
	$('#f_createdAt').datebox('setValue',getDateStr(data.createdAt));
	$('#f_phone').textbox('setValue',data.phone);
	$('#f_province').combobox('setValue',data.province);
	$('#f_city').combobox('setValue',data.city);
	$('#f_email').textbox('setValue',data.email);
	$('#f_qq').textbox('setValue',data.qq);
	$('#f_weixin').textbox('setValue',data.weixin);
	var isRealAuth = data.isRealAuth;
	var mainStatus = data.mainStatus;
	if(!isRealAuth)isRealAuth = 1;
	if(!mainStatus)mainStatus = 1;
	$('#f_isRealAuth').combobox('setValue', isRealAuth);
	$('#f_mainStatus').combobox('setValue', mainStatus);
	//以下为只读字段：
	$('#f_shopName').textbox('setValue',data.shopName);
	$('#f_avaliableMoney').textbox('setValue',data.avaliableMoney);
	$('#f_frozenMoney').textbox('setValue',data.frozenMoney);
}

function search(typeId){
	$('#datagrid').datagrid('load',{
		typeId : typeId
	});
}

function initSearch(){
	
	$('#sc_mainStatus').combobox({
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
	
	$('#sc_isRealAuth').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
		data : [{
			'id' : 1,
			'name' : '未认证'
		},{
			'id' : 2,
			'name' : '已认证'
		}]
	});
	
	$('#sc_city').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight: 180
	    /*,onSelect: function(rec){
            var url = '/getCity?provinceId='+rec.id;
            $('#area').combobox('reload', url);
        }*/
	});

	$('#sc_province').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight: 180,
		url:'/getProvince',
		onSelect: function(rec){
			var $city = $('#sc_city');
			$city.combobox('clear');
            var url = '/getCity?provinceId='+rec.id;
            $city.combobox('reload', url);
        }
	});
	
}


/**根据查询条件查询匹配的数据*/
function doSearch(){
	var sc_username = $('#sc_username').textbox('getValue');
	var sc_realName = $('#sc_realName').textbox('getValue');
	var sc_phone = $('#sc_phone').textbox('getValue');
	var sc_serial = $('#sc_serial').textbox('getValue');
	var sc_mainStatus = $('#sc_mainStatus').combobox('getValue');
	var sc_isRealAuth = $('#sc_isRealAuth').combobox('getValue');
	var sc_province = $('#sc_city').combobox('getValue');
	var sc_city = $('#sc_city').combobox('getValue');
	
	
	/*var sc_addTimeFrom = $('#sc_addTimeFrom').datebox('getValue');
	var sc_addTimeTo = $('#sc_addTimeTo').datebox('getValue');
	if(sc_addTimeFrom && !sc_addTimeTo){
		sc_addTimeTo = sc_addTimeFrom;
	}
	if(!sc_addTimeFrom && sc_addTimeTo){
		sc_addTimeFrom = sc_addTimeTo;
	}
	*/
	var sc_lastLoginTimeFrom = $('#sc_lastLoginTimeFrom').datebox('getValue');
	var sc_lastLoginTimeTo = $('#sc_lastLoginTimeTo').datebox('getValue');
	if(sc_lastLoginTimeFrom && !sc_lastLoginTimeTo){
		sc_lastLoginTimeTo = sc_lastLoginTimeFrom;
	}
	if(!sc_lastLoginTimeFrom && sc_lastLoginTimeTo){
		sc_lastLoginTimeFrom = sc_lastLoginTimeTo;
	}

	if(!sc_mainStatus){sc_mainStatus=""}
	if(!sc_isRealAuth){sc_isRealAuth=""}
	if(!sc_province){sc_province=""}
	if(!sc_city){sc_city=""}
	
	var common_queryObj = GRID_QUERYOBJ;
	common_queryObj.username = sc_username;
	common_queryObj.realName = sc_realName;
	common_queryObj.phone = sc_phone;
	common_queryObj.serial = sc_serial;
	common_queryObj.mainStatus = sc_mainStatus;
	
	common_queryObj.isRealAuth = sc_isRealAuth;
	common_queryObj.province = sc_province;
	common_queryObj.city = sc_city;
	
	common_queryObj.lastLoginTimeFrom = sc_lastLoginTimeFrom;
	common_queryObj.lastLoginTimeTo = sc_lastLoginTimeTo;
	
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$('#sc_username,#sc_realName,#sc_phone,#sc_serial').textbox('reset');
	$('#sc_mainStatus,#sc_isRealAuth,#sc_province,#city').combobox('reset');
	$('#sc_lastLoginTimeFrom,#sc_lastLoginTimeTo').datebox('reset');
	
}


/** 跳转到 修改用户 页面 */
/**function jumpToUserUpdate(){}*/
/** 跳转到 用户详情 页面 */
/**function jumpToUserDetail(){}*/

/** 批量删除用户 */
function batchDelete(){
	commonBatchDelete('/back/updateUserDelete');//common_back:通用批量删除
}

/** 切换到回收站 */
function showTrach(){
	IS_SHOW_TRASH = true;
	commonShowTrash('#batchRecover,#batchThoroughDelete,#returnBack','#batchDelete,#addUser,#userMoneyLink,#userControlLink,#showTrash');
}

/** 从回收站返回 */
function returnBack(){
	IS_SHOW_TRASH = false;
	commonReturnBack('#batchDelete,#addUser,#userMoneyLink,#userControlLink,#showTrash','#batchRecover,#batchThoroughDelete,#returnBack');
}

/** 批量恢复用户 */
function batchRecover(id){
	commonBatchRecover('/back/updateUserRecover',null,id);//common_back:通用批量恢复
}

/** 批量强制删除用户 */
function batchThoroughDelete(id){
	commonBatchThoroughDelete('/back/deleteUserThorough',null,id);//common_back:通用批量强制删除
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
function jumpToUserControl(){
	var url = "/back/userControl";
	var id = $('#userId').val();
	if(id){
		url += "?userId="+id;
	}
	subShowMain('用户控制', url)
}

/** 跳转：新增或修改 */
function jumpToUserAddOrUpdate(id){
	var url = "/back/userAddOrUpdate";
	if(id){
		url += "?userId="+id;
	}
	window.location.href = url;
}

