$(document).ready(function() {
	setTimeout(function(){
		if ($("#type").val() == 1){
			searchGroup('1') ;
		}else{
			searchGroup('2') ;
		}
	}, 100);
});

function searchGroup(type) {
	$("#m_noGroup").html("");
	if ('1' == type) {
		$("#seleBook").html('个人通讯录<em class="iconSele"></em>');
		type = 1;
		findGroup(1);
		$("#btnSearch").val("");
		$("#type").val("1");
	} else {
		$("#seleBook").html('单位通讯录<em class="iconSele"></em>');
		findGroup(2);
		$("#type").val("2");
		$("#btnSearch").val("");
	}
}

// 按组查询信息
function findGroup(type) {
	var requestUrl = "";
	if ("1" == type) {		
		requestUrl = "addressGroup/setup_getAddressGroupList.action"+wapParam+"&random="+Math.random();;
	} else {
		requestUrl = "group/getGroupList.action"+wapParam+"&groupType=1";
	}

	var groupHtml = "";
	$.ajax({
	    url : basePath + requestUrl,
	    type : "post",
	    dataType : "json",
	    success : function(data) {

		    if (data != null && "" != data) {
			    // 分解群组信息
			    if ("1" == type) {
				    for ( var i = 0; i < data.aaData.length; i++) {
					    var jsonMap = data.aaData[i];
					    // 组名
					    var name = jsonMap["name"];
					    // 组ID
					    var id = jsonMap["id"];
//					    if (i == 0) {
//						    groupHtml += '<li class="current"><p><em></em><font>' + name 
//						            + '</font></i></p><ul class="m_List"></ul></li>';
//
//						    // 同时查询第一小组成员信息
//						    getUserByGroupId(id, type);
//					    } else {
					    	// 统一不显示群组下人员数量
						    groupHtml += '<li><p id="p_' + id + '" ><em></em><font>' + name 
						            + '</font></i></p><ul class="m_List"></ul></li>';
//					    }
				    }
			    } else if ("2" == type) {
				    for ( var i = 0; i < data.length; i++) {
					    var jsonMap = data[i];
					    // 组名
					    var name = jsonMap["name"];
					    // 组ID
					    var id = jsonMap["id"];
//					    if (i == 0) {
//						    groupHtml += '<li class="current"><p><em></em><font>' + name
//						            + '</font></i></p><ul class="m_List"></ul></li>';
//
//						    // 同时查询第一小组成员信息
//						    getUserByGroupId(id.substring(4, id.length), type);
//					    } else {
						    groupHtml += '<li><p id="p_' + id.substring(4, id.length) + '" ><em></em><font>' + name
						            + '</font></i></p><ul class="m_List"></ul></li>';
//					    }
				    }
			    }

			    $("#bookListul").html(groupHtml);

			    $(".bookList li p").click(function() {
				    if ($(this).parent().hasClass("current"))
					    $(this).parent().removeClass("current");
				    else {
					    $(this).parent().siblings("li").removeClass("current");
					    $(this).parent().addClass("current");

					    // 发送请求获取
					    if (!$(this).hasClass("searched")) {
						    var groupid = $(this).attr("id");
						    getUserByGroupId(groupid.substring(2, groupid.length), type);
						    $(this).addClass("searched");
					    }
				    }
			    })
		    }
	    }
	});
}

// 根据组名获取组下成员信息
function getUserByGroupId(groupId, type) {
	var userHtml = "";
	var requestUrl = "";
	if ("1" == type) {
		requestUrl = "address/setup_getWapAddressList.action"+wapParam+"&ownSign=1&addressVo.addressGroupId=" + groupId + "&addressVo.type="
		        + $("#type").val();
	} else {
		requestUrl = "group/findUsersByGroupId.action"+wapParam+"&groupId=" + groupId;
	}
	$.ajax({
	    url : basePath + requestUrl  +"&random="+Math.random(),
	    type : "post",
	    dataType : "json",
	    success : function(data) {
		    if (data != null && null != data.aaData) {
			    // 分解员工信息
			    userHtml += '<ul class="m_List" id="' + groupId + '">';
			    if ("1" == type) {
				    for ( var i = 0; i < data.aaData.length; i++) {
					    var jsonMap = data.aaData[i];
					    // 姓名
					    var userName = jsonMap["name"];
					    // id
					    var userId = jsonMap["id"];
					    // 职务
					    var job = jsonMap["postInfo"];
					    if (job == undefined || "" == job) {
						    job = "";
					    }else{
					    	job = "<span><font>" + job + "</font></span>";
					    }
					    // 电话
					    var phone = jsonMap["familyMobileNo"];
					    if (phone == undefined) {
						    phone = "";
					    }
					    userHtml += '<li onclick=getDetailInfo("'+userId+'") ><span class="mChk"></span><h2><font>' + userName + '</font></h2><h3>' + job  + phone
					            + '</h3></li>';
				    }
			    } else {
				    for ( var i = 0; i < data.aaData.length; i++) {
					    var jsonMap = data.aaData[i];
					    // 姓名
					    var userName = jsonMap["userName"];
					    // id
					    var userId = jsonMap["userId"];
					    // 职务
					    var job = jsonMap["job"];
					    if (job == undefined || "" == job) {
						    job = "";
					    }else{
					    	job = "<span><font>" + job + "</font></span>";
					    }
					    // 电话
					    var phone = jsonMap["phone"];
					    if (phone == undefined) {
						    phone = "";
					    }
					    userHtml += '<li onclick=getDetailInfo("'+userId+'") ><span class="mChk"></span><h2><font>' + userName + '</font></h2><h3>' + job + phone
					            + '</h3></li>';
				    }
			    }

			    userHtml += '</ul>';
			    $(".current").append(userHtml);
		    }
	    }
	});
}

