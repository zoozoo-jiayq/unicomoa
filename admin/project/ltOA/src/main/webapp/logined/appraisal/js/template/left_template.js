$(function() {
	// 设置左侧菜单栏列表高度
	var h=$(window).height()-90;
	$(".nav").css("height",h);
	// 设置右侧内容区域高度
	$(".right_content").css("height",h);
	
	$(".nav li").live("click",function(){
		$(this).addClass("active").siblings('.active').removeClass('active');
	});
	
	
})
