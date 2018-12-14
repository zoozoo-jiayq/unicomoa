/**
 * 登陆后首页
 */
var _title="新密市人民检察院办公系统";
var timerMessage = null;
var jsxxTimer=null; //及时消息定时器
var lastTimeMessage = 2;
var getAffairsTime;
var newAffairsTitleTime = null;
var getNewMessageTime;
var newMessageTitleTime = null;
var quickAffairsTimer = null;
var _second=3;
// 微讯盒子是否显示
var is_smsbox_panel_show = false;
$(document).ready(function() {
	var moduleType = $("#moduleType").val();
	if(moduleType=="left"){
		initMenuLeft();
	}else{
		initMenu(menuBindEvent);
	}
	var loginSysName = $("#loginSysName").val();
	if(loginSysName=="OA"){
		setTimeout(function() {
			getNewAffairs();
		}, 3000);
	}
	getWeather();
	 $("#smsbox_panel").hide();
	 $("#smsbox_textarea").keyup(function(){
		var smsBoxValue=$.trim($("#smsbox_textarea").val()); 
		$("#smsbox_textarea").val(smsBoxValue);
	 });
	// 登录后先获取微讯和事务提醒信息
	qytx.app.ajax({
	    url : basePath + "affairs/setup_getNewAffairsNum.action",
	    type : "post",
	    dataType : 'text',
	    success : function(data) {
		    if (data != null && data != "0") {
		    	$("#affairsNum").html("事务提醒（"+data+"）<a href='javascript:void(0);' class='close' onclick='closeLittleDiv(event)'></a></a>");
 			    $("#newAffairsAlert").show();
 			    setTimeout(function() {
 			    	$("#newAffairsAlert").hide();
 			    }, 5000);
		    }
	    }
	});
	
	// 定期访问事务提醒信息
//	getAffairs();
    newAffairsTitleTime = setInterval("getAffairs()", 6000);

	// 定期访问微讯信息  及时消息
//   getNewMessage();
  getNewMessageTime = setInterval("getNewMessage()", 6000);
	
	// 快速请求事务提醒 要求每隔3秒钟发送一次请求获取是否有最新的未接受的事务提醒记录信息
//	quickAffairsTimer = setInterval("getQuickAffair()", 3000);

	 

	$("#smsbox_panel").delegate(".list-block", "click", function(event) {
		$(this).parent().find(".list-block-active").removeClass("list-block-active");
		$(this).addClass("list-block-active");
		return false;
	});

});
function menuBindEvent() {
	 menuBindClick(); // 菜单绑定事件
}


/**
 * 即时消息
 */
function messageBoxClick(){
	document.title = _title;
	$("#messageBox").removeClass("messageBox_on");
	if (!is_smsbox_panel_show) {
		// 初始化页面
		lastTimeMessage = 2;
		$("#smsbox_close_countdown").empty();
		$("#smsbox_close_countdown").append(3);

		// 获取最近联系人信息
		getCurrentMessageUser();
	}
	return false;
}


/**
 * 初始化菜单
 */
function initMenu(callBack) {
	var sysName = $("#systemName").val();
	qytx.app.ajax({
	    url : basePath + "menu/menulist.action",
	    type : 'post',
	    data:{"sysName":sysName},
	    dataType : 'html',
	    success : function(data) {
		    $("#menu").html(data);
		    if(callBack){
		    	callBack(); // 执行回调函数绑定事件
		    }
	    },
	    error : function() {
		    //alert("error");
	    }
	});
}


/**
 * 初始化菜单
 */
