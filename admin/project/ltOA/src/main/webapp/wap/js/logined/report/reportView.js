$(document).ready(function () {
	
	setPage();
	
});

/**
 * 设置报表
 * @param reportId
 */
function setPage(){
	 var reportId =$("#reportId").val();
	 $.ajax({
	        url: basePath + "wap/reportInfo/report_getReportInfo.action"+"?random="+Math.random(),
	        type: "post",
	        dataType: 'json',
	        data: {
	        	 reportId: reportId,
	            _clientType:"wap"
	        },
	        success: function (data) {
	        	var url = data.url;
	        	var arr = url.split("?");
	        	var urlPage ="";
	        	if(arr.length>1){
	        		urlPage = basePath+url+"&_clientType=wap"
	        	}else{
	        		urlPage = basePath+url+"?_clientType=wap"
	        	}
	        	$("#mainIfrme").attr("src",urlPage)
	        }
	    });
}