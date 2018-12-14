var fileType = null;
var fileNum=0;
var fileTypeExts = "";
$(document).ready(function(){
	selectFileType();
	
	$("#groupSel").click(function() {
		showGroup();
		return false;
	});
});

/**
 * 选择上传的文件类型
 */
function selectFileType(){
	$("#file_upload").val("");
	fileType = $("#fileType").val();
	if(fileType==1){
		fileNum = 1;//上传文件个数限制
		fileTypeExts = "*.zip";//允许上传的文件后缀
		startFileUpload(fileTypeExts,fileNum);
	}else if(fileType==2){
		fileNum = 25;//上传文件个数限制
		fileTypeExts = "*.htm;*.html;*.txt;*.doc";//允许上传的文件后缀
		startFileUpload(fileTypeExts,fileNum);
	}
}
/**
 * 上传
 */
function startFileUpload(fileTypeExts,fileNum){
//	alert(fileTypeExts+"<>"+fileNum);
	$("#file_upload").show();
    $("#file_upload").uploadify({
        //和存放队列的DIV的id一致
        'queueID':'queue',
        //服务器端脚本使用的文件对象的名称 $_FILES个['upload']
        'fileObjName':'fileupload',
        //上传处理程序
        'uploader':basePath+'knowledge/uploadKnowledge_uploadKnowledge.action',
        //附带值
        'formData':{
            'userid':'用户id',
            'username':'用户名',
            'rnd':'加密密文'
        },
        //浏览按钮的背景图片路径
        'buttonImage':basePath+'flat/images/upload.png',
        //取消上传文件的按钮图片，就是个叉叉
        'cancel': basePath+'plugins/upload/upbutton.png',
        //浏览按钮的宽度
        'width':'48',
        //浏览按钮的高度
        'height':'15',
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc':'支持的格式:',
        //允许上传的文件后缀
        'fileTypeExts':fileTypeExts,
        //上传文件的大小限制
        'fileSizeLimit':'100MB',
        //上传数量
        'queueSizeLimit' : fileNum,
        //开启调试
        'debug' : false,
        //是否自动上传
        'auto':true,
        //上传后是否删除
        'removeComplete':false,
        //清除
        removeTimeout : 0,
        
        langFile:basePath+'plugins/upload/ZH_cn.js',
        
        //超时时间
        'successTimeout':99999,
        
        //flash
        'swf': basePath+'plugins/upload/uploadify.swf',
        //不执行默认的onSelect事件
        'overrideEvents' : ['onDialogClose'],
        //每次更新上载的文件的进展
        'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
            //有时候上传进度什么想自己个性化控制，可以利用这个方法
        },
        //选择上传文件后调用
        'onSelect' : function(file) {
             return true;
        },
        //返回一个错误，选择文件的时候触发
        'onSelectError':function(file, errorCode, errorMsg){
            switch(errorCode) {
                case -100:
                	art.dialog.alert("上传的文件数量已经超出系统限制的"+$('#file_upload').uploadify('settings','queueSizeLimit')+"个文件！");
                    break;
                case -110:
                	art.dialog.alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#file_upload').uploadify('settings','fileSizeLimit')+"大小！");
                    break;
                case -120:
                	art.dialog.alert("文件 ["+file.name+"] 大小异常！");
                    break;
                case -130:
                	art.dialog.alert("文件 ["+file.name+"] 类型不正确！");
                    break;
            }
        },
        //检测FLASH失败调用
        'onFallback':function(){
        	art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
        },
        //上传到服务器，服务器返回相应信息到data里
        'onUploadSuccess':function(file, data, response){
//        	alert(file+"<>"+data+"<>"+response);
        	var fileNames = $("#fileNames").val();
        	if(fileNames==null || fileNames==''){
        		fileNames = data;
        	}else{
        		fileNames = fileNames+","+data;
        	}
        	$("#fileNames").val(fileNames);
        	
        	$("#msg").html("<span style='color:green;'>上传成功！</span>");
        },
        //上传前取消文件
        'onCancel' : function(file) {
//             alert('The file ' + file.name + ' was cancelled.');
        }
    });
}


