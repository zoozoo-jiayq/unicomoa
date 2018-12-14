<%--
   选择人员
  User: 黄普友
  Date: 13-3-16
  Time: 上午9:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择人员</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/selectMember/skins/selectMember_default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${ctx}flat/plugins/peopleTree/skins/tree_default.css" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>

<script type="text/javascript" src="${ctx}flat/js/smallTabSelectTree.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/peopleTree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/inputdefault.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>

</head>
<body  class="bg_white" >
 	<input type="hidden" id="defaultSelectId" value='${param.defaultSelectId}' />
		<div class="selectMember">
       <div class="member">
       <div class="tab">
         <ul>
           <li><a  href="javascript:void(0);">组织机构</a></li>
         </ul>  
       </div>
       <div class="tabContent"    >
          <ul id="groupUserTree" class="ztree">
			</ul>
       </div>
        
   </div>
</div>
		
		
		
		<div class="btn_box fl" style="display: none;">
			<span class="mainButton"> <input name="" onclick="addUser();"
				type="button" value="添加 >" /> </span> <span class="mainButton"> <input
				name="" onclick="removeUser();" type="button" value="< 删除" /> </span>
		</div>
		<div class="mem_listbox fl" style="display: none;">
			<p>
				<strong id="selectedTile">已选定部门</strong>
			</p>
			<select size="1" multiple="multiple" id="selUserList"
				class="selelist">

			</select>
			<ul id="selectGroupTree" class="ztree" style="display: none;">
			</ul>
		</div>
		<div class="clear"></div>
	</div>

	<script type="text/javascript">
		var basePath = '${ctx}';
        var userMap = new HashMap();
        //ztree设置
        var setting = {
            check : {
                enable : true,
                chkboxType : {
                    "Y" : "ps",
                    "N" : "ps"
                }
            },
            view: {
    			showIcon: showIconForTree
    		},
			data : {
				simpleData : {
					enable : true
				},
				showTitle : true, //是否显示节点title信息提示 默认为true
				key : {
					title : "title" //设置title提示信息对应的属性名称 也就是节点相关的某个属性
				}
			},
			callback : {
				onCheck : zTreeOnClick
			}
		};
		function showIconForTree(treeId, treeNode) {
			if ("gid_0" == treeNode.id) {
				return true;
			}
			if (treeNode.id.substr(0, 4) == "gid_"
					|| treeNode.id.substr(0, 4) == "rid_") {
				return true;
			}

			return !treeNode.isParent;
		};
		var groupuSelectSetting = {
			view : {
				showIcon : false
			},
			data : {
				simpleData : {
					enable : true
				},
				showTitle : true, //是否显示节点title信息提示 默认为true
				key : {
					title : "title" //设置title提示信息对应的属性名称 也就是节点相关的某个属性
				}
			},
			callback : {
				onCheck : zTreeOnClick
			}
		};

		var first = true;
		var preSearch = '搜索人员';
		$(document).ready(function() {
			$("#selUserList").hide();
			$("#selectGroupTree").show();
			// 初始化已选择部门树列表
			$.fn.zTree.init($("#selectGroupTree"), groupuSelectSetting, null);
			$("#selectedTile").html("已选定部门：");

			searchUser();
		});

		var tempType = '';
		/**
		 * 选择人员
		 * @param type 1 部门 2 角色 3 分组 4 在线 5,查找
		 */
		function searchUser() {
			var dataParam = {"flag":"column"};

			$.ajax({
				url : "${ctx}user/selectGroup.action",
				type : 'post',
				dataType : 'json',
				data : dataParam,
				success : function(data) {
					var treeObj = $.fn.zTree.init($("#groupUserTree"), setting,
							data);
					
					
					// 默认展示第一个组
		              var rootNodesArr = treeObj.getNodes();	      
		                for (var j=0;j<rootNodesArr.length;j++){
		                	var nodes = treeObj.transformToArray(rootNodesArr[j]);
		                	var isExpand =false;
		                    for(var i=0;i<nodes.length;i++){
		                    	
		                    	if (!nodes[i].isParent){                    	
		                    		var nodesArr = treeObj.getNodesByParam("id", nodes[i].id, null);
		                    		
		                    		var tempNode = nodesArr[0];   
		                    		while(tempNode.getParentNode() != null){
		                    			treeObj.expandNode(tempNode.getParentNode(),true);
		                    			tempNode=tempNode.getParentNode();
		                    	    }   
		                    		isExpand = true;
		                    		break;
		                    	}
		                    }
		                    if (isExpand){
		                    	break;
		                    }
		                }
					
					
					// 初始化后,同时初始化已经选择的人员
					if (first == true) {
						initDefaultSelected();
					} else {
						// 根据userMap初始化
						initSelected();
					}
					preSearch = $("#btnSearch").val();
				}
			});
		}

		/**
		 *刷新已经选择的人员列表
		 */
		function refreshSelectedUser() {
			// 区分选择部门,部门选择时按照树形结构显示
			var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
			var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
			var treeSelectObj = $.fn.zTree.getZTreeObj("selectGroupTree");
			for (var i = 0, l = nodes.length; i < l; i++) {
				var node = treeSelectObj.getNodesByParam("id", nodes[i].id,
						null);
				if (node.length == 0) {
					var newNode = {
						name : nodes[i].name,
						id : nodes[i].id
					};
					if (nodes[i].getParentNode() != null) {
						var tempNode = treeSelectObj.getNodesByParam("id",
								nodes[i].getParentNode().id, null);
						treeSelectObj.addNodes(tempNode[0], newNode);
					} else {
						treeSelectObj.addNodes(null, newNode);
					}
				}
			}
			updateSelectGroupMap();
		}

		/**
		 * 初始化已经选择的部门
		 */
		function initDefaultSelected() {
			first = false;
			var defaultSelectId = $("#defaultSelectId").val();
			if ("" != defaultSelectId) {
				var ids = defaultSelectId.split(",");
				var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");

				for (var i = 0; i < ids.length; i++) {
					var node = treeObj.getNodeByParam("id", "gid_" + ids[i],
							null);
					if (null != node) {
						treeObj.checkNode(node, true, true);
					}
				}
				refreshSelectedUser();
			}
		}

		function initSelected() {
			userMap.forEach(function(value, key) {
				var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
				var node = null;
				updateSelectGroupMap();
			});
		}

		// 更新用户选择的部门信息
		function updateSelectGroupMap() {
			var treeSelectObj = $.fn.zTree.getZTreeObj("selectGroupTree");
			var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
			var nodes = treeSelectObj
					.transformToArray(treeSelectObj.getNodes());
			userMap = new HashMap();
			for ( var j in nodes) {
				// 判断如果为父节点,则必须选择了全部子部门才能选择父部门
				if (nodes[j].isParent) {
					var treeNodes = treeObj.getNodesByParam("id", nodes[j].id,
							null);
					if (nodes[j].children.length == treeNodes[0].children.length) {
						var treeNode = new TreeNode();
						var id = nodes[j].id.substr(4);
						treeNode.setId(id);
						treeNode.setData("");
						treeNode.setName(nodes[j].name);
						treeNode.setType("group");
						userMap.set(id, treeNode);
					}
				} else {
					var treeNode = new TreeNode();
					var id = nodes[j].id.substr(4);
					treeNode.setId(id);
					treeNode.setData("");
					treeNode.setName(nodes[j].name);
					treeNode.setType("group");
					userMap.set(id, treeNode);
				}
			}
			art.dialog.data("userMap", userMap);//把选择的部门返回到调用页面
		}

		/**
		 *节点被选中事件
		 * @param event
		 * @param treeId
		 * @param treeNode
		 */
		function zTreeOnClick(event, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
			var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
			userMap.clear();
			//循环开始
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				if (node.id.substr(0, 4) == "gid_" && !node.isParent) {
					//如果是部门，则保存到map里面
					var treeNode = new TreeNode();
					var id = node.id.substr(4);
					treeNode.setId(id);
					treeNode.setData(node.obj);
					treeNode.setName(node.name);
					treeNode.setType("group");
					userMap.set(id, treeNode);
				}
			}
			//循环结束

			art.dialog.data("userMap", userMap);
		};
	</script>
</body>
</html>