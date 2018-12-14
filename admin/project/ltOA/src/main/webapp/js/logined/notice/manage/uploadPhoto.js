$(document).ready(function() {	
    $("#addImg").uploadify(uploadSet($("#fileName"),$("#photoPath")));
});
/**
 * uploadBefore 上传前图片文本框
 * uploadAfter 上传过图片文本框
 */
function uploadSet(uploadBefore,uploadAfter){
    return {
        //和存放队列的DIV的id一致
        'queueID':'queue',
        //服务器端脚本使用的文件对象的名称 $_FILES个['upload']
        'fileObjName':'fileupload',
        //上传处理程序
        'uploader':basePath+'filemanager/uploadfile.action?module=notice',
        //按钮文字
        'buttonText' : '上传附件...',
        //浏览按钮的背景图片路径
        'buttonImage':basePath+'/ydzj/images/content/add_lmtb.png',
        //取消上传文件的按钮图片，就是个叉叉
        'cancel': basePath+'plugins/upload/upbutton.png',
        //浏览按钮的宽度
        'width':'50',
        //浏览按钮的高度
        'height':'50',
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc':'支持的格式',
        //允许上传的文件后缀
        'fileTypeExts':'*.jpg;*.gif;*.png;*.bmp',
        //上传文件的大小限制
        'fileSizeLimit':'0KB',

        //上传数量
        'queueSizeLimit' : 25,
        //开启调试
        'debug' : false,
        //是否自动上传
        'auto':true,
        //上传后是否删除
        'removeComplete':false,
        //清除
        'removeTimeout' : 0,
        
        'langFile':basePath+'plugins/upload/ZH_cn.js',
        
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
            uploadBefore.text(file.name);
            return true;
        },
        //返回一个错误，选择文件的时候触发
        'onSelectError':function(file, errorCode, errorMsg){
            switch(errorCode) {
                case -100:
                    alert("上传的文件数量已经超出系统限制的"+$('#addImg').uploadify('settings','queueSizeLimit')+"个文件！");
                    break;
                case -110:
                    alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#addImg').uploadify('settings','fileSizeLimit')+"！");
                    break;
                case -120:
                    alert("文件 ["+file.name+"] 大小异常！");
                    break;
                case -130:
                    alert("文件 ["+file.name+"] 类型不正确！");
                    break;
            }
        },
        //检测FLASH失败调用
        'onFallback':function(){
            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
        },
        //上传到服务器，服务器返回相应信息到data里
        'onUploadSuccess':function(file, data, response){          
        	var obj = jQuery.parseJSON(data);
        	//向前边添加li
        	showLI();
            // 上传成功后，删除页面提示信息
//            $("#photoPath").val(data);
            //显示图片
            $("#userPhoto").attr("src",downPath+obj.attachFile);
            $("#userPhoto").attr("tit",obj.attachFile);
        },
        //上传前取消文件
        'onCancel' : function(file) {

        }
    };
}
 