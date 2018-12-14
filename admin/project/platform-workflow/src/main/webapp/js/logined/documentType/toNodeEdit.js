jQuery(document).ready(function(){
/*	$("li").click(function(){
		var select=$("li[class=current]").attr("id");
		switch(select){
		     case 'candidate':candidate();break;
		     case 'write':write();break;
		     case 'secret':secret();break;
		}
	});*/
	 $("input[type=radio][name='node.editDoc']").each(function() { 
         if ($(this).val() == $("#editDoc").val()) { 
             $(this).attr("checked", "checked"); 
         } 
     }); 
	$("#sure").click(function(){
		var wstr = "";
		$("#writeAbles").find("option").each(function(){
			var id = $(this).attr("value");
			if(id){
				wstr+=id+",";
			}
		});
		$("#writeableProperties").val(wstr);
		var sstr = "";
		$("#secretAbles").find("option").each(function(){
			var id = $(this).attr("value");
			if(id){
				sstr+=id+",";
			}

		});
		$("#secretProperties").val(sstr);
		$("#editDoc").val($("input[name='node.editDoc']:checked").val());
		$('#from').submit();
	});
	
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

	$("#removeRole").click(function(){
		$("#roles").html("");
		$("#roleIds").val("");
	});

	
	var ws = $("#writeableProperties").val();
	ws = ws.split(",");
	$("#wprops").find("option").each(function(){
		var temp = $(this).attr("value");
		for(var i=0;i<ws.length;i++){
			if(ws[i] == temp){
				$(this).remove();
			}
		}
	});

	$("#writeAbles").find("option").each(function(){
		if(!$(this).attr("value")){
			$(this).remove();
		}
	});

	//选择全部可写字段
	$("#allW").click(function(){
		$("#writeAbles").find("option").each(function(){
			$(this).attr("selected",true);
		});
	});
	$("#allWriteProps").click(function(){
		$("#wprops").find("option").each(function(){
			$(this).attr("selected",true);
		});
	});

	//向左选择
	$("#wleftSelect").click(function(){
		$("#wprops").find("option").each(function(){
			if($(this).attr("selected")){
				var id = $(this).attr("value");
				var name = $(this).html();
				var temp = document.createElement("option");
				temp.value = id;
				temp.text = name;
				document.getElementById("writeAbles").options.add(temp);
				$(this).remove();
			}
		});
	});

	//向右选择
	$("#wrightSelect").click(function(){
		$("#writeAbles").find("option").each(function(){
			if($(this).attr("selected")){
				var id = $(this).attr("value");
				var name = $(this).html();
		
				var temp = document.createElement("option");
				temp.value = id;
				temp.text = name;
				document.getElementById("wprops").options.add(temp);
				$(this).remove();
			}
		});
	});
	
	
	var s = $("#secretProperties").val();
	s = s.split(",");
	$("#sprops").find("option").each(function(){
		var temp = $(this).attr("value");
		for(var i=0;i<s.length;i++){
			if(s[i] == temp){
				$(this).remove();
			}
		}
	});

	$("#secretAbles").find("option").each(function(){
		if(!$(this).attr("value")){
			$(this).remove();
		}
	});
	//选择全部可写字段
	$("#allS").click(function(){
		$("#secretAbles").find("option").each(function(){
			$(this).attr("selected",true);
		});
	});
	$("#allSecretProps").click(function(){
		$("#sprops").find("option").each(function(){
			$(this).attr("selected",true);
		});
	});

	//向左选择
	$("#sleftSelect").click(function(){
		$("#sprops").find("option").each(function(){
			if($(this).attr("selected")){
				var id = $(this).attr("value");
				var name = $(this).html();

				var temp = document.createElement("option");
				temp.value = id;
				temp.text = name;
				document.getElementById("secretAbles").options.add(temp);
				$(this).remove();
			}
		});
	});

	//向右选择
	$("#srightSelect").click(function(){
		$("#secretAbles").find("option").each(function(){
			if($(this).attr("selected")){
				var id = $(this).attr("value");
				var name = $(this).html();
				var temp = document.createElement("option");
				temp.value = id;
				temp.text = name;
				document.getElementById("sprops").options.add(temp);
				$(this).remove();
			}
		});
	});
});

