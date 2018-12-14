<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
var userId = ${adminUser.userId};
var companyId = ${adminUser.companyId};
var appId = "tyoa";//应用标志，必填
var appVersion = "3.5.6";//应用版本，非必填
var key = window.location.href;
var browser = getBrowserInfo() ;
var verinfo = (browser+"").replace(/[^0-9.]/ig,""); 

var param = {
				"userId":userId.toString(),
				"companyId":companyId.toString(),
				"appId":appId,
				"appVersion":appVersion,
				"keyType":"userActionKey",
				"key":key,
				"systemType":"windows"
			};
var arr = [];
arr.push(param);
$.post("http://10.200.12.197:9988/behavior/ClientServlet",{"data":JSON.stringify(arr)});
function getBrowserInfo(){
	var agent = navigator.userAgent.toLowerCase() ;
	var regStr_ie = /msie [\d.]+;/gi ;
	var regStr_ff = /firefox\/[\d.]+/gi
	var regStr_chrome = /chrome\/[\d.]+/gi ;
	var regStr_saf = /safari\/[\d.]+/gi ;
	//IE
	if(agent.indexOf("msie") > 0)
	{
		return agent.match(regStr_ie) ;
	}
	
	//firefox
	if(agent.indexOf("firefox") > 0)
	{
		return agent.match(regStr_ff) ;
	}
	
	//Chrome
	if(agent.indexOf("chrome") > 0)
	{
		return agent.match(regStr_chrome) ;
	}
	
	//Safari
	if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0)
	{
		return agent.match(regStr_saf) ;
	}
}
</script>
