/**
 * Created by lilipo on 2016/12/20.
 */
$(function(){
    getTable();
})
function getTable() {
    qytx.app.grid({
        id:'dataTable_record',
        url:basePath + "logined/recordOther/leaveList.action?recordLeave.userInfo.userId="+userId,//
        iDisplayLength:	15, // 每页显示多少行
        valuesFn:[
            {
                "aTargets": [5],// 覆盖第5列
                "fnRender": function ( oObj ) {
                    var id = oObj.aData.id;
                    var html='<a href="javascript:void(0);" onclick="findDetail('+id+')">查看</a>';
                    html +='<a href="javascript:void(0);" onclick="update('+id+')">修改</a>';
                    return   html;
                }
            }
        ]
    });
}

function toAddOrUpdate() {
    var param={
        "userId":userId
    }
    $.ajax({
        url:basePath+"/logined/recordOther/checkLeave.action",
        type:"post",
        dataType:"json",
        data:param,
        success:function(data){
            if(data=="1"){
                art.dialog.alert("已有离职信息！");
            }else {
                openUrl = basePath+"/logined/recordOther/toAddLeave.action?userId="+userId;
                window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
            }
        }

    });

}


/**
 * 修改
 */

function update(leaveId) {
    var openUrl = basePath + "/logined/recordOther/leaveDetail.action?leaveId=" + leaveId + "&type=2";
    window.open(openUrl, "", "scrollbars=yes,top=100,left=300, width=830, height=600")
}
/**
 * 查看详情
 */
function findDetail(leaveId){
    var openUrl = basePath+"/logined/recordOther/leaveDetail.action?leaveId="+leaveId+"&type=1";
    window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}