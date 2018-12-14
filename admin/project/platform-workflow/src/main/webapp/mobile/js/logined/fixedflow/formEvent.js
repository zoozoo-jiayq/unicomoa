//移除请选择颜色
function removeSelectStyle(obj){
	obj.removeClass("showSelect"); 
}

/*
 * 表单事件js
 */
//显示单选列表
function showRadioHtml(name,cnName,radios){
	var templateHtml='<div id="'+name+'" class="mui-popover mui-top-zero">';
	templateHtml+='<header class="mui-bar mui-bar-nav qy-head">';
	templateHtml+='<a class="mui-icon mui-icon-left-nav mui-pull-left qy-back" name="iconClose"></a>';
	templateHtml+='<h1 class="mui-title">'+cnName+'</h1>';
	templateHtml+='</header>';
	templateHtml+='<div class="mui-scroll-wrapper">';
	templateHtml+='<div class="mui-scroll">';
	templateHtml+='<ul class="mui-table-view" style="padding-top:37px;">';
	for(var j=0;j<radios.length;j++){
		templateHtml+='<li class="mui-table-view-cell" onclick="selectMenu(this)" name='+name+'>'+radios[j]+'</li>';
	}
	templateHtml+='</ul></div></div></div>';
	return templateHtml;
}
//显示多选列表
function showCheckHtml(name,cnName,radios){
	var templateHtml='<div id="'+name+'" class="mui-popover mui-top-zero">';
	templateHtml+='<header class="mui-bar mui-bar-nav qy-head">';
	templateHtml+='<a class="mui-icon mui-icon-left-nav mui-pull-left qy-back" name="iconClose"></a>';
	templateHtml+='<h1 class="mui-title">'+cnName+'</h1>';
	templateHtml+='</header>';
	templateHtml+='<div class="mui-scroll-wrapper">';
	templateHtml+='<div class="mui-scroll">';
	templateHtml+='<ul class="mui-table-view check" style="padding-top:37px;">';
	for(var j=0;j<radios.length;j++){
		templateHtml+='<li class="mui-table-view-cell" onclick="selectChk(this);"><span>'+radios[j]+'</span><i class="checkoff mui-pull-right"></i></li>';
	}
	templateHtml+='</ul>';
	templateHtml+='</div></div>';
	templateHtml+='<nav class="mui-bar mui-bar-tab fixed-btn sel-user">';
	templateHtml+='<button class="mui-btn" onclick="cancle(\''+name+'\')">取消</button>';
	templateHtml+='<button class="mui-btn mui-btn-blue" onclick="sureChk(\''+name+'\')">确定</button>';
	templateHtml+='</nav>';
	templateHtml+='</div>';
	return templateHtml;
}
//打开单选，多选页面
function openSelectHtml(type,name,cnName,radios,obj){
	var defaltValue=$(obj).children("span").html();
	if(defaltValue&&(defaltValue=="请选择"||defaltValue=="请选择（必选）")){
		defaltValue="";
	}
	var isDetail=0;
	if(!$(obj).children("span").hasClass("applyForm")){
		isDetail=1;
	}
	mui.openWindow({
		url:"select.html",
		id:"select",
		extras:{
			type:type,
			name:name,
			cnName:cnName,
			radios:radios,
			defaltValue:defaltValue,
			isDetail:isDetail
		},
		show:{
			autoShow:true,//页面loaded事件发生后自动显示，默认为true
		    aniShow:"slide-in-right",//页面显示动画，默认为”slide-in-right“；
		    duration:300//页面动画持续时间，Android平台默认100毫秒，iOS平台默认200毫秒；
		},
		waiting:{
			 autoShow:false,//自动显示等待框，默认为true
		}
	});
}
//检查表单元素是否可以编辑
function checkFormEdit(){
	//文本元素
	$("#formDiv li input.applyForm").each(function(){
		if($(this).attr("canEdit")=="true"){
			$(this).prop("disabled",false);
		}else{
			$(this).prop("disabled",true);
			$(this).prop("placeholder",'');
		}
	})
	$("#formDiv li textarea.applyForm").each(function(){
		if($(this).attr("canEdit")=="true"){
			$(this).prop("disabled",false);
		}else{
			$(this).prop("disabled",true);
			$(this).prop("placeholder",'');
		}
	})
}
//绑定表单中所有选择事件
function bindSelectEvent(){
	$("#formDiv li a").each(function(){
		if($(this).attr("canEdit")=="false"){
			if($(this).children("span").attr("show")!=1){
				$(this).children("span").html("");
			}
			$(this).removeClass("mui-navigate-right");
		}else{
			//绑定事件
			if($(this).hasClass("calendar")){
				$(this).unbind("tap");
				$(this).bind("tap",function(){
					selectTime(this);
				});
			}else if($(this).hasClass("group")){
				$(this).unbind("tap");
				$(this).bind("tap",function(){
					selectGroup(this);
				});
			}else if($(this).hasClass("users")){
				$(this).unbind("tap");
				$(this).bind("tap",function(){
					selectUser(this);
				});
			}
		}
	});
}
//绑定关闭弹框事件
function bindCloseIcon(){
	$("header a[name='iconClose']").each(function(){
		this.addEventListener("tap",function(){
			var id=$(this).parent().parent().attr("id");
			setTimeout(function(){
				mui("#"+id).popover("toggle"); 
			},300);
		});
	});
}
//多选取消按钮
function cancle(id){
	$("#formDiv").show();
	mui('#'+id).popover('toggle');
}
//单选、下拉选择
function selectMenu(name,cnName){
	$("#formDiv span[name='"+name+"']").html(cnName);
	removeSelectStyle($("#formDiv span[name='"+name+"']"));
}
//初始化日期控件
function initcalendar(){
	$("#formDiv li a.calendar").each(function(){
		var dateFormat=$(this).attr("dateFormat");
		var calendarRangeFlag=$(this).attr("calendarRangeFlag");
		var dateMap=getNowDateMap();
		var nowDate = new Date();
		if(dateFormat){
			if(dateFormat=="yyyy-MM"){
				$(this).children("span").html($.formatDate(dateFormat,nowDate));
			}else if(dateFormat=="yyyy-MM-dd AM/PM"){
				if(calendarRangeFlag=="end"){
					$(this).children("span").html("明天 上午");
				}else{
					$(this).children("span").html("今天 上午");
				}
				
			}else if(dateFormat=="yyyy-MM-dd"){
				if(calendarRangeFlag=="end"){
					//结束时间向后延一天
					$(this).children("span").html("明天");
				}else{
					$(this).children("span").html("今天");
				}
			}else{
				dateFormat=dateFormat.replace("HH","hh");
				if(calendarRangeFlag=="end"){
					//结束时间向后延一个小时
					nowDate.setTime(nowDate.getTime()+1000*60*60);
				}
				var nowValue=$.formatDate(dateFormat,nowDate);
				if(nowValue.length>=10){
					var nowValue10=nowValue.substring(0,10);
					if(dateMap.get(nowValue10)){
						nowValue=nowValue.replace(nowValue10,dateMap.get(nowValue10));
					}
				}
				$(this).children("span").html(nowValue);
			}
			removeSelectStyle($(this).children("span"));
		}
	});
}
//初始化日期控件
function addInitcalendar(index){
	$("#formDiv div.mx_detail ul").eq(index).find("li a.calendar").each(function(){
		var dateFormat=$(this).attr("dateFormat");
		var calendarRangeFlag=$(this).attr("calendarRangeFlag");
		var dateMap=getNowDateMap();
		var nowDate = new Date();
		if(dateFormat){
			if(dateFormat=="yyyy-MM"){
				$(this).children("span").html($.formatDate(dateFormat,nowDate));
			}else if(dateFormat=="yyyy-MM-dd AM/PM"){
				if(calendarRangeFlag=="end"){
					$(this).children("span").html("明天 上午");
				}else{
					$(this).children("span").html("今天 上午");
				}
				
			}else if(dateFormat=="yyyy-MM-dd"){
				if(calendarRangeFlag=="end"){
					//结束时间向后延一天
					$(this).children("span").html("明天");
				}else{
					$(this).children("span").html("今天");
				}
			}else{
				dateFormat=dateFormat.replace("HH","hh");
				if(calendarRangeFlag=="end"){
					//结束时间向后延一个小时
					nowDate.setTime(nowDate.getTime()+1000*60*60);
				}
				var nowValue=$.formatDate(dateFormat,nowDate);
				if(nowValue.length>=10){
					var nowValue10=nowValue.substring(0,10);
					if(dateMap.get(nowValue10)){
						nowValue=nowValue.replace(nowValue10,dateMap.get(nowValue10));
					}
				}
				$(this).children("span").html(nowValue);
			}
			removeSelectStyle($(this).children("span"));
		}
	});
}
//选择时间
function selectTime(obj){
	var dateMap=getNowDateMap();
	var defaultTime=$(obj).children("span").html();
	var dateFormat=$(obj).attr("dateFormat");
	var calendarRangeFlag=$(obj).attr("calendarRangeFlag");
	if(!dateFormat||dateFormat=="undefined"||dateFormat=="null"){
		dateFormat="yyyy-MM-dd HH:mm";
	}
	if(!defaultTime||defaultTime=="请选择"||defaultTime=="请选择（必填）"){
		defaultTime="";
	}else{
		defaultTime=defaultTime.replace("今天",dateMap.get("今天"));
	 	defaultTime=defaultTime.replace("明天",dateMap.get("明天"));
	}
	
	var defaultAry=[defaultTime,dateFormat];
	if(dateFormat=="yyyy-MM-dd AM/PM"){
		defaultAry=[defaultTime,"yyyy-MM-dd a"];
		plus.qytxplugin.showDateTimeHalf(defaultAry,function(data){
			//$(obj).children("span").html(data.date);
			//日期结束时间大于开始时间
			if(calendarRangeFlag=="start"||calendarRangeFlag=="end"){
				var other = $(obj).attr("other");
				var cr=compareCalendar(calendarRangeFlag,other,dateFormat,data.date);
				if (cr < 0) {
					mui.toast("开始时间不能大于结束时间!");
					return;
				}
			}
			//时间替换
			var returnDate=data.date;
			if(returnDate.length>=10){
				var returnDate10=returnDate.substring(0,10);
				if(dateMap.get(returnDate10)){
					returnDate=returnDate.replace(returnDate10,dateMap.get(returnDate10));
				}
			}
			$(obj).children("span").html(returnDate);
			removeSelectStyle($(obj).children("span"));
			//日期差自动计算
			if(calendarRangeFlag=="start"||calendarRangeFlag=="end"){
				caculateRange();
			}
		});
	}else{
		plus.qytxplugin.selectDateTime(defaultAry,function(data){
			//$(obj).children("span").html(data.date);
						//日期结束时间大于开始时间
			if(calendarRangeFlag=="start"||calendarRangeFlag=="end"){
				var other = $(obj).attr("other");
				var cr=compareCalendar(calendarRangeFlag,other,dateFormat,data.date);
				if (cr < 0) {
					mui.toast("开始时间不能大于结束时间!");
					return;
				}
			}
			//时间替换
			var returnDate=data.date;
			if(returnDate.length>=10){
				var returnDate10=returnDate.substring(0,10);
				if(dateMap.get(returnDate10)){
					returnDate=returnDate.replace(returnDate10,dateMap.get(returnDate10));
				}
			}
			$(obj).children("span").html(returnDate);
			removeSelectStyle($(obj).children("span"));
			//日期差自动计算
			if(calendarRangeFlag=="start"||calendarRangeFlag=="end"){
				caculateRange();
			}
		});
	}
	
}
//结束时间开始时间比较
function compareCalendar(calendarRangeFlag, other, dateFormat, date) {
	var dateMap = getNowDateMap();
	var startTime = "";
	var endTime = "";
	if (calendarRangeFlag == "start") {
		startTime = date;
		endTime = $("#formDiv span[cnname='" + other + "']").html();
		endTime = endTime.replace("今天", dateMap.get("今天"));
		endTime = endTime.replace("明天", dateMap.get("明天"));
	} else {
		endTime = date;
		startTime = $("#formDiv span[cnname='" + other + "']").html();
		startTime = startTime.replace("今天", dateMap.get("今天"));
		startTime = startTime.replace("明天", dateMap.get("明天"));
	}
	if (!startTime || startTime == "请选择" || startTime == "请选择（必选）") {
		startTime = "";
	}
	if (!endTime || endTime == "请选择" || endTime == "请选择（必选）") {
		endTime = "";
	}
	if (dateFormat == "yyyy-MM") {
		endTime = endTime + "-01";
		startTime = startTime + "-01";
	}
	var cr = new StringDate(endTime).compare(new StringDate(startTime));
	return cr;
}
//选择人员
function selectUser(obj){
	var defaultUserIds=$(obj).attr("userIds");
	plus.qytxplugin.selectUsers(defaultUserIds,1,function(data){
		if(data){
			var userIds="";
			var userNames="";
			for(var i=0;i<data.length;i++){
				var userId=data[i].userId;
				var userName=data[i].userName;
				userIds+=userId+",";
				userNames+=userName+",";
			}
			if(userIds!=""){
				userIds=userIds.substring(0,userIds.length-1);
			}
			if(userNames!=""){
				userNames=userNames.substring(0,userNames.length-1);
			}
			$(obj).attr("userIds",userIds);
			$(obj).children("span").html(userNames);
			removeSelectStyle($(obj).children("span"));
		}
		
	});
}
//选择部门
function selectGroup(obj){
	var defaultGroupIds=$(obj).attr("groupIds");
	plus.qytxplugin.selectGroups(defaultGroupIds,0,function(data){
		if(data){
			var groupIds="";
			var groupNames="";
			for(var i=0;i<data.length;i++){
				var groupId=data[i].groupId;
				var groupName=data[i].groupName;
				groupIds+=groupId+",";
				groupNames+=groupName+",";
			}
			if(groupIds!=""){
				groupIds=groupIds.substring(0,groupIds.length-1);
			}
			if(groupNames!=""){
				groupNames=groupNames.substring(0,groupNames.length-1);
			}
			$(obj).attr("groupIds",groupIds);
			$(obj).children("span").html(groupNames);
			removeSelectStyle($(obj).children("span"));
		}
		
	});
}
//选择复选框确定
function sureChk(name,chkCont){
	var required=$("#formDiv span[name='"+name+"']").attr("require");
	if(chkCont==""){
		if(required=="true"){
			chkCont="请选择（必选）";
		}else{
			chkCont="请选择";
		}
	}
	$("#formDiv span[name='"+name+"']").html(chkCont);
	//移除颜色
	removeSelectStyle($("#formDiv span[name='"+name+"']"));
}
//计算控件
function caculate(){
	//按照"+","-","*","/"拆分字符串
	var regexp = /[^\+-\/\*]*/gi;
	$("#formDiv div[expr]").each(function(){
			var expr   = $(this).attr("expr");
			var cnName   = $(this).attr("cnName");
			// var jingdu = $(this).attr("jingdu");
			var arr = expr.match(regexp);
			for (var i = 0; i < arr.length; i++) {
				var cnname = arr[i];
				if(cnname){
					var tempValue = $("#formDiv input[cnname='"+cnname+"']").val();
					if(tempValue==undefined){
						tempValue=$("#formDiv div[cnname='"+cnname+"']").html();
					}
					if(!tempValue){
						tempValue = 0 ;
					}
					tempValue = isNaN(tempValue)?0:tempValue;
					expr = expr.replace(arr[i],tempValue);
				}
			};
			$(this).html(eval('('+expr+')'));
	});
}
//日期计算控件
function caculateRange(){
	$("#formDiv div[calendarRangeExpr]").each(function(){
			var expr   = $(this).attr("calendarRangeExpr");
			var cnName   = $(this).attr("cnName");
			// var jingdu = $(this).attr("jingdu");
			var arr = expr.split("-");
			var endcnName=arr[0];
			var startcnName=arr[1];
			var endValue=$("#formDiv span[cnname='"+endcnName+"']").html();
			var startValue=$("#formDiv span[cnname='"+startcnName+"']").html();
			var dateFormat=$("#formDiv span[cnname='"+startcnName+"']").parent().attr("dateFormat");
			if(!endValue||!startValue||endValue=="请选择"||endValue=="请选择（必选）"||startValue=="请选择"||startValue=="请选择（必选）"){
				$(this).html("");
			}else{
				var dateMap=getNowDateMap();
	 			endValue=endValue.replace("今天",dateMap.get("今天"));
	 			endValue=endValue.replace("明天",dateMap.get("明天"));
	 			startValue=startValue.replace("明天",dateMap.get("明天"));
	 			startValue=startValue.replace("今天",dateMap.get("今天"));
	 			if(dateFormat=="yyyy-MM"){
	 				endValue=endValue+"-01";
	 				startValue=startValue+"-01";
	 				var days=DateDiff(endValue,startValue);
					$(this).html(days+"天");
	 			}else if(dateFormat=="yyyy-MM-dd"){
					var days=DateDiff(endValue,startValue);
					$(this).html(days+"天");
				}else if(dateFormat=="yyyy-MM-dd AM/PM"){
					var days=new StringDate(endValue).diff(new StringDate(startValue));
					$(this).html(days+"天");
				}else if(dateFormat=="yyyy-MM-dd HH:mm"||dateFormat=="yyyy-MM-dd HH:mm:ss"){
					var endDate=endValue.split(" ");
					endValue=endDate[0]+" "+endDate[1].split(":")[0]+":00:00";
					var startDate=startValue.split(" ");
					startValue=startDate[0]+" "+startDate[1].split(":")[0]+":00:00";
					var hours=HoursDiff(endValue,startValue);
					$(this).html(hours+"小时");
				}
			}
			
	});
}
/*
 * 检测表单元素
 */
