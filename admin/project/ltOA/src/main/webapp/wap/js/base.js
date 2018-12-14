// JavaScript Document
$(document).ready(function(){
    //列表页面返回按钮
    $(".backToClientHome").live("click",function(){
        window.activity.retMainPage();
    });
    
	$(".tab li").click(function(){
		var index_tab = $(this).parent().children().index($(this));//选项卡的索引值
		$(this).parent().find(".current").removeClass("current"); 
		$(this).addClass("current");
		var content_obj = $(this).parent().parent().find(".tabContent");//内容节点
		content_obj.hide();
        content_obj.eq(index_tab).show(); 
		
	});//选项卡
		$(".bookList li p").click(function(){
		if ($(this).parent().hasClass("current"))
			$(this).parent().removeClass("current");
		else
		{
			$(this).parent().siblings("li").removeClass("current");
			$(this).parent().addClass("current");
		}})//打开收起通讯录
		//公文打开收起
		$(".bookDetailList li p").click(function(eventObject){
			if ($(this).parent().hasClass("current")){
				$(this).parent().removeClass("current");
			}
			else{
				$(this).parent().addClass("current");
			}
			})
		
		$(".seleBook").click(function(){
			if($(".selectBookBox").is(":visible"))
			$(".selectBookBox").hide();
			else
			$(".selectBookBox").show();
			})//打开收起选择通讯录类型
		$(".selectBookBox").click(function(){
			$(this).hide();
			})
		$(".bookList .m_List").each(function() {
		$(this).children("li").first().css("border-top","0")	
		});
		$(window).resize(function() {
			frameResize("msgBox");
		});
		$("#msgBox").height($(window).height()-127);
});
//展开显示
function show(id){
	var v_ID = document.getElementById(id);
	jQuery(v_ID).show();
}
//iframe自适应
function frameResize(frameID) {
	var frame = $("#"+frameID+"")
	frame.height($(window).height()-127);
}


