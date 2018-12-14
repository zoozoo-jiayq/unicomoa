/**
 * REN
 * IE6下关闭对话框
 */
$(document).ready(function() {
    myArtClose();
});
/**
 * 关闭对话框
 */
function myArtClose(){
	if($.browser){
		 if ( $.browser.msie&&$.browser.version==6.0 ){
		        if(art){
		            art.dialog().close();
		        }
		    }
	}
   
}
