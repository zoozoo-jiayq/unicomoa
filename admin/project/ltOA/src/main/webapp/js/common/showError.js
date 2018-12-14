/**
 * 统一显示错误信息
 */

/**
 * @author REN
 */
$(document).ready(function() {

});
/**
 * @author REN
 * 显示错误信息
 * domEleId 元素id
 * errMsg  提示信息
 */
function showError(domEleId,errMsg) {
	if(!domEleId){
		return;
	}
	if(!errMsg){
		return;
	}
	var objEle=$("#"+domEleId);
	showObjError(objEle,errMsg);
}
/**
 * @author REN
 * 显示错误信息
 * JObjEle jquery对象元素
 * errMsg  提示信息
 */
function showObjError(JObjEle,errMsg) {
	if(!JObjEle){
		return;
	}
	if(!errMsg){
		return;
	}
	$(JObjEle).focus();
	$(document).scrollTop(0);
	
	var objEle=JObjEle;
	
	//设置元素边框
	objEle.addClass("inputError");
	if(objEle.is(":input")||objEle.is("textarea")){
		var firstValue=$.trim(objEle.val());
		objEle.keyup(function(){
			var nextValue=$.trim(objEle.val());
			if(firstValue!=nextValue){
				hideError(JObjEle);
			}
		});
		objEle.focusout(function(){
			var nextValue=$.trim(objEle.val());
			if(firstValue!=nextValue){
				hideError(JObjEle);
			}
		});		
	}
    if(objEle.is("select")){
		objEle.change(function(){
			hideError(JObjEle);
		});
	}
    $(JObjEle).nextAll("p.requireField").remove();
    
    $(objEle).parent().children().last().after('<p class="requireField">'+sprintf(errMsg)+'</p>');
}
/**
 * @author REN
 * 隐藏错误信息
 */
function hideError(JObjEle){
	$(JObjEle).nextAll("p.requireField").remove();
	$(JObjEle).removeClass("inputError");
}

//重写错误显示方式
FormValid.showError = function(errMsg,errName) {
	showObjError($(errName[0]),errMsg[0]);
}
