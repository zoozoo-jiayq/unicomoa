/**
 * 登陆js
 */
$(document).ready(function() {
			$("#submitButton").bind("click", function() {
						pwdSet();
					});

		});

function pwdSet() {
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}

	if (newPass1Blur() == false) {
		return false;
	}

	var userId = $("#userId").val();
	var loginPass = $("#loginPass").val();
	var oldPass = $("#oldPass").val();
	var newPass = $("#newPass").val();
	var newPass1 = $("#newPass1").val();
	var paramData = {
		'userId' : userId,
		'oldPass':oldPass,
		'user.loginPass' : newPass
	};

	var urlStr = basePath + "sysset/sysset_updatePwd.action";
	$.ajax({
				url : urlStr,
				type : "post",
				dataType : 'json',
				data : paramData,
				success : function(data) {
					if (data == 0) {
						art.dialog({
							   title:"消息",
							   content:"密码修改成功！",
							   width:320,
							   height:110,
							   icon:"succeed",
							   opacity:0.08,
							   ok:function(){},
							   close:function(){
									window.document.location.reload();
							   }
							});
					}else{
						art.dialog({
							   title:"消息",
							   content:"原密码不正确！",
							   width:320,
							   height:110,
							   icon:"succeed",
							   opacity:0.08,
							   ok:function(){},
							   close:function(){
							   }
							});
					}
				}
			});
}

// 原始密码
function oldPassBlur() {
	var userId = $("#userId").val();
	var oldPass = $("#oldPass").val();

	if (oldPass == "") {
		showObjError($("#oldPass"), 'sysset.sysset_oldPass_not_null');
		return;
	}

	var paramData = {
		'userId' : userId,
		'user.loginPass' : oldPass
	};

	var urlStr = basePath + "sysset/sysset_validatePwd.action";
	$.ajax({
				url : urlStr,
				type : "post",
				dataType : 'json',
				data : paramData,
				success : function(data) {
					if (data == 1) {
						showObjError($("#oldPass"),
								'sysset.sysset_oldPass_is_wrong');
						return;
					}
				}
			});
}

// 新密码
function newPassFocus() {
	var oldPass = $("#oldPass").val();
	if (oldPass == "") {
		$("#oldPass").focus();
	}
}

function newPass1Focus() {
	var newPass = $("#newPass").val();
	if (newPass == "") {
		$("#newPass").focus();
	}
}

function newPass1Blur() {
	var newPass = $("#newPass").val();
	var newPass1 = $("#newPass1").val();
	if (newPass != newPass1) {
		$("#newPass1").focus();
		showObjError($("#newPass1"), 'sysset.sysset_newPass1_is_wrong');
		return false;
	}
}

/**
 * 获取当前日期
 * 
 * @return
 */
function CurentTime() {
	var now = new Date();

	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日

	var hh = now.getHours(); // 时
	var mm = now.getMinutes(); // 分

	var clock = year + "-";

	if (month < 10)
		clock += "0";

	clock += month + "-";

	if (day < 10)
		clock += "0";

	clock += day;
	/**
	 * if(hh < 10) clock += "0";
	 * 
	 * clock += hh + ":"; if (mm < 10) clock += '0'; clock += mm;
	 */
	$("#currentDate").html(clock);
}