function initMenuLeft() {
	var sysName = $("#systemName").val();
	qytx.app.ajax({
	    url : basePath + "menu/menulistLeft.action",
	    type : 'post',
	    data:{"sysName":sysName},
	    dataType : 'html',
	    success : function(data) {
		    $("#menu").html(data);
		    $("ul.l_f_menu li h3").click(function(){
		  	  $(this).parents().find("h3").removeClass("on off");
		  	  $(this).addClass("off");
		  	   if($(this).next().is(":hidden")){
		  		  $(this).removeClass("off").addClass("on").siblings().removeClass("on");
		  		  $(this).next("dl").show();
		  	   }else{
		  		   $(this).next("dl").hide();;
		  		}});//二级菜单
		  	 
//		  	$(".l_f_menu dl dt").click(function(){
//		  		if(!$(this).hasClass("on")){
//		  			$(this).parents().find("dt").removeClass("on");
//		  			$(this).addClass("on");
//		  		}
//		  	});//三级菜单
		  	$(".l_f_menu dl dt").click(function(){
				if(!$(this).hasClass("on")){
					$(this).parents().find("h3").removeClass("on off");
					 $(this).parent().prev("h3").addClass("on");
					$(this).parents().find("dt").removeClass("on");
					$(this).addClass("on");
				}
			});
//		    $("ul.l_f_menu li h3").click(function(){
//		  	  $(this).parents().find("dl").hide();
//		  	  $(this).parents().find("h3").removeClass("on off");
//		  	  $(this).addClass("off");
//		  	   if($(this).next().is(":hidden")){
//		  		  $(this).removeClass("off").addClass("on").siblings().removeClass("on");
//		  		  $(this).parent().find("dl").hide();
//		  		  $(this).next().show();
//		  	   }});//二级菜单
		  	 
//		  	$(".l_f_menu dl dt").click(function(){
//		  		if(!$(this).hasClass("on")){
//		  			$(this).parents().find("dt").removeClass("on");
//		  			$(this).addClass("on");
//		  		}
//		  	});//三级菜单
		    
	    },
	    error : function() {
	    }
	});
}

function exit() {
	$(".operate").find("span").each(function(){
		if($(this).hasClass("on")){
			$(this).removeClass("on");
		}
	});
	qytx.app.dialog.confirm('确认注销吗？    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ', function() {
		window.document.location = basePath + "logout.jsp";
	});
}

function closeOa() {
	qytx.app.dialog.confirm('确认要退出吗？ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ', function() {
		window.document.location = basePath + "logout.jsp";
		window.close();
	});
}

/**
 * 跳转到url
 * 
 * @param url
 */
function gotoUrl(moduleId, name, url, json) {
	$(".operate").find("span").each(function(){
		if($(this).hasClass("on")){
			$(this).removeClass("on");
		}
	});
	// 如果为收文登记或者发文核稿时,直接弹出新页面,解决nkto插件覆盖弹出层问题
	// gotoUrl(102100,'发文拟稿','http://10.200.10.11:8080/qyoa//dom/dispatch!toAdd.action','');
	// gotoUrl(101110,'收文登记','http://10.200.10.11:8080/qyoa//dom/gather!toAdd.action','');
//	if ((name == "发文拟稿" && url.indexOf("dom/dispatch!toAdd.action") > 0) 
//			|| (name == "收文登记" && url.indexOf("dom/gather!toAdd.action") > 0))
//	{
//		window.open(url,'','height='+screen.availHeight+',width='+screen.availWidth+',top=0,left=0,menubar=0,toolbar=0,status=1,scrollbars=0,resizable=1');
//	}else{
	      var timestamp3 = new Date().getTime();  
		  if(url==basePath+"bbs"){
		        var url=basePath2+"/bbs/loginToken.jspx?token="+toke;
		    	window.open(url);
		    }else{
		    	var urlArr  =url.split("?");
		    	if(urlArr.length>1){
		    		url=url+"&timeurl="+timestamp3
		    	}else{
		    		url=url+"?timeurl="+timestamp3
		    	}
		    	if("室内大屏展示"==name || "室外大屏展示"==name){
		    		window.parent.open(url);
		    		return;
		    	}
		    	addTab(moduleId, url, name, 1, json);
		    }
//	}
}

/**
 * 打开事务提醒页面
 */
