//添加 编辑栏目公用一个图片上传插件
jQuery(document).ready(function(){
	//图标默认中第一个
	$("#photoPath").val("/ydzj/images/content/dshy.png");
	$("#savelm").click(function(){pdSave();});
	addContent();
	showRight();
	showTD();
});
//绑定secondUL事件
function showWrong(){
	var obj = $("#userPhoto").parent();
	//绑定所以的secondUL中的li
	obj.mouseenter(function(){
		if($(this).find("em").attr("class") != "right_icon"){
			$(this).find("em").attr("class","wrong_icon");
		}
		//删除li
		$(this).find("em").click(function(){
			if($(this).attr("class") == "wrong_icon"){
				$(this).parent().remove();
				if($("#secondUL").find("li").size() <= 4){
					$("#addLI").removeAttr("style");
				}
			}
		});
	});
	obj.mouseleave(function(){
		$(this).find("em").removeClass("wrong_icon");
	});
	//为新增li绑定对号
	obj.click(function(){
		$("li").find("em").removeClass("right_icon");
		$(this).find("em").attr("class","right_icon");
		//往隐藏域传值
		$("#photoPath").val($(this).find("img").attr("tit"));
	});
}
//添加li标签
function showLI(){
	$("#userPhoto").attr("id","");
	$("#addLI").before("<li><em></em><img id=\"userPhoto\" src=\"\"/></li>");
	if($("#secondUL").find("li").size() > 4){
		$("#addLI").css({display:"none"});
	}
	showWrong();
}
//firstUL对号显示
function showRight(){
	$("#firstUL").find("li").click(function(){
		$("li").find("em").removeClass("right_icon");
		$(this).find("em").attr("class","right_icon");
		//往隐藏域传值
		$("#photoPath").val($(this).find("img").attr("tit"));
	});
	
}
var isApprover = "N";
//隐藏审批人
function showTD(){
	$("#showApp").bind("click",function(){
		$("#showTH").removeAttr("style");
		$("#showTD").removeAttr("style");
		isApprover = "Y";
	});
	$("#hideApp").bind("click",function(){
		$("#showTH").css({display:"none"});
		$("#showTD").css({display:"none"});
		$("#dxUserIds").val("");
		$("#dxUserName").val("");
		isApprover = "N";
	});
}

//判断必填
function pdSave(){
	var tTitle=$.trim($("#lmName").val());
	if(isApprover =='Y' && $.trim($("#dxUserName").val()) ==""){
		art.dialog.alert("请选择审批人员！");
		return;
	}
	if($.trim($("#lmName").val()) == null || $.trim($("#lmName").val()) == ""){
		art.dialog.alert("栏目名称不能为空！");
		return;
	}
	
	if($.trim($("#userNames").val()) == null || $.trim($("#userNames").val()) == ""){
		art.dialog.alert("请选择发布人员！");
		return;
	}
	if($.trim($("#fwNames").val()) == null || $.trim($("#fwNames").val()) == ""){
		art.dialog.alert("请选择发布范围！");
		return;
	}
	$.ajax({
		type:"post",
		url:basePath +"news/checkName.action",
		dataType:"text",
		data:{"Title":tTitle,"Category":1},
		success:function(data){
			if(data == "true"){
				art.dialog.alert("已存在该栏目名称！");
			} else {
				savelm();
			}
		}
	});
}
//添加栏目
function savelm(){
	$("#savelm").attr("disabled","disabled");
	var TitleIcon=$("#photoPath").val();
	var OrderIndex = $.trim($("#lmNum").val());
	if(OrderIndex == "" || OrderIndex == undefined ){
		OrderIndex = "999";
	}
	var param={
		//栏目名称
		"Title":$.trim($("#lmName").val()),
		//栏目图标
		"TitleIcon":TitleIcon,
		//发布范围id
		"Distribution":$("#fwIds").val(),
		//发布范围
		"DistributionName":$("#fwNames").val(),
		//发布人
		"Issuer":$("#userNames").val(),
		//发布人id
		"IssuerID":$("#userIds").val(),
		//是否审批
		"Approve":isApprover,
		//审批人
		"Approver":$("#dxUserName").val(),
		//审批人id
		"ApproverID":$("#dxUserIds").val(),
		//栏目序号
		"OrderIndex":OrderIndex,
		//栏目类别
		"Category":1
	};
	$.ajax({
		type:"post",
		url: basePath + "news/addColumn.action",
		dataType:"text",
		data:param,
		success:function(data){
			if(data > 0){
				art.dialog({
					content:"添加栏目成功！",
					cancel: false,
					icon: "succeed",
					fixed:true,
					ok : function(){
						window.location.href = basePath + "logined/notice/manage/list.jsp";
					}
				});
			}else{
				$("#savelm").removeAttr("disabled");
				art.dialog.alert("添加栏目失败!");
			}
		}
	});
}
//选择按钮
function addContent(){
	$("#addNames").click(function(){
		var userIds = $("#userIds").val();
		selectUsers(3,userIds,"bussinessMeetingAdmin");
	});
	$("#userEmpty").click(function(){
		$("#userNames").val("");
		$("#userIds").val("");
	});
	//选择范围
	$("#addfw").click(function(){
		var userIds = $("#fwIds").val();
		selectfw(3,userIds,"bussinessMeetingAdmin");
	});
	$("#fwEmpty").click(function(){
		$("#fwNames").val("");
		$("#fwIds").val("");
	});
	//人员单选
	$("#userName").click(function(){
		var userIds = $("#dxUserIds").val();
		roleAddUsers(0,userIds,"roleManager");
		return false;
	});
}