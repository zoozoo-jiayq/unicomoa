//提交更新表单
    function submitForm(){
    	 var valid=validator($("#groupForm")[0]);
         if(!valid){
            return false;
         }
        //得到部门id
        var groupId= $.trim($("#groupId").val());
        //部门排序号
        var groupOrder= $.trim($("#groupOrder").val());
        //部门名称
        var groupName= $.trim($("#groupName").val());
        //电话
        var groupPhone= $.trim($("#groupPhone").val());
        //上级部门
        var parentId= $.trim($("#parentId").val());
        if(parentId==""){
            parentId="0";
        }
        if(groupId==parentId){
            art.dialog.alert(sprintf("group.parent_group_not_self_group"));
            return;
        }
        //是否分支机构 
        var branch = $(":radio[name='branch']:checked").val();
        //部门主管
        var directorId= $.trim($("#directorId").val());
        //部门助理
        var assistantId= $.trim($("#assistantId").val());
        //上级主管领导
        var topDirectorId= $.trim($("#topDirectorId").val());
        //上级分管领导
        var topChangeId= $.trim($("#topChangeId").val());
        //部门职能
        var functions= $.trim($("#functions").val());
        //是否分支机构
        var isForkGroup = $("input[name='isForkGroup']:checked").val();
        if(functions.length>200){
        	qytx.app.dialog.alert("部门职能字数不能大于200");
            return;
        }
        
        var paramData={
            'group.groupId':groupId,
            'group.groupType':4,
            'group.groupState':0,
            'group.orderIndex':groupOrder,
            'group.groupName':groupName,
            'group.phone':groupPhone,
            'group.parentId':parentId,
            'group.branch':branch,
            'group.directorId':directorId,
            'group.assistantId':assistantId,
            'group.topDirectorId':topDirectorId,
            'group.topChangeId':topChangeId,
            'group.isForkGroup':isForkGroup,
            'group.functions':functions
        }; 
        qytx.app.ajax({
            url : basePath+"group/updateGroup.action",
            type : "post",
            dataType :'text',
            data:paramData,
            success : function(data) {
                if(data==1)
                {             
                	/*  art.dialog({
                    	   title:"消息",
                    	   content:"更新部门信息成功！",
                    	   width : 317,
                    	   height : 109,
                    	   icon:"succeed",
                    	    opacity:0.08,
                    	   ok:function(){},
                    	   close:function(){
                    		  var mainWindow=window.parent;
                               mainWindow.openSelectTreeUser(mainWindow.zTreeOnCheckResult, null, "gid_"+groupId);
                    	   }
                    	});*/
                	art.dialog.close("updateGroup");
                   
                } else if (data==2){
                	qytx.app.dialog.alert('群组名称已存在！');
                } else {
                	qytx.app.dialog.alert('更新群组信息失败！');
                }
            }});
    }