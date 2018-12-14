//显示表单数据
function showDetailWithHTML(detail,operation){
	if(detail!=null){
		var html="";
		for(var i=0;i<detail.length;i++){
			var propertyId=detail[i].propertyId;
			var cnName=detail[i].cnName;
			var name=detail[i].name;
			var canEdit=detail[i].canEdit;
			var htmlType=detail[i].htmlType;
			var value=detail[i].value;
			var defaltValue=detail[i].defaltValue;
			var dateFormat=detail[i].dateFormat;
			if(!value){
				value="";
			}
	        if(operation=="approving_test"){
	        	//表单控件类别
		        if(htmlType=="text"){//输入框
		        	if(value==""&&defaltValue){
		        		value=defaltValue;
		        	}
		        	html+="<li class=\"mui-table-view-cell\">";
				    html+="<label class=\"titleName\">"+cnName+"</label>";
				    var tip="请输入"+cnName;
				    html+="<input type=\"text\" class='mui-input-clear inputBox applyForm whiteim' cnName="+cnName+" name="+name+" canEdit="+canEdit+" onblur='caculate()' value='"+value+"' placeholder="+tip+" />";
		        }else if(htmlType=="users"){//选人控件
		        	html+="<li class=\"mui-table-view-cell\">";
				    html+="<a href='javascript:void(0);' class=\"mui-navigate-right users\" userIds='' canEdit="+canEdit+"><label>"+cnName+"</label>";
		        	if(value){
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" show='1' name="+name+" >"+value+"</span></a>";
		        	}else{
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" name="+name+" >请选择</span></a>";
		        	}
		        }else if(htmlType=="group"){//选部门
		        	html+="<li class=\"mui-table-view-cell\">";
		        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right group\" groupIds='' canEdit="+canEdit+"><label>"+cnName+"</label>";
		        	if(value){
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" show='1' name="+name+" >"+value+"</span></a>";
		        	}else{
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" name="+name+" >请选择</span></a>";
		        	}
		        }else if(htmlType=="radio"){//单选
		        	var showValue="请选择";
		        	if(defaltValue){
		        		showValue=defaltValue;
		        	}
		        	html+="<li class=\"mui-table-view-cell\">";
		        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right radio\" name="+name+" canEdit="+canEdit+"><label>"+cnName+"</label>";
		        	if(value){
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" show='1' name="+name+" >"+value+"</span></a>";
		        	}else{
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" name="+name+" >"+showValue+"</span></a>";
		        	}
		        	var radios=detail[i].radios;
		        	<!--单选项-->
					templateHtml=showRadioHtml(name,cnName,radios);
					$("#formDiv").after(templateHtml);
		        }else if(htmlType=="checkbox"){//复选
		        	var showValue="请选择";
		        	if(defaltValue){
		        		if(defaltValue.indexOf(",")==0){
		        			defaltValue=defaltValue.substring(1,defaltValue.length);
		        		}
		        		showValue=defaltValue;
		        	}
		        	html+="<li class=\"mui-table-view-cell\">";
		        	html+="<a href='javascript:void(0);' name="+name+" class=\"mui-navigate-right checkbox\" canEdit="+canEdit+"><label>"+cnName+"</label>";
		        	if(value){
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" show='1' name="+name+" >"+value+"</span></a>";
		        	}else{
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" name="+name+" >"+showValue+"</span></a>";
		        	}
		        	var radios=detail[i].radios;
		        	<!--复选项-->
					templateHtml=showCheckHtml(name,cnName,radios);
					$("#formDiv").after(templateHtml);
		        }else if(htmlType=="select"){//下拉
		        	var showValue="请选择";
		        	if(defaltValue){
		        		showValue=defaltValue;
		        	}
		        	html+="<li class=\"mui-table-view-cell\">";
		        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right radio\" name="+name+" canEdit="+canEdit+"><label>"+cnName+"</label>";
		        	if(value){
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" show='1' name="+name+" >"+value+"</span></a>";
		        	}else{
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" name="+name+" >"+showValue+"</span></a>";
		        	}
		        	var options=detail[i].options;
		        	<!--下拉-->
		        	templateHtml=showRadioHtml(name,cnName,options);
					$("#formDiv").after(templateHtml);
		        }else if(htmlType=="textarea"){
		        	if(value==""&&defaltValue){
		        		value=defaltValue;
		        	}
		        	html+="<li class=\"mui-table-view-cell\" style=\"margin:10px 0px\">";
		        	 html+="<label class=\"titleName_textarea\" style=\"margin-bottom:5px\">"+cnName+"</label>";
					var tip="请输入"+cnName;
			         html+="<textarea class='textareabox applyForm whiteim' cnName="+cnName+" name="+name+" canEdit="+canEdit+" placeholder="+tip+"  rows=4>"+value+"</textarea>";
		        }else if(htmlType=="calendar"){
		        	html+="<li class=\"mui-table-view-cell\">";
		        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right calendar\" canEdit="+canEdit+" dateFormat='"+dateFormat+"'><label>"+cnName+"</label>";
		        	if(value){
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" show='1' name="+name+" >"+value+"</span></a>";
		        	}else{
		        		html+="<span class=\"contBox applyForm\" cnName="+cnName+" name="+name+" >请选择</span></a>";
		        	}
		        }else if(htmlType=="approve"){
		        	html+="<li class=\"mui-table-view-cell\">";
					html+="<a class=\"mui-navigate-right\"><label>"+cnName+"</label>";
		        	if(canEdit&&operation=="approving"){
		        		html+="<span class=\"contBox\" cnName="+cnName+" name="+name+" onclick=\"advice(this,0,'"+defaltValue+"')\">审批</span></a>";
		        	}else{
		        		html+="<span class=\"contBox\" cnName="+cnName+" name="+name+" onclick=\"advice(this,1,'"+defaltValue+"')\">查看</span></a>";
		        	}
		        }else if(htmlType=="cacu"){
		        	html+="<li class=\"mui-table-view-cell\">";
		        	var expr=detail[i].expr;
		        	html+="<label class=\"titleName\">"+cnName+"</label>";
		        	html+="<div class=\"txtBox applyForm\" contenteditable=\"false\" cnName="+cnName+" name="+name+" expr="+expr+" >"+value+"</div>";
		        }
				html+="</li>";
	        	
	        }else{
	        	//表单控件类别
	        	if(value){
	        		html+="<li class=\"mui-table-view-cell\">";
			        if(htmlType=="approve"){
			        	html+="<a class=\"mui-navigate-right\"><label>"+cnName+"</label>";
			        	if(canEdit&&operation=="approving"){
			        		html+="<span class=\"contBox\" cnName="+cnName+" name="+name+" onclick=\"advice(this,0,'"+defaltValue+"')\">审批</span></a>";
			        	}else{
			        		html+="<span class=\"contBox\" cnName="+cnName+" name="+name+" onclick=\"advice(this,1,'"+defaltValue+"')\">查看</span></a>";
			        	}
			        }else if(htmlType=="textarea"){
			        	html+="<label class=\"titleName_textarea\">"+cnName+"</label>";
			        	html+="<div class=\"textareabox text_fl applyForm\" contenteditable=\"false\">"+value+"</div>";
			        }else if(htmlType=="calendar"){
			        	if(value){
			        		var dateMap=getNowDateMap();
			        		if(value.length>=10){
			        			var valueDate10=value.substring(0,10);
			        			if(dateMap.get(valueDate10)){
			        				value=value.replace(valueDate10,dateMap.get(valueDate10));
			        			}
			        		}
			        	}
			        	html+="<label class=\"titleName\">"+cnName+"</label>";
			        	html+="<div class=\"txtBox text_fl applyForm\" contenteditable=\"false\">"+value+"</div>";
			        }else{
			        	html+="<label class=\"titleName\">"+cnName+"</label>";
			        	html+="<div class=\"txtBox text_fl applyForm\" contenteditable=\"false\">"+value+"</div>";
			        }
			        html+="</li>";
	        	}
		        
	        }
	        
		}
		$("#formDiv.mui-content ul").append(html);
	}
}
//打开审批页面
function advice(obj,type,defaultValue){
	var self = plus.webview.currentWebview();
	var instanceId=self.instanceId;
	var name=$(obj).attr("name");
	var taskId=$("#taskId").val();
	mui.openWindow({
		url:'advice.html',
		id:'advice',
		extras:{
			instanceId:instanceId,
			editorId:name,
			taskId:taskId,
			type:type,
			defaultValue:defaultValue
		}
	});
}

