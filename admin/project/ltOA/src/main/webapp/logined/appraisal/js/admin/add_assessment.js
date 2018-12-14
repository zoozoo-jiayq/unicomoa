/**
 * Created by lilipo on 2016/12/29.
 */
var selects = '<option value="">请选择</option>';
var selectData = '';
var appraisalExamArr ='';
var ispost = false;
var types = new Array();
//下拉
function formatSelects(data) {
    selectData = data;
    for(var i=0;i<data.length;i++){
        selects += '<option value="'+data[i].value+'">'+data[i].name+'</option>'
    }
}
//处理数字
function formatNum(val) {
    if(val == ""){
        return 0;
    }else {
        return parseFloat(val);
    }
}

//分割 type
function formatType(val) {
    types = val.split(",");
    return types;
}
// 获取指标分类下拉项  参数 1： 获取参考指标 2： 获取模块指标 其他 ：添加空行
function khzbfl(zb) {
    var dataParam = {
        "infoType":"appraisal_index",
        "sysTag":1
    };
    $.ajax({
        url : basePath+"dict/getDicts.action",
        type : 'post',
        dataType : 'json',
        data : dataParam,
        success : function(data) {
            if(data!=null){
                formatSelects(data);
            }else {
                art.dialog.alert("错误");
            }
        },
        complete:function () {
            switch(zb)
            {
                case 1:
                    //参考指标
                    getckzb();
                    break;
                case 2:
                    //模块指标
                    getmkzbxx();
                    break;
                default:
                    addTr();
            }
        }
    });
}

//获取参考指标信息
function getckzb() {
    var datackzbxx = {
        "at.vid":$("#ck_vid").val()
    };
    $.ajax({
        url : basePath+"performance/task_findAppraisalExam.action",
        type : 'post',
        dataType : 'json',
        data : datackzbxx,
        success : function(data) {
            if(data!=0){
                addTrByVid(data)
            }else {
                addTr();
                art.dialog.alert("错误");
            }
        }
    });

}

//获取本模块指标信息
function getmkzbxx() {
    var datackzbxx = {
        "at.vid":$("#vid").val()
    };
    $.ajax({
        url : basePath+"performance/task_findAppraisalExam.action",
        type : 'post',
        dataType : 'json',
        data : datackzbxx,
        success : function(data) {
            if(data!=0){
                addTrByVid(data)
            }else {
                addTr();
                art.dialog.alert("错误");
            }
        }
    });
}

$(function(){
    formatType($("#type").val());

    if(types[1] == "1"){
        khzbfl(2);
    }else {
        if($("#ck_vid").val()!=''){
            khzbfl(1);
        }else {
            khzbfl(-1);
        }
    }
})
function processZbfl(val) {
    var zbfl = '<option value="">请选择</option>';
    for(var i=0;i<selectData.length;i++){
        if(val == selectData[i].value ){
            zbfl += '<option selected = "selected" value="'+selectData[i].value+'">'+selectData[i].name+'</option>'
        }else {
            zbfl += '<option value="'+selectData[i].value+'">'+selectData[i].name+'</option>'
        }
    }
    return zbfl;
}

function addTrByVid(data) {
    var total = 0;
    for(var i=0;i<data.length;i++){
        var appra = data[i];
        var trStr = '  <tr class="odd"> ' +
            '<td class="wd110"> <input type="checkbox"> </td>' +
            ' <td> <input type="text" maxlength="50" class="table_input" value="'+appra.zbmc+'"> </td> ' +
            '<td> <input type="text" maxlength="3" class="table_input" value="'+appra.xh+'"  onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)"> </td> ' +
            '<td class="wd200"> <select class="table_select"> '+processZbfl(appra.zbfl)+' </select> </td> ' +
            '<td class="wd100"> <input type="text" maxlength="3" name="fz" value="'+appra.fz+'" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9.]+/,\'\');}).call(this)" onblur="checkfz(this)" class="creat_list_input percent70 text_center"></td>' +
            ' <td> <input type="text" maxlength="50" class="table_input" value="'+appra.ms+'"> </td> ' +
            '</tr>';
        $("#table").append(trStr);
        total += formatNum(appra.fz);
    }
    $("#total").text(total.toFixed(0));
}


function addTr() {
    var trStr = '  <tr class="odd"> ' +
        '<td class="wd110"> <input type="checkbox"> </td>' +
        ' <td> <input type="text" maxlength="50" class="table_input"> </td> ' +
        '<td> <input type="text" maxlength="3" class="table_input" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)"> </td> ' +
        '<td class="wd200"> <select class="table_select" onchange="selectChange(this.value)"> '+selects+' </select> </td> ' +
        '<td class="wd100"> <input maxlength="3" type="text" name="fz" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9.]+/,\'\');}).call(this)" onblur="checkfz(this)" class="creat_list_input percent70 text_center">  </td>' +
        ' <td> <input type="text" maxlength="50" class="table_input"> </td> ' +
        '</tr>';
    $("#table").append(trStr);
}

