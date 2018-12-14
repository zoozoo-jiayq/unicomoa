/**
 * @author REN

 */
$(document).ready(function() {
	$("#search").click(function(){
		$.removeTableCookie('SpryMedia_DataTables_userTable_userList.jsp');
		getDataTable();
		return false;
	});
	
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			$.removeTableCookie('SpryMedia_DataTables_userTable_userList.jsp');
			getDataTable();
			return false;
		}
	}); 
	
	
	// 清除cookie中的分页信息
	$.removeTableCookie('SpryMedia_DataTables_userTable_userList.jsp');
	//添加人员
    $("#btnAddUser").click(function(){
    	var groupId=$("#groupId").val();
       // var url=basePath+"groupExt/toAddGoupUser.action?groupId="+groupId;
        //var groupName=$("#groupName").val();
        //var groupType=$("#groupType").val();
        if(groupId=="" || groupId==0)
        {
        	qytx.app.dialog.alert("请选择要添加人员的群组!");
            return;
        }
        /*art.dialog.data('groupName', groupName);
        art.dialog.data('groupId', groupId);
        art.dialog.data('groupType', groupType);

		art.dialog.open(url, {
			title : '选择人员',
			width : '800px',
			height : '450px',
			id : 'openSelectUserDialog',
			close: function () {
				window.location.reload();
		    }
		}); */
        selectUser('userNames','userIds');

    });
    
    //移动人员到另一群组
    $("#moveToGroup").click(function(){
    	var ids = _checkedIds;
    	if(ids.length <= 1){
    		qytx.app.dialog.alert('请选择要移动的人员！');
    		return false;
    	}
    	openSelectUser(1,callBackMoveGroup,ids);//选择人员
    });
  //---------------------------------------------------移动到群组
    
   function callBackMoveGroup(data)
    {
        if(data){
        	var userIds = [];
        	var userNames = [];
            data.forEach(function(value) {
               userIds.push(value.Id);
               userNames.push(value.Name);
            });
            if(userIds.length==0){
            	qytx.app.dialog.alert("请选择群组!");
            	return false;
            }
            var ids = _checkedIds; //选择人员Id
            var groupId = $("#groupId").val();
            var url = basePath+"group/moveUserToGroup.action";
            var paramData = {"userIds":ids,
            				  "groupId":userIds[0],
            				  "groupName":userNames[0],
            				  "oldGroupId":groupId
            				};
     qytx.app.ajax({
            	url:url,
            	data:paramData,
            	type:"POST",
            	dataType:"html",
            	success:function(data){
            		if(data == 1){
            			qytx.app.dialog.alert("移动人员到群组操作失败,请稍后重试!");
            		}else{
            			// 刷新左边的人员树
    					window.parent.refreshTree("gid_" + $("#groupId").val());
    					getDataTable();
            		}
            	}
            });
        }
    }
    function openSelectUser(showType,callback,defaultSelectId) {
        var url = basePath + "/logined/group_ext/selectuserSign.jsp?showType="+showType+"&defaultSelectId="+defaultSelectId;
        var title="选择人员";
        if(showType==1)
        {
            title="选择群组";
        }
        else if(showType==2)
        {
            title="选择角色";
        }
        art.dialog.open(url,
            {
                title:title,
                width : 360,
    			height : 480,
    			lock : true,
    		    opacity: 0.08,
                button:[
                    {
                        name:'确定',
                        focus:true,
                        callback:function () {
                            var userMap =art.dialog.data("userMap");
                            callback(userMap);
                            return true;
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
    
   //移动到群组结束----------------------------------------------------     
    $("#btnDeleteUser").click(function(){
    	deleteUser();
    });
	
	getDataTable();
	$("#searchButton").click(function(){
		getDataTable();
	});
	
	// 头部全选复选框
	$("#userTable").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
	// 第一列复选按钮
	$("#userTable").delegate("input:checkbox[name='chk']", "click",
			function(event) {
				check();
				event.stopPropagation();
			});
	
	
});
var getDataTable=function(){
	var searchName = $.trim($("#searchName").val());
	var groupId = $.trim($("#groupId").val());
	if (groupId == "") {
		groupId = null;
	}
	var sex = $.trim($("#sex").val());
	if (sex == "") {
		sex = null;
	}
	var mobileViewState = $("#mobileViewState").val();
	qytx.app.grid({
		id:'userTable',
		url:basePath + "groupExt/getUserByGroupExtId.action",
		selectParam:{
				'searchName':searchName,
                'groupId':groupId,
                'sex': sex,
                'mobileViewState':mobileViewState
				},
		valuesFn:[
			{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var isDefault = oObj.aData.isDefault;
				if (isDefault == 0) {
					return '<input name="chk" disabled="disabled" value="'+oObj.aData.id+'" type="checkbox" />';
				} else {
					return '<input name="chk" value="'+ oObj.aData.id+'"type="checkbox" />';
				}

			}
		},
		{
			"aTargets" : [3],
			"fnRender" : function(oObj) {
				var sex = oObj.aData.sex;
				return sex;
			}},
			{
				"aTargets" : [5],
				"fnRender" : function(oObj) {
					var userState = oObj.aData.userState;
					var userStateName = "";
					if (userState == 0) {
						userStateName = "是";
					} else {
						userStateName = "否";
					}
					return userStateName;
				}}
		]
});	
}



/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalChecked = $("input:checkbox[id='total']").prop("checked");
	var listCheckbox = $("input:checkbox[name='chk']");
	if (isTotalChecked) {
		listCheckbox.prop("checked", function(i, val) {
					if (!$(this).prop("disabled")) {
						return true;
					}
				});
	} else {
		listCheckbox.prop("checked", false);
	}
}
/**
 * 选择记录
 */
function check() {
	var checkTotalNum = $("input:checkbox[name='chk']");
	var checkNum = 0;
	var checkNumAll = true;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
					checkNum++;
				} else {
					checkNumAll = false;
				}
			});
	if (!checkNumAll) {
		$("#total").prop("checked", false);
	} else {
		$("#total").prop("checked", true);
	}
}

