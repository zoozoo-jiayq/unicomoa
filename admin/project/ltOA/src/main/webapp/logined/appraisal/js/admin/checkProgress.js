var tid="";
$(document).ready(function () {
	tid=$("#tid").val();
	var state=$("#state").val();
	var pfState=$("#pfState").val();
	getList(state);
	getList2(pfState);
	getName();
});

var find=function() {
	var state=$("#state").val();
	getList(state);
}
var getName=function() {
	var paramData = {
			"tid":tid
	};
	$.ajax({
	    url : basePath + "performance/setup_getAppraisalInfo.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	$("#khmc").text(data.khmc);	
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};
var getList=function(state) {
	qytx.app.grid({
		id:'myTable',
		url:basePath + "performance/task_findTaskReuslt.action?atr.tid="+tid+"&atr.sfzs="+state,
		iDisplayLength:	15,
		valuesFn:[{
	                    "aTargets": [3],// 覆盖
	                    "fnRender": function ( oObj ) {
	                        var userId =oObj.aData.khryId;
	                        var sfzs =oObj.aData.sfzs;
	                    	var res="";  
					        if(sfzs==1){//1表示自述 
					        	res+="<a href='"+basePath+"logined/appraisal/jsp/staff/leadership_assessment_details.jsp?tid="+tid+"&userId="+userId+"&view_type=1' class='search_icon'></a>";
					        }else if(sfzs==0){// 0表示未自述  -1表示无需自述
					        	res+="未述职";
					        }else{
					        	res+="无需自述";
					        }
					        return   res;
	                    }
	                }
			]
		});
};
var getList2=function(pfState) {
	qytx.app.grid({
		id:'myTable2',
		url:basePath + "performance/task_findTaskReuslt.action?atr.tid="+tid+"&atr.jd="+pfState,
		iDisplayLength:	15,
		valuesFn:[{
		            "aTargets": [2],// 覆盖
		            "fnRender": function ( oObj ) {
		                var khryId =oObj.aData.khryId;
		                var ydfrs =oObj.aData.ydfrs;
		            	var res=""; 
				        if(ydfrs||ydfrs==0){
				        	res+="<span class='blue_color' onclick='ydf("+khryId+")'>"+ydfrs+"</span>";
				        }else{
				        	res+="无打分人员";
				        }
				        return   res;
		            }
		        },{
	                    "aTargets": [3],// 覆盖
	                    "fnRender": function ( oObj ) {
	                       var khryId =oObj.aData.khryId;
	                        var wdfrs =oObj.aData.wdfrs;
	                    	var res="";  
					        if(wdfrs||wdfrs==0){
					        	res+="<span class='blue_color' onclick='wdf("+khryId+")'>"+wdfrs+"</span>";
					        }else{
					        	res+="无未打分人员";
					        }
					        return   res;
	                    }
	                }
			]
		});
};
var getDfry=function(khryId,dflx) {
	var paramData = {
			"at.vid":tid,
			"dflx":dflx,
			"khryId":khryId
	};	
	//alert(JSON.stringify(paramData))
	$.ajax({
	    url : basePath + "performance/task_findDfry.action",
	    type : "post",
	    dataType : 'text',
	    data : paramData,
	    success : function(data) {
		    if (data&&data!=0) {
		    	if(data==""){
			    	if(dflx==1){
			    		$("#ydfrys").html("无人员数据");	
			    	}else{
				    	$("#wdfrys").html("无人员数据");	
			    	}
		    	}else{
			    	if(dflx==1){
			    		$("#ydfrys").html(data);	
			    	}else{
				    	$("#wdfrys").html(data);	
			    	}
		    	}
		    }else if (data==0){
		    	art.dialog.alert("获取打分人员信息失败！");
		    }else{
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
};