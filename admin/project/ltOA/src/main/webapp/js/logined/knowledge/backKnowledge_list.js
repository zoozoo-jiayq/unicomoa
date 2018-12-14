
jQuery(document).ready(function() {
	var num=$("#numType").val();
	if(num==1){
		//隐藏增删改
		$("#buttonType").addClass('hide');
		$("#add").addClass('hide');
	}else{
		$("#buttonType").removeClass('hide');
		$("#add").removeClass('hide');
	}
	
	setTimeout(function(){
		getTable();
	}, 100);
	/**
	 * 添加
	 */
    $("#addType").click(
	 function(){
		 openAddType();
	 }
	 );
	
   /**
    * 修改
    */
	 $("#updateType").click(function(){
		
		 openUpdateType();
	 });
	 
	 /**
	  * 删除
	  */
	 $("#deleteType").click(function(){
		 deleteType();
       
	 });
	 
	 /**
	  * 点击查询
	  * 
	  */
	 $("#searchBtn").click(function(){
		 $.removeTableCookie('SpryMedia_DataTables_knowlegeTable_backKnowledge_list.jsp');
		 getTable();
	 });
	 
	 /**
	  * 
	  * 添加知识库
	  */
	 $("#add").click(function(){
		
		 var isBeforeOrAfter = $("#isBeforeOrAfter").val();
		 var treeNodeId = $("#treeNodeId").val();
		 location.href=basePath+"logined/knowledge/addKnowledge.jsp?columnId="+$("#columnId").val()+"&isBeforeOrAfter="+isBeforeOrAfter+"&treeNodeId="+treeNodeId;
	 });
	 
	 
	 
	  	 
	$("html").die().live("keydown",function(event){    
		        if(event.keyCode==13){
		        	$.removeTableCookie('SpryMedia_DataTables_knowlegeTable_backKnowledge_list.jsp');
		        	getTable();    
		         }    
		    }); 
	 
});



function openUpdateType(){
	//var typeName = $("#typeName").val();
	var typeId = $("#typeId").val();
	//var orderIndex=$("#orderIndex").val();
//	alert(typeId);
	if(typeId==0){
		art.dialog.alert("请选择修改类型!");
		return null;
	}
   // typeName=encodeURIComponent(encodeURIComponent(typeName));
    
	var url = basePath + "knowledge/knowledgeType_toUpdateKnowledgeType.action?knowledgeType.vid="+typeId+"&isPrivate=1";
	art.dialog.open(url, {
				id : 'addAttach3',
				title : '修改分类',
				width : 600,
				height : 300,
				lock : false,
			    opacity: 0.08,
				// 在open()方法中，init会等待iframe加载完毕后执行
				init : function() {
				// var iframe = this.iframe.contentWindow;
				},close:function()
				{
					art.dialog.close();
//					$("#typeId").val("");
					$("#parentId").val("0");
					openSelectTreeUser(zTreeOnCheckResult);
					 
				},
				button : [
				              {
									name : '确定',
									focus: true,
									disabled: false,
									callback : function() {
										var iframe = this.iframe.contentWindow;
										iframe.updateType();
//										$("#typeId").val("");
										return false;
									}
					              }, 
					              {
									name : '取消',
									callback : function() {
											art.dialog().close();
//											$("#typeId").val("");
										}
					              }
					              ]
			});
}



function openAddType(){
	var parentId= $("#parentId").val();
	if(parentId==0){
		art.dialog.alert("请选择父级分类！");
		return ;
	}
	var url = basePath + "logined/knowledge/addKnowledgeType.jsp?columnId="+$("#columnId").val()+"&parentId="+parentId+"&isPrivate=0";
	art.dialog.open(url, {
				id : 'addAttach2',
				title : '新增分类',
				width : 600,
				height : 300,
				lock : true,
			    opacity: 0.08,
				// 在open()方法中，init会等待iframe加载完毕后执行
				init : function() {
				// var iframe = this.iframe.contentWindow;
				},close:function()
				{
					art.dialog.close();
					openSelectTreeUser(zTreeOnCheckResult);
//					$("#typeId").val("");
					$("#parentId").val("0");
				}, button : [
				              {
									name : '确定',
									focus: true,
									disabled: false,
									callback : function() {
										var iframe = this.iframe.contentWindow;
										iframe.addType();
										return false;
									}
					              }, 
					              {
									name : '取消',
									callback : function() {
											art.dialog().close();
//											$("#typeId").val("");
											$("#parentId").val("0");
										}
					              }
					              ]
			});
}




