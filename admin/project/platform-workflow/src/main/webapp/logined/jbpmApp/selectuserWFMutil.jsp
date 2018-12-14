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
<%
    int nodeId=1;//查找该节点所有人员
    if(request.getParameter("nodeId")!=null)
    {
        try
        {
            nodeId=Integer.parseInt(request.getParameter("nodeId"));
        }
        catch (Exception ex)
        {
            nodeId=1;
        }
    }

%>

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
<body class="bg_white">
	<input type="hidden" value="<%=showType%>" id="showType" />
	<input type="hidden" value="<%=nodeId%>" id="nodeId"/>
	<input type="hidden" id="defaultSelectId" value='<%=defaultSelectId%>' />
	<%
		String selectUserMode = request.getParameter("selectUserMode");
	%>
	<input type="hidden" id="selectUserMode" value="<%=selectUserMode%>"/>

	
		<div class="selectMember">
       <input type="text" class="search" placeholder="搜索人员"   id="btnSearch" />
       <div class="member">
       <div class="tab">
         <ul>
           <li class="current" id="liSelectGroup" ><a  href="javascript:void(0);" id="btnSelectGroup" >办理人员</a></li>
         </ul>  
       </div>
       <div class="tabContent"    >
          <ul id="groupUserTree" class="ztree">
			</ul>
       </div>
        
   </div>
</div>

	<script type="text/javascript">
		var basePath = '${ctx}';
        var userMap = new HashMap();
        //ztree设置
         //ztree设置
        var setting = {
            check : {
                enable : true,
                chkboxType : {
                    "Y" : "ps",
                    "N" : "ps"
                }
            },
            view : {
	            dblClickExpand : false
            },
            data : {
	            simpleData : {
		            enable : true
	            }
            }, 
            callback: {  
                onCheck: zTreeOnClick
            }
        };

        /**
         *单选按钮选择事件
         * @param event
         * @param treeId
         * @param node
         */
        function zTreeOnClick(event, treeId, node) {
	        var showType = $("#showType").val();
	         var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
	         var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
	         userMap.clear();
	         //循环开始
	         for ( var i = 0; i < nodes.length; i++) {
	             		var node = nodes[i];
			            var treeNode = new TreeNode();
			            var id = node.id;
			            var index = id.indexOf("uid_");
			            if(index>=0){
				            id = id.substring(index+1);
				            treeNode.setId(id);
				            treeNode.setData(node.obj);
				            treeNode.setName(node.name);
				            treeNode.setType("user");
				            userMap.set(id, treeNode);
			            }
	 		   }
	         //循环结束
	         art.dialog.data("userMap", userMap);
        }
     
        var first = true;
        $(document).ready(function() {
	        var showType = $("#showType").val();
	        if (showType == 3) {
		        $("#btnSearch").css('display', 'block');
		        $("#btnSearch").bind("keyup", function() {
			        searchUser(5);
		        });
		        //选择部门
		        $("#btnSelectGroup").bind("click", function() {
			        searchUser(1);
		        });
		        //选择角色
		        $("#btnSelectRole").bind("click", function() {
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
	            'showType' : 3,
	            'nodeId':<%=nodeId%>,
	            'selectUserMode':$("#selectUserMode").val()
			};
	        
	        $.ajax({
	            url : "${ctx}jbpmflow/getUser.action",
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
	    	         for(var i=0; i<data.length; i++){
	    	        	data[i].open = true;
	    	        }
		            $.fn.zTree.init($("#groupUserTree"), setting, data);

		            // 初始化后,同时初始化已经选择的人员
		            if (first == true) {
			            initDefaultSelected();
		            } else {
			            // 根据userMap初始化
			            initSelected();
		            }

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
			        if (showType == 3) {
				        node = treeObj.getNodesByParam("id",  ids[i], null);
				        for ( var j in node) {
					        treeObj.checkNode(node[j], true, true);
					        var treeNode = new TreeNode();
					        var id = node[j].id;
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
	funPlaceholder(document.getElementById("btnSearch"));
	</script>
</body>
</html>