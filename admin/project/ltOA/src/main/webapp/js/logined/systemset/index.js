/**
 * 登陆js
 */
$(document).ready(function() {
			toRecordSet();
		});

/**
 * 修改个人资料
 * 
 * @return
 */
function toRecordSet() {
	$("#mainFrame").attr("src", basePath + "sysset/toRecordSet.action");
	$("#pwdSetli").removeClass("current");
	$("#recordSetli").addClass("current");
}

/**
 * 修改密码
 * 
 * @return
 */
function toPwdSet() {
	$("#mainFrame").attr("src", basePath + "sysset/toPwdSet.action");
	$("#pwdSetli").addClass("current");
	$("#recordSetli").removeClass("current");
}
