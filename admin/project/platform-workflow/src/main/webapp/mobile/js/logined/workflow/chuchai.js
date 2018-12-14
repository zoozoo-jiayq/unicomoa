var bTime = "";
var eTime = "";
$(document).ready(function(){
	//获取默认开始时间，结束时间
	var date=addDate(new Date(),1);
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	bTime=year+"-"+month+"-"+day+" 上午";
	eTime=year+"-"+month+"-"+day+" 下午";
	$("#beginTime").val(new StringDate(bTime).format());
	$("#endTime").val(new StringDate(eTime).format());
	if($("#beginTime").val()!=null&&$("#beginTime").val()!=""&&$("#endTime").val()!=null&&$("#endTime").val()!=""){
		$("#days").html(new StringDate(eTime).diff(new StringDate(bTime)));
	}
	//设置选择时间控件
	$("#beginTime").click(function(){
		h5Adapter.selectDateTimeHalf(bTime, "yyyy-MM-dd a", function(da){
			if(da.isSuccess){
				//判断选择时间是否小于当前时间
				if(compareTime(da.selectTime)){
					return;
				};
				var cr = new StringDate(eTime).compare(new StringDate(da.selectTime));
				if(cr<0){
					h5Adapter.tips("开始时间不能大于结束时间!",500);
					return;
				}
				bTime = da.selectTime;
				processTime("beginTime",bTime);
			}
		});
	});
	//设置结束时间控件
	$("#endTime").click(function(){
		h5Adapter.selectDateTimeHalf(eTime, "yyyy-MM-dd a", function(da){
			if(da.isSuccess){
				//判断选择时间是否小于当前时间
				if(compareTime(da.selectTime)){
					return;
				};
				var cr = new StringDate(da.selectTime).compare(new StringDate(bTime));
				if(cr<0){
					h5Adapter.tips("开始时间不能大于结束时间!",500);
					return;
				}
				eTime = da.selectTime;
				processTime("endTime",eTime);
			}
		});
	});
});
function processTime(id,selectTime){
	var r = new StringDate(selectTime).format();
	$("#"+id).val(r);
	if($("#beginTime").val()!=null&&$("#beginTime").val()!=""&&$("#endTime").val()!=null&&$("#endTime").val()!=""){
		$("#days").html(new StringDate(eTime).diff(new StringDate(bTime)));
	}
}
function compareTime(selectTime){
	var nowDate=new Date();
	var timeAry=selectTime.split(" ");
	var selDate=new Date(timeAry[0].replace(/-/g,"/"));
	if(timeAry[1]=="上午"){
		selDate.setHours(0, 0, 0, 0);
	}
	if(timeAry[1]=="下午"){
		selDate.setHours(11, 59, 59, 999);
	}
	if(selDate.valueOf()<nowDate.valueOf()){
		h5Adapter.tips("选择时间不能小于当前时间",500);
		return true;
	}else{
		return false;
	}
}
function getFormData(){
	//目的地
	var destination = $.trim($("#destination").val());
	//开始时间
	var beginTime = $("#beginTime").val();
	//结束时间
	var endTime = $("#endTime").val();
	//出差天数
	var days = $("#days").html();
	//出差原因
	var reason = $("#reason").val();
	var data = new Object();
	data.destination = destination;
	data.beginTime =  beginTime;
	data.endTime= endTime;
	data.days = days;
	data.reason =  reason;
	data.imgs = photos;
	return JSON.stringify(data);
}

//提交
$("#sbn").click(function(){
	$("#sbn").attr("disabled",true);
	var flag = check();
	if(!flag){
		$("#sbn").attr("disabled",false);
		retun;
	}
	var datas = getFormData();
	var userId = h5Adapter.getItemValue("currentUserId");
	//发送数据
	jQuery.ajax({
		   type: "POST",
		   url: basePath+"baseworkflow/start.c?_clientType=wap",
		   data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"chuchai"},
		   success: function(msg){
		     if(msg.indexOf("100||")==0){
		    	 window.location.href = basePath+"mobile/logined/workflow/myStartList.jsp?_clientType=wap";
		     }else{
		    	 $("#sbn").attr("disabled",false);
		     }
		   }
	});
});

