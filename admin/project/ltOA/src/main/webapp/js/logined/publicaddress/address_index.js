$(document).ready(function() {


	// 获取联系人分组信息
	findGroups();

	// var urlinfo = window.location.href;// 獲取url中通讯薄分组名称
	// var allParam = urlinfo.split("?")[1];// 拆分url得到”=”後面的參數
	// var userName = allParam.split("&")[1].split("=")[1];
	// $("#groupName").val(decodeURI(userName));
});
/**
 * 获取通讯簿组列表
 */
function findGroups() {
	$.ajax({
	    url : basePath + "addressGroup/setup_getPublicAddressGroupList.action?maintain=1",
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
		    if (null != data && data.aaData != null && "" != data.aaData) {
			    var sb = "";
			    sb+='<h1>外部通讯录</h1>';
			    for ( var i = 0; i < data.aaData.length; i++) {
				    var groupName = encodeURI(data.aaData[i].name);
				    var groupId = data.aaData[i].id;
				    
//				    if (i == 0) {
//					    sb.Append('<li onclick=openSrc("' + address_main + '") class="current" title="'
//					            + data.aaData[i].name + '"><a>' + data.aaData[i].name + '</a>');
//					    $("#address_index_main").attr("src", address_main);
//
//				    } else {
//					    sb.Append('<li onclick=openSrc("' + address_main + '") title="' + data.aaData[i].name + '"><a>'
//					            + data.aaData[i].name + '</a>');
//				    }

				    if (i== 0){				    	
				    	  sb+='<p class="menu-p borderlast" onclick="openSrc(\''+groupName+'\',\''+groupId+'\')"><i class="menu-i"></i><a  href="javascript:void(0)" >'+ data.aaData[i].name + '</a></p>';
				    	  openSrc(groupName,groupId);
				    	  //  $("#address_index_main").attr("src", address_main);
				    }else if(i== (data.aaData.length-1)){
				    	sb+='<p class="menu-p borderlast" onclick="openSrc(\''+groupName+'\',\''+groupId+'\')"><i class="menu-i"></i><a href="javascript:void(0)" >'+ data.aaData[i].name + '</a></p>';
				    }else{
				    	sb+=' <p class="menu-p" onclick="openSrc(\''+groupName+'\',\''+groupId+'\')"><i class="menu-i"></i><a href="javascript:void(0)" >'+ data.aaData[i].name + '</a></p>';
				    }
				    
				    
			    }
			    $("#groupListMenu").html(sb.toString());

		    } else {
		    	$("#address_index_main").attr("src", basePath + "logined/publicaddress/no_address.jsp");
		    }
	    }
	});
}

function openSrc(groupName,groupId) {
	$("#groupName").val(groupName);
	$("#groupId").val(groupId);
	
	// 获取当前用户,是否拥有此用户组的维护权限
	if ('-2' ==groupId){
		$("#maintainPriv").val(1);		
    	$("#addAddressDiv").show();
    	queryAddress();
	}else{
		$.ajax({
		    url : basePath + "addressGroup/setup_getMaintainPriv.action?deleteType=2&addressGroup.id=" + $("#groupId").val(),
		    type : "post",
		    dataType : "text",
		    success : function(data) {
			    if ("success" == data) {
			    	$("#maintainPriv").val(1);		
			    	$("#addAddressDiv").show();
			    } else {
			    	$("#maintainPriv").val(0);
			    	
			    }
			    queryAddress();
		    }
		});	
}

}
