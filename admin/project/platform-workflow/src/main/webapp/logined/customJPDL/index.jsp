<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计流程</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/logined/customJPDL/index.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/flat/plugins/autoComplete/jquery.autocomplete.css"/>
</head>
<body>
	<input type="hidden" id="processAttributeId" value="${processAttributeId}"/>
	<input type="hidden" id="type" value="${type }"/>
	
	<div class="mainpage">
<!--左侧begin-->
	<div class="leftMenu">
	<div class="service-menu">
		<h1>选择流程<em class="fr fresh" id="refresh"  onclick="javascript:window.location.reload();" ><span></span>更新缓存</em></h1>
			<p class="search"><input  id="searchProcess"  maxlength="20" type="text" class="formText" /></p>
			 <div class="menu-cont">
						     <c:forEach items="${procssCategorys}"  varStatus="pc"  var="cate">
					            <c:if test="${pc.first==true}">
					                  <input type="hidden" id="categoryId" value="${cate.formCategory.categoryId}"/>
					             	<p class="menu-p borderfirstnone"   >
					          			<i class="menu-i"></i>
					          			<a href="${ctx}workflow/manager!editProcess.action?processAttributeId=&categoryId=${cate.formCategory.categoryId}&type=${type}"  target="page" >
					          				${cate.formCategory.categoryName}
											<c:if test="${type==12 }">
					     						&nbsp;&nbsp;(${cate.formCategory.domTypeZH})
											</c:if>
					          			</a>
					          		</p>
					            </c:if>
				           <c:if test="${pc.first==false}">
				                <c:if test="${pc.last==true}">
				             	   <p class="menu-p borderlast"    >
				          			   <i class="menu-i"></i>
					          			<a href="${ctx}workflow/manager!editProcess.action?processAttributeId=&categoryId=${cate.formCategory.categoryId}&type=${type}"  target="page" >
					          				${cate.formCategory.categoryName}
					          				<c:if test="${type==12 }">
					     						&nbsp;&nbsp;(${cate.formCategory.domTypeZH})
											</c:if>
					          			</a>
				          		   </p>
				               </c:if>
				                <c:if test="${pc.last==false}">
					             	<p class="menu-p"    >
					          			<i class="menu-i"></i>
					          			<a href="${ctx}workflow/manager!editProcess.action?processAttributeId=&categoryId=${cate.formCategory.categoryId}&type=${type}"  target="page" >
					          				${cate.formCategory.categoryName}
											<c:if test="${type==12 }">
					     						&nbsp;&nbsp;(${cate.formCategory.domTypeZH})
											</c:if>
					          			</a>
					          		</p>
				          		</c:if>
				            </c:if>
								<c:if test="${pc != null && fn:length(procssCategorys)>0}">
										<div class="menu-c">
										<ul>
											<c:forEach items="${cate.processAttributes }" var="proc" varStatus="pr">
												<li><a title="${proc.processName }" cateType='${cate.formCategory.categoryName }' target="page"  href="${ctx }/workflow/manager!editProcess.action?processAttributeId=${proc.id}&type=${type}" >${proc.processName }</a></li>
											</c:forEach>
										</ul>
										</div>
								</c:if>
							</c:forEach>
				
	</div>				
			
</div>
</div>
<iframe id="page" class="iframeresize" name="page" src="" border="0" frameBorder="0" scrolling="auto" style="width: 100%; height:100%" ></iframe>
</div>
</body>
</html>