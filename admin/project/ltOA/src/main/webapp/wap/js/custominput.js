;(function($){
	$.fn.hcheckbox=function(options){
		$(':checkbox+label',this).each(function(){
			$(this).addClass('checkbox');
            if($(this).prev().is(':disabled')==false){
                if($(this).prev().is(':checked'))
				    $(this).addClass("checked");
            }else{
                $(this).addClass('disabled');
            }
		}).unbind("click").click(function(event){
				if(!$(this).prev().is(':checked')){
				    $(this).addClass("checked");
                    $(this).prev()[0].checked = true;
                }
                else{
                    $(this).removeClass('checked');			
                    $(this).prev()[0].checked = false;
                }
                event.stopPropagation();
			}
		).prev().hide();
	}

    $.fn.hradio = function(options){
        return $(':radio+label',this).each(function(){
            $(this).addClass('hRadio');
            if($(this).parent().prev().children(":radio+label").is("checked"))
                $(this).addClass('hRadio_Checked');
        }).click(function(event){
            $(this).parent().siblings().children(":radio+label").removeClass("hRadio_Checked");
            if(!$(this).parent().prev().children(":radio+label").is(':checked')){
				$(this).addClass("hRadio_Checked");
				$(this).prev()[0].checked = true;				
            }
            event.stopPropagation();
        })
        .prev().hide();
    }
//Download by http://www.codefans.net
})(jQuery)