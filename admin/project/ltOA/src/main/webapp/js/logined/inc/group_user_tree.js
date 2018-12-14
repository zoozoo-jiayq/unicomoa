/**
 * 部门用户树js
 **/
var callbackFunc;//回调函数
/**
 * 选择部门或者人员
 */
function openSelectTreeGroupUser(callback, bashPathSrc) {
    var urlStr = "";
    if (null != bashPathSrc && "" != bashPathSrc) {
        urlStr = bashPathSrc + "user/selectTreeUser.action";
    } else {
        urlStr = basePath + "user/selectTreeUser.action";
    }
    callbackFunc = callback;
    var setting = {
        check: {enable: false, chkboxType: {"Y": "ps", "N": "ps"}},
        view: {dblClickExpand: false},
        data: { simpleData: { enable: true}},
        callback: {onClick: zTreeOnCheck}
    };
    var searchName = $("#searchName").val();
    searchName = "";
    var dataParam = {
        'type': 1,
        'searchName': searchName,
        'showType': 3
    };
    $.ajax({
        url: urlStr,
        type: 'post',
        dataType: 'json',
        data: dataParam,
        success: function (data) { 
            var treeObj = $.fn.zTree.init($("#groupUserTree"), setting, data);
            var node = treeObj.getNodeByTId("groupUserTree_1");
            treeObj.expandNode(node,true);

            var nodes = treeObj.transformToArray(treeObj.getNodes());
            var zTree_Menu = $.fn.zTree.getZTreeObj("groupUserTree");
            for(var i=0;i<nodes.length;i++){
            	if (nodes[i].id.indexOf("uid_")==0){
            		var nodesArr = treeObj.getNodesByParam("id", nodes[i].id, null);
            		var tempNode = nodesArr[0];                		
            		while(tempNode.getParentNode() != null){
            			treeObj.expandNode(tempNode,true);
            			tempNode=tempNode.getParentNode();
            	    }    
            		zTreeOnCheck(null, null, nodes[i]);
            		zTree_Menu.selectNode(nodes[i]);
            		break;              	
            	}
            }
        }
    });
}
function zTreeOnCheck(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
    var node = treeObj.getNodeByTId(treeNode.tId);
    var treeNodeCallBack = new TreeNode();
    var id = node.id.substr(4);
    treeNodeCallBack.setId(id);
    treeNodeCallBack.setData(node.obj);
    treeNodeCallBack.setName(node.name);
    //获取人员信息
    if (node.id.substr(0, 4) == "uid_") {
        //如果是人员，则保存到map里面
        treeNodeCallBack.setType("user");
    } else if (node.id.substr(0, 4) == "gid_") {
        //如果是群组，则保存到map里面
        treeNodeCallBack.setType("group");
    }
    callbackFunc(treeNodeCallBack);
};