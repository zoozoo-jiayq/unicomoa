/**
 * @author CQL
 */
$(document).ready(function() {
	   //加载树
	    	refreshTree("gid_0");
	   //获取人员
	    	$.removeTableCookie('SpryMedia_DataTables_userTable_dayStatistics.jsp');
	    	getDataTable();
	    $("#exportAtten").click(function(){
	    	var keyword = $.trim($("#keyword").val());
	    	if (!keyword) {
	    		keyword = "";
	    	}
	    	
	    	var groupId = $.trim($("#groupId").val());
	    	if (groupId == "" || groupId == 0) {
	    		groupId = "";
	    	}
	    	var startTime=$.trim($("#startTime").val());
	    	if(!startTime){
	    		//startTime=null;
	    		art.dialog.alert("请选择开始日期!");
	    		return;
	    	}
	    	var endTime=$.trim($("#endTime").val());
	    	if(!endTime){
	    		//endTime=null;
	    		art.dialog.alert("请选择结束日期!");
	    		return;
	    	}
	    	
	    	var url=basePath+"attendance/statistics_exportDayStatistics.action?groupId="+groupId+"&startTime="+startTime+"&endTime="+endTime+"&searchKey="+encodeURI(encodeURI(keyword));
	    	document.location.href=url;
	    });
	    //查询
	    $("#searchRecords").click(function(){
	    	$.removeTableCookie('SpryMedia_DataTables_userTable_dayStatistics.jsp');
	    	getDataTable();
	    });
	 // 回车事件
		document.onkeydown = function(e) {
			// 兼容FF和IE和Opera
			var theEvent = e || window.event;
			var code = theEvent.keyCode || theEvent.which
					|| theEvent.charCode;

			if (code == 13) {
				_checkedIds="";
				$.removeTableCookie('SpryMedia_DataTables_userTable_dayStatistics.jsp');
				getDataTable();
			}

		}
});
 

//单击部门树菜单
function zTreeOnCheckResult(data) {
	     var type=data.getType(); // user 或者group
	     var groupId=data.getId();
	     var from = "&type=" + $("#type").val();
	     var source = "&source=tree";
	     $("#groupId").val(groupId);
	     $.removeTableCookie('SpryMedia_DataTables_userTable_dayStatistics.jsp');
	     getDataTable();
	     
 }

/**
 * 获取树内容
 * @param id
 */
function refreshTree(id){
	openSelectTreeUser(zTreeOnCheckResult, null, id);
}
/**
 * 获取人员考勤信息
 * @return
 */
