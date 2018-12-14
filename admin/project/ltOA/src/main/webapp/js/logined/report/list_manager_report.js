var _reportIds=""; //报表ID
var _roleType=""; //权限类型 1 单个授权 2 批量授权
var userIdMap = new Map(); //存放权限ID
var userNameMap = new Map();

$(document).ready(function() {
	$.removeTableCookie('SpryMedia_DataTables_dataTable_report_list_manager_report.jsp');
	getList();
	// 保存按钮绑定事件
	$("#search").bind("click", function() {
		$.removeTableCookie('SpryMedia_DataTables_dataTable_report_list_manager_report.jsp');
				getList();
	 });

	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			$.removeTableCookie('SpryMedia_DataTables_dataTable_report_list_manager_report.jsp');
			getList();
			return false;
		}
	});
	setFormType();
	//头部全选复选框
	$("#dataTable_report").delegate("#total", "click", function(event){    
	   	checkTotal();
		event.stopPropagation();
    });
	
	// 子项复选框按钮
	$("#dataTable_report").delegate(":checkbox[name='chk']", "click", function(event) {
		checkChange();
		event.stopPropagation();
	});
	
	/**
	 * 批量授权
	 */
	$("#plsq").click(function(){
		editRoleMore();
	});
	
});

 
/**
 * 查询日志获取列表
 */
function getList() {
	$('#dataTable_report').dataTable({
		"bDestroy" : true,
		"bProcessing" : true,
		"bStateSave" : true, // 状态保存
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
								var reportCategory= $("#reportCategory").val();
								var reportType=$("#reportType").val();
								var reportName=$.trim($("#reportName").val());
								aoData.push({
												"name" : "reportInfo.reportName",
												"value" : reportName
											}, {
												"name" : "reportInfo.reportType",
												"value" : reportType
											}, {
												"name" : "reportInfo.reportCategory",
												"value" : reportCategory
											});
							},
		"sAjaxSource" : basePath
				+ "reportInfo/reportInfoList.action",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [  
		                 {"sTitle":"<input type='checkbox' id='total'/>", "mDataProp": null },
		                 {"sTitle" : '报表类型 ',"mDataProp" : "reportType"}, 
		                 {"sTitle" : '报表分类',"mDataProp" : "reportCategory","sClass" : "longTxt"},
		                 {"sTitle" : '报表名称',"mDataProp" : "reportName","sClass" : "longTxt"},
		                 {"sTitle" : '授权用户',"mDataProp" : "roleNames","sClass" : "longTxt"},
		                 {"sTitle" : '操作',"mDataProp" : null,"sClass":"oper"}
		          ],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			 $('#dataTable_report tbody  tr td[class="longTxt"]').each(function() {
  				this.setAttribute('title', $(this).text());
  			});
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
			//window.parent.frameResize();
		},
		"aoColumnDefs" : [{
            "aTargets": [0],//覆盖第一列
            "fnRender": function ( oObj ) {
            	 var id = oObj.aData.id;
           		 return '<input name="chk" value="' + id+ '"    type="checkbox" />';
            }
        },{
			"aTargets" : [5],
			"fnRender" : function(oObj) {
				     var id = oObj.aData.id;
				     var userIds = oObj.aData.roleIds;
					 var userNames=oObj.aData.roleNames;
					 userIdMap.put(id,userIds);
					 userNameMap.put(id,userNames);
				     var href="<a style='cursor:pointer;' onClick=\"editRole("+id+")\" >授权</a>";
				     return href;
			   }
		  }

		]
	});
}


/**
 * 设置权限
 * @param id
 */
function editRole(id){
	var userIds =userIdMap.get(id);
	var userNames =userNameMap.get(id);
	$("#userIds").val(userIds);
	$("#userNames").val(userNames);
	_roleType=1;//单个授权
	selectPerson();
	_reportIds = id;
}

/**
 * 批量授权
 */
function editRoleMore(){
	  //获取选择公告id
	var chkbox = document.getElementsByName("chk");
	var ids = "";
    var selLen = 0;
    for (var i = 0; i < chkbox.length; i++) {
        if (chkbox[i].checked) {
        	ids += chkbox[i].value+',';
            selLen++;
        }
    }
    if (selLen == 0) {
    	art.dialog.alert(sprintf("reportSet.msg_set_report_at_least_one"));
    	return;
    }else{
    	$("#userIds").val("");
    	$("#userNames").val("");
    	_reportIds=ids;
    	_roleType=2;//批量授权
    	selectPerson();
    }
	
	
}


/***
* 获取表单类别
**/
function setFormType(){
	var paramData={
			'type':21
	};
	$.ajax({
	      url:basePath+"workflowForm/findAllCategory.action",
	      type:"post",
	      dataType: "json",
	      data:paramData,
	      success: function(data){
				var jsonData = data.fcList; 
				$("#reportCategory").empty();
				$("#reportCategory").append("<option value='0'>请选择</option>");
			    for(var i=0;i<jsonData.length;i++){  
			         $("#reportCategory").append("<option value='"+jsonData[i].categoryId+"'>"+jsonData[i].categoryName+"</option>"); 
			     }
	    	}
	 }); 
 }



 
/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck=$("input:checkbox[id='total']").prop("checked");
	var checkNum=0;
	if(isTotalCheck){
		$("input:checkbox[name='chk']").prop("checked", function( i, val ) {
			checkNum=checkNum+1;
            return true;
        });
	}else{
		$("input:checkbox[name='chk']").prop("checked", false);
	}
}

/**
 * 子项复选框变更
 */
function checkChange(){
	if ($('#dataTable_report :checkbox[name="chk"][checked="checked"]').length ==
		$('#dataTable_report :checkbox[name="chk"]').length){
		$("input:checkbox[id='total']").prop("checked", true);
	}else{
		$("input:checkbox[id='total']").prop("checked", false);
	}
}

 
