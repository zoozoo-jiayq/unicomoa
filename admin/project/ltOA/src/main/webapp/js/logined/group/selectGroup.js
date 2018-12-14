$(document).ready(function() {
	getGroupTree();
	
	$("#groupSel").click(function() {
		showGroup();
		return false;
	});
	
	//科室选择不能点击回车
    $("#groupSel").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
});

/**
 * 群组树调用群组树
 */
var showOrHide = true;
function showGroup(){
	$('#menuContent').toggle(showOrHide);
	//相当于
	if (showOrHide) {
		showOrHide=false;
		var groupObj = $("#groupSel");
		var groupOffset = $("#groupSel").position();
		$("#menuContent").css({left:groupOffset.left + "px", top:groupOffset.top + groupObj.outerHeight() - 1 + "px"}).show();
		$("#treeContent").one("mouseleave",function(){
			$("#menuContent").hide();
			showOrHide=true;
			return false;
        });
	} else {
	   $("#menuContent").hide();
	   showOrHide=true;
	}
}

/**
 * 获取群组的树结构
 */
function getGroupTree() {
	// 设置树样式
	// 开始隐藏
	$("#menuContent").hide();
    // 组织机构树参数设置
    var setting = {
        view: {dblClickExpand: false},
        data: { simpleData: {enable: true }},
        callback: {onClick: onTreeNodeClick}
    };
    var dataParam = {
            'type' : 1,
            'showType' :1
	};
    qytx.app.ajax({
		url : basePath+ "user/selectUser.action",
		type : 'post',
		dataType :'json',
		data:dataParam,
		success : function(data) {
            $.fn.zTree.init($("#groupTree"), setting, data);
			//选择部门
	        getGroup();
		}
	});
	/**
	 * 得到部门
	 */
	function getGroup(){
	    var parentId=$("#parentId").val();
		var zTree = $.fn.zTree.getZTreeObj("groupTree");
		var nodes = zTree.transformToArray(zTree.getNodes());
		for (var i=0; i<nodes.length; i++) {
			var gName = nodes[i].name;
			var gId = nodes[i].id;
			if(gId=="gid_"+parentId){   
				zTree.selectNode(nodes[i]);
				$("#groupSel").val(gName);
			}
		};
	}
	/**
	 * 群组树调用点击事件
	 */
	function onTreeNodeClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("groupTree");
		var nodes = zTree.getSelectedNodes();

		if (nodes.length > 0) {
			var groupName = nodes[nodes.length - 1].name;
			var groupId = nodes[nodes.length - 1].id;
			$("#parentId").val(groupId.substring(4, groupId.length));
			$("#groupSel").val(groupName);
			$("#menuContent").hide();
		}
		//根据部门名称得到当前节点
        var groupId=$("#groupId").val();
        var parentId=$("#parentId").val();
        if(parentId){
            if(groupId==parentId){
                $("#parentId").val("");
                $("#groupSel").val("");
                qytx.app.dialog.alert("不能选择本部门作为上级部门！");
                return;
            }
        }

        
        var currentNode=zTree.getNodesByParam("id","gid_"+groupId, null);
        if(currentNode.length>0){
            var childrenArr=getAllChildrenNodes(currentNode[0],[]);
            //不能选择子节点
            if($.inArray(parentId, childrenArr)!=-1){
                $("#parentId").val("");
                $("#groupSel").val("");
                qytx.app.dialog.alert("不能选择子部门作为上级部门！");
                return;
            }
        }
	}
    /**
     * 得到所有子节点
     */
    function getAllChildrenNodes(treeNode,result) {
        if (treeNode.isParent) {
            var childrenNodes = treeNode.children;
            if (childrenNodes) {
                for (var i = 0; i < childrenNodes.length; i++) {
                    result.push((childrenNodes[i].id).substring(4));
                    result = getAllChildrenNodes(childrenNodes[i], result);
                }
            }
        }
        return result;
    }


}