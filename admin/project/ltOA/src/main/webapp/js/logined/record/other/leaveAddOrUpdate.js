var ue;
$(document).ready(function() {
	/*加载ueditor*/
	ue = UE.getEditor('contentInfo', {
		initialFrameWidth : "100%",
		toolbars:[[
                    "bold","underline","strikethrough","forecolor","backcolor","justifyleft",
                    "justifycenter","justifyright","justifyjustify","customstyle","paragraph","fontsize","insertimage","date","time"]]
		});
	
	loadCapitalUnit();
	var leaveId = $("#leaveId").val();
	if(leaveId){
		var whereabouts = $("#whereabouts").val();
		$("#limitWhereabouts").empty().text(255-whereabouts.length);
		var resignationProcedure = $("#resignationProcedure").val();
		$("#limitResignationProcedure").empty().text(255-resignationProcedure.length);
		var remark = $("#remark").val();
		$("#limitRemark").empty().text(255-remark.length);
		ue.addListener("ready", function () {
	        // editor准备好之后才可以使用
	        ue.setContent(contentInfo);
	     });
	}
} );

function addOrUpdate(obj){
	$(obj).attr("disabled",true);
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		$(obj).attr("disabled",false);
		return;
	}
	var leaveId = $("#leaveId").val();
	var position = $("#position").val();
	var leaveType = $("#leaveType").val();
	if(typeof(leaveType) == "undefined" || leaveType.trim() == ""){
		qytx.app.valid.showObjError($("#leaveTypeHidden"), 'recordLeave.leaveType_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#leaveTypeHidden"));
	}
	var applyDate = $("#applyDate").val();
	var intendedToLeaveDate = $("#intendedToLeaveDate").val();
	if(typeof(intendedToLeaveDate) == "undefined" || intendedToLeaveDate.trim() == ""){
		qytx.app.valid.showObjError($("#intendedHidden"), 'recordLeave.intended_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#intendedHidden"));
	}
	var actualLeaveDate = $("#actualLeaveDate").val();
	if(typeof(actualLeaveDate) == "undefined" || actualLeaveDate.trim() == ""){
		qytx.app.valid.showObjError($("#actualHidden"), 'recordLeave.actual_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#actualHidden"));
	}	
	if(applyDate&&(intendedToLeaveDate||actualLeaveDate)&&(applyDate>intendedToLeaveDate||applyDate>actualLeaveDate)){
		qytx.app.valid.showObjError($("#applyHidden"), 'recordLeave.date_not_error');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#applyHidden"));
	}	
	var wageCutoffDate = $("#wageCutoffDate").val();
	if(typeof(wageCutoffDate) == "undefined" || wageCutoffDate.trim() == ""){
		qytx.app.valid.showObjError($("#wageHidden"), 'recordLeave.wage_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#wageHidden"));
	}
	var leaveTheMonthWage = $("#leaveTheMonthWage").val();
	if(isNaN(leaveTheMonthWage)){
		qytx.app.valid.showObjError($("#leaveWageHidden"), 'recordLeave.leaveWage_not_number');
		$(obj).attr("disabled",false);
		return;
	}else if(leaveTheMonthWage > 99999999){
        qytx.app.valid.showObjError($("#leaveWageHidden"), 'recordLeave.leaveWage_not__big');
        $(obj).attr("disabled",false);
        return;
    }else{
		qytx.app.valid.hideError($("#leaveWageHidden"));
	}
	var whereabouts = $("#whereabouts").val();
	var resignationProcedure = $("#resignationProcedure").val();
	var remark = $("#remark").val();
	var leaveReason=ue.getContent();
    var attment = $("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var param = {
		"recordLeave.id":leaveId,
		"recordLeave.position":position,
		"recordLeave.userInfo.userId":$("#userId").val(),
		"recordLeave.leaveType":leaveType,
		"recordLeave.applyDate":applyDate,
		"recordLeave.intendedToLeaveDate":intendedToLeaveDate,
		"recordLeave.actualLeaveDate":actualLeaveDate,
		"recordLeave.wageCutoffDate":wageCutoffDate,
		"recordLeave.leaveTheMonthWage":leaveTheMonthWage,
		"recordLeave.whereabouts":whereabouts,
		"recordLeave.resignationProcedure":resignationProcedure,
		"recordLeave.remark":remark,
		"recordLeave.leaveReason":leaveReason,
		"recordLeave.leaveDepartmentName":$("#leaveDepartmentName").val(),
		"recordLeave.attment":attment
	};
	var url = basePath+"/logined/recordOther/leaveSaveOrUpdate.action";
	$.ajax({
		url:url,
		type:'POST',
		data:param,
		dataType:'json',
		success:function(result){
			if(result=="1"){
				art.dialog.tips("添加成功!");
				setTimeout(function(){
					window.close();
				},1000);
			}else if(result=="2"){
				art.dialog.tips("修改成功!");
				setTimeout(function(){
					window.close();
				},1000);
			}else{
				art.dialog.alert("操作失败!");
			}
		}
	});
		
}

//加载计量单位下拉选
function loadCapitalUnit(){
	var param={
			"infoType":"leave_type",
			"sysTag":1
	}
	$.ajax({
		url:basePath+"dict/getDicts.action",
		type:'post',
		data:param,
		dataType:'json',
		success:function(data){
			var html="<option value =''>请选择</option>";
			var lleaveType=$("#lleaveType").val();
			if(data!=null){
				for(var i=0;i<data.length;i++){     
					if(data[i].value==lleaveType){
						html+="<option value='"+data[i].value+"' selected='selected'>"+data[i].name+"</option>";
					}else{
						html+="<option value='"+data[i].value+"'>"+data[i].name+"</option>";
					}				
				}
			}
			$("#leaveType").html(html);
		}
	})
};
