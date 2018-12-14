$(document).ready(function() {
			getAllLogNums();
			// 清除cookie中的分页信息
			$.removeTableCookie('SpryMedia_DataTables_productionScheduleTable_productionScheduleList.jsp');

			// 获取信息列表
			getDataTable();
			
			
			//获取安全生产天数
			getDiff();
			
			
			// 查询
			$("#searchLoginUser").click(function() {
				$.removeTableCookie('SpryMedia_DataTables_productionScheduleTable_productionScheduleList.jsp');
				getAllLogNums();
				getDataTable();//要触发的方法
			    return false;
		    });
			
			 //回车事件
			$(document).keydown(function(event){
				var code=event.which;
				if (code == 13) {
					// 清除cookie中的分页信息
					$.removeTableCookie('SpryMedia_DataTables_productionScheduleTable_productionScheduleList.jsp');
					getAllLogNums();
					getDataTable();//要触发的方法
		        }
			});
		});

/**
 * 获取列表
 */
function getDataTable() {

	
	qytx.app.grid({
		id	:	"productionScheduleTable",
		url	:	basePath + "productionSchedule/getProductionSchedule.action",
		iDisplayLength:	tableDisplayLength,
		selectParam:	{
			"startTime":$.trim($("#startTime").val()),
			"endTime":$.trim($("#endTime").val())
						},
		valuesFn:	[ 
		         	 ]
	});
}
/**
 * 统计结果
 * 
 * @return
 */
function getAllLogNums() {
	var urlStr = basePath + "productionSchedule/getTotelProduction.action";
	var startTime = $.trim($("#startTime").val());
	var endTime = $.trim($("#endTime").val());
	qytx.app.ajax({
				url : urlStr,
				type : "post",
				dataType : 'json',
				 data : {"startTime":startTime,"endTime":endTime},
				success : function(data) {
					$("#dailyCarAssembly").html(data[0].dailyCarAssembly);
					$("#stop").html(data[0].stop);
					$("#transportSchedule").html(data[0].transportSchedule);
					$("#coalLoading").html(data[0].coalLoading);
					$("#middle").html(data[0].middle);
					$("#doorToStand").html(data[0].doorToStand);
					$("#largeCoalMine").html(data[0].largeCoalMine);
					$("#income").html(data[0].income);
					$("#bothincome").html(data[0].bothincome);
					$("#smallCoalMine").html(data[0].smallCoalMine);
					$("#sender").html(data[0].sender);
					$("#standDoor").html(data[0].standDoor);
					$("#groceries").html(data[0].groceries);
					$("#useOfVehicles").html(data[0].useOfVehicles);
					$("#deadLoad").html(data[0].deadLoad);
					$("#comparedPlan").html(data[0].comparedPlan);
					$("#doorToDoor").html(data[0].doorToDoor);
					$("#sendTons").html(data[0].sendTons);
					$("#sendPlan").html(data[0].sendPlan);
					$("#unload").html(data[0].unload);
					$("#sendPersonPlan").html(data[0].sendPersonPlan);
				}
			});
}


/**
 * 获取安全生产天数
 */
function getDiff(){
	var urlStr = basePath + "productionSchedule/getSafeDays.action";
	qytx.app.ajax({
		url : urlStr,
		type : "post",
		dataType : 'json',
		success : function(data) {
			$("#diff").html("<font color='red'>"+data.diff+"</font>");
			$("#curDate").html(data.curDate);
		}
	});
	
}