$(function(){
	
	 userid=location.search;      //获取url后面带的参数？userid=1  这类的
	 index=userid.indexOf("userid=")+7; //获取=的位置，下面截取
	userid=userid.substring(index)   //获取userid的值
	
	
	getOnlineRecordList(userid);
	/**
	 * 
	 */
	$("#serch").click(function(){
		
		iDisplayStart=0;
		resultData=[];
		getOnlineRecordList(userid);
	})
	$("#moreData").click(function(){
		//iDisplayStart=0;
		//resultData=[];
		getOnlineRecordList(userid);
	})
	
	$("#leaveRecord").click(function(){
		window.location.href="leaveRecord.jsp?_clientType=wap&userid="+userid
	})
	
	$("#backImg").click(function(){
		window.history.back()
	})
	
	
})
var iDisplayStart=0;
var resultData=new Array();
function getOnlineRecordList(userid){
//	var userId=$("#userId").val();
	var treeType=$("#treeType").val();
	var serchName=$("#serchName").val();
	var param={
		"userId":userid,
		"treeType":treeType,
		"iDisplayLength":20,
		"iDisplayStart":iDisplayStart,
		"searchName":serchName
	}
	$.ajax({
		url:basePath+"/logined/record/listAjax.action?_clientType=wap",
		type:"post",
		data:param,
		dataType:"json",
		success:function(result){
			var data=result.aaData;
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
			html+="<div class='row' onclick='toDetial("+resultData[i].id+")'  ><div class='info-box'>";
			html+="<span class='name-box orange'>"+(resultData[i].userName).substring((resultData[i].userName).length-2)+"</span>";
			html+="<div class='info'><span class='name block'>"+resultData[i].userName+"</span>";
			html+="<span class='block department'>"+resultData[i].groupName+"</span></div></div></div>";
		}
		$("#list").html(html);
	}
}
/**
 * 跳转详情
 * @param id
 */
function toDetial(id){
	window.location.href=basePath+"logined/record/findUserRecordView.action?_clientType=wap&id="+id+"&loginUserNow="+userid;
}
