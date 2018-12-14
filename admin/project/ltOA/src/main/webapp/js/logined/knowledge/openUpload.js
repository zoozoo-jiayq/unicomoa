
//刷新页面
var freshPage = false;
/**
 * 上传
 */

function uploadKnowledge(typeId) {
	var treeNodeId = $("#treeNodeId").val();
	art.dialog.open(basePath +"logined/knowledge/uploadKnowledge.jsp?typeId="+typeId+"&treeNodeId="+treeNodeId,{
		id : 'uploadKnowledgeArchives',
		title : '知识库上传',
		width : 500,
		height : 270,
		opacity: 0.08,
		lock : true,
		close:function(){// 重新加载
			$.removeTableCookie('SpryMedia_DataTables_knowlegeTable_backKnowledge_list.jsp');
			getTable();
		},
		button : [
				{
					name : '导 入',
					callback : function() {
						var iframe = this.iframe.contentWindow;
						iframe.saveUploadKnowledge();
						return false;
					},
					focus:true
				},
				{
					name : '取 消',
					callback : function() {// 重新加载
						setTimeout(function(){
							$.removeTableCookie('SpryMedia_DataTables_knowlegeTable_backKnowledge_list.jsp');
							getTable();
						}, 100);
					}
				}]
	});

}


