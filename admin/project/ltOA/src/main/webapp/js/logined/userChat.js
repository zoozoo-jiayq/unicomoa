var IDMark_A = "_a";
IDMARK_A_CHAT = "_a_chat", IDMARK_A_EMAIL = "_a_email", IDMARK_A_MESSAGE = "_a_message";
var _onLineCount=0; //在线人数
// 在线
treeType = 1;
var setting = {

	view : {
		expandSpeed : "",
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom
		// addDiyDom : addDiyDom
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : userClickCallBack
	}
};
function addHoverDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + IDMark_A);

	if ($("#diy_" + IDMARK_A_CHAT + treeNode.id).length > 0)
		return;

	// 发送微讯
	var userids = treeNode.id;
	var obj = treeNode.obj;
	var treeType = treeNode.TreeType;

	var name = encodeURI(treeNode.name);
	var rediectTalk = basePath + 'logined/chat/client.jsp?rid=' + RndNum(20)
			+ '&username=' + name + '&userids=' + userids + '&treeType='
			+ treeType;
	var backgroudTalk = basePath + 'images/luntanimages/talk.jpg';
	var rediectMail = basePath
			+ 'logined/email/chatSendEmailByIdStr.action?idStr=' + userids;
	// 是否只给在线人员发送邮件
	if (treeType == 1) {
		rediectMail += "&onLine=onLine";
	}
	var backgroudMessage = basePath + 'images/luntanimages/message.jpg';
	var backgroudWeixun = basePath + 'images/luntanimages/weixun.jpg';
	var hasUser = false;
	if (undefined != treeNode.children) {
		for (var i = 0; i < treeNode.children.length; i++) {
			if (treeNode.children[i]["id"].substr(0, 4) == "uid_") {
				hasUser = true;
				break;
			}
		}
	}

	if (userids.substr(0, 4) == "uid_" || hasUser) {
	
		var editStr = "<a style='display:none;'  id='diy_"
				+ IDMARK_A_CHAT
				+ treeNode.id
				+ "' title='"
				+ treeNode.name
				+ "' "
				+ "'  href='javascript:void(0);' onclick=openChatTab('"
				+ rediectTalk
				+ "') onfocus='this.blur();'><img src='"
				+ backgroudTalk
				+ "'/></a><a  href='javascript:void(0);' onclick=openChatSendEmailTab('"
				+ rediectMail + "')   id='diy_" + IDMARK_A_EMAIL + treeNode.id
				+ "'  title='邮件'   ><img src='"
				+ backgroudMessage + "'/></a><a  onclick=openSendMessageChat('"
				+ userids + "','" + name + "','')  id='diy_" + IDMARK_A_MESSAGE
				+ treeNode.id + "' title='即时消息' onfocus='this.blur();'><img src='" + backgroudWeixun
				+ "'/></a>";

		aObj.append(editStr);
	}
}

function removeHoverDom(treeId, treeNode) {
	$("#diy_" + IDMARK_A_CHAT + treeNode.id).unbind().remove();
	$("#diy_" + IDMARK_A_EMAIL + treeNode.id).unbind().remove();
	$("#diy_" + IDMARK_A_MESSAGE + treeNode.id).unbind().remove();
}
function userClickCallBack(treeId, treeNode, clickFlag) {
	var userIdStr = treeNode.id;
	if (userIdStr.indexOf('uid_') == 0) {
/**
		var url = basePath
				+ "logined/record/createOrEditByUserId.action?from=onLine&userId="
				+ userIdStr.substring(4, userIdStr.length);
		art.dialog.open(url, {
					id : "userInfo" + Math.random(),
					title : "人员详情",
					minwitch : 450,
					 width : 800,
					 height : 450,
					lock : true,
				    opacity: 0.3
				});
				*/
	}
	return false;
}

function RndNum(n) {
	var rnd = "";
	for (var i = 0; i < n; i++)
		rnd += Math.floor(Math.random() * 10);
	return rnd;
};

function openChatTab(chatUrl) {
	if ($.browser.msie) {
		event.cancelBubble = true;
	} else {
		event.stopPropagation();
	}
	addTab(RndNum(15), chatUrl, "聊天室", true, null);
};

