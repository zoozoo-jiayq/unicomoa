/**
 * 打开人员选择对话框 callback 回调函数，会把选择的人员以json格式返回
 * 
 * @showType 显示类型 1 显示部门 2显示角色 3显示人员
 * @callback 回调方法
 * @param 参数，不处理只返回
 *            defaultSelectId 默认选择的Id,以,分割
 */
function openSelectUser(showType, callback, param, defaultSelectId, moduleName) {
	
	var url = basePath + "/logined/user/selectuser.jsp?showType=" + showType
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
				width : 360,
				height : 407,
				lock : true,
			    opacity:0.08,
//			    resize:false,
//			    drag:false,
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



/**
 * 打开人员选择对话框 callback 回调函数，会把选择的人员以json格式返回
 * 
 * @showType 显示类型 1 显示部门 2显示角色 3显示人员
 * @callback 回调方法
 * @param 参数，不处理只返回
 *            defaultSelectId 默认选择的Id,以,分割
 */
function openSelectGroup(showType, callback, param, defaultSelectId, moduleName) {
	var url = basePath + "/logined/user/selectgroup.jsp?showType=" + showType
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
				width : 360,
				height : 407,
				lock : true,
			    opacity:0.08,
//			    resize:false,
//			    drag:false,
				button : [{
							name : '确定',
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
