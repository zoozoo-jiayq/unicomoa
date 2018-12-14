jQuery(document).ready(function() {
			/** 选择 */
			$("#checkFileContent").click(function() {
						checkFile();
						return false;
					});
		});
/**
 * 删除短信
 */

function checkFile() {

	var chkbox = document.getElementsByName("chk");
	var fileIds = "";
	var selLen = 0;
	for (var i = 0; i < chkbox.length; i++) {
		if (chkbox[i].checked) {
			fileIds += '&fileIds=' + chkbox[i].value;
			selLen++;
		}
	}
	if (selLen == 0) {
		art.dialog.alert('请至少选择一个文件！');
		return;
	}

	art.dialog.confirm('确定要签阅这些文件吗？', function() {
				$.ajax({
							url : basePath + "file/checkFile.action",
							type : "post",
							dataType : 'text',
							data : fileIds,
							success : function(data) {
								if (data != "") {
									art.dialog({
										   title:"消息",
										   content:"签阅成功！",
										   width : 317,
										   height : 109,
										   icon:"succeed",
										   opacity:0.08,
										   lock:true,
										   ok:function(){},
										   close:function(){
											   getDataTable();
										   }
										});
									
								} else {
									art.dialog.alert('签阅失败！');
									getDataTable();
								}
							}
						});
			}, function() {
				return;
			});
}