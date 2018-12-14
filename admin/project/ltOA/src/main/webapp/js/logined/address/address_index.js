$(document).ready(function() {
	
	$(".menuList").delegate("li", "click", function() {
		$(this).parent().parent().find(".current").removeClass("current"); 
		$(this).addClass("current");	
	});
	
	// 获取联系人分组信息
	findGroups();

	// 获取共享联系人分组信息
	findShareGroups();
});
/**
 * 获取通讯簿组列表
 */
function findGroups() {
	$.ajax({
	    url : basePath + "addressGroup/setup_getAddressGroupList.action",
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
		    if (null != data && data.aaData != null) {
			    var sb = new StringBuilder();

			    for ( var i = 0; i < data.aaData.length; i++) {
				    var groupName = encodeURI(data.aaData[i].name);
				    var address_main = basePath + "logined/address/address_main.jsp?groupId=" + data.aaData[i].id
				            + "&groupName=" + groupName;
				    if (i == 0) {
					    sb.Append('<li onclick=getAddressByGroup("'+address_main+'") class="current" title="'+data.aaData[i].name+'" ><a>'
					            + data.aaData[i].name + '</a>');
					    $("#address_index_main").attr("src", address_main);

				    } else {
					    sb.Append('<li onclick=getAddressByGroup("'+address_main+'") title="'+data.aaData[i].name+'"><a>'
					            + data.aaData[i].name + '</a>');
				    }
				    sb.Append('</li>');
			    }
			    $("#groupListMenu").html(sb.toString());
			    findPublicGroups();
		    }
	    }
	});
}

/**
 * 获取公共通讯簿组列表
 */
function findPublicGroups() {
	$.ajax({
		url : basePath + "addressGroup/setup_getPublicAddressGroupList.action?maintain=1",
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
		    if (null != data && data.aaData != null) {
			    var sb = new StringBuilder();

			    for ( var i = 0; i < data.aaData.length; i++) {
				    var groupName = encodeURI(data.aaData[i].name);
				    
				    if (data.aaData[i].id == "-2"){
				    	continue;
				    }
				    var address_main = basePath + "logined/address/address_main.jsp?publicSign=1&groupId=" + data.aaData[i].id
				            + "&groupName=" + groupName;
	                 sb.Append('<li onclick=getAddressByGroup("'+address_main+'")  title="'+data.aaData[i].name+'（公共）" ><a>'
					        + data.aaData[i].name + '（公共）</a>');
			
				    sb.Append('</li>');
			    }
			    $("#groupListMenu").append(sb.toString());
		    }
	    }
	});
}

/**
 * 获取共享通讯簿组列表
 */
function findShareGroups() {
	$.ajax({
	    url : basePath + "addressGroup/setup_getShareGroupList.action",
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
		    if (null != data && data.aaData != null) {
			    var sb = new StringBuilder();

			    for ( var i = 0; i < data.aaData.length; i++) {
				    var groupName = encodeURI(data.aaData[i].name);
				    var address_main = basePath + "logined/address/address_main.jsp?groupId=" + data.aaData[i].id
				            + "&groupName=" + groupName +"&type=share";
				    sb.Append('<li onclick=getAddressByGroup("'+address_main+'") ><a>'
				            + data.aaData[i].name + '</a>');
				    sb.Append('</li>');
			    }
			    $("#shareGroupMenu").html(sb.toString());
		    }
	    }
	});
}

function getAddressByGroup(url){
	$("#address_index_main").attr("src", url);
}

