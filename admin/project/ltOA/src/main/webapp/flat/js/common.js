/**
 * 过滤特殊字符
 * @param th
 */
function cleanSpelChar(th){
	    var pattern = /["'_<>%;)*(&+]/;
	    var rs='';
        if(pattern.test(th.value)){
    	    var str = $(th).val();
    	    for (var i = 0; i < str.length; i++) {
    	        rs = rs + str.substr(i, 1).replace(pattern, '');
    	    }
           $(th).val(rs);
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
	    		 if(!$(this).hasClass("noSqlLimit")) {
	    			 $(this).bind('keyup', function(){
	    		    	 cleanSpelChar(this);   
	    		      });
	    		 }
    	     }); 
    	 
    	 $("textarea").each(function(){
    		    $(this).bind('keyup', function(){
   		    	 cleanSpelChar(this);   
   		      });
    		    $(this).bind('blur', function(){
      		    	 cleanSpelChar(this);   
      		      });
    	 });
     },200);
});
