
$(document).ready(function() {
	myArtClose();
});
/**
 * 关闭对话框
 */
function myArtClose(){
	if(art){
		art.dialog().close();
	}
}
