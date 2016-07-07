/**
 * 描述：公用JS
 * 日期：2013年12月30日9:50:22
 */
LOADEDGRID = [];
/**
 * 判断用户是否登陆
 */
function isLogin(){
	$.ajaxSetup({
		error:function(data, textStatus) {
			if(data&&data.responseText=='toLogin'){
				window.parent.location.href="/index";
			}
		}
	});
}

/**
 * 描述：将简写的Json转换为Tree的标准Json格式
 * 作者：虞荣华
 * 日期：2013年12月30日9:50:22
 * @param rows:简写的json数据
 * @return 标准的json数据
 */

function convertTreeJsonData(rows){
	var rows = rows.data;//data才是实际数据 -- 暂时注释掉
    function exists(rows, parentId){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentId) return true;
        }
        return false;
    }
    
    var nodes = [];
    // 得到根节点们
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        if (!exists(rows, row.parentId)){
        	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
            var root = {id:row.id,text:row.name,checked:row.checked,iconCls:row.iconCls,attr:attr};
            nodes.push(root);
        }
    }
    
    var toDo = [];
    for(var i=0; i<nodes.length; i++){
        toDo.push(nodes[i]);
    }
    while(toDo.length){
        var node = toDo.shift();    // 父节点
        // 得到子节点们
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (row.parentId == node.id){
            	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
                var child = {id:row.id,text:row.name,checked:row.checked,iconCls:row.iconCls,attr:attr};
                if (node.children){
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}

/**
 * 描述：将简写的Json转换为Treegrid的标准Json格式
 * 作者：王玉川
 * 日期：2014年1月27日
 * @param rows:简写的json数据
 * @return 标准的json数据
 */
function convertTreeGridJsonData(rows){
	var rows = rows.data;//data才是实际数据 -- 暂时注释掉
    function exists(rows, parentId){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentId) return true;
        }
        return false;
    }
    
    var nodes = [];
    // 得到根节点们
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        if (!exists(rows, row.parentId)){
        	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
            var root = {id:row.id,text:row.deptName,higherDept:row.higherDept,deptNote:row.deptNote,orderStatus:row.orderStatus,
            		businessId:row.businessId,business:row.business,deptTypeId:row.deptTypeId,
            		deptType:row.deptType,foundId:row.foundId,founder:row.founder,foundTime:row.foundTime,
            		editId:row.editId,editor:row.editor,editTime:row.editTime,remark:row.remark,attr:attr};
            nodes.push(root);
        }
    }
    
    var toDo = [];
    for(var i=0; i<nodes.length; i++){
        toDo.push(nodes[i]);
    }
    while(toDo.length){
        var node = toDo.shift();    // 父节点
        // 得到子节点们
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (row.parentId == node.id){
            	var attr = {url:row.url,parentId:row.parentId,isBlnToRole:row.isBlnToRole};//添加自定义属性
                var child = {id:row.id,text:row.deptName,higherDept:row.higherDept,deptNote:row.deptNote,orderStatus:row.orderStatus,
                		businessId:row.businessId,business:row.business,deptTypeId:row.deptTypeId,
                		deptType:row.deptType,foundId:row.foundId,founder:row.founder,foundTime:row.foundTime,
                		editId:row.editId,editor:row.editor,editTime:row.editTime,remark:row.remark,attr:attr};
                if (node.children){
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}

/**判断按钮的显隐性
 * 参数：buttonIds：以逗号分隔的按钮ID字符串(对应数据库中的ID)
 * 参数：buttonDomIds：以逗号分隔的按钮ID名字字符串(对应页面中的ID值)
 * 这两个参数中的值对应的按钮顺序应该一致
 * 如：  buttonIds='1,2,3'
 * 		buttonDomIds='selfDispatchBtnId,其他按钮ID'
 * */
function isAllowedToShowButtons(buttonIds,buttonDomIds){
	if(!buttonIds || !buttonDomIds){return;}
	$.post('/CRMTWO/user_menuAction_isAllowedToShowButtons',{buttonIds:buttonIds},function(rsp){
	     if(rsp.status=='success'){
	     	var isShowBtnStr = rsp.isShowBtnStr;
	     	if(isShowBtnStr){
	     		var buttonDomIdsAry = buttonDomIds.split(',');//分割多个按钮
	     		var isShowBtnAry = isShowBtnStr.split(',');
	     		for(var i in isShowBtnAry){
	     			if(isShowBtnAry[i]&&isShowBtnAry[i]=="1"){$('#'+buttonDomIdsAry[i]).css('display','');}
	     		}
	     	}
		 }
	},'json');
}

/**渲染表格
 * 调整表格长度，修改分页栏
 * 参数：dataGridOuterDivId：表格的外层的某一个div id;
 * */
function renderDataGrid(dataGridOuterDivId){
	var width = $('#'+dataGridOuterDivId+' .datagrid-view').css('width');  
	$('#'+dataGridOuterDivId+' .datagrid-btable').css('width',width);
	$('#'+dataGridOuterDivId+' .datagrid-view2 .datagrid-htable').attr('style',"width:"+width+"; table-layout: auto;");
	
//	setTimeout(function(){
//		var text = $('#'+dataGridOuterDivId+' .pagination-info').html();
//		text = text.replace(/\d+/g,function($1){return ' <font color="chocolate">'+$1+'</font> '});
//		$('#'+dataGridOuterDivId+' .pagination-info').html(text);
//	},0);
	$('#'+dataGridOuterDivId+' .note').tooltip({//悬浮窗提示  
                onShow: function(){  
                    $(this).tooltip('tip').css({   
                        backgroundColor: 'white',
            	 		borderColor: 'gray'	
                    });  
                }  
            });
}

/**克隆对象
 * */
function clone(obj){
	var o;
	switch(typeof obj){
	case 'undefined': break;
	case 'string'   : o = obj + '';break;
	case 'number'   : o = obj - 0;break;
	case 'boolean'  : o = obj;break;
	case 'object'   :
		if(obj === null){
			o = null;
		}else{
			if(obj instanceof Array){
				o = [];
				for(var i = 0, len = obj.length; i < len; i++){
					o.push(clone(obj[i]));
				}
			}else{
				o = {};
				for(var k in obj){
					o[k] = clone(obj[k]);
				}
			}
		}
		break;
	default:		
		o = obj;break;
	}
	return o;	
}

/**验证数字
 * inputDomId:需要验证的输入框的ID
 * */
function validateNum(inputDomId){
	if(inputDomId && (typeof inputDomId =="string")){
		var value = $('#'+inputDomId).val();
		var flag = /^[0-9]+\.?[0-9]{0,9}$/i.test(value);
		if(!flag){
			$('#'+inputDomId).val('');
		}
	}
}
/**验证数字
 * 只能输入正整数 
 * inputDomId:需要验证的输入框的ID
 * */
function validateNums(inputDomId){
	if(inputDomId && (typeof inputDomId =="string")){
		var value = $('#'+inputDomId).val();
		var flag = /^[+]?[1-9]+\d*$/i.test(value);
		if(!flag){
			$('#'+inputDomId).val('');
		}
	}
}

/**session过期返回登录页面*/
$.fn.datagrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
	onLoadError: function(data){
		if(data&&data.responseText=='toLogin'){
			window.parent.location.href="/index";
		}
	}
});
/**默认渲染分页*/
/*$.fn.pagination.defaults = $.extend({}, $.fn.pagination.defaults, {
	beforePageText: '第',//页数文本框前显示的汉字
    afterPageText: '页    共 {pages} 页'
//	displayMsg : "显示 <font color='chocolate'>{from}</font> 到 <font color='chocolate'>{to}</font>, 共 <font color='chocolate'>{total}</font> 记录"
});*/
