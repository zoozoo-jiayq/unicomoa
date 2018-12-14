<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--设置缓存-->
<meta http-equiv="cache-control" content="no-cache,must-revalidate"/>
<meta http-equiv="pragram" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<title>公文-正文</title>
<jsp:include page="../../../common/flatHeadNoChrome.jsp" />
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/handle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/ntko/OfficeContorlFunctions.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/ntkodoc/docNtko.js"></script> 
<script>
var basePath ="${ctx}";
var currentUser="${adminUser.userName}";
$(document).click(function(){
    $(window.parent.document).find(".menu").hide(); //
    $(window.parent.parent.document).find(".menu").hide();
    $(window.parent.parent.parent.document).find(".menu").hide();
});
$(document).ready(function(){
	 $(".closeMenu").click(function(){
	     $(".mainpage").removeClass("mainpage_45");
	 });
	 $(".openMenu h2").click(function(){
	     $(".mainpage").addClass("mainpage_45");
	 });
	  $(".openMenu h3").click(function(){
		if($(".more_cz").is(":visible")){
	      $(this).next(".more_cz").slideToggle();
		}else{
		  $(this).next(".more_cz").slideToggle();
	    }
		
	 });
	 
	 if(OFFICE_CONTROL_OBJ){
	 	SetReviewMode(true);//保留痕迹
	    setShowRevisions(true);//显示痕迹
	 }	
	 init();
});

</script>
</head>
<body >
	<input type="hidden" id="changeDocPath" />
	<input type="hidden" id="signFlag" value="${adminUser.sinWidget}"/>
	<input type="hidden" id="domflow" value='<%=request.getParameter("domflow") %>'/>
	<input type="hidden" id="templateId" value='<%=request.getParameter("templateId") %>' />
	<div class="tabContent">
            <div class="mainpage mainpage_45" >
             
              <div class="openMenu">
                 <h2>隐藏常用操作</h2>
                 <ul>
                     <li><a href="#" onclick="doHandSign();">手写签批</a></li>
                     <li><a href="#" onclick="saveOffice();">保存文档</a></li>
                     <li><a href="#"  onclick="addEkeySecSign();">插入印章</a></li>
                 </ul>
                 <h3>更多 ∨</h3>
                 <ul class="more_cz">
					<li><a onclick="doCopyWenhao();" id="liwenhao" style="cursor:pointer;">获取文号</a></li>
					<li><a onclick="dogetDocHistoy();" style="cursor:pointer;">操作历史</a></li>
					<li><a onclick="doSelectDocTemplate();" id="liDocTemplate" style="cursor:pointer;">套红模板</a></li>
					<li><a onclick="OFFICE_CONTROL_OBJ.PrintPreview();" style="cursor:pointer;">打印</a></li>
                 </ul>
              </div>
                 <div class="closeMenu">
                 <p><img src="${ctx }/flat/images/colse_hd.png">显示常用操作<img src="${ctx }/flat/images/down_hd.png"></p>
              </div>
               <div class="rightForm">
               <div class="pt10" id="formDiv">
		 
		 <form  id="form1" action="../../../servlet/ntkoManagerServlet?taskId=<%=request.getParameter("taskId") %>" enctype="multipart/form-data" style="padding:0px;margin:0px;">
		 	<c:if test="${adminUser.officeWidget==1}"><script type="text/javascript" src="${ctx}js/logined/ntko/ntkoofficecontrol.js"></script></c:if>
		 	<input type="hidden" id="documentExtId"  name="documentExtId" value="<%=request.getParameter("documentExtId") %>" />
		 	<input type="hidden" name="docType"  id="docType" value="doc"     />
		 	<input type="hidden" name="taskId" id="taskId" value='<%=request.getParameter("taskId") %>' />
		 		<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
									setFileOpenedOrClosed(false);
				</script>
				<script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
									OFFICE_CONTROL_OBJ.activeDocument.saved=true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
									//获取文档控件中打开的文档的文档类型
									switch (OFFICE_CONTROL_OBJ.doctype)
									{
										case 1:
											fileType = "Word.Document";
											fileTypeSimple = "wrod";
											break;
										case 2:
											fileType = "Excel.Sheet";
											fileTypeSimple="excel";
											break;
										case 3:
											fileType = "PowerPoint.Show";
											fileTypeSimple = "ppt";
											break;
										case 4:
											fileType = "Visio.Drawing";
											break;
										case 5:
											fileType = "MSProject.Project";
											break;
										case 6:
											fileType = "WPS Doc";
											fileTypeSimple="wps";
											break;
										case 7:
											fileType = "Kingsoft Sheet";
											fileTypeSimple="et";
											break;
										default :
											fileType = "unkownfiletype";
											fileTypeSimple="unkownfiletype";
									}
									setFileOpenedOrClosed(true);
			 </script>
		</form>
		</div>
               
               </div>
          </div>
      </div>
		</body>
		</html>