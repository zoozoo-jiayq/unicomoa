/**
 * 审批
 */
$(document).ready(function() {
	setNotifyType();
	appriveList();
	// 回车事件
	$(document).keydown(function(event) {
		var code = event.which;
		if (code == 13) {
			//getNotAuditingList();
			appriveList();
			return false;
		}
	});
	
	$("input[type='button']").click(function(){
		appriveList();
	});
})
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
			$("#notifyType").empty();
			$("#notifyType").append("<option value=''>全部类型</option>");
			for (var i = 0; i < jsonData.length; i++) {
				$("#notifyType").append("<option value='"
						+ jsonData[i].value + "'>" + jsonData[i].name
						+ "</option>");
			}
		}
	});
}
/**
 * 获取审批列表
 * 
 * @return
 */
function appriveList() {
	$('#myTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		"bStateSave" : false, // 状态保存
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
				"name" : "beginDate",
				"value" : $.trim($("#beginDate").val())
			},{
				"name" : "endDate",
				"value" : $.trim($("#endDate").val())
			}
			,{
				"name" : "status",
				"value" : 2
			});
		},
		"sAjaxSource" : basePath + "notify/notify_viewApproveList.action",
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [
				{
					"mDataProp" : "subject",
					"sClass" : "longTxt"
				},{
					"mDataProp" : "notifyType"
				},{
					"mDataProp" : "username"
				},{
					"mDataProp" : "createDate"
				},{
					"mDataProp" : "browse",
					"sClass" : "data_r"
				},{
					"mDataProp" : null
				}
			,{
					"mDataProp" : null,
					"sClass" : "data_r"
				}
				],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"aoColumnDefs" :[
			{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var id = oObj.aData.id;
				var columnId = $("#columnId").val();
				var subject = oObj.aData.subject;
				var isTop = oObj.aData.isTop;
				var topTemplate = "";
				if(isTop==1){
					topTemplate = '<font color="red"><b>[置顶]&nbsp;&nbsp;</b></font>';
				}
				return topTemplate+'<a  title = "'+subject+'"   href="'+ basePath + 'logined/notify/viewApproveDetails.jsp?notifyId='+ id + '&columnId='+columnId+'&returnType=3">' + subject + '</a>'
			}          
		},{
			"aTargets" : [5],
			"fnRender" : function(oObj) {
				var status = oObj.aData.status;
				if(status==0){
					return "草稿";
				}else if(status==1){
					return "待审核";
				}else if(status==2){
					return "通过";
				}else if(status==3){
					return "<font color=\"red\">不通过</font>"; 
				}else if(status==4){
					return "已终止";
				}
			}            
		},{
			"aTargets":[6],
			"fnRender":function(oObj){
				var columnId = $("#columnId").val();
				var id = oObj.aData.id;
				return '<a href="'+ basePath + 'logined/notify/viewApproveDetails.jsp?notifyId='+ id + '&columnId='+columnId+'&returnType=3">查看</a>'
			}
		}
/*		,{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				var status = oObj.aData.status;
				var isEdit = oObj.aData.isEdit;
				var id = oObj.aData.id;
				var html="";
				html='<a style="cursor:pointer;" title="审核" onclick="auditer('+id+')">审核</a>';
				return html;
				if(status==1 || status==3){
					html='<a style="cursor:pointer;" title="审核" onclick="auditer('+id+')">审核</a>';
//					if(isEdit==1){
//						html+='<a style="text-decoration:none;color:#04578D" title="修改" onclick="modifyNotify('+id+','+status+')">修改</a>';
//					}
					return html;
				}else if(status==2){
					html='<a  style="cursor:pointer;"  title="审核" onclick="auditer('+id+')">审核</a>';
//					if(isEdit==1){
//						html+='<a style="text-decoration:none;color:#04578D;cursor:pointer" title="修改" onclick="modifyNotify('+id+','+status+')">修改</a>';
//					}
			//		html+='<a  style="cursor:pointer;"  title="查阅状态" onclick=rediectCheckPerson('+id+')>查阅状态</a>';
					return html;
				}
			}
		}*/
		]
	});
}

function rediectCheckPerson(id){
//		var columnId = $("#columnId").val();
//		var url = basePath + "logined/notify/viewNotify.jsp?id=" + id+"&columnId="+columnId;
		var columnId = $("#columnId").val();
		var url = basePath + "logined/notify/viewNotify.jsp?id=" + id+"&columnId="+columnId+"&category="+2;
		window.location.href=url;
//		art.dialog.open(url, {
//			id : 'addAttach',
//			title : '查阅情况',
//			 width : 800,
//			 height : 450,
//			lock : true,
//		    opacity: 0.3,
//			init : function() {
//				
//			}
//		});
	}
