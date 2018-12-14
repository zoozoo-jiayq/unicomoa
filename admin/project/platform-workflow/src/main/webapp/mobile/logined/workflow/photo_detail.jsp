<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<dd class="imgList" id="container">
       <!-- <img src="../images/tx1.jpg">
       <img src="../images/tx2.jpg">
       <img src="../images/tx1.jpg"> -->
</dd>
 <script type="text/javascript">
 function processPhoto(imgs){
		for(var i=0; i<imgs.length; i++){
			var id = imgs[i];
		 	url = h5Adapter.config.downloadUrl+"?fileId="+id;
			$("#container").append("<img src='"+url+"'>");
		}
 }
</script>         