mui.plusReady(function(){
	initParams();
	function initParams(){
		window.windowCommon={
			baseUrl:plus.storage.getItem("baseUrl"),
			basePath:plus.storage.getItem("basePath"),
			photoUrl:plus.storage.getItem("photoUrl"),
			approveLoginId:plus.storage.getItem("approveLoginId"),
			approveLoginName:plus.storage.getItem("approveLoginName"),
			approveLoginGroupId:plus.storage.getItem("approveLoginGroupId"),
		};
	}
	//初始化无网络页面
	initNoNetHTML();
	$(".mui-action-back").bind("touchend",function(){
		mui.back();
	});
	//监听网络状态变化
	document.addEventListener("netchange",function(){
		if(getNetConnection()){
			plus.webview.currentWebview().reload();
		}else{
			$(".mui-content").hide();
			$(".noNet").show();
		}
	});
	
});
function initNoNetHTML(){
	var html='<div class="noNet" style="width:100%;overflow: hidden;margin-top: 10%;display:none;">';
	html+='<img src="../../images/noNetworkBackground@2x.png" alt="" style="display: block;width: 100%;" />';		
	html+='</div>';
	$("body").append(html);
}
//判断当前网络状态
function getNetConnection() {
	var type = plus.networkinfo.getCurrentType();
	if (type == plus.networkinfo.CONNECTION_NONE) {
		return false;
	}
	return true;
}
//显示提交等待
function showWaiting(){
	plus.nativeUI.showWaiting("处理中，请等待...");
}
//关闭提交等待
function closeWaiting(){
	plus.nativeUI.closeWaiting();
}