//显示审批历史记录
function showHistoryWithHTML(result,operation,totalState){
	
	if(result!=null){
		$("#historyData").empty();
		var userInfos=result;
		var total=userInfos.length;
		for(var i = 0 ; i<total; i++){
			var atime = userInfos[i].time;
			var userName = userInfos[i].approver;
			var advice = userInfos[i].advice;
			var approveResult = userInfos[i].approveResult;
			var approverId = userInfos[i].approverId;
			var taskName=userInfos[i].taskName;
			var content = userInfos[i].content;
			if(taskName=="结束"){
				continue;
			}
			
			createHead(atime, userName, content, approveResult, approverId,i,totalState,total,taskName,operation);
			
		}
		//如果流程结束，不允许再次撤销
		if(totalState == "3"&&operation=="view"){
			$("#viewOper").show();
		}else{
			$("#viewOper").hide();
		}
	}
}

function createHead(atime,userName,advice,approveResult,userId,index,totalState,userNum,taskName,operation){
	
	var result = "";
	var stateClass="";
	var stepClass="";
	var redClass="";
	if(approveResult == 0 ){
		result = "同意";
		stateClass="";
	}else if(approveResult == 1 ){
		result = "拒绝";
		redClass="redBtn";
		stateClass="state2";
	}else if(approveResult == 2){
		result = "已撤销";
		redClass="grayBtn";
		stateClass="state6";
	}else if(approveResult == 3){
		result = "审批中";
		redClass="orangeBtn";
		stepClass="stepBoxIng";
		stateClass="state1";
	}else if(approveResult == 4){
		result = "已转交";
		redClass="yellowBtn";
		stateClass="state5";
	}else if(approveResult == -1){
		result = "提交申请";
		stateClass="";
	}else if(approveResult == 5){
		result = "待审批";
		stateClass="state3";
	}else{
		result = "待审批";
		stateClass="state3";
		userName=taskName;
	}
	if(totalState == "1"&&result=="同意"&&((index+1)==userNum)){
		result = "完结";
		redClass="greenBtn";
		stateClass="";
	}
	
	var strs = "<div class='stepBox "+stepClass+"'>";
	strs+="<span class='state "+stateClass+"'></span>";
	strs+="<div class='txt'>";
	strs+="<em class='jt'></em>";
	strs+="<h2>";
	
	if(atime){
		var tstrs = atime.split(" ");
		var ymd = tstrs[0].split("-");
		var hms=tstrs[1].split(":");
		strs+="<span class='time'>"+ymd[1]+"-"+ymd[2]+" "+hms[0]+":"+hms[1]+"</span>";
	}
	strs+="<span>"+userName+"</span>";
	if(redClass!=""){
		strs+="<span class='Btn "+redClass+"'>"+result+"</span>";
	}
	if(operation=="view" && approveResult == 3){
		var self = plus.webview.currentWebview();
		var processerUser=JSON.parse(self.processerUser);
		var title=self.title;
		
		if(processerUser.length==1){
			userId=processerUser[0].userId;
		}else if(processerUser.length>1){
			for(var j=0 ;j<processerUser.length;j++){
				if(userName==processerUser[j].userName){
					userId=processerUser[j].userId;
				}
			}
		}
		
		var content=window.windowCommon.approveLoginName+"催您审批他的"+title;
		strs+='<a href="javascript:void(0);" onclick="sendDidi(\''+userId+'\',\''+content+'\')" ><em class="water"></em></a>';
	}
	strs+="</h2>";
	
	if(result == "提交申请"||result == "待审批"){
		strs+="<p>"+result+"</p>";
	}
	if(advice&&result!="同意"){
		strs+="<p>"+advice+"</p>";
	}
	if(advice&&result=="同意"){
		strs+="<p>已同意&nbsp;("+advice+")</p>";
	}else if(!advice&&result=="同意"){
		strs+="<p>已同意</p>";
	}
	if(!advice&&result=="已撤销"){
		strs+="<p>已撤销</p>";
	}
	if(!advice&&result=="拒绝"){
		strs+="<p>已拒绝</p>";
	}
	if(!advice&&result=="完结"){
		strs+="<p>已完结</p>";
	}
	$("#historyData").append(strs);

}
/**
 * 发送嘀嘀提醒
 * @param {Object} userId
 * @param {Object} content
 */
