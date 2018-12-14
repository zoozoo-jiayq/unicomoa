<!--展示每个节点的操作，-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/publicDom" prefix="dom"%>
<div  id="option"><dom:showOperation taskId="${taskId }" menu="${menu}"/></div>
<input type="hidden" id="nextUser" />
<script type="text/javascript">
	var taskName = $("#taskName").val();
	var instanceId = $("#instanceId").val();
	var taskId = $("#taskId").val();
	//给按钮绑定事件
	$("input[nextAction='保存']").click(function(){
		domOption.save();
	});
	$("input[nextAction='转领导批阅']").click(function(){
		var nodeId = $(this).attr("nodeId");
		selectUserDialog(3,0,"转领导批阅",nodeId);
	});
	$("input[nextAction='转收文分发']").click(function(){
		var nodeId = $(this).attr("nodeId");
		selectUserDialog(3,0,"转收文分发",nodeId);
	});
	$("input[nextAction='转阅读']").click(function(){
		var nodeId = $(this).attr("nodeId");
		selectUserDialog(3,1,"转阅读",nodeId);
	});
	$("input[nextAction='已阅']").click(function(){
		endCurrentTask("已阅", "");
	});
	$("input[nextAction='转核稿']").click(function(){
		var nodeId = $(this).attr("nodeId");
		selectUserDialog(3,0,"转核稿",nodeId);
	});
	$("input[nextAction='转盖章']").click(function(){
		var nodeId = $(this).attr("nodeId");
		selectUserDialog(3,0,"转盖章",nodeId);
	});
	$("input[nextAction='转发文分发']").click(function(){
		var nodeId = $(this).attr("nodeId");
		selectUserDialog(3,0,"转发文分发",nodeId);
	});
	$("input[nextAction='转分发']").click(function(){
		selectUserDialog(1,1,"转分发");
	});
	$("input[nextAction='归档']").click(function(){
		//domOption.completeTask("归档","");
		endCurrentTask("归档", "");
	});
	
	/*
	 * 弹出选择下一步的处理人
	 * selectType:1 显示部门 2显示角色 3显示人员
	 * isSingle:0 单选 1 复选
	 */
	function selectUserDialog(selectType,isSingle,action,nodeId){
		 try{
			 hideDoc();
			 $("#pdf_div").hide();
		 }catch(e){}
		if(selectType == 1){
			openDocSelectUser(selectType,function(data){
				if(data && (data!=undefined) && (data._data)){
					var nextUser = "";
	                data.forEach(function(value, key) {
	                    //具体参数查看 treeNode.js
	                	if(isSingle == 0){
	                    	nextUser = value.Id;
	                	}else if(isSingle == 1){
	                		nextUser += value.Id+",";
	                	}
	                });
	                if(nextUser){
		        		endCurrentTask(action, nextUser);
	                }else{
	                	art.dialog.alert("请选择人员");	
	                }
	            }
	            else
	            {
	                art.dialog.alert("请选择人员");
	            }
			},isSingle,"","","公文管理",function(){
				showDoc();
				$(".tab > ul > .current").click();
			});
		}else{
			 //默认是单选
			var url = "";
		    if(isSingle==0){//
		         url = basePath+ "logined/jbpmApp/selectuserWFSign.jsp?nodeId="+nodeId+"&selectUserMode=2";
		    }else if(isSingle==1){//mutilSign
		         url = basePath+ "logined/jbpmApp/selectuserWFMutil.jsp?nodeId="+nodeId+"&selectUserMode=2";
		    }
		    art.dialog.open(url,
		            {
		                title:"人员选择",
		                width:360, 
		                height:407,
		                opacity: 0.08,
		                resize: false,   
		                drag: true ,
		                lock:true,
		                button:[
		                    {
		                        name:'确定',
		                        focus:true,
		                        callback:function () {
		                           var userMap =art.dialog.data("userMap");
		                           var _userId="";
		                           userMap.forEach(function(treeNode,key){
		                        	   	var strs = key.split("_");
		                        	   	if(strs && strs.length==2){
		                        	   		_userId +=strs[1]+",";
		                        	   	}
		                           });
		                           if(_userId.length>0){
		                        	   _userId = _userId.substring(0, _userId.length-1);
		                        	   endCurrentTask(action, _userId);
			                       }else{
			                    	   art.dialog.alert("请选择人员!");
			                       }
		                        }
		                    },
		                    {
		                        name:'取消',
		                        callback:function () {
		                            return true;
		                        }
		                    }
		                ]
		            }, false);
		}
	}
	
	function endCurrentTask(action,nextUser){
		async.series({
			save:function(callback){
				domOption.save(callback);
			},
			complete:function(callback){
				 domOption.completeTask(action, nextUser,callback);
			}
		},function(err,result){
		}); 
	}
	
	/*
	 * add by 贾永强，保存公文信息的时候保存印章的图片BASE64编码，下载的时候使用
	 */
	function generateImgBase64code(){
		var result = new Array();
		var objs = document.getElementsByTagName("object");
		for (var i = objs.length - 1; i >= 0; i--) {
			var obj = objs[i];
			if(obj.classid == "clsid:AA4B3728-B61C-4bcc-AEE7-0AA47D3C0DDA"){
				var str = "data:image/gif;base64," + obj.SignPicBase64Str;
				result.push(str);
			}
		}
		return JSON.stringify(result);
	} 

	var domOption = {
			//保存
			save:function(callback){
				$("body").lock();
				var customFormValue = getCustomFormValueMap();
				var imgs = generateImgBase64code();
				$.post("${ctx}/dom/public!saveDom.action",{
					"customFormValue":customFormValue,
					"taskId":$("#taskId").val(),
					"instanceId":instanceId,
					"taskName":taskName,
					"imgs":imgs
				},function(data){
					if(data == "success"){
						$("body").unlock();
						try{
							hideDoc();
							if(docflag){
								saveDoc();
							}
							saveSignByForm("yzform");
						}catch(e){
						}
						qytx.app.dialog.tips("操作成功!",function(){
							try{
	    	 					showDoc();
	    	 				}catch(e){
	    	 					
	    	 				}
						});
						/* art.dialog({
    	    	 			title:'提示',
    	    	 			content:"操作成功!", 
    	    	 			icon:'succeed',
    	    	 			width : 317,
  						   	height : 109,
    	    	 			ok:function(){
    	    	 				try{
    	    	 					showDoc();
    	    	 				}catch(e){
    	    	 					
    	    	 				}
    	    	 				return true;
    	    	 			}
    	    	 		}); */
					}else if(data == "delete"){
						art.dialog.alert("该任务已不存在！");
						$("body").unlock();
						return ;
					}
					if(callback){
						callback();
					}
				});
			},
			completeTask  :function(action,nextUser,callback){
				$("body").lock();
				try{
					hideDoc();
					$("#pdf_div").hide();
				}catch(e){}
				$.post(basePath+"/dom/public!completeTask.action",{
					"taskId":taskId,
					"action":encodeURI(action),
					"nextUser":nextUser
					},function(data){
						qytx.app.dialog.tips("操作成功!",function(){
							try{
	    	 					window.top.closeCurrentTab();
	    	 				}catch(e){
	    	 					window.close();
	    	 				}
						});
							/* art.dialog({
			    	    	 			title:'提示',
			    	    	 			content:"操作成功!", 
			    	    	 			icon:'succeed',
			    	    	 			width : 317,
			  						   	height : 109,
			    	    	 			ok:function(){
			    	    	 				try{
			    	    	 					window.top.closeCurrentTab();
			    	    	 				}catch(e){
			    	    	 					window.close();
			    	    	 				};
			    	    				}
			    	    	 });							 */	
						}
				);
				if(callback){
					callback();
				}
			}
	};
</script>