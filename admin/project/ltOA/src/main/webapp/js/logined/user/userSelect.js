/**
 * 人员更新
 */

$(document).ready(function() {
		    window.parent.frameResize();
			// 单击更新
			$("#userSelect").click(function() {
						$("#userSelectFrom")[0].submit();
						return false;
					});
			// 单击主角色
			$("#roleSelect").click(function() {
						selectUserRoleOnly();
						return false;
					});
			// 单击主角色清除
			$("#roleRemove").click(function() {
						$("#roleId").val("");
						$("#role").val("");
						return false;
					});
			// 单击选择部门
			$("#groupSelect").click(function() {
						selectGroup();
						return false;
					});
			// 单击部门移除
			$("#parentRemove").click(function() {
						$("#parentId").val("");
						$("#groupSel").val("");
						var refreshTree = window.parent.refreshTree;
						if (undefined != refreshTree && "" != refreshTree){
							refreshTree("gid_0");
						}
						return false;
					});
			// 单击导出
			$("#exportExcel").click(function() {
						var data = $("#userSelectFrom").serialize();
						data = "userVo." + data;
						var re = /&/g;
						data = data.replace(re, "&userVo.");
						window.open(basePath + "user/exportUserInfo.action?"
								+ data);
						return false;
					});
		})