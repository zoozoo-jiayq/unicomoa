/**
 * 用户树js
 **/
var callbackFunc;//回调函数
/**
 * 选择人员
 */
function openSelectTreeUser(callback, bashPathSrc, defaultSelect)
{
	var urlStr =  basePath+"user/selectTreeUser.action";
    callbackFunc=callback;
    var setting = {
        check: {enable: false,chkboxType: {"Y":"ps", "N":"ps"}},
        view: {dblClickExpand: false},
        data: { simpleData: { enable: true}},
        callback: {onClick: zTreeOnCheck}
    };
    var searchName= $("#searchName").val();
    searchName="";
    var dataParam={
        'type':4,
        'searchName':searchName,
        'showType':2
        };
        $.ajax({
            url : urlStr,
            type : 'post',
            dataType :'json',
            data:dataParam,
            success : function(data) {
            	var treeObj = $.fn.zTree.init($("#groupUserTree"), setting, data);
            	var zTree_Menu = $.fn.zTree.getZTreeObj("groupUserTree");
                var node = treeObj.getNodeByTId("groupUserTree_1");
                treeObj.expandNode(node,true);
                var nodes = treeObj.transformToArray(treeObj.getNodes());
                if (null != defaultSelect && undefined != defaultSelect || "" != defaultSelect ){
                	// 修改时默认需要刷新左边的树					
	                for(var i=0;i<nodes.length;i++){	                	
	                	if (nodes[i].id==defaultSelect){
	                		zTree_Menu.selectNode(nodes[i]);
	                		treeObj.expandNode(nodes[i], true);
	                		break;              	
	                	}
	                }
                }
            }
        });
   }
function zTreeOnCheck(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
    var node = treeObj.getNodeByTId(treeNode.tId);
    //获取部门信息
    if(node.id.substr(0,4)=="gid_")
    {
        //如果是部门，则保存到map里面
        var treeNode=new TreeNode();
        var id=node.id.substr(4);
        treeNode.setId(id);
        treeNode.setData(node.obj);
        treeNode.setName(node.name);
        treeNode.setType("group");
        callbackFunc(treeNode);
    }
};