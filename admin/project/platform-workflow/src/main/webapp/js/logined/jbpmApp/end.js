$(document).ready(function() {
	grid();
    $("#search").click(function(){
    	grid();
    });
	 // 绑定回车事件
    $("body").keydown(function(){
        if(event.keyCode == 13){
        	grid();
        }
    });
}) ;

/**
 * 获取管理员信息列表
 */
var grid=function(){
	qytx.app.grid({
		id:'myTable',
		url:basePath + "jbpmflow/listSearch!endTaskList.action",
		selectParam:{
			      "searchkey":$.trim($("#searchkey").val()),
      	          "beginDate":$.trim($("#beginDate").val()),
      	          "endDate":$.trim($("#endDate").val())
				},
		valuesFn:[
		          {
                	 "aTargets": [2],
                     "fnRender": function ( oObj ) {
                      var processId =oObj.aData.processId;
                      var res="";      
 				      var processInstanceId=oObj.aData.executionId;
 				      var title = oObj.aData.title;
 				      res+="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+processInstanceId+"')\" >"+title+"</a>";
 				      return   res;
	                     }
	                },{
	                    "aTargets": [6],// 覆盖第6列
	                    "fnRender": function ( oObj ) {
	                        var processId =oObj.aData.processId;
	                    	var res="";      
	                    	var processInstanceId=oObj.aData.executionId;
					        res+="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+processInstanceId+"')\" >查看</a>";
					        return   res;
	                    }
	                }
			]
		});
}