$(function(){
	getRewardList()
	
	$("#moreData").click(function(){
		getRewardList();
	})
})
var resultData=new Array();
var iDisplayStart=0;
function getRewardList(){
	var param={
			"recordWork.userInfo.userId":userId,
			"loginUserNow":loginUserNow,
			"iDisplayStart":iDisplayStart,
			"iDisplayLength":15
		}
	$.ajax({
		url:basePath+"logined/recordOther/findlistByPage.action?_clientType=wap",
		data:param,
		type:"post",
		dataType:"json",
		success:function(result){
			var data=result.aaData;
			if(data.length==0){
				$("#nohave").show();
			}else{
				$("#nohave").hide();
			}
			resultData=resultData.concat(data);
			iDisplayStart=resultData.length;
			if(iDisplayStart<result.iTotalRecords){
				$("#moreData").show();
			}else{
				$("#moreData").hide();
			}
			getHtml(resultData);
		}
	})
}

/**
 * 组装HTML
 */
function getHtml(resultData){
	var html="";
	if(resultData.length>0){
		for(var i=0;i<resultData.length;i++){
			
			html+="<div class='row' onclick='toDetial("+resultData[i].id+")'  ><div class='info-left'>";
			html+="<span class='name block'>"+(resultData[i].name)+"</span>";
			html+="<span class='name block'>"+(resultData[i].workUnit)+"</span></div>";
			html+="<div class='info-right'><span class='block'>"+resultData[i].reterence+"</span>";
			html+="<span class='date block'>"+(resultData[i].industryCategory)+"</span></div></div>";
		}
		$("#list").html(html);
	}
}

/**
 * 跳转详情
 * @param id
 */
function toDetial(id){
	var userId=$("#userId").val();
	window.location.href=basePath+"/logined/recordOther/findDetail.action?_clientType=wap&loginUserNow="+loginUserNow+"&type=2&recordWorkId="+id;
}