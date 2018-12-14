$(document).ready(function() {
	// 单击修改
	$("#add").click(function() {
		shareAddress();
		return false;
	});
	
	setAffairCheck("通讯薄", "affairsSign", "affairsLable");
});

function shareAddress() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if ("" != startDate && "" != endDate && startDate > endDate){
		art.dialog.alert(sprintf("addressbook.msg_time_error"));
		return;
	}
	
	var addressId = $("#addressId").val();
	var shareToUserIds = $("#shareToUserIds").val();
	if (null != shareToUserIds && "" != shareToUserIds && "," != shareToUserIds.charAt(0)) {
		shareToUserIds = "," + shareToUserIds;
	}
	var allowUpdateUserIds = $("#allowUpdateUserIds").val();
	if (null != allowUpdateUserIds && "" != allowUpdateUserIds && "," != allowUpdateUserIds.charAt(0)) {
		allowUpdateUserIds = "," + allowUpdateUserIds;
	}
	var affairsSign = 0;
	var ischeck = $("#affairsSign:checked").length;
	if (ischeck > 0) {
		affairsSign = 1;
	}
	var paramData = {
	    'addressVo.shareStartTime' : startDate,
	    'addressVo.shareEndTime' : endDate,
	    'addressVo.id' : addressId,
	    'addressVo.shareToUserIds' : shareToUserIds,
	    'addressVo.allowUpdateUserIds' : allowUpdateUserIds,
	    'addressVo.affairsSign' : affairsSign
	};
	$.ajax({
	    url : basePath + "address/setup_shareAddress.action",
	    type : "post",
	    dataType : "text",
	    data : paramData,
	    beforeSend:function(){
			$("body").lock();
	    },
		complete:function(){
			$("body").unlock();
		},
	    success : function(data) {
		    if ("success" == data) {
			    art.dialog({
			    	   title:"消息",
			    	   content:sprintf("addressbook.msg_share_address_success"),
			    	   width : 317,
			    	   height : 109,
			    	   icon:"succeed",
			    	   opacity:0.08,
			    	   lock:true,
			    	   ok:function(){},
			    	   close:function(){
			    		
			    	   }
			    	});
		    }
	    }
	});
}

/**
 * 选择部门人员控件，选择人员
 * 
 * @param obj
 */
function selectPerson(obj) {
	openSelectUser(3, selectCallBackForm, obj, $("#shareToUserIds").val());// 选择人员
}

/**
 * 部门人员选择的回调函数
 */
function selectCallBackForm(data, param) {
	var shareToUserIds = "";
	var shareToUserNames = "";
	data.forEach(function(value, key) {
		// alert("key="+key+",name="+value.Name+",id="+value.Id+",data="+value.Data+",type="+value.Type);
		shareToUserIds += value.Id + ",";
		shareToUserNames += value.Name + ",";
	});

	$("#shareToUserIds").val(shareToUserIds);
	$("#shareToUserNames").val(shareToUserNames);
}

/**
 * 选择部门人员控件，选择人员
 * 
 * @param obj
 */
function selectAllowUpdatePerson(obj) {
	openSelectUser(3, selectAllowUpdateCallBack, obj, $("#allowUpdateUserIds").val());// 选择人员
}

/**
 * 部门人员选择的回调函数
 */
function selectAllowUpdateCallBack(data, param) {
	var allowUpdateUserIds = "";
	var allowUpdateUserNames = "";
	data.forEach(function(value, key) {
		// alert("key="+key+",name="+value.Name+",id="+value.Id+",data="+value.Data+",type="+value.Type);
		allowUpdateUserIds += value.Id + ",";
		allowUpdateUserNames += value.Name + ",";
	});

	$("#allowUpdateUserIds").val(allowUpdateUserIds);
	$("#allowUpdateUserNames").val(allowUpdateUserNames);
}

/**
 * 清空共享人
 */
function clearPerson() {
	$("#shareToUserIds").val(null);
	$("#shareToUserNames").val(null);
}

/**
 * 清空修改人
 */
function clearAllowUpdatePerson() {
	$("#allowUpdateUserIds").val(null);
	$("#allowUpdateUserNames").val(null);
}