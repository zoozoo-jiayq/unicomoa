jQuery(document).ready(function() {
			initFileSortTree();
			setTimeout("firstCheck()",1000); 

		});

function firstCheck(){
	zTree_Menu = $.fn.zTree.getZTreeObj("fileContentTree");
	curMenu = zTree_Menu.getNodes()[0];
	zTree_Menu.selectNode(curMenu);
	var a = $("#" + zTree_Menu.getNodes()[0].tId + "_a");
	//update by jiayq
	if($("#" + zTree_Menu.getNodes()[0].children[0].tId + "_a")){
		a = $("#" + zTree_Menu.getNodes()[0].children[0].tId + "_a");
	}
	a.addClass("cur");
	
}
function treeNodeClick(name, vid, pid, parid, path) {
		// document.getElementById("newFileContent").value=vid;
		document.getElementById("fileSortId").value = vid;
		document.getElementById("path").value = path;
		name = encodeURI(name);
		 var  fileTypeSort= $("#fileTypeSort").val();
		 if(vid<0){
			 document.getElementById("page").src = basePath+  "logined/file/firstFile.jsp";
		 }else{
			document.getElementById("page").src = basePath
					+ "logined/file/viewFileContentMain.jsp?fileSortId=" + vid + "&path="
					+ path + "&name=" + name+"&type="+type;
			getFSortById(vid);
		 }
}

/**
 * 初始化文件夹类
 */
function initFileSortTree() {
	var fileSortType = $("#fileSortType").val();
	var url = basePath + "file/fileContent.action?fileSortType="+fileSortType+"&type="+type;

	qytx.app.ajax({
				url : url,
				type : 'post',
				dataType : 'json',

				success : function(data) {
					if (null == data || data.length == 0){
						var redictFirstfile = basePath + 'logined/file/firstFile.jsp?size=0';
						$("#page").attr('src', redictFirstfile);
					}else{
						//var redictFirstfile = basePath + 'logined/file/firstFile.jsp';
						var redictFirstfile = basePath
						+ "logined/file/viewFileContentMain.jsp?fileSortId=0&type="+type  ;
						$("#page").attr('src', redictFirstfile);
					}
					qytx.app.tree.base({
						id	:	"fileContentTree",
						data:	data,
						click:	function(nodes){
							onTreeNodeClick(nodes);
						}
					});
				}
			});

}

function onTreeNodeClick(nodes) {
	if(nodes&&nodes.length > 0){
		var name = nodes[0].name;
		var vid = nodes[0].id;
		var pId = nodes[0].pId;
		var parid = nodes[0].arr[0];
		var path = nodes[0].arr[1];

		treeNodeClick(name, vid, pId, parid, path);
	}

}

/**
 * 得到子文件夹信息
 */
function getFSortById(fileSortId) {

	var dataParam = {

		'fileSort.sortId' : fileSortId

	};

	qytx.app.ajax({
				type : 'post',
				url : basePath + "file/getFSortById.action",
				data : dataParam,
				dataType : 'json',
				success : function(data) {
						$("#sortNo").val(data.sortNo);
						$("#sortName").val(data.sortName);

				}
			});

}

