/**
 * 人员选择
 * REN
 **/
/**
 * 部门主管
 **/
function selectUserDirector()
{
    var directorId=$("#directorId").val();
    qytx.app.tree.alertUserTree({
    	type:"radio",
    	defaultSelectIds:directorId,
    	callback:callBackDirector
    });
}
function callBackDirector(data)
{
    if(data){
    	var userIds = [];
    	var userNames = [];
    	$(data).each(function(i,item){
    		userIds.push(item.id);
    		userNames.push(item.name);
    	});
        $("#directorId").val(userIds[0]);
        $("#director").val(userNames[0]);
    }
}
/**
 * 部门助理
 **/
function selectUserAssistant()
{
    var assistantId=$("#assistantId").val();
    qytx.app.tree.alertUserTree({
    	type:"radio",
    	defaultSelectIds:assistantId,
    	callback:callBackAssistant
    });
}
function callBackAssistant(data)
{
	if(data!=undefined) {
        var userIds = [];
        var userNames = [];
        $(data).each(function(i,item){
    		userIds.push(item.id);
    		userNames.push(item.name);
    	});
        $("#assistantId").val(userIds[0]);
        $("#assistant").val(userNames[0]);
    }
}
/**
 * 上级主管领导
 **/
function selectUserTopDirector()
{
     var topDirectorId=$("#topDirectorId").val();
     qytx.app.tree.alertUserTree({
     	type:"radio",
     	defaultSelectIds:topDirectorId,
     	callback:callBackTopDirector
     });
}
function callBackTopDirector(data)
{
    if(data){
        var userIds = [];
        var userNames = [];
        $(data).each(function(i,item){
    		userIds.push(item.id);
    		userNames.push(item.name);
    	});
        $("#topDirectorId").val(userIds[0]);
        $("#topDirector").val(userNames[0]);
    }
}
/**
 * 上级分管领导
 **/
function selectUserTopChange()
{
    var topChangeId=$("#topChangeId").val();
    qytx.app.tree.alertUserTree({
     	type:"radio",
     	defaultSelectIds:topChangeId,
     	callback:callBackTopChange
     });
}
function callBackTopChange(data)
{
    if(data){
        var userIds = [];
        var userNames = [];
        $(data).each(function(i,item){
    		userIds.push(item.id);
    		userNames.push(item.name);
    	});
        $("#topChangeId").val(userIds[0]);
        $("#topChange").val(userNames[0]);
    }
}
/**
 * 上级部门
 **/
function selectGroupParent()
{
    openSelectUser(1,callBackParent);
}
function callBackParent(data)
{
    if(data){
        var userIds = [];
        var userNames = [];
        data.forEach(function(value) {
           userIds.push(value.Id);
           userNames.push(value.Name);
        });
        $("#parentId").val(userIds[0]);
        $("#parent").val(userNames[0]);
    }
}
/**
 * 打开人员选择对话框
 * callback 回调函数，会把选择的人员以json格式返回
 * @showType 显示类型 1 显示部门 2显示角色 3显示人员
 * @callback 回调方法
 */
function openSelectUser(showType,callback,defaultSelectId) {
    var url = basePath + "/logined/user/selectuserSign.jsp?showType="+showType+"&defaultSelectId="+defaultSelectId;
    var title="选择人员";
    if(showType==1)
    {
        title="选择部门";
    }
    else if(showType==2)
    {
        title="选择角色";
    }
    art.dialog.open(url,
        {
            title:title,
            width : 360,
			height : 407,
			lock : true,
		    opacity: 0.08,
            button:[
                {
                    name:'确定',
                    focus:true,
                    callback:function () {
                        var userMap =art.dialog.data("userMap");
                        callback(userMap);
                        return true;
                    }
                },
                {
                    name:'取消',
                    callback:function () {
                        return true;
                    }
                }
            ]
        }, false);

}