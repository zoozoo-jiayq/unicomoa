/**
 * 自动执行placeholder的元素绑定
 */
$(document).ready(function(){
	var _input = $("input");
	if(_input && _input.length>0){
		$(_input).each(function(i,item){
			var placeholder = $(item).attr("placeholder");
			if(placeholder){
				funPlaceholder(item);
			}
		});
	}
});