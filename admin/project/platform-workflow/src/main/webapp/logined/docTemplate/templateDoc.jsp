<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--设置缓存-->
<meta http-equiv="cache-control" content="no-cache,must-revalidate"/>
<meta http-equiv="pragram" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<title>常用工作流程</title>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${cxt}js/logined/ntko/OfficeContorlFunctions.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/ntkodoc/docNtko.js"></script>  
<style>
.leftHide{width:15px;display:none; cursor:pointer}
.leftShow{width:135px;}
.mainpage{ padding-left:150px;}
.mainpageHide{padding-left:30px}
</style>
<script>
		  var basePath ="${ctx}";
		   $(document).click(function(){
              $(window.parent.document).find(".menu").hide(); //
              $(window.parent.parent.document).find(".menu").hide();
              $(window.parent.parent.parent.document).find(".menu").hide();
          });
   var currentUser="${adminUser.userName}";
   $(document).ready(function(){
	$(".hideleft").click(function(){
		$(".leftShow").hide();
		$(".leftHide").show();
		$(".mainpage").addClass("mainpageHide");
		});//隐藏
		$(".leftHide").click(function(){
		$(".leftShow").show();
		$(".leftHide").hide();
		$(".mainpage").removeClass("mainpageHide");
		});//展开
	$("#more").click(function(){
	  $(".moreMemu").slideToggle("slow");
	});
	 
	 if(OFFICE_CONTROL_OBJ){
	 	SetReviewMode(true);//保留痕迹
	    setShowRevisions(true);//显示痕迹
	 }

		 
});
</script>
 <script type="text/javascript" src="${ctx}js/logined/ntko/OfficeContorlFunctions.js"></script>
  <script type="text/javascript" src="${ctx}js/logined/docTemplate/ntkoManager.js"></script>
</head>
<body   onbeforeunload ="onPageClose()" >
	<input type="hidden" id="changeDocPath" />
<div  style="position:relative">
		<div class="mainpage">
		<div class="leftMenu leftHide" style="width:15px;display:none; cursor:pointer" ><p>&gt;&gt;</p><p>显示常用操作</p></div>
		<div class="leftMenu leftShow" style="width:135px;">
		 	<table cellpadding="0" cellspacing="0" class="BlockTop">
						<tr>
								<td class="left"></td>
								<td class="center"><h3 class="hideleft"><i>&lt;&lt;</i>&nbsp;隐藏常用操作</h3></td>
								<td class="right"></td>
						</tr>
				</table>
				<div class="blockBox">
						<h3>&nbsp;文件操作</h3>
						<ul class="menuList">
						       <!-- 
								<li class="current"><a onclick="doSelectSignByType(2);" style="cursor:pointer;">个人签章</a></li>
								<li><a onclick="doHandSign();" style="cursor:pointer;">手写签批</a></li>
								<li><a onclick="doSelectSignByType(1);" style="cursor:pointer;">单位盖章</a></li>
								 -->
								<li ><a onclick="saveOffice();" style="cursor:pointer;" >保存文档</a></li>
						</ul>
						<h3 id="more">&nbsp;更多操作</h3>
						<ul class="menuList moreMemu">
								<li><a onclick="OFFICE_CONTROL_OBJ.showDialog(5);" style="cursor:pointer;">页面设置</a></li>
								<!-- 
								<li><a  onclick="SetReviewMode(true);" style="cursor:pointer;">保留痕迹</a></li>
								<li><a onclick="SetReviewMode(false);" style="cursor:pointer;">不留痕迹</a></li>
								<li><a onclick="setShowRevisions(true);" style="cursor:pointer;">显示痕迹</a></li>
								 <li><a onclick="setShowRevisions(false);" style="cursor:pointer;">隐藏痕迹</a></li> 
								<li><a onclick="DoCheckSign();" style="cursor:pointer;">印章验证</a></li>
								<li><a onclick="doCopyWenhao();" style="cursor:pointer;">获取文号</a></li>
								<li><a onclick="dogetDocHistoy();" style="cursor:pointer;">操作历史</a></li>
								<li><a onclick="doSelectDocTemplate();" style="cursor:pointer;">套红模板</a></li>
								-->
								<li><a onclick="OFFICE_CONTROL_OBJ.PrintPreview();" style="cursor:pointer;">打印</a></li>
				</div>			
		</div>
		<div class="pt10" id="formDiv">
		 
		 <form id="form1" action="../../servlet/ntkoTemplateManagerServlet?docTemplateId=<%=request.getParameter("docTemplateId") %>" enctype="multipart/form-data" style="padding:0px;margin:0px;">
		 	<script type="text/javascript" src="${ctx}js/logined/ntko/ntkoofficecontrol.js"></script>
		 	<input type="hidden" id="documentExtId"  name="documentExtId" />
		 	<input type="hidden" name="docType"  id="docType" value="doc"     />
		 	<input type="hidden" name="docTemplateId" id="docTemplateId" value='<%=request.getParameter("docTemplateId") %>' />
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
		</body>
		<script type="text/javascript">
			jQuery(document).ready(function($){
				init();
			});
		</script>
		</html>