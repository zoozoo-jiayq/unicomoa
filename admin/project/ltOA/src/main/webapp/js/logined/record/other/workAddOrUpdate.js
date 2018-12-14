$(function(){
	/*加载ueditor*/
	ue = UE.getEditor('contentInfo', {
		initialFrameWidth : "100%",
		toolbars:[[
                    "bold","underline","strikethrough","forecolor","backcolor","justifyleft",
                    "justifycenter","justifyright","justifyjustify","customstyle","paragraph","fontsize","insertimage","date","time"]]
		});
	var workId=$("#workId").val();
	if(workId){
		var jobContent =$("#jobContent").val();
		$("#limitHomeJobContent").empty().text(255-jobContent.length);
		
		var leavingReasons =$("#leavingReasons").val();
		$("#limitHomeLeavingReasons").empty().text(255-leavingReasons.length);
		
		var remark =$("#remark").val();
		$("#limitRemark").empty().text(255-remark.length);
		
		
		ue.addListener("ready", function () {
	        // editor准备好之后才可以使用
	        ue.setContent(contentInfo);
	     });
	}
	
	
})

function saveOrUpdateWork(obj){
	$(obj).attr("disabled",true);
	var userId=$("#userId").val();
	var workId=$("#workId").val();
	var position=$("#position").val();
	if(!position){
		qytx.app.valid.showObjError($("#position"), 'work.work_position_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#position"));
	}
	
	var department=$("#department").val();
	if(!department){
		qytx.app.valid.showObjError($("#department"), 'work.work_department_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#department"));
	}
	
	var reterence = $("#reterence").val();
	var startDate= $("#startDate").val();
	if(!startDate){
		qytx.app.valid.showObjError($("#startDateHidden"), 'work.work_startDate_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#startDateHidden"));
	}
	var endDate=$("#endDate").val();
	if(!endDate){
		qytx.app.valid.showObjError($("#endDateHidden"), 'work.work_endDate_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#endDateHidden"));
	}
	if(!tab(startDate,endDate)){
		qytx.app.valid.showObjError($("#endDateHidden"), 'work.work_comparison_time');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#endDateHidden"));
	}
	var industryCategory=$("#industryCategory").val();
	var workUnit=$("#workUnit").val();
	if(!workUnit){
		qytx.app.valid.showObjError($("#workUnit"), 'work.work_workUnit_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#workUnit"));
	}
	var jobContent=$("#jobContent").val();
	var leavingReasons=$("#leavingReasons").val();
	var remark=$("#remark").val();
	var achievement=ue.getContent();
	var attment=$("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var param={
			"recordWork.id":workId,
			"recordWork.userInfo.userId":userId,
			"recordWork.position":position,
			"recordWork.department":department,
			"recordWork.reterence":reterence,
			"recordWork.startDate":startDate,
			"recordWork.endDate":endDate,
			"recordWork.industryCategory":industryCategory,
			"recordWork.workUnit":workUnit,
			"recordWork.jobContent":jobContent,
			"recordWork.leavingReasons":leavingReasons,
			"recordWork.remark":remark,
			"recordWork.achievement":achievement,
			"recordWork.attment":attment
	}
	$.ajax({
		url:basePath+"/logined/recordOther/saveOrUpdateWork.action",
	    type:"post",
	    dataType:"text",
	    data:param,
	    success:function(result){
	    	if(result==1){
	    		art.dialog.tips("添加成功!");
				setTimeout(function(){
					window.close();
				},1000);
	    	}else if(result==2){
	    		art.dialog.tips("修改成功!");
				setTimeout(function(){
					window.close();
				},1000);
	    	}else{
	    		$(obj).attr("disabled",false);
	    		art.dialog.alert("操作失败！");
	    	}
	    }
	})
}
/**
 * 比较两个时间大小
 * @param date1
 * @param date2
 */
function tab(startTimne,endTime){
    var oDate1 = new Date(startTimne);
    var oDate2 = new Date(endTime);
    if(oDate1.getTime() > oDate2.getTime()){
        return false;
    } else {
       return true;
    }
}