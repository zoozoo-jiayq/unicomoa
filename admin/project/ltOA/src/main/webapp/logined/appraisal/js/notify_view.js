var id="";
$(document).ready(function () {
	id=$("#id").val();
	getData();
});
var getData=function() {
	var paramData = {
			"id":id
	};
	$.ajax({
	    url : basePath + "notify/notify_view.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
			   // art.dialog.alert('操作成功！')
		    	$("#title").text(data.subject);
		    	$("#time").text(data.createDate);
		    	$("#num").text(data.viewCount);
		    	$("#name").text(data.createUser.userName);
		    	$("#content").html(data.content);
		        if (data.attachmentList.length>0) {
		        	$('#fj').css('display','block');
		        	$('#file').css('display','block');
		        	 var attaStr='<ul id="attachmentList">';
		        	for(var i=0;data.attachmentList.length>i;i++){
			            attaStr += ' <li><a  onclick="downFileById(' +data.attachmentList[i].id + ')" class="file_tishi"><em></em>' +data.attachmentList[i].attachName+ '</a></li>';
		        	}
		        	attaStr +='</ul>';
		            $("#file").html(attaStr);
		        }else {
		        	$('#fj').css('display','none');
		        	$('#file').css('display','none');
		        }
		    } else {
		    	art.dialog.alert("获取公告信息失败！");
		    }
	    }
	});
	return false;
};