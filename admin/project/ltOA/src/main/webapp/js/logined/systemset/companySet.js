jQuery(document).ready(function(){
	$(".close").click(function(){
		$("#logUrl").val("");
		$("#view").attr("src",basePath+"/flat/images/hy.png" );
	});
	
	
	//提交表单
	$("#submitButton").click(function(){
			if(qytx.app.valid.check({form:$("#form1")[0]})){
					if($("#introduction").val().length>200){
						showError("introduction","record.introduction_out_of_max_length");
						return false;
					}
					/*$.post(basePath+"/company/update.action",$("#form1").serialize(),function(data){
							if(data == 'success'){
								art.dialog({
									title : '消息',
									content : '修改成功！',
									icon : 'succeed',
									height : 109,
									width : 317,
									ok:true
								});
							}
					});*/
					var paramData = {
							'company.companyName' : $("#companyName").val(),
							'company.companyId' : $("#companyId").val(),
							'company.logUrl' :  $("#logUrl").val(),
							'company.shortName' : $("#shortName").val(),
							'company.sysName' : $("#sysName").val(),
							'company.email' : $("#email").val(),
							'company.linkMan' : $("#linkMan").val(),
							'company.tel' : $("#tel").val(),
							'company.address' : $("#address").val(),
							'company.introduction' : $("#introduction").val(),
							'company.philosophy' : $("#philosophy").val()
						};
					qytx.app.ajax({
						url : basePath + "company/update.action",
						type : "post",
						data : paramData,
						shade:true,
						success : function(data) {	
							if(data == 'success'){
								qytx.app.dialog.tips('修改成功！',function(){
									$(window.top.document).find("#companyLogo").attr("src",basePath+"filemanager/prevViewByPath.action?filePath="+$("#logUrl").val());
									$(window.top.document).find("#sysName").html($("#sysName").val());
								});
								
							}else{qytx.app.dialog.alert("修改失败！")}
						},
						error:function(){
							qytx.app.dialog.alert("系统异常，请稍后再试");
						}
					});
			}
		});
		
	qytx.app.fileupload({
		id	:	"file_upload",
		moduleName:	"LOGO",
		fileTypeExts:	"*.jpg;*.jpeg;*.gif;*.png",
		fileSizeLimit:	"200K",
		queueSizeLimit : 1,
		callback:	function(data){
			$("#logUrl").val(data.attachFile);
        	prewVeiw();
		}
	});
	
	//预览
	function prewVeiw(){
		var url = $("#logUrl").val();
		if(url){
			$("#view").attr("src",basePath+"filemanager/downview.action?attachPath="+url);
			window.parent.frameResize();
		    //window.parent.setLogo(url);
		}else{
			$("#view").attr("src",basePath+"/flat/images/hy.png" );
		}
	}
	
	prewVeiw();
		
	$("#clean").click(function(){
		$("#logUrl").val("");
		prewVeiw();
	});
});