/**
 * Created by lilipo on 2016/12/27.
 */
function format(val) {
    return (typeof(val)=="undefined")?"无":val;
}

function formatNum(val) {
    return (typeof(val)=="undefined")?0:parseInt(val);
}

function FormatMS(val) {
    if(typeof(val)=="undefined"||val == ''){
        return "无";
    }else {
        return val;
    }
}

var typeStrs = new Array();
var types = new Array();
function getSize(value, array) {
    var size = 0;
    for (var i = 0; i < array.length; i++) {
        if (array[i] == value) {
            size++;
        }
    }
    return size;
}
function painting(data) {
    var wdsz = data.wdsz;
    $("#wdsz").text(format(wdsz));
    $("#userName").text(format(data.userName));
    $("#groupName").text(format(data.groupName));
    $("#job").text(FormatMS(data.job));
    if (data.atta) {
        var attaStr = ' <a  onclick="downLoadAttachment(' + data.atta.id + ')" class="file_tishi"><em></em>' + data.atta.attachName + '</a>'
        $("#file").html(attaStr)
    }else {
        var attaStr = ' <a class="file_tishi"><em></em>暂无附件</a>'
        $("#file").html(attaStr)
    }
    var alltypes = new Array();
    var temp = null;
    var total = 0;
    var s = "";
    for (var i = 0; i < data.wdpf.length; i++) {
        total += parseInt(formatNum(data.wdpf[i].df));
        alltypes.push(data.wdpf[i].zbfl);
        if (temp != null) {
            if (temp.zbfl != data.wdpf[i].zbfl) {
                s += '  <tr> <td class="wd115 text_hide" id="type' + data.wdpf[i].zbfl + '">' + data.wdpf[i].zbflStr + '</td> ' +
                    '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                    '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                    '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                    '<td class="dafen_td wd80 text_hide"> <div class="pr">' + formatNum(data.wdpf[i].df) + '分</div> </td> ' +
                    ' </tr>';
                types.push(data.wdpf[i].zbfl);
                typeStrs.push(data.wdpf[i].zbflStr)
            } else {
                s += '  <tr>' +
                    '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                    '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                    '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                    '<td class="dafen_td wd80 text_hide"> <div class="pr">' + formatNum(data.wdpf[i].df) + '分</div> </td> ' +
                    ' </tr>';
            }
        } else {
            s += '  <tr> <td class="wd115 text_hide" id="type' + data.wdpf[i].zbfl + '">' + data.wdpf[i].zbflStr + '</td> ' +
                '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                '<td class="dafen_td wd80 text_hide"> <div class="pr">' + formatNum(data.wdpf[i].df) + '分</div> </td> ' +
                ' </tr>';
            types.push(data.wdpf[i].zbfl);
            typeStrs.push(data.wdpf[i].zbflStr)
        }
        temp = data.wdpf[i];
    }
    $("#total").text(total + "分");
    $("#table").append(s);
    for (var i = 0; i < types.length; i++) {
        var id = "#type" + types[i];
        $(id).attr("rowspan", getSize(types[i], alltypes));
    }

}


$(function () {
    if($("#view_type").val()==1){
        $("#dh").text("> 考核进度 > 查看自述自评")
    }else if($("#view_type").val()==2) {
        $("#dh").text("> 考核成绩 > 查看自述自评")
    }else {
        $("#dh").text("> 查看自述自评")
    }

    var dataParam = {
        "tid": $("#tid").val(),
        "userId":$("#userId").val()
    }
    $.ajax({
        url: basePath + "performance/setup_getZiPing.action",
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            if (data != null) {
                painting(data)
            } else {
                art.dialog.alert("错误");
            }

        }
    });
    $.ajax({
        url : basePath+"performance/setup_getAppraisalInfo.action",
        type : 'post',
        dataType : 'json',
        data : dataParam,
        success : function(data) {
            if(data!=null){
                $("#khmc").text(data.khmc);
            }else {
                art.dialog.alert("错误");
            }

        }
    });

})

function goback() {
    if($("#view_type").val()==1){
        parent.changeIframe(basePath+"logined/appraisal/jsp/admin/checkProgress.jsp?tid=" + $("#tid").val());

    }else if($("#view_type").val()==2) {
        parent.changeIframe(basePath+"logined/appraisal/jsp/staff/leadResultAppr.jsp?tid="+$("#tid").val()+"&view_type=1");
    }else {
        parent.changeIframe(basePath+"logined/appraisal/jsp/staff/leadResultAppr.jsp?tid=" + $("#tid").val());
    }
}