$(document).ready(function(){
	
	$("#file_upload").uploadify({
		// 和存放队列的DIV的id一致
		'queueID' : 'queue',
		// 服务器端脚本使用的文件对象的名称 $_FILES个['upload']
		'fileObjName' : 'fileupload',
		// 上传处理程序
		'uploader' : basePath +  'filemanager/uploadfile.action?module=module',
		// 按钮文字
		'buttonText' : '上传附件...',
		// 附带值
		'formData' : {
			'userid' : '用户id',
			'username' : '用户名',
			'rnd' : '加密密文'
		},
		// 浏览按钮的背景图片路径
		'buttonImage' : basePath + 'flat/images/upload.png',
		// 取消上传文件的按钮图片，就是个叉叉
		'cancel' : basePath + 'plugins/upload/upbutton.png',
		// 浏览按钮的宽度
		'width' : '48',
		// 浏览按钮的高度
		'height' : '15',
		// 在浏览窗口底部的文件类型下拉菜单中显示的文本
		'fileTypeDesc' : '支持的格式:',
		// 允许上传的文件后缀
		'fileTypeExts' : '*.jpg;*.png',
		// 上传文件的大小限制
		'fileSizeLimit' : '200K',
		// 上传数量
		'queueSizeLimit' : 1,
		// 开启调试
		'debug' : false,
		// 是否自动上传
		'auto' : true,
		// 上传后是否删除
		'removeComplete' : false,
		// 清除
		removeTimeout : 0,
		langFile : basePath + 'plugins/upload/ZH_cn.js',
		// 超时时间
		'successTimeout' : 99999,
		// flash
		'swf' : basePath + 'plugins/upload/uploadify.swf',
		// 不执行默认的onSelect事件
		'overrideEvents' : ['onDialogClose'],
		// 每次更新上载的文件的进展
		'onUploadProgress' : function(file, bytesUploaded, bytesTotal,
				totalBytesUploaded, totalBytesTotal) {
			// 有时候上传进度什么想自己个性化控制，可以利用这个方法
		//	onUploadProgressHelp(file, totalBytesUploaded, totalBytesTotal);
		},
		// 选择上传文件后调用
		'onSelect' : function(file) {
			//onSelectHtml(file, "attachmentList", "file_upload");
			return true;
		},
		// 返回一个错误，选择文件的时候触发
		'onSelectError' : function(file, errorCode, errorMsg) {
			switch (errorCode) {
				case -100 :
					art.dialog.alert("上传的文件数量已经超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'queueSizeLimit') + "个文件！");
					break;
				case -110 :
					art.dialog.alert("文件 ["
							+ file.name
							+ "] 大小超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'fileSizeLimit') + "大小！");
					break;
				case -120 :
					art.dialog.alert("文件 [" + file.name + "] 大小异常！");
					break;
				case -130 :
					art.dialog.alert("文件 [" + file.name + "] 类型不正确！");
					break;
			}
		},
		// 检测FLASH失败调用
		'onFallback' : function() {
			art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
		},
		// 上传到服务器，服务器返回相应信息到data里
		'onUploadSuccess' : function(file, data, response) {
			var obj = eval("("+data+")");
			$("#icon").val(obj.attachFile);
			getFilePath(obj.id);
		},
		// 上传前取消文件
		'onCancel' : function(file) {
			// art.dialog.alert('The file ' + file.name + ' was cancelled.');
		}
	});
	function getFilePath(id){
		$.ajax({
			type:"post",
			url:basePath+"module/filePath.action",
			data:{"id":id},
			dataType:"text",
			success:function(result){
				$("#iconImage").attr("src",result);
			}
		});
	}
	
});
function  subModule(){
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var id = $("#id").val(); 
	var name = $("#name").val();
	var nameValidate = false;
	$.ajax({
		type:"post",
		url:basePath+"module/valiName.action",
		data:{"name":name,"id":id},
		dataType:"text",
		async:false,
		success:function(result){
			nameValidate = result.toString();
		}
	});
	if(nameValidate=="true"){
		showObjError($("#name"), 'module.module_name_is_exit');
		return;
	}
	var code = $("#code").val();
	var codeValidate = false;
//	$.ajax({
//		type:"post",
//		url:basePath+"module/valiCode.action",
//		data:{"code":code,"id":id},
//		dataType:"text",
//		async:false,
//		success:function(result){
//			codeValidate = result.toString();
//		}
//	});
	if(codeValidate=="true"){
		showObjError($("#code"), 'module.module_code_is_exit');
		return;
	}
	var orderList = $("#orderList").val();
	var orderListValidate = 2;
	$.ajax({
		type:"post",
		url:basePath+"module/valiOrder.action",
		data:{"order":orderList,"id":id},
		dataType:"text",
		async:false,
		success:function(result){
			orderListValidate = result.toString();
		}
	});
	//1 表示已存在，2表示成功，3表示父节点不存在
	if(orderListValidate==1){
		showObjError($("#orderList"), 'module.module_order_is_exit');
		return;
	}else if(orderListValidate ==3){
		showObjError($("#orderList"), 'module.module_parent_is_exit');
		return;
	}else{
		var orderList = $("#orderList").val();
		var url = $("#url").val();
		var icon = $("#icon").val();
		var statue = $("input[name='statue']:checked").val();
		var paramDate = {"mobile.name":name,"mobile.code":code,"mobile.url":url,"mobile.orderList":orderList,'mobile.icon':icon,"mobile.statue":statue,"mobile.id":id};
		$.ajax({
			type:"post",
			url:basePath+"module/save.action",
			data:paramDate,
			dataType:"json",
			success:function(result){
				window.location.href=basePath + "logined/module/index.jsp";
			}
		});
	}
	
}
function goback(){
	window.location.href=basePath + "logined/module/index.jsp";
}
