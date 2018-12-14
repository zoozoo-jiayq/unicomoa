var ue = null;
$(document).ready(
        function() {
        	var initialFrameHeight = 200;
	        var requestType = $("#requestType").val();
	        if (null != requestType && "box" == requestType) {
		        $("#sendTimeTr").hide();
		        $("#titleDiv").hide();
		        initialFrameHeight=200;
		        // 初始化姓名,id,内容
		        if (null != $("#requestUserInfoId").val() && "" != $("#requestUserInfoId").val()){
		        	$("#userIds").val($("#requestUserInfoId").val());
			    	$("#userNames").val(decodeURI($("#requestUserInfoName").val()));
			    	$("#contentInfo").val(decodeURI($("#requestContentInfo").val()));	
		        }
		        
		    	ue = UE.getEditor('contentInfo', {
		            initialFrameWidth:"100%",
		            initialFrameHeight:initialFrameHeight,
		            toolbars:[["bold","italic","underline","strikethrough","forecolor","backcolor","justifyleft",
		                       "justifycenter","justifyright","justifyjustify","customstyle","paragraph","fontfamily","fontsize","emotion","date","time"]]
		    	
		    	
		        });
	        }else if(null != requestType && "chat" == requestType) {
		        $("#sendTimeTr").hide();
		        $("#titleDiv").hide();
		        initialFrameHeight=200;

	        }
	        else{

	        }
        	ue = UE.getEditor('contentInfo', {
	            initialFrameWidth:"100%",
	            initialFrameHeight:"300"
	        });
        	ue.addListener( 'selectionchange', function( editor ) {
        		var contentInfo = ue.getContent();
        		if(contentInfo!=""){
        			hideError($("#contentInfoInput"));
        		}
        	 });
	        // 单击修改
	        $("#sendBtn").click(function() {
		        sendMessage();
		        return false;
	        });

	        // 单击返回
	        $("#back").click(function() {
		        window.location.href = basePath + "logined/address/list_addressGroup.jsp";
		        return false;
	        });

	    	
//	    	ue.addListener('ready',function(){
//	    		 window.parent.frameResize();
//	        });
	    	setTimeout(function(){
//	    		window.parent.frameResize();
	    	}, 100);
        });
var dialog;
function sendMessage() {
	// 框架校验
	var valid=qytx.app.valid.check({form:$("#form1")[0]});
    if(!valid){
    	return;
    }

	var userIds = $("#userIds").val();
	var sendTime = $("#sendTime").val();
	var contentInfo = ue.getContent();
	
	if (null == contentInfo || '' == contentInfo) {
		qytx.app.valid.showObjError($("#contentInfoInput"), 'message.msg_contentInfo_not_null');
		return;
	} else {
		var contentInfoStr = ue.getContentTxt();
		if(contentInfoStr.length>10000){
			qytx.app.valid.showObjError($("#contentInfoInput"), "message.msg_content_too_long");
			return;
		}else{
			qytx.app.valid.hideError($("#contentInfoInput"));
		}
	}
	

	var paramData = {
	    'messageInfo.toUids' : userIds,
	    'messageInfo.sendTimeStr' : sendTime,
	    'messageInfo.contentInfo' : contentInfo,
	    'messageInfo.msgType' : 1
	};
	qytx.app.ajax({
	    url : basePath + "message/setup_sendMessage.action",
	    type : "post",
	    dataType : "text",
	    data : paramData,
	    success : function(data) {
		    if ("success" == data) {
		    	qytx.app.dialog.tips(sprintf("message.msg_send_success"), function(){
		    		ue.setContent("");
//		    		$("#userIds").val(null);
//	            	$("#userNames").val(null);
	            	$("#sendTime").val("");
		    	},true);
		    } else {
			    qytx.app.dialog.alert(sprintf("message.msg_send_failure"));
		    }
	    }
	});
}

/**
 * 清空人员
 */
function clearPerson() {
	$("#userIds").val(null);
	$("#userNames").val(null);
}

/**
 * 选择部门人员控件，选择人员
 */
function selectPerson() {
	openSelectUser(3, selectCallBackForm, null, $("#userIds").val());// 选择人员
}

/**
 * 部门人员选择的回调函数
 */
function selectCallBackForm(data) {
	var userIds = "";
	var userNames = "";
	if(data && data.length>0){
		$(data).each(function(i,item){
			userIds += item.id + ",";
			userNames += item.name + ",";
		});
	}

	$("#userIds").val(userIds);
	$("#userNames").val(userNames);

	if ("" != userIds) {
		qytx.app.valid.hideError($("#userNames"));
	}
}
