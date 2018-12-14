/***
***选择部门人员控件，选择人员
**/
function initSelectPerson()
{	
	$("input[selectuser]").each(function(){
		var temp = $(this);
		if($(temp).attr("readonly")){
		}else{
			$(temp).focus(function(){
				openDocSelectUser(3,selectCallBackForm,0,this);//选择人员
			});
		}
	});
}

/*
* 选择日期控件的实现
*/
function generateDateWidget(){
	$("input[dateWidget]").each(function(){
		var temp = $(this);
		var format = $(temp).attr("date_format");
		if($(temp).attr("readonly")=='readonly'){
		}else{
			$(temp).focus(function(){
				WdatePicker({isShowClear:true,dateFmt:format});
			});
		}
	});
}

/**
 * 部门人员选择的回调函数
 */
function selectCallBackForm(data,param)
{
	if(data){
	    data.forEach(function(value, key) {
	       $("input[name='"+param.name+"']").val(value.Name);
	    });
	}
}
/**
 * 选择部门人员控件，选择部门
 */
function initSelectGroup(){
	// if($(obj).attr("readonly")){
	// }else{
	// 	openDocSelectUser(1,selectCallBackForm,0,obj);//选择部门
	// }
	$("input[selectgroup]").each(function(){
		var temp = $(this);
		if($(temp).attr("readonly")=='readonly'){
		}else{
			$(temp).focus(function(){
				openDocSelectUser(1,selectCallBackForm,0,this);//选择部门
			});
		}
	});
}
/**
 * 关闭当前页面
 */
