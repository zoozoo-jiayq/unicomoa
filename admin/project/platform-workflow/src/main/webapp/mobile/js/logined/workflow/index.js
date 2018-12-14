
$(document).ready(function(){
	var winWidth = $(window).width();
	var bannerHeight = 0.5*winWidth;
	$('.banner').height(bannerHeight+"px");
	
});

function mywaitCount(){
	$.ajax({
		type: "POST",
        url: basePath+"/baseworkflow/myWaitCount.c?r="+Math.random(),
        data: {
        	"userId":h5Adapter.getItemValue("currentUserId"),
        	"moduleCode":"baseworkflow",
        	"_clientType":"wap"
        },
        success: function(msg){
            if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
                if(result>0){
                	$("#approveRound").addClass("approve-round");
                }
            }
        }
		
	});
}
/**
 * webview加载完毕初始化导航条按钮
 */
function loadSuccess(isNetOk){
	//获取用户信息并保存到localStorage中
	h5Adapter.getLoginUserInfo(function(data){
		var dataUser = eval('('+data.userInfo+')');
		h5Adapter.setItem("currentUserId", dataUser.userId);
		//获取待审批数量
		mywaitCount();
	});
	h5Adapter.onHeaderButton(null,{"type":"back","onClickMethod":"back"});
	
}

//退出审批模块
function back(){
	h5Adapter.backDesk();
}



