    //REN
    $(document).ready(function() {
        //部门职能长度
        $("#functions").maxLength(200);
        //单击部门主管添加
        $("#directorSelect").click(function(){
            selectUserDirector();
            return false;
        });
        //单击部门主管移除
        $("#directorRemove").click(function(){
            $("#directorId").val("");
            $("#director").val("");
            return false;
        });
        //单击部门助理添加
        $("#assistantSelect").click(function(){
            selectUserAssistant();
            return false;
        });
        //单击部门助理移除
        $("#assistantRemove").click(function(){
            $("#assistantId").val("");
            $("#assistant").val("");
            return false;
        });
        //单击上级主管领导添加
        $("#topDirectorSelect").click(function(){
            selectUserTopDirector();
            return false;
        });
        //单击上级主管领导移除
        $("#topDirectorRemove").click(function(){
            $("#topDirectorId").val("");
            $("#topDirector").val("");
            return false;
        });
        //单击上级分管领导添加
        $("#topChangeSelect").click(function(){
            selectUserTopChange();
            return false;
        });
        //单击上级分管领导移除
        $("#topChangeRemove").click(function(){
            $("#topChangeId").val("");
            $("#topChange").val("");
            return false;
        });
        //单击部门添加
        $("#parentSelect").click(function(){
            selectGroupParent();
            return false;
        });
        //单击部门移除
        $("#parentRemove").click(function(){
            $("#parentId").val("");
            $("#groupSel").val("");
            return false;
        });
        //更新组
        $("#groupUpdate").click(function() {
            var valid=qytx.app.valid.check({form:$("#groupForm")[0]});
            if(valid){
               submitForm();
            }
            return false;
        });
        //新建部门
        $("#forwordGroupAdd").click(function(){
             window.location.href=basePath+"logined/group/groupAdd.jsp";
             return false;
        });
    });
    //提交更新表单
    function submitForm(){
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
        	qytx.app.dialog.alert(sprintf("group.parent_group_not_self_group"));
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
            'group.groupType':1,
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
            'group.functions':functions
        }; 
        qytx.app.ajax({
            url : basePath+"group/updateGroup.action",
            type : "post",
            data:paramData,
            success : function(data) {
                if(data==1)
                {           
                	var mainWindow=window.parent;
                	mainWindow.openSelectTreeUser(mainWindow.zTreeOnCheckResult, null, "gid_"+groupId);   
                	qytx.app.dialog.tips("更新成功!");
                } else if (data==2){
                	qytx.app.dialog.alert('部门名称已存在，请重新输入');
                } else {
                	qytx.app.dialog.alert('操作失败');
                }
            }});
    }