function closeDialog(){
	art.dialog.confirm('确定要关闭此页面吗？', function () {
		/**
		 * 不用二次确认直接关闭 兼容ie678
		 */
		window.opener=null;
		window.open('','_self');
		window.close();
		//需要二次确认才关闭
		//window.close();
    }, function () {
    	
    });
}
/*****************************计算控件开始*******************************/
jQuery(document).ready(function($){

	//add by 贾永强,执行计算控件
	//按照"+","-","*","/"拆分字符串
	var regexp = /[^\+-\/\*]*/gi;
	function caculate(){
		$("input[calu=1]").each(function(){
			var expr   = $(this).attr("expr");
			// var jingdu = $(this).attr("jingdu");
			var arr = expr.match(regexp);
			for (var i = 0; i < arr.length; i++) {
				var cnname = arr[i];
				if(cnname){
					cnname="%"+cnname+"%";
					var tempValue = $("input[cnname='"+cnname+"']").val();
					if(!tempValue){
						tempValue = 0 ;
					}
					tempValue = isNaN(tempValue)?0:tempValue;
					expr = expr.replace(arr[i],tempValue);
				}
			};
			$(this).val(eval('('+expr+')'));
		});
	}
	$("input").blur(function(){
		caculate();
	});
});
/*****************************计算控件结束*******************************/
/***********************************审批控件开始*******************************/
	/*
	 * 初始化审批意见控件，
	 * 1，初始化为富文本编辑框
	 * 2，绑定focus事件
	 */
	function generateTdAdvice(){
		var instanceId = "";
		if($("#instanceId").val()){
			instanceId = $("#instanceId").val();
		}
		var adviceCon =$("textarea[adviceflag='advice']");
		if(adviceCon!=null){
			//初始化多个富文本编辑框
			for(var i=0; i<adviceCon.length; i++){
				createEditor(adviceCon[i]);
			}
			//点击触发事件
			function focusEvent( ev ) {
				var editor = ev.editor;
				var name = editor.name;
				var viewflag = $("#history").val();
				var instanceId = $("#instanceId").val();
				if(viewflag == "history"){
					editor.setReadOnly(true);
					return;
				}
				if(($("#"+name).attr("disabled")=="disabled") || ($("#"+name).attr("readonly")=="readonly")){
			    	editor.setReadOnly(true);
			    	return;
			    }
				var taskId = $("#taskId").val();
				var query = "";
				//工作流发起的时候还没有生成流程实例ID，所以要自己组合根据规则，流程名称.工作名称
				if(instanceId){
					query+="&instanceId="+encodeURI(instanceId)+"&taskId="+taskId;
				}else{
					var processId = $("#processId").val();
					var processName = $("#processInstanceName").val();
					query+="&processId="+processId+"&processName="+encodeURI(processName);
				}
				query+="&editorName="+encodeURI(editor.name)+"&r="+Math.random();
				art.dialog.open(basePath+"customWidget/manager!addAdvice.action",{
						title:"签批意见：",
						width : 800,
				        height : 450,
						lock : true,
					    opacity: 0.08,
					    init:function(){
				    		var ifr = this.iframe;
							var subWin = ifr.contentWindow;
							$(subWin.document).ready(function(){
								var childUe = subWin.ue;
								$.post(basePath+"/customWidget/manager!getLastAdvice.action?1=1"+query,function(data){
									childUe.setContent(data);
								//	contentObj.setContent(data);
						    	});
							});							
					    },
						ok:function(){
							var ifr = this.iframe;
							var subWin = ifr.contentWindow;
							var content = subWin.ue.getContent();
							if(!content){
								content = "";
							}
							$.post(basePath+"customWidget/manager!saveAdvice.action?advice="+encodeURIComponent(content)+query,function(data){
								if(data){
									var width = $("#"+name).attr("width");
									editor.setData(parseHtml(data,width,name));
								}
								art.dialog.close();
							},"json");
				           	return true;
						},
						cancel:true
				});
			}; 

			//富文本编辑框初始化完成事件
			function readyEvent(ev){
				var editor = ev.editor;
				var name = editor.name;
				var width = $("#"+name).attr("width");
				$(".cke_top").hide();
				$(".cke_bottom").hide();
				$.get(basePath+"/customWidget/manager!getTdAdvice.action?instanceId="+encodeURI(instanceId)+"&r="+Math.random(),function(result){
					if(result){
						editor.setData(parseHtml(result,width,name));
					}
			    },"json");
			}
		}
		
		/*
		 *创建一个符文本编辑框
		 * param:textareaObj:多上输入框
		 */
		function createEditor(textareaObj){
			var adviceName = $(textareaObj).attr("name");
			var width = $(textareaObj).attr("width");
			var height = $(textareaObj).attr("height");
			var editor = CKEDITOR.replace(adviceName,{
		       	language:'zh-cn',
		        height:height,
		        width:width,
		        toolbar:"Base",
				on:{
					focus:focusEvent,
					instanceReady:readyEvent
				}		       
		    });
		}

		/*
		 *把json字符串转化为html字符串
		 */
		function parseHtml(result,width,name){
			var html = "";
			if(!result){
				return;
			}
			// result = eval('('+result+')');
			for(var i=0; i<result.length; i++){
				var temp = result[i];
				if(temp.editorName != name){
					continue;
				}
				var content = '<strong>'+temp.content+'</strong>';
				var signType = temp.signType;
				var adviceTime = temp.adviceTime;
				if(!adviceTime){
					adviceTime = "";
				}
				var sign = '';
				if(signType == 1){
					sign += '<p >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+temp.userName+'&nbsp;'+adviceTime+'</p>';
				}else if(signType == 2){
					var url = basePath+'/filemanager/downFileByFilePath.action?filePath='+temp.signUrl;
					sign = '<p >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width="120px" height="40px"  src='+url+' />'+adviceTime+'</p>';
				}
				html+=content+sign;
			}
			return html;
		}
	}
/***********************************审批控件结束*******************************/
/***********************************阅读控件开始*******************************/
/*
 *保存阅读意见
 */
function saveReadState(){
	var instanceId = $("#instanceId").val();
	if(instanceId){
		art.dialog.confirm("确定要阅读吗?",function(){
			$.post(basePath+"/customWidget/manager!saveReaderName.action?instanceId="+encodeURI(instanceId)+"&r="+Math.random(),function(result){
				if(result == "success"){
					art.dialog({
					width : 317,
				    height : 109,
				    title: '消息',
				    content: "阅读成功！",
				    icon: 'succeed',
				    ok: function(){
				    	initReadState();
				    	return true;
				    }
				});
				}
			});
		});
	}
}