function getDataTable() {
	var keyword = $.trim($("#keyword").val());
	if (!keyword) {
		keyword = "";
	}
	var groupId = $.trim($("#groupId").val());
	if (groupId == "" || groupId == 0) {
		groupId = "";
	}
	var startTime=$.trim($("#startTime").val());
	if(!startTime){
		//startTime=null;
		art.dialog.alert("请选择开始日期!");
		return;
	}
	var endTime=$.trim($("#endTime").val());
	if(!endTime){
		//endTime=null;
		art.dialog.alert("请选择结束日期!");
		return;
	}
	var startDate = new Date(startTime);
	var endDate = new Date(endTime);
	var diff = endDate.valueOf() - startDate.valueOf();
	var diff_day = parseInt(diff/(1000*60*60*24));
	if(diff_day>30){
		art.dialog.alert("最大只能查询时间间隔为一个月!");
		return;
	}
	
	$('#userTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
						"name" : "searchKey",
						"value" : keyword
					},  {
						"name" : "groupId",
						"value" : groupId
					},  {
						"name" : "startTime",
						"value" : startTime
					},  {
						"name" : "endTime",
						"value" : endTime
					},  {
						"name" : "state",
						"value" : $("#state").val()
					});
		},
		 
		"sAjaxSource" : basePath + "attendance/statistics_dayStatistics.action",// 考勤统计
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bStateSave" : true, // 状态保存
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"bDestroy" : true,
		"iDisplayLength" : tableDisplayLength, // 每页显示多少行
		"aoColumns" : [ {
					"mDataProp" : "no",
					"sClass" : "num"
				},{
					"mDataProp" : "userName" 
				} ,{
					"mDataProp" : "groupName",
					"sClass" : "tdCenter"
				},{
					"mDataProp" : null,
					"sClass" : "tdCenter"
				},{
					"mDataProp" : "attendTime",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "onTime",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "onState",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "onPosition",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "onMemo",
					"sClass" : "tdCenter"
				}
				/*,{
					"mDataProp" : "amOffTime",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "amOffState",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "amOffPosition",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "amOffMemo",
					"sClass" : "tdCenter"
				},{
					"mDataProp" : "pmOnTime",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "pmOnState",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "pmOnPosition",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "pmOnMemo",
					"sClass" : "tdCenter"
				}*/
				,{
					"mDataProp" : "offTime",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "offState",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "offPosition",
					"sClass" : "tdCenter"
				}
				,{
					"mDataProp" : "offMemo",
					"sClass" : "tdCenter"
				},{
					"mDataProp" : "attendDays",
					"sClass" : "tdCenter"
				}
				, {
					"mDataProp" : "lateTimes",
					"sClass" : "tdCenter"
				}
				, {
					"mDataProp" : null,
					"sClass" : "tdCenter"
				}, {
					"mDataProp" : "earlyTimes",
					"sClass" : "tdCenter"
				}, {
					"mDataProp" : "earlyDuration",
					"sClass" : "tdCenter"
				}, {
					"mDataProp" : "onDutyLackTimes",
					"sClass" : "tdCenter"
				}, {
					"mDataProp" : "offDutyLackTimes",
					"sClass" : "tdCenter"
				}
				/*, {
					"mDataProp" : "allLackTimes",
					"sClass" : "tdCenter"
				}*/
				,
				/*{
					"mDataProp" : "outDays",
					"sClass" : "tdCenter"
				}*/
				{
					"mDataProp" : null,
					"sClass" : "tdCenter"
				},{
					"mDataProp" : null,
					"sClass" : "tdCenter"
				},{
					"mDataProp" : null,
					"sClass" : "tdCenter"
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
//			$("#totalNum").html(oSettings.fnRecordsDisplay());
//			$("#companyAll").html(oSettings.fnRecordsDisplay());
//			$("#selectedNum").html(0);
//			$("#total").prop("checked", false);
			// 重置iframe高度
//            window.parent.frameResize();
//			_userCount =   oSettings.fnRecordsDisplay();
//			_getChecked();
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
 //          window.parent.frameResize();"lateDuration"
		},
		"aoColumnDefs" : [{
			"aTargets" : [3],
			"fnRender" : function(oObj) {
				var createDate = oObj.aData.createDate;
				var week = oObj.aData.week;
				return createDate+"&nbsp;"+week;

			}
		},{
			"aTargets" : [15],
			"fnRender" : function(oObj) {
				var userId = oObj.aData.userId;
				var lateDuration = oObj.aData.lateDuration;
				var lateDurationNum = oObj.aData.lateDurationNum;
				var createDate = oObj.aData.createDate;
				if(lateDurationNum!=0){
					lateDuration = "<a onClick='lateOrEarlyDetail("+userId+",2,\""+createDate+"\")' style='cursor:pointer'>"+lateDuration+"</a>";
				}
				return lateDuration;

			}
		},{
			"aTargets" : [17],
			"fnRender" : function(oObj) {
				var userId = oObj.aData.userId;
				var earlyDuration = oObj.aData.earlyDuration;
				var earlyDurationNum = oObj.aData.earlyDurationNum;
				var createDate = oObj.aData.createDate;
				if(earlyDurationNum!=0){
					earlyDuration = "<a onClick='lateOrEarlyDetail("+userId+",3,\""+createDate+"\")' style='cursor:pointer'>"+earlyDuration+"</a>";
				}
				return earlyDuration;
			}
		},{
			"aTargets" : [20],
			"fnRender" : function(oObj) {
				var userId = oObj.aData.userId;
				var leaveDays = oObj.aData.leaveDays;
				if(leaveDays!=0){
					leaveDays = "<a onClick='leaveDetail("+userId+",1)' style='cursor:pointer'>"+leaveDays+"</a>";
				}
				return leaveDays;

			}
		},{
			"aTargets" : [21],
			"fnRender" : function(oObj) {
				var userId = oObj.aData.userId;
				var workLeaveDays = oObj.aData.workLeaveDays;
				if(workLeaveDays!=0){
					workLeaveDays = "<a onClick='leaveDetail("+userId+",2)' style='cursor:pointer'>"+workLeaveDays+"</a>";
				}
				return workLeaveDays;

			}
		},{
			"aTargets" : [22],
			"fnRender" : function(oObj) {
				var userId = oObj.aData.userId;
				var sleepLeaveDays = oObj.aData.sleepLeaveDays;
				if(sleepLeaveDays!=0){
					sleepLeaveDays = "<a onClick='leaveDetail("+userId+",3)' style='cursor:pointer'>"+sleepLeaveDays+"</a>";
				}
				return sleepLeaveDays;

			}
		}]
	});
}

function leaveDetail(userId,type){
	var startTime=$.trim($("#startTime").val());
	if(!startTime){
		//startTime=null;
		art.dialog.alert("请选择开始日期!");
		return;
	}
	var endTime=$.trim($("#endTime").val());
	if(!endTime){
		//endTime=null;
		art.dialog.alert("请选择结束日期!");
		return;
	}
	var startDate = new Date(startTime);
	var endDate = new Date(endTime);
	var diff = endDate.valueOf() - startDate.valueOf();
	var diff_day = parseInt(diff/(1000*60*60*24));
	if(diff_day>30){
		art.dialog.alert("最大只能查询时间间隔为一个月!");
		return;
	}
	var url = basePath+"logined/attendance/leaveList.jsp?userId="+userId+"&startTime="+startTime+"&endTime="+endTime+"&type="+type;
	var title = "请假单";
	if(type==1){
		title = "请假单";
	}else if(type==2){
		title = "工作假单";
	}else if(type==3){
		title = "公休假单";
	}
	art.dialog.open(url,{
		id:"leaveList",
		title:title,
		width : 700,
		height : 307,
		lock : true,
	    opacity: 0.08,
		button : [{
					name : '关闭',
					callback : function() {
						return true;
					}
				}]
	});
}


function lateOrEarlyDetail(userId,attState,startTime){
	var url = basePath+"logined/attendance/lateOrEarlyList.jsp?userId="+userId+"&startTime="+startTime+"&endTime="+startTime+"&attState="+attState;
	var title = "早退列表";
	if(attState==2){
		title = "迟到列表";
	}
	art.dialog.open(url,{
		id:"lateOrEarly",
		title:title,
		width : 700,
		height : 307,
		lock : true,
		opacity: 0.08,
		button : [{
			name : '关闭',
			callback : function() {
				return true;
			}
		}]
	});
}


