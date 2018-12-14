	jQuery(document).ready(function($){
		
		var userId = h5Adapter.getItemValue("currentUserId");
		
		var instanceId = $("#instanceId").val();
		//不同意
		$("#noagree").click(function(){
			window.location.href = basePath+"mobile/logined/workflow/noagree.jsp?_clientType=wap&instanceId="+instanceId;
		});
		$("#but_noagree").click(function(){
			$("#but_noagree").attr("disabled",true);
			var advice = $("#advice_noagree").val();
			if(advice!=null&&advice!=""&&advice.length>=500){
				h5Adapter.tips("批复内容应小于500个字");
				$("#but_noagree").attr("disabled",false);
				return false;
			}
			$.ajax({
				type:"post",
				url:basePath+"baseworkflow/approve.c",
				data:{"instanceId":instanceId,"approveResult":"1","userId":userId,"advice":advice},
				success:function(msg){
					if(msg.indexOf("100||")==0){
						h5Adapter.tips("提交成功");
						window.location.href = basePath+"mobile/logined/workflow/myApproveList.jsp?_clientType=wap&flag=processed";
					}else{
						$("#but_noagree").attr("disabled",false);
					}
				}
			});
		});
		
		//同意
		$("#agree").click(function(){
			window.location.href = basePath+"mobile/logined/workflow/agree.jsp?_clientType=wap&instanceId="+instanceId;
		});
		$("#but_agree").click(function(){
			$("#but_agree").attr("disabled",true);
			var advice = $("#advice_agree").val();
			if(advice!=null&&advice!=""&&advice.length>=500){
				h5Adapter.tips("批复内容应小于500个字");
				$("#but_agree").attr("disabled",false);
				return false;
			}
			$.ajax({
				type:"post",
				url:basePath+"baseworkflow/approve.c",
				data:{"instanceId":instanceId,"approveResult":"0","userId":userId,"advice":advice},
				success:function(msg){
					if(msg.indexOf("100||")==0){
						h5Adapter.tips("提交成功");
						window.location.href = basePath+"mobile/logined/workflow/myApproveList.jsp?_clientType=wap&flag=processed";
					}else{
						$("#but_agree").attr("disabled",false);
					}
				}
			});
		});
		
		//转交
		$("#turn").click(function(){
			h5Adapter.selectOneUser(function(data){
				if(data.isSuccess){
					var suid = data.userList[0].userId;
					$.ajax({
						type:"post",
						url:basePath+"baseworkflow/checkTurnerIsRepeat.c",
						data:{"instanceId":instanceId,"turnUserId":suid},
						success:function(msg){
							if(msg.indexOf("100||") == 0 ){
								msg = msg.substring(5);
								msg = eval('('+msg+')');
								if(msg.result == true){//重复
									h5Adapter.tips("该审批人已在列表中");
								}else{
									var turn = JSON.stringify(data.userList[0]);
									var turnEncode=encodeURIComponent(turn);
									window.location.href = basePath+"mobile/logined/workflow/turn.jsp?_clientType=wap&instanceId="+instanceId+"&turn="+turnEncode;
								}
							}
						}
					});
				}
			});
		});
		$("#but_turn").click(function(){
			$("#but_turn").attr("disabled",true);
			var advice = $("#advice_turn").val();
			var turner = $("#turn").val();
			turner = eval('('+turner+')');
			var userName = turner.userName;
			var photoUrl = turner.userPhoto;
			var ss = {
					userId:turner.userId,
					userName:userName,
					photoUrl:photoUrl
			};
			var sr = JSON.stringify(ss);
			if(advice!=null&&advice!=""&&advice.length>=500){
				h5Adapter.tips("批复内容应小于500个字");
				$("#but_turn").attr("disabled",false);
				return false;
			}
			$.ajax({
				type:"post",
				url:basePath+"baseworkflow/turn.c",
				data:{"instanceId":instanceId,"turner":sr,"userId":userId,"advice":advice},
				success:function(msg){
					if(msg.indexOf("100||")==0){
						h5Adapter.tips("提交成功");
						window.location.href = basePath+"mobile/logined/workflow/myApproveList.jsp?_clientType=wap&flag=processed";
					}else{
						$("#but_turn").attr("disabled",false);
					}
				}
			});
		});
	});