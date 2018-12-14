//发送范围批量设置
var editor;
$(document).ready(function(){
	//创建编辑器
	var option = {
   		initialContent: '',//初始化编辑器的内容
   		minFrameHeight: 100, //设置高度
   		initialFrameHeight:200,
   		initialFrameWidth:"100%",
   		textarea: 'content'  //设置提交时编辑器内容的名字，之前我们用的名字是默认的editorValue
	};
	editor=new baidu.editor.ui.Editor(option);
	editor.render("myEditor");
	//限制发送长度
	$("#pushContent").maxLength(50);
	//添加公司
	$("#addPushUsers").click(function() {
		openSelectUser(3,function(data){
			 data.forEach(function(value, key) {
              if(value.Type == "user"){
	               $("#userNames").append(value.Name+",");
	               var temp = $("#userIds").val();
	               temp+=value.Id+",";
	               $("#userIds").val(temp);
              }
           });
		});
	});
	//清空公司
	$("#clearPushUsers").click(function() {
		$("#userIds").val("");
		$("#userNames").val("");
		return false;
	});
	//点击增加推送
	$("#addPush").click(function() {
		addPush();
		return false;
	});
	//显示部门
	$("input[name='rangeType']").click(function(){
		if($(this).val()==5){
			$("#sendGroups").show();
		}else{
			$("#sendGroups").hide();
		}
	});
	//清空
	$("#removePush").click(function(){
		window.location.href = basePath + "logined/jpush/pushList.jsp";
	});
});
//增加推送
function addPush(){
		var c = editor.getContent();// 内容
		var showContent = editor.getContentTxt();// 纯文本内容
		if(c.length>1500){
			art.dialog.alert("内容长度过长!");
 			return false;
		}
		var subject=$("#subject").val();
		if(subject==""){
 			art.dialog.alert("请输入主题!");
 			return false;
 		}
		var pushContent=c;
		if(pushContent==""){
 			art.dialog.alert("请输入内容!");
 			return false;
 		}
 		var userIds=$("#userIds").val();
 		if(userIds==""){
 			art.dialog.alert("请选择发送范围!");
 			return false;
 		}
		if (userIds.length>0) {
			art.dialog.confirm('确认要推送活动吗？', function() {
				var paramData = {
					'userIds' : userIds.toString(),
					'pushInfo.subject' : subject,
					'pushInfo.showContent' : showContent,
					'pushInfo.pushContent' : pushContent
				};
					$.ajax({
						url : basePath + "push/addPush.action",
						type : "post",
						dataType : 'text',
						data : paramData,
						beforeSend:function(){
							$("body").lock();
						},
						complete:function(){
							$("body").unlock();
						},
						success : function(data) {	
							if(data==1){
							art.dialog.alert("添加推送成功!",function(){
								window.location.href = basePath + "logined/jpush/pushList.jsp";			
							});
							}else{art.dialog.alert("添加推送失败！")}
						},
						error:function(){
							art.dialog.alert("系统异常，请稍后再试");
						}
					});
			});
	    }
}
