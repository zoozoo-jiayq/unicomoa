var userMap = new HashMap();
$(document).ready(function() {
	// 查询所有内部组信息
	findGroup();	
});

/**
 * 初始化页面信息
 */
function _initSelectUser(){
	$("#btnSearch").val("");
	$("#bookListul").show();
	$("#m_noGroup").hide();
	userMap.clear();
	findGroup();
}

// 按组查询信息
function findGroup() {
	var groupHtml = "";
	$
	        .ajax({
	            url : basePath + "group/getGroupList.action"+wapParam+"&groupType=1"+"&random="+Math.random(),
	            type : "post",
	            dataType : "json",
	            success : function(data) {
		            if (data != null && "" != data) {
			            // 分解群组信息
			            for ( var i = 0; i < data.length; i++) {
				            var jsonMap = data[i];
				            // 组名
				            var name = jsonMap["name"];
				            // 组ID
				            var id = jsonMap["id"];
//				            if (i == 0) {
//					            groupHtml += '<li class="current"><p class="searched"><span class="mChk"><input name="opinions" type="checkbox" value="'
//					                    + id + '"  ><label></label> </span><em></em>' + name + '</p></li>';
//
//					            // 同时查询第一小组成员信息
//					            getUserByGroupId(id.substring(4, id.length));
//				            } else {
					            groupHtml += '<li><p id="p_' + id.substring(4, id.length)
					                    + '"  ><span class="mChk"><input name="opinions" type="checkbox" value="' + id
					                    + '"  ><label></label> </span><em></em><font>' + name + '</font></p></li>';
//				            }
			            }
			            $("#bookListul").html(groupHtml);
			            $('#bookList').hcheckbox();
			            headClick();

			            $("#bookList li p").click(function() {
				            if ($(this).parent().hasClass("current"))
					            $(this).parent().removeClass("current");
				            else {
					            $(this).parent().siblings("li").removeClass("current");
					            $(this).parent().addClass("current");

					            // 发送请求获取
					            if (!$(this).hasClass("searched")) {
						            var groupid = $(this).attr("id");
						            getUserByGroupId(groupid.substring(2, groupid.length));
						            $(this).addClass("searched");
					            }
				            }
			            });
		            }
	            }
	        });
}

// 点击组复选按钮
function headClick() {
	// 选择部门复选框时,同时展开
	$("#bookList li p span label").click(function() {
		if ($(this).parent().parent().parent().hasClass("current")) {
			setSelectAll($(this).siblings("input:checkbox"));
		} else {
			$(this).parent().parent().parent().siblings("li").removeClass("current");
			$(this).parent().parent().parent().addClass("current");
			setSelectAll($(this).siblings("input:checkbox"));
		}
		var pobj = $(this).parent().parent();
		if (!pobj.hasClass("searched")) {
			var groupid = pobj.attr("id");
			getUserByGroupId(groupid.substring(2, groupid.length), this);
			pobj.addClass("searched");
		}
	});
}

// 设置是否全选
function setSelectAll(checkbox) {
	if (checkbox.attr("checked") != "checked") {
		$(checkbox).parents("#bookList .current").find(".m_List li input").removeAttr("checked");
		$(checkbox).parents("#bookList .current").find(".m_List li label").removeClass('checked');

		var inputArray = $(checkbox).parents("#bookList .current").find(".m_List li input");
		for ( var i = 0; i < inputArray.length; i++) {
			// 如果是人员，则保存到map里面
			var userInfo = $(inputArray[i]).val();
			var userInfoArr = userInfo.split("_");
			userMap.remove(userInfoArr[0]);
		}
	} else {
		$(checkbox).parents("#bookList .current").find(".m_List li input").attr("checked", "checked");
		$(checkbox).parents("#bookList .current").find(".m_List li label").addClass("checked");

		var inputArray = $(checkbox).parents(".current").find(".m_List li input");
		for ( var i = 0; i < inputArray.length; i++) {
			// 如果是人员，则保存到map里面
			var treeNode = new TreeNode();
			var userInfo = $(inputArray[i]).val();
			var userInfoArr = userInfo.split("_");
			treeNode.setId(userInfoArr[0]);
			treeNode.setName(userInfoArr[1]);
			treeNode.setType("user");
			userMap.set(userInfoArr[0], treeNode);
		}
	}
}

