$(document).ready(function(){
    getGroupTree();
});

/**
 * 生成部门树js
 **/

function getGroupTree()
{
    var setting = {
        check: {enable: false,chkboxType: {"Y":"ps", "N":"ps"}},
        view: {dblClickExpand: false,showLine:false,showIcon:false,showTitle:true},
        data: { simpleData: { enable: true},
                key:{title:'title'}
        },
        callback: {onClick: onTreeNodeClick}
    };
    var params={
        'showUserNum':1
    };
    var userType=$("#userType").val();
    var ajaxUrl=basePath+"group/getUnitTree.action";

    $.ajax({
        url : ajaxUrl,
        type : 'post',
        dataType :'json',
        data:params,
        success : function(data) {
            $.fn.zTree.init($("#groupUserTree"), setting, data);
            var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
            var node = treeObj.getNodeByParam("id", 0, null);
            if(node){
                //展开第一个节点
               treeObj.expandNode(node, true, false, true);
            }
        }
    });
}
/**
 * 部门树调用点击事件
 */
function onTreeNodeClick(e, treeId, treeNode) {
    var unitId=treeNode.id;
    var groupType=treeNode.type;
    var unit=treeNode.obj;
    $("#groupId").val(unitId);
    $("#groupType").val(groupType);
    if(unit!=null)
    {
        var unitName=unit.unitName;
        $("#groupName").val(unitName);
    }
    getDataTable();
}
