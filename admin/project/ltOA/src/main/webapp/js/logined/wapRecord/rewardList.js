$(function(){
	getRewardList()
	
	$("#moreData").click(function(){
		getRewardList();
	})
	
	/*$(".icons").click(function(){
		var url=basePath+"logined/record/findUserRecordView.action?_clientType=wap&index=4&id="+id+"&loginUserNow="+loginUserNow;
		location.href=url;
		
	})*/
})



var resultData=new Array();
var iDisplayStart=0;
function getRewardList(){
	var param={
			"loginUserNow":loginUserNow,
			"userId":userId,
			"iDisplayStart":iDisplayStart,
			"iDisplayLength":15
		}
	$.ajax({
		url:basePath+"logined/recordOther/penaltiesListWap.action?_clientType=wap",
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
			html+="<span class='name block'>"+(resultData[i].project)+"</span>";
			html+="<span class='name block'>"+(resultData[i].nature)+":"+(resultData[i].money)+"</span></div>";
			html+="<div class='info-right'><span class='block'>"+resultData[i].nature+"</span>";
			html+="<span class='date block'>"+(resultData[i].date)+"</span></div></div>";
		}
		$("#list").html(html);
	}
}

/**
 * 跳转详情
 * @param id
 */
function toDetial(id){
	window.location.href=basePath+"logined/recordOther/penaltiesFindDetail.action?_clientType=wap&loginUserNow="+loginUserNow+"&penaltiesId="+id;
}