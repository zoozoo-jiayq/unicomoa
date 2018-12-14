<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <iframe id="ntkoframe" src="${ctx }/logined/publicDom/ntkodoc/ntkoDoc.jsp?taskId=${taskId}&templateId=${taohongTemplate}" class="iframeoffice"   border="0" frameBorder="no" frameSpacing="0" marginWidth="0" marginHeight="0" style="width: 100%;display:block; overflow:hidden;height:800px" ></iframe>
<input type="hidden" id="isOpen" value="0" />
 <script type="text/javascript">
			//保存正文
	 function saveDoc(){
	 	try{
	 	 window.frames["ntkoframe"].saveOffice();
	 	}catch(e){
	 		
	 	}
	 }

	 function hideDoc(){
	 	var formDiv = window["ntkoframe"].formDiv;
	 	if(formDiv){
	 		formDiv.style.display = "none";
	 	}
	 }

	 function showDoc(){
	 	var formDiv = window["ntkoframe"].formDiv;
	 	if(formDiv){
	 		formDiv.style.display = "";
	 	}
	 }
	 
 </script>