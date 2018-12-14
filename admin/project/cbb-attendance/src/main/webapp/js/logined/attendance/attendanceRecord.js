var _iDisplayStart=0; //显示开始位置
var _iDisplayLength = 7; // 每页显示条数 
 
/**
 * 页面加载
 */
$(document).ready(function() {
	//-获取考勤记录
	getAttendanceRecode();
	 
	 
});

//跳转页
function getPage(page){
	if(page){
		_iDisplayStart = _iDisplayLength*page-1;
		//-获取考勤记录
		getAttendanceRecode();
	}
}

/**
 * 获取考勤记录
 */
function getAttendanceRecode(){
	
	var userId = $("#userId").val();
	var beginT=$("#beginT").val();
	var endT=$("#endT").val();
	var paramData = {
			"userId":userId,
			"beginT":beginT,
			"endT":endT,
			"iDisplayStart":_iDisplayStart,//显示开始位置
			"iDisplayLength":_iDisplayLength // 每页显示条数 
	};
	var url=basePath + "attendance/getRecode.action";
	qytx.app.ajax({
		url : url,
		type : "post",
		data : paramData,
		dataType : "json",
		success : function(data) {	
			 var contents= data.contents;
			 var pageHtml= data.pageHtml;
			 $("#pageFoot").html(pageHtml);
			 $("#contents").html(contents);
	    }
	});
	
}

 
