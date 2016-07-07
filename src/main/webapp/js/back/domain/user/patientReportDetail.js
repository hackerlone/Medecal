$(function(){

});

function saveDoctorResult(){
	var reportId = $("#reportId").val();
	var doctorResult = $("#doctorResult").val();
	var obj = {id:reportId,doctorResult:doctorResult};
	lh.post("front", "/back/updateReportDoctorResult", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.back();
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}