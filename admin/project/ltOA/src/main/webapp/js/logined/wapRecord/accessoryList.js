$(function(){
	
	getRewardList()
//	$("#moreData").click(function(){
//		getRewardList();
//	})
})
var resultData=new Array();
var iDisplayStart=0;
function getRewardList(){
	
	var param={
			"id":id,
			"loginUserNow":loginUserNow,
			"iDisplayStart":iDisplayStart,
			"iDisplayLength":15,
			"type":"fujian"
			
		}
	$.ajax({
		url:basePath+"logined/record/findUserRecordView.action?_clientType=wap",
		data:param,
		type:"post",
		dataType:"json",
		success:function(result){
			var data=result.attachment;
			if(data==""){
				$("#nohave").show();
				return
			}else{
				$("#nohave").hide();
			}
			data=JSON.parse(data);
			resultData=resultData.concat(data);
			/*iDisplayStart=resultData.length;
			if(iDisplayStart<result.iTotalRecords){
				$("#moreData").show();
			}else{
				$("#moreData").hide();
			}*/
//			alert(JSON.stringify(data))
			getHtml(resultData);
		}
	})
}


/**
 * 向手机端传参 
 * @param id
 * @param attachSize
 * @param attachName
 * @param attacthSuffix
 */
function toDownLoadFile(id,attachSize,attachName,attacthSuffix){
	attachName=encodeURI(encodeURI(attachName));
	window.location.href = basePath+"mobile/logined/sign/meeting-list.jsp?_clientType=wap&fileSize="+attachSize + "kb&attachmentName=" + attachName
						+ "&suffix=" + attacthSuffix + "&attachmentId="+id ;
}

/**
 * 组装HTML
 */
function getHtml(resultData){
	var html="";
	if(resultData.length>0){
		
		for(var i=0;i<resultData.length;i++){
//			alert(resultData[i].name)
			html+="<div class='row' onclick='toDetial(\""+resultData[i].name+"\",\""+resultData[i].path+"\")' ><div class='info-left'>";
			html+="<span class='name block'>"+(resultData[i].name)+"</span></div></div>";
			
		}
		$("#list").html(html);
	}
}

/**
 * 跳转详情
 * @param id
 */
function toDetial(fileName,filePath){
	window.location.href=basePath+"filemanager/downFileByFilePath.action?_clientType=wap&filePath=" + filePath + "&fileName=" + fileName;
}