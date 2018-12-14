jQuery(document).ready(function(){

	//添加人员
	$("#addUser").click(function(){
		openSelectUser(3,function(data){
			 $("#users").val("");
			 $("#userIds").val("");
			 data.forEach(function(value, key) {
               if(value.Type == "user"){
	               $("#users").append(value.Name+",");
	               var temp = $("#userIds").val();
	               temp+=value.Id+",";
	               $("#userIds").val(temp);
               }
            });
		},null,$("#userIds").val());
	});
	
	//清空人员
	$("#removeUser").click(function(){
		$("#users").html("");
		$("#userIds").val("");
	});

	//添加部门
	$("#addGroup").click(function(){
		openSelectUser(1,function(data){
			$("#groups").html("");
			$("#groupIds").val("");
			data.forEach(function(value, key) {
               if((value.Type == "group") && (value.Id!=0)){
	               $("#groups").append(value.Name+",");
	               var temp = $("#groupIds").val();
	               temp+=value.Id+",";
	               $("#groupIds").val(temp);
               }
            });
		},null,$("#groupIds").val());
	});
	
	//清空部门
	$("#removeGroup").click(function(){
		$("#groups").html("");
		$("#groupIds").val("");
	});

	//添加角色
	$("#addRole").click(function(){
		openSelectUser(2,function(data){
			$("#roles").html("");
			$("#roleIds").val("");
			data.forEach(function(value, key) {
               if(value.Type == "role"){
	               $("#roles").append(value.Name+",");
	               var temp = $("#roleIds").val();
	               temp+=value.Id+",";
	               $("#roleIds").val(temp);
               }
            });
		},null,$("#roleIds").val());
	});

	//清空角色
	$("#removeRole").click(function(){
		$("#roles").html("");
		$("#roleIds").val("");
	});

});