/**
 * 人员选择
 * REN
 **/
/**
 * 打开人员选择对话框 callback 回调函数，会把选择的人员以json格式返回
 * 
 * @showType 显示类型 1 显示部门 2显示角色 3显示人员
 * @callback 回调方法
 * @param 参数，不处理只返回
 *            defaultSelectId 默认选择的Id,以,分割
 */
function openSelectUserdx(showType, callback, roleId, defaultSelectId, moduleName) {
	var url = basePath + "/logined/news/manage/selectUserOne.jsp?showType=" + showType
			+ "&defaultSelectId=" + defaultSelectId + "&moduleName="
			+ moduleName;
	var title = "选择人员";
	if (showType == 1) {
		title = "选择部门";
	} else if (showType == 2) {
		title = "选择角色";
	}
	art.dialog.open(url, {
				title : title,
				width : 400,
				height : 427,
				lock : true,
			    opacity: 0.08,
			    resize:false,
			    drag:false,
				button : [{
							name : '确定',
							callback : function() {
								var userMap = art.dialog.data("userMap");
								callback(roleId,userMap,defaultSelectId);
								return true;
							}
						}, {
							name : '取消',
							callback : function() {
								return true;
							}
						}]
			}, false);

}

/**
 * 角色
 **/
function roleAddUsers(roleId,userIds, moduleName)
{
    openSelectUserdx(3,callBackRoleAddUsers,roleId,userIds, moduleName);//选择人员
}
function callBackRoleAddUsers(id,data)
{
    if(data){
    	var userIds = [];
    	var userNames = [];
        data.forEach(function(value) {
           userIds.push(value.Id);
           userNames.push(value.Name);
        });
        $("#dxUserIds").val(userIds.toString());
        $("#dxUserName").val(userNames.toString());
    }
}