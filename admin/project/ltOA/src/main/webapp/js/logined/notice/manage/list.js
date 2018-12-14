$(document).ready(function(){
	contentList();
	//头部全选复选框
	$("#contentList").delegate("#allCheckbox","click",function(event){
		  checkTotal();
		  event.stopPropagation();
	});
});
function add(){
	window.location.href=basePath +"logined/notice/manage/add.jsp";
}
function del(){
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
	        art.dialog.alert("请选择要删除的栏目！");
	        return;
	    }
	    art.dialog.confirm('确认要删除选择的栏目吗？', function (){
	    var groupId = $("#groupId").val();
	    if (ids != "") {
	        var paramData = {
	            'ids':$.trim(ids)
	        };
	        $.ajax({
	            url: basePath+"news/deleteColumn.action",
	            type:"post",
	            dataType:'html',
	            data:paramData,
	            success:function (data) {
	            	if(data == "0"){
	            		art.dialog.alert("删除失败!");
	            	}else{
	            		art.dialog.alert("删除成功！",function(){
	            			window.location.reload();
	            		});
	            	}
	            }});
	    } });
}
/**
* 头部全选记录
*/
function checkTotal() {
	var isTotalChecked=$("input:checkbox[id='allCheckbox']").prop("checked");
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
function contentList(){
	$('#contentList').dataTable({
				"bDestroy" : true,
				"bProcessing" : true,
				'bServerSide' : true,
				'fnServerParams' : function(aoData) {
					aoData.push(
				            { "name":"category", "value":1 }
				            );
				},
				"sAjaxSource" : basePath  + "news/showColumnList.action",//sAjaxSource属性是指定table数据来源
				"sServerMethod" : "post",
				"sPaginationType" : "full_numbers",
				"bPaginate" : true, // 翻页功能
				"bLengthChange" : false, // 改变每页显示数据数量
				"bFilter" : false, // 过滤功能
				"bSort" : false, // 排序功能
				"bInfo" : true,// 页脚信息
				"bAutoWidth" : false,// 自动宽度
				"iDisplayLength" :15, // 每页显示多少行
				"aoColumns" : [
				               {     						    
				            	   	"mDataProp" : null			
				               	},{     						    //aoColumns属性是table的列的集合，和jsp页面中对应
				            	   "mDataProp" : "num"			//指定数据中显示的数据字段名
				               }, {
				            	   "mDataProp" : "Title",
				            	   "sClass":"longTxt"//
				               }, {
				            	   "mDataProp" : "Distribution" ,
				            	   "sClass":"longTxt"//
				               },{
				            	   "mDataProp" : "Issuer",//
				            	   "sClass":"longTxt"
				               },{
				            	   "mDataProp" : "Approve" //
				               },{
				            	   "mDataProp" : "Approver"  //
				               },{
				            	   "mDataProp" : "CreatedDatetime" //
				               },{
				            	   "mDataProp" : null
				               } ],
				               "oLanguage" : {
				            	   "sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
				               },
				               "fnDrawCallback":function(oSettings){   //这个函数是呼吁每一个“画”事件,并允许您动态地修改任何方面你想要创建的DOM
				       			$(".longTxt").each(function(){
				       				this.setAttribute('title', $(this).text());
				       			});
				       			},
				               "aoColumnDefs" : [ 
								{
									"aTargets" : [0],   //由下标获取对应列
									"fnRender" : function(oObj) {
										return '<input name="chk" value="'+oObj.aData.VID+'" type="checkbox"/>';
									}
								},{
									"aTargets" : [2],   //由下标获取对应列
									"fnRender" : function(oObj) {
										var TitleIcon = oObj.aData.TitleIcon;
										var Title = oObj.aData.Title;
										return '<img src="'+downPath+''+TitleIcon+'" style="width:50px;height:50px;"/>&nbsp;&nbsp;'+Title;
									}
								},
								{
									"aTargets" : [8],   //由下标获取对应列
									"fnRender" : function(oObj) {
										var editStatus=oObj.aData.editStatus;
										var id = oObj.aData.VID;
										if(editStatus=="true"){
											var url = basePath+"news/toUpdateColumn.action?id="+id;
											return '<a href='+url+'>修改</a>';
										}else{
											var url = basePath+"news/toUpdateColumnForUsed.action?id="+id;
											return '<a href='+url+'>修改</a>';
										}
									}
								}
								]
			});
	
	
}








