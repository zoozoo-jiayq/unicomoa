$(document).ready(function() {
	_checkedIds=""; 
	
	findGroups();
	
	setTimeout(function(){
		var isInit = $("#isInit").val();
		if (null != isInit && "" != isInit){		
		//	$("#searchArea").hide();
			//$("#dataArea").show();
			queryAddress();
		}
	}, 100);
	
	
    //单击查询
    $("#search").click(function(){
    	_checkedIds="";
    	$("#searchArea").hide();
    	$("#searchArea").attr("style", "display: none;");
    	$("#dataArea").show();
    	$("#dataArea").attr("style", "");
    	$("#isInit").val("1");
    	queryAddress();
        return false;
    });
    
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
	
    //单击返回
    $("#backBtn").click(function(){
    	$("#searchArea").show();
    	$("#dataArea").hide();
    	window.parent.frameResize();
        return false;
    });
    
    //回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			_checkedIds="";
	    	$("#searchArea").hide();
	    	$("#searchArea").attr("style", "display: none;width:auto;")
	    	$("#dataArea").show();
	    	$("#dataArea").attr("style", "");
	    	$("#isInit").val("1");
	    	queryAddress();
	        return false;
        }
	});
	
	// 删除标题是否显示
	if ($("#publicSign").val() == "1"){
		$("#buttonDiv").hide();
	}
	
	queryAddress();
});

/**
 * 获取通讯簿组列表
 */
function findGroups()
{
	var urlStr = "";
	if ($("#publicSign").val() == "1"){
		urlStr = basePath + "addressGroup/setup_getPublicAddressGroupList.action?maintain=1";
	}else{
		urlStr = basePath + "addressGroup/setup_getAddressGroupList.action";
	}
    qytx.app.ajax({
        url :  urlStr,
        type : "post",
        dataType :'json',
        success : function(data) {
			if(data.aaData!=null){
				for(var i=0;i<data.aaData.length;i++){
				    // 联系人组
					$("#addressGroupId").append('<option value='+data.aaData[i].id+'>'+data.aaData[i].name+'</option>');
				}
				if ($("#publicSign").val() != "1"){
				    findPublicGroups();
				}
			}
	    }
	});
}

/**
 * 获取公共通讯簿组列表
 */
function findPublicGroups() {
	qytx.app.ajax({
		url : basePath + "addressGroup/setup_getPublicAddressGroupList.action?maintain=1",
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
		    if (null != data && data.aaData != null) {
			    var sb = new StringBuilder();

			    for ( var i = 0; i < data.aaData.length; i++) {
				    var groupName = encodeURI(data.aaData[i].name);
				    
				    if (data.aaData[i].id == "-2"){
				    	continue;
				    }
	                $("#addressGroupId").append('<option value="'+data.aaData[i].id+'">'+data.aaData[i].name+'（公共）</option>');
			    }
		    }
	    }
	});
}

function queryAddress(){
	_checkedIds="";
	if ($("#publicSign").val() == "1"){
		queryPublicAddress();
		$("#publicTable").show();
		$("#myTable").hide();
	}else{
		queryOwnAddress();
		$("#myTable").show();
		$("#publicTable").hide();
	}
}

