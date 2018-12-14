$(document).ready(function() {
	$.removeTableCookie('SpryMedia_DataTables_dataTable_report_projectBDList.jsp');
	getList();
	// 保存按钮绑定事件
	$("#search").bind("click", function() {
		$.removeTableCookie('SpryMedia_DataTables_dataTable_report_projectBDList.jsp');
				getList();
	 });

	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			$.removeTableCookie('SpryMedia_DataTables_dataTable_report_projectBDList.jsp');
			getList();
			return false;
		}
	});
	//头部全选复选框
	$("#dataTable_report").delegate("#total", "click", function(event){    
	   	checkTotal();
		event.stopPropagation();
    });
	
	/**
	 * 新增
	 */
	$("#add").click(function(){
		addProject();
		return false;
	});
	
	/**
	 * 批量删除
	 */
	$("#deleteMany").click(function(){
		deleteManyProject();
		return false;
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
								var projectName=$.trim($("#projectName").val());
								var isDelete = $("#isDelete").val();
								if(isDelete == '-1'){
									isDelete=null;
								}
								aoData.push({
												"name" : "reportProjectDic.projectName",
												"value" : projectName
											},{
												"name" : "reportProjectDic.isDelete",
												"value" : isDelete
											});
							},
		"sAjaxSource" : basePath
				+ "reportBD/projectBDList.action",
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
		                 {"sTitle" : '项目名称',"mDataProp" : "projectName","sClass" : "longTxt"}, 
		                 {"sTitle" : '创建时间',"mDataProp" : "createTime"},
		                 {"sTitle" : '创建人',"mDataProp" : "creater"},
		                 {"sTitle" : '状态',"mDataProp" : null},
		                 {"sTitle" : '备注',"mDataProp" : "note","sClass" : "longTxt"},
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
            	 var isExistSection=oObj.aData.isExistSection;
            	 if(isExistSection=="1"){
            		 return '<input name="chk" value="' + id+ '"    type="checkbox" disabled="disabled"/>';
            	 }else{
            		 return '<input name="chk" value="' + id+ '"    type="checkbox"/>';
            	 }
           		 
            }
        },{
			"aTargets" : [4],
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
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				     var id = oObj.aData.id;
				     //标段维护  置为失效 删除
				     var isExistSection=oObj.aData.isExistSection;
				     var isDelete = oObj.aData.isDelete;
				     var sectionView = "";
				     var sectionInfo = "";
				     var isOpenInfo = "";
				     var deletePrj = "";
				     var updateInfo = "";
				     if(isExistSection=='1'&&isDelete=='0'){
				    	 sectionInfo = '<a href='+basePath+'logined/report/sectionBDList.jsp?projectId='+id+'>标段维护</a>';
				    	 isOpenInfo = '<a onclick="updateState('+id+',1)" style="cursor:pointer;">置为无效</a>';
				     }else if(isExistSection=='1'&&isDelete=='1'){
				    	 sectionInfo = '<a href='+basePath+'logined/report/sectionBDList.jsp?projectId='+id+'>标段维护</a>';
				    	 isOpenInfo = '<a onclick="updateState('+id+',0)" style="cursor:pointer;">置为有效</a>';
				     }else if(isExistSection=='2'&&isDelete=='0'){
				    	 sectionInfo = '<a href='+basePath+'logined/report/sectionBDList.jsp?projectId='+id+'>标段维护</a>';
				    	 isOpenInfo = '<a onclick="updateState('+id+',1)" style="cursor:pointer;">置为无效</a>';
				    	 deletePrj = '<a onclick="deletePrj('+id+')" style="cursor:pointer;">删除</a>';
				     }else if(isExistSection=='2'&&isDelete=='1'){
				    	 sectionInfo = '<a href='+basePath+'logined/report/sectionBDList.jsp?projectId='+id+'>标段维护</a>';
				    	 isOpenInfo = '<a onclick="updateState('+id+',0)" style="cursor:pointer;">置为有效</a>';
				    	 deletePrj = '<a onclick="deletePrj('+id+')" style="cursor:pointer;">删除</a>';
				     }
				     updateInfo = '<a onclick="updateProject('+id+')" style="cursor:pointer;">编辑</a>';
				     return sectionView+sectionInfo+isOpenInfo+updateInfo+deletePrj;
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
				if (!$(this).prop("disabled")) {
					checkNum=checkNum+1;
					return true;
				}
        });
	}else{
		$("input:checkbox[name='chk']").prop("checked", false);
	}
}
/**改变状态*/
function updateState(projectId,state){
	var isOpenInfo = "";
	if(state == '0'){
		isOpenInfo="确认置为有效吗?"
	}else{
		isOpenInfo="确认置为无效吗?"
	}
	art.dialog.confirm(isOpenInfo,function(){
		var urlStr = basePath+"reportBD/updateState.action";
		var paramData = {
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
						var url = basePath+"logined/report/projectBDList.jsp";
						win.document.location = url;
					});
					
				}else{
					art.dialog.alert("操作失败！");
				}
		}
		});
		
	});
}

function deletePrj(projectId){
	art.dialog.confirm("确认删除吗？",function(){
		var urlStr = basePath+"reportBD/deleteProject.action?projectId="+projectId;
		$.ajax({
			url:urlStr,
			type:"post",
			dataType:"html",
			success:function(data){
				if(data == 0){
					art.dialog.alert("删除成功！",function(){
						var win = art.dialog.open.origin;// 来源页
						var url = basePath+"logined/report/projectBDList.jsp";
						win.document.location = url;
					});
					
				}else{
					art.dialog.alert("删除失败！");
				}
		}
		});
		
	});
}

function addProject(){
	var url = basePath + "logined/report/addProject.jsp";
	art.dialog.open(url,{
		id:'addProject',
		title:'添加项目',
		width:600,
		height:270,
		init:function(){
	},
		close:function(){
	},
	lock:true,
	opacity:0.3
	});
}

function updateProject(projectId){
	var  url = basePath+"logined/report/updateProject.jsp?projectId="+projectId;
	art.dialog.open(url,{
		id:'updateProject',
		title:'更新项目',
		width:600,
		height:270,
		init:function(){
	},
		close:function(){
	},
	lock:true,
	opacity:0.3
	});
}
/**批量删除*/
function deleteManyProject(){
	var chk = $("input[name='chk']:checked");
	var projectIds="";
	chk.each(function(i,value){
		projectIds+=this.value+",";
	});
	if(projectIds==""){
		art.dialog.alert("至少选择一项");
		return false;
	}
	art.dialog.confirm("确认删除吗？",function(){
		var urlStr = basePath+"reportBD/deleteManyProject.action?projectIds="+projectIds;
		$.ajax({
			url:urlStr,
			type:"post",
			dataType:"html",
			success:function(data){
				if(data == 0){
					art.dialog.alert("删除成功！",function(){
						var win = art.dialog.open.origin;// 来源页
						var url = basePath+"logined/report/projectBDList.jsp";
						win.document.location = url;
					});
					
				}else{
					art.dialog.alert("删除失败！");
				}
		}
		});
		
	});
}
