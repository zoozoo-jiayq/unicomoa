// JavaScript Document
function menuBindClick() {
 
	initMenuScroll('first_panel');//建立菜单滚动事件
	initStartMenu();//建立菜单
	$(".index_menu").click(function() {
		if ($(".menu").is(":visible"))
				$(".menu").hide();
		else
					$(".menu").show();
	});
	$(".s_menu").hide();
	$(".menu .m_right li dl dd").click(function() {
				$(".menu").hide();
	});
	$(".s_menu").eq(0).show();
	$(".menu .m_left li").eq(0).addClass("current");// 初始化的时候默认打开第一个
	
	/**
	 * init three category menu,make the first three open
	 */
	var threeMenu = $(".menu .s_menu li");
	if(threeMenu.eq(0).children("dl").length>0){
		threeMenu.eq(0).addClass("current");
		threeMenu.eq(0).find("dd a").eq(0).addClass("on");
	}
	$(".menu .m_left li").click(function() {
				var index_tab = $(this).parent().children().index($(this));// 选项卡的索引值
				$(this).parent().find(".current").removeClass("current");
				$(this).addClass("current");
				var content_obj = $(this).closest(".m_c").find(".s_menu");// 内容节点

				content_obj.hide();
				content_obj.eq(index_tab).show();

	});// 打开二级菜单
	$(".menu .m_right li").click(function() {
		if ($(this).find(".tile_a").hasClass("part")) {
			 $(this).show();
		} else
			 $(".menu").hide();
	});
	$(".menu .m_right li").has("dl").find(".tile_a").addClass("part");
	$(".menu .m_right li .tile_a").click(function() {
	
		if ($(this).parent().hasClass("current")) {
			 $(this).parent().removeClass("current");
		}else {$(".menu .m_right li").removeClass("current");
			$(this).parent().addClass("current");
	   }
	});// 打开三级菜单

	$(".menu .m_right li dd a").click(function() {
				$(".menu .m_right li dd a").removeClass("on");
				$(this).addClass("on");
	});// 菜单结束
} 
