jQuery(document).ready(function($){

	//新建流程
	$("#flow_creat").click(function(){
		//window.parent.parent.parent.addTab("newCreate",basePath+"/workflow/manager!createDefine.action","新建流程");
		art.dialog.open(basePath+"/workflow/manager!prepCreate.action?type="+$("#type").val(),{
			title:"新增向导",
		    opacity: 0.08
			// ok:function(){
			// 	this.time(0.5);
			// 	window.parent.parent.addTab("newCreate", basePath+"/workflow/manager!createDefine.action", "新建流程");
			// 	return false;
			// },
			// okVal:"新建",
			// cancel:true
		});
	});

	//进入流程设计器界面
	$("#process_designer").click(function(){
		if($("#processState").val() == 2){
			art.dialog.alert("请先停用该流程!");
			return ;
		}
		if($("#instanceNum").html() == 0){
			if(window.parent && window.parent.parent && window.parent.parent.parent && window.parent.parent.parent.addTab){
				window.parent.parent.parent.addTab(Math.random(),basePath+"/workflow/jpdl!define.action?processAttributeId="+$("#processId").val()+"&type="+$("#type").val(),"流程设计器");
			}else{
				window.open(basePath+"/workflow/jpdl!define.action?processAttributeId="+$("#processId").val()+"&type="+$("#type").val(), "流程设计器");
			}
		}else{
			art.dialog.alert("该流程下的工作不为0，请先结束工作再设计流程!");
		}
	})

	//进入节点属性编辑界面，进入前先验证流程是否已经设计好
	$("#node_designer").click(function(){
		//先验证
		$.get(basePath+"/workflow/jpdl!isDefine.action?processAttributeId="+$("#processId").val()+"&random="+Math.random(),function(result){
			if(result=="success"){
				if(window.parent && window.parent.parent && window.parent.parent.parent && window.parent.parent.parent.addTab){
					window.parent.parent.parent.addTab(Math.random(),basePath+"/workflow/jpdl!nodeEdit.action?processAttributeId="+$("#processId").val()+"&type="+$("#type").val(),"编辑流程节点属性");
				}else{
					window.open(basePath+"/workflow/jpdl!nodeEdit.action?processAttributeId="+$("#processId").val(), "编辑流程节点属性");
				}
			}else{
				art.dialog.alert("请先使用流程设计器设计流程!");
			}
		});
	});

	//编辑流程
	$("#flow_edit").click(function(){
		var processId = $("#processId").val();
		if(window.parent && window.parent.parent && window.parent.parent.parent && window.parent.parent.parent.addTab){
			window.parent.parent.parent.addTab(Math.random(),basePath+"/workflow/manager!update.action?processAttributeId="+processId+"&type="+$("#type").val(),"编辑流程");
		}else{
			window.open(basePath+"/workflow/manager!update.action?processAttributeId="+processId+"&type="+$("#type").val(), "编辑流程");
		}

	});

	//预览表单
	$("#flow_view").click(function(){
		var formId = $("#formId").val();
		if(window.parent && window.parent.parent && window.parent.parent.parent && window.parent.parent.parent.addTab){
			window.parent.parent.addTab(Math.random(),basePath+"workflowForm/viewForm.action?formId="+formId,"预览表单");
		}else{
			window.open(basePath+"workflowForm/viewForm.action?formId="+formId, "预览表单");
		}
	});

	//删除
	$("#flow_delete").click(function(){
		art.dialog.confirm("确认删除该流程定义?",function(){
			var processId = $("#processId").val();
			//先验证是否可删除
			$.get(basePath+"/workflow/manager!isCanDelete.action?processAttributeId="+processId+"&rd="+Math.random(),function(data){
				if(data == "success"){
					$.get(basePath+"/workflow/manager!deleteProcess.action?processAttributeId="+processId,function(result){
						window.parent.location.href=basePath+"workflow/manager.action?type="+$("#type").val();
					});
				}else if(data == "false"){
					art.dialog.alert("请先停用该流程，并确保该流程下没有在用实例!");
				}
			});
			
		});

	});

	//校验
	$("#process_flowCheck").click(function(){
		var processId = $("#processId").val();
		
		art.dialog.open(basePath+"/workflow/manager!check.action?processAttributeId="+processId,{
			title:"校验",
			 width : 600,
			 height : 300,
		    opacity: 0.08,
		    lock:true,
			ok:function(){
				window.location.href=basePath+"/workflow/manager!editProcess.action?processAttributeId="+processId+"&type="+$("#type").val();
			}
		});
	});

	//发布
	$("#deploy").click(function(){
		var processId = $("#processId").val();
		$.get(basePath+"/workflow/manager!isCanDelete.action?processAttributeId="+processId+"&rd="+Math.random(),function(data){
			if(data == "success"){
				$.post(basePath+"/workflow/manager!deploy.action?processAttributeId="+processId,function(result){
					if(result == "success"){
						art.dialog.through({
										title: '消息',
										height : 109,
										width : 317,
										content: '操作成功!',
										icon: 'succeed',
									    opacity: 0.08,
									    lock:true,
										ok: function(){
											window.location.href=basePath+"/workflow/manager!editProcess.action?processAttributeId="+processId+"&type="+$("#type").val();
										}
									});
					}else{
						art.dialog.alert("发布失败!");
					}
				});
			}else{
				art.dialog.alert("请先停用该流程，并确保该流程下没有在用实例!");
			}
		});
	});

	//停用
	$("#stop").click(function(){
		var processId = $("#processId").val();
		$.post(basePath+"/workflow/manager!stop.action?processAttributeId="+processId,function(result){
			if(result == "success"){
				art.dialog.through({
					title: '消息',
					height : 109,
					width : 317,
					content: '操作成功!',
					icon: 'succeed',
					height : 109,
					width : 317,
				    opacity: 0.08,
				    lock:true,
					ok: function(){
						window.location.href=basePath+"/workflow/manager!editProcess.action?processAttributeId="+processId+"&type="+$("#type").val();
					}
				});
			}
		});
	});

	//启用
	$("#start").click(function(){
		var processId = $("#processId").val();
		$.post(basePath+"/workflow/manager!start.action?processAttributeId="+processId,function(result){
			if(result == "success"){
				art.dialog.through({
								title: '消息',
								height : 109,
								width : 317,
								content: '操作成功!',
								icon: 'succeed',
							    opacity: 0.08,
							    lock:true,
								ok: function(){
									window.location.href=basePath+"/workflow/manager!editProcess.action?processAttributeId="+processId+"&type="+$("#type").val();
								}
							});
			}else if(result == "repeat"){
				art.dialog.alert("流程名称不能重复!");
			}
		});
	});

	//预览流程
	$("#process_view").click(function(){
		var processId = $("#processId").val();
		if(window.parent && window.parent.parent && window.parent.parent.parent && window.parent.parent.parent.addTab){
			window.parent.parent.addTab(Math.random(),basePath+"/workflow/jpdl!view.action?processAttributeId="+processId,"预览流程");
		}else{
			window.open(basePath+"/workflow/jpdl!view.action?processAttributeId="+processId, "预览流程");
		}
	});
	
	/*导出*/
	$("#exortProcessDefine").click(function(){
		var processId = $("#processId").val();
		window.open(basePath+"/workflow/manager!exportProcessDefine.action?processAttributeId="+processId);
	});

});