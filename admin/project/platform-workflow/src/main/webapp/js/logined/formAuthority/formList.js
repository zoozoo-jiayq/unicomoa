/**
 * 表单列表
 */
$(document).ready(function() {
		 var firstPage = $("#firstPage").val();
		 if(firstPage=="true"){
			 $.removeTableCookie('SpryMedia_DataTables_myTable_formList.jsp');
		 }
	    getDataTable();
	    
	    //回车自动查询
	    document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	             //要做的事情
	        	 $.removeTableCookie('SpryMedia_DataTables_myTable_formList.jsp');
	        	 getDataTable();
	        }
	    }; 
	    
	    //搜索
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
            			  { "name":"formType", "value":$.trim($("#formType").val()) },
                          { "name":"formName", "value":$.trim($("#title").val())}
                );
            },
            "sAjaxSource": basePath+"baseSet/ajax_getFormList.action",//获取管理员列表
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
                { "mDataProp": "no" ,"sClass": "num"},
                { "mDataProp": "formName" ,"sClass": "longTxt"},
                { "mDataProp": "formType"},
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
                                    var formId=oObj.aData.formId;
                                    var href = basePath+"logined/formAuthority/propertyList.jsp?formId="+formId+"&firstPage=true";
                                    return "<a style=\"cursor:pointer\" href=\""+href+"\"  >设置</a>";
                                }
                            }
                ]
        });

}

