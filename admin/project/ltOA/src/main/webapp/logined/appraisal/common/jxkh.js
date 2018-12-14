$(function() {

	// 设置左侧菜单栏列表高度
	var h=$(window).height()-90;
	$(".nav").css("height",h);
	// 设置右侧内容区域高度
	$(".right_content").css("height",h);
	// 左侧菜单栏点击切换背景
	$(".nav li").live("click",function(){
		$(this).addClass("active").siblings('.active').removeClass('active');
	});

	// 考核成绩页面审核按钮鼠标移入，列表出现,同时设置列表宽度和按钮宽度一样(为了兼容IE)
	$("#shenhe").live("hover",function(){
		var w=$(".shenhe_btn").width()+40;
		$(".shenhe_list").toggle().css("width",w);

	})
	
	/*进度查询页面切换*/
	$(".tabs_list li").live("click",function(){
		$(this).addClass('active');
		$(this).siblings().removeClass('active')
		var index = $(this).index();
		$(".progress_item").eq(index).removeClass('hide');
		$(".progress_item").eq(index).siblings(".progress_item").addClass('hide');
	})
	
})
