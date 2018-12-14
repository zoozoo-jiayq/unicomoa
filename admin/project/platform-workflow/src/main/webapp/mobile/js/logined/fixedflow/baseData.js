mui.plusReady(function(){
//	var baseUrl=window.plus.qytxplugin.getBaseUrlPath("h5baseUrl","workflowUrl");
//	var baseUrl="http://218.206.244.202:38080/";
	var baseUrl="http://101.200.31.143/";
	var basePath=baseUrl+"txzlbmc/";
	var photoUrl=baseUrl+"ydzjMobile/headPictureView.action?_clientType=wap&filePath=";
	var approveLoginId="";
	var approveLoginName="王红军";
//	var approveLoginName="洪亚勤";
//	var approveLoginName="任鹏辉";
	var approveLoginGroupId="";   
//	approveLoginId="29221016";
      approveLoginId="29115650";
//	approveLoginId="29221065";  
	approveLoginId="29220911";
//	approveLoginId="29220905";
	
	

	//获取登录人员信息
//	var dataJson=plus.qytxplugin.getLoginUserInfo();
//	if(dataJson){
//		var data="";
//		if(typeof(dataJson)=="string"){
//			data=JSON.parse(dataJson);
//		}else{
//			data=dataJson;
//		}
//		approveLoginId=data.userId;
//		approveLoginName=data.userName;
//		approveLoginGroupId=data.groupId;
//	}
	plus.storage.setItem("baseUrl",baseUrl);
	plus.storage.setItem("basePath",basePath);
	plus.storage.setItem("photoUrl",photoUrl);
	plus.storage.setItem("approveLoginId",approveLoginId+"");
	plus.storage.setItem("approveLoginName",approveLoginName);
	plus.storage.setItem("approveLoginGroupId",approveLoginGroupId);
});