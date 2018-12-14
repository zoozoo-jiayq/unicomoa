//javascript for wap email list.jsp

//收件箱翻页数据：起始位置
var iDisplayStartOfInBoxEmailList = 0;
//发件箱翻页数据：起始位置
var iDisplayStartOfOutBoxEmailList = 0;

//页面加载完成后调用的方法，对页面一些内容进行初始化
$(document).ready(function () {
    initTagClick();
    initShowWhichTab();
    initEmailSubjectClick();
    initLoadMoreClick();
});

//初始化“点击加载更多”的单击方法
function initLoadMoreClick() {
    $("#loadMoreInBoxEmail").click(function () {
        getInBoxList();
    });
    $("#loadMoreOutBoxEmail").click(function () {
        getOutBoxList();
    });
}

//初始化显示哪个Tab，根据参数默认显示“收件箱”或者“发件箱”
function initShowWhichTab() {
    var showTab = $("#showTab").val();
    if (showTab != undefined && $.trim(showTab) == "outBox") {
        $("#outBoxTag").parent().click();
        $("#outBoxTag").click();
    } else {
        $("#inBoxTag").parent().click();
        $("#inBoxTag").click();
    }
}
//初始化邮件内容点击事件，点击进入邮件详情
function initEmailSubjectClick() {
    $("#inBoxEmailList li").live("click", function () {
        var emailToId = $(this).attr("emailToId");
        window.location = basePath + "logined/email/emailDetailForWap.action" + wapParam + "&emailToId=" + emailToId+"&random="+Math.random();
    });

    $("#outBoxEmailList li").live("click", function () {
        var emailBodyId = $(this).attr("emailBodyId");
        window.location = basePath + "logined/email/emailDetailForWap.action" + wapParam + "&emailBodyId=" + emailBodyId+"&random="+Math.random();
    });
}

//发件箱Tab第一次点击标记
var outBoxTagFirstClick = 0;
//收件箱Tab第一次点击标记
var inBoxTagFirstClick = 0;

//初始化“收件箱”“发件箱”Tab点击效果
function initTagClick() {
    $("#outBoxTag").click(function () {
        if (outBoxTagFirstClick == 0) {
            getOutBoxList();
            outBoxTagFirstClick = 1;
        }
    });
    $("#inBoxTag").click(function () {
        if (inBoxTagFirstClick == 0) {
            getInBoxList();
            inBoxTagFirstClick = 1;
        }
    });
}
//收件箱完全加载标记
var inBoxLoadAll = 0;
//获取收件箱列表数据
function getInBoxList() {
    if (inBoxLoadAll == 1) {
        return;
    }
    $("#loadMoreInBoxEmail a").text("正在加载...");
    $.ajax({
        url: basePath + "logined/email/getInBoxListForWap.action" + wapParam+"&random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: {
            iDisplayStart: iDisplayStartOfInBoxEmailList
        },
        success: function (data) {
            if (data == undefined) {
                $("#loadMoreInBoxEmail a").text("点击加载更多...");
                return;
            }
            var aaData = data["aaData"];
            var html = "";
            for (var i = 0; i < aaData.length; i++) {
                var dataMap = aaData[i];
                html += "<li emailToId='" + dataMap["id"] + "'>";
                if (dataMap["readStatus"] == 0) {
                    html += "<span>未读</span>";
                }
                html += "<h2>" + dataMap["subject"] + "</h2>";
                html += "<h3>" + dataMap["fromUserName"] + "<time>" + dataMap["longTime"] + "</time></h3>";
                html += "</li>";
            }
            $("#inBoxEmailList").append(html);
            if (iDisplayStartOfInBoxEmailList > 0) {
                window.scrollTo(0, document.body.scrollHeight);
            }
            iDisplayStartOfInBoxEmailList = parseInt(data["iDisplayStart"]);

            if (data["loadAll"]) {
                $("#loadMoreInBoxEmail a").text("已完成加载");
                inBoxLoadAll = 1;
            } else {
                $("#loadMoreInBoxEmail a").text("点击加载更多...");
            }
        }
    });
}
//发件箱完全加载标记
var outBoxLoadAll = 0;
//获取发件箱列表内容
function getOutBoxList() {
    if (outBoxLoadAll == 1) {
        return;
    }
    $("#loadMoreOutBoxEmail a").text("正在加载...");
    $.ajax({
        url: basePath + "logined/email/getOutBoxListForWap.action" + wapParam+"&random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: {
            iDisplayStart: iDisplayStartOfOutBoxEmailList
        },
        success: function (data) {
            if (data == undefined) {
                $("#loadMoreOutBoxEmail a").text("点击加载更多...");
                return;
            }
            var aaData = data["aaData"];
            var html = "";
            for (var i = 0; i < aaData.length; i++) {
                var dataMap = aaData[i];
                html += "<li emailBodyId='" + dataMap["id"] + "'>";
                html += "<h2>" + dataMap["subject"] + "</h2>";
                html += "<h3>" + dataMap["toName"] + "<time>" + dataMap["longTime"] + "</time></h3>";
                html += "</li>";
            }
            $("#outBoxEmailList").append(html);
            if (iDisplayStartOfOutBoxEmailList > 0) {
                window.scrollTo(0, document.body.scrollHeight);
            }
            iDisplayStartOfOutBoxEmailList = parseInt(data["iDisplayStart"]);

            if (data["loadAll"]) {
                $("#loadMoreOutBoxEmail a").text("已完成加载");
                outBoxLoadAll = 1;
            } else {
                $("#loadMoreOutBoxEmail a").text("点击加载更多...");
            }
        }
    });
}