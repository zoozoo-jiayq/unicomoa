/**
 * 用户树js
 **/
var callbackFunc;//回调函数
function addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	if(treeNode.obj && treeNode.obj == "4"){
		aObj.addClass("treegreen");
	}else if(treeNode.obj && treeNode.obj == "5"){
		aObj.addClass("treered");
	}
}

/**
 * 选择人员
 */
function openSelectTreeUser(callback, bashPathSrc, defaultSelect)
{
	var urlStr =  basePath+"groupExt/selectGroup.action";
    callbackFunc=callback;
    var setting = {
        check: {enable: false,chkboxType: {"Y":"ps", "N":"ps"}},
        view: {dblClickExpand: false,addDiyDom: addDiyDom},
        data: { simpleData: { enable: true}},
        callback: {onClick: zTreeOnCheck}
    };
        $.ajax({
            url : urlStr,
            type : 'post',
            dataType :'json',
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


var tempGroupId = '';
var edit="0";//是否允许修改/删除 默认0否
//部门管理页面
$(document).ready(function() {
	var type = "?type=" + $("#type").val();
	//查询用户
	$("#userSelect").click(function(){
		
		$("#page").attr("src",basePath+"logined/group_ext/userList.jsp"+type);
		
		refreshTree("gid_0");
	});
	//点击导入
	$("#importUser").click(function(){
		uploadUser();
		return false;
	});
	//$("#page").attr("src",basePath+"logined/user/userList.jsp"+type);
	(function(){
	    $("#page").attr("src",basePath+"logined/group_ext/userList.jsp"+type);
     refreshTree("gid_0");
	})();
	// 单击删除按钮
	$("#deleteGroupDiv").click(function() {
		if (tempGroupId == '' || tempGroupId == null || tempGroupId == 0){
			art.dialog.alert("请选择群组!");		
			return false;
		}
		if(edit == "0"){
			art.dialog.alert("您无权限删除该群组!");
			return;
		}
		art.dialog.confirm("确定要删除该群组吗？", function() {
			groupDelete();
		}, function() {
		});
		return false;
	});
});


//openSelectTreeUser(zTreeOnCheckResult);
function zTreeOnCheckResult(data) {
	var groupId = data.Id;
	tempGroupId = groupId;
	//类型
	var type=data.getType();
	var from = "&type=" + $("#type").val();
	if(data.data == "4"){
		from += "&edit=0";
		edit="0";
	}else if(data.data == "5"){
		from += "&edit=1";
		edit="1";
	}
	var source = "&source=tree";
	if(type=="group"){
 	//组id
 	var groupId=data.Id;
 	if(groupId){
 		$("#page").attr("src",basePath+"logined/group_ext/userList.jsp?groupId="+groupId+from);
 	}
	}else{
 	//人员id
 	var userId=data.Id;
 	if(userId){
 		$("#page").attr("src",basePath+"user/getUserById.action?userId="+userId+from+source);
 	}
	}
}

//刷新部门树
function refreshTree(id){
	openSelectTreeUser(zTreeOnCheckResult, null, id);
}


//新增组
function addGroup(){
	var url = basePath + "logined/group_ext/groupAdd.jsp?groupType=5";
	art.dialog.open(url,
	        {	id : "addGroup",
	            title:"新增群组",
	            width : 600,
	            height : 300,
			    opacity: 0.08,
			    close:function(){
			    	refreshTree("gid_0");
			    },
			    button: [{name: '确定',focus: true,callback :function(){
			    	var iframe = this.iframe.contentWindow;
			    	iframe.submitForm();
			    	return false;
			    }},{name: '取消',callback:function(){
			    	refreshTree("gid_0");
			    }}]
	        });
}


//新增组
function updateGroup(){
	if(tempGroupId == "0" || tempGroupId == ""){
		art.dialog.alert("请先选择一个群组进行修改!");
		return;
	}else if(edit == "0"){
		art.dialog.alert("您无权限修改该群组!");
		return;
	}
	var url = basePath + "group/preUpdateGroup.action?groupId=" + tempGroupId;
	art.dialog.open(url,
	        {	id : "updateGroup",
	            title:"修改群组",
	            width : 600,
	            height : 300,
			    opacity: 0.08,
			    close:function(){
			    	refreshTree("gid_0");
			    },
			    button: [{name: '确定',focus: true,callback :function(){
			    	var iframe = this.iframe.contentWindow;
			    	iframe.submitForm();
			    	return false;
			    }},{name: '取消',callback:function(){
			    	refreshTree("gid_0");
			    }}]
	        });
}
//提交删除
function groupDelete(){
 var paramData={
     'groupId':tempGroupId
 }; 
 $.ajax({
     url : basePath+"group/deleteGroup.action",
     type : "post",
     dataType :'text',
     data:paramData,
     success : function(data) {
         if(data==1)
         {             
         	// 更新部门列表
             openSelectTreeUser(zTreeOnCheckResult);
		    } else if(data==2) {
             art.dialog.alert('含有子群组不能删除！');
         }else if(data==3) {
             art.dialog.alert('群组包含人员不能删除！');
         }else{
         	art.dialog.alert('删除群组失败！');
         }
         
     }});
}