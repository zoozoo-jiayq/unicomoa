/**
 * Created by lilipo on 2017/1/4.
 */
$(function () {
    getGGList(0);
})
//获得公告
function getGGList(startPage) {
    var iDisplayStart = startPage * 5;
    var dataParam = {
        "notifyType": 45,
        "columnId": 35,
        "iDisplayStart": iDisplayStart,
        "iDisplayLength": 5
    }
    $.ajax({
        url: basePath + "notify/notify_viewList.action",
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            if (data.aaData.length >0) {
                paintingGG(startPage,data)
                $(".jxkh_page_box").show();
            } else {
            	$(".jxkh_page_box").hide();
            	$("#gg_ul").html("暂无公告");
            }

        }
    });
}

function paintingGG(startPage, data) {
    var temp = (startPage+1)*7;
    var gg_ul = $("#gg_ul");
    gg_ul.html("");
    var nowPage = $("#nowPage");
    for (var i = 0; i < data.aaData.length; i++) {
        var gg = data.aaData[i];
        var ggStr = '';
        if (gg.isTop == 1) {
            ggStr += '<li> <span class="block_icon"></span> <span class="notice_to_top">【置顶】</span> ' +
                '<a href="javascript:void(0);" title="'+gg.subject+'" onclick="ggxq('+gg.notifyId+')">' + processStr(gg.subject,13) + '</a> </li>';
        } else {
            ggStr += '<li> <span class="block_icon"></span>' +
                '<a href="javascript:void(0);"title="'+gg.subject+'" onclick="ggxq('+gg.notifyId+')">' + processStr(gg.subject,17) + '</a> </li>';
        }
        gg_ul.append(ggStr);
    }
    if (startPage == 0) {
        $("#lastPage").attr("onclick", "ts('已经是第一页了')");
        $("#nextPage").attr("onclick", "getGGList(" + (startPage + 1) + ");");
    } else if(temp >= data.iTotalRecords) {
        $("#lastPage").attr("onclick", "getGGList(" + (startPage - 1) + ");");
        $("#nextPage").attr("onclick", "ts('已经是最后一页了')");
    }else {
        $("#lastPage").attr("onclick", "getGGList(" + (startPage - 1) + ");");
        $("#nextPage").attr("onclick", "getGGList(" + (startPage + 1) + ");");
    }
    $("#nowPage").text(startPage + 1)
}

function ts(val) {
    art.dialog.alert(val);
}

function getBLen(str) {
    if (str == null) return 0;
    if (typeof str != "string") {
        str += "";
    }
    return str.replace(/[^\x00-\xff]/g, "01").length;
}

function processStr(val,length) {
    if(getBLen(val) >length){
        var str = val.substring(0,length);
        return str+"...";
    }else {
        return val;
    }
}

function ggxq(id) {
    var openUrl = basePath+"/logined/appraisal/jsp/notify_view.jsp?id="+id;
    qytx.app.dialog.openUrl({
        url	: openUrl,
        title:	"公告详情",
        size:	"L",
        /*customButton: [
            {
                name: '确定',
                callback: function () {
                    var iframe = this.iframe.contentWindow;
                    iframe.post();
                    return false;
                },
                focus: true
            },
            {name: '取消'}
        ]*/
    });
}