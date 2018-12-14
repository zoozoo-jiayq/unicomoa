// JavaScript Document
$(document).ready(function(){
	$(".tab ul li").click(function(){
		var index_tab = $(this).parents().children().index($(this));//选项卡的索引值
		$(this).parents().find(".current").removeClass("current"); 
		$(this).addClass("current");
	});//选项卡
});