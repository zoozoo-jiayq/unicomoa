$(document).ready(function(){
	loadProperty();
});

/**
 * 加载用户属性
 **/
function loadProperty(){
	qytx.app.ajax({
		url:	basePath+"secret/propertyList.action",
		type:	"post",
		dataType:"json",
		success:function(data){
			$(data).each(function(i,item){
				$("input[name='property'][value='"+item.attribute+"']").attr("checked","checked");
			});
		}
	});
}


/**
 * 提交设置
 */
function sub(){
	var propertyList=[];
	$($("input[name='property']")).each(function(index,obj){
		if($(obj).attr("checked")){
			var property = {
					attribute:$(obj).val(),
					attributeName:$(obj).attr("title")
			};
			propertyList.push(property);
		}
	});
	
	qytx.app.ajax({
		url:	basePath+"secret/saveProperty.action",
		type:	"post",
		shade:	"true",
		data:	{"propertyListJson":JSON.stringify(propertyList)},
		dataType:"text",
		success:function(data){
			if(data=="1"){
				qytx.app.dialog.tips("保存成功！",function(){
					window.location.reload();
				});
			}else{
				qytx.app.dialog.alert("操作失败！",function(){
					window.location.reload();
				});
			}
		}
	});
}