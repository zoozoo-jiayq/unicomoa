<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计表单</title>
<jsp:include page="../../common/flatHeadNoChrome.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/index.js" ></script>

</head>
<body>
<jsp:include page="shareFormCategory.jsp" />

<div class="mainpage">
<!--左侧begin-->
	<div class="leftMenu">
	<div class="service-menu">
		<h1>表单列表<em class="fr fresh" id="refresh" ><span></span>更新缓存</em></h1>
			<p class="search"><input  id="searchName"  maxlength="20" type="text" class="formText" /></p>
			<div class="menu-cont">
			
			<c:forEach items="${fcateList}"  varStatus="status"  var="fc">
			            <c:if test="${status.first==true}">
			             	<p class="menu-p borderfirstnone" onclick="javascript:formView(${fc.categoryId},'${fc.categoryName }',2);return false;" >
			          			<i class="menu-i"></i><a href="javascript:void()">${fc.categoryName }</a>
			          		</p>
			            </c:if>
			           <c:if test="${status.first==false}">
			                <c:if test="${status.last==true}">
			             	   <p class="menu-p borderlast" onclick="javascript:formView(${fc.categoryId},'${fc.categoryName }',2);return false;" >
			          			   <i class="menu-i"></i><a href="javascript:void()">${fc.categoryName }</a>
			          		   </p>
			               </c:if>
			                <c:if test="${status.last==false}">
				             	<p class="menu-p" onclick="javascript:formView(${fc.categoryId},'${fc.categoryName }',2);return false;" >
				          			<i class="menu-i"></i><a href="javascript:void()">${fc.categoryName }</a>
				          		</p>
			          		</c:if>
			            </c:if>
							<c:if test="${fattList != null && fn:length(fattList)>0}">
									<div class="menu-c">
									<ul>
										<c:forEach items="${fattList }" var="fa">
											<c:if test="${ fc.categoryId==fa.categoryId }">
												<li><a title="${fa.formName }" href="javascript:void(0)" onclick="javascript:formView(${fa.formId},'',1);return false;">${fa.formName }</a></li>
											</c:if>
										</c:forEach>
									</ul>
									</div>
							</c:if>
						</c:forEach>
						</div>
</div>
</div>
<!--左侧end-->

   <iframe id="page" class="iframeresize" name="page"  frameborder="0" scrolling="auto" border="0"  scrolling="auto"  width="100%"  height="100%" ></iframe>
</div>

 
</body>
</html>
