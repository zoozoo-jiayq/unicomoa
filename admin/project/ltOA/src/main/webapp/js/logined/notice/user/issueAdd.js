var ue;
$(document).ready(function() {
	//添加人员
	$("#addUsers").click(function() {
		var userIds=$("#userIds").val();
		var managerID=$("#managerID").val();
		openSelectUser(3,callBackUsers,"user",userIds, "content",managerID);//选择人员
		return false;
	});
	//清空人员
	$("#clearUsers").click(function() {
		$("#userIds").val("");
		$("#userNames").val("");
		return false;
	});
	ue = UE.getEditor('editor',{
	    toolbars:[  
	                ['bold', 'italic', 'underline', 'fontborder','forecolor', 'backcolor','justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', 'customstyle', 'paragraph', 'fontsize']  
	            ] ,
		initialFrameWidth : "81%",
		  initialFrameHeight:"300"
	});
});

function openSelectUser(showType, callback, param, defaultSelectId, moduleName,managerID) {
	var url = basePath + "logined/news/manage/selectUserMany.jsp?showType=" + showType
	+ "&defaultSelectId=" + defaultSelectId 
	+ "&moduleName=" + moduleName
	+ "&columnId=" + managerID;
	var title = "选择人员";
	if (showType == 1) {
		title = "选择部门";
	} else if (showType == 2) {
		title = "选择角色";
	}
	art.dialog.open(url, {
				title : title,
				width : 400,
				height : 427,
				lock : true,
			    opacity: 0.3,
			    resize:false,
			    drag:false,
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
function callBackUsers(data)
{
    if(data){
    	var userIds = [];
    	var userNames = [];
        data.forEach(function(value) {
           userIds.push(value.Id);
           userNames.push(value.Name);
        });
        $("#userIds").val(userIds.toString());
        $("#userNames").val(userNames.toString());
    }
}

//发布内容
function publish(type){
	var title=$("#noticeTitle").val();
	var content=ue.getContent();
	var contentTxt=ue.getContentTxt();
	var rangeIds=$("#userIds").val();
	var rangeNames=$("#userNames").val();
	var materialId=$("#MaterialID").val();
	var approve=$("#Approve").val();
	var columnId=$("#managerID").val();
	var issuerId=$("#IssuerID").val();
	var approver=$("#Approver").val();
	var approverId=$("#ApproverID").val();
	if(!title||$.trim(title)==""){
		art.dialog.alert("标题不能为空!");
			return false;
	}
	if(!content||$.trim(contentTxt)==""){
		art.dialog.alert("内容不能为空!");
		return false;
	}
	if(contentTxt.length>1500){
		art.dialog.alert("内容长度过长!");
		return false;
	}
	if(!rangeNames){
		art.dialog.alert("发布范围不能为空!");
		return false;
	}
	var url="";
	url = basePath + "news/addIssue.action";
	var param={
			"type":type,
			"category":1,
			"newsIssue.title":title,
			"newsIssue.content":content,
			"newsIssue.distribution":rangeIds,
			"newsIssue.distributionName":rangeNames,
			"newsIssue.materialId":-1,
			"newsIssue.approve":approve,
			"newsIssue.columnId":columnId,
			"newsIssue.issuerId":issuerId,
			"newsIssue.approve":approve,
			"newsIssue.approverId":approverId
	};
	$.ajax({
		type:	"post",
		url:	url,
		data:	param,
		dataType:"text",
		success:function(data){
			if(data=='success'){
				$(window.parent.document).find(".houtai_left").find("p").removeClass("current_left");
				if(type==1){
					art.dialog({content:"发布新闻成功!",width:300,height:100,icon:"succeed",icon:"succeed",ok:function(){
						$(window.parent.document).find("#tag2").addClass("current_left");
						window.location.href=basePath+"logined/notice/user/issueList.jsp";
					}});
				}else if(type==2){
					art.dialog({content:"提交审批成功!",width:300,height:100,icon:"succeed",icon:"succeed",ok:function(){
						$(window.parent.document).find("#tag2").addClass("current_left");
						window.location.href=basePath+"logined/notice/user/issueList.jsp";
					}});
				}else if(type==3){
					art.dialog({content:"存草稿成功!",width:300,height:100,icon:"succeed",icon:"succeed",ok:function(){
						$(window.parent.document).find("#tag3").addClass("current_left");
						window.location.href=basePath+"logined/notice/user/issueDraft.jsp";
					}});
				}else if(type==4){
					art.dialog({content:"发布预览成功!",width:300,height:100,icon:"succeed",icon:"succeed",ok:function(){return;}});
				}
			}else{
				art.dialog.alert("操作失败!");
			}
		}
	});
}

function dispPreview() {
	var title=$("#noticeTitle").val();
	var content=ue.getContent();
	var contentTxt=ue.getContentTxt();
	if(!title||$.trim(title)==""){
		art.dialog.alert("标题不能为空!");
			return false;
	}
	if(!content||$.trim(contentTxt)==""){
		art.dialog.alert("内容不能为空!");
		return false;
	}
	if(contentTxt.length>1500){
		art.dialog.alert("内容长度过长!");
		return false;
	}
	var rangeNames=$("#userNames").val();
	if(!rangeNames){
		art.dialog.alert("发布范围不能为空!");
		return;
	}
	
    var html = createPreviewWin();
	art.dialog( {
		title : '发布预览',
		content : html,
		width : '335px',
		padding : 0,
		button : [ {
			name : '确 定',
			callback : function() {
				var MobileNo = $("#MobileNo").val();
				if(!MobileNo){
					art.dialog.alert("手机号不能为空！ ");
					return false;
				}
				if(MobileNo.length!=11||!MobileNo.match(/^1[3|4|5|8][0-9]\d{4,8}$/)){
					art.dialog.alert("请输入正确的手机号!");
					return false;
				}
				var oldUserPhone = $("#oldDistributionPhone").val();
				var title=$("#title").val();
				if(oldUserPhone.indexOf(MobileNo)<0){
					art.dialog.alert("该手机号不在栏目"+title+"发布范围内！");
					return false;
				}
				publish(4);
			}
		}, {
			name : '取 消',
			callback : function() {}
		} ]
	});
}

function createPreviewWin() {
	var title=$("#title").val();
    var html = '<div class="pop" style="width:450px;margin:15px;">';
    html += '<p style="font-size:11;">该内容将发送到以下手机号码的手机客户端的'+title+'栏目，请查看</p>';
    html += '<table width="410px" border="0" class="inputTable" style="margin: 0px auto;">';
    html += '<tr>';
    html += '  <th><em class="requireField">*</em>手机号：</th>';
    html += '  <td><input id="MobileNo" name="MobileNo" type="text" maxlength="11" class="formText"/></td>';
    html += '</tr>';
    html += '</table></div>';
    return html;
}

function disp_alert2() {
	art.dialog.open(basePath + 'logined/news/user/materialSelect.jsp', {
		title : '选取素材',
		width : 1010,
		height : 500,
		lock : true,
	    opacity: 0.08,// 透明度
		button : [ {
			name : '确定',
			callback : function() {
				var id = art.dialog.data("id");
				if(!id||id==""){
					art.dialog.alert("请至少选择一个素材！");
					return false;
				}
				getMaterialId(id);
				return true;
			}
		}, {
			name : '取消',
			callback : function() {
				return true;
			}
		} ]
	}, false);
}

function getMaterialId(data){
	if(data&&data!='undefined'){
		$("#MaterialID").val(data);
		$.ajax({
			url:	basePath+"news/loadMeterial.action",
			type:	"post",
			data:	{"materialId":data},
			dataType:"text",
			success:function(data){
				var obj=eval("("+data+")");

				var html="";
				html+='<div class="fl item item_ncz"  style="margin-bottom: 5px;">';
				html+='<div class="top_item"></div>';
				html+='<div class="cont_item">';
				html+='<div class="img_new">';
				html+='<img src="'+downPath+''+obj.material.titleIcon+'"/>';
				html+='<p>'+obj.material.title+'</p>';
				html+='</div>';
				if(obj.materialIcons){
					html+='<div class="list_new">';
					html+='<ul>';
					$(obj.materialIcons).each(function(j,j_item){
						html+='<li><img src="'+downPath+''+item.iconURL+'"/><p>'+item.title+'</p></li>';
					});
					html+='</ul>';
					html+='</div>';
				}
				html+='</div>';
				html+='</div>';
				$("#materialArea").html(html);
			}
		});
	}
}