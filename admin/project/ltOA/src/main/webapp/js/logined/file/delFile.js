jQuery(document).ready(function() {
			/** 选择 */
			$("#delFileContent").click(function() {
						delFile();
						return false;
					});
		});


function delFile() {
	var chkbox = document.getElementsByName("chk");
	var fileIds = _checkedIds;
	if (fileIds.length <= 1) {
		art.dialog.alert('请选择要删除的文件！');
		return;
	}
	fileIds = fileIds.substring(0,fileIds.length-1).replace(/\,/g,"&fileIds=");
	fileIds = "&fileIds="+fileIds;
	
	art.dialog.confirm('确定要删除所选项吗？', function() {
				$.ajax({
							url : basePath + "file/delFile.action",
							type : "post",
							dataType : 'text',
							data : fileIds,
							success : function(data) {
								if (data != "") {
								//	art.dialog.alert('删除成功！');
									_checkedIds="";
									getDataTable();
								} else {
									art.dialog.alert('删除失败！');
									getDataTable();
								}
							}
						});
			}, function(){art.dialog.tips('你取消了操作');});
}