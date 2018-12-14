/**
 * desktop kp
 */

 var deskmap = new Map();
 var _Kp_iDisplayLength = 7;
 var _Kp_iDisplayLengthMax = 10;
 var _canPu=false;//是否可以打卡
$(document).ready(function () {
	var _moduleList = $("#_moduleList").val();
	if(_moduleList){
		var marr  = _moduleList.split(",");
		for(var i=0;i<marr.length-1;i++){
			var module = marr[i];
			if(module){
				deskmap.put(module,module);
			}
		}
		
	}
	
	//控制我的审批
	checkWdsp();
	//控制收文阅读
	checkSwyd();
 
	//控制我的日志
 	checkDaily();
	//控制公告查看
	var columnId = 35; //对应公告ID
	checkGgck(columnId);
	//我的任务
    checkTask();
    //问卷列表
    checkQuestion();
    //资料查询
    checkFiles();
    //打卡------------------------
    //动态的获取时间
    if(deskmap.get("考勤登记")){
		$("#attendanceMore").click(function(){
			moreAttendance(); 
		});
	}else{
		_canPu = false;
		$("div").unbind("click"); 
		$("#click").attr("class","dk_btn nodk");
	}
	setInterval(getNowTime,60000);
	getNowTime();
	getServiceTime();
	//事务提醒
	setInterval(getAffairsCount,60000);
	getAffairsCount();
	//公司理念
	getCompanyPhilosophy();	 
});
/**
 * 从新计算高度
 */
function reSetItemList(){
    $('.item_list').masonry({ 
		itemSelector: '.item',
		columnWidth:380,
		gutterWidth:10								
	});
}

/**
 * 打卡新链接
 * @param title
 * @param url
 */
function openUrl(title,url,tabId){
	url = basePath+url;
	window.parent.addTab(tabId,url,title,true,"");
}

//控制我的审批
function checkWdsp(){
	
	if(deskmap.get("待审批")){
		$.ajax({
			url : basePath + "logined/desktop/deskka_approveCount.action",
			type : "post",
			dataType : "json",
			success : function(data) {
					if(data){
					    //工作流
						if(data.jbpmWaitSize){
							if(data.jbpmWaitSize>0){
								$("#jbpmWaitSize").click(function(){
									openUrl("待审批","logined/jbpmApp/myjob/management.jsp","dsp");
								});
							}
							$("#jbpmWaitSize").html(data.jbpmWaitSize);
						}
						if(data.jbpmApproveSize){
							if(data.jbpmApproveSize>0){
								$("#jbpmApproveSize").click(function(){
									openUrl("已审批","logined/jbpmApp/myjob/completed.jsp","ysp");
								});
							}
							$("#jbpmApproveSize").html(data.jbpmApproveSize);
						}
						
					} 
			  }
	    });
	}
	
	
}
//控制收文阅读
function checkSwyd(){
	if(deskmap.get("收文阅读")){
		 
	}
}
/** 控制公告查看--------BEGIN+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
function checkGgck(columnId){
	if(deskmap.get("公告查看")){
		$("#notifyMore").click(function(){
			moreGg();
		});
		$("#notifyItem").show();
		$("#notifyItem").addClass("item");
	   var paramData = {
				'columnId' : columnId,
				'notifyType':0,
				'iDisplayLength':_Kp_iDisplayLength
	    };
	  $.ajax({
			url : basePath + "notify/notify_viewList.action",
			type : "post",
			data : paramData,
			dataType : "json",
			success : function(data) {
					if(data&&data.aaData.length>0){
						var html ="<ul class=\"news\">";
						for(var i=0;i<data.aaData.length;i++){
							var approveDate = data.aaData[i].approveDate;
							 if(approveDate){
								 if(approveDate.length>12){
									 approveDate = approveDate.substring(0,10);
								 }
							 }
							html +="<li><a href='javascript:detailGg("+data.aaData[i].notifyId+");'>"+data.aaData[i].subject;
							html +="<span class=\"time\">"+approveDate;
							html +="</span></a></li>";
						}
						html +="</ul>";
						$("#notifyViewDiv").html(html);
					}else{
						$("#notifyViewDiv").html("暂无数据");
					}
					reSetItemList();	
			  	}
		   });
		} 
}
//公告详情
function detailGg(id){
	var tabId =getRandomTabId();
	var url = basePath+"logined/notify/view.jsp?notifyId="+ id +"&columnId=35&returnType=1";
	window.parent.addTab(tabId,url,"公告详情",true,"");
}
//更多公告
function moreGg(){
	var tabId ="ggck";
	var url = basePath+"/logined/notify/viewList.jsp?id=35";
	window.parent.addTab(tabId,url,"公告查看",true,"");
}
/** 控制公告查看-------END++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

/** 工作日志+++++++BEGIN++++++++++++++++++++++++++++++++++++++++++++++ */
//工作日志列表
function checkDaily(){
	if(deskmap.get("我的日志")){
		$("#dailyMore").click(function(){
			moreDaily(); 
		});
		
		$("#dailyItem").show();
		$("#dailyItem").addClass("item");
		var userId = $("#userId").val();
		 var paramData = {
					'dailySearch.searchType' : 1,
					'notifyType':0,
					'dailySearch.userId':userId,
					'iDisplayLength':_Kp_iDisplayLength
		    };
		 $.ajax({
				url : basePath + "daily/daily_getDailyList.action",
				type : "post",
				data : paramData,
				dataType : "json",
				success : function(data) {
						if(data&&data.aaData.length>0){
							 var html ="<ul class=\"news\">";
							 for(var i=0;i<data.aaData.length;i++){
								 var dailyTime = data.aaData[i].dailyTime;
								 if(dailyTime){
									 if(dailyTime.length>12){
										 dailyTime = dailyTime.substring(0,10);
									 }
								 }
								 var content = data.aaData[i].content;
								 content = content.replace(/[\r\n]/g,"");//去掉回车换行
							 	html +="<li><a href='javascript:void(0);' onclick=\"detailDaily("+data.aaData[i].id+");\">"+content;
							 	html +="<span class=\"time\">"+dailyTime;
							 	html +="</span></a></li>";
							 }
							 html +="</ul>";
							 $("#dailyViewDiv").html(html);
						}else{
							$("#notifyViewDiv").html("暂无数据");
						}
						reSetItemList();		
				  	}
			   });
		
	}
}
/**
 * 日志详情
 * @param dailyId
 */
