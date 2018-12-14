var ntkoobj = null;
var errMes = "NTKO WebSignHelper控件没有装载！";
var WebSignInfo =  "姓名=name;部门=group;职务=job";
function initOcx()
{
	ntkoobj = document.getElementById("ntkoocx");
	if(!ntkoobj)
	{
		// alert("NTKO WebSignHelper 控件初始化失败！您尚未安装签章所需控件。请点击确定" +
		// "\n关闭本对话框之后，单击浏览器上方的阻止工具条安装控件。");
		return;
	}
	var documentExtId=$("#documentExtId").val();
	if(!documentExtId){
		return;
	}
	 var paramData={
				"documentExtId":documentExtId
		 };
		$.ajax({
		      url:basePath+"ntko/webSign_getContent.action",
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	     if(data=="-1"){
		    	     }else{
		    	    	ntkoobj.LoadFromURL(basePath+"ntko/webSign_getContent.action?documentExtId="+documentExtId);
		    	     }
		    	}
		 }); 
}

function saveSign(){
	try{
	   var retstr = ntkoobj.SaveToURL(
			document.forms[0].action,
			"SIGNSFILE",
			"",
			"ntkowebsigns.info",
			0
		);
   }catch(e){
		
	}
}


function saveSignByForm(formId){
	if(ntkoobj){
	   var retstr = ntkoobj.SaveToURL(
			document.getElementById(formId).action,
			"SIGNSFILE",
			"",
			"ntkowebsigns.info",
			0
		);
	}
}
/**
 * 添加印章
 */
function addYZ(webSignUrl){
	var secSignObj = ntkoobj.AddSecSignOcx("SecSignFromURLID",100,10);
	secSignObj.SetPrintMode(2);
	secSignObj.WebSignInfo = WebSignInfo;
	if(webSignUrl==""){
		art.dialog.alert("请选择印章！");
	}else{
		ntkoobj.AddSecSignFromURL(secSignObj,'管理员',basePath+"upload/webSign/"+webSignUrl);
	}
} 
/**
 * 添加印章
 */
function addYZFromEkey(){
	var signFlag = $("#signFlag").val();
	if(signFlag == 1){
		var secSignObj = ntkoobj.AddSecSignOcx("SecSignFromEkeyID",100,100);
		//secSignObj.SetPrintMode(2);
		secSignObj.WebSignInfo = WebSignInfo;
		var currentUser = document.getElementById("currentSignUser").value;
	//	ntkoobj.AddSecSignFromEkey(secSignObj,currentUser,2);
		ntkoobj.AddSecSignFromEkey(secSignObj,currentUser,2,false,false,true,true,null,true,-1/*SignIndex*/);
	}else{
		art.dialog.alert("印章控件未启用!");
	}
}