function getNotAuditingList() {
	var searchWord = $("#searchWord").val();
	if (typeof (nowOption) == "undefined" || nowOption == "undefined") {
		nowOption = 0;
	}
	oTable = $('#myTable')
			.dataTable(
					{
						"bProcessing" : true,
						'bServerSide' : true,
						"bStateSave" : false, // 状态保存
						"bDestroy" : true,
						'fnServerParams' : function(aoData) {
							aoData.push({
								"name" : "searchWord",
								"value" : $.trim($("#searchWord").val())
							}, {
								"name" : "columnId",
								"value" : $.trim($("#columnId").val())
							});
						},
						"sAjaxSource" : basePath
								+ "notify/notify_viewApproveList.action?notifyType="
								+ nowOption,
						"sServerMethod" : "POST",
						"sPaginationType" : "full_numbers",
						"bPaginate" : true, // 翻页功能
						"bLengthChange" : false, // 改变每页显示数据数量
						"bFilter" : false, // 过滤功能
						"bSort" : false, // 排序功能
						"bInfo" : true,// 页脚信息
						"bAutoWidth" : false,// 自动宽度
						"iDisplayLength" : 20, // 每页显示多少行
						"aoColumns" : [ {
							"sTitle" : '标题',
							"mDataProp" : "subject",
							"sClass" : "longTxt",
							"sWidth" : "40%"
						}, {
							"sTitle" : '发布时间',
							"mDataProp" : "createDate",
							"sWidth" : "100px"
						}, {
							"sTitle" : '发布人',
							"mDataProp" : "username",
							"sWidth" : "80px"
						}, {
							"sTitle" : '发布范围',
							"mDataProp" : "publish_user_names",
							"sClass" : "longTxt",
							"sWidth" : "150px"
						}, {
							"sTitle" : '浏览次数',
							"mDataProp" : "totalCount",
							"sWidth" : "50px"
						}, {
							"sTitle" : '操作',
							"mDataProp" : null,
							"sClass" : "oper right_bdr0",
							"sWidth" : "80px"
						} ],
						"oLanguage" : {
							"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
						},
						"aoColumnDefs" : [
								{
									"aTargets" : [ 0 ],
									"fnRender" : function(oObj) {
										var isRecommend = oObj.aData.isRecommend;
										var recommendSign = "";
										var columnId = $("#columnId").val();
										if (isRecommend != null
												&& isRecommend == 1) {
											recommendSign = "<img src='"
													+ basePath
													+ "images/recommend.png'/>";
										}
										return '<a    href="'
												+ basePath
												+ 'logined/notify/view.jsp?notifyId='
												+ oObj.aData.notifyId
												+ '&columnId='
												+ columnId
												+'&returnType=3'
												+ '">'
												+ oObj.aData.subject
												+ '</a>' + recommendSign;
									}
								},
								{
									"aTargets" : [ 5 ],
									"fnRender" : function(oObj) {
										var notifyId = "'"
												+ oObj.aData.notifyId + "'";
										var isCanedit = oObj.aData.isCanedit;
										var optionStr = '<a    href="'
												+ basePath
												+ 'logined/notify/view.jsp?notifyId='
												+ oObj.aData.notifyId
												+ '&columnId='
												+ $("#columnId").val()
												+'&returnType=3'
												+ '">查看</a><a  href="javascript:auditer('
												+ notifyId + ')">审核</a>';
										var modifyOption = '';
//										if (isCanedit == '1') {
//											modifyOption = '<a style="text-decoration:none;color:#04578D;text-align:left;" href="javascript:modifyNotify('
//													+ notifyId + ')">修改</a>';
//										} else if (isCanedit == '0') {
//											modifyOption = '';
//										}
										return modifyOption + optionStr;
									}
								} ]
					});

}

/**
 * 批准操作
 * 
 * @param notifyId
 * @return
 */
function auditer(notifyId) {
	var columnId = $("#columnId").val();
	window.location.href = basePath + 'logined/notify/viewApprove.jsp?notifyId='
			+ notifyId + "&columnId=" + columnId;
}

/**
 * 不批准操作
 * 
 * @param notifyId
 * @return
 */
function notAuditer(notifyId) {
	var columnId = $("#columnId").val();
	window.location.href = basePath
			+ 'logined/notify/viewApproveNo.jsp?notifyId=' + notifyId
			+ "&columnId=" + columnId;
}

/**
 * 修改操作
 * 
 * @param notifyId
 * @return
 */
function modifyNotify(id,status) {
	var columnId = $("#columnId").val();
	window.location.href = basePath
			+ 'logined/notify/viewApproveUpdate.jsp?id=' + id
			+ "&columnId=" + columnId+"&status="+status;
}