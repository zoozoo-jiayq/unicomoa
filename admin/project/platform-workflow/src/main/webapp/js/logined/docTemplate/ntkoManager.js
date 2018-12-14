$(document).ready(function(){
	$(".hideleft").click(function(){
		$(".leftShow").hide();
		$(".leftHide").show();
		$(".mainpage").addClass("mainpageHide");
		});//隐藏
		$(".leftHide").click(function(){
		$(".leftShow").show();
		$(".leftHide").hide();
		$(".mainpage").removeClass("mainpageHide");
		});//展开
	   // window.parent.frameResize1();
	    
});
 

/**
 * 初始化
 */
function init(){
	var docTemplateId =$("#docTemplateId").val();
	var changeDocPath = $("#changeDocPath").val();
	var url = basePath+"ntko/ajaxTemplate_getOfficeContent.action?docTemplateId="+docTemplateId;
	intializePage(url);  //读取文件，如果没有保存，则读取默认页
}