function searchUser() {
	var searchName = $("#btnSearch").val();
	type = $("#type").val();
	if (null == searchName || $.trim(searchName) == '') {
		$("#bookListul").show();
		$("#m_noGroup").html("");
		if ('2' == type) {
			findGroup(2);
		} else {
			findGroup(1);
		}
		return;
	} else {
		$("#bookListul").html("");
		$("#m_noGroup").show();
	}
	
	if ("1" == type) {
		searchOwnUser();
	} else {
		searchCompanyUser();
	}
}

function searchOwnUser() {
	 var searchName = $("#btnSearch").val();
     var dataParam = {
         'address.name' : searchName
     };
     $.ajax({
         url : basePath + "address/setup_getOwnAddressByName.action"+wapParam+"&random="+Math.random(),
         type : 'post',
         dataType : 'json',
         data : dataParam,
         success : function(data) {
        	 if (data != null && "" != data) {
 			    // 分解群组信息
 			    var userHtml = "";
 			    for ( var i = 0; i < data.aaData.length; i++) {
 				    var jsonMap = data.aaData[i];
 				    // 组名
 				    var name = jsonMap["name"];
 				    // 组ID
 				    var id = jsonMap["id"];
 				    // 职务
 				    var job = jsonMap["postInfo"];
 				    if (job == undefined || "" == job) {
 					    job = "";
 				    }else{
				    	job = "<span><font>" + job + "</font></span>";
				    }
 				    var phone = jsonMap["familyMobileNo"];
 				    if (phone == undefined) {
 					    phone = "";
 				    }
 				    userHtml += '<li onclick=getDetailInfo("'+id+'")><span class="mChk"></span><h2><font>' + name + '</font></h2><h3>' + job 
 				            + phone + '</h3></li>';
 			    }
 			    $("#m_noGroup").html(userHtml);
 			    $('.bookList').hcheckbox();
 		    }
         }
     });
}

function searchCompanyUser() {
	var dataParam = {
	    'type' : 5,
	    'searchName' : $("#btnSearch").val(),
	    'showType' : 3
	};
	$.ajax({
	    url : basePath + "user/selectUser.action"+"?random="+Math.random(),
	    type : 'post',
	    dataType : 'json',
	    data : dataParam,
	    success : function(data) {
		    if (data != null && "" != data) {
			    // 分解群组信息
			    var userHtml = "";
			    for ( var i = 0; i < data.length; i++) {
				    var jsonMap = data[i];
				    // 组名
				    var name = jsonMap["name"];
				    // 组ID
				    var id = jsonMap["id"];
				    // 职务
				    var job = jsonMap["target"];
				    if (job == undefined || "" == job) {
					    job = "";
				    }else{
				    	job = "<span><font>" + job + "</font></span>";
				    }
				    var phone = jsonMap["obj"];
				    if (phone == undefined) {
					    phone = "";
				    }
				    userHtml += '<li onclick=getDetailInfo("'+id.substring(4, id.length)+'")><span class="mChk"></span><h2><font>' + name + '</font></h2><h3>' + job
				            + phone + '</h3></li>';
			    }
			    $("#m_noGroup").html(userHtml);
			    $('.bookList').hcheckbox();
		    }
	    }
	});

}

/**
 * 根据用户Id
 * 
 * @param groupId
 */
function getDetailInfo(userId) {
	var address_url = '';
	if ("1" == $("#type").val()) {
		address_url = basePath + "address/getDetailInfo.action"+wapParam+"&operateType=wapDetail&addressVo.id=" + userId+"&random="+Math.random();
	} else {
		address_url = basePath + "logined/record/createOrEditByUserId.action"+wapParam+"&from=wap&userId="+userId+"&random="+Math.random();;
	}
	window.document.location = address_url;
}