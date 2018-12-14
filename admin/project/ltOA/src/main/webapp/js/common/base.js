// JavaScript Document

/**
 * 重置iFrame的高度
 */
function frameResize(){
    var frame=$(".mainpage iframe");
    if(frame.length>0){
        frame.height(frame.contents().find("body").height()+50);
    }
}

$(document).ready(function(){
	$(".tab li").click(function(){
		var index_tab = $(this).parent().children().index($(this));//选项卡的索引值
		$(this).parent().find(".current").removeClass("current"); 
		$(this).addClass("current");
		var content_obj = $(this).parent().parent().find(".tabContent");//内容节点
		content_obj.hide();
        content_obj.eq(index_tab).show(); 
		
	});//选项卡
	$(".menuList").eq(0).show();//默认第一个打开
	$(".menuList").each(function(){
		$(this).children("li").eq(0).css("border","0");
		});//清除第一个Li的border
		$(".leftMenu .blockBox h2").toggle(function(){
		if ($(this).next().is("ul"))
		{	$(this).children("em").addClass("op");
			$(this).next().show();
		}},function(){
	   if ($(this).next().is("ul"))
		{ $(this).next().hide();	
		$(this).children("em").removeClass("op");
		}});//左侧展开收起
	$(".BlockTop h2").toggle(function(){
		$(".blockBox").children(".menuList").eq(0).hide();
		$(this).children("em").removeClass("op");
		},function(){
		$(".blockBox").children(".menuList").eq(0).show();	
		$(this).children("em").addClass("op");
			});//左侧第一个展开收起
		$(".menu_l .menu-p").click(function(){
			if ($(this).parent().hasClass("menu_current"))
			{$(this).parent().removeClass("menu_current");}
			else
			{$(".menu_l").removeClass("menu_current");
			$(this).parent().addClass("menu_current");
	    }});	
	$(".pretty thead th").last().css("border-right","0");//去除最右侧border
	$(".pretty tbody tr").each(function(){
		$(this).children("td").last().css("border-right","0");
		})//去除最右侧线border
	$(".morebtn").hover(function(){
		$(this).addClass("morebtnHover");
		},function(){
		$(this).removeClass("morebtnHover");	
			});//更多操作
  //$(".mainpage iframe").height($(".mainpage iframe").contents().find("body").height()+50);//去除内页带iframe的外层滚动条
   $(".mainpage iframe").load(function(){
       frameResize();
   });
	$(".mywork h2").toggle(function(){
		$(this).children("em").addClass("cls");
		$(this).parent().children(".wlist").hide();
		},function(){
		$(this).children("em").removeClass("cls");
		$(this).parent().children(".wlist").show();	
			});//我的工作展开收起
	// 分类树管理
	$("td.categoryName").click(function(){
		var grade = $(this).parent().attr("grade");
		var isHide;
		if (grade == "0")
		{
			if($(this).children("span").hasClass("firstCategory_cls"))
				$(this).children("span").removeClass("firstCategory_cls");
			else
			    $(this).children("span").addClass("firstCategory_cls");	
		};
		if (grade!= "0" && grade < $(this).parent().next("tr").attr("grade"))
		{
			if($(this).children("span").hasClass("category_cls"))
				$(this).children("span").removeClass("category_cls");
			else
			    $(this).children("span").addClass("category_cls");
		};
		$(this).parent().nextAll("tr").each(function(){
			var thisLevel = $(this).attr("grade");
			if(thisLevel <=  grade) {
				return false;
			}
			if(isHide == null) {
				if($(this).is(":hidden")){
					isHide = true;
				} else {
					isHide = false;
				}
			}
			if( isHide) {
				$(this).show();
			} else {
				$(this).hide();			
			}
		});
	});

});
//iframe auto
function dyniframesize(down) {
var pTar = null;
if (document.getElementById){ 
pTar = document.getElementById(down); 
} 
else{ 
eval('pTar = ' + down + ';'); 
} 
if (pTar && !window.opera){ 
//begin resizing iframe 
pTar.style.display="block" 
if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
//ns6 syntax 
pTar.height = pTar.contentDocument.body.offsetHeight +20; 
pTar.width = pTar.contentDocument.body.scrollWidth+20; 
} 
else if (pTar.Document && pTar.Document.body.scrollHeight){ 
//ie5+ syntax 
pTar.height = pTar.Document.body.scrollHeight; 
pTar.width = pTar.Document.body.scrollWidth; 
} 
} 
} 
//展开显示
function showHide(id){
	var v_ID = document.getElementById(id);
	if (jQuery(v_ID).is(":visible"))
	jQuery(v_ID).hide();
	else
	jQuery(v_ID).show();
}
