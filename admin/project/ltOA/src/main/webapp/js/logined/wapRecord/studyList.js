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
			"loginUserNow":loginUserNow,
			"userId":userId,
			"iDisplayStart":iDisplayStart,
			"iDisplayLength":15
		}
	$.ajax({
		url:basePath+"logined/recordOther/listlearn.action?_clientType=wap",
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
			
			html+="<div class='row' onclick='toDetial("+resultData[i].learnId+")'  ><div class='info-left'>";
			html+="<span class='name block'>"+(resultData[i].userName)+"</span>";
			html+="<span class='name block'>"+(resultData[i].school)+"</span></div>";
			html+="<div class='info-right'><span class='block'>"+resultData[i].education+"</span>";
			html+="<span class='date block'>"+(resultData[i].major)+"</span></div></div>";
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
	window.location.href=basePath+"logined/recordOther/findByIdlearnWap.action?_clientType=wap&learnId="+id;
}