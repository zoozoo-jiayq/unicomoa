var publicTime;
$(document).ready(function() {
	        getInfo();
	
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
 * 判断是否有当天数据，没有则表示新增，有表示修改
 */
function getInfo(){
	var rTime = $("#rTime").val();
	if(rTime && rTime!=publicTime){
		publicTime = rTime;
		clearInfo();//1.清空除了日期外所有输入框 2.删除隐藏域id
		$.ajax({
			type:"post",
			data:{'isRtime':rTime},
			url:basePath +"productionSchedule/isRtime.action",//如果当天有数据，则返回当日对象，没有则返回空
			dataType : "json",
			success:function(result){
				if(!result){//代表无数据
					//代表没有数据	
				}else{
					$("#dailyCarAssembly").val(result.dailyCarAssembly==null?"":result.dailyCarAssembly);
					$("#stop").val(result.stop==null?"":result.stop);
					$("#transportSchedule").val(result.transportSchedule==null?"":result.transportSchedule);
					$("#coalLoading").val(result.coalLoading==null?"":result.coalLoading);
					$("#middle").val(result.middle==null?"":result.middle);
					$("#doorToStand").val(result.doorToStand==null?"":result.doorToStand);
					$("#Bothincome").val(result.Bothincome==null?"":result.Bothincome);
					$("#smallCoalMine").val(result.smallCoalMine==null?"":result.smallCoalMine);
					$("#largeCoalMine").val(result.largeCoalMine==null?"":result.largeCoalMine);
					$("#income").val(result.income==null?"":result.income);
					$("#sender").val(result.sender==null?"":result.sender);
					$("#standDoor").val(result.standDoor==null?"":result.standDoor);
					$("#groceries").val(result.groceries==null?"":result.groceries);
					$("#useOfVehicles").val(result.useOfVehicles==null?"":result.useOfVehicles);
					$("#doorToDoor").val(result.doorToDoor==null?"":result.doorToDoor);
					$("#comparedPlan").val(result.comparedPlan==null?"":result.comparedPlan);
					$("#sendTons").val(result.sendTons==null?"":result.sendTons);
					$("#sendPlan").val(result.sendPlan==null?"":result.sendPlan);
					$("#unload").val(result.unload==null?"":result.unload);
					$("#sendPersonPlan").val(result.sendPersonPlan==null?"":result.sendPersonPlan);
					$("#productionScheduleId").val(result.id);
					//input赋值
//					$("#rTime").val(rTime);
				}
			}
		});
	}
}

function clearInfo(){
	$(".content_form").find("input[maxlength='6']").val("");
	$("#productionScheduleId").val("");
}

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
	
	if($("#productionScheduleId").val()){
		//id幼稚，代表编辑
		paramData['productionScheduleId']=$("#productionScheduleId").val();
	}
	
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



