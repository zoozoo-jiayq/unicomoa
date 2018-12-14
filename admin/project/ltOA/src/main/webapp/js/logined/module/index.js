jQuery(document).ready(function(){
	list();
	
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			list();
			return false;
		}
	}); 
	
	$("#search").click(function(){
		list();
	});
	
	$("#add").click(function(){
		window.location.href=basePath + "logined/module/viewAdd.jsp";
	});
	
	$("#exportButton").click(function(){
		var searchkey = $("#searchkey").val();
		window.location.href=basePath + "module/download.action?keyword="+searchkey;
	});
	
	$("#importButton").click(function(){
		qytx.app.dialog.openUrl({
			id	:	"importModule",
			url	:	basePath +"logined/module/alertMsg.jsp",
			title:	"手机模块导入",
			size:	"M",
			customButton:[{
							name : '导 入',
							focus:true,
							callback : function() {
								var obj = this.iframe.contentWindow;
								obj.startAjaxFileUpload();
								return false;
							}
						}, {
							name : '取 消',
							callback : function() {
								return true;
							}
						}]
		});
	});
	
	$("a.update").live("click",function(){
		var statue = jQuery(this).text() =="启用"?1:2;
		var id = parseInt(jQuery(this).parent().parent().find("TD:first").text());
		updateStatue(id,statue);
	});
	function updateStatue(id,statue){
		qytx.app.ajax({
			type:"post",
			url:basePath+"module/updateStatue.action",
			data:{"id":id,"statue":statue},
			dataType:"text",
			success:function(result){
				list();
			}
		});
	}
})
function del(id){
	qytx.app.dialog.confirm("确定要删除吗?",function(){
		qytx.app.ajax({
			type:"post",
			url:basePath+"module/del.action",
			data:{"id":id},
			dataType:"text",
			success:function(result){
				list();
			}
		});
	});
	
}

/**
 * 获取公告列表
 * 
 * @return
 */
function list() {
	var searchkey = jQuery("#searchkey").val();
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath + "module/list.action?date="+new Date(),
		bInfo:false,
		selectParam:	{
							"keyword":searchkey
						},
		valuesFn:	[{
						"aTargets": [6],
			            "fnRender": function (oObj){
			            	var img = oObj.aData.icon;
			            	return "<img src=\""+img+"\" style=\"width:50px;height:50px;\"/>";
			            }
					},{
						"aTargets": [7],
			            "fnRender": function (oObj){
			            	var statue = oObj.aData.statue;
			            	if(statue==1){
			            		return "启用";
			            	}else{
			            		return "停用";
			            	}
			            }
					},{
						"aTargets": [8],
			            "fnRender": function (oObj){
			            	var statue = oObj.aData.statue;
			            	var no = oObj.aData.no;
			            	if(statue=="启用"){
			            		return "<a href=\""+basePath+"module/viewUpdate.action?id="+no+"\">修改</a><a href=\"javascript:void(0);\" onclick=\"del("+no+")\">删除</a><a class=\"update\" href=\"javascript:void(0);\">停用</a>";
			            	}else{
			            		return "<a href=\""+basePath+"module/viewUpdate.action?id="+no+"\">修改</a><a href=\"javascript:void(0);\" onclick=\"del("+no+")\">删除</a><a class=\"update\" href=\"javascript:void(0);\" >启用</a>";
			            	}
			            	
			            }
					}]
	});
}
