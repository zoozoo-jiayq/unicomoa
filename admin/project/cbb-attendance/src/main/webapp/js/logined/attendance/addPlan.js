$(document).ready(function(){
	$("#back").click(function(){
		window.history.go(-1);
	});
	$("#save").click(function(){
		submit();
	});
	
	$("#mon,#tues,#wed,#thur,#fri,#sat,#sun").delegate(":checkbox[name='checkbox']", "click", function(event){
	   	checkChange(this);
		event.stopPropagation();
    });
	$("#mon,#tues,#wed,#thur,#fri,#sat,#sun").delegate("a", "click", function(event){
	   	changeTime(this);
		event.stopPropagation();
    });
	
	$("#setLocation").click(function(){
		setLocation();
	});
});


/**
 * 添加按钮
 * @return
 */
function selectUser() {
	openSelectUser(3, selectUserCallBack, null, $("#userIds").val());
}

/**
 * 添加按钮(回调函数)
 * 
 * @param data
 * @return
 */
function selectUserCallBack(data) {
	var ids = ',';
	var count=0;
	$(data).each(function(i,item){
			if(item.id){
				ids += item.id + ',';
				count++;
			}
	});
	$("#userIds").val(ids);
	if(count==0){
		$("#users").val("请选择");
	}else{
		$("#users").val("已选择"+count+"人");
	}
}

/**
 * 提交考勤方案 
 */
function submit(){
	var subject=$("#subject").val();
	var userIds=$("#userIds").val();
	var location=$("#location").val();
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	var range=$("#range").val();
	var commonOn=$("#commonOn").val();
	var commonOff=$("#commonOff").val();
	if(!subject){
		art.dialog.alert("考勤方案名称不能为空");
		return false;
	}
	if(!userIds){
		art.dialog.alert("请选择人员");
		return false;
	}
	if(!location || !longitude || !latitude){
		art.dialog.alert("考勤地点不能为空");
		return false;
	}
	if(!commonOn){
		art.dialog.alert("上班时间不能为空");
		return false;
	}
	if(!commonOff){
		art.dialog.alert("下班时间不能为空");
		return false;
	}
	var param={
			'vo.subject':subject,
			'vo.userIds':userIds,
			'vo.commonOn':commonOn,
			'vo.commonOff':commonOff,
			'vo.location':location,
			'vo.longitude':longitude,
			'vo.latitude':latitude,
			'vo.range':range
		};

	//封装每日打卡时间
	$("#mon,#tues,#wed,#thur,#fri,#sat,#sun").each(function(e){
		var id=$(this).attr("id");
		var rest=0;
		if($(this).find("[name='checkbox']:checked").length<=0){
			rest=1;
		}
		var onTime=$(this).find("font").eq(0).html();
		var offTime=$(this).find("font").eq(1).html();
		param["vo."+id+"Rest"]=rest;
		param["vo."+id+"On"]=onTime;
		param["vo."+id+"Off"]=offTime;
	});
	
	$.ajax({
		url:basePath+"attendance/checkUserIds.action",
		data:{
			userIds:userIds
		},
		type:"post",
		dataType:"text",
		success:function(data){
			if(data){
				var obj = eval("("+data+")");
				if(obj.count>0){
					art.dialog({
					    id: 'checkDialog',
					    content: obj.failUserNames+"等"+obj.count+"人已在别的考勤组，是否将他们移到此考勤组？",
					    height:200,
					    width:400,
					    button: [
					        {
					            name: '不移',
					            callback: function () {
					            	if(!obj.successUserIds || obj.successUserIds==','){
					            		art.dialog.alert("请重新选择人员",function(){
					            			art.dialog.close();
					            		});
					            	}else{
					            		param["vo.userIds"]=obj.successUserIds;
					            		subAjax(param);
					            		art.dialog.close();
					            	}
					            	return true;
					            }
					        },
					        {
					            name: '移入',
					            callback: function () {
					            	param["vo.userIds"]=userIds;
					            	subAjax(param);
					            	art.dialog.close();
					                return true;
					            },
						        focus: true
					        }
					    ]
					});
				}else{
					subAjax(param);
				}
			}else{
				art.dialog.alert("操作失败");
			}
		}
	});
}

