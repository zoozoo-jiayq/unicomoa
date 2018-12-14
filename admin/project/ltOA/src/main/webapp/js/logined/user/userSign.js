jQuery(document).ready(function($){
    var flag = $("#userSign").val();
    showSign(flag);

    /*
     *点击签章类型，显示不同操作
     */
    $("#userSign").change(function(){
        var flag = $(this).val();
        showSign(flag);
    });

    function showSign(flag){
        if(flag == 0 ){
        	$("#contentSign").hide();
            $("#signContent").hide();
        }else if(flag == 1){
        	$("#contentSign").show();
            $("#signContent").hide();
        }else if(flag == 2){
            $("#signContent").show();
            $("#contentSign").hide();
        }
    }


	/*
	 *初始化个人印章的选择
	 */
	(function(){
		var imgUrl = $("#imgSignUrl").val();
		if(imgUrl){
			$("#imgContent").attr("src",basePath+"filemanager/prevViewByPath.action?filePath="+imgUrl);
            $("#imgContent").show();
		}else{
            //$("#imgContent").hide();
        }
		
		// var signType = $("#signType").val();
		// $("input[name='userSign'][value='"+signType+"']").attr("checked","checked");
		// $("input[name='userSign'][value='"+signType+"']").click();
	})(); 

	/*
	 * 上传图片印章
	 */
	
	qytx.app.fileupload({
		id	:	"userSign_upload",
		moduleName	:	"userSign",
		fileTypeExts	:	"*.jpg;*.jpeg;*.gif;*.png",
		fileSizeLimit	:	"200KB",
		callback	:	function(obj){
	    	var path = basePath + "filemanager/prevViewByPath.action?filePath=" +obj.attachFile;
	    	$("#imgContent").attr("src",path);
	    	$("#imgSignUrl").val(obj.attachFile);
	        $("#imgContent").show();
		}
	});
});

function check(){
	var val = $("#userSign").val();
	if(val == 2){
		if(!$("#imgSignUrl").val()){
			art.dialog.alert("请上传印章图片附件!");
			return false;
		}
	}
	return true;
}

