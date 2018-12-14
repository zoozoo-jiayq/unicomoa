$(document).ready(function() {	
    $("#file_upload").uploadify({
        //和存放队列的DIV的id一致
        'queueID':'queue',
        //服务器端脚本使用的文件对象的名称 $_FILES个['upload']
        'fileObjName':'fileupload',
        //上传处理程序
        'uploader':basePath+'upload/uploadfile.action?module='+$("#moduleName").val(),
        //按钮文字
        'buttonText' : '上传附件...',
        //附带值
        'formData':{
            'userid':'用户id',
            'username':'用户名',
            'rnd':'加密密文'
        },
        //浏览按钮的背景图片路径
        'buttonImage':basePath+'plugins/upload/inputFile.png',
        //取消上传文件的按钮图片，就是个叉叉
        'cancel': basePath+'plugins/upload/upbutton.png',
        //浏览按钮的宽度
        'width':'82',
        //浏览按钮的高度
        'height':'25',
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc':'支持的格式:',
        //允许上传的文件后缀
        'fileTypeExts':'*.xls',
        //上传文件的大小限制
        'fileSizeLimit':'10MB',
        //上传数量
        'queueSizeLimit' : 1,
        //开启调试
        'debug' : false,
        //是否自动上传
        'auto':true,
        //上传后是否删除
        'removeComplete':false,
        //清除
        removeTimeout : 9999,
        
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
        	// 清除上次传送的文件
        	$("#excelPath").val("");
        	if($("#queue").children(".uploadify-queue-item").length > 1){
        		$("#queue").children(".uploadify-queue-item").first().remove();
        	}
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

            // 上传成功后，保存物理路径
            $("#excelPath").val(data);
            
            // 导入按钮可用
            $("#improtBtn").attr("disabled", false);
            
            // 上传成功后，删除页面提示信息
            $("#"+file.id).find("div.cancel>a").attr("href","javascript:removeFile('"+file.id+"')"); 
        },
        //上传前取消文件
        'onCancel' : function(file) {
        }
    });
});
//上传成功后点击删除按钮
function removeFile(target){
	$("#"+target).remove();
	$("#excelPath").val("");
}