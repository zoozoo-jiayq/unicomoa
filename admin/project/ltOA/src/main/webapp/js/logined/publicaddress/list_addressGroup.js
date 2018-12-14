$(document).ready(function() {
	queryAddress();
});

function queryAddress() {
	$('#myTable')
	        .dataTable(
	                {
	                    "bDestroy" : true,
	                    "bProcessing" : true,
	                    'bServerSide' : true,
	                    'fnServerParams' : function(aoData) {
	                    },
	                    "sAjaxSource" : basePath + "addressGroup/setup_getPublicAddressGroupList.action?maintain=1",// 获取通讯薄联系人列表
	                    "sServerMethod" : "POST",
	                    "sPaginationType" : "full_numbers",
	                    "bPaginate" : false, // 翻页功能
	                    "bLengthChange" : false, // 改变每页显示数据数量
	                    "bFilter" : false, // 过滤功能
	                    "bSort" : false, // 排序功能
	                    "bInfo" : false,// 页脚信息
	                    "bAutoWidth" : false,// 自动宽度
	                    "iDisplayLength" : 1000000, // 每页显示多少行
	                    "aoColumns" : [ {
	                        "sTitle" : '分组名称',
	                        "mDataProp" : "name",
	                        "sClass" : "tdCenter"
	                    }, {
	                        "sTitle" : '操作',
	                        "mDataProp" : null,
	                        "sClass" : "pl73"
	                    } ],
	                    "oLanguage" : {
		                    "sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
	                    },
	                    "fnDrawCallback" : function(oSettings) {
	                    	 $('#myTable tbody  tr td[class="tdCenter"]').each(function() {
	                				this.setAttribute('title', $(this).text());
	                			});
		                    //重置iframe高度
		                    window.parent.frameResize();
	                    },
	                    "fnInitComplete": function () {
	                        //重置iframe高度
	                        window.parent.frameResize();
	                    },
	                    "aoColumnDefs" : [ {
	                        "aTargets" : [ 1 ],// 覆盖第二列
	                        "fnRender" : function(oObj) {	                        	
		                        // 详情 编辑 删除
		                        var id = oObj.aData.id;
		                        // 清空 导入 导出Foxmail格式 导出OutLook格式 打印
		                        var clearUrl = '<a href="javascript:void();" onclick=clearAddressGroup("' + id + '","'
		                                + oObj.aData.name + '") >清空</a>';

		                        // 导出文件
		                        var exportUrl = '<a href="' + basePath
		                                + "address/setup_exportAddress.action?addressVo.addressGroupId=" + id
		                                + '" >导出</a>';
		                        var result = "";
		                        
		                        // 导入文件
				                var importUrl = '<a href="javascript:void(0);" onclick=importAddress("' + id + '","'
				                        + oObj.aData.name + '") >导入</a>';
				                
		                        var isMaintainPriv = oObj.aData.isMaintainPriv;
		                        if (true == isMaintainPriv || '-2' == id){
		                        	result += clearUrl;
		                        	result += importUrl;
		                        }
		                        result += exportUrl;
		                        return result;

	                        }
	                    } ]
	                });
}

/**
 * 清空分组信息
 * 
 * @param id
 */
function clearAddressGroup(id, groupName) {
	art.dialog.confirm(sprintf("addressbook.msg_clear_address_confirm&&" + groupName), function() {
		$.ajax({
		    url : basePath + "addressGroup/setup_deleteAddressGroup.action?deleteType=2&id=" + id,
		    type : "post",
		    dataType : "text",
		    success : function(data) {
			    if ("success" == data) {
				    window.location.href = window.location.href;
			    } else {
				    art.dialog.alert(sprintf("addressbook.msg_clear_group_error"));
			    }
		    }
		});
	}, function() {
		return;
	});

}

/**
 * 打开导入通讯簿联系人页面
 * 
 * @param id
 */
function importAddress(id, groupNameSrc) {
	var groupName = groupNameSrc;
	var urlStr = basePath + "logined/address/import_address.jsp?groupId=" + id + "&groupName=" + groupName;
	art.dialog.open(urlStr, {
		title : "联系人导入",
		 width : 800,
		 height : 450,
	    lock : true,
	    opacity: 0.08,
	    close : function(){
	    	var iframe = this.iframe.contentWindow;
	    	var importTr = $(iframe.document).find("#importTr");
	        if(importTr.length > 0)
	        	importTr.get(0).parentNode.removeChild(importTr.get(0));
	    	return true;
	    }
	});
}