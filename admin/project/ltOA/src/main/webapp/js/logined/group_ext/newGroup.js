var tempGroupId = '';
// 部门管理页面
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
	var from = "&edit=1&type=" + $("#type").val();
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
	var url = basePath + "logined/group_ext/groupAdd.jsp?groupType=4";
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
		art.dialog.alert("请先选择一个部门进行修改!");
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
			    	refreshTree("gid_"+tempGroupId);
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
                //openSelectTreeUser(zTreeOnCheckResult);
            	window.location.reload();
		    } else if(data==2) {
                art.dialog.alert('含有子群组不能删除！');
            }else if(data==3) {
                art.dialog.alert('群组包含人员不能删除！');
            }else{
            	art.dialog.alert('删除群组失败！');
            }
            
        }});
}