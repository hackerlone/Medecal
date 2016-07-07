function saveResetPassword(){
	if(lh.preventRepeat()){//防止重复提交
		$('#resetPasswordForm').submit();
	}else{
		lh.showRepeatTip();//提示重复提交
	}
}

var resetPassword = function(){
	var rows = $('#datagrid').datagrid('getChecked');//得到选择的记录，可多条
	if(!rows || !rows.length){//如果有选择的记录,rows.length为选中记录的条数
		$.messager.alert('提示','请勾选数据');return;
	}
	if(!rows || rows.length >= 2){
		$.messager.alert('提示','请勾选1数据');return;
	}
	$('#resetPasswordForm').form({
	    url:'',
	    onSubmit: function(){
	       $('#resetPasswordForm').form('enableValidation');
	       var flag = $('#resetPasswordForm').form('validate');
	       if(flag){
	    	   var id = rows[0].id;
	    	   var password = $("#password").textbox('getValue');
	    	   var url = getResetPasswordUrl();
	    	   lh.post('back',url,{id:id,password:password},function(rsp){
					 if(rsp.status=='success'){
						 $.messager.alert('提示', '密码修改成功');
						 $('#datagrid').datagrid('clearSelections');
						 $('#datagrid').datagrid('clearChecked');
						 setTimeout(function(){
							 closeResetPasswordWin();
					      },500);
					 }else{
						 $.messager.alert('提示',rsp.msg);
					 }
				},'json');
	       }
	       return false;
	    }
	});
	var id = rows[0].id;
	$("#resetPasswordWin").window('open');
	$("#resetPasswordForm").form('clear');
}

function closeResetPasswordWin(){
	$("#resetPasswordWin").window('close');
	$('#datagrid').datagrid('clearSelections');
	$('#datagrid').datagrid('clearChecked');
}