//验证
function check(){
	var destination = $.trim($("#destination").val());
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var reason = $("#reason").val();
	if(destination == ""||destination == null){
		h5Adapter.tips('请输入出差地点')
		return false;
	}else if(destination.length>=255){
		h5Adapter.tips("出差地点应小于255个字")
		return false;
	}else if(beginTime == ""||beginTime == null){
		h5Adapter.tips("请选择开始时间")
		return false;
	}else if(endTime == ""||endTime == null){
		h5Adapter.tips("请选择结束时间")
		return false;
	}else if(reason == ""||reason == null){
        h5Adapter.tips("请输入出差原因")
        return false;
    }else if(reason.length>=500){
        h5Adapter.tips("出差原因应小于500个字")
        return false;
    }else if(!userIds){
		h5Adapter.tips("请选择人员");
		return false;
	}else{
		return true;
	}
	
}


//从后台加载表单数据
function loadData(){
	h5Adapter.getItem($("#instanceId").val(), function(result){
		if(result){
			//showDataWithHTML(result);
		}
	});
	$.ajax({
		url:basePath+"baseworkflow/view.c?_clientType=wap&instanceId="+$("#instanceId").val(),
		type:"POST",
		cache:false,
		success:function(msg){
			if(msg.indexOf("100||")==0){
				var result = msg.substring(5);
				showDataWithHTML(result);
				h5Adapter.setItem($("#instanceId").val(), result);
			}
		}
	});
}
function showDataWithHTML(result){
	result =jQuery.parseJSON(result);
	//发起人的姓名和头像
	var createrName = result.createrName;
	var createrPhoto = result.createrPhoto;
    var imgName=createrName;
	 var col = Math.floor(Math.random()*7+1);
	 if(createrName!=null&&createrName.length>2){
	 	imgName=createrName.substring(createrName.length-2,createrName.length);
	 }
	 var col=letterCode(imgName);
	 $("#imgName").html(imgName);
	 $("#imgDiv").addClass("head-bg-"+col);
	 if(createrPhoto!=null&&createrPhoto!=""&&createrPhoto!=undefined){
		 $("#photo").attr("src",createrPhoto);
		 $("#imgName").hide();
	 }else{
		 $("#photo").hide();
	 }
	$("#userName").html(createrName);
	//表单数据各自流程按照自己的表单解析
	//目的地和天数：北京<i>2.5</i>天
	var formData = jQuery.parseJSON(result.formData);
	var destion = formData.destination;
	var days = formData.days;
	$("#destion").html(destion+"<i>"+days+"</i>天");
	//时间范围:6月8号&nbsp;上午&nbsp;-&nbsp;6月9号&nbsp;下午
	var beginTime = formData.beginTime;
	var endTime = formData.endTime;
	$("#beginAndEndTime").html(beginTime+"&nbsp;-&nbsp;"+endTime);
	//原因
	var reason = formData.reason;
	$("#reason").html(reason);
	//图片
	processPhoto(formData.imgs);
	//审批历史
	processUserInfos(result.history,result.totalState,result.approverId);
}
 
/**
 * webview加载完毕初始化导航条按钮
 */
function loadSuccess(isNetOk){
	h5Adapter.onHeaderButton(null,{"type":"back","onClickMethod":"goBack"});
}

//退出调查问卷模块
function goBack(){
	var operation=$("#operation").val();
	if(operation!=null&&operation!=""&&operation!=undefined&&operation=="view"){
		window.location.href = basePath+"mobile/logined/workflow/myApproveList.jsp?_clientType=wap&flag=processed";
	}else{
		h5Adapter.backLast();
	}
}