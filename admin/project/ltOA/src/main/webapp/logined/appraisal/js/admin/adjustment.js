/**
 * Created by lilipo on 2017/1/3.
 */
var p_name,p_group,p_ysdf,p_zzdf,p_pj,in_tzfz,text_tzyy;
var ysdf = 0;
var zzdf = 0;
var pjfs = new Array();
var pjStr = new Array();
$(function () {
    p_name = $("#p_name");
    p_group = $("#p_group");
    p_ysdf = $("#p_ysdf");
    p_zzdf = $("#p_zzdf");
    p_pj = $("#p_pj");
    in_tzfz = $("#in_tzfz");
    text_tzyy = $("#text_tzyy");

    var dataParam = {
        "at.vid": $("#tid").val(),
        "khry": $("#khry").val()
    }
    $.ajax({
        url: basePath + "performance/task_findHierarchicalIndicators.action",
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            for (var i = 0; data.ahiList.length > i; i++) {
                pjfs.push(data.ahiList[i].jsf);
                pjStr.push(data.ahiList[i].mc);
            }
        }
    });

    var data = {
        "atr.tid": $("#tid").val(),
        "atr.khryId": $("#khry").val()
    }
    $.ajax({
        url: basePath + "performance/task_findTaskResult.action",
        type: 'post',
        dataType: 'json',
        data: data,
        success: function (data) {
            if(data!=""){
                var temp = 0;
                ysdf = data.fz;
                p_name.text(data.khryName);
                p_group.text(data.khryGroupName);
                p_ysdf.text(ysdf+"分");
                if(typeof (data.tzfz) !="undefined" ){
                    temp = data.tzfz;
                }
                p_zzdf.text(parseInt(ysdf)+parseInt(temp)+"分");
                p_pj.text(formJB(data.jb));
            }else {
                art.dialog.alert("请求出错！");
            }
        }
    });
})
function formFz(val) {
    if(val==""){
        return 0;
    }else {
        return parseInt(val);
    }
}

function formJB(val) {
    if(typeof (val)=="undefined"){
        return "不及格";
    }else {
        return val;
    }
}

var isSubmit = false;//防止重复提交
function post() {
	if(isSubmit){
		return;
	}
	isSubmit = true;
    if(formFz($("#in_tzfz").val()) == 0){
        art.dialog.alert("请输入调整分值！");
        isSubmit = false;
        return;
    }
    if($("#text_tzyy").val() == ""){
        art.dialog.alert("请输入调整原因！");
        isSubmit = false;
        return;
    }
    var dataParam = {
        "atr.tid": $("#tid").val(),
        "atr.khryUserInfo.userId": $("#khry").val(),
        "atr.tzfz": formFz($("#in_tzfz").val()),
        "atr.tzyy": $("#text_tzyy").val(),

    }
    $.ajax({
        url: basePath + "performance/task_updateTaskResult.action",
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            if(data == "1"){
                var refresh = art.dialog.data("refresh");
                refresh();
            }else if(data == "2") {
                art.dialog.alert("考核已结束，无法调整分数！");
                isSubmit = false;
            }else {
                art.dialog.alert("保存出错！");
                isSubmit = false;
            }
        }
    });
}
function checktzfz(val) {
    if (typeof (val) =="undefined"||val == ""){
        val = 0;
    }
    zzdf = ysdf+parseInt(val);
    if(zzdf<0){
        art.dialog.alert("最终的分不能为负分，请重新调整！");
        $("#in_tzfz").val("");
    }else if(zzdf >100) {
        art.dialog.alert("最终的分不能为超过100分，请重新调整！");
        $("#in_tzfz").val("");
    }else {
        p_zzdf.text(zzdf+"分");
        for (var i = 0; i < pjfs.length-1; i++) {
            if (zzdf > pjfs[i]&&zzdf < pjfs[i+1]) {
                $("#p_pj").text(pjStr[i+1]);
            }
        }
    }
}

function goBack() {
    window.opener=null;
    window.open('','_self');
    window.close();
}