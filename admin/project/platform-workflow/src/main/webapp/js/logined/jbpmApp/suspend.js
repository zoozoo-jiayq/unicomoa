
$(document).ready(function() {
    //获取人员信息列表
    getDataTable();
}) ;

var _taskName="";

/**
 * 获取管理员信息列表
 */
function getDataTable(){
     $('#myTable').dataTable({
            "bProcessing": true,
            'bServerSide': true,
            'fnServerParams': function ( aoData ) {
                aoData.push( { "name":"groupId", "value":$.trim($("#groupId").val()) }
                );
            },
            "sAjaxSource": basePath+"jbpmflow/listSearch!suspendTaskList.action",//获取管理员列表
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
                 {"sTitle":'序号', "mDataProp": "no" ,"sWidth": "30","sClass": "tdCenter "},
                {"sTitle":'工作名称', "mDataProp": "title" ,"sWidth": "40%","sClass": "data_l."},
                {"sTitle":'工作类型', "mDataProp": "processType" ,"sWidth": "30%","sClass": "data_l"},
                {"sTitle":'上一步骤', "mDataProp": "breforeTaskName" ,"sWidth": "30%","sClass": "data_l"},
                {"sTitle":'发起人', "mDataProp": "userName","sWidth": "100","sClass": "tdCenter"},
                {"sTitle":'发起时间', "mDataProp": "createTime" ,"sWidth": "90","sClass": "tdCenter"},
                {"sTitle":'挂起时间', "mDataProp": "recivedTime" ,"sWidth": "90","sClass": "tdCenter"},
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
                $('#myTable tbody  tr td[class="data_l"]').each(function() {
	  				this.setAttribute('title', $(this).text());
	  			});
            },
            "aoColumnDefs":[

								{
								    "aTargets": [1],//覆盖第6列
								    "fnRender": function ( oObj ) {
								        var taskId=oObj.aData.taskId;
								        var executionId =oObj.aData.executionId; 
								        var processId =oObj.aData.processId; 
								        var processInstanceName = oObj.aData.processInstanceName; 
								        var taskName=oObj.aData.taskName;
								        var executionId =oObj.aData.executionId; 
								        var title =oObj.aData.title;
								        var res="";
								        res="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+executionId+"')\" >"+title+"</a>";
								        return res;
								    }
								} ,
							{
							    "aTargets": [2],//覆盖第2列
							    "fnRender": function ( oObj ) {
							        var processType =oObj.aData.processType;
							        var processId =oObj.aData.processId;
							        var res="";
							        res="<a style=\"cursor: pointer;\"  onclick=\"goProcessView("+processId+")\">"+processType+"</a>";
							        return   res;
							    }
							},     
                  {
					    "aTargets": [3],//覆盖第2列
					    "fnRender": function ( oObj ) {
					    	  var processId =oObj.aData.processId;
	    				        var breforeTaskName=oObj.aData.breforeTaskName;
	    				        var taskNameShow=oObj.aData.taskNameShow;
	    				        var taskName=oObj.aData.taskName;
	    				        _taskName =taskName;
						        var res="";
						        res="<a style=\"cursor: pointer;\"   onclick=\"goView('"+processId+"','"+taskName+"')\" >"+breforeTaskName+"</a>";
						        return   res;
					    }
					},
                {
                    "aTargets": [7],//覆盖第6列
                    "fnRender": function ( oObj ) {
                        var taskId=oObj.aData.taskId;
                        var executionId =oObj.aData.executionId; 
                        var processId =oObj.aData.processId;
                        var taskName=_taskName;
				        var processInstanceName = oObj.aData.processInstanceName; 
				        var taskName=oObj.aData.taskName;
				        var title =oObj.aData.title;
                        var res = "<a title=\"作为主办人办理工作，填写表单和会签意见\" onclick=\"goHold('"+processId+"','"+executionId+"','"+taskId+"')\"  style=\"cursor:pointer;\"  class=\"view_url\" id=\"viewUrl\">审批</a>"
                       // return "<a style=\"cursor: pointer;\" onclick=\"resumeTask("+taskId+",4);\">恢复</a>";
                        return res;
                    }
                }
                
                ]
        });

}



