/**
 * 过滤特殊字符
 * @param th
 */
function cleanSpelChar(th){
    if(/["'_<>%;)(&+]/.test(th.value)){
          $(th).val(th.value.replace(/["'_<>%;)(&+]/,""));
    }
}

/**
 * 输入框只能输入数字
 * @param obj
 */
function validateNum(obj){
	if(!/^(\d)*$/.test(obj.value)){//验证需要增加别的字符的时候/^(\d|;|,)*$/
		obj.value = obj.value.replace(/[^\d]/g,'');
	}
}


/**
 *  页面特殊字符的限制
 */
$(document).ready(function(){
     setTimeout(function(){
    	 $("input[type='text']").each(function (i) { 
    		     $(this).bind('keyup', function(){
    		    	 cleanSpelChar(this);   
    		      });
    		     
    	     }); 
    	 
    	 $("textarea").each(function(){
    		    $(this).bind('keyup', function(){
   		    	 cleanSpelChar(this);   
   		      });
    	 });
     },200);
});
