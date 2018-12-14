// 部门管理页面
$(document).ready(function() {
	// 重置iframe高度
//	window.parent.frameResize();
	// 初始化部门树
	openSelectTreeUser(zTreeOnCheckResult);

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
			qytx.app.dialog.alert("请至少选择一项");		
			return false;
		}
		qytx.app.dialog.confirm("确定要删除吗？", function() {
			groupDelete();
		})
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
    qytx.app.ajax({
        url : basePath+"group/deleteGroup.action",
        type : "post",
        data:paramData,
        success : function(data) {
            if(data==1)
            {             
            	// 更新部门列表
                openSelectTreeUser(zTreeOnCheckResult);
                $("#page").attr("src", basePath + "logined/group/groupAdd.jsp");
		    } else if(data==2) {
		    	qytx.app.dialog.alert('含有子部门不能删除！');
            }else if(data==3) {
            	qytx.app.dialog.alert('部门包含人员不能删除！');
            }else{
            	qytx.app.dialog.alert('操作失败');
            }
            
        }});
}