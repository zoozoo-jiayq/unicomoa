// JavaScript Document
$(document).ready(function(){
	$(".tab ul li").click(function(){
		var index_tab = $(this).parents().children().index($(this));//选项卡的索引值
		$(this).parents().find(".current").removeClass("current"); 
		$(this).addClass("current");
		var content_obj = $(this).parents().parent().find(".tabContent");//内容节点
		content_obj.hide();
        content_obj.eq(index_tab).show(); 
		
	});//选项卡
});