//得到今日明日Map,依赖map.js,jQuery.timeDate.js
function getNowDateMap(){
	var nowDate = new Date(); 
	var nextDate = new Date(); 
	nextDate.setTime(nextDate.getTime()+1000*60*60*24);
	var dateMap=new Map();
	dateMap.put($.formatDate("yyyy-MM-dd",nowDate),"今天");
	dateMap.put($.formatDate("yyyy-MM-dd",nextDate),"明天");
	dateMap.put("今天",$.formatDate("yyyy-MM-dd",nowDate));
	dateMap.put("明天",$.formatDate("yyyy-MM-dd",nextDate));
	return dateMap;
}