function detailDaily(dailyId){
	var tabId =getRandomTabId();
	var url = basePath+"daily/toDailyMain.action?redictType=listEdit&&type=1&dailyId="+dailyId+"&isShare=1";
	window.parent.addTab(tabId,url,"日志详情",true,"");
}
/**
 * 更多日志
 */
function moreDaily(){
	var tabId ="wdrz1";;
	var url = basePath+"daily/toSearch.action?type=1";
	window.parent.addTab(tabId,url,"我的日志",true,"");
}

/** 工作日志+++++++END++++++++++++++++++++++++++++++++++++++++++++++ */

/** 任务管理 +++++++BEGIN++++++++++++++++++++++++++++++++++++++++++++++ */

//工作日志列表
function checkTask(){
	if(deskmap.get("我发起的")){
		$("#taskMore").click(function(){
			moreTask(); 
		});
		$("#taskItem").show();
		$("#taskItem").addClass("item");
		 var paramData = {
					'type' : 2,
					'iDisplayLength':_Kp_iDisplayLength
		    };
		 $.ajax({
				url : basePath + "task/taskList.action",
				type : "post",
				data : paramData,
				dataType : "json",
				success : function(data) {
						if(data&&data.aaData.length>0){
							var html ="<ul class=\"news\">";
							 for(var i=0;i<data.aaData.length;i++){
								 var startTime = data.aaData[i].startTime;
								 if(startTime){
									 if(startTime.length>12){
										 startTime = startTime.substring(0,10);
									 }
								 }
							 	html +="<li><a href='javascript:void(0);' onclick=\"detailTask("+data.aaData[i].id+");\"  >"+data.aaData[i].taskName;
							 	html +="<span class=\"time\">"+startTime;
							 	html +="</span></a></li>";
							 }
							 html +="</ul>";
							 $("#taskViewDiv").html(html);
						}else{
							 $("#taskViewDiv").html("暂无数据");
						}
						reSetItemList();		
				  	}
			   });
	}
}

/**
 * 处理任务
 * @param taskId
 */
function detailTask(taskId){
	var tabId =getRandomTabId();
	var url = basePath+"logined/task/handle.jsp?taskId="+taskId;
	window.parent.addTab(tabId,url,"任务办理",true,"");
}
/**
 * 更多任务
 */
