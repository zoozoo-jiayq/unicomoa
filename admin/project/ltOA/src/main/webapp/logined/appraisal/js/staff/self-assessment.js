/**
 * Created by lilipo on 2016/12/27.
 */
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

function FormatMS(val) {
    if(val == ''){
        return "无";
    }else {
        return val;
    }
}

function painting(data) {
    var wdsz = data.wdsz;
    $("#wdsz").val(wdsz);
    if (data.atta) {
        $("#file_upload").hide();
        var attaStr = '<li><div class="icon"></div><div class="txt"><p style="margin-top:15px;margin-right:15px;display:inline-block;" class="file_tishi"><em></em>' + data.atta.attachName + '</p><a style="color:#ff5454;cursor:pointer"  onclick="deleteAttachment_record(' + data.atta.id + ',this)">删除</a></p></li>'
        $("#attachmentList").html(attaStr)
        $("#attachmentId").val(data.atta.id)
    }
    var alltypes = new Array();
    var temp = null;
    var s = "";
    var total = 0;
    for (var i = 0; i < data.wdpf.length; i++) {
        if (data.wdpf[i].df) {
            total += parseFloat(data.wdpf[i].df);
        }
        var df = (typeof(data.wdpf[i].df)=="undefined")?"":data.wdpf[i].df;
        alltypes.push(data.wdpf[i].zbfl);
        if (temp != null) {
            if (temp.zbfl != data.wdpf[i].zbfl) {
                s += '  <tr> <td class="wd115 text_hide" id="type' + data.wdpf[i].zbfl + '">' + data.wdpf[i].zbflStr + '</td> ' +
                    '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                    '<td class="wd200 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                    '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                    '<td class="wd80 text_hide"> <input type="text" maxlength="5" id="khx_' + data.wdpf[i].vid + '" eid = "' + data.wdpf[i].eid + '" maxfs="' + data.wdpf[i].fz + '" name="' + data.wdpf[i].vid + '" value="' + df + '" class="creat_list_input percent70 text_center fontBold" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)" onblur="checkfz(this)"> ' +
                    '</td>' +
                    ' </tr>';
                types.push(data.wdpf[i].zbfl);
                typeStrs.push(data.wdpf[i].zbflStr)
            } else {
                s += '  <tr>' +
                    '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                    '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                    '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                    '<td class="wd80 text_hide"> <input id="khx_' + data.wdpf[i].vid + '" maxlength="5" eid = "' + data.wdpf[i].eid + '" maxfs="' + data.wdpf[i].fz + '" name="' + data.wdpf[i].vid + '" type="text" value="' + df + '" class="creat_list_input percent70 text_center fontBold" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)" onblur="checkfz(this)"> ' +
                    ' </td>' +
                    ' </tr>';
            }
        } else {
            s += '  <tr> <td class="wd115 text_hide" id="type' + data.wdpf[i].zbfl + '">' + data.wdpf[i].zbflStr + '</td> ' +
                '<td class="wd165 text_hide">' + data.wdpf[i].zbmc + '</td> ' +
                '<td class="wd400 text_hide">' + FormatMS(data.wdpf[i].ms) + '</td> ' +
                '<td class="wd100 text_hide">' + data.wdpf[i].fz + '</td>' +
                '<td class="wd80 text_hide"> <input type="text"  id="khx_' + data.wdpf[i].vid + '" maxlength="5" eid = "' + data.wdpf[i].eid + '" maxfs="' + data.wdpf[i].fz + '" name="' + data.wdpf[i].vid + '" value="' + df + '" class="creat_list_input percent70 text_center fontBold" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)" onblur="checkfz(this)"> ' +
                ' </td>' +
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

}
function formatNum(val) {
    if(val == ""){
        return 0;
    }else {
        return parseFloat(val);
    }
}

$(function () {
    var dataParam = {
        "tid": $("#tid").val(),
        "zt": $("#zt").val()
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
                alert("错误");
            }

        }
    });


})

function formatNum(val) {
    if(val == ""){
        return 0;
    }else {
        return parseFloat(val);
    }
}
function checkfz(obj) {
    var total=0;
    if (isNaN(obj.value)) {
        art.dialog.alert("打分请输入纯数字！");
        obj.value = '';
        $(obj).focus();
        return;
    }
    if (parseFloat(obj.value) > parseFloat(obj.getAttribute("maxfs"))) {
        art.dialog.alert("输入分数超过最大值请重新输入");
        obj.value = '';
        $(obj).focus()
        return;
    }
    var khxInputs = $("input[id^='khx_']");
    for (var i = 0; i < khxInputs.length; i++) {
        total +=formatNum($(khxInputs[i]).val());
    }
    $("#total").text(total.toFixed(0));

}
function getKhpfs() {
    /**
     * 考核评分
     * "考核项1id,考核模块1id,考核项1分值;考核项2id,考核模块2id,考核项2分值;考核项3id,考核模块3id,考核项3分值;..."
     */
    var khxInputs = $("input[id^='khx_']")
    var khpfs = "";
    for (var i = 0; i < khxInputs.length; i++) {
        if ($(khxInputs[i]).val() == "") {
            art.dialog.alert("请输入分数！");
            return;
        }
        khpfs += $(khxInputs[i]).prop('name') + "," + khxInputs[i].getAttribute("eid") + "," + $(khxInputs[i]).val() + ";";
    }
    return khpfs;
}

function getEids() {
    /**
     * 考核模块ids
     * 考核模块1id,考核模块1总分值;考核模块2id,考核模块2总分值;考核模块3id,考核模块3总分值
     */
    var eids = "";
    var khxInputs = $("input[id^='khx_']");
    var temp_total = 0;
    for (var j = 0; j < khxInputs.length; j++) {
        temp_total += parseFloat($(khxInputs[j]).val());
    }
    eids += khxInputs[0].getAttribute("eid") + "," + temp_total + ";";
    return eids;

}

var isSubmit = false;//防止重复提交
function post() {
	if(isSubmit){
		return;
	}
	isSubmit = true;
    if($("#wdsz").val()==""){
        art.dialog.alert("请填写自评自述！");
        isSubmit = false;
        return;
    }
    var dataParam = {
        "plVo.tid": $("#tid").val(),
        "plVo.pl": $("#wdsz").val(),
        "plVo.khpfs": getKhpfs(),
        "plVo.eids": getEids(),
        "plVo.fjid": $("#attachmentId").val()
    }
    $.ajax({
        url: basePath + "performance/setup_zpPreview.action",
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            if (data == 1) {
                parent.changeIframe(basePath+"logined/appraisal/jsp/staff/self-assessment_details.jsp?tid=" + $("#tid").val()+"&type=2"+"&zt=2");
            } else {
                art.dialog.alert("错误");
                isSubmit = false;
            }

        }
    });
}

function reset() {
    var khxInputs = $("input[id^='khx_']")
    for (var i = 0; i < khxInputs.length; i++) {
        $(khxInputs[i]).val("");
    }
    $("#total").text("0");
    $("#wdsz").val("");
}