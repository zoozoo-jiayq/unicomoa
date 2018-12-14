/**
 * 新增类别
 * 
 * @return
 */

function addType(typeValue,sysType) {
	if(typeValue == ""){
		typeValue = 1;
	}
	
	var infoType = $("#infoType").val();
	var infoOrder = $("#infoOrder").val();
	var typeName = $.trim($("#typeName").val());
	if(typeName==''){
		art.dialog.alert("类型名称不能为空！");
		return false;
	}
	

	if(infoType==""){
		art.dialog.alert("请选择上级类型！");
		return false;
	}
	if(infoOrder==""){
		art.dialog.alert("排序号不能为空！");
		return false;
	}
	
	var paramData = {
		'infoType' : infoType,
		'sysTag' : sysType,
		'name' : typeName,
		'typeValue':typeValue,
		'infoOrder':infoOrder
	};

	$.ajax({
					url : basePath + "dict/setup_addType.action",
					type : "post",
					dataType : "html",
					data : paramData,
					beforeSend:function(){
						$("body").lock();
					},
					complete:function(){
						$("body").unlock();
					},
					
					success : function(data) {
						if (data == "0") {
							art.dialog.close("addDict");
						} else if (data == "1") {
							art.dialog.alert("添加失败,类型已存在！");
						} else {
							art.dialog.alert("添加失败！");
						}
					}
				});
	
}
/**
 * 修改类别
 * 
 * @return
 */
function updateType(id,sysType,typeValue) {	
	if(typeValue == ""){
		typeValue = null;
	}
	var infoType = $("#infoType").val();
	var infoOrder = $("#infoOrder").val();
	var typeName = $.trim($("#typeName").val());
	if(typeName==''){
		art.dialog.alert("类型名称不能为空！");
		return false;
	}
	if(infoOrder==""){
		art.dialog.alert("排序号不能为空！");
		return false;
	}
	
	var paramData = {
		'id' : id,
		'name' : typeName,
		'typeValue':typeValue,
		'sysTag':sysType,
		'infoType':infoType,
		'infoOrder':infoOrder
	};
	$.ajax({
				url : basePath + "dict/setup_updateType.action",
				type : "post",
				dataType : "html",
				data : paramData,
				beforeSend:function(){
					$("body").lock();
				},
				complete:function(){
					$("body").unlock();
				},
				success : function(data) {
					if (data == 0) {
						art.dialog.close("updateDict");
					} else if (data == 1) {
						art.dialog.alert("修改失败,类型已存在！");
					} else {
						art.dialog.alert("修改失败！");
					}
				}
			});
}