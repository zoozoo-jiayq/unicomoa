// 部门管理页面
$(document).ready(function() {
	// 初始化部门树
	openSelectTreeUser(zTreeOnCheckResult, "", $("#parentId").val());

	// 部门列表
	$("#groupList").click(function() {
		$("#page").attr("src", basePath + "group/findGradeGroupTree.action");
	});
	$("#page").attr("src", basePath + "logined/group/groupAdd.jsp");

	// 单击新增部门按钮
	$("#addDiv").click(function() {
		$("#page").attr("src", basePath + "logined/group/groupAdd.jsp");
		return false;
	});

	// 单击删除按钮
	$("#deleteGroupDiv").click(function() {
		if (tempGroupId == '' || tempGroupId == null || tempGroupId == 0){
			art.dialog.alert("请选择部门!");		
			return false;
		}
		art.dialog.confirm("确定要删除该部门吗？", function() {
			groupDelete();
		}, function() {
		})
		return false;
	});
});

var tempGroupId = '';
function zTreeOnCheckResult(data) {
	var groupId = data.Id;
	tempGroupId = groupId;
	if (groupId != 0) {
		$("#page").attr("src", basePath + "group/loadGroupInfo.action?groupId=" + groupId);
	}
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
                $("#page").attr("src", basePath + "logined/group/groupAdd.jsp");
		    } else if(data==2) {
                art.dialog.alert('含有子部门不能删除！');
            }else if(data==3) {
                art.dialog.alert('部门包含人员不能删除！');
            }else if(data==4) {
                art.dialog.alert('默认部门不能删除！');
            }else{
            	art.dialog.alert('删除部门失败！');
            }
            
        }});
}