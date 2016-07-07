$(function(){

});

function saveDoctorResult(){
	var reportId = $("#reportId").val();
	var doctorResult = $("#doctorResult").val();
	var obj = {id:reportId,doctorResult:doctorResult};
	lh.post("front", "/doctor/updateReportDoctorResult", obj, function(rsp){
		if(rsp.status == 'success'){
			lh.jumpToUrl('/doctor/doctorUserReportList');
		}else{
			lh.alert(rsp.msg);
		}
	}, "json");
}
