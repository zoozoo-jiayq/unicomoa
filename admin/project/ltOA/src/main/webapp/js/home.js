// JavaScript Document
$(document).ready(function() {
	initMenuScroll('first_panel');//建立菜单滚动事件
	initStartMenu();//建立菜单
			$(".menu_right .clsHead").toggle(function() {
						$(this).addClass("opHead");
						$(".indexHeader").addClass("indexHeaderCls");
						$(".iframeFather").addClass("iframeFather_cls")
						$(".menu").css("top", "32px");
						$(".oa_hf").css("top","35px");
						$(this).attr("title", "展开");
					}, function() {
						$(this).removeClass("opHead");
						$(".indexHeader").removeClass("indexHeaderCls");
						$(".iframeFather").removeClass("iframeFather_cls")
						$(".menu").css("top", "92px");
						$(".oa_hf").css("top","79px");
						$(this).attr("title", "收起");
					}); // 打开收起头部
		});

// 窗口resize事件
function resizeLayout() {
	// 主操作区域宽度
	var wWidth = (window.document.documentElement.clientWidth
			|| window.document.body.clientWidth || window.innerHeight);
	// 一级标签宽度
	// var width = wWidth - $('#index_nav_left').outerWidth() -
	// $('#index_nav_right').outerWidth();
	// $('#div_tab').width(width - $('#tabs_left_scroll').outerWidth() -
	// $('#tabs_right_scroll').outerWidth() - 21);
	// $('#index_nav_center').width(width-20); //-1是为了兼容iPad
	// $('#div_tab').triggerHandler('_resize');

	// 一级标签宽度
	var width = wWidth - $('#index_nav_left').outerWidth()
			- $('#index_nav_right').outerWidth();
	$('#tabs_container').width(width - $('#tabs_left_scroll').outerWidth()
			- $('#tabs_right_scroll').outerWidth() - 21);
	$('#taskbar_center').width(width - 20); // -1是为了兼容iPad

	$('#tabs_container').triggerHandler('_resize');
	
	 initStartMenu();//建立菜
	
};

$(window).resize(function() {
			resizeLayout();
		});

$(document).ready(function($) {
	// 调整窗口大小
	resizeLayout();
	var scrollIncrement = 100;
	var scrollDuration = 200;
	$('#tabs_left_scroll').click(function() {
				var scrollTo = $("#tabs_container").scrollLeft()
						- scrollIncrement;
				if (scrollTo < scrollIncrement)// 如果不够一个tab宽度，则滚动到头部
					scrollTo = 0;
				$("#tabs_container").animate({
							scrollLeft : scrollTo
						}, scrollDuration);
			});
	$('#tabs_right_scroll').click(function() {
		var scrollTo = $("#tabs_container").scrollLeft() + scrollIncrement;
		if (scrollTo + scrollIncrement > $("#tabs_container")
				.attr('scrollWidth'))
			scrollTo = $("#tabs_container").attr('scrollWidth');
		$("#tabs_container").animate({
					scrollLeft : scrollTo
				}, scrollDuration);
	});

});
// 设置tab的宽度
function setUlWidth() {
	var li_width = getULWidth();
	var scrollIncrement = 120;
	var scrollDuration = 200;
	// alert($("#div_tab").width());
	$("#div_tab").width(getULWidth());
	var scrollleft=li_width - $("#tabs_container").width();
	if (li_width > $("#tabs_container").width()) {
		$('#tabs_left_scroll').show();
		$('#tabs_right_scroll').show();
		 var scrollTo = $("#div_tab").scrollLeft() - scrollIncrement;
		 if(scrollTo < scrollIncrement)//如果不够一个tab宽度，则滚动到头部
		 scrollTo = 0;
		$("#tabs_container").animate({
					scrollLeft : scrollleft+115
				}, scrollDuration);
	} else {
		$('#tabs_left_scroll').hide();
		$('#tabs_right_scroll').hide();
	}
}
// get ul width
function getULWidth() {
	var totalWidth = 0;
	$("#div_tab > li").each(function() {
		        //var liWidth = 115+5;
				var liWidth = $(this).outerWidth() + 10;
				totalWidth += liWidth;
			});
	return totalWidth;
}

//菜单根据屏幕自适应、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
//menu para
var MENU_ITEM_HEIGHT = 41;
var MIN_MENU_HEIGHT = 8 * MENU_ITEM_HEIGHT;
var MAX_MENU_HEIGHT = 20 * MENU_ITEM_HEIGHT;
var SCROLL_M_HEIGHT = 4 * MENU_ITEM_HEIGHT;
//设置菜单的高度
function initStartMenu()
{
	  ////计算并设置菜单面板的高度,是否显示滚动箭头
	   // 主操作区域宽度
      var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
		 var m_height = wHeight-280;
      var scrollHeight = $("#first_menu").attr('scrollHeight');
		 var menu_height = getMenuHeight();
      if(menu_height > m_height)
      {
         var height = ($('.bottom').offset().top - $('.menu').offset().top)*0.7;   //可用高度为开始菜单和状态栏高差的70%
         height = height - height % MENU_ITEM_HEIGHT;   //可用高度为 MENU_ITEM_HEIGHT 的整数倍
         //如果可用高度大于允许的最高高度，则限制
         height = height <= MAX_MENU_HEIGHT ? height : MAX_MENU_HEIGHT;
         //如果可用高度超过scrollHeight，则设置高度为scrollHeight
         height = height > scrollHeight ? scrollHeight : height;
         $('#first_menu').height(m_height);
			$('.m_c').height(m_height+16);
			$('.m_right').height(m_height+20);
			$('.m_left').height(m_height);
			$('#emptyIframe').height(m_height+60);
        $('#first_panel > .scroll-up:first').show();
         $('#first_panel > .scroll-down:first').show();
      }
      else
      {
			 var height= getMenuHeight();
         $('#first_menu').height(height);
			$('.m_c').height(height);
			$('.m_right').height(height);
			$('.m_left').height(height);
			$('#emptyIframe').height(height+60);
			$('#first_panel > .scroll-up:first').hide();
         $('#first_panel > .scroll-down:first').hide();
      }
     
	}
//菜单滚动箭头事件,id为first_menu
   function initMenuScroll(id)
   {
      //菜单向上滚动箭头事件
      $('#' + id + ' > .scroll-up:first').hover(
         function(){
            $(this).addClass('scroll-up-hover');
         },
         function(){
            $(this).removeClass('scroll-up-hover');
         }
      );

      //点击向上箭头
      $('#' + id + ' > .scroll-up:first').click(
         function(){
            var ul = $('#' + id + ' > ul:first');
            ul.animate({'scrollTop':(ul.scrollTop()-SCROLL_M_HEIGHT)}, 400);
         }
      );

      //向下滚动箭头事件
      $('#' + id + ' > .scroll-down:first').hover(
         function(){
            $(this).addClass('scroll-down-hover');
         },
         function(){
            $(this).removeClass('scroll-down-hover');
         }
      );

      //点击向下箭头
      $('#' + id + ' > .scroll-down:first').click(
         function(){
            var ul = $('#' + id + ' > ul:first');
            ul.animate({'scrollTop':(ul.scrollTop()+SCROLL_M_HEIGHT)}, 400);
         }
      );
    
      
   }
   function getMenuHeight(){
		var totalHeight=0;
		$("#first_menu > li").each(function() {
			var liHeight= $(this).outerHeight();
			totalHeight+=liHeight;
		});
		return totalHeight;
}//get ul width	
