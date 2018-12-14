function downFile(zipName) {
	var fileIds = "";
    var selLen = 0;
    var checkTotalNum = $("input:checkbox[name='chk']");
	var checkNum = 0;
	var isAllChecked = true;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
					fileIds+=$(this).attr("attId");
				} 
           selLen++;
	});
	if (selLen == 0) {
		qytx.app.dialog.alert('请选择需要下载的文件！');
		return;
	}
	if (fileIds != "" && fileIds != null) {
		window.location.href = basePath
				+ "filemanager/downZipFile.action?attachmentIds=" + fileIds+"&zipName="+zipName;
	} else {
		qytx.app.dialog.alert('请选择需要下载的文件！');
	}

}
