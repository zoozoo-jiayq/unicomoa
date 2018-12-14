var ue;
$(function(){
	/*加载ueditor*/
	ue = UE.getEditor('contentInfo', {
		initialFrameWidth : "100%",
		toolbars:[[
                    "bold","underline","strikethrough","forecolor","backcolor","justifyleft",
                    "justifycenter","justifyright","justifyjustify","customstyle","paragraph","fontsize","insertimage","date","time"]]
		});

	var transferId = $("#transferId").val();
	if(transferId){
		
		var transferProcedure =$("#transferProcedure").val();
		$("#limitTransferProcedure").empty().text(255-transferProcedure.length);
		
		var remark =$("#remark").val();
		$("#limitRemark").empty().text(255-remark.length);
		
		var beforeGroupId = $("#beforeGroupId").val();
		var postGroupId = $("#postGroupId").val();
		$("#type").val(transferType);
		ue.addListener("ready", function () {
	        // editor准备好之后才可以使用
	        ue.setContent(contentInfo);
	     });
		initGroupTree("gid_"+beforeGroupId,"gid_"+postGroupId);
	}else{
		initGroupTree("","");
	}
	//initRoleTree();暂时去掉
	
	$("#beforeGroupName").click(function() {
		showbeforeGroup();
		return false;
	});
	
	//科室选择不能点击回车
    $("#beforeGroupName").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
    
    $("#postGroupName").click(function() {
    	showpostGroup();
    	return false;
    });
    
    //科室选择不能点击回车
    $("#postGroupName").keydown(function(e) {
    	if(e.which==8){  
    		return false;       
    	}
    });
    
    $("#postRoleName").click(function() {
    	showpostRole();
    	return false;
    });
    
    //科室选择不能点击回车
    $("#postRoleName").keydown(function(e) {
    	if(e.which==8){  
    		return false;       
    	}
    });
	
});