function sendDidi(userId,content){
	var didi={
		"userIds":userId,//接收人userIds
		"userIdsEditable":true,//接收人是否可以编辑  true可以 false不可以
		"remindType":1,//提醒方式 1语音4录音8短信0应用内
		"remindTypeEditable":true,//提醒方式是否能编辑 true可以 false不可以
		"contentType":3,//内容类型 1录音 2短语 3文字
		"contentTypeEditable":true,//内容类型是否能编辑 true可以 false不可以
		"content":content,//嘀嘀内容
		"contentEidtable":true,//内容是否能编辑 true可以 false不可以
		"systemName":"lgz",//系统名字
		"moduleName":"approve",//模块名字
		"fromWhere":"approveView"//审批模块详情
	};
	var didiJson=JSON.stringify(didi);
	//添加蒙版
	var flag = false;
	var mask = mui.createMask(function(){
	    return flag;
	});
	//显示遮罩
	mask.show();
	window.plus.qytxplugin.sendDidi(didiJson,function(){
		//关闭遮罩
		setTimeout(function(){
			//关闭遮罩
			flag = true;
			mask.close();
		},1000);
	});
	//防止手机端出错,3秒自动消失
	setTimeout(function(){
		//关闭遮罩
		flag = true;
		mask.close();
	},3000);
}
//跳转到操作页面
function handle(type){
	if(!checkForm()){
		return false;
	}
	var self = plus.webview.currentWebview();
	var instanceId=self.instanceId;
	plus.webview.close("operate");
	var formData="";
	if(type==0||type==1){
		formData=getJSONVal();
	}
	mui.openWindow({
		url:"operate.html",
		id:"operate",
		extras: {
			instanceId: instanceId,
			type:type,
			formData:formData
		}
	});
}
mui.plusReady(function(){
	var self = plus.webview.currentWebview();
	var instanceId=self.instanceId;
	var title=self.title;
	var operation=self.operation;
	var from=self.from;
	if(operation=="view"){
		$("#approveOper").hide();
		$("#viewOper").hide();
	}else if(operation=="approving"){
		$("#approveOper").show();
		$("#viewOper").hide();
	}else{
		$("#approveOper").hide();
		$("#viewOper").hide();
	}
	$("#title").html(title);
	
	//查询表单数据
	mui.ajax(window.windowCommon.basePath+'workflow/detail!view.action',{
		data: {"instanceId": instanceId,"userId":window.windowCommon.approveLoginId, "_clientType":"wap"},
		dataType:'text',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		timeout:10000,//超时时间设置为10秒；
		crossDomain:true,
		success:function(msg){
			//服务器返回响应，根据响应结果，分析是否登录成功；
			if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
            	result =jQuery.parseJSON(result);
            	var detail=result.detail;
            	var historys=result.history;
            	var totalState=result.totalState;//0已结束 3审批中 1拒绝结束 2
            	var taskId=result.taskId;
            	$("#taskId").val(taskId);
            	//显示表单数据
            	
                showDetailWithHTML(detail,operation);
                //检查表单元素是否可以编辑
                checkFormEdit();
                //显示审批历史记录
                
                showHistoryWithHTML(historys,operation,totalState);
                
                plus.nativeUI.closeWaiting();
                plus.webview.currentWebview().show();
                plus.webview.close("apply");
            }
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			//console.log(errorThrown);
		}
	});
	//拒绝审批
	var rejectApply=document.getElementById("rejectApply");
	rejectApply.addEventListener("tap",function(){
		handle(1);
	});
	//同意审批
	var agreeApply=document.getElementById("agreeApply");
	agreeApply.addEventListener("tap",function(){
		handle(0);
	});
	//撤销审批
	var revokeApply=document.getElementById("revokeApply");
	revokeApply.addEventListener("tap",function(){
		handle(2);
	});
});
//返回
var old_back = mui.back;
mui.back = function(){
 	var self = plus.webview.currentWebview();
	var from=self.from;
	if (from == "apply") {
		mui.openWindow({
		    url: 'myStartList.html',
			id: 'myStartList',
			waiting:{
		      autoShow:false//自动显示等待框，默认为true
		    }
		});
	}else{
		old_back();
	}
}