function moreTask(){
	var tabId ="moreTask";
	var url = basePath+"logined/task/unfinishedList.jsp";
	window.parent.addTab(tabId,url,"我的任务",true,"");
}
/** 任务管理 +++++++END++++++++++++++++++++++++++++++++++++++++++++++ */


/** 问卷 +++++++begin=============================================== */

//问卷列表
function checkQuestion(){
	if(deskmap.get("问卷列表")){
		$("#questionMore").click(function(){
			moreQuestion(); 
		});
		 
		$("#questionItem").show();
		$("#questionItem").addClass("item");
		var paramData = {
				'iDisplayLength':_Kp_iDisplayLength
	    };
		 $.ajax({
				url : basePath + "question/questionnaire_issueList.action",
				type : "post",
				data:paramData,
				dataType : "json",
				success : function(data) {
						if(data&&data.aaData.length>0){
							var html ="<ul class=\"news\">";
							 for(var i=0;i<data.aaData.length;i++){
								    var jiang = data.aaData[i].jiang;
									var template = "";
									if(jiang==true){
										template = '<img src="../../images/praise_icon.png">';
									}
									 var state = data.aaData[i].state;
								     var endTime="";
							 		 if(data.aaData[i].endTime==null||data.aaData[i].endTime==""){
							 			endTime= '--';
									 }else{
										 endTime= data.aaData[i].endTime.substring(0,10);
									 }
							 	html +="<li><a href='javascript:void(0);' onclick=\"detailQuestion("+data.aaData[i].id+","+state+");\"  >"+data.aaData[i].title+template;
							 	html +="<span class=\"time\">"+endTime;
							 	html +="</span></a></li>";
							 }
							 html +="</ul>";
							 $("#questionViewDiv").html(html);
						}else{
							 $("#questionViewDiv").html("暂无数据");
						}
						reSetItemList();		
				  	}
			   });
	}
}
/**
 * 处理问卷
 * @param taskId
 */
function detailQuestion(questionId,state){
	var tabId =getRandomTabId();
	var url="";
	var title="问卷查看";
	if(state==1){
		url =basePath+"question/question_answerQuestion.action?questionnareId="+questionId;
		title="问卷答题";
 	}else{
 		url =basePath+"question/question_lookQuestion.action?questionnareId="+questionId;
 	}
	window.parent.addTab(tabId,url,title,true,"");
}
/**
  更多问卷 
*/
function moreQuestion(){
		 	var tabId ="moreQuestion";
		 	var url = basePath+"logined/question/issueList.jsp";
		 	window.parent.addTab(tabId,url,"问卷列表",true,"");
}
/** 问卷 +++++++END++++++++++++++++++++++++++++++++++++++++++++++ */




/** 资料查询 +++++++begin=============================================== */

//资料查询
function checkFiles(){
	if(deskmap.get("资料查阅")){
		$("#filesMore").click(function(){
			moreFiles(); 
		});
		
		var paramData = {
				'iDisplayLength':_Kp_iDisplayLength
	    };
		 $.ajax({
				url : basePath + "file/findAllFileContents.action",
				type : "post",
				data:paramData,
				dataType : "json",
				success : function(data) {
						if(data&&data.aaData.length>0){
							var html ="<ul class=\"news\">";
							 for(var i=0;i<data.aaData.length;i++){
									 var attName = data.aaData[i].attName;
									 var attId = data.aaData[i].attId;
									 attId = attId.substring(0,attId.length-1);
									 attName = attName.substring(0,attName.length-1);
									 var attFileType = attName.substring(attName.lastIndexOf("."));
								     var cls = getClassByFileType(attFileType);
								     var createTime="";
							 		 if(data.aaData[i].createTime==null||data.aaData[i].createTime==""){
							 			createTime= '--';
									 }else{
										 createTime= data.aaData[i].createTime.substring(0,10);
									 }
							 	html +="<li><a href='javascript:void(0);' class='"+cls+"' onclick=\"detailFiles("+attId+");\"  >"+attName;
							 	html +="<span class=\"time\">"+createTime;
							 	html +="</span></a></li>";
							 }
							 html +="</ul>";
							 $("#filesViewDiv").html(html);
						}else{
							 $("#filesViewDiv").html("暂无数据");
						}
						reSetItemList();		
				  	}
			   });
	}else{
		$("#filesViewDiv").html("暂无数据");
	}
}
/**
* 文件下载
* @param filesId
*/
function detailFiles(filesId){
	var tabId =getRandomTabId();
	var url="";
	url =basePath+"filemanager/downfile.action?attachmentId="+filesId;
	window.open(url);
}
/**
更多问卷 
*/
function moreFiles(){
		 	var tabId ="moreFiles";
		 	var url = basePath+"logined/file/viewFileContent.jsp?fileSort=28";
		 	window.parent.addTab(tabId,url,"资料查阅",true,"");
}
/** 问卷 +++++++END++++++++++++++++++++++++++++++++++++++++++++++ */