/*
 *初始化阅读意见
 */
function initReadState(){
	var instanceId = $("#instanceId").val();
	var tempObjs = $("input[readflag='readflag']");
	if(tempObjs && instanceId){
		$.get(basePath+"customWidget/manager!initReaderName.action?instanceId="+encodeURI(instanceId)+"&r="+Math.random(),function(data){
			if(data){
				data = eval('('+data+')');
				for(var i=0; i<data.length; i++){
					var userName = data[i].userName;
					var da = data[i].date+"";
					if(userName){
						if(tempObjs[i]){
							//$(tempObjs[i]).val(userName+" "+da);
							var daStr = da.substring(5,da.length);
							$(tempObjs[i]).val(userName+" "+daStr);
						}
					}
				}
			}
		})
	}
}

/*
 * 初始化阅读控件
 */
function generateReaderNames(){
	var tempObjs = $("input[readflag='readflag']");
	var viewflag = $("#history").val();
	if(viewflag!="history" && tempObjs){
		for(var i=0; i<tempObjs.length;i++){
			if($(tempObjs[i]).attr("disabled") || $(tempObjs[i]).attr("readonly")){
				continue;
			}
			var temp = $(tempObjs[i]).val();
			if(!temp){
				$(tempObjs[i]).focus(function(){
					saveReadState();
				});
			}
		}
	}else if(viewflag == "history"){
		$(tempObjs).attr("readonly","readonly");
	}
	initReadState();
}
/***********************************阅读控件结束*******************************/
/***********************************初始化发起人、发起部门、发起时间信息 开始*******************************/
function initCreaterInfoWidget(){
	var createrName = $("input[createrName=createrName]");
	var createGroup = $("input[createrGroup=createrGroup]");
	var createDate 	= $("input[createDate=createDate]");
	if(createrName || createGroup || createDate){
		var instanceId = $("#instanceId").val();
		if(instanceId){
			instanceId = encodeURI(instanceId);
		}
		$.get(basePath+"/customWidget/manager!initCreateInfo.action?instanceId="+instanceId+"&r="+Math.random(),function(data){
			if(!$(createrName).val()){
				$(createrName).val(data.userName);
				$(createrName).attr("readonly","readonly");
			}
			if(!$(createGroup).val()){
				$(createGroup).val(data.groupName);
				$(createGroup).attr("readonly","readonly");
			}
			if(!$(createDate).val()){
				$(createDate).val(data.dataTime);
				$(createDate).attr("readonly","readonly");
			}
		},"json");
	}
}
/***********************************初始化发起人、发起部门、发起时间信息 结束*******************************/
/******************************************文号控件 开始********************************************/
function initWenhaoWidget(){
	var widget = $("input[wenhaoflag='wenhaoflag']");
	if(widget){
		var instanceId = $("#instanceId").val();
		if(instanceId){
			instanceId = encodeURI(instanceId);
		}
		$.get(basePath+"/customWidget/manager!initWenhaoWidget.action?instanceId="+instanceId+"&r="+Math.random(),function(data){
			if(data && data.wenhao){
				$(widget).val(data.wenhao);
			}
		},"json");
	}
}

/******************************************文号控件 结束**************************************************/
/*
*对外调用接口 
*/
function  initWidget(callback){
	//初始化阅读控件
	try{
		generateReaderNames();
	}catch(e){
		
	}
	//初始化审批控件
	try{
		generateTdAdvice();
	}catch(e){
		
	}
	//初始化日历控件
	generateDateWidget();
	//初始化选择人员控件
	initSelectPerson();
	//初始化选择部门
	initSelectGroup();
	//初始化创建人信息
	initCreaterInfoWidget();
	//初始化文号
	initWenhaoWidget();
	//最后调用callback回调函数
	if(callback){
		callback();	
	}
}