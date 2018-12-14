/**
 * 列表
 */
$(document).ready(function() {
		
	getGroutName();
//	if(!setNotifyType()){
//		viewList();
//	}
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			viewList();
			return false;
		}
	});
	$("#searchButton").click(function(){
		viewList();
	});
		
});

/**
 * 获取未读公告列表
 * 
 * @return
 */
function viewList() {
	$('#myTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		"bStateSave" : false, // 状态保存
		"bDestroy" : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
				"name" : "groupId",
				"value" : $.trim($("#groupId").val())
			},{
				"name" : "subject",
				"value" : $.trim($("#subject").val())
			},{
				"name" : "notifyType",
				"value" : $.trim($("#notifyType").val())
			},{
				"name" : "columnId",
				"value" : $.trim($("#columnId").val())
			});
		},
		"sAjaxSource" : basePath + "notify/clmViewList.action",
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 20, // 每页显示多少行
		"aoColumns" : [{	
					"mDataProp" : "SUBJECT",
					"sClass" : "longTxt"
				},{	
					"mDataProp" : "TYPENAME"
				},{		
					"mDataProp" : "USERNAME"
				},{
					"mDataProp" : "APPROVEDATE"
				},{
					"mDataProp" : "TOTALCOUNT",
					"sClass" : 	"right_bdr0 data_r"
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"aoColumnDefs" :[{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var isTop = oObj.aData.ISTOP;
				var subject = oObj.aData.SUBJECT;
				var columnId =$("#columnId").val();
				var counting = oObj.aData.COUNTING;
				var fontWeight = "";
				if(counting == 0){
					fontWeight = "font-weight:bold;";
				}
				if(isTop==1){
					return '<a title="'+subject+'" style="text-decoration:none;color:#04578D;text-align:left;'+fontWeight+'"  href="'+ basePath+ 'logined/column/view.jsp?notifyId='+ oObj.aData.NOTIFYID + '&columnId='+columnId+'&returnType=1"><font color="red"><b>'+"[置顶]&nbsp;&nbsp;</b></font>"+subject + '</a>';
				}else{
					return '<a title="'+subject+'" style="text-decoration:none;color:#04578D;text-align:left;'+fontWeight+'"  href="'+ basePath+ 'logined/column/view.jsp?notifyId='+ oObj.aData.NOTIFYID+ '&columnId='+columnId+'&returnType=1">'+ subject+ '</a>';
				}
			}            
		},{
			"aTargets" : [1],
			"fnRender" : function(oObj) {
				var typename = oObj.aData.TYPENAME;
				if(typename==null){
					return "--";
				}else{
					return typename;
				}
			}
		}]
	});
}
/**
 * 初始公告类型
 * 
 * @return
 */
function setNotifyType() {
	var paramData = {
		'infoType' : "notifyType"+$("#columnId").val(),
		'sysTag' : 1
	};
	$.ajax({
		url : basePath + "dict/getDicts.action",
		type : "post",
		dataType : "html",
		data : paramData,
		success : function(data) {
			jsonData = eval("(" + data + ")");
			for (var i = 0; i < jsonData.length; i++) {
				$("#notifyType").append("<option value='"
						+ jsonData[i].id + "'>" + jsonData[i].name
						+ "</option>");
			}
		}
	});
}

//获得部门列表
function getGroutName(){
	$.ajax({
		url : basePath + "notify/getGroupColumn.action",
		type : "post",
		dataType : "html",
		success : function(data) {
			var list = eval("(" + data + ")");
				var groupHtml = " <div class=\"menu-cont\">";
				$(list).each(function(i,groupInfo){
					var str = groupInfo.groupName;
					var name = "";
					if(str.length > 7){
						name = str.substring(0,7) + "...";
					}else{
						name = str;
					}
					if(i == 0){
						 refresh(groupInfo.groupId);
					}
					groupHtml+="<p title="+str+" onclick=\"refresh("+groupInfo.groupId+")\" class=\"menu-p\"><i class=\"menu-i\"></i><a href=\"javascript:void()\" >"+name+"</a></p>";
				});
				groupHtml+="</div>";
				$("#groutName").append(groupHtml);
		}
	});
}
function refresh(groupId){
	$("#groupId").val(groupId);
	viewList();
};