/** 打卡 +++++++BEGIN============================ */
/**
 * 打卡
 */
function punchCard(){
	//打卡完先置为disabled
	if(_canPu){
		qytx.app.dialog.confirm("确认要打卡吗？",function(){
			 $("div").unbind("click"); 
			 $("#click").attr("class","dk_btn nodk");
			 _canPu= false;
			qytx.app.ajax({
				url :basePath + "attendance/punchCard.action" ,
				type : "post",
				success : function(data) {
					if(data=="1"){
						 qytx.app.dialog.tips("打卡成功");
					}else if(data=="0"){
						$("#click").click(function(){
							punchCard();}
						);
						$("#click").attr("class","dk_btn");
						_canPu = true;
						qytx.app.dialog.tips("不在打卡范围内!");
					}else{
						qytx.app.dialog.tips("打卡失败!");
						$("#click").click(function(){
							punchCard();}
						);
						$("#click").attr("class","dk_btn");
						_canPu = true;
					}
				   //读取记录
				   loadRecords();
				}
			});
		});
	}
}


/**
 * 读取记录
 */
function loadRecords(){
	var timeStr = $("#day").val();
	var paramData = {
			"day":timeStr
	};
	var url=basePath + "attendance/punchReportInfo.action";
	qytx.app.ajax({
		url : url,
		type : "post",
		data : paramData,
		dataType : "json",
		success : function(data) {	
			 var showAttHtml="";
			 var attList = data.attList;
			 if(attList){
				 for(var i=0;i<attList.length;i++){
					 var att = attList[i];
					 var position = att.position;
					 var createTime = att.createTime;
					 showAttHtml+="<dd><span class=\"time\">";
					 showAttHtml+=createTime;
					 showAttHtml+="</span><span class=\"num\">";
					 showAttHtml+="第"+(i+1)+"次打卡";
					 showAttHtml+="</span><span class=\"add\">";
					 showAttHtml+=position;
					 showAttHtml+="</span></dd>";
				 }
			 }
			$("#punchReportDiv").html(showAttHtml); 
			$("#lastTime").val(data.days);
			reSetItemList();	
	  }
	});
}

/**
 * 动态获取时间
 */
function getNowTime(){
			var lastTime = $("#lastTime").val();
			var limitTime=10;
			var paramData = {
					"lastTime":lastTime,
					"limitTime":limitTime
			};
			qytx.app.ajax({
				url :basePath + "attendance/getServiceTime.action" ,
				type : "post",
				data : paramData,
				dataType : "json",
				success : function(data) {
				    if(data){
				    	var data=data.split(">");	
						var nowTime=data[4];
						var nowTime_s=data[5];
						$("#nowTime").html(nowTime);
						if(data[6]=='0'){
							 //$("#click").attr("disabled","disabled");
							 //点击打卡
							 $("div").unbind("click"); 
							 $("#click").attr("class","dk_btn nodk");
							 _canPu = false;
						}else{
							if(deskmap.get("考勤登记")){
								//点击打卡
								$("#click").click(function(){
									punchCard();}
								);
							  //$("#click").removeAttr("disabled");
								$("#click").attr("class","dk_btn");
								_canPu = true;
							}else{
								_canPu = false;
							}
						}
						//当到00:00:00点时,刷新页面
						var H_M=nowTime.split(":");
						var h=H_M[0];
						var m=H_M[1];
						var s=nowTime_s;
						if(h=='00' && m=='00' && s=='00'){
							window.location.reload();
						}
				    }else{
				    	_canPu = false;
				    	 $("div").unbind("click"); 
						 $("#click").attr("class","dk_btn nodk");
				    }
				
			}
			});
	 
	
}

