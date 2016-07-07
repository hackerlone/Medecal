$(function () {
    var pzselect = $('div[pzdatatype=select]');

    pzselect.each(function () {
        //为自身赋值样式
        pzselect.css({ position: 'relative' });

        //为下拉框内容赋值样式
        var $title = $(this).find('div[pzdatatype=title]');
        var _kuandu = $title.width(), _gaodu = $title.height();

        $(this).find('ul[pzdatatype=content]').css({
            width: _kuandu + 'px',
            position: 'absolute',
            top: _gaodu + 'px',
            'overflow-x': 'hidden',
            display: 'none'
        });
    });
    pzselect.click(function () {
        $(this).find('ul[pzdatatype=content]').slideToggle(0);
    });
    pzselect.find('ul[pzdatatype=content] li').click(function () {
        var val = $(this).text();
        $(this).parent().siblings('div[pzdatatype=title]').text(val);
        $(this).parent().siblings('input[type=hidden]').val(val);
        $(this).addClass('on').siblings().removeClass('on');
    });
    pzselect.bind('mouseleave', function () {
        $(this).find('ul[pzdatatype=content]').hide();
    });
});
