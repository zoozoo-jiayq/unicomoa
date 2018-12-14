/**
 * 菜单js
 */
$(document).ready(function() {
			$("ul.l_f_menu li").first().addClass("current");
			$("ul.l_f_menu li p").click(function() {
						if ($(this).parent().hasClass("current")) {
							$(this).parent().removeClass("current")
						} else {
							$("ul.l_f_menu li").removeClass("current");
							$(this).parent().has("dl").addClass("current");
							$(".l_f_menu dl").removeClass("sele");
						}
					});// 一级菜单

			$(".l_f_menu dl dt").click(function() {
						if ($(this).parent().hasClass("sele")) {
							$(this).parent().removeClass("sele");
						} else {
							$(this).parent().has("dd").addClass("sele");
						}
					});// 二级菜单
			
			// 左边滑入滑出

			$("#s_center").toggle(function() {
						$("#menu").hide();
						$(this).addClass("cls");
						$(this).css({
									left : "0"
								});
						$("#mainFrameContainer").css({
									left : "7px"
								});
						$(".mainFrame").removeClass("leftOpen");
					}, function() {
						$("#menu").show();
						// $("#s_center").css("background-position-x","-6px");
						$(this).removeClass("cls");
						$(this).css({
									left : "175px"
								});
						$("#mainFrameContainer").css({
									left : "182px"
								});
						$(".mainFrame").addClass("leftOpen");
					});
		});