//显示表单
var detailsMap=new Map();
function showDataWithHTML(result){
	result =jQuery.parseJSON(result);
	if(result!=null){
		var html="";
		for(var i=0;i<result.length;i++){
			var propertyId=result[i].propertyId;
			var cnName=result[i].cnName;
			var name=result[i].name; 
			var canEdit=result[i].canEdit;
			var htmlType=result[i].htmlType;
			var defaltValue=result[i].defaltValue;
			var dateFormat=result[i].dateFormat;
			var contentType=result[i].contentType;
			var required=result[i].required;
			var calendarRangeExpr=result[i].calendarRangeExpr;
			var calendarRangeFlag=result[i].calendarRangeFlag;
			var other=result[i].other;
			var details=result[i].details;
			var templateHtml="";
			if(htmlType=="approve"){
				continue;
			}
			var showValue="请选择";
			var showSelectClass="showSelect";
	        //表单控件类别
	        if(htmlType=="text"){//输入框
	        	var showValue="";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        	}
	        	html+="<li class=\"mui-table-view-cell\">";
			    html+="<label class=\"titleName\">"+cnName+"</label>";
			    var tip="请输入"+cnName;
			     if(required){
			    	tip="请输入"+cnName+"（必填）";
			    }
			    if(calendarRangeExpr){
			    	html+="<div class=\"txtBox applyForm\" contenteditable=\"false\" cnName="+cnName+" name="+name+" calendarRangeExpr="+calendarRangeExpr+" ></div>";
			    }else{
			    	if(contentType&&contentType=="number"){
				    	html+="<input type=\"number\" class='mui-input-clear inputBox applyForm whiteim' require="+required+" cnName="+cnName+" name="+name+" canEdit="+canEdit+" oninput='caculate()' placeholder="+tip+" value='"+showValue+"' />";
				    }else{
				    	html+="<input type=\"text\" class='mui-input-clear inputBox applyForm whiteim' require="+required+" cnName="+cnName+" name="+name+" canEdit="+canEdit+" oninput='caculate()' placeholder="+tip+" value='"+showValue+"' />";
				    }
			    }
	        }else if(htmlType=="users"){//选人控件
	        	showValue="请选择";
	        	if(required){
	        		showValue+="（必选）";
	        	}
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
			    html+="<a href='javascript:void(0);' class=\"mui-navigate-right users\" userIds='' canEdit="+canEdit+"><label class=\"titleName\" >"+cnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm showSelect\" require="+required+" cnName="+cnName+" name="+name+" >"+showValue+"</span></a>";
	        }else if(htmlType=="group"){//选部门
	        	showValue="请选择";
	        	if(required){
	        		showValue+="（必选）";
	        	}
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right group\" groupIds='' canEdit="+canEdit+"><label class=\"titleName\">"+cnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm showSelect\" require="+required+" cnName="+cnName+" name="+name+">"+showValue+"</span></a>";
	        }else if(htmlType=="radio"){//单选
	        	showValue="请选择";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        		showSelectClass="";
	        	}else if(required){
	        		showValue+="（必选）";
	        	}
	        	var radios=result[i].radios;
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href=\"javascript:void(0);\" onclick=\"openSelectHtml('radio','"+name+"','"+cnName+"','"+radios+"',this)\" class=\"mui-navigate-right radio\" name="+name+" canEdit="+canEdit+"><label class=\"titleName\" >"+cnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm "+showSelectClass+"\" require="+required+" cnName="+cnName+" name="+name+">"+showValue+"</span></a>";
	        	
	        	<!--单选项-->
	        }else if(htmlType=="checkbox"){//复选
	        	showValue="请选择";
	        	if(defaltValue){
	        		if(defaltValue.indexOf(",")==0){
	        			defaltValue=defaltValue.substring(1,defaltValue.length);
	        		}
	        		showValue=defaltValue;
	        		showSelectClass="";
	        	}else if(required){
	        		showValue+="（必选）";
	        	}
	        	var radios=result[i].radios;
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href=\"javascript:void(0);\" onclick=\"openSelectHtml('checkbox','"+name+"','"+cnName+"','"+radios+"',this)\" name="+name+" class=\"mui-navigate-right checkbox\" canEdit="+canEdit+"><label class=\"titleName\" >"+cnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm "+showSelectClass+" \" require="+required+" cnName="+cnName+" name="+name+" >"+showValue+"</span></a>";
	        	
	        	<!--复选项-->
	        }else if(htmlType=="select"){//下拉
	        	showValue="请选择";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        		showSelectClass="";
	        	}else if(required){
	        		showValue+="（必选）";
	        	}
	        	var options=result[i].options;
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href=\"javascript:void(0);\" onclick=\"openSelectHtml('radio','"+name+"','"+cnName+"','"+options+"',this)\" class=\"mui-navigate-right radio\" name="+name+" canEdit="+canEdit+"><label>"+cnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm "+showSelectClass+"\" require="+required+" cnName="+cnName+" name="+name+">"+showValue+"</span></a>";
	        	<!--下拉-->
	        }else if(htmlType=="textarea"){
	        	var showValue="";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        	}
	        	html+="<li class=\"mui-table-view-cell\" style=\"margin:10px 0px\">";
				html+="<label class=\"titleName_textarea\" style=\"margin-bottom:5px\">"+cnName+"</label>";
				var tip="请输入"+cnName;
				if(required){
	        		tip="请输入"+cnName+"（必填）";
	        	}
		        html+="<textarea class='textareabox applyForm whiteim' require="+required+" cnName="+cnName+" name="+name+" canEdit="+canEdit+" placeholder="+tip+"  rows=4>"+showValue+"</textarea>";
	        }else if(htmlType=="calendar"){
	        	showValue="请选择";
	        	if(required){
	        		showValue+="（必选）";
	        	}
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right calendar\" other='"+other+"' canEdit="+canEdit+" calendarRangeFlag='"+calendarRangeFlag+"' dateFormat='"+dateFormat+"'><label class='titleName' >"+cnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm calendarSpan showSelect\" require="+required+" cnName="+cnName+"  name="+name+">"+showValue+"</span></a>";
	        }else if(htmlType=="cacu"){
	        	html+="<li class=\"mui-table-view-cell\">";
	        	var expr=result[i].expr;
	        	html+="<label class=\"titleName\">"+cnName+"</label>";
	        	html+="<div class=\"txtBox applyForm contenteditable=\"false\" cnName="+cnName+" name="+name+" expr="+expr+" ></div>";
	        }else if(htmlType=="detail"){
	        	//显示明细详情
	        	html+="<div class=\"mx_detail\">";
	        	var showHtml=showDetail(details,cnName,name,canEdit,1);
	        	html+=showHtml;
	        	detailsMap.put(cnName,details);
	        	html+="<div class=\"mx_detail_btn applyForm\" type='detail' cnName="+cnName+" name="+name+" canEdit="+canEdit+" onclick=\"addDetail(this,'"+cnName+"','"+name+"','"+canEdit+"')\">+ 添加"+cnName+"</div></div>";
	        }
			html+="</li>";
		}
		
		$("#formDiv.mui-content ul").append(html);
		
	}
	mui('.mui-scroll-wrapper').scroll();
	//初始化日期控件
	initcalendar();
	//日期控件差自动计算
	caculateRange();
}
//添加明细
function addDetail(obj,cnName,name,canEdit){
	var details=detailsMap.get(cnName);
	var i=$(obj).parent().find(".mx_detail_title").length;
	var showHtml=showDetail(details,cnName,name,canEdit,i+1);
	$(obj).before(showHtml);
	bindSelectEvent();
	//初始化日期控件
	addInitcalendar(i);
	//日期控件差自动计算
	caculateRange();
}
//删除明细
function deleteDetail(obj,cnName){
	var htmlObj=$(obj).parent().parent().parent();
	$(obj).parent().parent().remove();
	htmlObj.find(".mx_detail_title_span").each(function(i){
		i=i+1;
		$(this).html(cnName+"("+i+")");
	});
}
//显示明细详情
function showDetail(details,cnName,parentName,canEdit,i) {
	var html = "";
	if (details) {
		html += "<div><div class='mx_detail_title'><span class='mx_detail_title_span'>"+cnName+"("+i+")</span>";
		if(i!=1){
			html+="<em onclick=\"deleteDetail(this,'"+cnName+"')\"></em>";
		}
		html+="</div>";
		html+="<ul class='mui-table-view mui-my-from ul_"+parentName+"'>";
		for (var j = 0; j < details.length; j++) {
			var propertyId = details[j].propertyId;
			var cnName = details[j].cnName;
			var name = details[j].name;
			var canEdit = canEdit;
			var htmlType = details[j].htmlType;
			var defaltValue = details[j].defaltValue;
			var dateFormat = details[j].dateFormat;
			var contentType = details[j].contentType;
			var required = details[j].required;
			var calendarRangeExpr = details[j].calendarRangeExpr;
			var calendarRangeFlag = details[j].calendarRangeFlag;
			var other = details[j].other;
			var showcnName=cnName;
			name=name+"_"+i;
			cnName=cnName+"_"+i;
			other=other+"_"+i;
			if(htmlType=="approve"){
				continue;
			}
			var showValue="请选择";
			var showSelectClass="showSelect";
	        //表单控件类别
	        if(htmlType=="text"){//输入框
	        	var showValue="";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        	}
	        	html+="<li class=\"mui-table-view-cell\">";
			    html+="<label class=\"titleName\">"+showcnName+"</label>";
			    var tip="请输入"+showcnName;
			     if(required){
			    	tip="请输入"+showcnName+"（必填）";
			    }
			    if(calendarRangeExpr){
			    	var arr = calendarRangeExpr.split("-");
					var endcnName=arr[0]+"_"+i;
					var startcnName=arr[1]+"_"+i;
					calendarRangeExpr=endcnName+"-"+startcnName;
			    	html+="<div class=\"txtBox applyForm_"+parentName+"\" contenteditable=\"false\" cnName="+cnName+" name="+name+" calendarRangeExpr="+calendarRangeExpr+" ></div>";
			    }else{
			    	if(contentType&&contentType=="number"){
				    	html+="<input type=\"number\" class='mui-input-clear inputBox applyForm_"+parentName+" whiteim' require="+required+" cnName="+cnName+" name="+name+" canEdit="+canEdit+" oninput='caculate()' placeholder="+tip+" value='"+showValue+"' />";
				    }else{
				    	html+="<input type=\"text\" class='mui-input-clear inputBox applyForm_"+parentName+" whiteim' require="+required+" cnName="+cnName+" name="+name+" canEdit="+canEdit+" oninput='caculate()' placeholder="+tip+" value='"+showValue+"' />";
				    }
			    }
	        }else if(htmlType=="users"){//选人控件
	        	showValue="请选择";
	        	if(required){
	        		showValue+="（必选）";
	        	}
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
			    html+="<a href='javascript:void(0);' class=\"mui-navigate-right users\" userIds='' canEdit="+canEdit+"><label class=\"titleName\" >"+showcnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm_"+parentName+" showSelect\" require="+required+" cnName="+cnName+" name="+name+" >"+showValue+"</span></a>";
	        }else if(htmlType=="group"){//选部门
	        	showValue="请选择";
	        	if(required){
	        		showValue+="（必选）";
	        	}
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right group\" groupIds='' canEdit="+canEdit+"><label class=\"titleName\">"+showcnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm_"+parentName+" showSelect\" require="+required+" cnName="+cnName+" name="+name+">"+showValue+"</span></a>";
	        }else if(htmlType=="radio"){//单选
	        	showValue="请选择";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        		showSelectClass="";
	        	}else if(required){
	        		showValue+="（必选）";
	        	}
	        	var radios=details[j].radios;
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href=\"javascript:void(0);\" onclick=\"openSelectHtml('radio','"+name+"','"+cnName+"','"+radios+"',this)\" class=\"mui-navigate-right radio\" name="+name+" canEdit="+canEdit+"><label class=\"titleName\" >"+showcnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm_"+parentName+" "+showSelectClass+"\" require="+required+" cnName="+cnName+" name="+name+">"+showValue+"</span></a>";
	        	
	        	<!--单选项-->
	        }else if(htmlType=="checkbox"){//复选
	        	showValue="请选择";
	        	if(defaltValue){
	        		if(defaltValue.indexOf(",")==0){
	        			defaltValue=defaltValue.substring(1,defaltValue.length);
	        		}
	        		showValue=defaltValue;
	        		showSelectClass="";
	        	}else if(required){
	        		showValue+="（必选）";
	        	}
	        	var radios=details[j].radios;
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href=\"javascript:void(0);\" onclick=\"openSelectHtml('checkbox','"+name+"','"+cnName+"','"+radios+"',this)\" name="+name+" class=\"mui-navigate-right checkbox\" canEdit="+canEdit+"><label class=\"titleName\" >"+showcnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm_"+parentName+" "+showSelectClass+" \" require="+required+" cnName="+cnName+" name="+name+" >"+showValue+"</span></a>";
	        	
	        	<!--复选项-->
	        }else if(htmlType=="select"){//下拉
	        	showValue="请选择";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        		showSelectClass="";
	        	}else if(required){
	        		showValue+="（必选）";
	        	}
	        	var options=details[j].options;
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href=\"javascript:void(0);\" onclick=\"openSelectHtml('radio','"+name+"','"+cnName+"','"+options+"',this)\" class=\"mui-navigate-right radio\" name="+name+" canEdit="+canEdit+"><label>"+showcnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm_"+parentName+" "+showSelectClass+"\" require="+required+" cnName="+cnName+" name="+name+">"+showValue+"</span></a>";
	        	<!--下拉-->
	        }else if(htmlType=="textarea"){
	        	var showValue="";
	        	if(defaltValue){
	        		showValue=defaltValue;
	        	}
	        	html+="<li class=\"mui-table-view-cell\" style=\"margin:10px 0px\">";
				html+="<label class=\"titleName_textarea\" style=\"margin-bottom:5px\">"+showcnName+"</label>";
				var tip="请输入"+showcnName;
				if(required){
	        		tip="请输入"+showcnName+"（必填）";
	        	}
		        html+="<textarea class='textareabox applyForm_"+parentName+" whiteim' require="+required+" cnName="+cnName+" name="+name+" canEdit="+canEdit+" placeholder="+tip+"  rows=4>"+showValue+"</textarea>";
	        }else if(htmlType=="calendar"){
	        	showValue="请选择";
	        	if(required){
	        		showValue+="（必选）";
	        	}
	        	html+="<li class=\"mui-table-view-cell showSelectLi\">";
	        	html+="<a href='javascript:void(0);' class=\"mui-navigate-right calendar\" other='"+other+"' canEdit="+canEdit+" calendarRangeFlag='"+calendarRangeFlag+"' dateFormat='"+dateFormat+"'><label class='titleName' >"+showcnName+"</label>";
	        	html+="<span class=\"contBox contSelect applyForm_"+parentName+" calendarSpan showSelect\" require="+required+" cnName="+cnName+"  name="+name+">"+showValue+"</span></a>";
	        }else if(htmlType=="cacu"){
	        	html+="<li class=\"mui-table-view-cell\">";
	        	var expr=result[i].expr;
	        	html+="<label class=\"titleName\">"+showcnName+"</label>";
	        	html+="<div class=\"txtBox applyForm_"+parentName+"\" contenteditable=\"false\" cnName="+cnName+" name="+name+" expr="+expr+" ></div>";
	        }
			html+="</li>";
		}
		html+="</ul></div>";
	}
	return html;
}
//显示审批人员信息
function showUserWithHTML(result){
	result =jQuery.parseJSON(result);
	if(result!=null){
		var html="";
		var candidate=result[0].candidate;
		var nextTaskName=result[0].nextTaskName;
		var onlySelectOne=result[0].onlySelectOne;
		$("#nextAction").val("TO "+nextTaskName);
		if(onlySelectOne){
			$("#oper").hide();
		}else{
			$("#oper").show();
		}
		for(var i=0;i<candidate.length;i++){
			var userId=candidate[i].id;
			var name=candidate[i].name;
			var photo=candidate[i].photo;
			var photoUrl=window.windowCommon.photoUrl;
			var showName=name;
			if(name!=null&&name.length>2){
				showName=name.substring(name.length-2,name.length);
			}
			var col=letterCode(showName);
			html+="<li class=\"mui-table-view-cell mui-media mui-approve\">";
			html+="<a href='javascript:void(0);' onlySelectOne="+onlySelectOne+" userId="+userId+" name="+name+" photo="+photo+">";
			if(photo==null||photo==""||photo==undefined){
				 html+='<div class="textround mui-pull-left head-bg-'+col+'">'+showName+'</div>';
			}else{
				 html+="<img class=\"mui-pull-left imground\" src='"+photoUrl+photo+"'>";
			}
	        html+="<div class=\"mui-media-body\">";
	        html+="<span class=\"user-name\">"+name+"</span>";
	        html+="<i class=\"checkoff mui-pull-right\"></i></div></a>";
	        html+="</li>";
		}
		$("#userList ul").append(html);
		//如果只有一个审批人，则直接显示
		if(candidate.length==1){
			var userId=candidate[0].id;
			var name=candidate[0].name;
			var photo=candidate[0].photo;
			showOnlyOne(userId,name,photo);
		}
	}
}
//绑定选择人员事件
function bindSelectUser(){
	$("#userList ul").delegate("li a","tap",function(){
		var onlySelectOne=$(this).attr("onlySelectOne");
		selectUserStyle(this,onlySelectOne);
		return false;
	});
}
//取消绑定选择人员事件
function unbindSelectUser(){
	$("#userList ul").undelegate("li a","tap");
}
//如果只有一个审批人，则直接显示
function showOnlyOne(userId, name, photo) {
	var photoUrl = window.windowCommon.photoUrl;
	var showName = name;
	if (showName.length > 2) {
		showName = showName.substring(showName.length - 2, showName.length);
	}
	var html = "";
	html += '<a href="javascript:void(0);" name="headUser">';
	html += '<div class="head headImg">';
	if (photo == null || photo == "" || photo == undefined || photo == 'undefined') {
		html += '<div class="round ico-1">' + showName + '</div>';
	} else {
		html += '<div class="round"><img src="' + photoUrl + photo + '"></div>';
	}
	html += '<p class="center">' + name + '</p>';
	html += '</div></a>';
	$("#addHead").before(html);
	$("#nextUsers").val(userId);
	document.querySelector("a[name='headUser']").addEventListener("tap",function(){
		$(this).focus();
		showUserList();
	});
	document.querySelector("a[name='headUser']").addEventListener("longtap",function(){
		deleteSelectUser();
	});
	$("#userSelect").hide();
}
//显示审批人列表
function showUserList() {
	var nextUsers = $("#nextUsers").val();
	$("#userList li a").removeClass("on");
	$("#userList li a").each(function() {
		var userId = $(this).attr("userId");
		if (nextUsers==userId) {
			$(this).addClass("on");
		}
	});
	setTimeout(function() {
		mui("#userList").popover("toggle");
	}, 300);
	bindSelectUser();
}
//删除选择人员
function deleteSelectUser(){
	$("a[name='headUser']").remove();
	$("#nextUsers").val("");
	$("#userSelect").show();
}
//选择人员确定
function sure(){
	$("#userSelect").hide();
	$("#formDiv").show();
	$("#addHead").siblings().remove();
	var approveUserIds="";
	$("#userList a.on").each(function(){
		var userId=$(this).attr("userId");
		var name=$(this).attr("name");
		var photo=$(this).attr("photo");
		var photoUrl=window.windowCommon.photoUrl;
		var showName=name;
		if(showName.length>2){
			showName=showName.substring(showName.length-2,showName.length);
		}
		var html="";
		html+='<a href="javascript:void(0);" name="headUser">';
		html+='<div class="head headImg">';
		if(photo==null||photo==""||photo==undefined||photo=='undefined'){
			html+='<div class="round ico-1">'+showName+'</div>';
		}else{
			html+='<div class="round"><img src="'+photoUrl+photo+'"></div>';
		}
		html+='<p class="center">'+name+'</p>';
		html+='</div></a>';
		$("#addHead").before(html);
		approveUserIds+=userId+",";
	});
	if(approveUserIds!=""){
		approveUserIds=approveUserIds.substring(0,approveUserIds.length-1);
	}
	$("#nextUsers").val(approveUserIds);
	document.querySelector("a[name='headUser']").addEventListener("tap",function(){
		$(this).focus();
		showUserList();
	});
	document.querySelector("a[name='headUser']").addEventListener("longtap",function(){
		deleteSelectUser();
	});
	mui('#userList').popover('toggle');
	unbindSelectUser();
}

