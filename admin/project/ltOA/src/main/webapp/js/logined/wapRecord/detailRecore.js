$(function(){
	
	/**
	 * 奖惩
	 */
	$("#goRewardList").click(function(){
		var id=$("#id").val();
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/rewardList.jsp?_clientType=wap&id="+id+"&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	/**
	 * 后退
	 */
	$("#backImg").click(function(){
		window.history.back()
	})
	/**
	 * 学习
	 */
	$("#goStudyList").click(function(){
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/studyList.jsp?_clientType=wap&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	/**
	 * 工作
	 */
	$("#goWorkList").click(function(){
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/workList.jsp?_clientType=wap&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	/**
	 * 关系
	 */
	$("#goRelationList").click(function(){
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/relationList.jsp?_clientType=wap&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	/**
	 * 调动
	 */
	$("#goMoveList").click(function(){
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/moveList.jsp?_clientType=wap&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	/**
	 * 职称
	 */
	$("#goPositionList").click(function(){
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/positionList.jsp?_clientType=wap&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	/**
	 * 离职
	 */
	$("#goLeaveList").click(function(){
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/leaveList.jsp?_clientType=wap&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	
	/**
	 * 培训
	 */
	$("#goTrainList").click(function(){
		var userId=$("#userId").val()
		window.open(basePath+"logined/wapRecord/trainList.jsp?_clientType=wap&userId="+userId+"&loginUserNow="+loginUserNow,"_self")
	})
	/**
	 * 附件
	 */
	$("#goAccessoryList").click(function(){
		var userId=$("#userId").val()
		var id=$("#id").val();
		
		window.open(basePath+"logined/wapRecord/accessoryList.jsp?_clientType=wap&id="+id+"&loginUserNow="+loginUserNow,"_self")
	})
	
})