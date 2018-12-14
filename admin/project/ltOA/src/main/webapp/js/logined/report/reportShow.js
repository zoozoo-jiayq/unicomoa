$(document).ready(function() {
	 $(".menu-c li a ").click(function(){
		 $(".menu-c li a ").each(function(){
			 $(this).removeClass("on");
		 });
		  $(this).addClass("on");
	 });
});
 
/**
 * 跳转页面
 * @param url
 */
function goReportPage(url){
	var urlPage = basePath+url;
	$("#page").attr("src",urlPage)
}
 
 
