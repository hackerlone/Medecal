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
	    url:'/back/getUserFundList',
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
	        	return '<span class="opt_cuation" onclick="openUserFundWin('+index+',\'edit\')">修改</span>'  
	        	+'&nbsp;|&nbsp;<span class="opt_green" onclick="openUserFundWin('+index+',\'read\')">查看</span>';
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
	        onDblClickRow: function(index, row){
	        	openUserFundWin(index, 'read');
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
	       	initClipboard();//复制功能
	   }  
	        
	});
	
}

/** grid 行点击事件 */
/**function onClickRowOfGrid(rowIndex, rowData){}*/

/** 关闭窗口 */
function closeUserFundWin(){
	$('#userFundWin').window('close');
	//$('#datagrid').datagrid('clearSelections');
	//$('#datagrid').datagrid('clearChecked');
}

/** 提交表单 */
function submitUserFund(){
	//$('#userFundForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#userFundForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}


/** 打开窗口 */
function openUserFundWin(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
	}
	var $form = $('#userFundForm');
	
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#userFundForm').form('enableValidation');
	       var flag = $('#userFundForm').form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var userFund = getUserFund(id);//得到用户信息的字段值
	       	    $.post('/back/addOrUpdateUserFund',userFund,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeUserFundWin();
				       	 },500);
					 }else{
						 $.messager.alert('提示',rsp.msg);
					 }
				},'json');
	       }
	       return false;
	    }
	});
	
	//loadCombo();//加载下拉列表数据（省市）
	$('#userFundWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	$('#userFundTip').html('');
	
	var domIds = "#f_avaliableMoney,#f_frozenMoney";

	//查看或修改
	$('#userFundTable .readOnlyTr').show();//显示只读字段
	var isReadOnly = false;
	if(operation == 'read'){//查看
		isReadOnly = true;
		$('#userFundSave').hide();
	}else{//修改
		$('#userFundSave').show();
	}
	$(domIds).textbox('readonly',isReadOnly);//设置只读还是可编辑
	setUserFund(data);//设置用户详细信息字段值
	
}

/** 得到用户信息的字段值 */
function getUserFund(id){
	var f_avaliableMoney = $('#f_avaliableMoney').textbox('getValue');
	var f_frozenMoney = $('#f_frozenMoney').textbox('getValue');
	
	var userFund = {
		avaliableMoney:f_avaliableMoney,
		frozenMoney:f_frozenMoney
	};
	if(id){//有id为更新，无id为新增
		userFund.id = id;
	}
	return userFund;
}

/** 设置用户信息的字段值 */
function setUserFund(data){
	//以下为只读字段：
	$('#f_avaliableMoney').textbox('setValue',data.avaliableMoney);
	$('#f_frozenMoney').textbox('setValue',data.frozenMoney);
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
/**function jumpToUserFund(){}*/


/** 跳转：用户信息 */
function jumpToUserInfo(){
	var url = "/back/userInfo";
	var id = $('#userId').val();
	if(id){
		url += "?userId="+id;
	}
	subShowMain('用户信息', url)
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


