var vid="";
var ck_vid="";
var type="";
var hid="";
var yhid="";
$(document).ready(function () {
	vid=$("#vid").val();
	ck_vid=$("#ck_vid").val();
	type=$("#type").val();
	getGrader();
});
var getGrader=function() {
	var paramData = {};
	$.ajax({
	    url : basePath + "performance/aht_findAll.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	var text="";
	    		var types = type.split(",");
	    		if(types[3]==1){
	    			text+="<option value=''>请选择</option>";	
	    			for(var i=0;data.length>i;i++){
			    		text+="<option value='"+data[i].vid+"'>"+data[i].fjmc+"</option>";		 
			    	}
			    	$('#khfj').append(text);
			    	getOne();
	    		}else if(types[2]==1&&ck_vid!=""){
	    			text+="<option value=''>请选择</option>";	
	    			for(var i=0;data.length>i;i++){
			    		text+="<option value='"+data[i].vid+"'>"+data[i].fjmc+"</option>";		 
			    	}
			    	$('#khfj').append(text);
			    	getOne();
	    		}else{
			    	for(var i=0;data.length>i;i++){
			    		text+="<option value='"+data[i].vid+"'>"+data[i].fjmc+"</option>";		 
			    	}
			    	$('#khfj').append(text);
			    	gradeChange();
	    		}

		    } else {
		    	art.dialog.alert("获取考核级别信息失败！");
		    }
	    }
	});
	return false;
};
function gradeChange(){
	var hhid="";
	//alert((typeof(yhid)!="undefined")&&yhid!=""&&((typeof($("#khfj").val())=="undefined")||$("#khfj").val()==""));
	if(yhid&&yhid!=""&&(!($("#khfj").val())||$("#khfj").val()=="")){
		hhid=yhid;
	}else{
		hhid=$("#khfj").val();
	}
	var paramData = {
			"vid":hhid
			};
	$.ajax({
	    url : basePath + "performance/aht_findByVid.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
		    	//alert(JSON.stringify(data))
//			    art.dialog.alert('成功！')
		    	$("#fjms").text(data.fjms);
		    	$("#firstTr").html("");
		    	var html = '';
		    	if(data.ahiList.length!=0){
		    		for(var i=0;i<data.ahiList.length;i++){
		    			var jb=i+1;
				    	html +="<tr><td>"+jb+"</td>"
						+"<td>"+data.ahiList[i].mc+"</td>"
						+"<td>"+data.ahiList[i].qsf+"-"+data.ahiList[i].jsf+"</td>"
						+"</tr>";
			    	}
		    	}else{
		    		html ="<tr><td colspan='3'>暂无数据</td></tr>";
		    	}		    	
		    	$("#firstTr").append(html);
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
}
function toClear(){
	$("#khfj option:first").prop("selected", 'selected');
	gradeChange();

}
function goBack(){
    var url = basePath + "logined/appraisal/jsp/admin/add_relationship.jsp?vid=" + vid + "&ck_vid=" + ck_vid + "&type=" + type;
    window.location.href = url;
}

var isSubmit = false;//防止重复提交
function save(){
	if(isSubmit){
		return;
	}
	isSubmit = true;

	var hidd=$("#khfj").val();
	if(hidd==""){
		if(yhid&&yhid!=""){
			hidd=yhid;
		}else{
			 art.dialog.alert('请选择考核分级！')
			 isSubmit = false;
			 return;
		}
	}
	var paramData = {
			"at.vid":vid,
			"at.hid":hidd
	};
	$.ajax({
	    url : basePath + "performance/task_updateHierarchicalIndicators.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data==1) {
//			    art.dialog.alert('成功！')
		    	var types = type.split(",");
		    	if(types[3]==1){
			    	art.dialog.alert("考核修改成功!",function () {
				    	var url=basePath+"performance/welcome.action";
				    	window.location.href=url;
			    	})
		    	}else{
			    	art.dialog.alert("考核创建成功!",function () {
				    	var url=basePath+"performance/welcome.action";
				    	window.location.href=url;
			    	})
		    	}
		    	type="1,1,1,1";
		    } else {
		    	art.dialog.alert("考核创建失败!");
		    	isSubmit = false;
		    }
	    }
	});
	return false;
};
var getOne=function() {
	var types = type.split(",");
	var atVid="";
	if(types[2]==1&&ck_vid!=""){
		atVid=ck_vid;
	}else{
		atVid=vid;
	}
	var paramData = {
			"at.vid":atVid
	};
	$.ajax({
	    url : basePath + "performance/task_findTask.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	if(data.hid){
		    		yhid=data.hid;
			    	gradeChange();
		    	}
		    } else {
		    	art.dialog.alert("获取考核信息失败！");
		    }
	    }
	});
	return false;
};