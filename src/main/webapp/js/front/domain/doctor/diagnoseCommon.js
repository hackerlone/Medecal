var FLAG_ADD_DRUG = [];
$(function(){
	initData();
});
$(document).on('click','a[name="removeDrug"]',function(){
	var idDate = $(this).attr('val');
	idDate = JSON.parse(idDate);
	for(var i=0; i<FLAG_ADD_DRUG.length; i++){
		var medical = FLAG_ADD_DRUG[i];
		if(medical.medicalTypeId == idDate.medicalTypeId && medical.medicalId == idDate.medicalId){
			FLAG_ADD_DRUG.splice(i, i+1);
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
		var medicalTypeId = $(presItem).find('.medicalTypeId').text();
		var medicalPrice = $(presItem).find('.medicalPrice').text();
		
		var preObj = {id:medicalId,medicalType:medicalType,medicalName:medicalName,medicalNum:medicalNum,medicalTypeId:medicalTypeId, medicalPrice:medicalPrice};
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

function addPrescription(medicalId, medicalTypeId, medicalType, medicalName, medicalNum){
	if(!medicalType)medicalType = $('#mtSelect').find("option:selected").text(); 
	if(!medicalName)medicalName = $('#mSelect').find("option:selected").text(); 
	
	/*var mtSelectId = $('#mtSelect').find("option:selected").val(); //类型
	var mSelectId = $('#mSelect').find("option:selected").val(); //药品
	 */	
	//var medicalPrice = $('#mSelect').find("option:selected").attr('price'); //价格
	if(!medicalId)medicalId = $('#mSelect').val(); 
	if(!medicalTypeId)medicalTypeId = $('#mtSelect').val(); 
	if(!medicalNum)medicalNum = $('#medicalNum').val();
	
	if(!medicalType){
		lh.alert('请选择药品类型');return;
	}
	if(!medicalName){
		lh.alert('请选择药品');return;
	}
	if(!medicalNum){
		lh.alert('请选择药品数量');return;
	}
//	alert(mtSelectId+'=药品='+mSelectId)
	lh.post('front', '/selectMedicationRemainingAndPrice', {medicationId:medicalId, medicationNumber:medicalNum}, function(rsp){
		if(!rsp.success){
			lh.alert(rsp.msg);return;
		}
		if(rsp.remainNum >= 0 ){
			var medicalPrice = rsp.medicalPrice;
			var flag = true;
			for(var i=0; i<FLAG_ADD_DRUG.length; i++){
				var drug = FLAG_ADD_DRUG[i];
				if(drug.medicalTypeId == medicalTypeId && drug.medicalId == medicalId){
					flag = false;
					break;
				}
			}
			if(flag){
				var addData = {medicalTypeId:medicalTypeId, medicalId:medicalId};
				var data = {medicalId:medicalId,medicalType:medicalType,medicalTypeId:medicalTypeId, medicalName:medicalName, medicalNum:medicalNum, medicalPrice:medicalPrice, idData:JSON.stringify(addData)};
				var dom = buildDom(data);
				$('#prescriptionContainer').append(dom);
				refreshTotalPrice();//刷新总价格
				FLAG_ADD_DRUG.push(addData);
			}else{
				lh.alert('相同类型药品不能重复添加');
			}
		}else{
			lh.alert('库存数量不够添加');
		}
	})
}

function refreshTotalPrice(){
	var obj = getBaseDiagnoseFromDom();
	var prescription = obj.prescription;
	prescription = JSON.parse(prescription);
	var price = 0;
	for(var i in prescription){
		var pre = prescription[i];
		var medicalNum = pre.medicalNum;
		var medicalPrice = pre.medicalPrice;
		if(!medicalNum)medicalNum = 0;
		medicalNum = new Number(medicalNum);
		if(!medicalPrice)medicalPrice = 0;
		medicalPrice = new Number(medicalPrice);
		itemTotalprice = medicalNum * medicalPrice;
		price += itemTotalprice;
	}
	if(price < 0 ){
		lh.alert('药品价格不能小于零');
	}
	$('#totalPrice').val(price);
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

function removePrescription(serial, medicalId){
	$('#presItem_'+serial).remove();
	var index = 0;
	for(var i=0; i<FLAG_ADD_DRUG.length; i++){
		var drug = FLAG_ADD_DRUG[i];
		if(drug.medicalId == medicalId){
			index = i;break;
		}
	}
	if(index){
		FLAG_ADD_DRUG.splice(index, 1);
	}
	refreshTotalPrice();
}

function removeTag(serial){
	$('#tagItem_'+serial).remove();
}

function loadMedicationList(){
	var medicalType = $('#mtSelect').val();
	lh.post('front', '/getMedicationRepertoryAry', {medicationTypeId:medicalType}, function(rsp){
		if(rsp.success){
			var rows = rsp.rows;
			var dom = '';
			for(i in rows){
				var obj = rows[i];
				dom += '<option value="'+obj.medicationId+'" price="'+obj.price+'">'+obj.medicationName+'</option>';
			}
			//price
			//remainNum
			$('#mSelect').html(dom);
		}else{
			lh.alert(rsp.msg);
		}
	});
	
}

function openToScanRecord(){//phone, 
	//patientIdcardNum = '111';
	window.open('/diagnoseForApplyList?patientIdcardNum='+lh.data.patientIdcardNum);
}


