    //REN
    $(document).ready(function() {
    	$("#groupAdd").click(function(){
    		formSave();
    	});
    	selectUser('userNames','userIds');
    });
    
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
    }


    /**
     * 保存
     */
    function formSave(){
    	var userIds = $("#userIds").val();
    	var userNames = $("#userNames").val();
    	var groupId = $("#groupId").val();
    	$.ajax({
    		type:	"post",
    		url:	basePath + "groupExt/moveUserToGroup.action",
    		data:	{"userIds":userIds,"groupId":groupId},
    		dataType:"text",
    		success:function(data){
    			var win = art.dialog.open.origin;// 来源页
    			if(data==1||data==2){
    				art.dialog.alert("保存成功！",function(){
    					win.document.location = basePath + "logined/group_ext/userList.jsp?groupId="+groupId;
    				});
    			}
    		}
    	});
    }
    