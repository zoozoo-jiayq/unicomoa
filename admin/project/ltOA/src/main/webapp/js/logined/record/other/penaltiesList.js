/**
 * Created by lilipo on 2016/12/20.
 */
$(function(){
    getTable();
})
function getTable() {
    qytx.app.grid({
        id:'dataTable_record',
        url:basePath + "logined/recordOther/penaltiesList.action?userId="+userId,//
        iDisplayLength:	15, // 每页显示多少行
        valuesFn:[
            {
                "aTargets": [6],// 覆盖第5列
                "fnRender": function ( oObj ) {
                    var id = oObj.aData.id;
                    var html='<a href="javascript:void(0);" onclick="findDetail('+id+')">查看</a>';
                    html +='<a href="javascript:void(0);" onclick="toAddOrUpdate('+id+')">修改</a>';
                    return   html;
                }
            }
        ]
    });
}

function toAddOrUpdate(penaltiesId) {
    if(penaltiesId){
        openUrl = basePath+"/logined/recordOther/penaltiesFindDetail.action?penaltiesId="+penaltiesId+"&type=2";
    }else{
        openUrl = basePath+"/logined/recordOther/toAddPenalties.action?userId="+userId;
    }
    window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

/**
 * 查看详情
 */
function findDetail(penaltiesId){
    var openUrl = basePath+"/logined/recordOther/penaltiesFindDetail.action?penaltiesId="+penaltiesId+"&type=1";
    window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}