var HEIGHT = document.documentElement.clientHeight;
var WIDTH = document.documentElement.clientWidth;
if(WIDTH<1000)WIDTH=1000;
IS_SHOW_TRASH = false;
TEMPDATA = null;
SAVING_FLAG = 0;
$(function() {
	GRID_QUERYOBJ = {applyTypeNOTIN:"97"};//查询条件
	loadGrid();
	initData();
	$('#agreeDetailForm').form('disableValidation');
	addAgreeDetailForm();
});

/**加载用户列表*/
function loadGrid(){
	$('#datagrid').datagrid({
	    url:'/back/getApplyList',
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
	        /*{field:'applySerial',title:'申请用户编号',width:220,align:'center',formatter: function(value,row,index){
				var dom = '<span>'+value+'</span>' +
				'<button class="copy_btn pointer fr" data-clipboard-text="'+value+'">复制</button>';
	        	return dom;
	        }},*/
	        {field:'userName',title:'申请用户',width:220,align:'center'},
	        {field:'realName',title:'申请用户真实姓名',width:220,align:'center'},
	        {field:'instName',title:'申请机构',width:220,align:'center'},
	        {field:'mainStatus',title:'状态',width:60,align:'center',formatter: function(value,row,index){
	        	var status = "未处理";
	        	if(value == 2){status = '<span style="color:orange">已同意</span>';}
	        	if(value == 3){status = '<span style="color:orange">未同意</span>';}
	        	return status;
	        }},
	        {field:'dictName',title:'申请类型',width:220,align:'center'},
	        {field:'applyDate',title:'申请时间',width:160,align:'center',formatter: function(value,row,index){
	        	/*var dateStr = "";
	        	if(value){
	        		var d = new Date(value);
	        		var month = d.getMonth()+1;
	        		dateStr = ''+d.getFullYear()+'/'+month+'/'+d.getDate();
	        	}*/
	        	return formatDate(value,1,1);
	        }},
	        //{field:'message',title:'申请内容',width:220,align:'center'},
	        //{field:'reply',title:'回复消息',width:220,align:'center'},
	        {field:'operate',title:'操作',width:160,align:'center',formatter: function(value,row,index){
	        	return  '<span class="g_alive"><span  style="cursor: pointer;color: green" onclick="openApplyDetailWin('+index+')">查看详情</span></span>'
	        	+'<span class="g_trash"><span  style="cursor: pointer;color: #EC4949;" onclick="batchThoroughDelete('+row.id+')">彻底删除</span>'
	        	+'&nbsp;|&nbsp;<span  style="cursor: pointer;color: green" onclick="batchRecover('+row.id+')">恢复</span></span>';
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
	    	openApplyDetailWin(index, 'read');
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
	$('#sc_type').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:200,
	    url:'/back/getApplyTypeList'
	});
	$('#mainStatus').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:'auto',
	    data:[{
		    	'id' : 1,
		    	'name' : '未处理'
	    	},{
	    		'id' : 2,
	    		'name' : '已同意'
	    	},{
	    		'id' : 3,
	    		'name' : '未同意'
	    	}]
	});
}

/**根据查询条件查询匹配的数据*/
function doSearch(){
	var message = $("#message").textbox('getValue');
	var reply = $("#reply").textbox('getValue');
	var applySerial = $("#applySerial").textbox('getValue');
	var applyName = $("#applyName").textbox('getValue');
	var realName = $("#realName").textbox('getValue');
	var applyDate = $("#applyDate").datebox('getValue');
	var applyType = $("#sc_type").combobox('getValue');
	var mainStatus = $("#mainStatus").combobox('getValue');
	var instSerial = $("#instSerial").textbox('getValue');
	var common_queryObj = GRID_QUERYOBJ;
	common_queryObj.message = message;
	common_queryObj.reply = reply;
	common_queryObj.applyDate = applyDate;
	common_queryObj.applyType = applyType;
	common_queryObj.instSerial = instSerial;
	common_queryObj.applySerial = applySerial;
	common_queryObj.applyName = applyName;
	common_queryObj.realName = realName;
	common_queryObj.mainStatus = mainStatus;
	$('#datagrid').datagrid('load',common_queryObj);  
}
/**重置查询查询条件*/
function clearSearch(){
	$("#message").textbox('setValue','');
	$("#instSerial").textbox('setValue','');
	$("#reply").textbox('setValue','');
	$("#applySerial").textbox('setValue','');
	$("#applyName").textbox('setValue','');
	$("#realName").textbox('setValue','');
	$("#applyDate").datebox('setValue','');
	$("#sc_type").combobox('setValue','');
	$("#mainStatus").combobox('setValue','');
}
/**跳转到修改页面*/
function updateApply(id){
}

