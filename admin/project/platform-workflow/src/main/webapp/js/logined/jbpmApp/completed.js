
$(document).ready(function() {
    //获取人员信息列表
    getDataTable();
    $("#search").click(function(){
    	getDataTable();
    });
	 //绑定回车事件
    $("body").keydown(function(){
        if(event.keyCode == 13){
        	getDataTable();
        }
        
    });
}) ;

/**
 * 获取管理员信息列表
 */
function getDataTable(){
     $('#myTable').dataTable({
            "bProcessing": true,
            'bServerSide': true,
            'fnServerParams': function ( aoData ) {
                aoData.push( { "name":"searchkey", "value":$.trim($("#searchkey").val()) }
                );
            },
            "sAjaxSource": basePath+"jbpmflow/listSearch!completedTaskList.action",//获取管理员列表
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
                {"sTitle":'序号', "mDataProp": "no" ,"sWidth": "30","sClass": "tdCenter"},
                {"sTitle":'申请时间', "mDataProp": "createTime" ,"sWidth": "120","sClass": "tdCenter"},
                {"sTitle":'申请名称', "mDataProp": "processInstanceName" ,"sWidth": "30%","sClass": "longTxt"},
                {"sTitle":'当前步骤', "mDataProp": "taskNameShow" ,"sWidth": "25%","sClass": "data_l"},
                {"sTitle":'已停留', "mDataProp": "taskStart" ,"sWidth": "120","sClass": "tdCenter"},
                {"sTitle":'流程名称', "mDataProp": "processType","sWidth": "25%","sClass": "data_l"},
                {"sTitle":'流程分类', "mDataProp": "categoryName","sWidth": "120","sClass": "data_l"},
                {"sTitle":'申请人', "mDataProp": "userName","sWidth": "100","sClass": "tdCenter"},
                {"sTitle":'状态', "mDataProp": "state" ,"sWidth": "70","sClass": "tdCenter"},
                {"sTitle":'审批时间', "mDataProp": "recivedTime" ,"sWidth": "120","sClass": "tdCenter"},
                {"sTitle":'操作', "mDataProp": null,"sWidth": "70" ,"sClass": "right_bdr0"}
               
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
              //提示
                $('#myTable tbody  tr td[class="longTxt"]').each(function() {
	  				this.setAttribute('title', $(this).text());
	  			});
            },
            "aoColumnDefs":[

                            {
                                "aTargets": [2],//覆盖第6列
                                "fnRender": function ( oObj ) {
                                	 var processId =oObj.aData.processId;
                                 	var res="";      
               				        var processInstanceId=oObj.aData.executionId;
               				        var title = oObj.aData.title;
               				        res+="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+processInstanceId+"')\" >"+title+"</a>";
               				        return   res;
                                }
                            } ,{ "aTargets": [3],//覆盖第2列
                            	"fnRender": function ( oObj ) {
						        var processId =oObj.aData.processId;
	    				        var taskNameShow=oObj.aData.taskNameShow;
	    				        var processInstanceId=oObj.aData.executionId;
						        var res="";
						        res="<a style=\"cursor: pointer;\"   onclick=\"viewHistory('"+processId+"','"+processInstanceId+"')\" >"+taskNameShow+"</a>";
						        return   res;
						    }
				     	} ,{"aTargets":[4],
				     		"fnRender":function(oObj){
				     			var processInstanceId=oObj.aData.executionId;
				     			var taskStart = oObj.aData.taskStart;
				     			var a = "<a id='"+processInstanceId+"' href=javascript:showInfo('"+processInstanceId+"'); >"+taskStart+"</a>";
				     			return a;
				     		}
				     	},{
						    "aTargets": [8],//覆盖第2列
						    "fnRender": function ( oObj ) {
						        var state =oObj.aData.state;   
						        _state = state;
						        var res=getShowState(state);
						        return   res;
						    }
						}, {
						    "aTargets": [10],//覆盖第2列
						    "fnRender": function ( oObj ) {
                                    var processId =oObj.aData.processId; 
            				        var executionId =oObj.aData.executionId; 
            				        var res="";
            				        res="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+executionId+"')\" >查看</a>";
            				        return  res;
						    }
						} 
                
                ]
        });

}
 
