// JavaScript Document

/**
 * 重置iFrame的高度
 */
function frameResize() {
	var frame = $(".mainpage .iframeresize");
	if (frame.length > 0) {
		if (!($.browser.msie && $.browser.version == 6.0)) {
			frame.height(frame.contents().find("body").height() + 60);
		} else {
			frame.height(frame.contents().find("body").height());
		}
	}
}

$(document).ready(function() {
	if($(".input_new").is(":visible")){
	   var Pwidth = screen.availWidth;
	   if(Pwidth>=1280){
		    $(".input_new").css("width","1000px");
	   }else{

	      $(".input_new").css("width","100%");
	   }
	}
	$(".tab li").click(function() {
				var index_tab = $(this).parent().children().index($(this));// 选项卡的索引值
				$(this).parent().find(".current").removeClass("current");
				$(this).addClass("current");
				var content_obj = $(this).parent().parent().find(".tabContent");// 内容节点
				content_obj.hide();
				content_obj.eq(index_tab).show();

			});// 选项卡
	$(".menuList").eq(0).show();// 默认第一个打开
	
	$(".leftMenu .blockBox h2").toggle(function() {
				if ($(this).next().is("ul")) {
					$(this).children("em").addClass("op");
					$(this).next().show();
				}
			}, function() {
				if ($(this).next().is("ul")) {
					$(this).next().hide();
					$(this).children("em").removeClass("op");
				}
			});// 左侧展开收起
	$(".BlockTop h2").toggle(function() {
				$(".blockBox").children(".menuList").eq(0).hide();
				$(this).children("em").removeClass("op");
			}, function() {
				$(".blockBox").children(".menuList").eq(0).show();
				$(this).children("em").addClass("op");
			});// 左侧第一个展开收起
	$(".menu_l .menu-p").click(function() {
				if ($(this).parent().hasClass("menu_current")) {
					$(this).parent().removeClass("menu_current");
				} else {
					$(".menu_l").removeClass("menu_current");
					$(this).parent().addClass("menu_current");
				}
			});
  	$("a.shareShow").toggle(function(){
		$(this).addClass("shareCls");
		},function(){
		$(this).removeClass("shareCls");
		})//日志分享展开收起
	$(".pretty thead th").last().css("border-right", "0");// 去除最右侧border
	$(".pretty tbody tr").each(function() {
				$(this).children("td").last().css("border-right", "0");
			})// 去除最右侧线border
	$(".morebtn").hover(function() {
				$(this).addClass("morebtnHover");
			}, function() {
				$(this).removeClass("morebtnHover");
			});// 更多操作
	// $(".mainpage iframe").height($(".mainpage
	// iframe").contents().find("body").height()+50);//去除内页带iframe的外层滚动条
	$(".mainpage .iframeresize").load(function() {
				frameResize();
			});
	$(".mywork h2").toggle(function() {
				$(this).children("em").addClass("cls");
				$(this).parent().children(".wlist").hide();
			}, function() {
				$(this).children("em").removeClass("cls");
				$(this).parent().children(".wlist").show();
			});// 我的工作展开收起
	prgleft_height();//新工作左侧菜单的长度自适应屏幕
	leftMenuTreeHeight();
	//鼠标经过显示删除按钮
	$(".myphoto").hover(function(){
		$(this).children(".canclePic").show();
		
	},function(){
		$(this).children(".canclePic").hide();
		
	});
	// 分类树管理
	$("td.categoryName").click(function() {
				var grade = $(this).parent().attr("grade");
				var isHide;
				if (grade == "0") {
					if ($(this).children("span").hasClass("firstCategory_cls"))
						$(this).children("span")
								.removeClass("firstCategory_cls");
					else
						$(this).children("span").addClass("firstCategory_cls");
				};
				if (grade != "0"
						&& grade < $(this).parent().next("tr").attr("grade")) {
					if ($(this).children("span").hasClass("category_cls"))
						$(this).children("span").removeClass("category_cls");
					else
						$(this).children("span").addClass("category_cls");
				};
				$(this).parent().nextAll("tr").each(function() {
							var thisLevel = $(this).attr("grade");
							if (thisLevel <= grade) {
								return false;
							}
							if (isHide == null) {
								if ($(this).is(":hidden")) {
									isHide = true;
								} else {
									isHide = false;
								}
							}
							if (isHide) {
								$(this).show();
							} else {
								$(this).hide();
							}
						});
			});
	
	//add by jiayq,左侧列表添加选中状态
	$(".menu-p").first().addClass("menu-on");
	$(".menu-p").click(function(){
		$(".menu-p").removeClass("menu-on");
		$(this).addClass("menu-on");
	});

});
// iframe auto
function dyniframesize(down) {
	var pTar = null;
	if (document.getElementById) {
		pTar = document.getElementById(down);
	} else {
		eval('pTar = ' + down + ';');
	}
	if (pTar && !window.opera) {
		// begin resizing iframe
		pTar.style.display = "block"
		if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight) {
			// ns6 syntax
			pTar.height = pTar.contentDocument.body.offsetHeight + 20;
			pTar.width = pTar.contentDocument.body.scrollWidth + 20;
		} else if (pTar.Document && pTar.Document.body.scrollHeight) {
			// ie5+ syntax
			pTar.height = pTar.Document.body.scrollHeight;
			pTar.width = pTar.Document.body.scrollWidth;
		}
	}
}
// 展开显示
function showHide(id) {
	var v_ID = document.getElementById(id);
	if (jQuery(v_ID).is(":visible"))
		jQuery(v_ID).hide();
	else
		jQuery(v_ID).show();
	// 调整框架高度
	window.parent.frameResize();
}
function prgleft_height() {
//新工作左侧菜单的长度自适应屏幕
var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
$(".leftMenu .service-menu").height(wHeight-60);
}
//左侧树高度
function leftMenuTreeHeight(){
	var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
	if($(".leftMenu").is(":visible"))
	{  var blockBoxPosition = $(".leftMenu .blockBox").position();
	   var Meil_wrapPosition= $(".leftMenu .Meil_wrap").position();
		var treeHeight = wHeight-blockBoxPosition.top;
		var botHeight=0;
		var topHeight=0;
		var MeilHeight=0;
		/*if($(".leftMenu .MeilTitle").is(":visible")){
			treeHeight=treeHeight-0;
		}	*/
		if($(".leftMenu .BlockBot").is(":visible")){
			botHeight=$(".leftMenu .BlockBot").height();
		}
		if($(".leftMenu .BlockBot").length < 0){
			botHeight=$(".leftMenu .BlockBot").height();
		}
		if($(".leftMenu .blockBox h2").is(":visible")){
			botHeight = botHeight +$(".leftMenu .blockBox h2").height();
		}
		if($(".leftMenu .Meil_wrap").is(":visible")){
			MeilHeight = Meil_wrapPosition.top;
		}
		/*if($(".leftMenu #userSelect").is(":visible")){
			botHeight=botHeight+$(".leftMenu #userSelect").height();
		}	*/
		if($(".blockBox .file_top_list").is(":visible")){
			topHeight=$(".blockBox .file_top_list").height();
		}		
		$(".leftMenu .addressList").css("height",treeHeight-botHeight-topHeight);
		$(".leftMenu .ztree").css("height",treeHeight-botHeight-topHeight-11);
		$(".leftMenu .self_date").css("height",treeHeight-botHeight-topHeight-8);
		$(".leftMenu .chat_list").css("height",treeHeight);
		$(".leftMenu .service-menu").css("height",treeHeight);
		$(".leftMenu .Meil_wrap").css("height",wHeight-MeilHeight);
		/*$(".leftMenu .addressList").css("height",treeHeight-10);*/
	}
}
$(window).resize(function() {
	prgleft_height();
	leftMenuTreeHeight();
});