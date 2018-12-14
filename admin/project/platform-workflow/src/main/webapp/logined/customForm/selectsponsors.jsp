<%--
   选择经办人
  User: 吴洲
  Date: 13-3-29
  Time: 上午10:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
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
    <title>选择经办人</title>
    <jsp:include page="../../common/head.jsp"/>
    <script type="text/javascript" src="${ctx}js/common/inputdefault.js"></script>
    <script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/customForm/treeNode.js"></script>

</head>
<body style=" background:#f0f9fc;">
<input type="hidden" value="<%=nodeId%>" id="nodeId"/>
<div class="selectMember" style="width:480px">
    <div class="mem_listbox fl " style="width:190px">
        <p class="treeSearch" style="margin:5px 0 0 0; height:30px; padding:0px">
            <input id="btnSearch"  style="width:172px;"  type="text" class="ipt" value="" />
        </p>
		<ul id="seledUserList" class="ztree" style="border:1px  solid  #ccc;width:178px;height:330px">
		</ul>		
    </div>
    <div class="btn_box fl"> 
    
						<input name="" onclick="addUser();" type="button" value="添加 >" class="formButton" style="margin-top:80px" /><input name="" style="margin-top:80px"  onclick="removeUser();" type="button" value="< 删除" class="formButton ml10" />
						</div>
    <div class="mem_listbox fl" style="width:180px">
        <p><strong>已选人员：</strong></p>
        <div class="seleTable" style="OVERFLOW-Y: auto; OVERFLOW-X:hidden;">
			<ul id="userList" class="ztree">
			</ul>
		</div>
    </div>
    <div class="clear"></div>
</div>

<script type="text/javascript">
    var basePath='${ctx}';
    var userMap=new HashMap();
    art.dialog.data("userMap",userMap);//把选择的人员返回到调用页面
    //ztree设置
    var leftsetting = {
        check: {enable: true,chkboxType: {"Y":"ps", "N":"ps"}},
        view: {dblClickExpand: false},
        data: { simpleData: { enable: true} },
        callback: {
			onClick: leftTreeOnClickOrCheck,
			onCheck: leftTreeOnClickOrCheck
		}
    };
    var rightsetting = {
        check: {enable: true,chkStyle: "radio",radioType: "level"},
        view: {dblClickExpand: false},
        data: { simpleData: { enable: true} },
        callback: {
			onClick: treeOnClickOrCheck,
			onCheck: treeOnClickOrCheck
		}
    };
    
    $(document).ready(function(){
        var nodeId=$("#nodeId").val();
        $("#btnSearch").bind("keyup",function(){
            searchUser();
        });
        //查询所有候选人员
        searchUser();
        
    });

    /**
     * 选择人员
     */
    function searchUser()
    {
        var searchName= $("#btnSearch").val();
        var dataParam={
            'userNameSearch':searchName,
            'nodeId':<%=nodeId%>
        };
        $.ajax({
            url : "${ctx}workflowForm/getUser.action",
            type : 'post',
            dataType :'json',
            data:dataParam,
            success : function(data) {
                $("#seledUserList").empty();//首先清空
		        $.fn.zTree.init($("#seledUserList"), leftsetting, data);
            }
        });
    }
    
 	/**
     * 添加人员到hashmap中
     */
    function addUser()
    {
        var treeObj = $.fn.zTree.getZTreeObj("seledUserList");
        var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
        for(var i=0;i<nodes.length;i++)
        {
	        var node=nodes[i];
	        var treeNode=new TreeNode();
	        treeNode.setId(node.id);
	        treeNode.setData("");
	        treeNode.setName(node.name);
	        treeNode.setType(0);
	        treeNode.setPId(0);
	        treeNode.setData(node.obj);
	        userMap.set(node.id,treeNode);
		}
        
        refreshSelectedUser();
    }
    /**
     * 移除人员
     */
    function removeUser()
    {
        var treeObj = $.fn.zTree.getZTreeObj("userList");
        var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
        for(var i=0;i<nodes.length;i++)
        {
	        var nodeId = nodes[i];
	        userMap.remove(nodeId.id);
		}
        refreshSelectedUser();
    }
    /**
     *刷新已经选择的人员列表
     */
    function refreshSelectedUser()
    {
        $("#selUserList").empty();//首先清空
        var zNodes=new Array();
        userMap.forEach(function(value, key) {
            zNodes.push(value);
            
        });
        $.fn.zTree.init($("#userList"), rightsetting, zNodes);
         art.dialog.data("userMap",userMap);//把选择的人员返回到调用页面
    }
    function treeOnClickOrCheck(event, treeId, treeNode, clickFlag){
    	var treeObjSel = $.fn.zTree.getZTreeObj(treeId);
		if (treeNode.checked) {
			treeObjSel.checkNode(treeNode, true, true);
			userMap.forEach(function(value, key) {
            	userMap.remove(key);
            	if(key==treeNode.id){
            		value.type=1;
            	}else{
            		value.type=0;
            	}
            	userMap.set(key,value);
       		 });
		}else{
			treeObjSel.checkNode(treeNode, true, true);
			userMap.forEach(function(value, key) {
            	userMap.remove(key);
            	if(key==treeNode.id){
            		value.type=1;
            	}else{
            		value.type=0;
            	}
            	userMap.set(key,value);
       		 });
		} 
    }
    function leftTreeOnClickOrCheck(event, treeId, treeNode, clickFlag){
    	var treeObjSel = $.fn.zTree.getZTreeObj(treeId);
    	if (clickFlag != undefined) {
		if (treeNode.checked) {
			treeObjSel.checkNode(treeNode, false, true);
		} else {
			treeObjSel.checkNode(treeNode, true, true);
		}
    }
  }
</script>
</body>
</html>