
$(document).ready(function() {
	$.removeTableCookie('SpryMedia_DataTables_myTable_search.jsp');
    getDataTable();
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
             //要做的事情
             $.removeTableCookie('SpryMedia_DataTables_myTable_search.jsp');
        	 getDataTable();
        }
    }; 

}) ;


var _state="";
/**
 * 获取管理员信息列表
 */
function getDataTable(){

    //add by 贾永强，根据操作标志判断显示类型
    var refSelect = $("#refSelect").val();
    var headColumns = [];
    var dataColumns = [];
    if(refSelect==='refSelect'){
        headColumns.push({"sTitle":'选择',"mDataProp": "no" , "sWidth": "50","sClass": "tdCenter"});
        dataColumns.push({
                    "aTargets": [0],//覆盖第2列
                    "fnRender": function ( oObj ) {
                        var processType =oObj.aData.processType;
                        var processId =oObj.aData.processId;
                        var processInstanceId = oObj.aData.processInstanceId;
                        var res="";
                        // res="<a style=\"cursor: pointer;\"  onclick=\"goProcessView("+processId+")\">"+processType+"</a>";
                        res="<input type='radio' pid='"+processInstanceId+"' name='refSelected' value='"+processId+"'/>";
                        return   res;
                    }
                });
    }else{
        headColumns.push({"sTitle":'序号', "mDataProp": "no" ,"sWidth": "40","sClass": "tdCenter"});
    }
    headColumns.push({"sTitle":'流程类型', "mDataProp": "processType" ,"sWidth": "200","sClass": "tdCenter"},
                {"sTitle":'工作名称/文号', "mDataProp": "processInstanceName" ,"sWidth": "100%","sClass": "longTxt"},
                {"sTitle":'开始时间', "mDataProp": "createTime","sWidth": "200","sClass": "tdCenter"},
                {"sTitle":'状态', "mDataProp": "state" ,"sWidth": "100","sClass": "longTxt"},
                {"sTitle":'操作', "mDataProp": null,"sWidth": "100" ,"sClass": "oper"});
    dataColumns.push({
                    "aTargets": [1],//覆盖第2列
                    "fnRender": function ( oObj ) {
                        var processType =oObj.aData.processType;
                        var processId =oObj.aData.processId;
                        var res="";
                        res="<a style=\"cursor: pointer;\"  onclick=\"goProcessView("+processId+")\">"+processType+"</a>";
                        return   res;
                    }
                },
                {
                    "aTargets": [2],//覆盖第3列
                    "fnRender": function ( oObj ) {
                        var processInstanceName =oObj.aData.processInstanceName;
                        var processInstanceId=oObj.aData.processInstanceId;
                        var processId =oObj.aData.processId;
                        var taskName=oObj.aData.taskName;
                        var res="";
                        res="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+processInstanceId+"','"+taskName+"')\" >"+processInstanceName+"</a>";
                        return   res;
                    }
                },
                {
                    "aTargets": [4],//覆盖第6列
                    "fnRender": function ( oObj ) {
                        var state =oObj.aData.state;
                        _state=state;
                        var res="";
                        if(state=="active"){
                            res="<font color='green'>执行中</font>";
                        }
                        if(state=="ended"){
                            res="<font color='777777'>已经结束</font>";
                        }
                        return   res;
                    }
                },
                {
                    "aTargets": [5],//覆盖第6列
                    "fnRender": function ( oObj ) {
                        var taskId=oObj.aData.taskId;
                        var processInstanceId =oObj.aData.processInstanceId; 
                        var processId =oObj.aData.processId;
                        var taskName=oObj.aData.taskName;
                        var option = "";
                        if(_state=="active"){
                            option+="<a   style=\"cursor: pointer;\" onclick=\" deleteTask('"+processInstanceId+"',5);\"  >删除</a>";
                        }
                        if(_state=="ended"){
                             
                        }
                        // var option="<span class=\"morebtn\"><a style=\"cursor: pointer;\"  class=\"m_btn \">更多</a><div class=\"m_btnlist\">";
                        // option+="<p><a   style=\"cursor: pointer;\" onclick=\" deleteTask('"+processInstanceId+"',5);\"  >删除</a></p>";
                        // option+="</div></span>";
                        return "<a onclick=\"goView('"+processId+"','"+taskName+"')\"  style=\"cursor:pointer;\"  class=\"view_url\" id=\"viewUrl\">流程图</a>"+option;
                    }
                });

     $('#myTable').dataTable({
            "bProcessing": true,
            'bServerSide': true,
            "bDestroy":true,
            'fnServerParams': function ( aoData ) {
            	  var dtBegin = $("#dtBegin").val();
            	  var dtEnd = $("#dtEnd").val();
            	  if(dtBegin!=null&&dtBegin!=""){
            		  dtBegin=dtBegin+" 00:00:00";
            	  }
            	  if(dtEnd!=null&&dtEnd!=""){
            		  dtEnd=dtEnd+" 23:59:59";
            	  }
            	  var processInstanceDbId =  $.trim($("#processInstanceDbId").val());
            	  if (processInstanceDbId!=""&&!(/(^[0-9]\d*$)/.test(processInstanceDbId))){
            		   art.dialog.alert("流水号必须为整数");
            		   $("#processInstanceDbId").val("");
            		   return;
            	  } 
            	  aoData.push( 
            			  { "name":"processDefinitionId", "value":$.trim($("#processDefinitionId").val()) },
				          { "name":"processState", "value":$.trim($("#processState").val()) },
				          { "name":"assignType", "value":$.trim($("#assignType").val())},
                          { "name":"dtBegin", "value":dtBegin },
                          { "name":"dtEnd", "value":dtEnd },
                          { "name":"processInstanceDbId", "value":$.trim($("#processInstanceDbId").val())},
                          { "name":"taskName", "value":$.trim($("#taskName").val())}
                );
            },
            "sAjaxSource": basePath+"jbpmApp/AjaxMyjob_serachTaskList.action",//获取管理员列表
            "sServerMethod": "POST",
            "sPaginationType": "full_numbers",
            "bPaginate": true, //翻页功能
            "bLengthChange": false, //改变每页显示数据数量
            "bFilter": false, //过滤功能
            "bSort": false, //排序功能
            "bInfo": true,//页脚信息
            "bAutoWidth": false,//自动宽度
            "iDisplayLength":20, //每页显示多少行
            "aoColumns": headColumns,
            "oLanguage": {
                "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
            },
            "fnDrawCallback": function (oSettings) {
            	 
				//提示
            	 $('#myTable tbody  tr td[class="longTxt"]').each(function() {
 	  				this.setAttribute('title', $(this).text());
 	  			});
                $("#totalNum").html(oSettings.fnRecordsDisplay());
                $("#companyAll").html(oSettings.fnRecordsDisplay());
                $("#selectedNum").html(0);
                $("#total").prop("checked",false);
                $(".morebtn").hover(function(){
            		$(this).addClass("morebtnHover");
            		},function(){
            		$(this).removeClass("morebtnHover");	
            			});//更多操作
            },
            "aoColumnDefs":dataColumns
        });

}



 