function openAffairs() {
	document.title = _title;
    // 同时更新此信息为已接受
	qytx.app.ajax({
	    url : basePath + "affairs/setup_updateReceivedAffairs.action",
	    type : "post",
	    dataType : 'text',
	    success : function(data) {
	    }
	});
      
    $("#affairsEm").removeClass("icon_work_on"); //取消提醒
	art.dialog.data('addNewTab', addNewTab);
	qytx.app.dialog.openUrl({
		id	:	"openAffairs",
		url	:	basePath + "affairs/getShortcutMenu.action",
		title:	"事务提醒",
		size:	"L",
		customButton:	[{
		    	name: '全部已阅',
		    	focus: true,
		    	callback:function(){
		    		var iframe = this.iframe.contentWindow;
		    		iframe.updateAllReaded();
		    		return false;
		    	}
		    	},
		        {name: '关闭'}]
	});
}

function getAffairs() {
	 
	qytx.app.ajax({
	    url : basePath + "affairs/setup_getNewAffairs.action",
	    type : "post",
	    dataType : 'text',
	    success : function(data) {
		    if (data == "success") {
		        //$("#affairsEm").removeClass("icon_work_on");
		        $("#affairsEm").addClass("icon_work_on");
		    }else{
		    	$("#affairsEm").removeClass("icon_work_on");
		    }
	    }
	});
}


function getNewAffairs(){
	qytx.app.ajax({
	    url : basePath + "affairs/setup_getNewAffairs.action",
	    type : "post",
	    dataType : 'text',
	    success : function(data) {
		    if (data == "success") {
			    openAffairs();
		    }else{
		    }
	    }
	});
}

/**
 * 快速请求是否有最新的事务提醒 主要处理需要及时处理的事务提醒,解决聊天室通知等问题
 */
function getQuickAffair() {
	qytx.app.ajax({
	    url : basePath + "affairs/setup_getQuickAffairs.action",
	    type : "post",
	    dataType : 'text',
	    success : function(data) {
		    if (data == "success") {
			    // 变更提示信息,修改为右下角提示
			    $("#affairsEm").addClass("icon_work_on");
		    }else{
		    	$("#affairsEm").removeClass("icon_work_on");
		    }
	    }
	});
}

/**
 * 变更title
 */
function addNewTab(title, url, name) {
	addTab(title, url, name, 1, null);
}

function closeMessageBoxX() {
	$("#smsbox_panel").hide();
	is_smsbox_panel_show = false;
}

function getCurrentMessageUser() {
	// 停止定时器刷新title
	if (null != newMessageTitleTime){
		clearInterval(newMessageTitleTime);
		newMessageTitleTime=null;
	}
	
    // 去掉右下角的图片提示
   // $("#messageBox").removeClass("newmail");
   // $("#messageBox").addClass("icon_mail");
	
	$("#smsbox_msg_container").empty();
	qytx.app.ajax({
	            url : basePath + "message/setup_getCurrentUser.action",
	            type : "post",
	            dataType : 'json',
	            success : function(data) {
		            if (null != data && "" != data) {
			            if (null != timerMessage) {
				            clearInterval(timerMessage);
			            }
			            var sb = new StringBuilder();
			            sb.Append("<ul>");  
			            for ( var i = 0; i < data.length; i++) {
				            if (i == 0) {
					            sb.Append("<li    class=\"current\" onclick=getUserMessage('"  + data[i].userId + "',this) ><em></em>");
					            sb.Append(data[i].userName);
					            //sb.Append("<span>15:30</span></li>");  
					            getUserMessage(data[i].userId);
				            } else {
				            	     sb.Append("<li   onclick=getUserMessage('"  + data[i].userId + "',this) ><em></em>");
						             sb.Append(data[i].userName);
						             //sb.Append("<span>15:30</span></li>"); 
				               }
			            }
			        
			            sb.Append("</ul>");  
			            $("#smsbox_list_container").html(sb.toString());
			            $("#center-left").show();
			            $("#center-right").show();
			            $("#no_sms").hide();
			            
			            if (data.length > 6){
			            	$("#smsbox_scroll_up").show();
			            	$("#smsbox_scroll_down").show();
			            }else{
			            	$("#smsbox_scroll_up").hide();
			            	$("#smsbox_scroll_down").hide();
			            }
			            //表示有数据，显示
			            $("#smsbox_panel").show();
			    		is_smsbox_panel_show = true;
			            
		            } else {
		            	//--无数据
			            $("#center-left").hide();
			            $("#center-right").hide();
			             $("#smsbox_panel").hide();
			             $("#no_sms").show();
			             clearInterval(jsxxTimer);
			              jsxxTimer =  setInterval(function(){
			            	 if(_second>1){
			            		 _second=_second-1;
			            		 $("#jsxxTimes").html(_second);
			            	 }else{
			            		 $("#no_sms").hide();
			            		 clearInterval(jsxxTimer);
			            		 $("#jsxxTimes").html(3);
			            		 _second=3;
			            	 }
			             },1000);
			            timerMessage = setInterval("closeMessageBox()", 1000);
		            }
	            }
	        });

    setTimeout(function(){
    	 jQuery(".picScroll-top").slide({titCell:".hd ul",mainCell:".bd ul",effect:"top",autoPlay:false,pnLoop:false,vis:3,trigger:"click"});
    },100);
	 
}

