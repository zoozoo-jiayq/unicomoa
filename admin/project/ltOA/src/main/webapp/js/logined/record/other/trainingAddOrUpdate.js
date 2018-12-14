
function addOrUpdateTraining(obj){
 	$(obj).attr("disabled",true);
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		$(obj).attr("disabled",false);
		return;
	}
	var trainDate = $("#trainDate").val();
	if(!trainDate){
		$(obj).attr("disabled",false);
		qytx.app.valid.showObjError($("#trainDateHidden"), 'recordTraining.trainDate_not_null');
		return;
	}else{
		qytx.app.valid.hideError($("#trainDateHidden"));
	}
	
	var trainingId = $("#trainingId").val();
	var trainPlanName = $("#trainPlanName").val();
	var trainMechanism = $("#trainMechanism").val();
	var trainMoney = $("#trainMoney").val();
	var trainDate = $("#trainDate").val();
	var trainEndDate = $("#trainEndDate").val();
    var attment = $("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var param = {
		"trainingId":trainingId,
		"recordTraining.trainPlanName":trainPlanName,
		"recordTraining.trainMechanism":trainMechanism,
		"recordTraining.userInfo.userId":$("#userId").val(),
		"recordTraining.trainMoney":trainMoney,
		"recordTraining.trainDate":trainDate,
		"recordTraining.trainEndDate":trainEndDate,
		"recordTraining.attment":attment
	};
	var url = basePath+"/logined/recordOther/saveOrUpdateTraining.action";
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
				$(obj).attr("disabled",false);
				art.dialog.alert("操作失败!");
			}
		}
	});
	
	
}