function deleteUser(){
	var groupId = $.trim($("#groupId").val());
	if(groupId == ""||groupId == 0){
		qytx.app.dialog.alert('请选择群组！');
		return false;
	}
	
	var ids = _checkedIds;
	if(ids.length <= 1){
		qytx.app.dialog.alert('请选择要删除的人员信息！');
		return false;
	}
	
	qytx.app.dialog.confirm('确定删除已选择的人员吗？', function() {
		qytx.app.ajax({
			type:"post",
			url:basePath + "groupExt/moveOutUserFromGroup.action",
			data:{"ids":ids,"groupId":groupId},
			success:function(data){
				if(data == 1){
						window.parent.refreshTree("gid_" + $("#groupId").val());
						getDataTable();
				}else{
					qytx.app.dialog.alert('操作失败');
				}
			}
		});
	});
}


document.onkeydown = function(){
	if(event.keyCode == 13){
		$("#searchButton").click();
	}
}


/**
 *   打开人员选择树
 */
function selectUser(forName, forId) {
    var param = new HashMap();
    param.set("forName", forName);
    param.set("forId", forId);
    openSelectUser(3, selectCallBack, param,$("#"+forId).val());
}

function cleanUser(forName, forId) {
    $("#" + forName).val("");
    $("#" + forId).val("");
}
/**
 *人员选择树回调方法
 */
function selectCallBack(data, param) {
	 var userIds = '';
     var userNames = '';
     data.forEach(function (value, key) {
            var userId = value.Id;
            var userName = value.Name;
            userIds += userId+",";
            userNames += userName+",";
     });
     var forName = param.get("forName");
     var forId = param.get("forId");
     $("#" + forName).val(userNames);
     $("#" + forId).val(userIds);
     formSave(userNames,userIds);
}


/**
 * 保存
 */
function formSave(userNames,userIds){
	var userIds = userIds;
	var userNames = userNames;
	var groupId = $("#groupId").val();
	qytx.app.ajax({
		type:	"post",
		url:	basePath + "groupExt/moveUserToGroup.action",
		data:	{"userIds":userIds,"groupId":groupId},
		success:function(data){
			var win = art.dialog.open.origin;// 来源页
			if(data==1||data==2){
				win.document.location = basePath + "logined/group_ext/userList.jsp?edit=1&groupId="+groupId;
			}
		}
	});
}