function selectChange(value) {
    selects = '<option value="">请选择</option>';
    for(var i=0;i<selectData.length;i++){
        if(value == selectData[i].value ){
            selects += '<option selected = "selected" value="'+selectData[i].value+'">'+selectData[i].name+'</option>'
        }else {
            selects += '<option value="'+selectData[i].value+'">'+selectData[i].name+'</option>'
        }
    }
}

function delTr(val) {
    var trArr = new Array();
    var trList = $("#table").children("tr")
    for (var i=0;i<trList.length;i++) {
        var tdArr = trList.eq(i).find("td");
        if(typeof (tdArr.eq(0).find("input").attr('checked')) == val){
            trArr.push(trList.eq(i));
        }
    }
    if(trArr.length > 0){
        for (var i=0;i<trArr.length;i++) {
            trArr[i].remove()
        }
    }else {
        art.dialog.alert("请选择要删除的指标！");
    }
    $("#total").text(sumFz().toFixed(0));
}

function formatAppraisalExam() {
    var appraisalExamArray = new Array();
    var appraisalExam = {};
    var appraisalIndicatorsArray = new Array();
    var trList = $("#table").children("tr")
    for (var i=0;i<trList.length;i++) {
        var tdArr = trList.eq(i).find("td");
        var  zbmc= tdArr.eq(1).find("input").val();//指标名称
        var  xh= tdArr.eq(2).find("input").val();//序号
        var  znfl= tdArr.eq(3).find("select").val();//指标分类
        var  fz= tdArr.eq(4).find("input").val();//分值
        var  ms= tdArr.eq(5).find("input").val();//描述
        if(zbmc==""||xh==""||znfl==""||fz==""){
            ispost = false;
            return ;
        }
        var appraisalIndicators =  new Object();
        appraisalIndicators.zbmc = zbmc;
        appraisalIndicators.xh = xh;
        appraisalIndicators.zbfl = znfl;
        appraisalIndicators.fz = fz;
        appraisalIndicators.ms = ms;
        appraisalIndicatorsArray.push(appraisalIndicators);
        ispost = true;
    }
    appraisalExam.idtList = appraisalIndicatorsArray;
    appraisalExamArray.push(appraisalExam);
    appraisalExamArr = JSON.stringify(appraisalExamArray);
}

function sumFz() {
    var total=0;
    var khxInputs = $("input[name^='fz']");
    for (var i = 0; i < khxInputs.length; i++) {
        total +=formatNum($(khxInputs[i]).val());
    }
    return total;
}

function checkfz(obj) {
    if (isNaN(obj.value)) {
        art.dialog.alert("分值请输入纯数字！");
        obj.value = '';
        $(obj).focus();
        return;
    }
    if (obj.value == 0) {
        art.dialog.alert("分值不能为0！");
        obj.value = '';
        return;
    }
    if(sumFz().toFixed(0) > 100){
        art.dialog.alert("指标得分不能超过100分");
        obj.value = '';
        $(obj).focus();
    }else {
        $("#total").text(sumFz().toFixed(0));
    }


}

function goBack() {
    art.dialog.confirm("页面数据可能会丢失，是否继续？",function () {
        window.location.href =basePath+"logined/appraisal/jsp/admin/add_createNewCheck.jsp?vid="+$("#vid").val()+"&ck_vid="+$("#ck_vid").val()+"&type="+types.toString();
    });
}

function reset() {
    var trList = $("#table").children("tr");
    if(trList.length > 0){
        delTr("undefined");
    }
    addTr();
}

var isSubmit = false;//防止重复提交
function post() {
    if(isSubmit){
        return;
    }
    isSubmit = true;
    var trList = $("#table").children("tr");
    if(trList.length == 0){
        art.dialog.alert("至少需要一条考核指标！");
        isSubmit = false;
        return;
    }else {
        var td1 = trList.eq(0).find("td");
        var  zbmc= td1.eq(1).find("input").val();//指标名称
        var  xh= td1.eq(2).find("input").val();//序号
        var  znfl= td1.eq(3).find("select").val();//指标分类
        var  fz= td1.eq(4).find("input").val();//分值
        var  ms= td1.eq(5).find("input").val();//描述
        if(zbmc==""&&xh==""&&znfl==""&&fz==""){
            art.dialog.alert("指标填写有误！");
            isSubmit = false;
            return ;
        }
    }
    formatAppraisalExam();
    if(ispost){
        if(sumFz()!= 100){
            art.dialog.alert("考核总分需为100分!");
            isSubmit = false;
            return;
        }
        var paramData = {
            'appraisalExamArr' : appraisalExamArr,
            'at.vid' : $("#vid").val()
        };
        $.ajax({
            url : basePath + "performance/task_updateAppraisalExam.action",
            type : "post",
            dataType : 'json',
            data : paramData,
            success : function(data) {
                if(data == 1){
                    types[1] = 1;
                    window.location.href =basePath+"logined/appraisal/jsp/admin/add_relationship.jsp?vid="+$("#vid").val()+"&ck_vid="+$("#ck_vid").val()+"&type="+types.toString();
                }else {
                    art.dialog.alert("提交出错");
                    isSubmit = false;
                }
            }
        });


    }else {
        art.dialog.alert("指标填写有误！");
        isSubmit = false;
    }


}

