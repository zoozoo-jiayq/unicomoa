$(document).ready(function() {
	// 单击导入
	$("#improtBtn").click(function() {
		improtAddress();
		return false;
	});

	$("#groupName").val(decodeURI($("#groupName").val()));
});

/**
 * 导入文件
 */
function improtAddress() {
	// 文件路径不能为空
	if (null == $("#excelPath").val() || "" == $("#excelPath").val()) {
		art.dialog.alert(sprintf("addressbook.msg_must_uploadfile"));
		return;
	}

	$("#improtBtn").attr("disabled", true);
	
	var paramData = {
	    "fileUploadName" : $("#excelPath").val(),
	    "groupName" : $("#groupName").val(),
	    "groupId" : $("#groupId").val()
	};

	// 发送ajax请求,导入文件内容
	$.ajax({
	    url : basePath + 'address/setup_importAddress.action',
	    type : 'post',
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (null != data && null != data.importData) {
		    	$("#excelPath").val("");
		    	$("#improtBtn").attr("disabled", false);		    	
			    // 设置成功导入信息
			    if (null != data.importData.successNum && "" != data.importData.successNum) {
			    	var queryAddress = art.dialog.data('queryAddress');
			    	queryAddress();
				    $("#successNum").html(data.importData.successNum);
			    }
			    
			    // 设置失败信息
			    if (null != data.importData.errorFileName && "" != data.importData.errorFileName) {
				    $("#errorResult").html(
				            "<a href=" + basePath + "upload/downErrorFile.action?fileuploadFileName=" + data.importData.errorFileName +">"
				                    + data.importData.errorNum + "条导入失败</a>");
			    } else {
				    $("#errorResult").html("");
			    }
			    $("#result").show();

		    } else {
			    art.dialog.alert(sprintf("addressbook.msg_must_uploadfile"));
		    }
	    }
	});
}

function closeWindow(){
	var mailAttachmentTd = document.getElementById('importTr');
    if(null != mailAttachmentTd)
   mailAttachmentTd.parentNode.removeChild(mailAttachmentTd);
	art.dialog.close();
}