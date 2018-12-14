jQuery(document).ready(function() {
            
			/** 添加 */
//			$("#addChildSort").click(function() {
//						saveChildFile();
//						return false;
//					});

			
			
			initFileSortTree();
			setTimeout("firstCheck()",1000); 
			/** 修改 */
			$("#updateChildFileSortById").click(function() {
						updateFileSortById();
						return false;
					});
		
		/** 删除 */
			$("#deleteChildFileSortById").click(function() {
						delCatorlogById();
						return false;
					});
			
			if($("#fileSortType").val()==1){
				$("#file_top_list").hide();
			}
			
			
			/**添加子文件夹*/
			$("#addChildFileSort").click(function() {
						 addChildFileSort();
						return false;
					});
			
		});
function firstCheck(){
	zTree_Menu = $.fn.zTree.getZTreeObj("fileContentTree");
	curMenu = zTree_Menu.getNodes()[0];
	zTree_Menu.selectNode(curMenu);
	var a = $("#" + zTree_Menu.getNodes()[0].tId + "_a");
	a.addClass("cur");
}
function treeNodeClick(name, vid, pid, parid, path) {

	// document.getElementById("newFileContent").value=vid;
	document.getElementById("fileSortId").value = vid;
	document.getElementById("path").value = path;
	name = encodeURI(name);
	 if(vid<0){
		 document.getElementById("page").src = basePath+  "logined/file/firstFile.jsp&type="+type;
	 }else{
		document.getElementById("page").src = basePath
				+ "logined/file/fileContentMain.jsp?fileSortId=" + vid + "&path="
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
			 
					if (null == data || data == "" || data.length == 0){
						var redictFirstfile = basePath + 'logined/file/firstFile.jsp?size=0&type='+type;
						$("#page").attr('src', redictFirstfile);
					}else{
						//var redictFirstfile = basePath + 'logined/file/firstFile.jsp';
						var redictFirstfile = basePath
						+ "logined/file/fileContentMain.jsp?fileSortId=0&type="+type  ;
						$("#page").attr('src', redictFirstfile);
					}
					
					qytx.app.tree.base({
						id	:	"fileContentTree",
						data:	data,
						click:	function(nodes){
							onTreeNodeClick(nodes);
						}
					});
//					jQuery.fn.zTree.init(jQuery("#fileContentTree"), setting,
//							data);
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
                   
					/*if (typeof(data.sortNo) != 'undefined' || data.sortNo != "") {

					
					}*/
						$("#sortNo").val(data.sortNo);
						$("#sortName").val(data.sortName);

				}
			});

}




/**
 * 更新文件夹信息
 */
function updateFileSortById() {
		var sortNo = $("#sortNo").val();
		var fileSortType = $("#fileSortType").val();
		var sortName = $("#sortName").val();
		var sortId = $("#fileSortId").val();
		var path = $("#path").val();
	if (sortId == "" || sortId == 0) {
		qytx.app.dialog.alert("无权限修改！");
	} else {
		var titleName = "修改文件夹";
		sortName = sortName.replace(new RegExp("#","gm"),"%23"); 
		sortName = sortName.replace(new RegExp("&","gm"),"%26"); 
		qytx.app.dialog.openUrl({
			url	:	basePath + "logined/file/fileAlert.jsp?sortNo="+sortNo+"&sortName="+sortName+"&sortId="+sortId,
			title:	titleName,
			size:	"M",
			customButton:[{name: '确定',
							callback: function() {
								var iframe = this.iframe.contentWindow;
								var sortNo = $(iframe.sortNo).val();
								var sortName = $.trim($(iframe.sortName).val());
								var sortId = $(iframe.sortId).val();
							if(!isNaN(sortNo))
							 {  
							 }else
							 { 
							 qytx.app.dialog.alert("排序号不是数字！");
							 return false;
							 }
								if (sortName == "") {
//									qytx.app.dialog.alert('文件夹名称不能为空！');
									showObjError($(iframe.document).find("#sortName"), 'file.file_not_null');
									return false;
								} else {
									var dataParam = {
										'fileSort.sortNo' : sortNo,
										'fileSort.sortName' : sortName,
										'fileSort.sortId' : sortId,
										'fileSort.path' : path
									};
									qytx.app.ajax({
										type : 'post',
										url : basePath + "file/updateFileSort.action",
										data : dataParam,
										dataType : 'text',
										success : function(data) {
											if (data == 2) {
												qytx.app.dialog.tips("修改成功！", function(){
												 window.location.href = basePath
												 + 'logined/file/fileContent.jsp?fileSort='+fileSortType+'&type='+type;
										         return true;
												 });
											} else if (data == 1) {
												qytx.app.dialog.alert("文件夹已经存在！", function(){
											        return true;
												});
					
											} else {						
												qytx.app.dialog.alert("无权限修改！", function(){
											        return true;
												});
											}
												}
									});
									return true;
								}
							},focus: true},{name: '关闭'}]
		});
	}

}

