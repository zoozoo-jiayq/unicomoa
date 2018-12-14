/**
 * 人员选择
 * REN
 **/
/**
 * 主角色
 **/
function selectRoleUsers(userIds)
{
    openSelectUser(3,callBackRoleUsers,"user",userIds);//选择人员
}
function callBackRoleUsers(data)
{
    if(data){
    	var userIds = [];
    	var userNames = [];
    	$(data).each(function(i,item){
           userIds.push(item.id);
           userNames.push(item.name);
        });
        
        //提交添加
        var roleId=$("#roleId").val();  
        submitForm(roleId,userIds,1);

    }
}
/**
 * 辅助角色
 **/
function selectRoleAssists(userIds)
{
    openSelectUser(3,callBackRoleAssists,"user",userIds);//选择人员
}
function callBackRoleAssists(data)
{
    if(data){
        var userIds = [];
        var userNames = [];
        data.forEach(function(value) {
           userIds.push(value.Id);
           userNames.push(value.Name);
        });
        //提交添加
        var roleId=$("#roleId").val();  
        submitForm(roleId,userIds,0);

    }
}
/**
 * 提交信息
 * @return
 */
function submitForm(roleId,userIdArr,type) {
    //得到角色id
    if(!roleId){
        return;
    }
    var userIds="";
    for(var i=0;i<userIdArr.length;i++){
         userIds += '&userIds='+userIdArr[i];
    }
    qytx.app.ajax({
        url : basePath+"authority/addRoleUser.action",
        type : "post",
        dataType :'text',
        data:userIds+"&roleId="+roleId+"&type="+type,
        success : function(data) {
        	qytx.app.dialog.tips("维护角色成功", function(){
        		getDataTable();
        	});
       }
       });
}