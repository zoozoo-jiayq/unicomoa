$(document).ready(function() {
	getList();
	// 保存按钮绑定事件
	$("#search").bind("click", function() {
		$.removeTableCookie('SpryMedia_DataTables_dataTable_report_sectionBDList.jsp');
				getList();
	 });

	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			$.removeTableCookie('SpryMedia_DataTables_dataTable_report_sectionBDList.jsp');
			getList();
			return false;
		}
	});
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
	 * 新增
	 */
	$("#add").click(function(){
		addSection();
		return false;
	});
	
	/**
	 * 批量删除
	 */
	$("#deleteMany").click(function(){
		deleteManySection();
		return false;
	});
	
	$("#backSup").click(function(){
		window.location.href=basePath+"logined/report/projectBDList.jsp";
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
								var sectionName=$.trim($("#sectionName").val());
								var projectId = $("#projectId").val();
								var isDelete = $("#isDelete").val();
								if(isDelete == '-1'){
									isDelete=null;
								}
								aoData.push({
												"name" : "reportSectionDic.sectionName",
												"value" : sectionName
											},{
												"name" : "reportSectionDic.projectId",
												"value" : projectId
											},{
												"name" : "reportSectionDic.isDelete",
												"value" : isDelete
											});
							},
		"sAjaxSource" : basePath
				+ "reportBD/sectionBDList.action",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 10, // 每页显示多少行
		"aoColumns" : [  
		                 {"sTitle":"<input type='checkbox' id='total'/>", "mDataProp": null },
		                 {"sTitle" : '项目名称',"mDataProp" : "projectName","sClass" : "longTxt"}, 
		                 {"sTitle" : '标段名称',"mDataProp" : "sectionName","sClass" : "longTxt"},
		                 {"sTitle" : '标段编号',"mDataProp" : "sectionNumber","sClass" : "longTxt"},
		                 {"sTitle" : '创建时间',"mDataProp" : "createTime"},
		                 {"sTitle" : '创建人',"mDataProp" : "creater"},
		                 {"sTitle" : '状态',"mDataProp" : null},
		                 {"sTitle" : '备注',"mDataProp" : "note","sClass":"longTxt"},
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
			window.parent.frameResize();
		},
		"aoColumnDefs" : [{
            "aTargets": [0],//覆盖第一列
            "fnRender": function ( oObj ) {
            	 var id = oObj.aData.id;
           		 return '<input name="chk" value="' + id+ '"    type="checkbox" />';
            }
        },{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				     var id = oObj.aData.isDelete;
				     var state = "";
				     if(id == '0'){
				    	 state = "有效";
				     }else{
				    	 state = "无效";
				     }
				     return state;
			   }
		  },{
			"aTargets" : [8],
			"fnRender" : function(oObj) {
				     var id = oObj.aData.id;
				     var projectId = oObj.aData.projectId;
				     //标段维护  置为失效 删除
				     var isDelete = oObj.aData.isDelete;
				     var sectionView = "";
				     var sectionInfo = "";
				     var isOpenInfo = "";
				     var deletePrj = "";
				     if(isDelete=="0"){
				    	 isOpenInfo = '<a onclick="updateState('+id+','+projectId+',1)" style="cursor:pointer;">置为无效</a>';
				     }else{
				    	 isOpenInfo = '<a onclick="updateState('+id+','+projectId+',0)" style="cursor:pointer;">置为有效</a>';
				     }
				     sectionInfo = '<a onclick="updateSection('+id+','+projectId+')" style="cursor:pointer;">编辑</a>';
				     deletePrj = '<a onclick="deleteSection('+id+','+projectId+')" style="cursor:pointer;">删除</a>';
				     return sectionView+isOpenInfo+sectionInfo+deletePrj;
			   }
		  }

		]
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
/**改变状态*/
function updateState(sectionId,projectId,state){
	var isOpenInfo = "";
	if(state == '0'){
		isOpenInfo="确认置为有效吗?"
	}else{
		isOpenInfo="确认置为无效吗?"
	}
	art.dialog.confirm(isOpenInfo,function(){
		var urlStr = basePath+"reportBD/updateSectionState.action";
		var paramData = {
				"sectionId":sectionId,
				"projectId":projectId,
				"isDelete":state
		};
		$.ajax({
			url:urlStr,
			type:"post",
			data:paramData,
			dataType:"html",
			success:function(data){
				if(data == 0){
					art.dialog.alert("操作成功！",function(){
						var win = art.dialog.open.origin;// 来源页
						var url = basePath+"logined/report/sectionBDList.jsp?projectId="+projectId;
						win.document.location = url;
					});
					
				}else{
					art.dialog.alert("操作失败！");
				}
		}
		});
		
	});
}

function deleteSection(sectionId,projectId){
	art.dialog.confirm("确认删除吗？",function(){
		var urlStr = basePath+"reportBD/deleteSection.action?sectionId="+sectionId;
		$.ajax({
			url:urlStr,
			type:"post",
			dataType:"html",
			success:function(data){
				if(data == 0){
					art.dialog.alert("删除成功！",function(){
						var win = art.dialog.open.origin;// 来源页
						var url = basePath+"logined/report/sectionBDList.jsp?projectId="+projectId;
						win.document.location = url;
					});
					
				}else{
					art.dialog.alert("删除失败！");
				}
		}
		});
		
	});
}

function addSection(){
	var projectId = $("#projectId").val();
	var url = basePath + "logined/report/addSection.jsp?projectId="+projectId;
	art.dialog.open(url,{
		id:'addSection',
		title:'添加标段',
		width:580,
		height:400,
		init:function(){
	},
		close:function(){
	},
	lock:true,
	opacity:0.3
	});
}

function updateSection(sectionId,projectId){
	var url = basePath + "logined/report/updateSection.jsp?projectId="+projectId+"&sectionId="+sectionId;
	art.dialog.open(url,{
		id:'updateSection',
		title:'编辑标段',
		width:580,
		height:400,
		init:function(){
	},
		close:function(){
	},
	lock:true,
	opacity:0.3
	});
}

/**批量删除*/
function deleteManySection(){
	var projectId = $("#projectId").val();
	var chk = $("input[name='chk']:checked");
	var sectionIds="";
	chk.each(function(i,value){
		sectionIds+=this.value+",";
	});
	if(sectionIds==""){
		art.dialog.alert("至少选择一项");
		return false;
	}
	art.dialog.confirm("确认删除吗？",function(){
		var urlStr = basePath+"reportBD/deleteManySection.action?sectionIds="+sectionIds;
		$.ajax({
			url:urlStr,
			type:"post",
			dataType:"html",
			success:function(data){
				if(data == 0){
					art.dialog.alert("删除成功！",function(){
						var win = art.dialog.open.origin;// 来源页
						var url = basePath+"logined/report/sectionBDList.jsp?projectId="+projectId;
						win.document.location = url;
					});
					
				}else{
					art.dialog.alert("删除失败！");
				}
		}
		});
		
	});
}
