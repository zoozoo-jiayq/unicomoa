/**
 * 开始上传
 * 
 * @return {Boolean}
 */
function startAjaxFileUpload() {
	var fileName = $("#fileToUpload").val();
	if (fileName == "") {
		$("#msg").html('请选择要导入的文件！');
		return false;
	}else{   //(.xlsx)|(.xls)
		var rex = /.xls$/gi;
		if(!rex.test(fileName)){
			$("#msg").html('请选择电子表格！');
			return false;
		}
	}
	var radioType = $("input:radio[name='radioType']:checked").val();
	var url = basePath + 'logined/record/import.action';
	$("#msg").html('正在导入 &nbsp;<img src="'+basePath+'images/jindu.gif" />');
	$.ajaxFileUpload({
		url : url,
		secureuri : false,
		fileElementId : 'fileToUpload',
		dataType : 'text', // 这里只能写成text，不能写成json。否则ajaxfileupload.js中103行会抛异常。不知道为什么。
		data : {
			uploadFileName : fileName
		},
		success : function(data, status) {
			var json = eval("("+data+")");
			$("#msg").html(json.result);
			//art.dialog.data("successList",json.successList);
			//window.opener=null;
			//window.open('','_self');
			//window.close();
		},
		error : function(data, status, e) {
			$("#msg").html("对不起！导入文件时出错！");
		}
	});

	return false;

}