function deleteType(){
	var isAdminInput = $("#isAdminInput").val();
//	if ("1" != isAdminInput){
	if ("1" == isAdminInput){ //==1表示 不是超级管理员
		art.dialog.alert("权限不足,请联系管理员！");
		return ;
	}
	
	var typeId = $("#typeId").val();
	//alert(typeId);
	if(typeId==0){
		art.dialog.alert("请选择需要删除的选项！");
		return ;
	}
	var paramData = {
		'vid' : typeId,
		'columnId':$("#columnId").val()
	};
	art.dialog.confirm('确定删除吗？', function() {
				$.ajax({
							url : basePath + "knowledge/knowledgeType_deleteType.action",
							type : "post",
							dataType : "html",
							width: '600',    // 内容宽度
							height: '400',    // 内容高度
							data : paramData,
							success : function(data) {
								if (data == 0) {
									openSelectTreeUser(zTreeOnCheckResult); 
									$("#parentId").val("0");
								    $("#typeId").val("");
								    $("#treeNodeId").val("");
								} else if(data==1) {
									art.dialog.alert("包含子类不能删除！");

								}else if(data==2){
									if($("#columnId").val()==46){
										art.dialog.alert("包含车站明细不能删除！");
									}else{
										art.dialog.alert("包含规章制度不能删除！");
									}
								}else if(data==3){
									art.dialog.alert("权限不够！");
								}else if(data==4){
									art.dialog.alert("顶级类型不能删除！");
								}
								 else {
									art.dialog.alert("删除失败！");

								}
							}
						});
			}, function() {
				return;
			});
	

	
}



function getTable(){
	var typeId = $("#typeId").val();
	var search = $("#searchkey").val();
	search = search.replace(/;/g,"；");
	var auditSign=$("#auditSign").val();
	$('#knowlegeTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		 'bStateSave' : true, // 状态保存
	   
		'fnServerParams' : function(aoData) {
			aoData.push({
						"name" : "knowledge.knowledgeType.vid",
						"value" : typeId
					}, {
						"name" : "knowledge.search",
						"value" : search
					},
					{
						"name" : "knowledge.auditSign",
						"value" :auditSign
					},{
						"name":"isPrivate",
						"value":1
					},{
						"name":"knowledge.columnId",
						"value":$("#columnId").val()
					}
				);
		},
		"sAjaxSource" : basePath + "knowledge/knowledge_getTable.action",// 获取管理员列表
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
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [
			{
					"sTitle" : '序号',
					"mDataProp" : "orderNumber",
				}, {
					"sTitle" : '标题',
					"mDataProp" : "title",
				    "sClass" : "longTxt"
				}, {
					"sTitle" : '关键字',
					"mDataProp" : "keyword",
					"sClass" : "longTxt"
				}, {
					"sTitle" : '录入人',
					"mDataProp" :"userName"
				}, {
					"sTitle" : '创建时间',
					"mDataProp" : "auditTime"
				},{
					"sTitle" : '操作',
					"mDataProp" : null
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			$('#knowlegeTable tbody  tr td').each(function() {
				this.setAttribute('title', $(this).text());
			});
              
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
		
		},
		"aoColumnDefs" : [{
			"aTargets" : [1],
			"fnRender" : function(oObj) {
				var vid = oObj.aData.vid;
				
				var t= oObj.aData.title;
				var columnId = $("#columnId").val();
				return '<a href="'+basePath+'knowledge/knowledge_toViewOrUpdate.action?columnId='+columnId+'&isToView=0&knowledge.vid='+vid+'">'+t+'</a>';
			
			}
		},{
			"aTargets" : [5],
			"fnRender" : function(oObj) {
				var num=$("#numType").val();
                var vid = oObj.aData.vid;
                var columnId = $("#columnId").val();
                if(num!=1){
                	var isAdminInput = $("#isAdminInput").val();
//                	alert(isAdminInput);
                	if ("1" !== isAdminInput){
                		return '<a href="' + basePath+ 'knowledge/knowledge_toViewOrUpdate.action?columnId='+columnId+'&isToView=0&knowledge.vid='+vid+'">查看</a>'+
                		'<a href="' + basePath+ 'knowledge/knowledge_toViewOrUpdate.action?columnId='+columnId+'&isToView=1&knowledge.vid='+vid+'">修改</a>'+
                		'<a href="javascript:void(0);" onclick="deleteKnowledge('+vid+')">删除</a>';
                	}
                }
				return '<a href="' + basePath+ 'knowledge/knowledge_toViewOrUpdate.action?columnId='+columnId+'&isToView=0&knowledge.vid='+vid+'">查看</a>';		
			}
		}]
	});
}

function deleteKnowledge(vid){
	var paramData = {
		'knowledge.vid' : vid
	};
	
	art.dialog.confirm('确定删除吗？', function() {
				$.ajax({
							url : basePath + "knowledge/knowledge_delete.action",
							type : "post",
							dataType : "html",
							data : paramData,
							success : function(data) {
					
								if (data == 0) {
//									art.dialog.alert('删除成功！',function(){
										getTable();
										art.dialog.close();
								} else if(data==3){
									art.dialog.alert("删除失败！您的权限不够！");
								}
								 else {
									art.dialog.alert("删除失败！");

								}
							}
						});
			}, function() {
				return;
			});
}

/**
 * 为了满足这个功能
 * 在本页面无意义
 * @return
 */
function showGroup(){
	
}
/**
 * 点击上传
 */
function onclickOpload(){
	var typeId = $("#typeId").val();
	uploadKnowledge(typeId);
	return false;
}

