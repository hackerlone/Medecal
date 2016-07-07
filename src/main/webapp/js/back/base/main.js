var HEIGHT = document.documentElement.clientHeight;
$(function() {
	loadMenuOfCurrentUser();
	$('#updatePasswordWin').window({modal:true,width:300,height:200});
	$('#updatePasswordWin').window('close');
	
	//showMain('用户角色','/back/admin');//TODO test
});

function initMenuTree(){
	$("#firstpane .menu_body:eq(0)").show();
	$("#firstpane h3.menu_head").click(function(){
		$(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
		$(this).siblings().removeClass("current");
	});
	$("#secondpane .menu_body:eq(0)").show();
	$("#secondpane h3.menu_head").mouseover(function(){
		$(this).addClass("current").next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
		$(this).siblings().removeClass("current");
	});
}

function loadMenuOfCurrentUser(){
	$.post("/back/getMenuListOfCurrentUser", null, function(rsp){
		if(rsp.success){
			var menuList = rsp.menuList;
			var rootMenuList = [];
			for(var i in menuList){
				var menu = menuList[i];
				if(!menu.parentId)rootMenuList.push(menu);
			}
			var menuDom = '';
			for(var j in rootMenuList){
				var rootMenu = rootMenuList[j];
				menuDom += '<h3 class="menu_head">'+rootMenu.name+'</h3>';
				menuDom += '<div style="display:none" class="menu_body">';
				for(var k in menuList){
					var m = menuList[k];
					if(m.parentId && m.parentId == rootMenu.id){
						menuDom += '<a onclick="showMain(\''+m.name+'\',\''+m.url+'\')">'+m.name+'</a>';
					}
				}
				menuDom += '</div>';
			}
			$("#firstpane").html(menuDom);
			initMenuTree();//渲染菜单
		}else{
			$.messager.alert('提示', rsp.msg);
		}
	},'json');
}

function loadQuickMenu(){
	$.post("/back/getQuickMenuList",{userId:$("#userId").val()},function(rsp){
		if(rsp.status == 'success'){
			var quickMenu = rsp.rows;
			var dom ="";
			if(quickMenu.length > 0){
				for(var i = 0 ;i < quickMenu.length;i++){
					dom = '<span class="foot_item" onclick="showMain(\''+quickMenu[i].menuName+'\',\''+quickMenu[i].menuUrl+'\')">'+quickMenu[i].menuName+'</span>&nbsp;';
					$(".foot_items").append(dom);
				}
			}
		}else{
			$.messager.alert('提示',rsp.msg);
		}
	},'json');
}

function showMain(name,path){
	var base = '';
	path = base + path;
	var flag = $('#main').tabs('exists',name);
	if(flag){
		//$('#main').tabs('select',name);
		//return;
		$('#main').tabs('close',name);
	}
	$('#main').tabs('add',{
	    title:name,
	    content:'<iframe name="mainFrame" scrolling="auto" frameborder="0" src="'+path+'" style="width: 100%;height: 99.5%;border: 0;"></iframe>',
	    //href: path,
	    closable:true,
	    tools:[/*{
	        iconCls:'icon-mini-refresh',
	        handler:function(){
	            alert('refresh');
	        }
	    }*/]
	});
	var tab = $('#main').tabs('getSelected');
	var index = $('#main').tabs('getTabIndex',tab);
}

function login(){
	window.open("/");
    //window.location.href="/";
}


function logout(){
	$.post('/logout',null,function(rsp){
	     if(rsp.status == 'success'){     	
	     	window.location.href = "/back/login.html";
	     }else{
	     	alert('提示',rsp.msg);
	     }
	},'json');
	
}

function openUpdatePwdWin(){
	$('#updatePasswordWin').css('display','');
	$('#updatePasswordWin').window('open');
}

function closeUpdatePwdWin(){
	$('#updatePasswordWin').window('close');
	$('#updatePasswordWin').css('display','none');
}

function refresh(){
	location.reload();
	
}

function openUpdatePassword(){
	$('#updatePasswordWin').css('display','');
	$('#updatePasswordWin').window('open');
	$('#oldPsd').val('');
	$('#newPsd1').val('');
	$('#newPsd2').val('');
}

function updatePassword(){
	
	$('#updatePsdTip').text();
	var oldPsd = $('#oldPsd').val();
	var newPsd1 = $('#newPsd1').val();
	var newPsd2 = $('#newPsd2').val();
	if(!oldPsd || !newPsd1 || !newPsd2){
		$('#updatePsdTip').text('请填写完整。');return;
	}
	if(newPsd1 && newPsd2 && newPsd1 != newPsd2){
		$('#updatePsdTip').text('新密码两次输入不一致。');return;
	}
	
	$.post('/back/updateAdminPassword',{oldPsd:oldPsd,newPsd:newPsd1},function(rsp){
	     if(rsp.success){	
	     	$('#updatePasswordWin').window('close');
	     	$.messager.alert('提示','修改密码成功。请重新登录');
	     	localStorage.clear();
	     }else{
	    	 $.messager.alert('提示', rsp.msg);
	     }
	},'json');
	
	
	
}

