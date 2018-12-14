$(document).ready(function(){
	getDatatables();
	
	//点击查询
	$("#search").click(function(){
		getDatatables();
	});
	
	//新增考勤方案
	$("#toAdd").click(function(){
		window.location.href=basePath+"logined/attendance/addPlan.jsp";
	});
	
});

//加载列表信息
function getDatatables(){
	this.qytx.app.grid({
		id:"myTable",
		url:basePath + "attendance/planList.action",
		valuesFn:[{
					"aTargets" : [2],
					"fnRender" : function(oObj) {
						var userCounts = oObj.aData.userCounts;
						var userIds = oObj.aData.userIds;
						var html='';
							html+='<a style="cursor:pointer" title="人员维护" userids="'+userIds+'" href="javascript:void(0);" onclick="getUserName(this);">'+userCounts+'</a>人';
						return html;
					}
				},{
					"aTargets" : [3],
					"fnRender" : function(oObj) {
						var planId = oObj.aData.planId;
						var userIds = oObj.aData.userIds;
						var html='';
							html+='<a style="cursor:pointer" title="修改" href="javascript:void(0);" onclick="update('+planId+')">修改</a>';
							//html+='<a style="cursor:pointer" title="删除" href="javascript:void(0);" onclick="del('+planId+')">删除</a>';
							html+='<a style="cursor:pointer" title="人员维护" userids="'+userIds+'" href="javascript:void(0);" onclick="setUser(this,'+planId+');">指定人员</a>';
						return html;
					}
				}]	
	});
}

/**
 * 删除方案 
 * @param planId
 */
function del(planId){
	art.dialog.confirm("确定要删除吗？",function(){
		$.ajax({
			url:basePath+"attendance/delPlan.action",
			data:{
				planId:planId
			},
			type:"post",
			dataType:"text",
			success:function(data){
				window.location.reload();
			}
		});
	});
}

function update(planId){
	window.location.href=basePath+"/attendance/toEditPlan.action?planId="+planId;
}

function setUser(obj,planId){
	var userIds=$(obj).attr("userids");
	$("#planId").val(planId);
	openSelectUser(3, selectUserCallBack, null, userIds);
}
/**
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
	if(count==0){
		art.dialog.alert("请选择考勤组人员");
		return false;
	}else{
		var planId=$("#planId").val();
		if(planId){
			$.ajax({
				url:basePath+"attendance/checkUserIds.action",
				data:{
					userIds:ids,
					planId:planId
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
							            		changeUserAjax({
													planId:planId,
													userIds:obj.successUserIds
												});
							            		art.dialog.close();
							            	}
							            	return true;
							            }
							        },
							        {
							            name: '移入',
							            callback: function () {
							            	changeUserAjax({
												planId:planId,
												userIds:ids
											});
							            	art.dialog.close();
							                return true;
							            },
								        focus: true
							        }
							    ]
							});
						}else{
							changeUserAjax({
								planId:planId,
								userIds:ids
							});
						}
					}else{
						art.dialog.alert("操作失败");
					}
				}
			});
		}
	}
}

function changeUserAjax(param){
	$.ajax({
		url:basePath+"attendance/changeUsers.action",
		data:param,
		type:"post",
		dataType:"text",
		success:function(data){
			if(data==1){
				art.dialog.alert("操作成功！",function(){
					window.location.reload();
				});
			}else{
				art.dialog.alert("操作失败！");
				window.location.reload();
			}
		}
	});
}

//显示考勤组人员
function getUserName(obj){
	var userIds=$(obj).attr("userids");
	$.ajax({
		url:basePath+"attendance/getUserNames.action",
		data:{
			userIds:userIds
		},
		type:"post",
		dataType:"text",
		success:function(data){
			art.dialog({
			    id: 'userNames',
			    content: '<span style="position:absolute;top:65px;left:25px;right:25px;text-align:left;height:170px;overflow:auto;">'+data+'</span>',
			    title:"查看人员",
			    height:200,
			    width:400,
			    button: [
			        {
			            name: '确定',
			            callback: function () {
			                return true;
			            },
				        focus: true
			        }
			    ]
			});
		}
	});
}