function subAjax(param){
	$.ajax({
		url:basePath+"attendance/savePlan.action",
		data:param,
		type:"post",
		dataType:"text",
		success:function(data){
			if(data==1){
				art.dialog.alert("操作成功！",function(){
					window.history.go(-1);
				});
			}else{
				art.dialog.alert("操作失败！");
				return false;
			}
		}
	});
}

/**
 * 休息状态变更
 * @param obj
 */
function checkChange(obj){
	var commonOn=$("#commonOn").val();
	var commonOff=$("#commonOff").val();
	$(obj).parent().parent().find("font").eq(0).html(commonOn);
	$(obj).parent().parent().find("font").eq(1).html(commonOff);
	if($(obj).is(':checked')){
		$(obj).parent().parent().find("span").eq(0).show();
		$(obj).parent().parent().find("span").eq(1).hide();
	}else{
		$(obj).parent().parent().find("span").eq(1).show();
		$(obj).parent().parent().find("span").eq(0).hide();
	}
}

/**
 * 修改某日时间
 */
function changeTime(obj){
	var week=$(obj).parent().parent().attr("id");
	var onTime=$(obj).parent().parent().find("font").eq(0).html();
	var offTime=$(obj).parent().parent().find("font").eq(1).html();
	art.dialog.open(basePath+"logined/attendance/setTime.jsp?week="+week+"&onTime="+onTime+"&offTime="+offTime,{
	      title: '考勤时间设定',
	      width:600,
	      height:300,
	      opacity:0.08,
	      lock:true,
	      button: [{
	          name: '确 定',
	          focus: true,
	          callback: setTime
		  }, {
				name : '取 消'
		 }]
		});
}

/**
 * 设置某天时间R
 */
function setTime(){
	var iframe = this.iframe.contentWindow;
	var week=$(iframe.document).find("#week").val();
	var onTime=$(iframe.document).find("#onTime").val();
	var offTime=$(iframe.document).find("#offTime").val();
	
	$("#"+week).find("font").eq(0).html(onTime);
	$("#"+week).find("font").eq(1).html(offTime);
	$("#"+week).find("span").eq(0).show();
	$("#"+week).find("span").eq(1).hide();
	$("#"+week).find(":checkbox[name='checkbox']").attr("checked",true);
}

function picked(index){
	if(index==1){
		$(".day").each(function(e){
			$(this).find("font").eq(0).html($("#commonOn").val());
		});
	}else{
		$(".day").each(function(e){
			$(this).find("font").eq(1).html($("#commonOff").val());
		});
	}
}

/**
 * 设置考勤地点
 */
function setLocation(){
	var location=$("#location").val();
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	art.dialog.open(basePath+"logined/attendance/setLocation.jsp?location="+location+"&longitude="+longitude+"&latitude="+latitude,{
	      title: '设置考勤地点',
	      width:800,
	      height:400,
	      opacity:0.08,
	      lock:true,
	      button: [{
	          name: '确 定',
	          focus: true,
	          callback: setLocationCallback
		  }, {
				name : '取 消'
		 }]
		});
}

/**
 * 修改考勤位置
 */
function setLocationCallback(){
	var iframe = this.iframe.contentWindow;
	var location=$(iframe.document).find("#locationStr").html();
	var longitude=$(iframe.document).find("#longitude").val();
	var latitude=$(iframe.document).find("#latitude").val();
	
	if(!location || !longitude || !latitude){
		art.dialog.alert("请选择考勤地点");
		return false;
	}
	$("#location").val(location);
	$("#longitude").val(longitude);
	$("#latitude").val(latitude);
	$("#locationStr").html(location);
	
	$("#setLocation").html("更改");
}