$(function(){
	loadAnnouncement();
	scrollBottom(loadAnnouncement);
});

function loadAnnouncement(){
	var param = {};
	$.post('/getAnnouncementList',param,function(rsp){
		if(rsp.status == 'success'){
			makeAnnouncementListDom(rsp.rows,1);
		}else{
			frontBaseAlert(rsp.msg);
		}
	},'json');
}


function makeAnnouncementListDom(announcementList,isAppend){
	var template = $('#template').html();
	Mustache.parse(template);   // optional, speeds up future uses
	var rendered = Mustache.render(template, {rows:announcementList});
	if(isAppend){
		$('#announcement').append(rendered);
	}else{
		$('#announcement').html(rendered);
	}
}
