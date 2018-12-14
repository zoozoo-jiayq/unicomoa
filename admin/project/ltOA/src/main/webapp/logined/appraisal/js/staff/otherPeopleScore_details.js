/**
 * Created by lilipo on 2016/12/28.
 */
/**
 * Created by lilipo on 2016/12/27.
 */
function format(val) {
    return (typeof(val)=="undefined")?"":val;
}
function formatNum(val) {
    return (typeof(val)=="undefined")?0:parseInt(val);
}
function FormatMS(val) {
    if(val == ''){
        return "无";
    }else {
        return val;
    }
}

function sxgd(){
    var lists=$(".pingjia_list li p");
    for(var i=0;i<lists.length;i++ ){
        var p = lists[i];
        var liH=$(p).height()+14;
        $(p).prev().height(liH);
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
    $("#khpy").text(FormatMS(data.khpy));
    $("#userName").text(format(data.userName));
    $("#groupName").text(format(data.groupName));
    $("#job").text(format(data.job));
    if (data.atta) {
        var attaStr = ' <a  onclick="downFileById(' + data.atta.id + ')" class="file_tishi"><em></em>' + data.atta.attachName + '</a>'
        $("#file").html(attaStr)
    }
    var alltypes = new Array();
    var temp = null;
    var total = 0;
    var s = "";
    for (var i = 0; i < data.wdpf.length; i++) {
        total += parseInt(data.wdpf[i].df);
        alltypes.push(data.wdpf[i].zbfl);
        if (temp != null) {
            if (temp.zbfl != data.wdpf[i].zbfl) {
                s += '  <tr> <td class="wd115 text_hide" id="type' + data.wdpf[i].zbfl + '">' + data.wdpf[i].zbflStr + '</td> ' +
                    '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                    '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                    '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                    '<td class="dafen_td wd80 text_hide"> <div class="pr">' + formatNum(data.wdpf[i].df) + '</div> </td> ' +
                    ' </tr>';
                types.push(data.wdpf[i].zbfl);
                typeStrs.push(data.wdpf[i].zbflStr)
            } else {
                s += '  <tr>' +
                    '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                    '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                    '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                    '<td class="dafen_td wd80 text_hide"> <div class="pr">' + formatNum(data.wdpf[i].df) + '</div> </td> ' +
                    ' </tr>';
            }
        } else {
            s += '  <tr> <td class="wd115 text_hide" id="type' + data.wdpf[i].zbfl + '">' + data.wdpf[i].zbflStr + '</td> ' +
                '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                '<td class="dafen_td wd80 text_hide"> <div class="pr">' + formatNum(data.wdpf[i].df) + '</div> </td> ' +
                ' </tr>';
            types.push(data.wdpf[i].zbfl);
            typeStrs.push(data.wdpf[i].zbflStr)
        }
        temp = data.wdpf[i];
    }
    $("#total").text(total);
    $("#table").append(s);
    for (var i = 0; i < types.length; i++) {
        var id = "#type" + types[i];
        $(id).attr("rowspan", getSize(types[i], alltypes));
    }
    sxgd();
}


$(function () {
    var dataParam = {
        "tid": $("#tid").val(),
        "userId":$("#userId").val()
    }
    //http://192.168.10.25:8081/cloudoa/performance/setup_getAppraisalOther.action?tid=2&userId=11111
    $.ajax({
        url: basePath + "performance/setup_getAppraisalOther.action",
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
    parent.changeIframe(basePath+"logined/appraisal/jsp/staff/otherPeopleScore.jsp?tid=" + $("#tid").val()+"&userId="+$("#userId").val());
}

var isSubmit = false;//防止重复提交
function postend() {
	if(isSubmit){
		return;
	}
	isSubmit = true;
    var dataParam = {
        "tid": $("#tid").val(),
        "khsj":$("#userId").val()
    }
    $.ajax({
        url: basePath + "performance/setup_submitKhOther.action",
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            //-1 无需要打分人员  0失败  其他：被考核人员id
            if(data == 0) {
                art.dialog.alert("保存失败");
                isSubmit = false;
            }else {
                parent.clickKhtr();
            }
        }
    });
}

var isSubmit = false;//防止重复提交
function post() {
	if(isSubmit){
		return;
	}
	isSubmit = true;
    var dataParam = {
        "tid": $("#tid").val(),
        "khsj":$("#userId").val()
    }
    $.ajax({
        url: basePath + "performance/setup_submitAddgContinue.action",
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            //-1 无需要打分人员  0失败  其他：被考核人员id           
            if (data == -1) {
                art.dialog.alert("无需要打分人员,将回到列表页面",function () {
                    parent.clickKhtr();
                });
            } else if(data == 0) {
                art.dialog.alert("保存失败");
                isSubmit = false;
            }else {
                parent.changeIframe(basePath+"logined/appraisal/jsp/staff/otherPeopleScore.jsp?tid=" + $("#tid").val()+"&userId="+data+"&view_type=1");
            }
        }
    });

}