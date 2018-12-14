$(document).ready(function() {
    getDataTable();
    $(".add").click(function(){
    	if(window.top && window.top.addTab){
    		window.top.addTab(Math.random(),basePath+'jbpmflow/listSearch!often.action',"新增申请");
    	}else{
    		window.open(basePath+'jbpmflow/listSearch!often.action',"新增申请");
    	}
    });
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
                       aoData.push( { "name":"searchkey", "value":$.trim($("#searchkey").val())  } );
            },
            "sAjaxSource": basePath+"jbpmflow/listSearch!startTaskList.action",//获取管理员列表
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
                {"mDataProp": "no" },
                {"mDataProp": "createTime"},
                {"mDataProp": "title","sClass":"longTxt" },
                {"mDataProp": null,"sClass": "longTxt"},
                {"mDataProp": "processType","sClass": "longTxt"},
                { "mDataProp": "categoryName","sClass": "longTxt"},
                { "mDataProp": "taskStart","sClass": "tdCenter"},     
                { "mDataProp": null,"sClass": "longTxt"},
                { "mDataProp": null,"sClass": "right_bdr0 data_l "}
            ],
            "oLanguage": {
                "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
            },
            "fnDrawCallback": function (oSettings) {
                $('#myTable tbody  tr td[class="longTxt"]').each(function() {
	  				this.setAttribute('title', $(this).text());
	  			});
            },
            "aoColumnDefs":[
                {
                	 "aTargets": [2],
                     "fnRender": function ( oObj ) {
                         var processId =oObj.aData.processId;
                         var taskName=oObj.aData.taskName;
                     	var res="";      
 				        var processInstanceId=oObj.aData.processInstanceId;
 				        var title = oObj.aData.title;
 				        res+="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+processInstanceId+"')\" >"+title+"</a>";
 				        return   res;
                     }
                },{
                	"aTargets": [3],
                    "fnRender": function ( oObj ) {
                    	var res="";      
				        var processInstanceId=oObj.aData.processInstanceId;
				         var taskNameShow=oObj.aData.taskNameShow;
				         var processId = oObj.aData.processId;
				        res+="<a style=\"cursor: pointer;\"  onclick=\"viewHistory("+processId+",'"+processInstanceId+"')\" >"+taskNameShow+"</a>";
				        return   res;
                    }
                },{
                	"aTargets":[6],
                	"fnRender":function(oObj){
                		var taskStart = oObj.aData.taskStart;
                		var processInstanceId=oObj.aData.processInstanceId;
                		var a = "<a id='"+processInstanceId+"' href=javascript:showInfo('"+processInstanceId+"'); >"+taskStart+"</a>";
                		return a;
                	}
                },{
                	"aTargets": [7],
                    "fnRender": function ( oObj ) {
                    	var state=oObj.aData.state;
				        return   getShowState(state);
                    }
                },{
                    "aTargets": [8],//覆盖第6列
                    "fnRender": function ( oObj ) {
                        var processId =oObj.aData.processId;
                        var isCanCancel = oObj.aData.isCanCancle;
                        var isCanRedo = oObj.aData.isCanRedo;
                        var taskName= oObj.aData.taskName;
                    	var res="";      
				        var processInstanceId=oObj.aData.processInstanceId;
				        res+="<a style=\"cursor: pointer;\"  onclick=\"goTaskView("+processId+",'"+processInstanceId+"')\" >查看</a>";
				        
				        if(isCanCancel == 'yes'){
				        	res+="<a   style=\"cursor: pointer;\" onclick=\"repeal('"+processInstanceId+"')\"  >撤销</a>";
				        }
				        if(isCanRedo == "yes"){
				        	res+="<a style=\"cursor: pointer;\"  onclick=\"reDo("+processId+",'"+processInstanceId+"')\" >重新申请</a>";
				        }
				        return   res;
                    }
                }
                
                ]
        });
}

/**
 * 重发
 */
function reDo(processId,processInstanceId){
	 var viewStr=basePath+"jbpmflow/detailSearch!toStartProcess.action?redo=redo&processId="+processId+"&processInstanceId="+encodeURI(processInstanceId);
	 if(window.top && window.top.addTab){
		 window.top.addTab(Math.random(),viewStr,"重新申请");
	 }else{
		 window.open(viewStr,"重新申请");
	 }
	 
}

/*
 * 撤销任务
 */
function repeal(intanceId){
	qytx.app.dialog.confirm("确认撤销此任务吗?",function(){
		qytx.app.dialog.openUrl({
			url:basePath+"logined/jbpmApp/repeatDialog.jsp",
			title:"撤销任务",
			size:"M",
			ok:function(){
				var ifr = this.iframe;
				var subWin = ifr.contentWindow;
				var content = $("#advice",subWin.document).val();
				if(!content){
					art.dialog.alert("撤销原因不能为空!");
					return false;
				}
				$("body").lock();
				$.post(basePath+"jbpmflow/operation!repealTask.action?processInstanceId="+encodeURI(intanceId)+"&advice="+encodeURI(content),function(data){
					if(data == 1){
						window.location.href=basePath+"logined/jbpmApp/myjob/start.jsp";
					}else{
						art.dialog.alert("撤销失败！");
					}
				});
				return true;
			},
			cancel:true
		});
	})
}


 