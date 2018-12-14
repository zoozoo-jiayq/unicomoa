function getFormData(){
	//物品用处
	var useful = $.trim($("#useful").val());
	//备注
	var remark = $("#remark").val();
	var data = new Object();
	data.useful = useful;
	data.remark = remark;
	data.imgs = photos;
	return JSON.stringify(data);
}

//提交
$("#sbn").click(function(){
	$("#sbn").attr("disabled",true);
	var flag = check();
	if(!flag){
		$("#sbn").attr("disabled",false);
		return;
	}
	var datas = getFormData();
	var userId = h5Adapter.getItemValue("currentUserId");
	//发送数据
	jQuery.ajax({
		   type: "POST",
		   url: basePath+"baseworkflow/start.c?_clientType=wap",
		   data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"lingyong"}, 
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
	var useful = $.trim($("#useful").val());
	var remark = $("#remark").val();
	if(useful == ""||useful == null){
		h5Adapter.tips('请输入物品用处');
		return false;
	}
	if(useful.length>=255){
		h5Adapter.tips('物品用处应小于255个字');
		return false;
	}
	if(remark == ""||remark == null){
		h5Adapter.tips('请输入物品、数量以及领用时间');
		return false;
	}
	if(remark.length>=500){
		h5Adapter.tips('物品、数量以及领用时间应小于500个字');
		return false;
	}
	if(!userIds){
		h5Adapter.tips("请选择人员");
		return false;
	}
	return true;
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
		 success: function(msg){
			if(msg.indexOf("100||")==0){
			   var result = msg.substring(5);
			   showDataWithHTML(result);
				h5Adapter.setItem($("#instanceId").val(), result);
			}
		 }
		 
   });	
}

function showDataWithHTML(result){
	 result = jQuery.parseJSON(result);
	   //发起人的姓名和头像
	   var 	createrName = result.createrName;
	   var  createrPhoto = result.createrPhoto;
	     var imgName=createrName;
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
	   //表单解析
	   var formData = jQuery.parseJSON(result.formData);
	   //用处
	   var useful = formData.useful;
	   $("#useful").html(useful); 
	   //备注
	   var remark = formData.remark;
	   $("#remark").html(remark);
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