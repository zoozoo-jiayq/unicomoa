<%--
   选择人员
  User: 黄普友
  Date: 13-3-16
  Time: 上午9:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
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
	<jsp:include page="../../common/head.jsp" />
	<script type="text/javascript" src="${ctx}js/common/inputdefault.js"></script>
	<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
	<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
	<script type="text/javascript" src="${ctx}js/common/jquery.query.js"></script>
</head>
<body>
	<input type="hidden" value="<%=showType%>" id="showType" />
	<input type="hidden" id="defaultSelectId" />
	<div class="selectMember">
		<div class="mem_listbox fl">
			<p>
				<input id="btnSearch" type="text" style="display: none" class="ipt"
					fs="搜索人员" />
			</p>
			<ul class="tab" style="margin-top:5px">
				<li class="current" id="btnSelectPerson"><a
					href="javascript:void(0);" id="btnSelectPerson">个人</a>
				</li>
				<li id="btnSelectShare"><a href="javascript:void(0);"
					id="btnSelectShare">共享</a>
				</li>
				<li id="btnSelectCommon"><a href="javascript:void(0);"
					id="btnSelectCommon">公共</a>
				</li>

			</ul>

			<ul id="groupUserTree" class="ztree">
			</ul>
		</div>
		<div class="btn_box fl">
			<span class="mainButton"> <input name="" onclick="addUser();"
				type="button" value="添加 >" /> </span> <span class="mainButton"> <input
				name="" onclick="removeUser();" type="button" value="< 删除" /> </span>
		</div>
		<div class="mem_listbox fl">
			<p>
				<strong>已选定电话</strong>
			</p>
			<select size="1" multiple="multiple" id="selUserList"
				class="selelist">

			</select>
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
            view : {
	            dblClickExpand : false
            },
            data : {
	            simpleData : {
		            enable : true
	            }
            }
        };
        $(document).ready(function() {
        	$("#defaultSelectId").val(($.query.get("defaultSelectId")));
        	
	        //查询通讯录
	        $("#btnSearch").css('display', 'block');
	        $("#btnSearch").bind("keyup", function() {
		        searchUser(5, 2, false);
	        });
	        //个人通讯簿
	        $("#btnSelectPerson").bind("click", function() {
		        searchUser(1, 1, false);
	        });
	        //共享通讯簿
	        $("#btnSelectShare").bind("click", function() {
		        searchUser(2, 1, false);
	        });
	        //公共通讯簿
	        $("#btnSelectCommon").bind("click", function() {
		        searchUser(3, 1, false);
	        });
	        //默认个人通讯簿
	        searchUser(3, 1, true);
	        $("#groupUserTree").hide();
        });
        
        var persion = false;
        var share = false;
        var commonSign = false;        

        /**
         * 选择人员
         * @param type 1 部门 2 角色 3 分组 4 在线 5,查找
         */
        function searchUser(type, category, first) {

	        var searchName = $("#btnSearch").val();
	        var dataParam = {
	            'type' : type,
	            'searchName' : searchName,
	            'showType' : category
	        };
	        $.ajax({
	            url : "${ctx}user/findAllByUser.action",
	            type : 'post',
	            dataType : 'json',
	            data : dataParam,
	            success : function(data) {
	            	if (null == searchName || "" == $.trim(searchName)) {	
	    		         // 返回初始化状态
		    	         if (type == 5) {
		    	        	$("#btnSelectPerson").trigger("click");
		    	            return;
		    	         }
	    	             
	    	       }
		            $.fn.zTree.init($("#groupUserTree"), setting, data);
		            // 初始化后,同时初始化已经选择的人员
			       if (first == true) {
				        initDefaultSelected();
                        // 初始化共享联系人
                     if(!share){
                    	 searchUser(2, 1, true);
                    	 share = true;
                        }else{
                        	// 初始化个人联系人
                        	if (!commonSign){
                        		searchUser(1, 1, true);	
                        		commonSign=true;
                        	}
                         
                        }
                        if ("1" == type){
                           $("#groupUserTree").show();
                        }
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
	        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
	        var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
	        if (nodes == "") {
		        art.dialog.alert('请选择人员');
		        return false;
	        } else {
		        for ( var i = 0; i < nodes.length; i++) {
			        var node = nodes[i];

			        //只显示部门
			        if (node.id.substr(0, 4) != "gid_") {

				        //如果是人员，则保存到map里面
				        var treeNode = new TreeNode();
				        var id = node.id.substr(4);
				        treeNode.setId(id);
				        treeNode.setData(node.obj);
				        treeNode.setName(node.name);

				        userMap.set(id, treeNode);
			        }

		        }
		        refreshSelectedUser();
	        }
        }
        /**
         * 移除人员
         */
        function removeUser() {
	        if ($("#selUserList option:selected").length > 0) {
		        $("#selUserList option:selected").each(function() {
			        userMap.remove($(this).val());
			        //取消节点的选中状态
			        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");

			        var nodeId = "uid_" + $(this).val();

			        var node = treeObj.getNodeByParam("id", nodeId, null);
			        treeObj.checkNode(node, false, false);
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

		        var name = value.Name;
		        var phone = value.data;

		        var namephone = name + "(" + phone + ")";
		        $("#selUserList").append("<option value='"+key+"'>" + namephone + "</option>");
	        });
	        art.dialog.data("userMap", userMap);//把选择的人员返回到调用页面
        }

        /**
         * 初始化已经选择的人员
         */
        function initDefaultSelected() {
	        var defaultSelectId = $("#defaultSelectId").val();
	        if ("" != defaultSelectId) {
		        var ids = defaultSelectId.split(",");
		        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");

		        for ( var i = 0; i < ids.length; i++) {
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
		        refreshSelectedUser();
	        }
        }
        
        function initSelected() {
	        var node = null;
	        userMap.forEach(function(value, key) {
		        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
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
	        });
        }
	</script>
</body>
</html>