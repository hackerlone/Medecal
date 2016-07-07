
$(function(){
	var cityId = $("#cityId").val();
	provinceChange(cityId);
	$("#birthday").datetimepicker({
        format:'yyyy-mm-dd',
        todayBtn:true,
        language: 'zh-CN',
        startView:2,
        viewSelect:'year',
        minView: 2, //选择日期后，不会再跳转去选择时分秒 
        autoclose:true //选择日期后自动关闭 
    });
});

function provinceChange(city){
	var provinceId = $("#province").val();
	lh.post("front", "/getCityArray", {provinceId:provinceId}, function(rsp){
		if(rsp){
			var dom = '<select id="city"" class="input8" style="width:268px;"><option value="">请选择</option>';
			$("#cityDiv").empty();
			for(var i = 0;i < rsp.length;i++){
				dom +=' <option';
				if(city && city == rsp[i].id)dom += ' selected="selected" ';
				dom += ' value="'+rsp[i].id+'">'+rsp[i].name+'</option>';
			}
			dom += '</select>';
			$("#cityDiv").append(dom);
		}
	}, "json")
}

function addOrUpdatePatient(){
	var userId = $("#userId").val();
	var username = $("#username").val();
	var realName = $("#realName").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var idcardNum = $("#idcardNum").val();
	var sex = $("#sex").val();
	var province = $("#province").val();
	var city = $("#city").val();
	var job = $("#job").val();
	var age = $("#age").val();
	var email = $("#email").val();
	var birthday = $("#birthday").val();
	if(!username){lh.alert('请填写用户昵称');return;}
	if(!realName){lh.alert('请填写用户真实姓名');return;}
	if(!phone){lh.alert('请填写用户联系电话');return;}
	if(!idcardNum){lh.alert('请填写身份证号码');return;}
	if(!sex){lh.alert('请选择性别');return;}
	if(!province){lh.alert('请选择省市');return;}
	if(!city){lh.alert('请选择城市');return;}
	if(!job){lh.alert('请填写职业');return;}
	if(!birthday){lh.alert('请填写出生日期');return;}
	//if(!address){lh.alert('请填写用户住址');return;}
	//if(!age){lh.alert('请填写年龄');return;}
	//if(!email){lh.alert('请填写邮箱');return;}
	birthday += ' 00:00:00';
	var obj = {id:userId,username:username,realName:realName,phone:phone,address:address,idcardNum:idcardNum,
			sex:sex,province:province,city:city,job:job,age:age,email:email,birthday:birthday,addDoctorPatient:1};
	if(lh.preventRepeat()){//防止重复提交
		saveData(obj);
	}else{
		//lh.showRepeatTip();//提示重复提交
		lh.alert('请不要重复提交数据');
	}
}

function saveData(obj){
	lh.post("front", "/addOrUpdatePatient", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/patientLibrary');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}