//提交表单
function submitApply(obj){
	$(obj).attr("disabled",true);
	caculate();
	//表单元素验证
	if(!checkForm()||!checkNextUser()){
		$(obj).attr("disabled",false);
		return;
	}
	var self = plus.webview.currentWebview();
	var processId=self.processId;
	//下一步操作
	var nextAction=$("#nextAction").val();
	//下一步审批人
	var nextUsers=$("#nextUsers").val();
	//表单数据
	var formData=getJSONVal();
	if(formData==""){
		mui.toast("表单元素不能全部为空");
		$(obj).attr("disabled",false);
		return false;
	}
	//显示提交等待
	showWaiting();
	mui.ajax(window.windowCommon.basePath+'workflow/option!startProcess.action?time='+(new Date()).getTime(),{
		data: {
			"processId":processId,  
			"formData":formData,
			"nextAction":nextAction,
			"nextUsers":nextUsers,
			"userId":window.windowCommon.approveLoginId,
			"_clientType":"wap"
		},
		dataType:'text',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		timeout:60000,//超时时间设置为60秒
		crossDomain:true,
		success:function(msg){
			//关闭提交等待
			closeWaiting();
			//服务器返回响应，根据响应结果，分析是否登录成功；
			if(msg.indexOf("100||")==0){
				mui.toast("提交成功");
				var dataJson=msg.substring(5,msg.length);
				var data=JSON.parse(dataJson);
				var instanceId=data.instanceId;
				var processerUser=JSON.stringify(data.processerUser);
				var title=$("#title").html();
				//打开详情
				mui.openWindow({
					url: 'detail.html',
					id: 'detail',
					extras: { 
						instanceId: instanceId,
						title:title,
						processerUser:processerUser,
						operation:"view",
						from:"apply"
					},
					show:{
				      autoShow:false
				    },
					waiting:{
						autoShow:true,//自动显示等待框，默认为true
      					title:''//等待对话框上显示的提示内容
					}
				});
            }else{
            	$(obj).attr("disabled",false);
            	mui.toast("提交失败");
            }
		},
		error:function(xhr,type,errorThrown){
			//关闭提交等待
			closeWaiting();
			//异常处理；
			$(obj).attr("disabled",false);
			mui.toast("请求数据异常");
		}
	});
}