function delCatorlogById() {
	var fileSortType = $("#fileSortType").val();
	var fileSortId = $("#fileSortId").val();
	if (fileSortId == "") {
		qytx.app.dialog.alert("没有选择任何文件夹！");
	} else {
	var paramData = {
		'fileSort.SortId' : fileSortId
	};
	qytx.app.ajax({
		type : 'post',
		url : basePath + "file/checkChildFileSort.action",
		data : paramData,
		dataType : 'text',
		success : function(data) {

			if (data == 1) {
				qytx.app.dialog.alert("该文件夹下面有子文件夹,不能删除！");
				checkfilesort = 2;
				return;
			} else {
				qytx.app.dialog.confirm('将删除此文件夹下的所有文件,确定要删除吗?',
						function() {
							qytx.app.ajax({
								type : 'post',
								url : basePath + "file/deleteFileSort.action",
								data : paramData,
								dataType : 'text',
								success : function(data) {
									if (data == "") {
//										art.dialog.tips("删除成功！");
										qytx.app.dialog.tips('删除成功！', function(){
//										setTimeout(function(){
											window.location.href = basePath + 'logined/file/fileContent.jsp?fileSort='+fileSortType+'&type='+type;
										});
											
//										});
									} else {
										qytx.app.dialog.alert(data);
									}
								}
							});
						}, function() {
							return;
						});
			}

		}
	});
	}

}

/**
 * 添加子文件夹信息
 */
function addChildFileSort() {
	
	var fileSortId = $("#fileSortId").val();
	if(fileSortId=="")
    {
    	fileSortId=0;
    }
    /*if(fileSortId=="")
    {
    	art.dialog.alert("没有选择任何文件夹");
    	return false;
    }else{*/
	var path = $("#path").val();
	var titleName = "";
	var sortName = "";
	titleName = '新增文件夹';
	
//	sortName = '<div class="elasticFrame formPage"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable" ><tbody><tr><th><em class="requireField">*</em><label>文件夹名称：</label></th><td><input type="text" id="sortName" maxlength="50" class="formText"/></td></tr><tr><th><label>排序号：</label></th><td><input type="text" id="sortNo" maxlength="50" class="formText"/></td></tr></tbody><table></div>';
	qytx.app.dialog.openUrl({
		url	:	basePath + "logined/file/addFileAlert.jsp",
		title:	titleName,
		size:	"M",
		customButton:[{name: '确定',
						callback:function() {
							/***/			
							var iframe = this.iframe.contentWindow;
							var sortNo = $.trim($(iframe.document).find("#sortNo").val());
							var sortName = $.trim($(iframe.document).find("#sortName").val());
							 if(!isNaN(sortNo))
							 {  
							 }else{ 
								 qytx.app.dialog.alert("排序号不是数字！");
								 return false;
							 } 
							if (sortName == "") {
								qytx.app.valid.showObjError($(iframe.document).find("#sortName"), 'file.file_not_null');
								return false;
							} else {
								var fileSortType = $("#fileSortType").val();
								var dataParam = {
									'fileSort.sortNo' : sortNo,
									'fileSort.sortName' : sortName,
									'fileSort.parentId' : fileSortId,
									'fileSort.path' : path,
									'fileSort.type':type,
									'fileSort.sortType':fileSortType
								};
								qytx.app.ajax({
									type : 'post',
									url : basePath + "file/addChildFileSort.action",
									data : dataParam,
									dataType : 'text',
									success : function(data) {
										if (data == 1) {
											qytx.app.dialog.alert("文件夹已经存在！", function(){
										        return true;
											});
										} else {		
											qytx.app.dialog.tips("添加成功！",function(){
												window.location.href = basePath
												+ 'logined/file/fileContent.jsp?fileSort='+fileSortType+'&type='+type;
											});	
										}
									}
								});
								return true;
							}
						},focus: true},{name: '取消'}]
	});
}
