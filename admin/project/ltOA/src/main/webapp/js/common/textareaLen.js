/**
 * REN
 * 限制文本框输入次数
 */
jQuery.fn.maxLength = function(max){ 

	return	$(this).keyup(function(){
		
		         var curLength= $(this).val().length;   
		
		         if(curLength>=max){  
		
		             var num=$(this).val().substring(0,max-1);  
		
		             $(this).val(num);  
					 
		         }
		
		     });
};