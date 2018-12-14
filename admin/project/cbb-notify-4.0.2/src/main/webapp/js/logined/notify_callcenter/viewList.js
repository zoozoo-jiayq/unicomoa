/**
 * 鍒楄〃
 */
$(document).ready(function() {
		if(!setNotifyType()){
			viewList();
		}
		//鍥炶溅浜嬩欢
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
})

/**
 * 鑾峰彇鏈鍏憡鍒楄〃
 * 
 * @return
 */
function viewList() {
	$('#myTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		"bStateSave" : false, // 鐘舵�淇濆瓨
		"bDestroy" : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
				"name" : "subject",
				"value" : $.trim($("#subject").val())
			},{
				"name" : "notifyType",
				"value" : $.trim($("#notifyType").val())
			},{
				"name" : "columnId",
				"value" : $.trim($("#columnId").val())
			},{
				"name" : "isShowOut",
				"value" : 1
			});
		},
		"sAjaxSource" : basePath + "notify/notify_viewList.action",
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 缈婚〉鍔熻兘
		"bLengthChange" : false, // 鏀瑰彉姣忛〉鏄剧ず鏁版嵁鏁伴噺
		"bFilter" : false, // 杩囨护鍔熻兘
		"bSort" : false, // 鎺掑簭鍔熻兘
		"bInfo" : true,// 椤佃剼淇℃伅
		"bAutoWidth" : false,// 鑷姩瀹藉害
		"iDisplayLength" : 15, // 姣忛〉鏄剧ず澶氬皯琛�
		"aoColumns" : [{	
					"mDataProp" : "subject",
					"sClass" : "longTxt"
				},{	
					"mDataProp" : "typename"
				},{		
					"mDataProp" : "username"
				},{
	
					"mDataProp" : "approveDate"
				},{
					"mDataProp" : "totalCount",
					"sClass" : 	"right_bdr0 data_r"
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 涓枃鍖�
		},
		"aoColumnDefs" :[{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var isTop = oObj.aData.isTop;
				var subject = oObj.aData.subject;
				var columnId =$("#columnId").val();
				var counting = oObj.aData.counting;
				var fontWeight = "";
				if(counting == 0){
					fontWeight = "font-weight:bold;";
				}
				if(isTop==1){
					return '<a title="'+subject+'" style="text-decoration:none;color:#04578D;text-align:left;'+fontWeight+'"  href="'+ basePath+ 'logined/notify/view.jsp?notifyId='+ oObj.aData.notifyId + '&columnId='+columnId+'&returnType=1"><font color="red">[置顶]&nbsp;&nbsp;</font>'+subject + '</a>';
				}else{
					return '<a title="'+subject+'" style="text-decoration:none;color:#04578D;text-align:left;'+fontWeight+'"  href="'+ basePath+ 'logined/notify/view.jsp?notifyId='+ oObj.aData.notifyId+ '&columnId='+columnId+'&returnType=1">'+ subject+ '</a>';
				}
			}            
		},{
			"aTargets" : [1],
			"fnRender" : function(oObj) {
				var typename = oObj.aData.typename;
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
 * 鍒濆鍏憡绫诲瀷
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
								+ jsonData[i].value + "'>" + jsonData[i].name
								+ "</option>");
					}
				}
			});
}