/**
 * 获取服务器时间
 * @return
 */
function getServiceTime(){
	qytx.app.ajax({
		url :basePath + "attendance/getServiceTime.action" ,
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data){
				var data=data.split(">");	
				var dateStr = data[0].split("-");
				$("#day").val(data[0]);
				$("#week").html(data[2]); //星期
				loadRecords();
			}
	}
	});
}

 
/**考勤记录*/
function moreAttendance(){
		 	var tabId ="kqjl";
		 	var url = basePath+"logined/attendance/attendanceRecord.jsp";
		 	window.parent.addTab(tabId,url,"考勤记录",true,"");
}
/** 打卡 +++++++END++++++++++++++++++++++++++++++++++++++++++++++ */

/** 事物管理 +++++++BEGIN++++++++++++++++++++++++++++++++++++++++++++++ */
/**
 * 统计事务个数
 */
function getAffairsCount(){
	if(deskmap.get("事务提醒")){
		$("#affairsMore").click(function(){
			openUrl('事务提醒','logined/affairs/list_new_affairs.jsp',"swtx");
	   });
	}
	$.ajax({
		url : basePath + "logined/desktop/deskka_affairsCount.action",
		type : "post",
		dataType : "json",
		success : function(data) {
				if(data){
				    //工作流
					if(data.工作流){
						$("#gzlSpan").html(data.工作流);
						$("#gzlSpan").val(data.工作流);
					}
				    //任务管理
					if(data.任务管理){
						$("#rwglSpan").html(data.任务管理);
						$("#rwglSpan").val(data.任务管理);
					}
				    //内部邮件
					if(data.内部邮件){
						$("#nbyjSpan").html(data.内部邮件);
						$("#nbyjSpan").val(data.内部邮件);
					}
				    //通知公告
					if(data.通知公告){
						$("#tzglSpan").html(data.通知公告);
						$("#tzglSpan").val(data.通知公告);
					}
				    //日程管理
					if(data.日程管理){
						$("#rcglSpan").html(data.日程管理);
						$("#rcglSpan").val(data.日程管理);
					}
				    // 工作日志
					if(data.工作日志){
						$("#gzrzSpan").html(data.工作日志);
						$("#gzrzSpan").val(data.工作日志);
					}
				} 
			   
		  }
    });
}

function openAffairs(obj){
	var val = $(obj).first("span").text();
	if(val){
		if(val>0){
			openUrl('事务提醒','logined/affairs/list_new_affairs.jsp',"swtx");
		}
	}
}


/** 事物管理 +++++++END++++++++++++++++++++++++++++++++++++++++++++++ */

/**
 * 公司理念
 */

function getCompanyPhilosophy(){
	$.ajax({
		url : basePath + "logined/desktop/deskka_getCompanyPhilosophy.action",
		type : "post",
		dataType : "json",
		success : function(data) {
				if(data){
					if(data.philosophy){
						$("#philosophySpan").html(data.philosophy);
					}
				} 
			   
		  }
    });
}


/**
 * 获取随机数
 */
function getRandomTabId(){
	var str = [1,2,3,4,5,6,7,8,9,0]; 
	var res="";
	for (var i=0;i<=8;i++){
		var random = Math.floor(Math.random()*str.length); 
		var result = str[random]; 
		res+=result;
	}
	return res;
}

/**
 * 根据文件类型获取对应class
 * 
 * @param type
 *            文件类型
 * @return {string} class名称
 */
function getClassByFileType(type) {
	if (type.indexOf(".") != -1) {
		type = type.substr(1, type.length);
	}
	type = type.toLocaleLowerCase();
	var defaultType = {
		txt : "fileTxt",
		doc : "fileWord",
		ppt : "filePPT",
		excel : "fileExcel",
		img : "filePicture",
		rar : "fileRar"
	};
	switch (type) {
		case "txt" :
			return defaultType.txt;
		case "doc" :
		case "docx" :
			return defaultType.doc;
		case "ppt" :
		case "pptx" :
			return defaultType.ppt;
		case "xls" :
		case "xlsx" :
			return defaultType.excel;
		case "gif" :
		case "jpg" :
		case "jpeg" :
		case "png" :
			return defaultType.img;
		case "rar" :
		case "zip" :
		case "7z" :
			return defaultType.rar;
		default :
			return defaultType.txt;
	}
}