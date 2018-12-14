$(document).ready(function() { 
	
	findGroups();
	
    //单击删除
    $("#deleteBtn").click(function(){
    	deleteBatchAddress();
        return false;
    });
    
	//头部全选复选框
	$("#myTable").delegate("#total", "click", function(event){    
	   	checkTotal();
		event.stopPropagation();
    });
	
	// 子项复选框按钮
	$("#myTable").delegate(":checkbox[name='chk']", "click", function(event) {
		checkChange();
		event.stopPropagation();
	});
	
    //单击新建联系人
    $("#addAddressBtn").click(function(){
    	window.location.href = basePath + "logined/address/add_address.jsp?groupType=2&groupId=" + $("#groupId").val();
        return false;
    });
    
    //单击清空
    $("#deleteAllBtn").click(function(){
    	clearAddressGroup($("#groupId").val(), $("#groupName").val());
        return false;
    });
    
    //单击导入
    $("#importBtn").click(function(){
    	importAddress($("#groupId").val(), $("#groupName").val());
        return false;
    });
    
    //单击导出
    $("#exportBtn").click(function(){
    	exportAddress();
        return false;
    });
    
 // 回车事件
	document.onkeydown = function(e) {
		// 兼容FF和IE和Opera
		var theEvent = e || window.event;
		var code = theEvent.keyCode || theEvent.which
				|| theEvent.charCode;
		if (code == 13) {
			_checkedIds="";
			queryAddress();
		}
	};
});

function queryAddress(){
	_checkedIds="";
	$('#myTable').dataTable({
        "bDestroy":true,
        "bProcessing": true,
        'bServerSide': true,
        'fnServerParams': function ( aoData ) {
                 aoData.push( { "name":"addressVo.addressGroupId", "value":$("#groupId").val() },// 所属群组
                 { "name":"addressVo.name", "value":$.trim($("#searchkey").val()) },//关键字
                 { "name":"addressVo.sex", "value":$("#sex").val() }//性别
                 );
         },
        "sAjaxSource": basePath+"address/setup_getPublicAddressPage.action",//获取通讯薄联系人列表
        "sServerMethod": "POST",
        "sPaginationType": "full_numbers",
        "bPaginate": true, //翻页功能
        "bLengthChange": false, //改变每页显示数据数量
        "bFilter": false, //过滤功能
        "bSort": false, //排序功能
        "bInfo": true,//页脚信息
        "bAutoWidth": false,//自动宽度
        "iDisplayLength":tableDisplayLength, //每页显示多少行
        "aoColumns": [               
            {"sTitle":"<input type='checkbox' id='total'/>", "mDataProp": null },
            {"sTitle":'姓名', "mDataProp": "name"},
            {"sTitle":'性别', "sWidth" : "80px","mDataProp": "sex"},
            {"sTitle":'单位名称', "mDataProp": "companyName" ,"sClass": "longTxt" },
            {"sTitle":'职务', "mDataProp": "postInfo" },
            {"sTitle":'手机号码', "mDataProp": "familyMobileNo" },
            {"sTitle":'工作电话', "mDataProp": "officePhone"},               
            {"sTitle":'操作',"mDataProp": null,"sClass": "right_bdr0" }
        ],
        "oLanguage": {
            "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
        },
        "fnDrawCallback": function (oSettings) {
        	 $('#myTable tbody  tr td[class="longTxt"]').each(function() {
   				this.setAttribute('title', $(this).text());
   			});
            
//            window.parent.frameResize();
            $("input:checkbox[id='total']").prop("checked", false);
            _getChecked();
        },
        "fnInitComplete": function () {
            //重置iframe高度
//            window.parent.frameResize();
        },
		"aoColumnDefs":[{
             "aTargets": [0],//覆盖第一列
             "fnRender": function ( oObj ) {
            	 if ($("#maintainPriv").val() == '1'){
            		 return '<input name="chk" value="' + oObj.aData.id + '" type="checkbox" />';
            	 }else{
            		 return '<input name="chk" disabled="disabled" type="checkbox" />';
            	 }                 
             },
		     "bVisible": $("#maintainPriv").val() == '1' // 删除列是否显示
         },{
             "aTargets": [1],//覆盖第2列
             "fnRender": function ( oObj ) {
            	 var id=oObj.aData.id;
            	 var detailUrl = basePath+"address/getAllDetailInfo.action?operateType=detail&addressVo.id="+id;
                 return  '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >'+oObj.aData.name+'</a>';
             }
         },{
             "aTargets": [2],//覆盖第3列
             "fnRender": function ( oObj ) {
            	 if (oObj.aData.sex == "0"){
            		 return '女';
            	 } else if (oObj.aData.sex == "1"){
            		 return '男';
            	 }else{
            		 return '';
            	 }
             }
         },{
             "aTargets": [7],//覆盖第8列
             "fnRender": function ( oObj ) {
            	 // 详情  编辑  删除  
            	 var id=oObj.aData.id;
            	 var detailUrl = basePath+"address/getAllDetailInfo.action?operateType=detail&addressVo.id="+id;
            	 var updateUrl = basePath+"address/getAllDetailInfo.action?publicSign=1&operateType=update&addressVo.id="+id;
            	 var urlStr = '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>';
            	 if ( $("#maintainPriv").val() == '1'){
            		 urlStr += '<a href="'+updateUrl+'" >修改</a>';
            		 urlStr += '<a href="javascript:void(0);" onclick="deleteOne('+id+')">删除</a>';
            	 }
            	 return urlStr;  	 
             }
         }]  
		});
}