/**
 * 定时刷新页面时间信息
 */
function closeMessageBox() {
	$("#smsbox_close_countdown").empty();
	$("#smsbox_close_countdown").append(lastTimeMessage);
	if (lastTimeMessage == 0) {
		if (null != timerMessage) {
			clearInterval(timerMessage);
		}
		$("#smsbox_panel").hide();
		is_smsbox_panel_show = false;
	}
	lastTimeMessage--;
}

/**
 * 根据userId获取与此人的聊天信息
 * 
 * @param userId
 *            userId
 */
var messageUserId = '';
var currentUserNamer = '';
function getUserMessage(userId,obj) {
	$("#smsbox_textarea").val("");
	qytx.app.ajax({
	            url : basePath + "message/setup_getCurrentUserMessage.action?userId=" + userId,
	            type : "post",
	            dataType : 'json',
	            success : function(data) {
		            if (null != data) {
			            var sb = new StringBuilder();
			            for ( var i = 0; i < data.length; i++) {
			                sb.Append("<li>");
			                if (userId == data[i].createUserId) {
			                	sb.Append("<h3><a style=\"cursor:pointer;\" onclick=\"javascript:openSendMessage("+data[i].createUserId+", '"+data[i].fromUserName+"', '')\" class=\"fr\">回复</a>"+data[i].fromUserName+ "&nbsp;" + data[i].sendTimeStr+"</h3>");
			                	currentUserNamer = data[i].toUserName;
			                }else{
			                	sb.Append("<h3>"+data[i].fromUserName+ "&nbsp;" + data[i].sendTimeStr+"</h3>");
			                	currentUserNamer = data[i].fromUserName;
			                }
			                sb.Append("<p>"+data[i].contentInfo+"</p>");
			                sb.Append("</li>");
			                /**
				            sb.Append('<div class="msg-block from">');
				            sb.Append('<div class="head">');
				            sb.Append('<div class="name">' + data[i].fromUserName + '&nbsp;' + data[i].sendTimeStr
				                    + '</div>');
				            if (userId == data[i].createUserId) {
//					            sb
//					                    .Append('<div class="operation"><a hidefocus class="reply" href="javascript:void();">回复</a> </div>');
					            currentUserNamer = data[i].toUserName;
				            } else {
					            sb.Append('<div class="operation"></div>');
					            currentUserNamer = data[i].fromUserName;
				            }
				            sb.Append("</div>");
				            sb.Append('<div class="msg">' + data[i].contentInfo + '</div>');
				            sb.Append("</div>");
				            */
			            }
			           
			            $("#smsbox_msg_container").empty();
			            $("#smsbox_msg_container").append(sb.toString());
			            messageUserId = userId;
			            $("#smsbox_msg_container").scrollTop(
			                    document.getElementById("smsbox_msg_container").scrollHeight);
		            }

	            }
	        });
	$(".list-container ul li").removeClass("current");
	$(obj).addClass("current");
}

/**
 * 发送微讯信息
 * 
 * @returns {Boolean}
 */
