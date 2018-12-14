var freshPage = false;
/**
 * 导入
 */
function uploadUser() {
	art.dialog.open(basePath +"logined/user/alertMsg.jsp",{
				id : 'importUsers',
				title : '用户导入',
				lock :true,
				opacity: 0.08, 
				width : 600,
				height : 300,
				button : [{
							name : '验 证',
							callback : function() {
								var obj = this.iframe.contentWindow;
								obj.checkFileFormat();
								return false;
								}
							}, 
				          {
							name : '导 入',
							focus:true,
							callback : function() {
								var obj = this.iframe.contentWindow;
								obj.startAjaxFileUpload();
								return false;
							}
						}, {
							name : '取 消',
							callback : function() {
								return true;
							}
						}]
			});

}

 

 