var tid="";
$(document).ready(function () {
	tid=$("#tid").val();
	//tid=getvl("tid");
	var state=$("#state").val();
	var userName="";
	getList(state,userName);
	getName();
});

var find=function() {
	var state=$("#state").val();
	var userName=$("#userName").val();
	getList(state,userName);
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

function format(val) {
	return (typeof(val)=="undefined")?"0":parseInt(val);
}
var getList=function(state,userName) {
	var paramData = {
			"tid":tid,
			"userName":userName,
			"state":state
	};
	$.ajax({
	    url : basePath + "performance/setup_khtrList.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	$("#dpfrs").html("");
		    	$("#firstTr").html("");
		    	var html ="";
		    	var text="";
		    	var num=0;
		    	if(data.aaData.length!=0){
		    		for(var i=0;i<data.aaData.length;i++){
				    	html +="<tr><td class='wd100 text_hide'>"+data.aaData[i].userName+"</td>"
						+"<td class='wd165 text_hide'>"+data.aaData[i].groupName+"</td>";
						if(data.aaData[i].job){
							html +="<td class='wd165 text_hide'>"+data.aaData[i].job+"</td>";
						}else{
							html +="<td class='wd165 text_hide'>-</td>";
						}					
				    	if(data.aaData[i].pl){
						    html +="<td class='wd100 text_hide'><a href='"+basePath+"logined/appraisal/jsp/staff/appraisalShow.jsp?tid="+tid+"&userId="+data.aaData[i].userId+"' class='blue_color'>查看</a></td>";
				    	}else{
						    html +="<td class='wd100 text_hide'>无</td>";
				    	}
				    	if(typeof (data.aaData[i].fz) != "undefined"){
							html +="<td class='wd80 text_hide'>"+parseInt(data.aaData[i].fz)+"</td>"
							+"</tr>";							
				    	}else{
				    		if(data.khjd==2){
								html +="<td class='wd80 text_hide'><a href='"+basePath+"logined/appraisal/jsp/staff/otherPeopleScore.jsp?tid="+tid+"&userId="+data.aaData[i].userId+"&view_type=1' class='blue_color'>评分</a></td>"
								+"</tr>";
				    		}else{
								html +="<td class='wd80 text_hide'>--</td>"
								+"</tr>";
				    		}
							num++;
				    	}
			    	}
		    		text =(data.aaData.length-num)+"/"+data.aaData.length;
		    	}else{
		    		html ="<tr><td colspan='5'>暂无数据</td></tr>";
		    		text ="0/0";
		    	}
		    	$("#dpfrs").append(text);
		    	$("#firstTr").append(html);
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};


/*var getList=function(state,userName){
qytx.app.grid({
	id:'myTable',
	url:basePath + "performance/setup_khtrList.action?tid="+tid+"&userName="+userName+"&state="+state,
	iDisplayLength:	15,
	valuesFn:[{
			        "aTargets": [2],// 覆盖
			        "fnRender": function ( oObj ) {
			            var job =oObj.aData.job;
			        	var res="";  
				        if(job){
				        	res+=job;
				        }else{
				        	res+="-";
				        }
				        return   res;
			        }
			    },{
			        "aTargets": [3],// 覆盖
			        "fnRender": function ( oObj ) {
			            var pl =oObj.aData.pl;
			            var userId =oObj.aData.userId;
			        	var res="";  
				    	if(pl){
				    		res +="<a href='"+basePath+"logined/appraisal/jsp/staff/appraisalShow.jsp?tid="+tid+"&userId="+userId+"' class='blue_color'>查看</a>";
				    	}else{
				    		res +="无";
				    	}
				        return   res;
			        }
			    },{
                    "aTargets": [4],// 覆盖
                    "fnRender": function ( oObj ) {
                        var userId =oObj.aData.khry;
                        var fz =oObj.aData.fz;
                    	var res="";  
                    	if(typeof (fz) != "undefined"){
                    		res +=parseInt(fz);							
				    	}else{
				    		if(oObj.khjd==2){
				    			res +="<a href='"+basePath+"logined/appraisal/jsp/staff/otherPeopleScore.jsp?tid="+tid+"&userId="+data.aaData[i].userId+"&view_type=1' class='blue_color'>评分</a>";
				    		}else{
				    			res +="--";
				    		}
				    	}
				        return   res;
                    }
                }
		]
	});

}*/

function getvl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return unescape(r[2]);  
    return null;  
};