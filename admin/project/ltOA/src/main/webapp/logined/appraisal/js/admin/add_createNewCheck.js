var vid="";
var ck_vid="";
var type="";
$(document).ready(function () {
	vid=$("#vid").val();
	ck_vid=$("#ck_vid").val();
	type=$("#type").val();
	getRefer();
	getTime();
	$('#year').css('display','block');
	$('#jd').css('display','none');
	$('#month').css('display','none');
	$('#date').css('display','none');
	$('#startDate').css('display','none');
	$('#endDate').css('display','none');
	var types = type.split(",");
	if(types[0]==1){
		getOne(vid,ck_vid);
	}else{
		type="1,0,0,0"
	}
});

var getRefer=function() {
	var paramData = {};
	$.ajax({
	    url : basePath + "performance/task_findTaskList.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	var text="";
		    	for(var i=0;data.aaData.length>i;i++){
		    		text+="<option value='"+data.aaData[i].vid+"'>"+data.aaData[i].khmc+"</option>";		 
		    	}
		    	$('#ck').append(text);
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};
var getTime=function(){
	var d = new Date();
	var st=d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate();
	if(d.getMonth()==0){
		st=(d.getFullYear()-1) + "-12-" + d.getDate();
	}
	var et=d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();
	var m=d.getMonth()+1;
	if(m<4&&m>0){
		$("#jd").val("1");
	}else if(m<7&&m>3){
		$("#jd").val("2");
	}else if(m<10&&m>6){
		$("#jd").val("3");
	}else{
		$("#jd").val("4");
	}
	$("#startDate").val(st);
	$("#endDate").val(et);
    var bYear = false;
    var valueM = "";
    if(d.getMonth()==0){
        bYear = true;
    }
    var lastyear = d.getFullYear()-5;
    for(var i =0 ;i<11;i++){

        if(bYear&&(lastyear== d.getFullYear())){
            $("<option value="+lastyear+" selected='selected'>"+lastyear+"年</option>").appendTo("#year");
        }else {
            if(lastyear== d.getFullYear()){
                if(!bYear){
                    $("<option value="+lastyear+" selected='selected'>"+lastyear+"年</option>").appendTo("#year");
                }else {
                    $("<option value="+lastyear+">"+lastyear+"年</option>").appendTo("#year");
                }
            }else {
                $("<option value="+lastyear+">"+lastyear+"年</option>").appendTo("#year");
            }
        }
        lastyear++;
    }
    for(var i =1 ;i<=12;i++){
        if(i<10){
            valueM = "0"+i;
        }else {
            valueM = i;
        }
        if(i== d.getMonth()+1){
                $("<option value="+valueM+" selected='selected'>"+i+"月</option>").appendTo("#month");
        }else {
            $("<option value="+valueM+">"+i+"月</option>").appendTo("#month");
        }

    }
}

function gradeChange(){
	var khlx=$("#khlx").val();
	if(khlx==1){
		$('#year').css('display','block');
		$('#jd').css('display','none');
		$('#month').css('display','none');
		$('#date').css('display','none');
		$('#startDate').css('display','none');
		$('#endDate').css('display','none');
	}else if(khlx==2){
		$('#year').css('display','block');
		$('#jd').css('display','block');
		$('#month').css('display','none');
		$('#date').css('display','none');
		$('#startDate').css('display','none');
		$('#endDate').css('display','none');
	}else if(khlx==3){
		$('#year').css('display','block');
		$('#jd').css('display','none');
		$('#month').css('display','block');
		$('#date').css('display','none');
		$('#startDate').css('display','none');
		$('#endDate').css('display','none');
	}else{
		$('#year').css('display','none');
		$('#jd').css('display','none');
		$('#month').css('display','none');
		$('#date').css('display','block');
		$('#startDate').css('display','block');
		$('#endDate').css('display','block');
	}
}

var isSubmit = false;//防止重复提交
var save=function() {
	if(isSubmit){
		return;
	}
	isSubmit = true;
	var ck=$('#ck').val();
	var khmc=$('#khmc').val();
	if(khmc==""){
		 art.dialog.alert('请填写考核名称！');
		 document.getElementById("khmcts").style.display="block";
		 isSubmit = false;
		 return;
	}
	var khlx=$('#khlx').val();
	var khsj="";
	var khjssj="";
	var d = new Date();
	var st=d.getFullYear();
	if(khlx==1){
		khsj=$('#year').val()+"-01-01";
	}else if(khlx==2){
		var jd=$('#jd').val();
		if(jd==1){
			khsj=$('#year').val()+"-01-01";
		}else if(jd==2){
			khsj=$('#year').val()+"-04-01";
		}else if(jd==3){
			khsj=$('#year').val()+"-07-01";
		}else{
			khsj=$('#year').val()+"-10-01";
		}
	}else if(khlx==3){
		if($('#month').val()<10){
			khsj=$('#year').val()+"-0"+$('#month').val()+"-01";
		}else{
			khsj=$('#year').val()+"-"+$('#month').val()+"-01";
		}
	}else{
		khsj=$('#startDate').val();
		khjssj=$('#endDate').val();
	}
	var ms=$('#ms').val();
	var zszp=$("input[name='is_zishu']:checked").val();
	var nm=$("input[name='is_noname']:checked").val();
	var paramData = {
			"at.khmc":khmc,
			"at.khlx":khlx,
			"at.khsj":khsj,
			"at.khjssj":khjssj,
			"at.ms":ms,
			"at.zszp":zszp,
			"at.nm":nm,
			"at.vid":vid				
	};
	$.ajax({
	    url : basePath + "performance/task_addAppraisalTask.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data&&data!=0) {
//			    art.dialog.alert('成功！')
		    	var url=basePath+"logined/appraisal/jsp/admin/add_assessment.jsp?vid="+data+"&ck_vid="+ck+"&type="+type;
		    	window.location.href=url;
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    	isSubmit = false;
		    }
	    }
	});
	return false;
};
var getOne=function(vid,ck_vid) {
	var paramData = {"at.vid":vid};
	$.ajax({
	    url : basePath + "performance/task_findTask.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	$('#ck').val(ck_vid);
		    	$('#khmc').val(data.khmc);
		    	$('#khlx').val(data.khlx);
		    	var khlx=data.khlx;
		    	if(khlx==1){
		    		$('#year').css('display','block');
		    		$('#jd').css('display','none');
		    		$('#month').css('display','none');
		    		$('#date').css('display','none');
		    		$('#startDate').css('display','none');
		    		$('#endDate').css('display','none');
		    	}else if(khlx==2){
		    		$('#year').css('display','block');
		    		$('#jd').css('display','block');
		    		$('#month').css('display','none');
		    		$('#date').css('display','none');
		    		$('#startDate').css('display','none');
		    		$('#endDate').css('display','none');
		    	}else if(khlx==3){
		    		$('#year').css('display','block');
		    		$('#jd').css('display','none');
		    		$('#month').css('display','block');
		    		$('#date').css('display','none');
		    		$('#startDate').css('display','none');
		    		$('#endDate').css('display','none');
		    	}else{
		    		$('#year').css('display','none');
		    		$('#jd').css('display','none');
		    		$('#month').css('display','none');
		    		$('#date').css('display','block');
		    		$('#startDate').css('display','block');
		    		$('#endDate').css('display','block');
		    	}
		    	var str = data.khsjStr.substr(0, 4);
				$('#year').val(str);
				var mon = data.khsjStr.substr(5, 2);//2012-10-10
				if(parseInt(mon)<4&&parseInt(mon)>0){
					$('#jd').val("1");
				}else if(parseInt(mon)<7&&parseInt(mon)>3){
					$('#jd').val("2");
				}else if(parseInt(mon)<10&&parseInt(mon)>6){
					$('#jd').val("3");
				}else{
					$('#jd').val("4");
				}
				$('#month').val(mon);
				$('#startDate').val(data.khsjStr);
				$('#endDate').val(data.khjssjStr);				
		    	$('#ms').val(data.ms);
		    	if(data.zszp==1){
		            $("input[name='is_zishu']").eq(0).attr("checked","checked");
		            $("input[name='is_zishu']").eq(1).removeAttr("checked");
		    	}else{
		            $("input[name='is_zishu']").eq(1).attr("checked","checked");
		            $("input[name='is_zishu']").eq(0).removeAttr("checked");
		    	}
		    	if(data.nm==1){
		            $("input[name='is_noname']").eq(0).attr("checked","checked");
		            $("input[name='is_noname']").eq(1).removeAttr("checked");
		    	}else{
		            $("input[name='is_noname']").eq(1).attr("checked","checked");
		            $("input[name='is_noname']").eq(0).removeAttr("checked");
		    	}
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};
function toClear() {
		document.getElementById("khmcts").style.display="none";
    	$('#ck').val("");
    	$('#khmc').val("");
    	$('#khlx').val("1");
		$('#year').css('display','block');
		$('#jd').css('display','none');
		$('#month').css('display','none');
		$('#date').css('display','none');
		$('#startDate').css('display','none');
		$('#endDate').css('display','none');
		var d = new Date();
		var st=d.getFullYear();
		$('#year').val(st);
		var mon = d.getMonth()+1;
		if(mon<4&&mon>0){
			$("#jd").val("1");
		}else if(mon<7&&mon>3){
			$("#jd").val("2");
		}else if(mon<10&&mon>6){
			$("#jd").val("3");
		}else{
			$("#jd").val("4");
		}
		$('#jd').val("1");
		$('#month').val(mon);
		var st=d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate();
		var et=d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();
		$("#startDate").val(st);
		$("#endDate").val(et);		
    	$('#ms').val("");
        $("input[name='is_zishu']").eq(0).attr("checked","checked");
        $("input[name='is_zishu']").eq(1).removeAttr("checked");
        $("input[name='is_noname']").eq(0).attr("checked","checked");
        $("input[name='is_noname']").eq(1).removeAttr("checked");
};
function goHome() {
	var url=basePath+"logined/appraisal/jsp/admin/adminHome.jsp";
	window.location.href=url;
}