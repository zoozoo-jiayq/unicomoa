function firstfile() {
	// var
	// ret=showModalDialog("/oa/personselect_huiyi.jsp?bmid=0",window,"status:0;help:0;edge:sunken;dialogWidth:682px;dialogHeight:390px;");

	var url = basePath + "logined/file/firstfilecontentdetain.jsp?";
	art.dialog.open(url, {
				title : '',
				width : 800,
				height : 450,
				// 在open()方法中，init会等待iframe加载完毕后执行
				init : function() {
					// var iframe = this.iframe.contentWindow;
				},
				lock : true,
			    opacity: 0.08
			});

}