// JavaScript Document
function menuBindClick() {
	initMenuScroll('first_panel');//建立菜单滚动事件
	initStartMenu();//建立菜单
	$("#index_nav_left").click(function() {
				if ($(".menu").is(":visible"))
					$(".menu").hide();
				else
					$(".menu").show();
			});
	$(".index_menu").hover(function() {
				$(this).addClass("index_menu_hover");
			}, function() {
				$(this).removeClass("index_menu_hover");
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
	}
	
	
	$(".menu .m_left li").click(function() {
				var index_tab = $(this).parent().children().index($(this));// 选项卡的索引值
				$(this).parent().find(".current").removeClass("current");
				$(this).addClass("current");
				var content_obj = $(this).closest(".m_c").find(".s_menu");// 内容节点

				content_obj.hide();
				content_obj.eq(index_tab).show();

			});// 打开二级菜单

	$(".menu .m_right li").has("dl").addClass("s_menubg");
	$(".menu .m_right li").click(function() {
				if ($(this).hasClass("s_menubg")) {
					$(this).show();
				} else
					$(".menu").hide();
			});
	$(".menu .m_right li a.ss").click(function() {
				if ($(this).parent().hasClass("current")) {
					// $(".menu .m_right li").removeClass("current");
					$(this).parent().removeClass("current");
				} else {
					$(".menu .m_right li").removeClass("current");
					$(this).parent().has("dl").addClass("current");
				}

			});// 打开三级菜单

	$(".menu .m_right li dd a").click(function() {
				$(".menu .m_right li dd a").removeClass("sele");
				$(this).addClass("sele");
			});// 菜单结束

}
