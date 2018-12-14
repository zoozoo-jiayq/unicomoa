$(document).ready(function () {
	getList();
});

$(function() {
	/*var tableH=$(window).height()-140-51;
	$(".dc_index_right").css("height",tableH);	
	*/
	// 给左侧列表加高度
	/*var dcH=$(window).height()-91-17-5;*/
	var dcH=$(window).height()-90;
	
	$(".dc_notice").css("height",dcH);
	//	左侧列表加固定高度出现滚动条
	$(".notice_list").css("height",dcH-91);
	//右侧内容区域高度
	$(".dc_index_right").css("height",dcH);		
	/*给表格外面的div加高度，超出出现滚动条*/
	var tableH=dcH/2-30;	
	$(".index_table_box").css("height",tableH);		
})

var getList=function() {
	var paramData = {};
	$.ajax({
	    url : basePath + "performance/setup_getHomeList.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！');
		    	var html = '';
		    	if(data.length!=0){
		    		for(var i=0;i<data.length;i++){
			    		if(i%2==0){
			    			html +="<tr class='odd'>";
			    		}else{
			    			html +="<tr class='even'>";
			    		}
				    	html +="<td class='wd300 text_hide text_left' title='"+data[i].khmc+"' >"+data[i].khmc+"</td>"
						+"<td class='wd150 text_hide'>"+data[i].khjd+"</td>";
			    		if(data[i].wdzp == "未自评"){
                            html +="<td class='wd150 text_hide'><span class='color_red'>"+data[i].wdzp+"</span></td>";
						}else {
                            html +="<td class='wd150 text_hide'><span>"+data[i].wdzp+"</span></td>";
						}

				    	if(data[i].ydfrs!=data[i].xdfrs&&data[i].xdfrs){
                            html+="<td class='wd150 text_hide'><span class='color_red'>"+data[i].ydfrs+"/"+data[i].xdfrs+"</span></td>";
				    	}else{
                            html+="<td class='wd150 text_hide'><span>"+data[i].ydfrs+"/"+data[i].xdfrs+"</span></td>";
				    	}
						html +="<td class='wd100 text_hide'><a class='enter_color' href='"+basePath+"logined/appraisal/jsp/staff/main.jsp?tid="+data[i].vid+"'>进入</a></td>"
						+"</tr>";
			    	}
		    	}else{
		    		html ="<tr><td colspan='5'>暂无数据</td></tr>";
		    	}		    	
		    	/*$("#myTable tr").eq(0).nextAll().remove();//将第一行之后的行全部移除（留表头）
		    	$(html).insertAfter($("#myTable tr").eq(0));*/
		    	$("#firstTr").append(html);
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};

/*function getList(){
	var userId=$("#userId").val();
	$('#myTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
						"name" : "recordWork.userInfo.userId",
						"value" : userId
					}
				);
		},
		"sAjaxSource" : basePath+"/logined/recordOther/findlistByPage.action",// 
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"bDestroy" : true,
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [
			{
					"mDataProp" : "no",
				},{
					"mDataProp" : "name",
				}, {
					"mDataProp" : "workUnit",
				    "sClass" : "longTxt"
				}, {
					"mDataProp" : "industryCategory",
					"sClass" : "longTxt"
				}, {
					"mDataProp" :"position",
					"sClass" : "longTxt"
				}, {
					"mDataProp" : "reterence"
				},{
					"mDataProp" : null
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			$('#dataTable_work tbody  tr td').each(function() {
				this.setAttribute('title', $(this).text());
			});
              
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
		
		},
		"aoColumnDefs" : [{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				var vid = oObj.aData.id;
				var html='<a href="javascript:void(0);" onclick="findDetail('+vid+')">查看</a>';
				html +='<a href="javascript:void(0);" onclick="editorWorkDeatil('+vid+')">修改</a>';
				return html;
			}
		}]
	});
}	*/

