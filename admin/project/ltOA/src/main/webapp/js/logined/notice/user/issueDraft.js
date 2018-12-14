$(document).ready(function() {
	//获得所有栏目列表
	getLmList();
	//首次进入加载全部
    getDataTable();
    $("#search").click(function(){
    	getDataTable();
    	return false;
    });
    
    //头部全选复选框
	$("#table").delegate("#total", "click", function(event){
	   	checkTotal();
		event.stopPropagation();
    });
	
	//批量删除
	$("#delete").click(deleteFun);
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			//首次进入加载全部
		    getDataTable();
			return false;
		}
	});
}) ;
/**
 *得到分页
 */
function getDataTable() {
	var title=$("#title").val();
	var columnId=$("#lm").val();
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!==""&&endTime!=""&&startTime>endTime){
		art.dialog.alert("开始时间不能大于结束时间！");
		return false;
	}
	if(startTime!=""){
		startTime = startTime + " 00:00:00";
	}
	if(endTime!=""){
		endTime = endTime + " 23:59:59";
	}
    $('#table').dataTable({
        "bProcessing": true,
        'bServerSide': true,
        'fnServerParams': function (aoData) {
        	aoData.push(
        		{ "name":"type", "value":4 },
        		{ "name":"category", "value":1 },
        		{ "name":"title", "value":title },
        		{ "name":"columnId", "value":columnId},
        		{ "name":"startTime", "value":$.trim(startTime) },
        		{ "name":"endTime", "value":$.trim(endTime) }
        	);
        },
        "sAjaxSource": basePath+"news/listIssue.action",
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
            {"sTitle":"<input type='checkbox' id='total'/>", "mDataProp": null ,"sClass": "chk","sWidth": "25"},
            {"sTitle":'序号', "mDataProp": "num" ,"sClass": "num","sWidth": "50"},
            {"sTitle":'标题', "mDataProp": null,"sClass": "data_l longTxt"},
            {"sTitle":'栏目名称', "mDataProp": "columnName" ,"sClass":"longTxt","sWidth": "210"},
            {"sTitle":'创建时间', "mDataProp": "createdDatetime","sWidth": "185"},
            {"sTitle":'操作', "mDataProp": null,"sWidth": "80" ,"sClass": "right_bdr0"}
        ],
        "oLanguage": {
            "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
        },
        "fnDrawCallback": function (oSettings) {
        	$('#table tbody  tr td').each(function() {
				this.setAttribute('title', $(this).text());
		   });
        	$("#total").attr("checked",false);
        },
        "aoColumnDefs":[
			{
			    "aTargets": [0],
			    "fnRender": function ( oObj ) {
			    	return '<input name="chk" value="' + oObj.aData.vid + '" type="checkbox"/>'; 
			    }
			},
			{
			    "aTargets": [2],
			    "fnRender": function ( oObj ) {
			    	var title = oObj.aData.title;
			    	var columnIcons = oObj.aData.columnIcons;
			    	var html="";
			    	html+='<img src="'+downPath+''+columnIcons+'" style="width:50px;height:50px;">&nbsp;&nbsp;'+title;
			    	return html;
			    }
			},{
			    "aTargets": [5],
			    "fnRender": function ( oObj ) {
			    	var vid = oObj.aData.vid;
			    	var html="";
			    	html+='<a href="'+basePath+'news/toEditIssue.action?issueId='+vid+'">编辑</a>';
			    	return html;
			    }
			}]
    });
}

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalChecked=$("input:checkbox[id='total']").prop("checked");
	var listCheckbox=$("input:checkbox[name='chk']");
	if(isTotalChecked){
		listCheckbox.prop("checked", function( i, val ) {
			if (!$(this).prop("disabled")) {
				return true;
			}
        });
	}else{
		listCheckbox.prop("checked", false);
	}
}

//获得栏目列表
function getLmList(){
	var paramData = {
            'category':2
        };
	$.ajax({
		url:	basePath+"news/listColumnTitle.action",
		type:"post",
        dataType:'json',
        data:paramData,
		success:function(data){
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var html="";
					html+="<option value=\""+data[i].vid+"\">";
					html+=data[i].title;
					html+="</option>";
					$("#lm").append(html);
				}
			}
			
		}
	});
}

//批量删除
function deleteFun(){
	 var num = 0;
	    var ids = "";
	    $("input[name='chk']").each(function () {
	        if (this.checked)
	        {
	            num++;
	            ids +=this.value + ",";
	        }

	    });
	    if (num == 0) {
	        art.dialog.alert("未选中任何记录！");
	        return;
	    }
	    art.dialog.confirm('是否确定删除？', function (){
	    var groupId = $("#groupId").val();
	    if (ids != "") {
	        var paramData = {
	            'issueIds':$.trim(ids)
	        };
	        $.ajax({
	            url: basePath+"news/deleteIssue.action",
	            type:"post",
	            dataType:'html',
	            data:paramData,
	            success:function (data) {
	            	if(data>0){
	            		art.dialog.alert("删除成功！",function(){
	            			window.location.reload();
	            		});
	            	}else{
	            		art.dialog.alert("删除失败！",function(){
	            			window.location.reload();
	            		});
	            	}
	            }});
	    } });
}