var vid = "";//主键
var ck_vid = "";//参考考核id
var type = "";//进度
$(document).ready(function(){
    vid=$("#vid").val();
    ck_vid=$("#ck_vid").val();
    type=$("#type").val();

    if(type.split(",")[2]==0){//第一次进入该页面
        if(ck_vid){//有参考考核
            findByVid(ck_vid);
        }
    }else{//不是第一次进入该页面
        findByVid(vid);
    }

});

/**
 * 查询考核信息
 */
var findByVid = function(vid){
    $.ajax({
        url : basePath+"performance/setup_getAppraisalInfo.action",
        type : 'post',
        dataType : 'json',
        data : {"tid":vid},
        success : function(data) {
            $("#bkhryUserIds").val(data.khry);
            $("#bkhryUserNames").val(data.khryStr);
            $("#pfryUserIds").val(data.dfry);
            $("#pfryUserNames").val(data.dfryStr);
            $("#dcldUserIds").val(data.shry);
            $("#dcldUserNames").val(data.shryStr);
        }
    });
}

/**
 * 下一步
 */
var isSubmit = false;//防止重复提交
var nextStep = function(){
    if(isSubmit){
        return;
    }
    isSubmit = true;
    var khry = $("#bkhryUserIds").val();
    if(!khry){
        art.dialog.alert("请选择被考核人员！");
        isSubmit = false;
        return;
    }
    var dfry = $("#pfryUserIds").val();
    if(!dfry){
        art.dialog.alert("请选择评分人员！");
        isSubmit = false;
        return;
    }
    var shry = $("#dcldUserIds").val();
    if(!shry){
        art.dialog.alert("请选择督查领导！");
        isSubmit = false;
        return;
    }
    if(khry==dfry && khry.split(",").length==3){
        art.dialog.alert("评分与被评分不能同时为一个人员且相等！");
        isSubmit = false;
        return;
    }
    var param={
        "at.vid":vid,
        "at.khry":khry,
        "at.dfry":dfry,
        "at.shry":shry
    }
    $.ajax({
        url : basePath+"performance/task_updateAppraisalUser.action",
        type : 'post',
        dataType : 'json',
        data : param,
        success : function(data) {
            if(data==1){//成功
                //跳转到下个页面
                type = type.split(",")[0]+","+type.split(",")[1]+",1,"+type.split(",")[3];
                var url=basePath+"logined/appraisal/jsp/admin/add_graderNormal.jsp?vid="+vid+"&ck_vid="+ck_vid+"&type="+type;
                window.location.href=url;
            }else{
                art.dialog.alert("提交失败，请重试！");
                isSubmit = false;
            }
        }
    });
}

/**
 * 上一步
 */
var backStep = function(){
    art.dialog.confirm("页面数据可能会丢失，是否继续？",function () {
        window.location.href=basePath+"logined/appraisal/jsp/admin/add_assessment.jsp?vid="+vid+"&ck_vid="+ck_vid+"&type="+type;
    })
}

//被考核人员
/**
 * 选择被考核人员
 */
function selecBkhry() {
    openSelectUser(3, bkhryUserCallBack, null,$("#bkhryUserIds").val(), 'appraisal');
}
/**
 * 被考核人员添加按钮(回调函数)
 * @param data
 * @return
 */
function bkhryUserCallBack(data) {
    var ids = '';
    var names = '';
    data.forEach(function(value, key) {
        ids += value.Id + ',';
        names += value.Name + ',';
    });
    //下面对超级管理员特殊处理
    if(ids=="1,"){//只选超级管理员
        art.dialog.alert("超级管理员不参加考核！");
        return;
    }
    if(ids){
        ids = ','+ids;
        ids = ids.replace(',1,',',');
        names = ','+names;
        names = names.replace(',超级管理员,',',');
        names = names.substring(1,names.length-1);
    }
    $("#bkhryUserIds").val(ids);
    $("#bkhryUserNames").val(names);
}
/**
 * 清空被考核人员
 * @param obj
 * @return
 */
function clearBkhry() {
    $("#bkhryUserIds").val('');
    $("#bkhryUserNames").val('');
}



//评分人员
/**
 * 选择评分人员
 */
function selecPfry() {
    openSelectUser(3, pfryUserCallBack, null,$("#pfryUserIds").val(), 'appraisal');
}
/**
 * 评分人员添加按钮(回调函数)
 * @param data
 * @return
 */
function pfryUserCallBack(data) {
    var ids = '';
    var names = '';
    data.forEach(function(value, key) {
        ids += value.Id + ',';
        names += value.Name + ',';
    });
    //下面对超级管理员特殊处理
    if(ids=="1,"){//只选超级管理员
        art.dialog.alert("超级管理员不参加考核！");
        return;
    }
    if(ids){
        ids = ','+ids;
        ids = ids.replace(',1,',',');
        names = ','+names;
        names = names.replace(',超级管理员,',',');
        names = names.substring(1,names.length-1);
    }
    $("#pfryUserIds").val(ids);
    $("#pfryUserNames").val(names);
}
/**
 * 清空评分人员
 * @param obj
 * @return
 */
function clearPfry() {
    $("#pfryUserIds").val('');
    $("#pfryUserNames").val('');
}


//督查领导
/**
 * 选择督查领导
 */
function selecDcld() {
    openSelectUser(3, dcldUserCallBack, null,$("#dcldUserIds").val(), 'appraisal');
}
/**
 * 督查领导添加按钮(回调函数)
 * @param data
 * @return
 */
function dcldUserCallBack(data) {
    var ids = '';
    var names = '';
    data.forEach(function(value, key) {
        ids += value.Id + ',';
        names += value.Name + ',';
    });
    //下面对超级管理员特殊处理
    if(ids=="1,"){//只选超级管理员
        art.dialog.alert("超级管理员不参加考核！");
        return;
    }
    if(ids){
        ids = ','+ids;
        ids = ids.replace(',1,',',');
        names = ','+names;
        names = names.replace(',超级管理员,',',');
        names = names.substring(1,names.length-1);
    }
    $("#dcldUserIds").val(ids);
    $("#dcldUserNames").val(names);
}
/**
 * 清空督查领导
 * @param obj
 * @return
 */
function clearDcld() {
    $("#dcldUserIds").val('');
    $("#dcldUserNames").val('');
}
