var ue;
$(function(){
    /*加载ueditor*/
    ue = UE.getEditor('contentInfo', {
        initialFrameWidth : "100%",
        toolbars:[[
            "bold","underline","strikethrough","forecolor","backcolor","justifyleft",
            "justifycenter","justifyright","justifyjustify","customstyle","paragraph","fontsize","insertimage","date","time"]]
    });
    var penaltiesId = $("#penaltiesId").val();
    if(penaltiesId){
        $("#type").val(temp_nature);
        $("#limitRemark").empty().text(255-temp_remark.length);
        ue.addListener("ready", function () {
            // editor准备好之后才可以使用
            ue.setContent(temp_explain);
        });
    }

});

function deleteAttachment_daily(attachmentId, domAObj) {
    $(domAObj).parent().parent().parent().remove();
    var attachmentIdAll = $("#attachmentId").val();
    attachmentIdAll = attachmentIdAll.replace("," + attachmentId + ",", ",");
    $("#attachmentId").val(attachmentIdAll);
}

function addOrUpdate(obj){
	$(obj).attr("disabled",true);
    var project = $("#project").val();
    if(typeof(project) == "undefined" || project.trim() == ""){
        qytx.app.valid.showObjError($("#project"), 'penalties.penalties_project_not_null');
        $(obj).attr("disabled",false);
        return;
    }else{
        qytx.app.valid.hideError($("#project"));
    }
    var penaltiesDate = $("#penaltiesDate").val();
    if(!penaltiesDate){
        qytx.app.valid.showObjError($("#penaltiesDateHidden"), 'penalties.penalties_date_not_null');
        $(obj).attr("disabled",false);
        return;
    }else{
        qytx.app.valid.hideError($("#penaltiesDateHidden"));
    }
    var type = $("#type").val();
    if(!type){
        qytx.app.valid.showObjError($("#type"), 'penalties.penalties_type_not_null');
        $(obj).attr("disabled",false);
        return;
    }else{
        qytx.app.valid.hideError($("#type"));
    }
    var penalties_money = $("#penalties_money").val();
    if(penalties_money > 99999999){
        qytx.app.valid.showObjError($("#penalties_money"), 'penalties.penalties_penalties_money_not_null');
        $(obj).attr("disabled",false);
        return;
    }else if(isNaN(penalties_money)){
        qytx.app.valid.showObjError($("#penalties_money"), 'penalties.penalties_penalties_money_not_number');
        $(obj).attr("disabled",false);
        return;
    }else {
        qytx.app.valid.hideError($("#penalties_money"));
    }

    var penaltiesId = $("#penaltiesId").val();
    var wagesMonth = $("#wagesMonth").val();
    var remark = $("#remark").val();
    var explain=ue.getContent();
    var attment = $("#attachmentId").val();
    if (null == attment || "," == attment || "" == attment) {
        attment = "";
    }
    var param = {
        "penaltiesId":penaltiesId,
        "recordPenalties.userInfo.userId":$("#userId").val(),
        "recordPenalties.project":project,
        "recordPenalties.penaltiesDate":penaltiesDate,
        "recordPenalties.wagesMonth":wagesMonth,
        "recordPenalties.penalties_money":penalties_money,
        "recordPenalties.remark":remark,
        "recordPenalties.nature":type,
        "recordPenalties.explain":explain,
        "recordPenalties.attment":attment,
    };
    var url = basePath+"/logined/recordOther/penaltiesSaveOrUpdate.action";
    $.ajax({
        url:url,
        type:'POST',
        data:param,
        dataType:'json',
        success:function(result){
            if(result=="1"){
                art.dialog.tips("添加成功!");
                setTimeout(function(){
                    window.close();
                },1000);
            }else if(result=="2"){
                art.dialog.tips("修改成功!");
                setTimeout(function(){
                    window.close();
                },1000);
            }else{
                art.dialog.alert("操作失败!");
            }
        }
    });


}
