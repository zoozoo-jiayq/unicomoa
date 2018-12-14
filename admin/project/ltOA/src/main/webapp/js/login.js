/**
 * 登陆js
 */
$(document).ready(function() {
		window.location.href = basePath+"wangzhan/html/index.html";
		
		$("#j_username").keyup(function(){
			$("#j_username").val($.trim($("#j_username").val().replace(/[`~!@#$%^&*￥（）【】：；”“‘’？|，。()+<>?:"{},.\/;'[\]]/im,'')));
		});
			// 判断是否从其他页面跳转过来
			var error = $.query.get('loginFailure');
			if (error != undefined && error != "") {
				 $("#checkCode").val("");
				 loadimage();
				if (error == "failure") {
					$("#perror").html("用户名或密码不正确！");
				} else if (error == "loginNameError") {
					$("#perror").html("用户名或密码不正确！");
				} else if (error == "loginForbid") {
					$("#perror").html("对不起，该账号暂未启用，请与管理员联系！");
				} else if (error == "codeError") {
					$("#perror").html("验证码错误！");
					rememberUser();
				} else if (error == "loginNameRepeat") {
					$("#perror").html("用户名重复，请与管理员联系！");
					rememberUser();
				} else if(error == "companyCancel"){
					$("#perror").html("公司已注销，请与管理员联系！");
					rememberUser();
				} else if(error == "companyExpiration"){
					$("#perror").html("公司帐号已到期，请与管理员联系！");
					rememberUser();
				}else if(error == "companyNoOpen"){
					$("#perror").html("公司未开通，请与管理员联系！");
					rememberUser();
				}

				$(".error").show();
			} else {
				rememberUser();
			}
			 // 登陆按钮绑定事件
			 $("#btnLogin").bind("click", function() {
						submit_form();
			 });
		});
function submit_form() {
	var loginName = $.trim($("#j_username").val());
	var loginPass = $.trim($("#j_password").val());
	var checkCode = $.trim($("#checkCode").val());  
	if (loginName == "") {
		$("#perror").html("请输入用户名！");
		$(".error").show();
		 loadimage();
		return;
	}
	if (loginPass == "") {
		$("#perror").html("请输入密码！");
		$(".error").show();
		 loadimage();
		return;
	}
	
	if (checkCode == "") {
		$("#perror").html("请输入验证码！");
		$(".error").show();
		loadimage();
		return;
	}
//	if (loginPass.length < 6) {
//		$("#perror").html("对不起,密码长度不能少于6位！");
//		return;
//	}
	// 判断是否需要记住用户名和密码
	if ($("#cb_remember").attr("checked") == "checked") {
		$.cookie('cbb_loginName', loginName, {
					expires : 365
				});// 登录名保存到cookie ，有效期365天
		$.cookie('cbb_loginPass', loginPass, {
					expires : 365
				});// 登录密码保存到cookie ，有效期365天
	} else {
		// 移除cookie
		$.removeCookie('cbb_loginName');
		$.removeCookie('cbb_loginPass');
	}
	
//	$("#loginForm").submit();
	 
	 loginByToken(); //sso登陆
	 
}



// 回车事件
document.onkeydown = function(e) {
	// 兼容FF和IE和Opera
	var theEvent = e || window.event;
	var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
	if (code == 13) {
		submit_form();// 要触发的方法
	}
	return true;
}
// 记住用户名
function rememberUser() {
	var loginName = $.cookie('cbb_loginName'); // 名称需要根据具体情况修改
	var loginPass = $.cookie("cbb_loginPass"); // 名称需要根据具体情况修改
	if (loginName != undefined && loginPass != undefined) {
		// 读取到cookie后给登录名和密码赋值
		$("#j_username").val(loginName);
		$("#j_password").val(loginPass);
		 funPlaceholder(document.getElementById("j_username"));
		 funPlaceholder(document.getElementById("j_password"));
		 funPlaceholder(document.getElementById("checkCode"));
		$("#cb_remember").attr("checked", "true");
		$("#checkCode").focus();  
	}else{
		 funPlaceholder(document.getElementById("j_username"));
		 funPlaceholder(document.getElementById("j_password"));
		 funPlaceholder(document.getElementById("checkCode"));
//		 $("#j_username").focus();  
	}
}

function loadimage() {
	document.getElementById("randImage").src = basePath + "common/code.jsp?"
			+ Math.random();
}


/**
 *  SSO登陆
 */
function loginByToken(loginName){
	$(".error").hide();
	var j_username = $.trim($("#j_username").val());
	var j_password = $.trim($("#j_password").val());
	var checkCode = $.trim($("#checkCode").val());
	var paramData = {
			'j_username' : j_username,
			'j_password' : j_password,
			'checkCode' : checkCode
	};
	$.ajax({
		url : basePath+"sso/login",
		type : "post",
		dataType :'json',
		data:paramData,
		success : function(data) {
			 if(data){
				 var token = data.sso_token;
				 var result  = data.result;
				 if(result=="success"){
				   $("#sso_token").val(token);
				   $("#loginForm").submit();
				 }else if(result=="forbid"){
					    $("#perror").html("用户名或密码不正确！");
						$(".error").show();
						$("#checkCode").val("");
						 loadimage();
				 }else if(result=="loginNameError"){
					    $("#perror").html("用户名或密码不正确！");
						$(".error").show();
						$("#checkCode").val("");
						 loadimage();
				 }else if(result=="codeError"){
					     $("#perror").html("验证码错误！");
						 $(".error").show();
						 $("#checkCode").val("");
						 loadimage();
				 }else if(result=="loginNameRepeat"){
					     $("#perror").html("用户名重复，请与管理员联系！");
						 $(".error").show();
						 $("#checkCode").val("");
						 loadimage();
				 }else if(error == "companyCancel"){
						$("#perror").html("公司已注销，请与管理员联系！");
						$(".error").show();
						$("#checkCode").val("");
				 }else if(error == "companyExpiration"){
						$("#perror").html("公司帐号已到期，请与管理员联系！");
						$(".error").show();
						$("#checkCode").val("");
				}else if(error == "companyNoOpen"){
						$("#perror").html("公司未开通，请与管理员联系！");
						$(".error").show();
						$("#checkCode").val("");
				}
			 }
			  
	     }
	}); 
}


/**
 * 获取当前用户的工号 工号保存u盘的序列号
 */
function loginByUsb(loginName){
	var workNo = "";
	var paramData = {
			'loginName' : loginName
	};
//	$.ajax({
//		url : basePath+"login/getLoginUserByLoginName.action",
//		type : "post",
//		dataType :'text',
//		data:paramData,
//		success : function(data) {
//			/*if(data){
//				jsonData = eval("(" + data + ")");
//				workNo = jsonData.workNo;
//			}
//			if(workNo){ //如果存在workNo
//					 checkUserUsb(workNo); //验证Ukey
//			}else{
//				$("#loginForm").submit();
//			}*/
//			window.location.href=basePath+"logined/index.jsp";
//	     }
//	}); 
	$("#loginForm").submit();
}
//下面实现U盘ca认证登录
function checkUserUsb(workNo){ 
	if(document.JITDSignOcx.SetCert){
		//初始化客户端签名证书
		document.JITDSignOcx.SetCert("SC","","","","","");
		//判断初始化是否成功
		if(document.JITDSignOcx.GetErrorCode()!=0){
			$("#perror").html("错误信息：" + document.JITDSignOcx.GetErrorMessage());
			return false;
		}
		//以下为业务系统中从主题(DN)项中提取CN项
		var CertDN,String_CN,Index_CN,Index_Comma;
		CertDN = document.JITDSignOcx.GetCertInfo("SC", 0, "");	//得到证书DN
		Index_CN = CertDN.indexOf("CN=");										//获取CN项位置
		String_CN = CertDN.substring(Index_CN+3,CertDN.length);					//将CN项前的信息去除
		Index_Comma = String_CN.indexOf(",");									//第1个逗号所在位置
		if(Index_Comma != -1)	{//如果"CN="后仍有内容
		      String_CN = String_CN.substring(0,Index_Comma); //证书号
		}		
		if(workNo!=String_CN){
			art.dialog.alert("当前用户和证书不匹配！");
			return;
		}
		//loginForm.username.value = String_CN;
		//获取企业组织机构代码
		var usercardid = document.JITDSignOcx.getCertInfo("SC",7,"1.2.86.11.7.3");
		//对随机数进行签名
		var random = $("#random").val();
		var randomsigndata = document.JITDSignOcx.AttachSign(CertDN,random);
		if(randomsigndata == ""){
			$("#perror").html("签名失败,请确认证书有效性!");
			return false;
		}
		//获取签名证书BASE64编码
		var signcert = document.JITDSignOcx.GetCertInfo("SC", 8, "");
		var strDN = CertDN;
		var env = document.JITDSignOcx.EncryptEnvelop(strDN, "strOrgData");
		var paramData = {
				'signcert' : signcert,
				'randomsigndata' : randomsigndata,
				'random' : random
		};
		$.ajax({
			url : basePath+"login/checkCert.action",
			type : "post",
			dataType :'text',
			data:paramData,
			success : function(data) {
				   if(data==0){
					   $("#loginForm").submit();
				   }else if(data==1){
					   art.dialog.alert("用户身份验证成功.(证书即将到期,请联系河南CA办理延期手续.客户服务热线:9611111)",function(){
						   $("#loginForm").submit();
					   });
				   }else{
					   art.dialog.alert("用户身份验证失败，错误代码:"+data);
				   }
		     }
		}); 
	}else{
		var showText="请安装HncaClient用户工具！ <em style=\"padding-left:2px;\" class=\"con_ie\"><a  href=\""+basePath+"download/HCT_Setup v2.1.0.08.exe\">HCT_Setup.exe<a></em>";
		$("#perror").html(showText);
	}
	
 }
