$(document).ready(function() {
	//首次进入加载全部
    getDataTable();
}) ;
/**
 *得到分页
 */
function getDataTable() {
    $('#table').dataTable({
        "bProcessing": true,
        'bServerSide': true,
        'fnServerParams': function (aoData) {aoData.push(
	            { "name":"category", "value":1 }
        );},
        "sAjaxSource": basePath+"news/listColumn.action",
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
            {"sTitle":'序号', "mDataProp": "num" ,"sClass": "num","sWidth": "50"},
            {"sTitle":'栏目名称', "mDataProp": null,"sClass": "data_l"},
            {"sTitle":'发布数量', "mDataProp": "columnIssueNum","sWidth": "185"},
            {"sTitle":'最后一次发布时间', "mDataProp": "updatedDatetime","sWidth": "185"},
            {"sTitle":'操作', "mDataProp": null,"sWidth": "80","sClass": "right_bdr0"}
        ],
        "oLanguage": {
            "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
        },
        "fnDrawCallback": function (oSettings) {
        	$('.data_l').each(function() {
				this.setAttribute('title', $(this).text());
		   });
        },
        "aoColumnDefs":[
			{
			    "aTargets": [1],
			    "fnRender": function ( oObj ) {  
			    	var title = oObj.aData.title;
			    	var titleIcon = oObj.aData.titleIcon;	    	
			    	var html="";
			    	html+='<img src="'+downPath+titleIcon+'" style="width:50px;height:50px;">&nbsp;&nbsp;'+title;
			    	return html;
			    }
			},{
			    "aTargets": [4],
			    "fnRender": function ( oObj ) {
			    	var vid = oObj.aData.vid;
			    	var html="";
			    	html+='<a href="'+basePath+'news/toAddIssue.action?category=1&columnId='+vid+'">发布</a>';
			    	return html;
			    }
			}]
    });
}
