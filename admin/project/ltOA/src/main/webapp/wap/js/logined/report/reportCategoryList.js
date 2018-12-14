//javascript for wap report list.jsp
$(document).ready(function () {
	getReportWapList();
	
	//跳转到列表页面
	$("#categoryLi").click(function(){
		var userId = $("#userId").val();
		var url =basePath+"wap/logined/report/categoryList.jsp?_clientType=wap&userId="+userId;
		document.location.href=url;
	});
});
 


function getReportWapList() {
  //$("#reportList a").text("正在加载...");
	var userId = $("#userId").val();
	var categoryId = $("#categoryId").val();
    $.ajax({
        url: basePath + "wap/reportInfo/report_reportInfoList.action"+"?random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: {
            userId: userId,
            categoryId:categoryId,
            _clientType:"wap"
        },
        success: function (data) {
            var aaData = data["aaData"];
             var category = data["category"];
             var categoryName = category.categoryName;
             $("#categoryName").html("<h1>"+categoryName+"</h1>");
            if (aaData != undefined && aaData.length > 0) {
                var html = "";
                for (var i = 0; i < aaData.length; i++) {
                    	var dataMap = aaData[i];
                    	var reportName = dataMap["reportName"];
                    	html += "<li style='cursor:pointer;' onClick='showView("+dataMap["id"]+")'><a >";
                    	html +="<p>"+reportName+"</p>";
                    	html += "</a></li>";
                }
               $("#reportList").append(html);
            }
        }
    });
}


function  showView(reportId){
	var url= basePath+'wap/logined/report/reportView.jsp?_clientType=wap&reportId='+reportId;
	window.location.href=url;
}