function queryOwnAddress(){
	$('#myTable').dataTable({
        "bDestroy":true,
        "bProcessing": true,
        'bServerSide': true,
        'fnServerParams': function ( aoData ) {
                 aoData.push( { "name":"addressVo.addressGroupId", "value":$("#addressGroupId").val() }, // 所属群组
                              { "name":"addressVo.name", "value":$.trim($("#name").val()) }, //姓名
                              { "name":"addressVo.sex", "value":$("#sex").val() }, //性别
                              { "name":"addressVo.startDate", "value":$.trim($("#startDate").val()) }, //生日查询开始时间
                              { "name":"addressVo.endDate", "value":$.trim($("#endDate").val()) }, //生日查询结束时间
                              { "name":"addressVo.nickname", "value":$.trim($("#nickname").val()) }, //昵称
                              { "name":"addressVo.companyName", "value":$.trim($("#companyName").val()) }, //单位名称
                              { "name":"addressVo.officePhone", "value":$.trim($("#officePhone").val()) }, //工作电话
                              { "name":"addressVo.companyAddress", "value":$.trim($("#companyAddress").val()) }, //单位地址
                              { "name":"addressVo.familyPhoneNo", "value":$.trim($("#familyPhoneNo").val()) }, //家庭电话
                              { "name":"addressVo.familyAddress", "value":$.trim($("#familyAddress").val()) }, //家庭住址
                              { "name":"addressVo.familyMobileNo", "value":$.trim($("#familyMobileNo").val()) }, //手机
                              { "name":"addressVo.remark", "value":$.trim($("#remark").val()) },//备注
                              { "name":"addressVo.publicSign", "value":$.trim($("#publicSign").val()) } //是否查询公共通讯簿
                 );
         },
        "sAjaxSource": basePath+"address/setup_getAddressList.action",//获取通讯薄联系人列表
        "sServerMethod": "POST",
        "sPaginationType": "full_numbers",
        "bPaginate": false, //翻页功能
        "bLengthChange": false, //改变每页显示数据数量
        "bFilter": false, //过滤功能
        "bSort": false, //排序功能
        "bInfo": false,//页脚信息
        "bAutoWidth": false,//自动宽度
        "iDisplayLength":1000000, //每页显示多少行
        "aoColumns": [               
            {"sTitle":"<input type='checkbox' id='total'/>", "mDataProp": null,"sClass":"chk" },
            {"sTitle":'组名', "mDataProp": "groupName","sClass": "tdCenter" },
            {"sTitle":'姓名', "mDataProp": "name","sClass": "longTxt"},
            {"sTitle":'性别', "mDataProp": "sex"},
            {"sTitle":'单位/部门', "mDataProp": "companyName" ,"sClass": "longTxt" },
            {"sTitle":'工作电话', "mDataProp": "officePhone"},               
            {"sTitle":'家庭电话', "mDataProp": "familyPhoneNo"},
            {"sTitle":'手机号码', "mDataProp": "familyMobileNo" },
            {"sTitle":'电子邮件', "mDataProp": "familyEmail","sClass": "longTxt"},
            {"sTitle":'操作', "sWidth" : "70px","mDataProp": null,"sClass":"right_bdr0"}
        ],
        "oLanguage": {
            "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
        },
        "fnDrawCallback": function (oSettings) {
        	$('#myTable tbody  tr td[class="longTxt"]').each(function() {
				this.setAttribute('title', $(this).text());
			});
            
            _getChecked();
        },
        "fnInitComplete": function () {
            //重置iframe高度
           window.parent.frameResize();
        },
		"aoColumnDefs":[{
             "aTargets": [0],//覆盖第一列
             "fnRender": function ( oObj ) {            	 
            	 var addressGroupId = oObj.aData.addressGroupId;
            	 var isMaintainPriv=oObj.aData.isMaintainPriv;
            	 if (!isMaintainPriv){
            		 return  "";
            	 }
                 return '<input name="chk" value="' + oObj.aData.id + '" type="checkbox" />';
             }
         },{
             "aTargets": [2],//覆盖第三列
             "fnRender": function ( oObj ) {
            	 var id=oObj.aData.id;
            	 var detailUrl = basePath+"address/getAllDetailInfo.action?operateType=detail&addressVo.id="+id;
                 return  '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >'+oObj.aData.name+'</a>';
             }
         },{
             "aTargets": [3],//覆盖第四列
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
             "aTargets": [9],//覆盖第10列
             "fnRender": function ( oObj ) {
            	 // 详情  修改  删除  
            	 var id=oObj.aData.id;
            	 
            	 var detailUrl = basePath+"address/getAllDetailInfo.action?operateType=detail&addressVo.id="+id;
            	 var updateUrl = basePath+"address/getAllDetailInfo.action?operateType=update&addressVo.id="+id;
            	 
            	 // 公共通讯簿组联系人需要判断维护权限
            	 if ($("#publicSign").val() == '1'){
            		 var isMaintainPriv=oObj.aData.isMaintainPriv;
            		 
            		 if (isMaintainPriv == true){
            			 updateUrl += "&publicSign=1";
            			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>' + '<a href="'+updateUrl+'" >修改</a>'
                    	 + '<a href="javascript:void(0);" onclick="deleteOne('+id+')">删除</a>';
            		 }else{
            			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>';
            		 }
            	 }else{
            		 var isMaintainPriv=oObj.aData.isMaintainPriv;
            		 if (isMaintainPriv){
            			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>' + '<a href="'+updateUrl+'" >修改</a>'
                    	 + '<a href="javascript:void(0);" onclick="deleteOne('+id+')">删除</a>';   
            		 }else{
            			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>';
            		 }
            	 }	 
             }
         }]  
		});
}

