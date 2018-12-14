/**
 * 导入
 */
function uploadBaseStation() {
	art.dialog.open(basePath +"logined/record/import.jsp",{
		id : 'importBaseStation',
		title : '导入档案',
		width : 600,
		height : 350,
		opacity: 0.08,
		lock : true,
		button : [
				{
					name : '确 定',
					callback : function() {
						var iframe = this.iframe.contentWindow;
						iframe.startAjaxFileUpload();
						return false;
					},
					focus:true
				},
				{
					name : '取 消'
				}]
	});

}