mui.plusReady(function(){
	//判断当前是否有网络
	if(!getNetConnection()){
		$(".mui-content").hide();
		$(".noNet").show();
		return false;
	}
	var self = plus.webview.currentWebview();
	var processId=self.processId;
	mui('.mui-scroll-wrapper').scroll();
	mui('body').on('shown', '.mui-popover', function(e) {
		//console.log('shown', e.detail.id);//detail为当前popover元素
	});
	mui('body').on('hidden', '.mui-popover', function(e) {
		//console.log('hidden', e.detail.id);//detail为当前popover元素
	});
	//获取标题
	mui.ajax(window.windowCommon.basePath+'workflow/option!getStartProcessTitle.action?time='+(new Date()).getTime(),{
		data: {"userId":window.windowCommon.approveLoginId,"processId":processId,"_clientType":"wap"},
		dataType:'text',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		timeout:10000,//超时时间设置为10秒；
		crossDomain:true,
		success:function(msg){
			//服务器返回响应，根据响应结果，分析是否登录成功；
			if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
                $("#title").html(result);
                plus.nativeUI.closeWaiting();
                plus.webview.currentWebview().show();
            }
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			//console.log(errorThrown);
		}
	});
	//显示申请表单数据
	mui.ajax(window.windowCommon.basePath+'workflow/option!getStartFormProperties.action?time='+(new Date()).getTime(),{
		data: {"processId":processId,"_clientType":"wap"},
		dataType:'text',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		timeout:10000,//超时时间设置为10秒；
		crossDomain:true,
		success:function(msg){
			//服务器返回响应，根据响应结果，分析是否登录成功；
			if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
                showDataWithHTML(result);
                //检查表单元素是否可以编辑
                checkFormEdit();
                //绑定表单中所有选择事件
				bindSelectEvent();
                //绑定关闭弹框事件
                bindCloseIcon();
            }
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
//			console.log(errorThrown);
		}
	});
	mui.ajax(window.windowCommon.basePath+'workflow/option!getStartNextTaskInfo.action?time='+(new Date()).getTime(),{
		data: {"processId":processId,"_clientType":"wap"},
		dataType:'text',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		timeout:10000,//超时时间设置为10秒；
		crossDomain:true,
		success:function(msg){
			//服务器返回响应，根据响应结果，分析是否登录成功；
			if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
                showUserWithHTML(result);
            }
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			//console.log(errorThrown);
		}
	});
	//人员选择默认值
	document.getElementById("userSelect").addEventListener("tap",function(){
		$(this).focus();
		showUserList();
		event.stopPropagation();
		return false;
	});
	//提交审批
	var saveApply=document.getElementById("saveApply");
	saveApply.addEventListener("release",function(){
		submitApply(saveApply);
	});
	//选择人员确定
	var userSure=document.getElementById("userSure");
	userSure.addEventListener("tap",function(){
		sure();
	});
	//关闭弹出层
	var iconClose=document.getElementById("iconClose");
	iconClose.addEventListener("tap",function(){
		setTimeout(function(){
			mui("#userList").popover("toggle"); 
		},300); 
		unbindSelectUser();
	});
});


