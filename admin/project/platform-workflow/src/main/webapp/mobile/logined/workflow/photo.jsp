<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="block-item bt10" id="photoUl" style="display:none">
        <div class="container">
            <div class="row" id="photoContainer">
            <!-- 
                <div class="col-xs-4 pic-upload"><img src="../images/picture.jpg"></div>
                <div class="col-xs-4 pic-upload"><img src="../images/picture.jpg"></div>
                <div class="col-xs-4 pic-upload"><img src="../images/picture.jpg"></div>
                <div class="col-xs-4 pic-upload"><img src="../images/picture.jpg"></div>
                <div class="col-xs-4 pic-upload"><img src="../images/picture.jpg"></div>
                <div class="col-xs-4 pic-upload"><img src="../images/picture.jpg"></div>
 			-->
            </div>
        </div>
</div>
<script type="text/javascript">
var photos = new Array();
$(".camera").click(function(){
	h5Adapter.selectPhoto("workflow",h5Adapter.config.uploadUrl,function(data){
		var downloadUrl = h5Adapter.config.downloadUrl;
		if(data.isSuccess){
			photos.push(data.id);
			downloadUrl+="?fileId="+data.id;
			$("#photoContainer").append("<div class='col-xs-4 pic-upload'><img src='"+downloadUrl+"'></div>");
			$("#photoUl").show();
		}
	});
});
</script>