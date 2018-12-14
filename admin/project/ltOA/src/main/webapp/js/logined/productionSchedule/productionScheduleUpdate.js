
$(document).ready(function() {
			
	
			// 单击更新
			$("#userInfoUpdate").click(function() {
						//	var valid = qytx.app.valid.check({form:$("#userForm")[0]});
						
								submitForm();
					});
			// 单击取消
			$("#back").click(function() {
				window.history.go(-1);
						return false;
					});
	
		});


/**
 * 添加信息
 */
function submitForm() {
	var productionScheduleId  = $("#productionScheduleId").val();
	var dailyCarAssembly = $.trim($("#dailyCarAssembly").val());
	var stop = $.trim($("#stop").val());
	var transportSchedule = $.trim($("#transportSchedule").val());
	var coalLoading = $.trim($("#coalLoading").val());
	var middle = $.trim($("#middle").val());
	var doorToStand = $.trim($("#doorToStand").val());
	var Bothincome = $.trim($("#Bothincome").val());
	var smallCoalMine = $.trim($("#smallCoalMine").val());
	var largeCoalMine = $.trim($("#largeCoalMine").val());
	var income = $.trim($("#income").val());
	var sender = $.trim($("#sender").val());
	var standDoor = $.trim($("#standDoor").val());
	var groceries = $.trim($("#groceries").val());
	var useOfVehicles = $.trim($("#useOfVehicles").val());
	var deadLoad = $.trim($("#deadLoad").val());
	var doorToDoor = $.trim($("#doorToDoor").val());
	var comparedPlan = $.trim($("#comparedPlan").val());
	var sendTons = $.trim($("#sendTons").val());
	var sendPlan = $.trim($("#sendPlan").val());
	var unload = $.trim($("#unload").val());
	var sendPersonPlan = $.trim($("#sendPersonPlan").val());
	var rTime = $("#rTime").val();
	var title = "添加成功！";
	var titlerro = "添加失败！";
	if(productionScheduleId!=null&&productionScheduleId!=""){
		 title = "修改成功！";
		 titlerro = "修改失败！";
	}
	var paramData = {
		'startTimes' : rTime, 
		'productionScheduleId' : productionScheduleId,
		'productionSchedule.dailyCarAssembly' : dailyCarAssembly,
		'productionSchedule.stop' : stop,
		'productionSchedule.transportSchedule' : transportSchedule,
		'productionSchedule.coalLoading' : coalLoading,
		'productionSchedule.middle' : middle,
		'productionSchedule.doorToStand' : doorToStand,
		'productionSchedule.Bothincome' : Bothincome,
		'productionSchedule.smallCoalMine' : smallCoalMine,
		'productionSchedule.largeCoalMine' : largeCoalMine,
		'productionSchedule.income' : income,
		'productionSchedule.sender' : sender,
		'productionSchedule.standDoor' : standDoor,
		'productionSchedule.groceries' : groceries,
		'productionSchedule.useOfVehicles' : useOfVehicles,
		'productionSchedule.deadLoad' : deadLoad,
		'productionSchedule.doorToDoor' : doorToDoor,
		'productionSchedule.comparedPlan' : comparedPlan,
		'productionSchedule.sendTons' : sendTons,
		'productionSchedule.sendPlan' : sendPlan,
		'productionSchedule.unload' : unload,
		'productionSchedule.sendPersonPlan' : sendPersonPlan
		
	};
	qytx.app.ajax({
				type : 'post',
				url : basePath + "productionSchedule/productionScheduleSave.action",
				data : paramData,
				dataType : 'json',
				async : false,
				shade:true,
				success : function(data) {
					if (data == "1") {	
						qytx.app.dialog.tips(title,function(){
							window.location.href = basePath + "logined/productionSchedule/productionScheduleList.jsp";
						})
					} else {
						qytx.app.dialog.tips(titlerro,function(){
							return false;
						});
					}

				}
			});
}



