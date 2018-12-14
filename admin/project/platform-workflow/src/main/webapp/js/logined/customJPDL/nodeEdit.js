/**
 * 编辑流程节点
 */
jQuery(document).ready(function(){
	
	$("#listTab").click(function(){
		$(this).addClass("current");
		$("#viewTab").removeClass("current");
		$("#list").css("display","");
		$("#myflow").css("display","none");
	});
	$("#viewTab").click(function(){
		$(this).addClass("current");
		$("#listTab").removeClass("current");
		$("#list").css("display","none");
		$("#myflow").css("display","");
	});

	$("#sure").click(function(){
		window.parent.parent.closeTab("nodeDesign");
	});
	
});