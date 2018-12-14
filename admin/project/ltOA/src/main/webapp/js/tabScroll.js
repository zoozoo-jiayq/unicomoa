function setUlWidth() {
	$("#div_tab").width(getULWidth());
	var ulLeft = $("#div_tab").width() - $(".tabList").width() + 115;
	if ($("#div_tab").width() > $(".tabList").width()) {
		$(".btnScroll").show();
		$(".tabList").scrollLeft(ulLeft);
	} else {
		$(".btnScroll").hide();
	}
	$(".tabList").scroll(function() {
				// left
				if ($(".tabList").scrollLeft() > 0) {
					$("a.btnRight").removeClass("btnRightNo");
				}
				if ($(".tabList").scrollLeft() == 0) {
					$("a.btnLeft").addClass("btnLeftNo");
				}
				// right
				var scrollWidth = $(this).scrollLeft();
				var ulWidth = getULWidth();
				if ((ulWidth - scrollWidth) < $(this).width()) {
					$("a.btnRight").addClass("btnRightNo");
				}
			})
}
function getULWidth() {
	var totalWidth = 0;
	$("#div_tab > li").each(function() {
				var liWidth = $(this).outerWidth() + 5;
				totalWidth += liWidth;
			});
	return totalWidth;
}// get ul width
function upMove(obj) {
	var dom = $(".tabList");
	dom.animate({
				scrollLeft : -105 + dom.scrollLeft()
			}, 500);

}
function downMove(obj) {
	var dom = $(".tabList");
	dom.animate({
				scrollLeft : 105 + dom.scrollLeft()
			}, 500)
	if ($(".tabList").scrollLeft() > 0) {
		$("a.btnLeft").removeClass("btnLeftNo");
		// $("a.btnRight").removeClass("btnRightNo");
		// $("a.btnRight").addClass("btnRightNo");
	}
}
