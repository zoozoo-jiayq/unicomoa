$(function(){
	getLeaveList();
});

function getLeaveList(){
	var url = basePath+"attendance/statistics_leaveList.action";
	var userId = $("#userId").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var type = $("#type").val();
	var param = {"userId":userId,"startTime":startTime,"endTime":endTime,"type":type};
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
					var leave = data[i];
					if(i%2==0){
						html += "<tr class='even'>";
					}else{
						html += "<tr class='odd'>";
					}
					html += "<td class='longTxt' title='"+leave.title+"'>"+leave.title+"</td><td>"+leave.hour+"</td><td>"+leave.dayNum+"</td><td>"+leave.startLeaveTime+"到"+leave.endLeaveTime+"</td></tr>";
				}
			}else{
				html = "<tr><td colspan='4'>没有检索到数据</td></tr>";
			}
			$("#leaveBody").html(html);
		}
	});
}