function sendMessage() {
	if (null == messageUserId || "" == messageUserId) {
		return false;
	}
	var contendInfo = $("#smsbox_textarea").val();
	if (null == contendInfo || "" == $.trim(contendInfo)) {
		return false;
	}
	var paramData = {
	    'messageInfo.toUids' : messageUserId,
	    'messageInfo.contentInfo' : contendInfo,
	    'messageInfo.msgType' : 1,
	    'messageInfo.srcType' : 'box'
	};
	qytx.app.ajax({
	    url : basePath + "message/setup_sendMessage.action",
	    type : "post",
	    dataType : "text",
	    data : paramData,
	    success : function(data) {
		    if ("success" == data) {
//			    $("#smsbox_textarea").val("");
//			    getUserMessage(messageUserId);
			    // 将信息信息补充道后面
		    	    
		    	    var dateObj = new Date();
		    	    var sb = new StringBuilder();
		    	    
		    	    sb.Append("<li>");
	                sb.Append("<h3>"+currentUserNamer+ "&nbsp;" + dateObj.getHours() + ":" + dateObj.getMinutes()+"</h3>");
	                sb.Append("<p>"+contendInfo+"</p>");
	                sb.Append("</li>");
		    	    
//		    	    sb.Append('<div class="msg-block from">');
//		            sb.Append('<div class="head">');
//		            sb.Append('<div class="name">' + currentUserNamer + '&nbsp;' + dateObj.getHours() + ":" + dateObj.getMinutes()
//		                    + '</div>');
//			        sb.Append('<div class="operation"></div>');		
//		            sb.Append("</div>");
//		            sb.Append('<div class="msg">' + contendInfo + '</div>');
//		            sb.Append("</div>");
		            $("#smsbox_msg_container").append(sb.toString());		
		            
		            // 滚动条滚动到底部
		            $("#smsbox_msg_container")[0].scrollTop = $("#smsbox_msg_container")[0].scrollHeight - $("#smsbox_msg_container")[0].offsetHeight;
		            $("#smsbox_textarea").val("");
		    } else {

		    }
	    }
	});
}

/**
 * 更新微讯状态为已阅
 * 
 * @param isAll
 */
function updateReadedByUserId(isAll) {
	var urlStr = "";
	if (true == isAll) {
		urlStr = basePath + "message/setup_updateMessageReaded.action?userId=" + messageUserId;
	} else {
		urlStr = basePath + "message/setup_updateMessageReaded.action";
	}
	qytx.app.ajax({
	    url : urlStr,
	    type : "post",
	    dataType : "text",
	    success : function(data) {
		    if ("success" == data) {
			    $("#smsbox_textarea").val("");
			    getCurrentMessageUser();
			    
			    // 将滚动条滚动到上面
			    $("#smsbox_list_container")[0].scrollTop = 0;
		    } else {

		    }
	    }
	});
}

/**
 * 删除微讯信息
 * 
 * @param isAll
 */
function deleteByUserId() {
	if ('' == messageUserId) {
		return;
	}
	var urlStr = basePath + "message/setup_deleteByUserId.action?userId=" + messageUserId;

	qytx.app.ajax({
	    url : urlStr,
	    type : "post",
	    dataType : "text",
	    success : function(data) {
		    if ("success" == data) {
			    $("#smsbox_textarea").val("");
			    getCurrentMessageUser();
		    } else {

		    }
	    }
	});
}

/**
 * 获取新微讯
 */
function getNewMessage() {
	qytx.app.ajax({
	    url : basePath + "message/setup_getNewMessage.action",
	    type : "post",
	    dataType : 'text',
	    success : function(data) {
		    if (data == "success") {
			    if (null == newMessageTitleTime) {
				    newMessageTitleTime = setInterval("newMessageTitleChange()", 1000);
			    }
			    // 更新右下角图片的样式,不使用直接弹出的方式提醒
			    $("#messageBox").addClass("messageBox_on");
                
		    }else{
			    // 更新右下角图片的样式,不使用直接弹出的方式提醒
			    $("#messageBox").removeClass("messageBox_on");
		    }
	    }
	});
}

/**
 * 新微讯时,更改标题提示用户
 */
var isMessageChange = true;
function newMessageTitleChange() {
 if (isMessageChange) {
 	document.title = "您有新的即时消息";
 	isMessageChange = false;
 	} else {
 	document.title = _title;
 	isMessageChange = true;
  } 
}

