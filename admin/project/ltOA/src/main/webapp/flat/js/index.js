// 窗口resize事件
function resizeLayout() {
	// 主操作区域高度
	var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
	var nHeight = $('.indexHeader').is(':visible') ? $('.indexHeader').outerHeight():0;
    var cHeight = wHeight - nHeight - $('.bottom').outerHeight();
	$('#mainContainer').height(cHeight);
	$('#menu').height(cHeight);
	//$(".mainIframe").css({height: cHeight});
	//$(".meetingIframe").css({height: cHeight});
};

$(document).ready(function($) {
// 调整窗口大小
  resizeLayout();
});
 