function openChatSendEmailTab(chatUrl) {
	if ($.browser.msie) {
		event.cancelBubble = true;
	} else {
		event.stopPropagation();
	}
	addTab(RndNum(15), chatUrl, "发送邮件", true, null);
};

// 加入发送微信
function openSendMessageChat(userId, userName, contentInfo) {
	if ($.browser.msie) {
		event.cancelBubble = true;
	} else {
		event.stopPropagation();
	}
	var url = basePath + "message/chatSendMessageByIdStr.action?idStr="
			+ userId;
	if (treeType == 1) {
		url += "&onLine=onLine";
	}

	qytx.app.dialog.openUrl({
		id	:	"send_message",
		url	:	url,
        title:	"发送即时消息",
        size:	'L',
        customButton	:	[{
				name : '发送',
				focus:true,
				callback : function() {
					var iframe = this.iframe.contentWindow;
					iframe.sendMessage();
					return false;
				}
			}, {
				name : '取消',
				callback : function() {
					return true;
				}
			}]
	});
}

// function addDiyDom(treeId, treeNode) {
//
// var IDMark_A = "_a";
// var aObj = $("#" + treeNode.tId + IDMark_A);
// // 发送微讯
// var userids = treeNode.id;
// var obj = treeNode.obj;
// var treeType = treeNode.TreeType;
//
// if (userids.substr(0, 4) == "gid_" && obj != 1) {
//
// } else {
// // var userids = userid.substr(4);
//
// var name = encodeURI(treeNode.name);
// var rediectTalk = basePath + 'logined/chat/client.jsp?rid=' + RndNum(20) +
// '&username=' + name + '&userids='
// + userids + '&treeType=' + treeType;
// var backgroudTalk = basePath + 'images/luntanimages/talk.jpg';
// var rediectMail = basePath +
// 'logined/email/chatSendEmailByIdStr.action?idStr=' + userids;
// var backgroudMessage = basePath + 'images/luntanimages/message.jpg';
// var backgroudWeixun = basePath + 'images/luntanimages/weixun.jpg';
// var editStr = treeNode.pId;
//
// var editStr = "<a class='demoIcon' id='diyBtn_" + treeNode.id + "' title='" +
// treeNode.name
// + "' href='javascript:void(0);' onclick=openChatTab('" + rediectTalk
// + "') onfocus='this.blur();'><img src='" + backgroudTalk
// + "'/></a><a class='demoIcon' href='javascript:void(0);'
// onclick=openChatSendEmailTab('" + rediectMail
// + "') id='diyBtn_" + treeNode.id + "' title='" + treeNode.name + "' ><img
// src='"
// + backgroudMessage + "'/></a><a class='demoIcon'
// onclick=openSendMessageChat('" + userids + "','"
// + name + "','') id='diyBtn_" + treeNode.id + "' title='" + treeNode.name
// + "' onfocus='this.blur();'><img src='" + backgroudWeixun + "'/></a>";
// aObj.after(editStr);
//
// $(".ztree li").hover(function() {
// $(this).children("a.demoIcon").show();
// }, function() {
// $(this).children("a.demoIcon").hide();
// })
// }
// }
var global = null;
function searchUser(type) {
	if (type == undefined) {
		type = 1;
	}
	treeType = type;
	global = type;
	var dataParam = {
		'type' : type
	};
	$.ajax({
				url : basePath + "user/selectOnUser.action",
				type : 'post',
				dataType : 'json',
				data : dataParam,
				success : function(data) {
					var onlinecount = 0 ;
					if(type == 1){
						for(var i=0;i<data.length;i++){
							var userId = data[i].id;
							if (userId.substr(0, 4) == "gid_") {
								
							}else{
								onlinecount++;
							}
						}
						$("#onLineCount").html(onlinecount);
					}
					$.fn.zTree.init($("#groupUserTree"), setting, data);
				}
			});

}
$(document).ready(function() {

	searchUser();
	
	setInterval(function(){
		if(global == 1){
			searchUser();
		}
	}, 10*1000)

	$("#org_pannel .pannelTab span").click(function() {
				$("#org_pannel .pannelTab span").removeClass("current");
				$(this).addClass("current");
			});// 在线和全部切换

		/**
		 * 选择人员
		 * 
		 * @param type
		 *            1 部门 2 角色 3 分组 4 在线 5,查找
		 */

	});
