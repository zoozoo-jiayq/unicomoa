/**
 * REN
 * 选中select
 * domEle 元素id
 */
function selectedValue(domEle){
        var selectCur=$("#"+domEle);
	    var selected=selectCur.attr("defaultValue");
		
		selectCur.find("option").each(function(){
			if(selected==$(this).attr("value")){
				$(this).prop("selected",true);
			}
		});
}
