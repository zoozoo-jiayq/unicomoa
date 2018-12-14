jQuery(document).ready(function($){
	//添加人员
	$("#addUser").click(function(){
		openSelectUser(3,function(data){
			 data.forEach(function(value, key) {
               if(value.Type == "user"){
	               $("#users").append(value.Name+",");
	               var temp = $("#userIds").val();
	               temp+=value.Id+",";
	               $("#userIds").val(temp);
               }
            });
		});
	});
	//删除人员
	$("#cleanUsers").click(function(){
		$("#users").html("");
		$("#userIds").val("");
	});

	//下拉树
	$.selectTree(basePath+"user/selectUser.action?type=1&showType=3","selectUser","menuContent","treeDemo");

	$("input[name='nextAction']").click(function(){
		var value = $(this).val();
		if(value == "to 领导批阅"){
			$("#selectUser").show();
			$("#multiSelect").hide();
		}
		if(value == "to 收文阅读"){
			$("#selectUser").hide();
			$("#multiSelect").show();	
		}
	});
});

 
//获取登记数据
var getData = function(){
	var taskIds = $("#taskIds").val();
	var nextAction = $('input[name="nextAction"]:checked').val();
	var users = "";
	users = $("#selectUser_hidden").val();
	return {
		taskIds   :taskIds,
		nextAction:nextAction,
		userIds   :users
	};
};