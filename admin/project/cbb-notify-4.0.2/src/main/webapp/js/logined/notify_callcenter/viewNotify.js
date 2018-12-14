$(document).ready(function() {
			var notifyId = $("#id").val();
			//获取公告信息
			getNotifyInfo(notifyId);
			//获取已读人员、共人员
			//getMemCount();
			//获取列表信息
			getViewNotifyCheckList();
		});

/**
 * 获取公告
 * @param notifyId
 * @return
 */
function getNotifyInfo(notifyId) {
	dataParam = {
		'id' : notifyId
	};
	$.ajax({
				type : 'post',
				url : basePath + "notify/notify_viewNotify.action",
				data : dataParam,
				dataType : 'json',
				async : false,
				success : function(data) {
					$("#createTimeStr").html("发布时间："+data.approveDate);
					$("#createUserName").text("发布人："+data.username);					
					$("#viewCount").text("浏览次数："+data.viewCount);
					var columnId =$("#columnId").val();
					var cate=$.trim($("#category").val());
					var htmlSubject = '<a href="'+ basePath+ 'logined/notify/view.jsp?notifyId='+ notifyId + '&columnId='+columnId+'&returnType=4&category='+cate+'">'+data.subject+'</a>';
					$("#subject").html(htmlSubject);
					var menText="已读"+data.counting+"人，共"+data.totalCount+"人";
					$("#record font").text(menText);
				}
			});
}

function getViewNotifyCheckList() {
	dataParam = {
			'notifyId' :  $.trim($("#id").val())
		};
		$.ajax({
					type : 'post',
					url : basePath + "notify/view_viewList.action",
					data : dataParam,
					dataType : 'json',
					async : false,
					success : function(data) {
						 if (null != data && "" != data){
							 var aaData = data.aaData;
							 if (null != aaData && aaData.length > 0){
								 var tbody = ""; 
								 for (var i=0; i< aaData.length; i++){
									 if (i % 2 == 0){
										 tbody += '<tr class="odd">'+'<TD class=longTxt title='+ aaData[i].department+'>'+aaData[i].department
										 + '</TD><TD class=longTxt title=' + aaData[i].reader + '>' + aaData[i].reader + '</TD><TD class=longTxt title='
										 + aaData[i].notReader + '>' + aaData[i].notReader + '</TD>';
									 }else{
										 tbody += '<tr class="even">'+'<TD class=longTxt title='+ aaData[i].department+'>'+aaData[i].department
										 + '</TD><TD class=longTxt title=' + aaData[i].reader + '>' + aaData[i].reader + '</TD><TD class=longTxt title='
										 + aaData[i].notReader + '>' + aaData[i].notReader + '</TD>';
									 }
								 }
								 $("#tbodyId").html(tbody);
//								 var tfoot = '<tr>'+'<TD class=longTxt title='+ aaData[aaData.length-1].department+'>'+aaData[aaData.length-1].department
//								 + '</TD><TD class=longTxt title=' + aaData[aaData.length-1].reader + '>' + aaData[aaData.length-1].reader + '</TD><TD class=longTxt title='
//								 + aaData[aaData.length-1].notReader + '>' + aaData[aaData.length-1].notReader + '</TD>';
//								 $("#tfootId").html(tfoot);
							 }
						 }
					}
				});
}

/**
 * 获取未读公告列表
 * 
 * @return
 */
function getViewNotifyCheckList1() {
	if (typeof oTable == 'undefined') {
		oTable = $('#myTable').dataTable({
					"bProcessing" : true,
					'bServerSide' : true,
					'fnServerParams' : function(aoData) {
						aoData.push({
									"name" : "notifyId",
									"value" : $.trim($("#id").val())
								},{
									"name":"columnId",
									"value":$.trim($("#columnId").val())
								});
					},
					"sAjaxSource" : basePath + "notify/view_viewList.action",
					"sServerMethod" : "POST",
					"sPaginationType" : "full_numbers",
					"bPaginate" : false, // 翻页功能
					"bLengthChange" : false, // 改变每页显示数据数量
					"bFilter" : false, // 过滤功能
					"bSort" : false, // 排序功能
					"bInfo" : false,// 页脚信息
					"bAutoWidth" : false,// 自动宽度
					"iDisplayLength" : 15, // 每页显示多少行
					"aoColumns" : [
					        {
								"sTitle" : '部门/成员单位',
								"mDataProp" : "department",
								"sWidth" : "30%",
								"sClass" : "longTxt"
							}, 
							{
								"sTitle" : '已读人员',
								"mDataProp" : "reader",
								"sWidth" : "30%",
								"sClass" : "longTxt"
							}, {
								"sTitle" : '未读人员',
								"mDataProp" : "notReader",
								"sWidth" : "40%",
								"sClass" : "longTxt"
							}],
					"oLanguage" : {
						"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
					},"fnDrawCallback":function(oSettings){   //这个函数是呼吁每一个“画”事件,并允许您动态地修改任何方面你想要创建的DOM
		       			$(".longTxt").each(function(){
		       				this.setAttribute('title', $(this).text());
		       			});
		       			}
				});
	} else {
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayStart = 0;
		oTable.fnClearTable();
	}
}

/**
 * 返回列表
 * @return
 */
function goBack(){
	var id=$("#columnId").val();
	var ca=$.trim($("#category").val());
	if(ca == 1){
		var url=basePath+"logined/notify/list.jsp?id="+id;
		window.location.href=url;
	}
	if(ca == 2){
		var url=basePath+"logined/notify/viewApproveList.jsp?id="+id;
		window.location.href=url;
	}
	
}