function addOrUpdateTransfer(obj){
	$(obj).attr("disabled",true);
	var type = $("#type").val();
	if(!type){
		qytx.app.valid.showObjError($("#type"), 'transfer.transfer_type_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#type"));
	}
	var transferDate = $("#transferDate").val();
	if(!transferDate){
		qytx.app.valid.showObjError($("#transferDateHidden"), 'transfer.transfer_date_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#transferDateHidden"));
	}
	
	var effectiveDate = $("#effectiveDate").val();
	if(!effectiveDate){
		$(obj).attr("disabled",false);
		qytx.app.valid.showObjError($("#effectiveDateHidden"), 'transfer.effective_date_not_null');
		return;
	}else{
		qytx.app.valid.hideError($("#effectiveDateHidden"));
	}
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		$(obj).attr("disabled",false);
		return;
	}
	var transferId = $("#transferId").val();
	var beforePosition = $("#beforePosition").val();
	var postPosition = $("#postPosition").val();
	var transferProcedure = $("#transferProcedure").val();
	var remark = $("#remark").val();
    var transferReason=ue.getContent();
    var attment = $("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var beforeGroupId = $("#beforeGroupId").val();
	var postGroupId = $("#postGroupId").val();
	var param = {
		"transferId":transferId,
		"transfer.type":type,
		"transfer.userInfo.userId":$("#userId").val(),
		"transfer.transferDate":transferDate,
		"transfer.effectiveDate":effectiveDate,
		"transfer.beforeGroupId":beforeGroupId,
		"transfer.postGroupId":postGroupId,
		"transfer.beforePosition":beforePosition,
		"transfer.postPosition":postPosition,
		"transfer.transferProcedure":transferProcedure,
		"transfer.remark":remark,
		"transfer.transferReason":transferReason,
		"transfer.attment":attment
	};
	var url = basePath+"/logined/recordOther/saveOrUpdate.action";
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
 * 调动前部门-隐藏显示函数
 */
var showOrHideBeforeGroup = true;
function showbeforeGroup(){
	$('#beforeGroupContent').toggle(showOrHideBeforeGroup);
	//相当于
	if (showOrHideBeforeGroup) {
		showOrHideBeforeGroup=false;
		var groupObj = $("#beforeGroupName");
		var groupOffset = $("#beforeGroupName").position();
		$("#beforeGroupContent").css({left:groupOffset.left + "px", top:groupOffset.top + groupObj.outerHeight() - 1 + "px"}).show();
		$("#beforeGroupTreeDiv").one("mouseleave",function(){
			$("#beforeGroupContent").hide();
			showOrHideBeforeGroup=true;
			return false;
        });
	} else {
	   $("#beforeGroupContent").hide();
	   showOrHideBeforeGroup=true;
	}
}


/**
 * 调动前部门-隐藏显示函数
 */
var showOrHidepostGroup = true;
function showpostGroup(){
	$('#postGroupContent').toggle(showOrHidepostGroup);
	//相当于
	if (showOrHidepostGroup) {
		showOrHidepostGroup=false;
		var groupObj = $("#postGroupName");
		var groupOffset = $("#postGroupName").position();
		$("#postGroupContent").css({left:groupOffset.left + "px", top:groupOffset.top + groupObj.outerHeight() - 1 + "px"}).show();
		$("#postGroupTreeDiv").one("mouseleave",function(){
			$("#postGroupContent").hide();
			showOrHidepostGroup=true;
			return false;
        });
	} else {
	   $("#postGroupContent").hide();
	   showOrHidepostGroup=true;
	}
}

/**
 * 调动前部门-隐藏显示函数
 */
var showOrHidePostRole = true;
function showpostRole(){
	$('#postRoleContent').toggle(showOrHidePostRole);
	//相当于
	if (showOrHidePostRole) {
		showOrHidePostRole=false;
		var groupObj = $("#postRoleName");
		var groupOffset = $("#postRoleName").position();
		$("#postRoleContent").css({left:groupOffset.left + "px", top:groupOffset.top + groupObj.outerHeight() - 1 + "px"}).show();
		$("#postRoleContentDiv").one("mouseleave",function(){
			$("#postRoleContent").hide();
			showOrHidePostRole=true;
			return false;
        });
	} else {
	   $("#postRoleContent").hide();
	   showOrHidePostRole=true;
	}
}



function initGroupTree(defaultBeforeGroupId,defaultPostGroupId){
 var dataParam = {
	            'type' : 1,
	            'showType' :1
	};
    qytx.app.ajax({
		url : basePath+ "user/selectUser.action",
		type : 'post',
		dataType :'json',
		data:dataParam,
		success : function(data) {
			/*加载调动前部门*/
			qytx.app.tree.base({
				id	:	"beforeGroupTree",
				type:"radio",
				defaultSelectId:defaultBeforeGroupId,
				data:	data,
				click:	callBackbeforeGroup
			});
			
			qytx.app.tree.base({
				id	:	"postGroupTree",
				type:"radio",
				defaultSelectId:defaultPostGroupId,
				data:	data,
				click:	callBackpostGroup
			});
		}
	});
}

function initRoleTree(){
	 var dataParam = {
		            'type' : 2,
		            'showType' :1
		};
	    qytx.app.ajax({
			url : basePath+ "user/selectUser.action",
			type : 'post',
			dataType :'json',
			data:dataParam,
			success : function(data) {
				/*加载调动前部门*/
				qytx.app.tree.base({
					id	:	"postRoleTree",
					type:"radio",
					defaultSelectId:"",
					data:	data,
					click:	callBackpostRole
				});
			}
		});
	}


function callBackbeforeGroup(data)
{
    if(data){
    	var groupIds = [];
    	var groupNames = [];
    	$(data).each(function(i,item){
    		if(item.id.indexOf("gid_") != -1&&item.id.substring(4)!=0){
    			groupIds.push(item.id.substring(4));
    			groupNames.push(item.name);
        	}
    	});
    	qytx.app.valid.hideError($("#beforeGroupName"));
        $("#beforeGroupName").val(groupNames[0]);
        $("#beforeGroupId").val(groupIds[0]);
    }
}

function callBackpostGroup(data)
{
	if(data){
		var groupIds = [];
		var groupNames = [];
		$(data).each(function(i,item){
			if(item.id.indexOf("gid_") != -1&&item.id.substring(4)!=0){
				groupIds.push(item.id.substring(4));
				groupNames.push(item.name);
			}
		});
		qytx.app.valid.hideError($("#postGroupName"));
		$("#postGroupName").val(groupNames[0]);
		$("#postGroupId").val(groupIds[0]);
	}
}

function callBackpostRole(data)
{
	if(data){
		var groupIds = [];
		var groupNames = [];
		$(data).each(function(i,item){
			var uId = item.id.indexOf("rid_");
			if(item.id.indexOf("rid_") != -1){
				groupIds.push(item.id.substring(4));
				groupNames.push(item.name);
			}
		});
		qytx.app.valid.hideError($("#postRoleName"));
		$("#postRoleName").val(groupNames[0]);
		$("#postRoleId").val(groupIds[0]);
	}
}