/**添加数据*/
function addApply(){
	
}

function loadCombo(){
	$('#f_applyType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:false,
	    panelHeight:200,
	    url:'/back/getApplyTypeList'
	});
	$('#agreeType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:200,
	    url:'/back/getApplyTypeList'
	});
	
	$('#type').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple:false,
	    required:true,
	    panelHeight:200,
	    url:'/back/getForumTypeList'
	});
}

/** 关闭窗口 */
function closeApplyDetailWin(){
	$('#applyDetailWin').window('close');
	//$('#datagrid').datagrid('clearSelections');
	//$('#datagrid').datagrid('clearChecked');
}
/** 关闭窗口 */
function closeAgreeDetailWin(){
	$('#agreeDetailWin').window('close');
	//$('#datagrid').datagrid('clearSelections');
	//$('#datagrid').datagrid('clearChecked');
}

/** 提交表单 */
function submitApplyDetail(){
	//$('#applyDetailForm').submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#applyDetailForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}

/** 打开窗口 */
function openApplyDetailWin(index,operation){
	var rows,data;
	if(index >= 0){
		rows = $('#datagrid').datagrid('getRows');
		data = rows[index];//获取该行的数据
		TEMPDATA = data;
	}
	var $form = $('#applyDetailForm');
	$form.form({
	    url:'',
	    onSubmit: function(){
	       $('#applyDetailForm').form('enableValidation');
	       var flag = $('#applyDetailForm').form('validate');
	       if(flag){
	       		var id;
	       		if(operation != 'add')id = data.id;
	       		var apply = getApplyDetail(id);//得到用户信息的字段值
	       	    $.post('/back/addOrUpdateApply',apply,function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeApplyDetailWin();
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
	$('#applyDetailWin').window('open');
	$form.form('clear');
	$form.form('disableValidation');
	$('#applyDetailTip').html('');
	$('#applyDetailTable .readOnlyTr').show();//显示只读字段
	$('#winSearchTr,#winSearchDivisionTr').hide();//隐藏查询按钮
	$('#f_reply').textbox('readonly',true);
	$("#applyDetailSave").hide();
	setApplyDetail(data);//设置用户详细信息字段值
	
}

/** 得到用户信息的字段值 */
function getApplyDetail(id){
	var userId = $('#f_userId').val();
	var applyDate = $('#f_applyDate').datetimebox('getValue');
	var applyType = $('#f_applyType').combobox('getValue');
	var instId = $('#f_instType').combobox('getValue');
	var message = $('#f_message').textbox('getValue');
	var reply = $('#f_reply').textbox('getValue');
	var apply = {
		userId : userId,
		applyDate : applyDate,
		applyType : applyType,
		instId : instId,
		message : message,
		reply : reply,
		mainStatus :3,
		file1 : TEMPDATA.file1
	};
	if(id){//有id为更新，无id为新增
		apply.id = id;
	}
	return apply;
}

/** 设置用户信息的字段值 */
function setApplyDetail(data){
	//以下为只读字段：
	$('#f_serial').textbox('setValue', data.userSerial);
	$('#f_username').textbox('setValue', data.userName);
	$('#f_realName').textbox('setValue', data.realName);
	$('#f_userId').val(data.userId);//隐藏字段-用户ID
	if(data.applyDate){
		/*var d = new Date(data.applyDate);
		var month = d.getMonth()+1;
		dateStr = d.getFullYear()+'-'+month+'-'+d.getDate();*/
		$('#f_applyDate').datetimebox('setValue',formatDate(data.applyDate,1,1));
	}
	var fileList = data.file1+","+data.file2+","+data.file3+","+data.file4;
	fileList = fileList.split(",");
	$('#file').empty();
	if(fileList[0] && fileList[0] != 'null'){
		var dom ='<td class="td_pad"  colspan="3">附件：<span class="colorGray"></span>'
			+'<div>'
			for(var i in fileList){
				if(fileList[i] && fileList[i] != 'null'){
					dom+='<a href="'+fileList[i]+'" target="_blank"><img src="'+fileList[i]+'" style="height:60px;cursor:pointer;"/></a>&nbsp;'
				}
			}
		dom+='</div>'
		+'</td>'
		$("#file").append(dom);
	}
	$('#f_applyType').combobox('setValue',data.applyType);
	$('#f_instType').combobox('setValue',data.instId);
	$('#f_message').textbox('setValue',data.message);
	if(data.reply){
		$('#f_reply').textbox('setValue',data.reply);
		$("#applyDetailDisAgree").hide();
		$("#applyDetailAgree").hide();
	}else{
		$("#applyDetailDisAgree").show();
		$("#applyDetailAgree").show();
	}
	if(data.mainStatus == 2 || data.mainStatus == 3){
		$("#applyDetailAgree,#applyDetailDisAgree").hide();
	}
	if(data.instId){
		$("#applyInstType").show();
	}
}

function applyDetailDisAgree(){
	$("#f_reply").textbox('readonly',false);
	$("#applyDetailSave").show();
	$("#applyDetailDisAgree").hide();
	$("#applyDetailAgree").hide();
}

function applyDetailAgree(){
	$('#agreeDetailWin').window('open');
	setAgreeData();
}

function setAgreeData(){
	$('#serial').textbox('setValue', TEMPDATA.userSerial);
	$('#username').textbox('setValue', TEMPDATA.userName);
	$('#realname').textbox('setValue', TEMPDATA.realName);
	$('#userId').val(TEMPDATA.userId);//隐藏字段-用户ID
	if(TEMPDATA.applyDate){
		/*var d = new Date(data.applyDate);
		var month = d.getMonth()+1;
		dateStr = d.getFullYear()+'-'+month+'-'+d.getDate();*/
		$('#agreeDate').datetimebox('setValue',formatDate(TEMPDATA.applyDate));
	}
	$('#agreeType').combobox('setValue',TEMPDATA.applyType);
	$('#instType').combobox('setValue',TEMPDATA.instId);
	var fileList = TEMPDATA.file1+","+TEMPDATA.file2+","+TEMPDATA.file3+","+TEMPDATA.file4;
	fileList = fileList.split(",");
	$('#fileList').empty();
	if(fileList[0] && fileList[0] != 'null'){
		var dom ='<td class="td_pad"  colspan="3">附件：<span class="colorGray"></span>'
			+'<div>'
			for(var i in fileList){
				if(fileList[i] && fileList[i] != 'null'){
					dom+='<a href="'+fileList[i]+'" target="_blank"><img src="'+fileList[i]+'" style="height:60px;cursor:pointer;"/></a>&nbsp;'
				}
			}
		dom+='</div>'
		+'</td>'
		$("#fileList").append(dom);
	}
	if(TEMPDATA.applyType == "95"){
			$("#forum").show();
			$('#attr1').textbox('setValue', TEMPDATA.attr1);
			$('#type').combobox('setValue', TEMPDATA.attr2);
			$('#attr3').textbox('setValue', TEMPDATA.attr3);
	}
	if(TEMPDATA.applyType == "94"){
		$("#wholesale").show();
		$('#attrWholesale').textbox('setValue', TEMPDATA.attr1);
	}
	if(TEMPDATA.applyType == "92" || TEMPDATA.applyType == "93"){
		$("#auction").show();
		$('#attrInst').textbox('setValue', TEMPDATA.attr1);
		$('#attr2').textbox('setValue', TEMPDATA.attr2);
	}
	if(TEMPDATA.instId){
		$("#agreeInstType").show();
	}
}


function submitAgreeDetail(){
	//$("#agreeDetailForm").submit();
	var timeRec = preventRepeat(10, SAVING_FLAG);
	if(timeRec){
		SAVING_FLAG = timeRec;
		$('#agreeDetailForm').submit();//执行操作
	}else{//重复提交
		return;//可进行提示或其他操作，这里直接返回，即重复提交时没有反应
	}
}

function addAgreeDetailForm(){
	$form = $("#agreeDetailForm");
	$('#agreeDetailForm').form('enableValidation');
    var flag = $('#agreeDetailForm').form('validate');
    if(flag){
		$form.form({
			url:'',
			onSubmit:function(){
				var agreeType = $('#agreeType').combobox('getValue')
				var obj = {};
				if(agreeType == "95"){//开通论坛
					obj.name = $("#attr1").textbox('getValue');
					obj.logo = TEMPDATA.file1;
					obj.typeId = $("#type").combobox('getValue');
					obj.forumUserId = $("#userId").val();
					 $.post('/back/addOrUpdateForum',obj,function(rsp){
						 if(rsp.status=='success'){
							 $('#datagrid').datagrid('reload');
							 //$('#datagrid').datagrid('clearSelections');
							 //$('#datagrid').datagrid('clearChecked');
					       	 setTimeout(function(){
					       		closeAgreeDetailWin();
					       	 },500);
						 }else{
							 $.messager.alert('提示',rsp.msg);
						 }
					},'json');
				}else if(agreeType == "91"){//实名认证
					obj.id = TEMPDATA.userId;
					obj.isRealAuth = 2;
					 $.post('/back/addOrUpdateUserDetail',obj,function(rsp){
						 if(rsp.status=='success'){
							 $('#datagrid').datagrid('reload');
							 //$('#datagrid').datagrid('clearSelections');
							 //$('#datagrid').datagrid('clearChecked');
					       	 setTimeout(function(){
					       		closeAgreeDetailWin();
					       	 },500);
						 }else{
							 $.messager.alert('提示',rsp.msg);
						 }
					},'json');
				}else if(agreeType == "94"){//批发城开通
					obj.name = $("#attrWholesale").textbox('getValue');
					obj.logo = TEMPDATA.file1;
					obj.userId = $("#userId").val();
					obj.mainStatus = 1;
					 $.post('/back/addOrUpdateWholesale',obj,function(rsp){
						 if(rsp.status=='success'){
							 $('#datagrid').datagrid('reload');
							 //$('#datagrid').datagrid('clearSelections');
							 //$('#datagrid').datagrid('clearChecked');
					       	 setTimeout(function(){
					       		closeAgreeDetailWin();
					       	 },500);
						 }else{
							 $.messager.alert('提示',rsp.msg);
						 }
					},'json');
				}/*else if(agreeType == "96"){
					obj.auctionName = $("#attr1").textbox('getValue');
					obj.userId = $("#userId").val();
					obj.mainStatus = 1;
					 $.post('/back/addOrUpdateAuctionQuick',obj,function(rsp){
						 if(rsp.status=='success'){
							 $('#datagrid').datagrid('reload');
							 //$('#datagrid').datagrid('clearSelections');
							 //$('#datagrid').datagrid('clearChecked');
					       	 setTimeout(function(){
					       		closeAgreeDetailWin();
					       	 },500);
						 }else{
							 $.messager.alert('提示',rsp.msg);
						 }
					},'json');
				}*/else if(agreeType == "92"){//专场
					obj.name = $("#attrInst").textbox('getValue');
					obj.tel = $("#attr2").textbox('getValue');
					obj.userId = $("#userId").val();
					obj.picPaths = TEMPDATA.file1;
					obj.mainStatus = 1;
					 $.post('/back/addOrUpdateAuctionInst',obj,function(rsp){
						 if(rsp.status=='success'){
							 $('#datagrid').datagrid('reload');
							 //$('#datagrid').datagrid('clearSelections');
							 //$('#datagrid').datagrid('clearChecked');
					       	 setTimeout(function(){
					       		closeAgreeDetailWin();
					       	 },500);
						 }else{
							 $.messager.alert('提示',rsp.msg);
						 }
					},'json');
				}else if(agreeType == "93"){//即时拍开通
					obj.name = $("#attrInst").textbox('getValue');
					obj.tel = $("#att2").textbox('getValue');
					obj.userId = $("#userId").val();
					obj.picPaths = TEMPDATA.file1;
					obj.mainStatus = 1;
					 $.post('/back/addOrUpdateAuctionQuickInst',obj,function(rsp){
						 if(rsp.status=='success'){
							 $('#datagrid').datagrid('reload');
							 //$('#datagrid').datagrid('clearSelections');
							 //$('#datagrid').datagrid('clearChecked');
					       	 setTimeout(function(){
					       		closeAgreeDetailWin();
					       	 },500);
						 }else{
							 $.messager.alert('提示',rsp.msg);
						 }
					},'json');
				}
				
				$.post('/back/addOrUpdateApply',{id:TEMPDATA.id,mainStatus:2},function(rsp){
					 if(rsp.status=='success'){
						 $('#datagrid').datagrid('reload');
						 //$('#datagrid').datagrid('clearSelections');
						 //$('#datagrid').datagrid('clearChecked');
				       	 setTimeout(function(){
				       		closeApplyDetailWin();
				       	 },500);
					 }else{
						 $.messager.alert('提示',rsp.msg);
					 }
				},'json');
			}
		});
    }
    return false;
}


/**批量删除数据**/
function batchDelete(){
	commonBatchDelete('/back/updateApplyDelete');//common_back:通用批量恢复
}

/**批量彻底删除**/
function batchThoroughDelete(id){
	commonBatchThoroughDelete('/back/deleteApplyThorough',null,id);//common_back:通用批量强制删除
}

/**批量恢复数据**/
function batchRecover(id){
	commonBatchRecover('/back/updateApplyRecover',null,id);//common_back:通用批量恢复
}

/**读取删除数据*/
function showTrash(){
	IS_SHOW_TRASH = true;
	commonShowTrash('#batchRecover,#batchThoroughDelete,#returnBack','#batchDelete,#addApply,#showTrash');
}
/**读取未删除数据**/
function returnBack(){
	IS_SHOW_TRASH = false;
	commonReturnBack('#batchDelete,#addApply,#showTrash','#batchRecover,#batchThoroughDelete,#returnBack');
}

function jumpToUserInfo(){
	var url = "/back/userInfo";
	subShowMain("用户信息",url);
}

