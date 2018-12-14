/**
 * 表单属性列表
 */
$(document).ready(function() {
		 var firstPage = $("#firstPage").val();
		 if(firstPage=="true"){
			 $.removeTableCookie('SpryMedia_DataTables_myTable_propertyList.jsp');
		 }
	    getDataTable();
	    document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	             //要做的事情
	        	 $.removeTableCookie('SpryMedia_DataTables_myTable_propertyList.jsp');
	        	 getDataTable();
	        }
	    }; 
	    
	    $("#searchButton").click(function(){
	    	 $.removeTableCookie('SpryMedia_DataTables_myTable_formList.jsp');
        	 getDataTable();
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
            	  aoData.push( 
            			  { "name":"formId", "value":$.trim($("#formId").val()) }
                );
            },
            "sAjaxSource": basePath+"baseSet/ajax_getPropertyList.action",//获取管理员列表
            "sServerMethod": "POST",
            "sPaginationType": "full_numbers",
            "bPaginate": true, //翻页功能
            "bLengthChange": false, //改变每页显示数据数量
            "bFilter": false, //过滤功能
            "bSort": false, //排序功能
            "bInfo": true,//页脚信息
            "bAutoWidth": false,//自动宽度
            "bStateSave": true, // 状态保存
            "bDestroy":true,
            "iDisplayLength":20, //每页显示多少行
            "aoColumns": [
                { "mDataProp": "no","sClass": "tdCenter"},
                { "mDataProp": "propertyNameCh" ,"sClass": "longTxt"},
                { "mDataProp": "userNames","sClass": "longTxt"},
                { "mDataProp": null,"sClass": "right_bdr0"}
            ],
            "oLanguage": {
                "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
            },
            "fnDrawCallback": function (oSettings) {
                $("#totalNum").html(oSettings.fnRecordsDisplay());
                $("#companyAll").html(oSettings.fnRecordsDisplay());
                $("#selectedNum").html(0);
              //提示
                $('#myTable tbody  tr td[class="longTxt"]').each(function() {
	  				this.setAttribute('title', $(this).text());
	  			});
                $("#total").prop("checked",false);
                $(".morebtn").hover(function(){
            		$(this).addClass("morebtnHover");
            		},function(){
            		$(this).removeClass("morebtnHover");	
            			});//更多操作
            },
            "aoColumnDefs":[
                            {
                                "aTargets": [3],//覆盖第6列
                                "fnRender": function ( oObj ) {
                                    var formAuId=oObj.aData.formAuId;
                                    var propertyId=oObj.aData.propertyId;
                                    var userIds = oObj.aData.userIds;
                                    return "<a style=\"cursor:pointer\"  onclick=\"selectAuthor("+formAuId+","+propertyId+",'"+userIds+"')\" >设置编辑权限</a>";
                                }
                            }
                ]
        });

}

var _formAuId;
var _propertyId;
/**
 * 添加按钮
 * 
 * @param obj
 * @return
 */
function selectAuthor(formAuId,propertyId,userIds) {
	    
		openSelectUser(3, selectRangeAllUserCallBack, null,userIds, 'notifySet');
		_formAuId = formAuId;
		_propertyId = propertyId;
}

/**
 * 人员(回调函数)
 * 
 * @param data
 * @return
 */
function selectRangeAllUserCallBack(data) {
	/***************************************************************************
	 * var ids = $("#range_all_user_id").val(); if(ids!=""&&ids!=null){
	 * ids=ids+","; } var names= $("#range_all_user").val();
	 * if(names!=""&&name!=null){ names=names+","; }
	 **************************************************************************/
	var ids = '';
	var names = '';
	data.forEach(function(value, key) {
				ids += value.Id + ',';
				names += value.Name + ',';
			});
	var url= basePath+"baseSet/ajax_setAuthority.action";
	var formId = $("#formId").val();
	var paramData = {
			'formAuId':_formAuId,
			'userIds':ids,
			'formId':formId,
			'propertyId':_propertyId
		};
		$.ajax({
			url : url,
			type : "post",
			dataType :'text',
			data:paramData,
			success : function(data) {
				if(data != "") {
					 getDataTable();
	            } 
			}
		});
	$("#range_all_user_id").val(ids);
	$("#range_all_user").val(names);
}


/**
 * 返回
 */
function goFomrListPage(){
	var url = basePath+"logined/formAuthority/formList.jsp";
	window.location.href=url;
}
