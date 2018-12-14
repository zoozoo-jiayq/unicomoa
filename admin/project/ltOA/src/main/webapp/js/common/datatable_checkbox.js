/**
 * @author tang_botao
 * 列表上的全选按钮相关操作
 */

var _defaultCheckAllId="checkAll";
var _defaultCheckChildName="checkChild";

/**全选的事件绑定--在页面加载完毕后调用
 * @param {String} checkAllId 全选checkbox的id，不赋值则默认为:checkAll
 * @param {String} checkChildName 表格中具体行选中checkbox组的name,不赋值则默认为:checkChild
 */
function bindCheckAllEvent(checkAllId,checkChildName) {
	if(typeof(checkAllId)=="string"){
		_defaultCheckAllId=checkAllId;
	}
	if(typeof(checkChildName)=="string"){
		_defaultCheckChildName=checkChildName;
	}
    //初始化时清空‘全选’选中状态
    $("#"+_defaultCheckAllId).removeAttr("checked");

	$("#"+_defaultCheckAllId).click(function(event) {
		if ($(this).attr("checked") == "checked") {
			$(":checkbox[name='"+_defaultCheckChildName+"']").attr("checked", "checked");
		} else {
			$(":checkbox[name='"+_defaultCheckChildName+"']").removeAttr("checked");
		}
		event.stopPropagation();
	});
	$(":checkbox[name='"+_defaultCheckChildName+"']").click(function(event){
		if($(this).attr("checked")=="checked"){
			if($(":checkbox[name='"+_defaultCheckChildName+"']:checked").length==$(":checkbox[name='"+_defaultCheckChildName+"']").length){
				$("#"+_defaultCheckAllId).attr("checked","checked");
			}
		}else{
			$("#"+_defaultCheckAllId).removeAttr("checked");
		}
		event.stopPropagation();
	});
}

/**
 * 获取选中的checkbox值，多个值时用英文逗号分割开
 */
function getCheckedValues(){
	var values="";
	$(":checkbox[name='"+_defaultCheckChildName+"']:checked").each(function(){
		values+=this.value;
		values+=",";
	})
	return values.substr(0,values.length-1);
}

/**
 * 获取选中的checkbox的数量
 */
function getCheckedCount(){
	return $(":checkbox[name='"+_defaultCheckChildName+"']:checked").length;
}