/**
 * 删除单条联系人信息
 * @param id
 */
function deleteOne(id){
	var paramData={
			'addressId':id
	 };
	//删除联系人
	art.dialog.confirm(sprintf("addressbook.msg_del_address_confirm"), function () {
		$.ajax({
		      url:basePath+"address/setup_deleteAddress.action",
		      type:"post",
		      dataType: "text",
		      data:paramData,
		      success: function(data){
			      if("success" == data){
			    	  queryAddress();
			      }else{
			    	  art.dialog.alert(sprintf("addressbook.msg_del_address_error"));
			      }
		      }
		 });
	}, function () {
	    return;
	});

}

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck=$("input:checkbox[id='total']").prop("checked");
	var checkNum=0;
	if ($("#maintainPriv").val()!= '1'){
		return;
	}
	if(isTotalCheck){
		$("input:checkbox[name='chk']").prop("checked", function( i, val ) {
			checkNum=checkNum+1;
            return true;
        });
	}else{
		$("input:checkbox[name='chk']").prop("checked", false);
	}
}

/**
 * 批量删除联系人信息
 */
function deleteBatchAddress() {
    //获取选择公告id
	var chkbox = document.getElementsByName("chk");
	var ids = _checkedIds;
	if (ids.length <= 1) {
		art.dialog.alert(sprintf("addressbook.msg_del_address_at_least_one"));
		return;
	}
	ids = ids.substring(0,ids.length-1).replace(/\,/g, "&addressId=");
	ids = "&addressId="+ids;
	 
	//删除任务脚本
	art.dialog.confirm(sprintf("addressbook.msg_del_multiple_address_confirm"), function () {
       $.ajax({
			url : basePath+"address/setup_deleteAddress.action?type=delete",
			type : "post",
			dataType :'text',
			data: ids,
			success : function(data) {
				if(data == "success") {
					queryAddress();
				} else {
					art.dialog.alert(sprintf("addressbook.msg_del_address_error"));
				}
			}
		});
	}, function () {
	    return;
	});
}

function openDetail(urlStr){
	// 映射页面
	art.dialog.open(urlStr, 
			{	title: '联系人信息' ,
				width:800,
				lock : true,
				opacity: 0.08,
				resize:false,
				drag:false,
				height:450,
				button: [
	               {
	                   name: '关闭',
	                   focus: true
	               }
	           ]});

}
	
/**
 * 子项复选框变更
 */
