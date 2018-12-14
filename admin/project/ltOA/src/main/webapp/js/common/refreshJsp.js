/**
 * 获得坐席端中当前选中的标签页的id
 * @returns  当前选中的标签页的id
 */
function getCurrentTabId() {
	var ulObj=$(window.top.document.getElementById("div_tab"));
	var tabId=ulObj.find("li[class='crent']").first().attr("id");
	return tabId;
}



/**
 * 刷新指定的标签页面
 * @param msg 冒号分割：冒号前的是调用者的方法名，冒号后是具体传的信息（用逗号分割）； 逗号分割：自定义
 * @returns {Boolean}
 */
function refreshRelatedCustomerCall(msg) {
	//alert("process.js msg: "+msg);
	/* 开始解析信息 */
	var array=msg.split(':');
    var msgHead=array[0];//调用者的方法名
    var msgBody=array[1];//具体信息
    
    /* 不同的调用者，执行不同的方法。 */
    //alert(msgHead+","+msgBody);
    if(msgHead=='lotteryListJsp'){
    	//alert("true");
		var operationTabId=msgBody;//将要操作的标签页的id
    	
		// 刷新来电弹屏页面中的相关工单列表和历史服务列表
		if (undefined != window.frames["div_"+operationTabId]) {
			window.frames["div_"+operationTabId].getDataTables();
			return false;
		}
    }else{
    	//alert("false");
    }
    
}