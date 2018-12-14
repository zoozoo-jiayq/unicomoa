$(function(){

	$("#addType1").click(function(){
		var valid=validator($("#addTypeName")[0]);
		 if(valid){
		addType();
		 }
	});
	
	$("#updateType1").click(function(){
		var valid=validator($("#updateType")[0]);
		 if(valid){
		 updateType();
		 }
	});
	
	$("#typeName").focus();
});

function addType(){
	var valid=validator($("#addTypeName")[0]);
	 if(valid){
			var orderNo = $("#orderType").val();
			var name= $("#typeName").val();
			var parentId= $("#parentIds").val();
			var isPrivate = $("#isPrivate").val();
		 
			if(parentId==null || parentId==""){
				art.dialog.alert("请选择类型的路径");
				return ;
			}

			if(name==null || name==""){
				art.dialog.alert("类别不能为空");
				return ;
			}
			
			if(name.length>10){
				art.dialog.alert("类别名称最长为10个字符！");
					return ;
			}

		 
			
			var paramData = {
				'orderIndex' : orderNo,
				'name' : name,
				'parentId':parentId,
				'columnId':$("#columnId").val(),
				'isPrivate':isPrivate
			};


			$.ajax({
						url : basePath  + "knowledge/knowledgeType_updateType.action",
						type : "post",
						dataType : "html",
						data : paramData,
						success : function(data) {
							if (data == 0) {
//								art.dialog.alert('添加成功！',function(){
									parent.art.dialog.list["addAttach2"].close();
//								});		
							}else if(data==2){
								art.dialog.alert("操作失败，您的权限不够！");
							}else if(data==3){
								art.dialog.alert("操作失败，顶级类型不能修改！");
							}else if(data==1){
								art.dialog.alert("分类名称已存在！");
							}else {
								art.dialog.alert("添加失败，稍后再试！");
							}
						}
					});
	 }
	
	
	
}


function updateType(){	
	
	var valid=validator($("#updateType")[0]);
	 if(valid){
		 var typeName = $("#typeName").val();
			var typeId = $("#typeId").val();
			var orderNum =$("#orderNum").val();
			var parentId =$("#parentId").val();
			var isPrivate = $("#isPrivate").val();
			var t1 = /^(-)*[1-9]{1}\d{0,2}$/;
			
			if(typeName==null || ""==typeName){
				art.dialog.alert("类别名称不能为空");
					return false;
			}
			if(typeName.length>10){
				art.dialog.alert("类别名称最长为10个字符！");
					return false;
			}
//			if(orderNum!=null&& orderNum!=""){
//				 
//				if(!t1.test(orderNum)){
//				
//					art.dialog.alert("序号必须为三位以内数字！");
//					return false;
//				}
//				}
			
			
			var paramData = {
				'vid':typeId,
				'parentId':parentId,
				'name':typeName,
				'orderIndex':orderNum,
				'isPrivate':isPrivate
			};
			$.ajax({
						url : basePath + "knowledge/knowledgeType_updateType.action",
						type : "post",
						dataType : "html",
						data : paramData,
						success : function(data) {
				        
							if (data == 0) {
//								art.dialog.alert('修改成功！',function(){
									parent.art.dialog.list["addAttach3"].close();
//								});
							}else if(data==2){
								art.dialog.alert("操作失败，您的权限不够！");
							}else if(data==3){
								art.dialog.alert("操作失败，顶级类型不能修改！");		
							}else if(data==1){
								art.dialog.alert("分类名称已存在",function(){
									art.dialog.close();
								});
							}else {
								art.dialog.alert("修改失败，稍后再试",function(){
									art.dialog.close();
									});
							}
						}
					});
	 }
	
	
}