function checkChange(){
	if ($('#myTable :checkbox[name="chk"][checked="checked"]').length ==
		$('#myTable :checkbox[name="chk"]').length){
		$("input:checkbox[id='total']").prop("checked", true);
	}else{
		$("input:checkbox[id='total']").prop("checked", false);
	}
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
function importAddressOld(id, groupNameSrc) {
	var groupName = groupNameSrc;
	var urlStr = basePath + "logined/address/import_address.jsp?groupId=" + id + "&groupName=" + groupName;
	// 导入成功后刷新查询结果页面
    art.dialog.data('queryAddress', queryAddress);
	art.dialog.open(urlStr, {
		title : "联系人导入",
		 width : 800,
		 height : 450,
	    lock : true,
	    opacity: 0.1,
	    close : function(){
	    	var iframe = this.iframe.contentWindow;
	    	var importTr = $(iframe.document).find("#importTr");
	        if(importTr.length > 0)
	        	importTr.get(0).parentNode.removeChild(importTr.get(0));
	    	return true;
	    }
	});
}

var freshPage = false;
function importAddress(id, groupNameSrc) {
	var groupName = groupNameSrc;
	art.dialog.open(basePath + "logined/publicaddress/alertMsg.jsp",{
		id : 'importAddressUsers',
		title : '联系人导入',
		lock :true,
		opacity :0.08,
		width : 600,
		height : 300,
		button : [{
					name : '验 证',
					callback : function() {
						var obj = this.iframe.contentWindow;
						obj.checkFileFormat();
						return false;
					}
				}, {
					name : '导 入',
					focus:true,
					callback : function() {
						var obj = this.iframe.contentWindow;
						obj.startAjaxFileUpload(id, groupName);
						
						return false;
					}
				}, {
					name : '取 消',
					callback : function() {
						return true;
					}
				}]
	});
}
/**
 * 获取通讯簿组列表
 */
function findGroups() {
	$.ajax({
	    url : basePath + "addressGroup/setup_getPublicAddressGroupList.action?maintain=1",
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
	    	var sb = "";
	    	sb+='<h1>外部通讯录</h1><div class="menu-cont">';
		    if (null != data && data.aaData != null && "" != data.aaData) {
			    for ( var i = 0; i < data.aaData.length; i++) {
				    var groupName = data.aaData[i].name;
				    var groupId = data.aaData[i].id;
//				    if (i == 0) {
//					    sb.Append('<li onclick=openSrc("' + address_main + '") class="current" title="'
//					            + data.aaData[i].name + '"><a>' + data.aaData[i].name + '</a>');
//					    $("#address_index_main").attr("src", address_main);
//
//				    } else {
//					    sb.Append('<li onclick=openSrc("' + address_main + '") title="' + data.aaData[i].name + '"><a>'
//					            + data.aaData[i].name + '</a>');
//				    }
				    if (i== 0){				 
				    	  sb+='<p class="menu-p borderlast on" onclick="openSrc(\''+groupName+'\',\''+groupId+'\',this)"><i class="menu-i"></i><a  href="javascript:void(0)" title="'+ data.aaData[i].name + '">'+ data.aaData[i].name + '</a></p>';
				    	  openSrc(groupName,groupId);
				    	  //  $("#address_index_main").attr("src", address_main);
				    }else if(i== (data.aaData.length-1)){
				    	sb+='<p class="menu-p borderlast" onclick="openSrc(\''+groupName+'\',\''+groupId+'\',this)"><i class="menu-i"></i><a href="javascript:void(0)" title="'+ data.aaData[i].name + '">'+ data.aaData[i].name + '</a></p>';
				    }else{
				    	sb+=' <p class="menu-p" onclick="openSrc(\''+groupName+'\',\''+groupId+'\',this)"><i class="menu-i"></i><a href="javascript:void(0)" title="'+ data.aaData[i].name + '">'+ data.aaData[i].name + '</a></p>';
				    }
				    
				    
			    }

		    } else {
		    	//$("#address_index_main").attr("src", basePath + "logined/publicaddress/no_address.jsp");
		    	//window.location.href=basePath + "logined/publicaddress/no_address.jsp";
		    	$("#groupId").val(-1);
		    	queryAddress();
		    }
		    sb+="</div>";
		    $("#groupListMenu").html(sb.toString());
	    }
	});
}


function openSrc(groupName,groupId,obj) {
	$("#groupName").val(groupName);
	$("#groupId").val(groupId);
	$("#groupListMenu p").removeClass("menu-p borderlast on").addClass("menu-p borderlast");
	$(obj).addClass("menu-p borderlast on");
	// 获取当前用户,是否拥有此用户组的维护权限
	if ('-2' ==groupId){
		$("#maintainPriv").val(1);	
		$("#addAddressTd").show();
    	$("#addAddressDiv").show();
    	queryAddress();
	}else{
		$.ajax({
		    url : basePath + "addressGroup/setup_getMaintainPriv.action?deleteType=2&addressGroup.id=" + $("#groupId").val(),
		    type : "post",
		    dataType : "text",
		    success : function(data) {
			    if ("success" == data) {
			    	$("#maintainPriv").val(1);		
			    	$("#addAddressTd").show();
			    	$("#addAddressDiv").show();
			    } else {
			    	$("#maintainPriv").val(0);
			    	$("#addAddressDiv").hide();
			    	$("#addAddressTd").hide();
			    }
			    queryAddress();
		    }
		});	
}

}


/**
 * 导出联系人信息
 * 
 * @param id
 */
function exportAddress() {
	var url = basePath + "address/setup_exportAddress.action?addressVo.addressGroupId=" + $("#groupId").val() + "&addressVo.name="+$.trim($("#searchkey").val())+"&addressVo.sex="+$("#sex").val();
	window.open(url);
}