var iDisplayStartOfCalendarList = 0;

$(document).ready(function () {
	getCalendarList();
    //导出按钮绑定事件
    $("#loadMoreCalendarList").bind("click",function(){
    	getCalendarList();
    });
    //initLoadMore("loadMoreCalendarList", getCalendarList);
});

/**
 * 列表
 * @return
 */
function getCalendarList() {
    $.ajax({
        url: basePath + "calendar/calendar_getCalendarListByUserId.action"+"?random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: {
            iDisplayStart: iDisplayStartOfCalendarList
        },
        success: function (data) {
            if (data != undefined && data!="") {
            	var aaData = data["aaData"];
                var html = "";
                for (var i = 0; i < aaData.length; i++) {
                    var dataMap = aaData[i];
                    html +="<li onclick=\"calendarView("+dataMap["calendarId"]+")\">";
                    html+="<h2>";
                    html+=dataMap["begTimeStr"];
                    html+="<s:date value=\""+dataMap["begTime"]+"\" format=\"yyyy-MM-dd\"/>";
                    html+=" 至 ";
                    html+=dataMap["endTimeStr"];
                    html+="</h2><p>";
                    if(dataMap["calLevel"]==1){
                    	html+="<span class=\"orange\">[重要/紧急]</span>";
                    }else if(dataMap["calLevel"]==2){
                    	html+="<span class=\"yellow\">[重要/不紧急]</span>";
                    }else if(dataMap["calLevel"]==3){
                    	html+="<span class=\"green\">[不重要/紧急]</span>";
                    }else if(dataMap["calLevel"]==4){
                    	html+="<span class=\"gray\">[不重要/不紧急]</span>";
                    }else{
                    	html+="<span >[未指定]</span>";
                    }
                    html+=" "+dataMap["content"];
                    html+="</p></li>";
                }
                $("#calendarlist").append(html);
                
                if (iDisplayStartOfCalendarList > 0) {
                    window.scrollTo(0, document.body.scrollHeight);
                }
                iDisplayStartOfCalendarList = parseInt(data["iDisplayStart"]);

                if (data["loadAll"]) {
                    //loadMore.loadAll("loadMoreCalendarList");
                	 $("#loadMoreCalendarList a").html("已完成加载");
                } else {
                    //loadMore.more("loadMoreCalendarList");
                	 $("#loadMoreCalendarList a").html("点击加载更多……");
                }

            }else{
            	   loadMore.more("loadMoreCalendarList");
                   return;
            }
        }
    });
}

function calendarView(id){
	window.location.href=basePath+'calendar/getCalendarView.action?calendarId='+id+"&random="+Math.random();
}