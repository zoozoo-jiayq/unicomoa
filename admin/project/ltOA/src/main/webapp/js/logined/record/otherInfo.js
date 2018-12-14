$(function(){
	initFirstMenu();
	initMenuClick();
});

//初始化选中第一个并获取第一个菜单所有的子项
function initFirstMenu() {
	var userId = $("#userId").val();
    var firstApp = $("#app_cate_list ul li:first");
    if (firstApp.length > 0) {
        firstApp.addClass("current");
    }
    var url = basePath+firstApp.attr("url");
    $("#page").attr("src",url+"?userId="+userId);
}


function initMenuClick() {
	var userId = $("#userId").val();
    $("#app_cate_list ul li").click(function () {
        $("#app_cate_list ul li").removeClass("current");
        $(this).addClass("current");
        $("#page").attr("src",basePath+$(this).attr("url")+"?userId="+userId);
    })
}