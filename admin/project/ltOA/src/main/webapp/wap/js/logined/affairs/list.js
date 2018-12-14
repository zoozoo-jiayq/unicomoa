//javascript for wap affairs list.jsp

//翻页起始位置
var iDisplayStartOfAffairs = 0;

$(document).ready(function () {
    getAffairsList();
    initLoadMoreClick();
    initAffairsItemClick();
});

//初始化“点击加载更多”的单击事件
function initLoadMoreClick(){
    $("#loadMoreAffairs").click(function(){
        getAffairsList();
    });
}
//初始化事物列表中的每项的单击事件
function initAffairsItemClick() {
    $("#affairsList li").live("click", function () {
        var affairsId = $(this).attr("affairsId");
        window.location = basePath + "affairs/showAffairsForWap.action"+wapParam+"&affairsId=" + affairsId+"&random="+Math.random();
    });
}
//完全加载标记，为1则完全加载
var loadAllAffairsList=0;
//ajax获取事物列表
function getAffairsList() {
    if(loadAllAffairsList==1){
        return;
    }
    $("#loadMoreAffairs a").text("正在加载...");
    $.ajax({
        url: basePath + "affairs/getAffairsListForWap.action"+wapParam+"&random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: {
            iDisplayStart: iDisplayStartOfAffairs
        },
        success: function (data) {
            if (data == undefined) {
                $("#loadMoreAffairs a").text("点击加载更多...");
                return;
            }
            var aaData = data["aaData"];
            var html = "";
            for (var i = 0; i < aaData.length; i++) {
                var dataMap = aaData[i];
                html += "<li affairsId='" + dataMap["id"] + "'>";
                html += "<h2><time class='fr'>"+dataMap["sendTime"]+"</time>"+dataMap["fromUserName"]+"</h2>";
                html += "<p class='f14'>" + dataMap["contentInfo"]+"</p>";
                html += "</li>";
            }
            $("#affairsList").append(html);
            if (iDisplayStartOfAffairs > 0) {
                window.scrollTo(0, document.body.scrollHeight);
            }
            iDisplayStartOfAffairs = parseInt(data["iDisplayStart"]);
            if (data["loadAll"]) {
                $("#loadMoreAffairs a").text("已完全加载");
                loadAllAffairsList=1;
            } else {
                $("#loadMoreAffairs a").text("点击加载更多...");
            }
        }
    });
}