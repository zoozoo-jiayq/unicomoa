// JavaScript Document
/**
 * 重置iFrame的高度
 */
function frameResize() {
	var frame = $(".mainpage .iframeresize");
	var frame_r = $(".mainpage_r .iframeresize");
	var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
	if(frame){
		frame.height(wHeight-5);
	}
	if(frame_r){
		frame_r.height(wHeight-5);
	}
	
}

/**
 * 设置Logo
 */
function setLogo(url){
     var imgUrl=basePath+"filemanager/prevViewByPath.action?filePath="+url;
	 $("#companyLogo").attr("src",imgUrl);
}



$(window).resize(function() {
frameResize();
});
$(document).ready(function($) {
// 调整窗口大小
frameResize();
});
$(document).ready(function() {
    if($(".input_new").is(":visible")){
	   var Pwidth = screen.availWidth;
	   if(Pwidth>=1280){
		    $(".input_new").css("width","1000px");
	   }else{

	      $(".input_new").css("width","100%");
	   }
	}
	$(".morebtn").hover(function() {
				$(this).addClass("morebtnHover");
			}, function() {
				$(this).removeClass("morebtnHover");
	});// 更多操作
	var frame = $(".mainpage .iframeresize");
	var frame_r = $(".mainpage_r .iframeresize");
	if(frame){
		 if($(".mainpage .iframeresize").is(":visible")){
			   $(".mainpage .iframeresize").load(function() {
							frameResize();
				});
	    }
	}
	if(frame_r){
		if($(".mainpage_r .iframeresize").is(":visible")){
			   $(".mainpage_r .iframeresize").load(function() {
							frameResize();
				});
	    }
	}
	prgleft_height();//新工作左侧菜单的长度自适应屏幕
	leftMenuTreeHeight();
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

//左侧菜单的长度自适应屏幕
function prgleft_height() {
   var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
   $(".leftMenu .service-menu").height(wHeight-11);
   $(".leftMenu .meil_wrap").height(wHeight - 40);
   $(".leftMenu .txtlist").height(wHeight - 265);
   
   var tHeight = $('.leftMenu h1').is(':visible') ? $('.leftMenu h1').outerHeight(true) : 0;
   var kHeight = $('.leftMenu .workes').is(':visible') ? $('.leftMenu .workes').outerHeight(true) : 0;
   var sHeight = $('.leftMenu .search').is(':visible') ? $('.leftMenu .search').outerHeight(true) : 0;
   $(".leftMenu .menu-cont").height(wHeight-kHeight - sHeight - tHeight - 12);
   
   var frame = $(".mainpage .iframeresize");
   var frame_r = $(".mainpage_r .iframeresize");
   if(frame){
	   $(".mainpage .rightForm").height(wHeight - 142);
	   $(".mainpage .closeMenu").height(wHeight - 102);
	   $(".mainpage .openMenu").height(wHeight - 102);
   }
   if(frame_r){
	   $(".mainpage_r .rightForm").height(wHeight - 142);
	   $(".mainpage_r .closeMenu").height(wHeight - 102);
	   $(".mainpage_r .openMenu").height(wHeight - 102);
   }
   
}

//左侧树高度
function leftMenuTreeHeight(){
	var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
	if($(".leftMenu .zTreeDemoBackground").is(":visible")){
	    var tree_top = $(".leftMenu .zTreeDemoBackground").position().top;
		var treeHeight = wHeight-tree_top;
		$(".leftMenu .ztree").css("height",treeHeight-11);
	}
}
$(window).resize(function() {
	prgleft_height();
	leftMenuTreeHeight();	
});