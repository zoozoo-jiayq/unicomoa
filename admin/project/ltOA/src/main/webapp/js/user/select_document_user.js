/**
 * 打开人员选择对话框 callback 回调函数，会把选择的人员以json格式返回
 * 
 * @showType 显示类型 1 显示部门 2显示角色 3显示人员 (这里固定是3)
 * @callback 回调方法
 * @radioType 按钮类型 0 单选 1 复选
 * @param 参数，不处理只返回
 *            defaultSelectId 默认选择的Id,以,分割
 */
function openDocSelectUser(showType, callback,radioType, param, defaultSelectId, moduleName,failCallback) {

	var url = basePath + "/logined/user/select_document_user.jsp?showType=" + showType
			+ "&defaultSelectId=" + defaultSelectId + "&moduleName="+ moduleName
            +"&radioType="+radioType;

	var title = "选择人员";
	if (showType == 1) {
		title = "选择单位组织";
	} else if (showType == 2) {
		title = "选择角色";
	}
	art.dialog.open(url, {
				title : title,
				width : 360,
				height : 400,
				lock : true,
			    opacity: 0.08,
				button : [{
							name : '确定',
							focus:true,
							callback : function() {
								var userMap = art.dialog.data("userMap");
								callback(userMap, param);
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