// 用户复选框变更
function changeSelect(checklable) {
	var userInfo = $(checklable).prev().val();
	var userInfoArr = userInfo.split("_");
	if ($(checklable).hasClass("checked")) {
		// 删除map中的数据
		userMap.remove(userInfoArr[0]);
	} else {
		// 将人员保存在map中
		var treeNode = new TreeNode();
		treeNode.setId(userInfoArr[0]);
		treeNode.setName(userInfoArr[1]);
		treeNode.setType("user");
		userMap.set(userInfoArr[0], treeNode);
	}

	// 首先此用户是通过模糊查询获取,然后查找群组中的相同名字的用户设置选中状态
	if($(checklable).parents(".m_noGroup").length == '1' && ($("#bookListul :checkbox[value='"+userInfo+"']:first").length == 1)){
		var checklableTemp = $("#bookListul :checkbox[value='"+userInfo+"']:first");
		if ($(checklable).hasClass("checked")){
			$("#bookListul :checkbox[value='"+userInfo+"']:first").attr("checked", false);
			$("#bookListul :checkbox[value='"+userInfo+"']:first").siblings("label").removeClass("checked");
		}else{
			$("#bookListul :checkbox[value='"+userInfo+"']:first").attr("checked", "checked");
			$("#bookListul :checkbox[value='"+userInfo+"']:first").siblings("label").addClass("checked");
		}	
		
		// 同时判断所有子项都选中或未选中时,更新组上的复选框状态
		var selected = $(checklableTemp).parents(".current").find(".m_List li input:checkbox:checked").length;
		var selectAll = $(checklableTemp).parents(".current").find(".m_List li input:checkbox").length;

		if (selected == selectAll) {
			// 设置选中状态
			$(checklableTemp).parents("#bookList .current").find("p span label").addClass("checked");
			$(checklableTemp).parents("#bookList .current").find("p span input").attr("checked", "checked");
		} else {
			// 去掉选中状态
			$(checklableTemp).parents("#bookList .current").find("p span label").removeClass("checked");
			$(checklableTemp).parents("#bookList .current").find("p span input").attr("checked", false);
		}
	}else{
		var selected = $(checklable).parents("#bookList .current").find(".m_List li input:checkbox:checked").length;
		var selectAll = $(checklable).parents("#bookList .current").find(".m_List li input:checkbox").length;
		if (selected == selectAll - 1 && !($(checklable).hasClass("checked"))) {
			// 设置选中状态
			$(checklable).parents("#bookList .current").find("p span label").addClass("checked");
			$(checklable).parents("#bookList .current").find("p span input").attr("checked", "checked");
		} else {
			// 去掉选中状态
			$(checklable).parents("#bookList .current").find("p span label").removeClass("checked");
			$(checklable).parents("#bookList .current").find("p span input").attr("checked", false);
		}
	}
	headClick();
}

// 获取选择结果信息
function chooseUser() {
    _userSelectSelfCallBack(userMap);
    return false;
}

// 根据组名获取组下成员信息
function getUserByGroupId(groupId, obj) {
	var userHtml = "";
	$.ajax({
	    url : basePath + "group/findUsersByGroupId.action"+wapParam+"&groupId=" + groupId,
	    type : "post",
	    dataType : "json",
	    success : function(data) {
		    if (data != null && null != data.aaData && data.aaData.length>0) {
			    // 分解员工信息
			    userHtml += '<ul class="m_List" id="' + groupId + '">';
			    var isAllSelect = true;;
			    for ( var i = 0; i < data.aaData.length; i++) {
				    var jsonMap = data.aaData[i];
				    // 姓名
				    var userName = jsonMap["userName"];
				    // id
				    var userId = jsonMap["userId"];
				    // 职务
				    var job = jsonMap["job"];
				    if (job == undefined) {
					    job = "&nbsp;";
				    }else{
				    	job = "<span><font>" + job + "</font></span>";
				    }
				    userHtml += '<li><span class="mChk"><input name="userCheckbox" type="checkbox" value="' + userId + "_" + userName;
				    
				    if(containUser(userId, userMap)){
				    	userHtml += '" checked="checked"><label onclick="changeSelect(this)" class="checkbox checked" ></label>';;
				    }else{
				    	isAllSelect = false;
				    	userHtml += '"><label class="checkbox" onclick="changeSelect(this)"></label></span>';
				    }				    
				    userHtml += '</span><h2><font>' + userName + '</font></h2><h3>' + job + '</h3></li>';
			    }
			    userHtml += '</ul>';
			    $("#bookListul .current").append(userHtml);
			    $('#bookList').hcheckbox();
			    headClick();
			    
			    // 判断如果组下成员全部选择,则选中群组复选框
			    if(isAllSelect){
			    	$("#bookList .current p input").attr("checked", "checked");
				    $("#bookList .current p label").addClass("checked");
			    }
			    if (null != obj) {
				    setSelectAll($(obj).siblings("input:checkbox"));
			    }
		    }
	    }
	});
}

/**
 * 选择人员 * 
 * @param type 1 部门 2 角色 3 分组 4 在线 5,查找
 */
function searchUser(type, showType) {
	var searchName = $("#btnSearch").val();

	if (null == searchName || $.trim(searchName) == ''){
		$("#bookListul").show();
		$("#m_noGroup").html("");	
		return;
	}else{		
		$("#bookListul").hide();
		$("#m_noGroup").show();	
	}
	var dataParam = {
	    'type' : type,
	    'searchName' : searchName,
	    'showType' : showType
	};
	$.ajax({
	    url : basePath+"user/selectUser.action"+wapParam,
	    type : 'post',
	    dataType : 'json',
	    data : dataParam,
	    success : function(data) {	    	
	    	if (data != null && "" != data && data.length>0) {	
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
		            if (job == undefined) {
					    job = "";
				    }else{
				    	job = "<span><font>" + job + "</font></span>";
				    }
		            userHtml += '<li><span class="mChk"><input name="opinions" type="checkbox" value="'+id.substring(4, id.length) + "_" + name;
		            if(containUser(id.substring(4, id.length), userMap)){
		            	userHtml += '" checked="checked"><label class="checkbox checked" onclick="changeSelect(this)"></label></span>';
		            }else{
		            	userHtml += '"><label class="checkbox" onclick="changeSelect(this)"></label></span>';
		            }
		            
			        userHtml += '<h2><font>'+name+'</font></h2><h3>'+job+'</h3></li>';
		            
	            }
	            $("#m_noGroup").html(userHtml);	
	            $('#bookList').hcheckbox();
	            headClick();
            }else{
            	$("#m_noGroup").html("");	
            	$('#bookList').hcheckbox();
            	headClick();
            }
	    }
	});
}

// 判断是否包含选择信息
function containUser(userId, userMapTemp){	
	var isContainUser = false;
	userMapTemp.forEach(function(value, key) {
		if(value.Id == userId){
			isContainUser = true;
		}
	});
	return isContainUser;
}