/**
 * 新事务时,更改标题提示用户
 */
var isAffairsChange = true;
function newAffairsTitleChange() {
//	if (isAffairsChange) {
//		document.title = "您有新的事务提醒";
//		isAffairsChange = false;
//	} else {
//		document.title = "";
//		isAffairsChange = true;
//	}
}

/**
 * 发送微讯
 * 
 * @param userId
 *            用户ID
 * @param userName
 *            用户名
 * @param contentInfo
 *            微讯内容
 */
function openSendMessage(userId, userName, contentInfo) {
	qytx.app.dialog.openUrl({
		id	:	"send_message",
		url	:	basePath + "logined/message/alert_send_message.jsp?type=box&userInfoId=" + userId + "&userInfoName="
        + encodeURI(userName) + "&contentInfo=" + encodeURI(contentInfo),
        title:	"发送即时消息",
        size:	'L',
        customButton	:	[{
						name : '发送',
						focus: true,
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
/* org */
function openOrg() {
	$("#org_pannel").toggle();

}
function clsOrg() {
	$("#org_pannel").hide();
}


/**
 * 快捷键发送微讯 onkeypress="captureMessage(event,this)"
 * @param event
 * @param o
 */
function captureMessage(event, o)
{
	var keynum;
	if (window.event)
	{
		keynum = event.keyCode;
	}
	else
	{
		keynum = event.which;
	}
	event = event || window.event;
	if ($("#smsbox_rapid_reply").val() == 1
			&& event.ctrlKey && (keynum == 13 || keynum == 10))
	{
		sendMessage();
	}
}
//滚动按钮
$('#smsbox_scroll_up,#smsbox_scroll_down').hover(
   function(){$(this).addClass('active');},
   function(){$(this).removeClass('active');}
);
$('#smsbox_scroll_up').click(function(){
   var listContainer = $('#smsbox_list_container');
   var blockHeight = $('div.list-block:first', listContainer).outerHeight();
   listContainer.animate({scrollTop: listContainer.scrollTop()-blockHeight*3}, 300);
});
$('#smsbox_scroll_down').click(function(){
   var listContainer = $('#smsbox_list_container');
   var blockHeight = $('div.list-block:first', listContainer).outerHeight();
   listContainer.animate({scrollTop: listContainer.scrollTop()+blockHeight*3}, 300);
});

////列表鼠标滚轮事件
//$("#smsbox_list_container").mousewheel(function(){
//   //$('#smsbox_list_container').scrollTop($('#smsbox_list_container').scrollTop() - this.D);
//   $('#smsbox_list_container').stop().animate({'scrollTop':($('#smsbox_list_container').scrollTop() - this.D)}, 300);
//});
//列表hover效果
var listBlocks = $('#smsbox_list_container > div.list-block');
listBlocks.live('mouseenter', function(){$(this).addClass('list-block-hover');});
listBlocks.live('mouseleave', function(){$(this).removeClass('list-block-hover');});

function openNewAffairs(){
	openAffairs();
	$("#newAffairsAlert").hide();
}

function closeLittleDiv(event){
	$("#newAffairsAlert").hide();
	if ($.browser.msie){
		event.cancelBubble = true ;
	}else{
		event.stopPropagation();
	}
}

//修改密码提醒
(function(){
	
	var setFlag = $("#setFlag").val();
	if(setFlag==1){
		$.get(basePath+"/sysset/getCommonSet.action?r="+Math.random(),function(data){
			//如果设置提醒才判断是否和默认密码相同
			if(data.notice_update_password == 'yes'){
				$.get(basePath+"/sysset/isDefaultPwd.action?r="+Math.random(),function(data){
					if(data == 'default'){
						art.dialog({
							"title":"提示",
							"content":"<p style='text-align:center'>您用的是初始密码，<br/>为了您的账号安全，建议您立即修改密码。</p>",
							"ok":function(){
								window.addTab(Math.random(),basePath+"sysset/toPwdSet.action","修改密码");
							},
							"cancel":true
						});
					}
				});
			}
		},"json");
	}
	
	
	
})();
