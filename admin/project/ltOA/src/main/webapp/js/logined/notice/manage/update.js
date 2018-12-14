jQuery(document).ready(function(){
	$("#savelm").click(function(){pdSave();});
	showTD();
	addContent();
	showRight();
	//将已选择的栏目图标展示出来或者给本地图片加样式
	loadLocalOrLan();
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
function showTD(){
	if($("#isApp").text() == "是"){
		$("#showTH").removeAttr("style");
		$("#showTD").removeAttr("style");
	}
	if($("#isApp").text() == "否"){
		$("#showTH").css({display:"none"});
		$("#showTD").css({display:"none"});
//		$("#dxUserIds").val("");
//		$("#dxUserName").val("");
	}
}

//判断必填
function pdSave(){
	var tTitle=$.trim($("#lmName").val());
	var firstTitle=$.trim($("#firstTitle").val());
	if($("#isApp").text() == "是" && $.trim($("#dxUserName").val()) == "" ){
		art.dialog.alert("请选择审批人员！");
		return;
	}
	if($.trim($("#lmName").val()) == null || $.trim($("#lmName").val()) == ""){
		art.dialog.alert("栏目名称不能为空!");
		return;
	}
	
	if($.trim($("#userNames").val()) == null || $.trim($("#userNames").val()) == ""){
		art.dialog.alert("请选择发布人员!");
		return;
	}
	if($.trim($("#fwNames").val()) == null || $.trim($("#fwNames").val()) == ""){
		art.dialog.alert("请选择发布范围!");
		return;
	}
	$.ajax({
		type:"post",
		url:basePath +"news/checkName.action",
		dataType:"text",
		data:{"Title":tTitle,"Category":1},
		success:function(data){
			if(data == "true" && tTitle != firstTitle ){
				art.dialog.alert("已存在该栏目名称！");
				return;
			}else{
				savelm();
			}
		}
	});

}
//添加栏目
function savelm(){
	var TitleIcon=$("#photoPath").val();
//	for(var i=0;i<$("em").size();i++){
//		if($("em").eq(i).attr("class") == "right_icon"){
//			TitleIcon = $("em").eq(i).parent().find("img").attr("src");
//		}
//	}
	var param={
		//id
		"id":$("#VID").val(),
		//创建时间
		"CreatedDatetime":$("#CreatedDatetime").val(),
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
		"Approve":$("#isApp").val(),
		//审批人
		"Approver":$("#dxUserName").val(),
		//审批人id
		"ApproverID":$("#dxUserIds").val(),
		//栏目序号
		"OrderIndex":$.trim($("#lmNum").val()),
		//栏目类别
		"Category":1
	};
	$.ajax({
		type:"post",
		url: basePath + "news/updateColumn.action",
		dataType:"text",
		data:param,
		success:function(data){
			if(data > 0){
				art.dialog({
					content:"栏目编辑成功!",
					cancel:false,
					icon:"succeed",
					ok:function(){
						window.location.href = basePath + "logined/notice/manage/list.jsp";
					}
				});
			}else{
				art.dialog.alert("栏目编辑失败！");
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



//给已选择的默认图片加样式
function addIconForLocal(titleIcon){
	switch (titleIcon){
		case  "/ydzj/images/content/dshy.png":
			$("img[tit='/ydzj/images/content/dshy.png']").parent().find("em").attr("class","right_icon");
			return false;
		case  "/ydzj/images/content/gg.png":
			$("img[tit='/ydzj/images/content/gg.png']").parent().find("em").attr("class","right_icon");
			return false;
		case  "/ydzj/images/content/news.png":
			$("img[tit='/ydzj/images/content/news.png']").parent().find("em").attr("class","right_icon");
			return false;
		case  "/ydzj/images/content/qywh.png":
			$("img[tit='/ydzj/images/content/qywh.png']").parent().find("em").attr("class","right_icon");
			return false;
		case  "/ydzj/images/content/qz.png":
			$("img[tit='/ydzj/images/content/qz.png']").parent().find("em").attr("class","right_icon");
			return false;
		case  "/ydzj/images/content/scsz.png":
			$("img[tit='/ydzj/images/content/scsz.png']").parent().find("em").attr("class","right_icon");
			return false;
		case  "/ydzj/images/content/xxyl.png":
			$("img[tit='/ydzj/images/content/xxyl.png']").parent().find("em").attr("class","right_icon");
			return false;
		case  "/ydzj/images/content/xw.png":
			$("img[tit='/ydzj/images/content/xw.png']").parent().find("em").attr("class","right_icon");
			return false;
		default:
			return true;
	}
}

//将已选择的栏目图标展示出来或者给本地图片加样式
function loadLocalOrLan(){
	var titleIcon=$("#photoPath").val();
	if(titleIcon == "" || titleIcon == undefined ){
		return;
	}
	if(addIconForLocal(titleIcon)){//栏目图标不在本地图片中
		$("#addLI").before("<li><em></em><img id=\"userPhoto\" src=\"\"/></li>");
		if($("#secondUL").find("li").size() > 4){
			$("#addLI").css({display:"none"});
		}
		showWrong();
		$("#userPhoto").attr("src",downPath+titleIcon);
		$("#userPhoto").attr("tit",titleIcon);
		$("#userPhoto").parent().find("em").attr("class","right_icon");
	}
}