$(document).ready(function () {
	getList();
});
$(function() {
	/*var tableH=$(window).height()-140-51-61;
	$(".dc_index_right").css("height",tableH);*/	
	// 给左侧列表加高度
	/*var dcH=$(window).height()-91-17-51-5;*/
	var dcH=$(window).height()-91-39;
	$(".dc_notice").css("height",dcH);
	//	左侧列表加固定高度出现滚动条
	$(".notice_list").css("height",dcH-91);
	//右侧内容区域高度
	var dcRightH=dcH-51;
	$(".dc_index_right").css("height",dcRightH);	
	
	/*给表格外面的div加高度，超出出现滚动条*/
	var tableH=dcRightH/2-25-25;	
	$(".index_table_box").css("height",tableH);
})

var getList=function() {
	var paramData = {};
	$.ajax({
	    url : basePath + "performance/task_findTaskList.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！');
		    	var html = '';
		    	if(data.aaData.length!=0){
		    		for(var i=0;i<data.aaData.length;i++){
			    		if(i%2==0){
			    			html +="<tr class='odd'>";
			    		}else{
			    			html +="<tr class='even'>";
			    		}
				    	html +="<td class='wd300 text_hide text_left'  title='"+data.aaData[i].khmc+"' >"+data.aaData[i].khmc+"</td>"
						+"<td class='wd150 text_hide'>"+data.aaData[i].khjd+"</td>";				    	
						//html +="<td><a class='enter_color' href='"+basePath+"logined/appraisal/jsp/admin/main.jsp?tid="+data.aaData[i].vid+"'>进入</a></td>"
				    	html +="<td class='wd100 text_hide'><a class='enter_color' href='"+basePath+"logined/appraisal/jsp/admin/main.jsp?tid="+data.aaData[i].vid+"&type=1'>进入</a></td>"
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
};
var goCreate=function() {
	var url=basePath+"logined/appraisal/jsp/admin/add_createNewCheck.jsp?type=0,0,0,0";
	window.location.href=url;
}

var goTemplate=function(){
	var url=basePath+"logined/appraisal/jsp/template/left_template.jsp";
	window.location.href=url;	
}
