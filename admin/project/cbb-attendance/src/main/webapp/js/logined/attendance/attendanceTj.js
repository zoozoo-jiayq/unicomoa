/**
 * @author CQL
 */
$(document).ready(function() {
	   //加载树
	    	refreshTree("gid_0");
	   //获取人员
	    	$.removeTableCookie('SpryMedia_DataTables_userTable_attendanceTj.jsp');
	    	getDataTable();
	    $("#exportAtten").click(function(){
	    	var groupId=$("#groupId").val();
	    	if(groupId==""){
	    		groupId=0;
	    	}
	    	var beginT=$("#startT").val();
	    	var endT=$("#endT").val();
	    	var keyword=$("#keyword").val();
	    	var url=basePath+"attendance/exportAttendance.action?groupId="+groupId+"&beginT="+beginT+"&endT="+endT+"&keyWord="+keyword;
	    	document.location.href=url;
	    });
	    //查询
	    $("#searchRecords").click(function(){
	    	$.removeTableCookie('SpryMedia_DataTables_userTable_attendanceTj.jsp');
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
				$.removeTableCookie('SpryMedia_DataTables_userTable_attendanceTj.jsp');
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
	     $.removeTableCookie('SpryMedia_DataTables_userTable_attendanceTj.jsp');
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
	$('#userTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			
			var keyword = $.trim($("#keyword").val());
			if (keyword == "") {
				keyword = null;
			}
			var groupId = $.trim($("#groupId").val());
			if (groupId == "") {
				groupId = null;
			}
			var beginT=$.trim($("#startT").val());
			if(startT==""){
				startT=null;
			}
			var endT=$.trim($("#endT").val());
			if(endT==""){
				endT=null;
			}
			
			aoData.push({
						"name" : "keyword",
						"value" : keyword
					},  {
						"name" : "groupId",
						"value" : groupId
					},  {
						"name" : "beginT",
						"value" : beginT
					},  {
						"name" : "endT",
						"value" : endT
					});
		},
		 
		"sAjaxSource" : basePath + "attendance/listRecords.action",// 获取人员列表
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
					"mDataProp" : "deptName",
					"sClass" : "data_l"
				},{
					"mDataProp" : "job",
					"sClass" : "data_l"
				}, {
					"mDataProp" : "counts",
					"sClass" : "data_r"
				},{
					"mDataProp" : null,
					"sClass" : "right_bdr0"
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
 //          window.parent.frameResize();
		},
		"aoColumnDefs" : [{
			"aTargets" : [5],
			"fnRender" : function(oObj) {
				var userId = oObj.aData.userId;
				return "<a style=\"cursor:pointer;\"  onclick=\"details('"+userId+"');\"  >查看</a>";
						
			}
		}]
	});
}

//进入打卡详情
function details(userId){
	var beginT=$("#startT").val();
	var endT=$("#endT").val(); 
	var detailsUrl = basePath + "logined/attendance/attendanceRecord.jsp?userId="+userId+"&beginT="+beginT+"&endT="+endT;
//	window.open(detailsUrl);
	art.dialog.open(detailsUrl, {
	    id : "details",
	    title : "打卡记录详情",
	    width : 1050,
	    height : 600,
	    lock : true,
	    drag:true,
	    opacity: 0.08,// 透明度
	    button:[{
	    	name:"关闭",
	    	callback:function(){
	    		
	    	}
	    }]
	    /*close : function(){
	    	window.location.href=basePath+"logined/attendance/attendanceTj.jsp";
	    	return true;
	    },
	    cancel : function(){
	    	return true;
	    }*/
	});
}