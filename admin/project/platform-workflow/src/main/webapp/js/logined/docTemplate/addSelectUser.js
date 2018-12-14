$(document).ready(function(){
	//选择人员 --------- begin
 
		$("#selectUser").click(function(){
		var defaultSelectId = $("#nextUser").val();
			var flag = "check";
			var menu = $("input[name='menu']").val();
			if(menu == 3){
				flag = "check";
			}
			var type = 3;
			if($("input[name='menu']").val() == 8){
				type = 1;
				flag = "check";
			}
			qytx.app.tree.alertUserTree({
				defaultSelectIds:defaultSelectId,
				type:flag,
				showType:type,
				callback:function(data){ 
					if(data!=undefined)
		            {
			            $("textarea[name='nextUserName']").html("");
						$("input[name='nextUser']").val("");
						$(data).each(function(i,item){
		                    //具体参数查看 treeNode.js
	                    	if(flag == "radio"){
		                    	setUser.setOneUser(item.id,item.name);
	                    	}else if(flag == "check"){
	                    		setUser.setManyUser(item.id,item.name);
	                    	}
		                });
		            }
		            else
		            {
		                alert("请选择人员");
		            }
				}
			});
//			openDocSelectUser(type,function(data){
//				if(data!=undefined)
//	            {
//	            $("textarea[name='nextUserName']").html("");
//				$("input[name='nextUser']").val("");
//	                data.forEach(function(value, key) {
//	                    // alert("key="+key+",name="+value.Name+",id="+value.Id+",data="+value.Data+",type="+value.Type);
//	                    //具体参数查看 treeNode.js
//                    	if(flag == 0){
//	                    	setUser.setOneUser(value.Id,value.Name);
//                    	}else if(flag == 1){
//                    		setUser.setManyUser(value.Id,value.Name);
//                    	}
//	                });
//	            }
//	            else
//	            {
//	                alert("请选择人员");
//	            }
//			},flag,"",defaultSelectId,"");
		});

		//设置用户
		var setUser = {
			setOneUser:function(id,name){
				$("input[name='nextUserName']").val(name);
				$("input[name='nextUser']").val(id);
			},
			setManyUser:function (id,name) {
				// body...
				var userNames = $("textarea[name='nextUserName']").html();
            	$("textarea[name='nextUserName']").html(userNames+name+",");
            	var ids = $("input[name='nextUser']").val();
            	$("input[name='nextUser']").val(ids+id+",");
			}
		};

		//清空
		$("#cleanButton").click(function(){
        	$("input[name='nextUserName']").val("");
        	$("input[name='nextUser']").val("");
        	$("textarea[name='nextUserName']").html("");
		});
		
		
}); 
