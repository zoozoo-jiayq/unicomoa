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
    $('#myTable').dataTable({
        "bProcessing": true,
        'bServerSide': true,
        'fnServerParams': function ( aoData ) {
            aoData.push({"name":"searchkey", "value":$("#searchkey").val()},
            		    {"name":"beginDate","value":$("#beginDate").val()},
            		    {"name":"endDate","value":$("#endDate").val()}
            		    );
        },
        "sAjaxSource": basePath+"jbpmflow/listSearch!listenerlist.action",//获取管理员列表
        "sServerMethod": "POST",
        "sPaginationType": "full_numbers",
        "bPaginate": true, //翻页功能
        "bLengthChange": false, //改变每页显示数据数量
        "bFilter": false, //过滤功能
        "bSort": false, //排序功能
        "bInfo": true,//页脚信息
        "bAutoWidth": false,//自动宽度
        "bDestroy":true,
        "iDisplayLength":15, //每页显示多少行
        "aoColumns": [
			{"sTitle":'序号', "mDataProp": "no","sClass": "tdCenter num"},
			{"sTitle":'申请时间', "mDataProp": "createTime" ,"sWidth": "120","sClass": "tdCenter"},
			{"sTitle":'申请名称', "mDataProp": "title" ,"sWidth": "20%","sClass": "longTxt"},
			{"sTitle":'申请人', "mDataProp": "userName","sWidth": "100","sClass": "tdCenter"},
			{"sTitle":'流程名称', "mDataProp": "processType" ,"sWidth": "15%","sClass": "data_l longTxt"},
			{"sTitle":'流程分类', "mDataProp": "categoryName" ,"sWidth": "120","sClass": "data_l"},
			{"sTitle":'当前步骤', "mDataProp": null ,"sWidth": "150","sClass": "data_l longTxt"},
			{"sTitle":'已停留', "mDataProp": "taskStart" ,"sWidth": "120","sClass": "tdCenter"},
			{"sTitle":'操作', "mDataProp": null,"sWidth": "100px" ,"sClass": "right_bdr0"}
        ],
        "oLanguage": {
            "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
        },
        "fnDrawCallback": function (oSettings) {
            $("#totalNum").html(oSettings.fnRecordsDisplay());
            $("#companyAll").html(oSettings.fnRecordsDisplay());
            $("#selectedNum").html(0);
            $("#total").prop("checked",false);
            $(".morebtn").hover(function(){
        		$(this).addClass("morebtnHover");
        		},function(){
        		  $(this).removeClass("morebtnHover");	
        		});//更多操作
            $('#myTable tbody  tr td[class="longTxt"]').each(function() {
  				this.setAttribute('title', $(this).text());
  			});
            
        },
        "aoColumnDefs":[
            {
              "aTargets": [2],//覆盖第3列
              "fnRender": function ( oObj ) {
            	  var processId =oObj.aData.processId;
              		var res="";      
			        var processInstanceId=oObj.aData.executionId;
			        var title = oObj.aData.title;
			        res+="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+processInstanceId+"')\" >"+title+"</a>";
			        return   res;
              }
            } , {
            	"aTargets": [6],
                "fnRender": function ( oObj ) {
                	var res="";      
			        var processInstanceId=oObj.aData.executionId;
			         var taskNameShow=oObj.aData.taskNameShow;
			         var processId = oObj.aData.processId;
			        res+="<a style=\"cursor: pointer;\"  onclick=\"viewHistory("+processId+",'"+processInstanceId+"')\" >"+taskNameShow+"</a>";
			        return   res;
                }
            },{
				"aTargets":[7],
				"fnRender":function(oObj){
					var taskStart = oObj.aData.taskStart;
            		var processInstanceId=oObj.aData.executionId;
            		var a = "<a id='"+processInstanceId+"' href=javascript:showInfo('"+processInstanceId+"'); >"+taskStart+"</a>";
            		return a;
				}
			},{
            "aTargets": [8],//覆盖第6列
            "fnRender": function ( oObj ) {
                var taskId=oObj.aData.taskId;
                var executionId =oObj.aData.executionId; 
                var res="";
                res="<a title=\"\"  onclick=\"assigneeOther("+taskId+",grid)\" style=\"cursor:pointer;\"  class=\"view_url\" id=\"viewUrl\">委托</a>";
                res+="<a title=\"\" onclick=\"nofityNextUser('"+executionId+"')\"  style=\"cursor:pointer;\"  class=\"view_url\" id=\"viewUrl\">催办</a>";
                res+="<a title=\"\" onclick=\"deleteProcessInstance('"+executionId+"',grid)\"  style=\"cursor:pointer;\"  class=\"view_url\" >删除</a>";
                return   res;
               }
            } 
          ]
    });
}