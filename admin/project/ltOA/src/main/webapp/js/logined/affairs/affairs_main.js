$(document).ready(function() {	
	$(".menuList li").click(function(){
		$(this).parent().find(".current").removeClass("current"); 
		$(this).addClass("current");		
	});
});

function openIframe(url){
	$("#affairs_main").attr("src", url); 
}