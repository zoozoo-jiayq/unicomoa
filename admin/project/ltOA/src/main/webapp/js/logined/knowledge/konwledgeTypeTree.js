jQuery(document).ready(function() {
//	//判断个人知识库和公共知识库，1表示个人，0表示公共
//	$("#isPrivate").val("1");
});
/**
 * 用户树js
 */
var callbackFunc;// 回调函数
/**
 * 选择人员
 */
function openSelectTreeUser(callback, bashPathSrc, defaultSelect) {
	var t = $("#containAll").val(); // containAll 如果 containAll等于0，在列表中显示知识库文件夹。否则，在添加知识库时不显不文件夹。
//	var isPrivate = $("#isPrivate").val();//判断个人知识库和公共知识库，1表示个人，0表示公共								
//	&isOnly=1  通用的时候加
	var urlStr = "";
	if (t == 0) {
		urlStr = basePath + "knowledge/knowledgeType_selectTreeKnowledge.action?columnId="+$("#columnId").val()+"&containAll=true&isPrivate="+0;
	}
	if (t == 1) {
		urlStr = basePath + "knowledge/knowledgeType_selectTreeKnowledge.action?columnId="+$("#columnId").val()+"&containAll=false&isPrivate="+0;
	}
	callbackFunc = callback;
	var setting = {
	    check : {
	        enable : false,
	        chkboxType : {
	            "Y" : "ps",
	            "N" : "ps"
	        }
	    }, // check ，enable 设置 zTree 的节点上是否显示 checkbox / radio ; chkboxType 勾选
			// checkbox 对于父子节点的关联关系
	    view : {
		    dblClickExpand : false
	    }, // view , dblClickExpand 双击节点时，是否自动展开父节点的标识
	    data : {
		    simpleData : {
			    enable : true
		    }
	    }, // data , simpleData 确定 zTree 初始化时的节点数据、异步加载时的节点数据、或 addNodes 方法中输入的
			// newNodes 数据是否采用简单数据模式 (Array)
	    callback : {
		    onClick : zTreeOnCheck
	    }
	// callback , onClick 用于捕获节点被点击的事件回调函数
	};
	var searchName = $("#searchName").val();
	searchName = "";
	var dataParam = {
	    'type' : 1,
	    'searchName' : searchName,
	    'showType' : 2
	};
	$.ajax({
	    url : urlStr,
	    type : 'post',
	    dataType : 'json',
	    data : dataParam,
	    success : function(data) {
		    var isToView = $("#isToView").val();
		    var node = null;
		    if (isToView != 1) {// 新增
			    var treeNodeId = $("#treeNodeId").val();
			    if (treeNodeId == '' || treeNodeId == null) {
			    	if($("#columnId").val()==46){
			    		treeNodeId = "kid_1";
			    	}else{
			    		treeNodeId = "kid_2";
			    	}
			    } 
			    var treeObj = $.fn.zTree.init($("#groupUserTree"), setting, data);
			    node = treeObj.getNodesByParam("id",treeNodeId,null)[0];
			    if(node){
			    	var id = node.id.substr(4);
				    var name = node.name;
				    $("#groupSel").val(name);
				    $("#parentId").val(id);
				    treeObj.expandNode(node, true);//展开/折叠选中的节点
				    treeObj.selectNode( node );//默认选中节点
			    }	

		    } else {
		    	// 修改
			    var treeObj = $.fn.zTree.init($("#groupUserTree"), setting, data);
			    var zTree_Menu = $.fn.zTree.getZTreeObj("groupUserTree");
			    var nodes = treeObj.transformToArray(treeObj.getNodes());
			    // 修改时默认选中类别
		 
			    for ( var i = 0; i < nodes.length; i++) {			 
				    if (nodes[i].id == "kid_" + defaultSelect) {
				    	zTree_Menu.selectNode(nodes[i]);
					    treeObj.expandNode(nodes[i], true);
					    break;
				    }
			    }
		    }
	    }
	});
}

/**
 * onClick 用于捕获节点被点击的事件回调函数
 * 
 * @param event
 *            标准的 js event 对象
 * @param treeId
 *            对应 zTree 的 treeId，便于用户操控
 * @param treeNode
 *            被点击的节点 JSON 数据对象
 */

function zTreeOnCheck(event, treeId, treeNode) {

	var treeObj = $.fn.zTree.getZTreeObj("groupUserTree"); // $.fn.zTree.getZTrddObj
															// zTree v3.x
															// 专门提供的根据 treeId 获取
															// zTree
															// 对象的方法。必须在初始化
															// zTree 以后才可以使用此方法。
													// zTree 的唯一标识 tId 快速获取节点
													// JSON 数据对象,通过内部的 cache
													// 获取，不需要遍历节点。请通过 zTree
													// 对象执行此方法。
	$("#treeNodeId").val(treeNode.id);
	var id = treeNode.id.substr(4);
	var name = treeNode.name;
	$("#groupSel").val(name);
	$("#parentId").val(id);
	showGroup();
	callbackFunc(treeNode);

	// 去掉报错信息
	// hideError($("#groupSel"));
};
