    //提交表单
    function submitForm(){
    	 // 框架校验
    	if(!validator(document.getElementById("groupForm"))){
            return false;
        }
        //部门排序号
        var groupOrder= $.trim($("#groupOrder").val());
        //部门名称
        var groupName= $.trim($("#groupName").val());
        var parentId = $.trim($("#parentId").val());
        var paramData={
            'group.groupType':$("#groupType").val(),
            'group.groupState':0,
            'group.orderIndex':groupOrder,
            'group.groupName':groupName
        }; 
        qytx.app.ajax({
            url : basePath+"group/addGroup.action",
            type : "post",
            data:paramData,
            success : function(data) {
                if(data==1)
                {   
                    art.dialog.close("addGroup");
                } else if (data==2){
                	qytx.app.dialog.alert('群组名称已存在！');
                } else {
                	qytx.app.dialog.alert('保存群组信息失败！');
                }
            }});
    }