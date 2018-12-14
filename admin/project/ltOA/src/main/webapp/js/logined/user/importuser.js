var freshPage = false;

/**
 * 验证格式
 */
function checkFileFormat() {
	var fileName = $("#fileToUpload").val();
	if (fileName == "") {
		$("#msg").html('请选择要验证的文件！');
		return false;
	}else{
		if(fileName.indexOf(".xls")==-1){
			$("#msg").html('请选择电子表格！');
			return false;
		}
	}
	var radioType = 1;
	var url = basePath + 'userUpload/check.action';
	$("#msg").html('正在验证中，请稍候......'+'<img width="32" height="32" src="'+basePath+'flat/images/load.gif"></img>');
	$.ajaxFileUpload({
				url : url,
				secureuri : false,
				fileElementId : 'fileToUpload',
				dataType : 'text',
				data : {
					uploadFileName : fileName,
					radioType : radioType
				},
				success : function(data, status) {
					if(data.indexOf("pre") > 0){
						var data = data.substring(data.indexOf("pre")+4,data.lastIndexOf("pre")-2);
					}
					if (data == "") {
						$("#msg").html("验证通过,可以导入！");

					} else {
						$("#msg").html(data);
					}
				},
				error : function(data, status, e) {
					$("#msg").html("对不起！验证文件时出错！");
				}
			});
	return false;
}

/**
 * 开始上传
 * 
 * @return {Boolean}
 */
function startAjaxFileUpload() {
	var fileName = $("#fileToUpload").val();
	if (fileName == "") {
		$("#msg").html('请先选择需要导入的文件 ！');
		return false;
	}else{
		if(fileName.indexOf(".xls")==-1){
			$("#msg").html('请选择电子表格！');
			return false;
		}
	}
	var radioType = $("input:radio[name='radioType']:checked").val();
	var url = basePath + 'userUpload/importUser.action';
	$("#msg").html('正在导入，请稍候......'+'<img width="32" height="32" src="'+basePath+'flat/images/load.gif"></img>');
	$.ajaxFileUpload({
				url : url,
				secureuri : false,
				fileElementId : 'fileToUpload',
				dataType : 'text',
				data : {
					uploadFileName : fileName,
					radioType : radioType
				},
				success : function(data, status) {
					$("#msg").html(data);
					if(null != data && data.indexOf("共") == 0 ){
						freshPage = true;
//						$("#page").attr("src",
//								basePath + 'logined/user/userList.jsp');
						var win = art.dialog.open.origin;
						win.window.parent.refreshTree("gid_0");
						win.window.getInfo();
					}					
				},
				error : function(data, status, e) {
					$("#msg").html("对不起！导入文件时出错！");
				}
			});

	return false;

}