/**
 * 导入上传的文件到数据库
 */
function saveUploadKnowledge(){
	var typeId = $("#typeId").val();
	var fileNames = $("#fileNames").val();
	var parentId = $("#parentId").val();
	if (parentId == 0) {
		art.dialog.alert("请选择知识库分类名称!");
		return false;;
	}
	
//	if(fileNames.length>100){
//		art.dialog.alert("文件名称长度不能够超过100！");
//		return false;
//	}
	if(fileNames){
	    $("#msg").html('<span style="color:green">正在导入</span><img src="'+basePath+'images/jindu.gif" />');
		var paramData = {
				"typeId" : parentId,
				"fileNames" : fileNames
		};
		$.ajax({
			url:basePath + "knowledge/uploadKnowledge_saveUploadKnowledge.action",
			type : "post",
			dataType : "text",
			data : paramData,
			success : function(data) {
				if(data=="noPerm"){
					$("#msg").html("导入失败，您的权限不够！");
				}else if(data=="notExist"){
					$("#msg").html("导入失败，知识库类型不存在！");
				}else if(data=="error"){
//					art.dialog.tips("导入失败，请联系管理员处理！",3);
					$("#msg").html("导入失败，请联系管理员处理！");
				}else{
//					alert(data);
					var dataArr = data.split(",");
//					art.dialog.tips("导入成功"+dataArr[0]+"条，文件格式不正确失败"+dataArr[1]+"条，知识库中已存在导入失败"+dataArr[2]+"条",3);
					$("#msg").html("导入成功"+dataArr[0]+"条，文件格式不正确失败"+dataArr[1]+"条，知识库中已存在导入失败"+dataArr[2]+"条，文件名称长度超过100导入失败"+dataArr[3]+"条");
				}
			}
		});
	}else{
		art.dialog.alert("请先上传文件！");
		return false;
	}
}












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
	}else{
		var rex = /.doc|.txt|.htm|.html|.zip$/gi;
		if(!rex.test(fileName)){
			$("#msg").html('请选择正确的格式！');
			return false;
		}
	}
	var url = basePath + 'knowledge/uploadKnowledge_uploadKnowledge.action';
	$("#msg").html('<span class="gray_9 ml20 mr10">正在导入</span><img src="'+basePath+'images/jindu.gif" />');
	$.ajaxFileUpload({
				url : url,
				secureuri : false,
				fileElementId : 'fileToUpload',
				dataType : 'text', // 这里只能写成text，不能写成json。否则ajaxfileupload.js中103行会抛异常。不知道为什么。
				data : {
					uploadFileName : fileName
				},
				success : function(data, status) {
//					alert(data+" <--> "+status);
					if(data!=""&&status=="success"){
						$("#msg").html("<span style='color:green;'>上传成功！</span>");
						$("#filePath").val(data);
					}
				},
				error : function(data, status, e) {
					//alert(data+"  "+status+"  "+e);
					$("#msg").html("<span style='color:red;'>对不起！上传文件时出错！</span>");
				}
			});

	return false;

}


/**
 * 群组树调用群组树
 */
var showOrHide = true;
function showGroup() {
	$('#menuContent').toggle(showOrHide);
	// 相当于
	if (showOrHide) {
		showOrHide = false;
		var groupObj = $("#groupSel");
		var groupOffset = $("#groupSel").position();
		$("#menuContent").css({
		    left : groupOffset.left + "px",
		    top : groupOffset.top + groupObj.outerHeight() - 1 + "px"
		}).show();
		$("#treeContent").one("mouseleave", function() {
			$("#menuContent").hide();
			showOrHide = true;
			return false;
		});
	} else {
		$("#menuContent").hide();
		showOrHide = true;
	}
}