//javascript for wap report list.jsp
$(document).ready(function () {
	getReportWapList();
});

function getReportWapList() {
  //$("#reportList a").text("正在加载...");
	var userId = $("#userId").val();
    $.ajax({
        url: basePath + "wap/reportInfo/report_reportCategoryList.action"+"?random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: {
            userId: userId,
            _clientType:"wap"
        },
        success: function (data) {
            var aaData = data["aaData"];
            if (aaData != undefined && aaData.length > 0) {
                var html = "";
                for (var i = 0; i < aaData.length; i++) {
                    	var dataMap = aaData[i];
                    	var reportName = dataMap["reportName"];
                    	html += "<li style='cursor:pointer;' onClick='showReportList("+dataMap["id"]+")'><a >";
                    	html +="<p>"+reportName+"</p>";
                    	html += "</a></li>";
                }
               $("#reportList").append(html);
            }
        }
    });
}


function  showReportList(categoryId){
	var userId = $("#userId").val();
	var url= basePath+"wap/logined/report/reportCategoryList.jsp?_clientType=wap&userId="+userId+"&categoryId="+categoryId;
	window.location.href=url;
}

