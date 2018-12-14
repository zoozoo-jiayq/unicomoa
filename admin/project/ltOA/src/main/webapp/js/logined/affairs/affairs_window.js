var lastTimeAffairs = 2;
var timerAffairs;

$(document).ready(function() {	
    //单击全部已阅
    $("#allReadBtn").click(function(){
    	updateAllReaded();
        return false;
    });
    
    //单击关闭
    $("#closeBtn").click(function(){
    	art.dialog.close();
        return false;
    });
    
	lastTimeAffairs=2;
	$("#allDetailBtn").hide();
    var affairsSize = $("#affairsSize").html();
    if (null == affairsSize || "" == affairsSize || "0" == $.trim(affairsSize)){
    	$("#affairsSize").empty();
    	$("#affairsSize").append("0");
    	$("#popCont").hide();
    	$("#noAffairs").show();
    	$("#allReadBtn").hide();
    	$("#allDetailBtn").hide();
    	timerAffairs = setInterval("closeAffairsWindows()", 1000);

    }else{    	
    	setTimeout(function(){
    		$("#popCont").show();
        	$("#noAffairs").hide();
        	
            //单击全部已阅
            $("#allReadBtn").click(function(){
            	updateAllReaded();
                return false;
            });                
        	
            //单击关闭
            $("#closeBtn").click(function(){
            	art.dialog.close();
                return false;
            });
    	}, 300);    	
    }
});

/**
 * 打开提醒链接
 * @param remainUrl remainUrl
 */
function openRemaidUrl(remainUrl, id, smsType){
	// 应贾永强要求,工作流和公文不弹窗,显示在tab页中 由于其他原因,全部修改为弹窗
	var addNewTab = art.dialog.data('addNewTab');
	addNewTab(Math.random(), basePath+remainUrl, smsType);
	var url=basePath+"affairs/setup_updateReadedAffairs.action?affairsId="+id;
	$.ajax({
		url : url,
		type : "post",
		dataType :'text',
		success : function(data) {
			if(data == "success") {
				window.location.reload();
			}
		}
	});
}

/**
 * 分类全部已阅
 * @param remainUrl remainUrl
 */
function updateModuleReaded(moduleName){
	var paramData = {
	    'moduleName' : moduleName
	};
	$.ajax({
		url : basePath+"affairs/setup_updateModuleReaded.action",
		type : "post",
		dataType :'text',
		data : paramData,
		success : function(data) {
			if(data == "success") {    
				window.location.href=window.location.href;
			}
		}
	});
}

/**
 * 全部已阅
 * @param remainUrl remainUrl
 */
function updateAllReaded(){
	$.ajax({
		url : basePath+"affairs/setup_updateAllReaded.action",
		type : "post",
		dataType :'text',
		success : function(data) {
			if(data == "success") {    
				window.location.href=window.location.href;
//				art.dialog.close();
			}
		}
	});
}


/**
 * 定时刷新页面时间信息
 */
function closeAffairsWindows(){
	$("#lastTime").empty();
	$("#lastTime").append(lastTimeAffairs);	
	if (lastTimeAffairs == 0){
		clearInterval(timerAffairs);
		art.dialog.close();
	}else{
		lastTimeAffairs--;
	}
}

function showAffairHistory(){
	top.addTab("affairHistory", basePath+"logined/affairs/list_receive_affairs.jsp", "事务提醒历史记录");
	art.dialog.close();
}