$(document).ready(function() {
	// 公共通讯簿不允许新建联系人
	if($("#publicSign").val() == 1){
		$("#addAddress").hide();
	}else{
		// 单击新增联系人
		$("#addAddress").click(function() {
			window.location.href = basePath + "logined/address/add_address.jsp?groupId=" + $("#groupId").val();
			return false;
		});
	}
	
	
	// 样式调整
	var height = $(window).height() - 40;
	$(".mainpage").height(height + "px")
	// show xxx
	$("#filter").focus();

	$("#addressList").delegate("li", "hover", function() {
		var currentUserId = $("#currentUserId").val();
		if (currentUserId == $(this).children("a").attr("name") && $("#publicSign").val() != 1)
			$(this).children(".xxx").toggle();
	});

	// 格式化组名
	$("#groupName").val(decodeURI($("#groupName").val()));

	// 根据联系人组Id获取组下的成员
	findAddressByGroupId($("#groupId").val(), "", "");

	// 单击高级查询
	$("#searchAddress").click(function() {
		window.location.href = basePath + "logined/address/list_address.jsp";
		return false;
	});
	
	// 初始化显示群组名
	var groupName = $("#groupName").val();
	$("#groupNameStr").html(groupName);
	$("#groupH2").attr("title", groupName);
	
	$("body").show();
});

/**
 * 获取通讯簿组列表
 */
function findAddressByGroupId(groupId, addressName, extraParam) {
	var urlStr = basePath + "address/setup_getAddressList.action?addressVo.addressGroupId=" + groupId
	        + "&addressVo.name=" + encodeURI(addressName) + "&addressVo.type=" + $("#type").val();	
	if ("share" == $("#type").val()) {
		urlStr += "&type=share";
	}
	if (null != extraParam && "" != extraParam) {
		urlStr += extraParam;
	}

	$.ajax({
	    url : urlStr,
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
		    if (null != data && data.aaData != null && data.aaData.length > 0) {
		    	if (null == $("#filter").val() || "" == $.trim($("#filter").val())){
		    		$("#groupNum").html(data.aaData.length);
		    	}
			    var sb = new StringBuilder();
			    for ( var i = 0; i < data.aaData.length; i++) {
				    var photo = data.aaData[i].photo;
				    var imgStr = "";
				    if (null != photo && "" != photo) {
					    imgStr = '<img width="42" height="42"  src="' + basePath + 'upload/img/' + photo + '" />';
				    } else {
					    var sex = data.aaData[i].sex;
					    if (null != sex && "0" == sex) {
						    imgStr = '<img width="42" height="42"  src="' + basePath + 'images/address_info02.png" />';
					    } else {
						    imgStr = '<img width="42" height="42"  src="' + basePath + 'images/address_info01.png" />';
					    }
				    }

				    var address_url = basePath + "address/getDetailInfo.action?operateType=detail&addressVo.id="
				            + data.aaData[i].id + "&type=" + $("#type").val();
				    
				    // 公共组的用户需要屏蔽修改和共享按钮
				    if($("#publicSign").val() == 1 || data.aaData[i].addressGroupId == '-2'){
				    	address_url += "&publicSign=1";
				    }
				    if (0 == i) {
					    getDetailAddress(address_url);
				    }
				    sb.Append('<li onclick=getDetailAddress("' + address_url + '") title="'+data.aaData[i].name+'">');
				    sb.Append(imgStr);
                    sb.Append("<span>" + data.aaData[i].name + "</span>");
                    
				    // 公共组的不允许删除
				    if($("#publicSign").val() == 1 || data.aaData[i].addressGroupId == '-2'){
				    	sb.Append('</li>');
				    }else{
				    	sb.Append('<a class="xxx" href="javascript:void(0);" name="' + data.aaData[i].createId
					            + '" onclick=deleteAddress(event,"' + data.aaData[i].id + '")></a></li>');
				    }
			    }
			    
			    $("#addressList").html(sb.toString());
		    } else {
			    $("#addressList").html("");
			    $("#address_main").attr("src", basePath + "logined/address/no_address.jsp");
		    }
	    }
	});
}

/**
 * 访问详细信息
 * 
 * @param addressUrl
 * @returns
 */
function getDetailAddress(addressUrl) {
	$("#address_main").attr("src", addressUrl);
}

/**
 * 删除联系人
 * 
 * @param addressId
 *            联系人ID
 */
function deleteAddress(event, addressId) {
	var paramData = {
		'addressId' : addressId
	};
	if ($.browser.msie){
		event.cancelBubble = true ;
	}else{
		event.stopPropagation();
	}
	
	
	// 删除联系人
	art.dialog.confirm(sprintf("addressbook.msg_del_address_confirm"), function() { 
		$.ajax({
		    url : basePath + "address/setup_deleteAddress.action",
		    type : "post",
		    dataType : "text",
		    data : paramData,
		    success : function(data) {
			    if ("success" == data) {
				    window.location.href = window.location.href;
			    } else {
				    art.dialog.alert(sprintf("addressbook.msg_del_address_error"));
			    }
		    }
		});
	}, function() {
		return;
	});
}

/**
 * 过滤字段
 */
var filter = "";
function filterAddress() {
	var tempfilter = $.trim($("#filter").val());
	if (filter != tempfilter) {
		filter = tempfilter;
		findAddressByGroupId($("#groupId").val(), filter, "");
	}
}

/**
 * 设置排序方式
 * 
 * @param type
 */
function orderBy(type) {
	if (null != type && "" != type) {
		var extraPatam = "";
		if ("1" == type) {
			// 按序号（升）
			extraPatam = "&addressVo.sortFiled=orderNo&addressVo.sortType=asc";
		} else if ("2" == type) {
			// 按序号（降）
			extraPatam = "&addressVo.sortFiled=orderNo&addressVo.sortType=desc";
		} else if ("3" == type) {
			// 姓名（A-->Z）
			extraPatam = "&addressVo.sortFiled=name&addressVo.sortType=asc";
		} else if ("4" == type) {
			// 姓名（Z-->A）
			extraPatam = "&addressVo.sortFiled=name&addressVo.sortType=desc";
		}
		findAddressByGroupId($("#groupId").val(), filter, extraPatam);
	}
}

/**
 * 设置光标位置
 */
function filterFocus() {
	if (isNav) {
		document.getElementById("filter").focus();// 获取焦点

	} else {
		setFocus.call(document.getElementById("filter"));
	}
}
var isNav = (window.navigator.appName.toLowerCase().indexOf("netscape") >= 0);
var isIE = (window.navigator.appName.toLowerCase().indexOf("microsoft") >= 0);
function setFocus() {
	var range = this.createTextRange(); // 建立文本选区
	range.moveStart('character', this.value.length); // 选区的起点移到最后去
	range.collapse(true);
	range.select();
}