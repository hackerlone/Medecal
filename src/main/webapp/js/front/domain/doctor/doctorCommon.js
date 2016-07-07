
$(function(){
	initCommonPage();
	var s_count = $("#slideBox .bd img").length;
	var s_li = "";
	for (j = 1; j <= s_count; j++) {
			s_li += "<li>"+j+"</li>";
		}
	$("#doctorAdvertisement").html("<ul>" + s_li + "</ul>");
});

function initCommonPage(){
	jQuery(".Box").slide({mainCell:".bd ul",autoPlay:true});
	jQuery(".txtScroll-top").slide({mainCell:".bd ul",autoPage:true,effect:"top",autoPlay:true,vis:1});
    jQuery(".pz_tab").slide({});
}


function searchDoctor(){
	var searchDoctorName = $("#searchDoctorName").val();
	lh.jumpToUrl("/doctorList?searchDoctorName="+searchDoctorName);
}