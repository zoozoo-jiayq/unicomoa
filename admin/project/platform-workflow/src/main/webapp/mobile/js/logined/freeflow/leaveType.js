mui.plusReady(function(){
	$("ul li").bind("tap",function(){
		var type=$(this).html();
		plus.webview.getWebviewById("qingjia").evalJS("getSelectType('"+type+"')");
		plus.webview.close(plus.webview.currentWebview());
	});
});