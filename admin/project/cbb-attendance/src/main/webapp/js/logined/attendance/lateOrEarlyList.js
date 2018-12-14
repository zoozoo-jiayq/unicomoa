$(function(){
	getlateOrEarlyList();
});

function getlateOrEarlyList(){
	var url = basePath+"attendance/statistics_lateOrEarlyList.action";
	var userId = $("#userId").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var attState = $("#attState").val();
	var param = {"userId":userId,"startTime":startTime,"endTime":endTime,"attState":attState};
	$.ajax({
		url:url,
		type:'POST',
		data:param,
		dataType:'json',
		success:function(data){
			var html = "";
			if(data){
				var size = data.length;
				for(var i = 0;i < size;i++){
					var lateOrEarly = data[i];
					if(i%2==0){
						html += "<tr class='even'>";
					}else{
						html += "<tr class='odd'>";
					}
					html += "<td class='tdCenter' >"+lateOrEarly.recordDate+"</td><td class='tdCenter' >"+lateOrEarly.recordTime+"</td><td>"+lateOrEarly.timeLong+"</td><td>"+lateOrEarly.attendTime+"</td></tr>";
				}
			}else{
				html = "<tr><td colspan='4'>没有检索到数据</td></tr>";
			}
			$("#lateOrEarlyBody").html(html);
		}
	});
}


