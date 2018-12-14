<%--
   选择人员
  User: 黄普友
  Date: 13-3-16
  Time: 上午9:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    int showType = 3;//查找类型 默认按人员选择
			if (request.getParameter("showType") != null) {
				try {
					showType = Integer.parseInt(request
							.getParameter("showType"));
				} catch (Exception ex) {
					showType = 3;
				}
			}

			String defaultSelectId = request.getParameter("defaultSelectId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择人员</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/selectMember/skins/selectMember_default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css" />
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
	<input type="hidden" value="<%=showType%>" id="showType" />
	<input type="hidden" id="defaultSelectId" value='<%=defaultSelectId%>' />
	
	<div class="selectMember">
   <input type="text" class="search" fs="搜索人员"   id="btnSearch" />
   <div class="member">
       <div class="tab">
         <ul>
           <li class="current" id="liSelectGroup" ><a  href="javascript:void(0);" id="btnSelectGroup" >单位成员</a></li>
           <li  id="liSelectRole" ><a   href="javascript:void(0);"  id="btnSelectRole" >工作角色</a></li>
         </ul>  
       </div>
       <div class="tabContent"  >
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
		<div class="mem_listbox fl"  style="display: none;">
			<p>
				<strong id="selectedTile">已选定人员</strong>
			</p>
			<select size="1" multiple="multiple" id="selUserList"
				class="selelist">

			</select>
		</div>
		<div class="clear"  style="display: none;"></div>
	 

	<script type="text/javascript">
		var basePath = '${ctx}';
        var userMap = new HashMap();
        //ztree设置
        var setting = {
   			check: {
   				enable: true,
   				chkStyle: "radio",
   				radioType: "all"
   			},
   			view: {
    			showIcon: showIconForTree
    		},
    		
			data : {
				simpleData : {
					enable : true
				}/* ,
				showTitle : true, //是否显示节点title信息提示 默认为true
				key : {
					title : "title" //设置title提示信息对应的属性名称 也就是节点相关的某个属性
				} */
			},
			callback : {
				onCheck : zTreeOnCheck
			}
		};
		function showIconForTree(treeId, treeNode) {
			if ("gid_0" == treeNode.id) {
				return true;
			}
			if (treeNode.id.substr(0, 4) == "gid_"
					|| treeNode.id.substr(0, 4) == "rid_") {
				return false;
			}

			return !treeNode.isParent;
		};
		/**
		 *单选按钮选择事件
		 * @param event
		 * @param treeId
		 * @param node
		 */
		function zTreeOnCheck(event, treeId, node) {
			var showType = $("#showType").val();
			userMap.clear();
			if (showType == 1) {
				//只显示部门
				if (node.id.substr(0, 4) == "gid_") {
					//如果是人员，则保存到map里面
					var treeNode = new TreeNode();
					var id = node.id.substr(4);
					treeNode.setId(id);
					treeNode.setData("");
					treeNode.setName(node.name);
					treeNode.setType("group");
					userMap.set(id, treeNode);
				}
			} else if (showType == 2) {
				//只显示角色
				if (node.id.substr(0, 4) == "rid_") {
					//如果是人员，则保存到map里面
					var treeNode = new TreeNode();
					var id = node.id.substr(4);
					treeNode.setId(id);
					treeNode.setData("");
					treeNode.setName(node.name);
					treeNode.setType("role");
					userMap.set(id, treeNode);
				}
			} else if (showType == 3) {
				//只显示人员
				if (node.id.substr(0, 4) == "uid_") {
					//如果是人员，则保存到map里面
					var treeNode = new TreeNode();
					var id = node.id.substr(4);
					treeNode.setId(id);

					treeNode.setData(node.obj);
					treeNode.setName(node.name);
					treeNode.setType("user");
					userMap.set(id, treeNode);
				}
			}
			art.dialog.data("userMap", userMap);
		}

		var first = true;
		var preSearch = '搜索人员';
		$(document).ready(
				function() {
					var showType = $("#showType").val();
					if (showType == 3) {
						$("#btnSearch").css('display', 'block');
						$("#btnSearch").bind("keyup", function() {
							searchUser(5);
							$("#btnSearch").focus();

							setTimeout(function() {
								$("#btnSearch").focus();
							}, 200);
						});
						//选择部门
						$("#btnSelectGroup")
								.bind(
										"click",
										function() {
											if ($("#liSelectGroup").attr(
													"class") == 'current'
													&& "" != $("#btnSearch")
															.val()) {
												return;
											}
											$("#btnSearch").val("");
											searchUser(1);
											$("#btnSearch").focus();
										});
						//选择角色
						$("#btnSelectRole").bind("click", function() {
							if ($("#liSelectRole").attr("class") == 'current') {
								return;
							}

							$("#btnSearch").val("");
							searchUser(2);
							$("#btnSearch").focus();
						});

						//选择部门
						$("#liSelectGroup")
								.bind(
										"click",
										function() {
											if ($("#liSelectGroup").attr(
													"class") == 'current'
													&& "" != $("#btnSearch")
															.val()) {
												return;
											}
											$("#btnSearch").val("");
											searchUser(1);
										});
						//选择角色
						$("#liSelectRole").bind("click", function() {
							if ($("#liSelectRole").attr("class") == 'current') {
								//return;
							}
							$("#btnSearch").val("");
							searchUser(2);
						});

						//默认选择部门
						searchUser(1);
					} else if (showType == 1) {
						//选择部门
						$("#liSelectRole").hide();
						$("#selectedTile").html("已选定部门：");
						$("#btnSelectGroup").bind("click", function() {
							searchUser(1);
						});
						searchUser(1);
					} else if (showType == 2) {
						//选择角色            
						$("#liSelectGroup").hide();
						$("#selectedTile").html("已选定角色：");
						$("#liSelectRole").addClass("current");
						$("#btnSelectRole").bind("click", function() {
							searchUser(2);
						});
						searchUser(2);
					}
				});

		/**
		 * 选择人员
		 * @param type 1 部门 2 角色 3 分组 4 在线 5,查找
		 */
		function searchUser(type) {
			var searchName = $("#btnSearch").val();
			var dataParam = {
				'type' : type,
				'searchName' : searchName,
				'showType' :
	<%=showType%>
			};
	        
	        if(type == 5 && ((preSearch == "搜索人员" && "" == $.trim(searchName) || (preSearch == $.trim(searchName)))  )){
	        	return;
	        }
	        
	        $.ajax({
	            url : "${ctx}user/selectUser.action",
	            type : 'post',
	            dataType : 'json',
	            data : dataParam,
	            success : function(data) {
	            	if (null == searchName || "" == $.trim(searchName)) {	
	    		         // 返回初始化状态
		    	         if (type == 5) {
		    	        	$("#btnSelectGroup").trigger("click");
		    	            return;
		    	         }
	    	             
	    	         }
var treeObj = $.fn.zTree.init($("#groupUserTree"), setting, data);
		            
		            // 默认展示第一个人员所在的组
	              var rootNodesArr = treeObj.getNodes();	      
	                for (var j=0;j<rootNodesArr.length;j++){
	                	var nodes = treeObj.transformToArray(rootNodesArr[j]);
	                	var isExpand =false;
	                    for(var i=0;i<nodes.length;i++){
	                    	
	                    	if (nodes[i].id.indexOf("uid_")==0){                    	
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
         * 添加人员到hashmap中
         */
        function addUser() {
        	userMap.clear();
	        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
	        var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
	        var showType = $("#showType").val();
	        for ( var i = 0; i < nodes.length; i++) {
		        var node = nodes[i];
		        if (showType == 1) {
			        //只显示部门
			        if (node.id.substr(0, 4) == "gid_") {
				        //如果是人员，则保存到map里面
				        var treeNode = new TreeNode();
				        var id = node.id.substr(4);
				        treeNode.setId(id);
				        treeNode.setData("");
				        treeNode.setName(node.name);
				        treeNode.setType("group");
				        userMap.set(id, treeNode);
			        }
		        } else if (showType == 2) {
			        //只显示部门
			        if (node.id.substr(0, 4) == "rid_") {
				        //如果是人员，则保存到map里面
				        var treeNode = new TreeNode();
				        var id = node.id.substr(4);
				        treeNode.setId(id);
				        treeNode.setData("");
				        treeNode.setName(node.name);
				        treeNode.setType("role");
				        userMap.set(id, treeNode);
			        }
		        } else if (showType == 3) {
			        //只显示部门
			        if (node.id.substr(0, 4) == "uid_") {
				        //如果是人员，则保存到map里面
				        var treeNode = new TreeNode();
				        var id = node.id.substr(4);
				        treeNode.setId(id);

				        treeNode.setData(node.obj);
				        treeNode.setName(node.name);
				        treeNode.setType("user");
				        userMap.set(id, treeNode);
			        }
		        }

	        }
	        refreshSelectedUser();
        }
        /**
         * 移除人员
         */
        function removeUser() {
	        var showType = $("#showType").val();
	        if ($("#selUserList option:selected").length > 0) {
		        $("#selUserList option:selected").each(function() {
			        userMap.remove($(this).val());
			        //取消节点的选中状态
			        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");

			        var nodeId = "uid_" + $(this).val();
			        if (showType == 1) {
				        nodeId = "gid_" + $(this).val();
			        } else if (showType == 2) {
				        nodeId = "rid_" + $(this).val();
			        }
			        var node = treeObj.getNodesByParam("id", nodeId, null);
			        for ( var j in node) {
				        treeObj.checkNode(node[j], false, true);
			        }
		        });
	        }
	        refreshSelectedUser();
        }
        /**
         *刷新已经选择的人员列表
         */
        function refreshSelectedUser() {
	        $("#selUserList").empty();//首先清空
	        userMap.forEach(function(value, key) {
		        $("#selUserList").append("<option value='"+key+"'>" + value.Name + "</option>");
	        });
	        art.dialog.data("userMap", userMap);//把选择的人员返回到调用页面
        }

        /**
         * 初始化已经选择的人员
         */
        function initDefaultSelected() {
	        first = false;
	        var defaultSelectId = $("#defaultSelectId").val();
	        if ("" != defaultSelectId) {
		        var ids = defaultSelectId.split(",");
		        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
		        var showType = $("#showType").val();

		        for ( var i = 0; i < ids.length; i++) {
			        var node = null;
			        if (showType == 1) {
				        node = treeObj.getNodeByParam("id", "gid_" + ids[i], null);
				        if (null != node) {
					        treeObj.checkNode(node, true, false);
					        var treeNode = new TreeNode();
					        var id = node.id.substr(4);
					        treeNode.setId(id);
					        treeNode.setData("");
					        treeNode.setName(node.name);
					        treeNode.setType("group");
					        userMap.set(id, treeNode);
				        }
			        } else if (showType == 2) {
				        node = treeObj.getNodeByParam("id", "rid_" + ids[i], null);
				        if (null != node) {
					        treeObj.checkNode(node, true, true);
					        var treeNode = new TreeNode();
					        var id = node.id.substr(4);
					        treeNode.setId(id);
					        treeNode.setData("");
					        treeNode.setName(node.name);
					        treeNode.setType("role");
					        userMap.set(id, treeNode);
				        }
			        } else if (showType == 3) {
				        node = treeObj.getNodesByParam("id", "uid_" + ids[i], null);
				        for ( var j in node) {
					        treeObj.checkNode(node[j], true, true);
					        var treeNode = new TreeNode();
					        var id = node[j].id.substr(4);
					        treeNode.setId(id);
					        treeNode.setData(node[j].obj);
					        treeNode.setName(node[j].name);
					        treeNode.setType("user");
					        userMap.set(id, treeNode);
				        }
			        }
		        }
		        refreshSelectedUser();
	        }
        }

        function initSelected() {
	        userMap.forEach(function(value, key) {
		        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
		        var showType = $("#showType").val();
		        var node = null;
		        if (showType == 1) {
			        node = treeObj.getNodeByParam("id", "gid_" + key, null);
			        if (null != node) {
				        treeObj.checkNode(node, true, false);
				        var treeNode = new TreeNode();
				        var id = node.id.substr(4);
				        treeNode.setId(id);
				        treeNode.setData("");
				        treeNode.setName(node.name);
				        treeNode.setType("group");
				        userMap.set(id, treeNode);
			        }
		        } else if (showType == 2) {
			        node = treeObj.getNodeByParam("id", "rid_" + key, null);
			        if (null != node) {
				        treeObj.checkNode(node, true, true);
				        var treeNode = new TreeNode();
				        var id = node.id.substr(4);
				        treeNode.setId(id);
				        treeNode.setData("");
				        treeNode.setName(node.name);
				        treeNode.setType("role");
				        userMap.set(id, treeNode);
			        }
		        } else if (showType == 3) {
			        node = treeObj.getNodesByParam("id", "uid_" + key, null);
			        for ( var j in node) {
				        treeObj.checkNode(node[j], true, true);
				        var treeNode = new TreeNode();
				        var id = node[j].id.substr(4);
				        treeNode.setId(id);
				        treeNode.setData(node[j].obj);
				        treeNode.setName(node[j].name);
				        treeNode.setType("user");
				        userMap.set(id, treeNode);
			        }
		        }
	        });
        }
	</script>
	<script>funPlaceholder(document.getElementById("search"));</script>
</body>
</html>