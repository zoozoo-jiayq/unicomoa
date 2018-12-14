$(document).ready(function() {
	qytx.app.fileupload({
		id:"file_upload",
		moduleName:"publicDom",
		ulName:"attachUL",
		callback:function(data){
			//添加附件
        	$.post(basePath+"/dom/public!addAttach.action?attachId="+data.id+"&r="+Math.random()+"&instanceId="+encodeURI($("#instanceId").val()),function(){
        		
        	});
		},
		deleteFun:function(fileId){
			deleteAttach(fileId);
		}
	});
	
//显示附件
function createOneAttachmentHTML(fileName, id,isDelete,uploader) {
	var previewUrl = getPreviewUrl(id, null,  null);
	
    var className = getClassByFileName(fileName);
    var html;
    if(className!=null){
    	html="<li ><div class='icon'> <em class='"+className+"'></em></div>";
    	html+="<div class='txt'><p>"+ fileName+"</p><p><a   style=\"cursor:pointer\" href=\""+basePath+"filemanager/downfile.action?attachmentId="+id+"\"    >下载</a></p></div><p class='clear'></p></li>" ; 
     //html+="<div class='txt'><p><a style=\"cursor:pointer\"  onclick=\"buildAttachPrev('"+fileName+"',"+id+")\"   >"+ fileName+"</a></p><p><a style=\"cursor:pointer\"  onclick=\"buildAttachPrev('"+fileName+"',"+id+")\"   >预览</a><a   style=\"cursor:pointer\" href=\""+basePath+"upload/downfile.action?attachmentId="+id+"\"    >下载</a></p></div><p class='clear'></p></li>"
  	 }else{
  	 	html="<li><p class='no_acc'>暂无附件</p></li>";
  	 }  
    $('#attachUL').append(html); 
}



//加载附件列表
(function(){
	$.get(basePath+"dom/public!getAttachList.action?instanceId="+encodeURI($("#instanceId").val())+"&random="+Math.random(),function(data){
		if(data){
			data = eval('('+data+')');
			$("#attachSize").html("("+data.length+")");
			for (var i = data.length - 1; i >= 0; i--) {
				var temp = data[i];
				var fileName = temp.attachName;
				var id = temp.id;
				createOneAttachmentHTML(fileName,id,null,null);
			}
		};
	});
})();
});

//删除附件
function deleteAttach(attachId){
	 $.get(basePath+"dom/public!deleteAttach.action?attachId="+attachId+"&instanceId="+encodeURI($("#instanceId").val()),function(data){
	    	
	    });
}