function queryPublicAddress(){
	_checkedIds="";
	qytx.app.grid({
		id	:	"publicTable",
		url	:	basePath+"address/setup_getAddressList.action",//获取通讯薄联系人列表
		bInfo	:	false,
		selectParam	:	{
			"addressVo.addressGroupId":$("#addressGroupId").val(), // 所属群组
            "addressVo.name":$.trim($("#ncp").val()), //姓名
            "addressVo.sex":$("#sex").val(), //性别
            "addressVo.startDate":$.trim($("#startDate").val()), //生日查询开始时间
            "addressVo.endDate":$.trim($("#endDate").val()), //生日查询结束时间
            "addressVo.nickname":$.trim($("#nickname").val()), //昵称
            "addressVo.companyName":$.trim($("#ncp").val()), //单位名称
            "addressVo.officePhone":$.trim($("#officePhone").val()), //工作电话
            "addressVo.companyAddress":$.trim($("#companyAddress").val()), //单位地址
            "addressVo.familyPhoneNo":$.trim($("#familyPhoneNo").val()), //家庭电话
            "addressVo.familyAddress":$.trim($("#familyAddress").val()), //家庭住址
            "addressVo.familyMobileNo":$.trim($("#ncp").val()), //手机
            "addressVo.remark":$.trim($("#remark").val()),//备注
            "addressVo.publicSign":$.trim($("#publicSign").val()) //是否查询公共通讯簿
			},
		valuesFn	:	[{
			            "aTargets": [1],//覆盖第三列
			            "fnRender": function ( oObj ) {
			           	 var id=oObj.aData.id;
			           	 var detailUrl = basePath+"address/getAllDetailInfo.action?operateType=detail&addressVo.id="+id;
			                return  '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >'+oObj.aData.name+'</a>';
			            }
			        },{
			            "aTargets": [2],//覆盖第四列
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
			            "aTargets": [8],//覆盖第10列
			            "fnRender": function ( oObj ) {
			           	 // 详情  修改  删除  
			           	 var id=oObj.aData.id;
			           	 
			           	 var detailUrl = basePath+"address/getAllDetailInfo.action?operateType=detail&addressVo.id="+id;
			           	 var updateUrl = basePath+"address/getAllDetailInfo.action?operateType=update&addressVo.id="+id;
			           	 
			           	 // 公共通讯簿组联系人需要判断维护权限
			           	 if ($("#publicSign").val() == '1'){
			           		 var isMaintainPriv=oObj.aData.isMaintainPriv;
			           		 
			           		 if (isMaintainPriv == true){
			           			 updateUrl += "&publicSign=1";
			           			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>' + '<a href="'+updateUrl+'" >修改</a>'
			                   	 + '<a href="javascript:void(0);" onclick="deleteOne('+id+')">删除</a>';
			           		 }else{
			           			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>';
			           		 }
			           	 }else{
			           		 var isMaintainPriv=oObj.aData.isMaintainPriv;
			           		 if (isMaintainPriv){
			           			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>' + '<a href="'+updateUrl+'" >修改</a>'
			                   	 + '<a href="javascript:void(0);" onclick="deleteOne('+id+')">删除</a>';   
			           		 }else{
			           			 return '<a href="javascript:void(0);" onclick=openDetail("'+detailUrl+'") >查看</a>';
			           		 }
			           	 }	 
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
	qytx.app.dialog.confirm(sprintf("addressbook.msg_del_address_confirm"), function () {
		qytx.app.ajax({
		      url:basePath+"address/setup_deleteAddress.action",
		      type:"post",
		      data:paramData,
		      success: function(data){
			      if("success" == data){
			    	  queryAddress();
			      }else{
			    	  qytx.app.dialog.alert(sprintf("addressbook.msg_del_address_error"));
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
	var ids = "";
    var selLen = 0;
    var arr;
	if(_checkedIds!=null&&_checkedIds!=""){
		var checkedIds=_checkedIds.substring(0, _checkedIds.length-1);
		var arr=checkedIds.split(",");
		for (var i = 0; i < arr.length; i++) {
			ids += '&addressId='+arr[i];
			selLen++;
		}
	}
    if (selLen == 0) {
    	qytx.app.dialog.alert(sprintf("addressbook.msg_del_address_at_least_one"));
    	return;
    }
	 
	//删除任务脚本
	qytx.app.dialog.confirm(sprintf("addressbook.msg_del_multiple_address_confirm"), function () {
       qytx.app.ajax({
			url : basePath+"address/setup_deleteAddress.action?type=delete",
			type : "post",
			data: ids,
			success : function(data) {
				if(data == "success") {
					queryAddress();
				} else {
					qytx.app.dialog.alert(sprintf("addressbook.msg_del_address_error"));
				}
			}
		});
	}, function () {
	    return;
	});
}

function openDetail(urlStr){
	// 映射页面
	qytx.app.dialog.openUrl({
		url	:	urlStr,
		title	:	"联系人信息",
		size	:	"L",
		customButton	:	[
			  	               {
				                   name: '关闭',
				                   focus: true
				               }
			  	             ]
		});
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