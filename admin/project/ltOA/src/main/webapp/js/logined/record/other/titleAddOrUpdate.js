var ue;
$(function(){
	/*加载ueditor*/
	ue = UE.getEditor('contentInfo', {
		initialFrameWidth : "100%",
		toolbars:[[
                    "bold","underline","strikethrough","forecolor","backcolor","justifyleft",
                    "justifycenter","justifyright","justifyjustify","customstyle","paragraph","fontsize","insertimage","date","time"]]
		});

	var titleId = $("#titleId").val();
	if(titleId){
		var approveUserId = $("#approveUserId").val();
		$("#accessType").val(accessType);
		ue.addListener("ready", function () {
	        // editor准备好之后才可以使用
	        ue.setContent(contentInfo);
	     });
		initUserTree("uid_"+approveUserId);
	}else{
		initUserTree("");
	}
	//initRoleTree();暂时去掉
	
	$("#approveUserName").click(function() {
		showApproveUser();
		return false;
	});
	
	//科室选择不能点击回车
    $("#approveUserName").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
});


function addOrUpdateTitle(obj){
	$(obj).attr("disabled",true);
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		$(obj).attr("disabled",false);
		return;
	}
	var accessType = $("#accessType").val();
	if(!accessType){
		qytx.app.valid.showObjError($("#accessType"), 'recordTitle.access_type_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#accessType"));
	}
	
	var titleId = $("#titleId").val();
	var approveUserId = $("#approveUserId").val();
	var title = $("#title").val();
	var applyDate = $("#applyDate").val();
	var giveDate = $("#giveDate").val();
	var nextTitle = $("#nextTitle").val();
	var nextApplyDate = $("#nextApplyDate").val();
	var hiringPosition = $("#hiringPosition").val();
	var hiringUnits = $("#hiringUnits").val();
	var hiringBeginDate = $("#hiringBeginDate").val();
	var hiringEndDate = $("#hiringEndDate").val();
    var remark=ue.getContent();
    var attment = $("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var param = {
		"titleId":titleId,
		"recordTitle.accessType":accessType,
		"recordTitle.userInfo.userId":$("#userId").val(),
		"recordTitle.approveUser.userId":approveUserId,
		"recordTitle.title":title,
		"recordTitle.applyDate":applyDate,
		"recordTitle.giveDate":giveDate,
		"recordTitle.nextTitle":nextTitle,
		"recordTitle.nextApplyDate":nextApplyDate,
		"recordTitle.hiringPosition":hiringPosition,
		"recordTitle.hiringUnits":hiringUnits,
		"recordTitle.hiringBeginDate":hiringBeginDate,
		"recordTitle.hiringEndDate":hiringEndDate,
		"recordTitle.remark":remark,
		"recordTitle.attment":attment
	};
	var url = basePath+"/logined/recordOther/saveOrUpdateTitle.action";
	$.ajax({
		url:url,
		type:'POST',
		data:param,
		dataType:'json',
		success:function(result){
			if(result=="1"){
				art.dialog.tips("添加成功!");
				setTimeout(function(){
					window.close();
				},1000);
			}else if(result=="2"){
				art.dialog.tips("修改成功!");
				setTimeout(function(){
					window.close();
				},1000);
			}else{
				$(obj).attr("disabled",false);
				art.dialog.alert("操作失败!");
			}
		}
	});
	
	
}

/**
 * 批准人-隐藏显示函数
 */
var showOrHideApproveUser = true;
function showApproveUser(){
	$('#approveUserContent').toggle(showOrHideApproveUser);
	//相当于
	if (showOrHideApproveUser) {
		showOrHideApproveUser=false;
		var groupObj = $("#approveUserName");
		var groupOffset = $("#approveUserName").position();
		$("#approveUserContent").css({left:groupOffset.left + "px", top:groupOffset.top + groupObj.outerHeight() - 1 + "px"}).show();
		$("#approveUserTreeDiv").one("mouseleave",function(){
			$("#approveUserContent").hide();
			showOrHideApproveUser=true;
			return false;
        });
	} else {
	   $("#approveUserContent").hide();
	   showOrHideApproveUser=true;
	}
}


function initUserTree(defaultApproveUserId){
 var dataParam = {
	            'type' : 1,
	            'showType' :3
	};
    qytx.app.ajax({
		url : basePath+ "user/selectUser.action",
		type : 'post',
		dataType :'json',
		data:dataParam,
		success : function(data) {
			/*加载调动前部门*/
			qytx.app.tree.base({
				id	:	"approveUserTree",
				type:"radio",
				defaultSelectId:defaultApproveUserId,
				data:	data,
				click:	callBackApproveUser
			});
		}
	});
}


function callBackApproveUser(data)
{
	if(data){
		var userIds = [];
		var userNames = [];
		$(data).each(function(i,item){
			if(item.id.indexOf("uid_") != -1&&item.id.substring(4)!=0){
				userIds.push(item.id.substring(4));
				userNames.push(item.name);
			}
		});
		$("#approveUserName").val(userNames[0]);
		$("#approveUserId").val(userIds[0]);
	}
}

