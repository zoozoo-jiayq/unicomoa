$(document).ready(function () {
	getWeather();
});

function getWeather()
{
    var dataParam={
        'url':'http://61.4.185.209/data/cityinfo/101180105.html' // 信阳101180601 郑州101180101  101181601=驻马店   焦作:101181101
    };
    var url=basePath+"weather/getWeather.action";
    $.ajax({
        type:"GET",
        url:url ,
        dataType: "text",
        data:dataParam,
        success:function(msg){
            if(msg!=""&&msg!="8EF0000"){
                var obj = eval('('+msg+')'); //eval()是把后台传过来的json格式数据转换了一个jquery对象
                //下面是要显示的一些内容，由于json格式返回的结果集中有点像二维数组的样式，因此下面内容的显示要这样显示，这里的内容只是显示一部分，当然你也可以显示更多的内容(方法：加li,要是读者不清楚如何加时可以打印obj这个对象，你看它的返回值就明白如何加了)
                $("#w_weather").html(obj['weatherinfo']['weather']);
                $("#w_city").html(obj['weatherinfo']['city']);
                $("#w_temperature").html(obj['weatherinfo']['temp1']+"~"+obj['weatherinfo']['temp2']);
            }
        }
    });
}