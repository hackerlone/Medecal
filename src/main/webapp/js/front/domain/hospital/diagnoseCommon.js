var FLAG_ADD_DRUG = [];
$(function(){
	initData();
});
$(document).on('click','a[name="removeDrug"]',function(){
	var idDate = $(this).attr('val');
	idDate = JSON.parse(idDate);
	for(var i=0;i<FLAG_ADD_DRUG.length;i++){
		if(FLAG_ADD_DRUG[i].typeId==idDate.typeId && FLAG_ADD_DRUG[i].drugId==idDate.drugId){
			FLAG_ADD_DRUG.splice(i,i+1);
			console.log(FLAG_ADD_DRUG)
			break;
		}
	}
});
function initData(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	$('#tagName').bind('input propertychange focus', function() {
		$('#tagName').on('blur', function(){$('#phraseDiv').slideUp(600)});
		searchPhrase();
	});
	$("#phraseUl").click(function(e){
		completeTagName(e.target.innerText);
	});
}

function searchPhrase(){
	var tagName = $('#tagName').val();
	if(!tagName)return;
	lh.post('front','/getPhraseRecordArray',{name:tagName},function(rsp){
		if(!rsp)return;
		var rows = rsp;
		if(!rows || rows.length<=0)return;
		var dom = '';
		for(i in rows){
			var obj = rows[i];
			dom += '<li>'+obj.name+'</li>';
		}
		$('#phraseUl').html(dom);
		$('#phraseDiv').slideDown(300);
	});
}

function completeTagName(name){
	$('#tagName').val(name);
}

/** 更新病历 */
function getBaseDiagnoseFromDom(){
	var allergyHistory = $('#allergyHistory').val();
	var baseCondition = $('#baseCondition').val();
	
	var tagItems = $('#tagsContainer .tagsItem');
	var presItems = $('#prescriptionContainer .presItem');
	
	var preList = [];
	for(var i=0; i<presItems.length; i++){
		var presItem = presItems[i];
		var medicalType = $(presItem).find('.medicalType').text();
		var medicalName = $(presItem).find('.medicalName').text();
		var medicalNum = $(presItem).find('.medicalNum').text();
		var medicalId = $(presItem).find('.medicalId').text();
		
		var preObj = {id:medicalId,medicalType:medicalType,medicalName:medicalName,medicalNum:medicalNum};
		preList.push(preObj);
	}
	var tagNameList = [];
	for(var j=0; j<tagItems.length; j++){
		var tagName = tagItems[j];
		var name = $(tagName).find('.tagName').text();
		var tagObj = {tagName:name};
		tagNameList.push(tagObj);
	}
	preList = JSON.stringify(preList);
	tagNameList = JSON.stringify(tagNameList);
	var obj = {allergyHistory:allergyHistory,baseCondition:baseCondition,prescription:preList,diagnoseTags:tagNameList};
	return obj;
}

function addPrescription(){
	var medicalType = $('#mtSelect').find("option:selected").text(); 
	var medicalName = $('#mSelect').find("option:selected").text(); 
	
	var mtSelectId = $('#mtSelect').find("option:selected").val(); //类型
	var mSelectId = $('#mSelect').find("option:selected").val(); //药品
	var medicalId = $('#mSelect').val(); 
	var medicalNum = $('#medicalNum').val();
	
	if(!medicalType){
		lh.alert('请选择药品类型');return;
	}
	if(!medicalName){
		lh.alert('请选择药品名称');return;
	}
	if(!medicalNum){
		lh.alert('请选择药品数量');return;
	}
//	alert(mtSelectId+'=药品='+mSelectId)
	lh.post('front', '/hospital/selectMedicationNumberRemainingForHospital', {medicationTypeId:mtSelectId, medicationId:mSelectId, medicationNumber:medicalNum}, function(rsp){
		if(rsp.success=='success'){
			if(rsp.remainNum >= 0){
				var flag = true;
				for(var i=0;i<FLAG_ADD_DRUG.length;i++){
					if(FLAG_ADD_DRUG[i].typeId==mtSelectId && FLAG_ADD_DRUG[i].drugId==mSelectId){
						flag = false;
						break;
					}
				}
				if(flag){
					var addData = {typeId:mtSelectId,drugId:mSelectId};
					var data = {medicalId:medicalId,medicalType:medicalType, medicalName:medicalName, medicalNum:medicalNum,idData:JSON.stringify(addData)};
					var dom = buildDom(data);
					$('#prescriptionContainer').append(dom);
					FLAG_ADD_DRUG.push(addData);
				}else{
					lh.alert('相同类型药品不能重复添加');
				}
			}else{
				lh.alert('库存数量不够添加');
			}
		}
		
	})
}

function buildDom(data){
	var template = $('#template').html();
	Mustache.parse(template);
	data.serial = lh.createSerial();
	var rendered = Mustache.render(template, data);
	return rendered;
}

function addTag(tagName){
	if(!tagName){
		tagName = $('#tagName').val();
	}
	if(!tagName){
		lh.alert('请输入诊断结果');return;
	}
	var serial = lh.createSerial();
	var dom = '<span id="tagItem_'+serial+'" class="tagsItem"><span class="tagName">'+tagName+'</span><a href="javascript:" onclick="removeTag(\''+serial+'\');" class="pf_6"><img src="/images/front/er2.png" width="6" height="6"/></a></span>';
	$('#tagsContainer').append(dom);
}

function removePrescription(serial){
	$('#presItem_'+serial).remove();
}

function removeTag(serial){
	$('#tagItem_'+serial).remove();
}

function loadMedicationList(){
	var medicalType = $('#mtSelect').val();
	lh.post('front', '/hospital/getMedicationListForHospital', {typeId:medicalType}, function(rsp){
		if(rsp.success){
			var rows = rsp.rows;
			var dom = '';
			for(i in rows){
				var obj = rows[i];
				dom += '<option value="'+obj.id+'">'+obj.name+'</option>';
			}
			$('#mSelect').html(dom);
		}else{
			lh.alert(rsp.msg);
		}
	});
	
}
