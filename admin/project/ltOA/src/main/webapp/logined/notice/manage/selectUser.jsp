<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="../../../common/ydzjtaglibs.jsp" />
<%
    int showType = 3;//查找类型 默认按人员选择
			if (request.getParameter("showType") != null) {
				try {
					showType = Integer.parseInt(request.getParameter("showType"));
				} catch (Exception ex) {
					showType = 3;
				}
			}
			String moduleName = request.getParameter("moduleName") == null ? "" : request.getParameter("moduleName");
			String defaultSelectId = request.getParameter("defaultSelectId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择人员</title>
<jsp:include page="../../../common/ydzjhead.jsp" />
<script type="text/javascript" src="${ctx}/js/logined/util/inputdefault.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/js/logined/util/hashmap.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/js/logined/util/treeNode.js?v=${jsversion}"></script>
<link rel="stylesheet" href="${ctx}ydzj/css/selectMember.css" type="text/css"></link>
<style type="text/css">
   .ztree{ height:345px !important;}
</style>
</head>
<body style=" background:#f0f9fc;">
	<input type="hidden" value="<%=showType%>" id="showType" />
	<input type="hidden" id="defaultSelectId" value='<%=defaultSelectId%>' />
	<input type="hidden" id="moduleName" value='<%=moduleName%>' />
	<div class="selectMember">
		<div class="mem_listbox fl ml5">
			<p>
				<input id="btnSearch" type="text" style="display: none" class="ipt"
					fs="搜索人员" />
			</p>
			<ul class="selectTab" style="margin-top:5px">
				<li class="current" id="liSelectGroup"><a
					href="javascript:void(0);" id="btnSelectGroup">单位成员</a></li>
			</ul>
			<ul id="groupUserTree" class="ztree">
			</ul>
		</div>
		<div class="btn_box fl" style="display: none;">
			<span class="mainButton"> <input name="" onclick="addUser();"
				type="button" value="添加 >" /> </span> <span class="mainButton"> <input
				name="" onclick="removeUser();" type="button" value="< 删除" /> </span>
		</div>
		<div class="mem_listbox fl" style="display: none;">
			<p>
				<strong id="selectedTile">已选定人员：</strong>
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
     			enable: true,
    			chkStyle: "radio",
    			radioType: "all",
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
	            }
            }, 
            callback: {  
                onCheck: zTreeOnClick
            }
        };
        function showIconForTree(treeId, treeNode) {
        	if ("gid_0" == treeNode.id){
        		return true;
        	}
        	if(treeNode.id.substr(0,4)=="gid_" || treeNode.id.substr(0,4)=="rid_"){
        		return false;
        	}
        		
    		return !treeNode.isParent;
    	};
        var groupuSelectSetting = {
    			view: {
    				showIcon: false
    			},
    			data: {
    				simpleData: {
    					enable: true
    				}
    			},
    			callback: {  
                    onCheck: zTreeOnClick
                }
        };

        
        var first = true;
        var preSearch = '搜索人员';
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
		        $("#selUserList").hide();
		        $("#selectGroupTree").show();
		        // 初始化已选择部门树列表
		        $.fn.zTree.init($("#selectGroupTree"), groupuSelectSetting, null);
		        $("#btnSelectGroup").html("组织结构");
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
        
        var tempType='';
        /**
         * 选择人员
         * @param type 1 部门 2 角色 3 分组 4 在线 5,查找
         */
        function searchUser(type) {
        	tempType = type;
	        var searchName = $("#btnSearch").val();
	        var moduleName = $("#moduleName").val();
	        if(undefined == moduleName || "undefined" == moduleName){
	        	moduleName = "";
	        }
	        var dataParam = {
	            'type' : type,
	            'searchName' : searchName,
	            'moduleName':moduleName,
	            'showType' :<%=showType%>
			};
	        if(type == 5 && ((preSearch == "搜索人员" && "" == $.trim(searchName) || (preSearch == $.trim(searchName)))  )){
	        	return;
	        }
	        
	        $.ajax({
	            url : "${ctx}adminuser/selectUser.action",
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
         *刷新已经选择的人员列表
         */
        function refreshSelectedUser() {
        	// 区分选择部门,部门选择时按照树形结构显示
        	var showType = $("#showType").val();
        	if (showType == 1){
        		var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
    	        var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
    	       var treeSelectObj = $.fn.zTree.getZTreeObj("selectGroupTree");
    	        for (var i=0, l=nodes.length; i < l; i++) {
       			    var node = treeSelectObj.getNodesByParam("id", nodes[i].id, null);
    	            if (node.length == 0){    	            	
    	            	var newNode = {name:nodes[i].name, id:nodes[i].id};
    	            	if (nodes[i].getParentNode() != null){
    	            		var tempNode = treeSelectObj.getNodesByParam("id", nodes[i].getParentNode().id, null);
    	            		treeSelectObj.addNodes(tempNode[0], newNode);
    	            	}else{
    	            		treeSelectObj.addNodes(null, newNode);
    	            	}    	            	
    	            }
       		    }   
    	        updateSelectGroupMap();
        	}else{
        		$("#selUserList").empty();//首先清空
    	        art.dialog.data("userMap", userMap);//把选择的人员返回到调用页面
        	}
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
					        treeObj.checkNode(node, true, true);
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
				        for ( var j=0;j<node.length;j++) {
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
			        updateSelectGroupMap();
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
			        for (  var j=0;j<node.length;j++) {
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
        
         // 更新用户选择的部门信息
        function updateSelectGroupMap(){
        	var treeSelectObj = $.fn.zTree.getZTreeObj("selectGroupTree");
        	var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
        	var nodes = treeSelectObj.transformToArray(treeSelectObj.getNodes());
        	userMap = new HashMap();
        	for (  var j=0;j<nodes.length;j++){
        		// 判断如果为父节点,则必须选择了全部子部门才能选择父部门
        		if (nodes[j].isParent){
        			var treeNodes = treeObj.getNodesByParam("id", nodes[j].id, null);
        			if (nodes[j].children.length == treeNodes[0].children.length){
        				var treeNode = new TreeNode();
            	        var id = nodes[j].id.substr(4);
            	        treeNode.setId(id);
            	        treeNode.setData("");
            	        treeNode.setName(nodes[j].name);
            	        treeNode.setType("group");
            	        userMap.set(id, treeNode);
        			}
        		}else{
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
    	 userMap.clear();
    	 var showType = $("#showType").val();
         var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
         var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
        if (tempType == 5){
        	 if(!treeNode.checked){
        		 var id = treeNode.id.substr(4);
        		 userMap.remove(id);
        	 }else{
        		 //如果是人员，则保存到map里面
                 var node = new TreeNode();
                 var id = treeNode.id.substr(4);
                 node.setId(id);
                 node.setData(treeNode.obj);
                 node.setName(treeNode.name);
                 node.setType("user");
                 userMap.set(id, node);
        	 }
        	 art.dialog.data("userMap", userMap);
        	 return;
         }
         userMap.clear();
         //循环开始
         for ( var i = 0; i < nodes.length; i++) {
             var node = nodes[i];
             if (showType == 3) {
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
             }else if(showType == 2) {
            	 if (node.id.substr(0, 4) == "rid_") {
				        //如果是角色，则保存到map里面
				       var treeNode = new TreeNode();
				        var id = node.id.substr(4);
				        treeNode.setId(id);
				        treeNode.setData("");
				        treeNode.setName(node.name);
				        treeNode.setType("role");
				        userMap.set(id, treeNode);
			     }
             } else  if(showType == 1){
                	 if(node.id.substr(0, 4) == "gid_") {
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
 		        }
         //循环结束
         art.dialog.data("userMap", userMap);
     };
	</script>
</body>
</html>