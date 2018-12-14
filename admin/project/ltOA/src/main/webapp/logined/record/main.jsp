<%--
功能:人事档案 新建和修改页面
版本:1.0
开发人员: 汤波涛
创建日期: 2013-03-22
修改日期: 2013-03-22
修改人员: 汤波涛
修改列表:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>档案查看</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx}flat/plugins/peopleTree/skins/tree_default.css" type="text/css">
	
	<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
    <%-- <script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script> --%>
</head>
<body>
<input type="hidden" id="treeType" value="${treeType }"/>
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu">
      <h1>单位组织成员<span onclick="refresh()" style="cursor:pointer;float: right;">刷新</span></h1>
      <div class="zTreeDemoBackground">
        <ul id="groupUserTree" class="ztree">
        </ul>
      </div>
    </div>
  </div>
     <iframe frameborder="0" scrolling="auto" border="0" name="recordRightFrame" class="iframeresize" src=""></iframe>
</div>  
      
<script type="text/javascript">
var treeType = $("#treeType").val();
  //刷新操作
 function refresh(){
	 loadTree();
 }
   
    $(document).ready(function () {
    	
    	loadTree();
    	
    }); 
    //获得树
    function loadTree(){
    	$.ajax({
    		url:basePath+"/logined/record/userRecordTree.action",
    		type:'POST',
    		data:{"treeType":treeType},
    		async:false,
    		dataType:'json',
    		success:function(result){
    			qytx.app.tree.base({
    	    		id	:	"groupUserTree",
    	    		click:	clickCallback,
    	    		async:false,
    	    		data:result,
				    loadComplete:	function(){
    		            var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
    		            var nodes = treeObj.transformToArray(treeObj.getNodes());
    		            for(var i=0;i<nodes.length;i++){
    		            	 if (nodes[i].id.indexOf("uid_")!=0){
    		            		var nodesArr = treeObj.getNodesByParam("id", nodes[i].id, null);
    		            		var tempNode = nodesArr[0];                		
    		            		while(tempNode.getParentNode() != null){
    		            			treeObj.expandNode(tempNode,true);
    		            			tempNode=tempNode.getParentNode();
    		            	    }    
    		            		var arr = [];
    		            		arr.push(nodes[i]);
    		            		clickCallback(arr);
    		            		treeObj.selectNode(nodes[i]);
    		            		break;              	
    		            	}else{ 
    		            		var url = basePath + "logined/record/list.action?treeType="+treeType;
    		    	            window.open(url,"recordRightFrame");
    		            	}
    		            }
    			    }
    	    	});
    		}
    	});  
    	
    	
    }
    
    
    
    function clickCallback(nodes){
    	if(nodes && nodes.length>0){
			var node = nodes[0];
			var url = "";
	        if (node.id == "gid_0") {
	        	/* art.dialog.alert("请选择部门!");
	            //url = basePath + "logined/record/noGroup.jsp"; */
	        	url = basePath + "logined/record/list.action?groupId=" + node.id.substr(4)+"&treeType="+treeType;;
	            window.open(url,"recordRightFrame");
	        } else if (node.id.substr(0,4) == "gid_") {
	            url = basePath + "logined/record/list.action?groupId=" + node.id.substr(4)+"&treeType="+treeType;
	            window.open(url,"recordRightFrame");
	        } else if (node.id.substr(0,4)  == "uid_") {
	            url=basePath+"logined/record/createOrEditByUserId.action?from=detail&userId="+node.id.substr(4)+"&t="+Math.random();
	            window.open(url,"recordRightFrame");
	        }
	        
	      //  
	       // $("iframe[name='recordRightFrame']").attr("src",url);
		}
    }
</script>
</body>
<script type="text/javascript">
		if(menuStyle != "default"){
			var mainpage = $("div.mainpage");
			mainpage.removeClass("mainpage").addClass("mainpage_r");
		}
</script>
</html>