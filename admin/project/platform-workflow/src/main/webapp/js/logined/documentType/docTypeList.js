$(document).ready(function() {
	$.removeTableCookie('SpryMedia_DataTables_myTable_docTypeList.jsp');
    getDataTable();
  //重置
  $("#chongzhi").click(function(){
  	$("input[name='doctypeName']").val("");
  	$("#categoryId").val("");
  });

	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			$.removeTableCookie('SpryMedia_DataTables_myTable_docTypeList.jsp');
		    getDataTable();
			return false;
		}
	});
}) ;
/**
 * 获取管理员信息列表
 */
function getDataTable(){
	var doctypeName = $("#doctypeName").val();
	var categoryId = $("#categoryId").val();
	qytx.app.grid({
		id:"myTable",
		url:basePath+"documentType/ajax_showList.action",
		selectParam:{"doctypeName":$.trim(doctypeName),"categoryId":categoryId},
		sPage:false,
		valuesFn:[{
		    "aTargets": [4],//覆盖第6列
		    "fnRender": function ( oObj ) {
		    	var doctypeId=oObj.aData.docType.doctypeId;
		    	var count=oObj.aData.count;
		        var res="";
		        res+="<a  onclick=\"updateDoc("+doctypeId+")\" style=\"cursor:pointer;\">修改</a> ";
		        res+="<a style=\"cursor:pointer;\" onclick=\"setUpGongwen("+doctypeId+")\" >设置</a>";
		        	if(count==0){
		        		res+=" <a onclick=\"deleteDoc("+doctypeId+")\" style=\"cursor:pointer;\">删除</a> ";
		        	}
		        return   res;
		    }
		}]
	});
}

/**
 * 设置公文各个节点属性
 */
function setUpGongwen(doctypeId){
	window.top.addTab(Math.random(),basePath+"/documentType/setUpGongwen.action?doctypeId="+doctypeId,"公文设置");
}

/**
 * 添加新类型
 */
function add(){
	document.location.href=basePath+"documentType/docTypeMan_add.action";
}

/**
 * 更新
 * @param doctypeId
 */
function updateDoc(doctypeId){
	document.location.href=basePath+"documentType/docTypeMan_updateDoc.action?doctypeId="+doctypeId;
}

/**
 * 删除
 * @param doctypeId
 */
function deleteDoc(doctypeId){
	 var paramData={
				'doctypeId':doctypeId
		 };
	 
	 art.dialog.confirm('确定要删除该公文类型吗？', function () {
		 $.ajax({
		      url:basePath+"documentType/docTypeDelete.action?r="+Math.random(),
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	        if(data==1){
		    	        	getDataTable();
		    	        }else{
		    	        	art.dialog.alert("删除失败");
		    	        }
			    		return;
		    	}
		 }); 
		 
	}, function () {
			return ;
	});
}

/**选中分类触发的事件
 * @param cateId
 */
function selectCate(cateId,ptag){
	$(".service-menu").find("p").removeClass("on");
	$(ptag).addClass("on");
	$("#categoryId").val(cateId);
	getDataTable();
}