function checkForm(){
	var flag=false;
	$(".applyForm").each(function(){
		var value=$(this).val();
		var valueHtml=$(this).html();
		if(valueHtml=="请选择"||valueHtml=="请选择（必填）"||valueHtml=="请选择（必选）"){
			valueHtml=""
		}
 		var cnName=$(this).attr("cnName");
 		var required=$(this).attr("require");
 		if(value.length>1000||valueHtml.length>1000){
 			mui.toast(cnName+"长度不超过1000");
 			flag=true;
 			return false;
 		}
 		if(required=="true"){
 			if($(this).hasClass("contBox")&&!valueHtml){
 				mui.toast(cnName+"为必选项");
	 			flag=true;
	 			return false;
 			}else if(!$(this).hasClass("contBox")&&!value){
 				mui.toast(cnName+"为必填项");
		 		flag=true;
		 		return false;
 			}
 		}
 	});
 	if(flag){
 		return false;
 	}
 	return true;
}
/**
 * 验证是否已选择下一步审批人
 */
function checkNextUser(){
	//下一步审批人
	var nextUsers=$("#nextUsers").val();
	if(nextUsers==null||nextUsers==""||nextUsers==undefined){
		mui.toast('请选择下一步审批人');
		return false;
	}
 	return true;
}
/**
  * 获取表单中所有元素的值
  * @returns {String}
  */
 function  getJSONVal(){
 	var jsonMap=new Map();
 	var val=0;
 	$(".applyForm").each(function(){
 		var value="";
 		if($(this).attr("type")=="text"||$(this).attr("type")=="number"||$(this).is("textarea")){
 			value=$(this).val();
 		}else if($(this).attr("type")=="detail"){
 			value=getDetailValue($(this).attr("name"));
 		}else{
 			value=$(this).html();
 		}
 		if(value=="请选择"||value=="请选择（必选）"){
 			value="";
 		}
 		if(value){
 			val=1;
 		}
 		//日期替换
 		if($(this).is(".calendarSpan")){
 			var dateMap=getNowDateMap();
 			value=value.replace("今天",dateMap.get("今天"));
 			value=value.replace("明天",dateMap.get("明天"));
 		}
 		jsonMap.put($(this).attr("name"),value);
 	});
 	
 	var res="";
	 //遍历map获取内容
	if(jsonMap.size()>0&&val==1){
		res+="[";
		for( var i=0;i<jsonMap.size();i++){
			var value =jsonMap.arr[i].value;
			var name =jsonMap.arr[i].key;
			if(value.indexOf("json||")>-1){
				value=value.substring(6,value.length);
				res+='{"name":"'+name+'","value":\''+value+'\'},';
			}else{
				res+='{"name":"'+name+'","value":"'+value+'"},';
			}
		}
	    res = res.substring(0, res.length-1);
		res+="]";
	}
    return res;
}
 //获取明细控件的值
 function getDetailValue(parentName){
 	var ary=new Array();
 	var len=$(".mx_detail ul.ul_"+parentName).length;
 	var res=0;
	for(var i=0;i<len;i++){
		var jsonMap=new Map();
		$(".mx_detail ul.ul_"+parentName).eq(i).find(".applyForm_"+parentName).each(function(j){
			var value="";
	 		if($(this).attr("type")=="text"||$(this).attr("type")=="number"||$(this).is("textarea")){
	 			value=$(this).val();
	 		}else{
	 			value=$(this).html();
	 		}
	 		if(value=="请选择"||value=="请选择（必选）"){
	 			value="";
	 		}
	 		//日期替换
	 		if($(this).is(".calendarSpan")){
	 			var dateMap=getNowDateMap();
	 			value=value.replace("今天",dateMap.get("今天"));
	 			value=value.replace("明天",dateMap.get("明天"));
	 		}
	 		if(value!=""){
	 			res=1;
	 		}
	 		jsonMap.put($(this).attr("cnName"),value);
		});
		ary[i]=jsonMap;
	}
	if(res==0){
		return "";
	}else{
		return "json||"+JSON.stringify(ary);
	}
 }
//选择样式添加
//选择人员
function selectUserStyle(obj,onlyOne){
	//单选人员
	if(onlyOne){
		$(obj).parent().parent().find("a").removeClass('on');
		$(obj).addClass('on');
		sure();
		
	//多选人员
	}else{
		$(obj).toggleClass('on');
	}
}
//复选框选择
function selectChk(obj){
	$(obj).toggleClass('on');
}

mui.plusReady(function(){
	//选择人员取消
	var userCancle=document.getElementById("userCancle");
	if(userCancle){
		userCancle.addEventListener("tap",function(){
			cancle('userList');
		});
	}
});	