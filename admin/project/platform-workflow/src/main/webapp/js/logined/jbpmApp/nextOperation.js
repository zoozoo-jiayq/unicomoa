/*
 * 下一步选择事件
 */
jQuery(document).ready(function($){
    setOneHold();
    controleShowCleanButton();
}); 
$("#nextNode").change(function(){
	var nodeId = $(this).val();
	for(var i=0; i<nextNodes.length; i++){
		var temp = nextNodes[i];
		if(temp.id == nodeId){
			$("#outComeNameShow").val(temp.name);
			$("#outComeName").val("TO "+temp.name);
			$("#nodeId").val(nodeId);
			$("#nodeType").val(temp.type);
		}
	}
	setOneHold();
	controleShowCleanButton();
});

/*
 * 如果是多选，则显示晴空按钮
 */
function controleShowCleanButton(){
	var nodeType = $("#nodeType").val();
	if(nodeType == "mutilSign"){
		$("#cleanButton").show();
	}
}

/**
 * 如果只有一个处理人，则直接显示该人
 */
function setOneHold(){
    var currentTaskCount = $("#currentTaskCount").val();
    if(currentTaskCount>2){
        return;
    }
    var nodeId = $("#nodeId").val();
    if(!nodeId || nodeId<=0){
        return;
    }
    var domflow = $("#domflow").val();
    if(!domflow){
        domflow = "";
    }
    var dataParam={
         'nodeId':nodeId,
         'domflow':domflow
    };
    //获取发起人信息
    var processInstanceId = $("#processInstanceId").val();
    if(processInstanceId){
    	dataParam.processInstanceId = processInstanceId;
    }
    $.ajax({
     url : basePath+"jbpmflow/detailSearch!getNextNodeUsers.action",
     type : 'post',
     dataType :'json',
     data:dataParam,
     success : function(data) {
         if(data.length==1){
              var userId=  data[0].id;
              var userName=data[0].name;
              $("#holdStr").val(userId);
              $("#exeUser").val(userName);
         }else{
             $("#holdStr").val("");
              $("#exeUser").val("");
         }  
     }
    });
}

/**
 * 选择办理人员
 */
function selectNextUserApply(){
    var nodeId = $("#nodeId").val();
    if(nodeId == 0){
    	art.dialog.alert("结束节点无需办理人!");
    }else{
    	openSelectUserApply(nodeId);
    }
}

/**
 * 弹出选择人员窗口
 * @param nodeId
 * update by jiayq,根据工作流中的设置，判断是按照部门选择人员还是直接选择人员
 */
function openSelectUserApply(nodeId){

    //add by jiayq，选人模式
    var selectUserMode = $("#selectUserMode").val();
    if(!selectUserMode){
        //默认是直接选人
        selectUserMode = 1;
    }
    var nodeType = $("#nodeType").val();
    var userIds = $("#holdStr").val();
    var domflow = $("#domflow").val();
    if(!domflow){
        domflow = "";
    }
    
    var url = basePath+ "logined/jbpmApp/selectuserWFSign.jsp?nodeId="+nodeId+"&defaultSelectId="+userIds;
    if(nodeType=="task"){//
         url = basePath+ "logined/jbpmApp/selectuserWFSign.jsp?nodeId="+nodeId+
         "&defaultSelectId="+userIds+"&selectUserMode="+selectUserMode;
        }
    if(nodeType=="mutilSign"){//mutilSign
         url = basePath+ "logined/jbpmApp/selectuserWFMutil.jsp?nodeId="+nodeId+
         "&defaultSelectId="+userIds+"&selectUserMode="+selectUserMode;
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
                            var _userName="";
                            try{
                            if(userMap){
	                            userMap.forEach(function(treeNode, key) {
	                                 var data = treeNode.data;//groupname_loginname
	                                 var arr = data.split("_");
	                                 var userId = arr[1];
	                                 var userName = treeNode.Name;
	                                 var nodeType = $("#nodeType").val();
	                                 if(nodeType=="task"){//
	                                         _userId = userId;
	                                         _userName = userName;
	                                 }
	                                 if(nodeType=="mutilSign"){//mutilSign
	                                     _userId += userId+",";
	                                     _userName += userName+",";
	                                 }
	                                 
	                            });
                            }}catch(e){
                            }
                            if(nodeType=="mutilSign"){
                                if(_userName!=""&&_userName.length>2){
                                    _userName=_userName.substring(0,_userName.length-1);
                                }
                            }
                            $("#holdStr").val(_userId);
                            $("#exeUser").val(_userName);
                             if(!_userId){
                                art.dialog.alert("请选择人员！");
                                return false;
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