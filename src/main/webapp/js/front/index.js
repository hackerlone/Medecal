$(function () {
	initNav();
    $(window).resize(function () { goTop(); });
    $(window).scroll(function () { goTop(); });
});

function initNav(){
	var navAll = locache.get('navAll');
	if(!navAll && navAll !== 0)navAll = 1;
	if (navAll) {
		$('#nav1,#nav2,#nav4,#nav5').css('visibility', 'visible');
		$("#nav_center").text('折叠');
	} else {
		$('#nav1,#nav2,#nav4,#nav5').css('visibility', 'hidden');
		$("#nav_center").text('展开');
        $('#nav3 a').css({'width':'46px','height':'46px','background':'url(/images/front/small.jpg) no-repeat','margin-top':'23px'});
        $('#nav3 img').removeAttr('src').css({'width':'0','height':'0'});
        $('.fixed').animate({right:'-31px'});
        $('#nav3 a i').css({'display':'none'})

    }
}

function toggleNav(){
	var navAll = locache.get('navAll');
	if(!navAll && navAll !== 0)navAll = 1;
	if (navAll) {
		 $('#nav1,#nav2,#nav4,#nav5').css('visibility', 'hidden');
		 $("#nav_center").text('展开');
         $('#nav3 a').css({'width':'46px','height':'46px','background':'url(/images/front/small.jpg) no-repeat','margin-top':'23px'});
         $('#nav3 img').removeAttr('src').css({'width':'0','height':'0'});
         $('#nav3 a i').css({'display':'none'})
         $('.fixed').animate({right:'-31px'});
        navAll = 0;
	} else {
		$('#nav1,#nav2,#nav4,#nav5').css('visibility', 'visible');
		$("#nav_center").text('折叠');
        $('#nav3 a').css({'width':'92px','height':'92px','background':'url(/images/front/main19.png) no-repeat','margin-top':''});
        $('#nav3 img').attr('src','/images/front/main20.png').css({'width':'24px','height':'24px'});
        $('#nav3 a i').css({'display':'block'})
        $('.fixed').animate({right:'25px'});
        navAll = 1;
	}     
	locache.set('navAll', navAll);
}

function goTop() {
    var _top = $(window).scrollTop();
    if (_top > 300) {
        $("#pf").stop().animate({
            "top": (($(window).height() - $("#pf").height()) / 2) + $(window).scrollTop()
        }, 1000);

    } else {
        $("#pf").stop().animate({
            "top": "245px"
        }, 1000);
    }
   
}
		
jQuery(".pz_tab").slide({});

jQuery(".slideBox").slide({mainCell:".bd ul",effect:"fade", autoPlay:true,delayTime:700});

var s_count = $(".pz_slides_3 ._pic img").length;
var s_li = "";
for (j = 1; j <= s_count; j++) { s_li += "<li></li>"; }
$(".pz_slides_3 ._btn").html("<ul>" + s_li + "</ul>");
var btn = 746 - $(".pz_slides_3 ._btn").width();
var btnl = btn / 2;
$(".pz_slides_3 ._btn").css("left", btnl + "px");
jQuery(".pz_slides_3").slide({ titCell: "._btn li", mainCell: "._pic ul", prevCell:".prev", nextCell:".next", effect: "fold", autoPlay: true, delayTime: 1500, interTime: 3000 });
   
jQuery(".menu").slide({ type: "menu", titCell: ".nli", targetCell: ".sub", effect: "slideDown", delayTime:200, triggerTime: 0,returnDefault: true });
	
